<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.pipeline.base.*,org.aspcfs.utils.web.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="opportunityHeader" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<jsp:useBean id="PipelineViewpointInfo" class="org.aspcfs.utils.web.ViewpointInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script type="text/javascript">
function reopenOpportunity(id) {
  if (id == '<%= opportunityHeader.getId() %>') {
    if ('<%= request.getParameter("return") == null %>' =='true') {
      scrollReload('Leads.do?command=Search');
    } else if ('<%= request.getParameter("return") != null && "dashboard".equals((String) request.getParameter("return") %>' == 'true') {
      scrollReload('Leads.do?command=Dashboard');
    }
    return id;
  } else {
    return '<%= opportunityHeader.getId() %>';
  }
}
</script>
<form name="modifyOpp" action="Leads.do?command=UpdateOpp<%= (request.getParameter("popup") != null?"&popup=true":"") %>" method="post">
<%
  boolean popUp = false;
  if (request.getParameter("popup") != null) {
    popUp = true;
  }
%>
<%-- Trails --%>
<dhv:evaluate if="<%= !popUp %>">
<table class="trails" cellspacing="0">
<tr>
<td>
  <a href="Leads.do"><dhv:label name="pipeline.pipeline">Pipeline</dhv:label></a> >
  <% if (request.getParameter("return") == null) { %>
	  <a href="Leads.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
    <a href="Leads.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_add.OpportunityDetails">Opportunity Details</dhv:label></a> >
    <%} else {%>
    <% if (request.getParameter("return").equals("list")) { %>
		<a href="Leads.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
    <%} else if (request.getParameter("return").equals("dashboard")) { %>
		<a href="Leads.do?command=Dashboard"><dhv:label name="communications.campaign.Dashboard">Dashboard</dhv:label></a> >
    <%}%>
  <%}%>
  <dhv:label name="accounts.accounts_contacts_opps_modify.ModifyOpportunity">Modify Opportunity</dhv:label>
</td>
</tr>
</table>
</dhv:evaluate>
<%-- End Trails --%>
<dhv:evaluate if="<%= PipelineViewpointInfo.isVpSelected(User.getUserId()) %>">
  <dhv:label name="pipeline.viewpoint.colon" param="<%= "username="+PipelineViewpointInfo.getVpUserName() %>"><b>Viewpoint: </b><b class="highlight"><%= PipelineViewpointInfo.getVpUserName() %></b></dhv:label><br />
  &nbsp;<br>
</dhv:evaluate>
<dhv:container name="opportunities" selected="details" object="opportunityHeader" param="<%= "id=" + opportunityHeader.getId() %>">
  <% if (request.getParameter("return") != null) {%>
      <input type="hidden" name="return" value="<%=request.getParameter("return")%>">
  <%}%>
      <input type="hidden" name="headerId" value="<%= opportunityHeader.getId() %>">
      <input type="hidden" name="modified" value="<%= opportunityHeader.getModified() %>">
      <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" onClick="this.form.dosubmit.value='true';">
  <% if (request.getParameter("return") != null) {%>
    <% if (request.getParameter("return").equals("list")) {%>
        <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='Leads.do?command=Search';this.form.dosubmit.value='false';">
    <%}%>
  <%} else {%>
    	<input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='Leads.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>';this.form.dosubmit.value='false';">
  <%}%>
      <dhv:evaluate if="<%= popUp %>">
        <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onclick="javascript:window.close();"> 
      </dhv:evaluate>
      <br />
      <dhv:formMessage />
      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
        <tr>
          <th colspan="2">
            <strong><%= opportunityHeader.getDescription() %></strong>
          </th>
        </tr>
        <tr class="containerBody">
          <td nowrap class="formLabel">
            <dhv:label name="accounts.accountasset_include.Description">Description</dhv:label>
          </td>
          <td>
            <input type="text" size="50" name="description" value="<%= toHtmlValue(opportunityHeader.getDescription()) %>">
            <font color="red">*</font> <%= showAttribute(request, "descriptionError") %>
          </td>
        </tr>
      </table>
      &nbsp;
      <br />
      <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" onClick="this.form.dosubmit.value='true';">
  <% if (request.getParameter("return") != null) {%>
	  <% if (request.getParameter("return").equals("list")) {%>
      <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='Leads.do?command=Search';this.form.dosubmit.value='false';">
	  <%}%>
  <%} else {%>
    	<input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='Leads.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>';this.form.dosubmit.value='false';">
  <%}%>
      <input type="hidden" name="dosubmit" value="true">
</dhv:container>
</form>
