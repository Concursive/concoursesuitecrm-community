<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="EmployeeList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="CompanyDirectoryInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<a href="MyCFS.do?command=Home">My Home Page</a> >
View Employees<br>
<hr color="#BFBFBB" noshade>
<dhv:permission name="contacts-internal_contacts-add"><a href="CompanyDirectory.do?command=Prepare">Add an Employee</a></dhv:permission>
<dhv:permission name="contacts-internal_contacts-add" none="true"><br></dhv:permission>
<center><%= CompanyDirectoryInfo.getAlphabeticalPageLinks() %></center>
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="CompanyDirectoryInfo"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
  <dhv:permission name="contacts-internal_contacts-edit,contacts-internal_contacts-delete">
    <th>
      <strong>Action</strong>
    </th>
  </dhv:permission>
    <th nowrap>
      <a href="CompanyDirectory.do?command=ListEmployees&column=c.namelast">
        <strong>Name</strong>
      </a>
      <%= CompanyDirectoryInfo.getSortIcon("c.namelast") %>
    </th>
    <th nowrap>
      <a href="CompanyDirectory.do?command=ListEmployees&column=departmentname">
        <strong>Department</strong>
      </a>
      <%= CompanyDirectoryInfo.getSortIcon("departmentname") %>
    </th>
    <th nowrap>
      <a href="CompanyDirectory.do?command=ListEmployees&column=c.title">
        <strong>Title</strong>
      </a>
      <%= CompanyDirectoryInfo.getSortIcon("c.title") %>
    </th nowrap>
    <th nowrap>
      <strong>Business Phone</strong>
    </th>
  </tr>
<%
  Iterator i = EmployeeList.iterator();
  if (i.hasNext()) {
    int rowid = 0;
    while (i.hasNext()) {
      rowid = (rowid != 1?1:2);
      Contact thisEmployee = (Contact)i.next();
%>      
  <tr>
    <dhv:permission name="contacts-internal_contacts-edit,contacts-internal_contacts-delete">
      <td width="8" valign="center" class="row<%= rowid %>" nowrap>
        <dhv:permission name="contacts-internal_contacts-edit"><a href="CompanyDirectory.do?command=ModifyEmployee&empid=<%= thisEmployee.getId()%>&return=list">Edit</a></dhv:permission><dhv:permission name="contacts-internal_contacts-edit,contacts-internal_contacts-delete" all="true">|</dhv:permission><dhv:permission name="contacts-internal_contacts-delete"><a href="javascript:popURLReturn('CompanyDirectory.do?command=ConfirmDelete&id=<%=thisEmployee.getId()%>&popup=true','CompanyDirectory.do?command=ListEmployees', 'Delete_Employee','330','200','yes','no');">Del</a></dhv:permission>
      </td>
    </dhv:permission>
    <td class="row<%= rowid %>" nowrap>
      <a href="CompanyDirectory.do?command=EmployeeDetails&empid=<%= thisEmployee.getId() %>"><%= toHtml(thisEmployee.getNameLastFirst()) %></a>
      <dhv:evaluate exp="<%=!(thisEmployee.hasEnabledAccount())%>"><font color="red">*</font></dhv:evaluate>          
        <%= thisEmployee.getEmailAddressTag("Business", "<img border=0 src=\"images/email.gif\" alt=\"Send email\" align=\"absmiddle\">", "&nbsp;") %>
    </td>
    <td class="row<%= rowid %>">
      <%= toHtml(thisEmployee.getDepartmentName()) %>
    </td>
    <td class="row<%= rowid %>">
      <%= toHtml(thisEmployee.getTitle()) %>
    </td>
    <td class="row<%= rowid %>">
      <%= toHtml(thisEmployee.getPhoneNumber("Business")) %>
    </td>
  </tr>
<%      
    }
  } else {
%>
  <tr>
    <td class="containerBody" colspan="5">
      No Employees found.
    </td>
  </tr>
<%  
  }
%>
</table>
<br>
<dhv:pagedListControl object="CompanyDirectoryInfo" tdClass="row1"/>
