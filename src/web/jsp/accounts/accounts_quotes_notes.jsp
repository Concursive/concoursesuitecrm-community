<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.*,java.text.DateFormat,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.products.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.quotes.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="quote" class="org.aspcfs.modules.quotes.base.Quote" scope="request"/>
<jsp:useBean id="quoteStatusList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="quoteBean" class="org.aspcfs.modules.quotes.base.Quote" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="quoteNoteList" class="org.aspcfs.modules.quotes.base.QuoteNoteList" scope="request"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<%@ include file="../initPage.jsp" %>
<script type="text/javascript">
  function checkComplete() {
    var note = document.forms['form_notes'].notes.value;
    if( note == ""){
      alert('Please enter the notes to be emailed to the Customer to continue');
      return false;
    }
    if (confirm('The following notes are going to be saved and emailed to the Customer\r\n' +
        '\r\n' + note + '\r\n'+
        'Are you sure you want to save and email these notes?')) {
        return true;
    } else {
      return false;
    }
  }
</script>
<form method="post" action="AccountQuotes.do?command=SaveNotes&quoteId=<%= quote.getId() %>&auto-populate=true">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do">Accounts</a> > 
<a href="Accounts.do?command=Search">Search Results</a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Account Details</a> >
<a href="AccountQuotes.do?command=View&orgId=<%=OrgDetails.getOrgId()%>">Quotes</a> >
Quote Details
</td>
</tr>
</table>
<%-- End Trails --%>
<%@ include file="accounts_details_header_include.jsp" %>
<dhv:container name="accounts" selected="quotes" param="<%= "orgId=" + OrgDetails.getOrgId() %>" style="tabs"/>
<table cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack" colspan="2">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <td align="left">
      <strong>Quote #<%= quote.getId() %> </strong> &nbsp;
      [ <dhv:container name="accountsQuotes" selected="notes" param="<%= "quoteId=" + quote.getId() %>"/> ]

    </td>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th><strong>Date</strong></th>
    <th><strong>Entered By</strong></th>
    <th><strong>Details</strong></th>
  </tr>
<%
  int rowid=0;
  int i=0;
  Iterator iterator = (Iterator) quoteNoteList.iterator();
  while(iterator.hasNext()){
    QuoteNote quoteNote = (QuoteNote) iterator.next();
    i++;
    rowid = ( rowid != 1 ? 1:2 );
%>
  <tr>
    <td class="row<%= rowid %>" width="20%">
      <dhv:tz timestamp="<%= quoteNote.getEntered() %>" dateOnly="false" dateFormat="<%= DateFormat.SHORT %>"/>
    </td>
    <td class="row<%= rowid %>" width="10%">
      <dhv:username id="<%= quoteNote.getEnteredBy() %>" />
    </td>
    <td class="row<%= rowid %>" width="70%">
      <%= toHtml(quoteNote.getNotes())%>
    </td>
  </tr>
<%}%>
</table>
<br />
<%
if(!quoteStatusList.getValueFromId(quote.getStatusId()).equals("Accepted by customer")){
%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th width="100%">
      <strong>Please enter new notes here</strong><br />
    </th>
  </tr>
  <tr>
    <td>
      <textarea name="notes" rows="5" cols="65"></textarea>
    </td>
  </tr>
</table>
<input type="submit" value="Save Notes"/>
<%
}
%>
</td>
</tr>
</table>
</form>
