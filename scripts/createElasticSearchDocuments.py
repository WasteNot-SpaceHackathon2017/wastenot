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

def sendRequest(d,identifier):
	url = 'http://localhost:9200/dev/food/' + identifier
	req = urllib2.Request(url)
	response = urllib2.urlopen(req, json.dumps(d))

f = open("food.csv",'w')
f.write("Year,Month,Day,Lunch/Dinner,Season,Number of People,temp,Wind Speed,Humidity,Precipitation,Food\n")
arr = json.loads(urllib2.urlopen("http://localhost:9000/entries").read())

for d in arr:
	sendRequest(d,d['name'])



