package com.darkhorseventures.utils;

import java.util.zip.*;
import java.io.*;

public class ZipUtils {

  public static void addTextEntry(ZipOutputStream zip, String fileName, String data) throws ZipException, IOException {
    int bytesRead;
    StringReader file = new StringReader(data);
    ZipEntry entry = new ZipEntry(fileName);
    zip.putNextEntry(entry);
    while ((bytesRead = file.read()) != -1) {
       zip.write(bytesRead);
    }
  }


}
