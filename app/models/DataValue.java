package models;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.google.gson.JsonObject;

import play.db.jpa.Model;

@Entity
public class DataValue extends Model {
	
	public DataValue(Entry country, DataKey key, String value) {
		this.value = value;
		this.entry = country;
		this.key = key;
		this.uuid = UUID.randomUUID().toString();
	}

	@Column(name = "uuid")
	private String uuid;
	
	@Column(name="value")
	private String value;
	
	@ManyToOne
	@JoinColumn(name="entry_id")
	private Entry entry;
	
	@ManyToOne
	@JoinColumn(name="key_id")
	private DataKey key;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Entry getCountry() {
		return entry;
	}

	public void setCountry(Entry country) {
		this.entry = country;
	}

	public DataKey getKey() {
		return key;
	}

	public void setKey(DataKey key) {
		this.key = key;
	}
	
	public JsonObject toJsonObject(){
		JsonObject output = new JsonObject();
		
		output.addProperty("key", this.getKey().getKey());
		output.addProperty("value", this.getValue());
		
		return output;
	}
	
}
