<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="campList" class="com.darkhorseventures.cfsbase.CampaignList" scope="request"/>
<jsp:useBean id="ContactDetails" class="com.darkhorseventures.cfsbase.Contact" scope="request"/>
<jsp:useBean id="ContactMessageListInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<%@ include file="initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></script>
<form name="listView" method="post" action="/ExternalContacts.do?command=ViewMessages&contactId=<%=ContactDetails.getId()%>">
<a href="/ExternalContacts.do?command=ListContacts">Back to Contact List</a><br>&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(ContactDetails.getNameFull()) %></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "id=" + ContactDetails.getId(); %>      
      <dhv:container name="contacts" selected="messages" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">
<br>
<center><%= ContactMessageListInfo.getAlphabeticalPageLinks() %></center>
<table width="100%" border="0">
  <tr>
    <td align="left">
      <select size="1" name="listView" onChange="javascript:document.forms[0].submit();">
        <option <%= ContactMessageListInfo.getOptionValue("my") %>>My Messages</option>
        <option <%= ContactMessageListInfo.getOptionValue("all") %>>All Messages</option>
      </select>
      <%= showAttribute(request, "actionError") %>
    </td>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td width=8 valign=center align=left>
      <strong>Action</strong>
    </td>
    <td valign=center width="65%" align=left>
      <a href="/ExternalContacts.do?command=ViewMessages&column=c.name&contactId=<%=ContactDetails.getId()%>"><strong>Name</strong></a>
      <%= ContactMessageListInfo.getSortIcon("c.name") %>
    </td>  
    <td valign=center width="20%" align=left>
      <a href="/ExternalContacts.do?command=ViewMessages&column=active_date&contactId=<%=ContactDetails.getId()%>"><strong>Run Date</strong></a>
      <%= ContactMessageListInfo.getSortIcon("active_date") %>
    </td> 
    <!--td valign=center align=left>
      <strong>Groups?</strong>
    </td> 
    <td valign=center align=left>
      <strong>Message?</strong>
    </td>
    <td valign=center align=left>
      <strong>Details?</strong>
    </td-->
    <td valign=center width="15%" align=left>
      <strong>Status</strong>
    </td>
<%
    Iterator j = campList.iterator();
    
    if ( j.hasNext() ) {
      int rowid = 0;
      while (j.hasNext()) {
        if (rowid != 1) {
          rowid = 1;
        } else {
          rowid = 2;
        }
        Campaign campaign = (Campaign)j.next();
%>
  <tr class="containerBody">
    <td width=8 valign=center nowrap class="row<%= rowid %>">
      <%= (campaign.hasRun()?"&nbsp;":"<font color=\"#787878\">Cancel</font>") %>
    </td>
    <td valign=center nowrap class="row<%= rowid %>">
      <a href="ExternalContacts.do?command=MessageDetails&id=<%= campaign.getId() %>&contactId=<%=ContactDetails.getId()%>"><%= toHtml(campaign.getMessageName()) %></a>
      <%= (("true".equals(request.getParameter("notify")) && ("" + campaign.getId()).equals(request.getParameter("id")))?" <font color=\"red\">(Cancelled)</font>":"") %>
    </td>
    <td valign=center align="left" nowrap class="row<%= rowid %>">
      <%=toHtml(campaign.getActiveDateString())%>
    </td>
    <td valign=center nowrap class="row<%= rowid %>">
      <%=toHtml(campaign.getStatus())%>
    </td>
    <!--td valign=center align="center" nowrap class="row<%= rowid %>">
      <a href="/ExternalContacts.do?command=ViewGroups&id=<%= campaign.getId() %>"><%= (campaign.hasGroups()?"<font color='green'>Complete</font>":"<font color='red'>Incomplete</font>") %></a>
    </td>
    <td valign=center align="center" nowrap class="row<%= rowid %>">
      <a href="/ExternalContacts.do?command=ViewMessage&id=<%= campaign.getId() %>"><%= (campaign.hasMessage()?"<font color='green'>Complete</font>":"<font color='red'>Incomplete</font>") %></a>
    </td>
    <td valign=center align="center" nowrap class="row<%= rowid %>">
      <a href="/ExternalContacts.do?command=ViewSchedule&id=<%= campaign.getId() %>"><%= (campaign.hasDetails()?"<font color='green'>Complete</font>":"<font color='red'>Incomplete</font>") %></a>
    </td>
    <td valign=center align="center" nowrap class="row<%= rowid %>">
      <%= (campaign.isReadyToActivate()?"<a href=\"javascript:confirmForward('/CampaignManager.do?command=Activate&id=" + campaign.getId() + "&notify=true&modified=" + campaign.getModified() + "');\"><font color=\"red\">Activate</font></a>":"&nbsp;") %>
    </td-->
	</tr>
	<%}%>
<%} else {%>
  <tr class="containerBody">
    <td colspan=7 valign=center>
      No messages found.
    </td>
  </tr>
<%}%>
</table>
<br>
<dhv:pagedListControl object="ContactMessageListInfo" tdClass="row1"/>
<br>
</td></tr>
</table>
</form>
