
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