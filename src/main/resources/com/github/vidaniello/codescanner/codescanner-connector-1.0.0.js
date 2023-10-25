/** @type {import('./codescanner-1.0.0')} */

var allCodescannerComponentConnectors = new Array();

window.com_github_vidaniello_codescanner_CodescannerComponent = function() {
    
    allCodescannerComponentConnectors.push(this);
    
    var codescannerComponent = new CodescannerComponent(this.getElement());
        
        
        
    codescannerComponent.getScanner().registerMediaDevicesReceivedCallbackFunction(mdevsInfo=>{
		this.onMediaDevicesReceived(JSON.stringify(mdevsInfo));
	});
	
	codescannerComponent.getScanner().registerDecodedCodeCallbackFunction(result=>{
		this.onDecodedCode(JSON.stringify(result));
	});
	
	codescannerComponent.getScanner().registerErrorDecodeCallbackFunction(error=>{
		this.onErrorDecodedCode(JSON.stringify(error));
	});
	
	

    // Handle changes from the server-side
    this.onStateChange = function() {
       
    };
	
	
	
	this._getAvailableCodeFormats = function() {
		this.getAvailableCodeFormats(window.com_github_vidaniello_codescanner_js.ScannerUtil.getAvailableCodeFormats());
	};
	

	this._setCodeReaderFormatReader = function(barcodeFormatArray) {
		
		let newSelectedFormats_Map = JSON.parse(barcodeFormatArray);
		
		let newSelectedFormats_Object = new Map(Object.entries(newSelectedFormats_Map));
		
		codescannerComponent.getScanner().setCodeReaderFormatReader(newSelectedFormats_Object);

		this.setCodeReaderFormatReader(true);
	};
    
    
    this._getSelectedFormats = function() {
		let selected = codescannerComponent.getScanner().getSelectedFormats();
		this.getSelectedFormats(JSON.stringify(Object.fromEntries(selected)));
	};
    
    
    this._getTimeWhaitToSendDecoded = function() {
		this.getTimeWhaitToSendDecoded(codescannerComponent.getScanner().getTimeWhaitToSendDecoded());
	};
	
	this._setTimeWhaitToSendDecoded = function(newValue) {
		codescannerComponent.getScanner().setTimeWhaitToSendDecoded(newValue);
		this.setTimeWhaitToSendDecoded(true);
	};
    
    
  	this._getVideoInputDevices = function() {
		this.getVideoInputDevices(JSON.stringify(codescannerComponent.getScanner().getVideoInputDevices()));
	};
    
    
    this._getSelectedMediaDevice = function() {
		this.getSelectedMediaDevice(JSON.stringify(codescannerComponent.getScanner().getSelectedMediaDevice()));
	};
	
	this._setMediaDevice = function(mediaDeviceSelected) {
		let medD = JSON.parse(mediaDeviceSelected);
		codescannerComponent.getScanner().setMediaDevice(medD);
		this.setMediaDevice(true);
	};
	
	//setAllCodeHint
	this._setAllCodeHint = function() {
		codescannerComponent.getScanner().setAllCodeHint();
		this.setAllCodeHint(true);
	};   
	
	//setEANCodeHint
	this._setEANCodeHint = function() {
		codescannerComponent.getScanner().setEANCodeHint();
		this.setEANCodeHint(true);
	};   
	
    //setQrCodeHint
   	this._setQrCodeHint = function() {
		codescannerComponent.getScanner().setQrCodeHint();
		this.setQrCodeHint(true);
	};   
    
    /*
    this._ = function(newValue) {
		codescannerComponent.getScanner().
		this.();
	};    
    */
        
    this._startScanning = function() {
		codescannerComponent.getScanner().startScanning();
		this.startScanning(true);
	};
    
    this._stopScanning = function() {
		codescannerComponent.getScanner().stopScanning();
		this.stopScanning(true);
	};
    
    this._isScannerStarted = function() {
		this.isScannerStarted(codescannerComponent.getScanner().isScannerStarted());
	};
    
    this._isPauseVideoAndDecode = function() {
		this.isPauseVideoAndDecode(codescannerComponent.getScanner().isPauseVideoAndDecode());
	};
    
   	this._isScannerScanning = function() {
		this.isScannerScanning(codescannerComponent.getScanner().isScannerStarted());
	};
    
    this._setPauseVideoAndDecode = function(valueBoolean) {
		codescannerComponent.getScanner().setPauseVideoAndDecode(valueBoolean);
		this.setPauseVideoAndDecode(true);
	};
    
	this._switchPausePlayVideoAndDecode = function() {
		codescannerComponent.getScanner().switchPausePlayVideoAndDecode();
		this.switchPausePlayVideoAndDecode(true);
	};
	
	this._isPauseOnDecoded = function() {
		this.isPauseOnDecoded(codescannerComponent.getScanner().isPauseOnDecoded());
	};
	
	this._setPauseOnDecoded = function(valueBoolean) {
		codescannerComponent.getScanner().setPauseOnDecoded(valueBoolean);
		this.setPauseOnDecoded(true);
	};

	this._requestSnapshot = function(){
		codescannerComponent.getScanner().getImageFromVideo(blob=>{
			this.requestSnapshot(JSON.stringify(Object.fromEntries(blob)));
		});
	}
    
    
    
    
    
};

