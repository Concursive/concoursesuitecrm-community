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
<%@ page import="java.util.*" %>
<jsp:useBean id="Campaign" class="org.aspcfs.modules.communications.base.Campaign" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<form name="modForm" action="CampaignManager.do?command=RemoveGroups&id=<%= Campaign.getId() %>" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="CampaignManager.do">Communications</a> >
<a href="CampaignManager.do?command=Dashboard">Dashboard</a> >
<a href="CampaignManager.do?command=Details&id=<%= Campaign.getId() %>">Campaign Details</a> >
Groups
</td>
</tr>
</table>
<%-- End Trails --%>
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
