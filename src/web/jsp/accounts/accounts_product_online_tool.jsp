<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="CustomerProduct" class="org.aspcfs.modules.products.base.CustomerProduct" scope="request"/>
<jsp:useBean id="FileItem" class="com.zeroio.iteam.base.FileItem" scope="request"/>
<jsp:useBean id="textItems" class="java.util.Vector" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<%-- 
  This function will submit the changes to the server (text + adId),
  the server will stream back the changed version (without saving anything yet)
  and this function will swap the image with the new image, without having
  to send a new page request.
--%>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/delay.js"></SCRIPT>
<script language="JavaScript">
function doCheck(form) {
  if (form.dosubmit.value == "false") {
    return(true);
  } else {
    return redrawAd();
  }
}
// preload the image
var preview = new Image;
preview.src = "images/generate_image.gif";

function redrawAd() {
  document.getElementsByName("imagePreview").item(0).src = preview.src;
  var url = "AccountsProducts.do?command=StreamAdImage&adId=<%= CustomerProduct.getId() %>";
  var size = <%= textItems.size() %>;
  for (i=1; i <= size; ++i) {
    url += "&text" + i + "=" + escape(document.getElementById("text" + i).value);
  }
  delay(500);
  scroll(0, 0);
  document.getElementsByName("imagePreview").item(0).src = url;
  return false;
}
</script>

<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do">Accounts</a> > 
<a href="Accounts.do?command=Search">Search Results</a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Account Details</a> >
<a href="AccountsProducts.do?command=List&orgId=<%=OrgDetails.getOrgId()%>">Products</a> >
<a href="AccountsProducts.do?command=SVGDetails&orgId=<%= OrgDetails.getOrgId() %>&adId=<%= CustomerProduct.getId() %>&fid=<%= FileItem.getId() %>">SVG Details</a> >
Editor
</td>
</tr>
</table>
<%-- End Trails --%>
<%@ include file="accounts_details_header_include.jsp" %>
<% String param1 = "orgId=" + OrgDetails.getOrgId(); %>      
<form name="tool" action="" onSubmit="return doCheck(this);" method="post">
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
              <a href="AccountsProducts.do?command=Download&orgId=<%= OrgDetails.getOrgId() %>&adId=<%= CustomerProduct.getId() %>&fid=<%= FileItem.getId() %>&ver=<%= 1.0 %>">Download</a>
            ]
            <%= toHtml((String) request.getAttribute("uploadMsg")) %>
          </td>
        </tr>
        <tr>
          <td valign="top" align="center">
            <!-- IMAGE GOES HERE -->
            <dhv:fileItemImage name="imagePreview" path="accounts" id="<%= FileItem.getId() %>" version="1.1d"/>
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
                    <td><%=  str.substring(1) %></td>
                    <td>
                      <dhv:evaluate if="<%= str.substring(0,1).equals("P") %>">
                        <input type="text" name="text<%=(i+1)%>" id="text<%=(i+1)%>" value="<%= toHtmlValue(str.substring(1)) %>" size="40">
                      </dhv:evaluate>
                      <dhv:evaluate if="<%= str.substring(0,1).equals("A") %>">
                        <textarea name="text<%=(i+1)%>" id="text<%=(i+1)%>" cols="40" rows="3"><%= toHtmlValue(str.substring(1)) %></textarea>
                      </dhv:evaluate>
                    </td>
                  </tr>
              <%
                }
              %>
            </table>
            &nbsp;<br />
            <table>
              <tr>
                <td>
                  <input type="hidden" name="adId" value="<%= CustomerProduct.getId() %>" />
                  <input type="submit" name="Preview" value="Preview" onClick="this.form.dosubmit.value='true';">
                  <input type="button" name="Cancel" value="Cancel" onClick="javascript:window.location.href='AccountsProducts.do?command=SVGDetails&orgId=<%= OrgDetails.getOrgId() %>&adId=<%= CustomerProduct.getId() %>&fid=<%= FileItem.getId() %>'">
                  <input type="hidden" name="dosubmit" value="true" />
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</form>
