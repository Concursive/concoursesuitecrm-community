<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.help.base.*" %>
<jsp:useBean id="HelpContents" class="org.aspcfs.modules.help.base.HelpContents" scope="request"/>
<jsp:useBean id="Help" class="org.aspcfs.modules.help.base.HelpItem" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body bgcolor='#FFFFFF' onLoad="document.inputForm.description.focus();">
<script language="JavaScript">
  function checkForm(form) {
    if (form.dosubmit.value == "false") {
      return true;
    }
    var formTest = true;
  
    //Check required fields
    if (form.description.value == "") {    
      form.dosubmit.value = "true";
      alert("Description is required, please verify then try submitting the information again.");
      formTest = false;
    }
  
    if (formTest == false) {
      return false;
    } else {
      return true;
    }
  }
</script>
<form method="post" name="inputForm" action="Help.do?command=Process&auto-populate=true" onSubmit="return checkForm(this);">
  <table border="0" width="100%" cellspacing="0" cellpadding="4">
    <tr class="title">
      <td>
        <font color="#000000"><b>Help for this page</b></font>
      </td>
    </tr>
    <tr>
      <td>
        Module: <b><%= toHtmlValue(Help.getModule()) %></b> 
      </td>
    </tr>
    <tr>
      <td>
        Section: <b><%= toHtmlValue(Help.getSection()) %></b>
      </td>
    </tr>
    <tr>
      <td>
        Sub-section: <b><%= toHtmlValue(Help.getSubsection()) %></b>
      </td>
    </tr>
    <tr>
      <td>
        Entered: <dhv:username id="<%= Help.getEnteredBy() %>"/> <%= toDateTimeString(Help.getEntered()) %>
      </td>
    </tr>
    <tr>
      <td>
        Modified: <dhv:username id="<%= Help.getModifiedBy() %>"/> <%= toDateTimeString(Help.getModified()) %>
      </td>
    </tr>
    <tr>
      <td>
        Help:<br>
        <textarea rows='18' name='description' cols='50'><%= toString(Help.getDescription()) %></textarea>
      </td>
    </tr>      
    <tr>
      <td>
          <input type="submit" value="Update">
          <input type="submit" value="Cancel" onClick="javascript:this.form.action='';this.form.dosubmit.value='false';window.close();">
      </td>
    </tr>
  </table>
  <input type="hidden" name="id" value="<%= Help.getId() %>">
  <input type="hidden" name="module" value="<%= Help.getModule() %>">
  <input type="hidden" name="section" value="<%= Help.getSection() %>">
  <input type="hidden" name="subsection" value="<%= Help.getSubsection() %>">
  <input type="hidden" name="dosubmit" value="true">
</form>  
</body>
