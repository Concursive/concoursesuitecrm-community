<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="org.aspcfs.modules.admin.base.*,org.aspcfs.modules.troubletickets.base.*, java.util.*, org.aspcfs.utils.web.HtmlSelect" %>
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryDraftList"  scope="request"/>
<script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript">
  function populateForm(){
  var items = "";
    for(i = 0; i < itemList.length ; i++){
      var catInfo = itemList[i].id + "|" + itemList[i].description + "|" + itemList[i].enabled;
      if(i > 0){
        if(itemList[i] != ""){
          items = items + "|" + catInfo ;
        }
      }else{
        if(itemList[i] != ""){
          items = catInfo;
        }
      }
    }
    document.getElementById('categories').value = items;
  }
  
  function category(id, description, enabled){
    this.id = id;
    this.description = description;
    this.enabled = enabled;
  }
  
  function removeValues(){
    var tmpList = document.getElementById("itemSelect");
    var tempArray = new Array()
    var offset = 0;
    var count = 0;
    if (itemList.length != tmpList.length) {
      for (count=0; count<(tmpList.length); count++) {
        itemList[count] = tmpList.options[count].value;
      }
    }
    if (tmpList.length == 0) {
      alert("Nothing to remove")
    }	else if (tmpList.selectedIndex == -1) {
      alert("An item needs to be selected before it can be removed");
    } else {
        if(itemList[tmpList.selectedIndex].id == -1){
        itemList[tmpList.selectedIndex] = "skip";
        tmpList.options[tmpList.selectedIndex] = null;
        for (i=0; i < itemList.length; i++){
          if (itemList[i] == "skip") {
            offset = 1;
            delete itemList[i];
            tempArray[i] = itemList[i+offset];
          } else if (i+offset == itemList.length) {
            break;
          } else {
            tempArray[i] = itemList[i+offset];
          }
        }
        delete itemList
        itemList = new Array();
        for (i=0; i < tempArray.length; i++){
          if (tempArray[i] != null) {
            itemList[i] = tempArray[i];
          }
        }
      }else{
        if(itemList[tmpList.selectedIndex].enabled == 'true'){
        itemList[tmpList.selectedIndex].enabled = 'false';
        tmpList.options[tmpList.selectedIndex].text = tmpList.options[tmpList.selectedIndex].text + "*";
        }else{
          alert('The selected category is already disabled');
        }
      }
    }
    document.getElementById("addButton").value  = "Add >";
    document.getElementById("newitem").value = "";
  }
  
  function addValues(){
    var text = document.getElementById("newitem").value;
    var tmpList = document.getElementById("itemSelect");
    var mode = document.getElementById("addButton").value;
    if(text == ""){
      alert('Description is required');
      return;
    }else if(checkDuplicate(text, mode)){
      alert('Category with that description already exists');
      return;
    }
    if (tmpList.length == 0 || tmpList.options[0].value == "-1"){
      tmpList.options[0] = new Option(text);
    }	else {
      if(mode  == "Add >"){
        tmpList.options[tmpList.length] = new Option(text);
      }else{
        if(tmpList.options[tmpList.selectedIndex].enabled){
          tmpList.options[tmpList.selectedIndex].text = text;
        }else{
          tmpList.options[tmpList.selectedIndex].text = text + "*";
        }
      }
    }
    if(mode == "Add >"){
      itemList[tmpList.length-1] = new category(-1, text, 'true');
    }else{
      itemList[tmpList.selectedIndex].description = text;
    }
    document.getElementById("addButton").value  = "Add >";
    document.getElementById("newitem").value = "";
    document.getElementById("newitem").focus();
  }
  
  function editValues(){
   var tmpList = document.getElementById("itemSelect");
    if(tmpList.options[0].value != "-1"){
      document.getElementById("addButton").value  = "Update >";
      document.getElementById("newitem").value = itemList[tmpList.selectedIndex].description;
    }
  }
  
  function enable(){
   var tmpList = document.getElementById("itemSelect");
    if(tmpList.selectedIndex != -1){
      for(i = 0; i < itemList.length ; i++){
      if(itemList[i].id == tmpList.options[tmpList.selectedIndex].value){
        if(itemList[i].enabled == 'false'){
          itemList[i].enabled = 'true';
          tmpList.options[tmpList.selectedIndex].text = itemList[i].description;
        }else{
          alert('Category is already enabled');
        }
      }
    }
   }
  }
  
  function clearSelection(){
    document.getElementById("itemSelect").selectedIndex =  "-1";
    document.getElementById("addButton").value  = "Add >";
    document.getElementById("newitem").value = "";
  }
  
  function checkDuplicate(description, mode){
    for(i = 0; i < itemList.length ; i++){
      if(itemList[i].description == description){
        if(mode == "Add >" || (mode == "Update >" && document.getElementById("itemSelect").selectedIndex != i)){
          return true;
        }
      }
    }
    return false;
  }
  
  function confirmSave(){
    populateForm();
    var params =
    'categories=' + escape(document.getElementById('categories').value) + 
    '&parentCode=' + <%= request.getParameter("categoryId") %> + 
    '&level=' + <%= request.getParameter("level") %>;
    popURL('AdminCategories.do?command=ConfirmSave&' + params + '&popup=true', 'Save','320','200','yes','no');
  }
  
</SCRIPT>
<script>var itemList = new Array();</script>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF" id="viewTable">
<tr class="title">
  <td colspan="2">
    <strong>Edit Categories</strong>
  </td>
</tr>
<tr>
  <td align="left" valign="center" width="40%">
    <table width="100%" border="0" cellpadding="2" cellspacing="0">
      <tr>
        <td nowrap align="left">
          Description&nbsp;
        </td>
      </tr>
      <tr>
        <td nowrap align="left">
          <input type="text" name="description" value="" size="30" id ="newitem">
          <font color="red">*</font>
        </td>
        <td nowrap align="right" width="12">
          <input type="button" value="Add >" onClick="javascript:addValues();" id="addButton">
        </td>
     </tr>
    </table>
  </td>
  <td align="center" valign="center" width="50%">
    <%
    int count = 0;
    HtmlSelect itemListSelect = CategoryList.getCatListSelect();
    itemListSelect.setSelectSize(10);
    Iterator i = CategoryList.iterator();
    if (i.hasNext()) {
      while (i.hasNext()) {
          TicketCategoryDraft thisCategory = (TicketCategoryDraft) i.next();
     %>
          <script>itemList[<%= count %>] = new category(<%= thisCategory.getId() %>, "<%= thisCategory.getDescription() %>", '<%= thisCategory.getEnabled() ? "true" : "false" %>');</script>
     <% 
        count++;
     }%>
        <% CategoryList.setHtmlJsEvent(""); %>
        <%= CategoryList.getHtmlSelect("itemSelect", -1) %>
    <%}else{%>
      <select name="itemSelect" id="itemSelect" size="10">
        <option value="-1">--------Item List-------</option>
        </select>
    <%}%>
    <br>
    <center>
      <input type="button" value="Remove" onclick="javascript:removeValues()">
      <input type="button" value="Rename" onclick="javascript:editValues();">
      <input type="button" value="Enable" onclick="javascript:enable();" disable>
    </center>
   </td>
  </tr>
 </table>
  <br>
 <table cellpadding="0" cellspacing="0" border="0" width="100%">
  <tr>
    <td align="left" colspan="2">
      <input type="button" value="Save" onClick="javascript:confirmSave();">&nbsp;
      <input type="button" value="Cancel" onclick="javascript:window.close();">
    </td>
  </tr>
 </table>
 <script>document.getElementById("newitem").focus();document.getElementById("itemSelect").selectedIndex = "-1"; </script>
<br>
<input type="hidden" name="categories" id="categories">

