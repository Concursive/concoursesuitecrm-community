<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="ContactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="ContactTypeList" class="org.aspcfs.modules.contacts.base.ContactTypeList" scope="request"/>
<jsp:useBean id="ExternalContactsInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<a href="ExternalContacts.do">General Contacts</a> > 
View Contacts<br>
<hr color="#BFBFBB" noshade>
<dhv:permission name="contacts-external_contacts-add">
  <a href="ExternalContacts.do?command=Prepare">Add a Contact</a>
</dhv:permission>
<dhv:permission name="contacts-external_contacts-add" none="true">
  <br>
</dhv:permission>
<center><%= ExternalContactsInfo.getAlphabeticalPageLinks() %></center>
<table width="100%" border="0">
  <tr>
    <form name="listView" method="post" action="ExternalContacts.do?command=ListContacts">
    <td>
      <select size="1" name="listView" onChange="javascript:document.forms[0].submit();">
        <option <%= ExternalContactsInfo.getOptionValue("my") %>>My Contacts </option>
        <option <%= ExternalContactsInfo.getOptionValue("all") %>>All Contacts</option>
        <option <%= ExternalContactsInfo.getOptionValue("archived") %>>Archived Contacts</option>
        <% if (!(ExternalContactsInfo.getSavedCriteria().isEmpty())) { %>
          <option <%= ExternalContactsInfo.getOptionValue("search") %>>Search Results</option>
        <%}%>
      </select>
			<% ContactTypeList.setJsEvent("onChange=\"javascript:document.forms[0].submit();\""); %>
			<%= ContactTypeList.getHtmlSelect("listFilter1", ExternalContactsInfo.getFilterKey("listFilter1")) %>
    </td>
    <td>
      <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="ExternalContactsInfo"/>
    </td>
    </form>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <dhv:permission name="contacts-external_contacts-edit,contacts-external_contacts-delete">
    <td valign="center">
      <strong>Action</strong>
    </td>
    </dhv:permission>
    <td nowrap>
      <strong><a href="ExternalContacts.do?command=ListContacts&column=c.namelast">Name</a></strong>
      <%= ExternalContactsInfo.getSortIcon("c.namelast") %>
    </td>
    <td nowrap>
      <strong><a href="ExternalContacts.do?command=ListContacts&column=c.company">Company</a></strong>
      <%= ExternalContactsInfo.getSortIcon("c.company") %>
    </td>
    <td>
      <strong>Phone: Business</strong>
    </td>
    <td>
      <strong>Phone: Mobile</strong>
    </td>
    <dhv:evaluate exp="<%= "all".equals(ExternalContactsInfo.getListView()) %>">
      <td>
        <strong>Owner</strong>
      </td>
    </dhv:evaluate>
  </tr>
<%    
	Iterator i = ContactList.iterator();
	if (i.hasNext()) {
	int rowid = 0;
		while (i.hasNext()) {
			rowid = (rowid != 1?1:2);
      Contact thisContact = (Contact)i.next();
%>      
      <tr>
        <dhv:permission name="contacts-external_contacts-edit,contacts-external_contacts-delete">
          <td width="8" class="row<%= rowid %>" nowrap>
            <%if(thisContact.getEnabled()) {%>
             <dhv:permission name="contacts-external_contacts-edit"><a href="ExternalContacts.do?command=ModifyContact&id=<%= thisContact.getId()%>&return=list">Edit</a></dhv:permission><dhv:permission name="contacts-external_contacts-edit,contacts-external_contacts-delete" all="true">|</dhv:permission><dhv:permission name="contacts-external_contacts-delete"><a href="javascript:popURLReturn('ExternalContacts.do?command=ConfirmDelete&id=<%=thisContact.getId()%>&popup=true','ExternalContacts.do?command=ListContacts', 'Delete_contact','330','200','yes','no');">Del</a></dhv:permission>
            <%}else{%>
              &nbsp;
            <%}%>
          </td>
        </dhv:permission>
        <td class="row<%= rowid %>" nowrap>
          <a href="ExternalContacts.do?command=ContactDetails&id=<%= thisContact.getId() %>"><%= toHtml(thisContact.getNameLastFirst()) %></a>
          <%= thisContact.getEmailAddressTag("Business", "<img border=0 src=\"images/email.gif\" alt=\"Send email\" align=\"absmiddle\">", "") %>
          <dhv:permission name="accounts-view,accounts-accounts-view"><%= ((thisContact.getOrgId() > 0 && thisContact.getOrgEnabled())?"<a href=\"Accounts.do?command=Details&orgId=" + thisContact.getOrgId() + "\">[Account]</a>":"")%></dhv:permission>
        </td>
        <td class="row<%= rowid %>">
          <%= toHtml(thisContact.getCompany()) %><dhv:evaluate exp="<%= (!(thisContact.getOrgEnabled()) && thisContact.getOrgId() > 0)%>">&nbsp;<font color="red">*</font></dhv:evaluate>
        </td>
        <td class="row<%= rowid %>" nowrap>
          <%= toHtml(thisContact.getPhoneNumber("Business")) %>
        </td>
        <td class="row<%= rowid %>" nowrap>
          <%= toHtml(thisContact.getPhoneNumber("Mobile")) %>
        </td>
        <dhv:evaluate exp="<%= "all".equals(ExternalContactsInfo.getListView()) %>">
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
<dhv:pagedListControl object="ExternalContactsInfo" tdClass="row1"/>
