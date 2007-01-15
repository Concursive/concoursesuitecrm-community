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
<%@ page import="java.util.*,org.aspcfs.modules.troubletickets.base.*, org.aspcfs.modules.actionplans.base.*" %>
<%@ page import="org.aspcfs.modules.admin.base.*" %>
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList1" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList2" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList3" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="actionPlans" class="org.aspcfs.modules.actionplans.base.ActionPlanList" scope="request"/>
<jsp:useBean id="assignment" class="org.aspcfs.modules.troubletickets.base.TicketCategoryAssignment" scope="request"/>
<body onload="page_init();">
<script language="JavaScript">
<%
  String reset = request.getParameter("reset");
  String catCode = request.getParameter("catCode");
  String subCat1 = request.getParameter("subCat1");
  String subCat2 = request.getParameter("subCat2");
  String form = request.getParameter("form");
%>
function newOpt(param, value) {
  var newOpt = parent.document.createElement("OPTION");
	newOpt.text=param;
	newOpt.value=value;
  return newOpt;
}
function page_init() {
<dhv:evaluate if="<%= ((CategoryList.size() > 0) || (reset != null && "true".equals(reset.trim()))) %>">
  var list = parent.document.forms['<%= form %>'].elements['catCode'];
  list.options.length = 0;
  list.options[list.length] = newOpt("Undetermined", "0");
<%
  Iterator list1 = CategoryList.iterator();
  while (list1.hasNext()) {
    TicketCategory thisCategory = (TicketCategory)list1.next();
     if (thisCategory.getEnabled()) {
%>
  list.options[list.length] = newOpt("<%= thisCategory.getDescription() %>", "<%= thisCategory.getId() %>");
<%
  }
 }
%>
<dhv:include name="ticket.subCat1" none="true">
  resetList(parent.document.forms['<%= form %>'].elements['subCat1']);
</dhv:include>
<dhv:include name="ticket.subCat2" none="true">
  resetList(parent.document.forms['<%= form %>'].elements['subCat2']);
</dhv:include>
<dhv:include name="ticket.subCat3" none="true">
  resetList(parent.document.forms['<%= form %>'].elements['subCat3']);
</dhv:include>
</dhv:evaluate>

<dhv:evaluate if="<%= ((SubList1.size() > 0) || (catCode != null)) %>">
  var list = parent.document.forms['<%= form %>'].elements['subCat1'];
  list.options.length = 0;
  list.options[list.length] = newOpt("Undetermined", "0");
<%
  Iterator list1 = SubList1.iterator();
  while (list1.hasNext()) {
    TicketCategory thisCategory = (TicketCategory)list1.next();
     if (thisCategory.getEnabled()) {
%>
  list.options[list.length] = newOpt("<%= thisCategory.getDescription() %>", "<%= thisCategory.getId() %>");
<%
  }
 }
%>
<dhv:include name="ticket.subCat2" none="true">
  resetList(parent.document.forms['<%= form %>'].elements['subCat2']);
</dhv:include>
<dhv:include name="ticket.subCat3" none="true">
  resetList(parent.document.forms['<%= form %>'].elements['subCat3']);
</dhv:include>
</dhv:evaluate>
  
<dhv:evaluate if="<%= ((SubList2.size() > 0) || (subCat1 != null)) %>">
  var list2 = parent.document.forms['<%= form %>'].elements['subCat2'];
  list2.options.length = 0;
  list2.options[list2.length] = newOpt("Undetermined", "0");
<%
  Iterator list2 = SubList2.iterator();
  while (list2.hasNext()) {
    TicketCategory thisCategory = (TicketCategory)list2.next();
    String elementText = thisCategory.getDescription();
      if (thisCategory.getEnabled()) {
%>
  list2.options[list2.length] = newOpt("<%= elementText %>", "<%= thisCategory.getId() %>");
<%
  }
 }
%>
<dhv:include name="ticket.subCat3" none="true">
  resetList(parent.document.forms['<%= form %>'].elements['subCat3']);
</dhv:include>
</dhv:evaluate>
  
<dhv:evaluate if="<%= ((SubList3.size() > 0) || (subCat2 != null)) %>">
  var list3 = parent.document.forms['<%= form %>'].elements['subCat3'];
  list3.options.length = 0;
  list3.options[list3.length] = newOpt("Undetermined", "0");
<%
  Iterator list3 = SubList3.iterator();
  while (list3.hasNext()) {
    TicketCategory thisCategory = (TicketCategory)list3.next();
     if (thisCategory.getEnabled()) {
%>
  list3.options[list3.length] = newOpt("<%= thisCategory.getDescription() %>", "<%= thisCategory.getId() %>");
<%  
    }
  }
%>
</dhv:evaluate>
<dhv:include name="tickets.actionPlans" none="true">
  resetActionPlanList(parent.document.forms['<%= form %>'].actionPlanId);
<dhv:evaluate if="<%= actionPlans != null && actionPlans.size() > 0 %>">
  var list4 = parent.document.forms['<%= form %>'].actionPlanId;
  list4.options.length = 0;
  list4.options[list4.length] = newOpt("-- None --", "0");
<%
  Iterator list4 = actionPlans.iterator();
  while (list4.hasNext()) {
    ActionPlan thisPlan = (ActionPlan)list4.next();
%>
  list4.options[list4.length] = newOpt("<%= thisPlan.getName() %>", "<%= thisPlan.getId() %>");
<%  
  }
%>
</dhv:evaluate>
<dhv:evaluate if="<%= actionPlans == null || actionPlans.size() == 0 %>">
  var form = parent.document.forms['<%= form %>'];
  var list4 = parent.document.forms['<%= form %>'].actionPlanId;
  list4.options.length = 0;
  list4.options[list4.length] = newOpt("-- None --", "0");
</dhv:evaluate>
</dhv:include>
//Ticket Category Assignment related code
<dhv:include name="tickets.ticketcategory.assignment" none="true">
var autoSetFieldsValue = parent.document.forms['<%= form %>'].autoSetFields.checked;
if (autoSetFieldsValue) {
  <dhv:evaluate if="<%= assignment != null %>">
  //first set the department.
    var departmentAssignedWidget = parent.document.forms['<%= form %>'].departmentCode;
    for (i=0;i<departmentAssignedWidget.length;i++) {
      if (departmentAssignedWidget.options[i].value == '<%= assignment.getDepartmentId() %>') {
        departmentAssignedWidget.options.selectedIndex = i;
        break;
      }
    }
  //then set the user from the list of users
  <dhv:evaluate if="<%= assignment.getUsers() != null %>">
      var resourceAssignedWidget = parent.document.forms['<%= form %>'].assignedTo;
      resourceAssignedWidget.options.length = 0;
      resourceAssignedWidget.options[resourceAssignedWidget.length] = newOpt(label("option.none","-- None --"), "0");
    <%
      Iterator resourceAssignedIterator = assignment.getUsers().iterator();
      while (resourceAssignedIterator.hasNext()) {
        User thisUser = (User)resourceAssignedIterator.next();
        if (thisUser.getId() != 0) {
          if (thisUser.getSiteId() == -1) {
            if (thisUser.getId() == assignment.getAssignedTo()) {
    %>
      resourceAssignedWidget.options[resourceAssignedWidget.length] = newOpt("<%= thisUser.getContact().getValidName() %>", "<%= thisUser.getId() %>");
      resourceAssignedWidget.options.selectedIndex = resourceAssignedWidget.length-1;
          <%} else {%>
      resourceAssignedWidget.options[resourceAssignedWidget.length] = newOpt("<%= thisUser.getContact().getValidName() %>", "<%= thisUser.getId() %>");
      
    <%
            }
          } else {
            if (thisUser.getId() == assignment.getAssignedTo()) {
    %>
      resourceAssignedWidget.options[resourceAssignedWidget.length] = newOpt("<%= thisUser.getContact().getValidName() +"(" + thisUser.getSiteIdName() + ")"%>", "<%= thisUser.getId() %>");
      resourceAssignedWidget.options.selectedIndex = resourceAssignedWidget.length-1;
          <%} else {%>
      resourceAssignedWidget.options[resourceAssignedWidget.length] = newOpt("<%= thisUser.getContact().getValidName() +"(" + thisUser.getSiteIdName() + ")"%>", "<%= thisUser.getId() %>");
    <%
            }
          }
        }
      }
    %>
  </dhv:evaluate>
  //then set the user group
  parent.document.forms['<%= form %>'].userGroupId.value='<%= assignment.getUserGroupId() %>';
  <dhv:evaluate if="<%= assignment.getUserGroupId() == -1 %>">
    parent.changeDivContent('changeUserGroup', label('none.selected','None Selected'));
  </dhv:evaluate>
  <dhv:evaluate if="<%= assignment.getUserGroupId() != -1 %>">
    parent.changeDivContent('changeUserGroup', '<%= assignment.getUserGroupName() %>');
  </dhv:evaluate>
  </dhv:evaluate>
  }
  </dhv:include>
  <dhv:evaluate if='<%= (assignment.getUsers() == null || assignment.getUsers().size() == 0) && (reset != null && "true".equals(reset.trim())) %>'>
    try {
      parent.updateAllUserLists();
    } catch (oException) {
    }
  </dhv:evaluate>
  <dhv:evaluate if='<%= !((assignment.getUsers() == null || assignment.getUsers().size() == 0) && (reset != null && "true".equals(reset.trim()))) %>'>
    try {
      parent.updateResolvedByUserList();
    } catch(oException) {
    }
  </dhv:evaluate>
}

function resetActionPlanList(list) {
  list.options.length = 0;
  list.options[list.length] = newOpt("-- None --", "0");
}

function resetList(list) {
  list.options.length = 0;
  list.options[list.length] = newOpt("Undetermined", "0");
}
</script>
</body>
