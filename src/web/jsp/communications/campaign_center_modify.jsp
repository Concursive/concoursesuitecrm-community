<jsp:useBean id="Campaign" class="org.aspcfs.modules.communications.base.Campaign" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/popCalendar.js"></script>
<script language="JavaScript">
  function checkForm(form) {
    if (form.dosubmit.value == "false") {
      return true;
    }
    formTest = true;
    message = "";
    if (form.name.value == "") { 
      message += "- Campaign name is requred\r\n";
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
</script>
<body onLoad="javascript:document.forms[0].name.focus();">
<a href="CampaignManager.do">Communications Manager</a> > 
<a href="CampaignManager.do?command=View">Campaign List</a> >
<a href="CampaignManager.do?command=ViewDetails&id=<%= Campaign.getId() %>">Campaign Details</a> >
Modify
<hr color="#BFBFBB" noshade>
<form name="addForm" action="CampaignManager.do?command=Update&auto-populate=true" method="post" onSubmit="return checkForm(this);">
  <input type="hidden" name="dosubmit" value="true">
  <input type="hidden" name="id" value="<%= Campaign.getId() %>">
  <input type="hidden" name="modified" value="<%= Campaign.getModified() %>">
  <input type="submit" value="Update" name="Save">
  <input type="submit" value="Cancel" onClick="javascript:this.form.dosubmit.value='false';this.form.action='CampaignManager.do?command=ViewDetails';">
  <input type="reset" value="Reset">
  <br>
  <%= showError(request, "actionError") %>
  <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
    <tr class="title">
      <td valign=center colspan=2 align=left>
        <strong>Modify base campaign information</strong>
      </td>
    </tr>
    <tr>
      <td nowrap class="formLabel">
       Campaign Name
      </td>
      <td>
        <input type=text size=35 name="name" value="<%= toHtmlValue(Campaign.getName()) %>"><font color="red">*</font> <%= showAttribute(request, "nameError") %>
      </td>
    </tr>
    <tr>
      <td nowrap class="formLabel" valign="top">
        Description
      </td>
      <td>
        <TEXTAREA NAME="description" ROWS="3" COLS="50"><%= toString(Campaign.getDescription()) %></TEXTAREA>
      </td>
    </tr>
  </table>
  <br>
  <input type="submit" value="Update" name="Save">
  <input type="submit" value="Cancel" onClick="javascript:this.form.dosubmit.value='false';this.form.action='CampaignManager.do?command=ViewDetails';">
  <input type="reset" value="Reset">
</form>
</body>
