package com.darkhorseventures.controller;

import com.darkhorseventures.cfsbase.CustomFieldCategory;
import com.darkhorseventures.cfsbase.CustomField;
import com.darkhorseventures.controller.*;
import java.util.Iterator;
import com.darkhorseventures.utils.ObjectUtils;
import com.darkhorseventures.utils.StringUtils;
import com.darkhorseventures.cfsbase.SurveyItem;
import java.util.ArrayList;

/**
 *  Description of the Class
 *
 *@author     chris price
 *@created    August 8, 2002
 *@version    $Id$
 */
public class CustomForm extends CustomFieldCategory {
  public String action = "";
  public String cancel = "";
  public String defaultField = "";
  public boolean reset = false;
  public String selectedTabName = "";
  public int selectedTabId = 0;
  public String returnLink = "";
  public String returnLinkText = "";
  public StringBuffer jsCheckInfo = new StringBuffer();
  public ArrayList buttonNames = new ArrayList();
  public ArrayList buttonLinks = new ArrayList();


  /**
   *  Constructor for the CustomForm object
   */
  public CustomForm() { }


  /**
   *  Sets the action attribute of the CustomForm object
   *
   *@param  action  The new action value
   */
  public void setAction(String action) {
    this.action = action;
  }


  /**
   *  Sets the defaultField attribute of the CustomForm object
   *
   *@param  defaultField  The new defaultField value
   */
  public void setDefaultField(String defaultField) {
    this.defaultField = defaultField;
  }


  /**
   *  Sets the selectedTabId attribute of the CustomForm object
   *
   *@param  selectedTabId  The new selectedTabId value
   */
  public void setSelectedTabId(int selectedTabId) {
    this.selectedTabId = selectedTabId;
  }


  /**
   *  Sets the selectedTabId attribute of the CustomForm object
   *
   *@param  selectedTabId  The new selectedTabId value
   */
  public void setSelectedTabId(String selectedTabId) {
    this.selectedTabId = Integer.parseInt(selectedTabId);
  }


  /**
   *  Sets the cancel attribute of the CustomForm object
   *
   *@param  cancel  The new cancel value
   */
  public void setCancel(String cancel) {
    this.cancel = cancel;
  }


  /**
   *  Sets the reset attribute of the CustomForm object
   *
   *@param  reset  The new reset value
   */
  public void setReset(boolean reset) {
    this.reset = reset;
  }


  /**
   *  Sets the selectedTabName attribute of the CustomForm object
   *
   *@param  selectedTabName  The new selectedTabName value
   */
  public void setSelectedTabName(String selectedTabName) {
    this.selectedTabName = selectedTabName;
  }


  /**
   *  Sets the returnLink attribute of the CustomForm object
   *
   *@param  tmp  The new returnLink value
   */
  public void setReturnLink(String tmp) {
    this.returnLink = tmp;
  }


  /**
   *  Sets the returnLinkText attribute of the CustomForm object
   *
   *@param  tmp  The new returnLinkText value
   */
  public void setReturnLinkText(String tmp) {
    this.returnLinkText = tmp;
  }


  /**
   *  Sets the buttonNames attribute of the CustomForm object
   *
   *@param  tmp  The new buttonNames value
   */
  public void setButtonNames(ArrayList tmp) {
    this.buttonNames = tmp;
  }


  /**
   *  Sets the buttonLinks attribute of the CustomForm object
   *
   *@param  tmp  The new buttonLinks value
   */
  public void setButtonLinks(ArrayList tmp) {
    this.buttonLinks = tmp;
  }


  /**
   *  Gets the action attribute of the CustomForm object
   *
   *@return    The action value
   */
  public String getAction() {
    return action;
  }


  /**
   *  Gets the cancel attribute of the CustomForm object
   *
   *@return    The cancel value
   */
  public String getCancel() {
    return cancel;
  }


  /**
   *  Gets the defaultField attribute of the CustomForm object
   *
   *@return    The defaultField value
   */
  public String getDefaultField() {
    return defaultField;
  }


  /**
   *  Gets the selectedTabId attribute of the CustomForm object
   *
   *@return    The selectedTabId value
   */
  public int getSelectedTabId() {
    return selectedTabId;
  }


  /**
   *  Gets the reset attribute of the CustomForm object
   *
   *@return    The reset value
   */
  public boolean getReset() {
    return reset;
  }


  /**
   *  Gets the selectedTabName attribute of the CustomForm object
   *
   *@return    The selectedTabName value
   */
  public String getSelectedTabName() {
    return selectedTabName;
  }


  /**
   *  Gets the returnLink attribute of the CustomForm object
   *
   *@return    The returnLink value
   */
  public String getReturnLink() {
    return returnLink;
  }


  /**
   *  Gets the returnLinkText attribute of the CustomForm object
   *
   *@return    The returnLinkText value
   */
  public String getReturnLinkText() {
    return returnLinkText;
  }


  /**
   *  Gets the jsCheckInfo attribute of the CustomForm object
   *
   *@return    The jsCheckInfo value
   */
  public String getJsCheckInfo() {
    return jsCheckInfo.toString();
  }


  /**
   *  Gets the buttonNames attribute of the CustomForm object
   *
   *@return    The buttonNames value
   */
  public ArrayList getButtonNames() {
    return buttonNames;
  }


  /**
   *  Gets the buttonLinks attribute of the CustomForm object
   *
   *@return    The buttonLinks value
   */
  public ArrayList getButtonLinks() {
    return buttonLinks;
  }


  /**
   *  Gets the hiddenValues attribute of the CustomForm object
   *
   *@return    The hiddenValues value
   */
  public String getHiddenValues() {
    StringBuffer hiddenResult = new StringBuffer();

    Iterator tabs = this.iterator();
    while (tabs.hasNext()) {
      CustomFormTab thisTab = (CustomFormTab) tabs.next();

      if (!(this.getSelectedTabName().equals(thisTab.getName()))) {
        Iterator groups = thisTab.iterator();
        while (groups.hasNext()) {
          CustomFormGroup thisGroup = (CustomFormGroup) groups.next();
          Iterator fields = thisGroup.iterator();
          if (fields.hasNext()) {
            while (fields.hasNext()) {
              CustomField thisField = (CustomField) fields.next();

              if (thisField.getType() == CustomField.ROWLIST) {
                for (int count = 1; count <= ((ArrayList) thisField.getElementData()).size(); count++) {
                  SurveyItem thisItem = (SurveyItem) ((ArrayList) thisField.getElementData()).get(count - 1);
                  hiddenResult.append("<input type=\"hidden\" name=\"" + thisField.getName() + count + "id\" value=\"" + thisItem.getId() + "\">\n");
                  hiddenResult.append("<input type=\"hidden\" name=\"" + thisField.getName() + count + "text\" value=\"" + StringUtils.toHtmlValue(thisItem.getDescription()) + "\">\n");
                }
              } else {
                if (thisField.getType() != CustomField.TEXTAREA) {
                  hiddenResult.append("<input type=\"hidden\" name=\"" + thisField.getName() + "\" value=\"" + StringUtils.toHtmlValue(thisField.getEnteredValue()) + "\">\n");
                } else {
                  hiddenResult.append("<input type=\"hidden\" name=\"" + thisField.getName() + "\" value=\"" + thisField.getEnteredValue() + "\">\n");
                }
              }
            }
          }
        }
      }
    }

    return hiddenResult.toString();
  }


  /**
   *  Description of the Method
   *
   *@param  thisField  Description of Parameter
   */
  public void buildJsCheckInfo(CustomField thisField) {
    if (thisField.getRequired() == true) {

      System.out.println("checking " + thisField.getName());

      if (jsCheckInfo.length() == 0) {
        jsCheckInfo.append("function checkForm(form) {\n");
        jsCheckInfo.append("    formTest = true;\n");
        jsCheckInfo.append("    message = \"\";\n");
      }

      if (thisField.getType() == CustomField.TEXT) {
        jsCheckInfo.append("    if (form." + thisField.getName() + ".value == \"\") {\n");
        jsCheckInfo.append("        message += \"- " + thisField.getDisplay() + "\\r\\n\";\n");
        jsCheckInfo.append("        formTest = false;\n");
        jsCheckInfo.append("    }\n");
      } else if (thisField.getType() == CustomField.SELECT) {
        jsCheckInfo.append("    if (form." + thisField.getName() + ".selectedIndex ==0) {\n");
        jsCheckInfo.append("        message += \"- " + thisField.getDisplay() + "\\r\\n\";\n");
        jsCheckInfo.append("        formTest = false;\n");
        jsCheckInfo.append("    }\n");
      } else if (thisField.getType() == CustomField.ROWLIST) {
        for (int rowListCount = 1; rowListCount <= thisField.getMaxRowItems(); rowListCount++) {
          jsCheckInfo.append("    if (form." + thisField.getName() + rowListCount + "text.value == \"\") {\n");
          jsCheckInfo.append("        message += \"- " + thisField.getDisplay() + rowListCount + "\\r\\n\";\n");
          jsCheckInfo.append("        formTest = false;\n");
          jsCheckInfo.append("    }\n");
        }

      }
    }
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Returned Value
   */
  public String displayButtons() {
    Iterator k = this.getButtonNames().iterator();
    Iterator m = this.getButtonLinks().iterator();
    StringBuffer result = new StringBuffer();
    String thisName = null;
    String thisLink = null;

    if (k.hasNext()) {
      while (k.hasNext()) {
        thisName = (String) k.next();
        thisLink = (String) m.next();

        if (thisName != null && !(thisName.equals("")) && !(thisLink.equals("")) && thisLink != null) {
          result.append("<input type=\"submit\" value=\"" + thisName + "\" onClick=\"javascript:this.form.action='" + thisLink + "'\">\n");
        }
      }
    }

    return result.toString();
  }


  /**
   *  Description of the Method
   *
   *@param  tmp  Description of Parameter
   *@return      Description of the Returned Value
   */
  public int populate(Object tmp) {
    int updatedFields = 0;
    jsCheckInfo = new StringBuffer();

    Iterator tabs = this.iterator();
    while (tabs.hasNext()) {
      CustomFormTab thisTab = (CustomFormTab) tabs.next();

      Iterator groups = thisTab.iterator();
      while (groups.hasNext()) {
        CustomFormGroup thisGroup = (CustomFormGroup) groups.next();
        Iterator fields = thisGroup.iterator();
        while (fields.hasNext()) {
          CustomField thisField = (CustomField) fields.next();
          if (thisField.getType() == CustomField.ROWLIST || thisField.getType() == CustomField.ROWLIST_QUESTION) {
            thisField.setElementData(ObjectUtils.getObject(tmp, thisField.getName()));
            thisField.setMaxRowItems(ObjectUtils.getParam(tmp, thisField.getLengthVar()));
          }

          thisField.setEnteredValue(ObjectUtils.getParam(tmp, thisField.getName()));

          //for select boxes??
          if (thisField.getType() == CustomField.SELECT) {
            thisField.setSelectedItemId(ObjectUtils.getParam(tmp, thisField.getName()));
          }

          if (this.getSelectedTabId() == thisTab.getId()) {
            System.out.println("==> " + thisField.getName());
            buildJsCheckInfo(thisField);
          }

          updatedFields++;
        }
      }
    }

    if (jsCheckInfo.length() != 0) {
      jsCheckInfo.append("    if (formTest == false) {\n");
      jsCheckInfo.append("        alert(\"Form could not be saved, please check the following:\\r\\n\\r\\n\" + message);\n");
      jsCheckInfo.append("        return false;\n");
      jsCheckInfo.append("    } else {\n");
      jsCheckInfo.append("        return true;\n");
      jsCheckInfo.append("    }\n");
      jsCheckInfo.append("}\n");
    }

    return updatedFields;
  }
}

