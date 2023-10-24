var codescanner = codescanner || {};

/** @type {import('./vidaniello@1d-2d-barcode-scanner-1.0.3')} */
var com_github_vidaniello_codescanner_js = window.com_github_vidaniello_codescanner_js;

var allCodescannerComponents = new Array();

class CodescannerComponent {
	
	/** @type {import('./vidaniello@1d-2d-barcode-scanner-1.0.3').Scanner} */
	scanner = null;
	
	/**
	 * @param {HTMLElement} element
	 */
	constructor(element){
		allCodescannerComponents.push(this);
		this.scanner = new com_github_vidaniello_codescanner_js.Scanner();
		this.scanner.showScanner(element);
	}
	
	getScanner(){
		return this.scanner;
	}
	
}
