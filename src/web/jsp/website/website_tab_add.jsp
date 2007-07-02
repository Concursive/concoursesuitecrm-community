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
<jsp:useBean id="tab" class="org.aspcfs.modules.website.base.Tab" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript">
	function checkForm(form) {
		var flag = true;
		message = "";
		if (flag == false) {
        alert(label("check.form","Form could not be saved, please check the following:\r\n\r\n") + message);
	      return false;
		} else {
			return true;
		}
	}
</script>
<body onLoad="javascript:document.website.displayText.focus();">
<form name="website" action="Tabs.do?command=Save&auto-populate=true&popup=true" onSubmit="return checkForm(this);" method="post">
<input type="hidden" name="id" value="<%= tab.getId() %>"/>
<input type="hidden" name="siteId" value="<%= tab.getSiteId() %>"/>
<input type="hidden" name="position" value="<%= tab.getPosition() %>"/>
<input type="hidden" name="nextTabId" value="<%= tab.getNextTabId() %>"/>
<input type="hidden" name="modified" value="<%= tab.getModified() %>">
<input type="hidden" name="previousTabId" value="<%= tab.getPreviousTabId() %>"/>
<dhv:formMessage showSpace="false"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <dhv:evaluate if="<%= tab.getId() > -1 %>">
        <strong><dhv:label name="">Modify Tab</dhv:label></strong>
      </dhv:evaluate><dhv:evaluate if="<%= tab.getId() == -1 %>">
            <strong><dhv:label name="">Add Tab</dhv:label></strong>
      </dhv:evaluate>
    </th>
  </tr>
	<tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="website.tab.title">Title</dhv:label>
    </td>
		<td>
      <table border="0" cellpadding="0" cellspacing="0" class="empty"><tr><td valign="top">
        <input type="text" size="35" maxlength="300" name="displayText" value="<%= toHtmlValue(tab.getDisplayText()) %>"><font color="red">*</font>
        <br />
        <dhv:label name="website.tab.meta.title">(This will become the page's Meta-Title)</dhv:label>&nbsp;
        <%= showAttribute(request, "displayTextError") %>
      </td></tr></table>
		</td>
	</tr>
	<tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="product.enabled">Enabled</dhv:label>
    </td>
    <td>
     <input type="checkbox" name="enabled" value="true" <%= tab.getEnabled()?"checked":"" %> />
    </td>
  </tr>
	<tr class="containerBody">
		<td nowrap class="formLabel">
			<dhv:label name="website.tab.internal.description">Internal Description</dhv:label>
		</td>
		<td>
      <table border="0" cellpadding="0" cellspacing="0" class="empty"><tr><td valign="top">
        <TEXTAREA NAME="internalDescription" ROWS="3" COLS="50"><%= toString(tab.getInternalDescription()) %></TEXTAREA>
        <br />
        <dhv:label name="website.tab.meta.description">(This will become the page's Meta-Description)</dhv:label>
      </td></tr></table>
		</td>
	</tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="website.tab.keywords">Keywords</dhv:label>
    </td>
    <td>
    <table border="0" cellpadding="0" cellspacing="0" class="empty"><tr><td valign="top">
        <input type="text" size="35" name="keywords" value="<%= toHtmlValue(tab.getKeywords()) %>" />
        <br />
        <dhv:label name="website.tab.meta.keywords">(This will become the page's Meta-Keywords)</dhv:label>
      </td></tr></table>
    </td>
  </tr>
</table>
<br />
<dhv:evaluate if="<%= tab.getId() > -1 %>">
  <input type="submit" value="<dhv:label name="button.update">Update</dhv:label>" name="Save"/>
</dhv:evaluate><dhv:evaluate if="<%= tab.getId() == -1 %>">
  <input type="submit" value="<dhv:label name="button.save">Save</dhv:label>" name="Save"/>
</dhv:evaluate>
<input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:self.close();">
</form>
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
</body>
