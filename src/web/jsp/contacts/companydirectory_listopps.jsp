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
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.pipeline.base.OpportunityHeader,com.zeroio.iteam.base.*" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="opportunityHeaderList" class="org.aspcfs.modules.pipeline.base.OpportunityHeaderList" scope="request"/>
<jsp:useBean id="ExternalOppsPagedListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="companydirectory_listopps_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<dhv:evaluate exp="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="ExternalContacts.do">Contacts</a> > 
<a href="ExternalContacts.do?command=SearchContacts">Search Results</a> >
<a href="ExternalContacts.do?command=ContactDetails&id=<%= ContactDetails.getId() %>">Contact Details</a> >
Opportunities
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<%@ include file="contact_details_header_include.jsp" %>
<% String param1 = "id=" + ContactDetails.getId(); 
    String param2 = addLinkParams(request, "popup|popupType|actionId"); %>
<dhv:container name="contacts" selected="opportunities" param="<%= param1 %>" appendToUrl="<%= param2 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
<% if(ContactDetails.getOrgId() > 0){ %>
  <dhv:permission name="contacts-external_contacts-opportunities-add,accounts-accounts-contacts-opportunities-add" all="true"><a href="ExternalContactsOpps.do?command=Prepare&contactId=<%= ContactDetails.getId() %>&actionSource=ExternalContactsOpps<%= addLinkParams(request, "popup|popupType|actionId") %>">Add an Opportunity</a></dhv:permission>
<% }else{ %>
  <dhv:permission name="contacts-external_contacts-opportunities-add"><a href="ExternalContactsOpps.do?command=Prepare&contactId=<%= ContactDetails.getId() %>&actionSource=ExternalContactsOpps<%= addLinkParams(request, "popup|popupType|actionId") %>">Add an Opportunity</a></dhv:permission>
<% } %>
<center><%= ExternalOppsPagedListInfo.getAlphabeticalPageLinks() %></center>
<table width="100%" border="0">
  <tr>
    <form name="listView" method="post" action="ExternalContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>">
    <td align="left">
      <select size="1" name="listView" onChange="javascript:document.forms[0].submit();">
        <option <%= ExternalOppsPagedListInfo.getOptionValue("my") %>>My Open Opportunities </option>
        <option <%= ExternalOppsPagedListInfo.getOptionValue("all") %>>All Open Opportunities</option>
        <option <%= ExternalOppsPagedListInfo.getOptionValue("closed") %>>All Closed Opportunities</option>
      </select>
    </td>
    <td>
      <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="ExternalOppsPagedListInfo"/>
    </td>
    </form>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th>
      <strong>Action</strong>
    </th>
    <th nowrap>
      <strong><a href="ExternalContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %>&column=x.description<%= addLinkParams(request, "popup|popupType|actionId") %>">Opportunity Name</a></strong>
      <%= ExternalOppsPagedListInfo.getSortIcon("x.description") %>
    </th>
    <th nowrap>
      <strong>Best Guess Total</strong>
    </th>
    <th nowrap>
      <strong><a href="ExternalContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %>&column=x.modified<%= addLinkParams(request, "popup|popupType|actionId") %>">Last Modified</a></strong>
      <%= ExternalOppsPagedListInfo.getSortIcon("x.modified") %>
    </th>
  </tr>
<%
	Iterator j = opportunityHeaderList.iterator();
  FileItem thisFile = new FileItem();
	if ( j.hasNext() ) {
		int rowid = 0;
    int count =0;
	    while (j.hasNext()) {
        count++;
        rowid = (rowid != 1?1:2);
        OpportunityHeader oppHeader = (OpportunityHeader)j.next();
%>      
    <tr class="containerBody">
      <td width="8" valign="top" align="center" class="row<%= rowid %>" nowrap>
      <%-- check if user has edit or delete based on the type of contact --%>
        <%
          int hasEditPermission = 0;
          int hasDeletePermission = 0;
          if(ContactDetails.getOrgId() < 0){ %>
          <dhv:permission name="contacts-external_contacts-opportunities-edit">
            <% hasEditPermission = 1; %>
          </dhv:permission>
          <dhv:permission name="contacts-external_contacts-opportunities-delete">
            <% hasDeletePermission = 1; %>
          </dhv:permission>
        <% }else{ %>
          <dhv:permission name="contacts-external_contacts-opportunities-edit,accounts-accounts-contacts-opportunities-edit"  all="true">
            <% hasEditPermission = 1; %>
          </dhv:permission>
          <dhv:permission name="contacts-external_contacts-opportunities-delete,accounts-accounts-contacts-opportunities-delete" all="true">
            <% hasDeletePermission = 1; %>
          </dhv:permission>
        <% } %>
        
        <%-- Use the unique id for opening the menu, and toggling the graphics --%>
         <a href="javascript:displayMenu('select<%= count %>','menuOpp','<%= ContactDetails.getId() %>','<%= oppHeader.getId() %>','<%= hasEditPermission %>', '<%= hasDeletePermission %>');" onMouseOver="over(0, <%= count %>)" onmouseout="out(0, <%= count %>); hideMenu('menuOpp');"><img src="images/select.gif" name="select<%= count %>" id="select<%= count %>" align="absmiddle" border="0"></a>
      </td>
      <td width="100%" valign="top" class="row<%= rowid %>">
        <a href="ExternalContactsOpps.do?command=DetailsOpp&headerId=<%= oppHeader.getId() %>&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>">
        <%= toHtml(oppHeader.getDescription()) %></a>
        (<%= oppHeader.getComponentCount() %>)
<dhv:evaluate if="<%= oppHeader.hasFiles() %>">
        <%= thisFile.getImageTag() %>
</dhv:evaluate>
      </td>  
      <td valign="top" align="right" class="row<%= rowid %>" nowrap>
        <zeroio:currency value="<%= oppHeader.getTotalValue() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;"/>
      </td>
      <td valign="top" align="center" class="row<%= rowid %>" nowrap>
        <zeroio:tz timestamp="<%= oppHeader.getModified() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="yes" />
      </td>
    </tr>
<%
    }
	} else {
%>
    <tr class="containerBody">
      <td colspan="6">
        No opportunities found.
      </td>
    </tr>
<%}%>
</table>
<br>
<dhv:pagedListControl object="ExternalOppsPagedListInfo"/>
</td>
</tr>
</table>

