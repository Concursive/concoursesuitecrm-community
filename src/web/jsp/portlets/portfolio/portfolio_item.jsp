<%@ page import="org.aspcfs.utils.StringUtils"%>
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
- Version: $Id:  $
- Description:
--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/portlet.tld" prefix="portlet" %>
<jsp:useBean id="introduction" class="java.lang.String" scope="request"/>
<jsp:useBean id="portfolioItem" class="org.aspcfs.modules.website.base.PortfolioItem" scope="request"/>
<%@ include file="../../initPage.jsp" %>
<%@ include file="../../initPopupMenu.jsp" %>
<portlet:defineObjects/>
<dhv:evaluate if="<%= StringUtils.hasText(introduction) %>">
  <table cellpadding="0" cellspacing="0" border="0" width="100%">
    <tr>
      <td>
        <%= StringUtils.toHtml(introduction) %>
      </td>
    </tr>
  </table>
  <br />
</dhv:evaluate>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <th>
			 <dhv:pagedListStatus title="<%= StringUtils.toHtml(portfolioItem.getName()) %>" object="portfolioItemListInfo"/>
    </th>
  </tr>
  <tr>
    <td style="text-align:left;">
      <portlet:renderURL portletMode="view" var="url">
        <portlet:param name="viewType" value="summary"/>
        <portlet:param name="categoryId" value="<%= String.valueOf(portfolioItem.getCategoryId()) %>"/>
      </portlet:renderURL>
      [<a href="<%= pageContext.getAttribute("url") %>">Back to Items</a>]
    </td>
  </tr>
  <tr>
    <td colspan="2">
       <%= StringUtils.toHtml(portfolioItem.getDescription()) %><br /><br />
       <dhv:fileItemImage id="<%=  portfolioItem.getImageId() %>" path="portfolioitem" thumbnail="false" name="<%=  portfolioItem.getName() %>" /><br />
       <%= StringUtils.toHtml(portfolioItem.getCaption()) %>
    </td>
  </tr>
</table>
