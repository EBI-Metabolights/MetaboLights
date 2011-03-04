#!/bin/bash
# author Mark Rijnbeek
# TODO - should move into Ant and become an Ant task really

# Purpose: creates an (advanced) closure compilation of specview, minimizing it in size
# See also: http://code.google.com/closure/compiler/docs/api-tutorial3.html

# The web page pub.html in this folder makes use of the compile library and should work properly


if [ -f ../namespace.js ]
then
  rm ../namespace.js
fi
if [ -f ./provide.lst ]
then
  rm ./provide.lst
fi

#____________________

find ../ -name "*.js" -print | xargs grep 'goog.provide' > provide.lst

sed -i 's/^.*goog.provide/goog.require/g' provide.lst 

(echo "goog.provide('specview')"  && cat provide.lst )> ../namespace.js  

cd ../../

./third-party/closure/closure/bin/build/closurebuilder.py    --root=third-party/closure/ --root=specview/  --namespace="specview" --output_mode=compiled --compiler_jar=third-party/closure-compiler/compiler.jar --compiler_flags="--compilation_level=ADVANCED_OPTIMIZATIONS"  > ./specview/deploy/specview.js

#______________________

cd ./specview/deploy

if [ -f ../namespace.js ]
then
  rm ../namespace.js
fi
if [ -f ./provide.lst ]
then
  rm ./provide.lst
fi


