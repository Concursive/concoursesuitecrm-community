<%-- Displays any global items from the GlobalItemsHook --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="GlobalItems" class="java.lang.String" scope="request"/>
<dhv:evaluate if="<%= GlobalItems.length() > 0 %>">
<td width="150" valign="top" id="rightcol">
  <%= GlobalItems %>
</td>
</dhv:evaluate>
