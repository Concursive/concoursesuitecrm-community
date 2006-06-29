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
package org.aspcfs.modules.products.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.ImageUtils;

import com.isavvix.tools.FileInfo;
import com.zeroio.iteam.base.FileItem;
import com.zeroio.iteam.base.Thumbnail;

/**
 * Represents importer for Contacts
 * 
 * @author Aliaksei.Yarotski
 * @version $Id: ContactImport.java,v 1.7.12.1 2004/11/12 19:55:25 mrajkowski
 *          Exp $
 * @created May 29, 2006
 */
public class ProductCatalogImages extends HashMap {

  public ProductCatalogImages() {
    super();
  }

  /**
   * Constructor for the ProductCatalogImages object
   * 
   * @param fileItem
   * @param zipFilePath
   * @throws IOException
   */
  public ProductCatalogImages(FileItem fileItem, String zipFilePath)
      throws IOException {
    File zipFile = new File(zipFilePath + fileItem.getFilename());
    ZipInputStream zip = new ZipInputStream(new FileInputStream(zipFile));
    ZipEntry entry = null;
    FileInfo fileInfo;
    while ((entry = zip.getNextEntry()) != null) {
      fileInfo = new FileInfo();
      fileInfo.setLocalFile(new File(entry.getName()));
      fileInfo.setSize((int) entry.getSize());
      this.put(entry.getName(), fileInfo);
    }
    zip.close();
  }

  /**
   * Unzip client images
   * 
   * @param fileItem
   * @param zipFilePath
   * @return
   * @throws IOException
   */
  public static ProductCatalogImages processZipFile(FileItem fileItem,
      String zipFilePath) throws IOException {
    String imagesPath = zipFilePath;
    File zipFile = new File(zipFilePath + fileItem.getFilename());
    ZipInputStream zip = new ZipInputStream(new FileInputStream(zipFile));
    ZipEntry entry = null;
    FileInfo fileInfo;
    ProductCatalogImages fileList = new ProductCatalogImages();
    byte[] buffer = new byte[1024];
    String filenameToUse = null;
    int fileCount = 0;
    while ((entry = zip.getNextEntry()) != null) {
      if (!entry.isDirectory()) {
        fileCount++;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        filenameToUse = formatter.format(new java.util.Date());
        if (fileCount > 1) {
          filenameToUse += String.valueOf(fileCount);
        }
        String fileName = imagesPath + filenameToUse;
        FileOutputStream file = new FileOutputStream(fileName);
        int count;
        while ((count = zip.read(buffer)) != -1) {
          file.write(buffer, 0, count);
        }
        file.close();
        fileInfo = new FileInfo();
        File newFile = new File(fileName);
        fileInfo.setLocalFile(newFile);
        fileInfo.setName(newFile.getName());
        fileInfo.setSize((int) entry.getSize());
        fileList.put(entry.getName(), fileInfo);
      }
    }
    zip.close();
    return fileList;
  }

  public FileItem saveImage(Connection db, String imageFileName, int productId,
      int userId) {
    FileInfo largeImageInfo = (FileInfo) this.get(imageFileName);
    FileItem image = new FileItem();
    try {
      image.setSubject("Attachmect");
      image.setClientFilename(imageFileName);
      image.setFilename(largeImageInfo.getRealFilename());
      image.setVersion(1.0);
      image.setSize(largeImageInfo.getSize());
      image.setEnabled(false);
      image.setLinkItemId(productId);
      image.setLinkModuleId(Constants.DOCUMENTS_PRODUCT_CATALOG);
      image.setEnteredBy(userId);
      image.setModifiedBy(userId);
      boolean largeImageInserted = image.insert(db);
      if (image.isImageFormat() && largeImageInserted) {
        // Create a thumbnail if this is an image
        File thumbnailFile = new File(largeImageInfo.getLocalFile().getPath()
            + "TH");
        try {
          ImageUtils.saveThumbnail(largeImageInfo.getLocalFile(),
              thumbnailFile, 133d, 133d);
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
          return null;
        }
        // Store thumbnail in database
        Thumbnail thumbnail = new Thumbnail();
        thumbnail.setId(image.getId());
        thumbnail.setFilename(largeImageInfo.getRealFilename() + "TH");
        thumbnail.setVersion(image.getVersion());
        thumbnail.setSize((int) thumbnailFile.length());
        thumbnail.setEnteredBy(image.getEnteredBy());
        thumbnail.setModifiedBy(image.getModifiedBy());
        thumbnail.insert(db);
      } else {
        return null;
      }
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return null;
    }
    return image;
  }

}
