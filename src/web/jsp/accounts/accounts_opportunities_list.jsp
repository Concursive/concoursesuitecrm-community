<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.pipeline.base.OpportunityHeader,com.zeroio.iteam.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="OpportunityList" class="org.aspcfs.modules.pipeline.base.OpportunityHeaderList" scope="request"/>
<jsp:useBean id="OpportunityPagedInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="accounts_opportunities_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do">Accounts</a> > 
<a href="Accounts.do?command=Search">Search Results</a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Account Details</a> >
Opportunities
</td>
</tr>
</table>
<%-- End Trails --%>
<%@ include file="accounts_details_header_include.jsp" %>
<dhv:container name="accounts" selected="opportunities" param="<%= "orgId=" + OrgDetails.getOrgId() %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
      <%-- Begin container content --%>
<dhv:permission name="accounts-accounts-opportunities-add"><a href="Opportunities.do?command=Add&orgId=<%= request.getParameter("orgId") %>">Add an Opportunity</a></dhv:permission>
<center><%= OpportunityPagedInfo.getAlphabeticalPageLinks() %></center>
<table width="100%" border="0">
  <tr>
    <form name="listView" method="post" action="Opportunities.do?command=View&orgId=<%= OrgDetails.getOrgId() %>">
    <td align="left">
      <select size="1" name="listView" onChange="javascript:document.forms[0].submit();">
        <option <%= OpportunityPagedInfo.getOptionValue("my") %>>My Open Opportunities </option>
        <option <%= OpportunityPagedInfo.getOptionValue("all") %>>All Open Opportunities</option>
        <option <%= OpportunityPagedInfo.getOptionValue("closed") %>>All Closed Opportunities</option>
      </select>
    </td>
    <td>
      <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="OpportunityPagedInfo"/>
    </td>
    </form>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th width="8" nowrap>
      <strong>Action</strong>
    </th>
    <th width="100%" nowrap>
      <strong><a href="Opportunities.do?command=View&orgId=<%= OrgDetails.getId() %>&column=x.description">Opportunity Name</a></strong>
      <%= OpportunityPagedInfo.getSortIcon("x.description") %>
    </th>
    <th nowrap>
      <strong>Best Guess Total</strong>
    </th>
    <th nowrap>
      <strong><a href="Opportunities.do?command=View&orgId=<%= OrgDetails.getId() %>&column=x.modified">Last Modified</a></strong>
      <%= OpportunityPagedInfo.getSortIcon("x.modified") %>
    </th>
  </tr>
<%
	Iterator j = OpportunityList.iterator();
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
    <td width="8" valign="center" nowrap class="row<%= rowid %>">
      <%-- Use the unique id for opening the menu, and toggling the graphics --%>
      <%-- To display the menu, pass the actionId, accountId and the contactId--%>
      <a href="javascript:displayMenu('menuOpp','<%= OrgDetails.getId() %>','<%= oppHeader.getId() %>');" onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>)"><img src="images/select.gif" name="select<%= i %>" align="absmiddle" border="0"></a>
    </td>
    <td valign="center" class="row<%= rowid %>">
      <a href="Opportunities.do?command=Details&headerId=<%= oppHeader.getId() %>&orgId=<%= OrgDetails.getId() %>&reset=true">
      <%= toHtml(oppHeader.getDescription()) %></a>
      (<%= oppHeader.getComponentCount() %>)
      <dhv:evaluate if="<%= oppHeader.hasFiles() %>">
      <%= thisFile.getImageTag() %>
      </dhv:evaluate>
    </td>  
    <td valign="center" align="right" class="row<%= rowid %>" nowrap>
      $<%= toHtml(oppHeader.getTotalValueCurrency()) %>
    </td>      
    <td valign="center" class="row<%= rowid %>" nowrap>
      <dhv:tz timestamp="<%= oppHeader.getModified() %>" dateFormat="<%= DateFormat.SHORT %>" timeFormat="<%= DateFormat.LONG %>"/>
    </td>   
  </tr>
<%}%>
<%} else {%>
  <tr class="containerBody">
    <td colspan="5">
      No opportunities found.
    </td>
  </tr>
<%}%>
</table>
<br>
<dhv:pagedListControl object="OpportunityPagedInfo"/>
</td>
</tr>
</table>

