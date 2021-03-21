#!/usr/bin/sh

# n = $1
# Tmax = $2

n = 1000
Tmax = 20000

# jar directory
dir=/

# jar name
jar_name=/

# execute jar
java -jar $dir/$jar_name n Tmax

# save plot generated
python plot.py
