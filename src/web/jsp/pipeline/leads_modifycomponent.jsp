<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.pipeline.base.*,org.aspcfs.utils.web.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="opportunityHeader" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<jsp:useBean id="LeadsComponentDetails" class="org.aspcfs.modules.pipeline.base.OpportunityComponent" scope="request"/>
<jsp:useBean id="BusTypeList" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="UnitTypeList" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="PipelineViewpointInfo" class="org.aspcfs.utils.web.ViewpointInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<SCRIPT LANGUAGE="JavaScript">
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
    var test = document.updateOpp.selectedList;
    if (test != null) {
      return selectAllOptions(document.updateOpp.selectedList);
    }
  }
}
</SCRIPT>
<form name="updateOpp" action="LeadsComponents.do?command=UpdateComponent&auto-populate=true<%= (request.getParameter("popup") != null?"&popup=true":"") %>" onSubmit="return doCheck(this);" method="post">
<% 
  boolean popUp = false;
  if(request.getParameter("popup")!=null){
    popUp = true;
  }
%>
<dhv:evaluate if="<%= !popUp %>">
<a href="Leads.do">Pipeline Management</a> > 
<a href="Leads.do?command=ViewOpp">View Opportunities</a> >
<% if ("list".equals(request.getParameter("return"))) {%>
  <a href="Leads.do?command=DetailsOpp&headerId=<%= LeadsComponentDetails.getHeaderId() %>">Opportunity Details</a> > 
<%} else if (request.getParameter("return") != null) {%>
  <a href="Leads.do?command=DetailsOpp&headerId=<%= LeadsComponentDetails.getHeaderId() %>">Opportunity Details</a> > 
  <a href="LeadsComponents.do?command=DetailsComponent&id=<%= LeadsComponentDetails.getId() %>">Component Details</a> >
<%}%>
Modify Component<br>
<hr color="#BFBFBB" noshade>
</dhv:evaluate>
<input type="hidden" name="id" value="<%= LeadsComponentDetails.getId() %>">
<input type="hidden" name="headerId" value="<%= LeadsComponentDetails.getHeaderId() %>">
<input type="hidden" name="modified" value="<%= LeadsComponentDetails.getModified() %>">
<dhv:evaluate if="<%= request.getParameter("return") != null %>">
  <input type="hidden" name="return" value="<%= request.getParameter("return") %>">
</dhv:evaluate>
<dhv:evaluate if="<%= PipelineViewpointInfo.isVpSelected(User.getUserId()) %>">
  <b>Viewpoint: </b><b class="highlight"><%= PipelineViewpointInfo.getVpUserName() %></b><br>
  &nbsp;<br>
</dhv:evaluate>
<%-- Begin container --%>
<dhv:evaluate if="<%= !popUp %>">
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(opportunityHeader.getDescription()) %></strong>&nbsp;
      <dhv:evaluate exp="<%= (opportunityHeader.getAccountEnabled() && opportunityHeader.getAccountLink() > -1) %>">
        <dhv:permission name="accounts-view,accounts-accounts-view">[ <a href="Accounts.do?command=Details&orgId=<%= opportunityHeader.getAccountLink() %>">Go to this Account</a> ]</dhv:permission>
      </dhv:evaluate>
      <dhv:evaluate exp="<%= opportunityHeader.getContactLink() > -1 %>">
        <dhv:permission name="contacts-view,contacts-external_contacts-view">[ <a href="ExternalContacts.do?command=ContactDetails&id=<%= opportunityHeader.getContactLink() %>">Go to this Contact</a> ]</dhv:permission>
      </dhv:evaluate>
      <dhv:evaluate if="<%= opportunityHeader.hasFiles() %>">
        <% FileItem thisFile = new FileItem(); %>
        <%= thisFile.getImageTag()%>
      </dhv:evaluate>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "id=" + opportunityHeader.getId(); %>      
      <dhv:container name="opportunities" selected="details" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">
</dhv:evaluate>
<%-- Begin the container contents --%>
<input type="submit" value="Update" onClick="this.form.dosubmit.value='true';">
<% 
  if (request.getParameter("return") != null) {
	  if (request.getParameter("return").equals("list")) {
%>
	<input type="submit" value="Cancel" onClick="javascript:this.form.action='Leads.do?command=ViewOpp';this.form.dosubmit.value='false';">
<%
    } else if (request.getParameter("return").equals("details")) {
%>
  <input type="submit" value="Cancel" onClick="javascript:this.form.action='Leads.do?command=DetailsOpp&headerId=<%= LeadsComponentDetails.getHeaderId() %>';this.form.dosubmit.value='false';">
<%
    }
  } else {
%>
  <input type="submit" value="Cancel" onClick="javascript:this.form.action='LeadsComponents.do?command=DetailsComponent&id=<%= LeadsComponentDetails.getId() %>';this.form.dosubmit.value='false';">
<%
  }
%>
  <input type="reset" value="Reset">
<dhv:evaluate exp="<%= popUp %>">
  <input type="button" value="Cancel" onclick="javascript:window.close();">
</dhv:evaluate>
<br>
<%= showError(request, "actionError") %>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="2">
      <strong><%= LeadsComponentDetails.getDescription() %></strong>&nbsp;
    </td>     
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Reassign To
    </td>
    <td>
      <%= UserList.getHtmlSelect("owner", LeadsComponentDetails.getOwner() ) %>
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
            <%if(request.getAttribute("TypeList") != null){ %>
              <dhv:lookupHtml listName="TypeList" lookupName="TypeSelect"/>
            <% }else{ %>
               <dhv:evaluate exp="<%= LeadsComponentDetails.getTypes().isEmpty() %>">
                  <option value="-1">None Selected</option>
                </dhv:evaluate>
                <dhv:evaluate exp="<%= !LeadsComponentDetails.getTypes().isEmpty() %>">
              <%
                Iterator i = LeadsComponentDetails.getTypes().iterator();
                while (i.hasNext()) {
                LookupElement thisElt = (LookupElement)i.next();
              %>
                <option value="<%= thisElt.getCode() %>"><%= thisElt.getDescription() %></option>
              <%}%>
              </dhv:evaluate>
            <% } %>
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
    <td class="formLabel">
      Description
    </td>
    <td>
      <input type="text" size="50" name="description" value="<%= toHtmlValue(LeadsComponentDetails.getDescription()) %>">
      <font color="red">*</font> <%= showAttribute(request, "componentDescriptionError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">Additional Notes</td>
    <td><TEXTAREA NAME="notes" ROWS="3" COLS="50"><%= toString(LeadsComponentDetails.getNotes()) %></TEXTAREA></td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Source
    </td>
    <td>
      <% BusTypeList.setDefaultKey(LeadsComponentDetails.getType());%>
      <%= BusTypeList.getHtml() %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Prob. of Close
    </td>
    <td>
      <input type="text" size="5" name="closeProb" value="<%= LeadsComponentDetails.getCloseProbValue() %>">%
      <font color="red">*</font> <%= showAttribute(request, "closeProbError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Est. Close Date
    </td>
    <td>
      <input type="text" size="10" name="closeDate" value="<%= toHtmlValue(LeadsComponentDetails.getCloseDateString()) %>">
      <a href="javascript:popCalendar('updateOpp', 'closeDate');">Date</a> (mm/dd/yyyy)
      <font color="red">*</font> <%= showAttribute(request, "closeDateError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Low Estimate
    </td>
    <td>
      <input type=text size="10" name="low" value="<%= LeadsComponentDetails.getLowAmount() %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Best Guess Estimate
    </td>
    <td>
      <input type="text" size="10" name="guess" value="<%= LeadsComponentDetails.getGuessAmount() %>">
      <font color="red">*</font> <%= showAttribute(request, "guessError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      High Estimate
    </td>
    <td>
      <input type="text" size="10" name="high" value="<%= LeadsComponentDetails.getHighAmount() %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Est. Term
    </td>
    <td>
      <input type="text" size="5" name="terms" value="<%= LeadsComponentDetails.getTermsString() %>">
      <%= UnitTypeList.getHtml("units", (LeadsComponentDetails.getUnits() != null ? LeadsComponentDetails.getUnits() : "")) %>
      <font color="red">*</font> <%= showAttribute(request, "termsError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Current Stage
    </td>
    <td>
      <%= StageList.getHtmlSelect("stage", LeadsComponentDetails.getStage()) %>
      <input type="checkbox" name="closeNow" <dhv:evaluate if="<%= (LeadsComponentDetails.getClosed() != null || LeadsComponentDetails.getCloseIt()) %>"> checked</dhv:evaluate>> Closed
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Est. Commission
    </td>
    <td>
      <input type="text" size="5" name="commission" value="<%= LeadsComponentDetails.getCommissionValue() %>">%
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Alert Description
    </td>
    <td>
      <input type="text" size="50" name="alertText" value="<%= toHtmlValue(LeadsComponentDetails.getAlertText()) %>"><br>
    </td>
  </tr>
   <tr class="containerBody">
    <td nowrap class="formLabel">
      Alert Date
    </td>
    <td>
      <input type="text" size="10" name="alertDate" value="<%= toHtmlValue(LeadsComponentDetails.getAlertDateStringLongYear()) %>">
      <a href="javascript:popCalendar('updateOpp', 'alertDate');">Date</a> (mm/dd/yyyy)
    </td>
  </tr>
</table>
&nbsp;
<br>
<input type="submit" value="Update" onClick="this.form.dosubmit.value='true';">
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<input type="submit" value="Cancel" onClick="javascript:this.form.action='Leads.do?command=ViewOpp';this.form.dosubmit.value='false';">
	<%} else if(request.getParameter("return").equals("details")) {%>
  <input type="submit" value="Cancel" onClick="javascript:this.form.action='Leads.do?command=DetailsOpp&headerId=<%= LeadsComponentDetails.getHeaderId() %>';this.form.dosubmit.value='false';">
 <%}
 } else {%>
<input type="submit" value="Cancel" onClick="javascript:this.form.action='LeadsComponents.do?command=DetailsComponent&id=<%= LeadsComponentDetails.getId() %>';this.form.dosubmit.value='false';">
<%}%>
<input type="reset" value="Reset">
<dhv:evaluate exp="<%= popUp %>">
  <input type="button" value="Cancel" onclick="javascript:window.close();">
</dhv:evaluate>
<input type="hidden" name="dosubmit" value="true">
<%-- End container contents --%>
<dhv:evaluate if="<%= !popUp %>">
    </td>
  </tr>
</table>
</dhv:evaluate>
<%-- End container --%>
</form>

