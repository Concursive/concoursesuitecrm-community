<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.utils.web.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="ContactTypeList" class="org.aspcfs.modules.contacts.base.ContactTypeList" scope="request"/>
<jsp:useBean id="DepartmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkPhone.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
<script language="JavaScript">
  function doCheck(form) {
    <% if("adduser".equals(request.getParameter("source"))){ %>
    if(document.forms['addContact'].contactcategory[0].checked && document.forms['addContact'].contactcategory[0].value == '3'){
      document.forms['addContact'].action = 'CompanyDirectory.do?command=Save&auto-populate=true&popup=true';
    }
    <% } %>
    if (form.dosubmit.value == "false") {
      return true;
    } else {
      return(checkForm(form));
    }
  }
  function checkForm(form) {
    formTest = true;
    message = "";
    if ((!checkPhone(form.phone1number.value)) || (!checkPhone(form.phone2number.value)) || (!checkPhone(form.phone3number.value)) ) { 
      message += "- At least one entered phone number is invalid.  Make sure there are no invalid characters and that you have entered the area code\r\n";
      formTest = false;
    }
    if (formTest == false) {
      alert("Form could not be saved, please check the following:\r\n\r\n" + message);
      return false;
    } else {
      var test = document.addContact.selectedList;
      if (test != null) {
        return selectAllOptions(document.addContact.selectedList);
      }
    }
  }
  function update(countryObj, stateObj) {
  var country = document.forms['addContact'].elements[countryObj].value;
   if(country == "UNITED STATES" || country == "CANADA"){
      hideSpan('state2' + stateObj);
      showSpan('state1' + stateObj);
   }else{
      hideSpan('state1' + stateObj);
      showSpan('state2' + stateObj);
    }
  }
  
  function setCategoryPopContactType(selectedId, contactId){
    var category = 'general';
    if(document.addContact.contactcategory[1].checked){
      category = 'accounts';
    }
    popContactTypeSelectMultiple(selectedId, category, contactId); 
  }
</script>
<body onLoad="javascript:document.forms[0].nameFirst.focus();">
<%
  boolean popUp = false;
  if(request.getParameter("popup")!=null){
    popUp = true;
  }
%>
  <form name="addContact" action="ExternalContacts.do?command=Save&auto-populate=true<%= (request.getParameter("popup") != null?"&popup=true":"") %>" onSubmit="return doCheck(this);" method="post">
  <dhv:evaluate exp="<%= !popUp %>">
  <a href="ExternalContacts.do">General Contacts</a> > 
    Add Contact<br>
    <hr color="#BFBFBB" noshade>
  </dhv:evaluate>
  <input type="submit" value="Save" onClick="this.form.dosubmit.value='true';">
  <dhv:evaluate exp="<%= !popUp %>">
  <input type="submit" value="Save & New" onClick="this.form.saveAndNew.value='true';this.form.dosubmit.value='true';">
  </dhv:evaluate>
  <input type="submit" value="Cancel" onClick="<%= popUp ? "javascript:window.close();" : "javascript:this.form.action='ExternalContacts.do?command=ListContacts';this.form.dosubmit.value='false';" %>">
<input type=reset value="Reset">
<br>
<%= showError(request, "actionError") %>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="2">
      <strong>Add a New Contact</strong>
    </td>     
  </tr>
    <tr class="containerBody">
    <td class="formLabel" nowrap>
      Contact Category
    </td>
    <td>
      <% if("adduser".equals(request.getParameter("source"))){ %>
      	<input type="radio" name="contactcategory" value="3" onclick="javascript:document.forms[0].orgId.value = '-1';" <%= ContactDetails.getOrgId() == -1 ? " checked":""%>>Employee<br>
	<input type="hidden" name="source" value="<%= request.getParameter("source") %>">
      <% }else{ %>
        <input type="radio" name="contactcategory" value="1" onclick="javascript:document.forms[0].orgId.value = '-1';" <%= ContactDetails.getOrgId() == -1 ? " checked":""%>>General Contact<br>
      <% } %>
      <table cellspacing="0" cellpadding="0" border="0">
          <tr>
            <td>
              <input type="radio" name="contactcategory" value="2" <%= ContactDetails.getOrgId() > -1 ? " checked":""%>>
            </td>
            <td>
              Permanently associate with Account: &nbsp;
            </td>
            <td>
              <div id="changeaccount"><%= ContactDetails.getOrgId() > -1 ? ContactDetails.getCompany() : "None Selected"%></div>
            </td>
            <td>
              <input type="hidden" name="orgId" id="orgId" value="<%= ContactDetails.getOrgId() %>">
              &nbsp;[<a href="javascript:document.forms['addContact'].contactcategory[1].checked='t';popAccountsListSingle('orgId','changeaccount');" onMouseOver="window.status='Select an Account';return true;" onMouseOut="window.status='';return true;">Select</a>]&nbsp;
            </td>
          </tr>
       </table>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Contact Type(s)
    </td>
    <td>
      <table border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>
            <select multiple name="selectedList" id="selectedList" size="5">
               <dhv:lookupHtml listName="TypeList" lookupName="ContactTypeList"/>
            </select>
            <input type="hidden" name="previousSelection" value="">
            <input type="hidden" name="category" value="<%= request.getParameter("category") %>">
          </td>
          <td valign="top">
            <%-- Check for cloned contact in case of General Contacts --%>
            <% if(request.getParameter("id") == null) {%>
              [<a href="javascript:setCategoryPopContactType('selectedList', <%= ContactDetails.getId() %>);">Select</a>]
            <%}else{%>
                [<a href="javascript:setCategoryPopContactType('selectedList', <%= request.getParameter("id") %>);">Select</a>]
            <% } %>
              <%= showAttribute(request, "personalContactError") %>
          </td>
        </tr>
      </table>
     </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      First Name
    </td>
    <td>
      <input type="text" size="35" name="nameFirst" value="<%= toHtmlValue(ContactDetails.getNameFirst()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
    Middle Name
    </td>
    <td>
      <input type="text" size="35" name="nameMiddle" value="<%= toHtmlValue(ContactDetails.getNameMiddle()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Last Name
    </td>
    <td>
      <input type="text" size="35" name="nameLast" value="<%= toHtmlValue(ContactDetails.getNameLast()) %>">
      <font color="red">*</font> <%= showAttribute(request, "nameLastError") %>
    </td>
  </tr>
  <%-- Check if a user is being added --%>
  <% if("adduser".equals(request.getParameter("source"))){ %>
  <tr>
      <td class="formLabel" nowrap>Department</td>
      <td>
        <%= DepartmentList.getHtmlSelect("department", ContactDetails.getDepartment()) %>
      </td>
   </tr>
  <% } %>
    <tr class="containerBody">
      <td class="formLabel" nowrap>
        Company
      </td>
      <td>
        <input type="text" size="35" name="company" value="<%= toHtmlValue(ContactDetails.getCompany()) %>">
        <font color="red">-</font> <%= showAttribute(request, "lastcompanyError") %>
      </td>
    </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Title
    </td>
    <td>
      <input type="text" size="35" name="title" value="<%= toHtmlValue(ContactDetails.getTitle()) %>">
    </td>
  </tr>
</table>
&nbsp;<br>  

<%--  include basic contact form --%>
<%@ include file="../contacts/contact_form.jsp" %>

<br>
  <input type="submit" value="Save" onClick="this.form.dosubmit.value='true';">
  <dhv:evaluate exp="<%= !popUp %>">
  <input type="submit" value="Save & New" onClick="this.form.saveAndNew.value='true';this.form.dosubmit.value='true';">
  <input type="hidden" name="saveAndNew" value="">
  <input type="hidden" name="dosubmit" value="true">
  </dhv:evaluate>
  <input type="submit" value="Cancel" onClick="<%= popUp ? "javascript:window.close();" : "javascript:this.form.action='ExternalContacts.do?command=ListContacts';this.form.dosubmit.value='false';" %>">
  <input type="hidden" name="dosubmit" value="true">
  <input type="reset" value="Reset">
<input type="hidden" name="source" value="<%= toHtmlValue(request.getParameter("source")) %>">
</form>
</body>
