<dhv:pagedListStatus showExpandLink="true" title='<%= User.getSystemStatus(getServletConfig()).getLabel("accounts.accounts_contacts_calls_list.PendingActivities", "Pending Activities") %>' object="CallsListInfo"/>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
    <tr>
      <th width="8">&nbsp;</th>
      <th nowrap="true">
        <strong><dhv:label name="accounts.accountasset_include.Contact">Contact</dhv:label></strong>
      </th>
      <th nowrap="true">
        <strong><dhv:label name="accounts.accounts_calls_list.DueDate">Due Date</dhv:label></strong>
      </th>
      <th nowrap="true">
        <strong><dhv:label name="accounts.accounts_contacts_calls_list.AssignedTo">Assigned To</dhv:label></strong>
      </th>
      <th>
        <strong><dhv:label name="accounts.accounts_add.Type">Type</dhv:label></strong>
      </th>
      <th width="100%">
        <strong><dhv:label name="accounts.accountasset_include.Description">Description</dhv:label></strong>
      </th>
    </tr>
<%
      Iterator j = CallList.iterator();
      if ( j.hasNext() ) {
        int rowid = 0;
          while (j.hasNext()) {
          i++;
            rowid = (rowid != 1?1:2);
            Call thisCall = (Call) j.next();
%>
    <tr class="row<%= rowid %>">
      <td <%= CallsListInfo.getExpandedSelection() && !"".equals(toString(thisCall.getFollowupNotes())) ? "rowspan=\"2\"" : ""%> width="8" valign="top" nowrap>
        <%-- Use the unique id for opening the menu, and toggling the graphics --%>
        <dhv:evaluate if="<%= ContactDetails.getEnabled() && !ContactDetails.isTrashed() %>" >
          <a href="javascript:displayMenu('select<%= i %>','menuCall', '<%= thisCall.getContactId() %>', '<%= thisCall.getId() %>', 'pending', '<%= thisCall.isTrashed() %>');"
          onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>);hideMenu('menuCall')"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
        </dhv:evaluate>
        <dhv:evaluate if="<%= !ContactDetails.getEnabled() || ContactDetails.isTrashed() %>" >&nbsp;</dhv:evaluate>
      </td>
      <td valign="top" nowrap>
        <%= toHtml(thisCall.getContactName()) %>
      </td>
      <td valign="top" nowrap>
        <% if(!User.getTimeZone().equals(thisCall.getAlertDateTimeZone())){%>
        <zeroio:tz timestamp="<%= thisCall.getAlertDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
        <% } else { %>
        <zeroio:tz timestamp="<%= thisCall.getAlertDate() %>" dateOnly="true" timeZone="<%= thisCall.getAlertDateTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
        <% } %>
      </td>
      <td valign="top" nowrap>
        <dhv:username id="<%= thisCall.getOwner() %>" firstInitialLast="true"/>
      </td>
      <td valign="top" nowrap>
        <%= toHtml(CallTypeList.getSelectedValue(thisCall.getAlertCallTypeId())) %>
      </td>
      <td width="100%" valign="top">
        <a href="ExternalContactsCalls.do?command=Details&id=<%= thisCall.getId() %>&contactId=<%= thisCall.getContactId() %><%= addLinkParams(request, "popup|popupType|actionId") %>&view=pending&trailSource=accounts">
        <%= toHtml(thisCall.getAlertText()) %>
        </a>
      </td>
    </tr>
    <dhv:evaluate if='<%= CallsListInfo.getExpandedSelection() && !"".equals(toString(thisCall.getFollowupNotes())) %>'>
        <tr class="row<%= rowid %>">
          <td colspan="5" valign="top">
            <%= toHtmlValue(thisCall.getFollowupNotes()) %>
          </td>
        </tr>
    </dhv:evaluate>
 <%}%>
<%} else {%>
    <tr class="containerBody">
      <td colspan="6">
        <dhv:label name="accounts.accounts_calls_list.NoActivitiesFound">No activities found.</dhv:label>
      </td>
    </tr>
<%}%>
 </table>
 <br>
<%}%>
<% if ((request.getParameter("pagedListSectionId") == null && !CallsListInfo.getExpandedSelection()) || CompletedCallsListInfo.getExpandedSelection()) { %>
 <%-- Completed/Canceled list --%>
  <dhv:pagedListStatus showExpandLink="true" title='<%= User.getSystemStatus(getServletConfig()).getLabel("accounts.accounts_contacts_calls_list.CompletedCanceledActivities", "Completed/Canceled Activities") %>' object="CompletedCallsListInfo"/>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
    <tr>
      <th>
        &nbsp;
      </th>
      <th nowrap>
        <strong><dhv:label name="accounts.accountasset_include.Contact">Contact</dhv:label></strong>
      </th>
      <th>
        <dhv:label name="accounts.accountasset_include.Status">Status</dhv:label>
      </th>
      <th>
        <strong><dhv:label name="accounts.accounts_add.Type">Type</dhv:label></strong>
      </th>
      <th width="50%">
        <strong><dhv:label name="accounts.accounts_contacts_calls_details_include.Subject">Subject</dhv:label></strong>
      </th>
      <th width="50%">
        <dhv:label name="accounts.accounts_calls_list.Result">Result</dhv:label>
      </th>
      <th nowrap>
        <strong><dhv:label name="accounts.accounts_calls_list.Entered">Entered</dhv:label></strong>
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
      <td <%= CompletedCallsListInfo.getExpandedSelection() && !"".equals(toString(thisCall.getNotes())) ? "rowspan=\"2\"" : ""%> width="8" valign="top" nowrap class="row<%= rowid %>">
        <%-- Use the unique id for opening the menu, and toggling the graphics --%>
        <dhv:evaluate if="<%= ContactDetails.getEnabled() && !ContactDetails.isTrashed() %>" >
          <a href="javascript:displayMenu('select<%= i %>','menuCall','<%= thisCall.getContactId() %>','<%= thisCall.getId() %>','<%= thisCall.getStatusId() == Call.CANCELED ? "cancel" : ""%>','<%= thisCall.isTrashed() %>');"
          onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>);hideMenu('menuCall');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
        </dhv:evaluate>
        <dhv:evaluate if="<%= !ContactDetails.getEnabled() || ContactDetails.isTrashed() %>" >&nbsp;</dhv:evaluate>
      </td>
      <td valign="top" nowrap>
        <%= thisCall.getContactName() %>
      </td>
      <td valign="top" nowrap>
        <%= thisCall.getStatusString() %>
      </td>
      <td valign="top" nowrap>
        <%= toHtml(CallTypeList.getSelectedValue(thisCall.getCallTypeId())) %>
      </td>
      <td width="50%" valign="top">
        <a href="ExternalContactsCalls.do?command=Details&id=<%= thisCall.getId() %>&contactId=<%= thisCall.getContactId() %><%= addLinkParams(request, "popup|popupType|actionId") %>&trailSource=accounts">
          <%= toHtml(thisCall.getSubject()) %>
        </a>
      </td>
      <td width="50%" valign="top">
        <%= toHtml(callResultList.getLookupList(thisCall.getResultId()).getSelectedValue(thisCall.getResultId())) %>
      </td>
      <td valign="top" nowrap>
        <dhv:username id="<%= thisCall.getEnteredBy() %>" firstInitialLast="true"/><br />
        <zeroio:tz timestamp="<%= thisCall.getEntered() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" />
      </td>
    </tr>
    <dhv:evaluate if='<%= CompletedCallsListInfo.getExpandedSelection()  && !"".equals(toString(thisCall.getNotes()))%>'>
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
<%}%>