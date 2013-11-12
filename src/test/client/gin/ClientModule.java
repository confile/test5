package test.client.gin;


import test.client.place.ClientPlaceManager;
import test.client.place.NameTokens;
import test.client.presenters.MainPresenter;
import test.client.views.MainView;

import com.gwtplatform.mvp.client.annotations.DefaultPlace;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class ClientModule extends AbstractPresenterModule {

	@Override
	protected void configure() {
		install(new DefaultModule(ClientPlaceManager.class));
//		install(new ApplicationModule());
				
		bindConstant().annotatedWith(DefaultPlace.class).to(NameTokens.main);

//		install(new ApplicationModule());
		
		bindPresenter(MainPresenter.class, MainPresenter.MyView.class,	MainView.class, MainPresenter.MyProxy.class);


	}
}
