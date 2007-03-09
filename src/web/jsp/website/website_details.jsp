<%--
- Copyright(c) 2006 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
- rights reserved. This material cannot be distributed without written
- permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
- this material for internal use is hereby granted, provided that the above
- copyright notice and this permission notice appear in all copies. DARK HORSE
- VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
- IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
- IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
- PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
- INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
- EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
- ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
- DAMAGES RELATING TO THE SOFTWARE.
-
- Version: $Id: Exp$
- Description:
--%>
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
<jsp:useBean id="viewType" class="java.lang.String" scope="request"/>
<jsp:useBean id="layout" class="org.aspcfs.modules.website.base.Layout" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" type="text/javascript">
//initialize common global variables
  var thisSiteId = -1;
  var thisTabId = -1;
  var thisPageGroupId = -1;
  var thisTabBannerId = -1;
  var thisPageId = -1;
  var thisPageVersionId = -1;
  var thisPageRowId = -1;
  var thisRowColumnId = -1;
  var menu_init = false;
</SCRIPT>
<dhv:evaluate if='<%= !"true".equals(portal) %>'>
<%@ include file="website_tab_menu.jsp" %>
<%@ include file="website_page_group_menu.jsp" %>
<%@ include file="website_page_menu.jsp" %>
<%@ include file="website_row_menu.jsp" %>
<%@ include file="website_column_menu.jsp" %>
</dhv:evaluate>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/div.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" type="text/javascript">
<%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
  function reopen() {
    window.location.href='Sites.do?command=Details&siteId=<%= site.getId() %>&tabId=<%= site.getTabToDisplay().getId() %>&pageId=<%= site.getTabToDisplay().getThisPageToBuild().getId() %>&popup=true';
  }
  function reopenWebsiteDetails(tabId, pageId) {
    window.location.href='Sites.do?command=Details&siteId=<%= site.getId() %>&tabId='+tabId+'&pageId='+pageId+'&popup=true';
  }
</script>
<!-- Trails -->
<dhv:evaluate if="<%= isPopup(request) %>">
<table class="trails" cellspacing="0" width="100%">
  <tr>
    <td nowrap>
      <a href="Sites.do?command=List&popup=true"><dhv:label name="">Sites</dhv:label></a> >
      <dhv:label name="">Sites</dhv:label>
    </td>
    <td align="right" nowrap>
      Options:
      <dhv:evaluate if="<%=Site.CONFIGURE.equals(viewType)%>">
        <a href="Sites.do?command=Details&siteId=<%=site.getId()%>&popup=true&viewType=<%=Site.PREVIEW%> "><dhv:label name="website.preview">Preview</dhv:label></a>
      </dhv:evaluate>
      <dhv:evaluate if="<%=Site.PREVIEW.equals(viewType)%>">
        <a href="Sites.do?command=Details&siteId=<%=site.getId()%>&popup=true&viewType=<%=Site.CONFIGURE%>"><dhv:label name="website.edit">Edit</dhv:label></a>
      </dhv:evaluate>
        <a href="javascript:popURL('WebsiteMedia.do?command=View&siteId=<%=site.getId()%>&popup=true','Add_Logo',600,400,'yes','yes');">Choose Site Logo</a>
        <a href="javascript:popURL('Sites.do?command=ViewLayouts&siteId=<%=site.getId()%>&popup=true','Choose_Layout',600,400,'yes','yes');">Choose Layout</a>
        <a href="javascript:popURL('Sites.do?command=ViewStyles&siteId=<%=site.getId()%>&popup=true','Choose_Style',600,400,'yes','yes');">Choose Style</a>
    </td>
  </tr>
</table>
</dhv:evaluate>
<!-- End Sub Trails -->
<%
  PortalEnvironment portalEnvironment = PortalEnvironment.getPortalEnvironment(request);
  PortalURL portalURL = portalEnvironment.getRequestedPortalURL();
  String theLayout = "../portal/layouts/" + layout.getJsp();
%>
<jsp:include page="<%= theLayout %>" flush="true"/>
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
