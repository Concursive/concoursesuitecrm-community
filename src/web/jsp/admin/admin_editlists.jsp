<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="com.darkhorseventures.cfsbase.PermissionCategory" %>
<jsp:useBean id="ContactTypeList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="RevenueTypeList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="AccountTypeList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="ContactEmailTypeList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="ContactPhoneTypeList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="ContactAddressTypeList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="DepartmentList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="SourceList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="PriorityList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="SeverityList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="StageList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="OpportunityTypeList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="PermissionCategory" class="com.darkhorseventures.cfsbase.PermissionCategory" scope="request"/>

<a href="Admin.do">Setup</a> >
<a href="Admin.do?command=Manage">System Management</a> >
<a href="Admin.do?command=Config">Configure Modules</a> >
<a href="Admin.do?command=ConfigDetails&moduleId=<%=PermissionCategory.getId()%>">Configuration Options</a> >
Lookup Lists<br> 
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td width="35" valign=center align=center bgcolor="#DEE0FA">
      <strong>Action</strong>
    </td>
    <td width="200" valign=center align=left>
      <strong>List Name</strong>
    </td>
    <td width="35" valign=center align=center>
      <strong>Items</strong>
    </td>
    <td valign=center align=left>
      <strong>Preview</strong>
    </td>
    
  </tr>

<% if (PermissionCategory.getId() == PermissionCategory.PERMISSION_CAT_LEADS) { %>

  <tr>
    <dhv:permission name="admin-sysconfig-lists-edit"><td align="center"><a href="/Admin.do?command=ModifyList&module=<%=PermissionCategory.getId()%>&sublist=1">Edit</a></td></dhv:permission>
    <td valign="center" width=200>Stage</td>
    <td width="35" valign="center" align="center"><%=StageList.getEnabledElementCount()%></td>
    <td valign="center"><%= StageList.getHtmlSelect("stage",0) %></td>
  </tr>
  
  <tr>
    <dhv:permission name="admin-sysconfig-lists-edit"><td align="center"><a href="/Admin.do?command=ModifyList&module=<%=PermissionCategory.getId()%>&sublist=2">Edit</a></td></dhv:permission>
    <td valign="center" width=200>Opportunity Type</td>
    <td width="35" valign="center" align="center"><%=OpportunityTypeList.getEnabledElementCount()%></td>
    <td valign="center"><%= OpportunityTypeList.getHtmlSelect("oppTypeList",0) %></td>
  </tr>  
  
<%} else if (PermissionCategory.getId() == PermissionCategory.PERMISSION_CAT_ACCOUNTS) { %>
  <tr>
    <dhv:permission name="admin-sysconfig-lists-edit"><td align="center"><a href="/Admin.do?command=ModifyList&module=<%=PermissionCategory.getId()%>&sublist=1">Edit</a></td></dhv:permission>
    <td valign="center" width=200>Account Type</td>
    <td width="35" valign="center" align="center"><%=AccountTypeList.getEnabledElementCount()%></td>
    <td valign="center" ><%=AccountTypeList.getHtmlSelect("typeId",0)%></td>
  </tr>
  <tr>
    <dhv:permission name="admin-sysconfig-lists-edit"><td align="center"><a href="/Admin.do?command=ModifyList&module=<%=PermissionCategory.getId()%>&sublist=2">Edit</a></td></dhv:permission>
    <td valign="center" width=200>Revenue Type</td>
    <td width="35" valign="center" align="center"><%=RevenueTypeList.getEnabledElementCount()%></td>
    <td valign="center"><%=RevenueTypeList.getHtmlSelect("typeId",0)%></td>
  </tr>
<%} else if (PermissionCategory.getId() == PermissionCategory.PERMISSION_CAT_CONTACTS) { %>
  <tr>
    <dhv:permission name="admin-sysconfig-lists-edit"><td align="center"><a href="/Admin.do?command=ModifyList&module=<%=PermissionCategory.getId()%>&sublist=1">Edit</a></td></dhv:permission>
    <td valign="center" width=200>Contact Type</td>
    <td width="35" valign="center" align="center"><%=ContactTypeList.getEnabledElementCount()%></td>
    <td valign="center"><%=ContactTypeList.getHtmlSelect("typeId",0)%></td>
  </tr>
  <tr>
    <dhv:permission name="admin-sysconfig-lists-edit"><td align="center"><a href="/Admin.do?command=ModifyList&module=<%=PermissionCategory.getId()%>&sublist=2">Edit</a></td></dhv:permission>
    <td valign="center" width=200>Contact Email Type</td>
    <td width="35" valign="center" align="center"><%=ContactEmailTypeList.getEnabledElementCount()%></td>
    <td valign="center"><%= ContactEmailTypeList.getHtmlSelect("contactEmailTypes",0) %></td>
  </tr>
  
  <tr>
    <dhv:permission name="admin-sysconfig-lists-edit"><td align="center"><a href="/Admin.do?command=ModifyList&module=<%=PermissionCategory.getId()%>&sublist=3">Edit</a></td></dhv:permission>
    <td valign="center" width=200>Contact Address Type</td>
    <td width="35" valign="center" align="center"><%=ContactAddressTypeList.getEnabledElementCount()%></td>
    <td valign="center"><%= ContactAddressTypeList.getHtmlSelect("contactAddressTypes",0) %></td>
  </tr>  
  
  <tr>
    <dhv:permission name="admin-sysconfig-lists-edit"><td align="center"><a href="/Admin.do?command=ModifyList&module=<%=PermissionCategory.getId()%>&sublist=4">Edit</a></td></dhv:permission>
    <td valign="center" width=200>Contact Phone Type</td>
    <td width="35" valign="center" align="center"><%=ContactPhoneTypeList.getEnabledElementCount()%></td>
    <td valign="center"><%= ContactPhoneTypeList.getHtmlSelect("contactPhoneTypes",0) %></td>
  </tr>   
  
  <tr>
    <dhv:permission name="admin-sysconfig-lists-edit"><td align="center"><a href="/Admin.do?command=ModifyList&module=<%=PermissionCategory.getId()%>&sublist=5">Edit</a></td></dhv:permission>
    <td valign="center" width=200>Department</td>
    <td width="35" valign="center" align="center"><%=DepartmentList.getEnabledElementCount()%></td>
    <td valign="center"><%= DepartmentList.getHtmlSelect("department",0) %></td>
  </tr>     
  
<%} else if (PermissionCategory.getId() == PermissionCategory.PERMISSION_CAT_TICKETS) { %>
  <tr>
    <dhv:permission name="admin-sysconfig-lists-edit"><td align="center"><a href="/Admin.do?command=ModifyList&module=<%=PermissionCategory.getId()%>&sublist=1">Edit</a></td></dhv:permission>
    <td valign="center" width=200>Ticket Source</td>
    <td width="35" valign="center" align="center"><%=SourceList.getEnabledElementCount()%></td>
    <td valign="center"><%= SourceList.getHtmlSelect("sourceCode",0) %></td>
  </tr>    
  
  <tr>
    <dhv:permission name="admin-sysconfig-lists-edit"><td align="center"><a href="/Admin.do?command=ModifyList&module=<%=PermissionCategory.getId()%>&sublist=2">Edit</a></td></dhv:permission>
    <td valign="center" width=200>Ticket Severity</td>
    <td width="35" valign="center" align="center"><%=SeverityList.getEnabledElementCount()%></td>
    <td valign="center"><%= SeverityList.getHtmlSelect("severityCode",0) %></td>
  </tr>  
  
  <tr>
    <dhv:permission name="admin-sysconfig-lists-edit"><td align="center"><a href="/Admin.do?command=ModifyList&module=<%=PermissionCategory.getId()%>&sublist=3">Edit</a></td></dhv:permission>
    <td valign="center" width=200>Ticket Priority</td>
    <td width="35" valign="center" align="center"><%=PriorityList.getEnabledElementCount()%></td>
    <td valign="center"><%= PriorityList.getHtmlSelect("priorityCode",0) %></td>
  </tr>    
<%} else {%>
  <tr>
    <td valign="center" colspan=4>No custom lookup lists to configure.</td>
  </tr>  
<%}%>

</table>

