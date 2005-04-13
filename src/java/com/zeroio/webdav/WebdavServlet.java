/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
/*
 *  Copyright 1999,2004 The Apache Software Foundation.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.zeroio.webdav;

import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.webdav.context.ModuleContext;
import org.apache.catalina.servlets.DefaultServlet;
import org.apache.catalina.util.DOMWriter;
import org.apache.catalina.util.RequestUtil;
import org.apache.catalina.util.XMLWriter;
import org.apache.naming.resources.Resource;
import org.apache.tomcat.util.http.FastHttpDateFormat;
import org.aspcfs.controller.SecurityHook;
import org.aspcfs.controller.SystemStatus;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *  Servlet which adds support for WebDAV level 2. All the basic HTTP requests
 *  are handled by the DefaultServlet.
 *
 *@author     Remy Maucherat
 *@created    July 12, 2004
 *@version    $Revision$ $Date$
 */

public class WebdavServlet
     extends DefaultServlet {

  // -------------------------------------------------------------- Constants

  private final static String METHOD_HEAD = "HEAD";
  private final static String METHOD_OPTIONS = "OPTIONS";
  private final static String METHOD_PROPFIND = "PROPFIND";
  private final static String METHOD_PROPPATCH = "PROPPATCH";
  private final static String METHOD_MKCOL = "MKCOL";
  private final static String METHOD_COPY = "COPY";
  private final static String METHOD_MOVE = "MOVE";
  private final static String METHOD_LOCK = "LOCK";
  private final static String METHOD_UNLOCK = "UNLOCK";

  /**
   *  CFS_USER_REALM should never be changed since when a user is added to the
   *  system this value is used in calculating the user's webdav password.
   *  changing the realm will invalidate all the users accessing webdav
   */
  public final static String CFS_USER_REALM = "Centric CRM";

  /**
   *  List of systems initialized if system is in asp mode
   */
  private HashMap systemsInitialized = new HashMap();

  /**
   *  Default depth is infite.
   */
  private final static int INFINITY = 3;
  // To limit tree browsing a bit

  /**
   *  PROPFIND - Specify a property mask.
   */
  private final static int FIND_BY_PROPERTY = 0;

  /**
   *  PROPFIND - Display all properties.
   */
  private final static int FIND_ALL_PROP = 1;

  /**
   *  PROPFIND - Return property names.
   */
  private final static int FIND_PROPERTY_NAMES = 2;

  /**
   *  Create a new lock.
   */
  private final static int LOCK_CREATION = 0;

  /**
   *  Refresh lock.
   */
  private final static int LOCK_REFRESH = 1;

  /**
   *  Default lock timeout value.
   */
  private final static int DEFAULT_TIMEOUT = 3600;

  /**
   *  Maximum lock timeout.
   */
  private final static int MAX_TIMEOUT = 604800;

  /**
   *  Default namespace.
   */
  protected final static String DEFAULT_NAMESPACE = "DAV:";

  /**
   *  Simple date format for the creation date ISO representation (partial).
   */
  protected final static SimpleDateFormat creationDateFormat =
      new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

  static {
    creationDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
  }

  // ----------------------------------------------------- Instance Variables

  /**
   *  Repository of the locks put on single resources. <p>
   *
   *  Key : path <br>
   *  Value : LockInfo
   */
  private Hashtable resourceLocks = new Hashtable();

  /**
   *  Repository of the lock-null resources. <p>
   *
   *  Key : path of the collection containing the lock-null resource<br>
   *  Value : Vector of lock-null resource which are members of the collection.
   *  Each element of the Vector is the path associated with the lock-null
   *  resource.
   */
  private Hashtable lockNullResources = new Hashtable();

  /**
   *  Vector of the heritable locks. <p>
   *
   *  Key : path <br>
   *  Value : LockInfo
   */
  private Vector collectionLocks = new Vector();

  /**
   *  Secret information used to generate reasonably secure lock ids.
   */
  private String secret = "catalina";



  // --------------------------------------------------------- Public Methods

  /**
   *  Initialize this servlet.
   *
   *@exception  ServletException  Description of the Exception
   */
  public void init()
       throws ServletException {

    super.init();
    ServletConfig config = getServletConfig();
    String value = null;
    try {
      value = config.getInitParameter("secret");
      if (value != null) {
        secret = value;
      }
    } catch (Throwable t) {
      ;
    }
  }


  // ------------------------------------------------------ Protected Methods

  /**
   *  Return JAXP document builder instance.
   *
   *@return                       The documentBuilder value
   *@exception  ServletException  Description of the Exception
   */
  protected DocumentBuilder getDocumentBuilder()
       throws ServletException {
    DocumentBuilder documentBuilder = null;
    DocumentBuilderFactory documentBuilderFactory = null;
    try {
      documentBuilderFactory = DocumentBuilderFactory.newInstance();
      documentBuilderFactory.setNamespaceAware(true);
      documentBuilder = documentBuilderFactory.newDocumentBuilder();
    } catch (ParserConfigurationException e) {
      throw new ServletException
          (sm.getString("webdavservlet.jaxpfailed"));
    }
    return documentBuilder;
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  protected synchronized boolean systemInitialized(ActionContext context) {
    String serverName = context.getRequest().getServerName();
    return (systemsInitialized.containsKey(serverName));
  }


  /**
   *  Description of the Method
   *
   *@param  context          Description of the Parameter
   *@exception  IOException  Description of the Exception
   */
  protected synchronized void initializeSystem(ActionContext context) throws IOException {
    Connection db = null;
    String serverName = context.getRequest().getServerName();
    try {
      ConnectionElement ce = this.retrieveConnectionElement(context);
      db = this.getConnection(context);
      SecurityHook.retrieveSystemStatus(context.getServletContext(), db, ce);
      systemsInitialized.put(serverName, new Boolean(true));
    } catch (SQLException e) {
      e.printStackTrace(System.out);
      context.getResponse().sendError(CFS_SQLERROR, e.getMessage());
    } finally {
      this.freeConnection(db, context);
    }
  }



  /**
   *  A2 is one of the components used in re-calculating the request digest at
   *  the server end
   *
   *@param  context  Description of the Parameter
   *@param  params   Description of the Parameter
   *@return          The a2 value
   */
  protected String getA2(ActionContext context, HashMap params) {
    String method = context.getRequest().getMethod();
    String uri = (String) params.get("uri");
    return md5Encoder.encode(md5Helper.digest((method + ":" + uri).getBytes()));
  }


  /**
   *  Description of the Method
   *
   *@param  argHeader        Description of the Parameter
   *@param  context          Description of the Parameter
   *@return                  Description of the Return Value
   *@exception  IOException  Description of the Exception
   */
  protected boolean allowUser(ActionContext context, String argHeader) throws IOException {

    if (argHeader == null) {
      return false;
    } else {
      //If the Authorization header user is not the user requesting a resource the
      //force the user to authenticate again
      if (context.getRequest().getParameter("user") != null) {
        if (!context.getRequest().getParameter("user").equals(this.getUser(context))) {
          //System.out.println("User: " + this.getUser(context));
          //System.out.println("Req User: " + context.getRequest().getParameter("user"));
          return false;
        }
      }
    }
    boolean status = false;
    if (System.getProperty("DEBUG") != null) {
      System.out.println("USERAGENT: " + context.getRequest().getHeader("user-agent"));
    }
    if (context.getRequest().getHeader("user-agent").toLowerCase().startsWith("microsoft")
          || context.getRequest().getHeader("user-agent").toLowerCase().indexOf("windows") > -1) {
      //BASIC AUTHENTICATION
      if (!argHeader.toUpperCase().startsWith("BASIC")) {
        return false;  // we only do BASIC
      }
      // Get encoded user and password, comes after "BASIC "
      String userpassEncoded = argHeader.substring(6);
  
      // Decode it, using any base 64 decoder (we use com.oreilly.servlet)
      sun.misc.BASE64Decoder dec = new sun.misc.BASE64Decoder();
      String userpassDecoded = new String(dec.decodeBuffer(userpassEncoded));
      if (System.getProperty("DEBUG") != null) {
        System.out.println("USER (BASIC): " + userpassDecoded);
      }
      // Check our user list to see if that user and password are "allowed"
      String username = userpassDecoded.substring(0, userpassDecoded.indexOf(":"));
      String password = userpassDecoded.substring(username.length() + 1);
      Connection db = null;
      try {
        db = this.getConnection(context);
        WebdavManager manager = this.getWebdavManager(context);
        //System.out.println("username: " + username);
        if (manager.hasUser(username)) {
          status = true;
        } else if (manager.allowUser(db, username, password)) {
          String nonce = "";
          status = manager.addUser(db, username, nonce);
        }
      } catch (SQLException e) {
        e.printStackTrace(System.out);
        //TODO: set error message in the response
      } finally {
        this.freeConnection(db, context);
      }
    } else {
      //DIGEST AUTHENTICATION
      if (!argHeader.startsWith("Digest")) {
        return false;
      }
      
      HashMap params = getAuthenticationParams(context, argHeader);
      //System.out.println("client digest : " + (String) params.get("response"));
      Connection db = null;
      String username = (String) params.get("username");
      if (System.getProperty("DEBUG") != null) {
        System.out.println("USER (DIGEST): " + username);
      }
      try {
        WebdavManager manager = this.getWebdavManager(context);
        if (manager.hasUser(username)) {
          // user already logged in. calculate the digest value and validate the user
          String digest =
              md5Encoder.encode(md5Helper.digest((manager.getUser(username).getDigest() + ":" +
              (String) params.get("nonce") + ":" + getA2(context, params)).getBytes()));
          if (digest.equals((String) params.get("response"))) {
            // user has been authenticated earlier and is still valid
            status = true;
          } else {
            // user is no longer valid.
            manager.removeUser(username);
            status = false;
          }
          //System.out.println("server digest : " + digest);
        } else {
          // Try to authenticate the user
          db = this.getConnection(context);
          String digest =
              md5Encoder.encode(md5Helper.digest((manager.getWebdavPassword(db, username) + ":" +
              (String) params.get("nonce") + ":" +
              getA2(context, params)).getBytes()));
  
          //System.out.println("server digest : " + digest);
          String nonce = (String) params.get("nonce");
          if (digest.equals((String) params.get("response"))) {
            status = manager.addUser(db, username, nonce);
          }
        }
      } catch (SQLException e) {
        e.printStackTrace(System.out);
        //TODO: set error message in the response
      } finally {
        this.freeConnection(db, context);
      }
    }
    return status;
  }


  /**
   *  Handles the special WebDAV methods.
   *
   *@param  req                   Description of the Parameter
   *@param  resp                  Description of the Parameter
   *@exception  ServletException  Description of the Exception
   *@exception  IOException       Description of the Exception
   */
  protected void service(HttpServletRequest req, HttpServletResponse resp)
       throws ServletException, IOException {
    ActionContext context = new ActionContext(this, null, null, req, resp);
    if (!systemInitialized(context)) {
      // initialize the SystemStatus, WebdavManager and ConnectionElement for this server
      initializeSystem(context);
    }
    // Authentication Check
    String argHeader = req.getHeader("Authorization");
    if (!allowUser(context, argHeader)) {
      System.out.println("Unauthorized access");
      if (context.getRequest().getHeader("user-agent").toLowerCase().startsWith("microsoft")
            || context.getRequest().getHeader("user-agent").toLowerCase().indexOf("windows") > -1) {
        //Microsoft Client. Hence use Basic Authentication
        //Basic Authentication
        //System.out.println("Authentication Scheme-> BASIC");
        resp.setHeader("WWW-Authenticate", "BASIC realm=\"" + CFS_USER_REALM + "\"");
      } else {
        //Any othe client use Digest Authentication
        // Digest Authentication
        // determine the 'number once' value that is unique for this auth
        //System.out.println("Authentication Scheme-> DIGEST");
        String nonce = generateNonce();
        // determine the 'opaque' value which should be returned as is by the client
        String opaque = generateOpaque();
        resp.setHeader("WWW-Authenticate", "Digest realm=\"" + CFS_USER_REALM + "\", " +
            "nonce=\"" + nonce + "\", " +
            "opaque=\"" + opaque + "\"");
      }
      resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    String method = req.getMethod();

    if (method.equals(METHOD_PROPFIND)) {
      doPropfind(context);
    } else if (method.equals(METHOD_OPTIONS)) {
      doOptions(context);
    } else if (method.equals(METHOD_PROPPATCH)) {
      doProppatch(req, resp);
    } else if (method.equals(METHOD_MKCOL)) {
      doMkcol(req, resp);
    } else if (method.equals(METHOD_COPY)) {
      doCopy(req, resp);
    } else if (method.equals(METHOD_MOVE)) {
      doMove(req, resp);
    } else if (method.equals(METHOD_LOCK)) {
      doLock(req, resp);
    } else if (method.equals(METHOD_UNLOCK)) {
      doUnlock(req, resp);
    } else {
      // DefaultServlet processing
      super.service(req, resp);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   */
  protected void doOptions(ActionContext context) {
    context.getResponse().setHeader("DAV", "1");
    context.getResponse().setHeader("Allow", "GET, HEAD, POST, PUT, DELETE, OPTIONS, TRACE, PROPFIND");
  }


  /**
   *  Check if the conditions specified in the optional If headers are
   *  satisfied.
   *
   *@param  request          The servlet request we are processing
   *@param  response         The servlet response we are creating
   *@param  resourceInfo     File object
   *@return                  boolean true if the resource meets all the
   *      specified conditions, and false if any of the conditions is not
   *      satisfied, in which case request processing is stopped
   *@exception  IOException  Description of the Exception
   */
  protected boolean checkIfHeaders(HttpServletRequest request,
      HttpServletResponse response,
      ResourceInfo resourceInfo)
       throws IOException {

    if (!super.checkIfHeaders(request, response, resourceInfo)) {
      return false;
    }

    // TODO : Checking the WebDAV If header
    return true;
  }


  /**
   *  OPTIONS Method.
   *
   *@param  req                   Description of the Parameter
   *@param  resp                  Description of the Parameter
   *@exception  ServletException  Description of the Exception
   *@exception  IOException       Description of the Exception
   */
  protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
       throws ServletException, IOException {

    resp.addHeader("DAV", "1,2");

    // Retrieve the resources
    DirContext resources = getResources();

    if (resources == null) {
      resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      return;
    }

    StringBuffer methodsAllowed = determineMethodsAllowed(resources,
        req);

    resp.addHeader("Allow", methodsAllowed.toString());
    resp.addHeader("MS-Author-Via", "DAV");

  }


  /**
   *  PROPFIND Method.
   *
   *@param  context               Description of the Parameter
   *@exception  ServletException  Description of the Exception
   *@exception  IOException       Description of the Exception
   */
  protected void doPropfind(ActionContext context)
       throws ServletException, IOException {

    String path = getRelativePath(context.getRequest());
    //fix for windows clients
    if (path.equals("/files")) {
      path = "";
    }
    
    if (path.endsWith("/")) {
      path = path.substring(0, path.length() - 1);
    }

    if ((path.toUpperCase().startsWith("/WEB-INF")) ||
        (path.toUpperCase().startsWith("/META-INF"))) {
      context.getResponse().sendError(WebdavStatus.SC_FORBIDDEN);
      return;
    }

    // Properties which are to be displayed.
    Vector properties = null;
    // Propfind depth
    int depth = INFINITY;
    // Propfind type
    int type = FIND_ALL_PROP;

    String depthStr = context.getRequest().getHeader("Depth");
    if (depthStr == null) {
      depth = INFINITY;
    } else {
      if (depthStr.equals("0")) {
        depth = 0;
      } else if (depthStr.equals("1")) {
        depth = 1;
      } else if (depthStr.equals("infinity")) {
        depth = INFINITY;
      }
    }

    /*
     *  Read the request xml and determine all the properties
     */
  
    /*
    Node propNode = null;
    DocumentBuilder documentBuilder = getDocumentBuilder();
    try {
      Document document = documentBuilder.parse
          (new InputSource(context.getRequest().getInputStream()));
      // Get the root element of the document
      Element rootElement = document.getDocumentElement();
      NodeList childList = rootElement.getChildNodes();
      for (int i = 0; i < childList.getLength(); i++) {
        Node currentNode = childList.item(i);
        switch (currentNode.getNodeType()) {
          case Node.TEXT_NODE:
            break;
          case Node.ELEMENT_NODE:
            if (currentNode.getNodeName().endsWith("prop")) {
              type = FIND_BY_PROPERTY;
              propNode = currentNode;
            }
            if (currentNode.getNodeName().endsWith("propname")) {
              type = FIND_PROPERTY_NAMES;
            }
            if (currentNode.getNodeName().endsWith("allprop")) {
              type = FIND_ALL_PROP;
            }
            break;
        }
      }
    } catch (Exception e) {
      // Most likely there was no content : we use the defaults.
      // TODO : Enhance that !
      e.printStackTrace(System.out);
    }
    
    if (type == FIND_BY_PROPERTY) {
      properties = new Vector();
      NodeList childList = propNode.getChildNodes();
      for (int i = 0; i < childList.getLength(); i++) {
        Node currentNode = childList.item(i);
        switch (currentNode.getNodeType()) {
          case Node.TEXT_NODE:
            break;
          case Node.ELEMENT_NODE:
            String nodeName = currentNode.getNodeName();
            String propertyName = null;
            if (nodeName.indexOf(':') != -1) {
              propertyName = nodeName.substring
                  (nodeName.indexOf(':') + 1);
            } else {
              propertyName = nodeName;
            }
            // href is a live property which is handled differently
            properties.addElement(propertyName);
            break;
        }
      }
    }
    */
    // Properties have been determined
    // Retrieve the resources


    Connection db = null;
    boolean exists = true;
    boolean status = true;
    Object object = null;
    ModuleContext resources = null;
    SystemStatus thisSystem = null;
    StringBuffer xmlsb = new StringBuffer();
    try {
      db = this.getConnection(context);
      resources = getCFSResources(db, context);
      if (resources == null) {
        context.getResponse().sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return;
      }
      thisSystem = this.getSystemStatus(context);
      object = resources.lookup(thisSystem, db, path);
    } catch (NamingException e) {
      //e.printStackTrace(System.out);
      exists = false;
      int slash = path.lastIndexOf('/');
      if (slash != -1) {
        String parentPath = path.substring(0, slash);
        Vector currentLockNullResources =
            (Vector) lockNullResources.get(parentPath);
        if (currentLockNullResources != null) {
          Enumeration lockNullResourcesList =
              currentLockNullResources.elements();
          while (lockNullResourcesList.hasMoreElements()) {
            String lockNullPath = (String)
                lockNullResourcesList.nextElement();
            if (lockNullPath.equals(path)) {
              context.getResponse().setStatus(WebdavStatus.SC_MULTI_STATUS);
              context.getResponse().setContentType("text/xml; charset=UTF-8");
              // Create multistatus object
              XMLWriter generatedXML =
                  new XMLWriter(context.getResponse().getWriter());
              generatedXML.writeXMLHeader();
              generatedXML.writeElement
                  (null, "multistatus"
                   + generateNamespaceDeclarations(),
                  XMLWriter.OPENING);
              parseLockNullProperties
                  (context.getRequest(), generatedXML, lockNullPath, type,
                  properties);
              generatedXML.writeElement(null, "multistatus",
                  XMLWriter.CLOSING);
              generatedXML.sendData();
              //e.printStackTrace(System.out);
              return;
            }
          }
        }
      }
    } catch (SQLException e) {
      e.printStackTrace(System.out);
      context.getResponse().sendError(CFS_SQLERROR, e.getMessage());
      status = false;
    } finally {
      this.freeConnection(db, context);
    }

    if (!status) {
      return;
    }

    if (!exists) {
      context.getResponse().sendError(HttpServletResponse.SC_NOT_FOUND, path);
      return;
    }

    context.getResponse().setStatus(WebdavStatus.SC_MULTI_STATUS);
    context.getResponse().setContentType("text/xml; charset=UTF-8");
    // Create multistatus object
    ////System.out.println("Creating Multistatus Object");

    XMLWriter generatedXML = new XMLWriter(context.getResponse().getWriter());
    generatedXML.writeXMLHeader();
    generatedXML.writeElement(null, "multistatus"
         + generateNamespaceDeclarations(),
        XMLWriter.OPENING);

    //System.out.println("Depth: " + depth);
    if (depth == 0) {
      parseProperties(context, resources, generatedXML, path, type, properties);
    } else {
      // The stack always contains the object of the current level
      Stack stack = new Stack();
      stack.push(path);
      // Stack of the objects one level below
      Stack stackBelow = new Stack();

      while ((!stack.isEmpty()) && (depth >= 0)) {
        String currentPath = (String) stack.pop();
        if (!currentPath.equals(path)) {
          parseProperties(context, resources, generatedXML, currentPath, type, properties);
        }
        try {
          db = this.getConnection(context);
          object = resources.lookup(thisSystem, db, currentPath);
        } catch (NamingException e) {
          continue;
        } catch (SQLException e) {
          //e.printStackTrace(System.out);
          context.getResponse().sendError(CFS_SQLERROR, e.getMessage());
          status = false;
        } finally {
          this.freeConnection(db, context);
        }

        if (!status) {
          return;
        }

        if ((object instanceof ModuleContext) && depth > 0) {
          // Get a list of all the resources at the current path and store them
          // in the stack
          try {
            NamingEnumeration enum1 = resources.list(currentPath);
            int count = 0;
            while (enum1.hasMoreElements()) {
              NameClassPair ncPair =
                  (NameClassPair) enum1.nextElement();
              String newPath = currentPath;
              if (!(newPath.endsWith("/"))) {
                newPath += "/";
              }
              newPath += ncPair.getName();
              stackBelow.push(newPath);
              count++;
            }
            if (currentPath.equals(path) && count == 0) {
              // This directory does not have any files or folders.
              parseProperties(context, resources, generatedXML, properties);
            }
          } catch (NamingException e) {
            //e.printStackTrace(System.out);
            context.getResponse().sendError
                (HttpServletResponse.SC_INTERNAL_SERVER_ERROR, path);
            return;
          }

          // Displaying the lock-null resources present in that collection
          String lockPath = currentPath;
          if (lockPath.endsWith("/")) {
            lockPath =
                lockPath.substring(0, lockPath.length() - 1);
          }
          Vector currentLockNullResources =
              (Vector) lockNullResources.get(lockPath);
          if (currentLockNullResources != null) {
            Enumeration lockNullResourcesList =
                currentLockNullResources.elements();
            while (lockNullResourcesList.hasMoreElements()) {
              String lockNullPath = (String)
                  lockNullResourcesList.nextElement();
              System.out.println("Lock null path: " + lockNullPath);
              parseLockNullProperties
                  (context.getRequest(), generatedXML, lockNullPath, type,
                  properties);
            }
          }
        }
        if (stack.isEmpty()) {
          depth--;
          stack = stackBelow;
          stackBelow = new Stack();
        }
        xmlsb.append(generatedXML.toString());
        //System.out.println("xml : " + generatedXML.toString());
        generatedXML.sendData();
      }
    }

    generatedXML.writeElement(null, "multistatus",
        XMLWriter.CLOSING);
    xmlsb.append(generatedXML.toString());
    generatedXML.sendData();
    //System.out.println("xml: " + xmlsb.toString());
  }


  /**
   *  PROPPATCH Method.
   *
   *@param  req                   Description of the Parameter
   *@param  resp                  Description of the Parameter
   *@exception  ServletException  Description of the Exception
   *@exception  IOException       Description of the Exception
   */
  protected void doProppatch(HttpServletRequest req,
      HttpServletResponse resp)
       throws ServletException, IOException {

    if (readOnly) {
      resp.sendError(WebdavStatus.SC_FORBIDDEN);
      return;
    }

    if (isLocked(req)) {
      resp.sendError(WebdavStatus.SC_LOCKED);
      return;
    }

    resp.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);

  }


  /**
   *  MKCOL Method.
   *
   *@param  req                   Description of the Parameter
   *@param  resp                  Description of the Parameter
   *@exception  ServletException  Description of the Exception
   *@exception  IOException       Description of the Exception
   */
  protected void doMkcol(HttpServletRequest req, HttpServletResponse resp)
       throws ServletException, IOException {

    if (readOnly) {
      resp.sendError(WebdavStatus.SC_FORBIDDEN);
      return;
    }

    if (isLocked(req)) {
      resp.sendError(WebdavStatus.SC_LOCKED);
      return;
    }

    String path = getRelativePath(req);

    if ((path.toUpperCase().startsWith("/WEB-INF")) ||
        (path.toUpperCase().startsWith("/META-INF"))) {
      resp.sendError(WebdavStatus.SC_FORBIDDEN);
      return;
    }

    // Retrieve the resources
    DirContext resources = getResources();

    if (resources == null) {
      resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      return;
    }

    boolean exists = true;
    Object object = null;
    try {
      object = resources.lookup(path);
    } catch (NamingException e) {
      exists = false;
    }

    // Can't create a collection if a resource already exists at the given
    // path
    if (exists) {
      // Get allowed methods
      StringBuffer methodsAllowed = determineMethodsAllowed(resources,
          req);

      resp.addHeader("Allow", methodsAllowed.toString());

      resp.sendError(WebdavStatus.SC_METHOD_NOT_ALLOWED);
      return;
    }

    if (req.getInputStream().available() > 0) {
      DocumentBuilder documentBuilder = getDocumentBuilder();
      try {
        Document document = documentBuilder.parse
            (new InputSource(req.getInputStream()));
        // TODO : Process this request body
        resp.sendError(WebdavStatus.SC_NOT_IMPLEMENTED);
        return;
      } catch (SAXException saxe) {
        // Parse error - assume invalid content
        resp.sendError(WebdavStatus.SC_BAD_REQUEST);
        return;
      }
    }

    boolean result = true;
    try {
      resources.createSubcontext(path);
    } catch (NamingException e) {
      result = false;
    }

    if (!result) {
      resp.sendError(WebdavStatus.SC_CONFLICT,
          WebdavStatus.getStatusText
          (WebdavStatus.SC_CONFLICT));
    } else {
      resp.setStatus(WebdavStatus.SC_CREATED);
      // Removing any lock-null resource which would be present
      lockNullResources.remove(path);
    }

  }


  /**
   *  DELETE Method.
   *
   *@param  req                   Description of the Parameter
   *@param  resp                  Description of the Parameter
   *@exception  ServletException  Description of the Exception
   *@exception  IOException       Description of the Exception
   */
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
       throws ServletException, IOException {

    if (readOnly) {
      resp.sendError(WebdavStatus.SC_FORBIDDEN);
      return;
    }

    if (isLocked(req)) {
      resp.sendError(WebdavStatus.SC_LOCKED);
      return;
    }

    deleteResource(req, resp);

  }


  /**
   *  Process a POST request for the specified resource.
   *
   *@param  req                   Description of the Parameter
   *@param  resp                  Description of the Parameter
   *@exception  IOException       if an input/output error occurs
   *@exception  ServletException  if a servlet-specified error occurs
   */
  protected void doPut(HttpServletRequest req, HttpServletResponse resp)
       throws ServletException, IOException {

    if (isLocked(req)) {
      resp.sendError(WebdavStatus.SC_LOCKED);
      return;
    }

    super.doPut(req, resp);

    String path = getRelativePath(req);

    // Removing any lock-null resource which would be present
    lockNullResources.remove(path);

  }


  /**
   *  COPY Method.
   *
   *@param  req                   Description of the Parameter
   *@param  resp                  Description of the Parameter
   *@exception  ServletException  Description of the Exception
   *@exception  IOException       Description of the Exception
   */
  protected void doCopy(HttpServletRequest req, HttpServletResponse resp)
       throws ServletException, IOException {

    if (readOnly) {
      resp.sendError(WebdavStatus.SC_FORBIDDEN);
      return;
    }

    copyResource(req, resp);

  }


  /**
   *  MOVE Method.
   *
   *@param  req                   Description of the Parameter
   *@param  resp                  Description of the Parameter
   *@exception  ServletException  Description of the Exception
   *@exception  IOException       Description of the Exception
   */
  protected void doMove(HttpServletRequest req, HttpServletResponse resp)
       throws ServletException, IOException {

    if (readOnly) {
      resp.sendError(WebdavStatus.SC_FORBIDDEN);
      return;
    }

    if (isLocked(req)) {
      resp.sendError(WebdavStatus.SC_LOCKED);
      return;
    }

    String path = getRelativePath(req);

    if (copyResource(req, resp)) {
      deleteResource(path, req, resp, false);
    }
  }


  /**
   *  LOCK Method.
   *
   *@param  req                   Description of the Parameter
   *@param  resp                  Description of the Parameter
   *@exception  ServletException  Description of the Exception
   *@exception  IOException       Description of the Exception
   */
  protected void doLock(HttpServletRequest req, HttpServletResponse resp)
       throws ServletException, IOException {

    if (readOnly) {
      resp.sendError(WebdavStatus.SC_FORBIDDEN);
      return;
    }

    if (isLocked(req)) {
      resp.sendError(WebdavStatus.SC_LOCKED);
      return;
    }

    LockInfo lock = new LockInfo();

    // Parsing lock request

    // Parsing depth header

    String depthStr = req.getHeader("Depth");

    if (depthStr == null) {
      lock.depth = INFINITY;
    } else {
      if (depthStr.equals("0")) {
        lock.depth = 0;
      } else {
        lock.depth = INFINITY;
      }
    }

    // Parsing timeout header

    int lockDuration = DEFAULT_TIMEOUT;
    String lockDurationStr = req.getHeader("Timeout");
    if (lockDurationStr == null) {
      lockDuration = DEFAULT_TIMEOUT;
    } else {
      int commaPos = lockDurationStr.indexOf(",");
      // If multiple timeouts, just use the first
      if (commaPos != -1) {
        lockDurationStr = lockDurationStr.substring(0, commaPos);
      }
      if (lockDurationStr.startsWith("Second-")) {
        lockDuration =
            (new Integer(lockDurationStr.substring(7))).intValue();
      } else {
        if (lockDurationStr.equalsIgnoreCase("infinity")) {
          lockDuration = MAX_TIMEOUT;
        } else {
          try {
            lockDuration =
                (new Integer(lockDurationStr)).intValue();
          } catch (NumberFormatException e) {
            lockDuration = MAX_TIMEOUT;
          }
        }
      }
      if (lockDuration == 0) {
        lockDuration = DEFAULT_TIMEOUT;
      }
      if (lockDuration > MAX_TIMEOUT) {
        lockDuration = MAX_TIMEOUT;
      }
    }
    lock.expiresAt = System.currentTimeMillis() + (lockDuration * 1000);

    int lockRequestType = LOCK_CREATION;

    Node lockInfoNode = null;

    DocumentBuilder documentBuilder = getDocumentBuilder();

    try {
      Document document = documentBuilder.parse(new InputSource
          (req.getInputStream()));

      // Get the root element of the document
      Element rootElement = document.getDocumentElement();
      lockInfoNode = rootElement;
    } catch (Exception e) {
      lockRequestType = LOCK_REFRESH;
    }

    if (lockInfoNode != null) {

      // Reading lock information

      NodeList childList = lockInfoNode.getChildNodes();
      StringWriter strWriter = null;
      DOMWriter domWriter = null;

      Node lockScopeNode = null;
      Node lockTypeNode = null;
      Node lockOwnerNode = null;

      for (int i = 0; i < childList.getLength(); i++) {
        Node currentNode = childList.item(i);
        switch (currentNode.getNodeType()) {
          case Node.TEXT_NODE:
            break;
          case Node.ELEMENT_NODE:
            String nodeName = currentNode.getNodeName();
            if (nodeName.endsWith("lockscope")) {
              lockScopeNode = currentNode;
            }
            if (nodeName.endsWith("locktype")) {
              lockTypeNode = currentNode;
            }
            if (nodeName.endsWith("owner")) {
              lockOwnerNode = currentNode;
            }
            break;
        }
      }

      if (lockScopeNode != null) {

        childList = lockScopeNode.getChildNodes();
        for (int i = 0; i < childList.getLength(); i++) {
          Node currentNode = childList.item(i);
          switch (currentNode.getNodeType()) {
            case Node.TEXT_NODE:
              break;
            case Node.ELEMENT_NODE:
              String tempScope = currentNode.getNodeName();
              if (tempScope.indexOf(':') != -1) {
                lock.scope = tempScope.substring
                    (tempScope.indexOf(':') + 1);
              } else {
                lock.scope = tempScope;
              }
              break;
          }
        }

        if (lock.scope == null) {
          // Bad request
          resp.setStatus(WebdavStatus.SC_BAD_REQUEST);
        }
      } else {
        // Bad request
        resp.setStatus(WebdavStatus.SC_BAD_REQUEST);
      }

      if (lockTypeNode != null) {

        childList = lockTypeNode.getChildNodes();
        for (int i = 0; i < childList.getLength(); i++) {
          Node currentNode = childList.item(i);
          switch (currentNode.getNodeType()) {
            case Node.TEXT_NODE:
              break;
            case Node.ELEMENT_NODE:
              String tempType = currentNode.getNodeName();
              if (tempType.indexOf(':') != -1) {
                lock.type =
                    tempType.substring(tempType.indexOf(':') + 1);
              } else {
                lock.type = tempType;
              }
              break;
          }
        }

        if (lock.type == null) {
          // Bad request
          resp.setStatus(WebdavStatus.SC_BAD_REQUEST);
        }
      } else {
        // Bad request
        resp.setStatus(WebdavStatus.SC_BAD_REQUEST);
      }

      if (lockOwnerNode != null) {

        childList = lockOwnerNode.getChildNodes();
        for (int i = 0; i < childList.getLength(); i++) {
          Node currentNode = childList.item(i);
          switch (currentNode.getNodeType()) {
            case Node.TEXT_NODE:
              lock.owner += currentNode.getNodeValue();
              break;
            case Node.ELEMENT_NODE:
              strWriter = new StringWriter();
              domWriter = new DOMWriter(strWriter, true);
              domWriter.setQualifiedNames(false);
              domWriter.print(currentNode);
              lock.owner += strWriter.toString();
              break;
          }
        }

        if (lock.owner == null) {
          // Bad request
          resp.setStatus(WebdavStatus.SC_BAD_REQUEST);
        }
      } else {
        lock.owner = new String();
      }

    }

    String path = getRelativePath(req);

    lock.path = path;

    // Retrieve the resources
    DirContext resources = getResources();

    if (resources == null) {
      resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      return;
    }

    boolean exists = true;
    Object object = null;
    try {
      object = resources.lookup(path);
    } catch (NamingException e) {
      exists = false;
    }

    Enumeration locksList = null;

    if (lockRequestType == LOCK_CREATION) {

      // Generating lock id
      String lockTokenStr = req.getServletPath() + "-" + lock.type + "-"
           + lock.scope + "-" + req.getUserPrincipal() + "-"
           + lock.depth + "-" + lock.owner + "-" + lock.tokens + "-"
           + lock.expiresAt + "-" + System.currentTimeMillis() + "-"
           + secret;
      String lockToken =
          md5Encoder.encode(md5Helper.digest(lockTokenStr.getBytes()));

      if ((exists) && (object instanceof DirContext) &&
          (lock.depth == INFINITY)) {

        // Locking a collection (and all its member resources)

        // Checking if a child resource of this collection is
        // already locked
        Vector lockPaths = new Vector();
        locksList = collectionLocks.elements();
        while (locksList.hasMoreElements()) {
          LockInfo currentLock = (LockInfo) locksList.nextElement();
          if (currentLock.hasExpired()) {
            resourceLocks.remove(currentLock.path);
            continue;
          }
          if ((currentLock.path.startsWith(lock.path)) &&
              ((currentLock.isExclusive()) ||
              (lock.isExclusive()))) {
            // A child collection of this collection is locked
            lockPaths.addElement(currentLock.path);
          }
        }
        locksList = resourceLocks.elements();
        while (locksList.hasMoreElements()) {
          LockInfo currentLock = (LockInfo) locksList.nextElement();
          if (currentLock.hasExpired()) {
            resourceLocks.remove(currentLock.path);
            continue;
          }
          if ((currentLock.path.startsWith(lock.path)) &&
              ((currentLock.isExclusive()) ||
              (lock.isExclusive()))) {
            // A child resource of this collection is locked
            lockPaths.addElement(currentLock.path);
          }
        }

        if (!lockPaths.isEmpty()) {

          // One of the child paths was locked
          // We generate a multistatus error report

          Enumeration lockPathsList = lockPaths.elements();

          resp.setStatus(WebdavStatus.SC_CONFLICT);

          XMLWriter generatedXML = new XMLWriter();
          generatedXML.writeXMLHeader();

          generatedXML.writeElement
              (null, "multistatus" + generateNamespaceDeclarations(),
              XMLWriter.OPENING);

          while (lockPathsList.hasMoreElements()) {
            generatedXML.writeElement(null, "response",
                XMLWriter.OPENING);
            generatedXML.writeElement(null, "href",
                XMLWriter.OPENING);
            generatedXML.writeText((String) lockPathsList.nextElement());
            generatedXML.writeElement(null, "href",
                XMLWriter.CLOSING);
            generatedXML.writeElement(null, "status",
                XMLWriter.OPENING);
            generatedXML.writeText("HTTP/1.1 " + WebdavStatus.SC_LOCKED
                 + " " + WebdavStatus.getStatusText(WebdavStatus.SC_LOCKED));
            generatedXML.writeElement(null, "status",
                XMLWriter.CLOSING);

            generatedXML.writeElement(null, "response",
                XMLWriter.CLOSING);
          }

          generatedXML.writeElement(null, "multistatus",
              XMLWriter.CLOSING);

          Writer writer = resp.getWriter();
          writer.write(generatedXML.toString());
          writer.close();

          return;
        }

        boolean addLock = true;

        // Checking if there is already a shared lock on this path
        locksList = collectionLocks.elements();
        while (locksList.hasMoreElements()) {

          LockInfo currentLock = (LockInfo) locksList.nextElement();
          if (currentLock.path.equals(lock.path)) {

            if (currentLock.isExclusive()) {
              resp.sendError(WebdavStatus.SC_LOCKED);
              return;
            } else {
              if (lock.isExclusive()) {
                resp.sendError(WebdavStatus.SC_LOCKED);
                return;
              }
            }

            currentLock.tokens.addElement(lockToken);
            lock = currentLock;
            addLock = false;

          }
        }

        if (addLock) {
          lock.tokens.addElement(lockToken);
          collectionLocks.addElement(lock);
        }
      } else {

        // Locking a single resource

        // Retrieving an already existing lock on that resource
        LockInfo presentLock = (LockInfo) resourceLocks.get(lock.path);
        if (presentLock != null) {

          if ((presentLock.isExclusive()) || (lock.isExclusive())) {
            // If either lock is exclusive, the lock can't be
            // granted
            resp.sendError(WebdavStatus.SC_PRECONDITION_FAILED);
            return;
          } else {
            presentLock.tokens.addElement(lockToken);
            lock = presentLock;
          }

        } else {

          lock.tokens.addElement(lockToken);
          resourceLocks.put(lock.path, lock);

          // Checking if a resource exists at this path
          exists = true;
          try {
            object = resources.lookup(path);
          } catch (NamingException e) {
            exists = false;
          }
          if (!exists) {

            // "Creating" a lock-null resource
            int slash = lock.path.lastIndexOf('/');
            String parentPath = lock.path.substring(0, slash);

            Vector lockNulls =
                (Vector) lockNullResources.get(parentPath);
            if (lockNulls == null) {
              lockNulls = new Vector();
              lockNullResources.put(parentPath, lockNulls);
            }

            lockNulls.addElement(lock.path);

          }
          // Add the Lock-Token header as by RFC 2518 8.10.1
          // - only do this for newly created locks
          resp.addHeader("Lock-Token", "<opaquelocktoken:"
               + lockToken + ">");
        }

      }

    }

    if (lockRequestType == LOCK_REFRESH) {

      String ifHeader = req.getHeader("If");
      if (ifHeader == null) {
        ifHeader = "";
      }

      // Checking resource locks

      LockInfo toRenew = (LockInfo) resourceLocks.get(path);
      Enumeration tokenList = null;
      if (lock != null) {

        // At least one of the tokens of the locks must have been given

        tokenList = toRenew.tokens.elements();
        while (tokenList.hasMoreElements()) {
          String token = (String) tokenList.nextElement();
          if (ifHeader.indexOf(token) != -1) {
            toRenew.expiresAt = lock.expiresAt;
            lock = toRenew;
          }
        }

      }

      // Checking inheritable collection locks

      Enumeration collectionLocksList = collectionLocks.elements();
      while (collectionLocksList.hasMoreElements()) {
        toRenew = (LockInfo) collectionLocksList.nextElement();
        if (path.equals(toRenew.path)) {

          tokenList = toRenew.tokens.elements();
          while (tokenList.hasMoreElements()) {
            String token = (String) tokenList.nextElement();
            if (ifHeader.indexOf(token) != -1) {
              toRenew.expiresAt = lock.expiresAt;
              lock = toRenew;
            }
          }

        }
      }

    }

    // Set the status, then generate the XML response containing
    // the lock information
    XMLWriter generatedXML = new XMLWriter();
    generatedXML.writeXMLHeader();
    generatedXML.writeElement(null, "prop"
         + generateNamespaceDeclarations(),
        XMLWriter.OPENING);

    generatedXML.writeElement(null, "lockdiscovery",
        XMLWriter.OPENING);

    lock.toXML(generatedXML);

    generatedXML.writeElement(null, "lockdiscovery",
        XMLWriter.CLOSING);

    generatedXML.writeElement(null, "prop", XMLWriter.CLOSING);

    resp.setStatus(WebdavStatus.SC_OK);
    resp.setContentType("text/xml; charset=UTF-8");
    Writer writer = resp.getWriter();
    writer.write(generatedXML.toString());
    writer.close();

  }


  /**
   *  UNLOCK Method.
   *
   *@param  req                   Description of the Parameter
   *@param  resp                  Description of the Parameter
   *@exception  ServletException  Description of the Exception
   *@exception  IOException       Description of the Exception
   */
  protected void doUnlock(HttpServletRequest req, HttpServletResponse resp)
       throws ServletException, IOException {

    if (readOnly) {
      resp.sendError(WebdavStatus.SC_FORBIDDEN);
      return;
    }

    if (isLocked(req)) {
      resp.sendError(WebdavStatus.SC_LOCKED);
      return;
    }

    String path = getRelativePath(req);

    String lockTokenHeader = req.getHeader("Lock-Token");
    if (lockTokenHeader == null) {
      lockTokenHeader = "";
    }

    // Checking resource locks

    LockInfo lock = (LockInfo) resourceLocks.get(path);
    Enumeration tokenList = null;
    if (lock != null) {

      // At least one of the tokens of the locks must have been given

      tokenList = lock.tokens.elements();
      while (tokenList.hasMoreElements()) {
        String token = (String) tokenList.nextElement();
        if (lockTokenHeader.indexOf(token) != -1) {
          lock.tokens.removeElement(token);
        }
      }

      if (lock.tokens.isEmpty()) {
        resourceLocks.remove(path);
        // Removing any lock-null resource which would be present
        lockNullResources.remove(path);
      }
    }

    // Checking inheritable collection locks

    Enumeration collectionLocksList = collectionLocks.elements();
    while (collectionLocksList.hasMoreElements()) {
      lock = (LockInfo) collectionLocksList.nextElement();
      if (path.equals(lock.path)) {

        tokenList = lock.tokens.elements();
        while (tokenList.hasMoreElements()) {
          String token = (String) tokenList.nextElement();
          if (lockTokenHeader.indexOf(token) != -1) {
            lock.tokens.removeElement(token);
            break;
          }
        }

        if (lock.tokens.isEmpty()) {
          collectionLocks.removeElement(lock);
          // Removing any lock-null resource which would be present
          lockNullResources.remove(path);
        }
      }
    }

    resp.setStatus(WebdavStatus.SC_NO_CONTENT);

  }


  // -------------------------------------------------------- Private Methods

  /**
   *  Generate the namespace declarations.
   *
   *@return    Description of the Return Value
   */
  private String generateNamespaceDeclarations() {
    return " xmlns=\"" + DEFAULT_NAMESPACE + "\"";
  }


  /**
   *  Check to see if a resource is currently write locked. The method will look
   *  at the "If" header to make sure the client has give the appropriate lock
   *  tokens.
   *
   *@param  req  Servlet request
   *@return      boolean true if the resource is locked (and no appropriate lock
   *      token has been found for at least one of the non-shared locks which
   *      are present on the resource).
   */
  private boolean isLocked(HttpServletRequest req) {

    String path = getRelativePath(req);

    String ifHeader = req.getHeader("If");
    if (ifHeader == null) {
      ifHeader = "";
    }

    String lockTokenHeader = req.getHeader("Lock-Token");
    if (lockTokenHeader == null) {
      lockTokenHeader = "";
    }

    return isLocked(path, ifHeader + lockTokenHeader);
  }


  /**
   *  Check to see if a resource is currently write locked.
   *
   *@param  path      Path of the resource
   *@param  ifHeader  "If" HTTP header which was included in the request
   *@return           boolean true if the resource is locked (and no appropriate
   *      lock token has been found for at least one of the non-shared locks
   *      which are present on the resource).
   */
  private boolean isLocked(String path, String ifHeader) {

    // Checking resource locks

    LockInfo lock = (LockInfo) resourceLocks.get(path);
    Enumeration tokenList = null;
    if ((lock != null) && (lock.hasExpired())) {
      resourceLocks.remove(path);
    } else if (lock != null) {

      // At least one of the tokens of the locks must have been given

      tokenList = lock.tokens.elements();
      boolean tokenMatch = false;
      while (tokenList.hasMoreElements()) {
        String token = (String) tokenList.nextElement();
        if (ifHeader.indexOf(token) != -1) {
          tokenMatch = true;
        }
      }
      if (!tokenMatch) {
        return true;
      }
    }

    // Checking inheritable collection locks

    Enumeration collectionLocksList = collectionLocks.elements();
    while (collectionLocksList.hasMoreElements()) {
      lock = (LockInfo) collectionLocksList.nextElement();
      if (lock.hasExpired()) {
        collectionLocks.removeElement(lock);
      } else if (path.startsWith(lock.path)) {

        tokenList = lock.tokens.elements();
        boolean tokenMatch = false;
        while (tokenList.hasMoreElements()) {
          String token = (String) tokenList.nextElement();
          if (ifHeader.indexOf(token) != -1) {
            tokenMatch = true;
          }
        }
        if (!tokenMatch) {
          return true;
        }
      }
    }

    return false;
  }


  /**
   *  Copy a resource.
   *
   *@param  req                   Servlet request
   *@param  resp                  Servlet response
   *@return                       boolean true if the copy is successful
   *@exception  ServletException  Description of the Exception
   *@exception  IOException       Description of the Exception
   */
  private boolean copyResource(HttpServletRequest req,
      HttpServletResponse resp)
       throws ServletException, IOException {

    // Parsing destination header

    String destinationPath = req.getHeader("Destination");

    if (destinationPath == null) {
      resp.sendError(WebdavStatus.SC_BAD_REQUEST);
      return false;
    }

    // Remove url encoding from destination
    destinationPath = RequestUtil.URLDecode(destinationPath, "UTF8");

    int protocolIndex = destinationPath.indexOf("://");
    if (protocolIndex >= 0) {
      // if the Destination URL contains the protocol, we can safely
      // trim everything upto the first "/" character after "://"
      int firstSeparator =
          destinationPath.indexOf("/", protocolIndex + 4);
      if (firstSeparator < 0) {
        destinationPath = "/";
      } else {
        destinationPath = destinationPath.substring(firstSeparator);
      }
    } else {
      String hostName = req.getServerName();
      if ((hostName != null) && (destinationPath.startsWith(hostName))) {
        destinationPath = destinationPath.substring(hostName.length());
      }

      int portIndex = destinationPath.indexOf(":");
      if (portIndex >= 0) {
        destinationPath = destinationPath.substring(portIndex);
      }

      if (destinationPath.startsWith(":")) {
        int firstSeparator = destinationPath.indexOf("/");
        if (firstSeparator < 0) {
          destinationPath = "/";
        } else {
          destinationPath =
              destinationPath.substring(firstSeparator);
        }
      }
    }

    // Normalise destination path (remove '.' and '..')
    destinationPath = normalize(destinationPath);

    String contextPath = req.getContextPath();
    if ((contextPath != null) &&
        (destinationPath.startsWith(contextPath))) {
      destinationPath = destinationPath.substring(contextPath.length());
    }

    String pathInfo = req.getPathInfo();
    if (pathInfo != null) {
      String servletPath = req.getServletPath();
      if ((servletPath != null) &&
          (destinationPath.startsWith(servletPath))) {
        destinationPath = destinationPath.substring(servletPath.length());
      }
    }

    if (debug > 0) {
      System.out.println("Dest path :" + destinationPath);
    }

    if ((destinationPath.toUpperCase().startsWith("/WEB-INF")) ||
        (destinationPath.toUpperCase().startsWith("/META-INF"))) {
      resp.sendError(WebdavStatus.SC_FORBIDDEN);
      return false;
    }

    String path = getRelativePath(req);

    if ((path.toUpperCase().startsWith("/WEB-INF")) ||
        (path.toUpperCase().startsWith("/META-INF"))) {
      resp.sendError(WebdavStatus.SC_FORBIDDEN);
      return false;
    }

    if (destinationPath.equals(path)) {
      resp.sendError(WebdavStatus.SC_FORBIDDEN);
      return false;
    }

    // Parsing overwrite header

    boolean overwrite = true;
    String overwriteHeader = req.getHeader("Overwrite");

    if (overwriteHeader != null) {
      if (overwriteHeader.equalsIgnoreCase("T")) {
        overwrite = true;
      } else {
        overwrite = false;
      }
    }

    // Overwriting the destination

    // Retrieve the resources
    DirContext resources = getResources();

    if (resources == null) {
      resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      return false;
    }

    boolean exists = true;
    try {
      resources.lookup(destinationPath);
    } catch (NamingException e) {
      exists = false;
    }

    if (overwrite) {

      // Delete destination resource, if it exists
      if (exists) {
        if (!deleteResource(destinationPath, req, resp, true)) {
          return false;
        }
      } else {
        resp.setStatus(WebdavStatus.SC_CREATED);
      }

    } else {

      // If the destination exists, then it's a conflict
      if (exists) {
        resp.sendError(WebdavStatus.SC_PRECONDITION_FAILED);
        return false;
      }
    }

    // Copying source to destination

    Hashtable errorList = new Hashtable();

    boolean result = copyResource(resources, errorList,
        path, destinationPath);

    if ((!result) || (!errorList.isEmpty())) {

      sendReport(req, resp, errorList);
      return false;
    }

    // Removing any lock-null resource which would be present at
    // the destination path
    lockNullResources.remove(destinationPath);

    return true;
  }


  /**
   *  Copy a collection.
   *
   *@param  resources  Resources implementation to be used
   *@param  errorList  Hashtable containing the list of errors which occurred
   *      during the copy operation
   *@param  source     Path of the resource to be copied
   *@param  dest       Destination path
   *@return            Description of the Return Value
   */
  private boolean copyResource(DirContext resources, Hashtable errorList,
      String source, String dest) {

    if (debug > 1) {
      System.out.println("Copy: " + source + " To: " + dest);
    }

    Object object = null;
    try {
      object = resources.lookup(source);
    } catch (NamingException e) {
    }

    if (object instanceof DirContext) {

      try {
        resources.createSubcontext(dest);
      } catch (NamingException e) {
        errorList.put
            (dest, new Integer(WebdavStatus.SC_CONFLICT));
        return false;
      }

      try {
        NamingEnumeration enum1 = resources.list(source);
        while (enum1.hasMoreElements()) {
          NameClassPair ncPair = (NameClassPair) enum1.nextElement();
          String childDest = dest;
          if (!childDest.equals("/")) {
            childDest += "/";
          }
          childDest += ncPair.getName();
          String childSrc = source;
          if (!childSrc.equals("/")) {
            childSrc += "/";
          }
          childSrc += ncPair.getName();
          copyResource(resources, errorList, childSrc, childDest);
        }
      } catch (NamingException e) {
        errorList.put
            (dest, new Integer(WebdavStatus.SC_INTERNAL_SERVER_ERROR));
        return false;
      }

    } else {

      if (object instanceof Resource) {
        try {
          resources.bind(dest, object);
        } catch (NamingException e) {
          errorList.put
              (source,
              new Integer(WebdavStatus.SC_INTERNAL_SERVER_ERROR));
          return false;
        }
      } else {
        errorList.put
            (source,
            new Integer(WebdavStatus.SC_INTERNAL_SERVER_ERROR));
        return false;
      }

    }

    return true;
  }


  /**
   *  Delete a resource.
   *
   *@param  req                   Servlet request
   *@param  resp                  Servlet response
   *@return                       boolean true if the copy is successful
   *@exception  ServletException  Description of the Exception
   *@exception  IOException       Description of the Exception
   */
  private boolean deleteResource(HttpServletRequest req,
      HttpServletResponse resp)
       throws ServletException, IOException {

    String path = getRelativePath(req);

    return deleteResource(path, req, resp, true);
  }


  /**
   *  Delete a resource.
   *
   *@param  path                  Path of the resource which is to be deleted
   *@param  req                   Servlet request
   *@param  resp                  Servlet response
   *@param  setStatus             Should the response status be set on
   *      successful completion
   *@return                       Description of the Return Value
   *@exception  ServletException  Description of the Exception
   *@exception  IOException       Description of the Exception
   */
  private boolean deleteResource(String path, HttpServletRequest req,
      HttpServletResponse resp, boolean setStatus)
       throws ServletException, IOException {

    if ((path.toUpperCase().startsWith("/WEB-INF")) ||
        (path.toUpperCase().startsWith("/META-INF"))) {
      resp.sendError(WebdavStatus.SC_FORBIDDEN);
      return false;
    }

    String ifHeader = req.getHeader("If");
    if (ifHeader == null) {
      ifHeader = "";
    }

    String lockTokenHeader = req.getHeader("Lock-Token");
    if (lockTokenHeader == null) {
      lockTokenHeader = "";
    }

    if (isLocked(path, ifHeader + lockTokenHeader)) {
      resp.sendError(WebdavStatus.SC_LOCKED);
      return false;
    }

    // Retrieve the resources
    DirContext resources = getResources();

    if (resources == null) {
      resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      return false;
    }

    boolean exists = true;
    Object object = null;
    try {
      object = resources.lookup(path);
    } catch (NamingException e) {
      exists = false;
    }

    if (!exists) {
      resp.sendError(WebdavStatus.SC_NOT_FOUND);
      return false;
    }

    boolean collection = (object instanceof DirContext);

    if (!collection) {
      try {
        resources.unbind(path);
      } catch (NamingException e) {
        resp.sendError(WebdavStatus.SC_INTERNAL_SERVER_ERROR);
        return false;
      }
    } else {

      Hashtable errorList = new Hashtable();

      deleteCollection(req, resources, path, errorList);
      try {
        resources.unbind(path);
      } catch (NamingException e) {
        errorList.put(path, new Integer
            (WebdavStatus.SC_INTERNAL_SERVER_ERROR));
      }

      if (!errorList.isEmpty()) {

        sendReport(req, resp, errorList);
        return false;
      }
    }
    if (setStatus) {
      resp.setStatus(WebdavStatus.SC_NO_CONTENT);
    }
    return true;
  }


  /**
   *  Deletes a collection.
   *
   *@param  resources  Resources implementation associated with the context
   *@param  path       Path to the collection to be deleted
   *@param  errorList  Contains the list of the errors which occurred
   *@param  req        Description of the Parameter
   */
  private void deleteCollection(HttpServletRequest req,
      DirContext resources,
      String path, Hashtable errorList) {

    if (debug > 1) {
      System.out.println("Delete:" + path);
    }

    if ((path.toUpperCase().startsWith("/WEB-INF")) ||
        (path.toUpperCase().startsWith("/META-INF"))) {
      errorList.put(path, new Integer(WebdavStatus.SC_FORBIDDEN));
      return;
    }

    String ifHeader = req.getHeader("If");
    if (ifHeader == null) {
      ifHeader = "";
    }

    String lockTokenHeader = req.getHeader("Lock-Token");
    if (lockTokenHeader == null) {
      lockTokenHeader = "";
    }

    Enumeration enum1 = null;
    try {
      enum1 = resources.list(path);
    } catch (NamingException e) {
      errorList.put(path, new Integer
          (WebdavStatus.SC_INTERNAL_SERVER_ERROR));
      return;
    }

    while (enum1.hasMoreElements()) {
      NameClassPair ncPair = (NameClassPair) enum1.nextElement();
      String childName = path;
      if (!childName.equals("/")) {
        childName += "/";
      }
      childName += ncPair.getName();

      if (isLocked(childName, ifHeader + lockTokenHeader)) {

        errorList.put(childName, new Integer(WebdavStatus.SC_LOCKED));

      } else {

        try {
          Object object = resources.lookup(childName);
          if (object instanceof DirContext) {
            deleteCollection(req, resources, childName, errorList);
          }

          try {
            resources.unbind(childName);
          } catch (NamingException e) {
            if (!(object instanceof DirContext)) {
              // If it's not a collection, then it's an unknown
              // error
              errorList.put
                  (childName, new Integer
                  (WebdavStatus.SC_INTERNAL_SERVER_ERROR));
            }
          }
        } catch (NamingException e) {
          errorList.put
              (childName, new Integer
              (WebdavStatus.SC_INTERNAL_SERVER_ERROR));
        }
      }

    }

  }


  /**
   *  Send a multistatus element containing a complete error report to the
   *  client.
   *
   *@param  req                   Servlet request
   *@param  resp                  Servlet response
   *@param  errorList             List of error to be displayed
   *@exception  ServletException  Description of the Exception
   *@exception  IOException       Description of the Exception
   */
  private void sendReport(HttpServletRequest req, HttpServletResponse resp,
      Hashtable errorList)
       throws ServletException, IOException {

    resp.setStatus(WebdavStatus.SC_MULTI_STATUS);

    String absoluteUri = req.getRequestURI();
    String relativePath = getRelativePath(req);

    XMLWriter generatedXML = new XMLWriter();
    generatedXML.writeXMLHeader();

    generatedXML.writeElement(null, "multistatus"
         + generateNamespaceDeclarations(),
        XMLWriter.OPENING);

    Enumeration pathList = errorList.keys();
    while (pathList.hasMoreElements()) {

      String errorPath = (String) pathList.nextElement();
      int errorCode = ((Integer) errorList.get(errorPath)).intValue();

      generatedXML.writeElement(null, "response", XMLWriter.OPENING);

      generatedXML.writeElement(null, "href", XMLWriter.OPENING);
      String toAppend = errorPath.substring(relativePath.length());
      if (!toAppend.startsWith("/")) {
        toAppend = "/" + toAppend;
      }
      generatedXML.writeText(absoluteUri + toAppend);
      generatedXML.writeElement(null, "href", XMLWriter.CLOSING);
      generatedXML.writeElement(null, "status", XMLWriter.OPENING);
      generatedXML.writeText("HTTP/1.1 " + errorCode + " "
           + WebdavStatus.getStatusText(errorCode));
      generatedXML.writeElement(null, "status", XMLWriter.CLOSING);

      generatedXML.writeElement(null, "response", XMLWriter.CLOSING);

    }

    generatedXML.writeElement(null, "multistatus", XMLWriter.CLOSING);

    Writer writer = resp.getWriter();
    writer.write(generatedXML.toString());
    writer.close();

  }


  /**
   *  Propfind helper method.
   *
   *@param  resources         Resources object associated with this context
   *@param  generatedXML      XML response to the Propfind request
   *@param  path              Path of the current resource
   *@param  type              Propfind type
   *@param  propertiesVector  If the propfind type is find properties by name,
   *      then this Vector contains those properties
   *@param  context           Description of the Parameter
   *@exception  IOException   Description of the Exception
   */
  private void parseProperties(ActionContext context, ModuleContext resources,
      XMLWriter generatedXML,
      String path, int type,
      Vector propertiesVector) throws IOException {

    // Exclude any resource in the /WEB-INF and /META-INF subdirectories
    // (the "toUpperCase()" avoids problems on Windows systems)
    if (path.toUpperCase().startsWith("/WEB-INF") ||
        path.toUpperCase().startsWith("/META-INF")) {
      return;
    }

    Connection db = null;
    ResourceInfo resourceInfo = null;
    SystemStatus thisSystem = null;
    boolean actionStatus = true;
    try {
      db = this.getConnection(context);
      thisSystem = this.getSystemStatus(context);
      resourceInfo = new ResourceInfo(thisSystem, db, path, resources);
    } catch (SQLException e) {
      e.printStackTrace(System.out);
      context.getResponse().sendError(CFS_SQLERROR, e.getMessage());
      actionStatus = false;
    } catch (FileNotFoundException e) {
      e.printStackTrace(System.out);
      actionStatus = false;
    } finally {
      this.freeConnection(db, context);
    }

    if (!actionStatus) {
      return;
    }

    generatedXML.writeElement(null, "response", XMLWriter.OPENING);
    String status = new String("HTTP/1.1 " + WebdavStatus.SC_OK + " "
         + WebdavStatus.getStatusText
        (WebdavStatus.SC_OK));

    // Generating href element
    generatedXML.writeElement(null, "href", XMLWriter.OPENING);

    String href = context.getRequest().getContextPath();
    if ((href.endsWith("/")) && (path.startsWith("/"))) {
      href += path.substring(1);
    } else {
      href += path;
    }
    if ((resourceInfo.collection) && (!href.endsWith("/"))) {
      href += "/";
    }

    generatedXML.writeText(rewriteUrl(href));
    generatedXML.writeElement(null, "href", XMLWriter.CLOSING);

    String resourceName = path;
    int lastSlash = path.lastIndexOf('/');
    if (lastSlash != -1) {
      resourceName = resourceName.substring(lastSlash + 1);
    }

    switch (type) {

      case FIND_ALL_PROP:

        generatedXML.writeElement(null, "propstat", XMLWriter.OPENING);
        generatedXML.writeElement(null, "prop", XMLWriter.OPENING);
        generatedXML.writeProperty(null, "creationdate", getISOCreationDate(resourceInfo.creationDate));
        generatedXML.writeElement(null, "displayname", XMLWriter.OPENING);
        generatedXML.writeData(resourceName);
        generatedXML.writeElement(null, "displayname", XMLWriter.CLOSING);
        if (!resourceInfo.collection) {
          generatedXML.writeProperty(null, "getlastmodified", resourceInfo.httpDate);
          generatedXML.writeProperty(null, "getcontentlength", String.valueOf(resourceInfo.length));
          String contentType = getServletContext().getMimeType(resourceInfo.path);
          if (contentType != null) {
            generatedXML.writeProperty(null, "getcontenttype", contentType);
          }
          generatedXML.writeProperty(null, "getetag", getETag(resourceInfo));
          generatedXML.writeElement(null, "resourcetype", XMLWriter.NO_CONTENT);
        } else {
          generatedXML.writeElement(null, "resourcetype", XMLWriter.OPENING);
          generatedXML.writeElement(null, "collection", XMLWriter.NO_CONTENT);
          generatedXML.writeElement(null, "resourcetype", XMLWriter.CLOSING);
        }

        generatedXML.writeProperty(null, "source", "");

        String supportedLocks = "<lockentry>"
             + "<lockscope><exclusive/></lockscope>"
             + "<locktype><write/></locktype>"
             + "</lockentry>" + "<lockentry>"
             + "<lockscope><shared/></lockscope>"
             + "<locktype><write/></locktype>"
             + "</lockentry>";
        generatedXML.writeElement(null, "supportedlock", XMLWriter.OPENING);
        generatedXML.writeText(supportedLocks);
        generatedXML.writeElement(null, "supportedlock", XMLWriter.CLOSING);

        generateLockDiscovery(path, generatedXML);

        generatedXML.writeElement(null, "prop", XMLWriter.CLOSING);
        generatedXML.writeElement(null, "status", XMLWriter.OPENING);
        generatedXML.writeText(status);
        generatedXML.writeElement(null, "status", XMLWriter.CLOSING);
        generatedXML.writeElement(null, "propstat", XMLWriter.CLOSING);

        break;
      case FIND_PROPERTY_NAMES:
        generatedXML.writeElement(null, "propstat", XMLWriter.OPENING);
        generatedXML.writeElement(null, "prop", XMLWriter.OPENING);
        generatedXML.writeElement(null, "creationdate", XMLWriter.NO_CONTENT);
        generatedXML.writeElement(null, "displayname", XMLWriter.NO_CONTENT);
        if (!resourceInfo.collection) {
          generatedXML.writeElement(null, "getcontentlanguage", XMLWriter.NO_CONTENT);
          generatedXML.writeElement(null, "getcontentlength", XMLWriter.NO_CONTENT);
          generatedXML.writeElement(null, "getcontenttype", XMLWriter.NO_CONTENT);
          generatedXML.writeElement(null, "getetag", XMLWriter.NO_CONTENT);
          generatedXML.writeElement(null, "getlastmodified", XMLWriter.NO_CONTENT);
        }
        generatedXML.writeElement(null, "resourcetype", XMLWriter.NO_CONTENT);
        generatedXML.writeElement(null, "source", XMLWriter.NO_CONTENT);
        generatedXML.writeElement(null, "lockdiscovery", XMLWriter.NO_CONTENT);
        generatedXML.writeElement(null, "prop", XMLWriter.CLOSING);
        generatedXML.writeElement(null, "status", XMLWriter.OPENING);
        generatedXML.writeText(status);
        generatedXML.writeElement(null, "status", XMLWriter.CLOSING);
        generatedXML.writeElement(null, "propstat", XMLWriter.CLOSING);
        break;
      case FIND_BY_PROPERTY:
        Vector propertiesNotFound = new Vector();
        // Parse the list of properties
        generatedXML.writeElement(null, "propstat", XMLWriter.OPENING);
        generatedXML.writeElement(null, "prop", XMLWriter.OPENING);
        Enumeration properties = propertiesVector.elements();
        while (properties.hasMoreElements()) {
          String property = (String) properties.nextElement();
          if (property.equals("creationdate")) {
            generatedXML.writeProperty(null, "creationdate", getISOCreationDate(resourceInfo.creationDate));
          } else if (property.equals("displayname")) {
            generatedXML.writeElement(null, "displayname", XMLWriter.OPENING);
            generatedXML.writeData(resourceName);
            generatedXML.writeElement(null, "displayname", XMLWriter.CLOSING);
          } else if (property.equals("getcontentlanguage")) {
            if (resourceInfo.collection) {
              propertiesNotFound.addElement(property);
            } else {
              generatedXML.writeElement(null, "getcontentlanguage", XMLWriter.NO_CONTENT);
            }
          } else if (property.equals("getcontentlength")) {
            if (resourceInfo.collection) {
              propertiesNotFound.addElement(property);
            } else {
              generatedXML.writeProperty(null, "getcontentlength", (String.valueOf(resourceInfo.length)));
            }
          } else if (property.equals("getcontenttype")) {
            if (resourceInfo.collection) {
              propertiesNotFound.addElement(property);
            } else {
              generatedXML.writeProperty(null, "getcontenttype", getServletContext().getMimeType(resourceInfo.path));
            }
          } else if (property.equals("getetag")) {
            if (resourceInfo.collection) {
              propertiesNotFound.addElement(property);
            } else {
              generatedXML.writeProperty(null, "getetag", getETag(resourceInfo));
            }
          } else if (property.equals("getlastmodified")) {
            if (resourceInfo.collection) {
              //propertiesNotFound.addElement(property);
              // change
              generatedXML.writeProperty(null, "getlastmodified", resourceInfo.httpDate);
            } else {
              generatedXML.writeProperty(null, "getlastmodified", resourceInfo.httpDate);
            }
          } else if (property.equals("resourcetype")) {
            if (resourceInfo.collection) {
              generatedXML.writeElement(null, "resourcetype", XMLWriter.OPENING);
              generatedXML.writeElement(null, "collection", XMLWriter.NO_CONTENT);
              generatedXML.writeElement(null, "resourcetype", XMLWriter.CLOSING);
            } else {
              generatedXML.writeElement(null, "resourcetype", XMLWriter.NO_CONTENT);
            }
          } else if (property.equals("source")) {
            generatedXML.writeProperty(null, "source", "");
          } else if (property.equals("supportedlock")) {
            supportedLocks = "<lockentry>"
                 + "<lockscope><exclusive/></lockscope>"
                 + "<locktype><write/></locktype>"
                 + "</lockentry>" + "<lockentry>"
                 + "<lockscope><shared/></lockscope>"
                 + "<locktype><write/></locktype>"
                 + "</lockentry>";
            generatedXML.writeElement(null, "supportedlock", XMLWriter.OPENING);
            generatedXML.writeText(supportedLocks);
            generatedXML.writeElement(null, "supportedlock", XMLWriter.CLOSING);
          } else if (property.equals("lockdiscovery")) {
            if (!generateLockDiscovery(path, generatedXML)) {
              propertiesNotFound.addElement(property);
            }
          } else {
            propertiesNotFound.addElement(property);
          }
        }
        generatedXML.writeElement(null, "prop", XMLWriter.CLOSING);
        generatedXML.writeElement(null, "status", XMLWriter.OPENING);
        generatedXML.writeText(status);
        generatedXML.writeElement(null, "status", XMLWriter.CLOSING);
        generatedXML.writeElement(null, "propstat", XMLWriter.CLOSING);
        Enumeration propertiesNotFoundList = propertiesNotFound.elements();
        if (propertiesNotFoundList.hasMoreElements()) {
          status = new String("HTTP/1.1 " + WebdavStatus.SC_NOT_FOUND
               + " " + WebdavStatus.getStatusText
              (WebdavStatus.SC_NOT_FOUND));
          generatedXML.writeElement(null, "propstat", XMLWriter.OPENING);
          generatedXML.writeElement(null, "prop", XMLWriter.OPENING);
          while (propertiesNotFoundList.hasMoreElements()) {
            generatedXML.writeElement(null, (String) propertiesNotFoundList.nextElement(), XMLWriter.NO_CONTENT);
          }
          generatedXML.writeElement(null, "prop", XMLWriter.CLOSING);
          generatedXML.writeElement(null, "status", XMLWriter.OPENING);
          generatedXML.writeText(status);
          generatedXML.writeElement(null, "status", XMLWriter.CLOSING);
          generatedXML.writeElement(null, "propstat", XMLWriter.CLOSING);
        }
        break;
    }
    generatedXML.writeElement(null, "response", XMLWriter.CLOSING);
  }


  /**
   *  If a directory does not have files or folders under it then this
   *  method generates the necessary xml
   *
   *@param  context           Description of the Parameter
   *@param  resources         Description of the Parameter
   *@param  generatedXML      Description of the Parameter
   *@param  propertiesVector  Description of the Parameter
   *@exception  IOException   Description of the Exception
   */
  private void parseProperties(ActionContext context, ModuleContext resources,
      XMLWriter generatedXML, Vector propertiesVector) throws IOException {

    generatedXML.writeElement(null, "response", XMLWriter.OPENING);
    String status = new String("HTTP/1.1 " + WebdavStatus.SC_NOT_FOUND
         + " " + WebdavStatus.getStatusText
        (WebdavStatus.SC_NOT_FOUND));

    generatedXML.writeElement(null, "propstat", XMLWriter.OPENING);
    generatedXML.writeElement(null, "prop", XMLWriter.OPENING);
    generatedXML.writeElement(null, "prop", XMLWriter.CLOSING);
    generatedXML.writeElement(null, "status", XMLWriter.OPENING);
    generatedXML.writeText(status);
    generatedXML.writeElement(null, "status", XMLWriter.CLOSING);
    generatedXML.writeElement(null, "propstat", XMLWriter.CLOSING);
    generatedXML.writeElement(null, "response", XMLWriter.CLOSING);
  }


  /**
   *  Propfind helper method. Dispays the properties of a lock-null resource.
   *
   *@param  generatedXML      XML response to the Propfind request
   *@param  path              Path of the current resource
   *@param  type              Propfind type
   *@param  propertiesVector  If the propfind type is find properties by name,
   *      then this Vector contains those properties
   *@param  req               Description of the Parameter
   */
  private void parseLockNullProperties(HttpServletRequest req,
      XMLWriter generatedXML,
      String path, int type,
      Vector propertiesVector) {

    // Exclude any resource in the /WEB-INF and /META-INF subdirectories
    // (the "toUpperCase()" avoids problems on Windows systems)
    if (path.toUpperCase().startsWith("/WEB-INF") ||
        path.toUpperCase().startsWith("/META-INF")) {
      return;
    }

    // Retrieving the lock associated with the lock-null resource
    LockInfo lock = (LockInfo) resourceLocks.get(path);

    if (lock == null) {
      return;
    }

    generatedXML.writeElement(null, "response", XMLWriter.OPENING);
    String status = new String("HTTP/1.1 " + WebdavStatus.SC_OK + " "
         + WebdavStatus.getStatusText
        (WebdavStatus.SC_OK));

    // Generating href element
    generatedXML.writeElement(null, "href", XMLWriter.OPENING);

    String absoluteUri = req.getRequestURI();
    String relativePath = getRelativePath(req);
    String toAppend = path.substring(relativePath.length());
    if (!toAppend.startsWith("/")) {
      toAppend = "/" + toAppend;
    }

    generatedXML.writeText(rewriteUrl(normalize(absoluteUri + toAppend)));

    generatedXML.writeElement(null, "href", XMLWriter.CLOSING);

    String resourceName = path;
    //System.out.println("Resource Name: " + resourceName);
    int lastSlash = path.lastIndexOf('/');
    if (lastSlash != -1) {
      resourceName = resourceName.substring(lastSlash + 1);
    }

    switch (type) {

      case FIND_ALL_PROP:

        generatedXML.writeElement(null, "propstat", XMLWriter.OPENING);
        generatedXML.writeElement(null, "prop", XMLWriter.OPENING);

        generatedXML.writeProperty
            (null, "creationdate",
            getISOCreationDate(lock.creationDate.getTime()));
        generatedXML.writeElement
            (null, "displayname", XMLWriter.OPENING);
        generatedXML.writeData(resourceName);
        generatedXML.writeElement
            (null, "displayname", XMLWriter.CLOSING);
        generatedXML.writeProperty(null, "getlastmodified",
            FastHttpDateFormat.formatDate
            (lock.creationDate.getTime(), null));
        generatedXML.writeProperty
            (null, "getcontentlength", String.valueOf(0));
        generatedXML.writeProperty(null, "getcontenttype", "");
        generatedXML.writeProperty(null, "getetag", "");
        generatedXML.writeElement(null, "resourcetype",
            XMLWriter.OPENING);
        generatedXML.writeElement(null, "lock-null", XMLWriter.NO_CONTENT);
        generatedXML.writeElement(null, "resourcetype",
            XMLWriter.CLOSING);

        generatedXML.writeProperty(null, "source", "");

        String supportedLocks = "<lockentry>"
             + "<lockscope><exclusive/></lockscope>"
             + "<locktype><write/></locktype>"
             + "</lockentry>" + "<lockentry>"
             + "<lockscope><shared/></lockscope>"
             + "<locktype><write/></locktype>"
             + "</lockentry>";
        generatedXML.writeElement(null, "supportedlock",
            XMLWriter.OPENING);
        generatedXML.writeText(supportedLocks);
        generatedXML.writeElement(null, "supportedlock",
            XMLWriter.CLOSING);

        generateLockDiscovery(path, generatedXML);

        generatedXML.writeElement(null, "prop", XMLWriter.CLOSING);
        generatedXML.writeElement(null, "status", XMLWriter.OPENING);
        generatedXML.writeText(status);
        generatedXML.writeElement(null, "status", XMLWriter.CLOSING);
        generatedXML.writeElement(null, "propstat", XMLWriter.CLOSING);

        break;
      case FIND_PROPERTY_NAMES:

        generatedXML.writeElement(null, "propstat", XMLWriter.OPENING);
        generatedXML.writeElement(null, "prop", XMLWriter.OPENING);

        generatedXML.writeElement(null, "creationdate",
            XMLWriter.NO_CONTENT);
        generatedXML.writeElement(null, "displayname",
            XMLWriter.NO_CONTENT);
        generatedXML.writeElement(null, "getcontentlanguage",
            XMLWriter.NO_CONTENT);
        generatedXML.writeElement(null, "getcontentlength",
            XMLWriter.NO_CONTENT);
        generatedXML.writeElement(null, "getcontenttype",
            XMLWriter.NO_CONTENT);
        generatedXML.writeElement(null, "getetag",
            XMLWriter.NO_CONTENT);
        generatedXML.writeElement(null, "getlastmodified",
            XMLWriter.NO_CONTENT);
        generatedXML.writeElement(null, "resourcetype",
            XMLWriter.NO_CONTENT);
        generatedXML.writeElement(null, "source",
            XMLWriter.NO_CONTENT);
        generatedXML.writeElement(null, "lockdiscovery",
            XMLWriter.NO_CONTENT);

        generatedXML.writeElement(null, "prop", XMLWriter.CLOSING);
        generatedXML.writeElement(null, "status", XMLWriter.OPENING);
        generatedXML.writeText(status);
        generatedXML.writeElement(null, "status", XMLWriter.CLOSING);
        generatedXML.writeElement(null, "propstat", XMLWriter.CLOSING);

        break;
      case FIND_BY_PROPERTY:

        Vector propertiesNotFound = new Vector();

        // Parse the list of properties

        generatedXML.writeElement(null, "propstat", XMLWriter.OPENING);
        generatedXML.writeElement(null, "prop", XMLWriter.OPENING);

        Enumeration properties = propertiesVector.elements();

        while (properties.hasMoreElements()) {

          String property = (String) properties.nextElement();

          if (property.equals("creationdate")) {
            generatedXML.writeProperty
                (null, "creationdate",
                getISOCreationDate(lock.creationDate.getTime()));
          } else if (property.equals("displayname")) {
            generatedXML.writeElement
                (null, "displayname", XMLWriter.OPENING);
            generatedXML.writeData(resourceName);
            generatedXML.writeElement
                (null, "displayname", XMLWriter.CLOSING);
          } else if (property.equals("getcontentlanguage")) {
            generatedXML.writeElement(null, "getcontentlanguage",
                XMLWriter.NO_CONTENT);
          } else if (property.equals("getcontentlength")) {
            generatedXML.writeProperty
                (null, "getcontentlength", (String.valueOf(0)));
          } else if (property.equals("getcontenttype")) {
            generatedXML.writeProperty
                (null, "getcontenttype", "");
          } else if (property.equals("getetag")) {
            generatedXML.writeProperty(null, "getetag", "");
          } else if (property.equals("getlastmodified")) {
            generatedXML.writeProperty
                (null, "getlastmodified",
                FastHttpDateFormat.formatDate
                (lock.creationDate.getTime(), null));
          } else if (property.equals("resourcetype")) {
            generatedXML.writeElement(null, "resourcetype",
                XMLWriter.OPENING);
            generatedXML.writeElement(null, "lock-null",
                XMLWriter.NO_CONTENT);
            generatedXML.writeElement(null, "resourcetype",
                XMLWriter.CLOSING);
          } else if (property.equals("source")) {
            generatedXML.writeProperty(null, "source", "");
          } else if (property.equals("supportedlock")) {
            supportedLocks = "<lockentry>"
                 + "<lockscope><exclusive/></lockscope>"
                 + "<locktype><write/></locktype>"
                 + "</lockentry>" + "<lockentry>"
                 + "<lockscope><shared/></lockscope>"
                 + "<locktype><write/></locktype>"
                 + "</lockentry>";
            generatedXML.writeElement(null, "supportedlock",
                XMLWriter.OPENING);
            generatedXML.writeText(supportedLocks);
            generatedXML.writeElement(null, "supportedlock",
                XMLWriter.CLOSING);
          } else if (property.equals("lockdiscovery")) {
            if (!generateLockDiscovery(path, generatedXML)) {
              propertiesNotFound.addElement(property);
            }
          } else {
            propertiesNotFound.addElement(property);
          }

        }

        generatedXML.writeElement(null, "prop", XMLWriter.CLOSING);
        generatedXML.writeElement(null, "status", XMLWriter.OPENING);
        generatedXML.writeText(status);
        generatedXML.writeElement(null, "status", XMLWriter.CLOSING);
        generatedXML.writeElement(null, "propstat", XMLWriter.CLOSING);

        Enumeration propertiesNotFoundList = propertiesNotFound.elements();

        if (propertiesNotFoundList.hasMoreElements()) {

          status = new String("HTTP/1.1 " + WebdavStatus.SC_NOT_FOUND
               + " " + WebdavStatus.getStatusText
              (WebdavStatus.SC_NOT_FOUND));

          generatedXML.writeElement(null, "propstat", XMLWriter.OPENING);
          generatedXML.writeElement(null, "prop", XMLWriter.OPENING);

          while (propertiesNotFoundList.hasMoreElements()) {
            generatedXML.writeElement
                (null, (String) propertiesNotFoundList.nextElement(),
                XMLWriter.NO_CONTENT);
          }

          generatedXML.writeElement(null, "prop", XMLWriter.CLOSING);
          generatedXML.writeElement(null, "status", XMLWriter.OPENING);
          generatedXML.writeText(status);
          generatedXML.writeElement(null, "status", XMLWriter.CLOSING);
          generatedXML.writeElement(null, "propstat", XMLWriter.CLOSING);

        }

        break;
    }

    generatedXML.writeElement(null, "response", XMLWriter.CLOSING);

  }


  /**
   *  Print the lock discovery information associated with a path.
   *
   *@param  path          Path
   *@param  generatedXML  XML data to which the locks info will be appended
   *@return               true if at least one lock was displayed
   */
  private boolean generateLockDiscovery
      (String path, XMLWriter generatedXML) {

    LockInfo resourceLock = (LockInfo) resourceLocks.get(path);
    Enumeration collectionLocksList = collectionLocks.elements();

    boolean wroteStart = false;

    if (resourceLock != null) {
      wroteStart = true;
      generatedXML.writeElement(null, "lockdiscovery",
          XMLWriter.OPENING);
      resourceLock.toXML(generatedXML);
    }

    while (collectionLocksList.hasMoreElements()) {
      LockInfo currentLock =
          (LockInfo) collectionLocksList.nextElement();
      if (path.startsWith(currentLock.path)) {
        if (!wroteStart) {
          wroteStart = true;
          generatedXML.writeElement(null, "lockdiscovery",
              XMLWriter.OPENING);
        }
        currentLock.toXML(generatedXML);
      }
    }

    if (wroteStart) {
      generatedXML.writeElement(null, "lockdiscovery",
          XMLWriter.CLOSING);
    } else {
      return false;
    }

    return true;
  }


  /**
   *  Get creation date in ISO format.
   *
   *@param  creationDate  Description of the Parameter
   *@return               The iSOCreationDate value
   */
  private String getISOCreationDate(long creationDate) {
    StringBuffer creationDateValue = new StringBuffer
        (creationDateFormat.format
        (new Date(creationDate)));
    /*
     *  int offset = Calendar.getInstance().getTimeZone().getRawOffset()
     *  3600000; // FIXME ?
     *  if (offset < 0) {
     *  creationDateValue.append("-");
     *  offset = -offset;
     *  } else if (offset > 0) {
     *  creationDateValue.append("+");
     *  }
     *  if (offset != 0) {
     *  if (offset < 10)
     *  creationDateValue.append("0");
     *  creationDateValue.append(offset + ":00");
     *  } else {
     *  creationDateValue.append("Z");
     *  }
     */
    return creationDateValue.toString();
  }


  /**
   *  Determines the methods normally allowed for the resource.
   *
   *@param  resources  Description of the Parameter
   *@param  req        Description of the Parameter
   *@return            Description of the Return Value
   */
  private StringBuffer determineMethodsAllowed(DirContext resources,
      HttpServletRequest req) {

    StringBuffer methodsAllowed = new StringBuffer();
    boolean exists = true;
    Object object = null;
    try {
      String path = getRelativePath(req);

      object = resources.lookup(path);
    } catch (NamingException e) {
      exists = false;
    }

    if (!exists) {
      methodsAllowed.append("OPTIONS, MKCOL, PUT, LOCK");
      return methodsAllowed;
    }

    methodsAllowed.append("OPTIONS, GET, HEAD, POST, DELETE, TRACE");
    methodsAllowed.append(", PROPPATCH, COPY, MOVE, LOCK, UNLOCK");

    //if (listings) {
    methodsAllowed.append(", PROPFIND");
    //}

    if (!(object instanceof DirContext)) {
      methodsAllowed.append(", PUT");
    }

    return methodsAllowed;
  }


  // --------------------------------------------------  LockInfo Inner Class

  /**
   *  Holds a lock information.
   *
   *@author     matt rajkowski
   *@created    July 12, 2004
   *@version    $Id: WebdavServlet.java,v 1.1.2.2 2004/11/17 23:25:29 ananth Exp
   *      $
   */
  private class LockInfo {

    // -------------------------------------------------------- Constructor

    /**
     *  Constructor.
     */
    public LockInfo() { }


    // ------------------------------------------------- Instance Variables


    String path = "/";
    String type = "write";
    String scope = "exclusive";
    int depth = 0;
    String owner = "";
    Vector tokens = new Vector();
    long expiresAt = 0;
    Date creationDate = new Date();


    // ----------------------------------------------------- Public Methods


    /**
     *  Get a String representation of this lock token.
     *
     *@return    Description of the Return Value
     */
    public String toString() {

      String result = "Type:" + type + "\n";
      result += "Scope:" + scope + "\n";
      result += "Depth:" + depth + "\n";
      result += "Owner:" + owner + "\n";
      result += "Expiration:"
           + FastHttpDateFormat.formatDate(expiresAt, null) + "\n";
      Enumeration tokensList = tokens.elements();
      while (tokensList.hasMoreElements()) {
        result += "Token:" + tokensList.nextElement() + "\n";
      }
      return result;
    }


    /**
     *  Return true if the lock has expired.
     *
     *@return    Description of the Return Value
     */
    public boolean hasExpired() {
      return (System.currentTimeMillis() > expiresAt);
    }


    /**
     *  Return true if the lock is exclusive.
     *
     *@return    The exclusive value
     */
    public boolean isExclusive() {

      return (scope.equals("exclusive"));
    }


    /**
     *  Get an XML representation of this lock token. This method will append an
     *  XML fragment to the given XML writer.
     *
     *@param  generatedXML  Description of the Parameter
     */
    public void toXML(XMLWriter generatedXML) {

      generatedXML.writeElement(null, "activelock", XMLWriter.OPENING);

      generatedXML.writeElement(null, "locktype", XMLWriter.OPENING);
      generatedXML.writeElement(null, type, XMLWriter.NO_CONTENT);
      generatedXML.writeElement(null, "locktype", XMLWriter.CLOSING);

      generatedXML.writeElement(null, "lockscope", XMLWriter.OPENING);
      generatedXML.writeElement(null, scope, XMLWriter.NO_CONTENT);
      generatedXML.writeElement(null, "lockscope", XMLWriter.CLOSING);

      generatedXML.writeElement(null, "depth", XMLWriter.OPENING);
      if (depth == INFINITY) {
        generatedXML.writeText("Infinity");
      } else {
        generatedXML.writeText("0");
      }
      generatedXML.writeElement(null, "depth", XMLWriter.CLOSING);

      generatedXML.writeElement(null, "owner", XMLWriter.OPENING);
      generatedXML.writeText(owner);
      generatedXML.writeElement(null, "owner", XMLWriter.CLOSING);

      generatedXML.writeElement(null, "timeout", XMLWriter.OPENING);
      long timeout = (expiresAt - System.currentTimeMillis()) / 1000;
      generatedXML.writeText("Second-" + timeout);
      generatedXML.writeElement(null, "timeout", XMLWriter.CLOSING);

      generatedXML.writeElement(null, "locktoken", XMLWriter.OPENING);
      Enumeration tokensList = tokens.elements();
      while (tokensList.hasMoreElements()) {
        generatedXML.writeElement(null, "href", XMLWriter.OPENING);
        generatedXML.writeText("opaquelocktoken:"
             + tokensList.nextElement());
        generatedXML.writeElement(null, "href", XMLWriter.CLOSING);
      }
      generatedXML.writeElement(null, "locktoken", XMLWriter.CLOSING);

      generatedXML.writeElement(null, "activelock", XMLWriter.CLOSING);

    }

  }


  // --------------------------------------------------- Property Inner Class


  /**
   *  Description of the Class
   *
   *@author     matt rajkowski
   *@created    July 12, 2004
   *@version    $Id: WebdavServlet.java,v 1.1.2.2 2004/11/17 23:25:29 ananth Exp
   *      $
   */
  private class Property {

    public String name;
    public String value;
    public String namespace;
    public String namespaceAbbrev;
    public int status = WebdavStatus.SC_OK;

  }

}

// --------------------------------------------------------  WebdavStatus Class

/**
 *  Wraps the HttpServletResponse class to abstract the specific protocol used.
 *  To support other protocols we would only need to modify this class and the
 *  WebDavRetCode classes.
 *
 *@author     Marc Eaddy
 *@created    July 12, 2004
 *@version    1.0, 16 Nov 1997
 */
class WebdavStatus {


  // ----------------------------------------------------- Instance Variables


  /**
   *  This Hashtable contains the mapping of HTTP and WebDAV status codes to
   *  descriptive text. This is a static variable.
   */
  private static Hashtable mapStatusCodes = new Hashtable();

  // ------------------------------------------------------ HTTP Status Codes

  /**
   *  Status code (200) indicating the request succeeded normally.
   */
  public final static int SC_OK = HttpServletResponse.SC_OK;

  /**
   *  Status code (201) indicating the request succeeded and created a new
   *  resource on the server.
   */
  public final static int SC_CREATED = HttpServletResponse.SC_CREATED;

  /**
   *  Status code (202) indicating that a request was accepted for processing,
   *  but was not completed.
   */
  public final static int SC_ACCEPTED = HttpServletResponse.SC_ACCEPTED;

  /**
   *  Status code (204) indicating that the request succeeded but that there was
   *  no new information to return.
   */
  public final static int SC_NO_CONTENT = HttpServletResponse.SC_NO_CONTENT;

  /**
   *  Status code (301) indicating that the resource has permanently moved to a
   *  new location, and that future references should use a new URI with their
   *  requests.
   */
  public final static int SC_MOVED_PERMANENTLY =
      HttpServletResponse.SC_MOVED_PERMANENTLY;

  /**
   *  Status code (302) indicating that the resource has temporarily moved to
   *  another location, but that future references should still use the original
   *  URI to access the resource.
   */
  public final static int SC_MOVED_TEMPORARILY =
      HttpServletResponse.SC_MOVED_TEMPORARILY;

  /**
   *  Status code (304) indicating that a conditional GET operation found that
   *  the resource was available and not modified.
   */
  public final static int SC_NOT_MODIFIED =
      HttpServletResponse.SC_NOT_MODIFIED;

  /**
   *  Status code (400) indicating the request sent by the client was
   *  syntactically incorrect.
   */
  public final static int SC_BAD_REQUEST =
      HttpServletResponse.SC_BAD_REQUEST;

  /**
   *  Status code (401) indicating that the request requires HTTP
   *  authentication.
   */
  public final static int SC_UNAUTHORIZED =
      HttpServletResponse.SC_UNAUTHORIZED;

  /**
   *  Status code (403) indicating the server understood the request but refused
   *  to fulfill it.
   */
  public final static int SC_FORBIDDEN = HttpServletResponse.SC_FORBIDDEN;

  /**
   *  Status code (404) indicating that the requested resource is not available.
   */
  public final static int SC_NOT_FOUND = HttpServletResponse.SC_NOT_FOUND;

  /**
   *  Status code (500) indicating an error inside the HTTP service which
   *  prevented it from fulfilling the request.
   */
  public final static int SC_INTERNAL_SERVER_ERROR =
      HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

  /**
   *  Status code (501) indicating the HTTP service does not support the
   *  functionality needed to fulfill the request.
   */
  public final static int SC_NOT_IMPLEMENTED =
      HttpServletResponse.SC_NOT_IMPLEMENTED;

  /**
   *  Status code (502) indicating that the HTTP server received an invalid
   *  response from a server it consulted when acting as a proxy or gateway.
   */
  public final static int SC_BAD_GATEWAY =
      HttpServletResponse.SC_BAD_GATEWAY;

  /**
   *  Status code (503) indicating that the HTTP service is temporarily
   *  overloaded, and unable to handle the request.
   */
  public final static int SC_SERVICE_UNAVAILABLE =
      HttpServletResponse.SC_SERVICE_UNAVAILABLE;

  /**
   *  Status code (100) indicating the client may continue with its request.
   *  This interim response is used to inform the client that the initial part
   *  of the request has been received and has not yet been rejected by the
   *  server.
   */
  public final static int SC_CONTINUE = 100;

  /**
   *  Status code (405) indicating the method specified is not allowed for the
   *  resource.
   */
  public final static int SC_METHOD_NOT_ALLOWED = 405;

  /**
   *  Status code (409) indicating that the request could not be completed due
   *  to a conflict with the current state of the resource.
   */
  public final static int SC_CONFLICT = 409;

  /**
   *  Status code (412) indicating the precondition given in one or more of the
   *  request-header fields evaluated to false when it was tested on the server.
   */
  public final static int SC_PRECONDITION_FAILED = 412;

  /**
   *  Status code (413) indicating the server is refusing to process a request
   *  because the request entity is larger than the server is willing or able to
   *  process.
   */
  public final static int SC_REQUEST_TOO_LONG = 413;

  /**
   *  Status code (415) indicating the server is refusing to service the request
   *  because the entity of the request is in a format not supported by the
   *  requested resource for the requested method.
   */
  public final static int SC_UNSUPPORTED_MEDIA_TYPE = 415;

  // -------------------------------------------- Extended WebDav status code

  /**
   *  Status code (207) indicating that the response requires providing status
   *  for multiple independent operations.
   */
  public final static int SC_MULTI_STATUS = 207;
  // This one colides with HTTP 1.1
  // "207 Parital Update OK"


  /**
   *  Status code (418) indicating the entity body submitted with the PATCH
   *  method was not understood by the resource.
   */
  public final static int SC_UNPROCESSABLE_ENTITY = 418;
  // This one colides with HTTP 1.1
  // "418 Reauthentication Required"


  /**
   *  Status code (419) indicating that the resource does not have sufficient
   *  space to record the state of the resource after the execution of this
   *  method.
   */
  public final static int SC_INSUFFICIENT_SPACE_ON_RESOURCE = 419;
  // This one colides with HTTP 1.1
  // "419 Proxy Reauthentication Required"


  /**
   *  Status code (420) indicating the method was not executed on a particular
   *  resource within its scope because some part of the method's execution
   *  failed causing the entire method to be aborted.
   */
  public final static int SC_METHOD_FAILURE = 420;

  /**
   *  Status code (423) indicating the destination resource of a method is
   *  locked, and either the request did not contain a valid Lock-Info header,
   *  or the Lock-Info header identifies a lock held by another principal.
   */
  public final static int SC_LOCKED = 423;

  // ------------------------------------------------------------ Initializer

  static {
    // HTTP 1.0 tatus Code
    addStatusCodeMap(SC_OK, "OK");
    addStatusCodeMap(SC_CREATED, "Created");
    addStatusCodeMap(SC_ACCEPTED, "Accepted");
    addStatusCodeMap(SC_NO_CONTENT, "No Content");
    addStatusCodeMap(SC_MOVED_PERMANENTLY, "Moved Permanently");
    addStatusCodeMap(SC_MOVED_TEMPORARILY, "Moved Temporarily");
    addStatusCodeMap(SC_NOT_MODIFIED, "Not Modified");
    addStatusCodeMap(SC_BAD_REQUEST, "Bad Request");
    addStatusCodeMap(SC_UNAUTHORIZED, "Unauthorized");
    addStatusCodeMap(SC_FORBIDDEN, "Forbidden");
    addStatusCodeMap(SC_NOT_FOUND, "Not Found");
    addStatusCodeMap(SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
    addStatusCodeMap(SC_NOT_IMPLEMENTED, "Not Implemented");
    addStatusCodeMap(SC_BAD_GATEWAY, "Bad Gateway");
    addStatusCodeMap(SC_SERVICE_UNAVAILABLE, "Service Unavailable");
    addStatusCodeMap(SC_CONTINUE, "Continue");
    addStatusCodeMap(SC_METHOD_NOT_ALLOWED, "Method Not Allowed");
    addStatusCodeMap(SC_CONFLICT, "Conflict");
    addStatusCodeMap(SC_PRECONDITION_FAILED, "Precondition Failed");
    addStatusCodeMap(SC_REQUEST_TOO_LONG, "Request Too Long");
    addStatusCodeMap(SC_UNSUPPORTED_MEDIA_TYPE, "Unsupported Media Type");
    // WebDav Status Codes
    addStatusCodeMap(SC_MULTI_STATUS, "Multi-Status");
    addStatusCodeMap(SC_UNPROCESSABLE_ENTITY, "Unprocessable Entity");
    addStatusCodeMap(SC_INSUFFICIENT_SPACE_ON_RESOURCE,
        "Insufficient Space On Resource");
    addStatusCodeMap(SC_METHOD_FAILURE, "Method Failure");
    addStatusCodeMap(SC_LOCKED, "Locked");
  }


  // --------------------------------------------------------- Public Methods


  /**
   *  Returns the HTTP status text for the HTTP or WebDav status code specified
   *  by looking it up in the static mapping. This is a static function.
   *
   *@param  nHttpStatusCode  [IN] HTTP or WebDAV status code
   *@return                  A string with a short descriptive phrase for the
   *      HTTP status code (e.g., "OK").
   */
  public static String getStatusText(int nHttpStatusCode) {
    Integer intKey = new Integer(nHttpStatusCode);

    if (!mapStatusCodes.containsKey(intKey)) {
      return "";
    } else {
      return (String) mapStatusCodes.get(intKey);
    }
  }


  // -------------------------------------------------------- Private Methods


  /**
   *  Adds a new status code -> status text mapping. This is a static method
   *  because the mapping is a static variable.
   *
   *@param  nKey    [IN] HTTP or WebDAV status code
   *@param  strVal  [IN] HTTP status text
   */
  private static void addStatusCodeMap(int nKey, String strVal) {
    mapStatusCodes.put(new Integer(nKey), strVal);
  }

}

