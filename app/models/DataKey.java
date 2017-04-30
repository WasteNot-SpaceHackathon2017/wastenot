package models;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.google.gson.JsonObject;

import play.db.jpa.Model;

@Entity
public class DataKey extends Model{
	@Column(name = "uuid")
	private String uuid;
	
	@Column(name="description")
	private String description;
	
	@Column(name="keyname")
	private String key;
	
	public DataKey(String key, String description) {
		this.description = description;
		this.key = key;
		
		this.uuid = UUID.randomUUID().toString();
		
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	public JsonObject toJsonObject(){
		JsonObject output = new JsonObject();
		
		output.addProperty("key", this.getKey());
		output.addProperty("description", this.getDescription());
		
		return output;
	}
}
