package net.lr.ds.hello;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.felix.systemready.CheckStatus;
import org.apache.felix.systemready.StateType;
import org.apache.felix.systemready.SystemReadyCheck;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.http.whiteboard.propertytypes.HttpWhiteboardServletPattern;

@SuppressWarnings("serial")
@Component
@HttpWhiteboardServletPattern("/control/notready")
public class ReadyCheck extends HttpServlet implements SystemReadyCheck, Servlet {
	
	private long notReadyStartTime;
	private long notReadyEndTime;

	
	@Override
	public String getName() {
		return "Toggable ready check";
	}

	@Override
	public CheckStatus getStatus() {
		CheckStatus.State state = !isReady() ? CheckStatus.State.RED : CheckStatus.State.GREEN;
		String desc = !isReady()
				? (notReadyEndTime - System.currentTimeMillis())/ 1000 + " seconds left to report NOT ready"
				: "Set to report ready";
		return new CheckStatus(getName(), StateType.READY, state , desc);
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
