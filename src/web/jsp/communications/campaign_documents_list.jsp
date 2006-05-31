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
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.communications.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="Campaign" class="org.aspcfs.modules.communications.base.Campaign" scope="request"/>
<jsp:useBean id="CampaignDocListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="FileItemList" class="com.zeroio.iteam.base.FileItemList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="campaign_documents_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<script language="JavaScript" type="text/javascript" src="javascript/confirmDelete.js"></script>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="CampaignManager.do"><dhv:label name="communications.campaign.Communications">Communications</dhv:label></a> >
<a href="CampaignManager.do?command=Dashboard"><dhv:label name="communications.campaign.Dashboard">Dashboard</dhv:label></a> >
<a href="CampaignManager.do?command=Details&id=<%=Campaign.getId()%>"><dhv:label name="campaign.campaignDetails">Campaign Details</dhv:label></a> >
<dhv:label name="accounts.accounts_documents_details.Documents">Documents</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:container name="communications" selected="documents" object="Campaign" param="<%= "id=" + Campaign.getId() %>">
<dhv:permission name="campaign-campaigns-edit"><a href="CampaignDocuments.do?command=Add&id=<%= Campaign.getId() %>&folderId=<%= FileItemList.getFolderId() %>"><dhv:label name="campaign.addADocument">Add a Document</dhv:label></a> |
<a href="CampaignManager.do?command=ExportReport&id=<%= Campaign.getId() %>"><dhv:label name="campaign.exportCampaignToExcelFile">Export Campaign to Excel file</dhv:label></a><br/><br/></dhv:permission>
<dhv:include name="pagedListInfo.alphabeticalLinks" none="true">
<center><dhv:pagedListAlphabeticalLinks object="CampaignDocListInfo"/></center></dhv:include>
<dhv:pagedListStatus title="<%= showAttribute(request, "actionError") %>" object="CampaignDocListInfo"/>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th width="10" align="center">&nbsp;</th>
    <th>
      <strong><a href="CampaignDocuments.do?command=View&id=<%= Campaign.getId() %>&column=subject"><dhv:label name="accounts.accounts_documents_details.Item">Item</dhv:label></a></strong>
      <%= CampaignDocListInfo.getSortIcon("subject") %>
    </th>
    <th align="center"><dhv:label name="account.ext">Ext</dhv:label></th>
    <th align="center"><dhv:label name="accounts.accounts_documents_details.Size">Size</dhv:label></th>
    <th align="center"><dhv:label name="accounts.accounts_documents_details.Version">Version</dhv:label></th>
    <dhv:permission name="campaign-campaigns-edit">
      <th>&nbsp;</th>
    </dhv:permission>
    <th align="center"><dhv:label name="accounts.accounts_documents_details.Submitted">Submitted</dhv:label></th>
  </tr>
<%
  Iterator j = FileItemList.iterator();
  if ( j.hasNext() ) {
    int rowid = 0;
    int i = 0;
    while (j.hasNext()) {
      i++;
      rowid = (rowid != 1?1:2);
      FileItem thisFile = (FileItem)j.next();
%>      
    <tr class="row<%= rowid %>">
      <td width="10" valign="middle" align="center" nowrap>
        <%-- Use the unique id for opening the menu, and toggling the graphics --%>
        <a href="javascript:displayMenu('select<%= i %>','menuFile','<%= Campaign.getId() %>','<%= thisFile.getId() %>');" onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuFile');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
      </td>
      <td valign="middle" width="100%">
        <a href="CampaignDocuments.do?command=Details&id=<%= Campaign.getId() %>&fid=<%= thisFile.getId() %>"><%= thisFile.getImageTag() %><%= toHtml(thisFile.getSubject()) %></a>
      </td>
      <td align="center"><%= toHtml(thisFile.getExtension()) %>&nbsp;</td>
      <td align="center" valign="middle" nowrap>
        <%= thisFile.getRelativeSize() %> <dhv:label name="admin.oneThousand.abbreviation">k</dhv:label>&nbsp;
      </td>
      <td align="right" valign="middle" nowrap>
        <%= thisFile.getVersion() %>&nbsp;
      </td>
    <dhv:permission name="campaign-campaigns-edit">
      <td align="right" valign="middle" nowrap>
        [<a href="CampaignDocuments.do?command=AddVersion&id=<%= Campaign.getId() %>&fid=<%= thisFile.getId() %>"><dhv:label name="accounts.accounts_documents_list_menu.AddVersion">Add Version</dhv:label></a>]
      </td>
    </dhv:permission>
      <td nowrap>
        <zeroio:tz timestamp="<%= thisFile.getModified() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/><br>
        <dhv:username id="<%= thisFile.getEnteredBy() %>"/>
      </td>
    </tr>
<%}%>
  </table>
  <br>
  <dhv:pagedListControl object="CampaignDocListInfo"/>
<%} else {%>
    <tr class="containerBody">
      <td colspan="7">
        <dhv:label name="project.noDocumentsFound">No Documents found.</dhv:label>
      </td>
    </tr>
  </table>
<%}%>
</dhv:container>

