/*
 *  Copyright(c) 2006 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
package org.aspcfs.modules.website.icelet;

import com.zeroio.iteam.base.FileItem;
import org.aspcfs.modules.quotes.base.QuoteProductBean;
import org.aspcfs.modules.website.utils.PortletUtils;
import org.aspcfs.utils.SMTPMessage;
import org.aspcfs.utils.Template;
import org.aspcfs.utils.StringUtils;

import javax.portlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Description of the Class
 *
 * @author mrajkowski
 * @version $Id: Exp $
 * @created April 28, 2006
 */
public class CartPortlet extends GenericPortlet {

  /**
   * Description of the Field
   */
  public final static String fs = System.getProperty("file.separator");
  /**
   * Description of the Field
   */
  public static String EMAIL_TO = "6062311";
  /**
   * Description of the Field
   */
  public static String EMAIL_SUBJECT = "6062312";
  /**
   * Description of the Field
   */
  public static String MESSAGE_TO_REVIEWER = "6062313";
  /**
   * Description of the Field
   */
  public static String MESSAGE_TO_REQUESTER = "6062314";

  private final static String VIEW_PAGE1 = "/portlets/cart/cart.jsp";
  private final static String VIEW_PAGE2 = "/portlets/cart/cart_sent.jsp";


  /**
   * Constructor for the CartPortlet object
   */
  public CartPortlet() {
  }


  /**
   * Description of the Method
   *
   * @param request  Description of the Parameter
   * @param response Description of the Parameter
   * @throws PortletException Description of the Exception
   * @throws IOException      Description of the Exception
   */
  public void doView(RenderRequest request, RenderResponse response)
      throws PortletException, IOException {

    if (System.getProperty("DEBUG") != null) {
      System.out.println("CartPortlet-> PortletMode: "
          + request.getPortletMode());
    }
    String viewType = (String) request.getParameter("viewType");
    if (request.getPortletMode() == PortletMode.VIEW) {
      // Show the HTML content
      if ("email".equals(viewType)) {
        PortletRequestDispatcher requestDispatcher = getPortletContext()
            .getRequestDispatcher(VIEW_PAGE2);
        requestDispatcher.include(request, response);
      } else {
        buildQuoteList(request, response);
        PortletRequestDispatcher requestDispatcher = getPortletContext()
            .getRequestDispatcher(VIEW_PAGE1);
        requestDispatcher.include(request, response);
      }
    } else if (request.getPortletMode() == new PortletMode("config")) {
      // Show the HTML content editor
      // TODO: Use the PROPERTY_HTMLTEXT
      PortletRequestDispatcher requestDispatcher = getPortletContext()
          .getRequestDispatcher(VIEW_PAGE2);
      requestDispatcher.include(request, response);
    }
  }

  //??Kailash - Why does this method exist

  /**
   * Description of the Method
   *
   * @param request  Description of the Parameter
   * @param response Description of the Parameter
   */
  private void buildQuoteList(RenderRequest request, RenderResponse response) {
  }


  /**
   * Description of the Method
   *
   * @param request  Description of the Parameter
   * @param response Description of the Parameter
   * @throws PortletException Description of the Exception
   * @throws IOException      Description of the Exception
   */
  public void processAction(ActionRequest request, ActionResponse response)
      throws PortletException, IOException {
    if (request.getParameter("save") != null) {
      saveCart(request, response);
    } else if (request.getParameter("clear") != null) {
      clearCart(request, response);
    } else if (request.getParameter("email") != null) {
      emailCart(request, response);
    }
  }


  /**
   * @param request
   * @param response
   */
  private void clearCart(ActionRequest request, ActionResponse response) {
    if (((HttpServletRequest) request).getSession()
        .getAttribute("CartBean") != null) {
      ((HttpServletRequest) request).getSession().removeAttribute(
          "CartBean");
    }
  }


  /**
   * Description of the Method
   *
   * @param request  Description of the Parameter
   * @param response Description of the Parameter
   */
  private void emailCart(ActionRequest request, ActionResponse response) {
    String userName = request.getParameter("userName");
    String userEmail = request.getParameter("userEmail");
    String comments = request.getParameter("comments");
    HashMap cartBean = (HashMap) ((HttpServletRequest) request)
        .getSession().getAttribute("CartBean");

    if (cartBean != null && cartBean.size() > 0) {

      SMTPMessage mailToReviewer = new SMTPMessage();
      mailToReviewer.setHost(PortletUtils.getApplicationPrefs(request,
          "MAILSERVER"));
      mailToReviewer.setType("text/html");
      mailToReviewer.addTo(request.getPreferences().getValue(EMAIL_TO, ""));
      mailToReviewer.setFrom(userEmail);
      mailToReviewer.addReplyTo(userEmail);
      mailToReviewer.setSubject(request.getPreferences().getValue(EMAIL_SUBJECT, "Quote request"));

      Template template = new Template();
      template.setText((String) request.getPreferences()
          .getValue(
              MESSAGE_TO_REVIEWER,
              userName
                  + " has requested a quote for the following items:"));
      template.addParseElement("${quoteRequester.name}", userName);
      String messageHeader = template.getParsedText();
      getMailBody(cartBean, mailToReviewer, request, messageHeader);
      if (mailToReviewer.send() == 2) {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("CartPortlet-> Send error: "
              + mailToReviewer.getErrorMsg() + "<br><br>");
        }
        System.err.println(mailToReviewer.getErrorMsg());
      } else {
        ((HttpServletRequest) request).getSession().removeAttribute(
            "CartBean");
        response.setRenderParameter("viewType", "email");
      }

      SMTPMessage mailToRequester = new SMTPMessage();
      mailToRequester.setHost(PortletUtils.getApplicationPrefs(request,
          "MAILSERVER"));
      mailToRequester.setType("text/html");
      mailToRequester.addTo(userEmail);
      mailToRequester.setSubject((String) request.getPreferences()
          .getValue(EMAIL_SUBJECT, "Quote request"));

      template = new Template();
      template.setText(request.getPreferences().getValue(
              MESSAGE_TO_REQUESTER,
              userName
                  + " has requested a quote for the following items:"));
      template.addParseElement("${quoteReviewer.email}", request.getPreferences().getValue(
          EMAIL_TO, ""));
      messageHeader = template.getParsedText();

      getMailBody(cartBean, mailToRequester, request, messageHeader);
      if (mailToRequester.send() == 2) {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("CartPortlet-> Send error: "
              + mailToRequester.getErrorMsg() + "<br><br>");
        }
        System.err.println(mailToRequester.getErrorMsg());
      } else {
        ((HttpServletRequest) request).getSession().removeAttribute(
            "CartBean");
        response.setRenderParameter("viewType", "email");
      }
    }
  }


  /**
   * @param cartBean
   * @param mail
   * @param request
   * @param message  Description of the Parameter
   */
  private void getMailBody(HashMap cartBean, SMTPMessage mail,
                           ActionRequest request, String message) {
    StringBuffer body = new StringBuffer();
    Connection db = PortletUtils.getConnection(request);
    String path = PortletUtils.getApplicationPrefs(request, "FILELIBRARY");

    body.append(message);
    body.append("<table>");
    Iterator iterator = cartBean.keySet().iterator();
    body.append("<tr>");
    body.append("<th width=\"140\">Image</th>");
    body.append("<th width=\"140\">Name</th>");
    body.append("<th width=\"140\">Quantity</th>");
    body.append("</tr>");
    while (iterator.hasNext()) {
      String productId = (String) iterator.next();
      QuoteProductBean element = (QuoteProductBean) cartBean.get(productId);
      body.append("<tr>");
      body.append("<td width=\"140\">");
      FileItem fileItem;
      try {
        if (element.getProduct().getLargeImageId() != -1) {
          fileItem = new FileItem(db, element.getProduct()
              .getLargeImageId());
          fileItem.setDirectory(PortletUtils.getDbNamePath(request) + "products" + fs);
          String imagePath = fileItem.getFullFilePath();
          mail.addImage("productImage" + productId, "file://" + imagePath);
          body.append("<img src=\"cid:productImage" + productId
              + "\" border=\"0\"><BR>");
        } else {
          body.append("Image not available");
        }
      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      body.append("</td>");
      body.append("<td>");
      body.append("<div>");
      body.append(element.getProduct().getName());
      body.append("</div>");
      body.append("</td>");
      body.append("<td width=\"10\">");
      body.append(element.getQuantity());
      body.append("</td>");
      body.append("</tr>");
    }
    body.append("</table>");
    body.append("<br/>");
    body.append(request.getParameter("comments"));
    mail.setBody(body.toString());
  }


  /**
   *  Description of the Method
   *
   *@param  request   Description of the Parameter
   *@param  response  Description of the Parameter
   */
  private void saveCart(ActionRequest request, ActionResponse response) {
    // TODO Auto-generated method stub
    HashMap cartBean = (HashMap) ((HttpServletRequest) request)
        .getSession().getAttribute("CartBean");
    String[] deleteIds = request.getParameterValues("quoteProductId");
    if (deleteIds != null) {
      for (int i = 0; i < deleteIds.length; i++) {
        cartBean.remove(deleteIds[i]);
      }
    }
    if (cartBean.size() > 0) {
      Iterator quoteProductIterator = cartBean.keySet().iterator();
      while (quoteProductIterator.hasNext()) {
        String productId = (String) quoteProductIterator.next();
        QuoteProductBean quoteProductBean = (QuoteProductBean) cartBean.get(productId);
        String item = request.getParameter("quantity_" + productId);
        quoteProductBean.setQuantity(StringUtils.getIntegerNumber(item));
      }
    } else {
      ((HttpServletRequest) request).getSession().removeAttribute("CartBean");
		}
	}
}

