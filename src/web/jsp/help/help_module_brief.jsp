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
<%@ page import="java.util.*,org.aspcfs.modules.help.base.*" %>
<jsp:useBean id="helpModule" class="org.aspcfs.modules.help.base.HelpModule" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body bgcolor='#FFFFFF' onLoad="document.inputForm.description.focus();">
<script language="JavaScript">
  function checkForm(form) {
    var formTest = true;
    //Check required fields
    if (form.description.value == "") {    
      alert("Description is required.");
      formTest = false;
    }
    if (formTest == false) {
      return false;
    } else {
      return true;
    }
  }
</script>
<form method="post" name="inputForm" action="Help.do?command=SaveDescription&auto-populate=true" onSubmit="return checkForm(this);">
  <table cellpadding="4" cellspacing="0" width="100%" class="details">
<tr>
  <th colspan="2">
    <strong>Module Description:<%=helpModule.getRelatedAction()%></strong>
  </th>
</tr>
<tr class="containerBody">
  <td class="formLabel" valign="top">
    Brief Description 
  </td>
  <td>
    <textarea rows="10" name="briefDescription" cols="80"><%= toString(helpModule.getBriefDescription()) %></textarea>
  </td>
</tr>
<tr class="containerBody">
  <td class="formLabel" valign="top">
    Detail Description
  </td>
  <td>
    <textarea rows="18" name="detailDescription" cols="80"><%= toString(helpModule.getDetailDescription()) %></textarea>
  </td>
</tr>
</table><br>
<input type="submit" value="Update" />
<input type="submit" value="Cancel" onClick="javascript:window.close();" />
<input type="hidden" name="id" value="<%= helpModule.getId() %>">
<input type="hidden" name="linkCategoryId" value="<%= helpModule.getLinkCategoryId() %>">
<input type="hidden" name="relatedAction" value="<%= helpModule.getRelatedAction() %>">
<%= addHiddenParams(request, "popup") %>
</form>  
</body>
