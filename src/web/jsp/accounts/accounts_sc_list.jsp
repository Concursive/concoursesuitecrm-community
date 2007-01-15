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
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.utils.web.*,java.util.*,org.aspcfs.modules.assets.base.*,org.aspcfs.modules.servicecontracts.base.*,java.text.DateFormat" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="serviceContractList" class="org.aspcfs.modules.servicecontracts.base.ServiceContractList" scope="request"/>
<jsp:useBean id="serviceContractCategoryList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="serviceContractTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ServiceContractListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="sc_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<dhv:label name="accounts.accounts_sc_add.ServiceContracts">Service Contracts</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="accounts" selected="servicecontracts" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
  <dhv:evaluate if="<%= !OrgDetails.isTrashed() %>">
    <dhv:permission name="accounts-service-contracts-add">
      <a href="AccountsServiceContracts.do?command=Add&orgId=<%=OrgDetails.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>"><dhv:label name="account.sc.addServiceContract">Add Service Contract</dhv:label></a>
    </dhv:permission>
  </dhv:evaluate>
  <dhv:permission name="accounts-service-contracts-add" none="true"></dhv:permission>
  <dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="ServiceContractListInfo"/>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
    <tr>
      <th>
        &nbsp;
      </th>
      <th width="20%" nowrap>
        <strong><dhv:label name="account.sc.contractNumber">Contract Number</dhv:label></strong>
      </th>
      <th width="20%" >
        <strong><dhv:label name="accounts.accountasset_include.Category">Category</dhv:label></strong>
      </th>
      <th width="20%" nowrap>
        <strong><dhv:label name="accounts.accounts_add.Type">Type</dhv:label></strong>
      </th>
      <th width="20%">
        <strong><dhv:label name="account.sc.currentContractDate">Current Contract Date</dhv:label></strong>
      </th>
      <th width="20%" nowrap>
        <strong><dhv:label name="product.endDate">End Date</dhv:label></strong>
      </th>
    </tr>
  <%
    Iterator itr = serviceContractList.iterator();
    if (itr.hasNext()){
      int rowid = 0;
      int i = 0;
      while (itr.hasNext()){
        i++;
        rowid = (rowid != 1 ? 1 : 2);
        ServiceContract thisContract = (ServiceContract)itr.next();
    %>      
    <tr class="row<%= rowid %>">
      <td width="8" valign="center" nowrap>
          <% int status = -1;%>
          <% status = OrgDetails.getEnabled() ? 1 : 0; %>
          <%-- Use the unique id for opening the menu, and toggling the graphics --%>
           <a href="javascript:displayMenu('select<%= i %>','menuServiceContract', '<%=request.getParameter("orgId") %>', '<%= thisContract.getId() %>', '<%= thisContract.isTrashed() || OrgDetails.isTrashed() %>');"
           onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuServiceContract');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0" /></a>
      </td>
      <td width="20%">
        <a href="AccountsServiceContracts.do?command=View&orgId=<%=request.getParameter("orgId")%>&id=<%= thisContract.getId()%><%= addLinkParams(request, "popup|popupType|actionId") %>"><%= toHtml(thisContract.getServiceContractNumber()) %></a>
      </td>
      <td width="20%">
      <dhv:evaluate if="<%= thisContract.getCategory() > 0 %>">
        <%= toHtml(serviceContractCategoryList.getSelectedValue(thisContract.getCategory())) %>
      </dhv:evaluate>&nbsp;
      </td>
      <td width="20%" >
      <dhv:evaluate if="<%= thisContract.getType() > 0 %>">
        <%= toHtml(serviceContractTypeList.getSelectedValue(thisContract.getType())) %>
      </dhv:evaluate>&nbsp;
      </td>
      <td width="20%">
        <% if(!User.getTimeZone().equals(thisContract.getCurrentStartDateTimeZone())){%>
        <zeroio:tz timestamp="<%= thisContract.getCurrentStartDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
        <% } else { %>
        <zeroio:tz timestamp="<%= thisContract.getCurrentStartDate() %>" dateOnly="true" timeZone="<%= thisContract.getCurrentStartDateTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
        <% } %>
      </td>
      <td width="20%"  nowrap>
        <% if(!User.getTimeZone().equals(thisContract.getCurrentEndDateTimeZone())){%>
        <zeroio:tz timestamp="<%= thisContract.getCurrentEndDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
        <% } else {%>
        <zeroio:tz timestamp="<%= thisContract.getCurrentEndDate() %>" dateOnly="true" timeZone="<%= thisContract.getCurrentEndDateTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
        <% } %>
      </td>
    </tr>
    <%  
      }
    }else{
    %>
    <tr class="containerBody">
      <td colspan="6">
        <dhv:label name="account.sc.noServiceContractsFound">No service contracts found.</dhv:label>
      </td>
    </tr>
    <%
    }
    %>
</table>
  <br>
  <dhv:pagedListControl object="ServiceContractListInfo"/>
</dhv:container>