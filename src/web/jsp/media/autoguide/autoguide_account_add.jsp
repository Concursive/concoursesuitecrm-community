<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="OrgDetails" class="com.darkhorseventures.cfsbase.Organization" scope="request"/>
<jsp:useBean id="InventoryDetails" class="com.darkhorseventures.autoguide.base.AccountInventory" scope="request"/>
<jsp:useBean id="YearSelect" class="com.darkhorseventures.webutils.HtmlSelect" scope="request"/>
<jsp:useBean id="MakeSelect" class="com.darkhorseventures.webutils.HtmlSelect" scope="request"/>
<jsp:useBean id="ModelSelect" class="com.darkhorseventures.webutils.HtmlSelect" scope="request"/>
<jsp:useBean id="Vehicle" class="com.darkhorseventures.autoguide.base.Vehicle" scope="request"/>
<jsp:useBean id="Make" class="com.darkhorseventures.autoguide.base.Make" scope="request"/>
<jsp:useBean id="Model" class="com.darkhorseventures.autoguide.base.Model" scope="request"/>
<%@ include file="initPage.jsp" %>
<body onLoad="javascript:document.forms[0].description.focus();">
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/popCalendar.js"></script>
<script language="JavaScript">
  function checkForm(form) {
    formTest = true;
    message = "";
    if ((!form.closeDate.value == "") && (!checkDate(form.closeDate.value))) { 
      message += "- Check that Est. Close Date is entered correctly\r\n";
      formTest = false;
    }
    if ((!form.alertDate.value == "") && (!checkDate(form.alertDate.value))) { 
      message += "- Check that Alert Date is entered correctly\r\n";
      formTest = false;
    }
    if (formTest == false) {
      alert("Form could not be saved, please check the following:\r\n\r\n" + message);
      return false;
    } else {
      return true;
    }
  }
    
  function updateList() {
    var sel = document.forms['addVehicle'].elements['makeId'];
    var value = sel.options[sel.selectedIndex].value;
    var url = "AutoGuide.do?command=UpdateModelList&makeId=" + escape(value);
    window.frames['server_commands'].location.href=url;
  }
</script>
<form name="addVehicle" action="AccountsAutoGuide.do?command=AccountInsert&auto-populate=true" method="post" onSubmit="return checkForm(this);">
<a href="AccountsAutoGuide.do?command=AccountList&orgId=<%= OrgDetails.getOrgId() %>">Back to Vehicle List</a><br>&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(OrgDetails.getName()) %></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "orgId=" + OrgDetails.getOrgId(); %>      
      <dhv:container name="accounts" selected="vehicles" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">
<input type="submit" value="Save">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='AccountsAutoGuide.do?command=AccountList&orgId=<%= OrgDetails.getOrgId() %>'">
<input type="reset" value="Reset">
<br>
&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan=2 valign=center align=left>
      <strong>Add a New Vehicle Record</strong>
    </td>     
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Stock No
    </td>
    <td width="100%">
      <input type="text" size="35" name="stockNo" value="<%= toHtmlValue(InventoryDetails.getStockNo()) %>">
      <%= showAttribute(request, "stockNoError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Year
    </td>
    <td>
      <%= YearSelect.getHtml() %>
      <%= showAttribute(request, "yearError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Make
    </td>
    <td>
      <% MakeSelect.setJsEvent("onchange=\"updateList();\""); %>
      <%= MakeSelect.getHtml("makeId") %>
      <font color=red>*</font> <%= showAttribute(request, "makeIdError") %>
      <%= showAttribute(request, "modelError") %>
      <iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Model
    </td>
    <td>
      <%= ModelSelect.getHtml("modelId") %>
      <%= showAttribute(request, "modelIdError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Vin
    </td>
    <td>
      <input type="text" size="10" name="vin" value="<%= toHtmlValue(InventoryDetails.getVin()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Mileage
    </td>
    <td>
      <input type="text" size="10" name="mileage" value="<%= InventoryDetails.getMileage() %>">
      <%= showAttribute(request, "mileageError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Condition
    </td>
    <td>
      <input type="text" size="10" name="condition" value="<%= toHtmlValue(InventoryDetails.getCondition()) %>">
      <%= showAttribute(request, "conditionError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Comments
    </td>
    <td>
      <input type="text" size="5" name="comments" value="<%= toHtmlValue(InventoryDetails.getComments()) %>">
      <%= showAttribute(request, "commentsError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Exterior Color
    </td>
    <td>
      <input type="text" size="5" name="exteriorColor" value="<%= toHtmlValue(InventoryDetails.getExteriorColor()) %>">
      <%= showAttribute(request, "exteriorColorError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Selling Price
    </td>
    <td>
      <input type="text" size="5" name="sellingPrice" value="<%= InventoryDetails.getSellingPrice() %>">
      <%= showAttribute(request, "sellingPrice") %>
    </td>
  </tr>
</table>
&nbsp;
<br>
<input type="submit" value="Save">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='AccountsAutoGuide.do?command=AccountList&orgId=<%= OrgDetails.getOrgId() %>'">
<input type="reset" value="Reset">
    </td>
  </tr>
</table>
</form>
</body>
