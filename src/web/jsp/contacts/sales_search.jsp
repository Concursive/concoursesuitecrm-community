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
<%@ page import="java.util.*, java.sql.*" %>
<%@ page import="org.aspcfs.modules.contacts.base.*,org.aspcfs.utils.web.*" %>
<%@ page import="com.zeroio.iteam.base.*,org.aspcfs.modules.login.beans.*,org.aspcfs.controller.*" %>
<%@ page import="org.aspcfs.modules.admin.base.User, org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="SalesListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session" />
<jsp:useBean id="contactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request" />
<jsp:useBean id="countrySelect" class="org.aspcfs.utils.web.CountrySelect" scope="request" />
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="RatingList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<jsp:useBean id="allOpenLeads" class="java.lang.String" scope="request" />
<jsp:useBean id="GraphFileName" class="java.lang.String" scope="request" />
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application" />
<jsp:useBean id="systemStatus" class="org.aspcfs.controller.SystemStatus" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkInt.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkPhone.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkEmail.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popContacts.js"></script>
<SCRIPT LANGUAGE="JavaScript" type="text/javascript">
<%-- Preload image rollovers for drop-down menu --%>
  loadImages('select-arrow');

<%-- Clear form function --%>
  function clearForm(form) {
    form.searchcodeLeadStatus.selectedIndex = 0;
    form.searchcodeOldestFirst.selectedIndex = 0;
    form.searchcodeCountry.selectedIndex = 0;
    form.searchcodeSource.selectedIndex = 0;
    form.searchcodeRating.selectedIndex = 0;
    form.searchcodePostalCode.value = '';
    form.searchdateEnteredStart.value = '';
    form.searchdateEnteredEnd.value = '';
    form.searchcodeOwner.value = '-1';
    changeDivContent('changeowner',label('label.any','Any'));
  }
  
  function checkForm(form) {
    formTest = true;
    message = "";
    if (!checkInt(form.searchcodePostalCode.value)) {
      message += label("check.postalcode", "- Please enter a valid Postal Code\r\n");
      formTest = false;
    }
    if (formTest == false) {
      alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    }
    return true;
  }
  
  function resetNumericFieldValue(fieldId){
    document.getElementById(fieldId).value = -1;
  }

</script>
<form name="searchLeads" action="Sales.do?command=List&contactId=0&from=list" method="post" onSubmit="javascript:return checkForm(this);" >
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
  <a href="Sales.do"><dhv:label name="Leads" mainMenuItem="true">Leads</dhv:label></a> >
  <dhv:label name="sales.searchLeads">Search Leads</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:formMessage showSpace="false" />
<%-- Search Form --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th valign="center" align="left" nowrap colspan="2">
      <dhv:label name="sales.searchLeads">Search Leads</dhv:label>
    </th>
  </tr>
  <tr>
    <td class="formLabel" nowrap valign="top"><dhv:label name="accounts.accountasset_include.Status">Status</dhv:label></td>
    <td>
      <select name="searchcodeLeadStatus" size="1">
        <option value="<%= Contact.LEAD_UNREAD %>" <%= (SalesListInfo.getSearchOptionValue("searchcodeLeadStatus").equals(""+Contact.LEAD_UNREAD))?"SELECTED":"" %> ><dhv:label name="sales.Unread">Unread</dhv:label></option>
        <option value="<%= Contact.LEAD_UNPROCESSED %>" <%= (SalesListInfo.getSearchOptionValue("searchcodeLeadStatus").equals(""+Contact.LEAD_UNPROCESSED))?"SELECTED":"" %> ><dhv:label name="sales.Unprocessed">Unprocessed</dhv:label></option>
        <option value="<%= Contact.LEAD_TRASHED %>" <%= (SalesListInfo.getSearchOptionValue("searchcodeLeadStatus").equals(""+Contact.LEAD_TRASHED))?"SELECTED":"" %> ><dhv:label name="sales.Trashed">Trashed</dhv:label></option>
        <option value="<%= Contact.LEAD_ASSIGNED %>" <%= (SalesListInfo.getSearchOptionValue("searchcodeLeadStatus").equals(""+Contact.LEAD_ASSIGNED))?"SELECTED":"" %> ><dhv:label name="sales.Assigned">Assigned</dhv:label></option>
      </select>
    </td>
  </tr>
  <tr>
    <td class="formLabel" nowrap valign="top">
      <dhv:label name="quotes.owner">Owner</dhv:label>
    </td>
    <td nowrap>
      <table class="empty">
        <tr>
          <td>
            <div id="changeowner">
            <%if (SalesListInfo.getSearchOptionValue("searchcodeOwner") != null && !"".equals(SalesListInfo.getSearchOptionValue("searchcodeOwner")) && !"-1".equals(SalesListInfo.getSearchOptionValue("searchcodeOwner"))) {%>
              <dhv:username id="<%= SalesListInfo.getSearchOptionValue("searchcodeOwner") %>" lastFirst="true" />
            <%} else {%>
              <dhv:label name="pipeline.any">Any</dhv:label>
            <%}%>
            </div>
          </td>
          <td>
            <input type="hidden" name="searchcodeOwner" id="ownerid" value="<%= SalesListInfo.getSearchOptionValue("searchcodeOwner") %>">
            &nbsp;[<a href="javascript:popContactsListSingle('ownerid','changeowner', 'listView=employees&usersOnly=true');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
            &nbsp; [<a href="javascript:changeDivContent('changeowner',label('label.any','Any'));javascript:resetNumericFieldValue('ownerid');"><dhv:label name="accounts.accountasset_include.clear">Clear</dhv:label></a>]
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td class="formLabel" nowrap valign="top"><dhv:label name="contact.source">Source</dhv:label></td>
    <td><%= SourceList.getHtmlSelect("searchcodeSource",SalesListInfo.getSearchOptionValue("searchcodeSource")) %></td>
  </tr>
  <tr>
    <td class="formLabel" nowrap valign="top"><dhv:label name="sales.rating">Rating</dhv:label></td>
    <td><%= RatingList.getHtmlSelect("searchcodeRating",SalesListInfo.getSearchOptionValue("searchcodeRating")) %></td>
  </tr><tr>
    <td class="formLabel" nowrap valign="top"><dhv:label name="accounts.accounts_add.Country">Country</dhv:label></td>
    <td><%= countrySelect.getHtml("searchcodeCountry",SalesListInfo.getSearchOptionValue("searchcodeCountry")) %></td>
  </tr><tr>
    <td class="formLabel" nowrap><dhv:label name="accounts.accounts_add.ZipPostalCode">Postal Code</dhv:label></td>
    <td><input type="text" name="searchcodePostalCode" value="<%= SalesListInfo.getSearchOptionValue("searchcodePostalCode") %>" size="10" maxlength="12"></td>
  </tr><tr>
    <td class="formLabel" valign="top" nowrap><dhv:label name="ticket.enteredDateBetween">Date Entered between</dhv:label></td>
    <td nowrap>
      <zeroio:dateSelect form="searchLeads" field="searchdateEnteredStart" timestamp="<%= SalesListInfo.getSearchOptionValue("searchdateEnteredStart") %>" timeZone="<%= User.getTimeZone() %>" />
      &nbsp;<dhv:label name="admin.and.lowercase">and</dhv:label>
      <%=showAttribute(request,"searchdateEnteredStartError")%><br />
      <zeroio:dateSelect form="searchLeads" field="searchdateEnteredEnd" timestamp="<%= SalesListInfo.getSearchOptionValue("searchdateEnteredEnd") %>" timeZone="<%= User.getTimeZone() %>" />
      <%=showAttribute(request,"searchdateEnteredEndError")%>
    </td>
  </tr><tr>
    <td class="formLabel" valign="top" nowrap><dhv:label name="sales.sortOrder">Sort Order</dhv:label></td>
    <td>
      <select name="searchcodeOldestFirst" size="1">
        <option value="<%= Constants.TRUE %>" <%= (SalesListInfo.getSearchOptionValue("searchcodeOldestFirst").equals(""+Constants.TRUE))?"SELECTED":"" %> ><dhv:label name="sales.oldestFirst">Oldest First</dhv:label></option>
        <option value="<%= Constants.FALSE %>" <%= (SalesListInfo.getSearchOptionValue("searchcodeOldestFirst").equals(""+Constants.FALSE))?"SELECTED":"" %> ><dhv:label name="sales.newestFirst">Newest First</dhv:label></option>
      </select>
    </td>
  </tr>
</table><br />
<input type="submit" value="<dhv:label name="button.getNextLead">Get Next Lead</dhv:label>" onClick="javascript:this.form.nextValue.value='true';" />
<input type="submit" value="<dhv:label name="project.viewList">View List</dhv:label>" onClick="javascript:this.form.nextValue.value='';" />
<input type="button" value="<dhv:label name="button.clear">Clear</dhv:label>" onClick="javascript:clearForm(this.form);" />
<input type="hidden" name="searchcodeUserId" value="<%= User.getUserRecord().getId() %>" />
<input type="hidden" name="searchcodeLeadsOnly" value="<%= Constants.TRUE %>" />
<input type="hidden" name="listForm" value="true" />
<input type="hidden" name="nextValue" value="" />
</form>

