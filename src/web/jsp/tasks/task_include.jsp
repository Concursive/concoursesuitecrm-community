<%@ page import="java.text.DateFormat" %>
<jsp:useBean id="PriorityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="EstimatedLOETypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<script language="JavaScript" type="text/javascript" src="javascript/popContacts.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></script>
<SCRIPT language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/tasks.js"></script>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Task</strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Description
    </td>
    <td>
      <input type="text" name="description" value="<%= toHtmlValue(Task.getDescription()) %>" size="50" maxlength="80">
      <font color="red">*</font> <%= showAttribute(request, "descriptionError") %>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      Due Date
    </td>
    <td>
      <input type="text" size="10" name="dueDate" value="<dhv:tz timestamp="<%= Task.getDueDate() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>"/>">
      <a href="javascript:popCalendar('addTask', 'dueDate');"><img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle" height="16" width="16"/></a> (mm/dd/yyyy)
      <%= showAttribute(request, "dueDateError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">Priority</td>
    <td>
      <%= PriorityList.getHtmlSelect("priority",Task.getPriority()) %>
    </td>
  </tr>
  <tr class="containerBody"> 
    <td class="formLabel">Status</td>
    <td>
      <table cellpadding="3" cellspacing="0" class="empty">
        <tr>
          <td>
            <input type="checkbox" name="chk1" value="true" onclick="javascript:setField('complete',document.addTask.chk1.checked,'addTask');" <%= Task.getComplete()?" checked":"" %>>
          </td>
          <input type="hidden" name="complete" value="<%= Task.getComplete()?"1":"0" %>">
          <input type="hidden" name="modified" value="<%= Task.getModified() %>">
          <td>Complete</td>
        </tr>
      </table>
    </td>
  </tr>
  <tr class="containerBody"> 
    <td class="formLabel">Sharing</td>
    <td>
      <table cellpadding="3" cellspacing="0" class="empty">
        <tr>
          <td>
            <input type="checkbox" name="chk2" onclick="javascript:setField('sharing',document.addTask.chk2.checked,'addTask');" <%= (Task.getSharing()==1)?" checked":"" %>>
            <input type="hidden" name="sharing" value="<%= Task.getSharing() %>">
          </td>
          <td>personal</td>
        </tr>
      </table>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top">
      Assign To
    </td>
    <td>
      <table class="empty">
        <tr>
          <td>
            <div id="changeowner">
            <%if(Task.getOwner() > 0){ %>
              <dhv:username id="<%= Task.getOwner() %>"/>
            <% }else{ %>
               <dhv:username id="<%= User.getUserId() %>"/>
            <%}%>
            </div>
          </td>
          <td>
            <input type="hidden" name="owner" id="ownerid" value="<%= Task.getOwner() == -1 ? User.getUserRecord().getId() : Task.getOwner() %>">
            &nbsp;[<a href="javascript:popContactsListSingle('ownerid','changeowner', 'usersOnly=true&reset=true');">Change Owner</a>]
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top">
      Estimated LOE
    </td>
    <td>
      <input type="text" size="4" name="estimatedLOE" value="<%= Task.getEstimatedLOEValue() %>">
      &nbsp;<%= EstimatedLOETypeList.getHtmlSelect("estimatedLOEType",Task.getEstimatedLOEType()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td valign="top" nowrap class="formLabel">Notes</td>
    <td>
      <TEXTAREA NAME="notes" ROWS="3" COLS="50"><%= toString(Task.getNotes()) %></TEXTAREA>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top">
      Link Contact
    </td>
    <td>
      <table class="empty">
        <tr>
          <td>
            <div id="changecontact"><%=Task.getContactName()!=null?Task.getContactName():"None"%></div>
          </td>
          <td>
            <input type="hidden" name="contact" id="contactid" value="<%=(Task.getContactId() == -1)?-1:Task.getContactId()%>">
            &nbsp;[<a href="javascript:popContactsListSingle('contactid','changecontact', 'reset=true');">Change Contact</a>]
          </td>
          <td>
            [<a href="javascript:document.addTask.contact.value='-1';javascript:changeDivContent('changecontact','None');">Clear Contact</a>]
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<%= addHiddenParams(request, "popup|popupType|actionId") %> 
