<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="org.aspcfs.modules.admin.base.PermissionCategory, java.util.*" %>
<jsp:useBean id="rangeSelect" class="java.lang.String" scope="request"/>
<jsp:useBean id="dateStart" class="java.sql.Date" scope="request"/>
<jsp:useBean id="dateEnd" class="java.sql.Date" scope="request"/>
<jsp:useBean id="usageList" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="usageList2" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="applicationVersion" class="java.lang.String" scope="request"/>
<jsp:useBean id="databaseVersion" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
  function page_init() {
    if ('custom' == '<%= rangeSelect %>') {
      showSpan('customFields');
      showSpan('customFields2');
      showSpan('customFields3');
    }
  }
  function checkSubmit() {
    var sel = document.forms['usage'].elements['rangeSelect'];
    var value = sel.options[sel.selectedIndex].value;
    if (value == 'custom') {
      showSpan('customFields');
      showSpan('customFields2');
      showSpan('customFields3');
      document.usage.dateStart.focus();
      return false;
    } else {
      hideSpan('customFields');
      hideSpan('customFields2');
      hideSpan('customFields3');
      document.usage.dateStart.value = '';
      document.usage.dateEnd.value = '';
      document.forms['usage'].submit();
    }
  }
  function checkForm(form) {
    formTest = true;
    message = "";
    if ((!form.dateStart.value == "") && (!checkDate(form.dateStart.value))) { 
      message += "- Check that Start Date is entered correctly\r\n";
      formTest = false;
    }
    if ((!form.dateEnd.value == "") && (!checkDate(form.dateEnd.value))) { 
      message += "- Check that End Date is entered correctly\r\n";
      formTest = false;
    }
    if (formTest == false) {
      alert("Form could not be saved, please check the following:\r\n\r\n" + message);
      return false;
    } else {
      return true;
    }
  }
</SCRIPT>
<body onLoad="page_init();">
<a href="Admin.do">Setup</a> > 
Usage<br>
<hr color="#BFBFBB" noshade>
Current Usage and Billing Usage Information<br>
&nbsp;<br>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th>
      <strong>Current system resources:</strong>
    </th>
  </tr>
<%
  Iterator items = usageList.iterator();
  while (items.hasNext()) {
  String thisItem = (String)items.next();
%>
  <tr class="containerBody">
    <td>
      <%= thisItem %>
    </td>
  </tr>
<%
  }
%>
</table>
&nbsp;<br>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <form name="usage" action="Admin.do?command=Usage" onSubmit="return checkForm(this);" method="post">
    <tr>
      <td nowrap valign="top">
        Date Range:
        <select name="rangeSelect" onChange="javascript:checkSubmit();">
          <option value="today">Today</option>
          <option value="custom"<%= ("custom".equals(rangeSelect)?" selected":"") %>>Custom Date Range</option>
        </select>
      </td>
      <td nowrap align="right">
        <span name="customFields3" id="customFields3" style="display:none">
          Start:<br>
          End:
        </span>
      </td>
      <td nowrap align="left">
        <span name="customFields" id="customFields" style="display:none">
          <input type="text" size="10" name="dateStart" value="<%= toDateString(dateStart) %>">
          <a href="javascript:popCalendar('usage', 'dateStart');">Date</a> (mm/dd/yyyy)<br>
          <input type="text" size="10" name="dateEnd" value="<%= toDateString(dateEnd) %>">
          <a href="javascript:popCalendar('usage', 'dateEnd');">Date</a> (mm/dd/yyyy)
        </span>
      </td>
      <td width="100%" align="left" valign="top" nowrap>
        <span name="customFields2" id="customFields2" style="display:none">
          <input type="submit" value="Update" name="Update">
        </span>
      </td>
    </tr>
  </form>
</table>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th>
      <strong>Usage for <%= toDateString(dateStart) %> - <%= toDateString(dateEnd) %></strong>
    </th>
  </tr>
<%
  Iterator items2 = usageList2.iterator();
  while (items2.hasNext()) {
  String thisItem2 = (String)items2.next();
%>
  <tr class="containerBody">
    <td>
      <%= thisItem2 %>
    </td>
  </tr>
<%
  }
%>
</table>
<br>
Application Version: <%= toHtml(applicationVersion) %><br>
Database Version: (<%= toHtml(databaseVersion) %>)
</body>
