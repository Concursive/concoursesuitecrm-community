<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.admin.base.*" %>
<jsp:useBean id="ViewpointList" class="org.aspcfs.modules.admin.base.ViewpointList" scope="request"/>
<jsp:useBean id="ViewpointListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="UserRecord" class="org.aspcfs.modules.admin.base.User" scope="request"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<a href="Admin.do">Setup</a> >
<a href="Users.do?command=ListUsers">View Users</a> >
<a href="Users.do?command=UserDetails&id=<%= request.getParameter("userId") %>">User Details</a> >
Viewpoints<br>
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(UserRecord.getUsername()) %> (<%= toHtml(UserRecord.getContact().getNameLastFirst()) %>)</strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "id=" + UserRecord.getId(); %>
      <dhv:container name="users" selected="viewpoints" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">
<dhv:permission name="admin-roles-add"><a href="Viewpoints.do?command=InsertViewpointForm&userId=<%= UserRecord.getId() %>">Add New Viewpoint</a></dhv:permission>
<center><%= ViewpointListInfo.getAlphabeticalPageLinks() %></center>
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="ViewpointListInfo"/>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <dhv:permission name="admin-roles-edit,admin-roles-delete">
    <td valign="center" width="8" nowrap>
      <strong>Action</strong>
    </td>
    </dhv:permission>
    <td nowrap>
      <b><a href="Viewpoints.do?command=ListViewpoints&userId=<%= UserRecord.getId()%>&column=c.namelast">Viewpoint</a></b>
      <%= ViewpointListInfo.getSortIcon("c.namelast") %>
    </td>
    <td width="120" nowrap>
      <b><a href="Viewpoints.do?command=ListViewpoints&userId=<%= UserRecord.getId()%>&column=vp.entered">Entered</a></b>
      <%= ViewpointListInfo.getSortIcon("vp.entered") %>
    </td>
    <td width="30" nowrap>
      <b><a href="Viewpoints.do?command=ListViewpoints&userId=<%= UserRecord.getId()%>&column=vp.enabled">Enabled</a></b>
      <%= ViewpointListInfo.getSortIcon("vp.enabled") %>
    </td>
  </tr>
<%
  Iterator i = ViewpointList.iterator();
  if (i.hasNext()) {
    int rowid = 0;
    while (i.hasNext()) {
      if (rowid != 1) {
        rowid = 1;
      } else {
        rowid = 2;
      }
      Viewpoint thisViewpoint = (Viewpoint)i.next();
%>      
      <tr>
        <dhv:permission name="admin-roles-edit,admin-roles-delete">
        <td width="8" valign="center" nowrap class="row<%= rowid %>">
          <dhv:permission name="admin-roles-edit"><a href="Viewpoints.do?command=ViewpointDetails&id=<%= thisViewpoint.getId() %>&userId=<%= UserRecord.getId() %>">Edit</a></dhv:permission><dhv:permission name="admin-roles-edit,admin-roles-delete" all="true">|</dhv:permission><dhv:permission name="admin-roles-delete"><a href="javascript:popURLReturn('Viewpoints.do?command=ConfirmDelete&id=<%=thisViewpoint.getId()%>&userId=<%= UserRecord.getId() %>&popup=true','Viewpoints.do?command=ListViewpoints&userId=<%= UserRecord.getId()%>', 'Delete_Viewpoint','320','200','yes','no');">Del</a></dhv:permission>
        </td>
        </dhv:permission>
        <td class="row<%= rowid %>" width="150">
          <a href="Viewpoints.do?command=ViewpointDetails&id=<%= thisViewpoint.getId() %>&userId=<%= UserRecord.getId()%>"><%= thisViewpoint.getVpUser().getContact().getNameLastFirst() %></a>
        </td>
        <td class="row<%= rowid %>">
          <%= toDateTimeString(thisViewpoint.getEntered()) %>
        </td>
         <td class="row<%= rowid %>">
          <%= thisViewpoint.getEnabled() ? "<font color=\"green\">Yes</font>":"<font color=\"red\">No</font>" %>
        </td>
      </tr>
<%      
    }
  } else {
%>  
  <tr>
    <td class="row2" colspan="4">
      No Viewpoints found.
    </td>
  </tr>
<%  
  }
%>
</table>
<br>
<dhv:pagedListControl object="ViewpointListInfo" tdClass="containerBack"/>
</td></tr>
</table>

