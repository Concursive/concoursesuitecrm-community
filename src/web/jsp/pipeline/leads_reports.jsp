<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*,org.aspcfs.modules.pipeline.base.*" %>
<jsp:useBean id="FileList" class="com.zeroio.iteam.base.FileItemList" scope="request"/>
<jsp:useBean id="LeadRptListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="PipelineViewpointInfo" class="org.aspcfs.utils.web.ViewpointInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></SCRIPT>
<script language="JavaScript" type="text/javascript" src="/javascript/popURL.js"></script>
<a href="Leads.do">Pipeline Management</a> > 
Reports<br>
<hr color="#BFBFBB" noshade>
<dhv:permission name="pipeline-reports-add"><a href="Leads.do?command=GenerateForm">Generate new report</a></dhv:permission>
<dhv:permission name="pipeline-reports-add" none="true"><br></dhv:permission>
<dhv:evaluate exp="<%= PipelineViewpointInfo.isVpSelected(User.getUserId()) %>">
  <table cellpadding="0" cellspacing="0" border="0" width="100%">
  <tr>
    <td align="left" width="30%" nowrap>
      <b>Viewpoint: </b><b class="highlight"><%= PipelineViewpointInfo.getVpUserName() %></b>&nbsp;&nbsp;
    </td>
    <td align="center" width="30%" nowrap>
      <%= LeadRptListInfo.getAlphabeticalPageLinks() %>
    </td>
    <td align="right" width="40%">
      &nbsp;
    </td>
    </tr>
  </table>
</dhv:evaluate>
<dhv:evaluate exp="<%= !(PipelineViewpointInfo.isVpSelected(User.getUserId())) %>">
  <center><%= LeadRptListInfo.getAlphabeticalPageLinks() %></center>
</dhv:evaluate>
<form name="listView" method="post" action="Leads.do?command=Reports">
<table width="100%" border="0">
  <tr>
    <td align="left">
      <select size="1" name="listView" onChange="javascript:document.forms[0].submit();">
        <option <%= LeadRptListInfo.getOptionValue("my") %>>My Reports</option>
        <option <%= LeadRptListInfo.getOptionValue("all") %>>All Reports</option>
      </select>
    </td>
    <td>
      <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="LeadRptListInfo"/>
    </td>
  </tr>
</table>
</form>
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <dhv:permission name="pipeline-reports-view,pipeline-reports-delete">
    <td valign=center align=left bgcolor="#DEE0FA">
      <strong>Action</strong>
    </td>
    </dhv:permission>
    <td valign=center align=left bgcolor="#DEE0FA">
      <strong>File Subject</strong>
    </td>
    <td valign=center align=left bgcolor="#DEE0FA">
      <strong>Size</strong>
    </td>
    <td valign=center align=left bgcolor="#DEE0FA">
      <strong>Create Date</strong>
    </td>
     <td valign=center align=left bgcolor="#DEE0FA">
      <strong>Created By</strong>
    </td>
         <td valign=center align=left bgcolor="#DEE0FA">
      <strong>D/L</strong>
    </td>
    
  </tr>
<%
	Iterator j = FileList.iterator();
	
	if ( j.hasNext() ) {
		int rowid = 0;
	while (j.hasNext()) {
		if (rowid != 1) {
			rowid = 1;
		} else {
			rowid = 2;
		}
	
	FileItem thisItem = (FileItem)j.next();
%>      
  <tr>
    <dhv:permission name="pipeline-reports-view,pipeline-reports-delete">
    <td width=8 valign=center nowrap class="row<%= rowid %>">
    <dhv:permission name="pipeline-reports-view"><a href="Leads.do?command=DownloadCSVReport&fid=<%= thisItem.getId() %>">D/L</a></dhv:permission><dhv:permission name="pipeline-reports-view,pipeline-reports-delete" all="true">|</dhv:permission><dhv:permission name="pipeline-reports-delete"><a href="javascript:confirmDelete('Leads.do?command=DeleteReport&pid=-1&fid=<%= thisItem.getId() %>');">Del</a></dhv:permission>
    </td>
    </dhv:permission>
    <td width="40%" class="row<%= rowid %>">
    <a href="javascript:popURL('Leads.do?command=ShowReportHtml&pid=-1&fid=<%= thisItem.getId() %>&popup=true','Report','600','400','yes','yes');"><%=toHtml(thisItem.getSubject())%></a>
    </td>
    <td width=20 class="row<%= rowid %>">
    <%= thisItem.getRelativeSize() %>k
    </td>
    <td width="30%" class="row<%= rowid %>">
    <%=toHtml(thisItem.getEnteredDateTimeString())%>
    </td>
    <td width="30%" class="row<%= rowid %>">
    <%=toHtml(thisItem.getEnteredByString())%>
    </td>
    <td width=8 class="row<%= rowid %>">
    <%= thisItem.getDownloads() %>
    </td>
 </tr>

<%}%>
</table>
<br>
<dhv:pagedListControl object="LeadRptListInfo" tdClass="row1"/>
<%} else {%>
  <tr bgcolor="white"><td colspan=6 valign=center>No reports found.</td></tr>
</table>
<%}%>
