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
      messageText += label("Subject.required","- Subject is required\r\n");
      formTest = false;
    }
    if (form.description.value == "") {
      messageText += label("description.required","- Description is required\r\n");
      formTest = false;
    }
    if (form.productId.value == "") {
      messageText += label("select.product","- Select a product\r\n");
      formTest = false;
    }
    if (form.id<%= OrgDetails.getOrgId() %>.value.length < 5) {
      messageText += label("file.required","- File is required\r\n");
      formTest = false;
    }
    if (formTest == false) {
      messageText = label("File.not.submitted","The file could not be submitted.          \r\nPlease verify the following items:\r\n\r\n") + messageText;
      form.dosubmit.value = "true";
      alert(messageText);
      return false;
    } else {
      if (form.upload.value != label("button.pleasewait","Please Wait...")) {
        form.upload.value=label("button.pleasewait","Please Wait...");
        return true;
      } else {
        return false;
      }
    }
  }
</script>
<body onLoad="document.inputForm.subject.focus();">
<form method="post" name="inputForm" action="AccountsProducts.do?command=Upload" enctype="multipart/form-data" onSubmit="return checkFileForm(this);">
<input type="hidden" name="dosubmit" value="true">
<input type="hidden" name="id" value="<%= OrgDetails.getOrgId() %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="Accounts.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<a href="AccountsProducts.do?command=List&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="product.products">Products</dhv:label></a> >
<dhv:label name="product.uploadProduct">Upload Product</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<%= showError(request, "actionError", false) %>
<dhv:container name="accounts" selected="products" object="OrgDetails" param="<%= "orgId=" + OrgDetails.getOrgId() %>">
<% if (orderProduct.getId() != -1 ) { %>
    <input type="hidden" name="orderItemId" value="<%= orderProduct.getId() %>"/>
<% } %>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <img border="0" src="images/file.gif" align="absmiddle"><b><dhv:label name="account.product.uploadProduct">Upload a New Product</dhv:label></b>
      </th>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="accounts.accounts_calls_list.Subject">Subject</dhv:label>
      </td>
      <td>
        <input type="hidden" name="folderId" value="<%= (String)request.getAttribute("folderId") %>">
        <input type="text" name="subject" size="59" maxlength="255" value="<%= toHtmlValue((String)request.getAttribute("subject")) %>"><font color="red">*</font>
      </td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="accounts.accountasset_include.Description">Description</dhv:label>
      </td>
      <td>
        <textarea name="description" cols="59" value="<%= toHtmlValue((String)request.getAttribute("description")) %>" rows="2"></textarea><font color="red">*</font>
      </td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="account.product.label">Product</dhv:label>
      </td>
      <td>
        <table border="0" cellspacing="0" cellpadding="4" class="empty">
          <tr>
            <td valign="top" nowrap>
              <div id="changeproduct">
                <% if(Product.getId() != -1) {%>
                  <%= toHtml(Product.getCategoryName() + ", " + Product.getName()) %>
                <%} else {%>
                  <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
                <%}%>
              </div>
            </td>
            <td valign="top" width="100%" nowrap>
             <dhv:evaluate if="<%= orderProduct.getId() == -1 %>">
              <font color="red">*</font><%= showAttribute(request, "productIdError") %>
              [<a href="javascript:popCategoryList('productLink','changeproduct','');"><dhv:label name="account.selectProduct">Select Product</dhv:label></a>]
             </dhv:evaluate>
             <input type="hidden" name="productId" id="productLink" value="<%= (Product.getId() != -1) ? "" + Product.getId() : "" %>"></input>
            </td>

          </tr>
        </table>
      </td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="contacts.companydirectory_confirm_importupload.File">File</dhv:label>
      </td>
      <td>
        <input type="file" name="id<%= OrgDetails.getOrgId() %>" size="45">
      </td>
    </tr>
  </table>
  <p align="center">
  <dhv:label name="product.largeFileUploadStatement" param="break=<br />">* Large files may take a while to upload.<br />Wait for file completion message when upload is complete.</dhv:label>
  </p>
  <input type="submit" value="<dhv:label name="global.button.Upload">Upload</dhv:label>" name="upload">
  <input type="submit" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.dosubmit.value='false';this.form.action='AccountsProducts.do?command=List&orgId=<%= OrgDetails.getOrgId() %>';">
</dhv:container>
</form>
</body>
