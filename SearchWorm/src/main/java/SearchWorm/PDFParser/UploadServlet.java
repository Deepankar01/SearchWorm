package SearchWorm.PDFParser;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;

import java.io.*;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by abhinav on 24/10/15.
 */

public class UploadServlet extends HttpServlet {

    private boolean isMultipart;
    private static String filePath;
    private int maxFileSize = 50 * 1024 * 1024;
    private int maxMemSize = 4 * 1024;
    private File file;

    public void init() {
        // Get the file location where it would be stored.
//        filePath = getServletContext().getInitParameter("file-upload-path");
//        filePath = getServletContext().getContextPath();
        filePath = getServletContext().getRealPath("") + "/web/";

    }


    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws ServletException, IOException {
        // Check that we have a file upload request
        isMultipart = ServletFileUpload.isMultipartContent(request);
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        if (!isMultipart) {
            renderTop(out);

            out.print("<p class='lead'>No file uploaded</p>");

            renderEnd(out);
            return;
        }
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // maximum size that will be stored in memory
        factory.setSizeThreshold(maxMemSize);
        // Location to save data that is larger than maxMemSize.
        factory.setRepository(new File(filePath + "temp"));

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);
        // maximum file size to be uploaded.
        upload.setSizeMax(maxFileSize);

        try {
            // Parse the request to get file items.
            List fileItems = upload.parseRequest(request);

            // Process the uploaded file items
            Iterator i = fileItems.iterator();

            renderTop(out);
            while (i.hasNext()) {
                FileItem fi = (FileItem) i.next();
                if (!fi.isFormField()) {
                    // Get the uploaded file parameters
                    String fileName = fi.getName();
                    fileName = fileName.replaceAll(",", "_");
                    // Write the file
                    if (fileName.lastIndexOf("\\") >= 0) {
                        file = new File(filePath +
                                fileName.substring(fileName.lastIndexOf("\\")));
                    } else {
                        file = new File(filePath +
                                fileName.substring(fileName.lastIndexOf("\\") + 1));
                    }
                    fi.write(file);
//                    out.println("Uploaded Filename: " + fileName + "<br>");

                    out.print("<p class='lead'>Uploaded Filename: " + fileName + "</p>");

                    func(fileName);
                }
            }
            renderEnd(out);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter out = resp.getWriter();
        renderTop(out);
        out.print("<p class='lead'>Upload Book</p>" +
                "<form action='/upload' method='post' enctype='multipart/form-data'>" +
                "    <div class='input-group'>" +
                "         <label class='myLabel'>" +
                "              <input type='file' name='file' size='50' multiple/>" +
                "              <span class='btn-lg btn-success'><i class='glyphicon glyphicon-upload'></i>&nbsp;&nbsp;&nbsp;&nbsp;Choose files</span>" +
                "         </label>" +
                "         <br/>" +
                "         <label class='myLabel'>" +
                "              <input type='submit' value='Upload' class='btn-lg btn-success'>" +
                "         </label>" +
                "     </div>" +
                "</form>");
        renderEnd(out);
    }

    public static void func(String filename) throws Exception {
        // TODO Auto-generated method stub
        PDDocument document = PDDocument.load(filePath + filename);

        Dictionary<String, String> metaDict;
        Settings settings = ImmutableSettings.settingsBuilder()
                .put("cluster.name", "searchworm").build();
        SearchWormElasticSearch searchWormElasticSearch = new SearchWormElasticSearch(settings);


        //Class to read the pages of the book
        ReadPages readPages = new ReadPages(document);

        //Class to get the metaData of the object
        MetaData metaData = new MetaData(document);
        metaDict = metaData.getMetaData();

        for (int i = 1; i <= Integer.parseInt(metaDict.get("PageCount")); i++) {
            //To Index the data to the cluster
            searchWormElasticSearch.addData(filename, String.valueOf(i), readPages.getPages(i, i));
//            searchWormElasticSearch.addData(metaDict.get("Title"), String.valueOf(i), readPages.getPages(i, i));
        }

        searchWormElasticSearch.closeClient();
    }

    private static void renderTop(PrintWriter out) {
        out.println("<!DOCTYPE html>" +
                "<html lang='en'>" +
                "<head>" +
                "    <meta charset='UTF-8'>" +
                "    <meta http-equiv='X-UA-Compatible' content='IE=edge'>" +
                "    <meta name='viewport' content='width=device-width, initial-scale=1'>" +
                "    <link href='css/bootstrap.min.css' rel='stylesheet'>" +
                "    <link href='css/searchworm.css' rel='stylesheet'>" +
                "    <title>Upload Books</title>" +
                "    <style>" +
                "        label.myLabel input[type='file'] {" +
                "            position: fixed;" +
                "            top: -1000px;" +
                "        }" +
                "    </style>" +
                "</head>" +
                "<body>" +

                "<!--Content for Header-->" +
                "<div id='header'>" +

                "    <nav class='navbar navbar-default'>" +
                "        <div class='container-fluid'>" +
                "            <div class='navbar-header'>" +
                "                <a class='navbar-brand' href='#'>" +
                "                    <img src='images/logo.png' class='header-img'/>" +
                "                    <p class='header-text'>SearchWorm</p>" +
                "                </a>" +
                "            </div>" +
                "            <ul class='nav navbar-nav navbar-right'>" +
                "                <li><a href='/upload'>Upload Books</a></li>" +
                "                <li><a href='/search'>Search Books</a></li>" +
                "            </ul>" +
                "        </div>" +
                "    </nav>" +
                "</div>" +

                "<!--Content for Main Container-->" +
                "<div id='mainContainer'>" +
                "    <div class='row'>" +
                "        <div class='col-12 col-lg-12 col-sm-12 col-md-12'>&nbsp;</div>" +
                "    </div>" +
                "    <div class='row'>" +
                "        <div class='col-12 col-lg-12 col-sm-12 col-md-12'>&nbsp;</div>" +
                "    </div>" +
                "    <div class='row'>" +
                "        <div class='col-12 col-lg-12 col-sm-12 col-md-12'>&nbsp;</div>" +
                "    </div>" +
                "    <div class='row'>" +
                "        <div class='col-12 col-lg-12 col-sm-12 col-md-12'>&nbsp;</div>" +
                "    </div>" +
                "    <div class='row'>" +
                "        <div class='col-12 col-lg-12 col-sm-12 col-md-12'>&nbsp;</div>" +
                "    </div>" +
                "    <div class='row'>" +
                "        <div class='col-12 col-lg-12 col-sm-12 col-md-12'>&nbsp;</div>" +
                "    </div>" +
                "    <div class='row'>" +
                "        <div class='col-12 col-lg-12 col-sm-12 col-md-12'>&nbsp;</div>" +
                "    </div>" +
                "    <div class='row'>" +
                "        <div class='col-1 col-lg-1 col-sm-1 col-md-1'></div>" +
                "        <div class='col-10 col-lg-10 col-sm-10 col-md-10'>" +
                "            <!--Beginning of Panel-->" +
                "<div class='panel panel-default'>" +
                "                <div class='panel-body'>");
    }

    private static void renderEnd(PrintWriter out) {
        out.print("                </div>" +
                "            </div>" +
                "            <!--End of Panel-->" +
                "        </div>" +
                "        <div class='col-1 col-lg-1 col-sm-1 col-md-1'></div>" +
                "    </div>" +
                "</div>" +
                "<!--Javascript Included-->" +
                "<script href='js/jquery-2.1.1.min.js'/></script>" +
                "<script href='js/bootstrap.min.js' /></script>" +
                "</body>" +
                "</html>");
    }
}