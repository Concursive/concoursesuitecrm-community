<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.pipeline.base.OpportunityHeader,com.zeroio.iteam.base.*" %>
<jsp:useBean id="contactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="opportunityHeaderList" class="org.aspcfs.modules.pipeline.base.OpportunityHeaderList" scope="request"/>
<jsp:useBean id="ExternalOppsPagedListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<a href="ExternalContacts.do">General Contacts</a> > 
<a href="ExternalContacts.do?command=ListContacts">View Contacts</a> >
<a href="ExternalContacts.do?command=ContactDetails&id=<%= contactDetails.getId() %>">Contact Details</a> >
Opportunities<br>
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(contactDetails.getNameFull()) %></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "id=" + contactDetails.getId(); %>      
      <dhv:container name="contacts" selected="opportunities" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">
<dhv:permission name="contacts-external_contacts-opportunities-add"><a href="ExternalContactsOpps.do?command=AddOpp&contactId=<%= contactDetails.getId() %>">Add an Opportunity</a></dhv:permission>
<center><%= ExternalOppsPagedListInfo.getAlphabeticalPageLinks() %></center>
<table width="100%" border="0">
  <tr>
    <form name="listView" method="post" action="ExternalContactsOpps.do?command=ViewOpps&contactId=<%= contactDetails.getId() %>">
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

<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <dhv:permission name="contacts-external_contacts-opportunities-edit,contacts-external_contacts-opportunities-delete">
    <td valign=center align=left>
      <strong>Action</strong>
    </td>
    </dhv:permission>
    <td valign=center align=left>
      <strong><a href="ExternalContactsOpps.do?command=ViewOpps&contactId=<%= contactDetails.getId() %>&column=x.description">Opportunity Name</a></strong>
      <%= ExternalOppsPagedListInfo.getSortIcon("x.description") %>
    </td>
    <td valign=center align=left>
      <strong>Best Guess Total</strong>
    </td>
    <td valign=center align=left>
      <strong><a href="ExternalContactsOpps.do?command=ViewOpps&contactId=<%= contactDetails.getId() %>&column=x.modified">Last Modified</a></strong>
      <%= ExternalOppsPagedListInfo.getSortIcon("x.modified") %>
    </td>
  </tr>
<%
	Iterator j = opportunityHeaderList.iterator();
  FileItem thisFile = new FileItem();
	if ( j.hasNext() ) {
		int rowid = 0;
	    while (j.hasNext()) {
        if (rowid != 1) {
          rowid = 1;
        } else {
          rowid = 2;
        }
		OpportunityHeader oppHeader = (OpportunityHeader)j.next();
%>      
    <tr class="containerBody">
      <dhv:permission name="contacts-external_contacts-opportunities-edit,contacts-external_contacts-opportunities-delete">
      <td width=8 valign=center nowrap class="row<%= rowid %>">
      <dhv:permission name="contacts-external_contacts-opportunities-edit"><a href="ExternalContactsOpps.do?command=ModifyOpp&headerId=<%= oppHeader.getId() %>&contactId=<%= oppHeader.getContactLink() %>&return=list">Edit</a></dhv:permission><dhv:permission name="contacts-external_contacts-opportunities-edit,contacts-external_contacts-opportunities-delete" all="true">|</dhv:permission><dhv:permission name="contacts-external_contacts-opportunities-delete"><a href="javascript:popURLReturn('ExternalContactsOpps.do?command=ConfirmDelete&contactId=<%= contactDetails.getId() %>&headerId=<%= oppHeader.getId() %>&popup=true','ExternalContactsOpps.do?command=ViewOpps&contactId=<%= contactDetails.getId() %>', 'Delete_opp','320','200','yes','no');">Del</a></dhv:permission>
      </td>
      </dhv:permission>
      <td width=40% valign=center class="row<%= rowid %>">
        <a href="ExternalContactsOpps.do?command=DetailsOpp&headerId=<%= oppHeader.getId() %>&contactId=<%= contactDetails.getId() %>">
        <%= toHtml(oppHeader.getDescription()) %></a>
        (<%= oppHeader.getComponentCount() %>)
<dhv:evaluate if="<%= oppHeader.hasFiles() %>">
        <%= thisFile.getImageTag() %>
</dhv:evaluate>
      </td>  
      <td width="115" valign=center class="row<%= rowid %>">
        $<%= toHtml(oppHeader.getTotalValueCurrency()) %>
      </td>      
      <td valign=center class="row<%= rowid %>">
        <%= toHtml(oppHeader.getModifiedString()) %>
      </td>       
    </tr>
<%
    }
	} else {
%>
    <tr class="containerBody">
      <td colspan="6" valign=center>
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

