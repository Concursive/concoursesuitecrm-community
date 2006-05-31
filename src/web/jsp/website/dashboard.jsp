<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
  - Version: $Id: $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*, java.sql.*" %>
<%@ page import="org.aspcfs.modules.website.base.*,org.aspcfs.utils.web.*" %>
<%@ page import="com.zeroio.iteam.base.*,org.aspcfs.modules.login.beans.*,org.aspcfs.controller.*" %>
<%@ page import="org.aspcfs.modules.admin.base.User" %>
<jsp:useBean id="moduleId" class="java.lang.String" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application" />
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
  <tr>
    <td>
      <a href="Website.do"><dhv:label name="Website" mainMenuItem="true">Website</dhv:label></a> >
      <dhv:label name="communications.campaign.Dashboard">Dashboard</dhv:label>
    </td>
  </tr>
</table>
<%-- End Trails --%>
<table width="100%" border="0" cellspacing="0" cellpadding="3">
  <tr>
    <td width="100%" valign="top">
      <%-- Graphic --%>
      <table width="100%" cellpadding="2" cellspacing="0" border="0" class="pagedList">
        <%-- Graph Header --%>
        <tr>
          <th valign="top" style="text-align: center;" nowrap colspan="4">
            <dhv:label name="communications.campaign.Dashboard">Dashboard</dhv:label>
          </th>
        </tr>
        <%-- Graph Columns --%>
        <tr class="row1">
          <td><strong><dhv:label name="accounts.accounts_revenue_add.Month">Month</dhv:label></strong></td>
          <td><strong><dhv:label name="website.visitors">Visitors</dhv:label></strong></td>
          <td><strong><dhv:label name="sales.leads">Leads</dhv:label></strong></td>
          <td><strong><dhv:label name="project.accounts">Accounts</dhv:label></strong></td>
        </tr>
        <tr>
          <td colspan="4">TODO: Add the dynamic functionality to display data per month</td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<table class="note" cellspacing="0">
  <tr>
    <th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
    <td><dhv:label name="website.dashboard.note.text">Manage your web presence by configuring the following items. 
    Once the items are configured, they can be used in your public website.</dhv:label></td>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="empty">
<dhv:permission name="site-editor-view">
  <tr>
    <td><strong><a href="javascript:popURL('Sites.do?command=List&popup=true','Sites','800','600','yes','yes');"><dhv:label name="website.editor.setupPublicWebsite">Setup Public Website</dhv:label></a></strong></td>
  </tr>
</dhv:permission>
  <tr>
    <td><strong><a href="javascript:alert('This takes the user to setup business locations');"><dhv:label name="website.locations.setupBusinessLocations">Setup Business Locations</dhv:label></a></strong></td>
  </tr>
  <dhv:permission name="website-portfolio-view"><tr>
    <td><strong><a href="PortfolioEditor.do?command=List"><dhv:label name="website.portfolio.setupExamplesOfPastWork">Setup Examples of Past Work</dhv:label></a></strong></td>
  </tr></dhv:permission>
  <dhv:permission name="admin-sysconfig-products-view"><tr>
    <td><strong><a href="ProductCatalogEditor.do?command=List&moduleId=<%= moduleId %>"><dhv:label name="website.products.setupProductsAndServices">Setup Products and Services</dhv:label></a></strong></td>
  </tr></dhv:permission>
  <tr>
    <td><strong><a href="javascript:alert('This takes the user to the coupon editor');"><dhv:label name="">Setup Coupons</dhv:label></a></strong></td>
  </tr>
  <tr>
    <td><strong><a href="WebsiteMedia.do?command=View">Setup Media</a></strong></td>
  </tr>
</table>

