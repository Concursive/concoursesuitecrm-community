<%-- 
  - Copyright(c) 2007 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id: list_values.jsp 15115 2006-08-07 17:27:51 +0000 (Mon, 07 Aug 2006) matt $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>

<jsp:useBean id="SelectedList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="moduleId" class="java.lang.String" scope="request"/>
<jsp:useBean id="SubTitle" class="java.lang.String" scope="request"/>
<jsp:useBean id="category" class="java.lang.String" scope="request"/>
<%@ include file="initPage.jsp" %>
<script language="JavaScript" type="text/javascript" src="javascript/checkString.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/editListForm.js"></script>

<title><dhv:label name="admin.addSearchOption">Add Search Option</dhv:label></title>

<jsp:include page="templates/cssInclude.jsp" flush="true"/>

<script language="JavaScript" type="text/javascript">
  function doCheck() {
    if (document.modifyList.dosubmit.value == "false") {
      return true;
    }
    var test = document.modifyList.selectedList;
    if (test != null) {
        return submitList();
    }
  }
  
  //-------------------------------------
  // This function initilizes the list box  
  // with the values from textbox
  //-------------------------------------
  function initilizeList(){

      var optionVal = '<%=request.getParameter("optionVal")%>';	
      
      document.forms['modifyList'].newValue.focus();
      
      if(checkNullString(optionVal)){
       return;
      }
      
      var selectBox = document.forms['modifyList'].selectedList;
      var option = optionVal.split(";");
      var count = 0;
      var index = 0 ;
      
      while(index < option.length){
      	if(!checkNullString(option[index])){
      		var str = strtrim(option[index]);
      		selectBox.options[count]=new Option(str,("*" + str));
      		itemList[count] = new category(count,str,true);
      		count += 1;
        }
      	index += 1;
      }
      
      
  }
  
  //-------------------------------------
  // This function creates a string which 
  // contains semicolon separated values 
  // from list box.
  //-------------------------------------
  function submitList(){
      
      var selectBox = document.forms['modifyList'].selectedList;
      var element = '<%=request.getParameter("element")%>';
      
      if(selectBox.length == 0 || selectBox.options[0].value == "-1"){
      	window.opener.document.searchAccount.elements[element].value = "";
      	window.close();
      	return;
      }
      var returnStr = new String(selectBox.options[0].text);
      
      for(var i = 1 ; i < selectBox.options.length ; i++){
          returnStr = returnStr.concat(";",selectBox.options[i].text);        
      }
      
      window.opener.document.searchAccount.elements[element].value = returnStr;
      window.close();
  }
  
</script>
<body onLoad="javascript:initilizeList();">
<form name="modifyList" onSubmit="return doCheck();">

<table cellpadding="4" cellspacing="0" border="0" width="100%" height="80%" class="details">
  <tr>
    <th colspan="3">
      <strong><dhv:label name="contact.option">Option</dhv:label></strong>
    </th>
  </tr>
  <tr>
    <td width="50%">
      <table width="100%" cellspacing="0" cellpadding="2" border="0" class="empty">
        <tr>
          <td valign="center">
            <dhv:label name="admin.addSearchOption">Add Search Option</dhv:label>
          </td>
        </tr>
        <tr>
          <td valign="center">
            <input type="text" name="newValue" id="newValue" value="" size="25" maxlength="300">
          </td>
        </tr>
        <tr>
          <td valign="center">
            <input type="button" name="addButton" id="addButton" value="<dhv:label name="accounts.accounts_reports_generate.AddR">Add ></dhv:label>" onclick="javascript:addValues()">
          </td>
        </tr>
      </table>
    </td>
    <td width="25">
      <table width="100%" cellspacing="0" cellpadding="2" border="0" class="empty">
        <tr>
          <td valign="center">
            <input type="button" value="<dhv:label name="button.remove">Remove</dhv:label>" onclick="javascript:removeValues()">
          </td>
        </tr>
        <tr>
          <td valign="center">
          <input type="button" value="<dhv:label name="accounts.Rename">Rename</dhv:label>" onclick="javascript:editValues();">
          </td>
        </tr>

      </table>
    </td>
    <td width="50%">
      <select name="selectedList" multiple id="selectedList" size="10" onChange="javascript:resetOptions();">
      <option value="-1"><dhv:label name="admin.itemList">--------Item List-------</dhv:label></option>
      </select>
    </td>
  </tr>
  <tr>
    <td colspan="3">
      <input type="hidden" name="selectNames" value="">
      <input type="hidden" name="moduleId" value="<%= moduleId %>">
      <input type="hidden" name="dosubmit" value="true">
      <input type="hidden" name="tableName" value="<%= SelectedList.getTableName() %>">
      <input type="hidden" name="category" value="<%= category %>">
      <input type="submit" value="<dhv:label name="button.ok">Save Changes</dhv:label>" onClick="javascript:this.form.dosubmit.value='true';">
      <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.dosubmit.value='false';javascript:window.close();">
    </td>
  </tr>
</table>
</form>
</body>