<jsp:useBean id="OrgList" class="org.aspcfs.modules.accounts.base.OrganizationList" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/popCalendar.js"></script>
<script language="JavaScript" type="text/javascript" src="/javascript/popURL.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/submit.js"></script>
<body onLoad="javascript:document.forms[0].searchDescription.focus();">
<form name="searchLeads" action="Leads.do?command=ViewOpp" method="post">
<a href="Leads.do">Pipeline Management</a> > 
Search Opportunities<br>
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">

  <tr bgcolor="#DEE0FA">
    <td colspan=2 valign=center align=left>
    <strong>Search Pipeline</strong>
    </td>     
  </tr>
  
  <tr><td width="125" class="formLabel">
Description
</td>
<td colspan=1 valign=center>
<input type=text size=35 name="searchDescription">
</td>
</tr>

  <tr>
    <td nowrap class="formLabel">
      Organization
    </td>
	<td bgColor="white">
	<%= OrgList.getHtmlSelectDefaultNone("searchcodeOrgId") %>
	</td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <a href="javascript:popURLReturn('MyCFSInbox.do?command=ContactList&popup=true&flushtemplist=true&parentFieldType=contactsingle&parentFormName=searchLeads', 'Leads.do?command=SearchOpp', 'Search_Opp','700','450','yes','no');">Contact</a>
    </td>
    <td width="100%">
      <table>
        <tr>
          <td>
            <div id="changecontact">None Selected</div>
          </td>
          <td>
            <input type=hidden name="contact" value="-1">
          </td>
        </tr>
      </table>
    </td>
  </tr>
  
    <tr>
    <td nowrap class="formLabel">
      Current Stage
    </td>
	<td bgColor="white">
	<%=StageList.getHtmlSelectDefaultNone("searchcodeStage")%>
	</td>
    </tr>
    
      <tr>
      <td width="125" valign="top" class="formLabel">
Est. Close Date between
</td>
<td colspan=1 valign=center>
<input type=text size=10 name="searchdateCloseDateStart" value="">
<a href="javascript:popCalendar('searchLeads', 'searchdateCloseDateStart');">Date</a> (mm/dd/yyyy)
&nbsp;and<br>
<input type=text size=10 name="searchdateCloseDateEnd" value="">
<a href="javascript:popCalendar('searchLeads', 'searchdateCloseDateEnd');">Date</a> (mm/dd/yyyy)
</td>
</tr>
    
</table>
&nbsp;<br>
<input type=submit value="Search">
<input type=reset value="Clear">
</form>
</body>
