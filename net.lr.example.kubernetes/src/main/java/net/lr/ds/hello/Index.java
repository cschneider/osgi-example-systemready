package net.lr.ds.hello;

import java.io.IOException;
import java.io.PrintWriter;

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

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter wr = resp.getWriter();
		wr.write("<html><body>\n");
		wr.write("<p><a href=\"/control/notready\">Report not ready for 60 seconds</a></p>");
		wr.write("<p><a href=\"/control/notalive\">Report not alive</a></p>");
		wr.write("<p><a href=\"/systemready\">Show ready state</a></p>");
		wr.write("<p><a href=\"/systemalive\">Show alive state</a></p>");

		wr.write("</body></html>");
	}
}
