<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.pipeline.base.OpportunityHeader,com.zeroio.iteam.base.*" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="opportunityHeaderList" class="org.aspcfs.modules.pipeline.base.OpportunityHeaderList" scope="request"/>
<jsp:useBean id="ExternalOppsPagedListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<dhv:evaluate exp="<%= !isPopup(request) %>">
<a href="ExternalContacts.do">General Contacts</a> > 
<a href="ExternalContacts.do?command=ListContacts">View Contacts</a> >
<a href="ExternalContacts.do?command=ContactDetails&id=<%= ContactDetails.getId() %>">Contact Details</a> >
Opportunities<br>
<hr color="#BFBFBB" noshade>
</dhv:evaluate>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <%@ include file="contact_details_header_include.jsp" %>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "id=" + ContactDetails.getId(); 
          String param2 = addLinkParams(request, "popup|popupType|actionId"); %>
      <dhv:container name="contacts" selected="opportunities" param="<%= param1 %>" appendToUrl="<%= param2 %>"/>
    </td>
  </tr>
  <tr>
    <td class="containerBack">
<dhv:permission name="contacts-external_contacts-opportunities-add"><a href="ExternalContactsOpps.do?command=Prepare&contactId=<%= ContactDetails.getId() %>&actionSource=ExternalContactsOpps<%= addLinkParams(request, "popup|popupType|actionId") %>">Add an Opportunity</a></dhv:permission>
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

<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <dhv:permission name="contacts-external_contacts-opportunities-edit,contacts-external_contacts-opportunities-delete">
    <td>
      <strong>Action</strong>
    </td>
    </dhv:permission>
    <td nowrap>
      <strong><a href="ExternalContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %>&column=x.description<%= addLinkParams(request, "popup|popupType|actionId") %>">Opportunity Name</a></strong>
      <%= ExternalOppsPagedListInfo.getSortIcon("x.description") %>
    </td>
    <td nowrap>
      <strong>Best Guess Total</strong>
    </td>
    <td nowrap>
      <strong><a href="ExternalContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %>&column=x.modified<%= addLinkParams(request, "popup|popupType|actionId") %>">Last Modified</a></strong>
      <%= ExternalOppsPagedListInfo.getSortIcon("x.modified") %>
    </td>
  </tr>
<%
	Iterator j = opportunityHeaderList.iterator();
  FileItem thisFile = new FileItem();
	if ( j.hasNext() ) {
		int rowid = 0;
	    while (j.hasNext()) {
        rowid = (rowid != 1?1:2);
        OpportunityHeader oppHeader = (OpportunityHeader)j.next();
%>      
    <tr class="containerBody">
      <dhv:permission name="contacts-external_contacts-opportunities-edit,contacts-external_contacts-opportunities-delete">
      <td width="8" valign="top" align="center" class="row<%= rowid %>" nowrap>
        <dhv:permission name="contacts-external_contacts-opportunities-edit"><a href="ExternalContactsOpps.do?command=ModifyOpp&headerId=<%= oppHeader.getId() %>&contactId=<%= oppHeader.getContactLink() %>&return=list<%= addLinkParams(request, "popup|popupType|actionId") %>">Edit</a></dhv:permission><dhv:permission name="contacts-external_contacts-opportunities-edit,contacts-external_contacts-opportunities-delete" all="true">|</dhv:permission><dhv:permission name="contacts-external_contacts-opportunities-delete"><a href="javascript:popURLReturn('ExternalContactsOpps.do?command=ConfirmDelete&contactId=<%= ContactDetails.getId() %>&headerId=<%= oppHeader.getId() %>&popup=true<%= addLinkParams(request, "popupType|actionId") %>','ExternalContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %>', 'Delete_opp','320','200','yes','no');">Del</a></dhv:permission>
      </td>
      </dhv:permission>
      <td width="100%" valign="top" class="row<%= rowid %>">
        <a href="ExternalContactsOpps.do?command=DetailsOpp&headerId=<%= oppHeader.getId() %>&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>">
        <%= toHtml(oppHeader.getDescription()) %></a>
        (<%= oppHeader.getComponentCount() %>)
<dhv:evaluate if="<%= oppHeader.hasFiles() %>">
        <%= thisFile.getImageTag() %>
</dhv:evaluate>
      </td>  
      <td valign="top" align="right" class="row<%= rowid %>" nowrap>
        $<%= toHtml(oppHeader.getTotalValueCurrency()) %>
      </td>      
      <td valign="top" align="center" class="row<%= rowid %>" nowrap>
        <%= toHtml(oppHeader.getModifiedString()) %>
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

