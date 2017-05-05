import json
import urllib2

def sendRequest(d,identifier):
	url = 'http://localhost:9200/dev/food/' + identifier
	req = urllib2.Request(url)
	response = urllib2.urlopen(req, json.dumps(d))

f = open("food.csv",'w')
f.write("Year,Month,Day,Lunch/Dinner,Season,Number of People,temp,Wind Speed,Humidity,Precipitation,Food\n")
arr = json.loads(urllib2.urlopen("http://localhost:9000/entries").read())

for d in arr:
	sendRequest(d,d['name'])



