package org.aspcfs.modules.website.base;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipFile;

/**
 * Description of the Class
 *
 * @author mrajkowski
 * @version $Id: Exp $
 * @created May 15, 2006 $Id: Exp $
 */
public class TemplateList extends ArrayList {

  private String zipPath = null;
  private String template = null;

  public TemplateList() {
  }

  public String getZipPath() {
    return zipPath;
  }

  public void setZipPath(String zipPath) {
    this.zipPath = zipPath;
  }

  public String getTemplate() {
    return template;
  }

  public void setTemplate(String template) {
    this.template = template;
  }

  public void buildList(String path, String locale) throws IOException {
    String fs = System.getProperty("file.separator");
    // Check template directory for given locale, or default to en_US
    File templateDirectory = new File(path + fs + locale);
    if (!templateDirectory.isDirectory() ||
        templateDirectory.list().length == 0) {
      templateDirectory = new File(path + fs + "en_US");
    }
    if (templateDirectory.isDirectory() &&
        templateDirectory.list().length > 0) {
      zipPath = templateDirectory.getPath();
      // Build the template files
      String[] templates = templateDirectory.list();
      for (int i = 0; i < templates.length; i++) {
        ZipFile zipFile = new ZipFile(templateDirectory + fs + templates[i]);
        if (zipFile.getName().endsWith(".zip")) {
          // Filters
          if (template != null &&
              (!zipFile.getName().endsWith("/" + template + ".zip") ||
                  !zipFile.getName().endsWith("\\" + template + ".zip"))) {
            continue;
          }
          Template thisTemplate = new Template(zipFile);
          this.add(thisTemplate);
        }
        zipFile.close();
      }
    }
  }
}
