<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="FileItem" class="com.zeroio.iteam.base.FileItem" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body bgcolor='#FFFFFF'>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="7">
      &nbsp;<strong>All Versions:</strong>
    </td>
  </tr>
  <tr>
    <td nowrap>
      &lt;Action&gt;
    </td>
    <td nowrap>
      &lt;Item&gt;
    </td>
    <td align="right" nowrap>
      &lt;Size&gt;
    </td>
    <td align="right" nowrap>
      &lt;Version&gt;
    </td>
    <td nowrap>
      &lt;Submitted&gt;
    </td>
    <td nowrap>
      &lt;Sent By&gt;
    </td>
    <td nowrap>
      &lt;D/L&gt;
    </td>
  </tr>
<%          
  int rowid = 0;
  Iterator versionList = FileItem.getVersionList().iterator();
  while (versionList.hasNext()) {
    if (rowid != 1) {
      rowid = 1;
    } else {
      rowid = 2;
    }
    FileItemVersion thisVersion = (FileItemVersion)versionList.next();
%>
  <tr class="row<%= rowid %>">
    <td rowspan="2">
      <a href="ProjectManagementFiles.do?command=Download&pid=<%= Project.getId() %>&fid=<%= FileItem.getId() %>&ver=<%= thisVersion.getVersion() %>">Download</a>
    </td>
    <td>
      <%= FileItem.getImageTag() %><%= thisVersion.getClientFilename() %>
    </td>
    <td align="right">
      <%= thisVersion.getRelativeSize() %> k&nbsp;
    </td>
    <td align="right">
      <%= thisVersion.getVersion() %>&nbsp;
    </td>
    <td nowrap>
      <%= thisVersion.getEnteredDateTimeString() %>
    </td>
    <td>
      <dhv:username id="<%= thisVersion.getEnteredBy() %>"/>
    </td>
    <td align="right">
      <%= thisVersion.getDownloads() %>
    </td>
  </tr>
  <tr class="row<%= rowid %>">
    <td colspan="6">
      <i><%= thisVersion.getSubject() %></i>
    </td>
  </tr>
<%
  }
%>
</table>
</body>
