package SearchWorm.PDFParser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by abhinav on 25/10/15.
 */
public class SearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);

        PrintWriter out = resp.getWriter();
        out.print("<h1>Enter search query</h1>" +
                "<form action='search' method='get'>" +
                "<input type='search' placeholder='Type to Search!' name='query' value=''>" +
                "<input type='submit'>" +
                "</form>");
    }
}
