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
  - Version: $Id: $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<jsp:useBean id="pageGroup" class="org.aspcfs.modules.website.base.PageGroup" scope="request"/>
<jsp:useBean id="thisPage" class="org.aspcfs.modules.website.base.Page" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<script type="text/javascript" src="javascript/popURL.js"></script>
<script language="JavaScript" type="text/javascript">
	function checkForm(form) {
		var flag = true;
		message = "";
		if (!flag) {
        alert(label("check.form","Form could not be saved, please check the following:\r\n\r\n") + message);
	      return false;
		} else {
			return true;
		}
	}
  function setAlias(pageId) {
    document.getElementById("alias").value = pageId;
  }
</script>
<body onLoad="document.website.name.focus();">
<form name="website" action="Pages.do?command=Save&auto-populate=true&popup=true" onSubmit="return checkForm(this);" method="post">
<input type="hidden" name="id" value="<%= thisPage.getId() %>"/>
<input type="hidden" name="pageGroupId" value="<%= thisPage.getPageGroupId() %>"/>
<input type="hidden" name="position" value="<%= thisPage.getPosition() %>"/>
<input type="hidden" name="nextPageId" value="<%= thisPage.getNextPageId() %>"/>
<input type="hidden" name="tabBannerId" value="<%= thisPage.getTabBannerId() %>"/>
<input type="hidden" name="modified" value="<%= thisPage.getModified() %>">
<input type="hidden" name="activePageVersionId" value="<%= thisPage.getActivePageVersionId() %>">
<input type="hidden" name="constructionPageVersionId" value="<%= thisPage.getConstructionPageVersionId() %>">
<input type="hidden" name="previousPageId" value="<%= thisPage.getPreviousPageId() %>"/>
<dhv:formMessage showSpace="false"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <dhv:evaluate if="<%= thisPage.getId() > -1 %>">
        <strong><dhv:label name="">Modify Page</dhv:label></strong>
      </dhv:evaluate><dhv:evaluate if="<%= thisPage.getId() == -1 %>">
            <strong><dhv:label name="">Add Page</dhv:label></strong>
      </dhv:evaluate>
    </th>
  </tr>
	<tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="website.page.name">Name</dhv:label>
    </td>
		<td>
      <table border="0" cellpadding="0" cellspacing="0" class="empty"><tr><td valign="top">
        <input type="text" size="35" maxlength="300" name="name" value="<%= toHtmlValue(thisPage.getName()) %>"><font color="red">*</font>
        <br />
        <dhv:label name="website.page.meta.title">(This will become the page's Meta-Title)</dhv:label>&nbsp;
        <%= showAttribute(request, "nameError") %>
      </td></tr></table>
		</td>
	</tr>
	<tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="product.enabled">Enabled</dhv:label>
    </td>
    <td>
     <input type="checkbox" name="enabled" value="true" <%= thisPage.getEnabled()?"checked":"" %> />
    </td>
  </tr>
	<tr class="containerBody">
		<td nowrap class="formLabel">
			<dhv:label name="website.page.internal.description">Internal Description</dhv:label>
		</td>
		<td valign="top">
      <table border="0" cellpadding="0" cellspacing="0" class="empty"><tr><td valign="top">
        <TEXTAREA NAME="notes" ROWS="3" COLS="50"><%= toString(thisPage.getNotes()) %></TEXTAREA>
        <br />
        <dhv:label name="website.page.meta.description">(This will become the page's Meta-Description)</dhv:label>&nbsp;
      </td>
      </tr>
      </table>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="website.page.keywords">Keywords</dhv:label>
    </td>
		<td>
      <table border="0" cellpadding="0" cellspacing="0" class="empty"><tr><td valign="top">
        <input type="text" size="35" maxlength="300" name="keywords" value="<%= toHtmlValue(thisPage.getKeywords()) %>">
        <br />
        <dhv:label name="website.page.meta.keywords">(This will become the page's Meta-Keywords)</dhv:label>&nbsp;
      </td>
      </tr></table>
		</td>
	</tr>
	<tr class="containerBody">
		<td nowrap class="formLabel">
			<dhv:label name="">Page Alias</dhv:label>
		</td>
		<td>
			Reference the content from page:
      <input type="text" size="12" maxlength="20" id="alias" name="alias" value='<%= thisPage.getAlias() > -1 ? String.valueOf(thisPage.getAlias()) : "" %>'>
      [<a href="javascript:popURL('Pages.do?command=SelectAlias&pageGroupId=<%= thisPage.getPageGroupId() %>&popup=true','PageAlias',600,600,'yes','yes');">select</a>]
    </td>
	</tr>
</table>
<br />
<dhv:evaluate if="<%= thisPage.getId() > -1 %>">
  <input type="submit" value="<dhv:label name="button.update">Update</dhv:label>" name="Save"/>
</dhv:evaluate><dhv:evaluate if="<%= thisPage.getId() == -1 %>">
  <input type="submit" value="<dhv:label name="button.save">Save</dhv:label>" name="Save"/>
</dhv:evaluate>
<input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:self.close();">
</form>
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
</body>
