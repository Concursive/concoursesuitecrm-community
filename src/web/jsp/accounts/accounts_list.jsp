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
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*, org.aspcfs.modules.base.*" %>
<jsp:useBean id="OrgList" class="org.aspcfs.modules.accounts.base.OrganizationList" scope="request"/>
<jsp:useBean id="SearchOrgListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TypeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="accounts_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<dhv:label name="accounts.SearchResults">Search Results</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:evaluate if="<%= (User.getRoleType() > 0) %>" >
<table class="note" cellspacing="0">
  <tr>
    <th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
    <td><dhv:label name="accounts.manage">Select an account to manage.</dhv:label></td>
  </tr>
</table>
</dhv:evaluate>
<dhv:permission name="accounts-accounts-add"><a href="Accounts.do?command=Add"><dhv:label name="accounts.add">Add an Account</dhv:label></a></dhv:permission>
<dhv:include name="pagedListInfo.alphabeticalLinks" none="true">
<center><dhv:pagedListAlphabeticalLinks object="SearchOrgListInfo"/></center></dhv:include>
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="SearchOrgListInfo"/>
<% int columnCount = 0; %>
<table cellpadding="8" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th width="8" <% ++columnCount; %>>
      &nbsp;
    </th>
    <th nowrap <% ++columnCount; %>>
      <strong><a href="Accounts.do?command=Search&column=o.name"><dhv:label name="organization.name">Account Name</dhv:label></a></strong>
      <%= SearchOrgListInfo.getSortIcon("o.name") %>
    </th>
    
    <th nowrap <% ++columnCount; %>>
      <strong><a href="Accounts.do?command=Search&column=ct_owner.namelast"><dhv:label name="organization.owner">Owner Name</dhv:label></a></strong>
      <%= SearchOrgListInfo.getSortIcon("ct_owner.namelast") %>     
    </th>
    
<%--    <dhv:include name="organization.list.siteId" none="true"> --%>
<zeroio:debug value="<%="JSP::accounts_list.jsp "+ SearchOrgListInfo.getSearchOptionValue("searchcodeOrgSiteId")+" == "+(String.valueOf(Constants.INVALID_SITE)) %>"/>
      <dhv:evaluate if="<%= SearchOrgListInfo.getSearchOptionValue("searchcodeOrgSiteId").equals(String.valueOf(Constants.INVALID_SITE)) %>">
        <th <% ++columnCount; %>>
          <strong><dhv:label name="accounts.site">Site</dhv:label></strong>
        </th>
      </dhv:evaluate>
<%--    </dhv:include> --%>
      <dhv:include name="organization.addresses" none="true">
        <th nowrap <% ++columnCount; %>>
           <strong><a href="Accounts.do?command=Search&column=oa.city"><dhv:label name="accounts.accounts_add.City">City</dhv:label></a></strong>
	 	   <%= SearchOrgListInfo.getSortIcon("oa.city") %> 
	 	</th>
      </dhv:include>    
      <dhv:include name="organization.addresses" none="true">
    	<th nowrap <% ++columnCount; %>>
          <strong><a href="Accounts.do?command=Search&column=oa.state"><dhv:label name="accounts.accounts_add.State">State</dhv:label></a></strong>
					<%= SearchOrgListInfo.getSortIcon("oa.state") %> 
		</th>
	  </dhv:include>
      <dhv:include name="organization.addresses" none="true">
        <th nowrap <% ++columnCount; %>>
          <strong><a href="Accounts.do?command=Search&column=oa.postalcode"><dhv:label name="accounts.accounts_add.Zip">Zip</dhv:label></a></strong>
					<%= SearchOrgListInfo.getSortIcon("oa.postalcode") %> 
		</th>
      </dhv:include>
      <dhv:include name="organization.phoneNumbers" none="true">
        <th nowrap <% ++columnCount; %>>
          <strong><dhv:label name="account.phoneFax">Phone/Fax</dhv:label></strong>
		</th>
      </dhv:include>
      <dhv:include name="organization.addresses" none="true">
        <th nowrap <% ++columnCount; %>>
          <strong><a href="Accounts.do?command=Search&column=oa.county"><dhv:label name="accounts.accounts_add.County">County</dhv:label></a></strong>
					<%= SearchOrgListInfo.getSortIcon("oa.county") %> 
				</th>
      </dhv:include>
  </tr>
<%
	Iterator j = OrgList.iterator();
	if ( j.hasNext() ) {
    int rowid = 0;
    int i = 0;
    while (j.hasNext()) {
    i++;
    rowid = (rowid != 1 ? 1 : 2);
    Organization thisOrg = (Organization)j.next();
%>
  <tr class="row<%= rowid %>">
    <td width="8" valign="center" nowrap>
      <% int status = -1;%>
      <dhv:permission name="accounts-accounts-edit"><% status = thisOrg.getEnabled() ? 1 : 0; %></dhv:permission>
      <%-- Use the unique id for opening the menu, and toggling the graphics --%>
       <a href="javascript:displayMenu('select<%= i %>','menuAccount', '<%= thisOrg.getOrgId() %>', '<%= status %>', '<%=thisOrg.isTrashed() %>');"
       onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuAccount');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
    </td>
	<td>
      <a href="Accounts.do?command=Details&orgId=<%=thisOrg.getOrgId()%>"><%= toHtml(thisOrg.getName()) %></a>
	</td>
	
	<td>
      <%= toHtml(thisOrg.getOwnerName()) %>
	</td>
		
		
<%--    <dhv:include name="organization.list.siteId" none="true"> --%>
      <dhv:evaluate if="<%= SearchOrgListInfo.getSearchOptionValue("searchcodeOrgSiteId").equals(String.valueOf(Constants.INVALID_SITE)) %>">
        <td valign="top"><%= SiteIdList.getSelectedValue(thisOrg.getSiteId()) %></td>
      </dhv:evaluate>
<%--    </dhv:include> --%>
      <dhv:include name="organization.addresses" none="true">
		<td valign="center" nowrap>
		<dhv:evaluate if="<%=(thisOrg.getPrimaryContact() == null)%>">
	     <% if ( thisOrg.getPrimaryAddress() != null) { %>	
           <% if ( (!"".equals(thisOrg.getPrimaryAddress().getCity()))) { %>
             <%= toHtml(thisOrg.getPrimaryAddress().getCity()) %>
           <%} else {%>
             &nbsp;
           <%}%>
         <%} else {%>
           &nbsp;
         <%}%>
		</dhv:evaluate>     
		<dhv:evaluate if="<%=(thisOrg.getPrimaryContact() != null)%>">
		<% if ( thisOrg.getPrimaryContact().getPrimaryAddress() != null) { %>	
          <% if ( (!"".equals(thisOrg.getPrimaryContact().getPrimaryAddress().getCity()))) { %>
            <%= toHtml(thisOrg.getPrimaryContact().getPrimaryAddress().getCity()) %>  
          <%} else {%>
            &nbsp;
          <%}%>  
        <%} else {%>
          &nbsp;
        <%}%>  
		</dhv:evaluate>
		</td>
    </dhv:include>
    <dhv:include name="organization.addresses" none="true">
		<td valign="center" nowrap>
		<dhv:evaluate if="<%=(thisOrg.getPrimaryContact() == null)%>">
	     <% if ( thisOrg.getPrimaryAddress() != null) { %>	
           <% if ( (!"-1".equals(thisOrg.getPrimaryAddress().getState()))) { %>
             <%= toHtml(thisOrg.getPrimaryAddress().getState()) %>
           <%} else {%>
             &nbsp;
           <%}%>
         <%} else {%>
           &nbsp;
         <%}%>
		</dhv:evaluate>     
		<dhv:evaluate if="<%=(thisOrg.getPrimaryContact() != null)%>">
		<% if ( thisOrg.getPrimaryContact().getPrimaryAddress() != null) { %>	
          <% if ( (!"-1".equals(thisOrg.getPrimaryContact().getPrimaryAddress().getState()))) { %>
            <%= toHtml(thisOrg.getPrimaryContact().getPrimaryAddress().getState()) %>  
          <%} else {%>
            &nbsp;
          <%}%>  
        <%} else {%>
          &nbsp;
        <%}%>  
		</dhv:evaluate>
		</td>
    </dhv:include>
    <dhv:include name="organization.addresses" none="true">
		<td valign="center" nowrap>
		<dhv:evaluate if="<%=(thisOrg.getPrimaryContact() == null)%>">
	     <% if ( thisOrg.getPrimaryAddress() != null) { %>	
           <% if ( (!"".equals(thisOrg.getPrimaryAddress().getZip()))) { %>
             <%= toHtml(thisOrg.getPrimaryAddress().getZip()) %>
           <%} else {%>
             &nbsp;
           <%}%>
         <%} else {%>
           &nbsp;
         <%}%>
		</dhv:evaluate>     
		<dhv:evaluate if="<%=(thisOrg.getPrimaryContact() != null)%>">
		<% if ( thisOrg.getPrimaryContact().getPrimaryAddress() != null) { %>	
          <% if ( (!"".equals(thisOrg.getPrimaryContact().getPrimaryAddress().getZip()))) { %>
            <%= toHtml(thisOrg.getPrimaryContact().getPrimaryAddress().getZip()) %>  
          <%} else {%>
            &nbsp;
          <%}%>  
        <%} else {%>
          &nbsp;
        <%}%>  
		</dhv:evaluate>
		</td>
    </dhv:include>


    <dhv:include name="organization.phoneNumbers" none="true">
		<td valign="center" nowrap>
		<dhv:evaluate if="<%=(thisOrg.getPrimaryContact() == null)%>">
      <%Iterator itr = thisOrg.getPhoneNumberList().iterator();%>
      <%if (!itr.hasNext()) {%>&nbsp;<%}%>
      <%while (itr.hasNext()) {
        PhoneNumber phoneNumber = (PhoneNumber)itr.next(); %>
        <%= phoneNumber.getPhoneNumber()%> (<%=phoneNumber.getTypeName()%>)<br />
      <%}%>
		</dhv:evaluate>
		<dhv:evaluate if="<%=(thisOrg.getPrimaryContact() != null)%>">
      <%Iterator itr = thisOrg.getPrimaryContact().getPhoneNumberList().iterator();%>
      <%if (!itr.hasNext()) {%>&nbsp;<%}%>
      <%while (itr.hasNext()) {
        PhoneNumber phoneNumber = (PhoneNumber)itr.next(); %>
        <%= phoneNumber.getPhoneNumber()%> (<%=phoneNumber.getTypeName()%>)<br />
      <%}%>
		</dhv:evaluate>
		</td>
    </dhv:include>
    <dhv:include name="organization.addresses" none="true">
		<td valign="center" nowrap>
		<dhv:evaluate if="<%=(thisOrg.getPrimaryContact() == null)%>">
	     <% if ( thisOrg.getPrimaryAddress() != null) { %>	
           <% if ( (!"".equals(thisOrg.getPrimaryAddress().getCounty()))) { %>
             <%= toHtml(thisOrg.getPrimaryAddress().getCounty()) %>
           <%} else {%>
             &nbsp;
           <%}%>
         <%} else {%>
           &nbsp;
         <%}%>
		</dhv:evaluate>     
		<dhv:evaluate if="<%=(thisOrg.getPrimaryContact() != null)%>">
		<% if ( thisOrg.getPrimaryContact().getPrimaryAddress() != null) { %>	
          <% if ( (!"".equals(thisOrg.getPrimaryContact().getPrimaryAddress().getCounty()))) { %>
            <%= toHtml(thisOrg.getPrimaryContact().getPrimaryAddress().getCounty()) %>  
          <%} else {%>
            &nbsp;
          <%}%>  
        <%} else {%>
          &nbsp;
        <%}%>  
		</dhv:evaluate>
		</td>
    </dhv:include>

  </tr>
<%}%>
<%} else {%>
  <tr class="containerBody">
    <td colspan="<%= SearchOrgListInfo.getSearchOptionValue("searchcodeOrgSiteId").equals(String.valueOf(Constants.INVALID_SITE))?columnCount+1:columnCount %>">
      <dhv:label name="accounts.search.notFound">No accounts found with the specified search parameters.</dhv:label><br />
      <a href="Accounts.do?command=SearchForm"><dhv:label name="accounts.accounts_list.ModifySearch">Modify Search</dhv:label></a>.
    </td>
  </tr>
<%}%>
</table>
<br />
<dhv:pagedListControl object="SearchOrgListInfo" tdClass="row1"/>

