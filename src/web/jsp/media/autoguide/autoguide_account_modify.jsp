<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.media.autoguide.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="InventoryDetails" class="org.aspcfs.modules.media.autoguide.base.Inventory" scope="request"/>
<jsp:useBean id="YearSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="MakeSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="ModelSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="OptionList" class="org.aspcfs.modules.media.autoguide.base.OptionList" scope="request"/>
<jsp:useBean id="adRunTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<%@ include file="../../initPage.jsp" %>
<body onLoad="javascript:document.forms[0].stockNo.focus();">
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<script language="JavaScript">
  function checkForm(form) {
    if (form.dosubmit.value == "false") {
      return true;
    }
    formTest = true;
    message = "";
    if (form.stockNo.value == "") { 
      message += "- StockNo is a required field\r\n";
      formTest = false;
    }
    if (form.vehicle_year.selectedIndex == 0) {
      message += "- Year is a required field\r\n";
      formTest = false;
    }
    if (form.vehicle_makeId.selectedIndex == 0) {
      message += "- Make is a required field\r\n";
      formTest = false;
    }
    if (form.vehicle_modelId.selectedIndex == 0) {
      message += "- Model is a required field\r\n";
      formTest = false;
    }
    if (formTest == false) {
      form.dosubmit.value = "true";
      alert("Form could not be saved, please check the following:\r\n\r\n" + message);
      return false;
    } else {
      return true;
    }
  }
  function updateMakeList() {
    var sel = document.forms['addVehicle'].elements['vehicle_year'];
    var value = sel.options[sel.selectedIndex].value;
    var url = "AutoGuide.do?command=UpdateMakeList&year=" + escape(value);
    if (document.forms['addVehicle'].elements['vehicle_makeId'].selectedIndex < 1) {
      window.frames['server_commands'].location.href=url;
    } else {
      updateModelList();
    }
  }
  function updateModelList() {
    var sel = document.forms['addVehicle'].elements['vehicle_makeId'];
    var value = sel.options[sel.selectedIndex].value;
    var sel2 = document.forms['addVehicle'].elements['vehicle_year'];
    var value2 = sel2.options[sel2.selectedIndex].value;
    var url = "AutoGuide.do?command=UpdateModelList&makeId=" + escape(value) + "&year=" + escape(value2);
    window.frames['server_commands'].location.href=url;
  }
  function togglePrice() {
    var sel = document.forms['addVehicle'].elements['sellingPriceType'];
    var value = sel.options[sel.selectedIndex].value;
    if (value == '1') {
      hideSpan('spanSelling2');
      showSpan('spanSelling1');
      document.addVehicle.sellingPrice.focus();
    } else {
      hideSpan('spanSelling1');
      showSpan('spanSelling2');
      document.addVehicle.sellingPriceText.focus();
    }
  }
</script>
<form name="addVehicle" action="AccountsAutoGuide.do?command=AccountUpdate&orgId=<%= OrgDetails.getOrgId() %>&auto-populate=true" method="post" onSubmit="return checkForm(this);">
<input type="hidden" name="accountId" value="<%= OrgDetails.getOrgId() %>"/>
<%-- Trails --%>
<table class="trails">
<tr>
<td>
<a href="Accounts.do">Account Management</a> > 
<a href="Accounts.do?command=Search">View Accounts</a> >
<a href="Accounts.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>">Account Details</a> >
<a href="AccountsAutoGuide.do?command=AccountList&orgId=<%= OrgDetails.getOrgId() %>">Vehicle Inventory List</a> >
<dhv:evaluate if="<%= (request.getParameter("return") == null) %>">
<a href="AccountsAutoGuide.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>&id=<%= InventoryDetails.getId() %>">Vehicle Details</a> >
</dhv:evaluate>
Modify Vehicle
</td>
</tr>
</table>
<%-- End Trails --%>
<%@ include file="../../accounts/accounts_details_header_include.jsp" %>
<% String param1 = "orgId=" + OrgDetails.getOrgId(); %>      
<dhv:container name="accounts" selected="vehicles" param="<%= param1 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
<input type="submit" value="Update">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='AccountsAutoGuide.do?command=AccountList&orgId=<%= OrgDetails.getOrgId() %>';this.form.dosubmit.value='false';">
<input type="reset" value="Reset">
<input type="hidden" name="id" value="<%= InventoryDetails.getId() %>">
<input type="hidden" name="modified" value="<%= InventoryDetails.getModified() %>">
<input type="hidden" name="dosubmit" value="true">
<br>
<%= showError(request, "actionError") %>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th colspan="2" valign="center">
      <strong>Modify Existing Vehicle Record</strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Stock No
    </td>
    <td width="100%">
      <input type="text" size="15" name="stockNo" value="<%= toHtmlValue(InventoryDetails.getStockNo()) %>">
      <font color=red>*</font> <%= showAttribute(request, "stockNoError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Year
    </td>
    <td>
      <% YearSelect.setJsEvent("onchange=\"updateMakeList();\""); %>
      <%= YearSelect.getHtml("vehicle_year", InventoryDetails.getVehicle().getYear()) %>
      <font color=red>*</font> <%= showAttribute(request, "yearError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Make
    </td>
    <td>
      <% MakeSelect.setJsEvent("onchange=\"updateModelList();\""); %>
      <%= MakeSelect.getHtml("vehicle_makeId", InventoryDetails.getVehicle().getMakeId()) %>
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
      <%= ModelSelect.getHtml("vehicle_modelId", InventoryDetails.getVehicle().getModelId()) %>
      <font color=red>*</font> <%= showAttribute(request, "modelIdError") %>
    </td>
  </tr>
	<tr class="containerBody">
    <td nowrap class="formLabel">
      Style
    </td>
    <td>
      <input type="text" size="10" name="style" value="<%= toHtmlValue(InventoryDetails.getStyle()) %>">
      <%= showAttribute(request, "styleError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Mileage
    </td>
    <td>
      <input type="text" size="10" name="mileage" value="<%= InventoryDetails.getMileageString() %>">
      <%= showAttribute(request, "mileageError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Vin
    </td>
    <td>
      <input type="text" size="30" name="vin" value="<%= toHtmlValue(InventoryDetails.getVin()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Selling Price
    </td>
    <td>
      <select size="1" name="sellingPriceType" onChange="togglePrice()">
        <option value="1"<dhv:evaluate if="<%= !hasText(InventoryDetails.getSellingPriceText()) %>"> selected</dhv:evaluate>>Total</option>
        <option value="2"<dhv:evaluate if="<%= hasText(InventoryDetails.getSellingPriceText()) %>"> selected</dhv:evaluate>>Custom</option>
      </select>
      <span name="spanSelling1" id="spanSelling1"<dhv:evaluate if="<%= hasText(InventoryDetails.getSellingPriceText()) %>"> style="display:none"</dhv:evaluate>>
        <input type="text" size="10" name="sellingPrice" value="<%= InventoryDetails.getSellingPriceString() %>">
        <%= showAttribute(request, "sellingPriceError") %>
      </span>
      <span name="spanSelling2" id="spanSelling2"<dhv:evaluate if="<%= !hasText(InventoryDetails.getSellingPriceText()) %>"> style="display:none"</dhv:evaluate>>
        <input type="text" size="20" name="sellingPriceText" value="<%= toHtmlValue(InventoryDetails.getSellingPriceText()) %>">
        <%= showAttribute(request, "sellingPriceTextError") %>
      </span>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Exterior Color
    </td>
    <td>
      <input type="text" size="15" name="exteriorColor" value="<%= toHtmlValue(InventoryDetails.getExteriorColor()) %>">
      <%= showAttribute(request, "exteriorColorError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Condition
    </td>
    <td>
      <input type="text" size="30" name="condition" value="<%= toHtmlValue(InventoryDetails.getCondition()) %>">
      <%= showAttribute(request, "conditionError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Additional Text
    </td>
    <td>
      <input type="text" size="30" name="comments" value="<%= toHtmlValue(InventoryDetails.getComments()) %>">
      <%= showAttribute(request, "commentsError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Sold
    </td>
    <td>
      <input type="checkbox" name="sold"<%= (InventoryDetails.getSold()?" checked":"") %>>
    </td>
  </tr>
</table>
<br>
&nbsp;
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th colspan="3" valign="center" align="left">
      <strong>Select Vehicle Options</strong>
    </th>
  </tr>
<%
  int rows = (OptionList.size()/3);
  if ((OptionList.size()%3) > 0) {
    ++rows;
  }
  
  int itemCount = 0;
  for (int rowCount = 0; rowCount < rows; rowCount++) { 
    Option option1 = null;
    Option option2 = null;
    Option option3 = null;
    ++itemCount;
    option1 = (Option)OptionList.get(rowCount);
    if (OptionList.size() > rowCount + rows) {
      option2 = (Option)OptionList.get(rowCount + rows);
    }
    if (OptionList.size() > rowCount + (rows*2)) {
      option3 = (Option)OptionList.get(rowCount + (rows*2));
    }
%>
  <tr class="containerBody">
    <td width="34%">
      <input type="hidden" name="option<%= itemCount %>id" value="<%= option1.getId() %>">
      <input type="checkbox" name="option<%= option1.getId() %>"<%= (InventoryDetails.hasOption(option1.getId())?" checked":"") %>><%= option1.getName() %>
    </td>
    <td width="33%">
<%
    if (option2 != null) {
      ++itemCount;
%>
      <input type="hidden" name="option<%= itemCount %>id" value="<%= option2.getId() %>">
      <input type="checkbox" name="option<%= option2.getId() %>"<%= (InventoryDetails.hasOption(option2.getId())?" checked":"") %>><%= option2.getName() %>
<%  }  %>&nbsp;
    </td>
    <td width="33%">
<%
    if (option3 != null) {
      ++itemCount;
%>
      <input type="hidden" name="option<%= itemCount %>id" value="<%= option3.getId() %>">
      <input type="checkbox" name="option<%= option3.getId() %>"<%= (InventoryDetails.hasOption(option3.getId())?" checked":"") %>><%= option3.getName() %>
<%  }  %>&nbsp;
    </td>
  </tr>
<%
  }
%>
</table>
<br>
&nbsp;
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th valign="center" align="left">
      <strong>Select Ad Run Dates</strong>
    </th>
  </tr>
<%
  int runCount = 0;
  Iterator adRuns = InventoryDetails.getAdRuns().iterator();
  while (adRuns.hasNext()) {
    AdRun adRun = (AdRun)adRuns.next();
    ++runCount;
%>  
  <tr class="containerBody">
    <td nowrap>
      <input type="hidden" name="adrun<%= runCount %>id" value="<%= adRun.getId() %>">
      Run Date <input type="text" size="10" name="adrun<%= runCount %>runDate" value="<%= toDateString(adRun.getRunDate()) %>">
      <a href="javascript:popCalendar('addVehicle', 'adrun<%= runCount %>runDate');"><img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle" height="16" width="16"/></a> (mm/dd/yyyy)
      &nbsp;&nbsp;
      Ad Type <%= adRunTypeList.getHtmlSelect("adrun" + runCount + "adType", adRun.getAdType()) %>
      &nbsp;&nbsp;
      <input type="checkbox" name="adrun<%= runCount %>includePhoto"<%= (adRun.getIncludePhoto()?" checked":"") %>>Include Photo
      &nbsp;&nbsp;
      <input type="checkbox" name="adrun<%= runCount %>remove">remove
    </td>
  </tr>
<%}%>
<%
  int runCount2 = ++runCount;
  for (; runCount < runCount2 + 5; ++runCount) {
%>  
  <tr class="containerBody">
    <td nowrap>
      <input type="hidden" name="adrun<%= runCount %>id" value="-1">
      Run Date <input type="text" size="10" name="adrun<%= runCount %>runDate">
      <a href="javascript:popCalendar('addVehicle', 'adrun<%= runCount %>runDate');"><img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle" height="16" width="16"/></a> (mm/dd/yyyy)
      &nbsp;&nbsp;
      Ad Type <%= adRunTypeList.getHtmlSelect("adrun" + runCount + "adType", -1) %>
      &nbsp;&nbsp;
      <input type="checkbox" name="adrun<%= runCount %>includePhoto">Include Photo
    </td>
  </tr>
<%}%>
</table>
&nbsp;
<br>
<input type="submit" value="Update" name="Save">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='AccountsAutoGuide.do?command=AccountList&orgId=<%= OrgDetails.getOrgId() %>';this.form.dosubmit.value='false';">
<input type="reset" value="Reset">
    </td>
  </tr>
</table>
</form>
</body>
