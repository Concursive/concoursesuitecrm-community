<%@ include file="../initPage.jsp" %>
<jsp:useBean id="fileLibrary" class="java.lang.String" scope="request"/>
<body onLoad="javascript:document.forms[0].fileLibrary.focus();">
<script language="JavaScript">
  function checkForm(form) {
    valid = true;
    message = "";
    if (form.fileLibrary.value.length == 0) {
      message += "- Target directory is a required field\r\n";
      valid = false;
    }
    if (valid == false) {
      alert("Form could not be submitted, please check the following:\r\n\r\n" + message);
      return false;
    }
    return true;
  }
</script>
<%= showError(request, "actionError", false) %>
<form name="configure" action="SetupDirectory.do?command=ConfigureDirectory" method="post" onSubmit="return checkForm(this)">
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th>
      Dark Horse CRM Configuration (Step 1 of 4)<br>
      File Library Settings
    </th>
  </tr>
  <tr>
    <td>
      Users will have the capability to upload and create files on the server.<br>
      Where should Dark Horse CRM store files that get created?<br>
      <br>
      - If the target directory does not exist it will be created
      - The target directory should have plenty of free storage space for uploads<br>
      - The target directory must have write permissions<br>
      - The target directory should be backed up often to prevent data loss<br>
      - The target directory should not be located in the servlet path to make upgrades easier<br>
      <br>
      <br>
      <table border="0" class="empty">
        <tr>
          <td class="formLabel">
            Target Directory:
          </td>
          <td nowrap>
            <input type="text" name="fileLibrary" value="<%= toHtmlValue(fileLibrary) %>" size="50"/><br>
            (ex: Linux <b>/var/lib/dh_crm/fileLibrary</b> -- Windows <b>c:\dh_crm\fileLibrary</b>)
          </td>
        </tr>
      </table>
      <br>
      <input type="submit" value="Continue >"/>
    </td>
  </tr>
</table>
</form>
</body>
