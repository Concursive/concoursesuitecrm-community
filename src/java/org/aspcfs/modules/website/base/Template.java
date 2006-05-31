package org.aspcfs.modules.website.base;

import com.darkhorseventures.framework.beans.GenericBean;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.zip.ZipFile;

/**
 * Description of the Class
 *
 * @author mrajkowski
 * @version $Id: Exp $
 * @created May 15, 2006 $Id: Exp $
 */
public class Template extends GenericBean {

  private String filename = null;
  private String locale = null;
  private String name = null;
  private double version = 0;
  private String vendor = null;
  private String description = null;
  private Timestamp entered = null;
  private Timestamp modified = null;
  private int layout = -1;
  private int style = -1;


  public Template() {
  }

  public Template(ZipFile zipFile) throws IOException {
    filename = zipFile.getName().substring(zipFile.getName().lastIndexOf(System.getProperty("file.separator")) + 1, zipFile.getName().lastIndexOf(".zip"));
    // Read the details from the zip file
    InputStream manifestStream = zipFile.getInputStream(
      zipFile.getEntry("website" + System.getProperty("file.separator") + "MANIFEST.MF"));
    byte[] manifest = new byte[manifestStream.available()];
    int offset = 0;
    int numRead = 0;
    while (offset < manifest.length &&
      (numRead = manifestStream.read(manifest, offset, manifest.length - offset)) >= 0)
    {
      offset += numRead;
    }
    manifestStream.close();
    String manifestAsString = new String(manifest);
    String[] manifestItem = manifestAsString.split("\n");
    // Store in properties
    int count = 0;
    while (count < manifestItem.length) {
      if (manifestItem[count].indexOf("Name") != -1) {
        String value = manifestItem[count].split(":")[1];
        name = value.trim();
      }
      if (manifestItem[count].indexOf("Description") != -1) {
        String value = manifestItem[count].split(":")[1];
        description = value.trim();
      }
      if (manifestItem[count].indexOf("Layout") != -1) {
        try {
          String value = manifestItem[count].split(":")[1];
          layout = Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
        }
      }
      if (manifestItem[count].indexOf("Style") != -1) {
        try {
          String value = manifestItem[count].split(":")[1];
          style = Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
        }
      }
      count++;
    }
  }

  public String getFilename() {
    return filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }

  public String getLocale() {
    return locale;
  }

  public void setLocale(String locale) {
    this.locale = locale;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getVersion() {
    return version;
  }

  public void setVersion(double version) {
    this.version = version;
  }

  public String getVendor() {
    return vendor;
  }

  public void setVendor(String vendor) {
    this.vendor = vendor;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Timestamp getEntered() {
    return entered;
  }

  public void setEntered(Timestamp entered) {
    this.entered = entered;
  }

  public Timestamp getModified() {
    return modified;
  }

  public void setModified(Timestamp modified) {
    this.modified = modified;
  }

  public int getLayout() {
    return layout;
  }

  public void setLayout(int layout) {
    this.layout = layout;
  }

  public boolean hasLayout() {
    return layout > -1;
  }

  public int getStyle() {
    return style;
  }

  public void setStyle(int style) {
    this.style = style;
  }

  public boolean hasStyle() {
    return style > -1;
  }
}
