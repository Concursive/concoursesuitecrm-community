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
<a href="Accounts.do">Accounts</a> > 
<a href="Accounts.do?command=Search">Search Results</a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Account Details</a> >
<a href="AccountsProducts.do?command=List&orgId=<%=OrgDetails.getOrgId()%>">Products</a> >
SVG Details
</td>
</tr>
</table>
<%-- End Trails --%>
<%@ include file="accounts_details_header_include.jsp" %>
<% String param1 = "orgId=" + OrgDetails.getOrgId(); %>      
<dhv:container name="accounts" selected="products" param="<%= param1 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
    <%-- Main table that has all the contents --%>
      <table cellpadding="4" cellspacing="0" border="0" width="100%">
        <tr>
          <td colspan="2">
            <strong><%= toHtml(FileItem.getSubject())%>:</strong>
            [
              <a href="AccountsProducts.do?command=Download&orgId=<%= OrgDetails.getOrgId() %>&adId=<%= CustomerProduct.getId() %>&fid=<%= FileItem.getId() %>&ver=<%= 1.0 %>">Download</a> | 
              <a href="javascript:popURL('AccountsProducts.do?command=ViewProduct&adId=<%= CustomerProduct.getId() %>','Products', 400, 400, 'YES', 'YES')">View Full Size</a> | 
              <a href="AccountsProducts.do?command=OnlineTool&orgId=<%= OrgDetails.getOrgId() %>&adId=<%= CustomerProduct.getId() %>">Online Tool</a> 
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
                  <strong>Editable Text Items</strong>
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
     <%-- Main table ends --%>
    </td>
  </tr>
</table>
