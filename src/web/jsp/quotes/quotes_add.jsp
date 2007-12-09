<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.*,java.text.DateFormat,org.aspcfs.modules.products.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.quotes.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="quoteBean" class="org.aspcfs.modules.quotes.base.Quote" scope="request"/>
<jsp:useBean id="quoteStatusList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="quoteDeliveryList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="opportunity" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<jsp:useBean id="contactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="fileItemList" class="com.zeroio.iteam.base.FileItemList" scope="request"/>
<jsp:useBean id="systemStatus" class="org.aspcfs.controller.SystemStatus" scope="request"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popOpportunities.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/executeFunction.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/moveContact.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkEmail.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkPhone.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popContactAddressListSingle.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popContactEmailAddressListSingle.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popContactPhoneNumberListSingle.js"></script>
<%@ include file="../initPage.jsp" %>
<body onLoad="javascript:document.addQuote.shortDescription.focus();">
<%-- Trails --%>
<table class="trails" cellspacing="0">
  <tr>
    <td>
      <a href="Quotes.do"><dhv:label name="dependency.quotes">Quotes</dhv:label></a> >
      <a href="Quotes.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
      <dhv:label name="quotes.addQuote">Add Quote</dhv:label>
    </td>
  </tr>
</table>
<%-- End Trails --%>
<% Quote quote = quoteBean; %>
<iframe src="../empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<form method="post" name="addQuote" action="Quotes.do?command=AddQuote&auto-populate=true" onSubmit="return checkForm(this);">
<dhv:evaluate if="<%= !isPopup(request) %>">
<input type="submit" value="<dhv:label name="global.button.insert">Insert</dhv:label>"/>
<input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='Quotes.do?command=Search&orgId=<%= OrgDetails.getId() %>';"/>
</dhv:evaluate>
 <dhv:evaluate if="<%= isPopup(request) %>">
<input type="submit" value="<dhv:label name="global.button.save">Save</dhv:label>"/>
<input value="Cancel" onclick="javascript:self.close();" type="button">
</dhv:evaluate>


<br />
<%= showError(request, "actionError") %>
<%
  String title = "Add a new Quote";
  String titleLabel = "quotes.addQuote";
  String quoteParams = "";
  boolean changeAccount = true;
  boolean changeContact = true;
  boolean changeOpportunity = true;
  String opportunityName = opportunity.getDescription() != null ? opportunity.getDescription() : "Opportunity Selected";
  String opportunityNameLabel = opportunity.getDescription() != null? "":"quotes.opportunitySelected";
%>
<%@ include file="quotes_modify_include.jsp" %>
&nbsp;
<br />
<dhv:evaluate if="<%= !isPopup(request) %>">
<input type="submit" value="<dhv:label name="global.button.insert">Insert</dhv:label>" />
<input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='Quotes.do?command=Search&orgId=<%= OrgDetails.getId() %>';" />
</dhv:evaluate>
<dhv:evaluate if="<%= isPopup(request) %>">
<input type="submit" value="<dhv:label name="global.button.save">Save</dhv:label>" />
<input value="Cancel" onclick="javascript:self.close();" type="button">
</dhv:evaluate>

</form>

