<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.pipeline.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="opportunityHeader" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<jsp:useBean id="LeadsComponentDetails" class="org.aspcfs.modules.pipeline.base.OpportunityComponent" scope="request"/>
<jsp:useBean id="PipelineViewpointInfo" class="org.aspcfs.utils.web.ViewpointInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<form name="componentDetails" action="Leads.do?command=ModifyComponent&id=<%= LeadsComponentDetails.getId() %>&return=details" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Leads.do">Pipeline</a> >
<% if ("dashboard".equals(request.getParameter("viewSource"))){ %>
	<a href="Leads.do?command=Dashboard">Dashboard</a> >
<% }else{ %>
	<a href="Leads.do?command=Search">Search Results</a> >
<% } %>
<a href="Leads.do?command=DetailsOpp&headerId=<%= LeadsComponentDetails.getHeaderId() %>&reset=true<%= addLinkParams(request, "viewSource") %>">Opportunity Details</a> > 
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
<% String param1 = "id=" + opportunityHeader.getId(); 
   String param2 = addLinkParams(request, "viewSource");
%>      
<dhv:container name="opportunities" selected="details" param="<%= param1 %>" appendToUrl="<%= param2 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
<%-- Begin the container contents --%>
<dhv:permission name="pipeline-opportunities-edit"><input type="button" value="Modify" onClick="javascript:this.form.action='LeadsComponents.do?command=ModifyComponent&id=<%= LeadsComponentDetails.getId() %>&return=details';submit();"></dhv:permission>
<dhv:permission name="pipeline-opportunities-delete"><input type="button" value="Delete" onClick="javascript:popURLReturn('LeadsComponents.do?command=ConfirmComponentDelete&id=<%= LeadsComponentDetails.getId() %>&popup=true<%= addLinkParams(request, "viewSource") %>','Leads.do?command=DetailsOpp&headerId=<%= LeadsComponentDetails.getHeaderId() %>', 'Delete_opp','320','200','yes','no')"></dhv:permission>
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
      <zeroio:tz timestamp="<%= LeadsComponentDetails.getCloseDate() %>" dateOnly="true" default="&nbsp;"/>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Low Estimate
    </td>
    <td>
      <zeroio:currency value="<%= LeadsComponentDetails.getLow() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;"/>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Best Guess
    </td>
    <td>
      <zeroio:currency value="<%= LeadsComponentDetails.getGuess() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;"/>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      High Estimate
    </td>
    <td>
      <zeroio:currency value="<%= LeadsComponentDetails.getHigh() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;"/>
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
      <zeroio:tz timestamp="<%= LeadsComponentDetails.getStageDate() %>" dateOnly="true" default="&nbsp;"/>
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
      <zeroio:tz timestamp="<%= LeadsComponentDetails.getAlertDate() %>" dateOnly="true" timeZone="<%= LeadsComponentDetails.getAlertDateTimeZone() %>" showTimeZone="yes"  default="&nbsp;"/>
      <% if(!User.getTimeZone().equals(LeadsComponentDetails.getAlertDateTimeZone())){%>
      <br>
      <zeroio:tz timestamp="<%= LeadsComponentDetails.getAlertDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="yes"  default="&nbsp;" />
      <% } %>
    </td>
  </tr>
</dhv:evaluate>  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Entered
    </td>
    <td>
      <dhv:username id="<%= LeadsComponentDetails.getEnteredBy() %>"/>
      <zeroio:tz timestamp="<%= LeadsComponentDetails.getEntered() %>"/>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Modified
    </td>
    <td>
      <dhv:username id="<%= LeadsComponentDetails.getModifiedBy() %>"/>
      <zeroio:tz timestamp="<%= LeadsComponentDetails.getModified() %>"/>
    </td>
  </tr>  
</table>
<dhv:permission name="pipeline-opportunities-edit,pipeline-opportunities-delete"><br></dhv:permission>
<dhv:permission name="pipeline-opportunities-edit"><input type="button" value="Modify" onClick="javascript:this.form.action='LeadsComponents.do?command=ModifyComponent&id=<%= LeadsComponentDetails.getId() %>&return=details';submit();"></dhv:permission>
<dhv:permission name="pipeline-opportunities-delete"><input type="button" value="Delete" onClick="javascript:popURLReturn('LeadsComponents.do?command=ConfirmComponentDelete&id=<%= LeadsComponentDetails.getId() %>&popup=true<%= addLinkParams(request, "viewSource") %>','Leads.do?command=DetailsOpp&headerId=<%= LeadsComponentDetails.getHeaderId() %>', 'Delete_opp','320','200','yes','no')"></dhv:permission>
<%-- End container contents --%>
    </td>
  </tr>
</table>
<%-- End container --%>
<%= addHiddenParams(request, "viewSource") %>
</form>
