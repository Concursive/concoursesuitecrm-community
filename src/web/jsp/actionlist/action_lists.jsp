<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.actionlist.base.*, org.aspcfs.modules.base.Constants"%>
<jsp:useBean id="ActionLists" class="org.aspcfs.modules.actionlist.base.ActionLists" scope="request"/>
<jsp:useBean id="ActionListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<a href="MyCFS.do?command=Home">My Home Page</a> >
My Action Lists<br>
<hr color="#BFBFBB" noshade>
<a href="javascript:window.location.href='MyActionLists.do?command=Add&return=list&params=' + escape('filters=all|mycontacts|accountcontacts');">New Action List</a><br><br>
<table width="100%" border="0">
  <tr>
    <form name="listView" method="post" action="MyActionLists.do?command=List&linkModuleId=<%= Constants.ACTIONLISTS_CONTACTS %>">
    <td align="left">
      <select size="1" name="listView" onChange="javascript:document.forms[0].submit();">
        <option <%= ActionListInfo.getOptionValue("inprogress") %>>All In Progress Lists</option>
        <option <%= ActionListInfo.getOptionValue("complete") %>>All Complete Lists</option>
        <option <%= ActionListInfo.getOptionValue("all") %>>All Lists</option>
      </select>
    </td>
    <td>
      <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="ActionListInfo"/>
    </td>
    </form>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th rowspan="2" valign="middle">
      <strong>Action</strong>
    </th>
    <th rowspan="2" valign="middle" width="100%">
      <strong><a href="MyActionLists.do?command=List&linkModuleId=<%= Constants.ACTIONLISTS_CONTACTS %>&column=al.description">Name</a></strong>
      <%= ActionListInfo.getSortIcon("al.description") %>
    </th>
    <th colspan="2" align="center">
      <strong>Progress</strong>
    </th>
    <th rowspan="2" valign="middle" nowrap>
      <strong><a href="MyActionLists.do?command=List&linkModuleId=<%= Constants.ACTIONLISTS_CONTACTS %>&column=al.modified">Last Updated</a></strong>
      <%= ActionListInfo.getSortIcon("al.modified") %>
    </th>
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
  Iterator j = ActionLists.iterator();
  if ( j.hasNext() ) {
  int rowid = 0;
  while (j.hasNext()) {
      rowid = (rowid != 1 ? 1 : 2);
      ActionList thisList = (ActionList) j.next();
%>
  <tr class="row<%= rowid %>">
    <td>
      <a href="javascript:window.location.href='MyActionLists.do?command=Modify&id=<%= thisList.getId() %>';">Edit</a>|<a href="javascript:popURLReturn('MyActionLists.do?command=ConfirmDelete&id=<%= thisList.getId() %>&popup=true&linkModuleId=<%= toHtmlValue(request.getParameter("linkModuleId")) %>','MyActionLists.do?command=List', 'Delete_message','320','200','yes','no');">Del</a>
    </td>
    <td width="100%">
      <a href="MyActionContacts.do?command=List&actionId=<%= thisList.getId() %>&reset=true"><%= toHtmlValue(thisList.getDescription()) %></a>
    </td>
    <td nowrap align="center">
      <%= thisList.getTotalComplete() %>
    </td>
    <td nowrap align="center">
      <%= thisList.getTotal() %>
    </td>
    <td nowrap align="center">
      <%= thisList.getModifiedString() %>
    </td>
  </tr>
<%}
}else{%>
      <tr>
        <td class="containerBody" colspan="5" valign="center">
          No Action Lists found in this view.
        </td>
      </tr>
<%}%>
</table>
&nbsp;<br>
<dhv:pagedListControl object="ActionListInfo" tdClass="row1"/>

