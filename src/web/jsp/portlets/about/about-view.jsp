<%@ taglib uri="/WEB-INF/portlet.tld" prefix="portlet" %>
<portlet:defineObjects/>
This is a portlet fragment called by VIEW, with the following info:<br />
Current Portlet Mode: <%= renderRequest.getPortletMode() %><br />
Current Window State: <%= renderRequest.getWindowState() %><br />
Namespace: <portlet:namespace/><br />

<portlet:renderURL var="editUrl" portletMode="edit">
  <portlet:param name="itemId" value="1"/>
</portlet:renderURL>
<a href="<%= pageContext.getAttribute("editUrl") %>">Show Edit Mode</a>

<portlet:renderURL var="helpUrl" portletMode="help">
  <portlet:param name="itemId" value="2"/>
</portlet:renderURL>
<a href="<%= pageContext.getAttribute("helpUrl") %>">Show Help Mode</a>

