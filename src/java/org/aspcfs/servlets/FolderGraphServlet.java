/*
 *  Copyright(c) 2007 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.servlets;

import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.servlets.DefaultServlet;
import com.darkhorseventures.framework.actions.ActionContext;

/**
 * This servlet will be used to read the Graph file and set to outputstrean
 *
 * @author dharmas
 * @version $Id: FolderGraphServlet.java 4.1 2007-04-23 17:46:33 +0530 (Mon, 23 Apr 2007) dharmas Exp $
 * @created Apr 23, 2007
 */
public class FolderGraphServlet extends DefaultServlet {

    /**
     * This method will read the foldergraph image file and write the data to the outputstream
     *
     * @param context Description of the Parameter
     * @throws ServletException
     * @throws IOException
     */
    public void doGet(ActionContext context)
            throws ServletException, IOException {
        //NOTE: How secure is this servlet?
        HttpServletRequest request = context.getRequest();
        HttpServletResponse response = context.getResponse();

        FileInputStream folderGraphInStream = null;
        ServletOutputStream folderGraphOutStream = null;
        String fileName = getPath(context) + "graphs" + fs + request.getParameter("GraphFileName") + ".jpg";
        
        try {
            File folderGraphFile = new File(fileName);
            if (folderGraphFile.exists()) {
                response.setContentLength((int) folderGraphFile.length());
                response.setContentType("image/jpg");

                folderGraphInStream = new FileInputStream(folderGraphFile);
                folderGraphOutStream = response.getOutputStream();

                byte[] folderGraphBuf = new byte[1024];
                int count = 0;
                while ((count = folderGraphInStream.read(folderGraphBuf)) >= 0) {
                    folderGraphOutStream.write(folderGraphBuf, 0, count);
                }
            } else {
                System.out.println("FolderGraph Servlet -> File Not Found");
            }
        } catch (IOException ioe) {
            System.out.println("Exception in FolderGraph Servlet");
            ioe.printStackTrace();
        } finally {
            folderGraphOutStream.flush();
            folderGraphInStream.close();
            folderGraphOutStream.close();
        }
    }

}
