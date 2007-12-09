<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description:
  --%>
<%@ page import="java.util.*,org.aspcfs.modules.communications.base.*,org.aspcfs.utils.web.LookupElement" %>
<%@ page import="org.aspcfs.utils.*" %>
<jsp:useBean id="SearchFieldList" class="org.aspcfs.modules.communications.base.SearchFieldList" scope="request"/>
<jsp:useBean id="StringOperatorList" class="org.aspcfs.modules.communications.base.SearchOperatorList" scope="request"/>
<jsp:useBean id="DateOperatorList" class="org.aspcfs.modules.communications.base.SearchOperatorList" scope="request"/>
<jsp:useBean id="NumberOperatorList" class="org.aspcfs.modules.communications.base.SearchOperatorList" scope="request"/>
<jsp:useBean id="SearchForm" class="org.aspcfs.modules.communications.beans.SearchFormBean" scope="request"/>
<jsp:useBean id="ContactTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AccountTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactSource" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="SiteValueList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteCriteriaList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkNumber.js"></script>
<SCRIPT LANGUAGE="JavaScript">
function checkOwnerSite(item) {
  <dhv:evaluate if="<%= User.getUserRecord().getSiteId() == -1 %>">
  try {
    var ownerid = document.getElementById('ownerid').value;
    // TODO: this action asks the server for parameters that are fed to continueSetSite()
    // however, because the iframe is invoking the popup, this fails when
    // popup blocking is turned on
    var url = 'MyActionLists.do?command=GetSiteForUser&userId='+ownerid+'&item='+item;
    window.frames['server_commands'].location.href=url;
  } catch (oException) {
    if (item == 'contactspopup') {
      popContactsListMultipleCampaign('listViewId','1','<%= User.getUserRecord().getSiteId() == -1?"includeAllSites=true&sources="+source+"&siteId=-1":"mySiteOnly=true&siteId="+User.getUserRecord().getSiteId() %><%= request.getParameter("params") != null ?  "&" + request.getParameter("params") + "" : ""%>');
    }
  }
  </dhv:evaluate>
  <dhv:evaluate if="<%= User.getUserRecord().getSiteId() > -1 %>">
    popContactsListMultipleCampaign('listViewId','1','mySiteOnly=true&sources=<%=source%>&siteId=<%= User.getUserRecord().getSiteId() %><%= request.getParameter("params") != null ?  "&" + request.getParameter("params") + "" : ""%>');
  </dhv:evaluate>
}

function continueSetSite(value, item) {
  if (item == 'siteId') {
    var siteElement = document.getElementById('siteId2');
    var siteId = -1;
    if ('<%= SiteCriteriaList.size() > 1 %>' == 'true') {
      siteId = siteElement.options[siteElement.options.selectedIndex].value;
    } else {
      siteId = siteElement.value;
    }
    if (value == '-1' || value == siteId) {
      return;
    }
    alert(label('','Site restricted for the selected owner'));
    var i = 0;
    for (i = 0;i< siteElement.options.length; i++) {
      siteId = siteElement.options[i].value;
      if (siteId == value) {
        siteElement.options.selectedIndex = i;
        return;
      }
    }
  } else if (item == 'contactspopup') {
    var site = '';
    if (value == '-1') {
      site = 'includeAllSites=true&siteId=-1';
    } else {
      site = 'mySiteOnly=true&siteId='+value;
    }
    popContactsListMultipleCampaign('listViewId','1',site+'<%= request.getParameter("params") != null ?  "&" + request.getParameter("params") + "" : ""%>');
    return;
  }
}

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
      !(document.searchForm.fieldSelect.options[document.searchForm.fieldSelect.selectedIndex].value == 11) &&
      !(document.searchForm.fieldSelect.options[document.searchForm.fieldSelect.selectedIndex].value == 12))  {
    if (checkNullString(document.getElementById("searchValue").value)) {
      alert(label("check.valid.input","Please enter a valid input"));
      return false;
    }
  }
  return true;
}
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
      if (thisContactType.isGroup()) {
%>
        // option group
        insertOptionGroup("<%= StringUtils.jsStringEscape(thisContactType.getDescription()) %>",  "idSelect");
    <%} else {
        if (thisContactType.getEnabled() || (!thisContactType.getEnabled() && !ContactTypeList.getExcludeDisabledIfUnselected())) {%>
          insertOption("<%= StringUtils.jsStringEscape(thisContactType.getDescription()) %>", '<%= thisContactType.getCode() %>', "idSelect");
      <%}
      }
    }
%>
		javascript:showSpan('new0');
    javascript:showSpan('new0a');
    javascript:hideSpan('searchText1');
    javascript:hideSpan('searchText2');    
    javascript:hideSpan('new1');
    javascript:hideSpan('new1a');
    javascript:hideSpan('searchSite1');
    javascript:showSpan('searchSite2a');
    javascript:showSpan('searchSite2b');
		document.searchForm.searchValue.value = document.searchForm.idSelect.options[document.searchForm.idSelect.selectedIndex].text;
	} else if (document.searchForm.fieldSelect.selectedIndex == 3) {
		javascript:hideSpan('new0');
    javascript:hideSpan('new0a');
		javascript:showSpan('new1');
    javascript:showSpan('new1a');
    javascript:showSpan('searchText1');
    javascript:showSpan('searchText2');
    javascript:hideSpan('searchSite1');
    javascript:showSpan('searchSite2a');
    javascript:showSpan('searchSite2b');
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
        if (thisElt.getEnabled() || (!thisElt.getEnabled() && !AccountTypeList.getExcludeDisabledIfUnselected())) {%>
          insertOption("<%= StringUtils.jsStringEscape(thisElt.getDescription()) %>", '<%= thisElt.getCode() %>', 'idSelect');
      <%}
      }
    }%>
		javascript:showSpan('new0');
    javascript:showSpan('new0a');
    javascript:hideSpan('searchText1');
    javascript:hideSpan('searchText2');    
    javascript:hideSpan('searchSite1');
    javascript:showSpan('searchSite2a');
    javascript:showSpan('searchSite2b');
		document.searchForm.searchValue.value = document.searchForm.idSelect.options[document.searchForm.idSelect.selectedIndex].text;
	} else if (document.searchForm.fieldSelect.options[document.searchForm.fieldSelect.selectedIndex].value == 12) {
		javascript:hideSpan('new0');
    javascript:hideSpan('new0a');
    javascript:hideSpan('new1');
    javascript:hideSpan('new1a');
    javascript:hideSpan('searchText1');
    javascript:hideSpan('searchText2');    
    javascript:showSpan('searchSite1');
    javascript:hideSpan('searchSite2a');
    javascript:hideSpan('searchSite2b');
    if (document.searchForm.allSites){
		  document.searchForm.searchValue.value = document.searchForm.siteId1.options[document.searchForm.siteId1.selectedIndex].text;
    } else {
		  document.searchForm.searchValue.value = document.searchForm.siteName1.value;
    }
  } else {
		javascript:hideSpan('new0');
    javascript:hideSpan('new0a');
    javascript:hideSpan('new1');
    javascript:hideSpan('new1a');
    javascript:showSpan('searchText1');
    javascript:showSpan('searchText2');
    javascript:hideSpan('searchSite1');
    javascript:showSpan('searchSite2a');
    javascript:showSpan('searchSite2b');
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
  function deleteOptions(optionListId){
    var frm = document.getElementById(optionListId);
    frm.innerHTML="";
    while (frm.options.length>0){
      deleteIndex=frm.options.length-1;
      frm.options[deleteIndex]=null;
    }
  }
  
  
  function insertOption(text,value,optionListId){
    var frm = document.getElementById(optionListId);
    if (frm.selectedIndex>0){
      insertIndex=frm.selectedIndex;
    }else{
      insertIndex= frm.options.length;
    }
    frm.options[insertIndex] = new Option(text,value);
  }
  
  
  function insertOptionGroup(text, optionListId) {
    var frm = document.getElementById(optionListId);
    var optGroup = document.createElement('optgroup');
    optGroup.label = text;
    frm.appendChild(optGroup);
  }
  function resetNumericFieldValue(fieldId){
    document.getElementById(fieldId).value = -1;
  } 
	function returnSource() {
		var sources = document.getElementsByName('source');
  	var source = null;
  	  for (var i = 0; i < sources.length; i++) { 
    		if(sources[i].checked){
  			source = sources[i];
  			return source.value; 
  		}
  	}
	}

  function selectEmployees(){
  	var sources = document.getElementsByName('source');
  	var source = null;
  	  for (var i = 0; i < sources.length; i++) { 
    		if(sources[i].checked){
  			source = sources[i];
  			break; 
  		}
  	}
  	var name = document.forms['searchForm'].groupName.value;
  	window.location.href='CampaignManagerGroup.do?command=Add&source=' + source.value + '&name=' + name;
  }
  
</SCRIPT>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/searchForm.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/popContacts.js?v=20070827"></script>
<script language="JavaScript" type="text/javascript">
var searchCriteria = new Array();
searchField = new Array();
<%
	Iterator f = SearchFieldList.iterator();
	int fieldArrayID = -1;
 	if (f.hasNext()){
		while (f.hasNext()){
			SearchField thisSearchField = (SearchField)f.next();
			if (("sales".equals(source))||("employees".equals(source))){
				if ((!"typeId".equals(thisSearchField.getFieldName()))&&(!"accountTypeId".equals(thisSearchField.getFieldName()))) {
				fieldArrayID ++;
				if ("namefirst".equals(thisSearchField.getFieldName())){
					%> 
					searchField[<%= fieldArrayID %>] = new field(<%= thisSearchField.getId() %>, "<%=StringUtils.jsStringEscape("First Name")%>" , <%= thisSearchField.getFieldTypeId() %>);
					<% 										
				}	
				if ("namelast".equals(thisSearchField.getFieldName())){
					%> 
					searchField[<%= fieldArrayID %>] = new field(<%= thisSearchField.getId() %>,  "<%=StringUtils.jsStringEscape("Last Name")%>" , <%= thisSearchField.getFieldTypeId() %>);
					<% 										
				}			
				if (!("namefirst".equals(thisSearchField.getFieldName()))&&(!"namelast".equals(thisSearchField.getFieldName()))){
					if ((("employees".equals(source))&&!("importName".equals(thisSearchField.getFieldName())))||("sales".equals(source))){
						%> 
						searchField[<%= fieldArrayID %>] = new field(<%= thisSearchField.getId() %>, "<%=StringUtils.jsStringEscape(thisSearchField.getDescription())%>" , <%= thisSearchField.getFieldTypeId() %>);
						<%
					}	 										
				}
				}
			}
			if ("contacts".equals(source)){
				fieldArrayID ++;
				if ("namefirst".equals(thisSearchField.getFieldName())){
					%> 
					searchField[<%= fieldArrayID %>] = new field(<%= thisSearchField.getId() %>,  "<%=StringUtils.jsStringEscape("First Name")%>" , <%= thisSearchField.getFieldTypeId() %>);
					<% 										
				}	
				if ("namelast".equals(thisSearchField.getFieldName())){
					%> 
					searchField[<%= fieldArrayID %>] = new field(<%= thisSearchField.getId() %>, " <%=StringUtils.jsStringEscape("Last Name") %>", <%= thisSearchField.getFieldTypeId() %>);
					<% 										
				}			
				if (!("namefirst".equals(thisSearchField.getFieldName()))&&(!"namelast".equals(thisSearchField.getFieldName()))){
					%> 
					searchField[<%= fieldArrayID %>] = new field(<%= thisSearchField.getId() %>, "<%=StringUtils.jsStringEscape(thisSearchField.getDescription())%>" , <%= thisSearchField.getFieldTypeId() %>);
					<% 										
				}
			}
} }
 %>
stringOperators = new Array();
<%
	Iterator s = StringOperatorList.iterator();
	int stringArrayID = -1;
 	if (s.hasNext()){
		while (s.hasNext()){
			stringArrayID ++;
			SearchOperator thisStringOperator = (SearchOperator)s.next();
%> 
stringOperators[<%= stringArrayID %>] = new operator(<%= thisStringOperator.getId() %>, "<%= StringUtils.jsStringEscape(thisStringOperator.getOperator()) %>", "<%= StringUtils.jsStringEscape(thisStringOperator.getDisplayText()) %>");
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
dateOperators[<%= dateArrayID %>] = new operator(<%= thisDateOperator.getId() %>, "<%= StringUtils.jsStringEscape(thisDateOperator.getOperator()) %>", "<%= StringUtils.jsStringEscape(thisDateOperator.getDisplayText()) %>");
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
numberOperators[<%= numberArrayID %>] = new operator(<%= thisNumberOperator.getId() %>, "<%= StringUtils.jsStringEscape(thisNumberOperator.getOperator()) %>", "<%= StringUtils.jsStringEscape(thisNumberOperator.getDisplayText()) %>");
<% } } %>
listOfOperators = new Array()
listOfOperators[0] = stringOperators
listOfOperators[1] = dateOperators
listOfOperators[2] = numberOperators
</script>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <dhv:label name="contact.selectCriteriaForGroup">Select criteria for this group</dhv:label>&nbsp;<font color="red">*</font>
    </th>
  </tr>
	<tr style="text-center: center;">
  	<td colspan="2" style="text-align: center;">
  	<% if ("contacts".equals(source)) { %>
			<INPUT TYPE="radio" NAME="source" value="contacts" id="1" onClick="javascript:selectEmployees();" CHECKED><dhv:label name="campaign.contacts">Contacts</dhv:label>
			<INPUT TYPE="radio" NAME="source" value="sales" id="2" onClick="javascript:selectEmployees();"><dhv:label name="campaign.leads">Leads</dhv:label>
			<INPUT TYPE="radio" NAME="source" value="employees" id="3" onClick="javascript:selectEmployees();"><dhv:label name="campaign.employees">Employees</dhv:label>
		<% } %>
		<% if ("sales".equals(source)) { %>
			<INPUT TYPE="radio" NAME="source" value="contacts" id="1" onClick="javascript:selectEmployees();"><dhv:label name="campaign.contacts">Contacts</dhv:label>
			<INPUT TYPE="radio" NAME="source" value="sales" id="2" onClick="javascript:selectEmployees();" CHECKED><dhv:label name="campaign.leads">Leads</dhv:label>
			<INPUT TYPE="radio" NAME="source" value="employees" id="3" onClick="javascript:selectEmployees();"><dhv:label name="campaign.emploees">Employees</dhv:label>
		<% } %>
		<% if ("employees".equals(source)) { %>
			<INPUT TYPE="radio" NAME="source" value="contacts" id="1" onClick="javascript:selectEmployees();"><dhv:label name="campaign.contacts">Contacts</dhv:label>
			<INPUT TYPE="radio" NAME="source" value="sales" id="2" onClick="javascript:selectEmployees();"><dhv:label name="campaign.leads">Leads</dhv:label>
			<INPUT TYPE="radio" NAME="source" value="employees" id="3" onClick="javascript:selectEmployees();" CHECKED><dhv:label name="campaign.emploees">Employees</dhv:label>
		<% } %>
  	</td> 
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
          	<% if ("contacts".equals(source)){ %>
            	[<a href="javascript:checkOwnerSite('contactspopup');"><dhv:label name="contacts.addRemove">Add/Remove Contacts</dhv:label></a>]
            <% } else if ("sales".equals(source)) { %>
            	[<a href="javascript:checkOwnerSite('contactspopup');"><dhv:label name="contacts.addRemoveLeads">Add/Remove Leads</dhv:label></a>]
            <% } else if ("employees".equals(source)) { %>
            	[<a href="javascript:checkOwnerSite('contactspopup');"><dhv:label name="contacts.addRemoveEmployees">Add/Remove Employees</dhv:label></a>]
            <% } %>
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
            <dhv:label name="campaign.operator">Operator</dhv:label>
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
            <span name="searchSite1" ID="searchSite1"  style="display:none">
                <dhv:evaluate if="<%= User.getSiteId() == -1 %>" >
                  <% SiteValueList.setJsEvent("onChange=\"javascript:setText(document.searchForm.siteId1);\""); %>
                  <%= SiteValueList.getHtmlSelect("siteId1",-1) %>
                  <input type="hidden" name="allSites" value="true" >
                </dhv:evaluate>
                <dhv:evaluate if="<%= User.getSiteId() != -1 %>" >
                   <%= SiteValueList.getSelectedValue(User.getSiteId()) %>
                  <input type="hidden" name="siteId1" value="<%=User.getSiteId()%>" >
                  <input type="hidden" name="siteName1" value="<%= SiteValueList.getSelectedValue(User.getSiteId()) %>" >
                </dhv:evaluate>
          </span>
         </td>
        </tr>
        <dhv:evaluate if="<%="contacts".equals(source)%>">
        <tr>
          <td style="text-align: right;" nowrap>
            <dhv:label name="campaign.from">From</dhv:label>
          </td>
          <td width="100%" valign="center">
            <%= ContactSource.getHtml("contactSource", -1) %>
          </td>
        </tr>
        </dhv:evaluate>
        <dhv:evaluate if="<%="sales".equals(source)%>">
        <td style="text-align: right;" nowrap>
            <dhv:label name="campaign.from">From</dhv:label>
          </td>
          <td width="100%" valign="center">
            <%= ContactSource.getHtml("contactSource", 5) %>
          </td>
        </dhv:evaluate>
        <dhv:evaluate if="<%="employees".equals(source)%>">
        <td style="text-align: right;" nowrap>
            <dhv:label name="campaign.from">From</dhv:label>
          </td>
          <td width="100%" valign="center">
            <%= ContactSource.getHtml("contactSource", 4) %>
          </td>
        </dhv:evaluate>

        <tr>
          <td style="text-align: right;" nowrap>
            <span name="searchSite2a" ID="searchSite2a">
              <dhv:evaluate if="<%= SiteCriteriaList.size() > 1 %>">
                <dhv:label name="campaign.at">At</dhv:label>
              </dhv:evaluate> 
            </span>
          </td>
          <td width="100%" valign="center">
            <span name="searchSite2b" ID="searchSite2b">
              <dhv:evaluate if="<%= SiteCriteriaList.size() > 1 %>">
                <dhv:evaluate if="<%= User.getSiteId() == -1 %>" >
                  <% SiteCriteriaList.setJsEvent("id=\"siteId2\" onChange=\"checkOwnerSite('siteId');\""); %>
                  <%= SiteCriteriaList.getHtmlSelect("siteId2",-1) %>
                </dhv:evaluate>
                <dhv:evaluate if="<%= User.getSiteId() != -1 %>" >
                   <%= SiteCriteriaList.getSelectedValue(User.getSiteId()) %>
                  <input type="hidden" name="siteId2" value="<%=User.getSiteId()%>" >
                  <input type="hidden" name="siteName2" value="<%= SiteCriteriaList.getSelectedValue(User.getSiteId()) %>" >
                </dhv:evaluate>
              </dhv:evaluate> 
              <dhv:evaluate if="<%= SiteCriteriaList.size() <= 1 %>">
                <input type="hidden" name="siteId2" value="-1" />
                <input type="hidden" name="siteName2" value="<%= SiteCriteriaList.getSelectedValue(User.getSiteId()) %>" />
              </dhv:evaluate>
            </span>
          </td>
        </tr>
        <tr>
          <td style="text-align: center;" colspan="2" nowrap>
            <br>
            <input type="button" value="<dhv:label name="accounts.accounts_reports_generate.AddR">Add ></dhv:label>" onclick="javascript:if(checkValue()) {addValues();}">
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
          <td><%= showAttribute(request, "criteriaError") %>
            &nbsp;
          </td>
        </tr>
        <tr>
          <td style="text-align: center;">
      <table width="100%" border="0" cellpadding="2" cellspacing="0" class="empty">
        <tr><td align="center">
		<% if (SCL.size() > 0) {%>
      <% SCL.setHtmlSelectIdName("listViewId"); %>
			<%= SCL.getHtmlSelect("searchCriteria") %>
		<%} else {%>
			<select name="searchCriteria" id="listViewId" size="10">
        <option value="-1"><dhv:label name="campaign.searchCriteria.label">----------------Search Criteria----------------</dhv:label></option>
			</select>
		<%}%>
    </td><td valign="top" align="left">
      <%= showAttribute(request,"textError") %>
    </td>
    </tr>
    <tr>
      <td colspan="2" align="center">
      <br>
      &nbsp;<br>
      <input type="hidden" name="previousSelection" value="" />
      <input type="button" value="<dhv:label name="button.remove">Remove</dhv:label>" onclick="removeValues()" />
      </td></tr></table>
          </td>
        </tr>
      </table>
    </td>
	</tr>
</table>
<input type="hidden" name="searchCriteriaText" value="">
<iframe src="../empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
