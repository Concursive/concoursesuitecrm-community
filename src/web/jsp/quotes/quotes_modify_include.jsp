<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<script type="text/javascript">
  function checkForm(form) {
    formTest = true;
    message = "";
    <dhv:evaluate if="<%= changeContact %>">
    if (form.contactId.options[form.contactId.selectedIndex].value == "-1") { 
      message += label("check.ticket.contact.entered","- Check that a Contact is selected\r\n");
      formTest = false;
    }
    </dhv:evaluate>
    if (checkNullString(form.shortDescription.value)) { 
      message += label("description.required","- Check that description is entered\r\n");
      formTest = false;
    }
    if (form.emailAddress.value != "" && !checkEmail(form.emailAddress.value)) {
      message += label("check.quote.email.entered","- Check that email address is entered\r\n");
      formTest = false;
    }
    if (form.phoneNumber.value != "" && !checkPhone(form.phoneNumber.value)) {
      message += label("check.quote.phone.entered","- Check that a valid phone number is entered\r\n");
      formTest = false;
    }
    if (formTest == false) {
      alert(label("check.form","Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    }

   <dhv:evaluate if="<%= isPopup(request) %>">
    window.close();
   </dhv:evaluate>
    return true;
  }

  function checkContactId(form) {
    <dhv:evaluate if="<%= changeContact %>">
    if (form.contactId.options[form.contactId.selectedIndex].value == -1) {
      alert(label("select.organization.and.contact","Please select an Organization and a Contact"));
      return false;
    }
    </dhv:evaluate>
    <dhv:evaluate if="<%= !changeContact %>">
    if (form.contactId.value == -1) {
      alert(label("select.organization.and.contact","Please select an Organization and a Contact"));
      return false;
    }
    </dhv:evaluate>
    return true;
  }
  
  function getContactId(form) {
    var contactId = -1;
    <dhv:evaluate if="<%= changeContact %>">
      contactId = form.contactId.options[form.contactId.options.selectedIndex].value;
    </dhv:evaluate>
    <dhv:evaluate if="<%= !changeContact %>">
      contactId = form.contactId.value;
    </dhv:evaluate>
    return contactId
  }
  
  function populateEmailAddress( email, type) {
    if (email != "") {
      document.forms['addQuote'].emailAddress.value = email;
    }
  }

  function populatePhoneNumber(number, type, hiddenField) {
    if (number != "") {
      document.getElementById(hiddenField).value = number;
    }
  }
  
  function populateAddress(line1, line2, line3, line4, city, state, otherState, zip, country, type) {
    var addressValue = "";
    if (line1 != "") {
      addressValue = line1;
    }
    if (line2 != "") {
      addressValue += "\r\n" + line2;
    }
    if (line3 != "") {
      addressValue += "\r\n" + line3;
    }
    if (line4 != "") {
      addressValue += "\r\n" + line4;
    }
    if (city != "") {
      addressValue += "\r\n" + city;
    }
    if (state != "") {
      addressValue += ", " + state;
    } else if (otherState != "") {
      addressValue += "\r\n" + otherState;
    }
    if (zip != "") {
      addressValue += " " + zip;
    }
    if (country != "") {
      addressValue += "\r\n" + country;
    }
    document.forms['addQuote'].address.value = addressValue;
  }
  
  
  function checkOrgIdForOpportunity(){
    var orgId = document.forms['addQuote'].orgId.value; 
    if ( orgId != '-1') {
      popOpportunityList('headerId','changeopportunity', '&orgId='+orgId);
    } else {
      alert(label("select.organization.first","Please select an organization first"));
    }
  }

  function changeDivContent(divName, divContents) {
    if(document.layers){
      // Netscape 4 or equiv.
      divToChange = document.layers[divName];
      divToChange.document.open();
      divToChange.document.write(divContents);
      divToChange.document.close();
    } else if(document.all){
      // MS IE or equiv.
      divToChange = document.all[divName];
      divToChange.innerHTML = divContents;
    } else if(document.getElementById){
      // Netscape 6 or equiv.
      divToChange = document.getElementById(divName);
      divToChange.innerHTML = divContents;
    }
    //when the content of any of the select items changes, do something here
    if(document.forms['addQuote'].orgId.value != '-1' && divName == 'changeaccount'){
      updateContactList();
      document.forms['addQuote'].headerId.value = -1;
      changeDivContent('changeopportunity', label('none.selected','None Selected'));
    }
  }

  function updateContactList() {
    var sel = document.forms['addQuote'].elements['orgId'];
    var value = document.forms['addQuote'].orgId.value;
    var url = "Quotes.do?command=OrganizationJSList&orgId=" + escape(value);
    window.frames['server_commands'].location.href=url;
  }

</script>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="4">
      <strong><dhv:label name="<%= titleLabel %>" param="<%= quoteParams %>"><%= toHtml(title) %></dhv:label></strong>
    </th>
  </tr>
  <dhv:include name="quote-description" none="true">
  <tr class="containerBody">
    <td class="formLabel" valign="top"><dhv:label name="accounts.accountasset_include.Description">Description</dhv:label></td>
    <td>
      <input type="text" name="shortDescription" value="<%= toHtmlValue(quoteBean.getShortDescription()) %>" size="58"/>
      <font color="red">*</font><%= showAttribute(request,"shortDescriptionError") %>
    </td>
  </tr>
  </dhv:include>
  <dhv:include name="quote-organization" none="true">
    <tr class="containerBody">
      <td class="formLabel" valign="top">
        <dhv:label name="accounts.accounts_add.Organization">Organization</dhv:label>
      </td>
      <td>
        <table cellspacing="0" cellpadding="0" border="0" class="empty">
          <tr>
            <td>
              <div id="changeaccount">
                <dhv:evaluate if="<%= quoteBean.getOrgId() != -1 %>">
                  <%= toHtml(quoteBean.getName()) %>
                </dhv:evaluate>
                <dhv:evaluate if="<%= quoteBean.getOrgId() == -1 %>">
                  <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
                </dhv:evaluate>
              </div>
            </td>
            <td>
              <input type="hidden" name="orgId" id="orgId" value="<%= quoteBean.getOrgId() %>"/>
              <dhv:evaluate if="<%= changeAccount %>">
                &nbsp;<font color="red">*</font>
                <%= showAttribute(request, "orgIdError") %>
                [<a href="javascript:popAccountsListSingle('orgId','changeaccount', 'showMyCompany=false<%= User.getUserRecord().getSiteId() == -1? (quoteBean.getOrgId() != -1?"&thisSiteIdOnly=true&siteIdOrg="+quoteBean.getOrgId():"&includeAllSites=true&siteId=-1"):"&thisSiteIdOnly=true&siteId="+User.getUserRecord().getSiteId() %>&filters=all|my|disabled');">Select</a>]
              </dhv:evaluate>
            </td>
          </tr>
        </table>
      </td>
    </tr>	
  </dhv:include>
  <dhv:include name="quote-contact" none="true">
    <tr class="containerBody">
      <td class="formLabel" valign="top">
       <dhv:label name="accounts.accountasset_include.Contact">Contact</dhv:label>
      </td>
      <td valign="center">
      <dhv:evaluate if="<%= !changeContact %>">
        <% if (contactList.size() > 0 && contactList.getContactFromId(quoteBean.getContactId()) != null) { %>
          <%= toHtml(contactList.getContactFromId(quoteBean.getContactId()).getNameLastFirst()) %>
          <input type="hidden" name="contactId" id="contactId" value="<%= quoteBean.getContactId() %>"/>
        <% } else if (request.getAttribute("ContactDetails") != null) { %>
          <%= toHtml(((Contact) request.getAttribute("ContactDetails")).getNameLastFirst()) %>
          <input type="hidden" name="contactId" id="contactId" value="<%= ((Contact) request.getAttribute("ContactDetails")).getId() %>"/>
        <% } else { %>
          <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
          <input type="hidden" name="contactId" id="contactId" value="<%= quoteBean.getContactId() %>"/>
        <% } %>
      </dhv:evaluate><dhv:evaluate if="<%= changeContact %>">
        <% if (quoteBean.getOrgId() == -1 || contactList.size() == 0) { %>
              <%= contactList.getEmptyHtmlSelect(systemStatus, "contactId") %>
        <%} else {%>
              <%= contactList.getHtmlSelect("contactId", quoteBean.getContactId() ) %>
        <%}%>
        <font color="red">*</font><%= showAttribute(request, "contactIdError") %>
      </dhv:evaluate>
      </td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel" valign="top"><dhv:label name="quotes.emailAddress">Email Address</dhv:label></td>
      <td>
        <table border="0" cellpadding="0" cellspacing="0" class="empty"><tr><td valign="top">
          <input type="text" name="emailAddress" id="emailAddress" value="<%= toHtmlValue(quoteBean.getEmailAddress()) %>" size="25" />
        </td><td valign="top" nowrap>&nbsp; 
          [<a href="javascript:if(checkContactId(document.forms['addQuote'])){popContactEmailAddressListSingle(getContactId(document.forms['addQuote']));}"><dhv:label name="quotes.selectEmailAddress">Select Email Address</dhv:label></a>]
        </td></tr></table>
      </td>
    </tr>
  </dhv:include>
  <dhv:include name="quote-phoneNumber" none="true">
    <tr class="containerBody">
      <td class="formLabel" valign="top"><dhv:label name="quotes.phoneNumber">Phone Number</dhv:label></td>
      <td>
        <table border="0" cellpadding="0" cellspacing="0" class="empty"><tr><td valign="top">
          <input type="text" name="phoneNumber" id="phoneNumber" value="<%= toHtmlValue(quoteBean.getPhoneNumber()) %>" size="20" /> 
        </td><td valign="top" nowrap>&nbsp; 
          [<a href="javascript:if(checkContactId(document.forms['addQuote'])){popContactPhoneNumberListSingle(getContactId(document.forms['addQuote']),'phoneNumber');}"><dhv:label name="quotes.selectPhoneNumber">Select Phone Number</dhv:label></a>]
        </td></tr></table>
      </td>
    </tr>
  </dhv:include>
  <dhv:include name="quote-faxNumber" none="true">
    <tr class="containerBody">
      <td class="formLabel" valign="top"><dhv:label name="quotes.faxNumber">Fax Number</dhv:label></td>
      <td>
        <table border="0" cellpadding="0" cellspacing="0" class="empty"><tr><td valign="top">
          <input type="text" name="faxNumber" id="faxNumber" value="<%= toHtmlValue(quoteBean.getFaxNumber()) %>" size="20" /> 
        </td><td valign="top" nowrap>&nbsp; 
          [<a href="javascript:if(checkContactId(document.forms['addQuote'])){popContactPhoneNumberListSingle(getContactId(document.forms['addQuote']), 'faxNumber');}"><dhv:label name="quotes.selectFaxNumber">Select Fax Number</dhv:label></a>]
        </td></tr></table>
      </td>
    </tr>
  </dhv:include>
  <dhv:include name="quote-address" none="true">
    <tr class="containerBody">
      <td class="formLabel" valign="top"><dhv:label name="quotes.address">Address</dhv:label></td>
      <td>
        <table border="0" cellpadding="0" cellspacing="0" class="empty"><tr><td valign="top">
          <textarea cols="35" rows="4" id="address" name="address"><%= toString(quoteBean.getAddress()) %></textarea>
        </td><td valign="top" nowrap>&nbsp; 
            [<a href="javascript:if(checkContactId(document.forms['addQuote'])){popContactAddressListSingle(getContactId(document.forms['addQuote']));}"><dhv:label name="quotes.selectAddress">Select Address</dhv:label></a>]
        </td></tr></table>
      </td>
    </tr>
  </dhv:include>
  <dhv:include name="quote-opportunity" none="true">
    <tr class="containerBody">
      <td class="formLabel" valign="top">
        <dhv:label name="quotes.opportunity">Opportunity</dhv:label>
      </td>
      <td>
        <table cellspacing="0" cellpadding="0" border="0" class="empty">
          <tr>
            <td>
              <div id="changeopportunity">
                <dhv:evaluate if="<%= quoteBean.getHeaderId() != -1 %>">
                  <dhv:label name="<%= opportunityNameLabel %>"><%= toHtml(opportunityName) %></dhv:label>
                </dhv:evaluate>
                <dhv:evaluate if="<%= quoteBean.getHeaderId() == -1 %>">
                  <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
                </dhv:evaluate>
              </div>
            </td>
            <td>
              <input type="hidden" name="headerId" id="headerId" value="<%= quoteBean.getHeaderId() %>"/> &nbsp;
              <dhv:evaluate if="<%= changeOpportunity %>">
                [<a href="javascript:checkOrgIdForOpportunity();"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>] &nbsp;
                [<a href="javascript:document.forms['addQuote'].headerId.value='-1';javascript:changeDivContent('changeopportunity', label('none.selected','None Selected'));"><dhv:label name="button.clear">Clear</dhv:label></a>]
                <%= showAttribute(request,"headerIdError") %>
              </dhv:evaluate>
              &nbsp;
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </dhv:include>
  <dhv:include name="quote-delivery" none="true">
    <tr class="containerBody">
      <td class="formLabel" valign="top"><dhv:label name="quotes.delivery">Delivery</dhv:label></td>
      <td>
        <%= quoteDeliveryList.getHtmlSelect("deliveryId", quoteBean.getDeliveryId()) %>
      </td>
    </tr>
  </dhv:include>
  <dhv:include name="quote-status" none="true">
    <tr class="containerBody">
      <td class="formLabel" valign="top"><dhv:label name="accounts.accountasset_include.Status">Status</dhv:label></td>
      <td>
        <%= quoteStatusList.getHtmlSelect("statusId", quoteBean.getStatusId()) %>
      </td>
    </tr>
  </dhv:include>
  <dhv:include name="quote-expirationDate" none="true">
    <tr class="containerBody">
      <td class="formLabel" valign="top">
        <dhv:label name="accounts.accountasset_include.ExpirationDate">Expiration Date</dhv:label>
      </td>
      <td>
        <zeroio:dateSelect form="addQuote" field="expirationDate" timestamp="<%= quoteBean.getExpirationDate() %>"  timeZone="<%= User.getTimeZone() %>" showTimeZone="false" />
        <%= showAttribute(request, "expirationDateError") %>
      </td>
    </tr>
  </dhv:include>
  <dhv:include name="quote-logo" none="true">
    <tr class="containerBody">
      <td class="formLabel" valign="top">
        <dhv:label name="quotes.logo">Logo</dhv:label>
      </td>
      <td>
        <%-- This is a new quote so use the default file item --%>
        <dhv:evaluate if="<%= quoteBean.getId() == -1 %>">
          <%= (fileItemList.getHtmlSelectDefaultNone(systemStatus, "logoFileId", quoteBean.getLogoFileId(), true)) %>
        </dhv:evaluate>
        <%-- This is an existing quote, so don't change the selected value --%>
        <dhv:evaluate if="<%= quoteBean.getId() > -1 %>">
          <%= (fileItemList.getHtmlSelectDefaultNone(systemStatus, "logoFileId", quoteBean.getLogoFileId(), false)) %>
        </dhv:evaluate>
      </td>
    </tr>
  </dhv:include>
  <dhv:include name="quote-internalNotes" none="true">
    <tr class="containerBody">
      <td class="formLabel" valign="top">
        <dhv:label name="quotes.internalNotes">Internal Notes</dhv:label>
      </td>
      <td colspan="5">
        <textarea name="notes" id="notes" rows="3" cols="55"><%= toString(quoteBean.getNotes()) %></textarea>
      </td>
    </tr>
  </dhv:include>
</table>
<zeroio:dateSelect form="addQuote" field="closed" timestamp="<%= quoteBean.getClosed() %>"  timeZone="<%= User.getTimeZone() %>" showTimeZone="false" hidden="true" />
<zeroio:dateSelect form="addQuote" field="issuedDate" timestamp="<%= quoteBean.getIssuedDate() %>"  timeZone="<%= User.getTimeZone() %>" showTimeZone="false" hidden="true" />
<input type="hidden" name="closeIt" value="<%= quoteBean.getCloseIt() %>" />
<input type="hidden" name="submitAction" value="<%= quoteBean.getSubmitAction() %>" />

