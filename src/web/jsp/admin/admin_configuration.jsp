<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="org.aspcfs.modules.admin.base.PermissionCategory, java.util.*" %>
<%@ include file="../initPage.jsp" %>
<jsp:useBean id="PermissionCategoryList" class="org.aspcfs.modules.admin.base.PermissionCategoryList" scope="request"/>
<%-- Trails --%>
<table class="trails">
<tr>
<td>
<a href="Admin.do">Admin</a> > 
Configure Modules
</td>
</tr>
</table>
<%-- End Trails --%>
Setup Dark Horse CRM to meet the specific needs of your organization, including configuration of lookup lists and custom fields.  Choose a Dark Horse CRM module to proceed.<br>
&nbsp;<br>
<table cellpadding="0" cellspacing="0" border="0" width="100%">
<%
  int column = 0;
  Iterator i = PermissionCategoryList.iterator();
  while (i.hasNext()) {
    column = (column != 1 ? 1 : 2);
    PermissionCategory thisPermissionCat = (PermissionCategory)i.next();
%>
<dhv:evaluate if="<%= column == 1 %>">
  <tr>
</dhv:evaluate>
    <td width="49%" valign="top">
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
    </td>
<dhv:evaluate if="<%= column == 1 %>">
    <td width="2%">
      &nbsp;
    </td>
</dhv:evaluate>
<dhv:evaluate if="<%= column == 2 %>">
  </tr>
</dhv:evaluate>
<%
  }
%>
<dhv:evaluate if="<%= column == 1 %>">
    <td width="49%">
      &nbsp;
    </td>
  </tr>
</dhv:evaluate>
</table>

