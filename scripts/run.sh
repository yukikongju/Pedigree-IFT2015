#!/usr/bin/sh

num_iter = 5

# n = $1
# Tmax = $2

# n = 1000
# Tmax = 20000

# # jar directory
# dir=/

# # jar name
# jar_name=/

for i in {0..$num_iter}
do
    # # execute jar
    # java -jar $dir/$jar_name n Tmax

    # save plot generated
    python plot.py

done
