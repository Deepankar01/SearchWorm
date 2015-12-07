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
        filePath = getServletContext().getInitParameter("file-upload-path");
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws ServletException, IOException {
        // Check that we have a file upload request
        isMultipart = ServletFileUpload.isMultipartContent(request);
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        if (!isMultipart) {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet upload</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<p>No file uploaded</p>");
            out.println("</body>");
            out.println("</html>");
            return;
        }
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // maximum size that will be stored in memory
        factory.setSizeThreshold(maxMemSize);
        // Location to save data that is larger than maxMemSize.
        factory.setRepository(new File(filePath+"temp"));

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);
        // maximum file size to be uploaded.
        upload.setSizeMax(maxFileSize);

        try {
            // Parse the request to get file items.
            List fileItems = upload.parseRequest(request);

            // Process the uploaded file items
            Iterator i = fileItems.iterator();

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet upload</title>");
            out.println("</head>");
            out.println("<body>");
            while (i.hasNext()) {
                FileItem fi = (FileItem) i.next();
                if (!fi.isFormField()) {
                    // Get the uploaded file parameters
                    String fieldName = fi.getFieldName();
                    String fileName = fi.getName();
                    String contentType = fi.getContentType();
                    boolean isInMemory = fi.isInMemory();
                    long sizeInBytes = fi.getSize();
                    // Write the file
                    if (fileName.lastIndexOf("\\") >= 0) {
                        file = new File(filePath +
                                fileName.substring(fileName.lastIndexOf("\\")));
                    } else {
                        file = new File(filePath +
                                fileName.substring(fileName.lastIndexOf("\\") + 1));
                    }
                    fi.write(file);
                    out.println("Uploaded Filename: " + fileName + "<br>");

                    func(fileName);
                }
            }
            out.println("</body>");
            out.println("</html>");
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {
        response.getWriter().print("<!DOCTYPE HTML>" +
                "<html>" +
                "<head>" +
                "    <title>File Uploading Form</title>" +
                "</head>" +
                "<body>" +
                "<h3>File Upload:</h3>" +
                "Select a file to upload: <br/>" +
                "" +
                "<form action=\"upload\" method=\"post\"" +
                "      enctype=\"multipart/form-data\">" +
                "    <input type=\"file\" name=\"file\" size=\"50\" multiple/>" +
                "    <br/>" +
                "    <input type=\"submit\" value=\"Upload File\"/>" +
                "</form>" +
                "</body>" +
                "</html>");
    }

    public static void func(String filename) throws Exception {
        // TODO Auto-generated method stub
        PDDocument document = PDDocument.load(filePath+filename);

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
            searchWormElasticSearch.addData(metaDict.get("Title"), String.valueOf(i), readPages.getPages(i, i));
        }

        searchWormElasticSearch.closeClient();
    }

}