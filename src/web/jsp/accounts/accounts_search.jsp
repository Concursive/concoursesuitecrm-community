<jsp:useBean id="IndustryList" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<body onLoad="javascript:document.forms[0].searchName.focus();">
<form name="searchAccount" action="Accounts.do?command=Search" method="post">
<a href="Accounts.do">Account Management</a> > 
Search Accounts<br>
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Search Accounts</strong>
    </th>
  </tr>
  <tr>
    <td class="formLabel">
      Name
    </td>
    <td>
      <input type="text" size="35" name="searchName">
    </td>
  </tr>
  <tr>
    <td class="formLabel">
      Account Number
    </td>
    <td>
      <input type="text" size="35" name="searchAccountNumber">
    </td>
  </tr>
</table>
&nbsp;<br>
<input type="submit" value="Search">
<input type="reset" value="Reset">
</form>
</body>
