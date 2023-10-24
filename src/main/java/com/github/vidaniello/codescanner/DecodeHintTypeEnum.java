package com.github.vidaniello.codescanner;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import elemental.json.JsonArray;

/**
 * Parser for node library
 * @author Vincenzo D'Aniello (vidaniello@gmail.com) github.com/vidaniello
 * @zxing-library\esm\core\DecodeHintType
 *
 */
public enum DecodeHintTypeEnum {
	
    /**
     * Unspecified, application-specific hint. Maps to an unspecified {@link Object}.
     */
    OTHER(0),
    /**
     * Image is a pure monochrome image of a barcode. Doesn't matter what it maps to;
     * use {@link Boolean#TRUE}.
     */
    PURE_BARCODE(1),
    /**
     * Image is known to be of one of a few possible formats.
     * Maps to a {@link List} of {@link BarcodeFormat}s.
     */
    POSSIBLE_FORMATS(2),
    /**
     * Spend more time to try to find a barcode; optimize for accuracy, not speed.
     * Doesn't matter what it maps to; use {@link Boolean#TRUE}.
     */
    TRY_HARDER(3),
    /**
     * Specifies what character encoding to use when decoding, where applicable (type String)
     */
    CHARACTER_SET(4),
    /**
     * Allowed lengths of encoded data -- reject anything else. Maps to an {@code Int32Array}.
     */
    ALLOWED_LENGTHS(5),
    /**
     * Assume Code 39 codes employ a check digit. Doesn't matter what it maps to;
     * use {@link Boolean#TRUE}.
     */
    ASSUME_CODE_39_CHECK_DIGIT(6),
    /**
     * Assume the barcode is being processed as a GS1 barcode, and modify behavior as needed.
     * For example this affects FNC1 handling for Code 128 (aka GS1-128). Doesn't matter what it maps to;
     * use {@link Boolean#TRUE}.
     */
    ASSUME_GS1(7),
    /**
     * If true, return the start and end digits in a Codabar barcode instead of stripping them. They
     * are alpha, whereas the rest are numeric. By default, they are stripped, but this causes them
     * to not be. Doesn't matter what it maps to; use {@link Boolean#TRUE}.
     */
    RETURN_CODABAR_START_END(8),
    /**
     * The caller needs to be notified via callback when a possible {@link ResultPoint}
     * is found. Maps to a {@link ResultPointCallback}.
     */
    NEED_RESULT_POINT_CALLBACK(9),
    /**
     * Allowed extension lengths for EAN or UPC barcodes. Other formats will ignore this.
     * Maps to an {@code Int32Array} of the allowed extension lengths, for example [2], [5], or [2, 5].
     * If it is optional to have an extension, do not set this hint. If this is set,
     * and a UPC or EAN barcode is found but an extension is not, then no result will be returned
     * at all.
     */
    ALLOWED_EAN_EXTENSIONS(10)
    ;
	
	private int id;
	
	private DecodeHintTypeEnum(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
	
	public static Set<DecodeHintTypeEnum> getFromIds(Collection<Integer> ids) {
		Set<DecodeHintTypeEnum> ret = new HashSet<>();
		
		nextId:
		for(int id : ids) {
			for(DecodeHintTypeEnum bce : DecodeHintTypeEnum.values()) {
				if(id==bce.id) {
					ret.add(bce);
					continue nextId;
				}
			}
		}
		return ret;
	}
	public static Set<DecodeHintTypeEnum> getFromIds(JsonArray response) {
		JsonArray array = response.getArray(0);
		Set<Integer> ids = new HashSet<>();
		for(int i=0;i<array.length();i++)
			ids.add(new Double(array.getNumber(i)).intValue());
		return getFromIds(ids);
	}

	
	public static int[] getFromBarcodeFormatEnums(Set<DecodeHintTypeEnum> barcodesEnum) {
		int[] ret = new int[barcodesEnum.size()];
		int i = 0;
		for(DecodeHintTypeEnum bce : barcodesEnum) {
			ret[i] = bce.getId();
			i++;
		}
		return ret;
	}
	
	public static DecodeHintTypeEnum getFromStr(String str) {
		int parsed = Integer.parseInt(str);
		
		for(DecodeHintTypeEnum dht : DecodeHintTypeEnum.values())
			if(dht.getId()==parsed)
				return dht;
		
		return null;
	}
	
	public static Map<DecodeHintTypeEnum,Set<BarcodeFormatEnum>> decodeFromJavascript (Map<String,Collection<Double>> fromJavascript){
		Map<DecodeHintTypeEnum,Set<BarcodeFormatEnum>> ret = new HashMap<>();
		
		for(String str : fromJavascript.keySet()) {
			Collection<Double> brfs = fromJavascript.get(str);
			
			DecodeHintTypeEnum dht = getFromStr(str);
			Set<BarcodeFormatEnum> bcfen = BarcodeFormatEnum.getFromIds_double(brfs);
			
			ret.put(dht, bcfen);
		}
		
		return ret;
	};
	
	public static Map<String,Collection<Double>> decodeForJavascript (Map<DecodeHintTypeEnum,Set<BarcodeFormatEnum>> fromJava) {
		Map<String,Collection<Double>> ret = new HashMap<>();
		
		for(DecodeHintTypeEnum dht : fromJava.keySet()) {
			Set<BarcodeFormatEnum> brfs = fromJava.get(dht);
			
			String dht_str = new Integer(dht.getId()).toString();
			Collection<Double> bcfen = BarcodeFormatEnum.getFromBarcodeFormatEnums_double(brfs);
			
			ret.put(dht_str, bcfen);
		}
		
		return ret;
	};

}
