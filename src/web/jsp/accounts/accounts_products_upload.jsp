<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.orders.base.*,org.aspcfs.modules.accounts.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="Product" class="org.aspcfs.modules.products.base.ProductCatalog" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="orderProduct" class="org.aspcfs.modules.orders.base.OrderProduct" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" type="text/javascript" src="javascript/popCategories.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/div.js"></script>
<script language="JavaScript">
  function checkFileForm(form) {
    if (form.dosubmit.value == "false") {
      return true;
    }
    var formTest = true;
    var messageText = "";
    if (form.subject.value == "") {
      messageText += "- Subject is required\r\n";
      formTest = false;
    }
    if (form.description.value == "") {
      messageText += "- Description is required\r\n";
      formTest = false;
    }
    if (form.productId.value == "") {
      messageText += "- Select a product\r\n";
      formTest = false;
    }
    if (form.id<%= OrgDetails.getOrgId() %>.value.length < 5) {
      messageText += "- File is required\r\n";
      formTest = false;
    }
    if (formTest == false) {
      messageText = "The file could not be submitted.          \r\nPlease verify the following items:\r\n\r\n" + messageText;
      form.dosubmit.value = "true";
      alert(messageText);
      return false;
    } else {
      if (form.upload.value != 'Please Wait...') {
        form.upload.value='Please Wait...';
        return true;
      } else {
        return false;
      }
    }
  }
</script>
<body onLoad="document.inputForm.subject.focus();">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do">Accounts</a> > 
<a href="Accounts.do?command=Search">Search Results</a> >
<a href="Accounts.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>">Account Details</a> >
<a href="AccountsProducts.do?command=View&orgId=<%= OrgDetails.getOrgId() %>">Products</a> >
Upload Product
</td>
</tr>
</table>
<%-- End Trails --%>
<%@ include file="accounts_details_header_include.jsp" %>
<% String param1 = "orgId=" + OrgDetails.getOrgId(); %>      
<dhv:container name="accounts" selected="products" param="<%= param1 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <form method="post" name="inputForm" action="AccountsProducts.do?command=Upload" enctype="multipart/form-data" onSubmit="return checkFileForm(this);">
<% if (orderProduct.getId() != -1 ) { %>
    <input type="hidden" name="orderItemId" value="<%= orderProduct.getId() %>"/>
<% } %>
  <tr>
    <td class="containerBack">
      <%= showError(request, "actionError") %>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <img border="0" src="images/file.gif" align="absmiddle"><b>Upload a New Product</b>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Subject
    </td>
    <td>
      <input type="hidden" name="folderId" value="<%= (String)request.getAttribute("folderId") %>">
      <input type="text" name="subject" size="59" maxlength="255" value="<%= toHtmlValue((String)request.getAttribute("subject")) %>"><font color="red">*</font>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Description
    </td>
    <td>
      <textarea name="description" cols="59" value="<%= toHtmlValue((String)request.getAttribute("description")) %>" rows="2"></textarea><font color="red">*</font>
    </td>  
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Product
    </td>
    <td>
      <table border="0" cellspacing="0" cellpadding="4" class="empty">
        <tr>
          <td valign="top" nowrap>
            <div id="changeproduct"><%= Product.getId() == -1 ? "None Selected" : Product.getCategoryName() + ", " + Product.getName() %></div>
          </td>
          <td valign="top" width="100%" nowrap>
           <dhv:evaluate if="<%= orderProduct.getId() == -1 %>">
            <font color="red">*</font><%= showAttribute(request, "productIdError") %>
            [<a href="javascript:popCategoryList('productLink','changeproduct','');">Select Product</a>]
           </dhv:evaluate>
           <input type="hidden" name="productId" id="productLink" value="<%= (Product.getId() != -1) ? "" + Product.getId() : "" %>"></input>
          </td>
          
        </tr>
      </table>
    </td>  
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      File
    </td>
    <td>
      <input type="file" name="id<%= OrgDetails.getOrgId() %>" size="45">
    </td>
  </tr>
</table>
  <p align="center">
    * Large files may take a while to upload.<br>
    Wait for file completion message when upload is complete.
  </p>
  <input type="submit" value=" Upload " name="upload">
  <input type="submit" value="Cancel" onClick="javascript:this.form.dosubmit.value='false';this.form.action='AccountsProducts.do?command=List&orgId=<%= OrgDetails.getOrgId() %>';">
  <input type="hidden" name="dosubmit" value="true">
  <input type="hidden" name="id" value="<%= OrgDetails.getOrgId() %>">
</td>
</tr>
</form>
</table>
</body>
