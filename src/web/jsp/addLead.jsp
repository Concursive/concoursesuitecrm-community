<!--jsp:useBean id="SearchResultsBean" class="com.darkhorseventures.cfsbase.SearchResultsBean" scope="request"/-->
<b>Add a New Lead</b>
<hr color="#BFBFBB" noshade>

<form name="addLead" action="/Leads.do?command=Insert" method="post">
<table cellpadding="4" cellspacing="0" border="1" width="100%">
      
<tr><td width=50% valign=center>
Name
</td>
<td valign=center>
<input type=text size=35 name="frm_name">
</td>
</tr>
      
<tr><td width=50% valign=center>
Industry
</td>
<td valign=center>
<select size='1' name="frm_industry"><option selected value='2'>Auto and Home Supply Stores</option><option value='1'>Auto Dealers</option><option value='3'>Building Materials</option><option value='4'>Hardware Stores</option><option value='5'>Hotels and Motels</option><option value='6'>Household Appliances</option><option value='7'>Household Audio and Video Equipment</option><option value='8'>Musical Instruments</option><option value='9'>Shoe Stores</option><option value='10'>Sporting Good Stores/General</option><option value='11'>Sporting Good Stores/Specialty</option><option value='12'>Tire Dealers</option><option value='13'>Video Tape Rental</option></select>
</td>
</tr>

<tr><td valign=center colspan=2>
<input type=submit value="Insert">&nbsp;
<input type=reset value="Clear">
</td></tr>
</table>
</form>
