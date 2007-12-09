<%-- 
  - Copyright(c) 2007 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id: companydirectory_listopps.jsp 18488 2007-01-15 20:12:32Z matt $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.pipeline.base.*,org.aspcfs.modules.pipeline.beans.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="OpportunityHeaderList" class="org.aspcfs.modules.pipeline.base.OpportunityHeaderList" scope="request"/>
<jsp:useBean id="SalesOpportunitiesPagedListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="from" class="java.lang.String" scope="request" />
<jsp:useBean id="listForm" class="java.lang.String" scope="request" />
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="sales_listoop_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<%if (isPopup(request)) {%>
<script language="JavaScript" type="text/javascript" src="javascript/scrollReload.js"></script>
<%}%>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
  function reopenContact(id) {
    if (id == '<%= ContactDetails.getId() %>') {
      scrollReload('SalesOpportunities.do?command=SearchContacts');
      return -1;
    } else {
      return '<%= ContactDetails.getId() %>';
    }
  }
  
  function reopenOnDelete(id) {
    try {
      if ('<%= isPopup(request) %>' != 'true') {
        scrollReload('SalesOpportunities.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %>');
      } else {
        scrollReload('SalesOpportunities.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %><%= isPopup(request)?"&popup=true":"" %>');
        var oppId = -1;
        try {
        oppId = opener.reopenOpportunity(id);
        } catch (oException) {
        }
        if (oppId != '-1') {
          opener.reopen();
        }
      }
    } catch (oException) {
    }
  }
  
</script>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Sales.do"><dhv:label name="Leads" mainMenuItem="true">Leads</dhv:label></a> >
<% if (from != null && "list".equals(from) && !"dashboard".equals(from)) { %>
  <a href="Sales.do?command=List&listForm=<%= (listForm != null? listForm:"") %>"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<%}%>
<a href="Sales.do?command=Details&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId|from|listForm") %>"><dhv:label name="sales.leadDetails">Lead Details</dhv:label></a> >
<dhv:label name="accounts.accounts_contacts_oppcomponent_add.Opportunities">Opportunities</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="leads" selected="opportunities" object="ContactDetails" param='<%= "id=" + ContactDetails.getId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId|from|listForm") %>'>
  <dhv:evaluate if="<%= !ContactDetails.isTrashed() %>" >
    <dhv:evaluate if="<%= (ContactDetails.getEnabled() && !ContactDetails.isTrashed() && ContactDetails.getOrgId() > 0) %>" >
      <dhv:permission name="sales-leads-opportunities-add,accounts-accounts-contacts-opportunities-add" all="true">
        <a href="SalesOpportunities.do?command=Prepare&contactId=<%= ContactDetails.getId() %>&actionSource=SalesOpportunities<%= addLinkParams(request, "popup|popupType|actionId|from|listForm") %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_list.AddAnOpportunity">Add an Opportunity</dhv:label></a>
      </dhv:permission>
    </dhv:evaluate>
    <dhv:evaluate if="<%= (ContactDetails.getEnabled() && !ContactDetails.isTrashed() && ContactDetails.getOrgId() <= 0) %>" >
      <dhv:permission name="sales-leads-opportunities-add">
        <a href="SalesOpportunities.do?command=Prepare&contactId=<%= ContactDetails.getId() %>&actionSource=SalesOpportunities<%= addLinkParams(request, "popup|popupType|actionId|from|listForm") %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_list.AddAnOpportunity">Add an Opportunity</dhv:label></a>
      </dhv:permission>
    </dhv:evaluate>
  </dhv:evaluate>
  <dhv:include name="pagedListInfo.alphabeticalLinks" none="true">
  <center><dhv:pagedListAlphabeticalLinks object="SalesOpportunitiesPagedListInfo"/></center></dhv:include>
  <table width="100%" border="0">
    <tr>
      <form name="listView" method="post" action="SalesOpportunities.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId|from|listForm") %>">
      <td align="left">
        <select size="1" name="listView" onChange="javascript:document.listView.submit();">
          <option <%= SalesOpportunitiesPagedListInfo.getOptionValue("all") %>><dhv:label name="accounts.accounts_contacts_oppcomponent_list.AllOpenOpportunities">All Open Opportunities</dhv:label></option>
          <option <%= SalesOpportunitiesPagedListInfo.getOptionValue("closed") %>><dhv:label name="accounts.accounts_contacts_oppcomponent_list.AllClosedOpportunities">All Closed Opportunities</dhv:label></option>
          <option <%= SalesOpportunitiesPagedListInfo.getOptionValue("my") %>><dhv:label name="accounts.accounts_contacts_oppcomponent_list.MyOpenOpportunities">My Open Opportunities</dhv:label> </option>
        </select>
      </td>
      <td>
        <dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="SalesOpportunitiesPagedListInfo"/>
      </td>
      </form>
    </tr>
  </table>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
    <tr>
      <th width="8">&nbsp;</th>
      <th nowrap>
        <strong><a href="SalesOpportunities.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %>&column=x.description<%= addLinkParams(request, "popup|popupType|actionId|from|listForm") %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_list.OpportunityName">Opportunity Name</dhv:label></a></strong>
        <%= SalesOpportunitiesPagedListInfo.getSortIcon("x.description") %>
      </th>
      <th nowrap>
        <strong><dhv:label name="accounts.accounts_contacts_oppcomponent_list.BestGuessTotal">Best Guess Total</dhv:label></strong>
      </th>
      <th nowrap>
        <strong><a href="SalesOpportunities.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %>&column=x.modified<%= addLinkParams(request, "popup|popupType|actionId|from|listForm") %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_list.LastModified">Last Modified</dhv:label></a></strong>
        <%= SalesOpportunitiesPagedListInfo.getSortIcon("x.modified") %>
      </th>
    </tr>
  <%
    Iterator j = OpportunityHeaderList.iterator();
    FileItem thisFile = new FileItem();
    if ( j.hasNext() ) {
      int rowid = 0;
      int count =0;
      HashMap headersListed = new HashMap();
      while (j.hasNext()) {
        count++;
        rowid = (rowid != 1?1:2);
        OpportunityHeader oppHeader = (OpportunityHeader)j.next();
        boolean hasPermission = false;
  %>
  <dhv:hasAuthority owner="<%= oppHeader.getManager() %>">
    <% hasPermission = true; %>
  </dhv:hasAuthority>
    <tr class="row<%= rowid %>">
      <td width="8" valign="top" align="center" nowrap>
      <%-- check if user has edit or delete based on the type of contact --%>
        <%
          int hasEditPermission = 0;
          int hasDeletePermission = 0;
          if(ContactDetails.getOrgId() < 0){ %>
          <dhv:permission name="sales-leads-opportunities-edit">
            <% hasEditPermission = 1; %>
          </dhv:permission>
          <dhv:permission name="sales-leads-opportunities-delete">
            <% hasDeletePermission = 1; %>
          </dhv:permission>
        <% }else{ %>
          <dhv:permission name="sales-leads-opportunities-edit,accounts-accounts-contacts-opportunities-edit"  all="true">
            <% hasEditPermission = 1; %>
          </dhv:permission>
          <dhv:permission name="sales-leads-opportunities-delete,accounts-accounts-contacts-opportunities-delete" all="true">
            <% hasDeletePermission = 1; %>
          </dhv:permission>
        <% } %>

        <%-- Use the unique id for opening the menu, and toggling the graphics --%>
        <dhv:evaluate if="<%= (ContactDetails.getEnabled() && !ContactDetails.isTrashed()) %>" >
          <a href="javascript:displayMenu('select<%= count %>','menuOpp','<%= ContactDetails.getId() %>','<%= oppHeader.getId() %>','<%= hasEditPermission %>', '<%= hasDeletePermission %>','<%= ContactDetails.isTrashed() || oppHeader.isTrashed() %>','<%= hasPermission %>');" onMouseOver="over(0, <%= count %>)" onmouseout="out(0, <%= count %>); hideMenu('menuOpp');"><img src="images/select.gif" name="select<%= count %>" id="select<%= count %>" align="absmiddle" border="0"></a>
        </dhv:evaluate>
        <dhv:evaluate if="<%= (!ContactDetails.getEnabled() || ContactDetails.isTrashed()) %>" >&nbsp;</dhv:evaluate>
      </td>
      <td width="100%" valign="top" class="row<%= rowid %>">
        <a href="SalesOpportunities.do?command=DetailsOpp&headerId=<%= oppHeader.getId() %>&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId|from|listForm") %>">
        <%= toHtml(oppHeader.getDescription()) %></a>
        (<%= oppHeader.getComponentCount() %>)
  <dhv:evaluate if="<%= oppHeader.hasFiles() %>">
          <%= thisFile.getImageTag() %>
  </dhv:evaluate>
      </td>
      <td valign="top" align="right" nowrap>
        <zeroio:currency value="<%= oppHeader.getTotalValue() %>" code='<%= applicationPrefs.get("SYSTEM.CURRENCY") %>' locale="<%= User.getLocale() %>" default="&nbsp;"/>
      </td>
      <td valign="top" align="center" nowrap>
        <zeroio:tz timestamp="<%= oppHeader.getModified() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" />
      </td>
    </tr>
  <%
      }
    } else {
  %>
    <tr class="containerBody">
      <td colspan="6">
        <dhv:label name="accounts.accounts_contacts_oppcomponent_list.NoOpportunitiesFound">No opportunities found.</dhv:label>
      </td>
    </tr>
  <%}%>
  </table>
  <br>
  <dhv:pagedListControl object="SalesOpportunitiesPagedListInfo"/>
</dhv:container>
