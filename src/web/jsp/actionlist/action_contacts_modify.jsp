<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.base.Constants,org.aspcfs.modules.mycfs.base.*,org.aspcfs.modules.contacts.base.Contact,org.aspcfs.modules.base.Filter,org.aspcfs.modules.base.EmailAddress" %>
<jsp:useBean id="ContactListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="selectedContacts" class="java.util.HashMap" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popContacts.js"></script>
<SCRIPT LANGUAGE="JavaScript">
function finalize(selectedContacts, actionId){
  window.location.href = 'MyActionContacts.do?command=Update&selectedContacts=' + selectedContacts + '&actionId=' + actionId
}
</SCRIPT>
<%
  if (!"true".equalsIgnoreCase(request.getParameter("finalsubmit"))) {
%>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="MyCFS.do?command=Home">My Home Page</a> >
<a href="MyActionLists.do?command=List&linkModuleId=<%= Constants.ACTIONLISTS_CONTACTS %>">Action Lists</a> >
<a href="MyActionContacts.do?command=List&actionId=<%= request.getParameter("actionId") %>">List Details</a> >
Modify Action List
</td>
</tr>
</table>
<%-- End Trails --%>
<%-- Navigating the contact list, not the final submit --%>
<form name="contactListView" method="post" action="MyActionContacts.do?command=Modify&doBuild=false">
  <center><%= ContactListInfo.getAlphabeticalPageLinks("setFieldSubmit","contactListView") %></center>
<!-- Make sure that when the list selection changes previous selected entries are saved -->
  <input type="hidden" name="letter">
  
  <%@ include file="../mycfs/contactlist_include.jsp" %>
  <br>
  <input type="button" value="Done" onClick="javascript:setFieldSubmit('finalsubmit','true','contactListView');">
  <input type="button" value="Cancel"  onClick="javascript:window.location.href='MyActionContacts.do?command=List&actionId=<%= request.getParameter("actionId") %>'">
  [<a href="javascript:SetChecked(1,'checkcontact','contactListView','<%=User.getBrowserId()%>');">Check All</a>]
  [<a href="javascript:SetChecked(0,'checkcontact','contactListView','<%=User.getBrowserId()%>');">Clear All</a>]
  <br>
  &nbsp;<br>
  <input type="hidden" name="actionId" value="<%= request.getParameter("actionId") %>">
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

