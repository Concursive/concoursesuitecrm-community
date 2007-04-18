<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/portlet.tld" prefix="portlet" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.*" %>
<jsp:useBean id="Category" class="org.aspcfs.modules.base.CustomFieldCategory" scope="request"/>
<jsp:useBean id="Record" class="org.aspcfs.modules.base.CustomFieldRecord" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<portlet:defineObjects/>
<form name="details" action="" method="post">
  <table cellspacing="0" cellpadding="0" border="0" width="100%">
    <tr>
      <td>
        <font size="2"><dhv:label name="accounts.accounts_documents_folders_add.Folder.colon">Folder:</dhv:label>
        <strong><%= toHtml(Category.getName()) %></strong></font>
      </td>
      <% if (!Category.getAllowMultipleRecords()) { %>
        <td align="right" nowrap>
          <img src="images/icons/16_edit_comment.gif" align="absMiddle" border="0" />
          <font size="2"><dhv:label name="accounts.accounts_fields.FolderOneRecord">This folder can have only one record</dhv:label></font>
        </td>
      <% } else { %>
        <td align="right" nowrap>
          <img src="images/icons/16_edit_comment.gif" align="absMiddle" border="0" />
          <font size="2"><dhv:label name="accounts.accounts_fields.FolderHaveMultipleRecords">This folder can have multiple records</dhv:label></font>
        </td>
      <% } %>
    </tr>
  </table>
  <br>
  <dhv:evaluate if="<%=Category.getCanEdit()%>">
    <input type="submit" value="<dhv:label name="global.button.modify">Modify</dhv:label>" onClick="javascript:this.form.action='<portlet:renderURL><portlet:param name="viewType" value="edit"/><portlet:param name="recordId" value="<%=Integer.toString(Record.getId())%>"/></portlet:renderURL>'">
  </dhv:evaluate>
  <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='<portlet:renderURL/>'"><br>
  <br>
  <%
    Iterator groups = Category.iterator();
    while (groups.hasNext()) {
      CustomFieldGroup thisGroup = (CustomFieldGroup)groups.next();
  %>
  <table cellpadding="4" cellspacing="0" border="1" width="100%" class="Details">
    <tr>
      <th colspan="2">
        <strong><dhv:label name="portlets.folder.group">Group:</dhv:label></strong><%= StringUtils.toHtml(thisGroup.getName())%>
      </th>
    </tr>
  <%
    Iterator fields = thisGroup.iterator();
    if (fields.hasNext()) {
      while (fields.hasNext()) {
        CustomField thisField = (CustomField)fields.next();
  %>
      <tr class="containerBody">
        <%-- Do not use toHtml() here, it's done by CustomField --%>
        <td valign="top">
          <%= thisField.getNameHtml() %>
        </td>
        <td valign="top">
          <%= thisField.getValueHtml() %>
        </td>
      </tr> 
  <%
      }
    } else {
  %>
      <tr class="containerBody">
        <td colspan="2">
          <font color="#9E9E9E"><dhv:label name="accounts.accounts_fields.NoFieldsAvailable">No fields available.</dhv:label></font>
        </td>
      </tr>
  <%}%>
  </table>
  &nbsp;
  <%}%>
  <table cellpadding="4" cellspacing="0" border="1" width="100%" class="details">
    <tr>
      <th colspan="2">
        <strong><dhv:label name="accounts.accounts_contacts_calls_details.RecordInformation">Record Information</dhv:label></strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_calls_list.Entered">Entered</dhv:label>
      </td>
      <td>
        <dhv:username id="<%= Record.getEnteredBy() %>" />
        <zeroio:tz timestamp="<%= Record.getEntered()  %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" />
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_contacts_calls_details.Modified">Modified</dhv:label>
      </td>
      <td>
        <dhv:username id="<%= Record.getModifiedBy() %>" />
        <zeroio:tz timestamp="<%= Record.getModified()  %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" />
      </td>
    </tr>
  </table>
  <br>
  <dhv:evaluate if="<%=Category.getCanEdit()%>">
    <input type="submit" value="<dhv:label name="global.button.modify">Modify</dhv:label>" onClick="javascript:this.form.action='<portlet:renderURL><portlet:param name="viewType" value="edit"/><portlet:param name="recordId" value="<%=Integer.toString(Record.getId())%>"/></portlet:renderURL>'">
  </dhv:evaluate>
  <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='<portlet:renderURL/>'">
  <%= addHiddenParams(request, "popup|popupType|actionId") %>
</form>
