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
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*, java.sql.*" %>
<%@ page import="org.aspcfs.modules.contacts.base.*,org.aspcfs.utils.web.*,org.aspcfs.utils.*" %>
<%@ page import="com.zeroio.iteam.base.*,org.aspcfs.modules.login.beans.*,org.aspcfs.controller.*" %>
<%@ page import="org.aspcfs.modules.admin.base.User, org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="SalesListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session" />
<jsp:useBean id="contactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request" />
<jsp:useBean id="stateSelect" class="org.aspcfs.utils.web.StateSelect" scope="request"/>
<jsp:useBean id="CountrySelect" class="org.aspcfs.utils.web.CountrySelect" scope="request" />
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="RatingList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
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
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js?1"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<script language="JavaScript" TYPE="text/javascript" src="javascript/popContacts.js?v=20070827"></script>
<SCRIPT LANGUAGE="JavaScript" type="text/javascript">
<%-- Preload image rollovers for drop-down menu --%>
  loadImages('select-arrow');

<%-- Clear form function --%>
  function clearForm(form) {
  <dhv:include name="leads.search.status" none="true">
    form.searchcodeLeadStatus.selectedIndex = 0;
  </dhv:include><dhv:include name="leads.search.sortOrder" none="true">
    form.searchcodeOldestFirst.selectedIndex = 0;
  </dhv:include><dhv:include name="leads.search.country" none="true">
    form.searchcodeCountry.selectedIndex = 0;
    update('searchcodeCountry');
  </dhv:include><dhv:include name="leads.search.source" none="true"><dhv:evaluate if="<%= SourceList.size() > 0 %>">
    form.searchcodeSource.selectedIndex = 0;
  </dhv:evaluate></dhv:include><dhv:include name="leads.search.rating" none="true"><dhv:evaluate if="<%= RatingList.size() > 0 %>">
    form.searchcodeRating.selectedIndex = 0;
  </dhv:evaluate></dhv:include><dhv:include name="leads.search.postalCode" none="true">
    form.searchPostalCode.value = '';
  </dhv:include><dhv:include name="leads.search.city" none="true">
    form.searchcodeCity.value = '';
  </dhv:include><dhv:include name="leads.search.state" none="true">
    form.elements['searchcodeState'].options.selectedIndex = 0;
    form.elements['searchcodeState1'].value = '';
  </dhv:include>
  form.searchFirstName.value = '';
  form.searchLastName.value = '';
  <dhv:include name="leads.search.company" none="true">
    form.searchCompany.value = '';
  </dhv:include><dhv:include name="leads.search.enteredDateBetween" none="true">
    form.searchdateEnteredStart.value = '';
    form.searchdateEnteredEnd.value = '';
  </dhv:include><dhv:include name="leads.search.owner" none="true">
    form.searchcodeOwner.value = '-1';
    changeDivContent('changeowner',label('label.any','Any'));
  </dhv:include><dhv:include name="leads.search.siteId" none="true">
    <dhv:evaluate if="<%= SiteIdList.size() > 0 %>">
      <dhv:evaluate if="<%=User.getUserRecord().getSiteId() == -1 %>" >
        form.searchcodeSiteId.selectedIndex = 0;
      </dhv:evaluate>
    </dhv:evaluate>
  </dhv:include>
  }
  
  function checkForm(form) {
    formTest = true;
    message = "";
  <dhv:include name="leads.search.postalCode" none="true">
    if (checkNullString(form.searchPostalCode.value) && form.searchPostalCode.value != '') {
      message += label("check.postalcode", "- Please enter a valid Postal Code\r\n");
      formTest = false;
    }
  </dhv:include>
    if (formTest == false) {
      alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    }
    return true;
  }
  
  function update(countryObj, stateObj, selectedValue) {
    var country = document.forms['searchLeads'].elements[countryObj].value;
    var url = "ExternalContacts.do?command=States&country="+country+"&obj="+stateObj+"&selected="+selectedValue+"&form=searchLeads&stateObj=searchcodeState";
    window.frames['server_commands'].location.href=url;
  }

  function continueUpdateState(stateObj, showText) {
    if(showText == 'true'){
      hideSpan('state11');
      showSpan('state21');
    } else {
      hideSpan('state21');
      showSpan('state11');
    }
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
  <dhv:include name="leads.search.status" none="true">
  <tr>
    <td class="formLabel" nowrap valign="top"><dhv:label name="accounts.accountasset_include.Status">Status</dhv:label></td>
    <td>
      <select name="searchcodeLeadStatus" size="1">
        <option value="-1" <%= (SalesListInfo.getSearchOptionValue("searchcodeLeadStatus").equals("-1"))?"SELECTED":"" %> ><dhv:label name="quotes.all">All</dhv:label></option>
      <dhv:include name="leads.search.unread" none="true">
        <option value="<%= Contact.LEAD_UNREAD %>" <%= (SalesListInfo.getSearchOptionValue("searchcodeLeadStatus").equals(""+Contact.LEAD_UNREAD))?"SELECTED":"" %> ><dhv:label name="sales.Unread">Unread</dhv:label></option>
      </dhv:include>
        <option value="<%= Contact.LEAD_UNPROCESSED %>" <%= (SalesListInfo.getSearchOptionValue("searchcodeLeadStatus").equals(""+Contact.LEAD_UNPROCESSED))?"SELECTED":"" %> ><dhv:label name="sales.Unprocessed">Unprocessed</dhv:label></option>
        <option value="<%= Contact.LEAD_ASSIGNED %>" <%= (SalesListInfo.getSearchOptionValue("searchcodeLeadStatus").equals(""+Contact.LEAD_ASSIGNED))?"SELECTED":"" %> ><dhv:label name="sales.Assigned">Assigned</dhv:label></option>
        <option value="<%= Contact.LEAD_TRASHED %>" <%= (SalesListInfo.getSearchOptionValue("searchcodeLeadStatus").equals(""+Contact.LEAD_TRASHED))?"SELECTED":"" %> ><dhv:label name="sales.Trashed">Trashed</dhv:label></option>
        <option value="<%= Contact.LEAD_WORKED %>" <%= (SalesListInfo.getSearchOptionValue("searchcodeLeadStatus").equals(String.valueOf(Contact.LEAD_WORKED)))?"SELECTED":"" %> ><dhv:label name="sales.working">Working</dhv:label></option>
      </select>
    </td>
  </tr>
  </dhv:include>
  <dhv:include name="leads.search.owner" none="true">
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
              <dhv:username id='<%= SalesListInfo.getSearchOptionValue("searchcodeOwner") %>' lastFirst="true" />
            <%} else {%>
              <dhv:label name="pipeline.any">Any</dhv:label>
            <%}%>
            </div>
          </td>
          <td>
<%--          <zeroio:debug value='<%= "JSP:: is the owner value not null? "+(SalesListInfo.getSearchOptionValue("searchcodeOwner") != null && !"".equals(SalesListInfo.getSearchOptionValue("searchcodeOwner")))+" the value is "+SalesListInfo.getSearchOptionValue("searchcodeOwner") %>' /> --%>
            <input type="hidden" name="searchcodeOwner" id="ownerid" value="<%= SalesListInfo.getSearchOptionValue("searchcodeOwner") != null && !"".equals(SalesListInfo.getSearchOptionValue("searchcodeOwner"))? SalesListInfo.getSearchOptionValue("searchcodeOwner"):"-1" %>" />
            &nbsp;[<a href="javascript:popContactsListSingle('ownerid','changeowner', 'listView=employees&usersOnly=true<%= User.getUserRecord().getSiteId() == -1 ? "&includeAllSites=true&siteId=-1":"&mySiteOnly=true&siteId="+ User.getUserRecord().getSiteId() %>&hierarchy=<%= User.getUserId() %>');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
            &nbsp; [<a href="javascript:changeDivContent('changeowner',label('label.any','Any'));javascript:resetNumericFieldValue('ownerid');"><dhv:label name="accounts.accountasset_include.clear">Clear</dhv:label></a>]
          </td>
        </tr>
      </table>
    </td>
  </tr>
  </dhv:include>
  <dhv:include name="leads.search.company" none="true">
  <tr>
    <td class="formLabel" nowrap><dhv:label name="accounts.accounts_contacts_detailsimport.Company">Company</dhv:label></td>
    <td><input type="text" name="searchCompany" value="<%= SalesListInfo.getSearchOptionValue("searchCompany") %>" size="35" /></td>
  </tr>
  </dhv:include>
  <tr>
    <td class="formLabel" nowrap><dhv:label name="accounts.accounts_add.FirstName">First Name</dhv:label></td>
    <td><input type="text" name="searchFirstName" value="<%= SalesListInfo.getSearchOptionValue("searchFirstName") %>" size="30" /></td>
  </tr>
  <tr>
    <td class="formLabel" nowrap><dhv:label name="accounts.accounts_add.LastName">Last Name</dhv:label></td>
    <td><input type="text" name="searchLastName" value="<%= SalesListInfo.getSearchOptionValue("searchLastName") %>" size="35" /></td>
  </tr>
  <dhv:include name="leads.search.source" none="true">
  <dhv:evaluate if="<%= SourceList.size() > 0 %>">
  <tr>
    <td class="formLabel" nowrap valign="top"><dhv:label name="contact.source">Source</dhv:label></td>
    <td><%= SourceList.getHtmlSelect("searchcodeSource",SalesListInfo.getSearchOptionValue("searchcodeSource")) %></td>
  </tr>
  </dhv:evaluate>
  </dhv:include>
  <dhv:include name="leads.search.rating" none="true">
  <dhv:evaluate if="<%= RatingList.size() > 0 %>">
  <tr>
    <td class="formLabel" nowrap valign="top"><dhv:label name="sales.rating">Rating</dhv:label></td>
    <td><%= RatingList.getHtmlSelect("searchcodeRating",SalesListInfo.getSearchOptionValue("searchcodeRating")) %></td>
  </tr>
  </dhv:evaluate>
  </dhv:include>
  <dhv:include name="leads.search.city" none="true">
  <tr>
    <td class="formLabel" nowrap><dhv:label name="accounts.accounts_add.City">City</dhv:label></td>
    <td><input type="text" name="searchcodeCity" value="<%= SalesListInfo.getSearchOptionValue("searchcodeCity") %>" size="35" maxlength="80"></td>
  </tr>
  </dhv:include>
  <dhv:include name="leads.search.state" none="true">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>
      <span name="state11" ID="state11" style="<%= stateSelect.hasCountry(SalesListInfo.getSearchOptionValue("searchcodeCountry"))? "" : " display:none" %>">
        <%= stateSelect.getHtmlSelect("searchcodeState",SalesListInfo.getSearchOptionValue("searchcodeCountry"), SalesListInfo.getSearchOptionValue("searchcodeState")) %>
      </span>
      <%-- If selected country is not US/Canada use textfield --%>
      <span name="state21" ID="state21" style="<%= !stateSelect.hasCountry(SalesListInfo.getSearchOptionValue("searchcodeCountry")) ? "" : " display:none" %>">
        <input type="text" size="25" name="searchcodeState1"  value="<%= toHtmlValue(SalesListInfo.getSearchOptionValue("searchcodeState1")) %>">
      </span>
    </td>
  </tr>
  </dhv:include>
  <dhv:include name="leads.search.country" none="true">
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.Country">Country</dhv:label>
    </td>
    <td>
      <% CountrySelect.setJsEvent("onChange=\"javascript:update('searchcodeCountry','1','"+ SalesListInfo.getSearchOptionValue("searchcodeState1") +"');\""); %>
      <%= CountrySelect.getHtml("searchcodeCountry", SalesListInfo.getSearchOptionValue("searchcodeCountry")) %>
    </td>
  </tr>
  </dhv:include>
<%--
  <tr>
    <td class="formLabel" nowrap><dhv:label name="accounts.accounts_add.StateProvince">State/Province</dhv:label></td>
    <td><input type="text" name="searchcodeState" value="<%= SalesListInfo.getSearchOptionValue("searchcodeState") %>" size="35" maxlength="80"></td>
  </tr>
  </dhv:include>
  <dhv:include name="leads.search.country" none="true">
  <tr>
    <td class="formLabel" nowrap valign="top"><dhv:label name="accounts.accounts_add.Country">Country</dhv:label></td>
    <td><%= countrySelect.getHtml("searchcodeCountry",SalesListInfo.getSearchOptionValue("searchcodeCountry")) %></td>
  </tr>
  </dhv:include>
  --%>
  <dhv:include name="leads.search.postalCode" none="true">
  <tr>
    <td class="formLabel" nowrap><dhv:label name="accounts.accounts_add.ZipPostalCode">Postal Code</dhv:label></td>
    <td><input type="text" name="searchPostalCode" value="<%= SalesListInfo.getSearchOptionValue("searchPostalCode") %>" size="10" maxlength="12"></td>
  </tr>
  </dhv:include>
  <dhv:include name="leads.search.enteredDateBetween" none="true">
  <tr>
    <td class="formLabel" valign="top" nowrap><dhv:label name="ticket.enteredDateBetween">Date Entered between</dhv:label></td>
    <td nowrap>
      <zeroio:dateSelect form="searchLeads" field="searchdateEnteredStart" timestamp='<%= SalesListInfo.getSearchOptionValue("searchdateEnteredStart") %>' timeZone="<%= User.getTimeZone() %>" />
      &nbsp;<dhv:label name="admin.and.lowercase">and</dhv:label>
      <%=showAttribute(request,"searchdateEnteredStartError")%><br />
      <zeroio:dateSelect form="searchLeads" field="searchdateEnteredEnd" timestamp='<%= SalesListInfo.getSearchOptionValue("searchdateEnteredEnd") %>' timeZone="<%= User.getTimeZone() %>" />
      <%=showAttribute(request,"searchdateEnteredEndError")%>
    </td>
  </tr>
  </dhv:include>
  <dhv:include name="leads.search.siteId" none="true">
  <dhv:evaluate if="<%= SiteIdList.size() > 0 %>">
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="accounts.site">Site</dhv:label>
    </td>
    <td>
     <dhv:evaluate if="<%=User.getUserRecord().getSiteId() == -1 %>" >
      <%= SiteIdList.getHtmlSelect("searchcodeSiteId", ("".equals(SalesListInfo.getSearchOptionValue("searchcodeSiteId")) ? String.valueOf(Constants.INVALID_SITE) : SalesListInfo.getSearchOptionValue("searchcodeSiteId"))) %>
     </dhv:evaluate>
     <dhv:evaluate if="<%=User.getUserRecord().getSiteId() != -1 %>" >
        <input type="hidden" name="searchcodeSiteId" value="<%= User.getUserRecord().getSiteId() %>">
        <%= SiteIdList.getSelectedValue(User.getUserRecord().getSiteId()) %>
     </dhv:evaluate>
     <input type="hidden" name="searchcodeExclusiveToSite" value="true"/>
    </td>
  </tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= SiteIdList.size() == 0 %>">
    <input type="hidden" name="searchcodeSiteId" value="<%= User.getUserRecord().getSiteId() %>">
  </dhv:evaluate>
  </dhv:include>
  <dhv:include name="leads.search.sortOrder" none="true">
  <tr>
    <td class="formLabel" valign="top" nowrap><dhv:label name="sales.sortOrder">Sort Order</dhv:label></td>
    <td>
      <select name="searchcodeOldestFirst" size="1">
        <option value="<%= Constants.FALSE %>" <%= (SalesListInfo.getSearchOptionValue("searchcodeOldestFirst") == null || SalesListInfo.getSearchOptionValue("searchcodeOldestFirst").equals(""+Constants.UNDEFINED) || SalesListInfo.getSearchOptionValue("searchcodeOldestFirst").equals(""+Constants.FALSE))?"SELECTED":"" %> ><dhv:label name="sales.newestFirst">Newest First</dhv:label></option>
        <option value="<%= Constants.TRUE %>" <%= (SalesListInfo.getSearchOptionValue("searchcodeOldestFirst").equals(""+Constants.TRUE))?"SELECTED":"" %> ><dhv:label name="sales.oldestFirst">Oldest First</dhv:label></option>
      </select>
    </td>
  </tr>
  </dhv:include>
</table><br />
<dhv:include name="leads.search.getNextLead" none="true">
<input type="submit" value="<dhv:label name="button.getNextLead">Get Next Lead</dhv:label>" onClick="javascript:this.form.nextValue.value='true';" />
</dhv:include>
<input type="submit" value="<dhv:label name="project.viewList">View List</dhv:label>" onClick="javascript:this.form.nextValue.value='';" />
<input type="button" value="<dhv:label name="button.clear">Clear</dhv:label>" onClick="javascript:clearForm(this.form);" />
<input type="hidden" name="searchcodeUserId" value="<%= User.getUserRecord().getId() %>" />
<input type="hidden" name="searchcodeZipCodeAscPotentialDesc" value="true" />
<input type="hidden" name="listForm" value="true" />
<input type="hidden" name="nextValue" value="" />
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<script type="text/javascript">
  update('searchcodeCountry','1', document.forms['searchLeads'].elements['searchcodeState1'].value);
</script>
</form>

