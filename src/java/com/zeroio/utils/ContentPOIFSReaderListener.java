/*
 *  Copyright 2000-2004 Matt Rajkowski
 *  matt.rajkowski@teamelements.com
 *  http://www.teamelements.com
 *  This class cannot be modified, distributed or used without
 *  permission from Matt Rajkowski and Team Elements
 */
package com.zeroio.utils;

import java.io.*;
import java.util.*;
import org.apache.poi.hpsf.*;
import org.apache.poi.poifs.eventfilesystem.*;
import org.apache.poi.util.HexDump;
import org.apache.poi.util.LittleEndian;

/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    June 17, 2004
 *@version    $Id: ContentPOIFSReaderListener.java,v 1.1 2004/06/21 02:31:19
 *      matt Exp $
 */
public class ContentPOIFSReaderListener implements POIFSReaderListener {

  private ByteArrayOutputStream out = new ByteArrayOutputStream();


  /**
   *  Description of the Method
   *
   *@param  event  Description of the Parameter
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
   *  Gets the contents attribute of the ContentPOIFSReaderListener object
   *
   *@return    The contents value
   */
  public String getContents() {
    byte[] bytes = out.toByteArray();
    return (new String(bytes));
  }
}

