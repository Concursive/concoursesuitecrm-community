<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.help.base.*" %>
<jsp:useBean id="Tip" class="org.aspcfs.modules.help.base.HelpTip" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript">
function validate() {
    formTest = true;
    message = "";
    if(document.forms[0].description.value == ""){
		    message += "- Description is required\r\n";
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
<form name="saveTip" action="HelpTips.do?command=SaveTip&id=<%= Tip.getId() %>&auto-populate=true" method="post" onSubmit="return validate();">
<table cellpadding="4" cellspacing="0" width="100%" class="details">
<tr>
  <th colspan="2">
    <strong>Tip</strong>
  </th>
</tr>
<tr class="containerBody">
  <td class="formLabel">
    Description
  </td>
  <td>
    <input type="text" name="description" value="<%= Tip.getDescription() != null ?  toHtml(Tip.getDescription()) : ""%>" size="60">
   </td>
</tr>
</table>
<br>
Where do you want to go after this action?<br>
<input type="radio" name="target" value="loop" <%= "loop".equals(request.getParameter("target")) ? " checked" : "" %>>&nbsp;Add another feature&nbsp;&nbsp;
<input type="radio" name="target" value="return" <%= "loop".equals(request.getParameter("target")) ? "" : " checked" %>>&nbsp;Return to QA Page
<br><br>
<dhv:evaluate if="<%= Tip.getId() > 0 %>">
  <input type="hidden" name="modified" value="<%= Tip.getModified() %>">
</dhv:evaluate>
<%= addHiddenParams(request, "popup|linkHelpId") %>
<input type="submit" value="Save">
<input type="button" value="Cancel" onClick="javascript:window.close();">
</form>
</body>
