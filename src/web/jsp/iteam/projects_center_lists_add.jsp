<%-- 
  - Copyright Notice: (C) 2000-2004 Team Elements, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Team Elements. This notice must remain in
  -          place.
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
      <a href="ProjectManagement.do?command=ProjectCenter&section=Lists_Categories&pid=<%= Project.getId() %>">Lists</a> >
      <a href="ProjectManagement.do?command=ProjectCenter&section=Lists&pid=<%= Project.getId() %>&cid=<%= category.getId() %>"><%= toHtml(category.getDescription()) %></a> >
      <%= Task.getId() == -1 ? "Add" : "Update" %>
    </td>
  </tr>
</table>
<br>
  <input type="submit" value=" Save " onClick="javascript:this.form.donew.value='false'">
<dhv:evaluate if="<%= Task.getId() == -1 %>">
  <input type="submit" value="Save & New" onClick="javascript:this.form.donew.value='true'">
</dhv:evaluate>
  <input type="submit" value="Cancel" onClick="javascript:this.form.dosubmit.value='false';this.form.action='ProjectManagement.do?command=ProjectCenter&section=Lists<%= ((category.getId() == -1)?"_Categories":"") %>&pid=<%= Project.getId() %>&cid=<%= category.getId() %>';"><br />
  <dhv:formMessage />
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
      <th colspan="2">
        <strong><%= Task.getId()==-1?"Add":"Update" %> Item</strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">Description</td>
      <td>
        <input type="text" name="description" size="57" maxlength="80" value="<%= toHtmlValue(Task.getDescription()) %>"><font color=red>*</font> <%= showAttribute(request, "descriptionError") %>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">Priority</td>
      <td>
        <%= PriorityList.getHtmlSelect("priority",Task.getPriority()) %>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">Status</td>
      <td>
        <input type="checkbox" name="complete" <%=Task.getComplete()?" checked":""%>> Complete
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel" valign="top">Notes</td>
      <td>
        <TEXTAREA NAME="notes" ROWS="8" COLS="55"><%= toString(Task.getNotes()) %></TEXTAREA>
      </td>
    </tr>
  </table>
  <br>
  <input type="submit" value=" Save " onClick="javascript:this.form.donew.value='false'">
<dhv:evaluate if="<%= Task.getId() == -1 %>">
  <input type="submit" value="Save & New" onClick="javascript:this.form.donew.value='true'">
</dhv:evaluate>
  <input type="submit" value="Cancel" onClick="javascript:this.form.dosubmit.value='false';this.form.action='ProjectManagement.do?command=ProjectCenter&section=Lists<%= ((category.getId() == -1)?"_Categories":"") %>&pid=<%= Project.getId() %>&cid=<%= category.getId() %>';">
  <input type="hidden" name="pid" value="<%= Project.getId() %>">
  <input type="hidden" name="categoryId" value="<%= category.getId() %>">
  <input type="hidden" name="modified" value="<%= Task.getModified() %>">
  <input type="hidden" name="dosubmit" value="true">
  <input type="hidden" name="donew" value="false">
</form>
</body>
