<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="EmployeeList" class="com.darkhorseventures.cfsbase.ContactList" scope="request"/>
<jsp:useBean id="CompanyDirectoryInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<%@ include file="initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></script>
<a href="/ExternalContacts.do">Contacts &amp; Resources</a> > 
View Employees<br>
<hr color="#BFBFBB" noshade>
<dhv:permission name="contacts-internal_contacts-add"><a href="/CompanyDirectory.do?command=InsertEmployeeForm">Add an Employee</a></dhv:permission>
<dhv:permission name="contacts-internal_contacts-add" none="true"><br></dhv:permission>
<center><%= CompanyDirectoryInfo.getAlphabeticalPageLinks() %></center>
<%= showAttribute(request, "actionError") %>
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
  <dhv:permission name="contacts-internal_contacts-edit,contacts-internal_contacts-delete">
    <td valign=center align=left>
      <strong>Action</strong>
    </td>
  </dhv:permission>
    <td>
      <a href="/CompanyDirectory.do?command=ListEmployees&column=c.namelast">
        <strong>Name</strong>
      </a>
      <%= CompanyDirectoryInfo.getSortIcon("c.namelast") %>
    </td>
    <td>
      <a href="/CompanyDirectory.do?command=ListEmployees&column=departmentname">
        <strong>Department</strong>
      </a>
      <%= CompanyDirectoryInfo.getSortIcon("departmentname") %>
    </td>
    <td>
      <a href="/CompanyDirectory.do?command=ListEmployees&column=c.title">
        <strong>Title</strong>
      </a>
      <%= CompanyDirectoryInfo.getSortIcon("c.title") %>
    </td>
    <td nowrap>
      <strong>Business Phone</strong>
    </td>
  </tr>
<%
  Iterator i = EmployeeList.iterator();
  if (i.hasNext()) {
    int rowid = 0;
    while (i.hasNext()) {
      if (rowid != 1) {
        rowid = 1;
      } else {
        rowid = 2;
      }
      Contact thisEmployee = (Contact)i.next();
%>      
      <tr>
        <dhv:permission name="contacts-internal_contacts-edit,contacts-internal_contacts-delete">
        <td width=8 valign=center nowrap class="row<%= rowid %>">
          <dhv:permission name="contacts-internal_contacts-edit"><a href="/CompanyDirectory.do?command=EmployeeDetails&empid=<%= thisEmployee.getId()%>&action=modify&return=list">Edit</a></dhv:permission><dhv:permission name="contacts-internal_contacts-edit,contacts-internal_contacts-delete" all="true">|</dhv:permission><dhv:permission name="contacts-internal_contacts-delete"><a href="javascript:confirmDelete('/CompanyDirectory.do?command=DeleteEmployee&empid=<%= thisEmployee.getId() %>');">Del</a></dhv:permission>
        </td>
	</dhv:permission>
        <td class="row<%= rowid %>"><font class="columntext1">
          <a href="/CompanyDirectory.do?command=EmployeeDetails&empid=<%= thisEmployee.getId() %>"><%= toHtml(thisEmployee.getNameLastFirst()) %></a></font>
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
    <td class="row2" colspan="5">
      No Employees found.
    </td>
  </tr>
<%  
  }
%>
</table>
<br>
<dhv:pagedListControl object="CompanyDirectoryInfo" tdClass="row1"/>
