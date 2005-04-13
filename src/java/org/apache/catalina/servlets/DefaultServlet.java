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
package org.apache.catalina.servlets;

import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.database.ConnectionPool;
import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.webdav.WebdavManager;
import com.zeroio.webdav.context.ModuleContext;
import org.apache.catalina.Globals;
import org.apache.catalina.util.MD5Encoder;
import org.apache.catalina.util.ServerInfo;
import org.apache.catalina.util.StringManager;
import org.apache.catalina.util.URLEncoder;
import org.apache.naming.resources.Resource;
import org.apache.naming.resources.ResourceAttributes;
import org.apache.tomcat.util.http.FastHttpDateFormat;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.login.base.AuthenticationItem;

import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

/**
 * The default resource-serving servlet for most web applications,
 * used to serve static resources such as HTML pages and images.
 *
 *@author     Craig R. McClanahan
 *@author     Remy Maucherat
 *@created    October 26, 2004
 *@version    $Revision$ $Date$
 */

public class DefaultServlet
     extends HttpServlet {

  // ----------------------------------------------------- Instance Variables

  /**
   * The debugging detail level for this servlet.
   */
  protected int debug = 0;

  /**
   * The input buffer size to use when serving resources.
   */
  protected int input = 2048;

  /**
   * Should we generate directory listings?
   */
  protected boolean listings = true;

  /**
   * Read only flag. By default, it's set to true.
   */
  protected boolean readOnly = true;

  /**
   * The output buffer size to use when serving resources.
   */
  protected int output = 2048;

  /**
   * MD5 message digest provider.
   */
  protected static MessageDigest md5Helper;

  /**
   * The MD5 helper object for this class.
   */
  protected final static MD5Encoder md5Encoder = new MD5Encoder();

  /**
   * Array containing the safe characters set.
   */
  protected static URLEncoder urlEncoder;

  /**
   * Allow customized directory listing per directory.
   */
  protected String localXsltFile = null;

  /**
   * Allow customized directory listing per instance.
   */
  protected String globalXsltFile = null;

  /**
   * Allow a readme file to be included.
   */
  protected String readmeFile = null;

  // ----------------------------------------------------- Static Initializer

  protected final static String METHOD_GET = "GET";
  protected final static String METHOD_PUT = "PUT";

  protected final static int CFS_SQLERROR = 425;

  public final static String fs = System.getProperty("file.separator");

  static {
    urlEncoder = new URLEncoder();
    urlEncoder.addSafeCharacter('-');
    urlEncoder.addSafeCharacter('_');
    urlEncoder.addSafeCharacter('.');
    urlEncoder.addSafeCharacter('*');
    urlEncoder.addSafeCharacter('/');
  }

  /**
   * MIME multipart separation string
   */
  protected final static String mimeSeparation = "CATALINA_MIME_BOUNDARY";

  /**
   * JNDI resources name.
   */
  protected final static String RESOURCES_JNDI_NAME = "java:/comp/Resources";

  /**
   * The string manager for this package.
   */
  protected static StringManager sm =
      StringManager.getManager(Constants.Package);

  /**
   * Size of file transfer buffer in bytes.
   */
  private final static int BUFFER_SIZE = 4096;

  private Hashtable connectionElements = new Hashtable();


  // --------------------------------------------------------- Public Methods

  /**
   * Finalize this servlet.
   */
  public void destroy() {
  }


  /**
   * Initialize this servlet.
   *
   *@exception  ServletException  Description of the Exception
   */
  public void init() throws ServletException {

    // Set our properties from the initialization parameters
    String value = null;
    try {
      value = getServletConfig().getInitParameter("debug");
      debug = Integer.parseInt(value);
    } catch (Throwable t) {
      ;
    }
    try {
      value = getServletConfig().getInitParameter("input");
      input = Integer.parseInt(value);
    } catch (Throwable t) {
      ;
    }
    try {
      value = getServletConfig().getInitParameter("listings");
      listings = (new Boolean(value)).booleanValue();
    } catch (Throwable t) {
      ;
    }
    try {
      value = getServletConfig().getInitParameter("readonly");
      if (value != null) {
        readOnly = (new Boolean(value)).booleanValue();
      }
    } catch (Throwable t) {
      ;
    }
    try {
      value = getServletConfig().getInitParameter("output");
      output = Integer.parseInt(value);
    } catch (Throwable t) {
      ;
    }

    globalXsltFile = getServletConfig().getInitParameter("globalXsltFile");
    localXsltFile = getServletConfig().getInitParameter("localXsltFile");
    readmeFile = getServletConfig().getInitParameter("readmeFile");

    // Sanity check on the specified buffer sizes
    if (input < 256) {
      input = 256;
    }
    if (output < 256) {
      output = 256;
    }

    if (debug > 0) {
      log("DefaultServlet.init:  input buffer size=" + input +
          ", output buffer size=" + output);
    }

    // Load the MD5 helper used to calculate signatures.
    try {
      md5Helper = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      throw new IllegalStateException();
    }
  }


  /*
   *  Utility Methods
   */
  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  protected boolean managerReady(ActionContext context) {
    if (context.getServletContext().getAttribute("WebdavManager") != null) {
      return true;
    } else {
      return false;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  protected synchronized ConnectionElement retrieveConnectionElement(ActionContext context) {
    String serverName = context.getRequest().getServerName();
    if (!connectionElements.containsKey(serverName)) {
      System.out.println("DefaultServlet-> Initializing Connection Element");
      AuthenticationItem auth = new AuthenticationItem();
      try {
        ConnectionElement ce = auth.getConnectionElement(context);
        connectionElements.put(serverName, ce);
      } catch (SQLException e) {
        e.printStackTrace(System.out);
      }
    }
    return (ConnectionElement) connectionElements.get(serverName);
  }


  /**
   *  Gets the path attribute of the DefaultServlet object
   *
   *@param  context  Description of the Parameter
   *@return          The path value
   */
  protected String getPath(ActionContext context) {
    ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
    return prefs.get("FILELIBRARY");
  }


  /**
   *  Gets the systemStatus attribute of the DefaultServlet object
   *
   *@param  context  Description of the Parameter
   *@param  ce       Description of the Parameter
   *@return          The systemStatus value
   */
  protected SystemStatus getSystemStatus(ActionContext context, ConnectionElement ce) {
    return (SystemStatus) ((Hashtable) context.getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
  }


  /**
   *  Gets the systemStatus attribute of the DefaultServlet object
   *
   *@param  context  Description of the Parameter
   *@return          The systemStatus value
   */
  protected SystemStatus getSystemStatus(ActionContext context) {
    return getSystemStatus(context, this.getConnectionElement(context));
  }


  /**
   *  Gets the connectionElement attribute of the DefaultServlet object
   *
   *@param  context  Description of the Parameter
   *@return          The connectionElement value
   */
  protected ConnectionElement getConnectionElement(ActionContext context) {
    String serverName = context.getRequest().getServerName();
    return (ConnectionElement) connectionElements.get(serverName);
  }


  /**
   *  Gets the webdavManager attribute of the DefaultServlet object
   *
   *@param  context  Description of the Parameter
   *@return          The webdavManager value
   */
  protected WebdavManager getWebdavManager(ActionContext context) {
    ConnectionElement ce = this.getConnectionElement(context);
    SystemStatus thisSystem = (SystemStatus) this.getSystemStatus(context, ce);
    return thisSystem.getWebdavManager();
  }


  /**
   *  Gets the userId attribute of the DefaultServlet object
   *
   *@param  context  Description of the Parameter
   *@return          The userId value
   */
  protected int getUserId(ActionContext context) {
    String username = this.getUser(context);
    return (getWebdavManager(context).getUser(username).getUserId());
  }


  /**
   *  Gets the connection attribute of the DefaultServlet object
   *
   *@param  context           Description of the Parameter
   *@return                   The connection value
   *@exception  SQLException  Description of the Exception
   */
  protected Connection getConnection(ActionContext context) throws SQLException {
    ConnectionElement ce = this.getConnectionElement(context);
    ConnectionPool sqlDriver = (ConnectionPool) context.getServletContext().getAttribute("ConnectionPool");
    return sqlDriver.getConnection(ce);
  }


  /**
   *  Description of the Method
   *
   *@param  db       Description of the Parameter
   *@param  context  Description of the Parameter
   */
  protected void freeConnection(Connection db, ActionContext context) {
    if (db != null) {
      ConnectionPool sqlDriver = (ConnectionPool) context.getServletContext().getAttribute("ConnectionPool");
      sqlDriver.free(db);
    }
    db = null;
  }


  /**
   *  Description of the Method
   *
   *@param  context    Description of the Parameter
   *@param  argHeader  Description of the Parameter
   *@return            The authenticationParams value
   */
  protected HashMap getAuthenticationParams(ActionContext context, String argHeader) {
    HashMap params = new HashMap();
    StringTokenizer st = new StringTokenizer(argHeader, ",");
    while (st.hasMoreTokens()) {
      String token = st.nextToken();
      if (token.startsWith("Digest")) {
        token = token.substring("Digest".length());
      }
      String param = token.substring(0, token.indexOf("=")).trim();
      String value = token.substring(token.indexOf("\"") + 1, token.lastIndexOf("\""));
      params.put(param, value);
    }
    return params;
  }


  /**
   *  Gets the user attribute of the DefaultServlet object
   *
   *@param  context  Description of the Parameter
   *@return          The user value
   */
  protected String getUser(ActionContext context) {
    String argHeader = context.getRequest().getHeader("Authorization");
    if (argHeader.toUpperCase().startsWith("BASIC")) {
      //BASIC AUTHENTICATION
      String username = "";
      try {
        String userpassEncoded = argHeader.substring(6);
        sun.misc.BASE64Decoder dec = new sun.misc.BASE64Decoder();
        String userpassDecoded = new String(dec.decodeBuffer(userpassEncoded));
        username = userpassDecoded.substring(0, userpassDecoded.indexOf(":"));
      } catch (IOException e) {
        e.printStackTrace(System.out);
      }
      return username;
    } else {
      //DIGEST AUTHENTICATION
      return (String) getAuthenticationParams(context, argHeader).get("username");
    }
  }


  /**
   *  Gets the nonce attribute of the DefaultServlet object
   *
   *@return    The nonce value
   */
  protected String generateNonce() {
    sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
    String timestamp = new java.sql.Timestamp(new Date().getTime()).toString();
    String random = org.aspcfs.utils.PasswordHash.getRandomString(0, 1);
    return (new String(encoder.encode((timestamp + ":" + random).getBytes())));
  }


  /**
   *  Gets the opaque attribute of the DefaultServlet object
   *
   *@return    The opaque value
   */
  protected String generateOpaque() {
    sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
    String random = org.aspcfs.utils.PasswordHash.getRandomString(0, 10);
    return (new String(encoder.encode((":" + random + ":").getBytes())));
  }


  /**
   *  Description of the Method
   *
   *@param  req                   Description of the Parameter
   *@param  resp                  Description of the Parameter
   *@exception  ServletException  Description of the Exception
   *@exception  IOException       Description of the Exception
   */
  protected void service(HttpServletRequest req, HttpServletResponse resp)
       throws ServletException, IOException {

    ActionContext context = new ActionContext(this, null, null, req, resp);
    String method = req.getMethod();

    if (method.equals(METHOD_GET)) {
      doGet(context, req, resp);
    } else if (method.equals(METHOD_PUT)) {
      doPut(req, resp);
    }
  }


  // ------------------------------------------------------ Protected Methods

  /**
   * Get resources. This method will try to retrieve the resources through
   * JNDI first, then in the servlet context if JNDI has failed (it could be
   * disabled). It will return null.
   *
   *@return    A JNDI DirContext, or null.
   */
  protected DirContext getResources() {

    DirContext result = null;

    // Try the servlet context
    try {
      result = (DirContext) getServletContext()
          .getAttribute(Globals.RESOURCES_ATTR);
    } catch (ClassCastException e) {
      // Failed : Not the right type
    }

    if (result != null) {
      return result;
    }

    // Try JNDI
    try {
      result =
          (DirContext) new InitialContext().lookup(RESOURCES_JNDI_NAME);
    } catch (NamingException e) {
      // Failed
    } catch (ClassCastException e) {
      // Failed : Not the right type
    }
    return result;
  }


  /**
   *  Gets the cFSResources attribute of the DefaultServlet object
   *
   *@param  context           Description of the Parameter
   *@param  db                Description of the Parameter
   *@return                   The cFSResources value
   *@exception  SQLException  Description of the Exception
   */
  protected ModuleContext getCFSResources(Connection db, ActionContext context) throws SQLException {
    ModuleContext result = null;
    /*
     *  /TODO: cache the context tree so that it is not built from scratch ie root
     *  / everytime. But caching would make the tree go out of sync with the
     *  / database
     */
    try {
      String username = this.getUser(context);
      ConnectionElement ce = this.getConnectionElement(context);
      SystemStatus thisSystem = this.getSystemStatus(context, ce);
      WebdavManager thisManager = this.getWebdavManager(context);
      result = (ModuleContext) thisManager.getResources(db, thisSystem, username);
    } catch (ClassCastException e) {
      // Failed : Not the right type
    }
    return result;
  }


  /**
   * Show HTTP header information.
   *
   *@param  req  Description of the Parameter
   */
  protected void showRequestInfo(HttpServletRequest req) {

    System.out.println();
    System.out.println("SlideDAV Request Info");
    System.out.println();

    // Show generic info
    System.out.println("Encoding : " + req.getCharacterEncoding());
    System.out.println("Length : " + req.getContentLength());
    System.out.println("Type : " + req.getContentType());

    System.out.println();
    System.out.println("Parameters");

    Enumeration parameters = req.getParameterNames();

    while (parameters.hasMoreElements()) {
      String paramName = (String) parameters.nextElement();
      String[] values = req.getParameterValues(paramName);
      System.out.print(paramName + " : ");
      for (int i = 0; i < values.length; i++) {
        System.out.print(values[i] + ", ");
      }
      System.out.println();
    }

    System.out.println();

    System.out.println("Protocol : " + req.getProtocol());
    System.out.println("Address : " + req.getRemoteAddr());
    System.out.println("Host : " + req.getRemoteHost());
    System.out.println("Scheme : " + req.getScheme());
    System.out.println("Server Name : " + req.getServerName());
    System.out.println("Server Port : " + req.getServerPort());

    System.out.println();
    System.out.println("Attributes");

    Enumeration attributes = req.getAttributeNames();

    while (attributes.hasMoreElements()) {
      String attributeName = (String) attributes.nextElement();
      System.out.print(attributeName + " : ");
      System.out.println(req.getAttribute(attributeName).toString());
    }

    System.out.println();

    // Show HTTP info
    System.out.println();
    System.out.println("HTTP Header Info");
    System.out.println();

    System.out.println("Authentication Type : " + req.getAuthType());
    System.out.println("HTTP Method : " + req.getMethod());
    System.out.println("Path Info : " + req.getPathInfo());
    System.out.println("Path translated : " + req.getPathTranslated());
    System.out.println("Query string : " + req.getQueryString());
    System.out.println("Remote user : " + req.getRemoteUser());
    System.out.println("Requested session id : "
         + req.getRequestedSessionId());
    System.out.println("Request URI : " + req.getRequestURI());
    System.out.println("Context path : " + req.getContextPath());
    System.out.println("Servlet path : " + req.getServletPath());
    System.out.println("User principal : " + req.getUserPrincipal());

    System.out.println();
    System.out.println("Headers : ");

    Enumeration headers = req.getHeaderNames();

    while (headers.hasMoreElements()) {
      String headerName = (String) headers.nextElement();
      System.out.print(headerName + " : ");
      System.out.println(req.getHeader(headerName));
    }

    System.out.println();
    System.out.println();

  }


  /**
   * Return the relative path associated with this servlet.
   *
   *@param  request  The servlet request we are processing
   *@return          The relativePath value
   */
  protected String getRelativePath(HttpServletRequest request) {

    // Are we being processed by a RequestDispatcher.include()?
    if (request.getAttribute("javax.servlet.include.request_uri") != null) {
      String result = (String)
          request.getAttribute("javax.servlet.include.path_info");
      if (result == null) {
        result = (String)
            request.getAttribute("javax.servlet.include.servlet_path");
      }
      if ((result == null) || (result.equals(""))) {
        result = "/";
      }
      return (result);
    }

    // No, extract the desired path directly from the request
    String result = request.getPathInfo();
    if (result == null) {
      result = request.getServletPath();
    }
    if ((result == null) || (result.equals(""))) {
      result = "/";
    }
    return (result);
  }


  /**
   * Process a GET request for the specified resource.
   *
   *@param  request               The servlet request we are processing
   *@param  response              The servlet response we are creating
   *@param  context               Description of the Parameter
   *@exception  IOException       if an input/output error occurs
   *@exception  ServletException  if a servlet-specified error occurs
   */
  protected void doGet(ActionContext context, HttpServletRequest request, HttpServletResponse response)
       throws IOException, ServletException {

    if (debug > 999) {
      showRequestInfo(request);
    }

    // Serve the requested resource, including the data content
    try {
      serveResource(context, request, response, true);
    } catch (IOException ex) {
      // we probably have this check somewhere else too.
      if (ex.getMessage() != null
           && ex.getMessage().indexOf("Broken pipe") >= 0) {
        // ignore it.
      }
      throw ex;
    }

  }


  /**
   * Process a HEAD request for the specified resource.
   *
   *@param  request               The servlet request we are processing
   *@param  response              The servlet response we are creating
   *@exception  IOException       if an input/output error occurs
   *@exception  ServletException  if a servlet-specified error occurs
   */
  protected void doHead(HttpServletRequest request,
      HttpServletResponse response)
       throws IOException, ServletException {

    // Serve the requested resource, without the data content
    //TODO: fix this call and uncomment
    //serveResource(request, response, false);

  }


  /**
   * Process a POST request for the specified resource.
   *
   *@param  request               The servlet request we are processing
   *@param  response              The servlet response we are creating
   *@exception  IOException       if an input/output error occurs
   *@exception  ServletException  if a servlet-specified error occurs
   */
  protected void doPost(HttpServletRequest request,
      HttpServletResponse response)
       throws IOException, ServletException {
    doGet(request, response);
  }


  /**
   * Process a POST request for the specified resource.
   *
   *@param  req                   Description of the Parameter
   *@param  resp                  Description of the Parameter
   *@exception  IOException       if an input/output error occurs
   *@exception  ServletException  if a servlet-specified error occurs
   */
  protected void doPut(HttpServletRequest req, HttpServletResponse resp)
       throws ServletException, IOException {

    if (readOnly) {
      resp.sendError(HttpServletResponse.SC_FORBIDDEN);
      return;
    }

    String path = getRelativePath(req);

    // Retrieve the resources
    DirContext resources = getResources();

    if (resources == null) {
      resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      return;
    }

    boolean exists = true;
    try {
      resources.lookup(path);
    } catch (NamingException e) {
      exists = false;
    }

    boolean result = true;

    // Temp. content file used to support partial PUT
    File contentFile = null;

    // Input stream for temp. content file used to support partial PUT
    FileInputStream contentFileInStream = null;

    ResourceInfo resourceInfo = new ResourceInfo(path, resources);
    Range range = parseContentRange(req, resp);

    InputStream resourceInputStream = null;

    // Append data specified in ranges to existing content for this
    // resource - create a temp. file on the local filesystem to
    // perform this operation
    // Assume just one range is specified for now
    if (range != null) {
      contentFile = executePartialPut(req, range, path);
      resourceInputStream = new FileInputStream(contentFile);
    } else {
      resourceInputStream = req.getInputStream();
    }

    try {
      Resource newResource = new Resource(resourceInputStream);
      // FIXME: Add attributes
      if (exists) {
        resources.rebind(path, newResource);
      } else {
        resources.bind(path, newResource);
      }
    } catch (NamingException e) {
      result = false;
    }

    if (result) {
      if (exists) {
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
      } else {
        resp.setStatus(HttpServletResponse.SC_CREATED);
      }
    } else {
      resp.sendError(HttpServletResponse.SC_CONFLICT);
    }

  }


  /**
   * Handle a partial PUT.  New content specified in request is appended to
   * existing content in oldRevisionContent (if present). This code does
   * not support simultaneous partial updates to the same resource.
   *
   *@param  req              Description of the Parameter
   *@param  range            Description of the Parameter
   *@param  path             Description of the Parameter
   *@return                  Description of the Return Value
   *@exception  IOException  Description of the Exception
   */
  protected File executePartialPut(HttpServletRequest req, Range range,
      String path)
       throws IOException {

    // Append data specified in ranges to existing content for this
    // resource - create a temp. file on the local filesystem to
    // perform this operation
    File tempDir = (File) getServletContext().getAttribute
        ("javax.servlet.context.tempdir");
    // Convert all '/' characters to '.' in resourcePath
    String convertedResourcePath = path.replace('/', '.');
    File contentFile = new File(tempDir, convertedResourcePath);
    if (contentFile.createNewFile()) {
      // Clean up contentFile when Tomcat is terminated
      contentFile.deleteOnExit();
    }

    RandomAccessFile randAccessContentFile =
        new RandomAccessFile(contentFile, "rw");

    Resource oldResource = null;
    try {
      Object obj = getResources().lookup(path);
      if (obj instanceof Resource) {
        oldResource = (Resource) obj;
      }
    } catch (NamingException e) {
    }

    // Copy data in oldRevisionContent to contentFile
    if (oldResource != null) {
      BufferedInputStream bufOldRevStream =
          new BufferedInputStream(oldResource.streamContent(),
          BUFFER_SIZE);

      int numBytesRead;
      byte[] copyBuffer = new byte[BUFFER_SIZE];
      while ((numBytesRead = bufOldRevStream.read(copyBuffer)) != -1) {
        randAccessContentFile.write(copyBuffer, 0, numBytesRead);
      }

      bufOldRevStream.close();
    }

    randAccessContentFile.setLength(range.length);

    // Append data in request input stream to contentFile
    randAccessContentFile.seek(range.start);
    int numBytesRead;
    byte[] transferBuffer = new byte[BUFFER_SIZE];
    BufferedInputStream requestBufInStream =
        new BufferedInputStream(req.getInputStream(), BUFFER_SIZE);
    while ((numBytesRead = requestBufInStream.read(transferBuffer)) != -1) {
      randAccessContentFile.write(transferBuffer, 0, numBytesRead);
    }
    randAccessContentFile.close();
    requestBufInStream.close();

    return contentFile;
  }


  /**
   * Process a POST request for the specified resource.
   *
   *@param  req                   Description of the Parameter
   *@param  resp                  Description of the Parameter
   *@exception  IOException       if an input/output error occurs
   *@exception  ServletException  if a servlet-specified error occurs
   */
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
       throws ServletException, IOException {

    if (readOnly) {
      resp.sendError(HttpServletResponse.SC_FORBIDDEN);
      return;
    }

    String path = getRelativePath(req);

    // Retrieve the Catalina context
    // Retrieve the resources
    DirContext resources = getResources();

    if (resources == null) {
      resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      return;
    }

    boolean exists = true;
    try {
      resources.lookup(path);
    } catch (NamingException e) {
      exists = false;
    }

    if (exists) {
      boolean result = true;
      try {
        resources.unbind(path);
      } catch (NamingException e) {
        result = false;
      }
      if (result) {
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
      } else {
        resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
      }
    } else {
      resp.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

  }


  /**
   * Check if the conditions specified in the optional If headers are
   * satisfied.
   *
   *@param  request          The servlet request we are processing
   *@param  response         The servlet response we are creating
   *@param  resourceInfo     File object
   *@return                  boolean true if the resource meets all the specified conditions,
   * and false if any of the conditions is not satisfied, in which case
   * request processing is stopped
   *@exception  IOException  Description of the Exception
   */
  protected boolean checkIfHeaders(HttpServletRequest request,
      HttpServletResponse response,
      ResourceInfo resourceInfo)
       throws IOException {

    return checkIfMatch(request, response, resourceInfo)
         && checkIfModifiedSince(request, response, resourceInfo)
         && checkIfNoneMatch(request, response, resourceInfo)
         && checkIfUnmodifiedSince(request, response, resourceInfo);
  }


  /**
   * Get the ETag associated with a file.
   *
   *@param  resourceInfo  File object
   *@return               The eTag value
   */
  protected String getETag(ResourceInfo resourceInfo) {
    if (resourceInfo.strongETag != null) {
      return resourceInfo.strongETag;
    } else if (resourceInfo.weakETag != null) {
      return resourceInfo.weakETag;
    } else {
      return "W/\"" + resourceInfo.length + "-"
           + resourceInfo.date + "\"";
    }
  }


  /**
   * Return a context-relative path, beginning with a "/", that represents
   * the canonical version of the specified path after ".." and "." elements
   * are resolved out.  If the specified path attempts to go outside the
   * boundaries of the current context (i.e. too many ".." path elements
   * are present), return <code>null</code> instead.
   *
   *@param  path  Path to be normalized
   *@return       Description of the Return Value
   */
  protected String normalize(String path) {

    if (path == null) {
      return null;
    }

    // Create a place for the normalized path
    String normalized = path;

    if (normalized == null) {
      return (null);
    }

    if (normalized.equals("/.")) {
      return "/";
    }

    // Normalize the slashes and add leading slash if necessary
    if (normalized.indexOf('\\') >= 0) {
      normalized = normalized.replace('\\', '/');
    }
    if (!normalized.startsWith("/")) {
      normalized = "/" + normalized;
    }

    // Resolve occurrences of "//" in the normalized path
    while (true) {
      int index = normalized.indexOf("//");
      if (index < 0) {
        break;
      }
      normalized = normalized.substring(0, index) +
          normalized.substring(index + 1);
    }

    // Resolve occurrences of "/./" in the normalized path
    while (true) {
      int index = normalized.indexOf("/./");
      if (index < 0) {
        break;
      }
      normalized = normalized.substring(0, index) +
          normalized.substring(index + 2);
    }

    // Resolve occurrences of "/../" in the normalized path
    while (true) {
      int index = normalized.indexOf("/../");
      if (index < 0) {
        break;
      }
      if (index == 0) {
        return (null);
      }
      // Trying to go outside our context
      int index2 = normalized.lastIndexOf('/', index - 1);
      normalized = normalized.substring(0, index2) +
          normalized.substring(index + 3);
    }

    // Return the normalized path that we have completed
    return (normalized);
  }


  /**
   * URL rewriter.
   *
   *@param  path  Path which has to be rewiten
   *@return       Description of the Return Value
   */
  protected String rewriteUrl(String path) {
    return urlEncoder.encode(path);
  }


  /**
   * Display the size of a file.
   *
   *@param  buf       Description of the Parameter
   *@param  filesize  Description of the Parameter
   */
  protected void displaySize(StringBuffer buf, int filesize) {

    int leftside = filesize / 1024;
    int rightside = (filesize % 1024) / 103;
    // makes 1 digit
    // To avoid 0.0 for non-zero file, we bump to 0.1
    if (leftside == 0 && rightside == 0 && filesize != 0) {
      rightside = 1;
    }
    buf.append(leftside).append(".").append(rightside);
    buf.append(" KB");

  }


  /**
   * Serve the specified resource, optionally including the data content.
   *
   *@param  request               The servlet request we are processing
   *@param  response              The servlet response we are creating
   *@param  content               Should the content be included?
   *@param  context               Description of the Parameter
   *@exception  IOException       if an input/output error occurs
   *@exception  ServletException  if a servlet-specified error occurs
   */
  protected void serveResource(ActionContext context, HttpServletRequest request,
      HttpServletResponse response,
      boolean content)
       throws IOException, ServletException {

    //TODO: remove this hardcoding
    debug = 2;
    // Identify the requested resource path
    String path = getRelativePath(request);
    if (debug > 0) {
      if (content) {
        log("DefaultServlet.serveResource:  Serving resource '" +
            path + "' headers and data");
      } else {
        log("DefaultServlet.serveResource:  Serving resource '" +
            path + "' headers only");
      }
    }

    // Retrieve the Catalina context and Resources implementation

    Connection db = null;
    ModuleContext resources = null;
    ResourceInfo resourceInfo = null;
    SystemStatus thisSystem = null;
    boolean status = true;
    try {
      System.out.println("DefaultServlet-> Serving Resource");
      db = this.getConnection(context);
      resources = getCFSResources(db, context);
      thisSystem = this.getSystemStatus(context);
      resourceInfo = new ResourceInfo(thisSystem, db, path, resources);
    } catch (SQLException e) {
      e.printStackTrace(System.out);
      context.getResponse().sendError(CFS_SQLERROR, e.getMessage());
      status = false;
    } catch (Exception e) {
      //TODO: remove silent catch
      e.printStackTrace(System.out);
    } finally {
      this.freeConnection(db, context);
    }
    if (!status) {
      return;
    }

    if (!resourceInfo.exists) {
      response.sendError(HttpServletResponse.SC_NOT_FOUND,
          request.getRequestURI());
      return;
    }

    // If the resource is not a collection, and the resource path
    // ends with "/" or "\", return NOT FOUND
    if (!resourceInfo.collection) {
      if (path.endsWith("/") || (path.endsWith("\\"))) {
        response.sendError(HttpServletResponse.SC_NOT_FOUND,
            request.getRequestURI());
        return;
      }
    }

    // Check if the conditions specified in the optional If headers are
    // satisfied.
    if (!resourceInfo.collection) {

      // Checking If headers
      boolean included =
          (request.getAttribute(Globals.INCLUDE_CONTEXT_PATH_ATTR) != null);
      if (!included
           && !checkIfHeaders(request, response, resourceInfo)) {
        return;
      }
    }

    // Find content type
    String contentType =
        getServletContext().getMimeType(resourceInfo.path);

    Vector ranges = null;

    if (resourceInfo.collection) {

      // Skip directory listings if we have been configured to
      // suppress them
      if (!listings) {
        response.sendError(HttpServletResponse.SC_NOT_FOUND,
            request.getRequestURI());
        return;
      }
      contentType = "text/html;charset=UTF-8";

    } else {
      // Parse range specifier
      ranges = parseRange(request, response, resourceInfo);
      // ETag header
      response.setHeader("ETag", getETag(resourceInfo));
      // Last-Modified header
      if (debug > 0) {
        log("DefaultServlet.serveFile:  lastModified='" +
            (new Timestamp(resourceInfo.date)).toString() + "'");
      }
      response.setHeader("Last-Modified", resourceInfo.httpDate);
    }
    
    ServletOutputStream ostream = null;
    PrintWriter writer = null;

    if (content) {
      // Trying to retrieve the servlet output stream
      try {
        ostream = response.getOutputStream();
      } catch (IllegalStateException e) {
        // If it fails, we try to get a Writer instead if we're
        // trying to serve a text file
        if ((contentType == null)
             || (contentType.startsWith("text"))) {
          writer = response.getWriter();
        } else {
          throw e;
        }
      }
    }

    if ((resourceInfo.collection) ||
        (((ranges == null) || (ranges.isEmpty()))
         && (request.getHeader("Range") == null))) {

      // Set the appropriate output headers
      if (contentType != null) {
        if (debug > 0) {
          log("DefaultServlet.serveFile:  contentType='" +
              contentType + "'");
        }
        response.setContentType(contentType);
      }
      long contentLength = resourceInfo.length;
      if ((!resourceInfo.collection) && (contentLength >= 0)) {
        if (debug > 0) {
          log("DefaultServlet.serveFile:  contentLength=" +
              contentLength);
        }
        response.setContentLength((int) contentLength);
      }
      if (resourceInfo.collection) {
        if (content) {
          // Serve the directory browser
          resourceInfo.setStream
              (render(request.getContextPath(), resourceInfo));
        }
      }
      // Copy the input stream to our output stream (if requested)
      if (content) {
        try {
          response.setBufferSize(output);
        } catch (IllegalStateException e) {
          // Silent catch
          e.printStackTrace(System.out);
        }
        if (ostream != null) {
          copy(resourceInfo, ostream);
        } else {
          copy(resourceInfo, writer);
        }
      }
    } else {
      if ((ranges == null) || (ranges.isEmpty())) {
        return;
      }
      // Partial content response.
      response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
      if (ranges.size() == 1) {
        Range range = (Range) ranges.elementAt(0);
        response.addHeader("Content-Range", "bytes "
             + range.start
             + "-" + range.end + "/"
             + range.length);
        response.setContentLength((int) (range.end - range.start + 1));

        if (contentType != null) {
          if (debug > 0) {
            log("DefaultServlet.serveFile:  contentType='" +
                contentType + "'");
          }
          response.setContentType(contentType);
        }

        if (content) {
          try {
            response.setBufferSize(output);
          } catch (IllegalStateException e) {
            // Silent catch
          }
          if (ostream != null) {
            copy(resourceInfo, ostream, range);
          } else {
            copy(resourceInfo, writer, range);
          }
        }
      } else {

        response.setContentType("multipart/byteranges; boundary="
             + mimeSeparation);

        if (content) {
          try {
            response.setBufferSize(output);
          } catch (IllegalStateException e) {
            // Silent catch
          }
          if (ostream != null) {
            copy(resourceInfo, ostream, ranges.elements(),
                contentType);
          } else {
            copy(resourceInfo, writer, ranges.elements(),
                contentType);
          }
        }
      }
    }
  }


  /**
   * Parse the content-range header.
   *
   *@param  request          The servlet request we are processing
   *@param  response         The servlet response we are creating
   *@return                  Range
   *@exception  IOException  Description of the Exception
   */
  protected Range parseContentRange(HttpServletRequest request,
      HttpServletResponse response)
       throws IOException {

    // Retrieving the content-range header (if any is specified
    String rangeHeader = request.getHeader("Content-Range");

    if (rangeHeader == null) {
      return null;
    }

    // bytes is the only range unit supported
    if (!rangeHeader.startsWith("bytes")) {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST);
      return null;
    }

    rangeHeader = rangeHeader.substring(6).trim();

    int dashPos = rangeHeader.indexOf('-');
    int slashPos = rangeHeader.indexOf('/');

    if (dashPos == -1) {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST);
      return null;
    }

    if (slashPos == -1) {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST);
      return null;
    }

    Range range = new Range();

    try {
      range.start = Long.parseLong(rangeHeader.substring(0, dashPos));
      range.end =
          Long.parseLong(rangeHeader.substring(dashPos + 1, slashPos));
      range.length = Long.parseLong
          (rangeHeader.substring(slashPos + 1, rangeHeader.length()));
    } catch (NumberFormatException e) {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST);
      return null;
    }

    if (!range.validate()) {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST);
      return null;
    }

    return range;
  }


  /**
   * Parse the range header.
   *
   *@param  request          The servlet request we are processing
   *@param  response         The servlet response we are creating
   *@param  resourceInfo     Description of the Parameter
   *@return                  Vector of ranges
   *@exception  IOException  Description of the Exception
   */
  protected Vector parseRange(HttpServletRequest request,
      HttpServletResponse response,
      ResourceInfo resourceInfo)
       throws IOException {

    // Checking If-Range
    String headerValue = request.getHeader("If-Range");

    if (headerValue != null) {

      long headerValueTime = (-1L);
      try {
        headerValueTime = request.getDateHeader("If-Range");
      } catch (Exception e) {
        ;
      }

      String eTag = getETag(resourceInfo);
      long lastModified = resourceInfo.date;

      if (headerValueTime == (-1L)) {

        // If the ETag the client gave does not match the entity
        // etag, then the entire entity is returned.
        if (!eTag.equals(headerValue.trim())) {
          return null;
        }
      } else {

        // If the timestamp of the entity the client got is older than
        // the last modification date of the entity, the entire entity
        // is returned.
        if (lastModified > (headerValueTime + 1000)) {
          return null;
        }
      }

    }

    long fileLength = resourceInfo.length;

    if (fileLength == 0) {
      return null;
    }

    // Retrieving the range header (if any is specified
    String rangeHeader = request.getHeader("Range");

    if (rangeHeader == null) {
      return null;
    }
    // bytes is the only range unit supported (and I don't see the point
    // of adding new ones).
    if (!rangeHeader.startsWith("bytes")) {
      response.addHeader("Content-Range", "bytes */" + fileLength);
      response.sendError
          (HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
      return null;
    }

    rangeHeader = rangeHeader.substring(6);

    // Vector which will contain all the ranges which are successfully
    // parsed.
    Vector result = new Vector();
    StringTokenizer commaTokenizer = new StringTokenizer(rangeHeader, ",");

    // Parsing the range list
    while (commaTokenizer.hasMoreTokens()) {
      String rangeDefinition = commaTokenizer.nextToken().trim();

      Range currentRange = new Range();
      currentRange.length = fileLength;

      int dashPos = rangeDefinition.indexOf('-');

      if (dashPos == -1) {
        response.addHeader("Content-Range", "bytes */" + fileLength);
        response.sendError
            (HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
        return null;
      }

      if (dashPos == 0) {

        try {
          long offset = Long.parseLong(rangeDefinition);
          currentRange.start = fileLength + offset;
          currentRange.end = fileLength - 1;
        } catch (NumberFormatException e) {
          response.addHeader("Content-Range",
              "bytes */" + fileLength);
          response.sendError
              (HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
          return null;
        }

      } else {

        try {
          currentRange.start = Long.parseLong
              (rangeDefinition.substring(0, dashPos));
          if (dashPos < rangeDefinition.length() - 1) {
            currentRange.end = Long.parseLong
                (rangeDefinition.substring
                (dashPos + 1, rangeDefinition.length()));
          } else {
            currentRange.end = fileLength - 1;
          }
        } catch (NumberFormatException e) {
          response.addHeader("Content-Range",
              "bytes */" + fileLength);
          response.sendError
              (HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
          return null;
        }

      }

      if (!currentRange.validate()) {
        response.addHeader("Content-Range", "bytes */" + fileLength);
        response.sendError
            (HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
        return null;
      }

      result.addElement(currentRange);
    }

    return result;
  }


  /**
   * Append the request parameters to the redirection string before calling
   * sendRedirect.
   *
   *@param  request       Description of the Parameter
   *@param  redirectPath  Description of the Parameter
   *@return               Description of the Return Value
   */
  protected String appendParameters(HttpServletRequest request,
      String redirectPath) {

    StringBuffer result = new StringBuffer(rewriteUrl(redirectPath));

    String query = request.getQueryString();
    if (query != null) {
      result.append("?").append(query);
    }

    return result.toString();
  }


  /**
   *  Decide which way to render. HTML or XML.
   *
   *@param  contextPath   Description of the Parameter
   *@param  resourceInfo  Description of the Parameter
   *@return               Description of the Return Value
   */
  protected InputStream render
      (String contextPath, ResourceInfo resourceInfo) {
    InputStream xsltInputStream =
        findXsltInputStream(resourceInfo.directory);

    if (xsltInputStream == null) {
      return renderHtml(contextPath, resourceInfo);
    } else {
      return renderXml(contextPath, resourceInfo, xsltInputStream);
    }

  }


  /**
   * Return an InputStream to an HTML representation of the contents
   * of this directory.
   *
   *@param  contextPath      Context path to which our internal paths are
   *  relative
   *@param  resourceInfo     Description of the Parameter
   *@param  xsltInputStream  Description of the Parameter
   *@return                  Description of the Return Value
   */
  protected InputStream renderXml(String contextPath,
      ResourceInfo resourceInfo,
      InputStream xsltInputStream) {

    StringBuffer sb = new StringBuffer();

    sb.append("<?xml version=\"1.0\"?>");
    sb.append("<listing ");
    sb.append(" contextPath='");
    sb.append(contextPath);
    sb.append("'");
    sb.append(" directory='");
    sb.append(resourceInfo.path);
    sb.append("' ");
    sb.append(" hasParent='").append(!resourceInfo.path.equals("/"));
    sb.append("'>");

    sb.append("<entries>");

    try {

      // Render the directory entries within this directory
      DirContext directory = resourceInfo.directory;
      NamingEnumeration enum1 =
          resourceInfo.resources.list(resourceInfo.path);
      while (enum1.hasMoreElements()) {

        NameClassPair ncPair = (NameClassPair) enum1.nextElement();
        String resourceName = ncPair.getName();
        ResourceInfo childResourceInfo =
            new ResourceInfo(resourceName, directory);

        String trimmed = resourceName;
        if (trimmed.equalsIgnoreCase("WEB-INF") ||
            trimmed.equalsIgnoreCase("META-INF") ||
            trimmed.equalsIgnoreCase(localXsltFile)) {
          continue;
        }

        sb.append("<entry");
        sb.append(" type='")
            .append(childResourceInfo.collection ? "dir" : "file")
            .append("'");
        sb.append(" urlPath='")
            .append(rewriteUrl(contextPath))
            .append(rewriteUrl(resourceInfo.path + resourceName))
            .append(childResourceInfo.collection ? "/" : "")
            .append("'");
        if (!childResourceInfo.collection) {
          sb.append(" size='")
              .append(renderSize(childResourceInfo.length))
              .append("'");
        }
        sb.append(" date='")
            .append(childResourceInfo.httpDate)
            .append("'");

        sb.append(">");
        sb.append(trimmed);
        if (childResourceInfo.collection) {
          sb.append("/");
        }
        sb.append("</entry>");

      }

    } catch (NamingException e) {
      // Something went wrong
      e.printStackTrace();
    }

    sb.append("</entries>");

    String readme = getReadme(resourceInfo.directory);

    if (readme != null) {
      sb.append("<readme><![CDATA[");
      sb.append(readme);
      sb.append("]]></readme>");
    }

    sb.append("</listing>");

    try {
      TransformerFactory tFactory = TransformerFactory.newInstance();
      Source xmlSource = new StreamSource(new StringReader(sb.toString()));
      Source xslSource = new StreamSource(xsltInputStream);
      Transformer transformer = tFactory.newTransformer(xslSource);

      ByteArrayOutputStream stream = new ByteArrayOutputStream();
      OutputStreamWriter osWriter = new OutputStreamWriter(stream, "UTF8");
      StreamResult out = new StreamResult(osWriter);
      transformer.transform(xmlSource, out);
      osWriter.flush();
      return (new ByteArrayInputStream(stream.toByteArray()));
    } catch (Exception e) {
      log("directory transform failure: " + e.getMessage());
      return renderHtml(contextPath, resourceInfo);
    }
  }


  /**
   * Return an InputStream to an HTML representation of the contents
   * of this directory.
   *
   *@param  contextPath   Context path to which our internal paths are
   *  relative
   *@param  resourceInfo  Description of the Parameter
   *@return               Description of the Return Value
   */
  protected InputStream renderHtml
      (String contextPath, ResourceInfo resourceInfo) {

    String name = resourceInfo.path;

    // Number of characters to trim from the beginnings of filenames
    int trim = name.length();
    if (!name.endsWith("/")) {
      trim += 1;
    }
    if (name.equals("/")) {
      trim = 1;
    }

    // Prepare a writer to a buffered area
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    OutputStreamWriter osWriter = null;
    try {
      osWriter = new OutputStreamWriter(stream, "UTF8");
    } catch (Exception e) {
      // Should never happen
      osWriter = new OutputStreamWriter(stream);
    }
    PrintWriter writer = new PrintWriter(osWriter);

    StringBuffer sb = new StringBuffer();

    // Render the page header
    sb.append("<html>\r\n");
    sb.append("<head>\r\n");
    sb.append("<title>");
    sb.append(sm.getString("directory.title", name));
    sb.append("</title>\r\n");
    sb.append("<STYLE><!--");
    sb.append(org.apache.catalina.util.TomcatCSS.TOMCAT_CSS);
    sb.append("--></STYLE> ");
    sb.append("</head>\r\n");
    sb.append("<body>");
    sb.append("<h1>");
    sb.append(sm.getString("directory.title", name));

    // Render the link to our parent (if required)
    String parentDirectory = name;
    if (parentDirectory.endsWith("/")) {
      parentDirectory =
          parentDirectory.substring(0, parentDirectory.length() - 1);
    }
    int slash = parentDirectory.lastIndexOf('/');
    if (slash >= 0) {
      String parent = name.substring(0, slash);
      sb.append(" - <a href=\"");
      sb.append(rewriteUrl(contextPath));
      if (parent.equals("")) {
        parent = "/";
      }
      sb.append(rewriteUrl(parent));
      if (!parent.endsWith("/")) {
        sb.append("/");
      }
      sb.append("\">");
      sb.append("<b>");
      sb.append(sm.getString("directory.parent", parent));
      sb.append("</b>");
      sb.append("</a>");
    }

    sb.append("</h1>");
    sb.append("<HR size=\"1\" noshade=\"noshade\">");

    sb.append("<table width=\"100%\" cellspacing=\"0\"" +
        " cellpadding=\"5\" align=\"center\">\r\n");

    // Render the column headings
    sb.append("<tr>\r\n");
    sb.append("<td align=\"left\"><font size=\"+1\"><strong>");
    sb.append(sm.getString("directory.filename"));
    sb.append("</strong></font></td>\r\n");
    sb.append("<td align=\"center\"><font size=\"+1\"><strong>");
    sb.append(sm.getString("directory.size"));
    sb.append("</strong></font></td>\r\n");
    sb.append("<td align=\"right\"><font size=\"+1\"><strong>");
    sb.append(sm.getString("directory.lastModified"));
    sb.append("</strong></font></td>\r\n");
    sb.append("</tr>");

    try {

      // Render the directory entries within this directory
      DirContext directory = resourceInfo.directory;
      NamingEnumeration enum1 =
          resourceInfo.resources.list(resourceInfo.path);
      boolean shade = false;
      while (enum1.hasMoreElements()) {

        NameClassPair ncPair = (NameClassPair) enum1.nextElement();
        String resourceName = ncPair.getName();
        ResourceInfo childResourceInfo =
            new ResourceInfo(resourceName, directory);

        String trimmed = resourceName;
        if (trimmed.equalsIgnoreCase("WEB-INF") ||
            trimmed.equalsIgnoreCase("META-INF")) {
          continue;
        }

        sb.append("<tr");
        if (shade) {
          sb.append(" bgcolor=\"#eeeeee\"");
        }
        sb.append(">\r\n");
        shade = !shade;

        sb.append("<td align=\"left\">&nbsp;&nbsp;\r\n");
        sb.append("<a href=\"");
        sb.append(rewriteUrl(contextPath));
        resourceName = rewriteUrl(name + resourceName);
        sb.append(resourceName);
        if (childResourceInfo.collection) {
          sb.append("/");
        }
        sb.append("\"><tt>");
        sb.append(trimmed);
        if (childResourceInfo.collection) {
          sb.append("/");
        }
        sb.append("</tt></a></td>\r\n");

        sb.append("<td align=\"right\"><tt>");
        if (childResourceInfo.collection) {
          sb.append("&nbsp;");
        } else {
          sb.append(renderSize(childResourceInfo.length));
        }
        sb.append("</tt></td>\r\n");

        sb.append("<td align=\"right\"><tt>");
        sb.append(childResourceInfo.httpDate);
        sb.append("</tt></td>\r\n");

        sb.append("</tr>\r\n");
      }

    } catch (NamingException e) {
      // Something went wrong
      e.printStackTrace();
    }

    // Render the page footer
    sb.append("</table>\r\n");

    sb.append("<HR size=\"1\" noshade=\"noshade\">");

    String readme = getReadme(resourceInfo.directory);
    if (readme != null) {
      sb.append(readme);
      sb.append("<HR size=\"1\" noshade=\"noshade\">");
    }

    sb.append("<h3>").append(ServerInfo.getServerInfo()).append("</h3>");
    sb.append("</body>\r\n");
    sb.append("</html>\r\n");

    // Return an input stream to the underlying bytes
    writer.write(sb.toString());
    writer.flush();
    return (new ByteArrayInputStream(stream.toByteArray()));
  }


  /**
   * Render the specified file size (in bytes).
   *
   *@param  size  File size (in bytes)
   *@return       Description of the Return Value
   */
  protected String renderSize(long size) {

    long leftSide = size / 1024;
    long rightSide = (size % 1024) / 103;
    // Makes 1 digit
    if ((leftSide == 0) && (rightSide == 0) && (size > 0)) {
      rightSide = 1;
    }

    return ("" + leftSide + "." + rightSide + " kb");
  }


  /**
   * Get the readme file as a string.
   *
   *@param  directory  Description of the Parameter
   *@return            The readme value
   */
  protected String getReadme(DirContext directory) {
    if (readmeFile != null) {
      try {
        Object obj = directory.lookup(readmeFile);

        if (obj != null && obj instanceof Resource) {
          StringWriter buffer = new StringWriter();
          InputStream is = ((Resource) obj).streamContent();
          copyRange(new InputStreamReader(is),
              new PrintWriter(buffer));

          return buffer.toString();
        }
      } catch (Throwable e) {
        ;
        /*
         *  Should only be IOException or NamingException
         *  can be ignored
         */
      }
    }

    return null;
  }


  /**
   * Return the xsl template inputstream (if possible)
   *
   *@param  directory  Description of the Parameter
   *@return            Description of the Return Value
   */
  protected InputStream findXsltInputStream(DirContext directory) {
    if (localXsltFile != null) {
      try {
        Object obj = directory.lookup(localXsltFile);
        if (obj != null && obj instanceof Resource) {
          InputStream is = ((Resource) obj).streamContent();
          if (is != null) {
            return is;
          }
        }
      } catch (Throwable e) {
        ;
        /*
         *  Should only be IOException or NamingException
         *  can be ignored
         */
      }
    }

    /*
     *  Open and read in file in one fell swoop to reduce chance
     *  chance of leaving handle open.
     */
    if (globalXsltFile != null) {
      FileInputStream fis = null;

      try {
        File f = new File(globalXsltFile);
        if (f.exists()) {
          fis = new FileInputStream(f);
          byte b[] = new byte[(int) f.length()];
          /*
           *  danger!
           */
          fis.read(b);
          return new ByteArrayInputStream(b);
        }
      } catch (Throwable e) {
        log("This shouldn't happen (?)...", e);
        return null;
      } finally {
        try {
          if (fis != null) {
            fis.close();
          }
        } catch (Throwable e) {
          ;
        }
      }
    }

    return null;
  }


  // -------------------------------------------------------- Private Methods

  /**
   * Check if the if-match condition is satisfied.
   *
   *@param  request          The servlet request we are processing
   *@param  response         The servlet response we are creating
   *@param  resourceInfo     File object
   *@return                  boolean true if the resource meets the specified condition,
   * and false if the condition is not satisfied, in which case request
   * processing is stopped
   *@exception  IOException  Description of the Exception
   */
  private boolean checkIfMatch(HttpServletRequest request,
      HttpServletResponse response,
      ResourceInfo resourceInfo)
       throws IOException {

    String eTag = getETag(resourceInfo);
    String headerValue = request.getHeader("If-Match");
    if (headerValue != null) {
      if (headerValue.indexOf('*') == -1) {

        StringTokenizer commaTokenizer = new StringTokenizer
            (headerValue, ",");
        boolean conditionSatisfied = false;

        while (!conditionSatisfied && commaTokenizer.hasMoreTokens()) {
          String currentToken = commaTokenizer.nextToken();
          if (currentToken.trim().equals(eTag)) {
            conditionSatisfied = true;
          }
        }

        // If none of the given ETags match, 412 Precodition failed is
        // sent back
        if (!conditionSatisfied) {
          response.sendError
              (HttpServletResponse.SC_PRECONDITION_FAILED);
          return false;
        }
      }
    }
    return true;
  }


  /**
   * Check if the if-modified-since condition is satisfied.
   *
   *@param  request          The servlet request we are processing
   *@param  response         The servlet response we are creating
   *@param  resourceInfo     File object
   *@return                  boolean true if the resource meets the specified condition,
   * and false if the condition is not satisfied, in which case request
   * processing is stopped
   *@exception  IOException  Description of the Exception
   */
  private boolean checkIfModifiedSince(HttpServletRequest request,
      HttpServletResponse response,
      ResourceInfo resourceInfo)
       throws IOException {
    try {
      long headerValue = request.getDateHeader("If-Modified-Since");
      long lastModified = resourceInfo.date;
      if (headerValue != -1) {

        // If an If-None-Match header has been specified, if modified since
        // is ignored.
        if ((request.getHeader("If-None-Match") == null)
             && (lastModified <= headerValue + 1000)) {
          // The entity has not been modified since the date
          // specified by the client. This is not an error case.
          response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
          return false;
        }
      }
    } catch (IllegalArgumentException illegalArgument) {
      return true;
    }
    return true;
  }


  /**
   * Check if the if-none-match condition is satisfied.
   *
   *@param  request          The servlet request we are processing
   *@param  response         The servlet response we are creating
   *@param  resourceInfo     File object
   *@return                  boolean true if the resource meets the specified condition,
   * and false if the condition is not satisfied, in which case request
   * processing is stopped
   *@exception  IOException  Description of the Exception
   */
  private boolean checkIfNoneMatch(HttpServletRequest request,
      HttpServletResponse response,
      ResourceInfo resourceInfo)
       throws IOException {

    String eTag = getETag(resourceInfo);
    String headerValue = request.getHeader("If-None-Match");
    if (headerValue != null) {

      boolean conditionSatisfied = false;

      if (!headerValue.equals("*")) {

        StringTokenizer commaTokenizer =
            new StringTokenizer(headerValue, ",");

        while (!conditionSatisfied && commaTokenizer.hasMoreTokens()) {
          String currentToken = commaTokenizer.nextToken();
          if (currentToken.trim().equals(eTag)) {
            conditionSatisfied = true;
          }
        }

      } else {
        conditionSatisfied = true;
      }

      if (conditionSatisfied) {

        // For GET and HEAD, we should respond with
        // 304 Not Modified.
        // For every other method, 412 Precondition Failed is sent
        // back.
        if (("GET".equals(request.getMethod()))
             || ("HEAD".equals(request.getMethod()))) {
          response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
          return false;
        } else {
          response.sendError
              (HttpServletResponse.SC_PRECONDITION_FAILED);
          return false;
        }
      }
    }
    return true;
  }


  /**
   * Check if the if-unmodified-since condition is satisfied.
   *
   *@param  request          The servlet request we are processing
   *@param  response         The servlet response we are creating
   *@param  resourceInfo     File object
   *@return                  boolean true if the resource meets the specified condition,
   * and false if the condition is not satisfied, in which case request
   * processing is stopped
   *@exception  IOException  Description of the Exception
   */
  private boolean checkIfUnmodifiedSince(HttpServletRequest request,
      HttpServletResponse response,
      ResourceInfo resourceInfo)
       throws IOException {
    try {
      long lastModified = resourceInfo.date;
      long headerValue = request.getDateHeader("If-Unmodified-Since");
      if (headerValue != -1) {
        if (lastModified > (headerValue + 1000)) {
          // The entity has not been modified since the date
          // specified by the client. This is not an error case.
          response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED);
          return false;
        }
      }
    } catch (IllegalArgumentException illegalArgument) {
      return true;
    }
    return true;
  }


  /**
   * Copy the contents of the specified input stream to the specified
   * output stream, and ensure that both streams are closed before returning
   * (even in the face of an exception).
   *
   *@param  ostream          The output stream to write to
   *@param  resourceInfo     Description of the Parameter
   *@exception  IOException  if an input/output error occurs
   */
  private void copy(ResourceInfo resourceInfo, ServletOutputStream ostream)
       throws IOException {

    IOException exception = null;

    // Optimization: If the binary content has already been loaded, send
    // it directly
    if (resourceInfo.file != null) {
      byte buffer[] = resourceInfo.file.getContent();
      if (buffer != null) {
        ostream.write(buffer, 0, buffer.length);
        return;
      }
    }

    InputStream resourceInputStream = resourceInfo.getStream();
    InputStream istream = new BufferedInputStream
        (resourceInputStream, input);

    // Copy the input stream to the output stream
    exception = copyRange(istream, ostream);

    // Clean up the input stream
    try {
      istream.close();
    } catch (Throwable t) {
      ;
    }

    // Rethrow any exception that has occurred
    if (exception != null) {
      throw exception;
    }
  }


  /**
   * Copy the contents of the specified input stream to the specified
   * output stream, and ensure that both streams are closed before returning
   * (even in the face of an exception).
   *
   *@param  writer           The writer to write to
   *@param  resourceInfo     Description of the Parameter
   *@exception  IOException  if an input/output error occurs
   */
  private void copy(ResourceInfo resourceInfo, PrintWriter writer)
       throws IOException {

    IOException exception = null;

    InputStream resourceInputStream = resourceInfo.getStream();
    // FIXME : i18n ?
    Reader reader = new InputStreamReader(resourceInputStream);

    // Copy the input stream to the output stream
    exception = copyRange(reader, writer);

    // Clean up the reader
    try {
      reader.close();
    } catch (Throwable t) {
      ;
    }

    // Rethrow any exception that has occurred
    if (exception != null) {
      throw exception;
    }
  }


  /**
   * Copy the contents of the specified input stream to the specified
   * output stream, and ensure that both streams are closed before returning
   * (even in the face of an exception).
   *
   *@param  resourceInfo     The ResourceInfo object
   *@param  ostream          The output stream to write to
   *@param  range            Range the client wanted to retrieve
   *@exception  IOException  if an input/output error occurs
   */
  private void copy(ResourceInfo resourceInfo, ServletOutputStream ostream,
      Range range)
       throws IOException {

    IOException exception = null;

    InputStream resourceInputStream = resourceInfo.getStream();
    InputStream istream =
        new BufferedInputStream(resourceInputStream, input);
    exception = copyRange(istream, ostream, range.start, range.end);

    // Clean up the input stream
    try {
      istream.close();
    } catch (Throwable t) {
      ;
    }

    // Rethrow any exception that has occurred
    if (exception != null) {
      throw exception;
    }
  }


  /**
   * Copy the contents of the specified input stream to the specified
   * output stream, and ensure that both streams are closed before returning
   * (even in the face of an exception).
   *
   *@param  resourceInfo     The ResourceInfo object
   *@param  writer           The writer to write to
   *@param  range            Range the client wanted to retrieve
   *@exception  IOException  if an input/output error occurs
   */
  private void copy(ResourceInfo resourceInfo, PrintWriter writer,
      Range range)
       throws IOException {

    IOException exception = null;

    InputStream resourceInputStream = resourceInfo.getStream();
    Reader reader = new InputStreamReader(resourceInputStream);
    exception = copyRange(reader, writer, range.start, range.end);

    // Clean up the input stream
    try {
      reader.close();
    } catch (Throwable t) {
      ;
    }

    // Rethrow any exception that has occurred
    if (exception != null) {
      throw exception;
    }
  }


  /**
   * Copy the contents of the specified input stream to the specified
   * output stream, and ensure that both streams are closed before returning
   * (even in the face of an exception).
   *
   *@param  resourceInfo     The ResourceInfo object
   *@param  ostream          The output stream to write to
   *@param  ranges           Enumeration of the ranges the client wanted to retrieve
   *@param  contentType      Content type of the resource
   *@exception  IOException  if an input/output error occurs
   */
  private void copy(ResourceInfo resourceInfo, ServletOutputStream ostream,
      Enumeration ranges, String contentType)
       throws IOException {

    IOException exception = null;

    while ((exception == null) && (ranges.hasMoreElements())) {

      InputStream resourceInputStream = resourceInfo.getStream();
      InputStream istream =
      // FIXME: internationalization???????
          new BufferedInputStream(resourceInputStream, input);

      Range currentRange = (Range) ranges.nextElement();

      // Writing MIME header.
      ostream.println();
      ostream.println("--" + mimeSeparation);
      if (contentType != null) {
        ostream.println("Content-Type: " + contentType);
      }
      ostream.println("Content-Range: bytes " + currentRange.start
           + "-" + currentRange.end + "/"
           + currentRange.length);
      ostream.println();

      // Printing content
      exception = copyRange(istream, ostream, currentRange.start,
          currentRange.end);

      try {
        istream.close();
      } catch (Throwable t) {
        ;
      }

    }

    ostream.println();
    ostream.print("--" + mimeSeparation + "--");

    // Rethrow any exception that has occurred
    if (exception != null) {
      throw exception;
    }
  }


  /**
   * Copy the contents of the specified input stream to the specified
   * output stream, and ensure that both streams are closed before returning
   * (even in the face of an exception).
   *
   *@param  resourceInfo     The ResourceInfo object
   *@param  writer           The writer to write to
   *@param  ranges           Enumeration of the ranges the client wanted to retrieve
   *@param  contentType      Content type of the resource
   *@exception  IOException  if an input/output error occurs
   */
  private void copy(ResourceInfo resourceInfo, PrintWriter writer,
      Enumeration ranges, String contentType)
       throws IOException {

    IOException exception = null;

    while ((exception == null) && (ranges.hasMoreElements())) {

      InputStream resourceInputStream = resourceInfo.getStream();
      Reader reader = new InputStreamReader(resourceInputStream);

      Range currentRange = (Range) ranges.nextElement();

      // Writing MIME header.
      writer.println();
      writer.println("--" + mimeSeparation);
      if (contentType != null) {
        writer.println("Content-Type: " + contentType);
      }
      writer.println("Content-Range: bytes " + currentRange.start
           + "-" + currentRange.end + "/"
           + currentRange.length);
      writer.println();

      // Printing content
      exception = copyRange(reader, writer, currentRange.start,
          currentRange.end);

      try {
        reader.close();
      } catch (Throwable t) {
        ;
      }

    }

    writer.println();
    writer.print("--" + mimeSeparation + "--");

    // Rethrow any exception that has occurred
    if (exception != null) {
      throw exception;
    }
  }


  /**
   * Copy the contents of the specified input stream to the specified
   * output stream, and ensure that both streams are closed before returning
   * (even in the face of an exception).
   *
   *@param  istream  The input stream to read from
   *@param  ostream  The output stream to write to
   *@return          Exception which occurred during processing
   */
  private IOException copyRange(InputStream istream,
      ServletOutputStream ostream) {

    // Copy the input stream to the output stream
    IOException exception = null;
    byte buffer[] = new byte[input];
    int len = buffer.length;
    while (true) {
      try {
        len = istream.read(buffer);
        if (len == -1) {
          break;
        }
        ostream.write(buffer, 0, len);
      } catch (IOException e) {
        exception = e;
        len = -1;
        break;
      }
    }
    return exception;
  }


  /**
   * Copy the contents of the specified input stream to the specified
   * output stream, and ensure that both streams are closed before returning
   * (even in the face of an exception).
   *
   *@param  reader  The reader to read from
   *@param  writer  The writer to write to
   *@return         Exception which occurred during processing
   */
  private IOException copyRange(Reader reader, PrintWriter writer) {

    // Copy the input stream to the output stream
    IOException exception = null;
    char buffer[] = new char[input];
    int len = buffer.length;
    while (true) {
      try {
        len = reader.read(buffer);
        if (len == -1) {
          break;
        }
        writer.write(buffer, 0, len);
      } catch (IOException e) {
        exception = e;
        len = -1;
        break;
      }
    }
    return exception;
  }


  /**
   * Copy the contents of the specified input stream to the specified
   * output stream, and ensure that both streams are closed before returning
   * (even in the face of an exception).
   *
   *@param  istream  The input stream to read from
   *@param  ostream  The output stream to write to
   *@param  start    Start of the range which will be copied
   *@param  end      End of the range which will be copied
   *@return          Exception which occurred during processing
   */
  private IOException copyRange(InputStream istream,
      ServletOutputStream ostream,
      long start, long end) {

    if (debug > 10) {
      log("Serving bytes:" + start + "-" + end);
    }

    try {
      istream.skip(start);
    } catch (IOException e) {
      return e;
    }

    IOException exception = null;
    long bytesToRead = end - start + 1;

    byte buffer[] = new byte[input];
    int len = buffer.length;
    while ((bytesToRead > 0) && (len >= buffer.length)) {
      try {
        len = istream.read(buffer);
        if (bytesToRead >= len) {
          ostream.write(buffer, 0, len);
          bytesToRead -= len;
        } else {
          ostream.write(buffer, 0, (int) bytesToRead);
          bytesToRead = 0;
        }
      } catch (IOException e) {
        exception = e;
        len = -1;
      }
      if (len < buffer.length) {
        break;
      }
    }

    return exception;
  }


  /**
   * Copy the contents of the specified input stream to the specified
   * output stream, and ensure that both streams are closed before returning
   * (even in the face of an exception).
   *
   *@param  reader  The reader to read from
   *@param  writer  The writer to write to
   *@param  start   Start of the range which will be copied
   *@param  end     End of the range which will be copied
   *@return         Exception which occurred during processing
   */
  private IOException copyRange(Reader reader, PrintWriter writer,
      long start, long end) {

    try {
      reader.skip(start);
    } catch (IOException e) {
      return e;
    }

    IOException exception = null;
    long bytesToRead = end - start + 1;

    char buffer[] = new char[input];
    int len = buffer.length;
    while ((bytesToRead > 0) && (len >= buffer.length)) {
      try {
        len = reader.read(buffer);
        if (bytesToRead >= len) {
          writer.write(buffer, 0, len);
          bytesToRead -= len;
        } else {
          writer.write(buffer, 0, (int) bytesToRead);
          bytesToRead = 0;
        }
      } catch (IOException e) {
        exception = e;
        len = -1;
      }
      if (len < buffer.length) {
        break;
      }
    }

    return exception;
  }



  // ------------------------------------------------------ Range Inner Class

  /**
   *  Description of the Class
   *
   *@author     ananth
   *@created    October 26, 2004
   */
  private class Range {

    public long start;
    public long end;
    public long length;


    /**
     * Validate range.
     *
     *@return    Description of the Return Value
     */
    public boolean validate() {
      if (end >= length) {
        end = length - 1;
      }
      return ((start >= 0) && (end >= 0) && (start <= end)
           && (length > 0));
    }


    /**
     *  Description of the Method
     */
    public void recycle() {
      start = 0;
      end = 0;
      length = 0;
    }

  }


  // ----------------------------------------------  ResourceInfo Inner Class


  /**
   *  Description of the Class
   *
   *@author     ananth
   *@created    October 26, 2004
   */
  protected class ResourceInfo {

    /**
     * Constructor.
     *
     *@param  path       Description of the Parameter
     *@param  resources  Description of the Parameter
     */
    public ResourceInfo(String path, DirContext resources) {
      set(path, resources);
    }


    /**
     *Constructor for the ResourceInfo object
     *
     *@param  path                       Description of the Parameter
     *@param  resources                  Description of the Parameter
     *@param  db                         Description of the Parameter
     *@param  thisSystem                 Description of the Parameter
     *@exception  SQLException           Description of the Exception
     *@exception  FileNotFoundException  Description of the Exception
     */
    public ResourceInfo(SystemStatus thisSystem, Connection db, String path, ModuleContext resources)
         throws SQLException, FileNotFoundException {
      set(thisSystem, db, path, resources);
    }


    public Object object;
    public ModuleContext dir;
    public DirContext directory;
    public Resource file;
    public Attributes attributes;
    public String path;
    public long creationDate;
    public String httpDate;
    public long date;
    public long length;
    public boolean collection;
    public String weakETag;
    public String strongETag;
    public boolean exists;
    public ModuleContext resources;
    protected InputStream is;


    /**
     *  Description of the Method
     */
    public void recycle() {
      object = null;
      directory = null;
      file = null;
      attributes = null;
      path = null;
      creationDate = 0;
      httpDate = null;
      date = 0;
      length = -1;
      collection = true;
      weakETag = null;
      strongETag = null;
      exists = false;
      resources = null;
      is = null;
    }


    /**
     *  Description of the Method
     *
     *@param  path       Description of the Parameter
     *@param  resources  Description of the Parameter
     */
    public void set(String path, DirContext resources) { }


    /**
     *  Description of the Method
     *
     *@param  path                       Description of the Parameter
     *@param  resources                  Description of the Parameter
     *@param  db                         Description of the Parameter
     *@param  thisSystem                 Description of the Parameter
     *@exception  SQLException           Description of the Exception
     *@exception  FileNotFoundException  Description of the Exception
     */
    public void set(SystemStatus thisSystem, Connection db, String path, ModuleContext resources) throws SQLException, FileNotFoundException {
      recycle();

      this.path = path;
      this.resources = resources;
      exists = true;
      try {
        object = resources.lookup(thisSystem, db, path);
        if (object instanceof Resource) {
          file = (Resource) object;
          collection = false;
        } else if (object instanceof ModuleContext) {
          dir = (ModuleContext) object;
          collection = true;
        } else {
          // Don't know how to serve another object type
          exists = false;
        }
      } catch (NamingException e) {
        //e.printStackTrace(System.out);
        exists = false;
      }
      if (exists) {
        try {
          attributes = resources.getAttributes(thisSystem, db, path);
          if (attributes instanceof ResourceAttributes) {
            ResourceAttributes tempAttrs =
                (ResourceAttributes) attributes;
            Date tempDate = tempAttrs.getCreationDate();
            if (tempDate != null) {
              creationDate = tempDate.getTime();
            }
            tempDate = tempAttrs.getLastModifiedDate();
            if (tempDate != null) {
              date = tempDate.getTime();
              httpDate =
                  FastHttpDateFormat.formatDate(date, null);
            } else {
              httpDate = FastHttpDateFormat.getCurrentDate();
            }
            weakETag = tempAttrs.getETag();
            strongETag = tempAttrs.getETag(true);
            length = tempAttrs.getContentLength();
          }
        } catch (NamingException e) {
          // Shouldn't happen, the implementation of the DirContext
          // is probably broken
          //e.printStackTrace(System.out);
          exists = false;
        }
      }
    }


    /**
     * Test if the associated resource exists.
     *
     *@return    Description of the Return Value
     */
    public boolean exists() {
      return exists;
    }


    /**
     * String representation.
     *
     *@return    Description of the Return Value
     */
    public String toString() {
      return path;
    }


    /**
     * Set IS.
     *
     *@param  is  The new stream value
     */
    public void setStream(InputStream is) {
      this.is = is;
    }


    /**
     * Get IS from resource.
     *
     *@return                  The stream value
     *@exception  IOException  Description of the Exception
     */
    public InputStream getStream()
         throws IOException {
      if (is != null) {
        return is;
      }
      if (file != null) {
        return (file.streamContent());
      } else {
        return null;
      }
    }
  }
}

