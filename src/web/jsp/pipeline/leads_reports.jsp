<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*,org.aspcfs.modules.pipeline.base.*" %>
<jsp:useBean id="FileList" class="com.zeroio.iteam.base.FileItemList" scope="request"/>
<jsp:useBean id="LeadRptListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="PipelineViewpointInfo" class="org.aspcfs.utils.web.ViewpointInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<a href="Leads.do">Pipeline Management</a> > 
Reports<br>
<hr color="#BFBFBB" noshade>
<dhv:evaluate exp="<%= PipelineViewpointInfo.isVpSelected(User.getUserId()) %>">
  <b>Viewpoint: </b><b class="highlight"><%= PipelineViewpointInfo.getVpUserName() %></b><br>
  &nbsp;<br>
</dhv:evaluate>
<dhv:permission name="pipeline-reports-add"><a href="Leads.do?command=GenerateForm">Generate new report</a></dhv:permission>
<center><%= LeadRptListInfo.getAlphabeticalPageLinks() %></center>
<table width="100%" border="0">
  <tr>
    <form name="listView" method="post" action="Leads.do?command=Reports">
    <td align="left">
      <select size="1" name="listView" onChange="javascript:document.forms[0].submit();">
        <option <%= LeadRptListInfo.getOptionValue("my") %>>My Reports</option>
        <option <%= LeadRptListInfo.getOptionValue("all") %>>All Reports</option>
      </select>
    </td>
    <td>
      <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="LeadRptListInfo"/>
    </td>
    </form>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <dhv:permission name="pipeline-reports-view,pipeline-reports-delete">
    <td>
      <strong>Action</strong>
    </td>
    </dhv:permission>
    <td>
      <strong>Subject</strong>
    </td>
    <td>
      <strong>Size</strong>
    </td>
    <td nowrap>
      <strong>Create Date</strong>
    </td>
    <td nowrap>
      <strong>Created By</strong>
    </td>
   <td nowrap>
      <strong>D/L</strong>
    </td>
  </tr>
<%
	Iterator j = FileList.iterator();
	if ( j.hasNext() ) {
		int rowid = 0;
    while (j.hasNext()) {
      rowid = (rowid != 1?1:2);
      FileItem thisItem = (FileItem)j.next();
%>      
  <tr>
    <dhv:permission name="pipeline-reports-view,pipeline-reports-delete">
    <td nowrap class="row<%= rowid %>">
      <dhv:permission name="pipeline-reports-view"><a href="Leads.do?command=DownloadCSVReport&fid=<%= thisItem.getId() %>">D/L</a></dhv:permission><dhv:permission name="pipeline-reports-view,pipeline-reports-delete" all="true">|</dhv:permission><dhv:permission name="pipeline-reports-delete"><a href="javascript:confirmDelete('Leads.do?command=DeleteReport&pid=-1&fid=<%= thisItem.getId() %>');">Del</a></dhv:permission>
    </td>
    </dhv:permission>
    <td width="100%" class="row<%= rowid %>">
      <a href="javascript:popURL('Leads.do?command=ShowReportHtml&pid=-1&fid=<%= thisItem.getId() %>&popup=true','Report','600','400','yes','yes');"><%=toHtml(thisItem.getSubject())%></a>
    </td>
    <td align="right" class="row<%= rowid %>">
      <%= thisItem.getRelativeSize() %>k
    </td>
    <td class="row<%= rowid %>" nowrap>
      <%= toHtml(thisItem.getEnteredDateTimeString()) %>
    </td>
    <td class="row<%= rowid %>" nowrap>
      <%= toHtml(thisItem.getEnteredByString()) %>
    </td>
    <td align="right" class="row<%= rowid %>" nowrap>
      <%= thisItem.getDownloads() %>
    </td>
  </tr>
<%}%>
</table>
<br>
<dhv:pagedListControl object="LeadRptListInfo" tdClass="row1"/>
<%} else {%>
  <tr class="containerBody"><td colspan="6">No reports found.</td></tr>
</table>
<%}%>
