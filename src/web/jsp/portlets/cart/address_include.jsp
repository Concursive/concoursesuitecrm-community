<%@ page import="org.aspcfs.utils.StringUtils"%>
<jsp:directive.page import="org.aspcfs.utils.web.LookupList" />
<%--
- Copyright(c) 2006 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
- Version: $Id: accounts_add.jsp 13563 2005-12-12 16:13:25Z mrajkowski $
- Description:
--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ taglib uri="/WEB-INF/portlet.tld" prefix="portlet"%>
<%@ include file="../../initPage.jsp"%>
<script type="text/javascript">
function update(countryObj,url) {
	var country = document.forms['addAddress'].elements[countryObj].value;
    window.frames['server_commands'].location.href=url+"&countryId="+country;
  }
   function continueUpdateState(showText) {
    if(showText == 'true'){
      hideSpan('state1s');
      showSpan('state2s');
    } else {
      hideSpan('state2s');
      showSpan('state1s');
    }
  }

  var states = new Array();
  var initStates = false;
  function resetStateList(country, stateObj) {
    var stateSelect = document.forms['addAddress'].elements['address'+stateObj+'state'];
    var i = 0;
    if (initStates == false) {
      for(i = stateSelect.options.length -1; i > 0 ;i--) {
        var state = new Array(stateSelect.options[i].value, stateSelect.options[i].text);
        states[states.length] = state;
      }
    }
    if (initStates == false) {
      initStates = true;
    }
    stateSelect.options.length = 0;
    for(i = states.length -1; i > 0 ;i--) {
      var state = states[i];
      if (state[0].indexOf(country) != -1 || country == label('option.none','-- None --')) {
        stateSelect.options[stateSelect.options.length] = new Option(state[1], state[0]);
      }
    }
  }
  
  </script>

<jsp:useBean id="addressItem"
	class="org.aspcfs.modules.orders.base.OrderAddress" scope="request" />
<jsp:useBean id="typeList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="StateSelect" class="org.aspcfs.utils.web.StateSelect"
	scope="request" />
<jsp:useBean id="CountrySelect"
	class="org.aspcfs.utils.web.CountrySelect" scope="request" />
<input type="hidden" name="addressId" value="<%=addressItem.getId()%>">

<table cellpadding="4" cellspacing="0" border="0">
	<tr>
		<th colspan="2">
			<strong>Add Address</strong>
		</th>
	</tr>
	<tr>
		<td>
			Address Type
		</td>
		<td>
			<%=typeList.getHtmlSelect("type", addressItem.getType())%>
		</td>
	</tr>
	<tr>
		<td>
			Address Line 1
		</td>
		<td>
			<input type="text" name="address1" size="25"
				value="<%=addressItem.getStreetAddressLine1()%>" />
		</td>
	</tr>
	<tr>
		<td>
			Address Line 2
		</td>
		<td>
			<input type="text" name="address2" size="25"
				value="<%=addressItem.getStreetAddressLine2()%>" />
		</td>
	</tr>
	<tr>
		<td>
			Address Line 3
		</td>
		<td>
			<input type="text" name="address3" size="25"
				value="<%=addressItem.getStreetAddressLine3()%>" />
		</td>
	</tr>
	<tr>
		<td>
			Address Line 4
		</td>
		<td>
			<input type="text" name="address4" size="25"
				value="<%=addressItem.getStreetAddressLine4()%>" />
		</td>
	</tr>
	<tr>
		<td>
			City
		</td>
		<td>
			<input type="text" name="city" size="25"
				value="<%=toHtmlValue(addressItem.getCity())%>" />
		</td>
	</tr>
	<tr>
		<td>
			State/Province
		</td>
		<td>
			<span name="state1s" ID="state1s"
				style="<%=StateSelect.hasCountry(addressItem.getCountry()) ? ""
          : " display:none"%>">
				<%=StateSelect.getHtmlSelect("state1", addressItem.getCountry(),
              addressItem.getState())%> </span>
			<%-- If selected country is not US/Canada use textfield --%>
			<span name="state2s" ID="state2s"
				style="<%=!StateSelect.hasCountry(addressItem.getCountry()) ? ""
          : " display:none"%>">
				<input type="text" size="25" name="<%="state2"%>"> </span>
		</td>
	</tr>
	<tr>
		<td>
			Country
		</td>
		<td>

			<portlet:renderURL var="url">
				<portlet:param name="viewType" value="selectState" />
			</portlet:renderURL>
			<%
			          CountrySelect.setJsEvent("onChange=\"javascript:update('countryId', '"
			          + pageContext.getAttribute("url") + "');\"");
			%>
			<%=CountrySelect.getHtml("countryId", addressItem.getCountry())%>
		</td>
	</tr>
</table>
<iframe src="empty.html" name="server_commands" id="server_commands"
	style="visibility:hidden" height="0"></iframe>
