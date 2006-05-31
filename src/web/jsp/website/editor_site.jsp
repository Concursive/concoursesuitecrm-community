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
<%@ page import="org.apache.pluto.driver.url.PortalURL"%>
<%@ page import="org.apache.pluto.driver.core.PortalEnvironment"%>
<%@ page import="org.aspcfs.modules.website.base.*"%>
<%@ page import="org.apache.pluto.driver.core.PortalServletResponse"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application" />
<jsp:useBean id="iceletManager" class="org.aspcfs.modules.website.framework.IceletManager" scope="application"/>
<jsp:useBean id="site" class="org.aspcfs.modules.website.base.Site" scope="request"/>
<jsp:useBean id="tabId" class="java.lang.String" scope="request"/>
<jsp:useBean id="pageId" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%
  PortalEnvironment portalEnvironment = PortalEnvironment.getPortalEnvironment(request);
  PortalURL portalURL = portalEnvironment.getRequestedPortalURL();
  // Does not work because the PortalServletRequest looks for PortalEnvironment
  //IceletEnvironment portalEnvironment = IceletEnvironment.getPortalEnvironment(request);
  //PortalURL portalURL = IceletURLFactory.getFactory().createPortalURL(request);
  Iterator tabIterator = site.getTabList().iterator();
  while (tabIterator.hasNext()) {
    Tab tab = (Tab) tabIterator.next();
%>
Tab: <%= toHtml(tab.getDisplayText()) %><br />
<%
    if (tab.getId() == Integer.parseInt(tabId)) {
      // This is the current tab
      Iterator pageGroupIterator = tab.getPageGroupList().iterator();
      while (pageGroupIterator.hasNext()) {
        PageGroup pageGroup = (PageGroup) pageGroupIterator.next();
%>
Page Group: <%= toHtml(pageGroup.getName()) %><br />
<%
        Iterator pageIterator = pageGroup.getPageList().iterator();
        while (pageIterator.hasNext()) {
          Page thisPage = (Page) pageIterator.next();
%>
Page: <%= toHtml(thisPage.getName()) %><br />
<%
          if (thisPage.getId() == Integer.parseInt(pageId)) {
            // This is the current page
            PageVersion pageVersion = thisPage.getPageVersionToView();
            Iterator pageRowIterator = pageVersion.getPageRowList().iterator();
            while (pageRowIterator.hasNext()) {
              PageRow pageRow = (PageRow) pageRowIterator.next();
%>
Row...<br />
<%
              Iterator rowColumnIterator = pageRow.getRowColumnList().iterator();
              while (rowColumnIterator.hasNext()) {
                RowColumn rowColumn = (RowColumn) rowColumnIterator.next();
                Icelet thisIcelet = rowColumn.getIcelet();
%>
Column...<%= thisIcelet.getConfiguratorClass() %><br />
<%
                PortalServletResponse portalResponse =
                  (PortalServletResponse) request.getAttribute("portal_response_" + rowColumn.getId());
                StringBuffer buffer = portalResponse.getInternalBuffer().getBuffer();
                pageContext.getOut().print(buffer.toString());
                pageContext.getOut().print("<br />");
              }
            }
          }
        }
      }
    }
  }
%>






