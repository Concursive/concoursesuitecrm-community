<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*,com.darkhorseventures.webutils.*" %>
<jsp:useBean id="OrgPhoneTypeList" class="com.darkhorseventures.webutils.LookupList" scope="request"/><html>
<jsp:useBean id="OrgAddressTypeList" class="com.darkhorseventures.webutils.LookupList" scope="request"/><html>
<jsp:useBean id="OrgEmailTypeList" class="com.darkhorseventures.webutils.LookupList" scope="request"/><html>
<head>
</head>
<body onload="page_init();">
<script language="JavaScript">

function newOpt(param, value) {
  var newOpt = parent.document.createElement("OPTION");
	newOpt.text=param;
	newOpt.value=value;
  return newOpt;
}

function page_init() {

  var phone1 = parent.document.forms['addAccount'].elements['phone1type'];
  phone1.options.length = 0;
  var phone2 = parent.document.forms['addAccount'].elements['phone2type'];
  phone2.options.length = 0;
<%
  Iterator phone1Iterator = OrgPhoneTypeList.iterator();
  while (phone1Iterator.hasNext()) {
    LookupElement thisElt = (LookupElement)phone1Iterator.next();
%>
  phone1.options[phone1.length] = newOpt("<%=thisElt.getDescription()%>", "<%= thisElt.getCode() %>");
  phone2.options[phone2.length] = newOpt("<%=thisElt.getDescription()%>", "<%= thisElt.getCode() %>");
<%
  }
%>

  var addr1 = parent.document.forms['addAccount'].elements['address1type'];
  addr1.options.length = 0;
  var addr2 = parent.document.forms['addAccount'].elements['address2type'];
  addr2.options.length = 0;
<%
  Iterator addr1Iterator = OrgAddressTypeList.iterator();
  while (addr1Iterator.hasNext()) {
    LookupElement thisElt = (LookupElement)addr1Iterator.next();
%>
  addr1.options[addr1.length] = newOpt("<%=thisElt.getDescription()%>", "<%= thisElt.getCode() %>");
  addr2.options[addr2.length] = newOpt("<%=thisElt.getDescription()%>", "<%= thisElt.getCode() %>");
<%
  }
%>

  var email1 = parent.document.forms['addAccount'].elements['email1type'];
  email1.options.length = 0;
  var email2 = parent.document.forms['addAccount'].elements['email2type'];
  email2.options.length = 0;
<%
  Iterator email1Iterator = OrgEmailTypeList.iterator();
  while (email1Iterator.hasNext()) {
    LookupElement thisElt = (LookupElement)email1Iterator.next();
%>
  email1.options[email1.length] = newOpt("<%=thisElt.getDescription()%>", "<%= thisElt.getCode() %>");
  email2.options[email2.length] = newOpt("<%=thisElt.getDescription()%>", "<%= thisElt.getCode() %>");
<%
  }
%>

}
</script>
</body>
</html>

