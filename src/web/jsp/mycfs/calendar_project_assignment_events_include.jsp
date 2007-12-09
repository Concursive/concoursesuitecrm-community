<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  --%>
  
  <%-- draws the project assignment events for a specific day --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="org.aspcfs.modules.mycfs.base.ProjectEventList, org.aspcfs.modules.base.Constants" %>
<%
  ProjectEventList projectEventList = (ProjectEventList) thisDay.get(category);
%>
<%-- include project pending activity events --%>
<dhv:evaluate if="<%= projectEventList.getPendingAssignments().size() > 0 %>">
<table border="0" id="alertprojectdetails<%= toFullDateString(thisDay.getDate()) %>" width="100%">
  <%-- title row --%>
  <tr>
    <td>&nbsp;</td>
    <td colspan="3" nowrap class="eventName">
      <img border="0" src="images/alertassignment.gif" align="absmiddle" title="Projects" />
      <dhv:label name="projects.assignment.alert">Project Assignment Alerts</dhv:label>
      (<%= projectEventList.getPendingAssignments().size() %>)
    </td>
  </tr>
  <%-- include project pending activity details --%>
  <%
    Iterator j = projectEventList.getPendingAssignments().iterator();
    if (j.hasNext()) {
  %>
    <tr>
      <th>
        &nbsp;
      </th>
      <th class="weekSelector">
        <dhv:label name="project.activity"><strong>Activity</strong></dhv:label>
      </th>
      <th class="weekSelector" nowrap>
        <dhv:label name="project.projectName"><strong>Project Name</strong></dhv:label>
      </th>
    </tr>
   <%  
      while(j.hasNext()){
      com.zeroio.iteam.base.Assignment thisAssignment = (com.zeroio.iteam.base.Assignment) j.next();
      menuCount++;
    %>
    <tr>
     <td valign="top">
       <%-- Use the unique id for opening the menu, and toggling the graphics --%>
       <a href="javascript:displayProjectMenu('select-arrow<%= menuCount %>','menuProject', '<%= thisAssignment.getId() %>', '<%= thisAssignment.getProjectId() %>');"
         onMouseOver="over(0, <%= menuCount %>)" onmouseout="out(0, <%= menuCount %>);hideMenu('menuProject');"><img
         src="images/select-arrow.gif" name="select-arrow<%= menuCount %>" id="select-arrow<%= menuCount %>" align="absmiddle" border="0"></a>
     </td>
     <td width="100%" valign="top">
       <%= toHtml(thisAssignment.getRole()) %>
     </td>
     <td nowrap valign="top">
       <%= toHtml(thisAssignment.getProject().getTitle()) %>
     </td>
    </tr>
   <% }
   } %>
</table>
</dhv:evaluate>
