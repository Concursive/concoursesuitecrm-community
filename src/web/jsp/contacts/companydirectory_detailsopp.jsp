<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="ContactDetails" class="com.darkhorseventures.cfsbase.Contact" scope="request"/>
<jsp:useBean id="HeaderDetails" class="com.darkhorseventures.cfsbase.OpportunityHeader" scope="request"/>
<jsp:useBean id="ComponentList" class="com.darkhorseventures.cfsbase.OpportunityComponentList" scope="request"/>
<jsp:useBean id="ComponentListInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<%@ include file="initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<form name="oppdet" action="ExternalContactsOpps.do?command=ModifyOpp&id=<%=HeaderDetails.getId()%>&orgId=<%= HeaderDetails.getAccountLink() %>&contactId=<%= HeaderDetails.getContactLink() %>" method="post">
<a href="ExternalContacts.do">Contacts &amp; Resources</a> > 
<a href="ExternalContacts.do?command=ListContacts">View Contacts</a> >
<a href="ExternalContacts.do?command=ContactDetails&id=<%=ContactDetails.getId()%>">Contact Details</a> >
<a href="ExternalContactsOpps.do?command=ViewOpps&contactId=<%=ContactDetails.getId()%>">Opportunities</a> >
Opportunity Details<br>
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(ContactDetails.getNameFull()) %></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "id=" + ContactDetails.getId(); %>      
      <dhv:container name="contacts" selected="opportunities" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">

<dhv:permission name="contacts-external_contacts-opportunities-edit"><input type="button" value="Modify" onClick="javascript:this.form.action='ExternalContactsOpps.do?command=ModifyOpp&oppId=<%=HeaderDetails.getId()%>&contactId=<%= HeaderDetails.getContactLink() %>';submit();"></dhv:permission>
<dhv:permission name="contacts-external_contacts-opportunities-delete"><input type="button" value="Delete" onClick="javascript:popURLReturn('ExternalContactsOpps.do?command=ConfirmDelete&contactId=<%=ContactDetails.getId()%>&id=<%=HeaderDetails.getId()%>','ExternalContactsOpps.do?command=ViewOpps&contactId=<%=ContactDetails.getId()%>', 'Delete_opp','320','200','yes','no')"></dhv:permission>
<dhv:permission name="contacts-external_contacts-opportunities-edit,contacts-external_contacts-opportunities-delete"></dhv:permission>
<dhv:permission name="contacts-external_contacts-opportunities-add"><input type="button" value="Add Component" onClick="javascript:this.form.action='ExternalContactsOppComponents.do?command=AddOppComponent&id=<%=HeaderDetails.getId()%>&contactId=<%= HeaderDetails.getContactLink() %>';submit();"></dhv:permission>
<br>&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  
  <tr class="title">
    <td colspan=2 valign=center align=left>
      <% FileItem thisFile = new FileItem(); %>
      <strong><%= toHtml(HeaderDetails.getDescription()) %></strong>
        <% if (HeaderDetails.hasFiles()) {%>
        <%= thisFile.getImageTag() %>
        <%}%>      
    </td>
  </tr>  
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Entered
    </td>
    <td>
      <%= HeaderDetails.getEnteredByName() %>&nbsp;-&nbsp;<%= HeaderDetails.getEnteredString() %>
    </td>
  </tr>
  
      <tr class="containerBody">
    <td nowrap class="formLabel">
      Modified
    </td>
    <td>
      <%= HeaderDetails.getModifiedByName() %>&nbsp;-&nbsp;<%= HeaderDetails.getModifiedString() %>
    </td>
  </tr>    
 
</table>
<br>
<dhv:pagedListStatus showExpandLink="false" title="<%= showError(request, "actionError") %>" object="ComponentListInfo"/>
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <dhv:permission name="contacts-external_contacts-opportunities-edit,contacts-external_contacts-opportunities-delete">
    <td valign=center align=left>
      <strong>Action</strong>
    </td>
    </dhv:permission>
    <td valign=center align=left>
	<strong><a href="ExternalContactsOpps.do?command=DetailsOpp&oppId=<%=HeaderDetails.getId()%>&contactId=<%=ContactDetails.getId()%>&column=description">Component</a></strong>
	<%= ComponentListInfo.getSortIcon("description") %>
    </td>
    <td valign=center align=left>
	<strong><a href="ExternalContactsOpps.do?command=DetailsOpp&oppId=<%=HeaderDetails.getId()%>&contactId=<%=ContactDetails.getId()%>&column=guessvalue">Guess Amount</a></strong>
	<%= ComponentListInfo.getSortIcon("guessvalue") %>
    </td>
    <td valign=center align=left>
      	<strong><a href="ExternalContactsOpps.do?command=DetailsOpp&oppId=<%=HeaderDetails.getId()%>&contactId=<%=ContactDetails.getId()%>&column=closedate">Close Date</a></strong>
	<%= ComponentListInfo.getSortIcon("closedate") %>
    </td>
    <td valign=center align=left>
      	<strong><a href="ExternalContactsOpps.do?command=DetailsOpp&oppId=<%=HeaderDetails.getId()%>&contactId=<%=ContactDetails.getId()%>&column=stage">Current Stage</a></strong>
	<%= ComponentListInfo.getSortIcon("stage") %>
    </td>  
  </tr>
  
<%
	Iterator j = ComponentList.iterator();
	
	if ( j.hasNext() ) {
		int rowid = 0;
	    while (j.hasNext()) {
		
        if (rowid != 1) {
          rowid = 1;
        } else {
          rowid = 2;
        }
		
		OpportunityComponent thisOpp = (OpportunityComponent)j.next();
%>      
  <tr class="containerBody">
    <dhv:permission name="contacts-external_contacts-opportunities-edit,contacts-external_contacts-opportunities-delete">
    <td width=8 valign=center nowrap class="row<%= rowid %>">
    <dhv:permission name="contacts-external_contacts-opportunities-edit"><a href="ExternalContactsOppComponents.do?command=ModifyComponent&id=<%= thisOpp.getId() %>&contactId=<%=ContactDetails.getId()%>&return=list">Edit</a></dhv:permission><dhv:permission name="contacts-external_contacts-opportunities-edit,contacts-external_contacts-opportunities-delete" all="true">|</dhv:permission><dhv:permission name="contacts-external_contacts-opportunities-delete"><a href="javascript:popURLReturn('ExternalContactsOppComponents.do?command=ConfirmComponentDelete&contactId=<%=ContactDetails.getId()%>&oppId=<%=thisOpp.getId()%>','ExternalContactsOpps.do?command=ViewOpps&contactId=<%=ContactDetails.getId()%>', 'Delete_opp','320','200','yes','no');">Del</a></dhv:permission>
    </td>
    </dhv:permission>
    <td width=100% valign=center class="row<%= rowid %>">
      <a href="ExternalContactsOppComponents.do?command=DetailsComponent&contactId=<%=ContactDetails.getId()%>&id=<%=thisOpp.getId()%>">
      <%= toHtml(thisOpp.getDescription()) %></a>
    </td>
    <td width=125 valign=center nowrap class="row<%= rowid %>">
      $<%= thisOpp.getGuessCurrency() %>
    </td>
    <td width=125 valign=center nowrap class="row<%= rowid %>">
      <%= toHtml(thisOpp.getCloseDateString()) %>
    </td>
    <td width=125 valign=center nowrap class="row<%= rowid %>">
      <%= toHtml(thisOpp.getStageName()) %>
    </td>		
  </tr>
<%}%>
<%} else {%>
  <tr class="containerBody">
    <td colspan=5 valign=center>
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
