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
@HttpWhiteboardServletPattern("/control/notready")
@HealthCheckService(name = "readycheck", tags = {"ready"})
public class ReadyCheck extends HttpServlet implements HealthCheck, Servlet {
	
	private long notReadyStartTime;
	private long notReadyEndTime;

	@Override
	public Result execute() {
		Result.Status state = !isReady() ? Status.CRITICAL : Status.OK;
		String desc = !isReady()
				? (notReadyEndTime - System.currentTimeMillis())/ 1000 + " seconds left to report NOT ready"
				: "Set to report ready";
		return new Result(state , desc);
	}

	private boolean isReady() {
		long curTime = System.currentTimeMillis();
		return curTime < notReadyStartTime || curTime > notReadyEndTime;
	}

	public void notReadyForSeconds(int seconds) {
		this.notReadyStartTime = System.currentTimeMillis();
		this.notReadyEndTime = this.notReadyStartTime + seconds * 1000;
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		notReadyForSeconds(60);
		resp.getWriter().print("Reporting not ready for 60 seconds");
	}
}
