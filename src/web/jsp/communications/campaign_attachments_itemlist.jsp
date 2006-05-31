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
<%@ page import="java.util.*,org.aspcfs.utils.web.HtmlSelect"%>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<SCRIPT LANGUAGE="JavaScript">
  function checkValue() {
    if (checkNullString(document.getElementById("newitem").value)) {
      alert(label("check.valid.input","Please enter a valid input"));
      return false;
    }
    return true;
  }
  
  function arrayToString(){
    var items = "";
    for(i = 0; i < itemList.length ; i++){
      if(i > 0){
        if(itemList[i] != ""){
          items = items + "^|^" + itemList[i]  ;
        }
      }else{
        if(itemList[i] != ""){
          items = itemList[i];
        }
      }
    }
    return items;
  }
  function removeValues(){
    var tmpList = document.getElementById("itemSelectId");
    var tempArray = new Array()
    var offset = 0;
    var count = 0;
    if (itemList.length != tmpList.length) {
      for (count=0; count<(tmpList.length); count++) {
        itemList[count] = tmpList.options[count].value;
      }
    }
    if (tmpList.length == 0) {
      alert(label("caution.nothingtoremove","Nothing to remove"));
    }	else if (tmpList.selectedIndex == -1) {
      alert(label("caution.itemneedstobe.selected","An item needs to be selected before it can be removed"));
    } else {
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
    }
    document.getElementById("addButton").value  = label("button.addR","Add >");
    document.getElementById("newitem").value = "";
  }
  function addValues(){
    var text = document.getElementById("newitem").value;
    var tmpList = document.getElementById("itemSelectId");
    var mode = document.getElementById("addButton").value;
    if(text == ""){
      alert(label("description.required",'Description is required'));
      return;
    }
    if (tmpList.length == 0 || tmpList.options[0].value == "-1"){
      tmpList.options[0] = new Option(text)
    }	else {
      if (itemList.length == 0) {
        for (count=0; count<(tmpList.length); count++) {
          itemList[count] = tmpList.options[count].value;
        }
      }
      if(mode  == label("button.addR","Add >")){
        tmpList.options[tmpList.length] = new Option(text);
      }else{
        tmpList.options[tmpList.selectedIndex].text = text;
      }
    }
    if(mode == label("button.addR","Add >")){
      itemList[tmpList.length-1] = text;
    }else{
      itemList[tmpList.selectedIndex] = text;
    }
    document.getElementById("addButton").value  = label("button.addR","Add >");
    document.getElementById("newitem").value = "";
    document.getElementById("newitem").focus();
  }
  function editValues(){
   var tmpList = document.getElementById("itemSelectId");
    if(tmpList.options[0].value != "-1"){
      document.getElementById("addButton").value  = label("button.updateR","Update >");
      document.getElementById("newitem").value = tmpList.options[tmpList.selectedIndex].text;
    }
  }
  function setParentItems(){
    var tmpList = document.getElementById("itemSelectId");
    if (tmpList.length == 0 || tmpList.options[0].value == "-1"){
      alert (label("required.oneiteminlist","You must have at least one item in this list."));
    } else {
      opener.document.forms['survey'].items.value = arrayToString();
      window.close();
    }
  }
  function clearSelection(){
    document.getElementById("itemSelectId").selectedIndex =  "-1";
    document.getElementById("addButton").value  = label("button.addR","Add >");
    document.getElementById("newitem").value = "";
  }
</SCRIPT>
<script>var itemList = new Array();</script>
<table cellpadding="4" cellspacing="0" width="100%" id="viewTable" class="details">
<tr>
  <th colspan="2">
    <strong><dhv:label name="project.editItem">Edit Item</dhv:label></strong>
  </th>
</tr>
<tr>
  <td valign="center" width="40%">
    <table width="100%" border="0" cellpadding="2" cellspacing="0" class="empty">
      <tr>
        <td nowrap>
          <dhv:label name="accounts.accountasset_include.Description">Description</dhv:label>
        </td>
      </tr>
      <tr>
        <td nowrap>
          <input type="text" name="description" value="" size="30" id ="newitem">
          <font color="red">*</font>
          <input type="hidden" name="questionid" value="<%=request.getParameter("questionid")%>">
        </td>
        <td nowrap style="text-align: right;" width="12">
          <input type="button" value="<dhv:label name="accounts.accounts_reports_generate.AddR">Add ></dhv:label>" onClick="javascript:if (checkValue()) {addValues();}" id="addButton">
        </td>
     </tr>
    </table>
  </td>
  <td style="text-align: center;" valign="center" width="50%">
    <%
    int count = 0;
    String items =  request.getParameter("items");
    HtmlSelect itemListSelect = new HtmlSelect();
    itemListSelect.setSelectSize(10);
    itemListSelect.addAttribute("id","itemSelectId");
    if (items != null && !items.equals("")) {
        StringTokenizer itemList = new StringTokenizer(items, "^|^");
        while (itemList.hasMoreTokens()) {
          String description = itemList.nextToken();
          itemListSelect.addItem(description);
     %>
          <script>itemList[<%= count %>] = "<%=description%>";</script>
     <% 
        count++;
       }%>
        <%= itemListSelect.getHtml("itemSelect") %>
    <%}else{%>
      <select name="itemSelect" id="itemSelectId" size="10">
        <option value="-1"><dhv:label name="admin.itemList">--------Item List-------</dhv:label></option>
        </select>
    <%}%>
    <br>
    <center>
      <input type="button" value="<dhv:label name="button.remove">Remove</dhv:label>" onclick="javascript:removeValues()">
      <input type="button" value="<dhv:label name="accounts.Rename">Rename</dhv:label>" onclick="javascript:editValues();">
    </center>
   </td>
  </tr>
 </table>
  <br>
 <table cellpadding="0" cellspacing="0" border="0" width="100%">
  <tr>
    <td colspan="2">
      <input type="button" value="<dhv:label name="global.button.save">Save</dhv:label>" onclick="javascript:setParentItems();">&nbsp;
      <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onclick="javascript:window.close();">
    </td>
  </tr>
 </table>
 <script>document.getElementById("newitem").focus();document.getElementById("itemSelectId").selectedIndex = "-1"; </script>
 
