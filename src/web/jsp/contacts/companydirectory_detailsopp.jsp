<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.pipeline.base.OpportunityComponent,com.zeroio.iteam.base.*" %>
<jsp:useBean id="contactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="headerDetails" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<jsp:useBean id="componentList" class="org.aspcfs.modules.pipeline.base.OpportunityComponentList" scope="request"/>
<jsp:useBean id="ComponentListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<form name="oppdet" action="ExternalContactsOpps.do?command=ModifyOpp&id=<%= headerDetails.getId() %>&orgId=<%= headerDetails.getAccountLink() %>&contactId=<%= headerDetails.getContactLink() %>" method="post">
<a href="ExternalContacts.do">General Contacts</a> > 
<a href="ExternalContacts.do?command=ListContacts">View Contacts</a> >
<a href="ExternalContacts.do?command=ContactDetails&id=<%= contactDetails.getId() %>">Contact Details</a> >
<a href="ExternalContactsOpps.do?command=ViewOpps&contactId=<%= contactDetails.getId() %>">Opportunities</a> >
Opportunity Details<br>
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
<dhv:permission name="contacts-external_contacts-opportunities-edit"><input type="button" value="Rename" onClick="javascript:this.form.action='ExternalContactsOpps.do?command=ModifyOpp&headerId=<%= headerDetails.getId() %>&contactId=<%= headerDetails.getContactLink() %>';submit();"></dhv:permission>
<dhv:permission name="contacts-external_contacts-opportunities-delete"><input type="button" value="Delete" onClick="javascript:popURLReturn('ExternalContactsOpps.do?command=ConfirmDelete&contactId=<%= contactDetails.getId() %>&headerId=<%= headerDetails.getId() %>&popup=true','ExternalContactsOpps.do?command=ViewOpps&contactId=<%= contactDetails.getId() %>', 'Delete_opp','320','200','yes','no')"></dhv:permission>
<dhv:permission name="contacts-external_contacts-opportunities-edit,contacts-external_contacts-opportunities-delete"></dhv:permission>
<dhv:permission name="contacts-external_contacts-opportunities-add"><input type="button" value="Add Component" onClick="javascript:this.form.action='ExternalContactsOppComponents.do?command=AddOppComponent&headerId=<%= headerDetails.getId() %>&contactId=<%= headerDetails.getContactLink() %>';submit();"></dhv:permission>
<br>&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan=2 valign=center align=left>
      <% FileItem thisFile = new FileItem(); %>
      <strong><%= toHtml(headerDetails.getDescription()) %></strong>
      <dhv:evaluate if="<%= headerDetails.hasFiles() %>"><%= thisFile.getImageTag() %></dhv:evaluate>
    </td>
  </tr>  
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Entered
    </td>
    <td>
      <%= headerDetails.getEnteredByName() %>&nbsp;-&nbsp;<%= headerDetails.getEnteredString() %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Modified
    </td>
    <td>
      <%= headerDetails.getModifiedByName() %>&nbsp;-&nbsp;<%= headerDetails.getModifiedString() %>
    </td>
  </tr>    
</table>
<br>
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="ComponentListInfo"/>
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <dhv:permission name="contacts-external_contacts-opportunities-edit,contacts-external_contacts-opportunities-delete">
    <td nowrap>
      <strong>Action</strong>
    </td>
    </dhv:permission>
    <td nowrap>
      <strong><a href="ExternalContactsOpps.do?command=DetailsOpp&headerId=<%= headerDetails.getId() %>&contactId=<%= contactDetails.getId() %>&column=oc.description">Component</a></strong>
      <%= ComponentListInfo.getSortIcon("oc.description") %>
    </td>
    <td nowrap>
      <strong><a href="ExternalContactsOpps.do?command=DetailsOpp&headerId=<%= headerDetails.getId() %>&contactId=<%= contactDetails.getId() %>&column=oc.closed">Status</a></strong>
      <%= ComponentListInfo.getSortIcon("oc.closed") %>
    </td>
    <td nowrap>
      <strong><a href="ExternalContactsOpps.do?command=DetailsOpp&headerId=<%= headerDetails.getId() %>&contactId=<%= contactDetails.getId() %>&column=oc.guessvalue">Guess Amount</a></strong>
      <%= ComponentListInfo.getSortIcon("oc.guessvalue") %>
    </td>
    <td nowrap>
      <strong><a href="ExternalContactsOpps.do?command=DetailsOpp&headerId=<%= headerDetails.getId() %>&contactId=<%= contactDetails.getId() %>&column=oc.closedate">Close Date</a></strong>
      <%= ComponentListInfo.getSortIcon("oc.closedate") %>
    </td>
    <td nowrap>
      <strong><a href="ExternalContactsOpps.do?command=DetailsOpp&headerId=<%= headerDetails.getId() %>&contactId=<%= contactDetails.getId() %>&column=stagename">Current Stage</a></strong>
      <%= ComponentListInfo.getSortIcon("stagename") %>
    </td>  
  </tr>
<%
	Iterator j = componentList.iterator();
	if ( j.hasNext() ) {
		int rowid = 0;
	    while (j.hasNext()) {
        rowid = (rowid != 1?1:2);
        OpportunityComponent oppComponent = (OpportunityComponent)j.next();
%>      
  <tr class="containerBody">
    <dhv:permission name="contacts-external_contacts-opportunities-edit,contacts-external_contacts-opportunities-delete">
    <td width="8" valign="top" nowrap class="row<%= rowid %>">
      <dhv:permission name="contacts-external_contacts-opportunities-edit"><a href="ExternalContactsOppComponents.do?command=ModifyComponent&id=<%= oppComponent.getId() %>&contactId=<%= contactDetails.getId() %>&return=list">Edit</a></dhv:permission><dhv:permission name="contacts-external_contacts-opportunities-edit,contacts-external_contacts-opportunities-delete" all="true">|</dhv:permission><dhv:permission name="contacts-external_contacts-opportunities-delete"><a href="javascript:popURLReturn('ExternalContactsOppComponents.do?command=ConfirmComponentDelete&contactId=<%= contactDetails.getId() %>&id=<%= oppComponent.getId() %>&popup=true','ExternalContactsOpps.do?command=ViewOpps&contactId=<%= contactDetails.getId() %>', 'Delete_opp','320','200','yes','no');">Del</a></dhv:permission>
    </td>
    </dhv:permission>
    <td width="100%" valign="top" class="row<%= rowid %>">
      <a href="ExternalContactsOppComponents.do?command=DetailsComponent&contactId=<%= contactDetails.getId() %>&id=<%= oppComponent.getId() %>">
      <%= toHtml(oppComponent.getDescription()) %></a>
    </td>
    <td valign="top" align="center" nowrap class="row<%= rowid %>">
      <%= oppComponent.getClosed() != null ? "<font color=\"red\">closed</font>" : "<font color=\"green\">open</font>" %>
    </td>
    <td valign="top" align="right" nowrap class="row<%= rowid %>">
      $<%= oppComponent.getGuessCurrency() %>
    </td>
    <td valign="top" align="center" nowrap class="row<%= rowid %>">
      <%= toHtml(oppComponent.getCloseDateString()) %>
    </td>
    <td valign="top" align="center" nowrap class="row<%= rowid %>">
      <%= toHtml(oppComponent.getStageName()) %>
    </td>		
  </tr>
<%}%>
<%} else {%>
  <tr class="containerBody">
    <td colspan="6">
      No opportunity components found.
    </td>
  </tr>
<%}%>
</table>
<br>
<dhv:pagedListControl object="ComponentListInfo"/>
</td>
</tr>
</table>
</form>
