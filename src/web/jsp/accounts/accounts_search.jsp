<!--jsp:useBean id="SearchResultsBean" class="com.darkhorseventures.cfsbase.SearchResultsBean" scope="request"/-->
<!--
<b>Add a New Account</b>
<hr color="#BFBFBB" noshade>
-->
<jsp:useBean id="IndustryList" class="com.darkhorseventures.webutils.HtmlSelect" scope="request"/>
<body onLoad="javascript:document.forms[0].searchName.focus();">
<form name="searchAccount" action="/Accounts.do?command=View" method="post">
<a href="/Accounts.do">Account Management</a> > 
Search Accounts<br>
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">

<tr bgcolor="#DEE0FA">
<td colspan=2 valign=center align=left>
<strong>Search Accounts</strong>
</td>     
</tr>

<tr><td width="125" class="formLabel">
Name
</td>
<td colspan=1 valign=center>
<input type=text size=35 name="searchName">
</td>
</tr>

<!--
<tr><td colspan=1 valign=center>
Email
</td>
<td colspan=1 valign=center>
<input type=text size=35 name="email">
</td>
</tr>

<tr><td colspan=1 valign=center>
URL
</td>
<td colspan=1 valign=center>
<input type=text size=35 name="url">
</td>
</tr>

<tr><td colspan=1 valign=center>
Phone
</td>
<td colspan=1 valign=center>
<input type=text size=3 name="phone1" maxlength=3>&nbsp;
<input type=text size=3 name="phone2" maxlength=3>&nbsp;
<input type=text size=4 name="phone3" maxlength=4>
<input type=hidden name=phone value="">
</td>
</tr>

<tr><td colspan=1 valign=center>
Fax
</td>
<td colspan=1 valign=center>
<input type=text size=3 name="fax1" maxlength=3>&nbsp;
<input type=text size=3 name="fax2" maxlength=3>&nbsp;
<input type=text size=4 name="fax3" maxlength=4>
<input type=hidden name=fax value="">
</td>
</tr>
      
<tr><td width="150" valign=center colspan=1>
Industry
</td>
<td valign=center colspan=1>

<%
	String thisElt = (String)IndustryList.getHtml();
	out.println(thisElt);
%>

<select size='1' name="industry"><option selected value='2'>Auto and Home Supply Stores</option><option value='1'>Auto Dealers</option><option value='3'>Building Materials</option><option value='4'>Hardware Stores</option><option value='5'>Hotels and Motels</option><option value='6'>Household Appliances</option><option value='7'>Household Audio and Video Equipment</option><option value='8'>Musical Instruments</option><option value='9'>Shoe Stores</option><option value='10'>Sporting Good Stores/General</option><option value='11'>Sporting Good Stores/Specialty</option><option value='12'>Tire Dealers</option><option value='13'>Video Tape Rental</option></select>
</td>
</tr>
-->
<tr><td valign=center colspan=2>
<input type=submit value="Search">
<input type=reset value="Clear">
</td></tr>

</table>
</form>
</body>
