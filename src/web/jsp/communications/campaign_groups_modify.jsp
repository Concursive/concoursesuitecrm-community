<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="SearchFieldList" class="com.darkhorseventures.cfsbase.SearchFieldList" scope="request"/>
<jsp:useBean id="StringOperatorList" class="com.darkhorseventures.cfsbase.SearchOperatorList" scope="request"/>
<jsp:useBean id="DateOperatorList" class="com.darkhorseventures.cfsbase.SearchOperatorList" scope="request"/>
<jsp:useBean id="NumberOperatorList" class="com.darkhorseventures.cfsbase.SearchOperatorList" scope="request"/>
<jsp:useBean id="SearchForm" class="com.darkhorseventures.cfsbase.SearchFormBean" scope="request"/>
<jsp:useBean id="SCL" class="com.darkhorseventures.cfsbase.SearchCriteriaList" scope="request"/>
<jsp:useBean id="ContactTypeList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="ContactSource" class="com.darkhorseventures.webutils.HtmlSelect" scope="request"/>
<%@ include file="initPage.jsp" %>


<SCRIPT LANGUAGE="JavaScript">
<!-- Begin
function HideSpans()
{
	isNS = (document.layers) ? true : false;
	isIE = (document.all) ? true : false;
	
  if( (isIE) )
  {
    //document.all.new0.style.visibility="hidden";
    //document.all.new1.style.visibility="hidden";
    //document.all.new2.style.visibility="hidden";
    //document.all.new3.style.visibility="hidden";
  }
  else if( (isNS) )
  {
    document.new0.visibility="hidden";
    document.new1.visibility="hidden";
    document.new2.visibility="hidden";
    document.new3.visibility="hidden";
  }

  return true;
}

function checkForm(form) {
	formTest = true;
        message = "";

        if (form.groupName.value == "") {
                message += "- Group Name is required\r\n";
                formTest = false;
        }

	if (formTest == false) {
         	alert("Registration could not be processed, please check the following:\r\n\r\n" + message);
                return false;
        } else {
		saveValues();
                return true;
        }
}

//  End -->
</SCRIPT>

<body onLoad="javascript:document.forms[0].groupName.focus();HideSpans();">
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/popCalendar.js"></script>
<script language="JavaScript" type="text/javascript" src="/javascript/searchForm.js"></script>
<script language="JavaScript" type="text/javascript">
var searchCriteria = new Array();

searchField = new Array();
<%
	Iterator f = SearchFieldList.iterator();
	int fieldArrayID = -1;
 	if (f.hasNext()){
		while (f.hasNext()){
			fieldArrayID ++;
			SearchField thisSearchField = (SearchField)f.next();
%> 
searchField[<%= fieldArrayID %>] = new field(<%= thisSearchField.getId() %>, "<%= thisSearchField.getDescription() %>", <%= thisSearchField.getFieldTypeId() %>);
<% } } %>

stringOperators = new Array();
<%
	Iterator s = StringOperatorList.iterator();
	int stringArrayID = -1;
 	if (s.hasNext()){
		while (s.hasNext()){
			stringArrayID ++;
			SearchOperator thisStringOperator = (SearchOperator)s.next();
%> 
stringOperators[<%= stringArrayID %>] = new operator(<%= thisStringOperator.getId() %>, "<%= thisStringOperator.getOperator() %>", "<%= thisStringOperator.getDisplayText() %>");
<% } } %>

dateOperators = new Array();
<%
	Iterator d = DateOperatorList.iterator();
	int dateArrayID = -1;
 	if (d.hasNext()){
	while (d.hasNext()){
		dateArrayID ++;
		SearchOperator thisDateOperator = (SearchOperator)d.next();
%> 
dateOperators[<%= dateArrayID %>] = new operator(<%= thisDateOperator.getId() %>, "<%= thisDateOperator.getOperator() %>", "<%= thisDateOperator.getDisplayText() %>");
<% } } %>

numberOperators = new Array();
<%
	Iterator n = NumberOperatorList.iterator();
	int numberArrayID = -1;
 	if (n.hasNext()){
	while (n.hasNext()){
		numberArrayID ++;
		SearchOperator thisNumberOperator = (SearchOperator)n.next();
%> 
numberOperators[<%= numberArrayID %>] = new operator(<%= thisNumberOperator.getId() %>, "<%= thisNumberOperator.getOperator() %>", "<%= thisNumberOperator.getDisplayText() %>");
<% } } %>

listOfOperators = new Array()
listOfOperators[0] = stringOperators
listOfOperators[1] = dateOperators
listOfOperators[2] = numberOperators
</script>
<a href="/CampaignManagerGroup.do?command=View">Back to Group List</a><br>
<form name="searchForm" method="post" action="/CampaignManagerGroup.do?command=Update&auto-populate=true&id=<%= SCL.getId() %>" onSubmit="return checkForm(this);" >
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="2">
      Update contact group details
    </td>
  </tr>
  <tr>
    <td class="formLabel" nowrap>
      Group Name
    </td>
    <td width="100%">
      <input type="text" size="40" name="groupName" value="<%=toHtmlValue(SCL.getGroupName())%>"><font color=red>*</font> <%= showAttribute(request, "groupNameError") %>
    </td>
  </tr>
  <tr>
    <td class="formLabel" nowrap>
      Contact Source
    </td>
    <td width="100%">
      <%= ContactSource.getHtml("contactSource", SCL.getContactSource()) %>
    </td>
  </tr>
</table>
&nbsp;<br>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="2">
      Update criteria for the contact group
    </td>
  </tr>
	<tr>
    <td align="left" valign="center" width="40%">
      Field Name
      <script language="JavaScript">
        var page = "" // start assembling next part of page and form
        page += "<SELECT NAME='fieldSelect' onChange='updateOperators()'> "
        for (var i = 0; i < searchField.length; i++) {
          page += "<OPTION" // OPTION tags
          if (i == 0) { // pre-select first item in list
            page += " SELECTED"
          }
          page += " VALUE='" + searchField[i].id + "'"
          page += ">" + searchField[i].name
        }
        page += "</SELECT>" // close selection item tag
        document.write(page) // lay out this part of the page
      </script>      
      <br>
      &nbsp;<br>
      
		  Operator
      <script language="JavaScript">
        var page = "" // start assembling next part of page and form
        var fieldSelectIndex = searchField[document.searchForm.fieldSelect.options.selectedIndex].type
        page += "<SELECT NAME='operatorSelect'> "
        for (var i = 0; i < listOfOperators[fieldSelectIndex].length; i++) {
          page += "<OPTION" // OPTION tags
          if (i == 0) { // pre-select first item in list
            page += " SELECTED"
          }
          page += " VALUE='" + listOfOperators[fieldSelectIndex][i].id + "'"
          page += ">" + listOfOperators[fieldSelectIndex][i].displayText
        }
        page += "</SELECT>" // close selection item tag
        document.write(page) // lay out this part of the page
      </script>
      <span name="new0" ID="new0" style="position:relative; visibility:hidden"><br>&nbsp;<br>Select&nbsp;&nbsp;<%=ContactTypeList.getHtmlSelect("typeId",0)%></span>
      <br>
      &nbsp;<br>
      
		  Search Text
      <input type="text" name="searchValue" value="" size=25  maxlength=125>
      <span name="new1" ID="new1" style="position:relative; visibility:hidden"><a href="javascript:popCalendar('searchForm', 'searchValue');">Date</a></span>
      
      <br>
      &nbsp;<br>
      <center>
        <input type="button" value="Add >" onclick="javascript:addValues()">
      </center>
    </td>
    
		<td align="center" valign="center" width="50%">
		<% if (SCL.size() > 0) {%>
			<%= SCL.getHtmlSelect("searchCriteria") %>
		<%} else {%>
			<select name="searchCriteria" size="10">
        <option value="-1">----------------Search Criteria----------------</option>
			</select>
		<%}%>
      <br>
      &nbsp;<br>
      <input type="button" value="Remove" onclick="removeValues()">
		</td>
	</tr>
</table>
&nbsp;<br>
<input type="hidden" name="searchCriteriaText">
<input type="hidden" name="owner" value="<%= SCL.getOwner() %>">

<input type="submit" value="Update & Preview Query" name="Save">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='/CampaignManagerGroup.do?command=View'">
</form>
</body>
