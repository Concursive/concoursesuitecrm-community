<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*,org.aspcfs.utils.web.*" %>
<jsp:useBean id="OrgPhoneTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/><html>
<jsp:useBean id="OrgAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/><html>
<jsp:useBean id="OrgEmailTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/><html>
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
<% int index =  Integer.parseInt((String)request.getParameter("index")); %>
<dhv:include name="organization.phoneNumbers" none="true">
  var phone1 = parent.document.forms['addAccount'].elements['phone1type'];
    phone1.options.length = 0;
  var phone2 = parent.document.forms['addAccount'].elements['phone2type'];
    phone2.options.length = 0;
  var phone1Index = 0;
  var phone2Index = 0;
<%
  Iterator phone1Iterator = OrgPhoneTypeList.iterator();
  while (phone1Iterator.hasNext()) {
    LookupElement thisElt = (LookupElement)phone1Iterator.next();
%>
  phone1.options[phone1.length] = newOpt("<%=thisElt.getDescription()%>", "<%= thisElt.getCode() %>");
  phone2.options[phone2.length] = newOpt("<%=thisElt.getDescription()%>", "<%= thisElt.getCode() %>");
<%
  if(index == 0 && "Main".equals(thisElt.getDescription().trim())){
%>
    phone1Index = phone1.length -1;
<%
  }else if(index == 0 && "Fax".equals(thisElt.getDescription())){
%>
    phone2Index = phone2.length -1;
<%
  }else if(index == 1 && "Business".equals(thisElt.getDescription())){
%>
    phone1Index = phone1.length -1;
<%
  }else if(index == 1 && "Business Fax".equals(thisElt.getDescription())){
%>
    phone2Index = phone2.length -1;
<%
   }
  }
%>
  phone1.selectedIndex = phone1Index;
  phone2.selectedIndex = phone2Index;
</dhv:include>

<dhv:include name="organization.addresses" none="true">
  var addr1 = parent.document.forms['addAccount'].elements['address1type'];
  addr1.options.length = 0;
  var addr2 = parent.document.forms['addAccount'].elements['address2type'];
  addr2.options.length = 0;
  var addr1Index = 0;
  var addr2Index = 0;
<%
  Iterator addr1Iterator = OrgAddressTypeList.iterator();
  while (addr1Iterator.hasNext()) {
    LookupElement thisElt = (LookupElement)addr1Iterator.next();
%>
  addr1.options[addr1.length] = newOpt("<%=thisElt.getDescription()%>", "<%= thisElt.getCode() %>");
  addr2.options[addr2.length] = newOpt("<%=thisElt.getDescription()%>", "<%= thisElt.getCode() %>");
<%
  if(index == 0 && "Primary".equals(thisElt.getDescription())){
%>
    addr1Index = addr1.length -1;
<%
  }else if(index == 0 && "Auxillary".equals(thisElt.getDescription())){
%>
    addr2Index = addr2.length -1;
<%
  }else if(index == 1 && "Business".equals(thisElt.getDescription())){
%>
    addr1Index = addr1.length -1;
<%
  }else if(index == 1 && "Home".equals(thisElt.getDescription())){
%>
    addr2Index = addr2.length -1;
<%
   }
  }
%>
  addr1.selectedIndex = addr1Index;
  addr2.selectedIndex = addr2Index;
</dhv:include>

<dhv:include name="organization.emailAddresses" none="true">
  var email1 = parent.document.forms['addAccount'].elements['email1type'];
  email1.options.length = 0;
  var email2 = parent.document.forms['addAccount'].elements['email2type'];
  email2.options.length = 0;
  var email1Index = 0;
  var email2Index = 0;
<%
  Iterator email1Iterator = OrgEmailTypeList.iterator();
  while (email1Iterator.hasNext()) {
    LookupElement thisElt = (LookupElement)email1Iterator.next();
%>
  email1.options[email1.length] = newOpt("<%=thisElt.getDescription()%>", "<%= thisElt.getCode() %>");
  email2.options[email2.length] = newOpt("<%=thisElt.getDescription()%>", "<%= thisElt.getCode() %>");
<%
  if(index == 0 && "Primary".equals(thisElt.getDescription())){
%>
    email1Index = email1.length -1;
<%
  }else if(index == 0 && "Auxillary".equals(thisElt.getDescription())){
%>
  email2Index = email2.length -1;
<%
  }else if(index == 1 && "Business".equals(thisElt.getDescription())){
%>
    email1Index = email1.length -1;
<%
  }else if(index == 1 && "Personal".equals(thisElt.getDescription())){
%>
    email2Index = email2.length -1;
<%
   }
  }
%>
 email1.selectedIndex = email1Index;
 email2.selectedIndex = email2Index;
 </dhv:include>
}
</script>
</body>
</html>

