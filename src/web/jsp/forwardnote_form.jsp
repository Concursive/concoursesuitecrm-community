<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*,java.net.URLEncoder.*" %>
<jsp:useBean id="NoteDetails" class="com.darkhorseventures.cfsbase.CFSNote" scope="request"/>
<jsp:useBean id="CallDetails" class="com.darkhorseventures.cfsbase.Call" scope="request"/>
<jsp:useBean id="UserList" class="com.darkhorseventures.cfsbase.UserList" scope="request"/>
<jsp:useBean id="DepartmentList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<%@ include file="initPage.jsp" %>
<script language="JavaScript">

  function updateUserList() {
    var sel = document.forms['ForwardForm'].elements['selDepartment'];
    var value = sel.options[sel.selectedIndex].value;
    var url = "ForwardNote.do?command=UpdateUserList&deptId=" + escape(value);
    window.frames['server_commands'].location.href=url;
  }
  
  function selectAllOptions(obj) {
  var size = obj.options.length;
  var i = 0;
  
  if (size == 0) {
    alert ("You must have at least one item in this list.");
    return false;
  }
  
  for (i=0;i<size;i++) {
    obj.options[i].selected = true;
  }
  
  return true;
}
  
  function switchList(form, thisAction) {
    var index;
    var copyValue;
    var copyText;
    var selectedListLength;
    var selTotalListLength;
  
  
    if(thisAction == "add" && form.selTotalList.options.length > 0 && form.selTotalList.options.selectedIndex != -1) {
      index = form.selTotalList.selectedIndex;
      copyValue = form.selTotalList.options[index].value;
      copyText = form.selTotalList.options[index].text;
      
	selectedListLength = form.selectedList.options.length;
	
	if (selectedListLength > 0 && form.selectedList.options[0].value == "") {
		form.selectedList.options[0] = null;
		selectedListLength--;
	}
	
	form.selTotalList.options[index] = null;
	//form.selectedList.options.length += 1;
	form.selectedList.options[selectedListLength] = new Option(copyText, copyValue);
    }
  
    if(thisAction == "remove" && form.selectedList.options.length > 0 && form.selectedList.options.selectedIndex != -1) {
      index = form.selectedList.selectedIndex;
      copyValue = form.selectedList.options[index].value;
      copyText = form.selectedList.options[index].text;
      
      selTotalListLength = form.selTotalList.options.length;
      
      var id_array = form.hiddendept.value.split("|");
      if ( inArray(id_array, copyValue) ) {
        form.selectedList.options[index] = null;
	form.selTotalList.options[selTotalListLength] = new Option(copyText, copyValue);
	form.selectedList.blur();
      } else {
	selTotalListLength = form.selTotalList.options.length;
	form.selectedList.options[index] = null;
	form.selectedList.blur();
      }

    }
  }
  
  function inArray(a, s) {
	var i = 0;
	
	for(i=0; i < a.length; i++) {
		if (a[i] == s) {
			return true;
		}
	}
	
	return false;
  }

</script>
<form name="ForwardForm" action="/ForwardNote.do?command=Forward&auto-populate=true&return=<%= java.net.URLEncoder.encode(request.getParameter("return")) %>" method=POST onsubmit="selectAllOptions(document.ForwardForm.selectedList)">
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan=2 valign=center align=left>
      <strong>CFS Message Details</strong>
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap valign="top" class="formLabel">
      Send To
    </td>
    
    <td width=100%>
    	<table border="0" width="100%" cellpadding="0" cellspacing="0">
		<tr>
		<td width="33%" align="center">Select a Department</td>
		<td width="33%" align="center">Choose Recipients</td>
		<td width="33%" align="center" valign="top">Added Recipients</td>
		</tr>

	    <tr>
	    <td width=33%>
	    
		<% DepartmentList.setSelectSize(10); %>
		<% DepartmentList.setJsEvent("onchange=\"updateUserList();\""); %>
		<%= DepartmentList.getHtmlSelect("selDepartment", 0) %>
		
	    </td>
	    
	    <td width="33%" align="center">
		<select size='10' name='selTotalList' onChange="switchList(this.form, 'add')">
		<option value="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<option>
		</select>
	    </td>
	    
	    <td width="33%" align="center"><font size="2">
		<select multiple size='10' name='selectedList' onChange="switchList(this.form, 'remove')">
		<option value="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
		</select>
		</font>
	    </td>
	    </tr>
	</table>
	
    </td>

  </tr>
  
    <tr class="containerBody">
    <td nowrap class="formLabel">
      Email Copy to Recipient(s)
    </td>
    <td width=100%>
      <input type=checkbox name="email1">
      <iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
      <input type="hidden" name="hiddendept" value="">
    </td>
  </tr>

  <tr class="containerBody">
    <td nowrap class="formLabel">
      Subject
    </td>
    <td width=100%>
      <input type=text name="subject" value="<%= toHtmlValue(NoteDetails.getSubject()) %>" size=50>
      <font color="red">*</font> <%= showAttribute(request, "messageSubjectError") %>
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap valign="top" class="formLabel">
      Text
    </td>
    <td width=100%>
      <textarea name="body" rows=10 style="width:100%;"><%= NoteDetails.getBody() %></textarea>
    </td>
  </tr>
</table>
<br>
<input type="submit" value="Send">
<input type="button" value="Cancel" onClick="javascript:window.close();">
</form>
