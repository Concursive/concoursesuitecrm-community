<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="quoteListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="sourceSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="statusSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="categorySelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript">
  function clearForm() {
    document.forms['searchQuote'].listFilter1.options.selectedIndex = 0;
    document.forms['searchQuote'].listFilter2.options.selectedIndex = 0;
    document.forms['searchQuoteNumber'].quoteId.value="";
    document.forms['searchQuoteNumber'].quoteId.focus();
  }
</script>
<body onLoad="javascript:document.forms['searchQuoteNumber'].quoteId.focus();">
<%-- Trails --%>
<table class="trails" cellspacing="0">
  <tr>
    <td>
      <a href="Quotes.do">Quotes</a> > 
      Search Quotes
    </td>
  </tr>
</table>
<%-- End Trails --%>
<%= showError(request, "actionError", false) %>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Search Quotes By Number</strong>
    </th>
  </tr>
<form name="searchQuoteNumber" action="Quotes.do?command=Details" method="post">
  <tr>
    <td class="formLabel">
      Quote #
    </td>
    <td>
      <input type="text" size="10" name="quoteId"/>
      <input type="submit" value="Search">
    </td>
  </tr>
</form>
</table>
<form name="searchQuote" action="Quotes.do?command=Search" method="post">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Search Quotes By Category</strong>
    </th>
  </tr>
  <tr>
    <td class="formLabel">
      Quote Status
    </td>
    <td>
      <%= statusSelect.getHtmlSelect("listFilter2", quoteListInfo.getFilterKey("listFilter2")) %>
    </td>
  </tr>
  <tr>
    <td class="formLabel">
      Product Category
    </td>
    <td>
      <%= categorySelect.getHtml("listFilter1", quoteListInfo.getFilterKey("listFilter1")) %>
    </td>
  </tr>
</table>
<br />
<input type="submit" value="Search">
<input type="button" value="Clear" onClick="javascript:clearForm();">
</form>
</body>
