<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
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
