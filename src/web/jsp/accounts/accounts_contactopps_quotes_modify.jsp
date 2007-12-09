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
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
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
<form method="post" name="addQuote" action="AccountContactsOppQuotes.do?command=Modify&versionId=<%= version %>&quoteId=<%= quoteBean.getId() %>&auto-populate=true<%= addLinkParams(request, "popup|popupType|actionId") %>" onSubmit="return checkForm(this);">
<dhv:evaluate if="<%= !isPopup(request) %>">
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
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<a href="Contacts.do?command=View&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.Contacts">Contacts</dhv:label></a> >
<a href="Contacts.do?command=Details&id=<%=ContactDetails.getId()%>&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.accounts_contacts_add.ContactDetails">Contact Details</dhv:label></a> >
<a href="AccountContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_add.Opportunities">Opportunities</dhv:label></a> >
<a href="AccountContactsOpps.do?command=DetailsOpp&headerId=<%= opportunity.getId() %>&orgId=<%= OrgDetails.getOrgId() %>&contactId=<%= ContactDetails.getId() %>"><dhv:label name="">Opportunity Details</dhv:label></a> > 
<a href="AccountContactsOppQuotes.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>&contactId=<%= ContactDetails.getId() %>&headerId=<%= opportunity.getId() %>&version=&quoteId=<%= quoteBean.getId() %>"><dhv:label name="quotes.quoteDetails">Quote Details</dhv:label></a> > 
<dhv:label name="quotes.modifyQuote">Modify Quote</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<% Quote quote = quoteBean; %>
<dhv:container name="accounts" selected="contacts" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
  <dhv:container name="accountscontacts" selected="opportunities" object="ContactDetails" param='<%= "id=" + ContactDetails.getId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
    <dhv:container name="accountcontactopportunities" selected="quotes" object="opportunity" param='<%= "headerId=" + quoteBean.getHeaderId() + "|" + "orgId=" + OrgDetails.getOrgId() + "|" + "contactId=" + ContactDetails.getId()%>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
    <%@ include file="../quotes/quotes_header_include.jsp" %>
    <input type="submit" value="<dhv:label name="button.update">Update</dhv:label>"/>
    <input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='AccountContactsOppQuotes.do?command=Details&version=<%= version %>&quoteId=<%= quoteBean.getId() %>&orgId=<%= OrgDetails.getOrgId() %>&contactId=<%= ContactDetails.getId() %>&headerId=<%=quoteBean.getHeaderId() %><%= addLinkParams(request, "popup|popupType|actionId") %>';"/>
    <br />
    <dhv:formMessage />
<%
  String title = "Modify Quote #" + quoteBean.getGroupId()+" version "+ quoteBean.getVersion();
  String titleLabel = "quotes.modifyQuote";
  String quoteParams = "quoteId="+quoteBean.getGroupId()+"|version="+quoteBean.getVersion(); 
  boolean changeAccount = false;
  boolean changeContact = false;
  boolean changeOpportunity = true;
  String opportunityName = (opportunity.getDescription() != null) ? opportunity.getDescription() : "Opportunity Selected";
  String opportunityNameLabel = opportunity.getDescription() != null? "":"quotes.opportunitySelected";
%>
    <%@ include file="../quotes/quotes_modify_include.jsp" %>
    <br />
    <input type="submit" value="<dhv:label name="button.update">Update</dhv:label>" />
    <input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='AccountContactsOppQuotes.do?command=Details&version=<%= version %>&quoteId=<%= quoteBean.getId() %>&orgId=<%= OrgDetails.getOrgId() %>&contactId=<%= ContactDetails.getId() %>&headerId=<%=quoteBean.getHeaderId() %><%= addLinkParams(request, "popup|popupType|actionId") %>';" />
    </dhv:container>
  </dhv:container>
</dhv:container>
</form>
</body>