<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.utils.web.HtmlSelect" %>
<jsp:useBean id="SearchOppListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TypeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/popContacts.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></script>
<script language="JavaScript">
  function clearForm() {
    document.forms['searchLeads'].searchDescription.value="";
    document.forms['searchLeads'].searchcodeOrgId.value="";
    changeDivContent('changeaccount', ' All ');
    document.forms['searchLeads'].searchcodeContactId.value="";
    changeDivContent('changecontact', ' Any ');
    document.forms['searchLeads'].searchcodeStage.options.selectedIndex = 0;
    document.forms['searchLeads'].searchdateCloseDateStart.value="";
    document.forms['searchLeads'].searchdateCloseDateEnd.value="";
    document.forms['searchLeads'].listView.options.selectedIndex = 0;
    document.forms['searchLeads'].listFilter1.options.selectedIndex = 0;
    document.forms['searchLeads'].listFilter2.options.selectedIndex = 0;
    document.forms['searchLeads'].searchDescription.focus();
    updateOwnedBy();
  }
  
  function updateOwnedBy(){
    if(document.forms[0].listView.value == "all" || document.forms[0].listView.value == "closed"){
      showSpan('ownedby');
    }else{
      document.forms['searchLeads'].listFilter2.options.selectedIndex = 0;
      hideSpan('ownedby');
    }
  }
  
  function checkOwnedBy(){
    <%
      if("all".equals(SearchOppListInfo.getListView()) || "closed".equals(SearchOppListInfo.getListView())){
    %>
      showSpan('ownedby');
    <% }else{ %>
      hideSpan('ownedby');
    <% } %>
  }
</script>
<body onLoad="javascript:document.forms[0].searchDescription.focus();checkOwnedBy();">
<form name="searchLeads" action="Leads.do?command=Search" method="post">
<%-- Trails --%>
<table class="trails">
<tr>
<td>
<a href="Leads.do">Pipeline</a> > 
Search Opportunities
</td>
</tr>
</table>
<%-- End Trails --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Search Pipeline</strong>
    </th>
  </tr>
  <tr>
    <td class="formLabel" nowrap>
      Opportunity Description
    </td>
    <td>
      <input type="text" size="35" name="searchDescription" value="<%= SearchOppListInfo.getSearchOptionValue("searchDescription") %>">
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      Account(s)
    </td>
    <td>
      <table cellspacing="0" cellpadding="0" border="0" class="empty">
        <tr>
          <td>
            <div id="changeaccount">
              <%if("".equals(SearchOppListInfo.getSearchOptionValue("searchcodeOrgId")) || "-1".equals(SearchOppListInfo.getSearchOptionValue("searchcodeOrgId"))){ %>
                &nbsp;All&nbsp;
              <% }else{ %>
                <%= toHtmlValue(OrgDetails.getName()) %>
              <% } %>
            </div>
          </td>
          <td>
            <input type="hidden" name="searchcodeOrgId" id="searchcodeOrgId" value="<%= SearchOppListInfo.getSearchOptionValue("searchcodeOrgId") %>">
            &nbsp;[<a href="javascript:popAccountsListSingle('searchcodeOrgId','changeaccount', 'filters=all|my|disabled');">Select</a>]
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Contact
    </td>
    <td>
      <table class="empty">
        <tr>
          <td>
            <div id="changecontact">
            <%if("".equals(SearchOppListInfo.getSearchOptionValue("searchcodeContactId")) || "-1".equals(SearchOppListInfo.getSearchOptionValue("searchcodeContactId"))){ %>
              &nbsp;Any&nbsp;
            <% }else{ %>
              <%= toHtmlValue(ContactDetails.getValidName()) %>
            <% } %>
            </div>
          </td>
          <td>
            <input type="hidden" id="contactId" name="searchcodeContactId" value="<%= SearchOppListInfo.getSearchOptionValue("searchcodeContactId") %>">
            &nbsp;[<a href="javascript:popContactsListSingle('contactId','changecontact', 'reset=true');">Select</a>]
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      Current Stage
    </td>
    <td>
      <%= StageList.getHtmlSelect("searchcodeStage", SearchOppListInfo.getSearchOptionValue("searchcodeStage")) %>
    </td>
  </tr>
  <tr>
    <td valign="top" class="formLabel">
      Est. Close Date between
    </td>
    <td>
      <input type="text" size="10" name="searchdateCloseDateStart" value="<%= SearchOppListInfo.getSearchOptionValue("searchdateCloseDateStart") %>">
      <a href="javascript:popCalendar('searchLeads', 'searchdateCloseDateStart');"><img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle" height="16" width="16"/></a> (mm/dd/yyyy)
      &nbsp;and<br>
      <input type="text" size="10" name="searchdateCloseDateEnd" value="<%= SearchOppListInfo.getSearchOptionValue("searchdateCloseDateEnd") %>">
      <a href="javascript:popCalendar('searchLeads', 'searchdateCloseDateEnd');"><img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle" height="16" width="16"/></a> (mm/dd/yyyy)
    </td>
  </tr>
  <tr>
    <td class="formLabel">
      Opportunity Type
    </td>
    <td>
      <%= TypeSelect.getHtmlSelect("listFilter1", SearchOppListInfo.getFilterKey("listFilter1")) %>
    </td>
  </tr>
  <tr>
    <td class="formLabel">
      Source
    </td>
    <td align="left" valign="bottom">
      <select size="1" name="listView" onChange="javascript:updateOwnedBy();">
        <option <%= SearchOppListInfo.getOptionValue("my") %>>My Open Opportunities</option>
        <option <%= SearchOppListInfo.getOptionValue("all") %>>All Open Opportunities</option>
        <option <%= SearchOppListInfo.getOptionValue("closed") %>>All Closed Opportunities</option>
      </select>
    </td>
  </tr>
  <tr id="ownedby" style="display:none">
    <td class="formLabel">
      Owned By
    </td>
    <td>
      <% 
         HtmlSelect userSelect = UserList.getHtmlSelectObj("listFilter2", SearchOppListInfo.getFilterKey("listFilter2"));
         userSelect.addItem(-1, "All Users", 0);
      %>
      <%= userSelect.getHtml("listFilter2", SearchOppListInfo.getFilterKey("listFilter2")) %>
    </td>
  </tr>
</table>
&nbsp;<br>
<input type="submit" value="Search">
<input type="button" value="Clear" onClick="javascript:clearForm();">
<input type="hidden" name="source" value="searchForm">
</form>
</body>
