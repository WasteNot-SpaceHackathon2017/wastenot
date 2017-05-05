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

f = open("prediction.csv",'r')
f_o = open("output.txt",'w')

dates = ["2017-05-01", "2017-05-08","2017-05-15","2017-05-22","2017-05-29","2017-06-05", "2017-06-12","2017-06-19","2017-06-26","2017-07-03","2017-07-10"]
j = 0
for line in f.read().split("\n"):
	i = 0
	for item in line.split(","):
		print j
		f_o.write("{{x: '{0}', y: {2}, group: {1}}},\n".format(dates[j],i,item))
		i += 1
	j += 1
