<%@ taglib uri="//WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.communications.base.*" %>
<jsp:useBean id="Campaign" class="org.aspcfs.modules.communications.base.Campaign" scope="request"/>
<jsp:useBean id="sclList" class="org.aspcfs.modules.communications.base.SearchCriteriaListList" scope="request"/>
<jsp:useBean id="selectedList" class="org.aspcfs.modules.communications.base.SearchCriteriaListList" scope="request"/>
<jsp:useBean id="CampaignCenterGroupInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<form name="modForm" action="CampaignManager.do?command=AddGroups&id=<%= Campaign.getId() %>" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="CampaignManager.do">Communications</a> > 
<a href="CampaignManager.do?command=View">Campaign List</a> >
<a href="CampaignManager.do?command=ViewDetails&id=<%= Campaign.getId() %>">Campaign Details</a> >
Choose Groups
</td>
</tr>
</table>
<%-- End Trails --%>
<input type="submit" value="Update Campaign Groups" onClick="this.form.action='CampaignManager.do?command=InsertGroups&id=<%= Campaign.getId() %>'">
<input type="submit" value="Cancel" onClick="this.form.action='CampaignManager.do?command=ViewDetails&id=<%= Campaign.getId() %>'">
<br>
&nbsp;<br>
<table cellpadding="4" cellspacing="0" width="100%" style="border: 1px solid #000;">
  <tr class="containerHeader">
    <td style="border-bottom: 1px solid #000;">
      <strong>Campaign: </strong><%= toHtml(Campaign.getName()) %>
    </td>
  </tr>
  <tr>
    <td class="containerBack">
<table width="100%" border="0" class="empty">
  <tr>
    <td align="left">
      <select size="1" name="listView" onChange="javascript:document.forms[0].action='CampaignManager.do?command=AddGroups&id=<%= Campaign.getId() %>';document.forms[0].submit();">
        <option <%= CampaignCenterGroupInfo.getOptionValue("my") %>>My Groups</option>
        <option <%= CampaignCenterGroupInfo.getOptionValue("all") %>>All Groups</option>
      </select>
      <%= showError(request, "actionError") %>
    </td>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="3">
      <strong>Select groups for this campaign</strong>
    </th>
  </tr>
<%
  Iterator i = sclList.iterator();
  int rowid = 0;
  int selectCount = 0;
  while (i.hasNext()) {
    rowid = (rowid != 1?1:2);
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
      <a href="javascript:popURL('CampaignManager.do?command=PreviewGroups&id=<%= Campaign.getId() %>&scl=<%= thisList.getId() %>&reset=true&popup=true','CRM_Recipients',600,450,'yes','yes')">Preview Recipients</a>
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
<dhv:pagedListControl object="CampaignCenterGroupInfo" showForm="false" resetList="false" enableJScript="true"/>
<br>
<input type="submit" value="Update Campaign Groups" onClick="this.form.action='CampaignManager.do?command=InsertGroups&id=<%= Campaign.getId() %>'">
<input type="submit" value="Cancel" onClick="this.form.action='CampaignManager.do?command=ViewDetails&id=<%= Campaign.getId() %>'">
  </td>
  </tr>
</table>
</form>
