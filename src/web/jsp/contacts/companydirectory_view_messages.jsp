<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.communications.base.Campaign" %>
<jsp:useBean id="campList" class="org.aspcfs.modules.communications.base.CampaignList" scope="request"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="ContactMessageListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<dhv:evaluate exp="<%= !isPopup(request) %>">
<a href="ExternalContacts.do">General Contacts</a> > 
<a href="ExternalContacts.do?command=SearchContacts">Search Results</a> >
<a href="ExternalContacts.do?command=ContactDetails&id=<%=ContactDetails.getId()%>">Contact Details</a> >
Messages<br>
<hr color="#BFBFBB" noshade>
</dhv:evaluate>
<%@ include file="contact_details_header_include.jsp" %>
<% String param1 = "id=" + ContactDetails.getId(); 
   String param2 = addLinkParams(request, "popup|popupType|actionId"); %>
<dhv:container name="contacts" selected="messages" param="<%= param1 %>" appendToUrl="<%= param2 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
<br>
<center><%= ContactMessageListInfo.getAlphabeticalPageLinks() %></center>
<table width="100%" border="0">
  <tr>
    <form name="listView" method="post" action="ExternalContacts.do?command=ViewMessages&contactId=<%=ContactDetails.getId()%>">
    <td align="left">
      <select size="1" name="listView" onChange="javascript:document.forms[0].submit();">
        <option <%= ContactMessageListInfo.getOptionValue("my") %>>My Messages</option>
        <option <%= ContactMessageListInfo.getOptionValue("all") %>>All Messages</option>
      </select>
    </td>
    <td>
      <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="ContactMessageListInfo"/>
    </td>
    <%= addHiddenParams(request, "popup|popupType|actionId") %>
    </form>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th width="8">
      <strong>Action</strong>
    </th>
    <th width="65%" nowrap>
      <a href="ExternalContacts.do?command=ViewMessages&column=c.name&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>"><strong>Name</strong></a>
      <%= ContactMessageListInfo.getSortIcon("c.name") %>
    </th>
    <th width="20%" nowrap>
      <a href="ExternalContacts.do?command=ViewMessages&column=active_date&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>"><strong>Run Date</strong></a>
      <%= ContactMessageListInfo.getSortIcon("active_date") %>
    </th>
    <th width="15%">
      <strong>Status</strong>
    </th>
  </tr>
<%
    Iterator j = campList.iterator();
    if ( j.hasNext() ) {
      int rowid = 0;
      while (j.hasNext()) {
        rowid = (rowid != 1?1:2);
        Campaign campaign = (Campaign)j.next();
%>
  <tr class="containerBody">
    <td width="8" valign="top" nowrap class="row<%= rowid %>">
      <%= (campaign.hasRun()?"&nbsp;":"<font color=\"#787878\">Cancel</font>") %>
    </td>
    <td class="row<%= rowid %>">
      <a href="ExternalContacts.do?command=MessageDetails&id=<%= campaign.getId() %>&contactId=<%=ContactDetails.getId()%><%= addLinkParams(request, "popup|popupType|actionId") %>"><%= toHtml(campaign.getMessageName()) %></a>
      <%= (("true".equals(request.getParameter("notify")) && ("" + campaign.getId()).equals(request.getParameter("id")))?" <font color=\"red\">(Cancelled)</font>":"") %>
    </td>
    <td valign="top" align="left" nowrap class="row<%= rowid %>">
      <%=toHtml(campaign.getActiveDateString())%>
    </td>
    <td valign="top" nowrap class="row<%= rowid %>">
      <%=toHtml(campaign.getStatus())%>
    </td>
	</tr>
	<%}%>
<%} else {%>
  <tr class="containerBody">
    <td colspan="7">
      No messages found.
    </td>
  </tr>
<%}%>
</table>
<br>
<dhv:pagedListControl object="ContactMessageListInfo"/>
<br>
</td></tr>
</table>
