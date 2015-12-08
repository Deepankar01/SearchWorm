/*
package SearchWorm.PDFParser;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Dictionary;
import java.util.Iterator;
import java.util.List;

*/
/**
 * Created by abhinav on 7/12/15.
 *//*


public class Servlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);

        PrintWriter out = resp.getWriter();

        renderTop(out);
        out.print("<p class='lead'>What do you want to search today</p>\n" +
                "\n" +
                "                    <form action='serv' method='post'>\n" +
                "                    <div class='input-group'>\n" +
                "                        <input type='search' class='form-control' id='searchBox' placeholder='Type to Search!'\n" +
                "                               name='query' value=''>\n" +
                "\n" +
                "                        <div class='input-group-btn'>\n" +
                "                            <button type='submit' class='btn btn-success'><i class='glyphicon glyphicon-search'></i>&nbsp;&nbsp;&nbsp;&nbsp;Search\n" +
                "                                Books\n" +
                "                            </button>\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                    </form>");
        renderEnd(out);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Settings settings = ImmutableSettings.settingsBuilder()
                .put("cluster.name", "searchworm").build();
        SearchWormElasticSearch swES = new SearchWormElasticSearch(settings);
        String query = req.getParameter("query");

        PrintWriter out = resp.getWriter();

        renderTop(out);
        out.print("<p class='lead'>What do you want to search today</p>\n" +
                "\n" +
                "                    <form action='serv' method='post'>\n" +
                "                    <div class='input-group'>\n" +
                "                        <input type='search' class='form-control' id='searchBox' placeholder='Type to Search!'\n" +
                "                               name='query' value=''>\n" +
                "\n" +
                "                        <div class='input-group-btn'>\n" +
                "                            <button type='submit' class='btn btn-success'><i class='glyphicon glyphicon-search'></i>&nbsp;&nbsp;&nbsp;&nbsp;Search\n" +
                "                                Books\n" +
                "                            </button>\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                    </form>");
        out.print("<p class='lead'>Showing results for query: " + query+"<p>");
        jsonParser(swES.searchBooks(query),out);

        renderEnd(out);
        swES.closeClient();
    }
    private void jsonParser(String s, PrintWriter out) {

        JSONObject jObj1 = new JSONObject(s);
        JSONObject jObj2 = jObj1.getJSONObject("hits");
        JSONArray jsonArray = jObj2.getJSONArray("hits");

        String bookname;
        int pageNumber;
        String content;
        out.print("<ol>");
        for (int i = 0; i < jsonArray.length(); i++) {
            out.print("<li>");
            JSONObject iObj = jsonArray.getJSONObject(i);
            bookname = iObj.getString("_type");
            pageNumber = iObj.getInt("_id");

            content = iObj.getJSONObject("_source").getString("content");
            out.print(bookname + " : " + pageNumber+"<a href='web/viewer.html?file="+bookname+"#page="+pageNumber+"'>Click to view</a>"+" : <br/>"+content.replaceAll("\n","<br/>")+"");
//            out.print();
            System.out.println(bookname + " : " + pageNumber+" : "+content);
            out.print("</li>");
        }
        out.print("</ol>");
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
                "    <title>Search</title>" +
                "    <style>" +
                "        label.myLabel input[type='file'] {" +
                "            position: fixed;" +
                "            top: -1000px;" +
                "        }" +
                "    </style>" +
                "</head>" +
                "<body>\n" +
                "\n" +
                "<!--Content for Header-->\n" +
                "<div id='header'>\n" +
                "\n" +
                "    <nav class='navbar navbar-default'>\n" +
                "        <div class='container-fluid'>\n" +
                "            <div class='navbar-header'>\n" +
                "                <a class='navbar-brand' href='#'>\n" +
                "                    <img src='images/logo.png' class='header-img'/>\n" +
                "\n" +
                "                    <p class='header-text'>SearchWorm</p>\n" +
                "                </a>\n" +
                "            </div>\n" +
                "            <ul class='nav navbar-nav navbar-right'>\n" +
                "                <li><a href='/upload'>Upload Books</a></li>\n" +
                "                <li><a href='/search'>Search Books</a></li>\n" +
                "            </ul>\n" +
                "        </div>\n" +
                "    </nav>\n" +
                "\n" +
                "</div>\n" +
                "\n" +
                "<!--Content for Main Container-->\n" +
                "<div id='mainContainer'>\n" +
                "\n" +
                "    <div class='row'>\n" +
                "        <div class='col-12 col-lg-12 col-sm-12 col-md-12'>&nbsp;</div>\n" +
                "    </div>\n" +
                "    <div class='row'>\n" +
                "        <div class='col-12 col-lg-12 col-sm-12 col-md-12'>&nbsp;</div>\n" +
                "    </div>\n" +
                "    <div class='row'>\n" +
                "        <div class='col-12 col-lg-12 col-sm-12 col-md-12'>&nbsp;</div>\n" +
                "    </div>\n" +
                "    <div class='row'>\n" +
                "        <div class='col-12 col-lg-12 col-sm-12 col-md-12'>&nbsp;</div>\n" +
                "    </div>\n" +
                "    <div class='row'>\n" +
                "        <div class='col-12 col-lg-12 col-sm-12 col-md-12'>&nbsp;</div>\n" +
                "    </div>\n" +
                "    <div class='row'>\n" +
                "        <div class='col-12 col-lg-12 col-sm-12 col-md-12'>&nbsp;</div>\n" +
                "    </div>\n" +
                "    <div class='row'>\n" +
                "        <div class='col-12 col-lg-12 col-sm-12 col-md-12'>&nbsp;</div>\n" +
                "    </div>\n" +
                "\n" +
                "    <div class='row'>\n" +
                "\n" +
                "        <div class='col-1 col-lg-1 col-sm-1 col-md-1'></div>\n" +
                "\n" +
                "        <div class='col-10 col-lg-10 col-sm-10 col-md-10'>\n" +
                "\n" +
                "            <!--Beginning of Panel-->\n" +
                "            <div class='panel panel-default'>\n" +
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
*/
