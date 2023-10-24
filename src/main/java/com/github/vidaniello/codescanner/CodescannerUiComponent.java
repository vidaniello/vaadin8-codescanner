package com.github.vidaniello.codescanner;

import java.util.List;

import com.github.vidaniello.codescanner.CodescannerComponent.DecodedCodeListner;
import com.github.vidaniello.codescanner.CodescannerComponent.ErrorDecodedCodeListner;
import com.github.vidaniello.codescanner.CodescannerComponent.MediaDevicesReceivedListner;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.Registration;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;


public class CodescannerUiComponent extends CustomComponent {

	private static final long serialVersionUID = 1L;
	
	public static String startScanning = "Start scanning";
	public static String stoptScanning = "Stop scanning";
	
	private VerticalLayout content;
	private CodescannerComponent codescannerComponent;
	private Button startStopScanButton;
	
	public CodescannerUiComponent() {
		
		
		content = new VerticalLayout();
		setCompositionRoot(content);
		content.setSpacing(false);
		content.setMargin(false);
		
		codescannerComponent = new CodescannerComponent();
		content.addComponent(codescannerComponent);
		
		content.setComponentAlignment(codescannerComponent, Alignment.MIDDLE_CENTER);
		
		HorizontalLayout hlaySettings = new HorizontalLayout();
		//hlaySettings.setSpacing(true);
		//hlaySettings.setWidthFull();
		content.addComponent(hlaySettings);
		content.setComponentAlignment(hlaySettings, Alignment.MIDDLE_CENTER);
		
		startStopScanButton = new Button("-");
		startStopScanButton.setWidthFull();
		hlaySettings.addComponent(startStopScanButton);
		startStopScanButton.addClickListener(evt->{
			
			codescannerComponent.isScannerStarted(isScannStarted->{
				
				if(isScannStarted) {
					startStopScanButton.setCaption(startScanning);
					codescannerComponent.stopScanning(null);
				} else {
					
					codescannerComponent.getSelectedMediaDevice(mdi->{
						if(mdi==null) {
							codescannerComponent.getVideoInputDevices(videoDevices->{
								
								if(videoDevices.size()<1) {
									Notification.show("Warning!", "No cameras found", Type.WARNING_MESSAGE);
								} else if(videoDevices.size()==1) {
									codescannerComponent.setMediaDevice(videoDevices.iterator().next(), resp->{
										startStopScanButton.setCaption(stoptScanning);
										codescannerComponent.startScanning(null);
									});
								} else
									getUI().addWindow(new SelectionCameraWindow(videoDevices, startStopScanButton));
							});
						} else {
							startStopScanButton.setCaption(stoptScanning);
							codescannerComponent.startScanning(null);
						}
					});
					
				}
			});
			
			
		});
		
		addAttachListener(attevt->{
			codescannerComponent.isScannerStarted(bol->{
				if(bol)
					startStopScanButton.setCaption("Stop scanning");
				else
					startStopScanButton.setCaption(startScanning);
			});
		});
		
		
		Button settings = new Button(VaadinIcons.COGS);
		hlaySettings.addComponent(settings);
		settings.addClickListener(evt->{
			getUI().addWindow(new SetupWindow());
		});
	}
	
	
	
	
	public CodescannerComponent getCodescannerComponent() {
		return codescannerComponent;
	}
		
	
	public Registration addMediaDevicesReceivedListner(MediaDevicesReceivedListner listener) {
		return codescannerComponent.addMediaDevicesReceivedListner(listener);
	}

	public Registration addDecodedCodeListner(DecodedCodeListner listener) {
		return codescannerComponent.addDecodedCodeListner(listener);
	}

	public Registration addErrorDecodedCodeListner(ErrorDecodedCodeListner listener) {
		return codescannerComponent.addErrorDecodedCodeListner(listener);
	}
	
	
	
	
	class SetupWindow extends Window {
		
		private static final long serialVersionUID = 1L;
		
		public SetupWindow() {
			setCaption("Settings");
			center();
			setResizable(false);
			setDraggable(false);
			setModal(true);
			
			setWidth(275, Unit.PIXELS);
			
			VerticalLayout content = new VerticalLayout();
			this.setContent(content);
			
			Button selectCamera = new Button("Seleziona fotocamera");
			content.addComponent(selectCamera);
			selectCamera.addClickListener(this::onSelectCameraButtonClk);
			
			codescannerComponent.stopScanning(null);
		}
		
		private void onSelectCameraButtonClk(ClickEvent event) {
			codescannerComponent.getVideoInputDevices(videoDevices->{
				SelectionCameraWindow scw = new SelectionCameraWindow(videoDevices, startStopScanButton);
				scw.addCloseListener(clEvt->this.close());
				getUI().addWindow(scw);
			});
		}
		
	}

	class SelectionCameraWindow extends Window {

		private static final long serialVersionUID = 1L;
		
		public SelectionCameraWindow() {
			
		}
		
		public SelectionCameraWindow(List<MediaDeviceInfo> mdinfs, Button startStopButton) {
			setCaption("Seleziona fotocamera");
			center();
			setResizable(false);
			setDraggable(false);
			setModal(true);
			
			setWidth(250, Unit.PIXELS);
			
			FormLayout flay = new FormLayout();
			setContent(flay);
			
			RadioButtonGroup<MediaDeviceInfo> rbg = new RadioButtonGroup<>();
			flay.addComponent(rbg);
		
			//List<MediaDeviceInfo> copy = mdinfs.stream().collect(Collectors.toList());
			rbg.setItems(mdinfs);
			
			rbg.setItemCaptionGenerator(MediaDeviceInfo::getLabel);
			
			rbg.addSelectionListener(selected->{
				if(selected.getValue()!=null) {
					codescannerComponent.setMediaDevice(selected.getValue(), resp->{
						close();
						startStopButton.setCaption(stoptScanning);
						codescannerComponent.startScanning(null);
					});
				}
			});
		}
		
	}
	
}
