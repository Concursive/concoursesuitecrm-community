<%-- 
  - Copyright(c) 2004 Team Elements LLC (http://www.teamelements.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Team Elements LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. TEAM ELEMENTS
  - LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL TEAM ELEMENTS LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Author(s): Matt Rajkowski
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.tasks.base.*,com.zeroio.iteam.base.*,org.aspcfs.utils.web.*" %>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="category" class="org.aspcfs.modules.tasks.base.TaskCategory" scope="request"/>
<jsp:useBean id="Task" class="org.aspcfs.modules.tasks.base.Task" scope="request"/>
<jsp:useBean id="PriorityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="document.inputForm.description.focus();">
<script language="JavaScript">
  function checkForm(form) {
    if (form.dosubmit.value == "false") {
      return true;
    }
    var formTest = true;
    var messageText = "";
    //Check required fields
    if (form.description.value == "") {    
      messageText += "- Description is a required field\r\n";
      formTest = false;
    }
    if (formTest == false) {
      messageText = "The form could not be submitted.          \r\nPlease verify the following items:\r\n\r\n" + messageText;
      form.dosubmit.value = "true";
      alert(messageText);
      return false;
    } else {
      return true;
    }
  }
</script>
<form method="POST" name="inputForm" action="ProjectManagementLists.do?command=Save&id=<%= Task.getId() %>&auto-populate=true" onSubmit="return checkForm(this);">
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="subtab">
    <td>
      <img border="0" src="images/icons/stock_list_enum2-16.gif" align="absmiddle">
      <a href="ProjectManagement.do?command=ProjectCenter&section=Lists_Categories&pid=<%= Project.getId() %>"><dhv:label name="project.lists">Lists</dhv:label></a> >
      <a href="ProjectManagement.do?command=ProjectCenter&section=Lists&pid=<%= Project.getId() %>&cid=<%= category.getId() %>"><%= toHtml(category.getDescription()) %></a> >
      <% if (Task.getId() == -1) { %>
      <dhv:label name="button.add">Add</dhv:label>
      <%} else {%>
      <dhv:label name="button.update">Update</dhv:label>
      <%}%>
    </td>
  </tr>
</table>
<br>
  <input type="submit" value="<dhv:label name="global.button.save">Save</dhv:label>" onClick="javascript:this.form.donew.value='false'">
<dhv:evaluate if="<%= Task.getId() == -1 %>">
  <input type="submit" value="<dhv:label name="button.saveAndNew">Save & New</dhv:label>" onClick="javascript:this.form.donew.value='true'">
</dhv:evaluate>
  <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.dosubmit.value='false';this.form.action='ProjectManagement.do?command=ProjectCenter&section=Lists<%= ((category.getId() == -1)?"_Categories":"") %>&pid=<%= Project.getId() %>&cid=<%= category.getId() %>';"><br />
  <dhv:formMessage />
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
      <th colspan="2">
        <dhv:evaluate if="<%= Task.getId() == -1 %>">
          <dhv:label name="product.addItem">Add Item</dhv:label>
        </dhv:evaluate>
        <dhv:evaluate if="<%= Task.getId() != -1 %>">
          <dhv:label name="project.updateItem">Update Item</dhv:label>
        </dhv:evaluate>
      </th>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel"><dhv:label name="accounts.accountasset_include.Description">Description</dhv:label></td>
      <td>
        <input type="text" name="description" size="57" maxlength="255" value="<%= toHtmlValue(Task.getDescription()) %>"><font color=red>*</font> <%= showAttribute(request, "descriptionError") %>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel"><dhv:label name="reports.myhomepage.tasks.priority">Priority</dhv:label></td>
      <td>
        <%= PriorityList.getHtmlSelect("priority",Task.getPriority()) %>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel"><dhv:label name="documents.details.status">Status</dhv:label></td>
      <td>
        <input type="checkbox" name="complete" <%=Task.getComplete()?" checked":""%>> <dhv:label name="reports.myhomepage.tasks.complete">Complete</dhv:label>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel" valign="top"><dhv:label name="accounts.accounts_add.Notes">Notes</dhv:label></td>
      <td>
        <TEXTAREA NAME="notes" ROWS="8" COLS="55"><%= toString(Task.getNotes()) %></TEXTAREA>
      </td>
    </tr>
  </table>
  <br />
  <input type="submit" value="<dhv:label name="global.button.save">Save</dhv:label>" onClick="javascript:this.form.donew.value='false'">
<dhv:evaluate if="<%= Task.getId() == -1 %>">
  <input type="submit" value="<dhv:label name="button.saveAndNew">Save & New</dhv:label>" onClick="javascript:this.form.donew.value='true'">
</dhv:evaluate>
  <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.dosubmit.value='false';this.form.action='ProjectManagement.do?command=ProjectCenter&section=Lists<%= ((category.getId() == -1)?"_Categories":"") %>&pid=<%= Project.getId() %>&cid=<%= category.getId() %>';">
  <input type="hidden" name="pid" value="<%= Project.getId() %>">
  <input type="hidden" name="categoryId" value="<%= category.getId() %>">
  <input type="hidden" name="modified" value="<%= Task.getModified() %>">
  <input type="hidden" name="dosubmit" value="true">
  <input type="hidden" name="donew" value="false">
</form>
</body>
