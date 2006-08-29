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
<%@ page import="org.aspcfs.modules.base.*, org.aspcfs.modules.actionplans.base.*" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<%--<jsp:useBean id="SalesListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session" /> --%>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RatingList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="IndustryList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="actionPlanSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="actionPlanWork" class="org.aspcfs.modules.actionplans.base.ActionPlanWork" scope="request"/>
<jsp:useBean id="hasDuplicateLastName" class="java.lang.String" scope="request" />
<jsp:useBean id="hasDuplicateEmailAddress" class="java.util.HashMap" scope="request" />
<jsp:useBean id="hasDuplicateCompany" class="java.lang.String" scope="request" />
<jsp:useBean id="readStatus" class="java.lang.String" scope="request" />
<jsp:useBean id="nextValue" class="java.lang.String" scope="request" />
<jsp:useBean id="from" class="java.lang.String" scope="request" />
<jsp:useBean id="listForm" class="java.lang.String" scope="request" />
<jsp:useBean id="ImportDetails" class="org.aspcfs.modules.contacts.base.ContactImport" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
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
      scrollReload('Sales.do?command=Details&contactId=<%= ContactDetails.getId() %>&nextValue=<%= nextValue!=null?""+("true".equals(nextValue)?"true":"false"):"" %>&listForm=<%= listForm!=null?listForm:"" %>');
    }
  }
  function reopenId(id) {
    scrollReload('Sales.do?command=Details&contactId='+id);
  }
  function reopenOnDelete() {
    if ('<%= from %>' == 'dashboard') {
      scrollReload('Sales.do?command=Dashboard');
    } else {
      scrollReload('Sales.do?command=Details&contactId=<%= ContactDetails.getId() %>&nextValue=<%= nextValue!=null?""+("true".equals(nextValue)?"true":"false"):"" %>&listForm=<%= listForm!=null?listForm:"" %>');
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
    var url = 'Sales.do?command=CheckAssignStatus&contactId=<%= ContactDetails.getId() %>&next=work&popup=true&from=<%= from %>&listForm=<%= (listForm != null?listForm:"") %>';
    popURL(url,'WorkLead','650','500','yes','yes');
  }

/*  function workAccount() {
    var url = 'Sales.do?command=CheckAssignStatus&contactId=<%= ContactDetails.getId() %>&next=account&popup=true&from=<%= from %>&listForm=<%= (listForm != null?listForm:"") %>';
    popURL(url,'WorkLead','650','500','yes','yes');
  }
*/
  function workAsAccount() {
    popURL('Sales.do?command=AssignLead&contactId=<%= ContactDetails.getId() %>&type=workAsAccount&from=dashboard&from=<%= from %>&listForm=<%= (listForm!=null?listForm:"")  %><%= addLinkParams(request, "popupType|actionId") %>&popup=true','ConvertToAccount','650','200','yes','yes');
  }

  function reassignLead() {
    popURL('Sales.do?command=AssignLead&contactId=<%= ContactDetails.getId() %>&type=assignLead&from=dashboard&from=<%= from %>&listForm=<%= (listForm!=null?listForm:"")  %><%= addLinkParams(request, "popupType|actionId") %>&popup=true','ReassignLead','650','200','yes','yes');
  }

  function continueWorkLead() {
    var rating = document.forms['details'].rating.value;
    var comments = document.forms['details'].comments.value;
    popURL('Sales.do?command=WorkLead&id=<%= ContactDetails.getId() %>&rating='+rating+'&comments='+comments+'&popup=true&listForm=<%= (listForm != null?listForm:"") %>','WorkLead','650','500','yes','yes');
    hideSpan("worklead");
    showSpan("nextlead");
  }

  function nextLead() {
    if ('<%= from %>' == 'dashboard') {
      scrollReload('Sales.do?command=Dashboard');
    } else {
      window.location.href = "Sales.do?command=Details&contactId=<%= ContactDetails.getId() %>&nextValue=true&listForm=<%= (listForm != null?listForm:"") %>";
    }
  }

  function modifyLead() {
    var owner = document.forms['details'].owner.value;
//    var actionPlan = document.forms['details'].actionPlan.options[document.forms['details'].actionPlan.selectedIndex].value;
//    var manager = document.forms['details'].planManager.value;

    if (owner == '-1') {
     owner = '<%= User.getUserRecord().getId() %>';
    }
    var nextTo = '<%= from %>';
    try {
      if (!document.getElementById("toNextLead").checked) {
        nextTo = "dashboard";
      }
    } catch (oException) {
    }
    var url = 'Sales.do?command=CheckAssignStatus&contactId=<%= ContactDetails.getId() %>&next=modify&from='+ nextTo +'&listForm=<%= (listForm != null?listForm:"") %>&owner=' + owner;
    window.frames['server_commands'].location.href=url;
  }

  function continueModifyLead() {
    var rating = '<%= ContactDetails.getRating() %>';
    var comments = '<%= ContactDetails.getComments() %>';
    var contactId = '<%= ContactDetails.getId() %>';
    var owner = '<%= ContactDetails.getOwner() %>';
    if (owner == '-1') {
     owner = '<%= User.getUserRecord().getId() %>';
    }
    var nextTo = '<%= from %>';
    try {
      if (!document.getElementById("toNextLead").checked) {
        nextTo = "dashboard";
      }
    } catch (oException) {
    }
    var url = "Sales.do?command=Modify&contactId="+contactId+"&nextValue=true&from="+nextTo+"&listForm=<%= (listForm != null?listForm:"") %>";
    window.location.href= url;
  }
/*
  function assignLead() {
    var owner = document.forms['details'].owner.value;
    var actionPlan = document.forms['details'].actionPlan.options[document.forms['details'].actionPlan.selectedIndex].value;
    var manager = document.forms['details'].planManager.value;

    if (owner == '-1') {
     owner = '<%= User.getUserRecord().getId() %>';
    }
    var nextTo = '<%= from %>';
    try {
      if (!document.getElementById("toNextLead").checked) {
        nextTo = "dashboard";
      }
    } catch (oException) {
    }
    var url = 'Sales.do?command=CheckAssignStatus&contactId=<%= ContactDetails.getId() %>&next=assign&from='+ nextTo +'&listForm=<%= (listForm != null?listForm:"") %>&owner=' + owner + '&actionPlan=' + actionPlan + '&manager=' + manager;
    window.frames['server_commands'].location.href=url;
  }

  function assignAccount() {
    var owner = document.forms['details'].owner.value;
    var actionPlan = document.forms['details'].actionPlan.options[document.forms['details'].actionPlan.selectedIndex].value;
    var manager = document.forms['details'].planManager.value;

    if (owner == '-1') {
     owner = '<%= User.getUserRecord().getId() %>';
    }
    var nextTo = '<%= from %>';
    try {
      if (!document.getElementById("toNextLead").checked) {
        nextTo = "dashboard";
      }
    } catch (oException) {
    }
    var url = 'Sales.do?command=CheckAssignStatus&contactId=<%= ContactDetails.getId() %>&next=assignaccount&from='+ nextTo +'&listForm=<%= (listForm != null?listForm:"") %>&owner=' + owner + '&actionPlan=' + actionPlan + '&manager=' + manager;
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
    var nextTo = '<%= from %>';
    try {
      if (!document.getElementById("toNextLead").checked) {
        nextTo = "dashboard";
      }
    } catch (oException) {
    }
    var url = "Sales.do?command=Update&contactId="+contactId+"&nextValue=true&owner="+owner+"&leadStatus=<%= Contact.LEAD_ASSIGNED %>&from="+nextTo+"&listForm=<%= (listForm != null?listForm:"") %>";
    url += "&comments="+comments+"&rating="+rating;
    window.location.href= url;
  }

  function continueAssignAccount() {
    var rating = document.forms['details'].rating.value;
    var comments = document.forms['details'].comments.value;
    var contactId = '<%= ContactDetails.getId() %>';
    var owner = document.forms['details'].owner.value;
    var actionPlan = document.forms['details'].actionPlan.options[document.forms['details'].actionPlan.selectedIndex].value;
    var manager = document.forms['details'].planManager.value;
    if (owner == '-1') {
     owner = '<%= User.getUserRecord().getId() %>';
    }
    var nextTo = '<%= from %>';
    try {
      if (!document.getElementById("toNextLead").checked) {
        nextTo = "dashboard";
      }
    } catch (oException) {
    }
    var url = "Sales.do?command=Update&contactId="+contactId+"&id="+contactId+"&next=assignaccount&nextValue=true&owner="+owner+"&leadStatus=<%= Contact.LEAD_ASSIGNED %>&from="+nextTo+"&listForm=<%= (listForm != null?listForm:"") %>";
    url += "&comments="+comments+"&rating="+rating+"&actionPlan=" + actionPlan + "&manager=" + manager;
    window.location.href= url;
  }
*/
  function trashLead() {
    var nextTo = '<%= from %>';
    try {
      if (!document.getElementById("toNextLead").checked) {
        nextTo = "dashboard";
      }
    } catch (oException) {
    }
    var url = 'Sales.do?command=CheckAssignStatus&contactId=<%= ContactDetails.getId() %>&next=trash&from='+nextTo+'&listForm=<%= (listForm != null?listForm:"") %>';
    window.frames['server_commands'].location.href=url;
  }

  function continueTrashLead() {
    var rating = '<%= ContactDetails.getRating() %>';
    var comments = '<%= ContactDetails.getComments() %>';
    var leadStatus = '<%= Contact.LEAD_TRASHED %>';
    var contactId = '<%= ContactDetails.getId() %>';
    var nextTo = '<%= from %>';
    try {
      if (!document.getElementById("toNextLead").checked) {
        nextTo = "dashboard";
      }
    } catch (oException) {
    }
    var url = "Sales.do?command=Update&contactId="+contactId+"&nextValue=true&leadStatus="+leadStatus+'&from='+nextTo+'&listForm=<%= (listForm != null?listForm:"") %>';
    url += "&comments="+comments+"&rating="+rating;
    window.location.href= url;
  }

  function skipLead() {
    var nextTo = '<%= from %>';
    try {
      if (!document.getElementById("toNextLead").checked) {
        nextTo = "dashboard";
      }
    } catch (oException) {
    }
    var url = 'Sales.do?command=CheckAssignStatus&contactId=<%= ContactDetails.getId() %>&next=skip&from='+nextTo+'&listForm=<%= (listForm != null?listForm:"") %>';
    window.frames['server_commands'].location.href=url;
  }

  function deleteLead() {
    var url = 'Sales.do?command=CheckAssignStatus&contactId=<%= ContactDetails.getId() %>&next=delete&from=<%= from %>&nextValue=<%= nextValue %>&listForm=<%= listForm!=null?listForm:"" %>';
    popURL(url+'&popup=true','DeleteLead','330','200','yes','yes');
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
<% if (listForm != null && !"".equals(listForm)) { %>
  <a href="Sales.do?command=SearchForm"><dhv:label name="tickets.searchForm">Search Form</dhv:label></a> >
<%}%>
<% if (from != null && "list".equals(from) && !"dashboard".equals(from)) { %>
  <a href="Sales.do?command=List&listForm=<%= (listForm != null? listForm:"") %>"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
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
	    <strong><dhv:label name="sales.leadStatus">Lead Status</dhv:label></strong>
	  </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="accounts.accountasset_include.Status">Status</dhv:label></td>
    <td>
      <dhv:label name="<%= "sales."+ContactDetails.getLeadStatusString() %>"><%= toHtml(ContactDetails.getLeadStatusString()) %></dhv:label>
      <input type="hidden" name="leadStatus" value="<%= ContactDetails.getLeadStatus() %>" />&nbsp;&nbsp;
    </td>
  </tr>
  <dhv:evaluate if="<%= SiteIdList.size() > 2 %>">
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="admin.user.site">Site</dhv:label>
    </td>
    <td>
       <%= SiteIdList.getSelectedValue(ContactDetails.getSiteId()) %>
       <input type="hidden" name="siteId" value="<%=ContactDetails.getSiteId()%>" >
    </td>
  </tr>
  </dhv:evaluate> 
  <dhv:evaluate if="<%= SiteIdList.size() <= 2 %>">
    <input type="hidden" name="siteId" id="siteId" value="-1" />
  </dhv:evaluate>
  <dhv:evaluate if="<%= ContactDetails.getOwner() > 0 %>">
    <tr class="containerBody">
      <td nowrap class="formLabel" valign="top">
        <dhv:label name="accounts.accounts_contacts_calls_list.AssignedTo">Assigned To</dhv:label>
      </td>
      <td>
        <dhv:username id="<%= ContactDetails.getOwner() %>"/>
        <dhv:evaluate if="<%=!(ContactDetails.getHasEnabledOwnerAccount())%>"><font color="red"><dhv:label name="accounts.accounts_importcontact_details.NoLongerAccess">(No longer has access)</dhv:label></font></dhv:evaluate>
        <input type="hidden" name="owner" id="owner" value="<%= ContactDetails.getOwner() %>"/>
      </td>
    </tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= ContactDetails.getOwner() <= 0 %>">
    <input type="hidden" name="owner" id="owner" value="<%= ContactDetails.getOwner() %>"/>
  </dhv:evaluate>
  <dhv:evaluate if="<%= ContactDetails.getComments() != null && !"".equals(ContactDetails.getComments().trim()) %>">
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="sales.assignmentMessage">Assignment Message</dhv:label>
    </td>
    <td>
      <%= toHtml(ContactDetails.getComments()) %>
    </td>
  </tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= ContactDetails.getRating() > -1 %>">
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="sales.rating">Rating</dhv:label>
    </td>
    <td>
      <%= toHtml(RatingList.getValueFromId(ContactDetails.getRating())) %>
    </td>
  </tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= ContactDetails.getSource() > -1 %>">
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="contact.source">Source</dhv:label>
    </td>
    <td>
      <%= toHtml(SourceList.getValueFromId(ContactDetails.getSource())) %>
    </td>
  </tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= ContactDetails.getIndustryTempCode() > -1 %>">
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="">Industry</dhv:label>
    </td>
    <td>
      <%= toHtml(IndustryList.getValueFromId(ContactDetails.getIndustryTempCode())) %>
    </td>
  </tr>
  </dhv:evaluate>
</table>
&nbsp;<br />
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="sales.contactInformation">Contact Information</dhv:label></strong>
	  </th>
  </tr>
  <dhv:evaluate if="<%= hasText(ContactDetails.getNameLast()) %>">
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="reports.accounts.name">Name</dhv:label>
    </td>
    <td>
      <%= toHtml(ContactDetails.getNameFull()) %>
      <dhv:evaluate if="<%= hasDuplicateLastName != null && "true".equals(hasDuplicateLastName) %>">&nbsp;&nbsp;
      <a href="#" onClick="javascript:popURL('Sales.do?command=ContactList&searchcodeLastName=<%= StringUtils.jsEscape(ContactDetails.getNameLast()) %>&popup=true&auto-populate=true','Last_Name',600,480,'yes','yes');">
      <span class="duplicate"><dhv:label name="sales.foundDuplicateLastName">Duplicate last name found</dhv:label></span></a></dhv:evaluate>
        &nbsp;&nbsp;<a href="http://www.google.com/search?hl=en&ie=UTF-8&oe=UTF-8&q=<%= "%22"+StringUtils.jsEscape(ContactDetails.getNameFull())+"%22" %>" target="_blank"><img src="images/google_logo.gif" border="0" align="absmiddle" height="15" width="45"/></a>
<%--        &nbsp;<a href="http://search.lycos.com/default.asp?lpv=1&loc=searchhp&tab=web&query=<%= ContactDetails.getNameFull() %>" target="_blank"><img src="images/lycos_logo.gif" border="0" align="absmiddle" height="20" width="60"/></a> --%>
        &nbsp;<a href="http://search.yahoo.com/search?p=<%= "%22"+StringUtils.jsEscape(ContactDetails.getNameFull())+"%22" %>" target="_blank"><img src="images/yahoo_logo.gif" border="0" align="absmiddle" height="15" width="60"/></a>
<%--        &nbsp;<a href="http://www.hotbot.com/default.asp?query=<%= ContactDetails.getNameFull() %>" target="_blank"><img src="images/hotbot_logo.gif" border="0" align="absmiddle" height="20" width="60"/></a> --%>
        &nbsp;<a href="http://search.msn.com/results.aspx?FORM=MSNH&srch_type=0&q=<%= "%22"+StringUtils.jsEscape(ContactDetails.getNameFull())+"%22" %>" target="_blank"><img src="images/msn_logo.gif" border="0" align="absmiddle" height="15" width="45"/></a>
        &nbsp;<a href="http://web.ask.com/web?q=<%= "%22"+StringUtils.jsEscape(ContactDetails.getNameFull())+"%22" %>" target="_blank"><img src="images/ask_logo.gif" border="0" align="absmiddle" height="15" width="50"/></a>

    </td>
  </tr>
</dhv:evaluate>
  <dhv:evaluate if="<%= hasText(ContactDetails.getAdditionalNames()) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.additionalNames">Additional Names</dhv:label>
    </td>
    <td>
      <%= toHtml(ContactDetails.getAdditionalNames()) %>
    </td>
  </tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= hasText(ContactDetails.getNickname()) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.nickname">Nickname</dhv:label>
    </td>
    <td>
      <%= toHtml(ContactDetails.getNickname()) %>
    </td>
  </tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= ContactDetails.getBirthDate() != null %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.dateOfBirth">Birthday</dhv:label>
    </td>
    <td>
      <zeroio:tz timestamp="<%= ContactDetails.getBirthDate() %>" dateOnly="true"/>
    </td>
  </tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= hasText(ContactDetails.getRole()) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.role">Role</dhv:label>
    </td>
    <td>
      <%= toHtml(ContactDetails.getRole()) %>
    </td>
  </tr>
  </dhv:evaluate>
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
      <dhv:evaluate if="<%= hasDuplicateCompany != null && !"".equals(hasDuplicateCompany) %>">&nbsp;&nbsp;
      <a href="#" onClick="javascript:popURL('Sales.do?command=ContactList&searchcodeCompany=<%= StringUtils.jsEscape(ContactDetails.getCompany()) %>&popup=true&auto-populate=true','Last_Name',600,480,'yes','yes');">
        <span class="duplicate"><dhv:label name="sales.foundDuplicateCompanyName">Duplicate company name found</dhv:label></span></a>
      </dhv:evaluate>&nbsp;&nbsp;<a href="http://www.google.com/search?hl=en&ie=UTF-8&oe=UTF-8&q=<%= "%22"+StringUtils.jsEscape(ContactDetails.getCompany())+"%22" %>" target="_blank"><img src="images/google_logo.gif" border="0" align="absmiddle" height="15" width="45"/></a>
<%--        &nbsp;<a href="http://search.lycos.com/default.asp?lpv=1&loc=searchhp&tab=web&query=<%= ContactDetails.getCompany() %>" target="_blank"><img src="images/lycos_logo.gif" border="0" align="absmiddle" height="20" width="60"/></a>--%>
        &nbsp;<a href="http://search.yahoo.com/search?p=<%= "%22"+StringUtils.jsEscape(ContactDetails.getCompany())+"%22" %>" target="_blank"><img src="images/yahoo_logo.gif" border="0" align="absmiddle" height="15" width="60"/></a>
<%--        &nbsp;<a href="http://www.hotbot.com/default.asp?query=<%= ContactDetails.getCompany() %>" target="_blank"><img src="images/hotbot_logo.gif" border="0" align="absmiddle" height="20" width="60"/></a>--%>
        &nbsp;<a href="http://search.msn.com/results.aspx?FORM=MSNH&srch_type=0&q=<%= "%22"+StringUtils.jsEscape(ContactDetails.getCompany())+"%22" %>" target="_blank"><img src="images/msn_logo.gif" border="0" align="absmiddle" height="15" width="45"/></a>
        &nbsp;<a href="http://web.ask.com/web?q=<%= "%22"+StringUtils.jsEscape(ContactDetails.getCompany())+"%22" %>" target="_blank"><img src="images/ask_logo.gif" border="0" align="absmiddle" height="15" width="50"/></a>
    </td>
  </tr>
</dhv:evaluate>
<dhv:evaluate if="<%= ContactDetails.getPotential() > 0 %>">
  <tr class="containerBody">
    <td class="formLabel">
      Potential
    </td>
    <td>
      <zeroio:currency value="<%= ContactDetails.getPotential() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;"/>
    </td>
  </tr>
</dhv:evaluate>
<dhv:evaluate if="<%= ContactDetails.getIndustryTempCode() > -1 %>">
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accounts_add.Industry">Industry</dhv:label>
    </td>
    <td>
      <%= toHtml(IndustryList.getSelectedValue(ContactDetails.getIndustryTempCode())) %>
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
        <dhv:evaluate if="<%= hasDuplicateEmailAddress.get(thisEmailAddress.getEmail()) != null && ((Boolean) hasDuplicateEmailAddress.get(thisEmailAddress.getEmail())).booleanValue() %>">&nbsp;&nbsp;
        <a href="javascript:popURL('Sales.do?command=ContactList&searchcodeEmailAddress=<%= thisEmailAddress.getEmail() %>&popup=true&auto-populate=true','Last_Name',600,480,'yes','yes');">
          <span class="duplicate"><dhv:label name="sales.foundDuplicateEmailAddress">Duplicate email address found</dhv:label></span></a>
        </dhv:evaluate>&nbsp;&nbsp;<a href="http://www.google.com/search?hl=en&ie=UTF-8&oe=UTF-8&q=<%= "%22"+thisEmailAddress.getEmail()+"%22" %>" target="_blank"><img src="images/google_logo.gif" border="0" align="absmiddle" height="15" width="45"/></a>
<%--        &nbsp;<a href="http://search.lycos.com/default.asp?lpv=1&loc=searchhp&tab=web&query=<%= thisEmailAddress.getEmail() %>" target="_blank"><img src="images/lycos_logo.gif" border="0" align="absmiddle" height="20" width="60"/></a> --%>
        &nbsp;<a href="http://search.yahoo.com/search?p=<%= "%22"+thisEmailAddress.getEmail()+"%22" %>" target="_blank"><img src="images/yahoo_logo.gif" border="0" align="absmiddle" height="15" width="60"/></a>
<%--        &nbsp;<a href="http://www.hotbot.com/default.asp?query=<%= thisEmailAddress.getEmail() %>" target="_blank"><img src="images/hotbot_logo.gif" border="0" align="absmiddle" height="20" width="60"/></a> --%>
        &nbsp;<a href="http://search.msn.com/results.aspx?FORM=MSNH&srch_type=0&q=<%= "%22"+thisEmailAddress.getEmail()+"%22" %>" target="_blank"><img src="images/msn_logo.gif" border="0" align="absmiddle" height="15" width="45"/></a>
        &nbsp;<a href="http://web.ask.com/web?q=<%= "%22"+thisEmailAddress.getEmail()+"%22" %>" target="_blank"><img src="images/ask_logo.gif" border="0" align="absmiddle" height="15" width="50"/></a>
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
<%}
  Iterator iaddress = ContactDetails.getAddressList().iterator();
  if (iaddress.hasNext()) {
%>
  <tr class="containerBody">
    <td class="formLabel">
	    <dhv:label name="accounts.accounts_add.Addresses">Addresses</dhv:label>
	  </td>
    <td>
<%  while (iaddress.hasNext()) {
        ContactAddress thisAddress = (ContactAddress)iaddress.next();
%>
      <%= toHtml(thisAddress.getTypeName()) %>&nbsp;
      <%= toHtml(thisAddress.toString()) %>&nbsp;
      <dhv:evaluate if="<%=thisAddress.getPrimaryAddress()%>">
        <dhv:label name="account.primary.brackets">(Primary)</dhv:label>
      </dhv:evaluate>
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
      <dhv:label name="accounts.accounts_calls_list.Entered">Entered</dhv:label>
    </td>
    <td>
      <zeroio:tz timestamp="<%= ContactDetails.getEntered() %>" dateFormat="<%= DateFormat.SHORT %>"/>
    </td>
  </tr>
</table>
<br />
<%--
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="sales.actionPlan">Action Plan</dhv:label>
    </td>
    <td>
      <%= actionPlanSelect.getHtml("actionPlan") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top">
      <dhv:label name="actionPlan.planManager">Plan Manager</dhv:label>
    </td>
    <td>
      <table class="empty">
        <tr>
          <td>
            <div id="changeplanmanager">
            <% if (actionPlanWork.getManagerId() > 0) { %>
              <dhv:username id="<%= actionPlanWork.getManagerId() %>"/>
            <% }else{ %>
               <dhv:username id="<%= User.getUserRecord().getId() %>"/>
            <%}%>
            </div>
          </td>
          <td>
            <input type="hidden" name="planManager" id="planManagerId" value="<%= ((actionPlanWork.getManagerId() > 0) ? actionPlanWork.getManagerId() : User.getUserRecord().getId()) %>">
            &nbsp;[<a href="javascript:popContactsListSingle('planManagerId','changeplanmanager', 'listView=employees&usersOnly=true&hierarchy=<%= User.getUserId() %>&siteId=<%=ContactDetails.getSiteId()%>&searchcodePermission=sales-leads-edit,myhomepage-action-plans-view&reset=true');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
            &nbsp; [<a href="javascript:changeDivContent('changeplanmanager',label('none.selected','None Selected'));javascript:resetNumericFieldValue('planManagerId');"><dhv:label name="accounts.accountasset_include.clear">Clear</dhv:label></a>]
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
--%>
<br />

<span name="worklead" id="worklead" style="">
<dhv:evaluate if="<%= ContactDetails.canWorkAsContact() %>">
  <dhv:include name="sales.details.workContact" none="true">
  <dhv:permission name="contacts-external_contacts-view"><input type="button" value="<dhv:label name="button.convertToContact">Convert to Contact</dhv:label>" onClick="javascript:workLead();" /></dhv:permission>
  </dhv:include>
  <dhv:include name="sales.details.workAccount" none="true">
    <dhv:permission name="sales-leads-edit"><input type="button" value="<dhv:label name="button.convertToAccount">Convert to Account</dhv:label>" onClick="javascript:workAsAccount();" /></dhv:permission>
  </dhv:include>
<dhv:include name="sales.details.assignLead" none="true">
  <dhv:permission name="sales-leads-edit">
    <dhv:evaluate if="<%= ContactDetails.getOwner() > -1 %>">
      <input type="button" value="<dhv:label name="button.reassignLeadR">Reassign Lead ></dhv:label>" onClick="javascript:reassignLead();" />
    </dhv:evaluate><dhv:evaluate if="<%= ContactDetails.getOwner() <= -1 %>">
      <input type="button" value="<dhv:label name="button.assignLeadR">Assign Lead ></dhv:label>" onClick="javascript:reassignLead();" />
    </dhv:evaluate>
  </dhv:permission>
</dhv:include>
  <dhv:permission name="sales-leads-edit"><input type="button" value="<dhv:label name="button.archiveLead">Archive Lead ></dhv:label>" onClick="javascript:trashLead();" /></dhv:permission>
</dhv:evaluate>
<dhv:include name="sales.details.skipThisLeadR" none="true"><dhv:permission name="sales-leads-edit"><input type="button" value="<dhv:label name="button.skipThisLeadR">Skip this Lead ></dhv:label>" onClick="javascript:skipLead();" /></dhv:permission></dhv:include>
<dhv:include name="sales.details.modifyLead" none="true"><dhv:permission name="sales-leads-edit"><input type="button" value="<dhv:label name="button.modify">Modify</dhv:label>" onClick="javascript:modifyLead();" /></dhv:permission></dhv:include>
<dhv:permission name="sales-leads-delete"><input type="button" value="<dhv:label name="button.delete">Delete</dhv:label>" onClick="javascript:deleteLead();" /></dhv:permission><br />
<% if ((listForm != null && !"".equals(listForm)) || (from != null && "list".equals(from) && !"dashboard".equals(from))) { %>
  <input type="checkbox" id="toNextLead" name="toNextLead" value="true" /> <dhv:label name="sales.continueToNextLead.text">Continue to next lead after assigning, trashing or skipping lead</dhv:label>
<% } %>
</span>
<span name="nextlead" id="nextlead" style="display:none">
<dhv:permission name="contacts-external_contacts-view"><input type="button" value="<dhv:label name="calendar.viewContactDetails">View Contact Details</dhv:label>" onClick="javascript:contactDetails();" /></dhv:permission>
<dhv:permission name="sales-leads-edit"><input type="button" value="<dhv:label name="button.nextR">Next ></dhv:label>" onClick="javascript:nextLead();" /></dhv:permission>
</span>
</form>
<iframe src="../empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
