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
<%@ page import="java.util.*" %>
<jsp:useBean id="Campaign" class="org.aspcfs.modules.communications.base.Campaign" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<form name="modForm" action="CampaignManager.do?command=RemoveGroups&id=<%= Campaign.getId() %>" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="CampaignManager.do"><dhv:label name="communications.campaign.Communications">Communications</dhv:label></a> >
<a href="CampaignManager.do?command=Dashboard"><dhv:label name="communications.campaign.Dashboard">Dashboard</dhv:label></a> >
<a href="CampaignManager.do?command=Details&id=<%= Campaign.getId() %>"><dhv:label name="campaign.campaignDetails">Campaign Details</dhv:label></a> >
<dhv:label name="Groups">Groups</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:container name="communications" selected="groups" object="Campaign" param='<%= "id=" + Campaign.getId() %>'>
      <%
      	LinkedHashMap groups = Campaign.getGroups();
        Iterator i = groups.keySet().iterator();
        while (i.hasNext()) {
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
            <dhv:label name="accounts.accounts_reports_generate.Criteria">Criteria</dhv:label>
          </td>
          <td nowrap>
	    <%
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
            <dhv:label name="campaign.noCriteriaFound">No criteria Found.</dhv:label>
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
            <dhv:label name="campaign.noGroupsSelected">No groups selected.</dhv:label>
          </td>
        </tr>
       </table>
      <%  
        }
      %>
</dhv:container>
</form>
