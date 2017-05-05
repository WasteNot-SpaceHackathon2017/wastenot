/*
Copyright (c) 2017 WasteNot

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */

package controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import models.Entry;
import models.DataKey;
import models.DataValue;
import play.mvc.Controller;

public class Application extends Controller {

	public static void index() {
		JsonArray entries= new JsonArray();
    	
    	List<Entry> entryObjects = Entry.findAll();
    	
    	Map<String,Map<String,Integer>> mapOfDatesToFood = new HashMap<String, Map<String, Integer>>();
    	
    	for(Entry entry:entryObjects){
    		String month = "";
    		String day = "";
    		String year = "";
    		String food = "";
    		
    		for(DataValue value: entry.getValues()){
    			String key = value.getKey().getKey();
    			if (key.equals("month")){ 
    				month = value.getValue();
    			} else if( key.equals("day")){
    				day = value.getValue();
    			}
    			else if(key.equals("year")){
    				year = value.getValue();
    			}
    			else if(key.equals("food")){
    				food = value.getValue().replaceAll("\\s+","");
    			}	
    		}
    		
    		String date = String.format("%s-%s-%s", year,month,day);
			Map<String, Integer> foodMap = null;
			if(mapOfDatesToFood.containsKey(date)){
				foodMap = mapOfDatesToFood.get(date);
			}
			else{
				foodMap = new HashMap<String, Integer>();
			}
			
			if(foodMap.containsKey(food)){
				int count = foodMap.get(food);
				foodMap.put(food, count + 1);
			}
			
			else{
				foodMap.put(food,1);
			}
			
			mapOfDatesToFood.put(date, foodMap);
    		
    	}
    	
    	JsonArray foodCounts = new JsonArray();
		for(String date: mapOfDatesToFood.keySet()){
			Map<String, Integer> foodMap = mapOfDatesToFood.get(date);
			JsonObject jo = new JsonObject();
			jo.addProperty("date", date);
			for(String foodString: foodMap.keySet()){
				jo.addProperty(foodString, foodMap.get(foodString));
			}
			
			foodCounts.add(jo);
		}
		
    	renderArgs.put("foodCounts",foodCounts);
		render();
	}
    
	public static void readEntry(String identifier){
		Entry country = Entry.find("uuid = ?1 OR name = ?2", identifier, identifier).first();
		
		if (country == null){
			JsonObject output = new JsonObject();
			output.addProperty("error", "entry not found");
			renderJSON(output.toString());
		}
		
		renderJSON(country.toJsonObject().toString());
	}
	
	public static void createOrUpdateEntry(){
		JsonParser parser = new JsonParser();
		String requestBodyString = params.get("body");
		
		JsonObject requestBody = (requestBodyString != null) ? parser.parse(requestBodyString).getAsJsonObject() : null;
		
		if(requestBody == null){
			JsonObject output = new JsonObject();
			output.addProperty("error", "Invalid Request Body");
			renderJSON(output.toString());
		}
		
		String identifier = (requestBody.has("identifier") && requestBody.get("identifier").isJsonPrimitive()) ?
				requestBody.get("identifier").getAsString() : "";
				
		if(identifier.isEmpty()){
			JsonObject output = new JsonObject();
			output.addProperty("error", "Missing or invalid entry identifier");
			renderJSON(output.toString());
		}
		
		Entry entry = Entry.find("uuid = ?1 OR name = ?2", identifier, identifier).first();
		
		if (entry == null){
			entry = new Entry(identifier);
			entry.save();
		}
		
		if (!createData(entry,requestBody)){
			JsonObject output = new JsonObject();
			output.addProperty("error", "Data Could Not Be Created");
			renderJSON(output.toString());
		}
		
		renderJSON(entry.toJsonObject().toString());
	}
	
	public static void addDataToEntry(String identifier){
		JsonParser parser = new JsonParser();
		String requestBodyString = params.get("body");
		
		JsonObject requestBody = (requestBodyString != null) ? parser.parse(requestBodyString).getAsJsonObject() : null;
		
		if(requestBody == null){
			JsonObject output = new JsonObject();
			output.addProperty("error", "Invalid Request Body");
			renderJSON(output.toString());
		}
		
		Entry entry = Entry.find("uuid = ?1 OR name = ?2", identifier, identifier).first();
		
		if (entry == null){
			JsonObject output = new JsonObject();
			output.addProperty("error", "entry not found");
			renderJSON(output.toString());
		}
		
		if (!createData(entry,requestBody)){
			JsonObject output = new JsonObject();
			output.addProperty("error", "Data Could Not Be Created");
			renderJSON(output.toString());
		}
		
		renderJSON(entry.toJsonObject().toString());
	}
	
	private static boolean createData(Entry entry, JsonObject requestBody) {
		if(entry == null || requestBody == null){
			return false;
		}
		
		JsonArray values = (requestBody.has("values") && requestBody.get("values").isJsonArray()) 
				? requestBody.get("values").getAsJsonArray() : new JsonArray();
		
		for(JsonElement valueJsonElement: values){
			if(!valueJsonElement.isJsonObject()){
				continue;
			}
			
			JsonObject valueJsonObject = valueJsonElement.getAsJsonObject();
			
			String valueString = (valueJsonObject.has("value") && valueJsonObject.get("value").isJsonPrimitive()) ? valueJsonObject.get("value").getAsString() : "";
			String keyString = (valueJsonObject.has("key") && valueJsonObject.get("key").isJsonPrimitive()) ? valueJsonObject.get("key").getAsString() : "";
			String keyDescription = (valueJsonObject.has("key_description") && valueJsonObject.get("key_description").isJsonPrimitive()) ? valueJsonObject.get("key_description").getAsString() : "";
			
			if(valueString.isEmpty() || keyString.isEmpty()){
				return false;
			}
			
			DataKey key = DataKey.find("key = ?1", keyString).first();
			if (key == null){
				key = new DataKey(keyString, keyDescription);
				key.save();
			}
			
			DataValue dataValue = DataValue.find("entry = ?1 AND key = ?2", entry,key).first();
			if(dataValue == null){
				dataValue = new DataValue(entry, key, valueString);
			}
			
			dataValue.setValue(valueString);		
			dataValue.save();
			
		}
		
		entry.refresh();
		return true;
	}

	public static void getEntries() {
    	JsonArray entries= new JsonArray();
    	
    	List<Entry> entryObjects = Entry.find("ORDER BY name").fetch();
    	
    	
    	for(Entry entry:entryObjects){
    		entries.add(entry.toJsonObject());
    	}
    	
        renderJSON(entries.toString());
    }
	

}