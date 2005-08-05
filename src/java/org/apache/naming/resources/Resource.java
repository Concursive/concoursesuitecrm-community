/*
 * Copyright 1999,2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.naming.resources;

import java.io.*;

/**
 * Encapsultes the contents of a resource. A resource could be representing the
 * contents of a Centric FileItem, Centric Contact or a Centric Calendar
 *
 * @author <a href="mailto:remm@apache.org">Remy Maucherat</a>
 * @version $Revision$
 * @created May 16, 2005
 */
public class Resource {

  //Centric properties
  private String name = null;

  //Centric FileItem properties
  private int linkModuleId = -1;
  private int linkItemId = -1;
  private int fileItemId = -1;
  private String subject = null;
  private int size = -1;

  //Centric Contact properties
  private int contactId = -1;


  /**
   * Gets the name attribute of the Resource object
   *
   * @return The name value
   */
  public String getName() {
    return name;
  }


  /**
   * Sets the name attribute of the Resource object
   *
   * @param tmp The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   * Gets the contactId attribute of the Resource object
   *
   * @return The contactId value
   */
  public int getContactId() {
    return contactId;
  }


  /**
   * Sets the contactId attribute of the Resource object
   *
   * @param tmp The new contactId value
   */
  public void setContactId(int tmp) {
    this.contactId = tmp;
  }


  /**
   * Sets the contactId attribute of the Resource object
   *
   * @param tmp The new contactId value
   */
  public void setContactId(String tmp) {
    this.contactId = Integer.parseInt(tmp);
  }


  /**
   * Gets the size attribute of the Resource object
   *
   * @return The size value
   */
  public int getSize() {
    return size;
  }


  /**
   * Sets the size attribute of the Resource object
   *
   * @param tmp The new size value
   */
  public void setSize(int tmp) {
    this.size = tmp;
  }


  /**
   * Sets the size attribute of the Resource object
   *
   * @param tmp The new size value
   */
  public void setSize(String tmp) {
    this.size = Integer.parseInt(tmp);
  }


  /**
   * Gets the subject attribute of the Resource object
   *
   * @return The subject value
   */
  public String getSubject() {
    return subject;
  }


  /**
   * Sets the subject attribute of the Resource object
   *
   * @param tmp The new subject value
   */
  public void setSubject(String tmp) {
    this.subject = tmp;
  }


  /**
   * Gets the linkModuleId attribute of the Resource object
   *
   * @return The linkModuleId value
   */
  public int getLinkModuleId() {
    return linkModuleId;
  }


  /**
   * Sets the linkModuleId attribute of the Resource object
   *
   * @param tmp The new linkModuleId value
   */
  public void setLinkModuleId(int tmp) {
    this.linkModuleId = tmp;
  }


  /**
   * Sets the linkModuleId attribute of the Resource object
   *
   * @param tmp The new linkModuleId value
   */
  public void setLinkModuleId(String tmp) {
    this.linkModuleId = Integer.parseInt(tmp);
  }


  /**
   * Gets the linkItemId attribute of the Resource object
   *
   * @return The linkItemId value
   */
  public int getLinkItemId() {
    return linkItemId;
  }


  /**
   * Sets the linkItemId attribute of the Resource object
   *
   * @param tmp The new linkItemId value
   */
  public void setLinkItemId(int tmp) {
    this.linkItemId = tmp;
  }


  /**
   * Sets the linkItemId attribute of the Resource object
   *
   * @param tmp The new linkItemId value
   */
  public void setLinkItemId(String tmp) {
    this.linkItemId = Integer.parseInt(tmp);
  }


  /**
   * Gets the fileItemId attribute of the Resource object
   *
   * @return The fileItemId value
   */
  public int getFileItemId() {
    return fileItemId;
  }


  /**
   * Sets the fileItemId attribute of the Resource object
   *
   * @param tmp The new fileItemId value
   */
  public void setFileItemId(int tmp) {
    this.fileItemId = tmp;
  }


  /**
   * Sets the fileItemId attribute of the Resource object
   *
   * @param tmp The new fileItemId value
   */
  public void setFileItemId(String tmp) {
    this.fileItemId = Integer.parseInt(tmp);
  }


  // ----------------------------------------------------------- Constructors

  /**
   * Constructor for the Resource object
   */
  public Resource() {
  }


  /**
   * Constructor for the Resource object
   *
   * @param inputStream Description of the Parameter
   */
  public Resource(InputStream inputStream) {
    setContent(inputStream);
  }


  /**
   * Constructor for the Resource object
   *
   * @param binaryContent Description of the Parameter
   */
  public Resource(byte[] binaryContent) {
    setContent(binaryContent);
  }


  // ----------------------------------------------------- Instance Variables


  /**
   * Binary content.
   */
  protected byte[] binaryContent = null;

  /**
   * Input stream.
   */
  protected InputStream inputStream = null;


  // ------------------------------------------------------------- Properties


  /**
   * Content accessor.
   *
   * @return InputStream
   * @throws IOException Description of the Exception
   */
  public InputStream streamContent()
      throws IOException {
    if (inputStream != null) {
      return inputStream;
    }
    if (binaryContent != null) {
      return new ByteArrayInputStream(binaryContent);
    }
    return null;
  }


  /**
   * Content accessor.
   *
   * @return binary content
   */
  public byte[] getContent() {
    return binaryContent;
  }


  /**
   * Content mutator.
   *
   * @param inputStream New input stream
   */
  public void setContent(InputStream inputStream) {
    this.inputStream = inputStream;
  }


  /**
   * Content mutator.
   *
   * @param binaryContent New bin content
   */
  public void setContent(byte[] binaryContent) {
    this.binaryContent = binaryContent;
  }


  /**
   * Description of the Method
   *
   * @param filePath Description of the Parameter
   * @return Description of the Return Value
   * @throws IOException Description of the Exception
   */
  public boolean saveAsFile(String filePath) throws IOException {
    FileOutputStream destination = null;
    File destinationFile = null;
    try {
      destinationFile = new File(filePath);
      destination = new FileOutputStream(destinationFile);
      byte[] buffer = new byte[4096];
      int read = -1;
      while ((read = streamContent().read(buffer)) != -1) {
        //System.out.println("WRITING " + read + " BYTES OF DATA");
        destination.write(buffer, 0, read);
        destination.flush();
      }
    } catch (Exception e) {
      return false;
    } finally {
      if (destination != null) {
        try {
          destination.close();
          size = (int) destinationFile.length();
        } catch (IOException io) {
        }
      }
    }
    return true;
  }
}

