package test.client.presenters;


import test.client.place.NameTokens;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;


public class MainPresenter extends
		Presenter<MainPresenter.MyView, MainPresenter.MyProxy> implements
		MainViewUiHandlers {

	
	public static final Object SCREEN_SLOT = new Object();
	
	public interface MyView extends View, HasUiHandlers<MainViewUiHandlers> {
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.main)
	public interface MyProxy extends ProxyPlace<MainPresenter> {
	}
	

	
	
	@Inject
	public MainPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy) {
		super(eventBus, view, proxy);
		view.setUiHandlers(this);
	}

	@Override
	protected void revealInParent() {
		RevealRootContentEvent.fire(this, this);
//		RevealRootLayoutContentEvent.fire(this, this);
		
	}
	

	@Override
	protected void onBind() {
		super.onBind();
	}
	
	
	@Override
	protected void onReveal() {
		super.onReveal();
	}

	

	
 
	
}
