package org.aspcfs.utils;

import java.io.*;
import javax.imageio.*;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

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
    BufferedImage originalImage = ImageIO.read(originalFile);
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
    if (System.getProperty("DEBUG") != null) {
      System.out.println("ImageUtils-> Ratio: " + ratio);
    }
    AffineTransform at = AffineTransform.getScaleInstance(ratio, ratio);
    AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
    BufferedImage thumbnailImage = op.filter(originalImage, null);
    ImageIO.write(thumbnailImage, "jpg", thumbnailFile);
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
    java.io.InputStream input;
    byte buffer[];
    int bytes;
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
    Process process;
    Runtime runtime;
    java.io.InputStream input;
    byte buffer[];
    int bytes;
    String command[] = {"/bin/sh", "-c", "/usr/bin/htmldoc --quiet --jpeg " +
        "--webpage -t ps --left 0 --top 0 " +
        "--header ... --footer ... --landscape " + url + " " +
        "| /usr/bin/gs -q -sDEVICE=jpeg -dNOPAUSE -dBATCH -sOutputFile=- -"};
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
    byte buffer[];
    int bytes;
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

