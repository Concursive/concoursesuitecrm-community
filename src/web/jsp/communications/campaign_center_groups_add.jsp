<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="Campaign" class="com.darkhorseventures.cfsbase.Campaign" scope="request"/>
<jsp:useBean id="sclList" class="com.darkhorseventures.cfsbase.SearchCriteriaListList" scope="request"/>
<jsp:useBean id="selectedList" class="com.darkhorseventures.cfsbase.SearchCriteriaListList" scope="request"/>
<jsp:useBean id="CampaignCenterGroupInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<%@ include file="initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/popURL.js"></script>
<form name="modForm" action="/CampaignManager.do?command=InsertGroups&id=<%= Campaign.getId() %>" method="post">
<a href="CampaignManager.do">Communications Manager</a> > 
<a href="/CampaignManager.do?command=View">Campaign List</a> >
<a href="/CampaignManager.do?command=ViewDetails&id=<%= Campaign.getId() %>">Campaign Details</a> >
Choose Groups
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong>Campaign: </strong><%= toHtml(Campaign.getName()) %>
    </td>
  </tr>
  <tr>
    <td class="containerBack">
      <ul>
        <li>Choose groups of recipients that will be sent a message</li>
        <li>Click "Update Campaign Groups" to save changes</li>
        <li>Groups can be created or edited in the <a href="CampaignManagerGroup.do?command=View">Build Groups</a> utility</li>
        <li>Use "Preview Recipients" to selectively remove a specific recipient from this campaign</li>
      </ul>
<table width="100%" border="0">
  <tr>
    <td align="left">
      <select size="1" name="listView" onChange="javascript:document.forms[0].action='/CampaignManager.do?command=AddGroups&id=<%= Campaign.getId() %>';document.forms[0].submit();">
        <option <%= CampaignCenterGroupInfo.getOptionValue("my") %>>My Groups</option>
        <option <%= CampaignCenterGroupInfo.getOptionValue("all") %>>All Groups</option>
      </select>
      <%= showError(request, "actionError") %>
    </td>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="3" valign="center" align="left">
      <strong>Select groups for this campaign</strong>
    </td>     
  </tr>
<%
  Iterator i = sclList.iterator();
  int rowid = 0;
  int selectCount = 0;
  while (i.hasNext()) {
    if (rowid != 1) { rowid = 1; } else { rowid = 2; }
    SearchCriteriaList thisList = (SearchCriteriaList)i.next();
%>  
  <tr class="containerBody">
    <td width="8" valign="center" nowrap class="row<%= rowid %>">
      <input type="hidden" name="select<%= ++selectCount %>" value="<%= thisList.getId() %>">
      <input type="checkbox" name="select<%= selectCount %>check"<%= (selectedList.containsItem(thisList)?" checked":"") %>>
    </td>
    <td width="100%" valign="center" nowrap class="row<%= rowid %>">
      <%= toHtml(thisList.getGroupName()) %>
    </td>
    <td valign="center" nowrap class="row<%= rowid %>">
      <a href="javascript:popURL('/CampaignManager.do?command=PreviewGroups&id=<%= Campaign.getId() %>&scl=<%= thisList.getId() %>','CFS_Recipients',600,450,true,true)">Preview Recipients</a>
    </td>
  </tr>
<%
  }
  i = selectedList.iterator();
  while (i.hasNext()) {
    SearchCriteriaList thisList = (SearchCriteriaList)i.next();
    if (!sclList.containsItem(thisList)) {
%>    
      <input type="hidden" name="select<%= ++selectCount %>" value="<%= thisList.getId() %>">
      <input type="hidden" name="select<%= selectCount %>check" value="on">
<%    }
  }
  if (sclList.size() == 0) {
%>  
  <tr class="containerBody">
    <td colspan="3">
      No groups to select from, use "Build Groups" to add groups.
    </td>
  </tr>
<%  
  }
%>
</table>
<br>
<input type="submit" value="Update Campaign Groups">
  </td>
  </tr>
</table>
</form>
