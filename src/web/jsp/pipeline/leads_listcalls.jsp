<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.pipeline.base.*,org.aspcfs.modules.contacts.base.Call,com.zeroio.iteam.base.*" %>
<jsp:useBean id="opportunityHeader" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<jsp:useBean id="LeadsCallList" class="org.aspcfs.modules.contacts.base.CallList" scope="request"/>
<jsp:useBean id="LeadsCallListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="PipelineViewpointInfo" class="org.aspcfs.utils.web.ViewpointInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="leads_listcalls_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<%-- Trails --%>
<table class="trails">
<tr>
<td>
<a href="Leads.do">Pipeline</a> > 
<a href="Leads.do?command=ViewOpp">View Components</a> >
<a href="Leads.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>">Opportunity Details</a> >
Calls
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:evaluate exp="<%= PipelineViewpointInfo.isVpSelected(User.getUserId()) %>">
  <b>Viewpoint: </b><b class="highlight"><%= PipelineViewpointInfo.getVpUserName() %></b><br>
  &nbsp;<br>
</dhv:evaluate>
<%@ include file="leads_details_header_include.jsp" %>
<% String param1 = "id=" + opportunityHeader.getId(); %>      
<dhv:container name="opportunities" selected="calls" param="<%= param1 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
      <dhv:permission name="contacts-external_contacts-calls-add"><a href="LeadsCalls.do?command=Add&headerId=<%= opportunityHeader.getId() %>">Add a Call</a><br></dhv:permission>
      <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="LeadsCallListInfo"/>
      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
        <tr>
          <th>
            <strong>Action</strong>
          </th>
          <th>
            <strong>Subject</strong>
          </th>
          <th>
            <strong>Type</strong>
          </th>
          <th>
            <strong>Length</strong>
          </th>
          <th>
            <strong>Date</strong>
          </th>
        </tr>
<%
    Iterator j = LeadsCallList.iterator();
    if ( j.hasNext() ) {
      int rowid = 0;
      int i =0;
        while (j.hasNext()) {
          i++;
          rowid = (rowid != 1?1:2);
          Call thisCall = (Call)j.next();
%>
        <tr class="containerBody">
          <td width="8" nowrap class="row<%= rowid %>">
            <%-- Use the unique id for opening the menu, and toggling the graphics --%>
             <a href="javascript:displayMenu('menuCall', '<%= opportunityHeader.getId() %>', '<%= thisCall.getId() %>');"
             onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>)"><img src="images/select.gif" name="select<%= i %>" align="absmiddle" border="0"></a>
          </td>
        <td width="100%" class="row<%= rowid %>">
          <a href="LeadsCalls.do?command=Details&id=<%= thisCall.getId() %>&headerId=<%= opportunityHeader.getId() %>">
          <%= toHtml(thisCall.getSubject()) %>
          </a>
        </td>
        <td nowrap class="row<%= rowid %>">
          <%= toHtml(thisCall.getCallType()) %>
        </td>
        <td valign=center nowrap class="row<%= rowid %>">
          <%= toHtml(thisCall.getLengthText()) %>
        </td>
        <td nowrap class="row<%= rowid %>">
          <dhv:tz timestamp="<%= thisCall.getEntered() %>" dateFormat="<%= DateFormat.SHORT %>" timeFormat="<%= DateFormat.LONG %>"/>
        </td>
      </tr>
   <%}%>
  <%} else {%>
      <tr class="containerBody">
        <td colspan="5">
          No calls found.
        </td>
      </tr>
  <%}%>
  </table>
  <br>
  <dhv:pagedListControl object="LeadsCallListInfo"/>
</td>
</tr>
</table>
