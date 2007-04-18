<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.admin.base.*,com.zeroio.iteam.base.*" %>
<%@ page import="org.aspcfs.utils.StringUtils" %>
<%@ page import="org.aspcfs.utils.CurrencyFormat" %>
<jsp:useBean id="thisUser" class="org.aspcfs.modules.admin.base.User" scope="request"/>
<jsp:useBean id="groupList" class="org.aspcfs.modules.admin.base.UserGroupList" scope="request"/>
<jsp:useBean id="groupUsersListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js?1"></SCRIPT>
<script language="javascript">
  function submitPage(headerId, displayValue, hiddenFieldId, displayFieldId) {
    opener.setParentHiddenField(hiddenFieldId, headerId);
    opener.changeDivContent(displayFieldId, displayValue);
    self.close();
  }
</script>
&nbsp;<br />
<center><%= groupUsersListInfo.getAlphabeticalPageLinks() %></center>
&nbsp;<br />
<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="groupUsersListInfo"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th colspan="4" width="100%">
      <strong><dhv:label name="usergroup.userGroupList">User Group List</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBack">
    <td nowrap>
      <strong><dhv:label name="admin.groupName">Group Name</dhv:label></strong>
    </td>
    <td nowrap>
      <strong><dhv:label name="accounts.accounts_calls_list.Entered">Entered</dhv:label></strong>
    </td>
    <dhv:evaluate if="<%= !groupList.getExclusiveToSite() %>">
      <td norwap><strong><dhv:label name="accounts.site">Site</dhv:label></strong></td>
    </dhv:evaluate>
    <td nowrap>
      <strong><dhv:label name="dependency.usersingroup">Users</dhv:label></strong>
    </td>
  </tr>
<%
	Iterator j = (Iterator) groupList.iterator();
	if ( groupList.size() > 0 ) {
		int rowid = 0;
    int i = 0;
	    while (j.hasNext()) {
        i++;
		    rowid = (rowid !=1?1:2);
        UserGroup group = (UserGroup)j.next();
%>      
  <tr class="row<%= rowid %>">
    <td valign="top" width="100%">
      <a href="javascript:submitPage('<%= group.getId() %>', '<%= StringUtils.jsStringEscape(group.getName()) %>', '<%= request.getAttribute("hiddenFieldId") %>','<%= request.getAttribute("displayFieldId") %>');"><%= toHtml(group.getName()) %></a>
    </td>
    <td valign="top" nowrap>
      <zeroio:tz timestamp="<%= group.getEntered() %>" timeZone="<%= User.getTimeZone() %>" dateFormat="<%= DateFormat.SHORT %>" timeFormat="<%= DateFormat.LONG %>"/>
    </td>
    <dhv:evaluate if="<%= !groupList.getExclusiveToSite() %>">
      <td valign="top" nowrap><%= SiteIdList.getSelectedValue(group.getSiteId()) %></td>
    </dhv:evaluate>
    <td valign="top" norwap>
      <%= group.getGroupUsers() != null? String.valueOf(group.getGroupUsers().size()):"" %>
    </td>
  </tr>
<%}%>
<%} else {%>
  <tr class="containerBody">
    <td colspan="<%= !groupList.getExclusiveToSite() ?"4":"3" %>">
      <dhv:label name="usergroups.noUserGroupsFound.txt">No user groups found.</dhv:label>
    </td>
  </tr>
<%}%>
</table>
<br>
<dhv:pagedListControl object="groupUsersListInfo"/>
<input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:self.close();"/>
</td>
</tr>
</table>
<br />

