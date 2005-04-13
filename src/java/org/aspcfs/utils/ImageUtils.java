/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.utils;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;

/**
 *  Various utilities that relate to Image processing
 *
 *@author     matt rajkowski
 *@created    2002
 *@version    $Id$
 */
public class ImageUtils {
  /**
   *  Takes an image file and saves a copy with th maximum specified width and
   *  height, while retaining the image's aspect ratio.
   *
   *@param  originalFile     Description of the Parameter
   *@param  thumbnailFile    Description of the Parameter
   *@param  maxWidth         Description of the Parameter
   *@param  maxHeight        Description of the Parameter
   *@exception  IOException  Description of the Exception
   */
  public static void saveThumbnail(File originalFile, File thumbnailFile, double maxWidth, double maxHeight) throws IOException {
    BufferedImage thumbnailImage = ImageIO.read(originalFile);
    double ratioWidth = maxWidth / thumbnailImage.getWidth();
    double ratioHeight = maxHeight / thumbnailImage.getHeight();
    double ratio = 1;
    if (thumbnailImage.getWidth() <= maxWidth && thumbnailImage.getHeight() <= maxHeight) {
      ratio = 1;
    } else {
      // Conform to Width
      if (maxWidth > 0 && maxHeight <= 0) {
        ratio = ratioWidth;
      }
      // Conform to Height
      if (maxWidth <= 0 && maxHeight > 0) {
        ratio = ratioHeight;
      }
      // Conform to Width and Height
      if (maxWidth > 0 && maxHeight > 0) {
        if (ratioWidth < ratioHeight) {
          ratio = ratioWidth;
        } else {
          ratio = ratioHeight;
        }
      }
    }
    if (System.getProperty("DEBUG") != null) {
      System.out.println("ImageUtils-> Ratio: " + ratio);
    }

    if (ratio == 1.0) {
      FileUtils.copyFile(originalFile, thumbnailFile);
    } else {
      //Soften
      try {
        float softenFactor = 0.05f;
        float[] softenArray = {0, softenFactor, 0, softenFactor, 1-(softenFactor*4), softenFactor, 0, softenFactor, 0};
        Kernel kernel = new Kernel(3, 3, softenArray);
        ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        thumbnailImage = cOp.filter(thumbnailImage, null);
      } catch (Exception e) {
        System.out.println("ImageUtils-> Soften: " + e.getMessage());
      }

      //Sharpen
      /* float[] sharpenArray = { 0, -1, 0, -1, 5, -1, 0, -1, 0 };
      Kernel kernel = new Kernel(3, 3, sharpenArray);
      ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
      thumbnailImage = cOp.filter(thumbnailImage, null);
       */

      //Scale
      AffineTransform at = AffineTransform.getScaleInstance(ratio, ratio);
      AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
      thumbnailImage = op.filter(thumbnailImage, null);
      ImageIO.write(thumbnailImage, "jpg", thumbnailFile);
    }
  }


  /**
   *  Converts a Postscript file to a Tiff file using ghostscript command-line
   *  application
   *
   *@param  baseFilename  Description of the Parameter
   *@return               Description of the Return Value
   */
  public static int convertPostscriptToTiffG3File(String baseFilename) {
    Process process;
    Runtime runtime;
    String[] command = null;
    File osCheckFile = new File("/bin/sh");
    if (osCheckFile.exists()) {
      //Linux
      command = new String[]{"/bin/sh", "-c",
          "gs -q " +
          "-sDEVICE=tiffg3 " +
          "-dNOPAUSE " +
          "-dBATCH " +
          "-sOutputFile=" + baseFilename + ".tiff " +
          baseFilename + ".ps"};
    } else {
      //Windows
      command = new String[]{"gs",
          "-q " +
          "-sDEVICE=tiffg4 " +
          "-dNOPAUSE " +
          "-dBATCH " +
          "-sOutputFile=" + baseFilename + ".tiff " +
          baseFilename + ".ps"};
    }
    runtime = Runtime.getRuntime();
    try {
      process = runtime.exec(command);
      return (process.waitFor());
    } catch (Exception e) {
      System.err.println("ImageUtils-> convertPostscriptToTiff error: " + e.toString());
      return (1);
    }
  }


  /**
   *  Uses HtmlDoc command line tool, pipes an HTML URL to GhostScript
   *  which outputs a JPEG file with the given dimensions.
   *
   *@param  url        Description of the Parameter
   *@param  filename   Description of the Parameter
   *@param  maxWidth   Description of the Parameter
   *@param  maxHeight  Description of the Parameter
   *@return            Description of the Return Value
   */
  public static int urlToJpegThumbnail(String url, String filename, double maxWidth, double maxHeight) {
    // Determine paths
    File gsPath = new File("/usr/bin/gs");
    if (!gsPath.exists()) {
      gsPath = new File("/sw/bin/gs");
    }
    Process process;
    Runtime runtime;
    java.io.InputStream input;
    String command[] = {"/bin/sh", "-c", "/usr/bin/htmldoc --quiet --jpeg " +
        "--webpage -t ps --left 0 --top 0 " +
        "--header ... --footer ... --landscape " + url + " " +
        "| " + gsPath.getAbsoluteFile() + " -q -sDEVICE=jpeg -dNOPAUSE -dBATCH -sOutputFile=- -"};
    runtime = Runtime.getRuntime();
    try {
      process = runtime.exec(command);
      input = process.getInputStream();
      BufferedImage originalImage = ImageIO.read(input);

      //Calculate scaling
      double ratioWidth = maxWidth / originalImage.getWidth();
      double ratioHeight = maxHeight / originalImage.getHeight();
      double ratio = 1;
      if (maxWidth > 0 && maxHeight < 0) {
        ratio = ratioWidth;
      } else if (maxHeight > 0 && maxWidth < 0) {
        ratio = ratioHeight;
      } else {
        if (ratioWidth < ratioHeight) {
          ratio = ratioWidth;
        } else {
          ratio = ratioHeight;
        }
      }
      //Scale
      AffineTransform at = new AffineTransform();
      at.scale(ratio, ratio);
      AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
      BufferedImage scaledImage = op.filter(originalImage, null);

      //Rotate
      at = new AffineTransform();
      at.setToTranslation(scaledImage.getHeight() / 2, scaledImage.getWidth() / 2);
      at.rotate(Math.toRadians(90));
      at.translate(-(scaledImage.getWidth() / 2), -(scaledImage.getHeight() / 2));
      op = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
      BufferedImage thumbnailImage = op.filter(scaledImage, null);

      File thumbnailFile = new File(filename);
      ImageIO.write(thumbnailImage, "jpg", thumbnailFile);
      return (process.waitFor());
    } catch (Exception e) {
      e.printStackTrace(System.out);
      System.err.println("GraphicUtils-> urlToJpeg error: " + e.toString());
      return (1);
    }
  }


  /**
   *  Uses HtmlDoc command line tool, pipes an HTML URL to GhostScript
   *  which outputs a JPEG file.
   *
   *@param  url       Description of the Parameter
   *@param  filename  Description of the Parameter
   *@return           Description of the Return Value
   */
  public static int urlToJpegThumbnail(String url, String filename) {
    Process process;
    Runtime runtime;
    java.io.InputStream input;
    String command[] = {"/bin/sh", "-c", "/usr/bin/htmldoc --quiet --jpeg " +
        "--webpage -t ps --left 0 --top 0 " +
        "--header ... --footer ... " + url + " " +
        "| /usr/bin/gs -q -sDEVICE=jpeg -dNOPAUSE -dBATCH -sOutputFile=- -"};
    runtime = Runtime.getRuntime();
    try {
      process = runtime.exec(command);
      input = process.getInputStream();
      BufferedImage originalImage = ImageIO.read(input);
      File thumbnailFile = new File(filename);
      ImageIO.write(originalImage, "jpg", thumbnailFile);
      return (0);
    } catch (Exception e) {
      e.printStackTrace(System.out);
      System.err.println("GraphicUtils-> urlToJpeg error: " + e.toString());
      return (1);
    }
  }
}

