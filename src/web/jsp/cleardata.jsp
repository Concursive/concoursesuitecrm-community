<%@ page import="java.util.*,org.aspcfs.modules.admin.base.*,org.aspcfs.controller.*" %>
<% 
  Hashtable globalStatus = (Hashtable)getServletConfig().getServletContext().getAttribute("SystemStatus");
  
  Iterator i = globalStatus.values().iterator();
  UserList shortChildList = new UserList();
  UserList fullChildList = new UserList();
  
  while (i.hasNext()) {
    SystemStatus thisStatus = (SystemStatus)i.next();
    
    UserList thisList = thisStatus.getHierarchyList();
    Iterator j = thisList.iterator();
    
    while (j.hasNext()) {
      User thisUser = (User)j.next();
      shortChildList = thisUser.getShortChildList();
      fullChildList = thisUser.getFullChildList(shortChildList, new UserList());
      
      Iterator k = fullChildList.iterator();
      
      while (k.hasNext()) {
        User indUser = (User)k.next();
        indUser.setIsValid(false,true);
      }
      
      //thisUser.setIsValid(false,true);
    }
    
  }
%>
