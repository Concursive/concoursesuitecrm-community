package org.aspcfs.taglib;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import org.aspcfs.utils.*;
import org.aspcfs.controller.*;
import java.util.*;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.LookupElement;

/**
 *  Converts a list specified to a html dropdown based on the LookupList
 *  included.
 *
 *@author     Mathur
 *@created    March 17, 2003
 *@version    $Id$
 */
public class LookupHtmlHandler extends TagSupport {
  private String listType = null;
  private String listName = null;
  private String lookupName = null;


  /**
   *  Sets the listName attribute of the LookupHtmlHandler object
   *
   *@param  listName  The new listName value
   */
  public void setListName(String listName) {
    this.listName = listName;
  }


  /**
   *  Sets the lookupList attribute of the LookupHtmlHandler object
   *
   *@param  lookupName  The new lookupName value
   */
  public void setLookupName(String lookupName) {
    this.lookupName = lookupName;
  }


  /**
   *  Sets the listType attribute of the LookupHtmlHandler object
   *
   *@param  listType  The new listType value
   */
  public void setListType(String listType) {
    this.listType = listType;
  }


  /**
   *  Gets the listType attribute of the LookupHtmlHandler object
   *
   *@return    The listType value
   */
  public String getListType() {
    return listType;
  }


  /**
   *  Gets the listName attribute of the LookupHtmlHandler object
   *
   *@return    The listName value
   */
  public String getListName() {
    return listName;
  }


  /**
   *  Gets the lookupList attribute of the LookupHtmlHandler object
   *
   *@return    The lookupList value
   */
  public String getLookupName() {
    return lookupName;
  }


  /**
   *  Description of the Method
   *
   *@return                   Description of the Return Value
   *@exception  JspException  Description of the Exception
   */
  public int doStartTag() throws JspException {
    try {
      //TODO: if a lookupType is specified like hashmap, logic needs to be included to handle that case.
      ArrayList selectedList = (ArrayList) pageContext.getRequest().getAttribute(listName);
      ArrayList lookupList = (ArrayList) pageContext.getRequest().getAttribute(lookupName);
      if (selectedList != null && lookupList != null) {
        Iterator i = selectedList.iterator();
        if (i.hasNext()) {
          while (i.hasNext()) {
            int val = Integer.parseInt((String) i.next());
            Iterator j = lookupList.iterator();
            while (j.hasNext()) {
              LookupElement thisElt = (LookupElement) j.next();
              if (thisElt.getCode() == val) {
                this.pageContext.getOut().write("<option value=\"" + thisElt.getCode() + "\">" + thisElt.getDescription() + "</option>");
              }
            }
          }
        } else {
          this.pageContext.getOut().write("<option value=\"-1\">None Selected</option>");
        }
      } else {
        this.pageContext.getOut().write("<option value=\"-1\">None Selected</option>");
      }
    } catch (Exception e) {
      throw new JspException("LookupHtmlHandler - > Error: " + e.getMessage());
    }
    return SKIP_BODY;
  }
}

