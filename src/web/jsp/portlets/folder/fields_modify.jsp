<%--
- Copyright(c) 2007 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
 - Version: $Id: fields_modify.jsp 4.1 2007-05-04 18:32:57 +0530 (Fri, 04 May 2007) rajendrad $
- Description:
--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/portlet.tld" prefix="portlet" %>
<%@ page import="java.util.*,org.aspcfs.modules.base.*" %>
<jsp:useBean id="Category" class="org.aspcfs.modules.base.CustomFieldCategory" scope="request"/>
<jsp:useBean id="systemStatus" class="org.aspcfs.controller.SystemStatus" scope="request"/>
<jsp:useBean id="beforeModifyTimeStamp" class="java.lang.String" scope="request"/>
<portlet:defineObjects/>
<%@ include file="../../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script type="text/javascript">
</script>
<% int randomNumber;
  String formName = Integer.toString((randomNumber = new Random().nextInt()) > 0 ? randomNumber / 10000 : (-randomNumber / 10000));
%>
<form name="details<%=formName%>" action="" method="post">

  <table cellspacing="0" cellpadding="0" border="0" width="100%">
    <tr>
      <td>
        <font size="2"><dhv:label name="accounts.accounts_documents_folders_add.Folder">Folder</dhv:label>:
          <strong><%= toHtml(Category.getName()) %></strong></font>
      </td>
      <% if (!Category.getAllowMultipleRecords()) { %>
      <td align="right" nowrap>
        <img src="images/icons/16_edit_comment.gif" align="absMiddle" border="0">
        <font size="2"><dhv:label name="accounts.accounts_fields.FolderOneRecord">This folder can have only one
          record</dhv:label></font>
      </td>
      <% } else { %>
      <td align="right" nowrap>
        <img src="images/icons/16_edit_comment.gif" align="absMiddle" border="0">
        <font size="2"><dhv:label name="accounts.accounts_fields.FolderHaveMultipleRecords">This folder can have
          multiple records</dhv:label></font>
      </td>
      <% } %>
    </tr>
  </table>
  <dhv:evaluate if="<%= !Category.isEmpty() %>">
    &nbsp;<br>
    <input type="hidden" name="actionType" value="update"/>
    <input type="hidden" name="recordId" value="<%=Category.getRecordId()%>"/>
    <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>"
           onClick="javascript:this.form.action='<portlet:actionURL/>'">
    <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>"
           onClick="javascript:this.form.action='<portlet:renderURL/>'"><br>
    &nbsp;<br><dhv:formMessage showSpace="false"/>
  </dhv:evaluate>
  <%
    Iterator groups = Category.iterator();
    while (groups.hasNext()) {
      CustomFieldGroup thisGroup = (CustomFieldGroup) groups.next();
  %>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
    <tr>
      <th colspan="2" valign="center">
        <strong><dhv:label name="portlets.folder.group">
          Group:</dhv:label></strong><%= StringUtils.toHtml(thisGroup.getName())%>
      </th>
    </tr>
    <%
      Iterator fields = thisGroup.iterator();
      if (fields.hasNext()) {
        while (fields.hasNext()) {
          CustomField thisField = (CustomField) fields.next();
          thisField.setRandomNumber(formName);
    %>
    <tr class="containerBody">
      <%-- Do not use toHtml() here, it's done by CustomField --%>
      <td valign="center" nowrap class="formLabel">
        <%= thisField.getNameHtml() %>
      </td>
      <td valign="center">
        <%= thisField.getHtmlElement(systemStatus) %> <font
              color="red"><%= (thisField.getRequired() ? "*" : "") %></font>
        <font color='#006699'><%= toHtml(thisField.getError()) %></font>
        <%= toHtml(thisField.getAdditionalText()) %>
      </td>
    </tr>
    <%
      }
    } else {
    %>
    <tr class="containerBody">
      <td colspan="2">
        <font color="#9E9E9E"><dhv:label name="accounts.accounts_fields.NoFieldsAvailable">No fields
          available.</dhv:label></font>
      </td>
    </tr>
    <%}%>
  </table>
  &nbsp;
  <%}%>
  <dhv:evaluate if="<%= !Category.isEmpty() %>">
    <br>
    <input type="hidden" name="actionType" value="update"/>
    <input type="hidden" name="beforeModifyTimeStamp" value="<%=beforeModifyTimeStamp%>"/>
    <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>"
           onClick="javascript:this.form.action='<portlet:actionURL/>'">
    <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>"
           onClick="javascript:this.form.action='<portlet:renderURL/>'">
  </dhv:evaluate>
  <%= addHiddenParams(request, "popup|popupType|actionId") %>
</form>