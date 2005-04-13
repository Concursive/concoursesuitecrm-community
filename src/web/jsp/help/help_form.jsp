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
      alert(label("description.required.resubmit","Description is required, please verify then try submitting the information again."));
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
        <font color="#000000"><b><dhv:label name="help.helpForThisPage">Help for this page</dhv:label></b></font>
      </td>
    </tr>
    <tr>
      <td>
        <dhv:label name="admin.module.colon">Module:</dhv:label>
        <b><%= toHtmlValue(Help.getModule()) %></b>
      </td>
    </tr>
    <tr>
      <td>
        <dhv:label name="help.section.colon" param="<%= "help.section="+toHtml(Help.getSection()) %>">Section: <b><%= toHtml(Help.getSection()) %></b></dhv:label>
      </td>
    </tr>
    <tr>
      <td>
        <dhv:label name="help.subsection.colon" param="<%= "help.subsection="+toHtml(Help.getSubsection()) %>">Sub-section: <b><%= toHtml(Help.getSubsection()) %></b></dhv:label>
      </td>
    </tr>
    <tr>
      <td>
        <dhv:label name="accounts.accounts_calls_list.Entered">Entered</dhv:label>: <dhv:username id="<%= Help.getEnteredBy() %>"/> <%= toDateTimeString(Help.getEntered()) %>
      </td>
    </tr>
    <tr>
      <td>
        <dhv:label name="accounts.accounts_contacts_calls_details.Modified">Modified</dhv:label>: <dhv:username id="<%= Help.getModifiedBy() %>"/> <%= toDateTimeString(Help.getModified()) %>
      </td>
    </tr>
    <tr>
      <td>
        <dhv:label name="help.help.colon">Help:</dhv:label><br />
        <textarea rows='18' name='description' cols='50'><%= toString(Help.getDescription()) %></textarea>
      </td>
    </tr>      
    <tr>
      <td>
        <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>">
        <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='';this.form.dosubmit.value='false';window.close();">
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
