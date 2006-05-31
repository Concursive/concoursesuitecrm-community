<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*" %>
<jsp:useBean id="id" class="java.lang.String" scope="request"/>
<body onLoad="forwardLink();">
<%@ include file="initPage.jsp" %>
<script type="text/javascript">
  function forwardLink(){
    if ('<%= !isPopup(request) %>' == 'true') {
      if ('<%= (id != null && !"".equals(id)) %>' == 'true') {
        var link = parent.reopenId('<%= id %>');
      } else {
        var link = parent.reopen();
      }
    } else {
      if ('<%= (id != null && !"".equals(id)) %>' == 'true') {
        var link = opener.reopenId('<%= id %>');
      } else {
        var link = opener.reopen();
      }
      self.close();
    }
  }
</script>
</body>
