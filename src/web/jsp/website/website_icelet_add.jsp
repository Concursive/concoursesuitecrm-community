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
<%@ page import="org.aspcfs.utils.StringUtils,org.aspcfs.utils.web.ClientType" %>
<%@ page import="java.util.*,java.text.*,org.aspcfs.modules.website.base.*,org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="rowColumn" class="org.aspcfs.modules.website.base.RowColumn" scope="request"/>
<jsp:useBean id="icelets" class="org.aspcfs.modules.website.base.IceletList" scope="request"/>
<jsp:useBean id="icelet" class="org.aspcfs.modules.website.base.Icelet" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js?1"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popProductCategories.js"></script>
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
<form name="icelet" action="RowColumns.do?command=ModifyIceletProperties&rowColumnId=<%= rowColumn.getId() %>&popup=true&auto-populate=true" onSubmit="return checkForm(this);" method="post">
<dhv:formMessage showSpace="false"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2"><strong><dhv:label name="">Column Properties</dhv:label></strong></th>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="">Width</dhv:label>
    </td>
    <td><input type="text" name="width" value="<%= (rowColumn.getWidth() > -1? rowColumn.getWidth(): 100) %>" /></td>
  </tr>
</table>
&nbsp;<br />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2"><strong><dhv:label name="">Choose a Portlet</dhv:label></strong></th>
  </tr>
<tr class="containerBody">
  <td>
<%  Iterator iter = (Iterator) icelets.iterator();
    while (iter.hasNext()) {
      Icelet thisIcelet = (Icelet) iter.next();
%>
  <div style="float: left;width: 80px;height: 90px;padding-right: 3px;">
    <table border="0" width="70" height="80" cellpadding="0" cellspacing="0">
      <tr>
      <dhv:evaluate if="<%= icelet != null && icelet.getId() == thisIcelet.getId() %>">
        <th width="70" height="80" align="center" valign="center">
      </dhv:evaluate>
      <dhv:evaluate if="<%= icelet == null || icelet.getId() != thisIcelet.getId() %>">
        <td width="70" height="80" align="center" valign="center">
      </dhv:evaluate>
      <a href="javascript:document.forms['icelet'].action += '&iceletId=<%= thisIcelet.getId() %>&fromWebsite=<%= icelets.getForWebsite()? "true":"false" %>';document.forms['icelet'].submit();">
        <div><%=  toHtml(thisIcelet.getName()) %></div></a>
      <dhv:evaluate if="<%= icelet == null || icelet.getId() != thisIcelet.getId() %>">
        </td>
      </dhv:evaluate>
      <dhv:evaluate if="<%= icelet != null && icelet.getId() == thisIcelet.getId() %>">
        </th>
      </dhv:evaluate>
      </tr>
    </table>
  </div>
<%} %>
</td></tr>
</table>
<br />
<input type="hidden" name="id" value="<%= rowColumn.getId() %>" />
<input type="hidden" name="position" value="<%= rowColumn.getPosition() %>" />
<input type="hidden" name="pageRowId" value="<%= rowColumn.getPageRowId() %>" />
<input type="hidden" name="enabled" value="<%= rowColumn.getEnabled() %>" />
<input type="hidden" name="modified" value="<%= rowColumn.getModified() %>" />
<input type="hidden" name="nextRowColumnId" value="<%= rowColumn.getNextRowColumnId() %>" />
<input type="hidden" name="previousRowColumnId" value="<%= rowColumn.getPreviousRowColumnId() %>" />
<input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:self.close();" />
</form>
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
