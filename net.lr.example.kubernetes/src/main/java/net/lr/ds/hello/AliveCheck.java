package net.lr.ds.hello;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.felix.hc.annotation.HealthCheckService;
import org.apache.felix.hc.api.HealthCheck;
import org.apache.felix.hc.api.Result;
import org.apache.felix.hc.api.Result.Status;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.http.whiteboard.propertytypes.HttpWhiteboardServletPattern;

@SuppressWarnings("serial")
@Component
@HttpWhiteboardServletPattern("/control/notalive")
@HealthCheckService(name = "alivecheck", tags= {"alive"})
public class AliveCheck extends HttpServlet implements HealthCheck, Servlet {
	private org.apache.felix.hc.api.Result.Status status = Status.OK;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.status = Status.CRITICAL;
		resp.getWriter().print("Reporting not alive. Our instance should be killed soon");
	}

	@Override
	public Result execute() {
		return new Result(status, "");
	}
}
