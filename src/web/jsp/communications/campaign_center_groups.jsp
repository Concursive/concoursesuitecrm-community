<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*" %>
<jsp:useBean id="Campaign" class="org.aspcfs.modules.communications.base.Campaign" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<form name="modForm" action="CampaignManager.do?command=RemoveGroups&id=<%= Campaign.getId() %>" method="post">
<a href="CampaignManager.do">Communications Manager</a> >
<a href="CampaignManager.do?command=Dashboard">Dashboard</a> >
<a href="CampaignManager.do?command=Details&id=<%= Campaign.getId() %>">Campaign Details</a> >
Groups
<hr color="#BFBFBB" noshade>
<strong>Campaign: </strong><%= toHtml(Campaign.getName()) %>
<% String param1 = "id=" + Campaign.getId(); %>
<dhv:container name="communications" selected="groups" param="<%= param1 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" width="100%">
  <tr>
    <td class="containerBack">
      <%
      	LinkedHashMap groups = Campaign.getGroups();
        Iterator i = groups.keySet().iterator();
        int rowid = 0;
        while (i.hasNext()) {
          rowid = 2;
          String groupName = (String) i.next();
      %>
      <table cellpadding="4" cellspacing="0" width="100%" class="details">
        <tr>
          <th colspan="2">
            <strong><%= toHtml(groupName) %></strong>
          </th>
        </tr>
        <tr class="containerBody">
          <td valign="top" class="formLabel" nowrap>
            Criteria
          </td>
          <td nowrap>
	    <%
          int count = 0;
          Iterator thisList = ((ArrayList)groups.get(groupName)).iterator();
          if (thisList.hasNext()) {
            while (thisList.hasNext()) {
%>
            <%= toHtml((String) thisList.next()) %>
            <dhv:evaluate if="<%= thisList.hasNext() %>"><br></dhv:evaluate>
<%  
            }
          } else {
%>
            No criteria Found.
<%
          }
%>
          </td>
        </tr>
      </table>
      <dhv:evaluate if="<%= i.hasNext() %>"><br></dhv:evaluate>
<%
        }
        if (Campaign.getGroups().size() == 0) {
      %>  
      <table cellpadding="4" cellspacing="0" width="100%" class="details">
        <tr class="containerBody">
          <td colspan="3">
            No groups selected.
          </td>
        </tr>
       </table>
      <%  
        }
      %>
   </td>
  </tr>
</table>
</form>
