<jsp:useBean id="SelectedList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<script language="JavaScript" type="text/javascript" src="/javascript/editListForm.js"></script>
<form name="modifyList" method=POST action="/Admin.do?command=UpdateList">
<a href="/Admin.do">Setup</a> >
<a href="/Admin.do?command=Config">System Configuration</a> >
<a href="/Admin.do?command=EditLists">Lookup Lists</a> > 
Edit List<br>
&nbsp;<br>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr bgcolor="#DEE0FA">
    <td colspan=3>
      <strong>Modify List - <%=request.getAttribute("ListLabel")%></strong>
    </td>
  </tr>
  <tr>
	<td width=50%>
	<table width=100% cellspacing=0 cellpadding=2 border=0>
	<tr><td valign=center>
	New Option
	</td></tr>
	
	<tr><td valign=center>
	<input type="text" name="newValue" value="" size=25  maxlength=125>
	</td></tr>
	
	<tr><td valign=center>
	<input type="button" value="Add >" onclick="javascript:addValues()">
	</td></tr>
	
	</table>
	
	</td>
    
    <td width=25>
    
    <table width=100% cellspacing=0 cellpadding=2 border=0>
    <tr><td valign=center>
    <input type=button value="Up" onclick="javascript:moveOptionUp(document.modifyList.selectedList)">
    </td></tr>
    
    <tr><td valign=center>
    <input type=button value="Down" onclick="javascript:moveOptionDown(document.modifyList.selectedList)">
    </td></tr>
    
    <tr><td valign=center>
    <input type="button" value="Remove" onclick="javascript:removeValues()">
    </td></tr>
    
    <tr><td valign=center>
    <input type="button" value="Sort" onclick="javascript:sortSelect(document.modifyList.selectedList)">
    </td></tr>
    
    </table>
    
    </td>

    <td width=50%><%=SelectedList.getHtmlSelect("selectedList",0)%></td>
    
  </tr>
  
  <tr>
  <td colspan=3 valign=center>
  <input type=hidden name="selectNames" value="">
  <input type=hidden name="listid" value="<%=request.getParameter("listId")%>">
  <input type=button value="Save Changes" onclick="javascript:selectAllOptions(document.modifyList.selectedList)">
  <input type="submit" value="Cancel" onClick="javascript:this.form.action='/Admin.do?command=EditLists'">
  </td>
  </tr>
    
</table>
</form>
