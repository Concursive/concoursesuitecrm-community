<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="contact_list_menu.html" %>
<%-- Preload image rollovers for drop-down menu --%>
<script language="JavaScript" type="text/javascript">
  loadImages('select');
</script>
<a href="MyCFS.do?command=Home">My Home Page</a> >
<a href="MyActionLists.do">My Action Lists</a> >
Action Contacts<br>
<hr color="#BFBFBB" noshade>
<a href="MyActionContacts.do?command=Add">Add Contacts to List</a><br>
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="ContactActionListInfo"/>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td align="center">
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
  int i = 0; //Temporary unique id for demonstration
  while (++i < 11) {
    rowid = (rowid != 1?1:2);
%>
  <tr class="row<%= rowid %>">
    <td>
      <%-- Use the unique id for opening the menu, and toggling the graphics --%>
      <%-- To display the menu, pass the actionId and the contact Id--%>
      <a href="javascript:displayMenu('menuContact',1,<%= i %>)"
         onMouseOver="over(0, <%= i %>)"
         onmouseout="out(0, <%= i %>)"><img 
        src="images/select.gif" name="select<%= i %>" align="absmiddle" border="0"></a>
    </td>
    <td nowrap>
      <img src="images/box.gif" align="absmiddle" border="0">
      Contact Name <%= i %>
    </td>
    <td width="100%">
      History
    </td>
    <td nowrap align="center">
      4/11/2003
    </td>
  </tr>
<%}%>
</table>
&nbsp;<br>
<dhv:pagedListControl object="ContactActionListInfo" tdClass="row1"/>

