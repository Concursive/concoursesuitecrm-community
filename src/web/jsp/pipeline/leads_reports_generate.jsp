<jsp:useBean id="PipelineViewpointInfo" class="org.aspcfs.utils.web.ViewpointInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/reportSelect.js"></script>
<script language="JavaScript">
  function checkForm(form) {
  	var test = document.generate.selectedList;
      formTest = true;
      message = "";
      if ((form.subject.value == "")) { 
        message += "- A subject is required\r\n";
        formTest = false;
      }
      if (formTest == false) {
        alert("Form could not be saved, please check the following:\r\n\r\n" + message);
        return false;
		} else {
			if (test != null) {
				return selectAllOptions(document.generate.selectedList);
			} else {
				return true;
			}
		}
  }
</script>
<body onLoad="javascript:document.forms[0].subject.focus();">
<form name="generate" action="Leads.do?command=ExportReport" method="post" onSubmit="return checkForm(this);">
<a href="Leads.do">Pipeline Management</a> > 
<a href="Leads.do?command=Reports">Reports</a> > 
Generate New Report <br>
<hr color="#BFBFBB" noshade>
<dhv:evaluate exp="<%= PipelineViewpointInfo.isVpSelected(User.getUserId()) %>">
      <b>Viewpoint: </b><b class="highlight"><%= PipelineViewpointInfo.getVpUserName() %></b><br>
</dhv:evaluate>
<input type="submit" value="Generate">
<input type="button" value="Cancel" onClick="javascript:this.form.action='Leads.do?command=Reports';javascript:this.form.submit();">
<br>
&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr bgcolor="#DEE0FA">
    <td colspan=5 valign=center align=left>
      <strong>Generate a New Report</strong>
    </td>     
  </tr>
  <tr>
    <td nowrap class="formLabel">
      Type
    </td>
    <td colspan=4>
      <select name="type">
      <option value="1">Opportunities Listing</option>
      </select>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      Subject
    </td>
    <td colspan="4">
      <input type="text" size="35" name="subject" maxlength="50">
    </td>
  </tr>
  
  <tr>
    <td nowrap class="formLabel">
      Criteria
    </td>
    <td colspan=4>
      <select name="criteria1">
      <option value="my">My Opportunities</option>
      <option value="all">All Opportunities</option>
      </select>
    </td>
  </tr>
  
  <tr>
    <td nowrap class="formLabel">
      Sorting
    </td>
    <td colspan="4">
      <select name="sort">
      <option value="x.description">Description</option>
      <option value="opp_id">Opportunity ID</option>
      <option value="lowvalue">Low Amount</option>
      <option value="guessvalue">Best Guess Amount</option>
      <option value="highvalue">High Amount</option>
      <option value="closeprob">Prob. of Close</option>
      <option value="x.closedate">Revenue Start</option>
      <option value="x.terms">Terms</option>
      <option value="x.alertdate">Alert Date</option>
      <option value="commission">Commission</option>
      <option value="x.entered">Entered</option>
      <option value="x.modified">Modified</option>
      </select>
    </td>
  </tr>
  
  <tr>
    <td nowrap valign=top class="formLabel">
      Select fields to include
    </td>
    <td width="50%">
      <select size="5" multiple name="fields">
      <option value="contact" >Contact/Organization</option>
      <option value="type" >Type(s)</option>
      <option value="owner" >Owner</option>
      <option value="amount1" >Low Amount</option>
      <option value="amount2" >Best Guess Amount</option>
      <option value="amount3" >High Amount</option>
      <option value="stageName" >Stage Name</option>
      <option value="stageDate" >Stage Date</option>
      <option value="probability" >Prob. of Close</option>
      <option value="revenueStart" >Revenue Start</option>
      <option value="terms" >Terms</option>
      <option value="alertDate" >Alert Date</option>
      <option value="commission" >Commission</option>
      <option value="entered" >Entered</option>
      <option value="enteredBy" >Entered By</option>
      <option value="modified" >Modified</option>
      <option value="modifiedBy" >Modified By</option>
      </select>
     </td>
      <td width="25">
      <table width="100%" cellspacing="0" cellpadding="2" border="0">
      <tr><td valign="center">
      <input type="button" value="All >" onclick="javascript:allValues()">
      </td></tr>
      
      <tr><td valign=center>
      <input type="button" value="Add >" onclick="javascript:addValue()">
      </td></tr>
      
      <tr><td valign=center>
      <input type="button" value="< Del" onclick="javascript:removeValue()">
      </td></tr>
      </table>
      </td>
      
      <td width="50%" align="right">
      <select size="5" name="selectedList" multiple>
      <option value="id" >Opportunity ID</option>
      <option value="description" >Description</option>
      </select>
      </td>
      <td width="25">
	<table width="100%" cellspacing="0" cellpadding="2" border="0">
	<tr><td valign="center">
	<input type="button" value="Up" onclick="javascript:moveOptionUp(document.generate.selectedList)">
	</td></tr>
	<tr><td valign="center">
	<input type="button" value="Down" onclick="javascript:moveOptionDown(document.generate.selectedList)">
	</td></tr>
	</table>
  </td>
  </tr>
</table>
<br>
<input type="submit" value="Generate">
<input type="button" value="Cancel" onClick="javascript:this.form.action='Leads.do?command=Reports';javascript:this.form.submit();">
</form>
</body>
