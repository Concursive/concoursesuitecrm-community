<%@ page import="java.util.*" %>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<html>
<head>
</head>
<body onload='page_init();'>
<script language='Javascript'>
function page_init() {
  var list = parent.document.forms['ForwardForm'].elements['selTotalList'];
  var hiddendept = parent.document.forms['ForwardForm'].elements['hiddendept'];
  var str = "";
  
  list.options.length = 0;

<%
  Iterator i = UserList.iterator();
  
  while (i.hasNext()) {
    User thisUser = (User)i.next();
%>
  if ( !(inArray(parent.document.forms['ForwardForm'].elements['selectedList'], <%= thisUser.getId() %>)) ) {
  	list.options[list.length] = new Option("<%= thisUser.getContact().getNameFull() %>", "<%= thisUser.getId() %>");
  }
  
  str = str + "<%= thisUser.getId() %>" + "|";
<%
  }
%>
  //alert(str);
  hiddendept.value = str;
  //alert(hiddendept.value);
}

function inArray(a, s) {
	var i = 0;
	
	for(i=0; i < a.length; i++) {
		//alert(s + ", " + a.options[i].value);
		if (a.options[i].value == s) {
			return true;
		}
	}
	
	return false;
}

</script>
</body>
</html>

