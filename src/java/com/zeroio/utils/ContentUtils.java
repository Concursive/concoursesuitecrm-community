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

import com.zeroio.iteam.base.FileItem;
import org.apache.poi.hdf.extractor.WordDocument;
import org.apache.poi.poifs.eventfilesystem.POIFSReader;
import org.apache.xerces.xni.parser.XMLDocumentFilter;
import org.apache.xerces.xni.parser.XMLInputSource;
import org.apache.xerces.xni.parser.XMLParserConfiguration;
import org.aspcfs.utils.StringUtils;
import org.cyberneko.html.HTMLConfiguration;
import org.cyberneko.html.filters.ElementRemover;
import org.pdfbox.pdfparser.PDFParser;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.util.PDFTextStripper;
import org.textmining.text.extraction.WordExtractor;

import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.rtf.RTFEditorKit;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Class to extract text for various file formats as they become available
 *
 * @author matt rajkowski
 * @version $Id$
 * @created June 14, 2004
 */
public class ContentUtils {

  /**
   * Gets the text attribute of the ContentUtils class
   *
   * @param fileItem Description of the Parameter
   * @return The text value
   */
  public static String getText(FileItem fileItem) {
    // TODO: Content should have no returns, no whitespace except logical spaces
    String contents = null;
    String ext = fileItem.getExtension();
    String filename = fileItem.getFullFilePath();
    if (".doc".equals(ext)) {
      try {
        // TextMining
        FileInputStream input = new FileInputStream(filename);
        WordExtractor extractor = new WordExtractor();
        contents = extractor.extractText(input);
      } catch (Exception e) {
        try {
          // POI
          Writer out = new StringWriter();
          WordDocument wordDocument = new WordDocument(filename);
          wordDocument.writeAllText(out);
          contents = out.toString();
        } catch (Exception ee) {
        }
      }
    } else if (".html".equals(ext) || ".htm".equals(ext)) {
      try {
        String encoding = "ISO-8859-1";
        ElementRemover remover = new ElementRemover();
        remover.removeElement("script");
        Writer out = new StringWriter();
        org.cyberneko.html.filters.Writer writer =
            new org.cyberneko.html.filters.Writer(out, encoding);
        XMLDocumentFilter[] filters = {
          remover,
          writer,
        };
        XMLParserConfiguration parser = new HTMLConfiguration();
        parser.setProperty(
            "http://cyberneko.org/html/properties/filters", filters);
        XMLInputSource source = new XMLInputSource(
            null, "file://" + filename, null);
        parser.parse(source);
        contents = StringUtils.fromHtmlValue(out.toString());
      } catch (Exception e) {
        e.printStackTrace(System.out);
      }
    } else if (".pdf".equals(ext)) {
      try {
        //Writer out = new StringWriter();
        FileInputStream input = new FileInputStream(filename);
        PDFParser parser = new PDFParser(input);
        parser.parse();
        PDDocument pdfDocument = parser.getPDDocument();
        if (pdfDocument.isEncrypted()) {
          //Just try using the default password and move on
          pdfDocument.decrypt("");
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(out);
        PDFTextStripper stripper = new PDFTextStripper();
        stripper.writeText(pdfDocument, writer);
        pdfDocument.close();
        writer.close();
        byte[] bytes = out.toByteArray();
        //InputStreamReader reader = new InputStreamReader(new ByteArrayInputStream(bytes));
        contents = new String(bytes);
        input.close();
      } catch (Exception e) {
      }
    } else if (".ppt".equals(ext) || ".xls".equals(ext)) {
      try {
        ContentPOIFSReaderListener listener = new ContentPOIFSReaderListener();
        POIFSReader reader = new POIFSReader();
        reader.registerListener(listener);
        reader.read(new FileInputStream(filename));
        contents = listener.getContents();
      } catch (Exception e) {
      }
    } else if (".rtf".equals(ext)) {
      try {
        FileReader reader = new FileReader(filename);
        DefaultStyledDocument document = new DefaultStyledDocument();
        RTFEditorKit rtf = new RTFEditorKit();
        rtf.read(reader, document, 0);
        contents = document.getText(0, document.getLength());
      } catch (Exception e) {
      }
    } else if (".sxw".equals(ext)) {
      try {
        // Use zip utils to get a handle on content.xml
        ZipInputStream zip = new ZipInputStream(new FileInputStream(filename));
        ZipEntry entry = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        while ((entry = zip.getNextEntry()) != null) {
          if (entry.getName().endsWith("content.xml")) {
            int count;
            while ((count = zip.read(buffer)) != -1) {
              out.write(buffer, 0, count);
            }
          }
        }
        zip.close();
        contents = stripXML(new String(out.toByteArray()));
      } catch (Exception e) {
      }
    } else if (".txt".equals(ext)) {
      try {
        contents = StringUtils.loadText(filename);
      } catch (Exception e) {
      }
    }
    if (contents != null) {
      if (System.getProperty("DEBUG") != null) {
        System.out.println(
            "ContentUtils-> ClientName: " + fileItem.getClientFilename());
        System.out.println(
            "ContentUtils-> Extension: " + fileItem.getExtension());
        System.out.println("ContentUtils-> Name: " + fileItem.getFilename());
        System.out.println(
            "ContentUtils-> Directory: " + fileItem.getFullFilePath());
        System.out.println("ContentUtils-> Size: " + fileItem.getSize());
      }
      System.out.println(
          "*[ BEGIN ]*****************************************************");
      System.out.println(contents);
      System.out.println(
          "*[ END ]*******************************************************");
      return (StringUtils.replace(contents, "\r\n", " "));
    } else {
      return contents;
    }
  }


  /**
   * Format the content for search results page
   *
   * @param content Description of the Parameter
   * @return Description of the Return Value
   */
  public static String toText(String content) {
    if (content == null) {
      return "";
    }
    StringBuffer sb = new StringBuffer();
    boolean gotSpace = false;
    for (int i = 0; i < content.length(); i++) {
      // Strip extra spaces, all returns
      char a = content.charAt(i);
      if (a == '\r' || a == '\n' || a == '\t' || a == ' ') {
        //01, 14, 15
        if (!gotSpace) {
          gotSpace = true;
          sb.append(" ");
        }
      } else {
        sb.append(a);
        gotSpace = false;
      }
    }
    return sb.toString();
  }


  /**
   * Description of the Method
   *
   * @param content Description of the Parameter
   * @return Description of the Return Value
   */
  public static String stripHTML(String content) {
    if (content == null) {
      return null;
    }
    // NOTE: temporary fix
    content = StringUtils.replace(content, "<li>", "<li> ");
    content = StringUtils.replace(content, "<br />", "<br /> ");
    content = StringUtils.replace(content, "<br>", "<br> ");
    content = StringUtils.replace(content, "</ul>", "</ul> ");
    content = StringUtils.replace(content, "</ol>", "</ol> ");
    try {
      ByteArrayInputStream byteIn = new ByteArrayInputStream(
          content.getBytes());
      String encoding = "ISO-8859-1";
      ElementRemover remover = new ElementRemover();
      remover.removeElement("script");
      Writer out = new StringWriter();
      org.cyberneko.html.filters.Writer writer =
          new org.cyberneko.html.filters.Writer(out, encoding);
      XMLDocumentFilter[] filters = {
        remover,
        writer,
      };
      XMLParserConfiguration parser = new HTMLConfiguration();
      parser.setProperty(
          "http://cyberneko.org/html/properties/filters", filters);
      XMLInputSource source = new XMLInputSource(
          null, null, null, byteIn, null);
      parser.parse(source);
      return (StringUtils.fromHtmlValue(out.toString()));
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }
    return content;
  }


  /**
   * Description of the Method
   *
   * @param content Description of the Parameter
   * @return Description of the Return Value
   */
  public static String stripXML(String content) {
    if (content == null) {
      return null;
    }
    try {
      StringBuffer sb = new StringBuffer();
      boolean isOpen = false;
      for (int i = 0; i < content.length(); i++) {
        char letter = content.charAt(i);
        if (letter == '<') {
          isOpen = true;
        }
        if (!isOpen) {
          sb.append(letter);
        }
        if (letter == '>') {
          isOpen = false;
          sb.append(' ');
        }
      }
      return (StringUtils.fromHtmlValue(sb.toString()));
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }
    return content;
  }
}

