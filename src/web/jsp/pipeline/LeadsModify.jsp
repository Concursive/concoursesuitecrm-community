<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.pipeline.base.*,org.aspcfs.utils.web.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="opportunityHeader" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<jsp:useBean id="PipelineViewpointInfo" class="org.aspcfs.utils.web.ViewpointInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<form name="modifyOpp" action="Leads.do?command=UpdateOpp<%= (request.getParameter("popup") != null?"&popup=true":"") %>" method="post">
<%
  boolean popUp = false;
  if (request.getParameter("popup") != null) {
    popUp = true;
  }
%>
<dhv:evaluate exp="<%= !popUp %>">
  <a href="Leads.do">Pipeline Management</a> > 
  <% if (request.getParameter("return") == null) { %>
	  <a href="Leads.do?command=ViewOpp">View Components</a> >
    <a href="Leads.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>">Opportunity Details</a> >
    <%} else {%>
    <% if (request.getParameter("return").equals("list")) { %>
		<a href="Leads.do?command=ViewOpp">View Components</a> >
    <%} else if (request.getParameter("return").equals("dashboard")) { %>
		<a href="Leads.do?command=Dashboard">Dashboard</a> >
    <%}%>
  <%}%>
  Modify Opportunity<br>
  <hr color="#BFBFBB" noshade>
  <dhv:evaluate exp="<%= PipelineViewpointInfo.isVpSelected(User.getUserId()) %>">
    <b>Viewpoint: </b><b class="highlight"><%= PipelineViewpointInfo.getVpUserName() %></b><br>
    &nbsp;<br>
  </dhv:evaluate>
</dhv:evaluate>
<%@ include file="leads_details_header_include.jsp" %>
<dhv:evaluate exp="<%= !popUp %>">
  <% String param1 = "id=" + opportunityHeader.getId(); %>      
  <dhv:container name="opportunities" selected="details" param="<%= param1 %>" style="tabs"/>
</dhv:evaluate>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
<% if (request.getParameter("return") != null) {%>
      <input type="hidden" name="return" value="<%=request.getParameter("return")%>">
<%}%>
      <input type="hidden" name="headerId" value="<%= opportunityHeader.getId() %>">
      <input type="hidden" name="modified" value="<%= opportunityHeader.getModified() %>">
      <input type="submit" value="Update" onClick="this.form.dosubmit.value='true';">
<% if (request.getParameter("return") != null) {%>
  <% if (request.getParameter("return").equals("list")) {%>
      <input type="submit" value="Cancel" onClick="javascript:this.form.action='Leads.do?command=ViewOpp';this.form.dosubmit.value='false';">
	<%}%>
<%} else {%>
    	<input type="submit" value="Cancel" onClick="javascript:this.form.action='Leads.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>';this.form.dosubmit.value='false';">
<%}%>
      <input type="reset" value="Reset">
      <dhv:evaluate exp="<%= popUp %>">
        <input type="button" value="Cancel" onclick="javascript:window.close();"> 
      </dhv:evaluate>
      <br>&nbsp;
      <% if (request.getAttribute("actionError") != null) { %>
      <%= showError(request, "actionError") %>
      <%}%>
      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
        <tr>
          <th colspan="2">
            <strong><%= opportunityHeader.getDescription() %></strong>
          </th>
        </tr>
        <tr class="containerBody">
          <td nowrap class="formLabel">
            Description
          </td>
          <td>
            <input type="text" size="50" name="description" value="<%= toHtmlValue(opportunityHeader.getDescription()) %>">
            <font color="red">*</font> <%= showAttribute(request, "descriptionError") %>
          </td>
        </tr>
      </table>
      &nbsp;
      <br>
      <input type="submit" value="Update" onClick="this.form.dosubmit.value='true';">
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
      <input type="submit" value="Cancel" onClick="javascript:this.form.action='Leads.do?command=ViewOpp';this.form.dosubmit.value='false';">
	<%}%>
<%} else {%>
    	<input type="submit" value="Cancel" onClick="javascript:this.form.action='Leads.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>';this.form.dosubmit.value='false';">
<%}%>
      <input type="reset" value="Reset">
      <input type="hidden" name="dosubmit" value="true">
    </td>
  </tr>
</table>
</form>
