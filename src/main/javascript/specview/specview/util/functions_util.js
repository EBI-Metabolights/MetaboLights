
goog.provide('specview.util.Smurf');


specview.util.Smurf.startsWith=function(word,string){
	return (word==string.substr(0,word.length) ? true : false);
};
goog.exportSymbol("specview.util.Smurf.startsWith", specview.util.Smurf.startsWith);
