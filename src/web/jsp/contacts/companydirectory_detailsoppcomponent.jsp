<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="OppComponentDetails" class="org.aspcfs.modules.pipeline.base.OpportunityComponent" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<form name="componentDetails" action="ExternalContactsOpps.do?command=ModifyComponent&id=<%=OppComponentDetails.getId()%>" method="post">
<a href="ExternalContacts.do">Contacts &amp; Resources</a> > 
<a href="ExternalContacts.do?command=ListContacts">View Contacts</a> >
<a href="ExternalContacts.do?command=ContactDetails&id=<%=ContactDetails.getId()%>">Contact Details</a> >
<a href="ExternalContactsOpps.do?command=ViewOpps&contactId=<%=ContactDetails.getId()%>">Opportunities</a> >
<a href="ExternalContactsOpps.do?command=DetailsOpp&oppId=<%=OppComponentDetails.getOppId()%>&contactId=<%=ContactDetails.getId()%>">Opportunity Details</a> >
Component Details
<br>
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
    
<input type=hidden name="oppId" value="<%= OppComponentDetails.getOppId() %>">
<input type=hidden name="id" value="<%= OppComponentDetails.getId() %>">
<input type=hidden name="contactId" value="<%= ContactDetails.getId() %>">

<dhv:permission name="contacts-external_contacts-opportunities-edit"><input type="button" value="Modify" onClick="javascript:this.form.action='ExternalContactsOppComponents.do?command=ModifyComponent&id=<%=OppComponentDetails.getId()%>&contactId=<%=ContactDetails.getId()%>';submit();"></dhv:permission>
<dhv:permission name="contacts-external_contacts-opportunities-delete"><input type="button" value="Delete" onClick="javascript:popURLReturn('ExternalContactsOppComponents.do?command=ConfirmComponentDelete&contactId=<%=ContactDetails.getId()%>&oppId=<%=OppComponentDetails.getId()%>','ExternalContactsOpps.do?command=DetailsOpp&contactId=<%=ContactDetails.getId()%>', 'Delete_opp','320','200','yes','no')"></dhv:permission>
<dhv:permission name="contacts-external_contacts-opportunities-edit,contacts-external_contacts-opportunities-delete"></dhv:permission>
<br>&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  
  <tr class="title">
    <td colspan=2 valign=center align=left>
      <strong><%= toHtml(OppComponentDetails.getDescription()) %></strong>
    </td>
  </tr>  
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Owner
    </td>
    <td valign=center width=100%>
      <%= OppComponentDetails.getOwnerName() %>
      <dhv:evaluate exp="<%=!(OppComponentDetails.getHasEnabledOwnerAccount())%>"><font color="red">*</font></dhv:evaluate>
    </td>
  </tr>
  
  <dhv:evaluate exp="<%= hasText(OppComponentDetails.getTypes().valuesAsString()) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Opportunity Type(s)
    </td>
    <td>  
      <%= toHtml(OppComponentDetails.getTypes().valuesAsString()) %>
     </td>
  </tr>
  </dhv:evaluate>  
  
  <dhv:evaluate exp="<%= hasText(OppComponentDetails.getNotes()) %>">
  <tr class="containerBody">
    <td valign="top" nowrap class="formLabel">Additional Notes</td>
    <td valign="top"><%= toHtml(OppComponentDetails.getNotes()) %></td>
  </tr>
  </dhv:evaluate>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Prob. of Close
    </td>
    <td valign=center width=100%>
      <%= OppComponentDetails.getCloseProbValue() %>%
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Est. Close Date
    </td>
    <td>
      <%= OppComponentDetails.getCloseDateString() %>&nbsp;
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Low Estimate
    </td>
    <td>
      $<%= OppComponentDetails.getLowCurrency() %>&nbsp;
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Best Guess
    </td>
    <td>
      $<%= OppComponentDetails.getGuessCurrency() %>&nbsp;
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      High Estimate
    </td>
    <td>
      $<%= OppComponentDetails.getHighCurrency() %>&nbsp;
    </td>
  </tr>  
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Est. Term (months)
    </td>
    <td>
      <%= OppComponentDetails.getTerms() %>
    </td>
  </tr>  
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Current Stage
    </td>
    <td>
      <%= toHtml(OppComponentDetails.getStageName()) %>&nbsp;
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Current Stage Date
    </td>
    <td>
      <%= toHtml(OppComponentDetails.getStageDateString()) %>&nbsp;
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Est. Commission
    </td>
    <td>
      <%= OppComponentDetails.getCommissionValue() %>%
    </td>
  </tr>
<dhv:evaluate exp="<%= hasText(OppComponentDetails.getAlertText()) %>">
   <tr class="containerBody">
    <td nowrap class="formLabel">
      Alert Description
    </td>
    <td valign=center colspan=1>
       <%= toHtml(OppComponentDetails.getAlertText()) %>
    </td>
  </tr>
</dhv:evaluate>

<dhv:evaluate exp="<%= (OppComponentDetails.getAlertDate() != null) %>">
   <tr class="containerBody">
    <td nowrap class="formLabel">
      Alert Date
    </td>
    <td valign=center colspan=1>
       <%= OppComponentDetails.getAlertDateStringLongYear() %>
    </td>
  </tr>
</dhv:evaluate>  
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Entered
    </td>
    <td>
      <%= OppComponentDetails.getEnteredByName() %>&nbsp;-&nbsp;<%= OppComponentDetails.getEnteredString() %>
    </td>
  </tr>
  
      <tr class="containerBody">
    <td nowrap class="formLabel">
      Modified
    </td>
    <td>
      <%= OppComponentDetails.getModifiedByName() %>&nbsp;-&nbsp;<%= OppComponentDetails.getModifiedString() %>
    </td>
  </tr>  

</table>  
<dhv:permission name="contacts-external_contacts-opportunities-edit,contacts-external_contacts-opportunities-delete"><br></dhv:permission>
<dhv:permission name="contacts-external_contacts-opportunities-edit"><input type="button" value="Modify" onClick="javascript:this.form.action='ExternalContactsOppComponents.do?command=ModifyComponent&oppId=<%=OppComponentDetails.getId()%>&contactId=<%=ContactDetails.getId()%>';submit();"></dhv:permission>
<dhv:permission name="contacts-external_contacts-opportunities-delete"><input type="button" value="Delete" onClick="javascript:popURLReturn('ExternalContactsOppComponents.do?command=ConfirmComponentDelete&contactId=<%=ContactDetails.getId()%>&id=<%=OppComponentDetails.getId()%>','ExternalContactsOpps.do?command=DetailsOpp&contactId=<%=ContactDetails.getId()%>', 'Delete_opp','320','200','yes','no')"></dhv:permission>
<dhv:permission name="contacts-external_contacts-opportunities-edit,contacts-external_contacts-opportunities-delete"></dhv:permission></td>
</td></tr>
</table>
</form>
