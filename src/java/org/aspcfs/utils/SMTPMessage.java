package org.aspcfs.utils;

// JavaMail docs at http://developer.java.sun.com/developer/onlineTraining/JavaMail/contents.html

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.io.*;
import com.zeroio.iteam.base.*;

/**
 *  A wrapper around Sun's JavaMail API. Makes sending email a snap.
 *
 *@author     mrajkowski
 *@created    July 12, 2001
 *@version    $Id$
 */
public class SMTPMessage {

  private String host = "";
  private String from = "";
  private ArrayList to = new ArrayList();
  private ArrayList cc = new ArrayList();
  private String subject = "";
  private String body = "";
  private String type = "text";
  private String errorMsg = "";
  private ArrayList replyTo = new ArrayList();
  private FileItemList attachments = null;


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
  public ArrayList getReplyTo() {
    return replyTo;
  }


  /**
   *  Sets the replyTo attribute of the SMTPMessage object
   *
   *@param  replyTo  The new replyTo value
   */
  public void setReplyTo(ArrayList replyTo) {
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
   *@param  tmp  The feature to be added to the ReplyTo attribute
   */
  public void addReplyTo(String tmp) {
    replyTo.add(tmp);
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

      // Set the from address
      message.setFrom(new InternetAddress(from));

      //Set the Reply to addresses
      if (replyTo.size() > 0) {
        InternetAddress[] tempReply = new InternetAddress[replyTo.size()];
        for (int i = 0; i < replyTo.size(); i++) {
          tempReply[i] = new InternetAddress((String) replyTo.get(i));
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

      // Set the subject
      message.setSubject(subject);

      //Set the content
      if ("text".equals(type) || "text/plain".equals(type)) {
        //A text only message
        message.setText(body);
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

        //Add add the parts to the message
        message.setContent(mpRoot);
      }

      // Send message
      Transport.send(message);
      return 0;
    } catch (javax.mail.MessagingException me) {
      errorMsg = me.toString();
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
   *  Adds multiple addresses that are comma separated to the email list
   *
   *@param  list    The feature to be added to the Multiple attribute
   *@param  emails  The feature to be added to the Multiple attribute
   */
  private void addMultiple(ArrayList list, String emails) {
    StringTokenizer st = new StringTokenizer(emails, ",");
    while (st.hasMoreTokens()) {
      String email = st.nextToken();
      list.add(email);
    }
  }
}

