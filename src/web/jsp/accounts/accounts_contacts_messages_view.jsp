<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.contacts.base.*, org.aspcfs.modules.communications.base.Campaign" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="CampaignList" class="org.aspcfs.modules.communications.base.CampaignList" scope="request"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="AccountContactMessageListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do">Accounts</a> > 
<% if (request.getParameter("return") == null) { %>
<a href="Accounts.do?command=Search">Search Results</a> >
<%} else if (request.getParameter("return").equals("dashboard")) {%>
<a href="Accounts.do?command=Dashboard">Dashboard</a> >
<%}%>
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Account Details</a> >
<a href="Contacts.do?command=View&orgId=<%=OrgDetails.getOrgId()%>">Contacts</a> >
<a href="Contacts.do?command=Details&id=<%=ContactDetails.getId()%>">Contact Details</a> >
Messages
</td>
</tr>
</table>
<%-- End Trails --%>
<%@ include file="accounts_details_header_include.jsp" %>
<dhv:container name="accounts" selected="contacts" param="<%= "orgId=" + OrgDetails.getOrgId() %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
    <% String param1 = "id=" + ContactDetails.getId(); 
    %>
        <strong><%= ContactDetails.getNameLastFirst() %>:</strong>
        [ <dhv:container name="accountscontacts" selected="messages" param="<%= param1 %>"/> ]
      <br>
      <br>
      <table width="100%" border="0">
        <tr>
          <td align="left">
          <form name="listView" method="post" action="Contacts.do?command=ViewMessages&contactId=<%=ContactDetails.getId()%>">
            <select size="1" name="listView" onChange="javascript:document.forms[0].submit();">
              <option <%= AccountContactMessageListInfo.getOptionValue("my") %>>My Messages</option>
              <option <%= AccountContactMessageListInfo.getOptionValue("all") %>>All Messages</option>
            </select>
            </form>
          </td>
          <td>
            <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="AccountContactMessageListInfo"/>
          </td>
        </tr>
      </table>
      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
        <tr>
          <th width="8">
            <strong>Action</strong>
          </th>
          <th width="65%" nowrap>
            <a href="Contacts.do?command=ViewMessages&column=c.name&contactId=<%= ContactDetails.getId() %>"><strong>Name</strong></a>
            <%= AccountContactMessageListInfo.getSortIcon("c.name") %>
          </th>
          <th width="20%" nowrap>
            <a href="Contacts.do?command=ViewMessages&column=active_date&contactId=<%= ContactDetails.getId() %>"><strong>Run Date</strong></a>
            <%= AccountContactMessageListInfo.getSortIcon("active_date") %>
          </th>
          <th width="15%">
            <strong>Status</strong>
          </th>
        </tr>
  <%
    Iterator j = CampaignList.iterator();
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
            <a href="Contacts.do?command=MessageDetails&id=<%= campaign.getId() %>&contactId=<%=ContactDetails.getId()%>"><%= toHtml(campaign.getMessageName()) %></a>
            <%= (("true".equals(request.getParameter("notify")) && ("" + campaign.getId()).equals(request.getParameter("id")))?" <font color=\"red\">(Cancelled)</font>":"") %>
          </td>
          <td valign="top" align="left" nowrap class="row<%= rowid %>">
            <zeroio:tz timestamp="<%= campaign.getActiveDate() %>" dateOnly="true" default="&nbsp;"/>
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
<dhv:pagedListControl object="AccountContactMessageListInfo"/>
<br>
<input type="hidden" name="id" value="<%=ContactDetails.getId()%>">
    </td>
  </tr>
</table>
