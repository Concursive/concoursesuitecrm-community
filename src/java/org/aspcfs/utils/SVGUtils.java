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

import com.darkhorseventures.framework.actions.ActionContext;
import org.apache.batik.bridge.*;
import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.apache.batik.dom.svg.SVGOMTSpanElement;
import org.apache.batik.dom.svg.SVGOMTextElement;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.gvt.renderer.DynamicRenderer;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.w3c.dom.*;
import org.w3c.dom.svg.SVGElement;
import org.w3c.dom.svg.SVGLocatable;
import org.w3c.dom.svg.SVGMatrix;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Vector;

/**
 * Provides essestial utilities when working with Scalable Vector Graphics
 *
 * @author ananth
 * @version $Id$
 * @created April 2, 2004
 */
public class SVGUtils {
  private final static String ADOBE_ENCODING = "adobe_illustrator_pgf";
  private final static String POINT_TEXT = "pointText";
  private final static String AREA_TEXT = "areaText";
  private final static String ls = System.getProperty("line.separator", "\n");
  private final static String fs = System.getProperty("file.separator");

  private DynamicRenderer renderer = null;
  private UserAgent userAgent = null;
  private DocumentLoader loader = null;
  private BridgeContext ctx = null;
  private Document svgDoc = null;
  private GVTBuilder builder = null;
  private GraphicsNode gvtRoot = null;
  private SVGElement textElement = null;
  private Vector textItems = null;
  private NodeList textElements = null;
  private SVGElement textLayer = null;


  /**
   * Sets the textElements attribute of the SVGUtils object
   *
   * @param tmp The new textElements value
   */
  public void setTextElements(NodeList tmp) {
    this.textElements = tmp;
  }


  /**
   * Sets the textLayer attribute of the SVGUtils object
   *
   * @param tmp The new textLayer value
   */
  public void setTextLayer(SVGElement tmp) {
    this.textLayer = tmp;
  }


  /**
   * Gets the textElements attribute of the SVGUtils object
   *
   * @return The textElements value
   */
  public NodeList getTextElements() {
    return textElements;
  }


  /**
   * Gets the textLayer attribute of the SVGUtils object
   *
   * @return The textLayer value
   */
  public SVGElement getTextLayer() {
    return textLayer;
  }


  /**
   * Sets the textItems attribute of the SVGUtils object
   *
   * @param tmp The new textItems value
   */
  public void setTextItems(Vector tmp) {
    this.textItems = tmp;
  }


  /**
   * Gets the textItems attribute of the SVGUtils object
   *
   * @return The textItems value
   */
  public Vector getTextItems() {
    return textItems;
  }


  /**
   * Sets the renderer attribute of the SVGUtils object
   *
   * @param tmp The new renderer value
   */
  public void setRenderer(DynamicRenderer tmp) {
    this.renderer = tmp;
  }


  /**
   * Sets the userAgent attribute of the SVGUtils object
   *
   * @param tmp The new userAgent value
   */
  public void setUserAgent(UserAgent tmp) {
    this.userAgent = tmp;
  }


  /**
   * Sets the loader attribute of the SVGUtils object
   *
   * @param tmp The new loader value
   */
  public void setLoader(DocumentLoader tmp) {
    this.loader = tmp;
  }


  /**
   * Sets the ctx attribute of the SVGUtils object
   *
   * @param tmp The new ctx value
   */
  public void setCtx(BridgeContext tmp) {
    this.ctx = tmp;
  }


  /**
   * Sets the svgDoc attribute of the SVGUtils object
   *
   * @param tmp The new svgDoc value
   */
  public void setSvgDoc(Document tmp) {
    this.svgDoc = tmp;
  }


  /**
   * Sets the builder attribute of the SVGUtils object
   *
   * @param tmp The new builder value
   */
  public void setBuilder(GVTBuilder tmp) {
    this.builder = tmp;
  }


  /**
   * Sets the gvtRoot attribute of the SVGUtils object
   *
   * @param tmp The new gvtRoot value
   */
  public void setGvtRoot(GraphicsNode tmp) {
    this.gvtRoot = tmp;
  }


  /**
   * Sets the textElement attribute of the SVGUtils object
   *
   * @param tmp The new textElement value
   */
  public void setTextElement(SVGElement tmp) {
    this.textElement = tmp;
  }


  /**
   * Gets the renderer attribute of the SVGUtils object
   *
   * @return The renderer value
   */
  public DynamicRenderer getRenderer() {
    return renderer;
  }


  /**
   * Gets the userAgent attribute of the SVGUtils object
   *
   * @return The userAgent value
   */
  public UserAgent getUserAgent() {
    return userAgent;
  }


  /**
   * Gets the loader attribute of the SVGUtils object
   *
   * @return The loader value
   */
  public DocumentLoader getLoader() {
    return loader;
  }


  /**
   * Gets the ctx attribute of the SVGUtils object
   *
   * @return The ctx value
   */
  public BridgeContext getCtx() {
    return ctx;
  }


  /**
   * Gets the svgDoc attribute of the SVGUtils object
   *
   * @return The svgDoc value
   */
  public Document getSvgDoc() {
    return svgDoc;
  }


  /**
   * Gets the builder attribute of the SVGUtils object
   *
   * @return The builder value
   */
  public GVTBuilder getBuilder() {
    return builder;
  }


  /**
   * Gets the gvtRoot attribute of the SVGUtils object
   *
   * @return The gvtRoot value
   */
  public GraphicsNode getGvtRoot() {
    return gvtRoot;
  }


  /**
   * Gets the textElement attribute of the SVGUtils object
   *
   * @return The textElement value
   */
  public SVGElement getTextElement() {
    return textElement;
  }


  /**
   * Constructor for the SVGUtils object
   */
  public SVGUtils() {
  }


  /**
   * Constructor for the SVGUtils object
   *
   * @param svgFileName Description of the Parameter
   * @throws Exception Description of the Exception
   */
  public SVGUtils(String svgFileName) throws Exception {
    buildSVGDOM(svgFileName);
    textItems = new Vector();
  }


  /**
   * Description of the Method
   *
   * @param svgFileName Description of the Parameter
   * @throws Exception Description of the Exception
   */
  public void buildSVGDOM(String svgFileName) throws Exception {
    try {
      renderer = new DynamicRenderer();
      //Renderer that simply does static rendering in an offscreen buffer image
      userAgent = new UserAgentAdapter();
      //provides a user agent
      loader = new DocumentLoader(userAgent);
      //responsible for loading an SVG document and maintaining a cache
      ctx = new BridgeContext(userAgent, loader);
      //The context encapsulates the dynamic bindings between DOM elements and GVT nodes
      builder = new GVTBuilder();
      //responsible for creating a GVT tree using an SVG DOM tree.
      ctx.setDynamic(true);

      svgDoc = loader.loadDocument(svgFileName);
      gvtRoot = builder.build(ctx, svgDoc);
      renderer.setTree(gvtRoot);
      // ---------------being used by the SVGTextHandler---------------------------------
      textElements = svgDoc.getElementsByTagNameNS(
          SVGDOMImplementation.SVG_NAMESPACE_URI, "text");
      if (textElements.getLength() > 0) {
        textElement = (SVGElement) textElements.item(0);
      }
      // -------------------------------------------------
      textLayer = (SVGElement) svgDoc.getElementById("TEXT");
      if (textLayer == null) {
        textLayer = (SVGElement) svgDoc.getElementById("text");
      }
      if (textLayer != null) {
        textElements = textLayer.getElementsByTagNameNS(
            SVGDOMImplementation.SVG_NAMESPACE_URI, "text");
      }
    } catch (Exception e) {
      System.err.println("EXCEPTION: SVGTextHandler-> " + e.getMessage());
    }
  }


  /**
   * Description of the Method
   *
   * @param svgFile Description of the Parameter
   * @return Description of the Return Value
   * @throws Exception Description of the Exception
   */
  public boolean processSvgFile(File svgFile) throws Exception {
    /*
     *  The method determines if the svg meets the requirements.
     *  The requirements are :
     *  1. No broken text elements
     *  2. Metadata - Not yet implemented
     */
    //System.out.println("SVGUtils -> Inside method processSvgFile");
    boolean saveFile = false;
    Vector textElementsWithNoTspans = new Vector();

    // determine if there are any blank text elements or tspans and remove them
    if (removeBlankTextElements()) {
      saveFile = true;
    }

    // validate each text element
    for (int i = 0; i < textElements.getLength(); ++i) {
      // get the next text element in the node list
      Element textElement = (Element) textElements.item(i);
      // Text Element can have 1 or more tspans
      if (isTextElementBroken(textElement)) {
        fixTextElementTspans(textElement);
        saveFile = true;
      } else {
        /*
         *  This means that the text element is not broken.
         *  Could also mean that the text element has no tspans at all
         */
        NodeList tspans = textElement.getElementsByTagNameNS(
            SVGDOMImplementation.SVG_NAMESPACE_URI, "tspan");
        //System.out.println("SVGUtils --> processSvgFile --> verifying text elements tspan # : " + tspans.getLength());
        if (tspans.getLength() == 0) {
          //System.out.println("SVGUtils --> text element has no tspans");
          textElementsWithNoTspans.add(textElement);
          saveFile = true;
        }
      }
    }

    // determine if there are text elements with no tspans
    if (textElementsWithNoTspans.size() > 0) {
      //System.out.println("SVGUtils --> # text elements without tspans : " + textElementsWithNoTspans.size());
      fixTextElements(textElementsWithNoTspans);
    }

    // save this svg without removing the encoding
    if (saveFile) {
      this.saveAsSVG(svgFile, false);
    }

    return saveFile;
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public boolean removeBlankTextElements() {
    //System.out.println("SVGUtils -> Inside method removeBlankTextElements");
    boolean discardTextElement = false;
    // check if empty text elements exist
    textElements = textLayer.getElementsByTagNameNS(
        SVGDOMImplementation.SVG_NAMESPACE_URI, "text");
    for (int i = 0; i < textElements.getLength(); ++i) {
      // get the next text element in the node list
      Element textElement = (Element) textElements.item(i);
      // get all the tspans of this text element
      NodeList tspans = textElement.getElementsByTagNameNS(
          SVGDOMImplementation.SVG_NAMESPACE_URI, "tspan");
      if (tspans.getLength() == 0) {
        // this text element does not have any tspans. Determine if the text
        // element has a text node
        NodeList textNodes = textElement.getChildNodes();
        boolean textExists = false;
        for (int j = 0; j < textNodes.getLength(); ++j) {
          Node thisNode = textNodes.item(j);
          if (thisNode.getNodeType() == Node.TEXT_NODE) {
            if (!"".equals(thisNode.getNodeValue().trim())) {
              textExists = true;
              break;
            }
          }
        }
        if (!textExists) {
          discardTextElement = true;
          removeElement(textElement);
        }
      } else {
        // This text element has multiple tspans
        for (int j = 0; j < tspans.getLength(); ++j) {
          Element tspan = (Element) tspans.item(j);
          //System.out.println("SVGUtils -> ......tspan's text node value : " + getText(tspan));
          if ("".equals(getText(tspan).trim())) {
            // this tspan has no text nodes, hence delete this tspan
            discardTextElement = true;
            //System.out.println("SVGUtils -> ......removing empty tspan");
            removeElement(tspan);
          }
        }
      }
    }
    textElements = textLayer.getElementsByTagNameNS(
        SVGDOMImplementation.SVG_NAMESPACE_URI, "text");
    return discardTextElement;
  }


  /**
   * Description of the Method
   *
   * @param element Description of the Parameter
   */
  public void removeElement(Element element) {
    Element parent = (Element) element.getParentNode();
    parent.removeChild(element);
  }


  /**
   * Description of the Method
   *
   * @param textElementsWithNoTspans Description of the Parameter
   */
  protected void fixTextElements(Vector textElementsWithNoTspans) {
    for (int j = 0; j < textElementsWithNoTspans.size(); ++j) {
      SVGOMTextElement textElement = (SVGOMTextElement) textElementsWithNoTspans.elementAt(
          j);
      // The text element does not have a tspan. hence create a tspan and move all the attributes of the
      // text element and append them to the tspan. also create two attributes x and y with values 0

      Node text = textElement.getFirstChild();
      if (text != null) {
        Element tspan = svgDoc.createElementNS(
            "SVGDOMImplementation.SVG_NAMESPACE_URI", "tspan");
        tspan.appendChild(textElement.removeChild(text));
        tspan.setAttribute("x", "0");
        tspan.setAttribute("y", "0");
        NamedNodeMap attrs = textElement.getAttributes();
        Vector removeAttrs = new Vector();
        // copy the text element attrs to the tspan
        for (int i = 0; i < attrs.getLength(); ++i) {
          Attr attr = (Attr) attrs.item(i);
          if (!"transform".equals(attr.getName())) {
            tspan.setAttributeNode(attr);
            removeAttrs.add(attr);
          }
        }
        // remove the text element attrs that were copied to the tspan
        for (int i = 0; i < removeAttrs.size(); ++i) {
          textElement.removeAttributeNode((Attr) removeAttrs.elementAt(i));
        }
        textElement.appendChild(tspan);
      }
    }

    //displayTextElements(textElementsWithNoTspans);

    // Now we have all text elements with a tspan. Begin from the left end and proceed towards
    // the right end and check to see if a text element and its
    // adjacent text element have the same y co-ordinate. If they have the same y co-ordinate
    // then check to see if the tspans have the same values, if yes merge the tspans into one
    // else add the tspan as a second child to the first text element and delete the adjacent one

    int index = 0;
    while (index < textElementsWithNoTspans.size() - 1) {
      for (int i = index; i < textElementsWithNoTspans.size() - 1; ++i) {
        SVGOMTextElement textElement1 = (SVGOMTextElement) textElementsWithNoTspans.elementAt(
            i);
        SVGOMTextElement textElement2 = (SVGOMTextElement) textElementsWithNoTspans.elementAt(
            i + 1);

        if (determineYAttributeMatch(textElement1, textElement2)) {
          // check to see if both text elements have tspans with identical attributes
          Element tspan1 = (Element) textElement1.getFirstChild();
          Element tspan2 = (Element) textElement2.getFirstChild();
          NamedNodeMap attrs1 = tspan1.getAttributes();
          NamedNodeMap attrs2 = tspan2.getAttributes();
          if (attributesMatch(attrs1, attrs2)) {
            mergeTspans(tspan1, tspan2);
            // delete textelement2
            textElement2.getParentNode().removeChild(textElement2);
            textElementsWithNoTspans.remove(textElement2);
            index = i;
            break;
          } else {
            // node i and node i+1 did not match, hence add tspan2 as the second child of textElement1 and
            // remove the textElement2. Repeat the process with index as the start
            tspan2.removeAttribute("x");
            textElement1.appendChild(tspan2);
            textElement2.getParentNode().removeChild(textElement2);
            textElementsWithNoTspans.remove(textElement2);
            index = i + 1;
            break;
          }
        }
      }
    }
  }


  /**
   * Description of the Method
   *
   * @param textElement Description of the Parameter
   * @return Description of the Return Value
   */
  protected float determineTextLength(SVGOMTextElement textElement) {
    float length = 0;
    for (int i = 0; i < textElement.getNumberOfChars(); ++i) {
      length += textElement.getExtentOfChar(i).getWidth();
    }
    return length;
  }


  /**
   * Description of the Method
   *
   * @param textElementsWithNoTspans Description of the Parameter
   */
  public void displayTextElements(Vector textElementsWithNoTspans) {
    for (int i = 0; i < textElementsWithNoTspans.size(); ++i) {
      SVGOMTextElement textElement = (SVGOMTextElement) textElementsWithNoTspans.elementAt(
          i);
      //SVGLocatable locate = (SVGLocatable) textElement;
      //SVGMatrix matrix = locate.getCTM();
      int size = textElement.getElementsByTagNameNS(
          SVGDOMImplementation.SVG_NAMESPACE_URI, "tspan").getLength();
    }
  }


  /**
   * Description of the Method
   *
   * @param textElement1 Description of the Parameter
   * @param textElement2 Description of the Parameter
   * @return Description of the Return Value
   */
  protected boolean determineYAttributeMatch(SVGOMTextElement textElement1, SVGOMTextElement textElement2) {
    SVGLocatable locate1 = (SVGLocatable) textElement1;
    SVGLocatable locate2 = (SVGLocatable) textElement2;

    SVGMatrix matrix1 = locate1.getCTM();
    SVGMatrix matrix2 = locate2.getCTM();

    if (String.valueOf(matrix1.getF()).equals(String.valueOf(matrix2.getF()))) {
      return true;
    } else {
      return false;
    }
  }


  /**
   * Description of the Method
   *
   * @param textElement Description of the Parameter
   * @return Description of the Return Value
   */
  protected boolean multipleTspansExist(Element textElement) {
    NodeList tspans = textElement.getElementsByTagNameNS(
        SVGDOMImplementation.SVG_NAMESPACE_URI, "tspan");
    if (tspans.getLength() > 1) {
      return true;
    } else {
      return false;
    }
  }


  /**
   * Description of the Method
   *
   * @param textElement Description of the Parameter
   * @return Description of the Return Value
   */
  protected boolean isTextElementBroken(Element textElement) {
    return (multipleTspansExist(textElement));
  }


  /**
   * Description of the Method
   *
   * @param textElement Description of the Parameter
   */
  protected void fixTextElementTspans(Element textElement) {
    /*
     *  The text element has multiple tspans. Fixing a text element
     *  results in merging all the successive tspans which have the same
     *  set of attributes.
     */
    // get all the tspans of this text element
    NodeList tspans = textElement.getElementsByTagNameNS(
        SVGDOMImplementation.SVG_NAMESPACE_URI, "tspan");
    int index = 0;
    while (index < tspans.getLength() - 1) {
      for (int i = index; i < tspans.getLength() - 1; ++i) {
        Element tspan1 = (Element) tspans.item(i);
        Element tspan2 = (Element) tspans.item(i + 1);
        NamedNodeMap attrs1 = tspan1.getAttributes();
        NamedNodeMap attrs2 = tspan2.getAttributes();
        if (attributesMatch(attrs1, attrs2)) {
          // node i and node i+1 match, hence
          // merge them and repeat the process with index as the start
          mergeTspans(tspan1, tspan2);
          tspans = textElement.getElementsByTagNameNS(
              SVGDOMImplementation.SVG_NAMESPACE_URI, "tspan");
          index = i;
          break;
        } else {
          // node i and node i+1 did not match, hence repeat
          // the process with index as the start
          index = i + 1;
          break;
        }
      }
    }
  }


  /**
   * Description of the Method
   *
   * @param attrs1 Description of the Parameter
   * @param attrs2 Description of the Parameter
   * @return Description of the Return Value
   */
  protected boolean attributesMatch(NamedNodeMap attrs1, NamedNodeMap attrs2) {
    if (attrs1.getLength() != attrs1.getLength()) {
      // lengths dont match, hence attributes dont match
      return false;
    }
    /*
     *  For merging the tspans, all the attributes must be the same other than
     *  the x attribute
     */
    for (int i = 0; i < attrs1.getLength(); ++i) {
      Node attr = attrs1.item(i);
      String attrName = attr.getNodeName();
      String attrValue = attr.getNodeValue();
      if (!attrName.toLowerCase().equals("x")) {
        if (attrs2.getNamedItem(attrName) != null) {
          if (!attrValue.equals(attrs2.getNamedItem(attrName).getNodeValue())) {
            return false;
          }
        }
      }
    }
    return true;
  }


  /**
   * Description of the Method
   *
   * @param tspan1 Description of the Parameter
   * @param tspan2 Description of the Parameter
   */
  protected void mergeTspans(Element tspan1, Element tspan2) {
    StringBuffer sb = new StringBuffer();
    sb.append(getText(tspan1).trim() + " " + getText(tspan2).trim());
    setNodeText(tspan1, sb.toString());
    Element parent = (Element) tspan2.getParentNode();
    parent.removeChild(tspan2);
  }


  /**
   * Gets the text attribute of the SVGUtils object
   *
   * @param element Description of the Parameter
   * @return The text value
   */
  public String getText(Element element) {
    if (element == null) {
      return null;
    }
    NodeList childList = element.getChildNodes();
    for (int i = 0; i < childList.getLength(); i++) {
      Node thisNode = childList.item(i);
      if (thisNode.getNodeType() == Node.TEXT_NODE ||
          thisNode.getNodeType() == Node.CDATA_SECTION_NODE) {
        return thisNode.getNodeValue();
      }
    }
    return null;
  }


  /**
   * Sets the text attribute of the SVGUtils class
   *
   * @param replace The new text value
   *                This method is being used by one SVGTextHandler
   */
  public void setText(String replace) {
    // get all the child nodes this node has
    if (textElement != null) {
      NodeList textChildNodes = textElement.getChildNodes();
      for (int i = 0; i < textChildNodes.getLength(); ++i) {
        Node childNode = textChildNodes.item(i);
        if (childNode.hasChildNodes()) {
          // set the "text" content of the <tspan>
          // TODO: If more properties are added to the SVGTextHandler, then those properties
          //       need to be set here as shown below
          /*
           *  Element tspan = (Element) childNode;
           *  if (tspan != null) {
           *  tspan.setAttribute("letter-spacing", spacing);
           *  tspan.setAttribute("fill", fill);
           *  tspan.setAttribute("stroke", stroke);
           *  }
           */
          this.setNodeText(childNode, replace);
        }
      }
    }
  }


  /**
   * This method assumes that there is only one 'tspan' element for every
   * 'text' element. Hence if there is more than one 'tspan' element, then all
   * the tspans are replaced with the value replace
   *
   * @param node    The new nodeText value
   * @param replace The new nodeText value
   */
  public void setNodeText(Node node, String replace) {
    if (node.hasChildNodes()) {
      // set the "text" content of the <tspan>
      NodeList textNodes = node.getChildNodes();
      for (int j = 0; j < textNodes.getLength(); ++j) {
        Node textNode = textNodes.item(j);
        if (textNode.getNodeType() == Node.TEXT_NODE ||
            textNode.getNodeType() == Node.CDATA_SECTION_NODE) {
          textNode.setNodeValue(replace);
          break;
        }
      }
    }
  }


  /**
   * Sets the allTextElements attribute of the SVGUtils object
   *
   * @param replace The new allTextElements value
   * @throws Exception Description of the Exception
   */
  public void setAllTextElements(Vector replace) throws Exception {
    if (textLayer != null) {
      int index = 0;
      //textElements = textLayer.getElementsByTagNameNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "text");
      Vector textElems = this.sortTextItems();
      for (int i = 0; i < textElems.size(); ++i) {
        // get the first text element in the node list
        SVGOMTextElement textElement = (SVGOMTextElement) textElems.elementAt(
            i);
        // determine the type of the text element
        String textType = determineTextType(textElement);
        if (textType.equals(this.POINT_TEXT)) {
          // determine the text elements original width
          float oldWidth = textElement.getComputedTextLength();
          // get all the tspans of this text element
          NodeList tspans = textElement.getElementsByTagNameNS(
              SVGDOMImplementation.SVG_NAMESPACE_URI, "tspan");
          for (int j = 0; j < tspans.getLength(); ++j) {
            Node tspan = tspans.item(j);
            if (j == 0) {
              setNodeText(tspan, ((String) replace.elementAt(index)).trim());
            } else {
              // every tspan after the first tspan will have a space prepended
              setNodeText(
                  tspan, " " + ((String) replace.elementAt(index)).trim());
            }
            ++index;
          }
          float newWidth = textElement.getComputedTextLength();
          // All the tspan replace values have been set. Now it is time to verify if there are any
          // adjacent tspans in this text element that need to be shifted if they overlap.
          fixTspanPositions(textElement);
          // Also based on the alignment information available, the text element's text-anchor
          // attribute can be altered to readjust its position

          if (newWidth != oldWidth) {
            fixTextAlignment(textElement, oldWidth, newWidth);
          }
        } else if (textType.equals(AREA_TEXT)) {
          // This is an area text. determine the bounds of the text area and flow the replace
          // text withing this area maintaining the alignment
          //SVGRect rect = textElement.getBBox();
          //float width = rect.getWidth();
          //float height = rect.getHeight();

          SVGMatrix matrix = textElement.getTransform().getBaseVal().consolidate().getMatrix();
          float width = determineTextWidth(textElement);
          float height = determineTextHeight(textElement);
          float incrY = determineLineSpacing(textElement);
          String str = (String) replace.elementAt(index);

          /*
           *  Element root = svgDoc.getDocumentElement();
           *  Element e;
           *  e = svgDoc.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "rect");
           *  e.setAttributeNS(null, "x", String.valueOf(matrix.getE()));
           *  e.setAttributeNS(null, "y", String.valueOf(matrix.getF()));
           *  e.setAttributeNS(null, "width", String.valueOf(width));
           *  e.setAttributeNS(null, "height", String.valueOf(height));
           *  e.setAttributeNS(null, "style", "fill:none;stroke:black;stroke-width:1");
           *  root.appendChild(e);
           */
          //System.out.println("SVGUtils --> Calling flowText");
          //System.out.println("SVGUtils --> width  : " + width);
          //System.out.println("SVGUtils --> height : " + height);
          //System.out.println("SVGUtils --> incrY  : " + incrY);
          //System.out.println("SVGUtils --> str    : " + str);
          String textAlign = this.getTextAlignment(textElement);
          flowText(textElement, width, height, incrY, str, textAlign);
          index++;

        }
      }
    }
  }


  /**
   * Description of the Method
   *
   * @param textElement Description of the Parameter
   * @return Description of the Return Value
   */
  protected float determineTextWidth(SVGOMTextElement textElement) {
    float width = 0;
    NodeList tspans = textElement.getChildNodes();
    for (int i = 0; i < tspans.getLength(); ++i) {
      SVGOMTSpanElement tspan = (SVGOMTSpanElement) tspans.item(i);
      if (tspan.getComputedTextLength() > width) {
        //System.out.println("SVGUtils --> determineTextWidth : tspan width : " + tspan.getComputedTextLength());
        width = tspan.getComputedTextLength();
      }
    }

    return width;
  }


  /**
   * Description of the Method
   *
   * @param textElement Description of the Parameter
   * @return Description of the Return Value
   */
  protected float determineTextHeight(SVGOMTextElement textElement) {
    float height = 0;
    NodeList tspans = textElement.getChildNodes();
    for (int i = 0; i < tspans.getLength(); ++i) {
      SVGOMTSpanElement tspan = (SVGOMTSpanElement) tspans.item(i);
      if (tspan.hasAttribute("y")) {
        height = new Float(tspan.getAttribute("y")).floatValue();
      }
    }

    return height;
  }


  /**
   * Description of the Method
   *
   * @param textElement Description of the Parameter
   * @return Description of the Return Value
   */
  protected float determineLineSpacing(SVGOMTextElement textElement) {
    float spacing = 0;
    NodeList tspans = textElement.getChildNodes();
    for (int i = 0; i < tspans.getLength(); ++i) {
      SVGOMTSpanElement tspan1 = (SVGOMTSpanElement) tspans.item(i);
      SVGOMTSpanElement tspan2 = (SVGOMTSpanElement) tspans.item(i + 1);
      if (tspan1.hasAttribute("y") && tspan2.hasAttribute("y")) {
        float y1 = new Float(tspan1.getAttribute("y")).floatValue();
        float y2 = new Float(tspan2.getAttribute("y")).floatValue();
        spacing = (y2 - y1);
        break;
      }
    }

    return spacing;
  }


  /**
   * Description of the Method
   *
   * @param textElement Description of the Parameter
   * @param width       Description of the Parameter
   * @param height      Description of the Parameter
   * @param incrY       Description of the Parameter
   * @param replace     Description of the Parameter
   * @param textAlign   Description of the Parameter
   */
  protected void flowText(SVGOMTextElement textElement, float width, float height, float incrY, String replace, String textAlign) {
    // The following code assumes that the tspans will have same style attributes
    int cursorX = 0;
    //  cursor value will always be within the rectangle defined by (0,0) and width and height
    int cursorY = 0;
    // determine the width for calculating the anchor point
    float anchorWidth = width;
    if ("center".equals(textAlign)) {
      SVGOMTSpanElement t = (SVGOMTSpanElement) textElement.getFirstChild();
      anchorWidth = t.getComputedTextLength();
    }
    // keep a copy of the the tspan before removing the text elements child nodes
    SVGOMTSpanElement oldtspan = (SVGOMTSpanElement) textElement.getFirstChild();
    // remove all the childNodes of the text element
    while (textElement.hasChildNodes()) {
      textElement.removeChild(textElement.getFirstChild());
    }

    int item = 0;
    String[] tokens = replace.split("\\s");
    while (cursorY <= height && item < tokens.length) {
      SVGOMTSpanElement newtspan = (SVGOMTSpanElement) oldtspan.cloneNode(
          true);
      //System.out.println("SVGUtils --> created new tspan : " + newtspan);
      textElement.appendChild(newtspan);
      StringBuffer sb = new StringBuffer();
      for (int i = item; i < tokens.length; ++i) {
        //System.out.println("SVGUtils --> new token : " + tokens[i]);
        boolean splitToken = false;
        // determine if this token width is greater than the bouding box width
        setNodeText(newtspan, tokens[i]);
        if (newtspan.getComputedTextLength() > width) {
          splitToken = true;
        }
        sb.append(tokens[i]);
        setNodeText(newtspan, sb.toString());
        //System.out.println("SVGUtils --> ref width : " + width + ", newtspan width : " + newtspan.getComputedTextLength());
        if (newtspan.getComputedTextLength() > width) {
          sb = sb.delete(sb.length() - (tokens[i].length()), sb.length());
          //System.out.println("SVGUtils --> token overflow detected....rectified buffer : " + sb.toString());
          setNodeText(newtspan, sb.toString());

          //---------------------------------------------------------
          // The token[i] caused the overflow, hence place char by char
          //System.out.println("token that caused the overflow : " + tokens[i]);
          if (splitToken) {
            StringBuffer sb2 = new StringBuffer();
            for (int j = 0; j < tokens[i].length(); ++j) {
              sb2.append(tokens[i].charAt(j));
              setNodeText(newtspan, sb.toString() + sb2.toString());
              //System.out.println("setting text : " + sb.toString() + sb2.toString());
              if (newtspan.getComputedTextLength() > width) {
                sb2 = sb2.delete(sb2.length() - 1, sb2.length());
                //System.out.println("char overflow detected.....rectified buffer : " + sb2.toString());
                setNodeText(newtspan, sb.toString() + sb2.toString());
                tokens[i] = tokens[i].substring(sb2.length());
                //System.out.println("new token at " + i + " : " + tokens[i]);
                splitToken = false;
                // reset splitToken
                break;
              }
            }
          }
          //-------------------------------------------------------------
          break;
        }
        if (i < tokens.length - 1) {
          setNodeText(newtspan, sb.append(" ").toString());
        }
        item = i + 1;
        // item represents the token that is next in line to be placed
      }
      //System.out.println("SVGUtils --> tspan contents : " + getText(newtspan));
      this.fixTextAlignment(newtspan, anchorWidth, textAlign);
      newtspan.setAttribute("y", String.valueOf(cursorY));
      cursorY += incrY;
    }
  }


  /**
   * Description of the Method
   *
   * @param textElement Description of the Parameter
   * @return Description of the Return Value
   */
  protected String determineTextType(SVGOMTextElement textElement) {
    NodeList tspans = textElement.getChildNodes();
    if (tspans.getLength() > 1) {
      for (int i = 0; i < tspans.getLength() - 1; ++i) {
        Element tspan1 = (Element) tspans.item(i);
        Element tspan2 = (Element) tspans.item(i + 1);
        if (tspan1.hasAttribute("y") && tspan2.hasAttribute("y")) {
          if (!tspan1.getAttribute("y").equals(tspan2.getAttribute("y"))) {
            return this.AREA_TEXT;
          }
        }
      }
    }

    return this.POINT_TEXT;
  }


  /**
   * Description of the Method
   *
   * @param textElement Description of the Parameter
   * @return Description of the Return Value
   */
  public float determineWidth(SVGOMTextElement textElement) {
    float width = 0;
    for (int i = 0; i < textElement.getNumberOfChars(); ++i) {
      width += textElement.getExtentOfChar(i).getWidth();
    }
    return width;
  }


  /**
   * Description of the Method
   *
   * @param textElement Description of the Parameter
   */
  public void fixTspanPositions(SVGOMTextElement textElement) {
    /*
     *  If a text element has multiple tspans, then tspans should not overlap
     *  with each other. This method iterates through the list of tspans of every
     *  text element and removes the absoulte x co-ordinates of each tspan.
     */
    if (isTextElementBroken(textElement)) {
      // Text Element has multiple tspans
      NodeList tspans = textElement.getElementsByTagNameNS(
          SVGDOMImplementation.SVG_NAMESPACE_URI, "tspan");
      for (int j = 0; j < tspans.getLength(); ++j) {
        SVGOMTSpanElement tspan = (SVGOMTSpanElement) tspans.item(j);
        tspan.removeAttribute("x");
      }
    }
  }


  /**
   * Gets the textAlignment attribute of the SVGUtils object
   *
   * @param textElement Description of the Parameter
   * @return The textAlignment value
   */
  public String getTextAlignment(SVGOMTextElement textElement) {
    String textAlign = "left";
    Element switchElm = (Element) textElement.getParentNode();
    Element rootElm = svgDoc.getDocumentElement();
    boolean switchExists = false;
    while (!"switch".equals(switchElm.getNodeName()) || !switchElm.equals(
        rootElm)) {
      switchElm = (Element) switchElm.getParentNode();
      if ("switch".equals(switchElm.getNodeName())) {
        switchExists = true;
        break;
      }
    }

    if (switchExists) {
      // get the 'p' elements and the 'flow' elements which have the text-align attribute
      NodeList list = switchElm.getElementsByTagName("flow");
      if (list.getLength() > 0) {
        for (int i = 0; i < list.getLength(); ++i) {
          Element flow = (Element) list.item(i);
          if (flow.hasAttribute("text-align")) {
            textAlign = flow.getAttribute("text-align");
            break;
          }
        }
      } else {
        list = switchElm.getElementsByTagName("p");
        for (int i = 0; i < list.getLength(); ++i) {
          Element p = (Element) list.item(i);
          if (p.hasAttribute("text-align")) {
            textAlign = p.getAttribute("text-align");
            break;
          }
        }
      }
    }

    return textAlign;
  }


  /**
   * Description of the Method
   *
   * @param textElement Description of the Parameter
   * @param oldWidth    Description of the Parameter
   * @param newWidth    Description of the Parameter
   */
  public void fixTextAlignment(SVGOMTextElement textElement, float oldWidth, float newWidth) {
    /*
     *  This method works only for point type text.
     *  If the text element has alignment information available in the xml, then
     *  determine the new width of the text element and compare it with the original
     *  width to determine the new x co-ordinate value
     *  Parse the sub tree with element 'switch' as the parent of the sub tree. Retrieve an attribute
     *  text-align if it exists in this tree
     */
    String textAlign = this.getTextAlignment(textElement);
    // Now we know the text alignment. If it is not equal to default value which is 'left'
    // then determine the anchor point for the text element
    if (!"left".equals(textAlign)) {
      Element tspan = (Element) textElement.getFirstChild();
      float anchor = 0;
      if (tspan != null) {
        if (!tspan.hasAttribute("text-anchor")) {
          if (textAlign.equals("center")) {
            //System.out.println("SVGUtils --> setting attr text-anchor -- middle");
            anchor = oldWidth / 2;
            //System.out.println("SVGUtils --> anchor - " + anchor);
            tspan.setAttribute("text-anchor", "middle");
          } else if (textAlign.equals("right")) {
            anchor = oldWidth;
            tspan.setAttribute("text-anchor", "end");
          }
          tspan.setAttribute("x", String.valueOf(anchor));
        }
      }
    }
  }


  /**
   * Description of the Method
   *
   * @param tspanElement Description of the Parameter
   * @param width        Description of the Parameter
   * @param textAlign    Description of the Parameter
   */
  public void fixTextAlignment(SVGOMTSpanElement tspanElement, float width, String textAlign) {
    /*
     *  This method works for area text.
     *  text-align if it exists in this tree
     */
    // Now we know the text alignment. If it is not equal to default value which is 'left'
    // then determine the anchor point for the text element
    if (!"left".equals(textAlign)) {
      float anchor = 0;
      if (tspanElement != null) {
        if (!tspanElement.hasAttribute("text-anchor")) {
          if (textAlign.equals("center")) {
            //System.out.println("SVGUtils --> setting attr text-anchor -- middle");
            anchor = width / 2;
            //System.out.println("SVGUtils --> anchor - " + anchor);
            tspanElement.setAttribute("text-anchor", "middle");
          } else if (textAlign.equals("right")) {
            anchor = width;
            tspanElement.setAttribute("text-anchor", "end");
          }
          tspanElement.setAttribute("x", String.valueOf(anchor));
        }
      }
    }
  }


  /**
   * Gets the textItems attribute of the SVGUtils object
   *
   * @return The textItems value
   */
  public Vector getTextValues() {
    if (textLayer != null) {
      Vector textElems = this.sortTextItems();
      for (int i = 0; i < textElems.size(); ++i) {
        // get the first text element in the node list
        SVGOMTextElement textElement = (SVGOMTextElement) textElems.elementAt(
            i);
        NodeList childNodes = textElement.getChildNodes();
        String textType = this.determineTextType(textElement);
        if (textType.equals(this.POINT_TEXT)) {
          for (int j = 0; j < childNodes.getLength(); ++j) {
            // get a <tspan> node and check if it has children
            Node childNode = childNodes.item(j);
            if (childNode.hasChildNodes()) {
              // get the "text" content of the <tspan>
              NodeList textNodes = childNode.getChildNodes();
              for (int k = 0; k < textNodes.getLength(); ++k) {
                Node textNode = textNodes.item(k);
                if (textNode.getNodeType() == Node.TEXT_NODE) {
                  textItems.add("P" + textNode.getNodeValue());
                }
              }
            } else {
              if (childNode.getNodeType() == Node.TEXT_NODE) {
                textItems.add(childNode.getNodeValue());
              }
            }
          }
        } else if (textType.equals(AREA_TEXT)) {
          StringBuffer sb = new StringBuffer();
          for (int j = 0; j < childNodes.getLength(); ++j) {
            // get a <tspan> node and check if it has children
            Node childNode = childNodes.item(j);
            NodeList textNodes = childNode.getChildNodes();
            for (int k = 0; k < textNodes.getLength(); ++k) {
              Node textNode = textNodes.item(k);
              if (textNode.getNodeType() == Node.TEXT_NODE) {
                sb.append(" " + textNode.getNodeValue().trim());
              }
            }
          }
          textItems.add("A" + sb.toString().trim());
        }
      }
    }
    return textItems;
  }


  /**
   * Description of the Method
   *
   * @param textElement Description of the Parameter
   * @return Description of the Return Value
   */
  protected float determineX(SVGOMTextElement textElement) {
    SVGMatrix matrix = textElement.getTransform().getBaseVal().consolidate().getMatrix();
    //System.out.println("x : " + matrix.getE());
    return matrix.getE();
  }


  /**
   * Description of the Method
   *
   * @param textElement Description of the Parameter
   * @return Description of the Return Value
   */
  protected float determineY(SVGOMTextElement textElement) {
    SVGMatrix matrix = textElement.getTransform().getBaseVal().consolidate().getMatrix();
    return matrix.getF();
  }


  /**
   * Description of the Method
   *
   * @param textItems Description of the Parameter
   */
  protected Vector sortTextItems() {

    Vector textElems = new Vector();
    for (int i = 0; i < textElements.getLength(); ++i) {
      //System.out.println("adding element to vector..");
      textElems.add(textElements.item(i));
    }

    for (int i = 0; i < textElems.size(); ++i) {
      for (int j = 0; j < textElems.size(); ++j) {
        //System.out.println("i : " + i);
        //System.out.println("j : " + j);
        SVGOMTextElement textElement1 = (SVGOMTextElement) textElems.elementAt(
            i);
        SVGOMTextElement textElement2 = (SVGOMTextElement) textElems.elementAt(
            j);
        if (determineY(textElement1) < determineY(textElement2)) {
          // text elements need to be swapped
          SVGOMTextElement tmp = (SVGOMTextElement) textElems.elementAt(i);
          textElems.setElementAt(textElems.elementAt(j), i);
          textElems.setElementAt(tmp, j);
          /*
          // text values need to be swapped
          String tmpA = (String) textItems.elementAt(i);
          textItems.setElementAt((String) textItems.elementAt(j), i);
          textItems.setElementAt(tmpA, j);
          */
        }
      }
    }

    return textElems;
  }


  /**
   * Description of the Method
   *
   * @param out Description of the Parameter
   * @throws Exception Description of the Exception
   */
  public void saveAsJPEG(File out) throws Exception {
    JPEGTranscoder t = new JPEGTranscoder();
    t.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, new Float(1));
    TranscoderInput input = new TranscoderInput(svgDoc);
    OutputStream ostream = new FileOutputStream(out);
    TranscoderOutput output = new TranscoderOutput(ostream);
    t.transcode(input, output);
    ostream.flush();
    ostream.close();
  }


  /**
   * Description of the Method
   *
   * @param originalFile  Description of the Parameter
   * @param thumbnailFile Description of the Parameter
   * @param maxWidth      Description of the Parameter
   * @param maxHeight     Description of the Parameter
   * @throws Exception Description of the Exception
   */
  public void saveAsJPEG(File originalFile, File thumbnailFile, float maxWidth, float maxHeight)
      throws Exception {
    BufferedImage originalImage = ImageIO.read(originalFile);
    /*
     *  double ratioWidth = maxWidth / originalImage.getWidth();
     *  double ratioHeight = maxHeight / originalImage.getHeight();
     *  double ratio = 1;
     *  if (maxWidth > 0 && maxHeight < 0) {
     *  ratio = ratioWidth;
     *  } else if (maxHeight > 0 && maxWidth < 0) {
     *  ratio = ratioHeight;
     *  } else {
     *  if (ratioWidth < ratioHeight) {
     *  ratio = ratioWidth;
     *  } else {
     *  ratio = ratioHeight;
     *  }
     *  }
     */
    float ratio = maxWidth;
    JPEGTranscoder t = new JPEGTranscoder();
    t.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, new Float(1));
    t.addTranscodingHint(JPEGTranscoder.KEY_WIDTH, new Float(ratio));
    TranscoderInput input = new TranscoderInput(svgDoc);
    OutputStream ostream = new FileOutputStream(thumbnailFile);
    TranscoderOutput output = new TranscoderOutput(ostream);
    t.transcode(input, output);
    ostream.flush();
    ostream.close();
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @throws Exception Description of the Exception
   */
  public void saveAsJPEG(ActionContext context) throws Exception {
    try {
      context.getResponse().setContentType("image/jpeg");
      // get the transcoder input
      JPEGTranscoder t = new JPEGTranscoder();
      t.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, new Float(1));
      TranscoderInput input = new TranscoderInput(svgDoc);
      ServletOutputStream ostream = context.getResponse().getOutputStream();
      TranscoderOutput output = new TranscoderOutput(ostream);
      t.transcode(input, output);
      ostream.flush();
      ostream.close();
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }
  }


  /**
   * Gets the fileSavePath attribute of the SVGUtils class
   *
   * @param id   Description of the Parameter
   * @param base Description of the Parameter
   * @return The fileSavePath value
   */
  public static String getFilePath(String base, int id) {
    String path = base + DateUtils.getDatePath(new java.util.Date());
    File f = new File(path);
    if (!f.exists()) {
      f.mkdirs();
    }
    path += getFileName(id);
    return path;
  }


  /**
   * Gets the fileName attribute of the SVGUtils class
   *
   * @param id Description of the Parameter
   * @return The fileName value
   */
  public static String getFileName(int id) {
    Date d = new java.util.Date();
    return (DateUtils.getFilename(new java.sql.Timestamp(d.getTime())) + "-" + id);
  }


  /**
   * Description of the Method
   *
   * @param svgFile        Description of the Parameter
   * @param removeEncoding Description of the Parameter
   * @throws Exception Description of the Exception
   */
  public void saveAsSVG(File svgFile, boolean removeEncoding) throws Exception {
    /*
     *  remove the adobe illustrator encoded information
     *  in the svg
     */
    if (removeEncoding) {
      this.removeIllustratorEncoding();
      this.removeBlankTextElements();
    }
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    OutputStream ostream = new FileOutputStream(svgFile);
    transformer.transform(new DOMSource(svgDoc), new StreamResult(ostream));
  }


  /**
   * Description of the Method
   *
   * @throws Exception Description of the Exception
   */
  private void removeIllustratorEncoding() throws Exception {
    Element encodedInfo = (Element) svgDoc.getElementById(this.ADOBE_ENCODING);
    //System.out.println("SVGUtils -> Removing Encoded Information");
    if (encodedInfo != null) {
      Element root = svgDoc.getDocumentElement();
      root.removeChild((Node) encodedInfo);
    }
  }
}

