<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.contacts.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="OpportunityHeader" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<jsp:useBean id="OppComponentDetails" class="org.aspcfs.modules.pipeline.base.OpportunityComponent" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails">
<tr>
<td>
<a href="Accounts.do">Accounts</a> > 
<a href="Accounts.do?command=View">View Accounts</a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Account Details</a> >
<a href="Contacts.do?command=View&orgId=<%=OrgDetails.getOrgId()%>">Contacts</a> >
<a href="Contacts.do?command=Details&id=<%=ContactDetails.getId()%>&orgId=<%=OrgDetails.getOrgId()%>">Contact Details</a> >
<a href="AccountContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %>">Opportunities</a> >
<a href="AccountContactsOpps.do?command=DetailsOpp&headerId=<%= OpportunityHeader.getId() %>&contactId=<%= ContactDetails.getId() %>">Opportunity Details</a> >
Component Details
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>

<form name="componentDetails" action="AccountContactsOpps.do?command=ModifyComponent&id=<%= OppComponentDetails.getId() %>" method="post">

<%-- include the account header --%>
<%@ include file="accounts_details_header_include.jsp" %>
<%-- load the accounts menu --%> 
<dhv:container name="accounts" selected="contacts" param="<%= "orgId=" + OrgDetails.getOrgId() %>" style="tabs"/>
<%-- actual opportunity add form --%>
<form name="opportunityForm" action="AccountContactsOpps.do?command=Save&contactId=<%= ContactDetails.getId() %>&auto-populate=true" onSubmit="return doCheck(this);" method="post">
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
      <dhv:permission name="accounts-accounts-contacts-opportunities-edit,accounts-accounts-contacts-opportunities-delete">
      <br>
      <br>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-contacts-opportunities-edit"><input type="button" value="Modify" onClick="javascript:this.form.action='AccountContactsOppComponents.do?command=ModifyComponent&id=<%= OppComponentDetails.getId() %>&headerId=<%= OppComponentDetails.getHeaderId() %>&contactId=<%= ContactDetails.getId() %>';submit();"></dhv:permission>
      <dhv:permission name="accounts-accounts-contacts-opportunities-delete"><input type="button" value="Delete" onClick="javascript:popURLReturn('AccountContactsOppComponents.do?command=ConfirmComponentDelete&contactId=<%= ContactDetails.getId() %>&id=<%= OppComponentDetails.getId() %>&popup=true<%= addLinkParams(request, "popupType|actionId") %>','AccountContactsOpps.do?command=DetailsOpp&contactId=<%= ContactDetails.getId() %>', 'Delete_opp','320','200','yes','no')"></dhv:permission>
      <dhv:permission name="accounts-accounts-contacts-opportunities-edit,accounts-accounts-contacts-opportunities-delete">
      <br>&nbsp;
      </dhv:permission>
      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
        <tr>
          <th colspan="2">
            <strong><%= toHtml(OppComponentDetails.getDescription()) %></strong>
          </th>
        </tr>  
        <tr class="containerBody">
          <td class="formLabel" nowrap>
            Owner
          </td>
          <td valign="center">
            <dhv:username id="<%= OppComponentDetails.getOwner() %>"/>
            <dhv:evaluate exp="<%= !(OppComponentDetails.getHasEnabledOwnerAccount()) %>"><font color="red">*</font></dhv:evaluate>
          </td>
        </tr>
        <dhv:evaluate exp="<%= hasText(OppComponentDetails.getTypes().valuesAsString()) %>">
        <tr class="containerBody">
          <td class="formLabel" nowrap>
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
          <td class="formLabel" nowrap>
            Prob. of Close
          </td>
          <td>
            <%= OppComponentDetails.getCloseProbValue() %>%
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel" nowrap>
            Est. Close Date
          </td>
          <td>
            <dhv:tz timestamp="<%= OppComponentDetails.getCloseDate() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>" default="&nbsp;"/>
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel" nowrap>
            Low Estimate
          </td>
          <td>
            $<%= OppComponentDetails.getLowCurrency() %>&nbsp;
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel" nowrap>
            Best Guess
          </td>
          <td>
            $<%= OppComponentDetails.getGuessCurrency() %>&nbsp;
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel" nowrap>
            High Estimate
          </td>
          <td>
            $<%= OppComponentDetails.getHighCurrency() %>&nbsp;
          </td>
        </tr>  
        <tr class="containerBody">
          <td class="formLabel" nowrap>
            Est. Term
          </td>
          <td>
            <%= OppComponentDetails.getTerms() %> months
          </td>
        </tr>  
        <tr class="containerBody">
          <td class="formLabel" nowrap>
            Current Stage
          </td>
          <td>
            <%= toHtml(OppComponentDetails.getStageName()) %>&nbsp;
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel" nowrap>
            Current Stage Date
          </td>
          <td>
            <dhv:tz timestamp="<%= OppComponentDetails.getStageDate() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>" default="&nbsp;"/>
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel" nowrap>
            Est. Commission
          </td>
          <td>
            <%= OppComponentDetails.getCommissionValue() %>%
          </td>
        </tr>
      <dhv:evaluate exp="<%= hasText(OppComponentDetails.getAlertText()) %>">
         <tr class="containerBody">
          <td class="formLabel" nowrap>
            Alert Description
          </td>
          <td>
             <%= toHtml(OppComponentDetails.getAlertText()) %>
          </td>
        </tr>
      </dhv:evaluate>
      <dhv:evaluate exp="<%= OppComponentDetails.getAlertDate() != null %>">
        <tr class="containerBody">
          <td class="formLabel" nowrap>
            Alert Date
          </td>
          <td>
            <dhv:tz timestamp="<%= OppComponentDetails.getAlertDate() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>" default="&nbsp;"/>
          </td>
        </tr>
      </dhv:evaluate>  
        <tr class="containerBody">
          <td class="formLabel" nowrap>
            Entered
          </td>
          <td>
            <dhv:username id="<%= OppComponentDetails.getEnteredBy() %>"/>
            -
            <dhv:tz timestamp="<%= OppComponentDetails.getEntered() %>" dateFormat="<%= DateFormat.SHORT %>" timeFormat="<%= DateFormat.LONG %>"/>
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel" nowrap>
            Modified
          </td>
          <td>
            <dhv:username id="<%= OppComponentDetails.getModifiedBy() %>"/>
            -
            <dhv:tz timestamp="<%= OppComponentDetails.getModified() %>" dateFormat="<%= DateFormat.SHORT %>" timeFormat="<%= DateFormat.LONG %>"/>
          </td>
        </tr>
      </table>
      <dhv:permission name="accounts-accounts-contacts-opportunities-edit,accounts-accounts-contacts-opportunities-delete"><br></dhv:permission>
      <dhv:permission name="accounts-accounts-contacts-opportunities-edit"><input type="button" value="Modify" onClick="javascript:this.form.action='AccountContactsOppComponents.do?command=ModifyComponent&id=<%= OppComponentDetails.getId() %>&contactId=<%= ContactDetails.getId() %>';submit();"></dhv:permission>
      <dhv:permission name="accounts-accounts-contacts-opportunities-delete"><input type="button" value="Delete" onClick="javascript:popURLReturn('AccountContactsOppComponents.do?command=ConfirmComponentDelete&contactId=<%= ContactDetails.getId() %>&id=<%= OppComponentDetails.getId() %>&popup=true<%= addLinkParams(request, "popupType|actionId") %>','AccountContactsOpps.do?command=DetailsOpp&contactId=<%= ContactDetails.getId() %>', 'Delete_opp','320','200','yes','no')"></dhv:permission>
      <dhv:permission name="accounts-accounts-contacts-opportunities-edit,accounts-accounts-contacts-opportunities-delete"></dhv:permission>
      <%-- end container content --%>
    </td>
  </tr>
</table>
<input type="hidden" name="headerId" value="<%= OppComponentDetails.getHeaderId() %>">
<input type="hidden" name="id" value="<%= OppComponentDetails.getId() %>">
<input type="hidden" name="contactId" value="<%= ContactDetails.getId() %>">
<input type="hidden" name="actionSource" value="AccountContactsOppComponents">
<%= addHiddenParams(request, "popup|popupType|actionId") %>
</form>
