package com.github.vidaniello.codescanner;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
//import com.googlecode.gentyref.TypeToken;
import com.vaadin.annotations.JavaScript;
import com.vaadin.shared.Registration;
import com.vaadin.ui.AbstractJavaScriptComponent;
import elemental.json.JsonArray;

@JavaScript({/*"main.develop.js"*/"vidaniello@1d-2d-barcode-scanner-1.0.3.js","codescanner-1.0.0.js","codescanner-connector-1.0.0.js"})
public class CodescannerComponent extends AbstractJavaScriptComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private transient Gson customDeserializer;
	private Gson getCustomDeserializer() {
		if(customDeserializer==null)
			customDeserializer =CodescannerGsonUtility.get().getDefaultBuilder().create();
		return customDeserializer;
	}
	
	private boolean pauseOnDecoded;
	
	private List<MediaDeviceInfo> mediaDevicesInfo;
        
    @FunctionalInterface
    public interface MediaDevicesReceivedListner extends Serializable {
        void onMediaDevicesReceived(List<MediaDeviceInfo> mediaDeviceInfos);
    }
    
    @FunctionalInterface
    public interface DecodedCodeListner extends Serializable {
        void onDecodedCode(Result arguments);
    }
    
    @FunctionalInterface
    public interface ErrorDecodedCodeListner extends Serializable {
        void onErrorDecodedCode(JsonArray arguments);
    }
    
    
	public CodescannerComponent() {
    	
        addFunction("onMediaDevicesReceived", 		this::_onMediaDevicesReceived);
        addFunction("onDecodedCode", 				this::_onDecodedCode);
        addFunction("onErrorDecodedCode", 			this::_onErrorDecodedCode);
        
        addFunction("getAvailableCodeFormats", 		this::_getAvailableCodeFormats);
        addFunction("getSelectedFormats", 			this::_getSelectedFormats);
        addFunction("setCodeReaderFormatReader", 	this::_setCodeReaderFormatReader);
        addFunction("setQrCodeHint", 				this::_setQrCodeHint);
        addFunction("setEANCodeHint", 				this::_setEANCodeHint);
        addFunction("setAllCodeHint", 				this::_setAllCodeHint);
        addFunction("getTimeWhaitToSendDecoded", 	this::_getTimeWhaitToSendDecoded);
        addFunction("setTimeWhaitToSendDecoded", 	this::_setTimeWhaitToSendDecoded);
        addFunction("getVideoInputDevices", 		this::_getVideoInputDevices);
        addFunction("getSelectedMediaDevice", 		this::_getSelectedMediaDevice);
        addFunction("setMediaDevice", 				this::_setMediaDevice);
        addFunction("startScanning", 				this::_startScanning);
        addFunction("stopScanning", 				this::_stopScanning);
        addFunction("isScannerStarted", 			this::_isScannerStarted);
        addFunction("isPauseVideoAndDecode", 		this::_isPauseVideoAndDecode);
        addFunction("isScannerScanning", 			this::_isScannerScanning);
        addFunction("setPauseVideoAndDecode", 		this::_setPauseVideoAndDecode);
        addFunction("switchPausePlayVideoAndDecode",this::_switchPausePlayVideoAndDecode);
        addFunction("isPauseOnDecoded", 			this::_isPauseOnDecoded);
        addFunction("setPauseOnDecoded", 			this::_setPauseOnDecoded);
        addFunction("requestSnapshot", 				this::_requestSnapshot);
        
	}
	    
	
    @Override
    protected CodescannerComponentState getState() {
    	return (CodescannerComponentState) super.getState();
    }
    
    
    
    
    
    private List<MediaDevicesReceivedListner> mediaDevicesReceivedListners = new ArrayList<>();
    public Registration addMediaDevicesReceivedListner(MediaDevicesReceivedListner listener) {
    	mediaDevicesReceivedListners.add(listener);
    	return ()->mediaDevicesReceivedListners.remove(listener);
    }
    
	private void _onMediaDevicesReceived(JsonArray jsonArray) {
    	Type listOfMediaDevType = new TypeToken<List<MediaDeviceInfo>>() {}.getType();
    	mediaDevicesInfo = new Gson().fromJson(jsonArray.asString(), listOfMediaDevType);
      	mediaDevicesReceivedListners.forEach(listner->listner.onMediaDevicesReceived(mediaDevicesInfo));  
    }
    
    
    private List<DecodedCodeListner> decodedCodeListners = new ArrayList<>();
    public Registration addDecodedCodeListner(DecodedCodeListner listener) {
    	decodedCodeListners.add(listener);
    	return ()->decodedCodeListners.remove(listener);
    }
   
    private void _onDecodedCode(JsonArray jsonArray) {
    	Result result = getCustomDeserializer().fromJson(jsonArray.asString(), Result.class);
    	decodedCodeListners.forEach(listner->listner.onDecodedCode(result));
    	if(pauseOnDecoded)
    		setPauseVideoAndDecode(false, null);
    }
    
    
    private List<ErrorDecodedCodeListner> errorDecodedCodeListners = new ArrayList<>();
    public Registration addErrorDecodedCodeListner(ErrorDecodedCodeListner listener) {
    	errorDecodedCodeListners.add(listener);
    	return ()->errorDecodedCodeListners.remove(listener);
    }
    private void _onErrorDecodedCode(JsonArray jsonArray) {
    	errorDecodedCodeListners.forEach(listner->listner.onErrorDecodedCode(jsonArray));
    }
    
       
    
 
	
	private CallJsListner<Set<BarcodeFormatEnum>> getAvailableCodeFormats_callback;
    public void getAvailableCodeFormats(CallJsListner<Set<BarcodeFormatEnum>> callback) {
    	getAvailableCodeFormats_callback = callback;
		callFunction("_getAvailableCodeFormats");
	}
	private void _getAvailableCodeFormats(JsonArray response) {		
		if(getAvailableCodeFormats_callback!=null) getAvailableCodeFormats_callback.response(BarcodeFormatEnum.getFromIds(response));
		getAvailableCodeFormats_callback = null;
	}
	
	
	
	
	private CallJsListner<Boolean> setCodeReaderFormatReader_callback;
    public void setCodeReaderFormatReader(Map<DecodeHintTypeEnum,Set<BarcodeFormatEnum>> barcodeFormats, CallJsListner<Boolean> callback) {
    	setCodeReaderFormatReader_callback = callback;
		callFunction("_setCodeReaderFormatReader", new Gson().toJson(DecodeHintTypeEnum.decodeForJavascript(barcodeFormats)));
	}
	private void _setCodeReaderFormatReader(JsonArray response) {		
		if(setCodeReaderFormatReader_callback!=null) setCodeReaderFormatReader_callback.response(response.getBoolean(0));
		setCodeReaderFormatReader_callback = null;
	}
	
	
	
    //setQrCodeHint
	private CallJsListner<Boolean> setQrCodeHint_callback;
    public void setQrCodeHint(CallJsListner<Boolean> callback) {
    	setQrCodeHint_callback = callback;
		callFunction("_setQrCodeHint");
	}
	private void _setQrCodeHint(JsonArray response) {		
		if(setQrCodeHint_callback!=null) setQrCodeHint_callback.response(response.getBoolean(0));
		setQrCodeHint_callback = null;
	}
	
    //setEANCodeHint
	private CallJsListner<Boolean> setEANCodeHint_callback;
    public void setEANCodeHint(CallJsListner<Boolean> callback) {
    	setEANCodeHint_callback = callback;
		callFunction("_setEANCodeHint");
	}
	private void _setEANCodeHint(JsonArray response) {		
		if(setEANCodeHint_callback!=null) setEANCodeHint_callback.response(response.getBoolean(0));
		setEANCodeHint_callback = null;
	}
	
    //setAllCodeHint
	private CallJsListner<Boolean> setAllCodeHint_callback;
    public void setAllCodeHint(CallJsListner<Boolean> callback) {
    	setAllCodeHint_callback = callback;
		callFunction("_setAllCodeHint");
	}
	private void _setAllCodeHint(JsonArray response) {		
		if(setAllCodeHint_callback!=null) setAllCodeHint_callback.response(response.getBoolean(0));
		setAllCodeHint_callback = null;
	}
	
	
	private CallJsListner<Map<DecodeHintTypeEnum,Set<BarcodeFormatEnum>>> getSelectedFormats_callback;
    public void getSelectedFormats(CallJsListner<Map<DecodeHintTypeEnum,Set<BarcodeFormatEnum>>> callback) {
    	getSelectedFormats_callback = callback;
		callFunction("_getSelectedFormats");
	}
	private void _getSelectedFormats(JsonArray response) {		
		
		Type mapOfStringAndCollection = new TypeToken<Map<String,Collection<Double>>>(){}.getType();
		Map<String,Collection<Double>> selForm = new Gson().fromJson(response.asString(), mapOfStringAndCollection);
		
		if(getSelectedFormats_callback!=null) getSelectedFormats_callback.response( DecodeHintTypeEnum.decodeFromJavascript(selForm)	);
		
		getSelectedFormats_callback = null;
	}
	
	
	
	//_getTimeWhaitToSendDecoded double
	private CallJsListner<Double> getTimeWhaitToSendDecoded_callback;
    public void getTimeWhaitToSendDecoded(CallJsListner<Double> callback) {
    	getTimeWhaitToSendDecoded_callback = callback;
		callFunction("_getTimeWhaitToSendDecoded");
	}
	private void _getTimeWhaitToSendDecoded(JsonArray response) {		
		if(getTimeWhaitToSendDecoded_callback!=null) getTimeWhaitToSendDecoded_callback.response(response.asNumber());
		getTimeWhaitToSendDecoded_callback = null;
	}
	
	
	
	
	//_setTimeWhaitToSendDecoded(double) boolean
	private CallJsListner<Boolean> setTimeWhaitToSendDecoded_callback;
    public void setTimeWhaitToSendDecoded(Double newValue, CallJsListner<Boolean> callback) {
    	setTimeWhaitToSendDecoded_callback = callback;
		callFunction("_setTimeWhaitToSendDecoded", newValue);
	}
	private void _setTimeWhaitToSendDecoded(JsonArray response) {		
		if(setTimeWhaitToSendDecoded_callback!=null) setTimeWhaitToSendDecoded_callback.response(response.getBoolean(0));
		setTimeWhaitToSendDecoded_callback = null;
	}
	
	
	
	//_getVideoInputDevices List<MediaDeviceInfo>
	private CallJsListner<List<MediaDeviceInfo>> getVideoInputDevices_callback;
    public void getVideoInputDevices(CallJsListner<List<MediaDeviceInfo>> callback) {
    	if(mediaDevicesInfo!=null) {
    		if(callback!=null)
    			callback.response(mediaDevicesInfo);
    	} else {
	    	getVideoInputDevices_callback = callback;
			callFunction("_getVideoInputDevices");
    	}
	}
	private void _getVideoInputDevices(JsonArray response) {
		
		Type listOfMediaDevType = new TypeToken<List<MediaDeviceInfo>>() {}.getType();
		List<MediaDeviceInfo> mdi = new Gson().fromJson(response.asString(), listOfMediaDevType);
		
		mediaDevicesInfo = mdi;
		
		if(getVideoInputDevices_callback!=null) getVideoInputDevices_callback.response(mdi);
		getVideoInputDevices_callback = null;
	}
	
	
	
	//_getSelectedMediaDevice MediaDeviceInfo
	private CallJsListner<MediaDeviceInfo> getSelectedMediaDevice_callback;
    public void getSelectedMediaDevice(CallJsListner<MediaDeviceInfo> callback) {
    	getSelectedMediaDevice_callback = callback;
		callFunction("_getSelectedMediaDevice");
	}
	private void _getSelectedMediaDevice(JsonArray response) {	
		MediaDeviceInfo mdi = new Gson().fromJson(response.asString(), MediaDeviceInfo.class);
		if(getSelectedMediaDevice_callback!=null) getSelectedMediaDevice_callback.response(mdi);
		getSelectedMediaDevice_callback = null;
	}
	
	
	
	//_setMediaDevice (MediaDeviceInfo) boolean
	private CallJsListner<Boolean> setMediaDevice_callback;
    public void setMediaDevice(MediaDeviceInfo mediaDeviceInfo, CallJsListner<Boolean> callback) {
    	setMediaDevice_callback = callback;
		callFunction("_setMediaDevice", new Gson().toJson(mediaDeviceInfo));
	}
	private void _setMediaDevice(JsonArray response) {		
		if(setMediaDevice_callback!=null) setMediaDevice_callback.response(response.getBoolean(0));
		setMediaDevice_callback = null;
	}
	
	
	private CallJsListner<Boolean> startScanning_callback;
    public void startScanning(CallJsListner<Boolean> callback) {
    	startScanning_callback = callback;
		callFunction("_startScanning");
	}
	private void _startScanning(JsonArray response) {		
		if(startScanning_callback!=null) startScanning_callback.response(response.getBoolean(0));
		startScanning_callback = null;
	}
    


	private CallJsListner<Boolean> stopScanning_callback;
    public void stopScanning(CallJsListner<Boolean> callback) {
    	stopScanning_callback = callback;
		callFunction("_stopScanning");
	}
	private void _stopScanning(JsonArray response) {		
		if(stopScanning_callback!=null) stopScanning_callback.response(response.getBoolean(0));
		stopScanning_callback = null;
	}
	
	
	
	private CallJsListner<Boolean> isScannerStarted_callback;
    public void isScannerStarted(CallJsListner<Boolean> callback) {
    	isScannerStarted_callback = callback;
		callFunction("_isScannerStarted");
	}
	private void _isScannerStarted(JsonArray response) {		
		if(isScannerStarted_callback!=null) isScannerStarted_callback.response(response.getBoolean(0));
		isScannerStarted_callback = null;
	}
	
	
	private CallJsListner<Boolean> isPauseVideoAndDecode_callback;
    public void isPauseVideoAndDecode(CallJsListner<Boolean> callback) {
    	isPauseVideoAndDecode_callback = callback;
		callFunction("_isPauseVideoAndDecode");
	}
	private void _isPauseVideoAndDecode(JsonArray response) {		
		if(isPauseVideoAndDecode_callback!=null) isPauseVideoAndDecode_callback.response(response.getBoolean(0));
		isPauseVideoAndDecode_callback = null;
	}
	
	
	private CallJsListner<Boolean> isScannerScanning_callback;
    public void isScannerScanning(CallJsListner<Boolean> callback) {
    	isScannerScanning_callback = callback;
		callFunction("_isScannerScanning");
	}
	private void _isScannerScanning(JsonArray response) {		
		if(isScannerScanning_callback!=null) isScannerScanning_callback.response(response.getBoolean(0));
		isScannerScanning_callback = null;
	}
	
	
	private CallJsListner<Boolean> setPauseVideoAndDecode_callback;
    public void setPauseVideoAndDecode(Boolean state, CallJsListner<Boolean> callback) {
    	setPauseVideoAndDecode_callback = callback;
		callFunction("_setPauseVideoAndDecode", state);
	}
	private void _setPauseVideoAndDecode(JsonArray response) {		
		if(setPauseVideoAndDecode_callback!=null) setPauseVideoAndDecode_callback.response(response.getBoolean(0));
		setPauseVideoAndDecode_callback = null;
	}
	
	
	private CallJsListner<Boolean> switchPausePlayVideoAndDecode_callback;
    public void switchPausePlayVideoAndDecode(CallJsListner<Boolean> callback) {
    	switchPausePlayVideoAndDecode_callback = callback;
		callFunction("_switchPausePlayVideoAndDecode");
	}
	private void _switchPausePlayVideoAndDecode(JsonArray response) {		
		if(switchPausePlayVideoAndDecode_callback!=null) switchPausePlayVideoAndDecode_callback.response(response.getBoolean(0));
		switchPausePlayVideoAndDecode_callback = null;
	}
	
	
	private CallJsListner<Boolean> isPauseOnDecoded_callback;
    public void isPauseOnDecoded(CallJsListner<Boolean> callback) {
    	isPauseOnDecoded_callback = callback;
		callFunction("_isPauseOnDecoded");
	}
	private void _isPauseOnDecoded(JsonArray response) {
		pauseOnDecoded = response.getBoolean(0);
		if(isPauseOnDecoded_callback!=null) isPauseOnDecoded_callback.response(response.getBoolean(0));
		isPauseOnDecoded_callback = null;
	}
	
	
	private CallJsListner<Boolean> setPauseOnDecoded_callback;
    public void setPauseOnDecoded(Boolean state, CallJsListner<Boolean> callback) {
    	pauseOnDecoded = state;
    	setPauseOnDecoded_callback = callback;
		callFunction("_setPauseOnDecoded", state);
	}
	private void _setPauseOnDecoded(JsonArray response) {		
		if(setPauseOnDecoded_callback!=null) setPauseOnDecoded_callback.response(response.getBoolean(0));
		setPauseOnDecoded_callback = null;
	}
	
	
	private CallJsListner<String> requestSnapshot_callback;
    public void requestSnapshot(CallJsListner<String> callback) {
    	requestSnapshot_callback = callback;
		callFunction("_requestSnapshot");
	}
	private void _requestSnapshot(JsonArray response) {		
		if(requestSnapshot_callback!=null) requestSnapshot_callback.response(response.asString());
		requestSnapshot_callback = null;
	}
}
