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
  - Version: $Id: action_plan_add_note.jsp
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.actionplans.base.*, org.aspcfs.modules.base.Constants"%>
<%@ page import="org.aspcfs.modules.admin.base.*,org.aspcfs.modules.troubletickets.base.*" %>
<jsp:useBean id="actionPlanWork" class="org.aspcfs.modules.actionplans.base.ActionPlanWork" scope="request"/>
<jsp:useBean id="orgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="ticket" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<jsp:useBean id="actionPlanWorkNote" class="org.aspcfs.modules.actionplans.base.ActionPlanWorkNote" scope="request"/>
<jsp:useBean id="actionPlanWorkNoteList" class="org.aspcfs.modules.actionplans.base.ActionPlanWorkNoteList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<body onLoad="document.actionPlan.description.focus();">
<dhv:container name="<%= (ticket != null && ticket.getId() != -1 ?"tickets":"accounts") %>" selected="documents" object="<%= (ticket != null && ticket.getId() != -1 ?"ticket":"orgDetails") %>" param="<%= (ticket != null && ticket.getId() != -1 ?"id=" + ticket.getId():"orgId=" + orgDetails.getOrgId()) %>" hideContainer="true">
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList"> 
  <tr>
    <th align="center"><strong><dhv:label name="documents.documents.submitted">Submitted</dhv:label></strong></th>
    <th width="100%"><strong><dhv:label name="dynamicForm.description">Description</dhv:label></strong></th>
    <th align="center" nowrap><strong><dhv:label name="actionPlan.submittedBy">Submitted By</dhv:label></strong></th>
  </tr>
  <dhv:evaluate if="<%= actionPlanWorkNoteList.size() > 0 %>">
    <%-- Notes exists. Display list of notes --%>
      <% 
        Iterator j = actionPlanWorkNoteList.iterator();
        int rowid = 0;
        int i =0;
        while (j.hasNext()) {
          i++;
          rowid = (rowid != 1 ? 1 : 2);
          ActionPlanWorkNote thisNote = (ActionPlanWorkNote) j.next();
     %>
     <tr class="row<%= rowid %>">
        <td nowrap><zeroio:tz timestamp="<%= thisNote.getSubmitted() %>" default="&nbsp;" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/></td>
        <td><%= toHtml(thisNote.getDescription()) %></td>
        <td align="center"><dhv:username id="<%= thisNote.getSubmittedBy() %>"/></td>
     </tr>
     <%
        }
     %>
  </dhv:evaluate>
  <dhv:evaluate if="<%= actionPlanWorkNoteList.size() == 0 %>">
    <tr class="containerBody">
      <td class="containerBody" colspan="4">
        <dhv:label name="actionplans.noPlanNotesFound">No notes found for this plan</dhv:label>
      </td>
    </tr>
  </dhv:evaluate>
</table>
&nbsp;<br />
<%-- Display Add Note Form or Modify Form--%>
<dhv:formMessage showSpace="false"/>
<form name="actionPlan" action="ActionPlanNotes.do?command=AddNotes&auto-populate=true" method="post">
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
      <th colspan="2"><strong><dhv:label name="actionPlan.addNote">Add Note</dhv:label></strong></th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel"><dhv:label name="dynamicForm.description">Description</dhv:label></td>
    <td><textarea name="description" rows="3" cols="55"><%= toString(actionPlanWorkNote.getDescription()) %></textarea><font color="red">*</font><%= showAttribute(request, "descriptionError") %></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel"><dhv:label name="documents.documents.submitted">Submitted</dhv:label></td>
    <td>
      <zeroio:dateSelect form="actionPlan" field="submitted" timestamp="<%= actionPlanWorkNote.getSubmitted() %>"/>
      <dhv:label name="project.at">at</dhv:label>
      <zeroio:timeSelect baseName="submitted" value="<%= actionPlanWorkNote.getSubmitted() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
      <font color="red">*</font>
      <%= showAttribute(request, "submittedError") %>
    </td>
  </tr>
</table>
&nbsp;<br />
<input type="submit" value="<dhv:label name="global.button.save">Save</dhv:label>" name="Save"/>
<input type="button" value="<dhv:label name="button.close">Close</dhv:label>" onClick="self.close();" />
<input type="hidden" name="orgId" value="<%= orgDetails.getOrgId() %>" />
<input type="hidden" name="planWorkId" value="<%= actionPlanWork.getId() %>" />
<input type="hidden" name="id" value="<%= actionPlanWorkNote.getId() %>" />
</form>
</dhv:container>
