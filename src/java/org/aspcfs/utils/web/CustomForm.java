package com.darkhorseventures.controller;

import com.darkhorseventures.cfsbase.CustomFieldCategory;
import com.darkhorseventures.cfsbase.CustomField;
import com.darkhorseventures.controller.*;
import java.util.Iterator;
import com.darkhorseventures.utils.ObjectUtils;
import com.darkhorseventures.cfsbase.SurveyItem;
import java.util.ArrayList;

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
	
	public CustomForm() { }
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getCancel() {
		return cancel;
	}
	public void setDefaultField(String defaultField) {
		this.defaultField = defaultField;
	}
	public String getDefaultField() {
		return defaultField;
	}
	public int getSelectedTabId() {
		return selectedTabId;
	}
	public void setSelectedTabId(int selectedTabId) {
		this.selectedTabId = selectedTabId;
	}
	public void setSelectedTabId(String selectedTabId) {
		this.selectedTabId = Integer.parseInt(selectedTabId);
	}
	public void setCancel(String cancel) {
		this.cancel = cancel;
	}
	public boolean getReset() {
		return reset;
	}
	public void setReset(boolean reset) {
		this.reset = reset;
	}
	public String getSelectedTabName() {
		return selectedTabName;
	}
	public void setSelectedTabName(String selectedTabName) {
		this.selectedTabName = selectedTabName;
	}
	
	public String getReturnLink() { return returnLink; }
	public String getReturnLinkText() { return returnLinkText; }
	public void setReturnLink(String tmp) { this.returnLink = tmp; }
	public void setReturnLinkText(String tmp) { this.returnLinkText = tmp; }
	
	public String getJsCheckInfo() {
		return jsCheckInfo.toString();
	}
	
	public void buildJsCheckInfo(CustomField thisField) {
		if ( thisField.getRequired() == true ) {
			
			System.out.println("checking " + thisField.getName());
			
			if (jsCheckInfo.length() == 0) {
				jsCheckInfo.append("function checkForm(form) {\n");
				jsCheckInfo.append("    formTest = true;\n");
				jsCheckInfo.append("    message = \"\";\n");
			}
			
			if ( thisField.getType() == CustomField.TEXT ) {
				jsCheckInfo.append("    if (form." + thisField.getName() + ".value == \"\") {\n");
				jsCheckInfo.append("        message += \"- " + thisField.getDisplay() + "\\r\\n\";\n");
				jsCheckInfo.append("        formTest = false;\n");
				jsCheckInfo.append("    }\n");
			} else if ( thisField.getType() == CustomField.SELECT ) {
				jsCheckInfo.append("    if (form." + thisField.getName() + ".selectedIndex ==0) {\n");
				jsCheckInfo.append("        message += \"- " + thisField.getDisplay() + "\\r\\n\";\n");
				jsCheckInfo.append("        formTest = false;\n");
				jsCheckInfo.append("    }\n");	
			} else if ( thisField.getType() == CustomField.ROWLIST ) {
				for( int rowListCount = 1; rowListCount <= thisField.getMaxRowItems(); rowListCount++ ) {
					jsCheckInfo.append("    if (form." + thisField.getName() + rowListCount + "text.value == \"\") {\n");
					jsCheckInfo.append("        message += \"- " + thisField.getDisplay() + rowListCount + "\\r\\n\";\n");
					jsCheckInfo.append("        formTest = false;\n");
					jsCheckInfo.append("    }\n");
				}
				
			}
		}
	}
		
public ArrayList getButtonNames() { return buttonNames; }
public ArrayList getButtonLinks() { return buttonLinks; }
public void setButtonNames(ArrayList tmp) { this.buttonNames = tmp; }
public void setButtonLinks(ArrayList tmp) { this.buttonLinks = tmp; }

	public String getHiddenValues() {
		StringBuffer hiddenResult = new StringBuffer();
		
		Iterator tabs = this.iterator();
		while (tabs.hasNext()) {
			CustomFormTab thisTab = (CustomFormTab)tabs.next();
			
			if ( !(this.getSelectedTabName().equals(thisTab.getName())) ) {
				Iterator groups = thisTab.iterator();
				while (groups.hasNext()) {
					CustomFormGroup thisGroup = (CustomFormGroup)groups.next();
					Iterator fields = thisGroup.iterator();
					if (fields.hasNext()) {
						while (fields.hasNext()) {
							CustomField thisField = (CustomField)fields.next();
							
							if (thisField.getType() == CustomField.ROWLIST) {
							  for (int count=1; count<=((ArrayList)thisField.getElementData()).size(); count++) {
									SurveyItem thisItem = (SurveyItem)((ArrayList)thisField.getElementData()).get(count-1);
									hiddenResult.append("<input type=\"hidden\" name=\"" + thisField.getName() + count + "id\" value=\"" + thisItem.getId() + "\">\n");
									hiddenResult.append("<input type=\"hidden\" name=\"" + thisField.getName() + count + "text\" value=\"" + thisItem.getDescription() + "\">\n");
							  }
							} else {
								hiddenResult.append("<input type=\"hidden\" name=\"" + thisField.getName() + "\" value=\"" + thisField.getEnteredValue() + "\">\n");
							}
						}
					}
				}
			}
		}
		
		return hiddenResult.toString();
		
	}
	
	public String displayButtons() {
		Iterator k = this.getButtonNames().iterator();
		Iterator m = this.getButtonLinks().iterator();
		StringBuffer result = new StringBuffer();
		String thisName = null;
		String thisLink = null;
		
		if (k.hasNext()) {
			while (k.hasNext()) {
				thisName = (String)k.next();
				thisLink = (String)m.next();
				
				if (thisName != null && !(thisName.equals("")) && !(thisLink.equals("")) && thisLink != null) {
					result.append("<input type=\"submit\" value=\"" + thisName + "\" onClick=\"javascript:this.form.action='" + thisLink + "'\">\n");
				}
			}
		}
		
		return result.toString();
	}
	
	public int populate(Object tmp) {
		int updatedFields = 0;
		jsCheckInfo = new StringBuffer();
		
		Iterator tabs = this.iterator();
		while (tabs.hasNext()) {
			CustomFormTab thisTab = (CustomFormTab)tabs.next();
		
			Iterator groups = thisTab.iterator();
			while (groups.hasNext()) {
				CustomFormGroup thisGroup = (CustomFormGroup)groups.next();
				Iterator fields = thisGroup.iterator();
					while (fields.hasNext()) {
						CustomField thisField = (CustomField)fields.next();
						if (thisField.getType() == CustomField.ROWLIST || thisField.getType() == CustomField.ROWLIST_QUESTION) {
							thisField.setElementData(ObjectUtils.getObject(tmp, thisField.getName()));
							thisField.setMaxRowItems(ObjectUtils.getParam(tmp, thisField.getLengthVar()));
						}  
						
						thisField.setEnteredValue(ObjectUtils.getParam(tmp, thisField.getName()));
						
						//for select boxes??
						if (thisField.getType() == CustomField.SELECT) {
							thisField.setSelectedItemId(ObjectUtils.getParam(tmp, thisField.getName()));
						}
						
						if ( this.getSelectedTabId() == thisTab.getId() ) {
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

