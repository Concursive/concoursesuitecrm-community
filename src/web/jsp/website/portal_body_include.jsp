<%@ page import="org.aspcfs.modules.website.base.*"%>
<%@ page import="org.apache.pluto.driver.core.PortalServletResponse"%>
<%@ page import="org.apache.pluto.driver.core.PortalEnvironment"%>
<%@ page import="org.apache.pluto.driver.url.PortalURL"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="portal" class="java.lang.String" scope="request"/>
<jsp:useBean id="site" class="org.aspcfs.modules.website.base.Site" scope="request"/>
<jsp:useBean id="rowsColumns" class="java.util.ArrayList" scope="request"/>
<%if (rowsColumns.size() > 0) { %>
<table cellpadding="0" cellspacing="0" width="100%" class="portalPortlets">
<%
    PageVersion pageVersion = site.getTabToDisplay().getThisPageToBuild().getPageVersionToView();
    Iterator nextIter = rowsColumns.iterator();
    Object nextObj = (Object) nextIter.next();
    Iterator rowColumnIterator = rowsColumns.iterator();
    int i = 1000;
    while (rowColumnIterator.hasNext()) {
      Object object = (Object) rowColumnIterator.next();
      if (nextIter.hasNext()) {
        nextObj = (Object) nextIter.next();
      } else {
        nextObj = null;
      }
      i++;
      if (object instanceof PageRow) {
        PageRow pageRow = (PageRow) object;
%>
        <dhv:evaluate if="<%= !"true".equals(portal) %>">
        <tr>
          <td class="portalRow" valign="top" colspan="<%= (pageRow.getPageVersionId() != -1? pageVersion.getPageRowList().getMaxColumns(): pageRow.getRowColumnList().size()) %>">
            <div class="portalEditorRow">
              Row: <a href="javascript:displayMenuRow('select<%= i %>','menuRow','<%= pageRow.getId() %>','<%= pageRow.getPageVersionId() %>','<%= pageRow.getRowColumnId() %>','<%= site.getId() %>','<%= site.getTabToDisplay().getId() %>','<%= site.getTabToDisplay().getThisPageToBuild().getId() %>');"
               onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuRow');">
               <img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
            </div>
          </td>
        </tr>
        </dhv:evaluate>
        <tr><td colspan="<%= (pageRow.getPageVersionId() != -1? pageVersion.getPageRowList().getMaxColumns(): pageRow.getRowColumnList().size()) %>" width="100%" valign="top"><table cellpadding="4" cellspacing="0" width="100%"><tr>
<%-- Row...<br /> --%>
<%
      } else if (object instanceof RowColumn) {
        RowColumn rowColumn = (RowColumn) object;
        Icelet thisIcelet = rowColumn.getIcelet();
        i++;
%>
        <td class="portalColumn" width="<%= (rowColumn.getParentRow().getTotalColumnWidth() != 0? String.valueOf((double)(rowColumn.getWidth() * 100)/(double)rowColumn.getParentRow().getTotalColumnWidth())+"%": String.valueOf(rowColumn.getWidth())) %>" valign="top">
          <dhv:evaluate if="<%= !"true".equals(portal) %>">
            <div class="portalEditorColumn">
            Column: <a href="javascript:displayMenuColumn('select<%= i %>','menuColumn','<%= rowColumn.getId() %>','<%= rowColumn.getParentRow().getId() %>','<%= rowColumn.getParentRow().getPageVersionId() %>','<%= site.getId() %>','<%= site.getTabToDisplay().getId() %>','<%= site.getTabToDisplay().getThisPageToBuild().getId() %>','<%= rowColumn.getIceletId() %>','<%= (rowColumn.getSubRows() != null && rowColumn.getSubRows().size() > 0) %>');"
            onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuColumn');">
              <img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
            </div>
          </dhv:evaluate>
<%
        if (thisIcelet != null) {
          PortalServletResponse portalResponse =
                  (PortalServletResponse) request.getAttribute("portal_response_" + rowColumn.getId());
          StringBuffer buffer = portalResponse.getInternalBuffer().getBuffer();
          pageContext.getOut().print(buffer.toString());
        } %>

<%      if(nextObj != null && nextObj instanceof PageRow) {
          PageRow nextPageRow = (PageRow) nextObj;
          int nextLevel = nextPageRow.getLevel();
          if (rowColumn.getLevel() >= nextLevel) {
            for (int count = 0; count < (rowColumn.getLevel() - nextLevel); count++) { %>
              </td></tr></table></td></tr></table>
<%          } %>
              </td></tr></table></td></tr>
<%        } else if (rowColumn.getLevel() < nextLevel) { %>
          <table cellpadding="4" cellspacing="0" width="100%">
<%        } %>
<%      } //Close the nextObj instanceOf PageRow
          else if (nextObj != null && nextObj instanceof RowColumn) {
          RowColumn nextRowColumn = (RowColumn) nextObj;
          int nextLevel = nextRowColumn.getLevel();
          if (rowColumn.getLevel() >= nextLevel) {
            for(int count = 0;count < (rowColumn.getLevel() - nextLevel); count++) { %>
              </td></tr></table></td></tr></table>
<%          } %>
          </td>
<%        } %>
<%      }  //Close nextObj instance of RowColumn
          else if (nextObj == null) {
            for (int count = 0; count <= rowColumn.getLevel(); count++) { %>
              </td></tr></table>
<%          } %>
            </td></tr>
<%      } //Close the nextObj == null
      } //Close object instanceof RowColumn
    } //Close the Iterator
%>
</table>
<%} else { %>
  <dhv:label name="">Page Rows or Columns do not exist</dhv:label>
<%} //Close if the rowsColumns is null
%>

