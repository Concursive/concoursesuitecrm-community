<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.pipeline.base.OpportunityHeader,com.zeroio.iteam.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="OpportunityHeaderList" class="org.aspcfs.modules.pipeline.base.OpportunityHeaderList" scope="request"/>
<jsp:useBean id="AccountContactOppsPagedListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<dhv:evaluate if="<%= !isPopup(request) %>">
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
<a href="Contacts.do?command=Details&id=<%=ContactDetails.getId()%>&orgId=<%=OrgDetails.getOrgId()%>">Contact Details</a> >
Opportunities
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<%@ include file="accounts_details_header_include.jsp" %>
<dhv:container name="accounts" selected="contacts" param="<%= "orgId=" + OrgDetails.getOrgId() %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
    <% String param1 = "id=" + ContactDetails.getId(); 
    %>
        <strong><%= ContactDetails.getNameLastFirst() %>:</strong>
        [ <dhv:container name="accountscontacts" selected="opportunities" param="<%= param1 %>"/> ]
      <br>
      <br>
      
      <dhv:permission name="accounts-accounts-contacts-opportunities-add"><a href="AccountContactsOpps.do?command=Prepare&contactId=<%= ContactDetails.getId() %>&actionSource=AccountContactsOpps<%= addLinkParams(request, "popup|popupType|actionId") %>">Add an Opportunity</a></dhv:permission>
      <center><%= AccountContactOppsPagedListInfo.getAlphabeticalPageLinks() %></center>
      <table width="100%" border="0">
        <tr>
          <form name="listView" method="post" action="AccountContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>">
          <td align="left">
            <select size="1" name="listView" onChange="javascript:document.forms[0].submit();">
              <option <%= AccountContactOppsPagedListInfo.getOptionValue("my") %>>My Open Opportunities </option>
              <option <%= AccountContactOppsPagedListInfo.getOptionValue("all") %>>All Open Opportunities</option>
              <option <%= AccountContactOppsPagedListInfo.getOptionValue("closed") %>>All Closed Opportunities</option>
            </select>
          </td>
          <td>
            <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="AccountContactOppsPagedListInfo"/>
          </td>
          </form>
        </tr>
      </table>
      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
        <tr>
        <dhv:permission name="accounts-accounts-contacts-opportunities-edit,accounts-accounts-contacts-opportunities-delete">
          <th>
            <strong>Action</strong>
          </th>
        </dhv:permission>
          <th nowrap>
            <strong><a href="AccountContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %>&column=x.description<%= addLinkParams(request, "popup|popupType|actionId") %>">Opportunity Name</a></strong>
            <%= AccountContactOppsPagedListInfo.getSortIcon("x.description") %>
          </th>
          <th nowrap>
            <strong>Best Guess Total</strong>
          </th>
          <th nowrap>
            <strong><a href="AccountContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %>&column=x.modified<%= addLinkParams(request, "popup|popupType|actionId") %>">Last Modified</a></strong>
            <%= AccountContactOppsPagedListInfo.getSortIcon("x.modified") %>
          </th>
        </tr>
      <%
        Iterator j = OpportunityHeaderList.iterator();
        FileItem thisFile = new FileItem();
        if ( j.hasNext() ) {
          int rowid = 0;
            while (j.hasNext()) {
              rowid = (rowid != 1?1:2);
              OpportunityHeader oppHeader = (OpportunityHeader)j.next();
      %>      
          <tr class="containerBody">
            <dhv:permission name="accounts-accounts-contacts-opportunities-edit,accounts-accounts-contacts-opportunities-delete">
            <td width="8" valign="top" align="center" class="row<%= rowid %>" nowrap>
              <dhv:permission name="accounts-accounts-contacts-opportunities-edit"><a href="AccountContactsOpps.do?command=ModifyOpp&headerId=<%= oppHeader.getId() %>&contactId=<%= oppHeader.getContactLink() %>&return=list<%= addLinkParams(request, "popup|popupType|actionId") %>">Edit</a></dhv:permission><dhv:permission name="accounts-accounts-contacts-opportunities-edit,accounts-accounts-contacts-opportunities-delete" all="true">|</dhv:permission><dhv:permission name="accounts-accounts-contacts-opportunities-delete"><a href="javascript:popURLReturn('AccountContactsOpps.do?command=ConfirmDelete&contactId=<%= ContactDetails.getId() %>&headerId=<%= oppHeader.getId() %>&popup=true<%= addLinkParams(request, "popupType|actionId") %>','AccountContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %>', 'Delete_opp','320','200','yes','no');">Del</a></dhv:permission>
            </td>
            </dhv:permission>
            <td width="100%" valign="top" class="row<%= rowid %>">
              <a href="AccountContactsOpps.do?command=DetailsOpp&headerId=<%= oppHeader.getId() %>&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>">
              <%= toHtml(oppHeader.getDescription()) %></a>
              (<%= oppHeader.getComponentCount() %>)
              <dhv:evaluate if="<%= oppHeader.hasFiles() %>">
                <%= thisFile.getImageTag() %>
              </dhv:evaluate>
            </td>
            <td valign="top" align="right" class="row<%= rowid %>" nowrap>
              <zeroio:currency value="<%= oppHeader.getTotalValue() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;"/>
            </td>      
            <td valign="top" align="center" class="row<%= rowid %>" nowrap>
              <zeroio:tz timestamp="<%= oppHeader.getModified() %>" />
            </td>       
          </tr>
      <%
          }
        } else {
      %>
          <tr class="containerBody">
            <td colspan="6">
              No opportunities found.
            </td>
          </tr>
      <%}%>
      </table>
      <br>
      <dhv:pagedListControl object="AccountContactOppsPagedListInfo"/>
   </td>
 </tr>
</table>


