<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="EmployeeList" class="com.darkhorseventures.cfsbase.ContactList" scope="request"/>
<jsp:useBean id="CompanyDirectoryInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<%@ include file="initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></script>
<dhv:permission name="contacts-internal_contacts-add"><a href="/CompanyDirectory.do?command=InsertEmployeeForm">Add an Employee</a></dhv:permission>
<center><%= CompanyDirectoryInfo.getAlphabeticalPageLinks() %></center>
<%= showAttribute(request, "actionError") %>
<%
  Iterator i = EmployeeList.iterator();
  if (i.hasNext()) {
    int rowid = 0;
%>
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td valign=center align=left>
      <strong>Action</strong>
    </td>
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
      <a href="/CompanyDirectory.do?command=ListEmployees&column=title">
        <strong>Title</strong>
      </a>
      <%= CompanyDirectoryInfo.getSortIcon("title") %>
    </td>
    <td nowrap>
      <strong>Business Phone</strong>
    </td>
  </tr>
<%    
    while (i.hasNext()) {
      if (rowid != 1) {
        rowid = 1;
      } else {
        rowid = 2;
      }
      Contact thisEmployee = (Contact)i.next();
%>      
      <tr>
        <td width=8 valign=center nowrap class="row<%= rowid %>">
          <a href="/CompanyDirectory.do?command=EmployeeDetails&empid=<%= thisEmployee.getId() %>&action=modify&return=list">Edit</a>|<a href="javascript:confirmDelete('/CompanyDirectory.do?command=DeleteEmployee&empid=<%= thisEmployee.getId() %>');">Del</a>
        </td>
        <td class="row<%= rowid %>"><font class="columntext1">
          <a href="/CompanyDirectory.do?command=EmployeeDetails&empid=<%= thisEmployee.getId() %>"><%= toHtml(thisEmployee.getNameLast()) %>, <%= toHtml(thisEmployee.getNameFirst()) %></a></font>
          <%= thisEmployee.getEmailAddressTag("Business", "<img border=0 src=\"images/email.gif\" alt=\"Send email\" align=\"absmiddle\">", "") %>
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
%>
</table><br>
[<%= CompanyDirectoryInfo.getPreviousPageLink("<font class='underline'>Previous</font>", "Previous") %> <%= CompanyDirectoryInfo.getNextPageLink("<font class='underline'>Next</font>", "Next") %>]  <%= CompanyDirectoryInfo.getNumericalPageLinks() %>
<%
  } else {
%>  
No Employees found.
<%  
  }
%>
