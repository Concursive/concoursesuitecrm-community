<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.pipline.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="LeadsComponentDetails" class="org.aspcfs.modules.pipeline.base.OpportunityComponent" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<form name="componentDetails" action="Leads.do?command=ModifyComponent&id=<%=LeadsComponentDetails.getId()%>" method="post">
<a href="Leads.do">Pipeline Management</a> > 
<a href="Leads.do?command=ViewOpp">View Opportunities</a> >
<a href="Leads.do?command=DetailsOpp&oppId=<%=LeadsComponentDetails.getOppId()%>">Opportunity Details</a> > 
Component Details<br>
<hr color="#BFBFBB" noshade>
<dhv:permission name="pipeline-opportunities-edit"><input type="button" value="Modify" onClick="javascript:this.form.action='LeadsComponents.do?command=ModifyComponent&id=<%=LeadsComponentDetails.getId()%>';submit();"></dhv:permission>
<dhv:permission name="pipeline-opportunities-delete"><input type="button" value="Delete" onClick="javascript:popURLReturn('LeadsComponents.do?command=ConfirmComponentDelete&oppId=<%=LeadsComponentDetails.getId()%>','Leads.do?command=DetailsOpp', 'Delete_opp','320','200','yes','no')"></dhv:permission>
<br>&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  
  <tr class="title">
    <td colspan=2 valign=center align=left>
      <strong><%= toHtml(LeadsComponentDetails.getDescription()) %></strong>
    </td>
  </tr>  
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Owner
    </td>
    <td valign=center width=100%>
      <%= LeadsComponentDetails.getOwnerName() %>
      <dhv:evaluate exp="<%=!(LeadsComponentDetails.getHasEnabledOwnerAccount())%>"><font color="red">*</font></dhv:evaluate>
    </td>
  </tr>
  
  <dhv:evaluate exp="<%= hasText(LeadsComponentDetails.getTypes().valuesAsString()) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Opportunity Type(s)
    </td>
    <td>  
      <%= toHtml(LeadsComponentDetails.getTypes().valuesAsString()) %>
     </td>
  </tr>
  </dhv:evaluate>  
  
  <dhv:evaluate exp="<%= hasText(LeadsComponentDetails.getNotes()) %>">
  <tr class="containerBody">
    <td valign="top" nowrap class="formLabel">Additional Notes</td>
    <td valign="top"><%= toHtml(LeadsComponentDetails.getNotes()) %></td>
  </tr>
  </dhv:evaluate>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Prob. of Close
    </td>
    <td valign=center width=100%>
      <%= LeadsComponentDetails.getCloseProbValue() %>%
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Est. Close Date
    </td>
    <td>
      <%= LeadsComponentDetails.getCloseDateString() %>&nbsp;
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Low Estimate
    </td>
    <td>
      $<%= LeadsComponentDetails.getLowCurrency() %>&nbsp;
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Best Guess
    </td>
    <td>
      $<%= LeadsComponentDetails.getGuessCurrency() %>&nbsp;
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      High Estimate
    </td>
    <td>
      $<%= LeadsComponentDetails.getHighCurrency() %>&nbsp;
    </td>
  </tr>  
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Est. Term (months)
    </td>
    <td>
      <%= LeadsComponentDetails.getTerms() %>
    </td>
  </tr>  
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Current Stage
    </td>
    <td>
      <%= toHtml(LeadsComponentDetails.getStageName()) %>&nbsp;
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Current Stage Date
    </td>
    <td>
      <%= toHtml(LeadsComponentDetails.getStageDateString()) %>&nbsp;
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Est. Commission
    </td>
    <td>
      <%= LeadsComponentDetails.getCommissionValue() %>%
    </td>
  </tr>
<dhv:evaluate exp="<%= hasText(LeadsComponentDetails.getAlertText()) %>">
   <tr class="containerBody">
    <td nowrap class="formLabel">
      Alert Description
    </td>
    <td valign=center colspan=1>
       <%= toHtml(LeadsComponentDetails.getAlertText()) %>
    </td>
  </tr>
</dhv:evaluate>

<dhv:evaluate exp="<%= (LeadsComponentDetails.getAlertDate() != null) %>">
   <tr class="containerBody">
    <td nowrap class="formLabel">
      Alert Date
    </td>
    <td valign=center colspan=1>
       <%= LeadsComponentDetails.getAlertDateStringLongYear() %>
    </td>
  </tr>
</dhv:evaluate>  
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Entered
    </td>
    <td>
      <%= LeadsComponentDetails.getEnteredByName() %>&nbsp;-&nbsp;<%= LeadsComponentDetails.getEnteredString() %>
    </td>
  </tr>
  
      <tr class="containerBody">
    <td nowrap class="formLabel">
      Modified
    </td>
    <td>
      <%= LeadsComponentDetails.getModifiedByName() %>&nbsp;-&nbsp;<%= LeadsComponentDetails.getModifiedString() %>
    </td>
  </tr>  
</table>
<dhv:permission name="pipeline-opportunities-edit,pipeline-opportunities-delete"><br></dhv:permission>
<dhv:permission name="pipeline-opportunities-edit"><input type="button" value="Modify" onClick="javascript:this.form.action='LeadsComponents.do?command=ModifyComponent&id=<%=LeadsComponentDetails.getId()%>';submit();"></dhv:permission>
<dhv:permission name="pipeline-opportunities-delete"><input type="button" value="Delete" onClick="javascript:popURLReturn('LeadsComponents.do?command=ConfirmComponentDelete&oppId=<%=LeadsComponentDetails.getId()%>','Leads.do?command=DetailsOpp', 'Delete_opp','320','200','yes','no')"></dhv:permission>
</form>
