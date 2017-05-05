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
