package com.darkhorseventures.apps.dataimport;

import java.util.HashMap;

public interface DataWriter extends DataImportHandler {
  boolean save(HashMap data);
}
