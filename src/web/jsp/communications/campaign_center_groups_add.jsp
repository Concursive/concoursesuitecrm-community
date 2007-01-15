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
<a href="CampaignManager.do"><dhv:label name="communications.campaign.Communications">Communications</dhv:label></a> > 
<a href="CampaignManager.do?command=View"><dhv:label name="campaign.campaignList">Campaign List</dhv:label></a> >
<a href="CampaignManager.do?command=ViewDetails&id=<%= Campaign.getId() %>"><dhv:label name="campaign.campaignDetails">Campaign Details</dhv:label></a> >
<dhv:label name="campaign.chooseGroups">Choose Groups</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<input type="submit" value="<dhv:label name="campaign.updateCampaignGroups">Update Campaign Groups</dhv:label>" onClick="this.form.action='CampaignManager.do?command=InsertGroups&id=<%= Campaign.getId() %>'">
<input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="this.form.action='CampaignManager.do?command=ViewDetails&id=<%= Campaign.getId() %>'">
<br />
<br />
<table cellpadding="4" cellspacing="0" width="100%" style="border: 1px solid #000;">
  <tr class="containerHeader">
    <td style="border-bottom: 1px solid #000;">
      <dhv:label name="campaign.campaign.colon" param='<%= "name="+toHtml(Campaign.getName()) %>'><strong>Campaign:</strong> <%= toHtml(Campaign.getName()) %></dhv:label>
    </td>
  </tr>
  <tr>
    <td class="containerBack">
<table width="100%" border="0" class="empty">
  <tr>
    <td align="left">
      <select size="1" name="listView" onChange="javascript:document.modForm.action='CampaignManager.do?command=AddGroups&id=<%= Campaign.getId() %>';document.modForm.submit();">
        <option <%= CampaignCenterGroupInfo.getOptionValue("my") %>><dhv:label name="campaign.myGroups">My Groups</dhv:label></option>
        <option <%= CampaignCenterGroupInfo.getOptionValue("all") %>><dhv:label name="campaign.allGroups">All Groups</dhv:label></option>
      </select>
      <dhv:formMessage />
    </td>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="3">
      <strong><dhv:label name="campaign.selectGroups.text">Select groups for this campaign</dhv:label></strong>
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
      <input type="checkbox" name="select<%= selectCount %>check" value="true"<%= (selectedList.containsItem(thisList)?" checked":"") %>>
    </td>
    <td width="100%" valign="center" nowrap class="row<%= rowid %>">
      <%= toHtml(thisList.getGroupName()) %>
    </td>
    <td valign="center" nowrap class="row<%= rowid %>">
      <a href="javascript:popURL('CampaignManager.do?command=PreviewGroups&id=<%= Campaign.getId() %>&scl=<%= thisList.getId() %>&reset=true&popup=true','CRM_Recipients',600,450,'yes','yes')"><dhv:label name="campaign.previewRecipents">Preview Recipients</dhv:label></a>
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
      <dhv:label name="campaign.noGroupsToSelect.text">No groups to select from, use "Build Groups" to add groups.</dhv:label>
    </td>
  </tr>
<%  
  }
%>
</table>
<br>
<dhv:pagedListControl object="CampaignCenterGroupInfo" showForm="false" resetList="false" enableJScript="true" form="modForm"/>
<br>
<input type="submit" value="<dhv:label name="campaign.updateCampaignGroups">Update Campaign Groups</dhv:label>" onClick="this.form.action='CampaignManager.do?command=InsertGroups&id=<%= Campaign.getId() %>'">
<input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="this.form.action='CampaignManager.do?command=ViewDetails&id=<%= Campaign.getId() %>'">
  </td>
  </tr>
</table>
</form>
