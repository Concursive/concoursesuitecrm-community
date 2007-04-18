<%--
  - Copyright(c) 2007 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  -
  - Version: $Id: range_select_end.jsp dharmas$
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Iterator"%>
<%@ page import="org.aspcfs.modules.base.CustomFieldData"%>

<jsp:useBean id="dataList" class="org.aspcfs.modules.base.CustomFieldDataList" scope="request"/>
<jsp:useBean id="rangeSelectInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="rangeStartValue" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>

<html>
  <head><title>Folder Range End Select Page</title></head>
 <script language="JavaScript">
 
 function setSelectedRecordId(id) {
	document.dataEndDisplay.rangeEndValue.value = id;
}

 function goToParent() {
 	window.close();
 	window.opener.focus(); 
 }
 
 function checkAndSetValues() {
 	 textToDisplay = "";
 	 start = document.dataEndDisplay.startValue.value;	 
	 end = document.dataEndDisplay.rangeEndValue.value;
	 if (parseInt(end) >= 0) {
		 document.dataEndDisplay.endValue.value = end;
		 if(parseInt(end) < parseInt(start)){
		 	 document.dataEndDisplay.startValue.value = "Start:Relative,"+start;
			 document.dataEndDisplay.endValue.value = "End:Relative,"+end;
			 if(end == 0){
			 	 textToDisplay = start+" Records Ago to Current";		
			 }
			 else {
			 	 textToDisplay = start+" Records Ago to "+end;		 	  
			 }
		 }
		 else {
			 document.dataEndDisplay.startValue.value = "Start:Absolute,"+start;
			 document.dataEndDisplay.endValue.value = "End:Absolute,"+end;
			 textToDisplay = "From "+start+" to "+end+" Records";
		 } 
		 textToStore = document.dataEndDisplay.startValue.value + ";" + document.dataEndDisplay.endValue.value;
		 window.opener.setParentLabel(textToDisplay,textToStore);
		 return true;
	 }
	 else {
        alert('Invalid End Range Value! Enter value >= 0');
	 	return false;
	 }
 }
 function goToMain() {
	 flag = checkAndSetValues();	 
	 if(flag) {
		 window.close();
		 window.opener.close();
     }
 }
 </script>
<body onLoad="document.dataEndDisplay.rangeEndValue.focus();">
 <form name="dataEndDisplay" action=""> 
 
    &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<dhv:label name="folder.range.end">Range End</dhv:label>&nbsp;
 	<input type="text" name="rangeEndValue" id="rangeEnd" value="0" size="8" maxlength="10">
	&nbsp;<dhv:label name="folder.range.enter">Enter 0 for Current</dhv:label>
	
	<dhv:pagedListStatus object="rangeSelectInfo"/>
	 				
 	<table width="100%" cellspacing="0" cellpadding="4" border="1" class="details"> 
 	       <br>
           
             <dhv:evaluate if='<%= dataList!=null && dataList.size() > 0 %>'>
             
             <tr>
             <th align="center" width="8">
					&nbsp;
		     </th> 
             <th>
             	<strong><dhv:label name="calendar.dateEntered">Date Entered</dhv:label></strong>
             </th>
             <th>
             	<strong><dhv:label name="folder.range.majorAxisField">Major Axis Field</dhv:label></strong>
             </th>
             </tr>
              <%
                  Iterator dataIter = dataList.iterator();
                  int count = 1; 
                  if(dataIter.hasNext()){
                   	int rowid = 0;
                    while(dataIter.hasNext()){		
                      	rowid = (rowid != 1?1:2);						                            
                       	CustomFieldData record = (CustomFieldData)dataIter.next();  
                        %>
            <tr class="row<%= rowid %>">
				<td><a href="javascript:setSelectedRecordId(<%= record.getRecordId() %>);">Select</a></td>
                <td> <%= record.getEntered() %> </td>  
                <td> <%= record.getEnteredValue() %> </td>    
            </tr>            
            <% count++; 
                } } %>
                
            </dhv:evaluate>    
    </table>
    <br>
    <input type="hidden" name="startValue" value="<%= request.getAttribute("rangeStartValue") %>"> 		   
	<input type="hidden" name="endValue" value="">
    <div align="right">    
    <br><br>
    <input type="button" name="chooseFrom" id="chooseFrom" value="<dhv:label name="folder.range.choosefrom">Choose From</dhv:label>" onClick="goToParent();" />
    <input type="button" name="done" id="done" align="center" value="Done" onClick="goToMain();" />
	</div>
 </form>
 </body>
</html>