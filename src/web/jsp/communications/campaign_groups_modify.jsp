<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.communications.base.*,org.aspcfs.utils.web.LookupElement" %>
<jsp:useBean id="SearchFieldList" class="org.aspcfs.modules.communications.base.SearchFieldList" scope="request"/>
<jsp:useBean id="StringOperatorList" class="org.aspcfs.modules.communications.base.SearchOperatorList" scope="request"/>
<jsp:useBean id="DateOperatorList" class="org.aspcfs.modules.communications.base.SearchOperatorList" scope="request"/>
<jsp:useBean id="NumberOperatorList" class="org.aspcfs.modules.communications.base.SearchOperatorList" scope="request"/>
<jsp:useBean id="SearchForm" class="org.aspcfs.modules.communications.beans.SearchFormBean" scope="request"/>
<jsp:useBean id="SCL" class="org.aspcfs.modules.communications.base.SearchCriteriaList" scope="request"/>
<jsp:useBean id="ContactTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AccountTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactSource" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
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
		javascript:showSpan('new0');
    javascript:showSpan('new0a');
    javascript:hideSpan('searchText1');
    javascript:hideSpan('searchText2');    
    javascript:hideSpan('new1');
    javascript:hideSpan('new1a');
		document.searchForm.searchValue.value = document.searchForm.idSelect.options[document.searchForm.idSelect.selectedIndex].text;
	} else if (document.searchForm.fieldSelect.selectedIndex == 3) {
		javascript:hideSpan('new0');
    javascript:hideSpan('new0a');
		javascript:showSpan('new1');
    javascript:showSpan('new1a');
    javascript:showSpan('searchText1');
    javascript:showSpan('searchText2');
		document.searchForm.searchValue.value = "";
	} else if (document.searchForm.fieldSelect.options[document.searchForm.fieldSelect.selectedIndex].value == 11) {
    javascript:hideSpan('new1');
    javascript:hideSpan('new1a');
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
		javascript:showSpan('new0');
    javascript:showSpan('new0a');
    javascript:hideSpan('searchText1');
    javascript:hideSpan('searchText2');    
		document.searchForm.searchValue.value = document.searchForm.idSelect.options[document.searchForm.idSelect.selectedIndex].text;
	} else {
		javascript:hideSpan('new0');
    javascript:hideSpan('new0a');
    javascript:hideSpan('new1');
    javascript:hideSpan('new1a');
    javascript:showSpan('searchText1');
    javascript:showSpan('searchText2');    
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

function checkForm(form) {
  formTest = true;
  message = "";
  if (form.groupName.value == "") {
    message += "- Group Name is required\r\n";
    formTest = false;
  }
  if (formTest == false) {
    alert("Criteria could not be processed, please check the following:\r\n\r\n" + message);
    return false;
  } 
  saveValues();
  return true;
}

</SCRIPT>
<body onLoad="javascript:document.forms[0].groupName.focus()">
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/searchForm.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/popContacts.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></script>
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
<form name="searchForm" method="post" action="CampaignManagerGroup.do?command=Update&auto-populate=true&id=<%= SCL.getId() %>" onSubmit="return checkForm(this);" >
<%-- Trails --%>
<table class="trails">
<tr>
<td>
<a href="CampaignManager.do">Communications Manager</a> >
<a href="CampaignManagerGroup.do?command=View">View Groups</a> >
<dhv:evaluate if="<%= !"list".equals(request.getParameter("return")) %>">
<a href="CampaignManagerGroup.do?command=Details&id=<%= SCL.getId() %>">Group Details</a> >
</dhv:evaluate>
Group Details
</td>
</tr>
</table>
<%-- End Trails --%>
<input type="submit" value="Save" name="Save">
<dhv:evaluate if="<%= "list".equals(request.getParameter("return")) %>">
  <input type="submit" value="Cancel" onClick="javascript:this.form.action='CampaignManagerGroup.do?command=View'">
</dhv:evaluate>
<dhv:evaluate if="<%= !"list".equals(request.getParameter("return")) %>">
  <input type="submit" value="Cancel" onClick="javascript:this.form.action='CampaignManagerGroup.do?command=Details&id=<%= SCL.getId() %>'">
</dhv:evaluate>
<input type="button" value="Preview" onClick="javascript:popPreview()">
<br>
&nbsp;<br>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      Update contact group details
    </th>
  </tr>
  <tr>
    <td class="formLabel" nowrap>
      Group Name
    </td>
    <td>
      <input type="text" size="40" name="groupName" value="<%= toHtmlValue(SCL.getGroupName()) %>"><font color="red">*</font> <%= showAttribute(request, "groupNameError") %>
    </td>
  </tr>
</table>
&nbsp;<br>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      Update contact criteria for this group
    </th>
  </tr>
	<tr>
    <td style="text-align: center;" valign="center" width="50%">
      <table width="100%" border="0" cellpadding="2" cellspacing="0" class="empty">
        <tr>
          <td class="row1" colspan="2">
            Choose specific contacts:
          </td>
        </tr>
        <tr>
          <td colspan="2" style="text-align: center;">
            [<a href="javascript:popContactsListMultipleCampaign('listViewId','1');">Add/Remove Contacts</a>]
          </td>
        </tr>
        <tr>
          <td colspan="2">
            &nbsp;
          </td>
        </tr>
        <tr>
          <td class="row1" colspan="2">
            Define criteria to generate a list:
          </td>
        </tr>
        <tr>
          <td style="text-align: right;" nowrap>
            Field
          </td>
          <td width="100%" valign="center">
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
          <td style="text-align: right;" nowrap>
            Operator
          </td>
          <td width="100%" valign="center">
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
        <tr>
          <td style="text-align: right;" nowrap>
            <span name="searchText1" ID="searchText1">Search Text</span>
          </td>
          <td width="100%" valign="center">
            <span name="searchText2" ID="searchText2"><input type="text" name="searchValue" value="" size="25"  maxlength="125"></span> 
          </td>
        </tr>
        <tr>
          <td style="text-align: right;">
            <span name="new1a" ID="new1a" style="display:none">&nbsp;</span>
          </td>
          <td valign="center">
            <span name="new1" ID="new1" style="display:none"><a href="javascript:popCalendar('searchForm', 'searchValue');"><img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle" height="16" width="16"/>Date</a></span>
          </td>
        </tr>
        <tr>
          <td style="text-align: right;" nowrap>
            <span name="new0a" ID="new0a" style="display:none">Search Text</span>
          </td>
          <td valign="center">
            <span name="new0" ID="new0" style="display:none"><select id="idSelect" name="idSelect" onChange="javascript:setText(document.searchForm.idSelect);"></select></span>
          </td>
        </tr>
        <tr>
          <td style="text-align: right;" nowrap>
            From
          </td>
          <td width="100%" valign="center">
            <%= ContactSource.getHtml("contactSource", -1) %>
          </td>
        </tr>
        <tr>
          <td style="text-align: center;" colspan="2" nowrap>
            <br>
            <input type="button" value="Add >" onclick="javascript:addValues()">
          </td>
        </tr>
      </table>
    </td>
		<td style="text-align: center;" valign="top" width="50%">
      <table width="100%" border="0" cellpadding="2" cellspacing="0" class="empty">
        <tr>
          <td class="row1">
            Selected criteria and contacts:
          </td>
        </tr>
        <tr>
          <td>
            &nbsp;
          </td>
        </tr>
        <tr>
          <td style="text-align: center;">
		<% if (SCL.size() > 0) {%>
      <% SCL.setHtmlSelectIdName("listViewId"); %>
			<%= SCL.getHtmlSelect("searchCriteria") %>
		<%} else {%>
			<select name="searchCriteria" id="listViewId" size="10">
        <option value="-1">----------------Search Criteria----------------</option>
			</select>
		<%}%>
      <br>
      &nbsp;<br>
      <input type="hidden" name="previousSelection" value="">
      <input type="button" value="Remove" onclick="removeValues()">
          </td>
        </tr>
      </table>
    </td>
	</tr>
</table>
&nbsp;<br>
<input type="hidden" name="searchCriteriaText">
<input type="hidden" name="owner" value="<%= SCL.getOwner() %>">
<input type="hidden" name="return" value="<%= request.getParameter("return") %>">
<input type="submit" value="Save" name="Save">
<dhv:evaluate if="<%= "list".equals(request.getParameter("return")) %>">
  <input type="submit" value="Cancel" onClick="javascript:this.form.action='CampaignManagerGroup.do?command=View'">
</dhv:evaluate>
<dhv:evaluate if="<%= !"list".equals(request.getParameter("return")) %>">
  <input type="submit" value="Cancel" onClick="javascript:this.form.action='CampaignManagerGroup.do?command=Details&id=<%= SCL.getId() %>'">
</dhv:evaluate>
<input type="button" value="Preview" onClick="javascript:popPreview()">
</form>
</body>
