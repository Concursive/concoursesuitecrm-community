<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="contactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="oppComponentDetails" class="org.aspcfs.modules.pipeline.base.OpportunityComponent" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<form name="componentDetails" action="ExternalContactsOpps.do?command=ModifyComponent&id=<%= oppComponentDetails.getId() %>" method="post">
<dhv:evaluate exp="<%= !isPopup(request) %>">
<a href="ExternalContacts.do">General Contacts</a> > 
<a href="ExternalContacts.do?command=ListContacts">View Contacts</a> >
<a href="ExternalContacts.do?command=ContactDetails&id=<%= contactDetails.getId() %>">Contact Details</a> >
<a href="ExternalContactsOpps.do?command=ViewOpps&contactId=<%= contactDetails.getId() %>">Opportunities</a> >
<a href="ExternalContactsOpps.do?command=DetailsOpp&headerId=<%= oppComponentDetails.getHeaderId() %>&contactId=<%= contactDetails.getId() %>">Opportunity Details</a> >
Component Details
<br>
<hr color="#BFBFBB" noshade>
</dhv:evaluate>

<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(contactDetails.getNameFull()) %></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
       <% String param1 = "id=" + contactDetails.getId(); 
          String param2 = addLinkParams(request, "popup|popupType|actionId"); %>
      <dhv:container name="contacts" selected="opportunities" param="<%= param1 %>" appendToUrl="<%= param2 %>"/>
    </td>
  </tr>
  <tr>
    <td class="containerBack">
<dhv:permission name="contacts-external_contacts-opportunities-edit"><input type="button" value="Modify" onClick="javascript:this.form.action='ExternalContactsOppComponents.do?command=ModifyComponent&id=<%= oppComponentDetails.getId() %>&headerId=<%= oppComponentDetails.getHeaderId() %>&contactId=<%= contactDetails.getId() %>';submit();"></dhv:permission>
<dhv:permission name="contacts-external_contacts-opportunities-delete"><input type="button" value="Delete" onClick="javascript:popURLReturn('ExternalContactsOppComponents.do?command=ConfirmComponentDelete&contactId=<%= contactDetails.getId() %>&id=<%= oppComponentDetails.getId() %>&popup=true<%= addLinkParams(request, "popupType|actionId") %>','ExternalContactsOpps.do?command=DetailsOpp&contactId=<%= contactDetails.getId() %>', 'Delete_opp','320','200','yes','no')"></dhv:permission>
<dhv:permission name="contacts-external_contacts-opportunities-edit,contacts-external_contacts-opportunities-delete"></dhv:permission>
<br>&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="2">
      <strong><%= toHtml(oppComponentDetails.getDescription()) %></strong>
    </td>
  </tr>  
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Owner
    </td>
    <td valign="center">
      <%= oppComponentDetails.getOwnerName() %>
      <dhv:evaluate exp="<%= !(oppComponentDetails.getHasEnabledOwnerAccount()) %>"><font color="red">*</font></dhv:evaluate>
    </td>
  </tr>
  <dhv:evaluate exp="<%= hasText(oppComponentDetails.getTypes().valuesAsString()) %>">
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Opportunity Type(s)
    </td>
    <td>  
      <%= toHtml(oppComponentDetails.getTypes().valuesAsString()) %>
     </td>
  </tr>
  </dhv:evaluate>  
  <dhv:evaluate exp="<%= hasText(oppComponentDetails.getNotes()) %>">
  <tr class="containerBody">
    <td valign="top" nowrap class="formLabel">Additional Notes</td>
    <td valign="top"><%= toHtml(oppComponentDetails.getNotes()) %></td>
  </tr>
  </dhv:evaluate>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Prob. of Close
    </td>
    <td>
      <%= oppComponentDetails.getCloseProbValue() %>%
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Est. Close Date
    </td>
    <td>
      <%= oppComponentDetails.getCloseDateString() %>&nbsp;
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Low Estimate
    </td>
    <td>
      $<%= oppComponentDetails.getLowCurrency() %>&nbsp;
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Best Guess
    </td>
    <td>
      $<%= oppComponentDetails.getGuessCurrency() %>&nbsp;
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      High Estimate
    </td>
    <td>
      $<%= oppComponentDetails.getHighCurrency() %>&nbsp;
    </td>
  </tr>  
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Est. Term
    </td>
    <td>
      <%= oppComponentDetails.getTerms() %> months
    </td>
  </tr>  
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Current Stage
    </td>
    <td>
      <%= toHtml(oppComponentDetails.getStageName()) %>&nbsp;
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Current Stage Date
    </td>
    <td>
      <%= toHtml(oppComponentDetails.getStageDateString()) %>&nbsp;
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Est. Commission
    </td>
    <td>
      <%= oppComponentDetails.getCommissionValue() %>%
    </td>
  </tr>
<dhv:evaluate exp="<%= hasText(oppComponentDetails.getAlertText()) %>">
   <tr class="containerBody">
    <td class="formLabel" nowrap>
      Alert Description
    </td>
    <td>
       <%= toHtml(oppComponentDetails.getAlertText()) %>
    </td>
  </tr>
</dhv:evaluate>
<dhv:evaluate exp="<%= oppComponentDetails.getAlertDate() != null %>">
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Alert Date
    </td>
    <td>
       <%= oppComponentDetails.getAlertDateStringLongYear() %>
    </td>
  </tr>
</dhv:evaluate>  
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Entered
    </td>
    <td>
      <%= oppComponentDetails.getEnteredByName() %>&nbsp;-&nbsp;<%= oppComponentDetails.getEnteredString() %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Modified
    </td>
    <td>
      <%= oppComponentDetails.getModifiedByName() %>&nbsp;-&nbsp;<%= oppComponentDetails.getModifiedString() %>
    </td>
  </tr>  
</table>  
<dhv:permission name="contacts-external_contacts-opportunities-edit,contacts-external_contacts-opportunities-delete"><br></dhv:permission>
<dhv:permission name="contacts-external_contacts-opportunities-edit"><input type="button" value="Modify" onClick="javascript:this.form.action='ExternalContactsOppComponents.do?command=ModifyComponent&id=<%= oppComponentDetails.getId() %>&contactId=<%= contactDetails.getId() %>';submit();"></dhv:permission>
<dhv:permission name="contacts-external_contacts-opportunities-delete"><input type="button" value="Delete" onClick="javascript:popURLReturn('ExternalContactsOppComponents.do?command=ConfirmComponentDelete&contactId=<%= contactDetails.getId() %>&id=<%= oppComponentDetails.getId() %>&popup=true<%= addLinkParams(request, "popupType|actionId") %>','ExternalContactsOpps.do?command=DetailsOpp&contactId=<%= contactDetails.getId() %>', 'Delete_opp','320','200','yes','no')"></dhv:permission>
<dhv:permission name="contacts-external_contacts-opportunities-edit,contacts-external_contacts-opportunities-delete"></dhv:permission></td>
</td></tr>
</table>
<input type="hidden" name="headerId" value="<%= oppComponentDetails.getHeaderId() %>">
<input type="hidden" name="id" value="<%= oppComponentDetails.getId() %>">
<input type="hidden" name="contactId" value="<%= contactDetails.getId() %>">
<input type="hidden" name="actionSource" value="ExternalContactsOppComponents">
<%= addHiddenParams(request, "popup|popupType|actionId") %>
</form>
