<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ include file="../initPage.jsp" %>
<a href="MyCFS.do?command=Home">My Home Page</a> >
<a href="MyActionLists.do">My Action Lists</a> >
Action Contacts<br>
<hr color="#BFBFBB" noshade>
<a href="MyActionContacts.do?command=Add">Add Contacts to List</a><br>
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="ContactActionListInfo"/>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td>
      <strong>Action</strong>
    </td>
    <td>
      <strong>Name</strong>
    </td>
    <td>
      <strong>Status</strong>
    </td>
    <td nowrap>
      <strong>Last Updated</strong>
    </td>
  </tr>
<%
  int rowid = 0;
  int i = 0;
  while (++i < 11) {
    rowid = (rowid != 1?1:2);
%>
  <tr class="row<%= rowid %>">
    <td>
      Edit|Del
    </td>
    <td width="100%">
      Contact Name <%= i %>
    </td>
    <td nowrap align="center">
      [Add Call] [Add Opportunity] [Add Account] [Add Ticket]
    </td>
    <td nowrap align="center">
      4/11/2003
    </td>
  </tr>
<%}%>
</table>
&nbsp;<br>
<dhv:pagedListControl object="ContactActionListInfo" tdClass="row1"/>

