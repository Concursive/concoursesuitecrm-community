<%@ page import="java.util.*,org.aspcfs.modules.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="DepartmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CurrentTeam" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="vector1" class="java.lang.String" scope="request"/>
<jsp:useBean id="vector2" class="java.lang.String" scope="request"/>
<jsp:useBean id="vector3" class="java.lang.String" scope="request"/>
<jsp:useBean id="vector4" class="java.lang.String" scope="request"/>
<jsp:useBean id="vector5" class="java.lang.String" scope="request"/>
<jsp:useBean id="UserSize" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%
  CurrentTeam.setSelectSize(10);
  CurrentTeam.setJsEvent("onChange=\"switchList(this.form, 'remove')\"");
%>
<script language="JavaScript">
  var vector1, vector2, vector3, vector4, vector5;
  var arIndex = <%= UserSize %>;
  var newIndex;
  var newSubIndex;
  vector1 = "<%= vector1 %>".split("|");<%-- First/Last name --%>
  vector2 = "<%= vector2 %>".split("|");<%-- Department id --%>
  vector3 = "<%= vector3 %>".split("|");<%-- User id --%>
  vector4 = "<%= vector4 %>".split("|");<%-- Saved state of member on team --%>
  vector5 = "<%= vector5 %>".split("|");<%-- Current state of member on team --%>
  
  var i; //counter var
  var j; //counter var
  
  function changeDept(form) {
    form.selTotalList.options.length = 0;
  }
  
  function updateUserList() {
    var sel = document.forms['projectMemberForm'].elements['selDepartment'];
    var value = sel.options[sel.selectedIndex].value;
    var url = "ProjectManagementTeam.do?command=UpdateUserList&deptId=" + escape(value);
    window.frames['server_commands'].location.href=url;
  }
  
  function switchList(form, thisAction) {
    var index;
    var copyValue;
    var copyText;
    var selProjectListLength;
    var selTotalListLength;
  
  
    if(thisAction == "add" && form.selTotalList.options.length > 0 && form.selTotalList.options.selectedIndex != -1) {
      index = form.selTotalList.selectedIndex;
      copyValue = form.selTotalList.options[index].value;
      copyText = form.selTotalList.options[index].text;
      checkListIntegrity(form, thisAction, index, copyValue, copyText);
    }
  
    if(thisAction == "remove" && form.selProjectList.options.length > 0 && form.selProjectList.options.selectedIndex != -1) {
      index = form.selProjectList.selectedIndex;
      copyValue = form.selProjectList.options[index].value;
      copyText = form.selProjectList.options[index].text;
      checkListIntegrity(form, thisAction, index, copyValue, copyText);
    }
  }
  
  function checkListIntegrity(form, thisAction, index, copyValue, copyText) {
    for(i = 0; i < arIndex; i++) {
      if(thisAction == "add" && vector1[i] == copyText) {
        if(vector5[i] == "0") {
          selProjectListLength = form.selProjectList.options.length;
          form.selTotalList.options[index] = null;
          form.selProjectList.options.length += 1;
          form.selProjectList.options[selProjectListLength] = new Option(copyText, copyValue);   
          vector5[i] = "1";
        }
      }
      if(thisAction == "remove" && vector1[i] == copyText) {
        if(vector5[i] == "1") {
          var thisUser = new getUser(buildUser(copyText));
  
          if(form.selDepartment.selectedIndex > -1 && form.selDepartment.options[form.selDepartment.selectedIndex].value == thisUser.deptName) {
            selTotalListLength = form.selTotalList.options.length;
            form.selProjectList.options[index] = null;
            form.selTotalList.options.length += 1;
            form.selTotalList.options[selTotalListLength] = new Option(copyText, copyValue);
            vector5[i] = "0";
            form.selProjectList.blur();
          } else {
            selTotalListLength = form.selTotalList.options.length;
            form.selProjectList.options[index] = null;
            vector5[i] = "0";
            form.selProjectList.blur();
          }
        }
      }
    }
  }
  
  function buildUser(userName) {
    var x, i;
    x = null;
    for(i = 0; i < arIndex; i++) {
      if(userName == vector1[i]) {
        x = i;
      }
    }
    return x;
  }

  function getUser(i) {
    this.fullName = vector1[i];  //vector1-- UserFullName
    this.deptName = vector2[i];  //vector2 -- Department Name
    this.userID = vector3[i];  //vector3 -- userID
    this.origConfig = vector4[i];  //vector4 -- Original List Config
    this.newConfig = vector5[i];  //vector5 -- New/Updated List Config
  }
  
  function resetValues(form) {
    var x;
    var newIndexTotal, newIndexProject; //counter values to find the appropriate size values for the lists.
    newIndexTotal = 0;
    newIndexProject = 0;
  
    for(x = 0; x < arIndex; x++) {
      vector5[x] = vector4[x];  
      if(vector5[x] == "1" && vector2[x] == "-2") {
        newIndexProject++;
      } else if(vector5[x] == "0" && vector2[x] == "-2") {
        newIndexTotal++;
      }
    }
  
    form.selTotalList.length = 0;
    form.selTotalList.length = newIndexTotal;
    form.selProjectList.length = 0;
    form.selProjectList.length = newIndexProject;
   
    var runningCtr1, runningCtr2;
    runningCtr1 = 0;
    runningCtr2 = 0;
  
    form.selDepartment.options[0].selected = true;
    var thisUser;
    for(x = 0; x < arIndex; x++) {
      thisUser = new getUser(x);
      if(thisUser.origConfig == "0" && thisUser.deptName == "-2") {
        form.selTotalList.options[runningCtr1] = new Option(thisUser.fullName, thisUser.userID);
        runningCtr1++;
      } else if(thisUser.origConfig == "1") {
        form.selProjectList.options[runningCtr2] = new Option(thisUser.fullName, thisUser.userID);
        runningCtr2++;
      }
    }
    form.insertMembers.value = "";
    form.deleteMembers.value = "";
    
    updateUserList();
  }
  
  function checkSubmit(form) {
    var x;
    var thisUser;
    form.insertMembers.value = "";
    form.deleteMembers.value = "";
    for(x = 0; x < arIndex; x++) {
      thisUser = new getUser(x);
      if(thisUser.newConfig != thisUser.origConfig) {
        if(thisUser.newConfig == "1") {
          form.insertMembers.value += thisUser.userID + "|";
        }
        if(thisUser.newConfig == "0") {
          form.deleteMembers.value += thisUser.userID + "|";
        }
      }
    }
  }
</script>
<body bgcolor='#FFFFFF' onLoad="changeDept(document.all.projectMemberForm)">
<table border="0" width="100%" cellpadding="0" cellspacing="0">
  <form name="projectMemberForm" method="post" action="ProjectManagementTeam.do?command=Update&pid=<%= Project.getId() %>&auto-populate=true">
    <tr>
      <td width='2' bgcolor='#808080'>&nbsp;</td>
      <td width="100%" colspan="3" rowspan="2" bgcolor="#808080">
        <font color='#FFFFFF'><b>&nbsp;Modify Team Members</b></font>
      </td>
      <td width='2' bgcolor='#808080'>&nbsp;</td>
    </tr>
    <tr>
      <td width='2' bgcolor='#808080'>&nbsp;</td>
      <td width='2' bgcolor='#808080'>&nbsp;</td>
    </tr>
    <tr>
      <td width='2' bgcolor='#808080'>&nbsp;</td>
      <td width='100%' colspan='3'>&nbsp;</td>
      <td width='2' bgcolor='#808080'>&nbsp;</td>
    </tr>
    <tr>
      <td width='2' bgcolor='#808080'>&nbsp;</td>
      <td width="33%" align="center">Select a Department</td>
      <td width="33%" align="center">Choose Team Members</td>
      <td width="33%" align="center" valign="top">Added Team Members</td>
      <td width='2' bgcolor='#808080'>&nbsp;</td>
    </tr>
    <tr>
      <td width='2' bgcolor='#808080'>&nbsp;</td>
      <td width="33%" align="center">
  <% DepartmentList.setSelectSize(10); %>
	<% DepartmentList.setJsEvent("onchange=\"updateUserList();\""); %>
	<%= DepartmentList.getHtmlSelect("selDepartment", -1) %>
      </td>
      <td width="33%" align="center">
        <select size='10' name='selTotalList' onChange="switchList(this.form, 'add')" style="width:150px">
        </select>
      </td>
      <td width="33%" align="center"><font size="2"><%= CurrentTeam.getHtml("selProjectList", 0) %></font></td>
      <td width='2' bgcolor='#808080'>&nbsp;</td>
    </tr>
    <tr>
      <td width='2' bgcolor='#808080'>&nbsp;</td>
      <td width='100%' colspan='3'>&nbsp;</td>
      <td width='2' bgcolor='#808080'>&nbsp;</td>
      <iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
    </tr>
    <tr>
      <td width='2' bgcolor='#808080'>&nbsp;</td>
      <td colspan="3" width="100%" align="center" bgcolor='#808080' height='30'>
        <input type="hidden" name="insertMembers">
        <input type="hidden" name="deleteMembers">
        <input type="submit" value="Update Team" onClick="checkSubmit(this.form)"> &nbsp;
        <input type="button" value="Restore Values" onClick="resetValues(this.form)"> &nbsp;
        <input type="submit" value="Cancel" onClick="javascript:this.form.action='ProjectManagement.do?command=ProjectCenter&section=Team&pid=<%= Project.getId() %>'">
      </td>
      <td width='2' bgcolor='#808080'>&nbsp;</td>
    </tr>
  </form>
</table>
</body>
