package com.darkhorseventures.apps.dataimport.writer;

import com.darkhorseventures.apps.dataimport.*;
import java.util.*;
import java.util.logging.*;
import com.darkhorseventures.utils.*;
import java.io.*;

public class TextWriter implements DataWriter {
  private String lastResponse = null;
  private String filename = null;
  private boolean overwrite = true;
  private String fieldSeparator = null;
  private boolean showColumnNames = true;
  private PrintWriter out = null;
  
  private int recordCount = 0;
  
  public void setFilename(String tmp) {
    this.filename = tmp;
  }
  
  public void setOverwrite(boolean tmp) { this.overwrite = tmp; }
  public void setOverwrite(String tmp) { this.overwrite = "true".equals(tmp); }
  public void setFieldSeparator(String tmp) { this.fieldSeparator = tmp; }
  public void setShowColumnNames(boolean tmp) { this.showColumnNames = tmp; }
  public void setShowColumnNames(String tmp) { this.showColumnNames = "true".equals(tmp); }

  public void setAutoCommit(boolean flag) {}
  
  public double getVersion() {
    return 1.0d;
  }


  public String getName() {
    return "Text Writer";
  }


  public String getDescription() {
    return "Generates a text representation of data as specified";
  }


  public String getLastResponse() {
    return lastResponse;
  }
  
  public String getFilename() { return filename; }
  public boolean getOverwrite() { return overwrite; }
  public String getFieldSeparator() { return fieldSeparator; }
  public boolean getShowColumnNames() { return showColumnNames; }

  public boolean isConfigured() {
    if (filename == null) {
      return false;
    }

    try {
      out = new PrintWriter(new BufferedWriter(new FileWriter(filename, !overwrite)));
    } catch (IOException io) {
      io.printStackTrace(System.out);
      return false;
    }
    return true;
  }


  public boolean save(DataRecord record) {
    ++recordCount;
    try {
      if (recordCount == 1 && showColumnNames) {
        Iterator fieldItems = record.iterator();
        while (fieldItems.hasNext()) {
          DataField thisField = (DataField) fieldItems.next();
          out.print(thisField.getName());
          if (fieldItems.hasNext() && fieldSeparator != null) {
            out.print(fieldSeparator);
          }
        }
        out.println("");
      }
      if (1 == 1) {
        Iterator fieldItems = record.iterator();
        while (fieldItems.hasNext()) {
          DataField thisField = (DataField) fieldItems.next();
          out.print(thisField.getValue());
          if (fieldItems.hasNext() && fieldSeparator != null) {
            out.print(fieldSeparator);
          }
        }
        out.println("");
      }
    } catch (Exception ex) {
      logger.info(ex.toString());
      ex.printStackTrace(System.out);
      return false;
    }
    return true;
  }


  public boolean commit() { 
    return false; 
  }


  public boolean rollback() {
    return false;
  }

  public boolean load(DataRecord record) {
    return false;
  }
  
  public boolean close() {
    if (out != null) {
      out.close();
    }
    return true;
  }
}

