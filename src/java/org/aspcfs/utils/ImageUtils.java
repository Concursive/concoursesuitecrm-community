package com.darkhorseventures.utils;

import java.io.*;
import javax.imageio.*;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

public class ImageUtils {
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
}
