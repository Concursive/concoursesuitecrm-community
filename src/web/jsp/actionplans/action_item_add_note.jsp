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
  - Version: $Id: action_item_add_note.jsp
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.actionplans.base.*, org.aspcfs.modules.base.Constants"%>
<%@ page import="org.aspcfs.modules.admin.base.*" %>
<jsp:useBean id="actionItemWork" class="org.aspcfs.modules.actionplans.base.ActionItemWork" scope="request"/>
<jsp:useBean id="orgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="actionItemWorkNote" class="org.aspcfs.modules.actionplans.base.ActionItemWorkNote" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="status" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript">
  function setActionPlanDetails() {
    var itemId = '<%= actionItemWork.getId() %>';
    var displayId = "changenote" + itemId;
    opener.document.getElementById('noteid').value="<%= actionItemWorkNote.getId() %>";
    opener.changeDivContent(displayId, '<zeroio:tz timestamp="<%=  actionItemWorkNote.getSubmitted() %>" timeZone="<%= User.getUserRecord().getTimeZone() %>" dateOnly="true"/>');
    opener.attachNote(itemId);
    <dhv:evaluate if="<%= actionItemWork.getActionId() == ActionStep.ATTACH_NOTE_SINGLE %>">
      self.close();
    </dhv:evaluate>
  }
</script>
<dhv:evaluate if="<%= status != null && "true".equals(status) %>">
  <body onLoad="javascript:setActionPlanDetails();document.actionStep.description.focus();">
</dhv:evaluate>
<dhv:evaluate if="<%= status != null && !"true".equals(status) %>">
  <body onLoad="document.actionStep.description.focus();">
</dhv:evaluate>
<dhv:container name="accounts" selected="documents" object="orgDetails" param="<%= "orgId=" + orgDetails.getOrgId() %>" hideContainer="true" appendToUrl="<%= addLinkParams(request, "popup|popupType|actionId") %>">
<dhv:evaluate if="<%= actionItemWork.getActionId() == ActionStep.ATTACH_NOTE_SINGLE %>">
  <%-- Single Note Allowed --%>
  <table class="note" cellspacing="0">
    <tr class="containerBody">
      <th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
      <td><strong><%= toHtml(actionItemWork.getStepDescription()) %></strong> <dhv:label name="actionplans.singleNote.text">allows single note to be attached</dhv:label></td>
    </tr>
  </table>
  <dhv:evaluate if="<%= actionItemWork.getNote() != null %>">
    <%-- Note already exists. Display the note --%>
    <table cellpadding="4" cellspacing="0" width="100%" class="details">
      <tr>
        <th colspan="2"><strong><dhv:label name="project.details">Details</dhv:label></strong></th>
      </tr>
      <tr class="containerBody">
        <td class="formLabel"><dhv:label name="dynamicForm.description">Description</dhv:label></td>
        <td><%= toHtml(actionItemWork.getNote().getDescription()) %></td>
      </tr>
      <tr class="containerBody">
        <td class="formLabel"><dhv:label name="documents.documents.submitted">Submitted</dhv:label></td>
        <td><zeroio:tz timestamp="<%= actionItemWork.getNote().getSubmitted() %>" default="&nbsp;" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/></td>
      </tr>
      <tr class="containerBody">
        <td class="formLabel"><dhv:label name="actionPlan.submittedBy">Submitted By</dhv:label></td>
        <td><dhv:username id="<%= actionItemWork.getNote().getSubmittedBy() %>"/></td>
      </tr>
    </table>
    &nbsp;<br />
  </dhv:evaluate>
</dhv:evaluate>
<dhv:evaluate if="<%= actionItemWork.getActionId() == ActionStep.ATTACH_NOTE_MULTIPLE %>">
  <%-- Multiple Notes Allowed --%>
  <table class="note" cellspacing="0">
    <tr class="containerBody">
      <th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
      <td><strong><%= toHtml(actionItemWork.getStepDescription()) %></strong> <dhv:label name="actionplans.multipleNotes.text">allows multiple notes to be attached</dhv:label></td>
    </tr>
  </table>
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList"> 
      <tr>
        <th align="center"><strong><dhv:label name="documents.documents.submitted">Submitted</dhv:label></strong></th>
        <th width="100%"><strong><dhv:label name="dynamicForm.description">Description</dhv:label></strong></th>
        <th align="center" nowrap><strong><dhv:label name="actionPlan.submittedBy">Submitted By</dhv:label></strong></th>
      </tr>
      <dhv:evaluate if="<%= actionItemWork.getNoteList() != null %>">
        <%-- Notes exists. Display list of notes --%>
          <% 
            Iterator j = actionItemWork.getNoteList().iterator();
            int rowid = 0;
            int i =0;
            while (j.hasNext()) {
              i++;
              rowid = (rowid != 1 ? 1 : 2);
              ActionItemWorkNote thisNote = (ActionItemWorkNote) j.next();
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
      <dhv:evaluate if="<%= actionItemWork.getNoteList() == null %>">
        <tr class="containerBody">
          <td class="containerBody" colspan="4">
            <dhv:label name="actionplans.noNotesFound">No notes found with this step</dhv:label>
          </td>
        </tr>
      </dhv:evaluate>
  </table>
  &nbsp;<br />
</dhv:evaluate>
<%-- Display Add Note Form or Modify Form--%>
<dhv:formMessage showSpace="false"/>
<form name="actionStep" action="ActionPlans.do?command=AttachNote&auto-populate=true" method="post">
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <zeroio:debug value="<%= "actionId = " + actionItemWork.getActionId() %>" />
    <dhv:evaluate if="<%= actionItemWork.getActionId() == ActionStep.ATTACH_NOTE_SINGLE && actionItemWork.getNote() != null %>">
      <th colspan="2"><strong>Modify Note</strong></th>
    </dhv:evaluate>
    <dhv:evaluate if="<%= actionItemWork.getActionId() == ActionStep.ATTACH_NOTE_SINGLE && actionItemWork.getNote() == null %>">
      <th colspan="2"><strong>Add Note</strong></th>
    </dhv:evaluate>
    <dhv:evaluate if="<%= actionItemWork.getActionId() == ActionStep.ATTACH_NOTE_MULTIPLE %>">
      <th colspan="2"><strong>Add Note</strong></th>
    </dhv:evaluate>
  </tr>
  <tr class="containerBody">
    <td class="formLabel"><dhv:label name="dynamicForm.description">Description</dhv:label></td>
    <dhv:evaluate if="<%= actionItemWork.getActionId() == ActionStep.ATTACH_NOTE_SINGLE && actionItemWork.getNote() != null %>">
      <dhv:evaluate if="<%= hasText(actionItemWorkNote.getDescription()) %>">
        <td><textarea name="description" rows="3" cols="55"><%= toString(actionItemWorkNote.getDescription()) %></textarea>
      </dhv:evaluate>
      <dhv:evaluate if="<%= !hasText(actionItemWorkNote.getDescription()) %>">
        <td><textarea name="description" rows="3" cols="55"><%= toString(actionItemWork.getNote().getDescription()) %></textarea>
      </dhv:evaluate>
    </dhv:evaluate>
    <dhv:evaluate if="<%= actionItemWork.getActionId() == ActionStep.ATTACH_NOTE_SINGLE && actionItemWork.getNote() == null %>">
      <td><textarea name="description" rows="3" cols="55"><%= toString(actionItemWorkNote.getDescription()) %></textarea>
    </dhv:evaluate>
    <dhv:evaluate if="<%= actionItemWork.getActionId() == ActionStep.ATTACH_NOTE_MULTIPLE %>">
      <td><textarea name="description" rows="3" cols="55"><%= toString(actionItemWorkNote.getDescription()) %></textarea>
    </dhv:evaluate>
      <font color="red">*</font><%= showAttribute(request, "descriptionError") %></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel"><dhv:label name="documents.documents.submitted">Submitted</dhv:label></td>
    <dhv:evaluate if="<%= actionItemWork.getActionId() == ActionStep.ATTACH_NOTE_SINGLE && actionItemWork.getNote() != null %>">
      <td>
        <zeroio:dateSelect form="actionStep" field="submitted" timestamp="<%= actionItemWork.getNote().getSubmitted() %>"/>
        <dhv:label name="project.at">at</dhv:label>
        <zeroio:timeSelect baseName="submitted" value="<%= actionItemWork.getNote().getSubmitted() %>" />
        <font color="red">*</font>
        <%= showAttribute(request, "submittedError") %>
      </td>
    </dhv:evaluate>
    <dhv:evaluate if="<%= actionItemWork.getActionId() == ActionStep.ATTACH_NOTE_SINGLE && actionItemWork.getNote() == null %>">
      <td>
        <zeroio:dateSelect form="actionStep" field="submitted" timestamp="<%= actionItemWorkNote.getSubmitted() %>"/>
       <dhv:label name="project.at">at</dhv:label>
        <zeroio:timeSelect baseName="submitted" value="<%= actionItemWorkNote.getSubmitted() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
        <font color="red">*</font>
        <%= showAttribute(request, "submittedError") %>
      </td>
    </dhv:evaluate>
    <dhv:evaluate if="<%= actionItemWork.getActionId() == ActionStep.ATTACH_NOTE_MULTIPLE %>">
      <td>
        <zeroio:dateSelect form="actionStep" field="submitted" timestamp="<%= actionItemWorkNote.getSubmitted() %>"/>
       <dhv:label name="project.at">at</dhv:label>
        <zeroio:timeSelect baseName="submitted" value="<%= actionItemWorkNote.getSubmitted() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
        <font color="red">*</font>
        <%= showAttribute(request, "submittedError") %>
      </td>
    </dhv:evaluate>
  </tr>
</table>
&nbsp;<br />
<input type="submit" value="<dhv:label name="global.button.save">Save</dhv:label>" name="Save"/>
<input type="button" value="<dhv:label name="button.close">Close</dhv:label>" onClick="self.close();" />
<input type="hidden" name="orgId" value="<%= orgDetails.getOrgId() %>" />
<input type="hidden" name="itemWorkId" value="<%= actionItemWork.getId() %>" />
<input type="hidden" name="actionId" value="<%= actionItemWork.getActionId() %>" />
<dhv:evaluate if="<%= actionItemWork.getNote() != null %>">
  <input type="hidden" name="id" value="<%= actionItemWork.getNote().getId() %>" />
</dhv:evaluate>
<input type="hidden" name="count" value="<%= actionItemWork.getNote() != null ? 1 : 0 %>" />
<input type="hidden" name="source" value="<%= request.getParameter("source") %>" />
</form>
</dhv:container>
<dhv:evaluate if="<%= status != null && !"true".equals(status) %>">
  </body>
</dhv:evaluate>
<dhv:evaluate if="<%= status != null && "true".equals(status) %>">
  </body>
</dhv:evaluate>
