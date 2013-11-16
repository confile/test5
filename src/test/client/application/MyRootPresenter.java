package test.client.application;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.RootPresenter;

public class MyRootPresenter extends RootPresenter {

	public static final class MyRootView extends RootView {
	   @Override
		public void setInSlot(Object slot, IsWidget content) {
		   RootPanel.get("GwtContainer").add(content);
		}
	}
	
	@Inject
	public MyRootPresenter( EventBus eventBus, MyRootView myRootView) {
		super( eventBus, myRootView);
	}
	
}
