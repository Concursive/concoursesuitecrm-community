<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="UserInfo" class="com.darkhorseventures.cfsbase.User" scope="request"/>
<jsp:useBean id="ShortChildList" class="com.darkhorseventures.cfsbase.UserList" scope="request"/>
<jsp:useBean id="FullChildList" class="com.darkhorseventures.cfsbase.UserList" scope="request"/>
<jsp:useBean id="FullOppList" class="com.darkhorseventures.cfsbase.OpportunityList" scope="request"/>
<jsp:useBean id="OppList" class="com.darkhorseventures.cfsbase.OpportunityList" scope="request"/>
<jsp:useBean id="GraphTypeList" class="com.darkhorseventures.webutils.HtmlSelect" scope="request"/>
<%@ include file="initPage.jsp" %>

<form name=Dashboard action="/Leads.do?command=Dashboard" method=POST>
<a href="/Leads.do">Pipeline Management</a> > 
Dashboard<br>
<hr color="#BFBFBB" noshade>
<table width=100% border=0 cellspacing=0 cellpadding=3>
  <tr>
    <!-- Left Column -->
    <td width="275" valign=top>
      <!-- Graphic -->
      <table width="275" cellpadding=3 cellspacing=0 border=1 bordercolorlight="#000000" bordercolor="#FFFFFF">
        <tr bgcolor="#DEE0FA">
          <td valign=center colspan=1 align=center>
          <% if (((String)request.getSession().getAttribute("leadsoverride")) == null) {%>
		My Dashboard
		<%} else {%>
		Dashboard: <%=toHtml((String)request.getSession().getAttribute("leadsothername"))%>
		<%}%>
          </td>
        </tr>
        <tr>
          <td>
            <img border="0" width="275" height="200" src="/graphs/<%=request.getAttribute("GraphFileName")%>">
          </td>
        </tr>
        <tr>
          <td align="center">
	  
	  <% if (request.getParameter("whichGraph") != null) { %>
		  <% GraphTypeList.setDefaultKey(request.getParameter("whichGraph")); %>
		  <%= GraphTypeList.getHtml() %>&nbsp;
		  
	  <%} else if ((String)request.getSession().getAttribute("whichGraph") != null) {%>
	  	<% GraphTypeList.setDefaultKey((String)request.getSession().getAttribute("whichGraph")); %>
		<%= GraphTypeList.getHtml() %>&nbsp;
	  <%} else {%>
          	<%=GraphTypeList.getHtml()%>&nbsp;
	  <%}%>
          
	  </td>
        </tr>
      </table>
      
      <br>
	<% if (!(((String)request.getSession().getAttribute("leadsoverride")) == null)) {%>
	
	<div margintop=5 align=center width=285>
	<input type=hidden name="oid" value="<%=((String)request.getSession().getAttribute("leadsoverride"))%>">
	<a href="Leads.do?command=Dashboard&oid=<%=((String)request.getSession().getAttribute("leadspreviousId"))%>">Up One Level</a> |
	<a href="Leads.do?command=Dashboard&reset=1">Back to My Dashboard</a>
	</div>
	<%}%>
      
      <table width=285 cellpadding=3 cellspacing=0 border=1 bordercolorlight="#000000" bordercolor="#FFFFFF">
        <tr bgcolor="#DEE0FA">
          <td valign=center nowrap>
           Reporting Staff
          </td>
          
          <td width=125 valign=center>
           Title
          </td>
        </tr>
<%
    Iterator x = ShortChildList.iterator();
      if ( x.hasNext() ) {
        int rowid = 0;
        while (x.hasNext()) {
          if (rowid != 1) {
            rowid = 1;
          } else {
            rowid = 2;
          }
          User thisRec = (User)x.next();
%>
        <tr>
          <td class="row<%= rowid %>" valign=center nowrap>
            <a href="/Leads.do?command=Dashboard&oid=<%=thisRec.getId()%>"><%= toHtml(thisRec.getContact().getNameLastFirst()) %></a>
            <dhv:evaluate exp="<%=!(thisRec.getEnabled())%>"><font color="red">*</font></dhv:evaluate>
          </td>
          <td width=125 class="row<%= rowid %>" valign=center>
            <%= toHtml(thisRec.getContact().getTitle()) %>
          </td>
        </tr>
    
<%      }
      } else {
%>
        <tr>
          <td valign=center colspan=3>No Reporting staff.</td>
        </tr>
      <%}%>
      </table>
    </td>
    <!-- Right Column -->
    <td valign=top width="100%">
      <table width=100% cellpadding=3 cellspacing=0 border=1 bordercolorlight="#000000" bordercolor="#FFFFFF">
        <!--tr bgcolor="#DEE0FA">
          <td valign=center colspan="6">
            <strong>
<% if (request.getAttribute("override") == null || request.getAttribute("override").equals("null")) {%>
	  	My
<%}%>
	  Opportunities</strong>
          </td>
        </tr-->
        <tr bgcolor="#DEE0FA">
          <!--td>Type</td-->
          <td>Opportunity</td>
          <!--td>Organization</td-->
          <td align="left">Amnt</td>
          <!--td align="right" nowrap width=25>%P</td>
          <td align="right" width=90>Revenue Start</td>
          <td valign="right" width=25>Term</td-->
        </tr>
<%
	Iterator n = OppList.iterator();
  FileItem thisFile = new FileItem();
  
		if ( n.hasNext() ) {
			int rowid = 0;
      int previousId = -1;
			while (n.hasNext()) {
		
				if (rowid != 1) {
					rowid = 1;
				} else {
					rowid = 2;
				}
	          	
				Opportunity thisOpp = (Opportunity)n.next();
        
        if (thisOpp.getId() != previousId) {
%>    
				<tr>
          <td width="100%" class="row<%= rowid %>" valign=center><a href="Leads.do?command=DetailsOpp&oppId=<%=thisOpp.getId()%>&return=dashboard"><%= toHtml(thisOpp.getAccountName()) %>:&nbsp;<%= toHtml(thisOpp.getDescription()) %></a>
          (<%=thisOpp.getComponentCount()%>)
        <% if (thisOpp.hasFiles()) {%>
        <%= thisFile.getImageTag() %>
        <%}%>             
          </td>
          <td width="55" class="row<%= rowid %>">$<%=thisOpp.getTotalValue()%></td>
        </tr>
<%
        }
        previousId = thisOpp.getId();
      }
	  } else {
%>
        <tr>
          <td valign=center colspan=7>No Opportunities at this time.</td>
        </tr>
<%}%>
	  
      </table>
    </td>
  </tr>
</table>
</form>
</body>
