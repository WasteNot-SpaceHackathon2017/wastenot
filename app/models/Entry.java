package models;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import play.db.jpa.Model;

@Entity
public class Entry extends Model {
	
	@Column(name = "uuid")
	private String uuid;
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public List<DataValue> getValues() {
		return values;
	}

	public void setValues(List<DataValue> values) {
		this.values = values;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "name")
	private String name;

	@OneToMany(mappedBy = "entry")
	private List<DataValue> values; 
	
	public Entry(String identifier) {
		this.name = identifier;
		this.uuid = UUID.randomUUID().toString();
	}

	public String getName() {
		return this.name;
	}
		
	public JsonObject toJsonObject(){
		JsonObject output = new JsonObject();
		
		output.addProperty("name", this.getName());

		JsonArray dataValues = new JsonArray();
		
		for(DataValue value: values){
			dataValues.add(value.toJsonObject());
		}
		
		output.add("data", dataValues);
		return output;
	}
	
}
