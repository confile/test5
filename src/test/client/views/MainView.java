package test.client.views;

import test.client.presenters.MainPresenter;
import test.client.presenters.MainViewUiHandlers;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.DragEndEvent;
import com.allen_sauer.gwt.dnd.client.DragHandler;
import com.allen_sauer.gwt.dnd.client.DragStartEvent;
import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.allen_sauer.gwt.dnd.client.drop.AbsolutePositionDropController;
import com.allen_sauer.gwt.dnd.client.drop.DropController;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;



public class MainView extends ViewWithUiHandlers<MainViewUiHandlers> implements MainPresenter.MyView {

	/**
	 * Resources that match the GWT standard style theme.
	 */
	public interface Resources extends ClientBundle {

	
		/**
		 * The styles used in this widget.
		 */
		@Source({ Css.DEFAULT_CSS })
		Css screenCss();
	}
	
	public interface Css extends CssResource {
		/**
		 * The path to the default CSS styles used by this resource.
		 */
		String DEFAULT_CSS = "test/client/views/MainView.css";
		
		int dragSensitivity();
		String viewportContainer();
		String focusPanel();
		String canvas();
		
		
		String mapContainer();
			
		String note();
		String noteFocusPanel();
		String test1();
		String test2();
		String test5();

	}
	
	@UiField(provided = true)
	Resources resources;
	
	
	@UiField
	ScrollPanel scrollPanel;

	@UiField
	AbsolutePanel viewport;
	
	@UiField
	AbsolutePanel canvas;
	
	@UiField
	FlowPanel test1;
//	
	@UiField
	FlowPanel test2;
	
	
//	@UiField
//	HTMLPanel panel5;
	
	private PickupDragController dragController;
	
	private DropController dropController;
	
	
	public interface Binder extends UiBinder<Widget, MainView> {
	}

	
	@Inject
	public MainView(final Binder binder, final Resources resources) {
		this.resources = resources;
		resources.screenCss().ensureInjected();
		initWidget(binder.createAndBindUi(this));
		
		
		scrollPanel.addAttachHandler(new AttachEvent.Handler() {
			
			@Override
			public void onAttachOrDetach(AttachEvent event) {
				if (event.isAttached()) {
					updateCanvasSize();
				}
			}
		});
		
		Window.addResizeHandler(new ResizeHandler() {

			@Override
			public void onResize(ResizeEvent event) {
				updateCanvasSize();
			}
		});
		
		
		// create drag controller
//		dragController = new PickupDragController(canvas, true);
		dragController = new PickupDragController(viewport, true);  // original
		dragController.addDragHandler(new DragHandler() {
			
			@Override
			public void onPreviewDragStart(DragStartEvent event)
					throws VetoDragException {
				GWT.log("drag preview start");
			}
			
			@Override
			public void onPreviewDragEnd(DragEndEvent event) throws VetoDragException {
				GWT.log("drag preview end");
			}
			
			@Override
			public void onDragStart(DragStartEvent event) {
				GWT.log("drag start");				
			}
			
			@Override
			public void onDragEnd(DragEndEvent event) {
				GWT.log("drag end");				
			}
		});
		dragController.setBehaviorDragStartSensitivity(resources.screenCss().dragSensitivity());
		dragController.setBehaviorBoundaryPanelDrop(false);
		
		
		// instantiate our drop controller
		dropController = new AbsolutePositionDropController(canvas) {  // original
//		dropController = new AbsolutePositionDropController(viewport) {
			@Override
			public void onDrop(DragContext context) {
				super.onDrop(context);
				GWT.log("onDrop");				
			}
			
		};
		dragController.registerDropController(dropController);
		
		
//		test1.addStyleName(resources.screenCss().note());
		
		FocusPanel focusPanel = new FocusPanel();
		focusPanel.addStyleName(resources.screenCss().noteFocusPanel());
//		test1.add(focusPanel);
				
//		canvas.add(test1, 10, 10);
		
		// test
//		dragController.makeDraggable(test1, focusPanel);
		
		
		// drag and drop map notes enabled
		
		
		FlowPanel notePanel = new FlowPanel();
		notePanel.addStyleName(resources.screenCss().note());
		
		// note drag and drop
		FocusPanel noteFocusPanel = new FocusPanel();
		noteFocusPanel.addStyleName(resources.screenCss().noteFocusPanel());
		notePanel.add(noteFocusPanel);
		
		// don't pass click events to the canvas
		noteFocusPanel.addClickHandler(clickStopPropagationHandler);
		
		
		// flow panel for all note elements
		Panel noteFlowPanel = new FlowPanel();
		noteFocusPanel.add(noteFlowPanel);
		
		// note body label
		HTML bodyLabel = new HTML();
		bodyLabel.setText("hello");
		noteFlowPanel.add(bodyLabel);
		
		// add note to canvas
		//canvas.add(notePanel, 100, 100);
		
		// drag and drop notes enabled
		dragController.makeDraggable(notePanel, noteFocusPanel);
		
//		dragController.makeDraggable(canvas);
		

	}

	
	

	
	
	private static ClickHandler clickStopPropagationHandler = new ClickHandler() {

		@Override
		public void onClick(ClickEvent event) {
			event.stopPropagation();
		}
	};
	
//	@Override
//	public int getScreenWidth() {
//		return scrollPanel.getOffsetWidth();
//	};
//	
//	@Override
//	public int getScreenHeight() {
//		return scrollPanel.getOffsetHeight();
//	};	
	
	
	private void updateCanvasSize() {
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {
				int maxX = scrollPanel.getOffsetWidth();
				int maxY = scrollPanel.getOffsetHeight();
//				for (NoteRepresentation noteRepresentation : representations) {
//					maxX = Math.max(noteRepresentation.note.getX() + 180
//							+ resources.canvasStyle().sizePadding(), maxX);
//					maxY = Math.max(noteRepresentation.note.getY() + 260
//							+ resources.canvasStyle().sizePadding(), maxY);
//				}
				
				viewport.setPixelSize(maxX, maxY);
				canvas.setPixelSize(maxX, maxY);
			}
		});

	}	
	 

}
