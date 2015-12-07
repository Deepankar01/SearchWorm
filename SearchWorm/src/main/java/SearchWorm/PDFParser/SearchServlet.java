package SearchWorm.PDFParser;

import org.elasticsearch.common.lucene.docset.AllDocIdSet;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by abhinav on 25/10/15.
 */
public class SearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);

        PrintWriter out = resp.getWriter();
        out.print("<h1>Enter search query</h1>" +
                "<form action='search' method='post'>" +
                "<input type='search' placeholder='Type to Search!' name='query' value=''>" +
                "<input type='submit'>" +
                "</form>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Settings settings = ImmutableSettings.settingsBuilder()
                .put("cluster.name", "searchworm").build();
        SearchWormElasticSearch swES = new SearchWormElasticSearch(settings);
        String query = req.getParameter("query");

        PrintWriter out = resp.getWriter();
        out.print("<h1>Enter search query</h1>" +
                "<form action='search' method='post'>" +
                "<input type='search' placeholder='Type to Search!' name='query' value=''>" +
                "<input type='submit'>" +
                "</form>");
        out.print("Showing results for query: " + query+"<br/>");
        jsonParser(swES.searchBooks(query),out);

        swES.closeClient();
    }
    private void jsonParser(String s, PrintWriter out) {

        JSONObject jObj1 = new JSONObject(s);
        JSONObject jObj2 = jObj1.getJSONObject("hits");
        JSONArray jsonArray = jObj2.getJSONArray("hits");

        String bookname;
        int pageNumber;
        String content;

        for (int i = 0; i < jsonArray.length(); i++) {
            out.print("!!!!!!!!!!BEGIN!!!!!!!!!!!!<br/>");
            JSONObject iObj = jsonArray.getJSONObject(i);
            bookname = iObj.getString("_type");
            pageNumber = iObj.getInt("_id");

            content = iObj.getJSONObject("_source").getString("content");
            out.print(bookname + " : " + pageNumber+" : "+content+"<br/>");
            out.print("!!!!!!!!!!!END!!!!!!!!!!!<br/>");

        }
    }
}
