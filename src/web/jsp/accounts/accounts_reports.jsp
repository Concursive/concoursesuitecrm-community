<%@ page import="java.util.*,com.zeroio.iteam.base.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="FileList" class="com.zeroio.iteam.base.FileItemList" scope="request"/>
<jsp:useBean id="RptListInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<%@ include file="initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></SCRIPT>
<script language="JavaScript" type="text/javascript" src="/javascript/popURL.js"></script>
<form name="listView" method="post" action="/Accounts.do?command=Reports">
<a href="/Accounts.do?command=GenerateForm">Generate new report</a>
<center><%= RptListInfo.getAlphabeticalPageLinks() %></center>

<table width="100%" border="0">
  <tr>
    <td align="left">
      <select size="1" name="listView" onChange="javascript:document.forms[0].submit();">
        <option <%= RptListInfo.getOptionValue("my") %>>My Reports</option>
        <option <%= RptListInfo.getOptionValue("all") %>>All Reports</option>
      </select>
      <%= showAttribute(request, "actionError") %>
    </td>
  </tr>
</table>

<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td valign=center align=left bgcolor="#DEE0FA">
      <strong>Action</strong>
    </td>
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
    <td width=8 valign=center nowrap class="row<%= rowid %>">
    <a href="/Accounts.do?command=DownloadCSVReport&fid=<%= thisItem.getId() %>">D/L</a>|<a href="javascript:confirmDelete('/Accounts.do?command=DeleteReport&pid=-1&fid=<%= thisItem.getId() %>');">Del</a>
    </td>
    <td width="40%" class="row<%= rowid %>">
    <a href="javascript:popURL('/Accounts.do?command=ShowReportHtml&pid=-1&fid=<%= thisItem.getId() %>&popup=true','Report','600','400','yes','yes');"><%=toHtml(thisItem.getSubject())%></a>
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
[<%= RptListInfo.getPreviousPageLink("<font class='underline'>Previous</font>", "Previous") %> <%= RptListInfo.getNextPageLink("<font class='underline'>Next</font>", "Next") %>] <%= RptListInfo.getNumericalPageLinks() %>
<%} else {%>
  <tr bgcolor="white"><td colspan=6 valign=center>No reports found.</td></tr>
</table>
<%}%>
</form>
