<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*" %>
<jsp:useBean id="tabId" class="java.lang.String" scope="request"/>
<jsp:useBean id="pageId" class="java.lang.String" scope="request"/>
<body onLoad="forwardLink();">
<%@ include file="../initPage.jsp" %>
<script type="text/javascript">
  function forwardLink(){
    var link = parent.reopen();
    //parent.reopenWebsiteDetails('<%= tabId %>','<%= pageId %>');
    self.close();
  }
</script>
</body>
