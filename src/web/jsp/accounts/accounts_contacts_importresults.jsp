<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="ImportResults" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="ImportDetails" class="org.aspcfs.modules.base.Import" scope="request"/>
<jsp:useBean id="AccountContactsImportResultsInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="accounts_contacts_importresults_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
  <td>
    <a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> >
    <a href="AccountContactsImports.do?command=View">View Imports</a> >
    <a href="AccountContactsImports.do?command=Details&importId=<%= ImportDetails.getId() %>">Import Details</a> >
    View Results
  </td>
</tr>
</table>
<%-- End Trails --%>
<center><%= AccountContactsImportResultsInfo.getAlphabeticalPageLinks() %></center>
<table width="100%" border="0">
  <tr>
    <td>
      <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="AccountContactsImportResultsInfo"/>
    </td>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th valign="center">
      <strong>Action</strong>
    </th>
    <th nowrap>
      <strong><a href="AccountContactsImports.do?command=ViewResults&importId=<%= ImportDetails.getId() %>&column=c.namelast">Name</a></strong>
      <%= AccountContactsImportResultsInfo.getSortIcon("c.namelast") %>
    </th>
    <th nowrap>
      <strong><a href="AccountContactsImports.do?command=ViewResults&importId=<%= ImportDetails.getId() %>&column=c.company">Company</a></strong>
      <%= AccountContactsImportResultsInfo.getSortIcon("c.company") %>
    </th>
    <th>
      <strong>Phone: Business</strong>
    </th>
    <th>
      <strong>Phone: Mobile</strong>
    </th>
    <dhv:evaluate if="<%= !"my".equals(AccountContactsImportResultsInfo.getListView()) && !"".equals(AccountContactsImportResultsInfo.getListView()) %>">
      <th>
        <strong>Owner</strong>
      </th>
    </dhv:evaluate>
  </tr>
<%    
	Iterator i = ImportResults.iterator();
	if (i.hasNext()) {
	int rowid = 0;
  int count  =0;
		while (i.hasNext()) {
      count++;
      rowid = (rowid != 1 ? 1 : 2);
      Contact thisContact = (Contact)i.next();
%>    
      <tr>
        <td width="8" class="row<%= rowid %>" nowrap>
         <%-- check if user has edit or delete based on the type of contact --%>
        <%
          int hasDeletePermission = 0;
          if(!thisContact.isApproved()){
            if(thisContact.getOrgId() < 0){ %>
            <dhv:permission name="accounts-accounts-contacts-delete">
              <% hasDeletePermission = 1; %>
            </dhv:permission>
          <% }else{ %>
              <dhv:permission name="accounts-accounts-contacts-delete">
                <% hasDeletePermission = 1; %>
              </dhv:permission>
          <% } %>
        <% } %>
        <%-- Use the unique id for opening the menu, and toggling the graphics --%>
         <a href="javascript:displayMenu('menuContact','<%= thisContact.getId() %>','<%= hasDeletePermission %>');" onMouseOver="over(0, <%= count %>)" onmouseout="out(0, <%= count %>)"><img src="images/select.gif" name="select<%= count %>" align="absmiddle" border="0"></a>
        </td>
        <td class="row<%= rowid %>" <%= "".equals(toString(thisContact.getNameLastFirst())) ? "width=\"10\"" : ""  %> nowrap>
          <% if(!"".equals(toString(thisContact.getNameLastFirst()))){ %>
          <a href="AccountContactsImports.do?command=ContactDetails&contactId=<%= thisContact.getId() %>"><%= toHtml(thisContact.getNameLastFirst()) %></a>
          <% }else{ %>
            &nbsp;
          <%}%>
        </td>
        <td class="row<%= rowid %>">
          <% if(!"".equals(toString(thisContact.getNameLastFirst()))){ %>
            <%= toHtml(thisContact.getOrgName()) %>
          <%}else{%>
            <a href="Contacts.do?command=ContactDetails&id=<%= thisContact.getId() %>"><%= toHtml(thisContact.getOrgName()) %></a>
            <%= thisContact.getEmailAddressTag("Business", "<img border=0 src=\"images/icons/stock_mail-16.gif\" alt=\"Send email\" align=\"absmiddle\">", "") %>
          <%}%>
        </td>
        <td class="row<%= rowid %>" nowrap>
          <%= toHtml(thisContact.getPhoneNumber("Business")) %>
        </td>
        <td class="row<%= rowid %>" nowrap>
          <%= toHtml(thisContact.getPhoneNumber("Mobile")) %>
        </td>
        <dhv:evaluate if="<%= !"my".equals(AccountContactsImportResultsInfo.getListView()) && !"".equals(AccountContactsImportResultsInfo.getListView()) %>">
          <td class="row<%= rowid %>" nowrap>
            <dhv:username id="<%= thisContact.getOwner() %>"/>
          </td>
        </dhv:evaluate>
      </tr>
<%
    }
  } else {%>  
  <tr>
    <td class="containerBody" colspan="5">
      No contacts found.
    </td>
  </tr>
<%}%>
</table>
<br>
<dhv:pagedListControl object="AccountContactsImportResultsInfo" tdClass="row1"/>
