<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.communications.base.Campaign" %>
<jsp:useBean id="campList" class="org.aspcfs.modules.communications.base.CampaignList" scope="request"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="ContactMessageListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<a href="ExternalContacts.do">General Contacts</a> > 
<a href="ExternalContacts.do?command=ListContacts">View Contacts</a> >
<a href="ExternalContacts.do?command=ContactDetails&id=<%=ContactDetails.getId()%>">Contact Details</a> >
Messages<br>
<hr color="#BFBFBB" noshade>
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
    </form>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td width="8">
      <strong>Action</strong>
    </td>
    <td width="65%" nowrap>
      <a href="ExternalContacts.do?command=ViewMessages&column=c.name&contactId=<%= ContactDetails.getId() %>"><strong>Name</strong></a>
      <%= ContactMessageListInfo.getSortIcon("c.name") %>
    </td>  
    <td width="20%" nowrap>
      <a href="ExternalContacts.do?command=ViewMessages&column=active_date&contactId=<%= ContactDetails.getId() %>"><strong>Run Date</strong></a>
      <%= ContactMessageListInfo.getSortIcon("active_date") %>
    </td> 
    <td width="15%">
      <strong>Status</strong>
    </td>
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
      <a href="ExternalContacts.do?command=MessageDetails&id=<%= campaign.getId() %>&contactId=<%=ContactDetails.getId()%>"><%= toHtml(campaign.getMessageName()) %></a>
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
