<%--
- Copyright 2006 Dark Horse Ventures, LLC
-
- Author(s): Matt Rajkowski
- Version: $Id: Exp $
- Description: Centric CRM Portal Layout
--%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="portal" class="java.lang.String" scope="request"/>
<%@ include file="../../initPage.jsp" %>
<%-- Logo at top left --%>
<c:if test="${site.logoImageId != -1}">
<table border="0" cellpadding="0" cellspacing="0" width="100%" class="portalLogo">
	<tr>
		<td>
      <c:set var="site" value="${site}"/>
      <jsp:useBean id="site" type="org.aspcfs.modules.website.base.Site" />
      <dhv:fileItemImage id="<%= site.getLogoImageId() %>" path="website" name="Logo"/>
			<dhv:evaluate if="<%= isPopup(request) %>">
		  	[<a href="Sites.do?command=UpdateLogo&logoImageId=-1&popup=true&siteId=<%=site.getId()%>">Remove</a>]
			</dhv:evaluate>
		</td>
	</tr>
</table>
</c:if>
<%-- Tabs across the top --%>
<table border="0" cellpadding="0" cellspacing="0" width="100%" class="portalTabs">
  <tr>
    <td width="8" class="portalTabSpace" nowrap>&nbsp;</td>
    <c:forEach items="${site.tabList}" var="tab">
      <c:set var="tab" value="${tab}"/>
      <jsp:useBean id="tab" type="org.aspcfs.modules.website.base.Tab" />
      <c:choose>
        <c:when test="${tab.id == site.tabToDisplay.id}">
          <th class="portalTabActive" nowrap><dhv:portalTabURL /></th>
        </c:when>
        <c:otherwise>
          <td class="portalTabInactive" nowrap><dhv:portalTabURL /></td>
        </c:otherwise>
      </c:choose>
    </c:forEach>
    <td width="100%" class="portalTabSpace">&nbsp;</td>
  </tr>
</table>
<%-- Page group and Portlets --%>
<table border="0" cellpadding="0" cellspacing="0" width="100%" class="portalBody">
  <tr>
    <c:set var="pageGroupList" value="${site.tabToDisplay.pageGroupList}"/>
    <jsp:useBean id="pageGroupList" type="org.aspcfs.modules.website.base.PageGroupList" />
    <dhv:evaluate if="<%= pageGroupList.canDisplay() || !"true".equals(portal) %>">
      <%-- PageGroup along the left --%>
      <td width="200" valign="top" class="portalPageGroupsColumn">
        <table cellpadding="0" cellspacing="0" width="100%" class="portalPageGroups">
          <c:forEach items="${site.tabToDisplay.pageGroupList}" var="pageGroup">
            <c:set var="pageGroup" value="${pageGroup}"/>
            <tr>
              <th class="portalPageGroup" nowrap>
                <dhv:portalPageGroupURL />
              </th>
            </tr>
            <c:forEach items="${pageGroup.pageList}" var="page">
              <tr>
                <c:choose>
                  <c:when test="${page.id == site.tabToDisplay.pageToBuild}">
                    <td class="portalPageActive" nowrap><dhv:portalPageURL /></td>
                  </c:when>
                  <c:otherwise>
                    <td class="portalPageInactive" nowrap><dhv:portalPageURL /></td>
                  </c:otherwise>
                </c:choose>
              </tr>
            </c:forEach>
          </c:forEach>
        </table>
      </td>
    </dhv:evaluate>
    <%-- Portlets --%>
    <td class="portalPortlets" nowrap valign="top">
      <jsp:include page="../../website/portal_body_include.jsp" flush="true"/>
    </td>
  </tr>
</table>
