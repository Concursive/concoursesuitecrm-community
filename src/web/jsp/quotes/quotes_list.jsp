<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.*,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.quotes.base.*,org.aspcfs.modules.products.base.*" %>
<jsp:useBean id="quoteList" class="org.aspcfs.modules.quotes.base.QuoteList" scope="request"/>
<jsp:useBean id="quoteListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="quoteStatusList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="quotes_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/executeFunction.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/moveContact.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" type="text/javascript">
<%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<script type="text/javascript">
  function reopen(){
    window.location.href='Quotes.do?command=Search';
  }
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
  <tr>
    <td>
      <a href="Quotes.do">Quotes</a> >
      Search Results
    </td>
  </tr>
</table>
<%-- End Trails --%>
<%-- Begin the container contents --%>
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="quoteListInfo"/>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
<tr>
  <th width="8">
    <strong>Action</strong>
  </th>
  <th>
    <strong><a href="Quotes.do?command=Search&column=qe.quote_id">Quote<br />Number</a></strong>
    <%= quoteListInfo.getSortIcon("qe.quote_id") %>
  </th>
  <th>
    <strong><a href="Quotes.do?command=Search&column=qe.org_id">Account</a></strong>
    <%= quoteListInfo.getSortIcon("qe.org_id") %>
  </th>
  <th>
    <strong><a href="Quotes.do?command=Search&column=qe.issued">Quote Issued</a></strong>
    <%= quoteListInfo.getSortIcon("qe.issued") %>
  </th>
  <th>
    <strong><a href="Quotes.do?command=Search&column=qe.short_description">Description</a></strong>
    <%= quoteListInfo.getSortIcon("qe.short_description") %>
  </th>
  <th>
    <strong><a href="Quotes.do?command=Search&column=qe.status_id">Quote Status</a></strong>
    <%= quoteListInfo.getSortIcon("qe.status_id") %>
  </th>
</tr>
<%
	Iterator j = quoteList.iterator();
	if ( j.hasNext() ) {
    int rowid = 0;
    int i = 0;
    while (j.hasNext()) {
    Quote thisQuote = (Quote)j.next();
    i++;
    rowid = (rowid != 1 ? 1 : 2);
%>      
		<tr class="containerBody">
      <td valign="center" nowrap class="row<%= rowid %>">
        <%-- Use the unique id for opening the menu, and toggling the graphics --%>
         <a href="javascript:displayMenu('menuQuote', '<%= thisQuote.getId() %>');"
         onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>)"><img src="images/select.gif" name="select<%= i %>" align="absmiddle" border="0"></a>
      </td>
      <td valign="center" width="20%" class="row<%= rowid %>">
        <a href="Quotes.do?command=Details&quoteId=<%=thisQuote.getId()%>">Quote #<%= thisQuote.getId() %></a>
      </td>
      <td valign="center" width="20%" class="row<%= rowid %>">
        <%= toHtml(thisQuote.getName()) %>
      </td>
      <td valign="center" width="20%" class="row<%= rowid %>">
<%
      if(thisQuote.getIssuedDate() != null){
%>
        <dhv:tz timestamp="<%= thisQuote.getIssuedDate() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>"/>
<%    
      }else{
%>
        not issued
<%    
      }
%>
      </td>
      <td valign="center" width="50%" class="row<%= rowid %>">
        <%= toHtml(thisQuote.getShortDescription()) %>
      </td>
      <td valign="center" class="row<%= rowid %>" nowrap>
        <%= toHtml(quoteStatusList.getValueFromId(thisQuote.getStatusId())) %>
      </td>
   </tr>
<% } %>
<%} else {%>
		<tr class="containerBody">
      <td colspan="6">
        No Quotes found.
      </td>
    </tr>
<%}%>
	</table>
	<br>
  <dhv:pagedListControl object="quoteListInfo"/>
