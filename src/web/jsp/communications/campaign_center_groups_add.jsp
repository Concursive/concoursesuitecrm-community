<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="Campaign" class="com.darkhorseventures.cfsbase.Campaign" scope="request"/>
<jsp:useBean id="sclList" class="com.darkhorseventures.cfsbase.SearchCriteriaListList" scope="request"/>
<jsp:useBean id="CampaignCenterGroupInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<%@ include file="initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></script>
<form name="modForm" action="/CampaignManager.do?command=InsertGroups&id=<%= Campaign.getId() %>" method="post">
<a href="/CampaignManager.do?command=View">Back to Campaign List</a><br>&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(Campaign.getName()) %></strong>
    </td>
  </tr>
  <tr>
    <td class="containerMenu">
      <a href="/CampaignManager.do?command=ViewDetails&id=<%= Campaign.getId() %>"><font color="#000000">Details</font></a> |
      <a href="/CampaignManager.do?command=ViewGroups&id=<%= Campaign.getId() %>"><font color="#0000FF">Groups</font></a> | 
      <a href="/CampaignManager.do?command=ViewMessage&id=<%= Campaign.getId() %>"><font color="#000000">Message</font></a> | 
      <a href="/CampaignManager.do?command=ViewSchedule&id=<%= Campaign.getId() %>"><font color="#000000">Schedule</font></a>
    </td>
  </tr>
  <tr>
    <td class="containerBack">
Create a New Group<br>
&nbsp;<br>
<table width="100%" border="0">
  <tr>
    <td align="left">
      <select size="1" name="listView" onChange="javascript:document.forms[0].action='/CampaignManager.do?command=AddGroups&id=<%= Campaign.getId() %>';document.forms[0].submit();">
        <option <%= CampaignCenterGroupInfo.getOptionValue("my") %>>My Groups</option>
        <option <%= CampaignCenterGroupInfo.getOptionValue("all") %>>All Groups</option>
      </select>
      <%= showAttribute(request, "actionError") %>
    </td>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan=3 valign=center align=left>
      <strong>Add groups to the campaign</strong>
    </td>     
  </tr>
<%
  Iterator i = sclList.iterator();
  int rowid = 0;
  while (i.hasNext()) {
    if (rowid != 1) { rowid = 1; } else { rowid = 2; }
    SearchCriteriaList thisList = (SearchCriteriaList)i.next();
%>  
  <tr class="containerBody">
    <td width=8 valign=center nowrap class="row<%= rowid %>">
      <input type="checkbox" name="<%= thisList.getId() %>">
    </td>
    <td width="100%" valign="center" nowrap class="row<%= rowid %>">
      <%= toHtml(thisList.getGroupName()) %>
    </td>
    <td valign=center nowrap class="row<%= rowid %>">
      [Preview Recipients]
    </td>
  </tr>
<%
  }
  if (sclList.size() == 0) {
%>  
  <tr class="containerBody">
    <td colspan="3">
      No groups to select from.
    </td>
  </tr>
<%  
  }
%>
</table>
<br>
<input type='submit' value="Add Selected Groups to Campaign">
  </td>
  </tr>
</table>
</form>
