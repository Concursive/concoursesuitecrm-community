<%-- 
  - Copyright(c) 2004 Team Elements LLC (http://www.teamelements.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Team Elements LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. TEAM ELEMENTS
  - LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL TEAM ELEMENTS LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Author(s): Matt Rajkowski
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="editList" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="subTitle" class="java.lang.String" scope="request"/>
<jsp:useBean id="returnUrl" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" type="text/javascript" src="javascript/editListForm.js"></script>
<script language="JavaScript" type="text/javascript">
  function doCheck() {
    var test = document.modifyList.selectedList;
    if (test != null) {
      return selectAllOptions(document.modifyList.selectedList);
    }
  }
</script>
<body onLoad="javascript:document.forms['modifyList'].newValue.focus();">
<form name="modifyList" method="post" action="<%= returnUrl %>" onSubmit="return doCheck();">
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th colspan="3">
      <%= toHtml(subTitle) %>
    </th>
  </tr>
  <tr class="containerBody">
    <td width="50%">
      <table width="100%" cellspacing="0" cellpadding="2" border="0" class="empty">
        <tr>
          <td valign="center">
            <dhv:label name="admin.newOption">New Option</dhv:label>
          </td>
        </tr>
        <tr>
          <td valign="center">
            <input type="text" name="newValue" value="" size="25" maxlength="125">
          </td>
        </tr>
        <tr>
          <td valign="center">
            <input type="button" name="addButton" value="<dhv:label name="accounts.accounts_reports_generate.AddR">Add ></dhv:label>" onclick="javascript:addValues()">
          </td>
        </tr>
      </table>
    </td>
    <td width="25">
      <table width="100%" cellspacing="0" cellpadding="2" border="0" class="empty">
        <tr>
          <td valign="center">
            <input type="button" value="<dhv:label name="global.button.Up">Up</dhv:label>" onclick="javascript:moveOptionUp(document.modifyList.selectedList)">
          </td>
        </tr>
        <tr>
          <td valign="center">
            <input type="button" value="<dhv:label name="global.button.Down">Down</dhv:label>" onclick="javascript:moveOptionDown(document.modifyList.selectedList)">
          </td>
        </tr>
        <tr>
          <td valign="center">
            <input type="button" value="<dhv:label name="button.remove">Remove</dhv:label>" onclick="javascript:removeValues()">
          </td>
        </tr>
        <tr>
          <td valign="center">
            <input type="button" value="<dhv:label name="button.sort">Sort</dhv:label>" onclick="javascript:sortSelect(document.modifyList.selectedList)">
          </td>
        </tr>
        <tr>
          <td valign="center">
            <input type="button" value="Rename" onclick="javascript:switchToRename()">
          </td>
        </tr>
      </table>
    </td>
    <td width="50%">
<%
  editList.setSelectSize(8);
  editList.setMultiple(true);
%>
      <%= editList.getHtml("selectedList",0) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td colspan="3">
      <input type="hidden" name="selectNames" value="" />
      <dhv:evaluate if="<%= request.getParameter("popup") != null %>">
        <input type="hidden" name="popup" value="true" />
      </dhv:evaluate>
      <dhv:evaluate if="<%= request.getParameter("form") != null %>">
        <input type="hidden" name="form" value="<%= request.getParameter("form") %>" />
      </dhv:evaluate>
      <dhv:evaluate if="<%= request.getParameter("field") != null %>">
        <input type="hidden" name="field" value="<%= request.getParameter("field") %>" />
      </dhv:evaluate>
      <input type="submit" value="<dhv:label name="button.saveChanges">Save Changes</dhv:label>" />
      <dhv:evaluate if="<%= request.getParameter("popup") != null %>">
        <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.close()" />
      </dhv:evaluate>
    </td>
  </tr>
</table>
</form>
