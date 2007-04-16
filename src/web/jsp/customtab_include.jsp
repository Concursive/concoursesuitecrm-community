<%-- 
  - Copyright(c) 2007 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
  - Version: $Id: admin_fields_folder_list.jsp 11310 2005-04-13 20:05:00Z mrajkowski $
  - Description: 
  --%>

<%@ page import="org.aspcfs.modules.website.base.*"%>
<%@ page import="org.apache.pluto.driver.core.PortalServletResponse"%>
<%@ page import="java.util.*"%>
<%@ page import="org.aspcfs.taglib.ContainerMenuClass"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ include file="initPopupMenu.jsp"%>
<jsp:useBean id="rowsColumns" class="java.util.ArrayList" 	scope="request" />
<jsp:useBean id="Page" class="org.aspcfs.modules.website.base.Page" scope="request" />
<jsp:useBean id="dhvcontainers" class="java.util.Stack" scope="request" />

<%-- Trails --%>
<%@ include file="customtabs_trails_include.jsp" %>
<%-- End Trails --%>
<dhv:containers containersstack='<%= dhvcontainers%>'>

<table cellpadding="0" cellspacing="0" width="100%" class="portalPortlets">
		<%
				PageVersion pageVersion = Page.getPageVersionToView();
				Iterator nextIter = rowsColumns.iterator();
				Object nextObj = (Object) nextIter.next();
				Iterator rowColumnIterator = rowsColumns.iterator();
				int pb_i = 1000;
				while (rowColumnIterator.hasNext()) {
					Object object = (Object) rowColumnIterator.next();
					if (nextIter.hasNext()) {
						nextObj = (Object) nextIter.next();
					} else {
						nextObj = null;
					}
					pb_i++;
					if (object instanceof PageRow) {
					  PageRow pageRow = (PageRow) object; %>
						<tr>
							<td colspan="<%= (pageRow.getPageVersionId() != -1? pageVersion.getPageRowList().getMaxColumns(): pageRow.getRowColumnList().size()) %>"
								width="100%" valign="top">
								<table cellpadding="4" cellspacing="0" width="100%">
									<tr>
										<%
      		} else 
						if (object instanceof RowColumn) {
										RowColumn rowColumn = (RowColumn) object;
										Icelet thisIcelet = rowColumn.getIcelet();
										pb_i++;
							%>
							<td class="portalColumn"
								width="<%= (rowColumn.getParentRow().getTotalColumnWidth() != 0? String.valueOf((double)(rowColumn.getWidth() * 100)/(double)rowColumn.getParentRow().getTotalColumnWidth())+"%": String.valueOf(rowColumn.getWidth())) %>"
								valign="top">
						<%
										if (thisIcelet != null) {
											PortalServletResponse portalResponse = (PortalServletResponse) request.getAttribute("portal_response_" + rowColumn.getId());
											StringBuffer buffer = portalResponse.getInternalBuffer().getBuffer();
											pageContext.getOut().print(buffer.toString());
										}
											if(nextObj != null && nextObj instanceof PageRow) {
								          PageRow nextPageRow = (PageRow) nextObj;
								          int nextLevel = nextPageRow.getLevel();
								          if (rowColumn.getLevel() >= nextLevel) {
								            for (int count = 0; count < (rowColumn.getLevel() - nextLevel); count++) { %>
															</td></tr></table></td></tr></table>
													<%} %>
																</td></tr></table></td>	</tr>
											<%} 
														else if (rowColumn.getLevel() < nextLevel) { %>
																	<table cellpadding="4" cellspacing="0" width="100%">
															<%} 
											} //Close the nextObj instanceOf PageRow
          						else if (nextObj != null && nextObj instanceof RowColumn) {
											          RowColumn nextRowColumn = (RowColumn) nextObj;
											          int nextLevel = nextRowColumn.getLevel();
											          if (rowColumn.getLevel() >= nextLevel) {
											            for(int count = 0;count < (rowColumn.getLevel() - nextLevel); count++) { %>
																			</td>	</tr></table></td></tr></table>
																<%} %>
																</td>
														<%}
															}  //Close nextObj instance of RowColumn
 //Close the nextObj == null
      			} //Close object instanceof RowColumn
    } //Close the Iterator
%>
</table>
</tr>
</table>
</dhv:containers>