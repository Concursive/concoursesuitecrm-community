<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="CustomerProduct" class="org.aspcfs.modules.products.base.CustomerProduct" scope="request"/>
<jsp:useBean id="DocListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="FileItemList" class="com.zeroio.iteam.base.FileItemList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="accounts_documents_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<script language="JavaScript" type="text/javascript" src="javascript/confirmDelete.js"></script>
<%-- Preload image rollovers for drop-down menu --%>
<script language="JavaScript" type="text/javascript">
  loadImages('select');
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="Accounts.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<a href="AccountsProducts.do?command=List&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="product.products">Products</dhv:label></a> >
<dhv:label name="product.productDetails">Product Details</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:container name="accounts" selected="products" object="OrgDetails" param="<%= "orgId=" + OrgDetails.getOrgId() %>" appendToUrl="<%= addLinkParams(request, "popup|popupType|actionId") %>">
<center><%= DocListInfo.getAlphabeticalPageLinks() %></center>
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="DocListInfo"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th width="8">&nbsp;</th>
    <th>
      <strong><a href="AccountsProducts.do?command=View&orgId=<%= OrgDetails.getOrgId() %>&column=subject"><dhv:label name="accounts.accounts_documents_details.Item">Item</dhv:label></a></strong>
      <%= DocListInfo.getSortIcon("subject") %>
    </th>
    <th align="center"><dhv:label name="account.ext">Ext</dhv:label></th>
    <th align="center"><dhv:label name="accounts.accounts_documents_details.Size">Size</dhv:label></th>
    <th align="center"><dhv:label name="accounts.accounts_documents_details.Version">Version</dhv:label></th>
      <th>&nbsp;</th>
    <th align="center"><dhv:label name="documents.documents.submitted">Submitted</dhv:label></th>
  </tr>
<%
  Iterator j = FileItemList.iterator();
  if ( j.hasNext() ) {
    int rowid = 0;
    int i = 0;
    while (j.hasNext()) {
      i++;
      rowid = (rowid != 1?1:2);
      FileItem thisFile = (FileItem)j.next();
%>      
    <tr class="row<%= rowid %>">
      <td width="10" valign="middle" align="center" nowrap>
        <%-- Use the unique id for opening the menu, and toggling the graphics --%>
        <%-- To display the menu, pass the actionId, accountId and the contactId--%>
        <a href="javascript:displayMenu('menuFile','<%= OrgDetails.getId() %>','<%= thisFile.getId() %>');" onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>)"><img src="images/select.gif" name="select<%= i %>" align="absmiddle" border="0"></a>
      </td>
      <td valign="middle" width="100%">
        <a href="AccountsProducts.do?command=VersionDetails&orgId=<%= OrgDetails.getOrgId() %>&adId=<%= CustomerProduct.getId() %>&fid=<%= thisFile.getId() %>"><%= thisFile.getImageTag() %><%= toHtml(thisFile.getSubject()) %></a>
      </td>
      <td align="center"><%= toHtml(thisFile.getExtension()) %>&nbsp;</td>
      <td align="center" valign="middle" nowrap>
        <%= thisFile.getRelativeSize() %> <dhv:label name="admin.oneThousand.abbreviation">k</dhv:label>&nbsp;
      </td>
      <td align="right" valign="middle" nowrap>
        <%= thisFile.getVersion() %>&nbsp;
      </td>
      <td align="right" valign="middle" nowrap>
        &nbsp;
      <%--
        [<a href="AccountsProducts.do?command=AddVersion&orgId=<%= OrgDetails.getOrgId() %>&fid=<%= thisFile.getId() %>">Add Version</a>]
      --%>
      </td>
      <td nowrap>
        <dhv:tz timestamp="<%= thisFile.getModified() %>" dateFormat="<%= DateFormat.SHORT %>" timeFormat="<%= DateFormat.LONG %>"/><br>
        <dhv:username id="<%= thisFile.getEnteredBy() %>"/>
      </td>
    </tr>
<%}%>
  </table>
  <br>
  <dhv:pagedListControl object="DocListInfo"/>
<%} else {%>
    <tr class="containerBody">
      <td colspan="7">
        <dhv:label name="project.noDocumentsFound">No Documents found.</dhv:label>
      </td>
    </tr>
  </table>
<%}%>
</dhv:container>