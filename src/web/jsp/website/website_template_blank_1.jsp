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
      <a href="Sites.do?command=Details&siteId=<%= site.getId() %>&tabId=<%= tab.getId() %><%= addLinkParams(request, "popup") %>"><%= toHtml(tab.getDisplayText()) %></a>
        <dhv:evaluate if="<%= isPopup(request) %>"><a href="javascript:displayMenuTab('select<%= i %>','menuTab','<%= tab.getId() %>','<%= site.getId() %>');"
           onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuTab');">
        <img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a></dhv:evaluate>
    </th>
<%} else {%>
    <td valign="center" nowrap >
      <%-- Use the unique id for opening the menu, and toggling the graphics --%>
      <a href="Sites.do?command=Details&siteId=<%= site.getId() %>&tabId=<%= tab.getId() %><%= addLinkParams(request, "popup") %>"><%= toHtml(tab.getDisplayText()) %></a>
        <dhv:evaluate if="<%= isPopup(request) %>"><a href="javascript:displayMenuTab('select<%= i %>','menuTab','<%= tab.getId() %>','<%= site.getId() %>');"
           onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuTab');">
        <img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a></dhv:evaluate>
    </td>
<% }} %>
  <td width="100%">&nbsp;</td>
</tr>
</table>
&nbsp;<br />
<table cellpadding="4" cellspacing="0" width="100%"<dhv:evaluate if="<%= isPopup(request) %>"> class="details"</dhv:evaluate>>
<tr><td width="100%" valign="top">
<% if (pageToDisplay != null && pageToDisplay.getPageVersionToView() != null) { %>
<table cellpadding="0" cellspacing="0" width="100%" <dhv:evaluate if="<%= isPopup(request) %>"> class="empty"</dhv:evaluate>>
<%
    // This is the current page
    PageVersion pageVersion = pageToDisplay.getPageVersionToView();
    if (pageVersion != null && pageVersion.getPageRowList() != null) {
      Iterator pageRowIterator = pageVersion.getPageRowList().iterator();
      while (pageRowIterator.hasNext()) {
        PageRow pageRow = (PageRow) pageRowIterator.next();
        i++;
%>
<dhv:evaluate if="<%= isPopup(request) %>">
<tr>
  <th colspan="<%= pageVersion.getPageRowList().getMaxColumns() %>">
    <div align="right">
      <a href="javascript:displayMenuRow('select<%= i %>','menuRow','<%= pageRow.getId() %>','<%= pageVersion.getId() %>','<%= site.getId() %>','<%= site.getTabToDisplay().getId() %>','<%= site.getTabToDisplay().getThisPageToBuild().getId() %>');"
       onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuRow');">
       <img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
    </div>
  </th>
</tr>
</dhv:evaluate>
<tr>
<%-- Row...<br /> --%>
<%
      Iterator rowColumnIterator = pageRow.getRowColumnList().iterator();
      while (rowColumnIterator.hasNext()) {
        RowColumn rowColumn = (RowColumn) rowColumnIterator.next();
        Icelet thisIcelet = rowColumn.getIcelet();
        i++;
%>
        <td width="<%= rowColumn.getWidth() %>" valign="top">
          <dhv:evaluate if="<%= isPopup(request) %>">
          <a href="javascript:displayMenuColumn('select<%= i %>','menuColumn','<%= rowColumn.getId() %>','<%= pageRow.getId() %>','<%= pageRow.getPageVersionId() %>','<%= site.getId() %>','<%= site.getTabToDisplay().getId() %>','<%= site.getTabToDisplay().getThisPageToBuild().getId() %>','<%= rowColumn.getIceletId() %>');"
          onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuColumn');">
            <img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a><br />
            <dhv:evaluate if="<%= rowColumn.getIceletId() == -1 || thisIcelet == null %>">
              <dhv:label name="">Please add an Icelet</dhv:label>
            </dhv:evaluate>
          </dhv:evaluate>
<%
        if (thisIcelet != null) {
          PortalServletResponse portalResponse =
                  (PortalServletResponse) request.getAttribute("portal_response_" + rowColumn.getId());
          StringBuffer buffer = portalResponse.getInternalBuffer().getBuffer();
          pageContext.getOut().print(buffer.toString());
        }
%>
      </td>
<%  }
    if (pageRow.getRowColumnList().size() == 0) {%>
      <td>Blank Column</td>
    <%}%>
    </tr>
    <tr><td>&nbsp;</td></tr>
<%} }%>
  </table>
<%}%>
</td>
<%-- the right pageGroup menu --%>
<dhv:evaluate if="<%= isPopup(request) || tabToDisplay.getPageGroupList().canDisplay() %>">
<td nowrap valign="top">
  <table cellpadding="0" cellspacing="0" width="100%" class="details">
<%
  // This is the current tab
  Iterator pageGroupIterator = tabToDisplay.getPageGroupList().iterator();
  while (pageGroupIterator.hasNext()) {
    PageGroup pageGroup = (PageGroup) pageGroupIterator.next();
    i++;
%>
    <tr>
      <th nowrap>
        <%= toHtml(pageGroup.getName()) %>
        <dhv:evaluate if="<%= isPopup(request) %>"><a href="javascript:displayMenuPageGroup('select<%= i %>','menuPageGroup','<%= pageGroup.getId() %>','<%= site.getId() %>','<%= site.getTabToDisplay().getId() %>');"
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
        <a href="Sites.do?command=Details&siteId=<%= site.getId() %>&tabId=<%= site.getTabToDisplay().getId() %>&pageId=<%= thisPage.getId() %><%= addLinkParams(request, "popup") %>"><%= toHtml(thisPage.getName()) %></a>
          <dhv:evaluate if="<%= isPopup(request) %>"><a href="javascript:displayMenuPage('select<%= i %>','menuPage','<%= thisPage.getId() %>','<%= pageGroup.getId() %>','<%= site.getId() %>','<%= site.getTabToDisplay().getId() %>','<%= site.getTabToDisplay().getThisPageToBuild().getId() %>');"
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

