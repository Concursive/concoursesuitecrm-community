<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="Campaign" class="org.aspcfs.modules.Campaign" scope="request"/>
<jsp:useBean id="FileItemList" class="com.zeroio.iteam.base.FileItemList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<a href="/CampaignManager.do?command=Dashboard">Back to Dashboard</a>
<br>&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="2" valign="center" align="left">
      <strong>Campaign Details</strong>
    </td>     
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Name
    </td>
    <td width="100%">
      <%= toHtml(Campaign.getName()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Description
    </td>
    <td width="100%">
      <%= toHtml(Campaign.getDescription()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Groups
    </td>
    <td width="100%">
      <font color='green'><%= Campaign.getGroupCount() %> selected</font>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Message
    </td>
    <td width="100%">
      <font color='green'><%= Campaign.getMessageName() %></font>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Schedule
    </td>
    <td width="100%">
      <font color='green'>Scheduled to run on <%= Campaign.getActiveDateString() %></font>
    </td>
  </tr>
  
  <tr class="containerBody">
    <td class="formLabel">
      Delivery
    </td>
    <td width="100%">
      <%= (Campaign.hasDetails()?"<font color='green'>" + toHtml(Campaign.getDeliveryName())  + "</font>":"<font color='red'>Not Specified</font>") %>
    </td>
  </tr>
  
  <tr class="containerBody">
    <td class="formLabel">
      Entered
    </td>
    <td width="100%">
      <dhv:username id="<%= Campaign.getEnteredBy() %>" /> - <%= Campaign.getEnteredString() %>
    </td>
  </tr>
  
  <tr class="containerBody">
    <td class="formLabel">
      Modified
    </td>
    <td width="100%">
      <dhv:username id="<%= Campaign.getModifiedBy() %>" /> - <%= Campaign.getModifiedString() %>
    </td>
  </tr>
</table>
&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="5" valign="center" align="left">
      <strong>List of Documents Available</strong>
    </td>     
  </tr>
  <tr class="title">
    <td width="10" align="center">Action</td>
    <td>Item</td>
    <td>Size</td>
    <td>Created</td>
    <td nowrap>Created By</td>
  </tr>
<%
  Iterator j = FileItemList.iterator();
  
  if ( j.hasNext() ) {
    int rowid = 0;
    while (j.hasNext()) {
      if (rowid != 1) rowid = 1; else rowid = 2;
      FileItem thisFile = (FileItem)j.next();
%>      
    <tr class="row<%= rowid %>">
      <td width="10" valign="middle" align="center" rowspan="2" nowrap>
        <a href="CampaignManager.do?command=Download&id=<%= Campaign.getId() %>&fid=<%= thisFile.getId() %>">Download</a><br>
			</td>
      <td valign="top" width="100%">
        <%= thisFile.getImageTag() %><%= toHtml(thisFile.getClientFilename()) %>
      </td>
      <td align="center" valign="middle" nowrap>
        <%= thisFile.getRelativeSize() %> k&nbsp;
      </td>
      <td valign="middle" nowrap>
        <%= thisFile.getModifiedDateTimeString() %>
      </td>
      <td valign="middle">
        <dhv:username id="<%= thisFile.getEnteredBy() %>"/>
      </td>
    </tr>
    <tr class="row<%= rowid %>">
      <td valign="middle" align="left" colspan="4">
        <i><%= toHtml(thisFile.getSubject()) %></i>
      </td>
    </tr>
<%}%>
  </table>
<%} else {%>
    <tr class="containerBody">
      <td colspan="5" valign="center">
        No documents found.
      </td>
    </tr>
  </table>
<%}%>
