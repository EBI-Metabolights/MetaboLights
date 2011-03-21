
goog.provide('specview.util.Utilities');


specview.util.Utilities.startsWith=function(word,string){
	return (word==string.substr(0,word.length) ? true : false);
};
goog.exportSymbol("specview.util.Utilities.startsWith", specview.util.Utilities.startsWith);
