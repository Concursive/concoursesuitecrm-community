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
<jsp:useBean id="Feature" class="org.aspcfs.modules.help.base.HelpFeature" scope="request"/>
<jsp:useBean id="GeneralFeatures" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript">
function validate() {
    formTest = true;
    message = "";
    if(document.forms[0].description.value == "" && document.forms[0].linkFeatureId.selectedIndex == 0){
		    message += "- Description is required\r\n";
        formTest = false;
    }
    if(document.forms[0].description.value != "" && document.forms[0].linkFeatureId.selectedIndex > 0){
		    message += "- Only a single description is allowed\r\n";
        formTest = false;
    }
    if (formTest) {
      return true;
    } else {
      alert("Form could not be saved, please check the following:\r\n\r\n" + message);
      return false;
    }
  }
</script>
<body onLoad="javascript:document.forms[0].description.focus();">
<form name="saveFeature" action="HelpFeatures.do?command=SaveFeature&id=<%= Feature.getId() %>&auto-populate=true" method="post" onSubmit="return validate();">
<table cellpadding="4" cellspacing="0" width="100%" class="details">
<tr>
  <th colspan="2">
    <strong>Feature</strong>
  </th>
</tr>
<tr class="containerBody">
  <td class="formLabel" valign="top">
    Description
  </td>
  <td>
    <%-- Description for a linked general feature comes from the lookup --%>
    <input type="text" name="description" value="<%= (Feature.getDescription() != null && Feature.getLinkFeatureId() == -1) ?  toHtml(Feature.getDescription()) : ""%>" size="60">
    <br><br><center>-----------OR-----------</center><br>
    <% GeneralFeatures.addItem(-1, "None"); %>
    <%= GeneralFeatures.getHtmlSelect("linkFeatureId", Feature.getLinkFeatureId()) %>
   </td>
</tr>
<tr class="containerBody">
  <td class="formLabel">
    Complete
  </td>
  <td>
    <input type="checkbox" name="complete" value="true" <%= Feature.getComplete() ?  " checked" : ""%>>
   </td>
</tr>
<tr class="containerBody">
  <td class="formLabel">
    Level
  </td>
  <td>
    <input type="text" name="level" value="<%= (Feature.getLevel() == -1) ? 0 : Feature.getLevel() %>" size="2">
   </td>
</tr>

</table>
<br>
Where do you want to go after this action?<br>
<input type="radio" name="target" value="loop" <%= "loop".equals(request.getParameter("target")) ? " checked" : "" %>>&nbsp;Add another feature&nbsp;&nbsp;
<input type="radio" name="target" value="return" <%= "loop".equals(request.getParameter("target")) ? "" : " checked" %>>&nbsp;Return to QA Page
<br><br>
<input type="submit" value="Save">
<input type="button" value="Cancel" onClick="javascript:window.close();">
<dhv:evaluate if="<%= Feature.getId() > 0 %>">
  <input type="hidden" name="modified" value="<%= Feature.getModified() %>">
</dhv:evaluate>
<%= addHiddenParams(request, "popup|linkHelpId") %>
</form>
</body>
