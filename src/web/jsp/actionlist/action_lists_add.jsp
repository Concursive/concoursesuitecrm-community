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
<%@ page import="java.util.*,org.aspcfs.modules.actionlist.base.*, org.aspcfs.modules.base.Constants"%>
<jsp:useBean id="ActionList" class="org.aspcfs.modules.actionlist.base.ActionList" scope="request"/>
<jsp:useBean id="SCL" class="org.aspcfs.modules.communications.base.SearchCriteriaList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" type="text/javascript" src="javascript/popContacts.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></script>
<SCRIPT LANGUAGE="JavaScript">
function checkForm(form) {
    formTest = true;
    message = "";
    if (form.description.value == "") { 
      message += "- Description is required\r\n";
      formTest = false;
    }
   <% if(ActionList.getId() == -1){ %> 
      saveValues();
      if (form.searchCriteriaText.value == "") { 
        message += "- Criteria is required\r\n";
        formTest = false;
      }
   <% } %>
    if (formTest == false) {
      alert("Form could not be saved, please check the following:\r\n\r\n" + message);
      return false;
    }
    return true;
}
</SCRIPT>
<%
  String cancelURL = "MyActionLists.do?command=List";
  if(ActionList.getId() > 0){
    if("details".equals(request.getParameter("return"))){
      cancelURL = "MyActionLists.do?command=Details&id=" + ActionList.getId();
    }
  }
  cancelURL = cancelURL + "&linkModuleId=" + Constants.ACTIONLISTS_CONTACTS;
%>
<body onLoad="javascript:document.forms[0].description.focus()">
<form name="searchForm" method="post" action="MyActionLists.do?command=Save&auto-populate=true" onSubmit="return checkForm(this);">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="MyCFS.do?command=Home">My Home Page</a> >
<a href="MyActionLists.do?command=List&linkModuleId=<%= Constants.ACTIONLISTS_CONTACTS %>">Action Lists</a> >
New Action List
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:formMessage showSpace="false" />
<input type="submit" value="Save">
<input type="button" value="Cancel" onClick="javascript:window.location.href='<%= cancelURL %>'">
<br />
<br />

<%-- include the basic form for adding a action list --%>
<%@ include file="action_lists_include.jsp" %>

&nbsp;<br>
<dhv:evaluate if="<%= ActionList.getId() == -1 %>">
<%-- include jsp for contact criteria --%>
<%@ include file="../communications/group_criteria_include.jsp" %>
<br />
</dhv:evaluate>

<input type="submit" value="Save">
<input type="button" value="Cancel" onClick="javascript:window.location.href='<%= cancelURL %>'">
<dhv:evaluate if="<%= request.getParameter("return") != null %>">
<input type="hidden" name="return" value="<%= request.getParameter("return") %>">
</dhv:evaluate>
<input type="hidden" name="linkModuleId" value="<%= Constants.ACTIONLISTS_CONTACTS %>">
<%-- Set hidden params if action is update --%>
<dhv:evaluate if="<%= ActionList.getId() > 0 %>">
<input type="hidden" name="id" value="<%= ActionList.getId() %>">
<input type="hidden" name="modified" value="<%= ActionList.getModified() %>">
<input type="hidden" name="complete" value="<%= ActionList.getComplete() ? "1" : "0" %>">
</dhv:evaluate>
</form>
</body>
