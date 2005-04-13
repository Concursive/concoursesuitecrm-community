<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.pipeline.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="oppList" class="org.aspcfs.modules.pipeline.base.OpportunityHeaderList" scope="request"/>
<jsp:useBean id="opportunityListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popOpportunities.js"></SCRIPT>
<script language="javascript">
  function submitPage(headerId, displayValue, hiddenFieldId, displayFieldId) {
    opener.changeDivContent(displayFieldId, displayValue);
    opener.setParentHiddenField(hiddenFieldId, headerId);
    self.close();
  }
</script>

<%--<center><%= opportunityListInfo.getAlphabeticalPageLinks() %></center>--%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th colspan="2" width="100%">
      <strong><dhv:label name="quotes.opportunityList">Opportunity List</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBack">
    <td width="100%" nowrap>
      <strong><dhv:label name="accounts.accounts_contacts_oppcomponent_list.OpportunityName">Opportunity Name</dhv:label></strong>
    </td>
    <td nowrap>
      <strong><dhv:label name="accounts.accounts_contacts_oppcomponent_list.LastModified">Last Modified</dhv:label></strong>
    </td>
  </tr>
<%
	Iterator j = (Iterator) oppList.iterator();
	if ( oppList.size() > 0 ) {
		int rowid = 0;
    int i = 0;
	    while (j.hasNext()) {
        i++;
		    rowid = (rowid !=1?1:2);
        OpportunityHeader oppHeader = (OpportunityHeader)j.next();
%>      
  <tr class="containerBody">
    <td valign="center" class="row<%= rowid %>">
      <a href="javascript:submitPage('<%= oppHeader.getId() %>', '<%= toHtml(oppHeader.getDescription()) %>', '<%= request.getAttribute("hiddenFieldId") %>','<%= request.getAttribute("displayFieldId") %>');">
      <%= toHtml(oppHeader.getDescription()) %></a>
      (<%= oppHeader.getComponentCount() %>)
    </td>  
    <td valign="center" class="row<%= rowid %>" nowrap>
      <dhv:tz timestamp="<%= oppHeader.getModified() %>" dateFormat="<%= DateFormat.SHORT %>" timeFormat="<%= DateFormat.LONG %>"/>
    </td>   
  </tr>
<%}%>
<%} else {%>
  <tr class="containerBody">
    <td colspan="2">
      <dhv:label name="accounts.accounts_contacts_oppcomponent_list.NoOpportunitiesFound">No opportunities found.</dhv:label>
    </td>
  </tr>
<%}%>
</table>
<br>
<input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.close();"/>
<%--<dhv:pagedListControl object="opportunityListInfo"/>--%>
</td>
</tr>
</table>

