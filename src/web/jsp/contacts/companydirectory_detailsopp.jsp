<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="ContactDetails" class="com.darkhorseventures.cfsbase.Contact" scope="request"/>
<jsp:useBean id="OppDetails" class="com.darkhorseventures.cfsbase.Opportunity" scope="request"/>
<%@ include file="initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<form name="oppdet" action="ExternalContactsOpps.do?command=ModifyOpp&id=<%=OppDetails.getId()%>&orgId=<%= OppDetails.getAccountLink() %>&contactId=<%= OppDetails.getContactLink() %>" method="post">
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
<input type=hidden name="command" value="<%= OppDetails.getId() %>">

<dhv:permission name="contacts-external_contacts-opportunities-edit"><input type="button" value="Modify" onClick="javascript:this.form.action='ExternalContactsOpps.do?command=ModifyOpp&id=<%=OppDetails.getId()%>&orgId=<%= OppDetails.getAccountLink() %>&contactId=<%= OppDetails.getContactLink() %>';submit();"></dhv:permission>
<dhv:permission name="contacts-external_contacts-opportunities-delete"><input type="button" value="Delete" onClick="javascript:popURLReturn('ExternalContactsOpps.do?command=ConfirmDelete&contactId=<%=ContactDetails.getId()%>&id=<%=OppDetails.getId()%>','ExternalContactsOpps.do?command=ViewOpps&contactId=<%=ContactDetails.getId()%>', 'Delete_opp','320','200','yes','no')"></dhv:permission>
<dhv:permission name="contacts-external_contacts-opportunities-edit,contacts-external_contacts-opportunities-delete"><br>&nbsp;</dhv:permission>

<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan=2 valign=center align=left>
      <strong><%= toHtml(OppDetails.getDescription()) %></strong>
            <% if (OppDetails.hasFiles()) { %>
      <% FileItem thisFile = new FileItem(); %>
      <%= thisFile.getImageTag()%>
      <%}%>  
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Owner
    </td>
    <td valign=center width=100%>
      <%= OppDetails.getOwnerName() %>
      <dhv:evaluate exp="<%=!(OppDetails.getHasEnabledOwnerAccount())%>"><font color="red">*</font></dhv:evaluate>
    </td>
  </tr>
  
  <dhv:evaluate exp="<%= hasText(OppDetails.getTypes().valuesAsString()) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Opportunity Type(s)
    </td>
    <td>  
      <%= toHtml(OppDetails.getTypes().valuesAsString()) %>
     </td>
  </tr>
  </dhv:evaluate>    
  
  <dhv:evaluate exp="<%= hasText(OppDetails.getNotes()) %>">
  <tr class="containerBody">
    <td valign="top" nowrap class="formLabel">Additional Notes</td>
    <td valign="top"><%= toHtml(OppDetails.getNotes()) %></td>
  </tr>
  </dhv:evaluate>

  <tr class="containerBody">
    <td nowrap class="formLabel">
      Prob. of Close
    </td>
    <td valign=center width=100%>
      <%= OppDetails.getCloseProbValue() %>%
    </td>
  </tr>

  <tr class="containerBody">
    <td nowrap class="formLabel">
      Est. Close Date
    </td>
    <td>
      <%= OppDetails.getCloseDateString() %>&nbsp;
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Low Estimate
    </td>
    <td>
      $<%= OppDetails.getLowCurrency() %>&nbsp;
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Best Guess
    </td>
    <td>
      $<%= OppDetails.getGuessCurrency() %>&nbsp;
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      High Estimate
    </td>
    <td>
      $<%= OppDetails.getHighCurrency() %>&nbsp;
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Est. Term (months)
    </td>
    <td>
      <%= OppDetails.getTerms() %>
    </td>
  </tr>  
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Current Stage
    </td>
    <td>
      <%= toHtml(OppDetails.getStageName()) %>&nbsp;
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Current Stage Date
    </td>
    <td>
      <%= toHtml(OppDetails.getStageDateString()) %>&nbsp;
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Est. Commission
    </td>
    <td>
      <%= OppDetails.getCommissionValue() %>%
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Alert Date
    </td>
    <td>
      <%= toHtml(OppDetails.getAlertDateString()) %>&nbsp;
    </td>
  </tr>
  
    <tr class="containerBody">
    <td nowrap class="formLabel">
      Entered
    </td>
    <td>
      <%= OppDetails.getEnteredByName() %>&nbsp;-&nbsp;<%= OppDetails.getEnteredString() %>
    </td>
  </tr>
  
      <tr class="containerBody">
    <td nowrap class="formLabel">
      Modified
    </td>
    <td>
      <%= OppDetails.getModifiedByName() %>&nbsp;-&nbsp;<%= OppDetails.getModifiedString() %>
    </td>
  </tr>
  
</table>
<dhv:permission name="contacts-external_contacts-opportunities-edit,contacts-external_contacts-opportunities-delete"><br></dhv:permission>
<dhv:permission name="contacts-external_contacts-opportunities-edit"><input type="button" value="Modify" onClick="javascript:this.form.action='ExternalContactsOpps.do?command=ModifyOpp&id=<%=OppDetails.getId()%>&orgId=<%= OppDetails.getAccountLink() %>&contactId=<%= OppDetails.getContactLink() %>';submit();"></dhv:permission>
<dhv:permission name="contacts-external_contacts-opportunities-delete"><input type="button" value="Delete" onClick="javascript:popURLReturn('ExternalContactsOpps.do?command=ConfirmDelete&contactId=<%=ContactDetails.getId()%>&id=<%=OppDetails.getId()%>','ExternalContactsOpps.do?command=ViewOpps&contactId=<%=ContactDetails.getId()%>', 'Delete_opp','320','200','yes','no')"></dhv:permission>
</td>
</tr>
</table>
</form>
