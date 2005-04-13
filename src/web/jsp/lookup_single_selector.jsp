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
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.base.*,org.aspcfs.utils.web.*" %>
<%@ page import="org.aspcfs.utils.*" %>
<jsp:useBean id="BaseList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="DisplayFieldId" class="java.lang.String" scope="request"/>
<jsp:useBean id="lookupId" class="java.lang.String" scope="request"/>
<jsp:useBean id="moduleId" class="java.lang.String" scope="request"/>
<jsp:useBean id="LookupSingleSelectorInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<%@ include file="../initPage.jsp" %>
<form name="elementListView" method="post" action="LookupSelector.do?command=PopupSelector">
<br />
<center><%= LookupSingleSelectorInfo.getAlphabeticalPageLinks() %></center>
<input type="hidden" name="letter">
<table width="100%" border="0">
  <tr>
      <td align="right">
        <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="LookupSingleSelectorInfo"/>
      </td>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th width="100%">
      <dhv:label name="product.options">Options</dhv:label>
    </th>
  </tr>
<%
  Iterator j = BaseList.iterator();
  if ( j.hasNext() ) {
    int rowid = 0;
    while (j.hasNext()) {
      rowid = (rowid != 1?1:2);
      LookupElement thisElt = (LookupElement)j.next();
%>
  <tr class="row<%= rowid %>">
    <td valign="center">
      <a href="javascript:setParentValue('<%= DisplayFieldId %>','<%= StringUtils.jsStringEscape(thisElt.getDescription()) %>');">
        <%= toHtml(thisElt.getDescription()) %>
      </a>
    </td>
  </tr>
<%} } else {%>
      <tr class="containerBody">
        <td colspan="2">
          <dhv:label name="calendar.noOptionsAvailable.text">No options are available.</dhv:label>
        </td>
      </tr>
<%}%>
</table>
<input type="hidden" name="rowcount" value="0">
<input type="hidden" name="displayFieldId" value="<%= DisplayFieldId %>">
<input type="hidden" name="moduleId" value="<%= moduleId %>">
<input type="hidden" name="lookupId" value="<%= lookupId %>">
<input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.close()">
<br />
</form>

