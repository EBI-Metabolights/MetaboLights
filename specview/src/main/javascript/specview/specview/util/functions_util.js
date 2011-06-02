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


specview.util.Utilities.startsWith=function(word,string){
	return (word==string.substr(0,word.length) ? true : false);
};
goog.exportSymbol("specview.util.Utilities.startsWith", specview.util.Utilities.startsWith);


specview.util.Utilities.getStringAfterCharacter=function(string,character){
	return (string.substring(string.indexOf(":")+1));
};

specview.util.Utilities.sortArrayIncreasing=function(arrayUnsorted){
	var array=arrayUnsorted;
	array=array.sort(function(a,b){return a - b;});
	return array;
};


specview.util.Utilities.parseOneDecimal = function(number){
	var string = number.toString();
	return string.substring(0,string.indexOf(".")+2);
};