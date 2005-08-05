/*
 *  Copyright(c) 2004 Team Elements LLC (http://www.teamelements.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Team Elements LLC. Permission to use, copy, and modify this
 *  material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. TEAM
 *  ELEMENTS MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL TEAM ELEMENTS LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR ANY
 *  DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package com.zeroio.utils;

import org.apache.poi.hpsf.PropertySet;
import org.apache.poi.poifs.eventfilesystem.POIFSReaderEvent;
import org.apache.poi.poifs.eventfilesystem.POIFSReaderListener;
import org.apache.poi.util.LittleEndian;

import java.io.ByteArrayOutputStream;

/**
 * Description of the Class
 *
 * @author matt rajkowski
 * @version $Id: ContentPOIFSReaderListener.java,v 1.1 2004/06/21 02:31:19
 *          matt Exp $
 * @created June 17, 2004
 */
public class ContentPOIFSReaderListener implements POIFSReaderListener {

  private ByteArrayOutputStream out = new ByteArrayOutputStream();


  /**
   * Description of the Method
   *
   * @param event Description of the Parameter
   */
  public void processPOIFSReaderEvent(POIFSReaderEvent event) {
    PropertySet ps = null;
    try {
      org.apache.poi.poifs.filesystem.DocumentInputStream
          dis = null;
      dis = event.getStream();
      byte btoWrite[] = new byte[dis.available()];
      dis.read(btoWrite, 0, dis.available());
      for (int i = 0; i < btoWrite.length - 20; i++) {
        long type = LittleEndian.getUShort(btoWrite, i + 2);
        long size = LittleEndian.getUInt(btoWrite, i + 4);
        if (type == 4008) {
          out.write(btoWrite, i + 4 + 1, (int) size + 3);
          out.write(" ".getBytes());
        }
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }


  /**
   * Gets the contents attribute of the ContentPOIFSReaderListener object
   *
   * @return The contents value
   */
  public String getContents() {
    byte[] bytes = out.toByteArray();
    return (new String(bytes));
  }
}

