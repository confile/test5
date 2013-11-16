/**
 * Copyright 2011 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package test.client.application;

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

public class ApplicationView extends ViewWithUiHandlers<ApplicationUiHandlers> implements ApplicationPresenter.MyView {
    interface Binder extends UiBinder<Widget, ApplicationView> {
    }

    
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
		String DEFAULT_CSS = "test/client/application/ApplicationView.css";
		
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
		String test();

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

	
	private PickupDragController dragController;
	
	private DropController dropController;
	
    @Inject
    ApplicationView(Binder uiBinder, final Resources resources) {
    	this.resources = resources;
		resources.screenCss().ensureInjected();
        initWidget(uiBinder.createAndBindUi(this));
        
        
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
    
    
    @Override
    public void resetAndFocus() {
//        nameField.setFocus(true);
//        nameField.selectAll();
    }

    @Override
    public void setError(String errorText) {
//        error.setText(errorText);
    }

    
    
    
}
