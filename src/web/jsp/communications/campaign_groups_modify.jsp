<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*,com.darkhorseventures.webutils.LookupElement" %>
<jsp:useBean id="SearchFieldList" class="com.darkhorseventures.cfsbase.SearchFieldList" scope="request"/>
<jsp:useBean id="StringOperatorList" class="com.darkhorseventures.cfsbase.SearchOperatorList" scope="request"/>
<jsp:useBean id="DateOperatorList" class="com.darkhorseventures.cfsbase.SearchOperatorList" scope="request"/>
<jsp:useBean id="NumberOperatorList" class="com.darkhorseventures.cfsbase.SearchOperatorList" scope="request"/>
<jsp:useBean id="SearchForm" class="com.darkhorseventures.cfsbase.SearchFormBean" scope="request"/>
<jsp:useBean id="SCL" class="com.darkhorseventures.cfsbase.SearchCriteriaList" scope="request"/>
<jsp:useBean id="ContactTypeList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="AccountTypeList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="ContactSource" class="com.darkhorseventures.webutils.HtmlSelect" scope="request"/>
<%@ include file="initPage.jsp" %>

<SCRIPT LANGUAGE="JavaScript">
<!-- 
//updateOperators has to be defined in each file because it uses bean
//information to populate selects

function updateOperators(){
	operatorList = document.searchForm.operatorSelect;
	fieldSelectIndex = searchField[document.searchForm.fieldSelect.selectedIndex].type
	
	if (document.searchForm.fieldSelect.options[document.searchForm.fieldSelect.selectedIndex].value == 8) {
    
    //clear the select
    deleteOptions("idSelect");
    <%
    Iterator x = ContactTypeList.iterator();
    if (x.hasNext()) {
      while (x.hasNext()) {
        LookupElement thisContactType = (LookupElement)x.next();
    %>
        insertOption("<%=thisContactType.getDescription()%>", "<%=thisContactType.getCode()%>", "idSelect");
    <%
      }
    }
    %>
    
		javascript:ShowSpan('new0');
    javascript:HideSpan('new1');
		document.searchForm.searchValue.value = document.searchForm.idSelect.options[document.searchForm.idSelect.selectedIndex].text;
	} else if (document.searchForm.fieldSelect.selectedIndex == 3) {
		javascript:HideSpan('new0');
		javascript:ShowSpan('new1');
		document.searchForm.searchValue.value = "";
	} else if (document.searchForm.fieldSelect.options[document.searchForm.fieldSelect.selectedIndex].value == 11) {
    javascript:HideSpan('new1');
    //clear the select
    deleteOptions("idSelect");
    <%
    Iterator z = AccountTypeList.iterator();
    if (z.hasNext()) {
      while (z.hasNext()) {
        LookupElement thisElt = (LookupElement)z.next();
    %>
        insertOption("<%=thisElt.getDescription()%>", "<%=thisElt.getCode()%>", "idSelect");
    <%
      }
    }
    
    %>
    
		javascript:ShowSpan('new0');
		document.searchForm.searchValue.value = document.searchForm.idSelect.options[document.searchForm.idSelect.selectedIndex].text;
	} else {
		javascript:HideSpan('new0');
    javascript:HideSpan('new1');
		document.searchForm.searchValue.value = "";
	}
	
	// empty the operator list
	for (i = operatorList.options.length; i >= 0; i--)
		operatorList.options[i]= null;
	// fill operator list with new values
	for (i = 0; i < listOfOperators[fieldSelectIndex].length; i++) {
		operatorList.options[i] = new Option(listOfOperators[fieldSelectIndex][i].displayText, listOfOperators[fieldSelectIndex][i].id)
	}
} // end updateOperators

//  End -->
</SCRIPT>

<body onLoad="javascript:document.forms[0].groupName.focus();HideSpans();">
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/popCalendar.js"></script>
<script language="JavaScript" type="text/javascript" src="/javascript/searchForm.js"></script>
<script language="JavaScript" type="text/javascript" src="/javascript/popContacts.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/submit.js"></script>
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
<form name="searchForm" method="post" action="/CampaignManagerGroup.do?command=Update&auto-populate=true&id=<%= SCL.getId() %>" onSubmit="return checkForm(this);" >
<a href="CampaignManager.do">Communications Manager</a> >
<a href="/CampaignManagerGroup.do?command=View">Group List</a> >
Group Details
<hr color="#BFBFBB" noshade>
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
    <table width=100% border=0 cellpadding=2 cellspacing=0>
    <tr>
    <td width=98 nowrap>
    Field
    </td>
    
    <td width=100% valign=center>
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
    </td>
    
    </tr>
    
    <tr>
    <td width=98 nowrap>
    Operator
    </td>
    
    <td width=100% valign=center>
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
    </td>
    
    </tr>
    
    <tr><td valign=center>
    &nbsp;
    </td><td valign=center>
    <span name="new0" ID="new0" style="position:relative; visibility:hidden"><select id="idSelect" name="idSelect" onChange="javascript:setText(document.searchForm.idSelect);"></select>
    </span>
    </td></tr>
    
    <tr>
    <td width=98 nowrap>
    Search Text
    </td>
    
    <td width=100% valign=center>
    <input type="text" name="searchValue" value="" size=25  maxlength=125> 
    </td>
    
    </tr>    
    
    <tr><td valign=center>
    &nbsp;
    </td><td align=right valign=center>
    <span name="new1" ID="new1" style="position:relative; visibility:hidden"><a href="javascript:popCalendar('searchForm', 'searchValue');">Date</a></span>
    </td></tr>
    
    <tr>
    <td width=98 nowrap>
    From
    </td>
    
    <td width=100% valign=center>
    <%= ContactSource.getHtml("contactSource", -1) %>
    </td>
    
    </tr> 
    
    <tr>
    <td align=center colspan=2 nowrap>
    <br>
    <input type="button" value="Add >" onclick="javascript:addValues()">
    </td>
    </tr>      
    
    
    </table>
    </td>
    
		<td align="center" valign="center" width="50%">
		<% if (SCL.size() > 0) {%>
      <% SCL.setHtmlSelectIdName("listViewId"); %>
			<%= SCL.getHtmlSelect("searchCriteria") %>
		<%} else {%>
			<select name="searchCriteria" id="listViewId" size="10">
        <option value="-1">----------------Search Criteria----------------</option>
			</select>
		<%}%>
      <br>
      <a href="javascript:popContactsListMultipleCampaign('listViewId','1');">Add/Remove Contacts</a><br>
      &nbsp;<br>
      <input type="hidden" name="previousSelection" value="">
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
