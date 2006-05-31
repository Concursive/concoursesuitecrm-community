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
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.*,java.text.DateFormat,org.aspcfs.modules.products.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.quotes.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="quoteStatusList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="quoteDeliveryList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="quoteBean" class="org.aspcfs.modules.quotes.base.Quote" scope="request"/>
<jsp:useBean id="opportunity" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<jsp:useBean id="contactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="version" class="java.lang.String" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="fileItemList" class="com.zeroio.iteam.base.FileItemList" scope="request"/>
<jsp:useBean id="systemStatus" class="org.aspcfs.controller.SystemStatus" scope="request"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popOpportunities.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/executeFunction.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/moveContact.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkEmail.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkPhone.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popContactAddressListSingle.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popContactEmailAddressListSingle.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popContactPhoneNumberListSingle.js"></script>
<%@ include file="../initPage.jsp" %>
<body onLoad="javascript:document.addQuote.shortDescription.focus();">
<form method="post" name="addQuote" action="Quotes.do?command=Modify&quoteId=<%= quoteBean.getId() %>&version=<%= version %>&auto-populate=true" onSubmit="return checkForm(this);">
<%-- Trails --%>
<table class="trails" cellspacing="0">
  <tr>
    <td>
      <a href="Quotes.do"><dhv:label name="dependency.quotes">Quotes</dhv:label></a> >
      <a href="Quotes.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
      <% if (version != null && !"".equals(version)) { %> 
      <a href="Quotes.do?command=Search&version=<%= quoteBean.getId() %>"><dhv:label name="quotes.versionResults">Version Results</dhv:label></a> >
      <% } %>
      <a href="Quotes.do?command=Details&quoteId=<%= quoteBean.getId() %>"><dhv:label name="quotes.quoteDetails">Quote Details</dhv:label></a> >
      <dhv:label name="quotes.modifyQuote">Modify Quote</dhv:label>
    </td>
  </tr>
</table>
<%-- End Trails --%>
<% Quote quote = quoteBean; %>
<% String param1 = "quoteId=" + quoteBean.getId() + "|version="+version; %>
<dhv:container name="quotes" selected="details" object="quoteBean" param="<%= param1 %>">
  <%@ include file="quotes_header_include.jsp" %>
  <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>"/>
  <input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='Quotes.do?command=Details&quoteId=<%= quoteBean.getId() %>&version=<%= version %>';"/>
  <br />
  <%= showError(request, "actionError") %>
  <%
    String title = "Modify Quote #" + quoteBean.getGroupId()+ " version "+ quoteBean.getVersion();
    String titleLabel = "quotes.modifyQuote";
    String quoteParams = "quoteId="+quoteBean.getGroupId()+"|version="+quoteBean.getVersion();
    boolean changeAccount = true;
    boolean changeContact = true;
    boolean changeOpportunity = true;
    String opportunityName = opportunity.getDescription() != null ? opportunity.getDescription() : "Opportunity Selected";
    String opportunityNameLabel = opportunity.getDescription() != null? "":"quotes.opportunitySelected";
  %>
  <%@ include file="quotes_modify_include.jsp" %>
  <br />
  <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" />
  <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='Quotes.do?command=Details&quoteId=<%= quoteBean.getId() %>&version=<%= version %>';" />
  <iframe src="../empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
</dhv:container>
</form>
