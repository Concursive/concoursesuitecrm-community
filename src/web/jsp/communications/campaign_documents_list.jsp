<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.communications.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="Campaign" class="org.aspcfs.modules.communications.base.Campaign" scope="request"/>
<jsp:useBean id="CampaignDocListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="FileItemList" class="com.zeroio.iteam.base.FileItemList" scope="request"/>
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
<table class="trails">
<tr>
<td>
<a href="CampaignManager.do">Communications</a> >
<a href="CampaignManager.do?command=Dashboard">Dashboard</a> >
<a href="CampaignManager.do?command=Details&id=<%=Campaign.getId()%>">Campaign Details</a> >
Documents
</td>
</tr>
</table>
<%-- End Trails --%>
<strong>Campaign: </strong><%= toHtml(Campaign.getName()) %>
<% String param1 = "id=" + Campaign.getId(); %>
<dhv:container name="communications" selected="documents" param="<%= param1 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" width="100%">
  <tr>
    <td class="containerBack">
<dhv:permission name="campaign-campaigns-edit"><a href="CampaignDocuments.do?command=Add&id=<%= Campaign.getId() %>&folderId=<%= FileItemList.getFolderId() %>">Add a Document</a><br></dhv:permission>
<center><%= CampaignDocListInfo.getAlphabeticalPageLinks() %></center>
<dhv:pagedListStatus title="<%= showAttribute(request, "actionError") %>" object="CampaignDocListInfo"/>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th width="10" align="center">Action</th>
    <th>
      <strong><a href="CampaignDocuments.do?command=View&id=<%= Campaign.getId() %>&column=subject">Item</a></strong>
      <%= CampaignDocListInfo.getSortIcon("subject") %>
    </th>
    <th align="center">Ext</th>
    <th align="center">Size</th>
    <th align="center">Version</th>
    <dhv:permission name="campaign-campaigns-edit">
      <th>&nbsp;</th>
    </dhv:permission>
    <th align="center">Submitted</th>
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
        <a href="javascript:displayMenu('menuFile','<%= Campaign.getId() %>','<%= thisFile.getId() %>');" onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>)"><img src="images/select.gif" name="select<%= i %>" align="absmiddle" border="0"></a>
      </td>
      <td valign="middle" width="100%">
        <a href="CampaignDocuments.do?command=Details&id=<%= Campaign.getId() %>&fid=<%= thisFile.getId() %>"><%= thisFile.getImageTag() %><%= toHtml(thisFile.getSubject()) %></a>
      </td>
      <td align="center"><%= toHtml(thisFile.getExtension()) %>&nbsp;</td>
      <td align="center" valign="middle" nowrap>
        <%= thisFile.getRelativeSize() %> k&nbsp;
      </td>
      <td align="right" valign="middle" nowrap>
        <%= thisFile.getVersion() %>&nbsp;
      </td>
    <dhv:permission name="campaign-campaigns-edit">
      <td align="right" valign="middle" nowrap>
        [<a href="CampaignDocuments.do?command=AddVersion&id=<%= Campaign.getId() %>&fid=<%= thisFile.getId() %>">Add Version</a>]
      </td>
    </dhv:permission>
      <td nowrap>
        <dhv:tz timestamp="<%= thisFile.getModified() %>" dateFormat="<%= DateFormat.SHORT   %>" timeFormat="<%= DateFormat.LONG %>"/><br>
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
        No documents found.
      </td>
    </tr>
  </table>
<%}%>
</td>
</tr>
</table>

