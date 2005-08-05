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
<%@ page import="java.util.*,org.aspcfs.modules.base.Constants,org.aspcfs.modules.mycfs.base.*,org.aspcfs.modules.contacts.base.Contact,org.aspcfs.modules.base.Filter,org.aspcfs.modules.base.EmailAddress" %>
<jsp:useBean id="ContactListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="selectedContacts" class="java.util.HashMap" scope="session"/>
<jsp:useBean id="viewUser" class="java.lang.String" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popContacts.js"></script>
<SCRIPT LANGUAGE="JavaScript">
function finalize(selectedContacts, actionId){
  window.location.href = 'MyActionContacts.do?command=Update&selectedContacts=' + selectedContacts + '&actionId=' + actionId;
}
</SCRIPT>
<%
  if (!"true".equalsIgnoreCase(request.getParameter("finalsubmit"))) {
%>
<form name="contactListView" method="post" action="MyActionContacts.do?command=Modify&doBuild=false">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="MyCFS.do?command=Home"><dhv:label name="My Home Page" mainMenuItem="true">My Home Page</dhv:label></a> >
<a href="MyActionLists.do?command=List&linkModuleId=<%= Constants.ACTIONLISTS_CONTACTS %>"><dhv:label name="myitems.actionLists">Action Lists</dhv:label></a> >
<a href="MyActionContacts.do?command=List&actionId=<%= request.getParameter("actionId") %>"><dhv:label name="actionList.listDetails">List Details</dhv:label></a> >
<dhv:label name="actionList.modifyActionList">Modify Action List</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<%-- Navigating the contact list, not the final submit --%>
  <center><%= ContactListInfo.getAlphabeticalPageLinks("setFieldSubmit","contactListView") %></center>
  <!-- Make sure that when the list selection changes previous selected entries are saved -->
  <input type="hidden" name="letter">
  <%@ include file="../mycfs/contactlist_include.jsp" %>
  <br />
  <input type="button" value="<dhv:label name="button.done">Done</dhv:label>" onClick="javascript:setFieldSubmit('finalsubmit','true','contactListView');" />
  <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>"  onClick="javascript:window.location.href='MyActionContacts.do?command=List&actionId=<%= request.getParameter("actionId") %>'" />
  [<a href="javascript:SetChecked(1,'checkcontact','contactListView','<%=User.getBrowserId()%>');"><dhv:label name="quotes.checkAll">Check All</dhv:label></a>]
  [<a href="javascript:SetChecked(0,'checkcontact','contactListView','<%=User.getBrowserId()%>');"><dhv:label name="quotes.clearAll">Clear All</dhv:label></a>]
  <br />
  <br />
  <input type="hidden" name="actionId" value="<%= request.getParameter("actionId") %>" />
</form>
<% }else{
  Set s = selectedContacts.keySet();
  Iterator i = s.iterator();
  String contacts = "";
  while (i.hasNext()) {
  String cId = ((Integer)i.next()).toString();
    contacts += cId;
    if(i.hasNext()){
      contacts += ",";
    }
  }
  %>
<body onLoad="javascript:finalize('<%= contacts %>', '<%= request.getParameter("actionId") %>');">
</body>
<% } %>
