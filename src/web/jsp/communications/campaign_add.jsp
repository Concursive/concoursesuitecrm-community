<jsp:useBean id="Campaign" class="com.darkhorseventures.cfsbase.Campaign" scope="request"/>
<%@ include file="initPage.jsp" %>
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
<form name="addForm" action="/CampaignManager.do?command=Insert&auto-populate=true" method="post" onSubmit="return checkForm(this);">
  <input type="hidden" name="dosubmit" value="true">
  <input type="submit" value="Insert" name="Save">
  <input type="submit" value="Cancel" onClick="javascript:this.form.dosubmit.value='false';this.form.action='/CampaignManager.do?command=View';">
  <input type="reset" value="Reset">
  <br>
  &nbsp;
  <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
    <tr class="title">
      <td valign=center colspan=2 align=left>
        <strong>Begin by naming the campaign</strong>
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
      <td nowrap class="formLabel">
        Description
      </td>
      <td>
        <TEXTAREA NAME='description' ROWS=3 COLS=50><%= toString(Campaign.getDescription()) %></TEXTAREA>
      </td>
    </tr>
  </table>
  <br>
  <input type="submit" value="Insert" name="Save">
  <input type="submit" value="Cancel" onClick="javascript:this.form.dosubmit.value='false';this.form.action='/CampaignManager.do?command=View';">
  <input type="reset" value="Reset">
</form>
</body>
