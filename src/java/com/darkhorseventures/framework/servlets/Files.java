package com.darkhorseventures.framework.servlets;

public class Files {
  private String file = null;
  private long lastModified = -1;

  public Files() {
  }


  public Files(String fileName) {
    this(fileName, -1);
  }


  public Files(String file, long lastModified) {
    this.file = file;
    this.lastModified = lastModified;
  }


  public long getLastModified() {
    return lastModified;
  }


  public String getFile() {
    return file;
  }


  public void setLastModified(long value) {
    lastModified = value;
  }


  public void setFile(String value) {
    file = value;
  }
}