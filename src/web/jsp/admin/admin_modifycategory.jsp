<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="org.aspcfs.modules.admin.base.*,org.aspcfs.modules.troubletickets.base.*, java.util.*, org.aspcfs.utils.web.HtmlSelect" %>
<jsp:useBean id="categoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryDraftList"  scope="request"/>
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
    }else if(text.indexOf("\"") > -1){
      alert('Double Quotes are not allowed in the description');
      return;
    }
    if (tmpList.length == 0 || tmpList.options[0].value == "-1"){
      tmpList.options[0] = new Option(text);
    }	else {
      if(mode  == "Add >"){
        tmpList.options[tmpList.length] = new Option(text);
      }else{
        if(itemList[tmpList.selectedIndex].enabled == 'true'){
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
   if(tmpList.selectedIndex == -1){
     alert('An item needs to be selected');
     return;
   }
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
  
  function save(){
    populateForm();
    var params =
    'categories=' + escape(document.getElementById('categories').value) + 
    '&parentCode=' + <%= request.getParameter("categoryId") %> + 
    '&level=' + <%= request.getParameter("level") %> +
    '&constantId=' + <%= request.getParameter("constantId") %>;
    window.location.href = 'AdminCategories.do?command=Save&' + params;
  }
  
  function reset(){
    document.getElementById("addButton").value  = "Add >";
    document.getElementById("newitem").value = "";
  }
</SCRIPT>
<script>var itemList = new Array();</script>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details" id="viewTable">
<tr>
  <th colspan="2">
    <strong>Edit Categories</strong>
  </th>
</tr>
<tr>
  <td align="left" valign="center" width="40%">
    <table width="100%" border="0" cellpadding="2" cellspacing="0" class="empty">
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
    HtmlSelect itemListSelect = categoryList.getCatListSelect();
    itemListSelect.setSelectSize(10);
    Iterator i = categoryList.iterator();
    if (i.hasNext()) {
      while (i.hasNext()) {
          TicketCategoryDraft thisCategory = (TicketCategoryDraft) i.next();
     %>
          <script>itemList[<%= count %>] = new category(<%= thisCategory.getId() %>, "<%= thisCategory.getDescription() %>", '<%= thisCategory.getEnabled() ? "true" : "false" %>');</script>
     <% 
        count++;
     }%>
        <% categoryList.setHtmlJsEvent("onChange=\"javascript:reset();\""); %>
        <%= categoryList.getHtmlSelect("itemSelect", -1) %>
    <%}else{%>
      <select name="itemSelect" id="itemSelect" size="10">
        <option value="-1">--------Item List-------</option>
        </select>
    <%}%>
    <br>
    <center>
      <dhv:permission name="admin-sysconfig-categories-delete"><input type="button" value="Remove" onclick="javascript:removeValues()"></dhv:permission>
      <dhv:permission name="admin-sysconfig-categories-edit"><input type="button" value="Rename" onclick="javascript:editValues();"></dhv:permission>
      <dhv:permission name="admin-sysconfig-categories-edit"><input type="button" value="Enable" onclick="javascript:enable();" disable></dhv:permission>
    </center>
   </td>
  </tr>
 </table>
  <br>
 <table cellpadding="0" cellspacing="0" border="0" width="100%" class="empty">
  <tr>
    <td align="left" colspan="2">
      <input type="button" value="Save" onClick="javascript:save();">&nbsp;
      <input type="button" value="Cancel" onclick="javascript:window.close();">
    </td>
  </tr>
 </table>
 <script>document.getElementById("newitem").focus();document.getElementById("itemSelect").selectedIndex = "-1"; </script>
<br>
<input type="hidden" name="categories" id="categories">

