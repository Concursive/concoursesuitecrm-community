<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.mycfs.base.*,org.aspcfs.modules.contacts.base.Contact,org.aspcfs.modules.base.Filter,org.aspcfs.modules.base.EmailAddress" %>
<jsp:useBean id="ContactListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="selectedContacts" class="java.util.HashMap" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popContacts.js"></script>
<%
  if (!"true".equalsIgnoreCase(request.getParameter("finalsubmit"))) {
    String source = "";
    if(request.getParameter("source") != null){
      source = request.getParameter("source");
    }
%>
<%-- Navigating the contact list, not the final submit --%>
<form name="contactListView" method="post" action="ContactsList.do?command=ContactList">
  <br>
  <center><%= ContactListInfo.getAlphabeticalPageLinks("setFieldSubmit","contactListView") %></center>
<!-- Make sure that when the list selection changes previous selected entries are saved -->
  <input type="hidden" name="letter">
  
  <%@ include file="contactlist_include.jsp" %>
  
<% if("list".equals(request.getParameter("listType"))){ %>
  <input type="button" value="Done" onClick="javascript:setFieldSubmit('finalsubmit','true','contactListView');">
  <input type="button" value="Cancel" onClick="javascript:window.close();">
  [<a href="javascript:SetChecked(1,'checkcontact','contactListView','<%=User.getBrowserId()%>');">Check All</a>]
  [<a href="javascript:SetChecked(0,'checkcontact','contactListView','<%=User.getBrowserId()%>');">Clear All</a>]
  <br>
<%}else{%>
  <input type="button" value="Cancel" onClick="javascript:window.close();">
<%}%>
  &nbsp;<br>
</form>
<%} else {%>
<%-- The final submit --%>
  <% if ("true".equals((String) request.getParameter("campaign"))) { %>
  <body onLoad="javascript:setParentListCampaign(recipientEmails,recipientIds,'<%= request.getParameter("listType") %>','<%= request.getParameter("displayFieldId") %>','<%= request.getParameter("hiddenFieldId") %>','<%=User.getBrowserId()%>');window.close()">
  <%} else {%>
  <body onLoad="javascript:setParentList(recipientEmails,recipientIds,'<%= request.getParameter("listType") %>','<%= request.getParameter("displayFieldId") %>','<%= request.getParameter("hiddenFieldId") %>','<%= User.getBrowserId() %>');window.close()">
  <%}%>
  <script>recipientEmails = new Array();recipientIds = new Array();</script>
<%
  Set s = selectedContacts.keySet();
  Iterator i = s.iterator();
  int count = -1;
  while (i.hasNext()) {
    count++;
    Object id = i.next();
    Object st = selectedContacts.get(id);
    String email = st.toString();
    if(email.startsWith("P:")){
      email = email.substring(2);
    }
%>
  <script>
    recipientEmails[<%= count %>] = '<%= toJavaScript(email) %>';
    recipientIds[<%= count %>] = '<%=id%>';
  </script>
<%	
  }
%>
  </body>
<%
  //Cleanup the session objects used
    if (((String) request.getParameter("campaign")).equalsIgnoreCase("true")) {
      session.removeAttribute("selectedContacts");
      session.removeAttribute("finalContacts");
    }
  }
%>

