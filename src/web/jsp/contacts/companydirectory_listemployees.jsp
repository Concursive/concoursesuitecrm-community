<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="EmployeeList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="CompanyDirectoryInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="companydirectory_listemployees_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="CompanyDirectory.do?command=ListEmployees"><dhv:label name="employees.employees">Employees</dhv:label></a> >
<dhv:label name="employees.view">View Employees</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:permission name="contacts-internal_contacts-add"><a href="CompanyDirectory.do?command=Prepare"><dhv:label name="employees.add">Add an Employee</dhv:label></a></dhv:permission>
<dhv:permission name="contacts-internal_contacts-add" none="true"><br></dhv:permission>
<center><%= CompanyDirectoryInfo.getAlphabeticalPageLinks() %></center>
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="CompanyDirectoryInfo"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th>
      <strong>Action</strong>
    </th>
    <th nowrap>
      <a href="CompanyDirectory.do?command=ListEmployees&column=c.namelast">
        <strong>Name</strong>
      </a>
      <%= CompanyDirectoryInfo.getSortIcon("c.namelast") %>
    </th>
    <th nowrap>
      <a href="CompanyDirectory.do?command=ListEmployees&column=departmentname,c.namelast">
        <strong>Department</strong>
      </a>
      <%= CompanyDirectoryInfo.getSortIcon("departmentname,c.namelast") %>
    </th>
    <th nowrap>
      <a href="CompanyDirectory.do?command=ListEmployees&column=c.title,c.namelast">
        <strong>Title</strong>
      </a>
      <%= CompanyDirectoryInfo.getSortIcon("c.title,c.namelast") %>
    </th nowrap>
    <th nowrap>
      <strong>Business Phone</strong>
    </th>
  </tr>
<%
  Iterator i = EmployeeList.iterator();
  if (i.hasNext()) {
    int rowid = 0;
    int count =0;
    while (i.hasNext()) {
      count++;
      rowid = (rowid != 1?1:2);
      Contact thisEmployee = (Contact)i.next();
%>      
  <tr>
      <td width="8" valign="center" class="row<%= rowid %>" nowrap>
        <%-- Use the unique id for opening the menu, and toggling the graphics --%>
        <a href="javascript:displayMenu('select<%= count %>','menuEmployee','<%= thisEmployee.getId() %>');" onMouseOver="over(0, <%= count %>)" onmouseout="out(0, <%= count %>); hideMenu('menuEmployee');"><img src="images/select.gif" name="select<%= count %>" id="select<%= count %>" align="absmiddle" border="0"></a>
      </td>
    <td class="row<%= rowid %>" nowrap>
      <a href="CompanyDirectory.do?command=EmployeeDetails&empid=<%= thisEmployee.getId() %>"><%= toHtml(thisEmployee.getNameLastFirst()) %></a>
      <dhv:evaluate exp="<%=!(thisEmployee.hasEnabledAccount())%>"><font color="red">*</font></dhv:evaluate>          
        <%= thisEmployee.getEmailAddressTag("Business", "<img border=0 src=\"images/icons/stock_mail-16.gif\" alt=\"Send email\" align=\"absmiddle\">", "&nbsp;") %>
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
      <dhv:label name="employees.search.notFound">No Employees found.</dhv:label>
    </td>
  </tr>
<%  
  }
%>
</table>
<br>
<dhv:pagedListControl object="CompanyDirectoryInfo" tdClass="row1"/>
