<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="Dialog" class="com.darkhorseventures.webutils.HtmlDialog" scope="session"/>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/submit.js"></SCRIPT>
<%@ include file="initPage.jsp" %>
<script type="text/javascript">
function loadFrames(){
     if(document.getElementById){
        parent.frames["middleframe"].document.location.href = "loadmiddleframe.jsp";
        parent.frames["bottomframe"].document.location.href = "loadbottomframe.jsp";
     }
   }
</script>
<body onLoad="javascript:loadFrames();" id="body">
   <%=Dialog.getFrameHtml(1)%>
   <%if(Dialog.getSynchFrameCounter()==0){
      session.removeAttribute("Dialog");
    }%>
</body>

