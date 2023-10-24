package com.github.vidaniello.codescanner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Vincenzo D'Aniello (vidaniello@gmail.com) github.com/vidaniello
 * @zxing/library\esm\core\Result.js
 */
public class Result implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	
	private String text;
	private Map<String,Integer> rawBytes;
	private int numBits;
    private List<ResultPoint> resultPoints;
    private BarcodeFormatEnum format;
    private Date timestamp;
    private Map<Integer,Serializable> resultMetadata;
    
    public Result() {
		
	}
    
    
	
	public String getText() {
		return text;
	}



	public void setText(String text) {
		this.text = text;
	}



	public Map<String, Integer> getRawBytes() {
		if(rawBytes==null)
			rawBytes = new HashMap<>();
		return rawBytes;
	}



	public void setRawBytes(Map<String, Integer> rawBytes) {
		this.rawBytes = rawBytes;
	}



	public int getNumBits() {
		return numBits;
	}



	public void setNumBits(int numBits) {
		this.numBits = numBits;
	}



	public List<ResultPoint> getResultPoints() {
		if(resultPoints==null)
			resultPoints = new ArrayList<>();
		return resultPoints;
	}



	public void setResultPoints(List<ResultPoint> resultPoints) {
		this.resultPoints = resultPoints;
	}



	public BarcodeFormatEnum getFormat() {
		return format;
	}



	public void setFormat(BarcodeFormatEnum format) {
		this.format = format;
	}



	public Date getTimestamp() {
		return timestamp;
	}



	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}



	public Map<Integer, Serializable> getResultMetadata() {
		if(resultMetadata==null)
			resultMetadata = new HashMap<>();
		return resultMetadata;
	}



	public void setResultMetadata(Map<Integer, Serializable> resultMetadata) {
		this.resultMetadata = resultMetadata;
	}



	class ResultPoint implements Serializable{
	
		private static final long serialVersionUID = 1L;
		
		private double x;
		private double y;
		private double estimatedModuleSize;
		private int count;
		
		public ResultPoint() {
			
		}

		public double getX() {
			return x;
		}

		public void setX(double x) {
			this.x = x;
		}

		public double getY() {
			return y;
		}

		public void setY(double y) {
			this.y = y;
		}

		public double getEstimatedModuleSize() {
			return estimatedModuleSize;
		}

		public void setEstimatedModuleSize(double estimatedModuleSize) {
			this.estimatedModuleSize = estimatedModuleSize;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}		
		
		
	}

}
