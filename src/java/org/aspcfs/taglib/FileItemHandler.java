package org.aspcfs.taglib;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.util.ArrayList;
import org.aspcfs.modules.communications.actions.ProcessFileItemImage;

/**
 *  This Class permits the user to have access to a specific fileItem for
 *  download by adding the id to a session array and then the image will
 *  evaluate the id later and stream the image.
 *
 * @author     matt rajkowski
 * @created    April 1, 2004
 * @version    $Id$
 */
public class FileItemHandler extends TagSupport {

  private String id = null;
  private String path = null;
  private String version = null;
  private String name = null;


  /**
   *  Sets the name attribute of the FileItemHandler object
   *
   * @param  tmp  The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Sets the version attribute of the FileItemHandler object
   *
   * @param  tmp  The new version value
   */
  public void setVersion(String tmp) {
    this.version = tmp;
  }



  /**
   *  Sets the id attribute of the FileItemHandler object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the FileItemHandler object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = String.valueOf(tmp);
  }


  /**
   *  Sets the path attribute of the FileItemHandler object
   *
   * @param  tmp  The new path value
   */
  public void setPath(String tmp) {
    this.path = tmp;
  }


  /**
   *  Description of the Method
   *
   * @return                   Description of the Return Value
   * @exception  JspException  Description of the Exception
   */
  public int doStartTag() throws JspException {
    try {
      ArrayList allowedImages = (ArrayList) pageContext.getSession().getAttribute(ProcessFileItemImage.PROCESS_FILE_ITEM_NAME);
      if (allowedImages == null) {
        synchronized (pageContext.getSession()) {
          allowedImages = (ArrayList) pageContext.getSession().getAttribute(ProcessFileItemImage.PROCESS_FILE_ITEM_NAME);
          if (allowedImages == null) {
            allowedImages = new ArrayList();
            pageContext.getSession().setAttribute(ProcessFileItemImage.PROCESS_FILE_ITEM_NAME, allowedImages);
          }
        }
      }
      allowedImages.add(id + (version != null ? "-" + version : ""));
      this.pageContext.getOut().write("<img src=\"ProcessFileItemImage.do?id=" + id + "&path=" + path + (version != null ? "&version=" + version : "") + "\" " + (name != null ? "name=\"" + name + "\"" : "") + " border=\"0\"/>");
    } catch (Exception e) {
      System.err.println("EXCEPTION: FileItemHandler-> " + e.getMessage());
    }
    return SKIP_BODY;
  }


  /**
   *  Description of the Method
   *
   * @return    Description of the Return Value
   */
  public int doEndTag() {
    return EVAL_PAGE;
  }

}

