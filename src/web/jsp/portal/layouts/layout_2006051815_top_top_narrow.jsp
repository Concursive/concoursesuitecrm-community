<%--
- Copyright 2006 Dark Horse Ventures, LLC
-
- Author(s): Matt Rajkowski
- Version: $Id: Exp $
- Description: Centric CRM Portal Layout
--%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ include file="../../initPage.jsp" %>
<c:set var="site" value="${site}"/>
<jsp:useBean id="site" type="org.aspcfs.modules.website.base.Site" />
<jsp:useBean id="portal" class="java.lang.String" scope="request"/>
<jsp:useBean id="viewType" class="java.lang.String" scope="request"/>
<center>
<%-- Login Link --%>
<dhv:evaluate if="<%= "true".equals(portal)  %>">
<table width="768" border="0" cellpadding="0" cellspacing="0" class="portalLogin">
	<tr>
    <td>
      <a href="Login.do?command=Default">Login</a>
    </td>
	</tr>
</table>
</dhv:evaluate>
<%-- Logo at top left --%>
<c:if test="${site.logoImageId > -1}">
<table border="0" cellpadding="0" cellspacing="0" width="768" class="portalLogo" style="padding-bottom: 6px;">
	<tr>
    <td width="8" nowrap>&nbsp;</td>
    <td>
      <dhv:fileItemImage id="<%= site.getLogoImageId() %>" path="website" name="Logo"/>
			<dhv:evaluate if="<%= !"true".equals(portal) %>">
		  	[<a href="Sites.do?command=UpdateLogo&logoImageId=-1&popup=true&siteId=<%=site.getId()%>">Remove</a>]
			</dhv:evaluate>
		</td>
	</tr>
</table>
</c:if>
<%-- Tabs across the top --%>
<table border="0" cellpadding="0" cellspacing="0" width="768" class="portalTabs">
  <tr>
    <td width="8" class="portalTabSpace" nowrap>&nbsp;</td>
    <c:forEach items="${site.tabList}" var="tab" varStatus="status">
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
      <c:if test="${status.last}">
        <c:set var="tabListCount" value="${status.count}"/>
      </c:if>
    </c:forEach>
     <td width="100%" class="portalTabSpace">&nbsp;</td>
  </tr>
</table>
<%-- PageGroup under the tabs --%>
<table border="0" cellpadding="0" cellspacing="0" width="768" class="portalTabs" style="padding-bottom: 6px;">
  <c:set var="pageGroupList" value="${site.tabToDisplay.pageGroupList}"/>
  <jsp:useBean id="pageGroupList" type="org.aspcfs.modules.website.base.PageGroupList" />
  <tr>
    <td width="8" class="portalTabBackground" nowrap>&nbsp;</td>
    <td colspan="<c:out value="${tabListCount}"/>" class="portalTabBackground" nowrap>
      <c:forEach items="${site.tabToDisplay.pageGroupList}" var="pageGroup">
        <c:set var="pageGroup" value="${pageGroup}"/>
        <dhv:evaluate if="<%= site.getTabToDisplay().getPageGroupList().size() > 1 || (!"true".equals(portal) && org.aspcfs.modules.website.base.Site.CONFIGURE.equals(viewType))%>">
          <dhv:portalPageGroupURL />:
        </dhv:evaluate>
        <jsp:useBean id="pageGroup" type="org.aspcfs.modules.website.base.PageGroup" />
        <dhv:evaluate if="<%= site.getTabToDisplay().getPageGroupList().size() > 1 || pageGroup.getPageList().size() > 1 %>">
          <c:forEach items="${pageGroup.pageList}" var="page">
            <c:set var="page" value="${page}"/>
            <dhv:portalPageURL />
          </c:forEach>
        </dhv:evaluate>
      </c:forEach>&nbsp;
    </td>
    <td width="100%" class="portalTabBackground" nowrap>&nbsp;</td>
  </tr>
</table>
<%-- Portlets --%>
<table border="0" cellpadding="0" cellspacing="0" width="768" class="portalBody" style="padding-left:8px;padding-right:6px">
  <tr>
    <td class="portalPortlets" nowrap valign="top">
      <jsp:include page="../../website/portal_body_include.jsp" flush="true"/>
    </td>
  </tr>
</table>
</center>