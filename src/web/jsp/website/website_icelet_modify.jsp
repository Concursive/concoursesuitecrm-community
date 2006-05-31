<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id: $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.*,org.aspcfs.modules.website.base.*,org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="site" class="org.aspcfs.modules.website.base.Site" scope="request"/>
<jsp:useBean id="rowColumn" class="org.aspcfs.modules.website.base.RowColumn" scope="request"/>
<jsp:useBean id="pageVersion" class="org.aspcfs.modules.website.base.PageVersion" scope="request"/>
<jsp:useBean id="propertyMap" class="java.util.HashMap" scope="request"/>
<jsp:useBean id="portfolioCategories" class="org.aspcfs.modules.website.base.PortfolioCategoryList" scope="request"/>
<jsp:useBean id="icelets" class="org.aspcfs.modules.website.base.IceletList" scope="request"/>
<jsp:useBean id="icelet" class="org.aspcfs.modules.website.base.Icelet" scope="request"/>
<jsp:useBean id="clientType" class="org.aspcfs.utils.web.ClientType" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<%
  if (clientType.getType() == -1) {
    clientType.setParameters(request);
  }
%>
<dhv:evaluate if="<%= !clientType.showApplet() %>">
  <jsp:include page="../tinymce_include.jsp" flush="true"/>
</dhv:evaluate>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popProductCategories.js"></script>
<script language="JavaScript">
	function checkForm(form) {
    try { tinyMCE.triggerSave(false); } catch(e) { }
		var flag = true;
		message = "";
		if (flag == false) {
        alert(label("check.form","Form could not be saved, please check the following:\r\n\r\n") + message);
	      return false;
  } else {
			return true;
		}
	}

  function changeDivContent(divName, divContents) {
  if(document.layers){
    // Netscape 4 or equiv.
    divToChange = document.layers[divName];
    divToChange.document.open();
    divToChange.document.write(divContents);
    divToChange.document.close();
  } else if(document.all){
    // MS IE or equiv.
    divToChange = document.all[divName];
    divToChange.innerHTML = divContents;
  } else if(document.getElementById){
    // Netscape 6 or equiv.
    divToChange = document.getElementById(divName);
    divToChange.innerHTML = divContents;
  }
}
</script>
<form name="icelet" action="RowColumns.do?command=SaveIcelet&rowColumnId=<%= rowColumn.getId() %>&popup=true&auto-populate=true" onSubmit="return checkForm(this);" method="post">
<input type="submit" value="<dhv:label name="button.update">Update</dhv:label>" name="Save"/>
<input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:self.close();">
<br /><dhv:formMessage showSpace="true"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2"><strong><dhv:label name="">Modify Icelet</dhv:label></strong></th>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="">Column Width</dhv:label>
    </td>
		<td><input type="text" name="width" value="<%= (rowColumn.getWidth() > -1? rowColumn.getWidth(): 100) %>"/></td>
  </tr>
	<tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="">Icelet</dhv:label>
    </td>
		<td>
      <table border="0" cellpadding="0" cellspacing="0" class="empty"><tr><td valign="top">
      <%  StringBuffer str = new StringBuffer();
          str.append("onChange=\"javascript:document.forms['icelet'].action=");
          str.append("'RowColumns.do?command=ModifyIcelet&popup=true&auto-populate=true&rowColumnId="+ rowColumn.getId());
          str.append("&iceletId='+document.forms['icelet'].iceletId.options[document.forms['icelet'].iceletId.options.selectedIndex].value;");
          str.append("document.forms['icelet'].submit();\"");//alert('the action is '+document.forms['icelet'].action);
          icelets.setJsEvent(str.toString()); %>
        <%= icelets.getHtmlSelect("iceletId", rowColumn.getIceletId()) %> <font color="red">*</font>
      </td><td valign="top" nowrap>&nbsp;
        <%= showAttribute(request, "iceletIdError") %>
      </td></tr></table>
		</td>
	</tr>
</table>
&nbsp;<br />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2"><strong><dhv:label name="">Icelet Properties</dhv:label></strong></th>
  </tr>
<%  if (propertyMap != null && propertyMap.size() > 0) {
    Iterator iter = (Iterator) propertyMap.keySet().iterator();
    while (iter.hasNext()) {
      Integer key = (Integer) iter.next();
      IceletProperty property = (IceletProperty) propertyMap.get(key);
%>
	<tr class="containerBody">
    <td class="formLabel">
      <%= toHtml(property.getLabel()) %>
    </td>
    <td>
    <zeroio:debug value="<%= property.getDefaultValue() %>"/>
      <dhv:evaluate if="<%= property.getType().equals(IceletProperty.HTML_EDITOR) %>">
        <%@ include file="website_html_editor_include.jsp" %>
      </dhv:evaluate>
      <dhv:evaluate if="<%= property.getType().equals(IceletProperty.TEXT_AREA) %>">
        <textarea rows="3" cols="50" name="<%= "property_"+rowColumn.getId()+"_"+property.getTypeConstant() %>"><%= toString(rowColumn.getIceletPropertyMap() != null && rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant())) != null && ((IceletProperty) rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant()))).getValue() != null? ((IceletProperty) rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant()))).getValue() : property.getDefaultValue()) %></textarea>
      </dhv:evaluate>
      <dhv:evaluate if="<%= property.getType().equals(IceletProperty.PORTFOLIO_CATEGORY) %>">
        <table cellspacing="0" cellpadding="0" border="0" class="empty">
          <tr>
            <td>
            <%if (1==1) { %>
              <div id="<%= "changeportfolio_"+rowColumn.getId()+"_"+property.getTypeConstant() %>">
            <%  IceletProperty iceletProperty = (rowColumn.getIceletPropertyMap() != null && rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant())) != null ? (IceletProperty) rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant())) : null); %>  
                <dhv:evaluate if="<%= iceletProperty != null && iceletProperty.getValueString() != null && !"".equals(iceletProperty.getValueString()) %>">
                  <%= toHtml(iceletProperty.getValueString()) %>
                </dhv:evaluate>
                <dhv:evaluate if="<%= iceletProperty == null || iceletProperty.getValueString() == null || "".equals(iceletProperty.getValueString()) %>">
                  <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
                </dhv:evaluate>
              </div>
            </td>
            <td>
<input type="hidden" name="<%= "property_"+rowColumn.getId()+"_"+property.getTypeConstant() %>" id="<%= "property_"+rowColumn.getId()+"_"+property.getTypeConstant() %>" value="<%= (iceletProperty != null && iceletProperty.getValueString() != null && !"".equals(iceletProperty.getValueString())?iceletProperty.getValue():-1) %>"/> &nbsp;
[<a href="javascript:popPortfolioCategoryListSingle('<%= "property_"+rowColumn.getId()+"_"+property.getTypeConstant() %>','<%= "changeportfolio_"+rowColumn.getId()+"_"+property.getTypeConstant() %>', '');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>] &nbsp;
[<a href="javascript:document.getElementById('<%= "property_"+rowColumn.getId()+"_"+property.getTypeConstant() %>').value='-1';javascript:changeDivContent('<%= "changeportfolio_"+rowColumn.getId()+"_"+property.getTypeConstant() %>', label('none.selected','None Selected'));"><dhv:label name="button.clear">Clear</dhv:label></a>]
            <%}%>
              &nbsp;
            </td>
          </tr>
        </table>
      </dhv:evaluate>
      <dhv:evaluate if="<%= property.getType().equals(IceletProperty.PRODUCT_CATEGORY) %>">
        <table cellspacing="0" cellpadding="0" border="0" class="empty">
          <tr>
            <td>
            <%if (1==1) { %>
              <div id="<%= "changeproduct_"+rowColumn.getId()+"_"+property.getTypeConstant() %>">
            <%  IceletProperty iceletProperty = (rowColumn.getIceletPropertyMap() != null && rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant())) != null ? (IceletProperty) rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant())) : null); %>  
                <dhv:evaluate if="<%= iceletProperty != null && iceletProperty.getValueString() != null && !"".equals(iceletProperty.getValueString()) %>">
                  <%= toHtml(iceletProperty.getValueString()) %>
                </dhv:evaluate>
                <dhv:evaluate if="<%= iceletProperty == null || iceletProperty.getValueString() == null || "".equals(iceletProperty.getValueString()) %>">
                  <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
                </dhv:evaluate>
              </div>
            </td>
            <td>
<input type="hidden" name="<%= "property_"+rowColumn.getId()+"_"+property.getTypeConstant() %>" id="<%= "property_"+rowColumn.getId()+"_"+property.getTypeConstant() %>" value="<%= (iceletProperty != null && iceletProperty.getValueString() != null && !"".equals(iceletProperty.getValueString())?iceletProperty.getValue():-1) %>"/> &nbsp;
[<a href="javascript:popProductCategoriesListSingle('<%= "property_"+rowColumn.getId()+"_"+property.getTypeConstant() %>','<%= "changeproduct_"+rowColumn.getId()+"_"+property.getTypeConstant() %>', 'setParentList=true&listType=single');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>] &nbsp;
[<a href="javascript:document.getElementById('<%= "property_"+rowColumn.getId()+"_"+property.getTypeConstant() %>').value='-1';javascript:changeDivContent('<%= "changeproduct_"+rowColumn.getId()+"_"+property.getTypeConstant() %>', label('none.selected','None Selected'));"><dhv:label name="button.clear">Clear</dhv:label></a>]
            <%}%>
              &nbsp;
            </td>
          </tr>
        </table>
      </dhv:evaluate>
      <dhv:evaluate if="<%= property.getType().equals(IceletProperty.TEXT) %>">
        <input type="text" size="60" name="<%= "property_"+rowColumn.getId()+"_"+property.getTypeConstant() %>" value="<%= toHtmlValue(rowColumn.getIceletPropertyMap() != null && rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant())) != null && ((IceletProperty) rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant()))).getValue() != null? ((IceletProperty) rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant()))).getValue() : property.getDefaultValue()) %>"/>
      </dhv:evaluate>
      <dhv:evaluate if="<%= property.getType().equals(IceletProperty.INTEGER) %>">
        <input type="text" name="<%= "property_"+rowColumn.getId()+"_"+property.getTypeConstant() %>" value="<%= toHtmlValue(rowColumn.getIceletPropertyMap() != null && rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant())) != null && ((IceletProperty) rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant()))).getValue() != null? ((IceletProperty) rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant()))).getValue() : property.getDefaultValue()) %>"/>
      </dhv:evaluate>
      <dhv:evaluate if="<%= property.getType().equals(property.CHECKBOX) %>">
        <dhv:checkbox name="<%= "property_"+rowColumn.getId()+"_"+property.getTypeConstant() %>" value="true" checked="<%= "true".equals(rowColumn.getIceletPropertyMap() != null && rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant())) != null && ((IceletProperty) rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant()))).getValue() != null? ((IceletProperty) rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant()))).getValue() : property.getDefaultValue()) %>" />
      </dhv:evaluate>
    </td>
  </tr>
<% } } else {%>
	<tr class="containerBody">
    <td colspan="2"><dhv:label name="">Properties not present for the icelet</dhv:label></td>
  </tr>
<% } %>
</table>
<br />
<input type="hidden" name="id" value="<%= rowColumn.getId() %>"/>
<input type="hidden" name="position" value="<%= rowColumn.getPosition() %>"/>
<input type="hidden" name="pageRowId" value="<%= rowColumn.getPageRowId() %>"/>
<input type="hidden" name="enabled" value="<%= rowColumn.getEnabled() %>"/>
<input type="hidden" name="modified" value="<%= rowColumn.getModified() %>"/>
<input type="submit" value="<dhv:label name="button.update">Update</dhv:label>" name="Save"/>
<input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:self.close();">
</form>
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
