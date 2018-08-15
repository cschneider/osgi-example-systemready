package net.lr.ds.hello;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.Scanner;

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
	private String styles;

	public Index() {
		InputStream in = this.getClass().getResourceAsStream("/styles.css");
		try (Scanner scanner = new Scanner(in, "UTF-8")) {
			styles = scanner.useDelimiter("\\A").next();
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String hostname = InetAddress.getLocalHost().getHostName();
		PrintWriter wr = resp.getWriter();
		wr.write("<html><style>\n");
		wr.write(styles);
		wr.write("</style><body>\n");

		wr.write("<div class=\"container\">");
		wr.write("<h1>AdaptTo 2018 kubernetes example</h1>");
		wr.write("<p>Hostname: " + hostname +"</p>");

		wr.write("<p><form action=\"/control/notready\" method=\"post\">\n");
		wr.write("<input type=\"submit\" value=\"Report not ready for 60 seconds\"</input>\n");
		wr.write("</form></p>\n");
		wr.write("<p><form action=\"/control/notalive\" method=\"post\">\n");
		wr.write("<input type=\"submit\" value=\"Report not alive\"</div>\n");
		wr.write("</form></p>\n");
		
		wr.write("<p><a href=\"/systemready\">Show ready state</a></p>\n");
		wr.write("<p><a href=\"/systemalive\">Show alive state</a></p>\n");
		wr.write("</div>");
		wr.write("</body></html>");
	}
}
