<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="org.aspcfs.modules.base.*" %>
<jsp:useBean id="quoteListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="sourceSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="statusSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%--<jsp:useBean id="categorySelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/> --%>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkRadioButton.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkNumber.js"></script>
<script language="JavaScript">
  function checkForm(form) {
    formTest = true;
    message = "";
    if (!checkNullString(form.searchcodeGroupId.value) && !checkNaturalNumber(form.searchcodeGroupId.value)) {
      message += label("check.number.invalid","- Please enter a valid Number\r\n");
      formTest = false;
    }
    if (formTest == false) {
      alert(label("check.campaign.criteria","Criteria could not be processed, please check the following:\r\n\r\n") + message);
      return false;
    }
    return true;
  }
  
  function getSiteId() {
    var site = document.forms['searchQuote'].searchcodeSiteId;
    var siteId = '';
    <dhv:evaluate if="<%= User.getUserRecord().getSiteId() == -1 %>">
      siteId = site.options[site.options.selectedIndex].value;
    </dhv:evaluate><dhv:evaluate if="<%= User.getUserRecord().getSiteId() == -1 %>">
      siteId = site.value;
    </dhv:evaluate>
     if (siteId == '<%= Constants.INVALID_SITE %>') {
      siteId = '<%= User.getUserRecord().getSiteId() %>&includeAllSites=true';
     } else {
      siteId = siteId + '&thisSiteIdOnly=true&exclusiveToSite=true';
     }
     return siteId;
  }
  
  function clearForm() {
//    document.forms['searchQuote'].listFilter1.options.selectedIndex = 0;
    document.forms['searchQuote'].listFilter1.options.selectedIndex = 0;
    document.forms['searchQuote'].searchcodeGroupId.value="";
    setSelectedRadio(document.forms['searchQuote'].searchcodeClosedOnly,'-1');
    setSelectedRadio(document.forms['searchQuote'].searchcodeSubmitAction,'-1');
    document.forms['searchQuote'].searchProductName.value="";
    document.forms['searchQuote'].searchSku.value="";
    <dhv:evaluate if="<%=User.getUserRecord().getSiteId() == -1 %>" >
      document.forms['searchQuote'].searchcodeSiteId.options.selectedIndex = 0;
    </dhv:evaluate>
    document.forms['searchQuote'].searchcodeGroupId.focus();
  }
</script>
<body onLoad="javascript:document.forms['searchQuote'].searchcodeGroupId.focus();">
<form name="searchQuote" action="Quotes.do?command=Search" method="post" onSubmit="javascript:return checkForm(this);">
<%-- Trails --%>
<table class="trails" cellspacing="0">
  <tr>
    <td>
      <a href="Quotes.do"><dhv:label name="dependency.quotes">Quotes</dhv:label></a> > 
      <dhv:label name="quotes.searchQuotes">Search Quotes</dhv:label>
    </td>
  </tr>
</table>
<%-- End Trails --%>
<%= showError(request, "actionError", false) %>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="quotes.searchQuotes">Search Quotes</dhv:label></strong>
    </th>
  </tr>
  <tr>
    <td class="formLabel">
      <dhv:label name="quotes.symbol.number" param="number=">Quote #</dhv:label>
    </td>
    <td>
      <input type="text" size="10" name="searchcodeGroupId" value="<%= toHtmlValue(quoteListInfo.getSearchOptionValue("searchcodeGroupId")) %>"/>
      <%= showAttribute(request, "groupIdError") %>
    </td>
  </tr><tr>
    <td class="formLabel">
      <dhv:label name="accounts.accounts_quotes_list.QuoteStatus">Quote Status</dhv:label>
    </td>
    <td>
      <%= statusSelect.getHtmlSelect("listFilter1", quoteListInfo.getFilterKey("listFilter1")) %>
    </td>
  </tr>
  <tr>
    <td class="formLabel">
      <dhv:label name="quotes.openOrClosed">Open/Closed</dhv:label>
    </td>
    <td>
      <input type="radio" name="searchcodeClosedOnly" value="<%= Constants.UNDEFINED %>" <%= (quoteListInfo.getSearchOptionValue("searchcodeClosedOnly") == null || "".equals(quoteListInfo.getSearchOptionValue("searchcodeClosedOnly")) || quoteListInfo.getSearchOptionValue("searchcodeClosedOnly").equals(""+Constants.UNDEFINED))?"checked":"" %> /> <dhv:label name="quotes.all">All</dhv:label> &nbsp;
      <input type="radio" name="searchcodeClosedOnly" value="<%= Constants.FALSE %>" <%= quoteListInfo.getSearchOptionValue("searchcodeClosedOnly").equals(""+Constants.FALSE)?"checked":"" %> /> <dhv:label name="quotes.open">Open</dhv:label> &nbsp;
      <input type="radio" name="searchcodeClosedOnly" value="<%= Constants.TRUE %>" <%= (quoteListInfo.getSearchOptionValue("searchcodeClosedOnly").equals(""+Constants.TRUE)?"checked":"") %> /> <dhv:label name="quotes.closed">Closed</dhv:label> &nbsp;
    </td>
  </tr>
  <tr>
    <td class="formLabel">
      <dhv:label name="quotes.submitStatus">Submit Status</dhv:label>
    </td>
    <td>
      <input type="radio" name="searchcodeSubmitAction" value="<%= Constants.UNDEFINED %>" <%= (quoteListInfo.getSearchOptionValue("searchcodeSubmitAction") == null || "".equals(quoteListInfo.getSearchOptionValue("searchcodeSubmitAction")) || quoteListInfo.getSearchOptionValue("searchcodeSubmitAction").equals(""+Constants.UNDEFINED))?"checked":"" %> /> <dhv:label name="quotes.all">All</dhv:label> &nbsp;
      <input type="radio" name="searchcodeSubmitAction" value="<%= Constants.FALSE %>" <%= (quoteListInfo.getSearchOptionValue("searchcodeSubmitAction").equals(""+Constants.FALSE)?"checked":"") %> /> <dhv:label name="quotes.notSubmitted">Not Submitted</dhv:label> &nbsp;
      <input type="radio" name="searchcodeSubmitAction" value="<%= Constants.TRUE %>" <%= (quoteListInfo.getSearchOptionValue("searchcodeSubmitAction").equals(""+Constants.TRUE)?"checked":"") %> /> <dhv:label name="quotes.submitted">Submitted</dhv:label> &nbsp;
    </td>
  </tr>
<%--
  <tr>
    <td class="formLabel">
      <dhv:label name="products.productCategory">Product Category</dhv:label>
    </td>
    <td>
      <%= categorySelect.getHtml("listFilter1", quoteListInfo.getFilterKey("listFilter1")) %>
    </td>
  </tr>
--%>
  <tr>
    <td class="formLabel">
      <dhv:label name="products.productName">Product Name</dhv:label>
    </td>
    <td>
      <input type="text" size="25" name="searchProductName" value="<%= quoteListInfo.getSearchOptionValue("searchProductName") %>"/>
    </td>
  </tr>
  <tr>
    <td class="formLabel">
      <dhv:label name="quotes.sku">Product SKU</dhv:label>
    </td>
    <td>
      <input type="text" size="15" name="searchSku" value="<%= quoteListInfo.getSearchOptionValue("searchSku") %>"/>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="accounts.site">Site</dhv:label>
    </td>
    <td>
      <dhv:evaluate if="<%=User.getUserRecord().getSiteId() == -1 %>" >
        <%= SiteList.getHtmlSelect("searchcodeSiteId", ("".equals(quoteListInfo.getSearchOptionValue("searchcodeSiteId")) ? String.valueOf(User.getUserRecord().getSiteId()) : quoteListInfo.getSearchOptionValue("searchcodeSiteId"))) %>
      </dhv:evaluate>
      <dhv:evaluate if="<%=User.getUserRecord().getSiteId() != -1 %>" >
        <input type="hidden" name="searchcodeSiteId" value="<%= User.getUserRecord().getSiteId() %>">
        <%= SiteList.getSelectedValue(User.getUserRecord().getSiteId()) %>
      </dhv:evaluate>
    </td>
  </tr>
</table>
<br />
<input type="submit" value="<dhv:label name="button.search">Search</dhv:label>">
<input type="button" value="<dhv:label name="button.clear">Clear</dhv:label>" onClick="javascript:clearForm();">
</form>
</body>
