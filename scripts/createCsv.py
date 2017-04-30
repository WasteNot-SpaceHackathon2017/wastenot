import json
import urllib2

f = open("food.csv",'w')
f.write("Year,Month,Day,Lunch/Dinner,Season,Number of People,temp,Wind Speed,Humidity,Precipitation,Food\n")
arr = json.loads(urllib2.urlopen("http://localhost:9000/entries").read())

for entryDictionary in arr:
	valuesArray = entryDictionary["data"]
	
	d = {}
	for valueDictionary in valuesArray:
		d[valueDictionary["key"]] =  valueDictionary["value"]
	if(len(d) != 11):
		print d
		continue
	month = d["month"]
	day = d["day"]
	year = d["year"]
	isDinner = d["is_dinner"]
	numberOfPeople = d["number_of_people"]
	temperature = d["temp"]
	windSpeed = d["wind_speed"]
	humidity = d["humidity"]
	precipitation = d["precipitation"]
	food = d["food"]
	season = d["season"]
	

	outputLine = "{0},{1},{2},{3},{4},{5},{6},{7},{8},{9},{10}".format(int(year),int(month),int(day),isDinner, season,numberOfPeople,temperature,windSpeed,humidity,precipitation,food)
	f.write("{}\n".format(outputLine))

