package org.aspcfs.modules.setup.actions;

import org.aspcfs.modules.actions.CFSModule;
import com.darkhorseventures.framework.actions.*;
import org.aspcfs.utils.XMLUtils;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.aspcfs.modules.setup.beans.Zlib;

public class SetupServer extends CFSModule {

  /**
   *  A sample server action to receive the submitted registration form
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSubmitRegistration(ActionContext context) {
    try {
      //Receive the xml from the request
      XMLUtils xml = new XMLUtils(context.getRequest());
      //Create the object from the serialized xml
      Zlib license = new Zlib(xml);
      //Prepare the response XML
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = dbf.newDocumentBuilder();
      Document document = builder.newDocument();
      Element app = document.createElement("aspcfs");
      document.appendChild(app);
      Element response = document.createElement("response");
      app.appendChild(response);
      //Append the status code
      Element status = document.createElement("status");
      //Append the error text
      Element errorText = document.createElement("errorText");
      if (license.isValid()) {
        status.appendChild(document.createTextNode("0"));
        errorText.appendChild(document.createTextNode("SUCCESS"));
        license.sendEmailRegistration();
      } else {
        status.appendChild(document.createTextNode("1"));
        errorText.appendChild(document.createTextNode("FAILURE"));
      }
      response.appendChild(status);
      response.appendChild(errorText);
      context.getRequest().setAttribute("statusXML", XMLUtils.toString(document, "UTF-8"));
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }
    return "SubmitProcessOK";
  }
}
