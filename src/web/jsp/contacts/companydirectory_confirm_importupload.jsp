<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.text.DateFormat" %>
<jsp:useBean id="ImportDetails" class="org.aspcfs.modules.contacts.base.ContactImport" scope="request"/>
<%@ include file="../initPage.jsp" %>
<table class="trails">
<tr>
  <td>
    <a href="ExternalContacts.do">Contacts</a> >
    <a href="ExternalContactsImports.do?command=View">Import</a> >
    <a href="ExternalContactsImports.do?command=New">New Import</a> >
    Upload Complete
  </td>
</tr>
</table>
<%-- End Trails --%>
<table class="note">
  <tr><td><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/>Import has been saved but no records have yet been imported into the system.<br>&nbsp;&nbsp; To process the records now, use the "Process Now" button. To process it later use the "Process Later" button.</td></tr>
</table><br>
<%-- Import Details --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Details</strong>
    </th>
  </tr>
  <tr class="containerBody">
  <td class="formLabel" nowrap>
    Name
  </td>
  <td>
    <%= toString(ImportDetails.getName()) %>
  </td>
  </tr>
  <tr class="containerBody">
  <td valign="top" class="formLabel" nowrap>
    Description
  </td>
  <td>
    <%= toHtml(ImportDetails.getDescription()) %>
  </td>
  </tr>
  <tr class="containerBody">
  <td class="formLabel" nowrap>
    File
  </td>
  <td>
    <%= ImportDetails.getFile().getClientFilename() %>&nbsp;&nbsp;[ <a href="javascript:window.location.href='ExternalContactsImports.do?command=Download&importId=<%= ImportDetails.getId() %>&fid=<%= ImportDetails.getFile().getId() %>'">Download File</a> ]
  </td>
  </tr>
  <tr class="containerBody">
  <td class="formLabel" nowrap>
    File Size
  </td>
  <td>
    <%= ImportDetails.getFile().getRelativeSize() %> k&nbsp;
  </td>
  </tr>
  <tr class="containerBody">
  <td class="formLabel" nowrap>
    Status
  </td>
  <td>
    <%= ImportDetails.getStatusString() %> &nbsp;
  </td>
  </tr>
  <tr class="containerBody">
  <td class="formLabel" nowrap>
    Entered
  </td>
  <td>
    <dhv:username id="<%= ImportDetails.getEnteredBy() %>"/>
      -
      <dhv:tz timestamp="<%= ImportDetails.getEntered()  %>" dateFormat="<%= DateFormat.SHORT %>" timeFormat="<%= DateFormat.LONG %>"/>
  </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Modified
    </td>
    <td>
      <dhv:username id="<%= ImportDetails.getModifiedBy() %>"/>
      -
      <dhv:tz timestamp="<%= ImportDetails.getModified()  %>" dateFormat="<%= DateFormat.SHORT %>" timeFormat="<%= DateFormat.LONG %>"/>
    </td>
  </tr>
</table><br>
<input type="button" value="Process Now" onClick="javascript:window.location.href='ExternalContactsImports.do?command=InitValidate&importId=<%= ImportDetails.getId() %>';">&nbsp;
<input type="button" value="Process Later" onClick="javascript:window.location.href='ExternalContactsImports.do';">

