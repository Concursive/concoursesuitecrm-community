<%-- Displays any global items from the GlobalItemsHook --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="GlobalItems" class="java.lang.String" scope="request"/>
<%= GlobalItems %>
