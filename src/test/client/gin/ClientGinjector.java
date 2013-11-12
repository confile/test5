package test.client.gin;

import test.client.presenters.MainPresenter;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

@GinModules({ ClientModule.class })
public interface ClientGinjector extends Ginjector {

	EventBus getEventBus();

	PlaceManager getPlaceManager();


	AsyncProvider<MainPresenter> getMainPresenter();

}
