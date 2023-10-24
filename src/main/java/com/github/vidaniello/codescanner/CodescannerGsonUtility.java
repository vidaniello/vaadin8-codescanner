package com.github.vidaniello.codescanner;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.github.vidaniello.codescanner.Result.ResultPoint;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.googlecode.gentyref.TypeToken;



public class CodescannerGsonUtility {

	
	private static CodescannerGsonUtility gsonUtility = new CodescannerGsonUtility();
	public static CodescannerGsonUtility get() {return gsonUtility;};
	
	public CodescannerGsonUtility() {
		
	}
	
	private GsonBuilder defaultBuilder;
	public synchronized GsonBuilder getDefaultBuilder() {
		if(defaultBuilder==null) {
			defaultBuilder = new GsonBuilder().registerTypeAdapter(Result.class, new CustomResultTypeAdapter());
		}
		return defaultBuilder;
	}
	
	
	class CustomResultTypeAdapter implements JsonDeserializer<Result>, JsonSerializer<Result>{

		@Override
		public Result deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			
			JsonObject jo = json.getAsJsonObject();
			
			Result ret = new Result();
			
			ret.setText(context.deserialize(jo.get("text"), String.class));
			
			Type rawBytes_type = new TypeToken<Map<String,Integer>>() {}.getType();
			ret.setRawBytes(context.deserialize(jo.get("rawBytes"), rawBytes_type));
			
			ret.setNumBits(context.deserialize(jo.get("numBits"), Integer.class));
						
			Type resultPoints_type = new TypeToken<List<ResultPoint>>() {}.getType();
			ret.setResultPoints(context.deserialize(jo.get("resultPoints"), resultPoints_type));
			
			ret.setFormat(BarcodeFormatEnum.getFromId( context.deserialize(jo.get("format"), Integer.class) ));
			ret.setTimestamp(new Date( (Long) context.deserialize(jo.get("timestamp"), Long.class) ));
			
			Type resultMetadata_type = new TypeToken<Map<Integer,Serializable>>() {}.getType();
			ret.setResultMetadata( context.deserialize(jo.get("resultMetadata"), resultMetadata_type) );
			
			return ret;
		}

		@Override
		public JsonElement serialize(Result src, Type typeOfSrc, JsonSerializationContext context) {
			return new JsonPrimitive(new Gson().toJson(src));
		}}
	
}
