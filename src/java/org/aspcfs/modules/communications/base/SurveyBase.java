//Copyright 2001-2002 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import org.theseus.beans.*;
import com.darkhorseventures.utils.DatabaseUtils;
import java.util.*;
import java.text.*;

/**
 *  Description of the Class
 *
 *@author
 *@created    October 14, 2002
 *@version    $Id$
 */
public class SurveyBase extends GenericBean {

  protected String name = "";
  protected String description = "";
  protected String intro = "";
  protected int itemLength = -1;
  protected int type = -1;
  //Thank You Text
  protected String outro = null;
  protected java.sql.Date expirationDate = null;


  /**
   *  Constructor for the SurveyBase object
   */
  public SurveyBase() { }


  /**
   *  Sets the outro attribute of the SurveyBase object
   *
   *@param  outro  The new outro value
   */
  public void setOutro(String outro) {
    this.outro = outro;
  }


  /**
   *  Gets the outro attribute of the SurveyBase object
   *
   *@return    The outro value
   */
  public String getOutro() {
    return outro;
  }


  /**
   *  Gets the name attribute of the SurveyBase object
   *
   *@return    The name value
   */
  public String getName() {
    return name;
  }


  /**
   *  Gets the description attribute of the SurveyBase object
   *
   *@return    The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Gets the intro attribute of the SurveyBase object
   *
   *@return    The intro value
   */
  public String getIntro() {
    return intro;
  }


  /**
   *  Gets the itemLength attribute of the SurveyBase object
   *
   *@return    The itemLength value
   */
  public int getItemLength() {
    return itemLength;
  }


  /**
   *  Gets the type attribute of the SurveyBase object
   *
   *@return    The type value
   */
  public int getType() {
    return type;
  }


  /**
   *  Gets the expirationDate attribute of the SurveyBase object
   *
   *@return    The expirationDate value
   */
  public java.sql.Date getExpirationDate() {
    return expirationDate;
  }


  /**
   *  Sets the expirationDate attribute of the Survey object
   *
   *@param  expirationDate  The new expirationDate value
   */
  public void setExpirationDate(java.sql.Date expirationDate) {
    this.expirationDate = expirationDate;
  }


  /**
   *  Sets the expirationDate attribute of the Survey object
   *
   *@param  tmp  The new expirationDate value
   */
  public void setExpirationDate(String tmp) {
    this.expirationDate = DatabaseUtils.parseDate(tmp);
  }


  /**
   *  Sets the name attribute of the SurveyBase object
   *
   *@param  tmp  The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Sets the description attribute of the SurveyBase object
   *
   *@param  tmp  The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Sets the intro attribute of the SurveyBase object
   *
   *@param  tmp  The new intro value
   */
  public void setIntro(String tmp) {
    this.intro = tmp;
  }


  /**
   *  Sets the itemLength attribute of the SurveyBase object
   *
   *@param  tmp  The new itemLength value
   */
  public void setItemLength(int tmp) {
    this.itemLength = tmp;
  }


  /**
   *  Sets the itemLength attribute of the SurveyBase object
   *
   *@param  tmp  The new itemLength value
   */
  public void setItemLength(String tmp) {
    this.itemLength = Integer.parseInt(tmp);
  }


  /**
   *  Sets the type attribute of the SurveyBase object
   *
   *@param  tmp  The new type value
   */
  public void setType(int tmp) {
    this.type = tmp;
  }


  /**
   *  Sets the type attribute of the SurveyBase object
   *
   *@param  tmp  The new type value
   */
  public void setType(String tmp) {
    this.type = Integer.parseInt(tmp);
  }

}

