package com.darkhorseventures.apps.dataimport;

public interface DataReader extends DataImportHandler {
  boolean execute(DataWriter writer);
}
