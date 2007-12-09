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
<%@ page import="org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="SCL" class="org.aspcfs.modules.communications.base.SearchCriteriaList" scope="request"/>
<jsp:useBean id="ActionList" class="org.aspcfs.modules.actionlist.base.ActionList" scope="request"/>
<jsp:useBean id="viewUser" class="java.lang.String" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" type="text/javascript" src="javascript/checkString.js"></script>
<SCRIPT LANGUAGE="JavaScript">
  function checkForm(form) {
    formTest = true;
    message = "";
    saveValues();
    if (checkNullString(form.searchCriteriaText.value)) { 
      message += label("check.criteria","- Criteria is required\r\n");
      formTest = false;
    }
    if (formTest == false) {
      alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    }
    return true;
  }
</SCRIPT>
<%
  String returnURL = "MyActionLists.do?command=List&linkModuleId=" + Constants.ACTIONLISTS_CONTACTS;
  if("details".equals(request.getParameter("return"))){
   returnURL = "MyActionContacts.do?command=List&actionId=" + ActionList.getId();
  }
%>
<form name="searchForm" method="post" action="MyActionContacts.do?command=Save&actionId=<%= ActionList.getId() %>" onSubmit="return checkForm(this);">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="MyCFS.do?command=Home"><dhv:label name="My Home Page" mainMenuItem="true">My Home Page</dhv:label></a> >
<a href="MyActionLists.do?command=List&linkModuleId=<%= Constants.ACTIONLISTS_CONTACTS %>"><dhv:label name="myitems.actionLists">Action Lists</dhv:label></a> >
<a href="MyActionContacts.do?command=List&actionId=<%= request.getParameter("actionId") %>"><dhv:label name="actionList.listDetails">List Details</dhv:label></a> >
<dhv:label name="actionList.addContacts">Add Contacts</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<input type="submit" value="<dhv:label name="global.button.save">Save</dhv:label>" />
<input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='<%= returnURL %>'" />
<input type="button" value="<dhv:label name="button.preview">Preview</dhv:label>" onClick="javascript:popPreview()">
<br />
<dhv:formMessage />
<%-- include jsp for contact criteria --%>
<%@ include file="../communications/group_criteria_include.jsp" %>
<br />
<input type="hidden" name="actionId" value="<%= ActionList.getId() %>">
<input type="submit" value="<dhv:label name="global.button.save">Save</dhv:label>" />
<input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='<%= returnURL %>'" />
<input type="button" value="<dhv:label name="button.preview">Preview</dhv:label>" onClick="javascript:popPreview()">
</form>
