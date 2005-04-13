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
package org.aspcfs.utils;

import com.zeroio.iteam.base.FileItem;
import com.zeroio.iteam.base.FileItemList;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.*;

/**
 *  A wrapper around Sun's JavaMail API. Makes sending email a snap.
 *
 *@author     mrajkowski
 *@created    July 12, 2001
 *@version    $Id: SMTPMessage.java,v 1.11.4.1 2004/11/29 20:53:41 mrajkowski
 *      Exp $
 */
public class SMTPMessage {

  private String host = "";
  private String from = "";
  private ArrayList to = new ArrayList();
  private ArrayList cc = new ArrayList();
  private ArrayList bcc = new ArrayList();
  private String subject = "";
  private String body = "";
  private String type = "text";
  private String errorMsg = "";
  private HashMap replyTo = new HashMap();
  private FileItemList attachments = null;
  private ArrayList byteArrayAttachments = null;


  /**
   *  Constructor for the SMTPMessage object
   *
   *@since
   */
  public SMTPMessage() { }


  /**
   *  Sets the Host attribute of the SMTPMessage object
   *
   *@param  tmp  The new Host value
   *@since
   */
  public void setHost(String tmp) {
    host = tmp;
  }


  /**
   *  Sets the From attribute of the SMTPMessage object
   *
   *@param  tmp  The new From value
   *@since
   */
  public void setFrom(String tmp) {
    from = tmp;
  }


  /**
   *  Sets the To attribute of the SMTPMessage object
   *
   *@param  tmp  The new To value
   *@since
   */
  public void setTo(String tmp) {
    to.clear();
    if (tmp.indexOf(",") > -1) {
      addMultiple(to, tmp);
    } else {
      addTo(tmp);
    }
  }


  /**
   *  Sets the Cc attribute of the SMTPMessage object
   *
   *@param  tmp  The new Cc value
   *@since
   */
  public void setCc(String tmp) {
    cc.clear();
    if (tmp.indexOf(",") > -1) {
      addMultiple(cc, tmp);
    } else {
      addCc(tmp);
    }
  }


  /**
   *  Sets the bcc attribute of the SMTPMessage object
   *
   *@param  tmp  The new bcc value
   */
  public void setBcc(String tmp) {
    bcc.clear();
    if (tmp.indexOf(",") > -1) {
      addMultiple(bcc, tmp);
    } else {
      addBcc(tmp);
    }
  }


  /**
   *  Sets the Subject attribute of the SMTPMessage object
   *
   *@param  tmp  The new Subject value
   *@since
   */
  public void setSubject(String tmp) {
    subject = tmp;
  }


  /**
   *  Sets the Body attribute of the SMTPMessage object
   *
   *@param  tmp  The new Body value
   *@since
   */
  public void setBody(String tmp) {
    body = tmp;
  }


  /**
   *  Gets the replyTo attribute of the SMTPMessage object
   *
   *@return    The replyTo value
   */
  public HashMap getReplyTo() {
    return replyTo;
  }


  /**
   *  Sets the replyTo attribute of the SMTPMessage object
   *
   *@param  replyTo  The new replyTo value
   */
  public void setReplyTo(HashMap replyTo) {
    this.replyTo = replyTo;
  }


  /**
   *  Sets the attachments attribute of the SMTPMessage object
   *
   *@param  tmp  The new attachments value
   */
  public void setAttachments(FileItemList tmp) {
    this.attachments = tmp;
  }



  /**
   *  Sets the Type attribute of the SMTPMessage object. Use "text" for standard
   *  email. Use "text/html" for sending HTML messages.
   *
   *@param  tmp  The new Type value
   *@since
   */
  public void setType(String tmp) {
    type = tmp;
  }

  public String getSubject() {
    return subject;
  }

  public String getBody() {
    return body;
  }

  /**
   *  Gets the ErrorMsg attribute of the SMTPMessage object
   *
   *@return    The ErrorMsg value
   *@since
   */
  public String getErrorMsg() {
    return errorMsg;
  }


  /**
   *  Adds a feature to the To attribute of the SMTPMessage object
   *
   *@param  tmp  The feature to be added to the To attribute
   *@since
   */
  public void addTo(String tmp) {
    to.add(tmp);
  }


  /**
   *  Adds a feature to the ReplyTo attribute of the SMTPMessage object
   *
   *@param  address  The feature to be added to the ReplyTo attribute
   */
  public void addReplyTo(String address) {
    replyTo.put(address, "");
  }


  /**
   *  Adds a feature to the ReplyTo attribute of the SMTPMessage object
   *
   *@param  address   The feature to be added to the ReplyTo attribute
   *@param  personal  The feature to be added to the ReplyTo attribute
   */
  public void addReplyTo(String address, String personal) {
    replyTo.put(address, personal);
  }


  /**
   *  Adds a feature to the Cc attribute of the SMTPMessage object
   *
   *@param  tmp  The feature to be added to the Cc attribute
   *@since
   */
  public void addCc(String tmp) {
    cc.add(tmp);
  }


  /**
   *  Adds a feature to the Bcc attribute of the SMTPMessage object
   *
   *@param  tmp  The feature to be added to the Bcc attribute
   */
  public void addBcc(String tmp) {
    bcc.add(tmp);
  }


  /**
   *  Adds a feature to the FileAttachment attribute of the SMTPMessage object
   *
   *@param  tmp  The feature to be added to the FileAttachment attribute
   */
  public void addFileAttachment(FileItem tmp) {
    if (attachments == null) {
      attachments = new FileItemList();
    }
    attachments.add(tmp);
  }


  /**
   *  Adds a feature to the ByteArrayAttachment attribute of the SMTPMessage
   *  object
   *
   *@param  fileName  The feature to be added to the ByteArrayAttachment
   *      attribute
   *@param  data      The feature to be added to the ByteArrayAttachment
   *      attribute
   *@param  mimeType  The feature to be added to the ByteArrayAttachment
   *      attribute
   */
  public void addByteArrayAttachment(String fileName, String data, String mimeType) {
    DataSource attachment = new ByteArrayDataSource(fileName, data, mimeType);
    if (byteArrayAttachments == null) {
      byteArrayAttachments = new ArrayList();
    }
    byteArrayAttachments.add(attachment);
  }


  /**
   *  Adds a feature to the ByteArrayAttachment attribute of the SMTPMessage
   *  object
   *
   *@param  fileName  The feature to be added to the ByteArrayAttachment
   *      attribute
   *@param  data      The feature to be added to the ByteArrayAttachment
   *      attribute
   *@param  mimeType  The feature to be added to the ByteArrayAttachment
   *      attribute
   */
  public void addByteArrayAttachment(String fileName, byte[] data, String mimeType) {
    DataSource attachment = new ByteArrayDataSource(fileName, data, mimeType);
    if (byteArrayAttachments == null) {
      byteArrayAttachments = new ArrayList();
    }
    byteArrayAttachments.add(attachment);
  }


  /**
   *  Sends an email based on this objects properties
   *
   *@return    Description of the Returned Value
   */
  public int send() {
    //throws Exception {

    if (host == null || host.equals("")) {
      errorMsg = "Host not specified";
    }
    if (from == null || from.equals("")) {
      errorMsg = "Reply to address not specified";
    }
    if (to.size() == 0) {
      errorMsg = "Recipients not specified";
    }

    if (errorMsg != null && !errorMsg.equals("")) {
      return 1;
    }

    // Get system properties
    Properties props = System.getProperties();

    // Setup mail server
    if (!host.equals("")) {
      props.put("mail.smtp.host", host);
    }

    // Get session
    Session session = Session.getInstance(props, null);

    // Define message
    MimeMessage message = new MimeMessage(session);

    try {
      // Set the sent date
      message.setSentDate(Calendar.getInstance().getTime());

      // Set the from address
      message.setFrom(new InternetAddress(from));

      //Set the Reply to addresses
      if (replyTo.size() > 0) {
        InternetAddress[] tempReply = new InternetAddress[replyTo.size()];
        Iterator i = replyTo.keySet().iterator();
        int count = 0;
        while (i.hasNext()) {
          String address = (String) i.next();
          String personal = (String) replyTo.get(address);
          if (personal != null && !"".equals(personal)) {
            tempReply[count] = new InternetAddress(address, personal);
          } else {
            tempReply[count] = new InternetAddress(address);
          }
          ++count;
        }
        message.setReplyTo(tempReply);
      }

      // Set the to address(es)
      for (int i = 0; i < to.size(); i++) {
        message.addRecipient(Message.RecipientType.TO,
            new InternetAddress((String) to.get(i)));
      }

      // Set the cc address(es)
      for (int i = 0; i < cc.size(); i++) {
        message.addRecipient(Message.RecipientType.CC,
            new InternetAddress((String) cc.get(i)));
      }

      // Set the bcc address(es)
      for (int i = 0; i < bcc.size(); i++) {
        message.addRecipient(Message.RecipientType.BCC,
            new InternetAddress((String) bcc.get(i)));
      }

      // Set the subject
      message.setSubject(subject);

      //Set the content
      if ("text".equals(type) || "text/plain".equals(type)) {
        if (attachments != null || byteArrayAttachments != null) {
          //Send a text message with attachments
          MimeBodyPart messageBodyPart = new MimeBodyPart();
          messageBodyPart.setText(body);
          MimeMultipart multipart = new MimeMultipart();
          multipart.addBodyPart(messageBodyPart);
          //Attach the files to the root
          this.addFileAttachments(multipart);
          //Attach a string as a file attachment
          this.addByteArrayFileAttachments(multipart);
          message.setContent(multipart);
        } else {
          //Send a text message without attachments
          message.setText(body);
        }
      } else if (!"text/html".equals(type)) {
        //A custom message test
        message.setContent(body, type);
      } else {
        //An HTML message with text, html, images, and attachments
        //This is the root of the email in which the various parts will be added
        MimeMultipart mpRoot = new MimeMultipart("mixed");

        //Attach the content to the root
        MimeBodyPart contentPartRoot = new MimeBodyPart();
        MimeMultipart mpContent = new MimeMultipart("alternative");
        contentPartRoot.setContent(mpContent);
        mpRoot.addBodyPart(contentPartRoot);

        //Text message -- always include for those without HTML viewers
        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setText(HTMLUtils.htmlToText(body));
        textPart.setContent(HTMLUtils.htmlToText(body), "text/plain");
        mpContent.addBodyPart(textPart);

        //Html message
        MimeMultipart multipart = new MimeMultipart("related");
        BodyPart messageBodyPart = new MimeBodyPart();
        //TODO: replace image source with embedded tags, put in array
        //messageBodyPart.setContent("<img src=\"cid:memememe\">" + body, "text/html");
        messageBodyPart.setContent(body, "text/html");
        multipart.addBodyPart(messageBodyPart);

        //Process the array tags, either local image or download the image, and embed the content
        /*
         *  BodyPart embeddedBodyPart = new MimeBodyPart();
         *  DataSource fds = new FileDataSource("/home/matt/cfs2/production/webapps/cfs2/images/refresh.gif");
         *  embeddedBodyPart.setDataHandler(new DataHandler(fds));
         *  embeddedBodyPart.setHeader("Content-ID", "<memememe>");
         *  multipart.addBodyPart(embeddedBodyPart);
         */
        //Add the complete html to the content
        MimeBodyPart mbp = new MimeBodyPart();
        mbp.setContent(multipart);
        mpContent.addBodyPart(mbp);

        //Attach the files to the root
        this.addFileAttachments(mpRoot);

        //Attach a string as a file attachment
        this.addByteArrayFileAttachments(mpRoot);

        //Add add the parts to the message
        message.setContent(mpRoot);
      }

      // Send message
      Transport.send(message);
      return 0;
    } catch (javax.mail.MessagingException me) {
      errorMsg = me.toString();
      return 2;
    } catch (java.io.UnsupportedEncodingException ue) {
      errorMsg = ue.toString();
      return 2;
    }
  }


  /**
   *  Adds file attachments to the email message using the FileItem object
   *
   *@param  root                    The feature to be added to the
   *      FileAttachments attribute
   *@exception  MessagingException  Description of the Exception
   */
  private void addFileAttachments(MimeMultipart root) throws MessagingException {
    if (attachments != null) {
      Iterator files = attachments.iterator();
      while (files.hasNext()) {
        FileItem fileItem = (FileItem) files.next();
        BodyPart attachmentBodyPart = new MimeBodyPart();
        FileDataSource source = new FileDataSource(fileItem.getFullFilePath());
        attachmentBodyPart.setDisposition(Part.ATTACHMENT);
        attachmentBodyPart.setDataHandler(new DataHandler(source));
        attachmentBodyPart.setFileName(fileItem.getClientFilename());
        root.addBodyPart(attachmentBodyPart);
      }
    }
  }


  /**
   *  Adds a feature to the ByteArrayFileAttachments attribute of the
   *  SMTPMessage object
   *
   *@param  root                    The feature to be added to the
   *      ByteArrayFileAttachments attribute
   *@exception  MessagingException  Description of the Exception
   */
  private void addByteArrayFileAttachments(MimeMultipart root) throws MessagingException {
    if (byteArrayAttachments != null) {
      Iterator attachments = byteArrayAttachments.iterator();
      while (attachments.hasNext()) {
        // Retrieve the attachment
        DataSource attachment = (DataSource) attachments.next();
        // attach the file to the message
        BodyPart attachmentBodyPart = new MimeBodyPart();
        attachmentBodyPart.setDisposition(Part.ATTACHMENT);
        attachmentBodyPart.setDataHandler(new DataHandler(attachment));
        attachmentBodyPart.setFileName(attachment.getName());
        root.addBodyPart(attachmentBodyPart);
      }
    }
  }


  /**
   *  Adds multiple addresses that are comma separated to the email list
   *
   *@param  list    The feature to be added to the Multiple attribute
   *@param  emails  The feature to be added to the Multiple attribute
   */
  private void addMultiple(ArrayList list, String emails) {
    StringTokenizer st = new StringTokenizer(emails, ",");
    while (st.hasMoreTokens()) {
      String email = st.nextToken();
      list.add(email.trim());
    }
  }
}

