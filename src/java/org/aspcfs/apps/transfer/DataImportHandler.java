package com.darkhorseventures.apps.dataimport;

import java.util.logging.*;

public interface DataImportHandler {
  public static Logger logger = Logger.getLogger(DataImport.class.getName());
  public boolean isConfigured();
  public double getVersion();
  public String getName();
  public String getDescription();
}
