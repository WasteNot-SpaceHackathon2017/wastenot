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
