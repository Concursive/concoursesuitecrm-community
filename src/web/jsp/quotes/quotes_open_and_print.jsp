<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.products.base.*" %>
<%@ page import="org.aspcfs.modules.quotes.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="canPrint" class="java.lang.String" scope="request"/>
<body onLoad="forwardLink();">
<%@ include file="../initPage.jsp" %>
<script type="text/javascript">
  function forwardLink(){
    var link = opener.reopenAndPrint('<%= canPrint %>');
    self.close();
  }
</script>
</body>
