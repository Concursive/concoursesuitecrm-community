<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="ContactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="ContactTypeList" class="org.aspcfs.modules.contacts.base.ContactTypeList" scope="request"/>
<jsp:useBean id="SearchContactsInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="companydirectory_listcontacts_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="ExternalContacts.do">Contacts</a> > 
Search Results
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:permission name="contacts-external_contacts-add">
  <a href="ExternalContacts.do?command=Prepare">Add a Contact</a>
</dhv:permission>
<dhv:permission name="contacts-external_contacts-add" none="true">
  <br>
</dhv:permission>
<center><%= SearchContactsInfo.getAlphabeticalPageLinks() %></center>
<table width="100%" border="0">
  <tr>
    <td>
      <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="SearchContactsInfo"/>
    </td>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th valign="center">
      <strong>Action</strong>
    </th>
    <th nowrap>
      <strong><a href="ExternalContacts.do?command=SearchContacts&column=c.namelast">Name</a></strong>
      <%= SearchContactsInfo.getSortIcon("c.namelast") %>
    </th>
    <th nowrap>
      <strong><a href="ExternalContacts.do?command=SearchContacts&column=c.company">Company</a></strong>
      <%= SearchContactsInfo.getSortIcon("c.company") %>
    </th>
    <th>
      <strong>Phone: Business</strong>
    </th>
    <th>
      <strong>Phone: Mobile</strong>
    </th>
    <dhv:evaluate if="<%= !"my".equals(SearchContactsInfo.getListView()) && !"".equals(SearchContactsInfo.getListView()) %>">
      <th>
        <strong>Owner</strong>
      </th>
    </dhv:evaluate>
  </tr>
<%    
	Iterator i = ContactList.iterator();
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
          int hasEditPermission = 0;
          int hasDeletePermission = 0;
          int hasClonePermission = 0;
          if(thisContact.getOrgId() < 0){ %>
          <dhv:permission name="contacts-external_contacts-edit">
            <% hasEditPermission = 1; %>
          </dhv:permission>
          <dhv:permission name="contacts-external_contacts-delete">
            <% hasDeletePermission = 1; %>
          </dhv:permission>
          <dhv:permission name="contacts-external_contacts-add">
            <% hasClonePermission = 1; %>
          </dhv:permission>
        <% }else{ %>
          <dhv:permission name="contacts-external_contacts-edit,accounts-accounts-contacts-edit"  all="true">
            <% hasEditPermission = 1; %>
          </dhv:permission>
          <dhv:permission name="contacts-external_contacts-delete,accounts-accounts-contacts-delete" all="true">
            <% hasDeletePermission = 1; %>
          </dhv:permission>
          <dhv:permission name="contacts-external_contacts-add,accounts-accounts-contacts-add" all="true">
            <% hasClonePermission = 1; %>
          </dhv:permission>
        <% } %>
        
        <%-- Use the unique id for opening the menu, and toggling the graphics --%>
         <a href="javascript:displayMenu('select<%= count %>','menuContact','<%= thisContact.getId() %>','<%= hasEditPermission %>', '<%= hasDeletePermission %>', '<%= hasClonePermission %>');" onMouseOver="over(0, <%= count %>)" onmouseout="out(0, <%= count %>); hideMenu('menuContact');"><img src="images/select.gif" name="select<%= count %>" id="select<%= count %>" align="absmiddle" border="0"></a>
        </td>
        <td class="row<%= rowid %>" <%= "".equals(toString(thisContact.getNameLastFirst())) ? "width=\"10\"" : ""  %> nowrap>
          <% if(!"".equals(toString(thisContact.getNameLastFirst()))){ %>
          <a href="ExternalContacts.do?command=ContactDetails&id=<%= thisContact.getId() %>"><%= toHtml(thisContact.getNameLastFirst()) %></a>
          <%= thisContact.getEmailAddressTag("Business", "<img border=0 src=\"images/icons/stock_mail-16.gif\" alt=\"Send email\" align=\"absmiddle\">", "") %>
          <dhv:permission name="accounts-view,accounts-accounts-view"><%= ((thisContact.getOrgId() > 0 )?"<a href=\"Accounts.do?command=Details&orgId=" + thisContact.getOrgId() + "\">[Account]</a>":"")%></dhv:permission>
          <% }else{ %>
            &nbsp;
          <%}%>
        </td>
        <td class="row<%= rowid %>">
          <% if(!"".equals(toString(thisContact.getNameLastFirst()))){ %>
            <%= toHtml(thisContact.getOrgName()) %>
          <%}else{%>
            <a href="ExternalContacts.do?command=ContactDetails&id=<%= thisContact.getId() %>"><%= toHtml(thisContact.getOrgName()) %></a>
            <%= thisContact.getEmailAddressTag("Business", "<img border=0 src=\"images/icons/stock_mail-16.gif\" alt=\"Send email\" align=\"absmiddle\">", "") %>
          <%}%>
        </td>
        <td class="row<%= rowid %>" nowrap>
          <%= toHtml(thisContact.getPhoneNumber("Business")) %>
        </td>
        <td class="row<%= rowid %>" nowrap>
          <%= toHtml(thisContact.getPhoneNumber("Mobile")) %>
        </td>
        <dhv:evaluate if="<%= !"my".equals(SearchContactsInfo.getListView()) && !"".equals(SearchContactsInfo.getListView()) %>">
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
      No contacts found with the specified search parameters.<br />
      <a href="ExternalContacts.do?command=SearchContactsForm">Modify Search</a>.
    </td>
  </tr>
<%}%>
</table>
<br>
<dhv:pagedListControl object="SearchContactsInfo" tdClass="row1"/>
