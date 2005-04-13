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
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.relationships.base.*,org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="relationshipList" class="org.aspcfs.modules.relationships.base.RelationshipList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="accounts_relationships_view_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<%-- Preload image rollovers for drop-down menu --%>
<script language="JavaScript" type="text/javascript">
  loadImages('select');
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<% if (request.getParameter("return") == null) { %>
<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<%} else if (request.getParameter("return").equals("dashboard")) {%>
<a href="Accounts.do?command=Dashboard"><dhv:label name="communications.campaign.Dashboard">Dashboard</dhv:label></a> >
<%}%>
<a href="Accounts.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
  <dhv:label name="accounts.accounts_relationships_add.Relationships">Relationships</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:container name="accounts" selected="relationships" object="OrgDetails" param="<%= "orgId=" + OrgDetails.getOrgId() %>">
  <dhv:permission name="accounts-accounts-relationships-add"><a href="AccountRelationships.do?command=Add&orgId=<%= OrgDetails.getOrgId() %>"><dhv:label name="accounts.accounts_relationships_view.AddARelationship">Add a  Relationship</dhv:label></a><br><br></dhv:permission>
  <%-- list --%>
  <%
    int count = 0;
    Iterator rt =  relationshipList.keySet().iterator();
    if ( rt.hasNext() ) {
    while(rt.hasNext()) {
      String relType = (String) rt.next();
      ArrayList tmpList = (ArrayList) relationshipList.get(relType);
      Iterator j =  tmpList.iterator();
  %>
  <table align="center" width="100%" cellpadding="4" cellspacing="0" border="0"><tr><th nowrap valign="bottom" align="left" class="pagedListTab"><%= toHtml(relType) %></th><td nowrap width="100%" valign="bottom" align="left">&nbsp;</td></tr></table>
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th valign="center" align="left">
      &nbsp;
    </th>
    <th>
      <strong><dhv:label name="accounts.accounts_relationships_view.RelatedToAccount">Related to Account</dhv:label></strong>
    </th>
  </tr>
  <%
    if ( j.hasNext() ) {
      int rowid = 0;
      int i = 0;
      while (j.hasNext()) {
        i++;
        ++count;
        rowid = (rowid != 1?1:2);
        Relationship thisRelationship = (Relationship)j.next();
  %>
      <tr class="containerBody">
        <td width="8" valign="top" nowrap class="row<%= rowid %>">
          <%-- Use the unique id for opening the menu, and toggling the graphics --%>
           <a href="javascript:displayMenu('select<%= count %>','menuRelation','<%= OrgDetails.getOrgId() %>', '<%= thisRelationship.getId() %>');"
              onMouseOver="over(0, <%= count %>)"
              onmouseout="out(0, <%= count %>); hideMenu('menuRelation');"><img
              src="images/select.gif" name="select<%= count %>" id="select<%= count %>" align="absmiddle" border="0"></a>
        </td>
        <td valign="center" class="row<%= rowid %>">
          <%
            if (thisRelationship.getObjectIdMapsFrom() == OrgDetails.getOrgId()) {
              if(thisRelationship.getCategoryIdMapsFrom() == Constants.ACCOUNT_OBJECT){
                Organization mappedOrg = (Organization) thisRelationship.getMappedObject();
          %>
              <a href="Accounts.do?command=Details&orgId=<%= mappedOrg.getOrgId() %>"><%= toHtml(mappedOrg.getName()) %></a>
          <% }
            }else{
              if(thisRelationship.getCategoryIdMapsTo() == Constants.ACCOUNT_OBJECT){
                Organization mappedOrg = (Organization) thisRelationship.getMappedObject();
          %>
              <a href="Accounts.do?command=Details&orgId=<%= mappedOrg.getOrgId() %>"><%= toHtml(mappedOrg.getName()) %></a>
          <% }
            }
          %>
        </td>
      </tr>
  <%}
  } else {%>
      <tr class="containerBody">
        <td colspan="2">
          <dhv:label name="accounts.accounts_relationships_view.NoRelationshipsFound">No relationships found.</dhv:label>
        </td>
      </tr>
  <%}%>
  </table><%= rt.hasNext() ? "<br><br>": ""%>
  <%}
 }else{%>
   <strong><dhv:label name="accounts.accounts_relationships_view.NoRelationshipsFound">No relationships found.</dhv:label></strong>
 <%}%>
</dhv:container>