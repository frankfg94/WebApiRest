package security;


import org.glassfish.jersey.server.ResourceConfig;


public class CustomApplication extends ResourceConfig {

	public CustomApplication()
	{
		packages("rest");
		register(SecurityManager.class);
	}

}
