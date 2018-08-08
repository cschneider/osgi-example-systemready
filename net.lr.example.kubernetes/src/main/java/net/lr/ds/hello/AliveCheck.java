package net.lr.ds.hello;

import org.apache.felix.systemready.CheckStatus;
import org.apache.felix.systemready.StateType;
import org.apache.felix.systemready.SystemReadyCheck;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import net.lr.ds.hello.AliveCheck.Config;

@Component(configurationPolicy=ConfigurationPolicy.REQUIRE)
@Designate(ocd=Config.class)
public class AliveCheck implements SystemReadyCheck {
	@ObjectClassDefinition(
            name="AliveCheck"
    )
    public static @interface Config {
		String dummy();
    }
	
	@Override
	public String getName() {
		return "Switchable alive check";
	}

	@Override
	public CheckStatus getStatus() {
		return new CheckStatus(getName(), StateType.ALIVE, CheckStatus.State.RED , "");
	}

}
