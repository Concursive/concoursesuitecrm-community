<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="OppComponentDetails" class="org.aspcfs.modules.pipeline.base.OpportunityComponent" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<form name="componentDetails" action="Opportunities.do?command=ModifyComponent&id=<%=OppComponentDetails.getId()%>" method="post">
<a href="Accounts.do">Account Management</a> > 
<a href="Accounts.do?command=View">View Accounts</a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Account Details</a> >
<a href="Opportunities.do?command=View&orgId=<%=OrgDetails.getOrgId()%>">Opportunities</a> >
<a href="Opportunities.do?command=Details&oppId=<%=OppComponentDetails.getOppId()%>&orgId=<%=OrgDetails.getId()%>&reset=true">Opportunity Details</a> >
Component Details
<br>
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(OrgDetails.getName()) %></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "orgId=" + OrgDetails.getOrgId(); %>      
      <dhv:container name="accounts" selected="opportunities" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">
    
<input type=hidden name="oppId" value="<%= OppComponentDetails.getOppId() %>">
<input type=hidden name="id" value="<%= OppComponentDetails.getId() %>">
<input type=hidden name="orgId" value="<%= OrgDetails.getId() %>">

<dhv:permission name="accounts-accounts-opportunities-edit"><input type="button" value="Modify" onClick="javascript:this.form.action='OpportunitiesComponents.do?command=ModifyComponent&id=<%=OppComponentDetails.getId()%>&orgId=<%=OrgDetails.getId()%>';submit();"></dhv:permission>
<dhv:permission name="accounts-accounts-opportunities-delete"><input type="button" value="Delete" onClick="javascript:popURLReturn('OpportunitiesComponents.do?command=ConfirmComponentDelete&orgId=<%=OrgDetails.getId()%>&oppId=<%=OppComponentDetails.getId()%>&popup=true','Opportunities.do?command=DetailsOpp&orgId=<%=OrgDetails.getId()%>', 'Delete_opp','320','200','yes','no')"></dhv:permission>
<dhv:permission name="accounts-accounts-opportunities-edit,accounts-accounts-opportunities-delete"></dhv:permission>
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
<dhv:permission name="accounts-accounts-opportunities-edit,accounts-accounts-opportunities-delete"><br></dhv:permission>
<dhv:permission name="accounts-accounts-opportunities-edit"><input type="button" value="Modify" onClick="javascript:this.form.action='OpportunitiesComponents.do?command=ModifyComponent&id=<%=OppComponentDetails.getId()%>&orgId=<%=OrgDetails.getId()%>';submit();"></dhv:permission>
<dhv:permission name="accounts-accounts-opportunities-delete"><input type="button" value="Delete" onClick="javascript:popURLReturn('OpportunitiesComponents.do?command=ConfirmComponentDelete&orgId=<%=OrgDetails.getId()%>&oppId=<%=OppComponentDetails.getId()%>&popup=true','Opportunities.do?command=DetailsOpp&orgId=<%=OrgDetails.getId()%>', 'Delete_opp','320','200','yes','no')"></dhv:permission>
<dhv:permission name="accounts-accounts-opportunities-edit,accounts-accounts-opportunities-delete"></dhv:permission></td>
</td></tr>
</table>
</form>
