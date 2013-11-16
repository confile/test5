package test.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.gwtplatform.mvp.client.ApplicationController;

public class Testgwtp implements EntryPoint {
	private static final ApplicationController controller = GWT.create(ApplicationController.class);

	
	@Override
	public void onModuleLoad() {
		controller.init();
		
		GWT.log("onModuleLoad()");
	}
	
}
