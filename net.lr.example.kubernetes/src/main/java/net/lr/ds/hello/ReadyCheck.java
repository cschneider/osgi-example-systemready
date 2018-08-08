package net.lr.ds.hello;

import org.apache.felix.systemready.CheckStatus;
import org.apache.felix.systemready.StateType;
import org.apache.felix.systemready.SystemReadyCheck;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import net.lr.ds.hello.ReadyCheck.Config;

@Component(configurationPolicy=ConfigurationPolicy.REQUIRE)
@Designate(ocd=Config.class)
public class ReadyCheck implements SystemReadyCheck {
	
	@ObjectClassDefinition(
            name="ReadyCheck"
    )
    public static @interface Config {
		String dummy();
    }

	private long startTime;
	
	@Activate
	public void activate() {
		startTime = System.currentTimeMillis();
	}

	@Override
	public String getName() {
		return "Switchable ready check";
	}

	@Override
	public CheckStatus getStatus() {
		CheckStatus.State state = System.currentTimeMillis() - startTime < 60000 ? CheckStatus.State.RED : CheckStatus.State.GREEN;
		return new CheckStatus(getName(), StateType.READY, state , "");
	}

}
