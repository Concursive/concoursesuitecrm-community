<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="Message" class="com.darkhorseventures.cfsbase.Message" scope="request"/>
<%= (Message.getMessageText()) %> 

