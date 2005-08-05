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
<%@ page import="org.aspcfs.utils.web.HtmlSelectLanguage" %>
<%@ page import="org.aspcfs.utils.web.HtmlSelect" %>
<script language="JavaScript">
  function checkForm(form) {
    if (form.language.value == "-1") {
      alert(label("select.language","Please select a language to continue"));
      return false;
    }
    return true;
  }
</script>
<form name="language" action="Setup.do?command=Welcome" method="post" onsubmit="return checkForm(this);">
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th><dhv:label name="setup.centricCRMInstallation">Centric CRM Installation</dhv:label></th>
  </tr>
  <tr>
    <td>
      <dhv:label name="setup.chooseLanguageToContinue.colon">Choose a language to continue:</dhv:label><br />
      <br />
      <% HtmlSelect selectLanguage = HtmlSelectLanguage.getSelect("language", "None Selected");
         selectLanguage.addItem(-1, "None Selected", 0); %><%= selectLanguage.getHtml("language", "None Selected") %>
      <input type="submit" value=">"/><br />
    </td>
  </tr>
</table>
</form>
