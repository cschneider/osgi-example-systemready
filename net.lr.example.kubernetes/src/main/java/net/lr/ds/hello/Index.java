package net.lr.ds.hello;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.http.whiteboard.propertytypes.HttpWhiteboardServletPattern;

@Component
@HttpWhiteboardServletPattern("/")
public class Index extends HttpServlet implements Servlet {

	private static final long serialVersionUID = 1L;
	private static final UUID ID = UUID.randomUUID();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter wr = resp.getWriter();


		wr.write("<html>\n<head>\n" +
				"<style type=\"text/css\" media=\"screen\">\n" +
                        "input, a {\n" +
                        "    display: inline-block;\n" +
                        "    border: none;\n" +
                        "    background: none;\n" +
                        "    cursor: pointer;\n" +
                        "    margin: 0;\n" +
                        "    padding: 0;\n" +
                        "    font: normal medium \"Verdana\";\n" +
                        "    position: relative;\n" +
                        "    text-decoration: none;\n" +
                        "    color: #3D8AC6;\n" +
                        "    text-shadow: 0 1px 1px rgba(255,255,255,0.9);\n" +
                        "}\n" +

                        "input:hover, a:hover {\n" +
                        "    color: #3C9CE7;\n" +
                        "}\n" +

                        ".inputdiv:before, a:before {\n" +
                        "    margin: 0;\n" +
                        "    padding: 0;\n" +
                        "    color: #6AB3EC;\n" +
                        "    content: \"\\25BA\";\n" +
                        "    font-size: 80%;\n" +
                        "    display: inline-block;\n" +
                        "    padding-right: 3px;\n" +
                        "    pointer-events: none;\n" +
                        "}\n" +

                        ".inputdiv:hover:before, a:hover:before {\n" +
                        "    color: #F2BF97;\n" +
                        "}\n    "+

						"h1 {\n" +
						"  font-size: x-large;\n" +
						"}\n" +

                        "h2 {\n" +
                        "  font-size: large;\n" +
                        "}\n" +

                        "div.container {\n" +
                        "  margin: 30px;\n" +
                        "  padding: 10px;\n     " +
                        "}\n" +

				"</style></head>\n<body>\n");

		wr.write("<div class=\"container\">");
		wr.write("<h1>AdaptTo 2018 kubernetes example</h1>");
		wr.write("<h2>ID: " + ID.toString().substring(0, 3)+ " (first 3 chars of random UUID)</h2>"   );

		wr.write("<p><form action=\"/control/notready\" method=\"post\">\n");
		wr.write("<div class=\"inputdiv\"><input type=\"submit\" value=\"Report not ready for 60 seconds\"</input></div>\n");
		wr.write("</form></p>\n");
		wr.write("<p><form action=\"/control/notalive\" method=\"post\">\n");
		wr.write("<div class=\"inputdiv\"><input type=\"submit\" value=\"Report not alive\"</input></div>\n");
		wr.write("</form></p>\n");
		wr.write("<p><a href=\"/systemready\">Show ready state</a></p>\n");
		wr.write("<p><a href=\"/systemalive\">Show alive state</a></p>\n");
		wr.write("</div>");
		wr.write("</body></html>");
	}
}
