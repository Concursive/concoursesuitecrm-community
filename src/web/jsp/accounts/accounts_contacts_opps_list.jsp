<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.pipeline.base.OpportunityHeader,com.zeroio.iteam.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="OpportunityHeaderList" class="org.aspcfs.modules.pipeline.base.OpportunityHeaderList" scope="request"/>
<jsp:useBean id="AccountContactOppsPagedListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="accounts_contacts_opps_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<dhv:evaluate if="<%= !isPopup(request) %>">
<a href="Accounts.do">Account Management</a> > 
<a href="Accounts.do?command=View">View Accounts</a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Account Details</a> >
<a href="Contacts.do?command=View&orgId=<%=OrgDetails.getOrgId()%>">Contacts</a> >
<a href="Contacts.do?command=Details&id=<%=ContactDetails.getId()%>&orgId=<%=OrgDetails.getOrgId()%>">Contact Details</a> >
Opportunities<br>
<hr color="#BFBFBB" noshade>
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
          <th>
            <strong>Action</strong>
          </th>
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
          int i = 0;
            while (j.hasNext()) {
              i++;
              rowid = (rowid != 1?1:2);
              OpportunityHeader oppHeader = (OpportunityHeader)j.next();
      %>      
          <tr class="containerBody">
            <td width="8" valign="top" align="center" class="row<%= rowid %>" nowrap>
              <%-- Use the unique id for opening the menu, and toggling the graphics --%>
              <%-- To display the menu, pass the actionId, accountId and the contactId--%>
              <a href="javascript:displayMenu('menuOpp','<%= oppHeader.getId() %>','<%= oppHeader.getContactLink() %>');" onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>)"><img src="images/select.gif" name="select<%= i %>" align="absmiddle" border="0"></a>
            </td>
            <td width="100%" valign="top" class="row<%= rowid %>">
              <a href="AccountContactsOpps.do?command=DetailsOpp&headerId=<%= oppHeader.getId() %>&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>">
              <%= toHtml(oppHeader.getDescription()) %></a>
              (<%= oppHeader.getComponentCount() %>)
              <dhv:evaluate if="<%= oppHeader.hasFiles() %>">
                <%= thisFile.getImageTag() %>
              </dhv:evaluate>
            </td>
            <td valign="top" align="right" class="row<%= rowid %>" nowrap>
              $<%= toHtml(oppHeader.getTotalValueCurrency()) %>
            </td>      
            <td valign="top" align="center" class="row<%= rowid %>" nowrap>
             <dhv:tz timestamp="<%= oppHeader.getModified() %>" dateFormat="<%= DateFormat.SHORT %>" timeFormat="<%= DateFormat.LONG %>"/>
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


