package test.client;

import test.client.gin.ClientGinjector;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.gwtplatform.mvp.client.DelayedBindRegistry;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Test5 implements EntryPoint {

	
	private final ClientGinjector ginjector = GWT.create(ClientGinjector.class);
	
	
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		DelayedBindRegistry.bind(ginjector);
		ginjector.getPlaceManager().revealCurrentPlace();
		

//		RootPanel.get("errorLabelContainer").add(errorLabel);


	}
}
