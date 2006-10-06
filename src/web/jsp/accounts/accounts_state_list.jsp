<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,com.zeroio.iteam.base.*,org.aspcfs.utils.web.*" %>
<jsp:useBean id="form" class="java.lang.String" scope="request"/>
<jsp:useBean id="selected" class="java.lang.String" scope="request"/>
<jsp:useBean id="obj" class="java.lang.String" scope="request"/>
<jsp:useBean id="stateObj" class="java.lang.String" scope="request"/>
<jsp:useBean id="stateSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<body onLoad="setStates();">
<%@ include file="../initPage.jsp" %>
<script type="text/javascript">
  function newOpt(param, value) {
    var newOpt = parent.document.createElement("OPTION");
    newOpt.text=param;
    newOpt.value=value;
    return newOpt;
  }

  function setStates() {
<%  
    if (stateSelect.size() == 0) { %>
      parent.continueUpdateState('<%= obj %>', 'true');
<%  } else { %>
      parent.continueUpdateState('<%= obj %>', 'false');
      var stateSelect = parent.document.getElementById('<%= stateObj %>');
      stateSelect.options.length = 0;
      var selectedOption = -1;
      stateSelect.options[stateSelect.options.length] = newOpt(label('option.none','-- None --'),'-1');
<%    Iterator iter = (Iterator) stateSelect.iterator();
      boolean foundSelected = false;
      while (iter.hasNext()) {
        HtmlOption option = (HtmlOption) iter.next();
        if (selected.equals(option.getValue())) {
          foundSelected = true;
%>
        selectedOption = stateSelect.options.length;
<%      } %>
        stateSelect.options[stateSelect.options.length] = newOpt('<%= option.getText() %>', '<%= option.getValue() %>');
<%    }
      if (!foundSelected && selected != null && !"".equals(selected) && !"null".equals(selected) && !"-1".equals(selected)) { %>
      selectedOption = stateSelect.options.length;
      stateSelect.options[stateSelect.options.length] = newOpt('<%= selected %> *', '<%= selected %>');
<%    } %>
      if (selectedOption > -1) {
        stateSelect.options.selectedIndex = selectedOption;
      }
<%  } %>
  }
</script>
</body>
