<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="SelectedList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="moduleId" class="java.lang.String" scope="request"/>
<jsp:useBean id="SubTitle" class="java.lang.String" scope="request"/>
<jsp:useBean id="category" class="java.lang.String" scope="request"/>
<script language="JavaScript" type="text/javascript" src="javascript/editListForm.js"></script>
<script language="JavaScript" type="text/javascript">
  function doCheck() {
    if (document.modifyList.dosubmit.value == "false") {
      return true;
    }
    var test = document.modifyList.selectedList;
    if (test != null) {
      return selectAllOptions(document.modifyList.selectedList);
    }
  }
</script>
<form name="modifyList" method="post" action="Admin.do?command=UpdateList" onSubmit="return doCheck();">
<a href="Admin.do">Setup</a> >
<a href="Admin.do?command=Config">Configure Modules</a> >
<a href="Admin.do?command=ConfigDetails&moduleId=<%=moduleId%>">Configuration Options</a> >
<a href="Admin.do?command=EditLists&moduleId=<%=moduleId%>">Lookup Lists</a> > 
Edit List<br>
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="3">
      <strong><%= SubTitle %></strong>
    </td>
  </tr>
  <tr>
    <td width="50%">
      <table width="100%" cellspacing="0" cellpadding="2" border="0">
        <tr>
          <td valign="center">
            New Option
          </td>
        </tr>
        <tr>
          <td valign="center">
            <input type="text" name="newValue" value="" size="25" maxlength="125">
          </td>
        </tr>
        <tr>
          <td valign="center">
            <input type="button" value="Add >" onclick="javascript:addValues()">
          </td>
        </tr>
      </table>
    </td>
    <td width="25">
      <table width="100%" cellspacing="0" cellpadding="2" border="0">
        <tr>
          <td valign="center">
            <input type="button" value="Up" onclick="javascript:moveOptionUp(document.modifyList.selectedList)">
          </td>
        </tr>
        <tr>
          <td valign="center">
            <input type="button" value="Down" onclick="javascript:moveOptionDown(document.modifyList.selectedList)">
          </td>
        </tr>
        <tr>
          <td valign="center">
            <input type="button" value="Remove" onclick="javascript:removeValues()">
          </td>
        </tr>
        <tr>
          <td valign="center">
            <input type="button" value="Sort" onclick="javascript:sortSelect(document.modifyList.selectedList)">
          </td>
        </tr>
      </table>
    </td>
    <td width="50%">
      <%= SelectedList.getHtmlSelect("selectedList",0) %>
    </td>
  </tr>
  <tr>
    <td colspan="3">
      <input type="hidden" name="selectNames" value="">
      <input type="hidden" name="moduleId" value="<%= moduleId %>">
      <input type="hidden" name="dosubmit" value="true">
      <input type="hidden" name="tableName" value="<%= SelectedList.getTableName() %>">
      <input type="hidden" name="category" value="<%= category %>">
      <input type="submit" value="Save Changes" onClick="javascript:this.form.dosubmit.value='true';">
      <input type="submit" value="Cancel" onClick="javascript:this.form.dosubmit.value='false';this.form.action='Admin.do?command=EditLists'">
    </td>
  </tr>
</table>
</form>
