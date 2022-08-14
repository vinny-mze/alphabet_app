#!/bin/bash
IMAGES="images/*.jpg"

for f in ${IMAGES}
do 
    for i in 3 5 11 15 21
    do
        printf "The name of this file is: $f with filter - ${i}x${i}" 
    done

done
