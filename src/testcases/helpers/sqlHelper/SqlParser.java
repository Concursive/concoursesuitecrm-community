/*
 *  Copyright(c) 2007 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
package helpers.sqlHelper;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;

/**
 * Description of the Class
 *
 * @author holub
 * @version $Id: Exp $
 * @created Feb 7, 2007
 *
 */
public class SqlParser {
  private String scriptsFilter = "new_.*\\.sql";

  private String scriptsDir = "";

  public SqlParser(String scriptsDir) {
    this(scriptsDir, null);
  }

  public SqlParser(String scriptsDir, String scriptsFilter) {
    if (scriptsFilter != null) {
      this.scriptsFilter = scriptsFilter;
    }
    this.scriptsDir = scriptsDir;
  }

  public SqlEntry getEntries(String tableName) {
    String script = findTable(tableName);
    SqlEntry entries = parseScript(script);

    return entries;
  }

  private String findTable(String tableName) {
    String[] files = (new Filter()).getFiles(scriptsDir, scriptsFilter);
    String fileContent = "";
    for (String file : files) {
      try {
        fileContent = new String(readFile(scriptsDir + file));
        if(fileContent.indexOf("CREATE TABLE " + tableName + " (") == -1 && 
           fileContent.indexOf("CREATE TABLE " + tableName + "(") == -1){
          fileContent = "";
        }else{
          if(fileContent.indexOf("CREATE TABLE " + tableName + " (") > -1){
            fileContent = fileContent.substring(fileContent.indexOf("CREATE TABLE " + tableName + " ("));
          }else{
            fileContent = fileContent.substring(fileContent.indexOf("CREATE TABLE " + tableName + "("));
          }
          if(fileContent.length() != 0){
            fileContent = fileContent.substring(0, fileContent.indexOf(";"));
            break;
          }
        }
        
      } catch (IOException ioe) {
      }
    }

    return fileContent;
  }
  
  public static byte[] readFile(String fileName) throws IOException{
    int nSize = 32768;
    // open the input file stream
    BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(fileName), nSize);
    byte[] pBuffer = new byte[nSize];
    int nPos = 0;
    // read bytes into a buffer
    nPos += inStream.read(pBuffer, nPos, nSize - nPos);
    // while the buffer is filled, double the buffer size and read more
    while (nPos == nSize) {
      byte[] pTemp = pBuffer;
      nSize *= 2;
      pBuffer = new byte[nSize];
      System.arraycopy(pTemp, 0, pBuffer, 0, nPos);
      nPos += inStream.read(pBuffer, nPos, nSize - nPos);
    }
    // close the input stream
    inStream.close();
    if (nPos == 0) {
      return "".getBytes();
    }
    // return data read into the buffer as a byte array
    byte[] pData = new byte[nPos];
    System.arraycopy(pBuffer, 0, pData, 0, nPos);
    return pData;
  }

  private SqlEntry parseScript(String script) {
    SqlEntry entry = new SqlEntry();
    String[] lines = script.split("\n");
    for(int i=0; i<lines.length; i++) {
      if(lines[i].trim().length() > 3){
        String[] fields = lines[i].trim().split("\\s");
        if(i != 0){
          //Fields
          if(fields[0].indexOf(",") == 0){
            fields[0] = fields[0].substring(1, fields[0].length());
          }
          if(fields[0].indexOf("\"") > -1){
            fields[0] = fields[0].substring(1, fields[0].length() - 1);
          }
          if(fields[1].indexOf(")") > -1){
            fields[1] = fields[1].substring(0, fields[1].indexOf(")"));
          }
          if(fields[1].toUpperCase().equals("CHAR(1")){
            fields[1] = "BOOLEAN";
          }
          if(fields[1].indexOf("(") > -1){
            fields[1] = fields[1].substring(0, fields[1].indexOf("("));
          }
          if(fields[1].indexOf(",") > -1){
            fields[1] = fields[1].substring(0, fields[1].indexOf(","));
          }
          if(fields[1].equalsIgnoreCase("SERIAL")){
            entry.setUniqueField(fields[0]);
            fields[1] = "INT";
          }
          entry.addField(fields[0], fields[1]);
          //Add references
          for(int j=2; j<fields.length; j++){
            if("references".equals(fields[j].toLowerCase())){
              if(fields[j+1].indexOf("(") > -1){
                fields[j+1] = fields[j+1].substring(0, fields[j+1].indexOf("("));
              }
              entry.addReference(fields[0], fields[j+1]);
              break;
            }
          }

          //Add sequence
          boolean isPrimary = false;
          for(int j=2; j<fields.length-1; j++){
            if("primary".equals(fields[j].toLowerCase()) && fields[j+1].toLowerCase().startsWith("key")){
              isPrimary = true;
              entry.setUniqueField(fields[0]);
            }
          }
          if(isPrimary){
            for(int j=2; j<fields.length; j++){
              if(fields[j].toLowerCase().startsWith("nextval")){
                entry.setPrimSequence(fields[j].substring(fields[j].indexOf("'") + 1, fields[j].indexOf("'", fields[j].indexOf("'") +1)));
                break;
              }
            }
          }
          
        }else{
          if(fields[2].indexOf("(") == -1){
            entry.setTableName(fields[2]);
          }else{
            entry.setTableName(fields[2].substring(0, fields[2].length() -1));
          }
        }
      }
    }
    return entry;
  }

//------------------------------------------------------------------------------  
  
  public class Filter implements FilenameFilter {
    protected String pattern;

    public boolean accept(File dir, String name) {
      return name.matches(pattern);
    }

    public Filter() {
    }

    public Filter(String filter) {
      pattern = filter;
    }

    public String[] getFiles(String dirPath, String filter) {
      this.pattern = filter;

      Filter nf = new Filter(filter);
      File dir = new File(dirPath);
      return dir.list(nf);
    }
  }
}
