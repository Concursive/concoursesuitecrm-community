package com.darkhorseventures.utils;

import java.sql.*;

public class AuthenticationItem {

  private String id = null;
  private String code = null;

  public AuthenticationItem() { }
  
  public void setId(String tmp) { id = tmp; }
  public void setCode(String tmp) { code = tmp; }

  public String getId() { return id; }
  public String getCode() { return code; }
  
  
}

