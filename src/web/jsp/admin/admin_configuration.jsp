<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="org.aspcfs.modules.admin.base.PermissionCategory, java.util.*" %>
<%@ include file="../initPage.jsp" %>
<jsp:useBean id="PermissionCategoryList" class="org.aspcfs.modules.admin.base.PermissionCategoryList" scope="request"/>
<a href="Admin.do">Setup</a> > 
Configure Modules<br>
<hr color="#BFBFBB" noshade>
Setup CFS to meet the specific needs of your organization, including configuration of lookup lists and custom fields.  Choose a CFS module to proceed.<br>
&nbsp;<br>
<table cellpadding="0" cellspacing="0" border="0" width="100%">
  <tr>
    <td width="49%" valign="top">
<%
  Iterator i = PermissionCategoryList.iterator();
  int limit = (PermissionCategoryList.size()/2);
  int count = 0;
  while (i.hasNext()) {
    count++;
    PermissionCategory thisPermissionCat = (PermissionCategory)i.next();
%>
      <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
        <tr>
          <th>
            <strong><a href="Admin.do?command=ConfigDetails&moduleId=<%=thisPermissionCat.getId()%>"><%=thisPermissionCat.getCategory()%></a></strong>
          </th>
        </tr>
        <tr class="containerBody">
          <td>
            Changes affect all users
          </td>
        </tr>
      </table>
      &nbsp;
<% 
    if (count == limit) { 
%>
    </td>
    <td width="2%">
      &nbsp;
    </td>
    <td width="49%" valign="top">
<%
    }
  }
%>
    </td>
  </tr>
</table>

