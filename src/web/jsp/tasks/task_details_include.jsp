<jsp:useBean id="PriorityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="EstimatedLOETypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
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
      <%= toHtmlValue(Task.getDescription()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Due Date
    </td>
    <td>
      <%= !"".equals(toString(Task.getDueDateString())) ?  toString(Task.getDueDateString()) : "&nbsp;" %>
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
      <% if(Task.getComplete()){ %>
        <img src="images/box-checked.gif">
      <% }else{ %>
        Pending
      <% } %>
    </td>
  </tr>
  <tr class="containerBody"> 
    <td class="formLabel">Sharing</td>
    <td>
      <table cellpadding="3" cellspacing="0" class="empty">
        <tr>
          <td>
            <input type="checkbox" name="chk2" <%= (Task.getSharing()==1)?" checked":"" %>>
          </td>
          <td>personal</td>
        </tr>
      </table>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top">
      Assigned To
    </td>
    <td>
         <dhv:username id="<%= Task.getOwner() %>"/>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top">
      Estimated LOE
    </td>
    <td>
      <%= EstimatedLOETypeList.getHtmlSelect("estimatedLOEType",Task.getEstimatedLOEType()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td valign="top" nowrap class="formLabel">Notes</td>
    <td>
      <%= !"".equals(toString(Task.getNotes())) ?  toString(Task.getNotes()) : "&nbsp;"%>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top">
      Link Contact
    </td>
    <td>
         <%= Task.getContactName()!=null?Task.getContactName():"None"%>
    </td>
  </tr>
</table>
