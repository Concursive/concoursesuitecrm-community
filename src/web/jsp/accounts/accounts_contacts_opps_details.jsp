<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.pipeline.base.OpportunityComponent,com.zeroio.iteam.base.*" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="OpportunityHeader" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<jsp:useBean id="ComponentList" class="org.aspcfs.modules.pipeline.base.OpportunityComponentList" scope="request"/>
<jsp:useBean id="ComponentListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="AccountContactComponentListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>

<dhv:evaluate if="<%= !isPopup(request) %>">
<a href="Accounts.do">Account Management</a> > 
<a href="Accounts.do?command=View">View Accounts</a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Account Details</a> >
<a href="Contacts.do?command=View&orgId=<%=OrgDetails.getOrgId()%>">Contacts</a> >
<a href="Contacts.do?command=Details&id=<%=ContactDetails.getId()%>&orgId=<%=OrgDetails.getOrgId()%>">Contact Details</a> >
<a href="AccountContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %>">Opportunities</a> >
Opportunity Details<br>
<hr color="#BFBFBB" noshade>
</dhv:evaluate>
<%-- include the account header --%>
<%@ include file="accounts_details_header_include.jsp" %>
<%-- load the accounts menu --%> 
<dhv:container name="accounts" selected="contacts" param="<%= "orgId=" + OrgDetails.getOrgId() %>" style="tabs"/>
<%-- actual opportunity add form --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
    <% String param1 = "id=" + ContactDetails.getId(); 
    %>
        <strong><%= ContactDetails.getNameLastFirst() %>:</strong>
        [ <dhv:container name="accountscontacts" selected="opportunities" param="<%= param1 %>"/> ]
      <br>
      <br>
      
      <%-- Begin container content --%>
      <img src="images/icons/stock_form-currency-field-16.gif" border="0" align="absmiddle">
      <strong><%= toHtml(OpportunityHeader.getDescription()) %></strong>
      <% FileItem thisFile = new FileItem(); %>
      <dhv:evaluate if="<%= OpportunityHeader.hasFiles() %>">
        <%= thisFile.getImageTag() %>
      </dhv:evaluate>
      <br>
      <dhv:permission name="accounts-accounts-contacts-opportunities-add">
        <br>
        <a href="AccountContactsOppComponents.do?command=Prepare&headerId=<%= OpportunityHeader.getId() %>&contactId=<%= OpportunityHeader.getContactLink() %><%= addLinkParams(request, "popup|popupType|actionId") %>">Add a Component</a><br>
      </dhv:permission>
      <%= addHiddenParams(request, "popup|popupType|actionId") %>
      <input type="hidden" name="actionSource" value="AccountContactsOppComponents">
      <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="AccountContactComponentListInfo"/>
      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
        <tr>
          <dhv:permission name="accounts-accounts-contacts-opportunities-edit,accounts-accounts-contacts-opportunities-delete">
          <th nowrap>
            <strong>Action</strong>
          </th>
          </dhv:permission>
          <th nowrap>
            <strong><a href="AccountContactsOpps.do?command=DetailsOpp&headerId=<%= OpportunityHeader.getId() %>&contactId=<%= ContactDetails.getId() %>&column=oc.description<%= addLinkParams(request, "popup|popupType|actionId") %>">Component</a></strong>
            <%= AccountContactComponentListInfo.getSortIcon("oc.description") %>
          </th>
          <th nowrap>
            <strong><a href="AccountContactsOpps.do?command=DetailsOpp&headerId=<%= OpportunityHeader.getId() %>&contactId=<%= ContactDetails.getId() %>&column=oc.closed<%= addLinkParams(request, "popup|popupType|actionId") %>">Status</a></strong>
            <%= AccountContactComponentListInfo.getSortIcon("oc.closed") %>
          </th>
          <th nowrap>
            <strong><a href="AccountContactsOpps.do?command=DetailsOpp&headerId=<%= OpportunityHeader.getId() %>&contactId=<%= ContactDetails.getId() %>&column=oc.guessvalue<%= addLinkParams(request, "popup|popupType|actionId") %>">Guess Amount</a></strong>
            <%= AccountContactComponentListInfo.getSortIcon("oc.guessvalue") %>
          </th>
          <th nowrap>
            <strong><a href="AccountContactsOpps.do?command=DetailsOpp&headerId=<%= OpportunityHeader.getId() %>&contactId=<%= ContactDetails.getId() %>&column=oc.closedate<%= addLinkParams(request, "popup|popupType|actionId") %>">Close Date</a></strong>
            <%= AccountContactComponentListInfo.getSortIcon("oc.closedate") %>
          </th>
          <th nowrap>
            <strong><a href="AccountContactsOpps.do?command=DetailsOpp&headerId=<%= OpportunityHeader.getId() %>&contactId=<%= ContactDetails.getId() %>&column=stagename<%= addLinkParams(request, "popup|popupType|actionId") %>">Current Stage</a></strong>
            <%= AccountContactComponentListInfo.getSortIcon("stagename") %>
          </th>
        </tr>
      <%
        Iterator j = ComponentList.iterator();
        if ( j.hasNext() ) {
          int rowid = 0;
            while (j.hasNext()) {
              rowid = (rowid != 1?1:2);
              OpportunityComponent oppComponent = (OpportunityComponent)j.next();
      %>      
        <tr class="containerBody">
          <dhv:permission name="accounts-accounts-contacts-opportunities-edit,accounts-accounts-contacts-opportunities-delete">
          <td width="8" valign="top" nowrap class="row<%= rowid %>">
            <dhv:permission name="accounts-accounts-contacts-opportunities-edit"><a href="AccountContactsOppComponents.do?command=ModifyComponent&headerId=<%= oppComponent.getHeaderId() %>&id=<%= oppComponent.getId() %>&contactId=<%= ContactDetails.getId() %>&return=list&actionSource=AccountContactsOppComponents<%= addLinkParams(request, "popup|popupType|actionId") %>">Edit</a></dhv:permission><dhv:permission name="accounts-accounts-contacts-opportunities-edit,accounts-accounts-contacts-opportunities-delete" all="true">|</dhv:permission><dhv:permission name="accounts-accounts-contacts-opportunities-delete"><a href="javascript:popURLReturn('AccountContactsOppComponents.do?command=ConfirmComponentDelete&contactId=<%= ContactDetails.getId() %>&id=<%= oppComponent.getId() %>&popup=true<%= addLinkParams(request, "popupType|actionId") %>','AccountContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %>', 'Delete_opp','320','200','yes','no');">Del</a></dhv:permission>
          </td>
          </dhv:permission>
          <td width="100%" valign="top" class="row<%= rowid %>">
            <a href="AccountContactsOppComponents.do?command=DetailsComponent&contactId=<%= ContactDetails.getId() %>&id=<%= oppComponent.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>">
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
      <dhv:pagedListControl object="AccountContactComponentListInfo"/>
      &nbsp;<br>
      <dhv:permission name="accounts-accounts-contacts-opportunities-edit"><input type="button" value="Rename Opportunity" onClick="javascript:window.location.href='AccountContactsOpps.do?command=ModifyOpp&headerId=<%= OpportunityHeader.getId() %>&contactId=<%= OpportunityHeader.getContactLink() %><%= addLinkParams(request, "popup|popupType|actionId") %>';"></dhv:permission>
      <dhv:permission name="accounts-accounts-contacts-opportunities-delete"><input type="button" value="Delete Opportunity" onClick="javascript:popURLReturn('AccountContactsOpps.do?command=ConfirmDelete&contactId=<%= ContactDetails.getId() %>&headerId=<%= OpportunityHeader.getId() %>&popup=true<%= addLinkParams(request, "popupType|actionId") %>','AccountContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %>', 'Delete_opp','320','200','yes','no')"></dhv:permission>
   </td>
 </tr>
</table>

