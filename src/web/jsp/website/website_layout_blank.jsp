<%@ page import="org.aspcfs.modules.website.base.*"%>
<%@ page import="org.apache.pluto.driver.core.PortalServletResponse"%>
<%@ page import="org.apache.pluto.driver.core.PortalEnvironment"%>
<%@ page import="org.apache.pluto.driver.url.PortalURL"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application" />
<jsp:useBean id="iceletManager" class="org.aspcfs.modules.website.framework.IceletManager" scope="application"/>
<jsp:useBean id="site" class="org.aspcfs.modules.website.base.Site" scope="request"/>
<jsp:useBean id="rowsColumns" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="tabId" class="java.lang.String" scope="request"/>
<jsp:useBean id="pageId" class="java.lang.String" scope="request"/>
<jsp:useBean id="portal" class="java.lang.String" scope="request"/>
<jsp:useBean id="layout" class="org.aspcfs.modules.website.base.Layout" scope="request"/>
<%@ include file="../initPage.jsp" %>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
<%
  int i = 0;
  Iterator tabIterator = site.getTabList().iterator();
  while (tabIterator.hasNext()) {
    Tab tab = (Tab) tabIterator.next();
    i++;
    if (tab.getId() == site.getTabToDisplay().getId()) {
%>
    <th valign="center" nowrap >
      <%-- Use the unique id for opening the menu, and toggling the graphics --%>
      <a href="<%= (portal != null && "true".equals(portal.trim()) ? "Portal.do?command=Default":"Sites.do?command=Details") %>&siteId=<%= site.getId() %>&tabId=<%= tab.getId() %><%= addLinkParams(request, "popup") %>"><%= toHtml(tab.getDisplayText()) %></a>
        <dhv:evaluate if="<%= portal == null || !"true".equals(portal) %>"><a href="javascript:displayMenuTab('select<%= i %>','menuTab','<%= tab.getId() %>','<%= site.getId() %>');"
           onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuTab');">
        <img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a></dhv:evaluate>
    </th>
<%} else {%>
    <td valign="center" nowrap >
      <%-- Use the unique id for opening the menu, and toggling the graphics --%>
      <a href="<%= (portal != null && "true".equals(portal.trim()) ? "Portal.do?command=Default":"Sites.do?command=Details") %>&siteId=<%= site.getId() %>&tabId=<%= tab.getId() %><%= addLinkParams(request, "popup") %>"><%= toHtml(tab.getDisplayText()) %></a>
        <dhv:evaluate if="<%= portal == null || !"true".equals(portal) %>"><a href="javascript:displayMenuTab('select<%= i %>','menuTab','<%= tab.getId() %>','<%= site.getId() %>');"
           onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuTab');">
        <img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a></dhv:evaluate>
    </td>
<% }} %>
  <td width="100%">&nbsp;</td>
</tr>
</table>
&nbsp;<br />
<table cellpadding="4" cellspacing="0" width="100%" class="details"><tr>
  <td width="100%" valign="top">
    <%@ include file="portal_body_include.jsp" %>
  </td>
<%-- the right pageGroup menu --%>
<dhv:evaluate if='<%= portal == null || !"true".equals(portal) || site.getTabToDisplay().getPageGroupList().canDisplay() %>'>
<td nowrap valign="top">
  <table cellpadding="0" cellspacing="0" width="100%" class="details">
<%
  // This is the current tab
  Iterator pageGroupIterator = site.getTabToDisplay().getPageGroupList().iterator();
  while (pageGroupIterator.hasNext()) {
    PageGroup pageGroup = (PageGroup) pageGroupIterator.next();
    i++;
%>
    <tr>
      <th nowrap>
        <%= toHtml(pageGroup.getName()) %>
        <dhv:evaluate if="<%= portal == null || !"true".equals(portal) %>"><a href="javascript:displayMenuPageGroup('select<%= i %>','menuPageGroup','<%= pageGroup.getId() %>','<%= site.getId() %>','<%= site.getTabToDisplay().getId() %>');"
           onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuPageGroup');">
           <img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a></dhv:evaluate>
      </th>
    </tr>
<%
    Iterator pageIterator = pageGroup.getPageList().iterator();
    while (pageIterator.hasNext()) {
      Page thisPage = (Page) pageIterator.next();
      i++;
%>
    <tr>
      <td nowrap>
        <a href="<%= (portal != null && "true".equals(portal.trim()) ? "Portal.do?command=Default":"Sites.do?command=Details") %>&siteId=<%= site.getId() %>&tabId=<%= site.getTabToDisplay().getId() %>&pageId=<%= thisPage.getId() %><%= addLinkParams(request, "popup") %>"><%= toHtml(thisPage.getName()) %></a>
          <dhv:evaluate if="<%= portal == null || !"true".equals(portal) %>"><a href="javascript:displayMenuPage('select<%= i %>','menuPage','<%= thisPage.getId() %>','<%= pageGroup.getId() %>','<%= site.getId() %>','<%= site.getTabToDisplay().getId() %>','<%= site.getTabToDisplay().getThisPageToBuild().getId() %>');"
           onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuPage');">
           <img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a></dhv:evaluate>
      </td>
    </tr>
<%  }
  }%>
    </table>
  </td>
</dhv:evaluate>
  </tr>
</table>

