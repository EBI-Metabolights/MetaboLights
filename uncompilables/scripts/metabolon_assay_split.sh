#!/bin/bash

A1="a__"
A2="metabolite_profiling_mass_spectrometry.txt"
P="POS"
N="NEG"
POS="${A1}${P}_${A2}"

#remove old split files
rm ${A1}${P}_*_${A2} ${A1}${N}_*_${A2} >/dev/null 2>&1

for i in $(find . -iname "a__*.txt" -type f -exec basename {} \;)
do
  head -n 1 $i > header_file
  if [[ "$i" == "$POS" ]]
  then
     echo $i is a $P assay
     for num in {1..2} 
     do
        f=${A1}${P}_${num}_${A2}
        cp header_file ${f}
        if [[ "$num" == 1 ]]; then pa="POSEAR"; else pa="POSLAT"; fi
     	grep ${pa} $i >> ${f}
     done
  else
     echo $i is a $N assay
     for num in {1..2} 
     do
        f=${A1}${N}_${num}_${A2}
        cp header_file ${f}
        if [[ "$num" == 1 ]]; then pa="NEG"; else pa="POL"; fi
     	grep ${pa} $i >> ${f}
     done
  fi
  rm header_file 
done
