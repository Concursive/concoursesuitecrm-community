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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="CallList" class="org.aspcfs.modules.contacts.base.CallList" scope="request"/>
<jsp:useBean id="CompletedCallList" class="org.aspcfs.modules.contacts.base.CallList" scope="request"/>
<jsp:useBean id="CalAccountContactCompletedCallsListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="CallTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="callResultList" class="org.aspcfs.modules.contacts.base.CallResultList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript">
  function showHistory() {
    popURL('CalendarCalls.do?command=View&contactId=<%= ContactDetails.getId() %>&popup=true','CONTACT_HISTORY','650','500','yes','yes');
  }
</script>
<dhv:container name="accountscontacts" selected="calls" object="ContactDetails" param='<%= "id=" + ContactDetails.getId() %>' hideContainer="<%= isPopup(request) %>">
  <%-- include contact menu --%>
  <% 
    int i = 0;
    String param1 = "id=" + ContactDetails.getId();
    String selected = "calls";
  %>
  [<a href="javascript:popURL('ExternalContacts.do?command=ContactDetails&id=<%= ContactDetails.getId() %>&popup=true&viewOnly=true','Details','650','500','yes','yes');"><dhv:label name="accounts.accounts_calls_list_menu.ViewDetails">View Details</dhv:label></a>]
<%--  <%@ include file="../accounts/accounts_contacts_details_header_include.jsp"%> --%>
  <%-- Completed/Canceled list --%>
  <dhv:pagedListStatus showExpandLink="false" title="Completed/Canceled Activities" object="CalAccountContactCompletedCallsListInfo"/>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
    <tr>
      <th>
        <dhv:label name="accounts.accountasset_include.Status">Status</dhv:label>
      </th>
      <th>
        <dhv:label name="accounts.accounts_add.Type">Type</dhv:label>
      </th>
      <th width="100%">
        <dhv:label name="accounts.accounts_contacts_calls_details_include.Subject">Subject</dhv:label>
      </th>
      <th>
        <dhv:label name="accounts.accounts_calls_list.Result">Result</dhv:label>
      </th>
      <th nowrap>
        <dhv:label name="quotes.date">Date</dhv:label>
      </th>
      <th nowrap>
        <dhv:label name="accounts.accounts_calls_list.EnteredBy">Entered By</dhv:label>
      </th>
      <th>
        <dhv:label name="accounts.accounts_calls_list.Entered">Entered</dhv:label>
      </th>
    </tr>
<%
  Iterator jc = CompletedCallList.iterator();
  if ( jc.hasNext() ) {
    int rowid = 0;
      while (jc.hasNext()) {
      i++;
        rowid = (rowid != 1?1:2);
        Call thisCall = (Call) jc.next();
%>
    <tr class="row<%= rowid %>">
      <td valign="top" nowrap <dhv:evaluate if='<%= CalAccountContactCompletedCallsListInfo.getExpandedSelection()  && !"".equals(toString(thisCall.getNotes()))%>'> rowspan="2"</dhv:evaluate>>
        <%= thisCall.getStatusString() %>
      </td>
      <td valign="top" nowrap>
        <%= toHtml(CallTypeList.getSelectedValue(thisCall.getCallTypeId())) %>
      </td>
      <td width="100%" valign="top">
          <%= toHtml(thisCall.getSubject()) %>
      </td>
      <td valign="top" nowrap>
        <%= toHtml(callResultList.getLookupList(thisCall.getResultId()).getSelectedValue(thisCall.getResultId())) %>
      </td>
      <td valign="top" nowrap>
        <dhv:tz timestamp="<%= thisCall.getCompleteDate() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>" />
      </td>
      <td valign="top" nowrap>
        <dhv:username id="<%= thisCall.getEnteredBy() %>" firstInitialLast="true"/>
      </td>
      <td valign="top" nowrap>
        <dhv:tz timestamp="<%= thisCall.getEntered() %>" dateFormat="<%= DateFormat.SHORT %>" timeFormat="<%= DateFormat.LONG %>"/>
      </td>
    </tr>
    <dhv:evaluate if='<%= CalAccountContactCompletedCallsListInfo.getExpandedSelection()  && !"".equals(toString(thisCall.getNotes()))%>'>
    <tr class="row<%= rowid %>">
      <td colspan="7" valign="top">
        <%= toHtmlValue(thisCall.getNotes()) %>
      </td>
    </tr>
    </dhv:evaluate>
 <%}%>
<%} else {%>
    <tr class="containerBody">
      <td colspan="7">
        <dhv:label name="accounts.accounts_calls_list.NoActivitiesFound">No activities found.</dhv:label>
      </td>
    </tr>
<%}%>
 </table>
<%-- End Container --%>
</dhv:container>


