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
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.troubletickets.base.*,org.aspcfs.modules.contacts.base.Contact, org.aspcfs.utils.web.HtmlOption" %>
<jsp:useBean id="ContactList"
             class="org.aspcfs.modules.contacts.base.ContactList"
             scope="request"/>
<jsp:useBean id="SubmitterList"
             class="org.aspcfs.modules.contacts.base.ContactList"
             scope="request"/>
<jsp:useBean id="OrgDetails"
             class="org.aspcfs.modules.accounts.base.Organization"
             scope="request"/>
<jsp:useBean id="resourceAssignedList"
             class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="resolvedByList" class="org.aspcfs.modules.admin.base.UserList"
             scope="request"/>
<jsp:useBean id="defectList"
             class="org.aspcfs.modules.troubletickets.base.TicketDefectList"
             scope="request"/>
<jsp:useBean id="resourceAssignedSelect" class="org.aspcfs.utils.web.HtmlSelect"
             scope="request"/>
<jsp:useBean id="resolvedBySelect" class="org.aspcfs.utils.web.HtmlSelect"
             scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
             scope="session"/>

<html>
<%@ include file="../initPage.jsp" %>
<body onLoad="javascript:page_init();">
<script language="JavaScript">
<%
  String orgId = request.getParameter("orgId");
  String subId = request.getParameter("subId");
  String populateResourceAssigned = request.getParameter("populateResourceAssigned");
  String resourceAssignedDepartmentCode = request.getParameter("resourceAssignedDepartmentCode");

  String populateResolvedBy = request.getParameter("populateResolvedBy");
  String resolvedByDepartmentCode = request.getParameter("resolvedByDepartmentCode");

  String populateDefects = request.getParameter("populateDefects");
%>
function newOpt(param, value) {
  var newOpt = parent.document.createElement("OPTION");
  newOpt.text = param;
  newOpt.value = value;
  return newOpt;
}
function page_init() {
<% if (!((orgId==null||"-1".equals(orgId))&&(subId!=null&&!"-1".equals(subId))))
{
 %>

  var contactIdWidget = parent.document.forms['addticket'].elements['contactId'];
  contactIdWidget.options.length = 0;
<dhv:evaluate if="<%= (ContactList.size() == 0) %>">
  contactIdWidget.options[contactIdWidget.length] = newOpt(label("option.none", "-- None --"), "-1");
</dhv:evaluate>
<%
  Iterator contactIterator = ContactList.iterator();
  while (contactIterator.hasNext()) {
    Contact thisContact = (Contact)contactIterator.next();
%>
  contactIdWidget.options[contactIdWidget.length] = newOpt("<%= toJavaScript(thisContact.getValidName()) %>", "<%= thisContact.getId() %>");
<%
  }
}
%>

  if (parent.document.forms['addticket'].elements['submitterContactId']) {
    var submitterContactIdWidget = parent.document.forms['addticket'].elements['submitterContactId'];
    submitterContactIdWidget.options.length = 0;
  <dhv:evaluate if="<%= (SubmitterList.size() == 0) %>">
    submitterContactIdWidget.options[submitterContactIdWidget.length] = newOpt(label("option.none", "-- None --"), "-1");
  </dhv:evaluate>
  <%
    Iterator submitterContactIterator = SubmitterList.iterator();
    while (submitterContactIterator.hasNext()) {
      Contact thisSubmitterContact = (Contact)submitterContactIterator.next();
  %>
    submitterContactIdWidget.options[submitterContactIdWidget.length] = newOpt("<%= toJavaScript(thisSubmitterContact.getValidName()) %>", "<%= thisSubmitterContact.getId() %>");
  <%
    }
  %>
  }
<dhv:evaluate if="<%= (!User.getUserRecord().isPortalUser()) %>" >
<dhv:evaluate if='<%= "true".equals(populateResourceAssigned) %>'>
<dhv:evaluate if="<%= ((resourceAssignedList.size() > 0) || (resourceAssignedDepartmentCode != null)) %>">
  var resourceAssignedWidget = parent.document.forms['addticket'].assignedTo;
  resourceAssignedWidget.options.length = 0;
<%
  Iterator resourceAssignedIterator = resourceAssignedSelect.iterator();
  while (resourceAssignedIterator.hasNext()) {
    HtmlOption option = (HtmlOption) resourceAssignedIterator.next();
    int value = Integer.parseInt(option.getValue());
    String text = option.getText();
    if (!"".equals(text.trim())){
%>
  resourceAssignedWidget.options[resourceAssignedWidget.length] = newOpt("<%= text %>", "<%= value %>");
<%
    }
  }
%>
</dhv:evaluate>
</dhv:evaluate>
<dhv:evaluate if='<%= "true".equals(populateResolvedBy) %>'>
<dhv:evaluate if="<%= ((resolvedByList.size() > 0) || (resolvedByDepartmentCode != null)) %>">
  var resolvedByWidget = parent.document.forms['addticket'].resolvedBy;
  resolvedByWidget.options.length = 0;
<%
  Iterator resolvedByIterator = resolvedBySelect.iterator();
  while (resolvedByIterator.hasNext()) {
    HtmlOption option = (HtmlOption) resolvedByIterator.next();
    int value = Integer.parseInt(option.getValue());
    String text = option.getText();
    if (!"".equals(text.trim())) {
%>
  resolvedByWidget.options[resolvedByWidget.length] = newOpt("<%= text%>", "<%= value %>");
<%
    }
  }
%>
</dhv:evaluate>
</dhv:evaluate>
<dhv:evaluate if='<%= "true".equals(populateDefects) %>'>
<dhv:evaluate if="<%= (defectList.size() > 0) %>">
  var defectWidget = parent.document.forms['addticket'].defectId;
  defectWidget.options.length = 0;
  defectWidget.options[defectWidget.length] = newOpt(label("option.none", "-- None --"), "-1");
<%
 Iterator defectIterator = defectList.iterator();
 while (defectIterator.hasNext()) {
   TicketDefect thisDefect = (TicketDefect)defectIterator.next();
   if (thisDefect.getSiteId() == -1) {
%>
  defectWidget.options[defectWidget.length] = newOpt("<%= thisDefect.getTitle() %>", "<%= thisDefect.getId() %>");
<%
   } else {
%>
  defectWidget.options[defectWidget.length] = newOpt("<%= thisDefect.getTitle() +" (" + thisDefect.getSiteName() + ")" %>", "<%= thisDefect.getId() %>");
<%
    }
  }
%>
</dhv:evaluate>
</dhv:evaluate>
  parent.updateCategoryList();
</dhv:evaluate>
}
</script>
</body>
</html>