package com.darkhorseventures.utils;

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
  public static int convertPostscriptToTiffFile(String baseFilename) {
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
        "-sDEVICE=tiffg4 " +
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
}

