goog.provide("specview.view.Renderer");
goog.require("goog.structs.Map");
goog.require("goog.debug.Logger");

/**
 * Abstract Class to render a model object to a graphics representation
 * 
 * @param graphics
 *            {goog.graphics.AbstractGraphics} graphics to draw on.
 * @param {Object=} opt_default_config holding default values
 * @param opt_config
 *            {Object=} opt_config to override defaults
 * @constructor
 */
specview.view.Renderer = function(graphics, opt_default_config, opt_config) {

	this.graphics = graphics;
	var default_config = goog.isDef(opt_default_config) ? opt_default_config : {};
	this.config = new goog.structs.Map(default_config);
	
	if (goog.isDef(opt_config)) {
		// merge optional config into defaults
		this.config.addAll(opt_config); 
	}
	
};
specview.view.Renderer.prototype.render = goog.abstractMethod;

specview.view.Renderer.prototype.logger = goog.debug.Logger.getLogger('specview.view.Renderer');

/**
 * @param {specview.graphics.AffineTransform} transform
 */
specview.view.Renderer.prototype.setTransform = function(transform){
	this.transform = transform;
};

specview.view.Renderer.prototype.renderGrid = function(box, opt_color){
	
	
	
    var boxTopLeftCoord =new goog.math.Coordinate(box.left,box.top);
    var boxTopRightCoord =new goog.math.Coordinate(box.right,box.top);
    var boxBotLeftCoord =new goog.math.Coordinate(box.left,box.bottom);
    var boxBotRightCoord =new goog.math.Coordinate(box.right,box.bottom);
    
    var boxCoords = this.transform.transformCoords( [boxTopLeftCoord,boxTopRightCoord,boxBotLeftCoord,boxBotRightCoord]);
    var boxPath = new goog.graphics.Path();
    var scaleX=(boxCoords[1].x-boxCoords[0].x)/21;
    var scaleY=(boxCoords[2].y-boxCoords[0].y)/9;
    //Grill x
    for(var k=boxCoords[0].x;k<boxCoords[1].x;k+=scaleX){
    	boxPath.moveTo(k,boxCoords[0].y);
    	boxPath.lineTo(k,boxCoords[3].y+10);
    }
    //Grill y
    for(var k=boxCoords[0].y;k<boxCoords[2].y;k+=scaleY){
    	boxPath.moveTo(boxCoords[0].x-10,k);
    	boxPath.lineTo(boxCoords[1].x,k);
    }
    
    if(!opt_color)
        opt_color='black';
        
    var boxStroke = new goog.graphics.Stroke(0.32,opt_color);
    var boxFill = null;
    
    this.graphics.drawPath(boxPath, boxStroke, boxFill);
    
};



specview.view.Renderer.prototype.renderAxis = function(metaSpecObject,box,opt_color){
    var boxTopLeftCoord =new goog.math.Coordinate(box.left,box.top);
    var boxTopRightCoord =new goog.math.Coordinate(box.right,box.top);
    var boxBotLeftCoord =new goog.math.Coordinate(box.left,box.bottom);
    var boxBotRightCoord =new goog.math.Coordinate(box.right,box.bottom);
    var peakList=spectrum.peakList;
    

    var boxCoords = this.transform.transformCoords( [boxTopLeftCoord,boxTopRightCoord,boxBotLeftCoord,boxBotRightCoord]);
//    this.logger.info("box coord: "+boxCoords);
    var scaleX=(boxCoords[1].x-boxCoords[0].x)/21;
    var scaleY=(boxCoords[2].y-boxCoords[0].y)/9;
  
    var stroke = new goog.graphics.Stroke(0.4,opt_color);
	var fill = new goog.graphics.SolidFill('black');
    var font = new goog.graphics.Font(10, 'Times');


    var boxPath = new goog.graphics.Path();
    var boxStroke = new goog.graphics.Stroke(1.5,"black");
    var boxFill = null;
    boxPath.moveTo(boxCoords[2].x, boxCoords[2].y);
    boxPath.lineTo(boxCoords[0].x, boxCoords[0].y);
    boxPath.moveTo(boxCoords[2].x, boxCoords[2].y);
    boxPath.lineTo(boxCoords[3].x, boxCoords[3].y);
    this.graphics.drawPath(boxPath, boxStroke, boxFill);
    
    for(var k=boxCoords[0].x;k<boxCoords[1].x;k+=1){
    	for(a in peakList){
    		if(parseInt(k)==parseInt(peakList[a].xPixel)){
    	        this.graphics.drawText(parseInt(peakList[a].xValue), k, boxCoords[2].x+75, 600, 200, 'l eft', null,
    	                font, stroke, fill);
    		}
    	}
    }
    
    for(var k=boxCoords[0].y;k<boxCoords[2].y;k+=scaleY){
        this.graphics.drawText(parseInt(k), boxCoords[0].x-35, k, 600, 200, 'l eft', null,
                font, stroke, fill);
    }
};


/**
 * Convenience method to see where elements are rendered by
 * drawing the bounding box
 * @param {goog.math.Box } bounding box
 */
specview.view.Renderer.prototype.renderBoundingBox = function(box, opt_color){

    var boxTopLeftCoord =new goog.math.Coordinate(box.left,box.top);
    var boxTopRightCoord =new goog.math.Coordinate(box.right,box.top);
    var boxBotLeftCoord =new goog.math.Coordinate(box.left,box.bottom);
    var boxBotRightCoord =new goog.math.Coordinate(box.right,box.bottom);

//    alert("top left: "+boxTopLeftCoord+"\ntop right: "+boxTopRightCoord+"\nbot left: "+boxBotLeftCoord+"\nbot right: "+boxBotRightCoord);
    
    boxTopRightCoord=(boxTopRightCoord.x<boxTopLeftCoord.x ? new goog.math.Coordinate(1200,box.top) : boxTopRightCoord);
    boxBotRightCoord=(boxBotRightCoord.x<boxBotLeftCoord.x ? new goog.math.Coordinate(1200,box.bottom) : boxBotRightCoord);
    
    var boxCoords = this.transform.transformCoords( [boxTopLeftCoord,boxTopRightCoord,boxBotLeftCoord,boxBotRightCoord]);
//    alert("box:\n"+box+"\n\nboxCoords: \n"+boxCoords)
    
    //var boxCoords = [boxTopLeftCoord,boxTopRightCoord,boxBotLeftCoord,boxBotRightCoord];
    var boxPath = new goog.graphics.Path();
    
//    this.logger.info("THE BOX\n\n\n"+boxCoords+"\n\n\n");

    boxPath.moveTo(boxCoords[0].x, boxCoords[0].y); 
    boxPath.lineTo(boxCoords[1].x,boxCoords[1].y);

    boxPath.moveTo(boxCoords[1].x,boxCoords[1].y);
    boxPath.lineTo(boxCoords[3].x,boxCoords[3].y);

    boxPath.moveTo(boxCoords[3].x,boxCoords[3].y);
    boxPath.lineTo(boxCoords[2].x,boxCoords[2].y);

    boxPath.moveTo(boxCoords[2].x,boxCoords[2].y);
    boxPath.lineTo(boxCoords[0].x, boxCoords[0].y); 
    
    if(!opt_color)
        opt_color='black'
        
    var boxStroke = new goog.graphics.Stroke(1,opt_color);
    var boxFill = null;
    
    this.graphics.drawPath(boxPath, boxStroke, boxFill);
};

