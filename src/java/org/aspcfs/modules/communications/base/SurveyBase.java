//Copyright 2001-2002 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import org.theseus.beans.*;
import java.util.*;
import java.text.*;

public class SurveyBase extends GenericBean {

  protected String name = "";
  protected String description = "";
  protected String intro = "";
  protected int itemLength = -1;
  protected int type = -1;
  protected String typeName = "";
  
  public SurveyBase() { }

  public String getTypeName() {
    return typeName;
  }


  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }


  public String getName() {
    return name;
  }


  public String getDescription() {
    return description;
  }


  public String getIntro() {
    return intro;
  }


  public int getItemLength() {
    return itemLength;
  }


  public int getType() {
    return type;
  }


  public void setName(String tmp) {
    this.name = tmp;
  }


  public void setDescription(String tmp) {
    this.description = tmp;
  }


  public void setIntro(String tmp) {
    this.intro = tmp;
  }


  public void setItemLength(int tmp) {
    this.itemLength = tmp;
  }


  public void setItemLength(String tmp) {
    this.itemLength = Integer.parseInt(tmp);
  }


  public void setType(int tmp) {
    this.type = tmp;
  }


  public void setType(String tmp) {
    this.type = Integer.parseInt(tmp);
  }
  
}

