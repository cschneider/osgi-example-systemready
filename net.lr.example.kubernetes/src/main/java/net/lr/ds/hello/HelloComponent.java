package net.lr.ds.hello;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

@Component
public class HelloComponent {

	@Activate
	public void start() {
		System.out.println("Hello World");
	}
}
