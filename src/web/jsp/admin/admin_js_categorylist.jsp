<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.troubletickets.base.*, org.aspcfs.utils.StringUtils" %>
<%@ include file="../initPage.jsp" %>
<jsp:useBean id="PermissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<jsp:useBean id="ParentCategory" class="org.aspcfs.modules.troubletickets.base.TicketCategoryDraft" scope="request"/>
<jsp:useBean id="categoryEditor" class="org.aspcfs.modules.admin.base.CategoryEditor" scope="request"/>
<jsp:useBean id="categoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryDraftList" scope="request"/>
<jsp:useBean id="hierarchialUsers" class="java.lang.String" scope="request"/>
<jsp:useBean id="draftAssignment" class="org.aspcfs.modules.troubletickets.base.TicketCategoryDraftAssignment" scope="request"/>
<body onload="page_init();">
<script language="JavaScript">
function newOpt(param, value, color) {
  var newOpt = parent.document.createElement("OPTION");
	newOpt.text=param;
	newOpt.value=value;
  if(color != '-none-'){
    newOpt.style.color =color;
  }
  return newOpt;
}
function page_init() {
  var level = parseInt('<%= request.getParameter("level")%>') + 1;
  var list;
  if (level <= <%=  categoryEditor.getMaxLevels() - 1 %>) {
    list = parent.document.getElementById('level' + level);
    list.options.length = 0;
  <%
    Iterator list1 = categoryList.iterator();
    if (list1.hasNext()) {
      while (list1.hasNext()) {
        TicketCategoryDraft thisCategory = (TicketCategoryDraft)list1.next();
        String elementText = StringUtils.replacePattern(thisCategory.getDescription(), "'", "\\\\'");
    %>
      list.options[list.length] = newOpt('<%= elementText %>', '<%= thisCategory.getId() %>', '<%= !(thisCategory.getEnabled()) ? "Red" : (thisCategory.getActualCatId() == -1 ? "blue" : "-none-") %>');
    <%
      }
    } else {%>
      list.options[list.length] = newOpt(label("option.none","---------None---------"), "-1");
  <%
    }
    // Since level and level + 1 are filled, erase the others
    int thisLevel = Integer.parseInt(request.getParameter("level")) + 2;
    if (thisLevel < categoryEditor.getMaxLevels()) {
      for (int k = thisLevel; k < categoryEditor.getMaxLevels(); k++) {
  %>
      resetList(parent.document.getElementById('level<%= k %>'));
  <%
      }
    }
    //disable the edit button if the parent is disabled
    if (!ParentCategory.getEnabled()) {
  %>
        parent.document.getElementById('edit' + level).disabled = true;
  <%}%>
  }
<dhv:evaluate if="<%= PermissionCategory.getId() == 8 %>">
  // set the assignment related information
    //set the categoryId
  parent.document.getElementById('categoryId').value = '<%= draftAssignment.getCategoryId() %>';
  //set the department
  var department = parent.document.getElementById('departmentId');
  if ('<%= draftAssignment.getDepartmentId() <= 0 %>' == 'true') {
      department.options.selectedIndex = 0;
  } else {
    for (i=0;i<department.options.length;i++) {
      if (department.options[i].value == '<%= draftAssignment.getDepartmentId() %>') {
        department.options.selectedIndex = i;
      }
    }
  }
    //iterate thru the list and set the selected index.
  //set the assignedTo
  var assignedTo = parent.document.getElementById('assignedTo');
  assignedTo.value = '<%= draftAssignment.getAssignedTo() %>';
  parent.document.getElementById('updateAssignedTo').value = 'false';
  if (assignedTo.value == '-1') {
    parent.changeDivContent('changeowner', label('none.selected','None Selected'));
  } else {
    parent.changeDivContent('changeowner', '<%= getUsername(pageContext, draftAssignment.getAssignedTo(),false,false,"&nbsp;") %>');
  }
  
  //set the action plans
  var actionList = parent.document.getElementById('actionPlanList');
  actionList.options.length = 0;
  <%
    if (draftAssignment != null && draftAssignment.getPlanMapList() != null) {
    Iterator list2 = draftAssignment.getPlanMapList().iterator();
    if (list2.hasNext()) {
      while (list2.hasNext()) {
        TicketCategoryDraftPlanMap thisPlanMap = (TicketCategoryDraftPlanMap) list2.next();
        String elementText = StringUtils.replacePattern(thisPlanMap.getPlan().getName(), "'", "\\\\'");
    %>
      actionList.options[actionList.length] = newOpt('<%= elementText %>', '<%= thisPlanMap.getId() %>', '<%= thisPlanMap.getPlan().getEnabled()?(thisPlanMap.getCategoryId() == -1 ? "blue" : "-none-"):"Red" %>');
    <%}
    } else {%>
      resetList(parent.document.getElementById('actionPlanList'));
  <%}} else {%>
      resetList(parent.document.getElementById('actionPlanList'));
  <%}%>
    //also set the category id for the Map button
  //set the user group
  var userGroup = parent.document.getElementById('userGroupId');
  userGroup.value = '<%= draftAssignment.getUserGroupId() %>';
    //also change the div content without updating the draftCategory
  parent.document.getElementById('updateUserGroup').value = 'false';
  if (userGroup.value == '-1') {
    parent.changeDivContent('changeUserGroup', label('none.selected','None Selected'));
  } else {
    parent.changeDivContent('changeUserGroup', '<%= draftAssignment.getUserGroupName() %>');
  }
  </dhv:evaluate>
}
function resetList(list) {
  list.options.length = 0;
  list.options[list.length] = newOpt(label("option.none","---------None---------"), "-1");
}
</script>
</body>
