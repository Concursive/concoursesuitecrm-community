<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="OpportunityHeader" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<jsp:useBean id="OppComponentDetails" class="org.aspcfs.modules.pipeline.base.OpportunityComponent" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="BusTypeList" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="UnitTypeList" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="PipelineViewpointInfo" class="org.aspcfs.utils.web.ViewpointInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="javascript:document.forms[0].description.focus();">
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script language="JavaScript">
  function doCheck(form) {
    if (form.dosubmit.value == "false") {
      return true;
    } else {
      return(checkForm(form));
    }
  }
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
    if ((!form.alertDate.value == "") && (!checkAlertDate(form.alertDate.value))) { 
      message += "- Check that Alert Date is on or after today's date\r\n";
      formTest = false;
    }
    if ((!form.alertText.value == "") && (form.alertDate.value == "")) { 
      message += "- Please specify an alert date\r\n";
      formTest = false;
    }
    if ((!form.alertDate.value == "") && (form.alertText.value == "")) { 
      message += "- Please specify an alert description\r\n";
      formTest = false;
    }
    if (formTest == false) {
      alert("Form could not be saved, please check the following:\r\n\r\n" + message);
      return false;
    } else {
      var test = document.addOpportunity.selectedList;
      if (test != null) {
        return selectAllOptions(document.addOpportunity.selectedList);
      }
    }
  }
</script>
<form name="addOpportunity" action="LeadsComponents.do?command=InsertOppComponent&auto-populate=true" onSubmit="return doCheck(this);" method="post">
<a href="Leads.do">Pipeline Management</a> > 
<% if (request.getParameter("return") == null) { %>
	<a href="Leads.do?command=ViewOpp">View Opportunities</a> >
<%} else {%>
	<% if (request.getParameter("return").equals("dashboard")) { %>
		<a href="Leads.do?command=Dashboard">Dashboard</a> >
	<%}%>
<%}%>
<a href="Leads.do?command=DetailsOpp&headerId=<%= OpportunityHeader.getId() %>">Opportunity Details</a> >
Add Component<br>
<hr color="#BFBFBB" noshade>
<dhv:evaluate exp="<%= PipelineViewpointInfo.isVpSelected(User.getUserId()) %>">
  <b>Viewpoint: </b><b class="highlight"><%= PipelineViewpointInfo.getVpUserName() %></b>
</dhv:evaluate>
<table cellpadding="4" cellspacing="0" border="0" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr>
    <td>
      <input type="submit" value="Save" onClick="this.form.dosubmit.value='true';">
      <input type="submit" value="Cancel" onClick="javascript:this.form.action='Leads.do?command=DetailsOpp&headerId=<%= OpportunityHeader.getId()%>';this.form.dosubmit.value='false';">
      <input type="reset" value="Reset">
      <br>
      <%= showError(request, "actionError") %>  
      <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
      <tr class="title">
        <td colspan="2">
          <strong><%= toHtml(OpportunityHeader.getDescription()) %></strong>
        </td>     
      </tr>
      <tr class="containerBody">
        <td nowrap class="formLabel">
          Assign To
        </td>
        <td>
          <%= UserList.getHtmlSelect("owner", OppComponentDetails.getOwner() ) %>
        </td>
      </tr>
      <tr class="containerBody">
        <td class="formLabel" valign="top">
          Component<br>Type(s)
        </td>
        <td>
          <table border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td>
                <select multiple name="selectedList" id="selectedList" size="5">
                <option value="-1">None Selected</option>
                </select>
                <input type="hidden" name="previousSelection" value="">
              </td>
              <td valign="top">
                &nbsp;[<a href="javascript:popLookupSelectMultiple('selectedList','1','lookup_opportunity_types');">Select</a>]
              </td>
            </tr>
          </table>
        </td>
      </tr>
      <tr class="containerBody">
        <td nowrap class="formLabel">
          Component Description
        </td>
        <td>
          <input type="text" size="50" name="description" value="<%= toHtmlValue(OppComponentDetails.getDescription()) %>">
          <font color=red>*</font> <%= showAttribute(request, "componentDescriptionError") %>
        </td>
      </tr>
      <tr class="containerBody">
        <td valign="top" nowrap class="formLabel">Additional Notes</td>
        <td><TEXTAREA NAME="notes" ROWS="3" COLS="50"><%= toString(OppComponentDetails.getNotes()) %></TEXTAREA></td>
      </tr>  
      <tr class="containerBody">
        <td nowrap class="formLabel">
          Source
        </td>
        <td>
          <%= BusTypeList.getHtml() %>
        </td>
      </tr>
      <tr class="containerBody">
        <td nowrap class="formLabel">
          Prob. of Close
        </td>
        <td>
          <input type="text" size="5" name="closeProb" value="<%= OppComponentDetails.getCloseProbValue() %>">%
          <font color="red">*</font> <%= showAttribute(request, "closeProbError") %>
        </td>
      </tr>
      <tr class="containerBody">
        <td nowrap class="formLabel">
          Est. Close Date
        </td>
        <td>
          <input type="text" size="10" name="closeDate" value="<%= toHtmlValue(OppComponentDetails.getCloseDateString()) %>">
          <a href="javascript:popCalendar('addOpportunity', 'closeDate');">Date</a> (mm/dd/yyyy)
          <font color="red">*</font> <%= showAttribute(request, "closeDateError") %>
        </td>
      </tr>
      <tr class="containerBody">
        <td nowrap class="formLabel">
          Low Estimate
        </td>
        <td>
          <input type="text" size="10" name="low" value="<%= toHtmlValue(OppComponentDetails.getLowAmount()) %>">
        </td>
      </tr>
      <tr class="containerBody">
        <td nowrap class="formLabel">
          Best Guess Estimate
        </td>
        <td>
          <input type="text" size="10" name="guess" value="<%= toHtmlValue(OppComponentDetails.getGuessAmount()) %>">
          <font color="red">*</font> <%= showAttribute(request, "guessError") %>
        </td>
      </tr>
      <tr class="containerBody">
        <td nowrap class="formLabel">
          High Estimate
        </td>
        <td>
          <input type="text" size="10" name="high" value="<%= toHtmlValue(OppComponentDetails.getHighAmount()) %>">
        </td>
      </tr>
      <tr class="containerBody">
        <td nowrap class="formLabel">
          Est. Term
        </td>
        <td>
          <input type="text" size="5" name="terms" value="<%= toHtmlValue(OppComponentDetails.getTermsString()) %>">
          <%= UnitTypeList.getHtml() %>
          <font color="red">*</font> <%= showAttribute(request, "termsError") %>
        </td>
      </tr>
      <tr class="containerBody">
        <td nowrap class="formLabel">
          Current Stage
        </td>
        <td>
          <%= StageList.getHtmlSelect("stage",OppComponentDetails.getStage()) %>
          <input type="checkbox" name="closeNow">Close this component
        </td>
      </tr>
      <tr class="containerBody">
        <td nowrap class="formLabel">
          Est. Commission
        </td>
        <td>
          <input type="text" size="5" name="commission" value="<%= OppComponentDetails.getCommissionValue() %>">%
          <input type="hidden" name="accountLink" value="<%=request.getParameter("orgId")%>">
          <input type="hidden" name="headerId" value="<%= OpportunityHeader.getId() %>">
          <input type="hidden" name="id" value="<%= OpportunityHeader.getId() %>">
          <input type="hidden" name="orgId" value="<%=request.getParameter("orgId")%>">
        </td>
      </tr>
      <tr class="containerBody">
        <td nowrap class="formLabel">
          Alert Description
        </td>
        <td>
          <input type="text" size="50" name="alertText" value="<%= toHtmlValue(OppComponentDetails.getAlertText()) %>"><br>
        </td>
      </tr>
       <tr class="containerBody">
        <td nowrap class="formLabel">
          Alert Date
        </td>
        <td>
          <input type="text" size="10" name="alertDate" value="<%= toHtmlValue(OppComponentDetails.getAlertDateString()) %>">
          <a href="javascript:popCalendar('addOpportunity', 'alertDate');">Date</a> (mm/dd/yyyy)
        </td>
      </tr>
      </table>
      &nbsp;
      <br>
      <input type="submit" value="Save" onClick="this.form.dosubmit.value='true';">
      <input type="submit" value="Cancel" onClick="javascript:this.form.action='Leads.do?command=DetailsOpp&headerId=<%= OpportunityHeader.getId() %>';this.form.dosubmit.value='false';">
      <input type="reset" value="Reset">
      <input type="hidden" name="dosubmit" value="true">
    </td>
  </tr>
</table>
</form>
</body>
