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
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<jsp:useBean id="configName" class="java.lang.String" scope="request"/>
<jsp:useBean id="allowMultiplePrices" class="java.lang.String" scope="request"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/div.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/editListForm.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript">
 function resetNumericFieldValue(propertyId){
  document.getElementById(propertyId).value = -1;
 }
 
 function setField(formField,thisValue,thisForm) {
    var frm = document.forms[thisForm];
    var len = document.forms[thisForm].elements.length;
    var i=0;
    for( i=0 ; i<len ; i++) {
      if (frm.elements[i].name == formField) {
        if(thisValue){
          frm.elements[i].value = "true";
        } else {
          frm.elements[i].value = "false";
        }
        break;
      }
    }
 }
 
 var currentItem = 5;
 function showItems(button) {
   for (var i = 0; i < 5; ++i) {
     currentItem++;
     showSpan('item' + currentItem);
   }
   if (currentItem == 30) {
    button.disabled = true;
   }
 }
</script>
<dhv:formMessage />
<table cellpadding="4" cellspacing="0" width="100%" class="details">
    <tr>
    <th colspan="2">
      <%
        String params = "optionName=" + toHtml(configName) ;
      %>
      <strong><dhv:label name="product.addNewOption" param="<%=params%>"><%=toHtml(configName)%> Option Properties</dhv:label></strong>
    </th>
  </tr>
	<%
		int count = 0;
		Iterator i = PropertyList.iterator();
		while (i.hasNext()) {
			OptionProperty property = (OptionProperty) i.next();
			//Show only the parameters that require input from the user
	%>
			<dhv:evaluate if="<%= (property.getIsForPrompting()) && (property.getType() == property.SIMPLE_PROPERTY) %>"><% ++count; %>
				<tr class="containerBody">
					<td class="formLabel"><%= toHtml(property.getDisplay()) %></td>
					<td class="empty"><%= property.getHtml(request) %></td>
				</tr>
			</dhv:evaluate>
	<%
  	}
	%>
	<dhv:evaluate if="<%= count == 0 %>">
		<tr class="containerBody">
			<td colspan="2"><dhv:label name="product.noPropertiesRequired">No Properties required.</dhv:label></td>
		</tr>
	</dhv:evaluate>
</table>
&nbsp;<br />
<dhv:evaluate if="<%= !("true".equals(allowMultiplePrices)) %>">
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="product.optionPriceAdjustment">Option Price Adjustment</dhv:label></strong>
    </th>
  </tr>
  <%
		int count1 = 0;
		Iterator j = PropertyList.iterator();
		while (j.hasNext()) {
			OptionProperty property = (OptionProperty) j.next();
			//Show only the parameters that require input from the user
	%>
			<dhv:evaluate if="<%= (property.getIsForPrompting()) && (property.getType() == property.BASEADJUST_PROPERTY) %>"><% ++count1; %>
				<tr class="containerBody">
					<td class="formLabel"><%= toHtml(property.getDisplay()) %></td>
					<td class="empty"  valign="top" nowrap>
            <table cellspacing="1" cellpadding="1" border="0">
              <tr>
                <td><%= applicationPrefs.get("SYSTEM.CURRENCY") %></td>
                <td width="100%"><%= property.getHtml(request) %></td>
              </tr>
            </table>
          </td>
				</tr>
			</dhv:evaluate>
	<%
  	}
	%>
  <dhv:evaluate if="<%= count1 == 0 %>">
		<tr class="containerBody">
			<td colspan="2"><dhv:label name="product.noPropertiesRequired">No Properties required.</dhv:label></td>
		</tr>
	</dhv:evaluate>
</table>
</dhv:evaluate>
<%-- Lookup List configurator --%>
<dhv:evaluate if="<%= "true".equals(allowMultiplePrices) %>">
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th><strong><dhv:label name="quotes.item">Item</dhv:label></strong></th>
    <th width="55%"><strong><dhv:label name="product.description">Description</dhv:label></strong></th>
    <th width="40%"><strong><dhv:label name="product.optionPriceAdjustment">Option Price Adjustment</dhv:label></strong></th>
    <th><strong><dhv:label name="product.enabled">Enabled</dhv:label></strong></th>
  </tr>
  <%
		int counter = 0;
    int rowcount = 0;
		Iterator k = PropertyList.iterator();
    while (k.hasNext()) {
			OptionProperty property = (OptionProperty) k.next();
			//Show only the parameters that require input from the user
      if (!property.getIsForPrompting() || property.getType() == property.SIMPLE_PROPERTY) {
        continue;
      }
      ++counter;
      if ((counter+2)%3 == 0) {
        ++rowcount;
      }
	%>
<dhv:evaluate if="<%= (counter+2)%3 == 0 %>">
    <tr class="containerBody" name="item<%= rowcount %>" id="item<%= rowcount %>" 
            <%= ((rowcount > 5 && "".equals(property.getValue().trim())) ? "style=\"display:none\"" : "") %>>
      <td class="formLabel" align="center"><%= "" + rowcount + "." %></td>
</dhv:evaluate>
			<dhv:evaluate if="<%= (property.getIsForPrompting() && property.getType() == property.BASEADJUST_PROPERTY) %>">
			    <td class="empty"  align="right" valign="top" nowrap>
            <table cellspacing="1" cellpadding="1" border="0">
              <tr>
                <td><%= applicationPrefs.get("SYSTEM.CURRENCY") %></td>
                <td width="100%"><%= property.getHtml(request) %></td>
              </tr>
            </table>
          </td>
      </dhv:evaluate>
      <dhv:evaluate if="<%= (property.getIsForPrompting() && property.getType() == property.LOOKUP_PROPERTY) %>">
			    <dhv:evaluate if="<%= property.getName().startsWith("boolean_") %>">
            <td class="empty" width="100%" align="center"><%= property.getHtml(request) %></td>
          </dhv:evaluate>
          <dhv:evaluate if="<%= !property.getName().startsWith("boolean_") %>">
            <td class="empty" width="100%"><%= property.getHtml(request) %></td>
          </dhv:evaluate>
      </dhv:evaluate>
<dhv:evaluate if="<%= counter%3 == 0 %>">
	</tr>
</dhv:evaluate>      
	<%
  	}
	%>
  <dhv:evaluate if="<%= counter%3 != 0 %>">
	<%
		while (counter%3 != 0) {
			++counter;
	%>
		<td>&nbsp;</td>
	<%
		}
	%>
	</tr>
</dhv:evaluate>
<dhv:evaluate if="<%= counter == 0 %>">
		<tr class="containerBody">
			<td colspan="4"><dhv:label name="product.noPropertiesRequired">No Properties required.</dhv:label></td>
		</tr>
</dhv:evaluate>
</table>
&nbsp;<br>
<input type="button" value="<dhv:label name="button.moreItems">More items >></dhv:label>" onclick="javascript:showItems(this);">
<br />
</dhv:evaluate>
<%-- End lookup list --%>
&nbsp;<br />
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="product.option.availability">Availability</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="product.enabled">Enabled</dhv:label>
    </td>
    <td>
      <input type="checkbox" name="chk1" onclick="javascript:setField('enabled', document.paramForm.chk1.checked, 'paramForm');" <%= (productOption.getEnabled() ? "checked" : "")%>>
      <input type="hidden" name="enabled" value="<%= productOption.getEnabled() %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="documents.details.startDate">Start Date</dhv:label>
    </td>
    <td>
      <zeroio:dateSelect form="paramForm" field="startDate" timestamp="<%= (productOption.getStartDate() != null ? productOption.getStartDate() : new java.sql.Timestamp(System.currentTimeMillis())) %>"/>
      <%= showAttribute(request, "startDateError") %>
    </td>
  </tr>
	<tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accountasset_include.ExpirationDate">Expiration Date</dhv:label>
    </td>
    <td>
      <zeroio:dateSelect form="paramForm" field="endDate" timestamp="<%= productOption.getEndDate() %>" />
      <%= showAttribute(request, "endDateError") %>
    </td>
  </tr>
</table>
