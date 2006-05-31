<%@ taglib uri="/WEB-INF/portlet.tld" prefix="portlet" %>
<portlet:defineObjects/>
This is a portlet fragment called by VIEW, with the following info:<br />
Current Portlet Mode: <%= renderRequest.getPortletMode() %><br />
Current Window State: <%= renderRequest.getWindowState() %><br />
Namespace: <portlet:namespace/><br />
This is a portlet fragment called by EDIT.<br />
<br />
<portlet:renderURL var="url" portletMode="view" />
<a href="<%= pageContext.getAttribute("url") %>">Return to View Mode</a>
