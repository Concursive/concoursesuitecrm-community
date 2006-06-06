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
<jsp:useBean id="fromList" class="java.lang.String" scope="request"/>
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
<body onLoad="javascript:document.website.name.focus();">
<form name="website" action="Sites.do?command=Save&auto-populate=true&popup=true" onSubmit="return checkForm(this);" method="post">
<!-- Trails -->
<table class="trails" cellspacing="0"><tr><td>
  <a href="Sites.do?command=List&popup=true"><dhv:label name="">Sites</dhv:label></a> >
  <dhv:evaluate if="<%= site.getId() == -1 %>">
    <a href="Sites.do?command=TemplateList&fromList=true&popup=true">Choose a Template</a> >
  </dhv:evaluate>
  Website Details
</td></tr></table>
<!-- End Trails -->
<table class="note" cellspacing="0">
  <tr>
    <th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
    <td><dhv:label name="">The following details determine how this website works and will also help you refer back to this website.</dhv:label></td>
  </tr>
</table>
<input type="hidden" name="id" value="<%= site.getId() %>"/>
<input type="hidden" name="template" value="<%= site.getTemplate() %>"/>
<input type="hidden" name="layoutId" value="<%= site.getLayoutId() %>"/>
<input type="hidden" name="styleId" value="<%= site.getStyleId() %>"/>
<dhv:formMessage showSpace="false"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <dhv:evaluate if="<%= site.getId() > -1 %>">
        <strong><dhv:label name="">Modify Website</dhv:label></strong>
      </dhv:evaluate><dhv:evaluate if="<%= site.getId() == -1 %>">
            <strong><dhv:label name="">Add Website</dhv:label></strong>
      </dhv:evaluate>
    </th>
  </tr>
	<tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="">Name</dhv:label>
    </td>
		<td>
      <table border="0" cellpadding="0" cellspacing="0" class="empty"><tr><td valign="top">
        <input type="text" size="35" maxlength="300" name="name" value="<%= toHtmlValue(site.getName()) %>"><font color="red">*</font>
      </td><td valign="top" nowrap>&nbsp;
        <%= showAttribute(request, "nameError") %>
      </td></tr></table>
		</td>
	</tr>
	<tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="product.enabled">Enabled</dhv:label>
    </td>
    <td>
      <input type="checkbox" name="enabled" value="true" <%= site.getEnabled()?"checked":"" %> />
      (indicates if the website can be publicly accessed at this time)
    </td>
  </tr>
	<tr class="containerBody">
		<td nowrap class="formLabel">
			<dhv:label name="">Internal Description</dhv:label>
		</td>
		<td>
			<TEXTAREA NAME="internalDescription" ROWS="3" COLS="50"><%= toString(site.getInternalDescription()) %></TEXTAREA>
		</td>
	</tr>
	<tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="">Notes</dhv:label>
    </td>
		<td>
			<TEXTAREA NAME="notes" ROWS="3" COLS="50"><%= toString(site.getNotes()) %></TEXTAREA>
		</td>
	</tr>
</table>
<br />
<dhv:evaluate if="<%= site.getId() > -1 %>">
  <input type="submit" value="<dhv:label name="button.update">Update</dhv:label>" name="Save"/>
  <input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='Sites.do?command=List&popup=true';"/>
</dhv:evaluate><dhv:evaluate if="<%= site.getId() == -1 %>">
  <input type="submit" value="<dhv:label name="button.save">Save</dhv:label>" name="Save"/>
  <input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='Sites.do?command=TemplateList<%= (fromList != null?"&fromList="+fromList:"") %>&popup=true';">
</dhv:evaluate>
</form>
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
</body>