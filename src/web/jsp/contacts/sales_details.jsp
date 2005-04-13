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
<%@ include file="../initPage.jsp" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.contacts.base.*" %>
<%@ page import="org.aspcfs.modules.base.*" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<%--<jsp:useBean id="SalesListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session" /> --%>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RatingList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="foundDuplicateLastName" class="java.lang.String" scope="request" />
<jsp:useBean id="foundDuplicateEmailAddress" class="java.util.HashMap" scope="request" />
<jsp:useBean id="foundDuplicateCompany" class="org.aspcfs.modules.contacts.base.ContactList" scope="request" />
<jsp:useBean id="readStatus" class="java.lang.String" scope="request" />
<jsp:useBean id="nextValue" class="java.lang.String" scope="request" />
<jsp:useBean id="from" class="java.lang.String" scope="request" />
<jsp:useBean id="searchForm" class="java.lang.String" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popContacts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script type="text/javascript">
  function reopen() {
    if ('<%= from %>' == 'dashboard') {
      scrollReload('Sales.do?command=Dashboard');
    } else {
      scrollReload('Sales.do?command=Details&contactId=<%= ContactDetails.getId() %>&nextValue=true');
    }
  }
  function reopenOnDelete() {
    if ('<%= from %>' == 'dashboard') {
      scrollReload('Sales.do?command=Dashboard');
    } else {
      scrollReload('Sales.do?command=Details&contactId=<%= ContactDetails.getId() %>&nextValue=true');
    }
  }

  function changeDivContent(divName, divContents) {
    if(document.layers){
      // Netscape 4 or equiv.
      divToChange = document.layers[divName];
      divToChange.document.open();
      divToChange.document.write(divContents);
      divToChange.document.close();
    } else if(document.all){
      // MS IE or equiv.
      divToChange = document.all[divName];
      divToChange.innerHTML = divContents;
    } else if(document.getElementById){
      // Netscape 6 or equiv.
      divToChange = document.getElementById(divName);
      divToChange.innerHTML = divContents;
    }
  }
  
  function workLead() {
    var url = 'Sales.do?command=CheckAssignStatus&contactId=<%= ContactDetails.getId() %>&next=work&popup=true&from=<%= from %>';
    popURL(url,'WorkLead','650','500','yes','yes');
  }
  
  function continueWorkLead() {
    var rating = document.forms['details'].rating.value;
    var comments = document.forms['details'].comments.value;
    popURL('Sales.do?command=WorkLead&id=<%= ContactDetails.getId() %>&rating='+rating+'&comments='+comments+'&popup=true','WorkLead','650','500','yes','yes');
    hideSpan("worklead");
    showSpan("nextlead");
  }
  
  function nextLead() {
    if ('<%= from %>' == 'dashboard') {
      scrollReload('Sales.do?command=Dashboard');
    } else {
      window.location.href = "Sales.do?command=Details&contactId=<%= ContactDetails.getId() %>&nextValue=true";
    }
  }
  
  function assignLead() {
    var owner = document.forms['details'].owner.value;
    if (owner == '-1') {
     owner = '<%= User.getUserRecord().getId() %>';
    }
    var url = 'Sales.do?command=CheckAssignStatus&contactId=<%= ContactDetails.getId() %>&next=assign&from=<%= from %>&owner='+owner;
    window.frames['server_commands'].location.href=url;
  }
  
  function continueAssignLead() {
    var rating = document.forms['details'].rating.value;
    var comments = document.forms['details'].comments.value;
    var contactId = '<%= ContactDetails.getId() %>';
    var owner = document.forms['details'].owner.value;
    if (owner == '-1') {
     owner = '<%= User.getUserRecord().getId() %>';
    }
    var url = "Sales.do?command=Update&contactId="+contactId+"&nextValue=true&owner="+owner+"&leadStatus=<%= Contact.LEAD_ASSIGNED %>&from=<%= from %>";
    url += "&comments="+comments+"&rating="+rating;
    window.location.href= url;
  }

  function trashLead() {
    var url = 'Sales.do?command=CheckAssignStatus&contactId=<%= ContactDetails.getId() %>&next=trash&from=<%= from %>';
    window.frames['server_commands'].location.href=url;
  }

  function continueTrashLead() {
    var rating = document.forms['details'].rating.value;
    var comments = document.forms['details'].comments.value;
    var leadStatus = '<%= Contact.LEAD_TRASHED %>';
    var contactId = '<%= ContactDetails.getId() %>';
    var url = "Sales.do?command=Update&contactId="+contactId+"&nextValue=true&leadStatus="+leadStatus+'&from=<%= from %>';
    url += "&comments="+comments+"&rating="+rating;
    window.location.href= url;
  }

  function skipLead() {
    var url = 'Sales.do?command=CheckAssignStatus&contactId=<%= ContactDetails.getId() %>&next=skip&from=<%= from %>';
    window.frames['server_commands'].location.href=url;
  }

  function deleteLead() {
    var url = 'Sales.do?command=CheckAssignStatus&contactId=<%= ContactDetails.getId() %>&next=delete&from=<%= from %>';
    window.frames['server_commands'].location.href=url;
  }

  function continueDeleteLead() {
    var leadStatus = '<%= Contact.LEAD_TRASHED %>';
    var contactId = '<%= ContactDetails.getId() %>';
    popURL('Sales.do?command=ConfirmDelete&contactId='+contactId+'&popup=true&return=<%= from %>&searchForm=<%= (searchForm != null ? searchForm : "") %>', 'Delete_Lead','330','200','yes','no');
  }

  function contactDetails() {
    popURL('ExternalContacts.do?command=ContactDetails&id=<%= ContactDetails.getId() %>&popup=true&viewOnly=true','Details','650','500','yes','yes');
  }

  function resetNumericFieldValue(fieldId){
    document.getElementById(fieldId).value = -1;
  }

</script>
<form name="details" action="Sales.do?command=Update&contactId=<%= ContactDetails.getId() %>" method="post">
<input type="hidden" name="id" value="<%= ContactDetails.getId() %>" />
<%--<input type="hidden" name="nextValue" value="<%= nextValue %>" /> --%>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Sales.do"><dhv:label name="Leads" mainMenuItem="true">Leads</dhv:label></a> >
<% if (searchForm != null && !"".equals(searchForm)) { %>
  <a href="Sales.do?command=SearchForm&backFromDetails=true"><dhv:label name="tickets.searchForm">Search Form</dhv:label></a> >
<%}%>
<% if (from != null && "list".equals(from) && !"dashboard".equals(from)) { %>
  <a href="Sales.do?command=List&backFromDetails=true&searchForm=<%= (searchForm != null? searchForm:"") %>"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<%}%>
<dhv:label name="sales.leadDetails">Lead Details</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:evaluate if="<%= (readStatus != null && !readStatus.equals("-1") && !readStatus.equals(""+User.getUserRecord().getId())) %>">
  <br />
  <img src="images/error.gif" border="0" align="absmiddle"/>
  <font color="red"><dhv:label name="sales.leadBeingReadBy" param="<%= "username="+getUsername(pageContext,Integer.parseInt(readStatus),false,false,"&nbsp;") %>">This lead is being read by <dhv:username id="<%= readStatus %>" /></dhv:label></font><br />
  <br />
</dhv:evaluate>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="sales.contactInformation">Contact Information</dhv:label></strong>
	  </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="reports.accounts.name">Name</dhv:label>
    </td>
    <td>
      <%= toHtml(ContactDetails.getNameFull()) %>
      <dhv:evaluate if="<%= foundDuplicateLastName != null && !"".equals(foundDuplicateLastName) %>">&nbsp;&nbsp;
        <span class="duplicate"><dhv:label name="sales.foundDuplicateLastName">Duplicate last name found</dhv:label></span>
      </dhv:evaluate>&nbsp;&nbsp;<a href="http://www.google.com/search?hl=en&ie=UTF-8&oe=UTF-8&q=<%= ContactDetails.getNameFull() %>" target="_blank"><img src="images/google_logo.gif" border="0" align="absmiddle" height="15" width="45"/></a>
<%--        &nbsp;<a href="http://search.lycos.com/default.asp?lpv=1&loc=searchhp&tab=web&query=<%= ContactDetails.getNameFull() %>" target="_blank"><img src="images/lycos_logo.gif" border="0" align="absmiddle" height="20" width="60"/></a> --%>
        &nbsp;<a href="http://search.yahoo.com/search?p=<%= ContactDetails.getNameFull() %>" target="_blank"><img src="images/yahoo_logo.gif" border="0" align="absmiddle" height="15" width="60"/></a>
<%--        &nbsp;<a href="http://www.hotbot.com/default.asp?query=<%= ContactDetails.getNameFull() %>" target="_blank"><img src="images/hotbot_logo.gif" border="0" align="absmiddle" height="20" width="60"/></a> --%>
        &nbsp;<a href="http://search.msn.com/results.aspx?FORM=MSNH&srch_type=0&q=<%= ContactDetails.getNameFull() %>" target="_blank"><img src="images/msn_logo.gif" border="0" align="absmiddle" height="15" width="45"/></a>
        &nbsp;<a href="http://web.ask.com/web?q=<%= ContactDetails.getNameFull() %>" target="_blank"><img src="images/ask_logo.gif" border="0" align="absmiddle" height="15" width="50"/></a>

</td>
  </tr>
<dhv:evaluate if="<%= hasText(ContactDetails.getTitle()) %>">
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="reports.contacts.title">Title</dhv:label>
    </td>
    <td>
      <%= toHtml(ContactDetails.getTitle()) %>
    </td>
  </tr>
</dhv:evaluate>
<dhv:evaluate if="<%= hasText(ContactDetails.getCompany()) %>">
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accounts_contacts_detailsimport.Company">Company</dhv:label>
    </td>
    <td>
      <%= toHtml(ContactDetails.getCompany()) %>
      <dhv:evaluate if="<%= foundDuplicateCompany != null && (foundDuplicateCompany.size() > 1) %>">&nbsp;&nbsp;
        <span class="duplicate"><dhv:label name="sales.foundDuplicateCompanyName">Duplicate company name found</dhv:label></span>
      </dhv:evaluate>&nbsp;&nbsp;<a href="http://www.google.com/search?hl=en&ie=UTF-8&oe=UTF-8&q=<%= ContactDetails.getCompany() %>" target="_blank"><img src="images/google_logo.gif" border="0" align="absmiddle" height="15" width="45"/></a>
<%--        &nbsp;<a href="http://search.lycos.com/default.asp?lpv=1&loc=searchhp&tab=web&query=<%= ContactDetails.getCompany() %>" target="_blank"><img src="images/lycos_logo.gif" border="0" align="absmiddle" height="20" width="60"/></a>--%>
        &nbsp;<a href="http://search.yahoo.com/search?p=<%= ContactDetails.getCompany() %>" target="_blank"><img src="images/yahoo_logo.gif" border="0" align="absmiddle" height="15" width="60"/></a>
<%--        &nbsp;<a href="http://www.hotbot.com/default.asp?query=<%= ContactDetails.getCompany() %>" target="_blank"><img src="images/hotbot_logo.gif" border="0" align="absmiddle" height="20" width="60"/></a>--%>
        &nbsp;<a href="http://search.msn.com/results.aspx?FORM=MSNH&srch_type=0&q=<%= ContactDetails.getCompany() %>" target="_blank"><img src="images/msn_logo.gif" border="0" align="absmiddle" height="15" width="45"/></a>
        &nbsp;<a href="http://web.ask.com/web?q=<%= ContactDetails.getCompany() %>" target="_blank"><img src="images/ask_logo.gif" border="0" align="absmiddle" height="15" width="50"/></a>
    </td>
  </tr>
</dhv:evaluate>
<%
  Iterator inumber = ContactDetails.getPhoneNumberList().iterator();
  if (inumber.hasNext()) {
%>
  <tr>
    <td class="formLabel">
	    <dhv:label name="accounts.accounts_add.PhoneNumbers">Phone Numbers</dhv:label>
	  </td>
    <td>
<%
    while (inumber.hasNext()) {
      ContactPhoneNumber thisPhoneNumber = (ContactPhoneNumber)inumber.next();
%>
        <%= toHtml(thisPhoneNumber.getTypeName()) %> &nbsp;
        <%= toHtml(thisPhoneNumber.getPhoneNumber()) %>&nbsp;
        <dhv:evaluate if="<%=thisPhoneNumber.getPrimaryNumber()%>">
          <dhv:label name="account.primary.brackets">(Primary)</dhv:label>
        </dhv:evaluate><br />
<%}%>
    </td>
  </tr>
<%}%>
<%
  Iterator iemail = ContactDetails.getEmailAddressList().iterator();
  if (iemail.hasNext()) {
%>
  <tr>
    <td class="formLabel">
	    <dhv:label name="accounts.accounts_add.EmailAddresses">Email Addresses</dhv:label>
	  </td>
    <td>
<%
    while (iemail.hasNext()) {
      ContactEmailAddress thisEmailAddress = (ContactEmailAddress)iemail.next();
%>
      <a href="mailto:<%= toHtml(thisEmailAddress.getEmail()) %>"><%= toHtml(thisEmailAddress.getEmail()) %></a>&nbsp;<%= toHtml(thisEmailAddress.getTypeName()) %>&nbsp;
      <dhv:evaluate if="<%=thisEmailAddress.getPrimaryEmail()%>">
        <dhv:label name="account.primary.brackets">(Primary)</dhv:label>
      </dhv:evaluate>
        <dhv:evaluate if="<%= foundDuplicateEmailAddress != null && (foundDuplicateEmailAddress.size() > 0) && (foundDuplicateEmailAddress.get(thisEmailAddress.getEmail()) != null) %>">&nbsp;&nbsp;
          <span class="duplicate"><dhv:label name="sales.foundDuplicateEmailAddress">Duplicate email address found</dhv:label></span>
        </dhv:evaluate>&nbsp;&nbsp;<a href="http://www.google.com/search?hl=en&ie=UTF-8&oe=UTF-8&q=<%= thisEmailAddress.getEmail() %>" target="_blank"><img src="images/google_logo.gif" border="0" align="absmiddle" height="15" width="45"/></a>
<%--        &nbsp;<a href="http://search.lycos.com/default.asp?lpv=1&loc=searchhp&tab=web&query=<%= thisEmailAddress.getEmail() %>" target="_blank"><img src="images/lycos_logo.gif" border="0" align="absmiddle" height="20" width="60"/></a> --%>
        &nbsp;<a href="http://search.yahoo.com/search?p=<%= thisEmailAddress.getEmail() %>" target="_blank"><img src="images/yahoo_logo.gif" border="0" align="absmiddle" height="15" width="60"/></a>
<%--        &nbsp;<a href="http://www.hotbot.com/default.asp?query=<%= thisEmailAddress.getEmail() %>" target="_blank"><img src="images/hotbot_logo.gif" border="0" align="absmiddle" height="20" width="60"/></a> --%>
        &nbsp;<a href="http://search.msn.com/results.aspx?FORM=MSNH&srch_type=0&q=<%= thisEmailAddress.getEmail() %>" target="_blank"><img src="images/msn_logo.gif" border="0" align="absmiddle" height="15" width="45"/></a>
        &nbsp;<a href="http://web.ask.com/web?q=<%= thisEmailAddress.getEmail() %>" target="_blank"><img src="images/ask_logo.gif" border="0" align="absmiddle" height="15" width="50"/></a>
      <br />
<%}%>
    </td>
  </tr>
<%}%>
<%  
  Iterator itmAddress = ContactDetails.getTextMessageAddressList().iterator();
  if (itmAddress.hasNext()) {
%>
  <tr class="containerBody">
    <td class="formLabel">
	    <dhv:label name="accounts.accounts_add.TextMessageAddresses">Text Message Addresses</dhv:label>
	  </td>
    <td>
<%
    while (itmAddress.hasNext()) {
      ContactTextMessageAddress thisTextMessageAddress = (ContactTextMessageAddress)itmAddress.next();
%>    
      <%= toHtml(thisTextMessageAddress.getTypeName()) %> &nbsp;
      <dhv:evaluate if="<%= hasText(thisTextMessageAddress.getTextMessageAddress()) %>">
      <a href="mailto:<%= toHtml(thisTextMessageAddress.getTextMessageAddress()) %>"><%= toHtml(thisTextMessageAddress.getTextMessageAddress()) %></a>&nbsp;
        <dhv:evaluate if="<%=thisTextMessageAddress.getPrimaryTextMessageAddress()%>">
          <dhv:label name="account.primary.brackets">(Primary)</dhv:label>
        </dhv:evaluate>
      </dhv:evaluate><br />
<%}%>
    </td>
  </tr>
<%}%>
<dhv:evaluate if="<%= hasText(ContactDetails.getNotes()) %>">
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accountasset_include.Notes">Notes</dhv:label>
    </td>
    <td>
      <%= toHtml(ContactDetails.getNotes()) %>
    </td>
  </tr>
</dhv:evaluate>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="campaign.comments">Comments</dhv:label>
    </td>
    <td>
      <TEXTAREA NAME="comments" ROWS="3" COLS="50"><%= toString(ContactDetails.getComments()) %></TEXTAREA>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accounts_calls_list.Entered">Entered</dhv:label>
    </td>
    <td>
      <dhv:tz timestamp="<%= ContactDetails.getEntered() %>" dateFormat="<%= DateFormat.SHORT %>"/>
    </td>
  </tr>
</table>
<br />
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="sales.leadStatus">Lead Status</dhv:label></strong>
	  </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="accounts.accountasset_include.Status">Status</dhv:label></td>
    <td>
      <dhv:label name="sales.<%= ContactDetails.getLeadStatusString() %>"><%= toHtml(ContactDetails.getLeadStatusString()) %></dhv:label>
      <input type="hidden" name="leadStatus" value="<%= ContactDetails.getLeadStatus() %>" />&nbsp;&nbsp;
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top">
      <dhv:label name="actionList.assignTo">Assign To</dhv:label>
    </td>
    <td>
      <table class="empty">
        <tr>
          <td>
            <div id="changeowner">
            <%if(ContactDetails.getOwner() > 0){ %>
              <dhv:username id="<%= ContactDetails.getOwner() %>"/>
              <dhv:evaluate if="<%=!(ContactDetails.getHasEnabledOwnerAccount())%>"><font color="red"><dhv:label name="accounts.accounts_importcontact_details.NoLongerAccess">(No longer has access)</dhv:label></font></dhv:evaluate>
            <% }else{ %>
               <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
            <%}%>
            </div>
          </td>
          <td>
            <input type="hidden" name="owner" id="ownerid" value="<%= ContactDetails.getOwner() == -1 ? User.getUserRecord().getId() : ContactDetails.getOwner() %>">
            &nbsp;[<a href="javascript:popContactsListSingle('ownerid','changeowner', 'listView=employees&usersOnly=true&reset=true');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
            &nbsp; [<a href="javascript:changeDivContent('changeowner',label('none.selected','None Selected'));javascript:resetNumericFieldValue('ownerid');"><dhv:label name="accounts.accountasset_include.clear">Clear</dhv:label></a>]
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="sales.rating">Rating</dhv:label>
    </td>
    <td>
      <%= RatingList.getHtmlSelect("rating",ContactDetails.getRating()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="contact.source">Source</dhv:label>
    </td>
    <td>
      <%= toHtml( SourceList.getValueFromId(ContactDetails.getSource())) %>
    </td>
  </tr>
</table>
<br />
<span name="worklead" id="worklead" style="">
<dhv:permission name="contacts-external_contacts-view"><input type="button" value="<dhv:label name="button.workAsContact">Work as Contact</dhv:label>" onClick="javascript:workLead();" /></dhv:permission>
<dhv:permission name="sales-leads-edit"><input type="button" value="<dhv:label name="button.assignLeadR">Assign Lead ></dhv:label>" onClick="javascript:assignLead();" /></dhv:permission>
<dhv:permission name="sales-leads-edit"><input type="button" value="<dhv:label name="button.trashLeadR">Trash Lead ></dhv:label>" onClick="javascript:trashLead();" /></dhv:permission>
<dhv:permission name="sales-leads-edit"><input type="button" value="<dhv:label name="button.skipThisLeadR">Skip this Lead ></dhv:label>" onClick="javascript:skipLead();" /></dhv:permission>
<dhv:permission name="sales-leads-delete"><input type="button" value="<dhv:label name="button.delete">Delete</dhv:label>" onClick="javascript:deleteLead();" /></dhv:permission>
</span>
<span name="nextlead" id="nextlead" style="display:none">
<dhv:permission name="contacts-external_contacts-view"><input type="button" value="<dhv:label name="calendar.viewContactDetails">View Contact Details</dhv:label>" onClick="javascript:contactDetails();" /></dhv:permission>
<dhv:permission name="sales-leads-edit"><input type="button" value="<dhv:label name="button.nextR">Next ></dhv:label>" onClick="javascript:nextLead();" /></dhv:permission>
</span>
<iframe src="../empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
</form>

