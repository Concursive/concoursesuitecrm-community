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
<a href="Accounts.do?command=Search">Search Results</a> >
<%} else if (request.getParameter("return").equals("dashboard")) {%>
<a href="Accounts.do?command=Dashboard">Dashboard</a> >
<%}%>
<a href="Accounts.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
  Relationships
</td>
</tr>
</table>
<%-- End Trails --%>
<%@ include file="accounts_details_header_include.jsp" %>
<% String param1 = "orgId=" + OrgDetails.getOrgId(); %>      
<dhv:container name="accounts" selected="relationships" param="<%= param1 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
      <dhv:permission name="accounts-accounts-relationships-add"><a href="AccountRelationships.do?command=Add&orgId=<%= OrgDetails.getOrgId() %>">Add a  Relationship</a><br><br></dhv:permission>
      <%-- list --%>
      <%
        int count = 0;
        Iterator rt =  relationshipList.keySet().iterator();
        if ( rt.hasNext() ) {
        while(rt.hasNext()){
        String relType = (String) rt.next();
        ArrayList tmpList = (ArrayList) relationshipList.get(relType);
        Iterator j =  tmpList.iterator();
      %>
      <table align="center" width="100%" cellpadding="4" cellspacing="0" border="0"><tr><th nowrap valign="bottom" align="left" class="pagedListTab"><%= toHtml(relType) %></th><td nowrap width="100%" valign="bottom" align="left">&nbsp;</td></tr></table>


      <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
      <tr>
        <th valign="center" align="left">
          <strong>Action</strong>
        </th>
        <th>
          <strong>Related to <dhv:label name="accounts.account">Account</dhv:label></strong>
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
              No relationships found.
            </td>
          </tr>
      <%}%>
      </table><%= rt.hasNext() ? "<br><br>": ""%>
      <%}
     }else{%>
       <strong>No relationships found.</strong>
     <%}%>
    </td>
  </tr>
</table>



