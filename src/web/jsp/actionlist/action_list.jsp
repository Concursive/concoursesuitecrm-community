<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ include file="../initPage.jsp" %>
<a href="MyCFS.do?command=Home">My Home Page</a> >
My Action Lists<br>
<hr color="#BFBFBB" noshade>
<a href="MyActionLists.do?command=Add">New Action List</a><br>
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="ActionListInfo"/>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td rowspan="2" valign="middle">
      <strong>Action</strong>
    </td>
    <td rowspan="2" valign="middle">
      <strong>Name</strong>
    </td>
    <td colspan="2" align="center">
      <strong>Progress</strong>
    </td>
    <td rowspan="2" valign="middle" nowrap>
      <strong>Last Updated</strong>
    </td>
  </tr>
  <tr class="title">
    <td>
      <strong>Complete</strong>
    </td>
    <td>
      <strong>Total</strong>
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
      <a href="MyActionContacts.do?command=List">List Name <%= i %></a>
    </td>
    <td nowrap align="center">
      5
    </td>
    <td nowrap align="center">
      10
    </td>
    <td nowrap align="center">
      4/11/2003
    </td>
  </tr>
<%}%>
</table>
&nbsp;<br>
<dhv:pagedListControl object="ActionListInfo" tdClass="row1"/>

