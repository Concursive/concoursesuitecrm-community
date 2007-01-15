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
<jsp:useBean id="sites" class="org.aspcfs.modules.website.base.SiteList" scope="request"/>
<jsp:useBean id="siteListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="website_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/checkNumber.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<SCRIPT LANGUAGE="JavaScript" type="text/javascript">
<%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
  function reopen() {
    window.location.href='Sites.do?command=List&popup=true';
  }
</script>
<!-- Sub Trails -->
<table class="trails" cellspacing="0"><tr><td>
  <dhv:label name="">Sites</dhv:label>
</td></tr></table>
<!-- End Sub Trails -->
<%-- Begin the container contents --%>
<dhv:permission name="site-editor-add">
<a href="Sites.do?command=TemplateList&fromList=true&popup=true"><dhv:label name="">Add Site</dhv:label></a><br />
</dhv:permission>
<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="siteListInfo"/>
<%-- TODO:: modify the code to display the break properly based on the permissions --%>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th width="8">
      &nbsp;
    </th>
    <th width="50%"><strong><dhv:label name="quotes.productName">Name</dhv:label></strong></th>
    <th width="50%"><strong>Description</strong></th>
    <th nowrap><strong><dhv:label name="">Hit Count</dhv:label></strong></th>
    <th nowrap><strong><dhv:label name="product.enabled">Enabled</dhv:label></strong></th>
  </tr>
<%
  int rowid = 0;
  int i = 0;
  Iterator iter = sites.iterator();
  while (iter.hasNext()) {
    Site site = (Site) iter.next();
    i++;
    rowid = (rowid != 1 ? 1 : 2);
%>
  <tr class="row<%= rowid %>">
    <td valign="center" nowrap>
      <%-- Use the unique id for opening the menu, and toggling the graphics --%>
       <a href="javascript:displayMenu('select<%= i %>','menuSite','<%= site.getId() %>','<%= site.getEnabled() %>');"
       onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuSite');">
       <img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
    </td>
    <td valign="top" width="50%">
      <a href="Sites.do?command=Details&siteId=<%= site.getId() %>&popup=true"><%= toHtml(site.getName()) %></a>
    </td>
    <td valign="top" width="50%"><%= toHtml(site.getInternalDescription()) %></td>
    <td valign="top" nowrap><%= site.getHitCount() %></td>
    <td valign="top" nowrap>
      <% if (site.getEnabled()) { %>
        <dhv:label name="">Yes</dhv:label>
      <%} else {%>
        <dhv:label name="">No</dhv:label>
      <%}%>
    </td>
  </tr>
<%}%>
</table>
<br>
<dhv:pagedListControl object="siteListInfo"/>
