# Copyright (c) 2017 WasteNot
# 
# Permission is hereby granted, free of charge, to any person obtaining a copy
# of this software and associated documentation files (the "Software"), to deal
# in the Software without restriction, including without limitation the rights
# to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
# copies of the Software, and to permit persons to whom the Software is
# furnished to do so, subject to the following conditions:
# 
# The above copyright notice and this permission notice shall be included in all
# copies or substantial portions of the Software.
# 
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
# IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
# FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
# AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
# LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
# OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
# SOFTWARE.

import json
import urllib2
from random import randint
from datetime import timedelta, date

def sendRequest(jsonString):
	d = json.loads(jsonString)
	url = 'http://localhost:9000/entries'
	req = urllib2.Request(url)
	response = urllib2.urlopen(req, json.dumps(d))

tempDict = {}
tempDict["01"]=[39,.99]
tempDict["02"]=[42,.75]
tempDict["03"]=[50,1.03]
tempDict["04"]=[60,1]
tempDict["05"]=[71,1.13]
tempDict["06"]=[79,.89]
tempDict["07"]=[85,1.15]
tempDict["08"]=[83,1.05]
tempDict["09"]=[76,1.01]
tempDict["10"]=[65,.86]
tempDict["11"]=[54,.97]
tempDict["12"]=[44,.91]




foodArray = ["Salad","Cheese Burger","Pizza"]

foodArray1 = ["Salad","Cheese Burger","Pizza","Salad"]

foodArray2 = ["Salad","Cheese Burger","Pizza","CheeseBurger"]

foodArray3 = ["Salad","Cheese Burger","Pizza", "Pizza"]

seasons = ["Spring","Summer","Autumn","Winter"]

# 2016 + 2017
def getFood(month):
	if month <= 3:
		i = randint(0,len(foodArray) -1 )
		return foodArray[i]
	elif month <= 6:
		i = randint(0,len(foodArray1) -1 )
		return foodArray1[i]
	elif month <= 9:
		i = randint(0,len(foodArray2) -1 )
		return foodArray2[i]
	else:
		i = randint(0,len(foodArray3) -1 )
		return foodArray3[i]

#2015
# def getFood(month):
# 	if month <= 3:
# 		i = randint(0,len(foodArray2) -1 )
# 		return foodArray2[i]
# 	elif month <= 6:
# 		i = randint(0,len(foodArray3) -1 )
# 		return foodArray3[i]
# 	elif month <= 9:
# 		i = randint(0,len(foodArray1) -1 )
# 		return foodArray1[i]
# 	else:
# 		i = randint(0,len(foodArray) -1 )
# 		return foodArray[i]

def getSeason(month):
	if month <= 3:
		return seasons[0]
	elif month <= 6:
		return seasons[1]
	elif month <= 9:
		return seasons[2]
	else:
		return seasons[3]

def daterange(start_date, end_date):
	for n in range(int ((end_date - start_date).days)):
		yield start_date + timedelta(n)

start_date = date(2016, 1, 1)
end_date = date(2017, 04, 30)

# start_date = date(2016, 1, 1)
# end_date = date(2016, 12, 31)

for single_date in daterange(start_date, end_date):
	year = single_date.strftime("%Y")
	month = single_date.strftime("%m")
	day = single_date.strftime("%d")
	temperature, humidity = tempDict[month]
	windSpeed = 21
	
	for food in foodArray:
		isDinner = randint(0, 1)
		numberOfPeople = randint(0, 4)
		
		precipitation=.15
		food = getFood(month)
		season = getSeason(month)
		identifier =  "{0}.{1}.{2}.{3}_spacefoods".format(year,month,day,"default")
		jsonString = "{{\"identifier\":\"{0}\",\"values\":[{{\"key\":\"Month\",\"value\":\"{1}\",\"key_description\":\"Month\"}},{{\"key\":\"day\",\"value\":\"{2}\",\"key_description\":\"Day\"}},{{\"key\":\"year\",\"value\":\"{3}\",\"key_description\":\"Year\"}},{{\"key\":\"is_dinner\",\"value\":\"{4}\",\"key_description\":\"Lunch = 0 and Dinner = 1\"}},{{\"key\":\"season\",\"value\":\"{11}\",\"description\":\"Season(Spring,Summer,Autumn,Winter)\"}},{{\"key\":\"number_of_people\",\"value\":\"{5}\",\"key_description\":\"Number Of People\"}},{{\"key\":\"temp\",\"value\":\"{6}\",\"key_description\":\"Temperature\"}},{{\"key\":\"wind_speed\",\"value\":\"{7}\",\"key_description\":\"Wind Speed\"}},{{\"key\":\"humidity\",\"value\":\"{8}\",\"key_description\":\"Humidity\"}},{{\"key\":\"precipitation\",\"value\":\"{9}\",\"key_description\":\"Precipitation\"}},{{\"key\":\"food\",\"value\":\"{10}\",\"key_description\":\"Food that was eaten\"}}]}}".format(identifier,month,day,year,isDinner,numberOfPeople,temperature,windSpeed,humidity,precipitation,food, season)
		
		try:
			sendRequest(jsonString)
		except:
			print "problem with the following string: " + jsonString

	for i in range(0,randint(0, 10)) :
		isDinner = randint(0, 1)
		numberOfPeople = randint(0, 4)
		temperature = 28
		windSpeed = 21
		humidity=.46
		precipitation=.15
		food = getFood(month)
		season = getSeason(month)
		identifier =  "{0}.{1}.{2}.{3}_spacefoods".format(year,month,day,i)
		jsonString = "{{\"identifier\":\"{0}\",\"values\":[{{\"key\":\"Month\",\"value\":\"{1}\",\"key_description\":\"Month\"}},{{\"key\":\"day\",\"value\":\"{2}\",\"key_description\":\"Day\"}},{{\"key\":\"year\",\"value\":\"{3}\",\"key_description\":\"Year\"}},{{\"key\":\"is_dinner\",\"value\":\"{4}\",\"key_description\":\"Lunch = 0 and Dinner = 1\"}},{{\"key\":\"season\",\"value\":\"{11}\",\"description\":\"Season(Spring,Summer,Autumn,Winter)\"}},{{\"key\":\"number_of_people\",\"value\":\"{5}\",\"key_description\":\"Number Of People\"}},{{\"key\":\"temp\",\"value\":\"{6}\",\"key_description\":\"Temperature\"}},{{\"key\":\"wind_speed\",\"value\":\"{7}\",\"key_description\":\"Wind Speed\"}},{{\"key\":\"humidity\",\"value\":\"{8}\",\"key_description\":\"Humidity\"}},{{\"key\":\"precipitation\",\"value\":\"{9}\",\"key_description\":\"Precipitation\"}},{{\"key\":\"food\",\"value\":\"{10}\",\"key_description\":\"Food that was eaten\"}}]}}".format(identifier,month,day,year,isDinner,numberOfPeople,temperature,windSpeed,humidity,precipitation,food, season)
		
		try:
			sendRequest(jsonString)
		except:
			print "problem with the following string: " + jsonString


