/*
 * Copyright [2010] [Samy Deghou] 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License.
 */
goog.provide('specview.util.Utilities');
goog.require('goog.debug.Logger');

/**
 * Logger object
 */
specview.util.Utilities.logger = goog.debug.Logger.getLogger('specview.util.Utilities.');

specview.util.Utilities.startsWith=function(word,string){
	return (word==string.substr(0,word.length) ? true : false);
};
goog.exportSymbol("specview.util.Utilities.startsWith", specview.util.Utilities.startsWith);


/**
 * Return a string after a given character
 */
specview.util.Utilities.getStringAfterCharacter=function(string,character){
		var indice = string.indexOf(character);
		return (indice==-1 ? string : string.substring(indice+1));
	
};

/**
 * Return the substring before a given character
 */
specview.util.Utilities.getStringBeforeCharacter = function(string,character){
	var indice = string.indexOf(character);
	return (indice==-1 ? string : string.substring(0,indice+1));
};

/**
 * Sort an array
 */
specview.util.Utilities.sortArrayIncreasing=function(arrayUnsorted){
	var array=arrayUnsorted;
	array=array.sort(function(a,b){return a - b;});
	return array;
};

/**
 * Get one decimal after a float
 */
specview.util.Utilities.parseOneDecimal = function(number){
	var string = number.toString();
	return string.substring(0,string.indexOf(".")+2);
};

/**
 * Get the number of elements in an associative array (objects of type Array or of type Object)
 */
specview.util.Utilities.getAssoArrayLength = function(array){
	var c=0;
	for(k in array){
		c++;
	}
	return c;
};


/**
 * Return an array like view of the object of type Object
 */
specview.util.Utilities.getSubSetOfObject = function(array,from,to){
	var truc = new Array();
	var c=0;
	for(key in array){
		c++;
	}
//	alert(truc);
	return truc;
};


specview.util.Utilities.insertCarriageReturnInString = function(string,CR,step){
	var len = string.length;
	var toreturn="";
	for(var k=0;k<len-step;k+=step){
		toreturn+=toreturn+string.substring(k,k+step)+CR;
//		alert(toreturn)
	}
	return toreturn;
}

specview.util.Utilities.parsePixel = function(pixel){
	return pixel.substring(0,pixel.indexOf("p"));
};


specview.util.Utilities.intersect =
	  function(a1,a2) {
	      var l = a1.length;
	      var ll = a2.length;
	      for(var i=0; i<l; i++) {
	        for(var j=0; j<ll; j++) {
	          if (a1[i] === a2[j])
	        	  return true;
	        }
	      }
	      return false;
	  };

	  
specview.util.Utilities.contains = function(a, obj){
		  for(var i = 0; i < a.length; i++) {
		    if(a[i] === obj){
		      return true;
		    }
		  }
		  return false;
		}
