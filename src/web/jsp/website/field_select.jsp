<%--
  - Copyright(c) 2007 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
  - Version: $Id: fields_select.jsp 4.1 2007-06-08 11:19:57 +0530 (Fri, 08 Jun 2007) rajendrad $
  - Description:
  --%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*" %>
<%@ page import="org.aspcfs.modules.base.CustomField"%>
<%@ page import="org.aspcfs.modules.base.CustomFieldGroup"%>
<jsp:useBean id="fieldListPropertyName" class="java.lang.String" scope="request"/>
<jsp:useBean id="fieldNamesIds" class="java.lang.String" scope="request"/>
<jsp:useBean id="categoryList" class="org.aspcfs.modules.base.CustomFieldCategory" scope="request"/>
<%@ include file="../initPage.jsp" %>

<script language="JavaScript" type="text/javascript" src="javascript/editFieldListForm.js"></script>
<script language="JavaScript" type="text/javascript">
 function optionsPopulate(){
        var usersAgent = navigator.userAgent.toLowerCase();
        var isBrowserTypeIE = (usersAgent.indexOf("msie") != -1);
        var form = document.fieldList;
        var optionArr ="";
        var tempList ="";
        var popValue="";
        optionArr = "<%=fieldNamesIds%>";
        if(window.opener.document.forms[0].fieldIds.value!=null && window.opener.document.forms[0].fieldIds.value!=""){
           optionArr = window.opener.document.forms[0].fieldIds.value;
        }
        if(optionArr=="EMPTY" || optionArr==""){
            return;
        }else
        {
          var nameIDs="";
           var splits = optionArr.split(",");
           for(var i=0;i<splits.length;i++){
              nameIDs = splits[i];
              form.customerFieldDisplayList.options[i]=new Option ((nameIDs.substring(nameIDs.indexOf(":")+1,nameIDs.length)),(nameIDs.substring(0,nameIDs.indexOf(":"))));
           }
       }
   }

 function processList(){
        var tempList = "";
        var tempListValue = "";
        var form = document.fieldList;
        if(form.customerFieldDisplayList.options.length==0){
            alert("Atleast one Field should be selected to Update");
            form.customerFieldList.focus();
            return false;
        }
       for (var i=0; i<form.customerFieldDisplayList.options.length; i++) {
            var text = form.customerFieldDisplayList.options[i].text;
            var Id = form.customerFieldDisplayList.options[i].value;
            tempList = tempList+text;
            tempList = tempList+"\n";
            tempListValue = tempListValue+Id+":"+text+",";
        }
        tempList = tempList.substring(0,tempList.length-1);
        tempListValue = tempListValue.substring(0,tempListValue.length-1);
        window.opener.document.forms[0].<%=fieldListPropertyName%>.value = tempList;
        window.opener.document.forms[0].fieldIds.value = tempListValue;
        window.close();
    }

</script>
<body onLoad="return optionsPopulate();">
<form name="fieldList">
    <table class="details" border="0" cellpadding="4" cellspacing="0" width="100%" >
        <tr>
            <th  width="40%"><dhv:label name="portlets.folder.fieldsInFolders">Fields in the Folder</dhv:label></th>
            <th  width="10%">&nbsp;</th>
            <th  width="40%"><dhv:label name="portlets.folder.fieldstoDisplyInList">Fields to Display in List</dhv:label></th>
            <th  width="10%">&nbsp;</th>
        </tr>

        <tr>
            <td align="right" width="50%">
                <table width="100%" cellspacing="0" cellpadding="2" border="0" class="empty">
                    <tr>
                        <td>

                            <select size=10 id="customerFieldList" name="customerFieldList" multiple="multiple">
                                <%
                                    CustomField field = null;
                                    CustomFieldGroup group = null;
                                    Iterator groups = categoryList.iterator();
                                    while (groups.hasNext()) {
                                        group = (CustomFieldGroup) groups.next();
                                        Iterator fields = group.iterator();
                                        while (fields.hasNext()) {
                                            field = (CustomField) fields.next();

                                %>
                                <option id="<%=field.getId()%>"><%=field.getName()%></option>
                                <% } }%>
                            </select>
                        </td>
                    </tr>
                </table>
            </td>

            <td width="25">
                <table width="100%" cellspacing="0" cellpadding="2" border="0" class="empty">
                    <tr>
                        <td><input type=button value="<dhv:label name="portlets.folder.greaterthan">>></dhv:label>" name=">>" onclick="javascript:moveToDisplayList()"></td>
                    </tr>
                    <tr>
                        <td><input type=button value="<dhv:label name="portlets.folder.lessthan"><<</dhv:label>" name="<<" onclick="javascript:moveToList()"></td>

                    </tr>
                </table>
            </td>
            <td width="50%">
                <table width="100%" cellspacing="0" cellpadding="2" border="0" class="empty">
                    <tr>
                        <td width="100%">

                            <select size="10" name="customerFieldDisplayList" id="customerFieldDisplayList" multiple="true"></select>

                        </td>
                    </tr>
                </table>
            </td>

            <td width="25">
                <table width="100%" cellspacing="0" cellpadding="2" border="0" class="empty">
                    <tr>
                        <td><input type=button value="<dhv:label name="global.button.Up">Up</dhv:label>" name="Up" onclick="javascript:moveUp()"></td>
                    </tr>
                    <tr>
                        <td><input type=button value="<dhv:label name="global.button.Down">Down</dhv:label>" name="Down" onclick="javascript:moveDown()"></td>
                    </tr>
                </table>
            </td>
        </tr>

        <tr>
            <td  colspan="4">
                <table width="100%" cellspacing="0" cellpadding="2" border="0" class="empty">
                    <td width="30%">&nbsp;</td>
                    <td width="20%"><input type=button value="<dhv:label name="global.button.update">Update</dhv:label>" name="Update" onclick="javascript:processList()"></td>
                    <td width="20%"><input type=button value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" name="Cancel" onclick="javascript:window.close()"></td>
                    <td width="30%">&nbsp;</td>
                </table>
            </td>
        </tr>
    </table>
</form>
</body>