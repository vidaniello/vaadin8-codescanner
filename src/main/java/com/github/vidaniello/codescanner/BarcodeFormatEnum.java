package com.github.vidaniello.codescanner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import elemental.json.JsonArray;

/**
 * Parser for node library
 * @author Vincenzo D'Aniello (vidaniello@gmail.com) github.com/vidaniello
 * @zxing-library\esm\core\BarcodeFormat
 *
 */
public enum BarcodeFormatEnum {
	
    /** Aztec 2D barcode format. */
    AZTEC(0),
    /** CODABAR 1D format. */
    CODABAR(1),
    /** Code 39 1D format. */
    CODE_39(2),
    /** Code 93 1D format. */
    CODE_93(3),
    /** Code 128 1D format. */
    CODE_128(4),
    /** Data Matrix 2D barcode format. */
    DATA_MATRIX(5),
    /** EAN-8 1D format. */
    EAN_8(6),
    /** EAN-13 1D format. */
    EAN_13(7),
    /** ITF (Interleaved Two of Five) 1D format. */
    ITF(8),
    /** MaxiCode 2D barcode format. */
    MAXICODE(9),
    /** PDF417 format. */
    PDF_417(10),
    /** QR Code 2D barcode format. */
    QR_CODE(11),
    /** RSS 14 */
    RSS_14(12),
    /** RSS EXPANDED */
    RSS_EXPANDED(13),
    /** UPC-A 1D format. */
    UPC_A(14),
    /** UPC-E 1D format. */
    UPC_E(15),
    /** UPC/EAN extension format. Not a stand-alone format. */
    UPC_EAN_EXTENSION(16);
	
	private int id;
	
	private BarcodeFormatEnum(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
	
	public static BarcodeFormatEnum getFromId(Integer id) {
		for(BarcodeFormatEnum bce : BarcodeFormatEnum.values()) 
			if(id==bce.id) 
				return bce;
		return null;
	}
	
	public static Set<BarcodeFormatEnum> getFromIds(Collection<Integer> ids) {
		Set<BarcodeFormatEnum> ret = new HashSet<>();
		
		for(int id : ids)		
			ret.add(getFromId(id));
		
		return ret;
	}
	
	public static Set<BarcodeFormatEnum> getFromIds(JsonArray response) {
		JsonArray array = response.getArray(0);
		Set<Integer> ids = new HashSet<>();
		for(int i=0;i<array.length();i++)
			ids.add(new Double(array.getNumber(i)).intValue());
		return getFromIds(ids);
	}
	
	public static Set<BarcodeFormatEnum> getFromIds_double(Collection<Double> ids){
		return getFromIds(
				ids.stream().mapToInt(Double::intValue).boxed().collect(Collectors.toList())
				);
	}
	
	public static int[] getFromBarcodeFormatEnums(Set<BarcodeFormatEnum> barcodesEnum) {
		int[] ret = new int[barcodesEnum.size()];
		int i = 0;
		for(BarcodeFormatEnum bce : barcodesEnum) {
			ret[i] = bce.getId();
			i++;
		}
		return ret;
	}

	public static Collection<Double> getFromBarcodeFormatEnums_double(Set<BarcodeFormatEnum> barcodesEnum){
		Collection<Double> ret = new HashSet<>();
		barcodesEnum.forEach(bfe->ret.add( new Integer(bfe.getId()).doubleValue()) );
		return ret;
	}
}
