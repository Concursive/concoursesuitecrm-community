<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.pipeline.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="opportunityHeader" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<jsp:useBean id="LeadsComponentDetails" class="org.aspcfs.modules.pipeline.base.OpportunityComponent" scope="request"/>
<jsp:useBean id="PipelineViewpointInfo" class="org.aspcfs.utils.web.ViewpointInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<form name="componentDetails" action="Leads.do?command=ModifyComponent&id=<%= LeadsComponentDetails.getId() %>&return=details" method="post">
<%-- Trails --%>
<table class="trails">
<tr>
<td>
<a href="Leads.do">Pipeline</a> > 
<a href="Leads.do?command=ViewOpp">View Components</a> >
<a href="Leads.do?command=DetailsOpp&headerId=<%= LeadsComponentDetails.getHeaderId() %>&reset=true">Opportunity Details</a> > 
Component Details
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:evaluate exp="<%= PipelineViewpointInfo.isVpSelected(User.getUserId()) %>">
  <b>Viewpoint: </b><b class="highlight"><%= PipelineViewpointInfo.getVpUserName() %></b><br>
  &nbsp;<br>
</dhv:evaluate>
<%-- Begin container --%>
<%@ include file="leads_details_header_include.jsp" %>
<% String param1 = "id=" + opportunityHeader.getId(); %>      
<dhv:container name="opportunities" selected="details" param="<%= param1 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
<%-- Begin the container contents --%>
<dhv:permission name="pipeline-opportunities-edit"><input type="button" value="Modify" onClick="javascript:this.form.action='LeadsComponents.do?command=ModifyComponent&id=<%= LeadsComponentDetails.getId() %>&return=details';submit();"></dhv:permission>
<dhv:permission name="pipeline-opportunities-delete"><input type="button" value="Delete" onClick="javascript:popURLReturn('LeadsComponents.do?command=ConfirmComponentDelete&id=<%= LeadsComponentDetails.getId() %>&popup=true','Leads.do?command=DetailsOpp&headerId=<%= LeadsComponentDetails.getHeaderId() %>', 'Delete_opp','320','200','yes','no')"></dhv:permission>
<br>&nbsp;
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><%= toHtml(LeadsComponentDetails.getDescription()) %></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Owner
    </td>
    <td>
      <dhv:username id="<%= LeadsComponentDetails.getOwner() %>"/>
      <dhv:evaluate exp="<%= !(LeadsComponentDetails.getHasEnabledOwnerAccount()) %>"><font color="red">*</font></dhv:evaluate>
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
    <td><%= toHtml(LeadsComponentDetails.getNotes()) %></td>
  </tr>
</dhv:evaluate>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Prob. of Close
    </td>
    <td>
      <%= LeadsComponentDetails.getCloseProbValue() %>%
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Est. Close Date
    </td>
    <td>
    <dhv:tz timestamp="<%= LeadsComponentDetails.getCloseDate() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>" default="&nbsp;"/>
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
      Est. Term
    </td>
    <td>
      <%= LeadsComponentDetails.getTerms() %> months
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
      <dhv:tz timestamp="<%= LeadsComponentDetails.getStageDate() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>" default="&nbsp;"/>
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
    <td>
       <%= toHtml(LeadsComponentDetails.getAlertText()) %>
    </td>
  </tr>
</dhv:evaluate>
<dhv:evaluate exp="<%= (LeadsComponentDetails.getAlertDate() != null) %>">
   <tr class="containerBody">
    <td nowrap class="formLabel">
      Alert Date
    </td>
    <td>
      <dhv:tz timestamp="<%= LeadsComponentDetails.getAlertDate() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>" default="&nbsp;"/>
    </td>
  </tr>
</dhv:evaluate>  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Entered
    </td>
    <td>
      <dhv:username id="<%= LeadsComponentDetails.getEnteredBy() %>"/>
      -
      <dhv:tz timestamp="<%= LeadsComponentDetails.getEntered() %>" dateFormat="<%= DateFormat.SHORT %>" timeFormat="<%= DateFormat.LONG %>"/>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Modified
    </td>
    <td>
      <dhv:username id="<%= LeadsComponentDetails.getModifiedBy() %>"/>
      -
      <dhv:tz timestamp="<%= LeadsComponentDetails.getModified() %>" dateFormat="<%= DateFormat.SHORT %>" timeFormat="<%= DateFormat.LONG %>"/>
    </td>
  </tr>  
</table>
<dhv:permission name="pipeline-opportunities-edit,pipeline-opportunities-delete"><br></dhv:permission>
<dhv:permission name="pipeline-opportunities-edit"><input type="button" value="Modify" onClick="javascript:this.form.action='LeadsComponents.do?command=ModifyComponent&id=<%= LeadsComponentDetails.getId() %>';submit();"></dhv:permission>
<dhv:permission name="pipeline-opportunities-delete"><input type="button" value="Delete" onClick="javascript:popURLReturn('LeadsComponents.do?command=ConfirmComponentDelete&id=<%= LeadsComponentDetails.getId() %>&popup=true','Leads.do?command=DetailsOpp&headerId=<%= LeadsComponentDetails.getHeaderId() %>', 'Delete_opp','320','200','yes','no')"></dhv:permission>
<%-- End container contents --%>
    </td>
  </tr>
</table>
<%-- End container --%>
</form>
