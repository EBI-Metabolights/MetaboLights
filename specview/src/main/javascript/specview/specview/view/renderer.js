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
	boxCoords=box;
	var topLeft=box[0];
	var topRight=box[1];
	var bottomLeft=box[2];
	var bottomRight=box[3];
    var boxPath = new goog.graphics.Path();
    var scaleX=(bottomRight.x-bottomLeft.x)/21;
    var scaleY=(bottomLeft.y-topLeft.y)/9;
    //Vertical grid
    for(var k=bottomLeft.x;k<bottomRight.x;k+=scaleX){
    	boxPath.moveTo(k,bottomRight.y);
    	boxPath.lineTo(k,topRight.y+10);
    }
    //Horizontal grid
    for(var k=topRight.y;k<bottomRight.y;k+=scaleY){
    	boxPath.moveTo(topLeft.x-10,k);
    	boxPath.lineTo(topRight.x,k);
    }
    if(!opt_color){
    	opt_color='black';	
    }
    var boxStroke = new goog.graphics.Stroke(0.32,opt_color);
    var boxFill = null;
    
    this.graphics.drawPath(boxPath, boxStroke, boxFill);
    
};



specview.view.Renderer.prototype.renderAxis = function(metaSpecObject,boxo,opt_color){
	
	var maxHeight=metaSpecObject.spectrum.getMaxHeightPeak();

	var boxCoords=metaSpecObject.mainSpecBox;
	var topLeft=boxCoords[0];
	var topRight=boxCoords[1];
	var bottomLeft=boxCoords[2];
	var bottomRight=boxCoords[3];

	var peakList=metaSpecObject.ArrayOfPeaks;
	
    var scaleX=(boxCoords[1].x-boxCoords[0].x)/21;
    var scaleY=(boxCoords[1].y-boxCoords[0].y)/9;
  
    var stroke = new goog.graphics.Stroke(0.4,opt_color);
	var fill = new goog.graphics.SolidFill('black');
    var font = new goog.graphics.Font(10, 'Times');

    var boxPath = new goog.graphics.Path();
    var boxStroke = new goog.graphics.Stroke(1.5,"black");
    var boxFill = null;
    /*
     * Draw the proper axis
     */
    boxPath.moveTo(bottomLeft.x, bottomLeft.y);
    boxPath.lineTo(bottomRight.x, bottomRight.y);
    boxPath.moveTo(bottomLeft.x, bottomLeft.y);
    boxPath.lineTo(topLeft.x, topLeft.y);
    this.graphics.drawPath(boxPath, boxStroke, boxFill);
    
    /*
     * Write the value on the x axis each time it encounters a peak
     */
    for(var k=bottomLeft.x;k<bottomRight.x;k+=1){
    	for(a in peakList){
    		if(parseInt(k)==parseInt(peakList[a].xPixel)){
    	        this.graphics.drawText(parseInt(peakList[a].xValue), k, bottomLeft.y, 600, 200, 'left', null,
    	                font, stroke, fill);
    		}
    	}
    }
    /*
     * Right the xUnit on the x Axis
     */
    this.graphics.drawText(metaSpecObject.spectrum.xUnit, bottomRight.x+20, bottomRight.y, 600, 200, 'left', null,
            font, stroke, fill);
    
    var count=9;
    scaleY=(280-5)/9;
    /*
     * Write the value on the y axis at regular steps
     */
    for(var k=bottomLeft.y-scaleY;k>topRight.y;k-=scaleY){
    	count-=1;
    	if(count!=0){
            this.graphics.drawText(parseInt(maxHeight/count), bottomLeft.x-30, k, 600, 200, 'left', null,
                    font, stroke, fill);	
    	}
    }
  /*
   * Right the yUnit on the y Axis if it is available.
   */
    if(metaSpecObject.spectrum.yUnit!=undefined){
    	this.graphics.drawText(metaSpecObject.spectrum.yUnit, topLeft.x-30, topLeft.y, 600, 200, 'left', null,
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
    
    boxTopRightCoord=(boxTopRightCoord.x<boxTopLeftCoord.x ? new goog.math.Coordinate(1200,box.top) : boxTopRightCoord);
    boxBotRightCoord=(boxBotRightCoord.x<boxBotLeftCoord.x ? new goog.math.Coordinate(1200,box.bottom) : boxBotRightCoord);
    
    var boxCoords = this.transform.transformCoords( [boxTopLeftCoord,boxTopRightCoord,boxBotLeftCoord,boxBotRightCoord]);

    var boxPath = new goog.graphics.Path();
    
    boxPath.moveTo(boxCoords[0].x, boxCoords[0].y); 
    boxPath.lineTo(boxCoords[1].x,boxCoords[1].y);

    boxPath.moveTo(boxCoords[1].x,boxCoords[1].y);
    boxPath.lineTo(boxCoords[3].x,boxCoords[3].y);

    boxPath.moveTo(boxCoords[3].x,boxCoords[3].y);
    boxPath.lineTo(boxCoords[2].x,boxCoords[2].y);

    boxPath.moveTo(boxCoords[2].x,boxCoords[2].y);
    boxPath.lineTo(boxCoords[0].x, boxCoords[0].y); 
    
    if(!opt_color)
        opt_color='black';
        
    var boxStroke = new goog.graphics.Stroke(1,opt_color);
    var boxFill = null;
    
    this.graphics.drawPath(boxPath, boxStroke, boxFill);
};

specview.view.Renderer.prototype.renderBoundingBoxWithPixel = function(boxCoords, opt_color){
/*
	var tl=new goog.math.Coordinate(400,40);
	var tr=new goog.math.Coordinate(400,88);
	var bl=new goog.math.Coordinate(890,40);
	var br=new goog.math.Coordinate(890,88);
	boxCoordsT=new Array();
	boxCoordsT[0]=tl;boxCoordsT[1]=tr;boxCoordsT[2]=bl;boxCoordsT[3]=br;
	
    var testPath = new goog.graphics.Path();

	
    testPath.moveTo(boxCoordsT[0].x, boxCoordsT[0].y); 
    testPath.lineTo(boxCoordsT[1].x,boxCoordsT[1].y);

    testPath.moveTo(boxCoordsT[1].x,boxCoordsT[1].y);
    testPath.lineTo(boxCoordsT[3].x,boxCoordsT[3].y);

    testPath.moveTo(boxCoordsT[3].x,boxCoordsT[3].y);
    testPath.lineTo(boxCoordsT[2].x,boxCoordsT[2].y);

    testPath.moveTo(boxCoordsT[2].x,boxCoordsT[2].y);
    testPath.lineTo(boxCoordsT[0].x, boxCoordsT[0].y); 
    
    var boxStroke = new goog.graphics.Stroke(1,"orange");
    var boxFill = null;
    
    this.graphics.drawPath(testPath, boxStroke, boxFill);
*/	
    var boxPath = new goog.graphics.Path();
//    alert("boxCoords: "+boxCoords)

    boxPath.moveTo(boxCoords[0].x, boxCoords[0].y); 
    boxPath.lineTo(boxCoords[1].x,boxCoords[1].y);

    boxPath.moveTo(boxCoords[1].x,boxCoords[1].y);
    boxPath.lineTo(boxCoords[3].x,boxCoords[3].y);

    boxPath.moveTo(boxCoords[3].x,boxCoords[3].y);
    boxPath.lineTo(boxCoords[2].x,boxCoords[2].y);

    boxPath.moveTo(boxCoords[2].x,boxCoords[2].y);
    boxPath.lineTo(boxCoords[0].x, boxCoords[0].y); 
    
    if(!opt_color)
        opt_color='black';
        
    var boxStroke = new goog.graphics.Stroke(1,opt_color);
    var boxFill = null;
    
    this.graphics.drawPath(boxPath, boxStroke, boxFill);
};

