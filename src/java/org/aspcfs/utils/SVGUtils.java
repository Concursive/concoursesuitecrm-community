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
import javax.servlet.*;
import javax.servlet.http.*;
import java.text.SimpleDateFormat;

import java.util.*;
import java.io.*;
import javax.imageio.*;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

import org.apache.batik.dom.*;
import org.apache.batik.dom.svg.*;
import org.w3c.dom.svg.*;

import org.apache.batik.bridge.UserAgent;
import org.apache.batik.bridge.UserAgentAdapter;
import org.apache.batik.bridge.DocumentLoader;
import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.bridge.GVTBuilder;

import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.gvt.renderer.StaticRenderer;

import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 *  Provides essestial utilities when working with Scalable Vector Graphics
 *
 *@author     ananth
 *@created    April 2, 2004
 *@version    $Id$
 */
public class SVGUtils {
  private final static String ls = System.getProperty("line.separator", "\n");
  private final static String fs = System.getProperty("file.separator");

  private StaticRenderer renderer = null;
  private UserAgent userAgent = null;
  private DocumentLoader loader = null;
  private BridgeContext ctx = null;
  private Document svgDoc = null;
  private GVTBuilder builder = null;
  private GraphicsNode gvtRoot = null;
  private SVGElement textElement = null;
  private Vector textItems = null;


  /**
   *  Sets the textItems attribute of the SVGUtils object
   *
   *@param  tmp  The new textItems value
   */
  public void setTextItems(Vector tmp) {
    this.textItems = tmp;
  }


  /**
   *  Gets the textItems attribute of the SVGUtils object
   *
   *@return    The textItems value
   */
  public Vector getTextItems() {
    return textItems;
  }


  /**
   *  Sets the renderer attribute of the SVGUtils object
   *
   *@param  tmp  The new renderer value
   */
  public void setRenderer(StaticRenderer tmp) {
    this.renderer = tmp;
  }


  /**
   *  Sets the userAgent attribute of the SVGUtils object
   *
   *@param  tmp  The new userAgent value
   */
  public void setUserAgent(UserAgent tmp) {
    this.userAgent = tmp;
  }


  /**
   *  Sets the loader attribute of the SVGUtils object
   *
   *@param  tmp  The new loader value
   */
  public void setLoader(DocumentLoader tmp) {
    this.loader = tmp;
  }


  /**
   *  Sets the ctx attribute of the SVGUtils object
   *
   *@param  tmp  The new ctx value
   */
  public void setCtx(BridgeContext tmp) {
    this.ctx = tmp;
  }


  /**
   *  Sets the svgDoc attribute of the SVGUtils object
   *
   *@param  tmp  The new svgDoc value
   */
  public void setSvgDoc(Document tmp) {
    this.svgDoc = tmp;
  }


  /**
   *  Sets the builder attribute of the SVGUtils object
   *
   *@param  tmp  The new builder value
   */
  public void setBuilder(GVTBuilder tmp) {
    this.builder = tmp;
  }


  /**
   *  Sets the gvtRoot attribute of the SVGUtils object
   *
   *@param  tmp  The new gvtRoot value
   */
  public void setGvtRoot(GraphicsNode tmp) {
    this.gvtRoot = tmp;
  }


  /**
   *  Sets the textElement attribute of the SVGUtils object
   *
   *@param  tmp  The new textElement value
   */
  public void setTextElement(SVGElement tmp) {
    this.textElement = tmp;
  }


  /**
   *  Gets the renderer attribute of the SVGUtils object
   *
   *@return    The renderer value
   */
  public StaticRenderer getRenderer() {
    return renderer;
  }


  /**
   *  Gets the userAgent attribute of the SVGUtils object
   *
   *@return    The userAgent value
   */
  public UserAgent getUserAgent() {
    return userAgent;
  }


  /**
   *  Gets the loader attribute of the SVGUtils object
   *
   *@return    The loader value
   */
  public DocumentLoader getLoader() {
    return loader;
  }


  /**
   *  Gets the ctx attribute of the SVGUtils object
   *
   *@return    The ctx value
   */
  public BridgeContext getCtx() {
    return ctx;
  }


  /**
   *  Gets the svgDoc attribute of the SVGUtils object
   *
   *@return    The svgDoc value
   */
  public Document getSvgDoc() {
    return svgDoc;
  }


  /**
   *  Gets the builder attribute of the SVGUtils object
   *
   *@return    The builder value
   */
  public GVTBuilder getBuilder() {
    return builder;
  }


  /**
   *  Gets the gvtRoot attribute of the SVGUtils object
   *
   *@return    The gvtRoot value
   */
  public GraphicsNode getGvtRoot() {
    return gvtRoot;
  }


  /**
   *  Gets the textElement attribute of the SVGUtils object
   *
   *@return    The textElement value
   */
  public SVGElement getTextElement() {
    return textElement;
  }


  /**
   *  Constructor for the SVGUtils object
   */
  public SVGUtils() { }


  /**
   *  Constructor for the SVGUtils object
   *
   *@param  svgFileName    Description of the Parameter
   *@exception  Exception  Description of the Exception
   */
  public SVGUtils(String svgFileName) throws Exception {
    buildSVGDOM(svgFileName);
    textItems = new Vector();
  }


  /**
   *  Description of the Method
   *
   *@param  svgFileName    Description of the Parameter
   *@exception  Exception  Description of the Exception
   */
  public void buildSVGDOM(String svgFileName) throws Exception {
    try {
      renderer = new StaticRenderer();
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
      NodeList textElements = svgDoc.getElementsByTagNameNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "text");
      if (textElements.getLength() > 0) {
        textElement = (SVGElement) textElements.item(0);
      }
    } catch (Exception e) {
      System.err.println("EXCEPTION: SVGTextHandler-> " + e.getMessage());
    }
  }


  /**
   *  Sets the text attribute of the SVGUtils class
   *
   *@param  replace  The new text value
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
          NodeList childNodes = childNode.getChildNodes();
          for (int j = 0; j < childNodes.getLength(); ++j) {
            Node thisNode = childNodes.item(j);
            if (thisNode.getNodeType() == Node.TEXT_NODE ||
                thisNode.getNodeType() == Node.CDATA_SECTION_NODE) {
              thisNode.setNodeValue(replace);
            }
          }
        }
      }
    }
  }


  /**
   *  This method assumes that there is only one 'tspan' element for every
   *  'text' element. Hence if there is more than one 'tspan' element, then all
   *  the tspans are replaced with the value replace
   *
   *@param  node     The new nodeText value
   *@param  replace  The new nodeText value
   */
  public void setNodeText(Node node, String replace) {
    // get all the <tspan> nodes this node has
    NodeList tspanNodes = node.getChildNodes();
    for (int i = 0; i < tspanNodes.getLength(); ++i) {
      // get a <tspan> node and check if it has children
      Node tspanNode = tspanNodes.item(i);
      if (tspanNode.hasChildNodes()) {
        // set the "text" content of the <tspan>
        NodeList textNodes = tspanNode.getChildNodes();
        for (int j = 0; j < textNodes.getLength(); ++j) {
          Node textNode = textNodes.item(j);
          if (textNode.getNodeType() == Node.TEXT_NODE ||
              textNode.getNodeType() == Node.CDATA_SECTION_NODE) {
            if (textNode.getNodeValue().length() > 1) {
              //System.out.println("replacing with -> " + replace);
              textNode.setNodeValue(replace);
            }
          }
        }
      }
    }
  }


  /**
   *  Sets the allTextElements attribute of the SVGUtils object
   *
   *@param  replace  The new allTextElements value
   */
  public void setAllTextElements(Vector replace) {
    SVGElement textLayer = (SVGElement) svgDoc.getElementById("TEXT");
    if (textLayer != null) {
      NodeList textElements = textLayer.getElementsByTagNameNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "text");
      for (int i = 0; i < textElements.getLength(); ++i) {
        // get the first text element in the node list
        Node textElement = textElements.item(i);
        System.out.println("setting value -> " + (String) replace.elementAt(i));
        setNodeText(textElement, (String) replace.elementAt(i));
      }
    }
  }


  /**
   *  Gets the textItems attribute of the SVGUtils object
   *
   *@return    The textItems value
   */
  public Vector getTextElements() {
    SVGElement textLayer = (SVGElement) svgDoc.getElementById("TEXT");
    if (textLayer != null) {
      NodeList textElements = textLayer.getElementsByTagNameNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "text");
      for (int i = 0; i < textElements.getLength(); ++i) {
        // get the first text element in the node list
        Node textElement = textElements.item(i);
        NodeList tspanNodes = textElement.getChildNodes();
        for (int j = 0; j < tspanNodes.getLength(); ++j) {
          // get a <tspan> node and check if it has children
          Element tspanNode = (Element) tspanNodes.item(j);
          if (tspanNode.hasChildNodes()) {
            // get the "text" content of the <tspan>
            NodeList textNodes = tspanNode.getChildNodes();
            for (int k = 0; k < textNodes.getLength(); ++k) {
              Node textNode = textNodes.item(k);
              if (textNode.getNodeType() == Node.TEXT_NODE ||
                  textNode.getNodeType() == Node.CDATA_SECTION_NODE) {
                textItems.add(textNode.getNodeValue());
              }
            }
          }
        }
      }
    }
    return textItems;
  }


  /**
   *  Description of the Method
   *
   *@param  out            Description of the Parameter
   *@exception  Exception  Description of the Exception
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
   *  Description of the Method
   *
   *@param  originalFile   Description of the Parameter
   *@param  thumbnailFile  Description of the Parameter
   *@param  maxWidth       Description of the Parameter
   *@param  maxHeight      Description of the Parameter
   *@exception  Exception  Description of the Exception
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
   *  Description of the Method
   *
   *@param  context        Description of the Parameter
   *@exception  Exception  Description of the Exception
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
      System.out.print("transcoding complete...");
      ostream.flush();
      ostream.close();
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }
  }


  /**
   *  Gets the fileSavePath attribute of the SVGUtils class
   *
   *@param  saveInDir  Description of the Parameter
   *@return            The fileSavePath value
   */
  public static String[] getFileSavePath(String saveInDir) {
    String path = null;
    String tmpPath = null;
    String filenameToUse = null;
    String names[] = {"", ""};
    tmpPath = saveInDir;
    SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy");
    String datePathToUse1 = formatter1.format(new java.util.Date());
    SimpleDateFormat formatter2 = new SimpleDateFormat("MMdd");
    String datePathToUse2 = formatter2.format(new java.util.Date());
    tmpPath += datePathToUse1 + fs + datePathToUse2 + fs;
    System.out.println("determined tmpPath -> " + tmpPath);
    // create the output directory if it does not exist
    File f = new File(tmpPath);
    f.mkdirs();

    // store files using a unique name, based on the date
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
    filenameToUse = formatter.format(new java.util.Date());
    names[0] = tmpPath + filenameToUse;
    names[1] = filenameToUse;
    return names;
  }


  /**
   *  Description of the Method
   *
   *@param  svgFile        Description of the Parameter
   *@exception  Exception  Description of the Exception
   */
  public void saveAsSVG(File svgFile) throws Exception {
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    OutputStream ostream = new FileOutputStream(svgFile);
    transformer.transform(new DOMSource(svgDoc), new StreamResult(ostream));
  }
}

