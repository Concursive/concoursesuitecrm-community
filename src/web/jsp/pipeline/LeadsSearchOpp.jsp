<body onLoad="javascript:document.forms[0].description.focus();">
<form name="searchLeads" action="/Leads.do?command=ViewOpp" method="post">
<br>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">

  <tr bgcolor="#DEE0FA">
    <td colspan=2 valign=center align=left>
    <strong>Search Pipeline</strong>
    </td>     
  </tr>
  
  <tr>
    <td class="pagelist">
    Opportunity<br> Description
    </td>
    <td valign=center width="100%">
    <input type=text size=35 name="description">
    </td>
  </tr>

</table>
&nbsp;<br>
<input type=submit value="Search">
<input type=reset value="Clear">
</form>
</body>
