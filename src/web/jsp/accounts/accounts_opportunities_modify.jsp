<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="OrgDetails" class="com.darkhorseventures.cfsbase.Organization" scope="request"/>
<jsp:useBean id="OppDetails" class="com.darkhorseventures.cfsbase.Opportunity" scope="request"/>
<jsp:useBean id="BusTypeList" class="com.darkhorseventures.webutils.HtmlSelect" scope="request"/>
<jsp:useBean id="StageList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="UnitTypeList" class="com.darkhorseventures.webutils.HtmlSelect" scope="request"/>
<jsp:useBean id="UserList" class="com.darkhorseventures.cfsbase.UserList" scope="request"/>
<%@ include file="initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/popCalendar.js"></script>
<script language="JavaScript">
  function checkForm(form) {
      formTest = true;
      message = "";
      if ((!form.closeDate.value == "") && (!checkDate(form.closeDate.value))) { 
        message += "- Check that Est. Close Date is entered correctly\r\n";
        formTest = false;
      }
      if ((!form.alertDate.value == "") && (!checkDate(form.alertDate.value))) { 
        message += "- Check that Alert Date is entered correctly\r\n";
        formTest = false;
      }
      if (formTest == false) {
        alert("Form could not be saved, please check the following:\r\n\r\n" + message);
        return false;
      } else {
        return true;
      }
    }
</script>
<form name="updateOpp" action="/Opportunities.do?command=Update&auto-populate=true" method="post" onSubmit="return checkForm(this);">
<a href="Opportunities.do?command=View&orgId=<%= OrgDetails.getOrgId() %>">Back to Opportunities List</a><br>&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(OrgDetails.getName()) %></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "orgId=" + OrgDetails.getOrgId(); %>      
      <dhv:container name="accounts" selected="opportunities" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">
<input type="hidden" name="id" value="<%= OppDetails.getId() %>">
<input type="hidden" name="modified" value="<%= OppDetails.getModified() %>">
<input type="submit" value="Update">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='/Opportunities.do?command=Details&id=<%= OppDetails.getId() %>'">
<input type="reset" value="Reset">
<br>
&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">

  <tr class="title">
    <td colspan=2 valign=center align=left>
      <strong>Modify an Opportunity</strong>
    </td>     
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Reassign To
    </td>
    <td valign=center>
      <%= UserList.getHtmlSelect("owner", OppDetails.getOwner() ) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Description
    </td>
    <td valign=center>
      <input type=text size=35 name="description" value="<%= toHtmlValue(OppDetails.getDescription()) %>">
      <font color="red">*</font> <%= showAttribute(request, "descriptionError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Type of Business
    </td>
    <td valign=center>
      <%= BusTypeList.getHtml() %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Prob. of Close
    </td>
    <td valign=center>
      <input type=text size=5 name="closeProb" value="<%= OppDetails.getCloseProbValue() %>">%
      <font color="red">*</font> <%= showAttribute(request, "closeProbError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Est. Close Date
    </td>
    <td valign=center>
      <input type=text size=10 name="closeDate" value="<%= toHtmlValue(OppDetails.getCloseDateString()) %>">
      <a href="javascript:popCalendar('updateOpp', 'closeDate');">Date</a> (mm/dd/yyyy)
      <font color="red">*</font> <%= showAttribute(request, "closeDateError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Low Estimate
    </td>
    <td valign=center>
      <input type=text size=10 name="low" value="<%= OppDetails.getLowAmount() %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Best Guess Estimate
    </td>
    <td valign=center>
      <input type=text size=10 name="guess" value="<%= OppDetails.getGuessAmount() %>">
      <font color="red">*</font> <%= showAttribute(request, "guessError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      High Estimate
    </td>
    <td valign=center>
      <input type=text size=10 name="high" value="<%= OppDetails.getHighAmount() %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Est. Terms
    </td>
    <td valign=center>
      <input type=text size=5 name="terms" value="<%= OppDetails.getTermsString() %>">
      <%= UnitTypeList.getHtml() %>
      <font color="red">*</font> <%= showAttribute(request, "termsError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Current Stage
    </td>
    <td valign=center>
      <%=StageList.getHtmlSelect("stage",OppDetails.getStage())%>
      <input type=checkbox name="closeNow"
      
      <% if (OppDetails.getClosed() != null) {%>
       		checked
      <%}%>
      
      >Closed 
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Est. Commission
    </td>
    <td valign=center>
      <input type=text size=5 name="commission" value="<%= OppDetails.getCommissionValue() %>">%
      <input type=hidden name="accountLink" value="<%=request.getParameter("orgId")%>">
      <input type=hidden name="orgId" value="<%=request.getParameter("orgId")%>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Alert Date
    </td>
    <td valign=center>
      <input type=text size=10 name="alertDate" value="<%= toHtmlValue(OppDetails.getAlertDateString()) %>">
      <a href="javascript:popCalendar('updateOpp', 'alertDate');">Date</a> (mm/dd/yyyy)
    </td>
  </tr>
  </table>
&nbsp;
<br>
<input type="submit" value="Update">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='/Opportunities.do?command=Details&id=<%= OppDetails.getId() %>'">
<input type="reset" value="Reset">
  </td>
  </tr>
  </table>
</form>
</body>
