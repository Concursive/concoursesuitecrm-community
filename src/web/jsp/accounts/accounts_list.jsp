<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*" %>
<jsp:useBean id="OrgList" class="org.aspcfs.modules.accounts.base.OrganizationList" scope="request"/>
<jsp:useBean id="SearchOrgListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
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
Search Results
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
<center><%= SearchOrgListInfo.getAlphabeticalPageLinks() %></center>
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="SearchOrgListInfo"/>
<% int columnCount = 0; %>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th width="8" <% ++columnCount; %>>
      <strong>Action</strong>
    </th>
    <th nowrap <% ++columnCount; %>>
      <strong><a href="Accounts.do?command=Search&column=o.name"><dhv:label name="organization.name">Account Name</dhv:label></a></strong>
      <%= SearchOrgListInfo.getSortIcon("o.name") %>
    </th>
    <dhv:include name="organization.phoneNumbers" none="true">
    <th nowrap <% ++columnCount; %>>
        <strong>Phone</strong>
		</th>
    </dhv:include>
    <dhv:include name="organization.phoneNumbers" none="true">
    <th nowrap <% ++columnCount; %>>
        <strong>Fax</strong>
		</th>
    </dhv:include>
    <dhv:include name="organization.emailAddresses" none="true">
    <th nowrap <% ++columnCount; %>>
        <strong>Email</strong>
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
  <tr>
    <td width="8" valign="center" nowrap class="row<%= rowid %>">
      <% int status = -1;%>
      <dhv:permission name="accounts-accounts-edit"><% status = thisOrg.getEnabled() ? 1 : 0; %></dhv:permission>
      <%-- Use the unique id for opening the menu, and toggling the graphics --%>
       <a href="javascript:displayMenu('select<%= i %>','menuAccount', '<%= thisOrg.getOrgId() %>', '<%= status %>');"
       onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuAccount');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
    </td>
		<td class="row<%= rowid %>">
      <a href="Accounts.do?command=Details&orgId=<%=thisOrg.getOrgId()%>"><%= toHtml(thisOrg.getName()) %></a>
		</td>
    <dhv:include name="organization.phoneNumbers" none="true">
		<td valign="center" class="row<%= rowid %>" nowrap>
		<dhv:evaluate exp="<%=(thisOrg.getPrimaryContact() == null)%>">
        <%= toHtml(thisOrg.getPhoneNumber("Main")) %>
		</dhv:evaluate>    
		<dhv:evaluate exp="<%=(thisOrg.getPrimaryContact() != null)%>">
        <%= toHtml(thisOrg.getPrimaryContact().getPhoneNumber("Business")) %>
		</dhv:evaluate>
		</td>
    </dhv:include>
    <dhv:include name="organization.phoneNumbers" none="true">
		<td valign="center" class="row<%= rowid %>" nowrap>
        <%= toHtml(thisOrg.getPhoneNumber("Fax")) %>
		</td>
    </dhv:include>
    <dhv:include name="organization.emailAddresses" none="true">
		<td valign="center" class="row<%= rowid %>" nowrap>
		<dhv:evaluate exp="<%=(thisOrg.getPrimaryContact() == null)%>">
      <% if ( (thisOrg.getEmailAddress("Primary")).length() > 0 ) { %>
        <a href="mailto:<%= toHtml(thisOrg.getEmailAddress("Primary")) %>"><%= toHtml(thisOrg.getEmailAddress("Primary")) %></a>
      <%} else {%>
        &nbsp;
      <%}%>
		</dhv:evaluate>    
		<dhv:evaluate exp="<%=(thisOrg.getPrimaryContact() != null)%>">
      <% if ( (thisOrg.getPrimaryContact().getEmailAddress("Business")).length() > 0 ) { %>
        <a href="mailto:<%= toHtml(thisOrg.getPrimaryContact().getEmailAddress("Business")) %>"><%= toHtml(thisOrg.getPrimaryContact().getEmailAddress("Business")) %></a>
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
    <td colspan="<%= columnCount %>">
      <dhv:label name="accounts.search.notFound">No accounts found with the specified search parameters.</dhv:label><br />
      <a href="Accounts.do?command=SearchForm">Modify Search</a>.
    </td>
  </tr>
<%}%>
</table>
<br />
<dhv:pagedListControl object="SearchOrgListInfo" tdClass="row1"/>

