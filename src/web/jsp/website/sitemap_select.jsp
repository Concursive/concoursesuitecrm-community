<%@ page import="org.aspcfs.modules.website.base.Tab" %>
<%@ page import="org.aspcfs.modules.website.base.PageGroup" %>
<%@ page import="org.aspcfs.modules.website.base.Page" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="siteMap" class="org.aspcfs.modules.website.base.Site" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script type="text/javascript">
  function forwardLink() {
    alert('info');
  }
</script>
<%
Iterator i = siteMap.getTabList().iterator();
while (i.hasNext()) {
  Tab tab = (Tab) i.next();
%>
  <%= toHtml(tab.getDisplayText()) %><br />
<%
  Iterator pg = tab.getPageGroupList().iterator();
  while (pg.hasNext()) {
    PageGroup pageGroup = (PageGroup) pg.next();
%>
    &nbsp;<%= toHtml(pageGroup.getName()) %><br />
<%
    Iterator pgI = pageGroup.getPageList().iterator();
    while (pgI.hasNext()) {
      Page webpage = (Page) pgI.next();
      boolean isAlreadyAlias = (webpage.getAlias() > 0);
%>
      <dhv:evaluate if="<%= !isAlreadyAlias %>">
        &nbsp;&nbsp;<a href="javascript:window.opener.setAlias('<%= webpage.getId() %>');window.close();"><%= toHtml(webpage.getName()) %></a><br />
      </dhv:evaluate>
<%     
    }
  }
}
%>