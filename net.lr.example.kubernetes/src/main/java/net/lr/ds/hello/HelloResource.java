package net.lr.ds.hello;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.http.whiteboard.propertytypes.HttpWhiteboardResource;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsResource;

@Component(service=HelloResource.class)
@JaxrsResource
@Path("hello")
@Produces(MediaType.APPLICATION_JSON)
@HttpWhiteboardResource(pattern="/rest/*", prefix="static")
public class HelloResource {
	
	@Reference
	HelloComponent hello;

	@GET
	public String get() {
		return hello.say();
	}
	
}
