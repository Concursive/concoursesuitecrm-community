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
<%@ page import="java.util.*,org.aspcfs.modules.communications.base.*,org.aspcfs.utils.web.LookupElement" %>
<%@ page import="org.aspcfs.utils.*" %>
<jsp:useBean id="SearchFieldList" class="org.aspcfs.modules.communications.base.SearchFieldList" scope="request"/>
<jsp:useBean id="StringOperatorList" class="org.aspcfs.modules.communications.base.SearchOperatorList" scope="request"/>
<jsp:useBean id="DateOperatorList" class="org.aspcfs.modules.communications.base.SearchOperatorList" scope="request"/>
<jsp:useBean id="NumberOperatorList" class="org.aspcfs.modules.communications.base.SearchOperatorList" scope="request"/>
<jsp:useBean id="SearchForm" class="org.aspcfs.modules.communications.beans.SearchFormBean" scope="request"/>
<jsp:useBean id="SCL" class="org.aspcfs.modules.communications.base.SearchCriteriaList" scope="request"/>
<jsp:useBean id="ContactTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AccountTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactSource" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkNumber.js"></script>
<SCRIPT LANGUAGE="JavaScript">
function checkValue() {
  if (document.getElementById("searchValue").value.indexOf("[") != -1 ||
      document.getElementById("searchValue").value.indexOf("]") != -1 ||
      document.getElementById("searchValue").value.indexOf("^") != -1 ||
      document.getElementById("searchValue").value.indexOf("|") != -1 ||
      document.getElementById("searchValue").value.indexOf("*") != -1) {
    alert(label("check.avoided.text","Please enter a valid input. Avoid the characters *[^|]"));
    return false;
  }
  if (document.searchForm.fieldSelect.selectedIndex == 4) {
    if (checkNullString(document.getElementById("searchValue").value)) {
      alert(label("check.valid.input","Please enter a valid input"));
      return false;
    }
  } else if (document.searchForm.fieldSelect.selectedIndex == 5) {
    if (checkNullString(document.getElementById("searchValue").value)) {
      alert(label("check.number.invalid","- Please enter a valid Number\r\n"));
      return false;
    } else {
      if (!checkNaturalNumber(document.getElementById("searchValue").value)) {
        alert(label("check.number.invalid","- Please enter a valid Number\r\n"));
        return false;
      }
    }
  } else if (document.searchForm.fieldSelect.selectedIndex == 3) {
    if (checkNullString(document.getElementById("searchValue").value)) {
        alert(label("check.valid.date","Please enter a valid date"));
      return false;
    } else {
      if (!checkDate(document.getElementById("searchValue").value)) {
        alert(label("check.valid.date","Please enter a valid date"));
        return false;
      }
    }
  } else if (!(document.searchForm.fieldSelect.options[document.searchForm.fieldSelect.selectedIndex].value == 8) && 
      !(document.searchForm.fieldSelect.options[document.searchForm.fieldSelect.selectedIndex].value == 11))  {
    if (checkNullString(document.getElementById("searchValue").value)) {
      alert(label("check.valid.input","Please enter a valid input"));
      return false;
    }
  }
  return true;
}
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
    while (x.hasNext()) {
      LookupElement thisContactType = (LookupElement)x.next();
      if (!thisContactType.isGroup()) {
        if (thisContactType.getEnabled() || (!thisContactType.getEnabled() && !ContactTypeList.getExcludeDisabledIfUnselected())) {%>
          insertOption("<%= StringUtils.jsStringEscape(thisContactType.getDescription()) %>", "<%= thisContactType.getCode() %>", "idSelect");
    <%}} else {%>
      // option group
        insertOptionGroup("<%= StringUtils.jsStringEscape(thisContactType.getDescription()) %>",  "idSelect");
    <%}
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
        if (thisElt.getEnabled() || (!thisElt.getEnabled() && !ContactTypeList.getExcludeDisabledIfUnselected())) {%>
          insertOption("<%= thisElt.getDescription() %>", "<%=thisElt.getCode()%>", "idSelect");
      <%}
      }
    }%>
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
  if (checkNullString(form.groupName.value)) {
    message += label("check.group.name","- Group Name is required\r\n");
    formTest = false;
  }
  saveValues();
  if (form.searchCriteriaText.value == "") {
    message += label("check.criteria","- Criteria is required\r\n");
    formTest = false;
  }  

  if (formTest == false) {
    alert(label("check.campaign.criteria","Criteria could not be processed, please check the following:\r\n\r\n") + message);
    return false;
  } 
  return true;
}

</SCRIPT>
<body onLoad="javascript:document.searchForm.groupName.focus()">
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
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="CampaignManager.do"><dhv:label name="communications.campaign.Communications">Communications</dhv:label></a> >
<a href="CampaignManagerGroup.do?command=View"><dhv:label name="campaign.viewGroups">View Groups</dhv:label></a> >
<dhv:evaluate if="<%= !"list".equals(request.getParameter("return")) %>">
<a href="CampaignManagerGroup.do?command=Details&id=<%= SCL.getId() %>"><dhv:label name="campaign.groupDetails">Group Details</dhv:label></a> >
</dhv:evaluate>
<dhv:label name="campaign.groupDetails">Group Details</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<input type="submit" value="<dhv:label name="global.button.save">Save</dhv:label>" name="Save">
<dhv:evaluate if="<%= "list".equals(request.getParameter("return")) %>">
  <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="window.location.href='CampaignManagerGroup.do?command=View'">
</dhv:evaluate>
<dhv:evaluate if="<%= !"list".equals(request.getParameter("return")) %>">
  <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="window.location.href='CampaignManagerGroup.do?command=Details&id=<%= SCL.getId() %>'">
</dhv:evaluate>
<input type="button" value="<dhv:label name="button.preview">Preview</dhv:label>" onClick="javascript:popPreview()">
<br>
&nbsp;<br>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <dhv:label name="campaign.updateContactGroupDetails">Update contact group details</dhv:label>
    </th>
  </tr>
  <tr>
    <td class="formLabel" nowrap>
      <dhv:label name="admin.groupName">Group Name</dhv:label>
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
      <dhv:label name="campaign.updateContactCriteria.text">Update contact criteria for this group</dhv:label>
    </th>
  </tr>
	<tr>
    <td style="text-align: center;" valign="center" width="50%">
      <table width="100%" border="0" cellpadding="2" cellspacing="0" class="empty">
        <tr>
          <td class="row1" colspan="2">
            <dhv:label name="campaign.chooseSpecificContacts.colon">Choose specific contacts:</dhv:label>
          </td>
        </tr>
        <tr>
          <td colspan="2" style="text-align: center;">
            [<a href="javascript:popContactsListMultipleCampaign('listViewId','1');"><dhv:label name="contacts.addRemove">Add/Remove Contacts</dhv:label></a>]
          </td>
        </tr>
        <tr>
          <td colspan="2">
            &nbsp;
          </td>
        </tr>
        <tr>
          <td class="row1" colspan="2">
            <dhv:label name="campaign.defineCriteria.text">Define criteria to generate a list:</dhv:label>
          </td>
        </tr>
        <tr>
          <td style="text-align: right;" nowrap>
            <dhv:label name="accounts.accounts_contacts_validateimport.Field">Field</dhv:label>
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
            <span name="searchText1" ID="searchText1"><dhv:label name="contact.searchText">Search Text</dhv:label></span>
          </td>
          <td width="100%" valign="center">
            <span name="searchText2" ID="searchText2"><input type="text" name="searchValue" id="searchValue" value="" size="25"  maxlength="125"></span> 
          </td>
        </tr>
        <tr>
          <td style="text-align: right;">
            <span name="new1a" ID="new1a" style="display:none">&nbsp;</span>
          </td>
          <td valign="center">
            <span name="new1" ID="new1" style="display:none">
            <a href="javascript:popCalendar('searchForm', 'searchValue', '<%= User.getLocale().getLanguage() %>', '<%= User.getLocale().getCountry() %>');"><img src="images/icons/stock_form-date-field-16.gif" height="16" width="16" border="0" align="absmiddle"></a>
            </span>
          </td>
        </tr>
        <tr>
          <td style="text-align: right;" nowrap>
            <span name="new0a" ID="new0a" style="display:none"><dhv:label name="contact.searchText">Search Text</dhv:label></span>
          </td>
          <td valign="center">
            <span name="new0" ID="new0" style="display:none"><select id="idSelect" name="idSelect" onChange="javascript:setText(document.searchForm.idSelect);"></select></span>
          </td>
        </tr>
        <tr>
          <td style="text-align: right;" nowrap>
            <dhv:label name="campaign.from">From</dhv:label>
          </td>
          <td width="100%" valign="center">
            <%= ContactSource.getHtml("contactSource", -1) %>
          </td>
        </tr>
        <tr>
          <td style="text-align: center;" colspan="2" nowrap>
            <br>
            <input type="button" value="<dhv:label name="accounts.accounts_reports_generate.AddR">Add ></dhv:label>" onclick="javascript:if (checkValue()) {addValues();}">
          </td>
        </tr>
      </table>
    </td>
		<td style="text-align: center;" valign="top" width="50%">
      <table width="100%" border="0" cellpadding="2" cellspacing="0" class="empty">
        <tr>
          <td class="row1">
            <dhv:label name="campaign.selectedCriteriaAndContacts.colon">Selected criteria and contacts:</dhv:label>
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
        <option value="-1"><dhv:label name="campaign.searchCriteria.label">----------------Search Criteria----------------</dhv:label></option>
			</select>
		<%}%>
      <br>
      &nbsp;<br>
      <input type="hidden" name="previousSelection" value="">
      <input type="button" value="<dhv:label name="button.remove">Remove</dhv:label>" onclick="removeValues()">
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
<input type="submit" value="<dhv:label name="global.button.save">Save</dhv:label>" name="Save">
<dhv:evaluate if="<%= "list".equals(request.getParameter("return")) %>">
  <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="window.location.href='CampaignManagerGroup.do?command=View'">
</dhv:evaluate>
<dhv:evaluate if="<%= !"list".equals(request.getParameter("return")) %>">
  <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="window.location.href='CampaignManagerGroup.do?command=Details&id=<%= SCL.getId() %>'">
</dhv:evaluate>
<input type="button" value="<dhv:label name="button.preview">Preview</dhv:label>" onClick="javascript:popPreview()">
</form>
</body>
