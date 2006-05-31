<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*" %>
<jsp:useBean id="rowColumnId" class="java.lang.String" scope="request"/>
<jsp:useBean id="pageRowId" class="java.lang.String" scope="request"/>
<jsp:useBean id="previousRowColumnId" class="java.lang.String" scope="request"/>
<body onLoad="forwardLink();">
<%@ include file="../initPage.jsp" %>
<script type="text/javascript">
  function forwardLink() {
    window.location.href='RowColumns.do?command=AddIcelet&rowColumnId=<%= rowColumnId %>&previousRowColumnId=<%= previousRowColumnId %>&pageRowId=<%= pageRowId %>&popup=true&auto-populate=true';
  }
</script>
</body>
