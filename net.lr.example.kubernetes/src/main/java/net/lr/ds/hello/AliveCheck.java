package net.lr.ds.hello;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.felix.systemready.CheckStatus;
import org.apache.felix.systemready.CheckStatus.State;
import org.apache.felix.systemready.StateType;
import org.apache.felix.systemready.SystemReadyCheck;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.http.whiteboard.propertytypes.HttpWhiteboardServletPattern;

@SuppressWarnings("serial")
@Component
@HttpWhiteboardServletPattern("/control/notalive")
public class AliveCheck extends HttpServlet implements SystemReadyCheck, Servlet {
	private CheckStatus.State state = State.GREEN;
	
	@Override
	public String getName() {
		return "Toggable alive check";
	}

	@Override
	public CheckStatus getStatus() {
		return new CheckStatus(getName(), StateType.ALIVE, state , "This is a toggable test check");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.state = State.RED;
		resp.getWriter().print("Reporting not alive. Our instance should be killed soon");
	}
}
