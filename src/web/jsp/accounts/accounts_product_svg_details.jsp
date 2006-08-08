<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="CustomerProduct" class="org.aspcfs.modules.products.base.CustomerProduct" scope="request"/>
<jsp:useBean id="FileItem" class="com.zeroio.iteam.base.FileItem" scope="request"/>
<jsp:useBean id="textItems" class="java.util.Vector" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="Accounts.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<a href="AccountsProducts.do?command=List&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="product.products">Products</dhv:label></a> >
<dhv:label name="product.svgDetails">SVG Details</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:container name="accounts" selected="products" object="OrgDetails" param="<%= "orgId=" + OrgDetails.getOrgId() %>" appendToUrl="<%= addLinkParams(request, "popup|popupType|actionId") %>">
  <table cellpadding="4" cellspacing="0" border="0" width="100%">
    <tr>
      <td colspan="2">
        <strong><%= toHtml(FileItem.getSubject())%>:</strong>
        [
          <a href="AccountsProducts.do?command=Download&orgId=<%= OrgDetails.getOrgId() %>&adId=<%= CustomerProduct.getId() %>&fid=<%= FileItem.getId() %>&ver=<%= 1.0 %>"><dhv:label name="button.download">Download</dhv:label></a> |
          <a href="javascript:popURL('AccountsProducts.do?command=ViewProduct&adId=<%= CustomerProduct.getId() %>','Products', 400, 400, 'YES', 'YES')"><dhv:label name="account.product.viewFullSize">View Full Size</dhv:label></a> |
          <a href="AccountsProducts.do?command=OnlineTool&orgId=<%= OrgDetails.getOrgId() %>&adId=<%= CustomerProduct.getId() %>"><dhv:label name="account.products.onlineTool">Online Tool</dhv:label></a>
        ]
        <%= toHtml((String) request.getAttribute("uploadMsg")) %>
      </td>
    </tr>
    <tr>
      <td valign="top" align="center">
        <dhv:fileItemImage path="accounts" id="<%= FileItem.getId() %>" />
      </td>
      <td valign="top" width="100%">
        <table valign="top" cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
          <tr>
            <th colspan="2">
              <strong><dhv:label name="account.product.editableTextItems">Editable Text Items</dhv:label></strong>
            </th>
          </tr>
          <%--
            The first character determines the type of text. If the first character is
            an 'A', then a text area needs to be displayed else if it is a 'P', then a
            text box needs to be displayed
          --%>
          <%
            for (int i=0; i < textItems.size(); ++i) {
              String str = (String) textItems.elementAt(i);
          %>
              <tr class="title2">
                <td><%= toHtml(str.substring(1)) %></td>
              </tr>
          <%
            }
          %>
        </table>
      </td>
    </tr>
   </table>
</dhv:container>