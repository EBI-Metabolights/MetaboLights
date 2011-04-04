goog.provide('specview.model.NeighborList');
goog.require('goog.math.Vec2');
goog.require('goog.array');
goog.require('goog.math.Line');
goog.require('goog.debug.Logger');
goog.require('goog.debug.Console');
goog.require('specview.controller.Plugin');

/**
 * @typedef {{obj:Object, getCenter:(function():goog.math.Coordinate),
 *          getDistance:function(goog.math.Coordinate):number}}
 */
specview.model.Neighbor;


/**
 * Class for locating the objects nearest to a specified coordinate.
 * 
 * <pre class="code">
 * var neighborList = new specview.model.NeighborList( [ neighbors ]);
 * neighborList.getNearest( {
 * 	x : 4,
 * 	y : 5
 * });
 * </pre>
 * 
 * @class Class for computing objects for a specified coordinate.
 * @param {Array.
 *            <specview.model.Neighbor>} objects The objects to initialize the grid.
 * @param {number=}
 *            opt_cellSize The cell size, default is 2. This is in atomic units.
 * @param {number=}
 *            opt_tolerance The tolerance to consider an atom close enough to the specified coordinate. 
 *            The default is 0.3. This is in atomic units.
 * @constructor
 */

specview.model.NeighborList = function(objects, opt_cellSize, opt_tolerance) {
	this.cells = {};
	
	this.cells_samy={};
	
	this.cellSize = opt_cellSize ? opt_cellSize : 2;
	this.tolerance = opt_tolerance ? opt_tolerance : 0.3;
	this.xMin = 100000;
	this.yMin = 100000;
	this.xMax = -100000;
	this.yMax = -100000;
	// find min/max values for the grid
	for ( var i = 0, li = objects.length; i < li; i++) {
		var o = objects[i];
		var center = o.getCenter();
//		this.logger2.info("here is the object: ("+center.x+","+center.y+")    "+center)

		if (center.x < this.xMin) {
			this.xMin = center.x;
		}
		if (center.x > this.xMax) {
			this.xMax = center.x;
		}
		if (center.y < this.yMin) {
			this.yMin = center.y;
		}
		if (center.y > this.yMax) {
			this.yMax = center.y;
		}
	}

	this.xMin -= 1;
	this.yMin -= 1;
	this.xMax += 1;
	this.yMax += 1;

	// compute number of cells and create them
	this.width = this.xMax - this.xMin;
	this.height = this.yMax - this.yMin;
	this.xDim = Math.ceil(this.width / this.cellSize);
	this.yDim = Math.ceil(this.height / this.cellSize);

	// for ( var i = 0, li = this.xDim * this.yDim; i < li; i++) {
	// this.cells.push( []);
	// }

	// add the objects to the grid
	var k=0;
	goog.array.forEach(objects, function(o) {
		k++;
		var center = o.getCenter();//Coordinates of the object as it appears in the file
		
		
		/**
		 * HERE THE CENTER IS THE RELATIVE COORDINATE OF THE THE OBJECT. IT HAS TO CHANGE TO THE PIXEL COORDINATES.
		 * TO DO SO YOU HAVE TO CHANGE THE WAY TEH NEIGHBOR OBJECT HAS BEEN CREATED. IT TAKES THE RELATIVE COORDINATES FROM THE FILE
		 * AND IT KEEPS THEM AS A REFERENCE TO MAP TO THE KEY AND HENCE TO THE CELLS.
		 * 
		 * SO IT SHOULD BE DONE : CREATING ATTRIBUTES THAT HOLD THE PIXEL COORDINATES AND TAKE THEM AS A REFERENCE. THAT WAY,
		 * IN THE FUNCTION FINDTARGETLIST WHICH IS IN THE CONTROLLER WE CAN
		 */

		
		var x = Math.floor((center.x - this.xMin) / this.cellSize);
		var y = Math.floor((center.y - this.yMin) / this.cellSize);
		var key = y * this.xDim + x;

		if (!this.cells[key]) {
			this.cells[key] = [];
		}
		this.cells[key].push(o);
//		if(o.obj instanceof specview.model.Atom){
//			alert("object: "+o.obj+"\nkey: "+key+"\nx: "+x+"\ny: "+y+"\nxDim: "+this.xDim);
//		}
//		if(o.obj instanceof specview.model.Peak){
//			alert("object: "+o.obj+"\nkey: "+key+"\nx: "+x+"\ny: "+y+"\nxDim: "+this.xDim);			
//		}

		//samy
		var objet=o.obj;
		var coord;
		if(objet instanceof specview.model.Atom){
			 coord=objet.pixelCoordinates;
			var newCoord=new goog.math.Coordinate(parseInt(coord.x),parseInt(coord.y));
			this.cells_samy[newCoord]=objet;
		}else if(objet instanceof specview.model.Peak){
			 coord=objet.pixelCoord;
				var newCoord=new goog.math.Coordinate(parseInt(coord.x),parseInt(coord.y));
				this.cells_samy[newCoord]=objet;
				this.logger.info("one peak will be at the foollowing coordinates: "+ newCoord.x+" "+newCoord.y);
		}

	}, this);
//alert(this.cells_samy[new goog.math.Coordinate(421,19)]);

};

specview.model.NeighborList.prototype.getObjectFromCoord=function(coord){
	for(key in this.cells_samy){
		var truc=key.substr(1).split(",")
		var g=new goog.math.Coordinate(truc[0],parseInt(truc[1]))
		//special case for the peaks to allow the recognition when browsing over all the peak
		if(this.cells_samy[key] instanceof specview.model.Peak && goog.math.nearlyEquals(parseInt(truc[0]),coord.x,10)){
			return this.cells_samy[key];
		}
		if(goog.math.Coordinate.distance(coord,g)<15){
			return this.cells_samy[key];
		}
	}
	
//	return this.cells_samy[coord];
}



/**
 * 
 * @param coord=transformed coordinate. The relative ones from the file(20;5)
 * @return
 */
specview.model.NeighborList.prototype.cellsAroundCoord = function(coord) {
	var cells = [];
	var x = Math.floor((coord.x - this.xMin) / this.cellSize);
	var y = Math.floor((coord.y - this.yMin) / this.cellSize);	
	for ( var i = x - 1, li = x + 2; i < li; i++) {
		if (i < 0 || i >= this.xDim) {
			continue;
		}
		for ( var j = y - 1, lj = y + 2; j < lj; j++) {
			if (j < 0 || j >= this.yDim) {
				continue;
			}
			cells.push(j * this.xDim + i);
		}
	}

	return cells;
};

/**
 * 
 */
specview.model.NeighborList.prototype.triangleSign = function(a, b, c) {
	return (a.x - c.x) * (b.y - c.y) - (a.y - c.y) * (b.x - c.x);
};

// /**
// * calculate distance from a point to the nearest point on the bond line
// segment
// *
// * @param {specview.model.Bond}
// * bond, the subject bond
// * @param {goog.math.Coordinate}
// * coord coordinate of the subject point
// * @return {number} distance from the point to the nearest point on the bond
// */
// specview.model.NeighborList.prototype.bondDistance = function(bond, coord) {
// var line = new goog.math.Line(bond.source.coord.x, bond.source.coord.y,
// bond.target.coord.x, bond.target.coord.y);
// return goog.math.Coordinate.distance(line.getClosestSegmentPoint(coord.x,
// coord.y), coord);
// };

//Receive the transformed coordinate from findTargetList(control.js)
specview.model.NeighborList.prototype.getNearest = function(coord) {
	var nearestList = this.getNearestList(coord);
	if (nearestList.length > 0) {
		return nearestList[0];
	}
};

/**
 * Returns a neighboring objects for the specified coordinate. Sorted by
 * distance from the coordinate. Atoms have higher priority than bonds since
 * bonds will overlap with atoms. For atoms, the distance from the specified
 * coordinate to the atom coordinate is checked. If this is within the used
 * tolerance, the atom is a candidate. The search goes on untill all near atoms
 * in the neighboring cells are checked and the nearest atom is returned. For
 * bonds, a bounding box with a 2 * tolerance width and length of the bond is
 * used to check is within the (rotated) box. Any bond matching the coordinate
 * are assigned the tolerance as distance resulting in atoms having a higher
 * priority (The atom will be closer than tolerance...).
 */

//coord = relative coordinates(of the file) given by the findTargetList function in control.js
specview.model.NeighborList.prototype.getNearestList = function(coord) {
	var nearest = [];
	var cells = this.cellsAroundCoord(coord);//find the cells arounf the relative coordinates. Cells are everywhere on the editor.
											//data structure: array of int.

	//this.logger2.info("cells nearest "+cells.length)
	var rMin = this.tolerance;
	for (i = 0, li = cells.length; i < li; i++) {
		var cell = this.cells[cells[i]];//cells[i] is obviously the key
		if (cell) {// === if an object has been found at the relative coordinates
			for (j = 0, lj = cell.length; j < lj; j++) {
				//Here we are using the neighbor object created to see whether there is a peak,bond,atom or other.
				var o = cell[j];//o.obj is an atom a bond or a peak. It is the neighbor object
				var r = o.getDistance(coord);
				if (r < this.tolerance) {
					nearest.push( {
						obj : o.obj,
						distance : r
					});
				}
			}
		}
	}

	nearest.sort(function(a, b) {
		return a.distance - b.distance;
	});

	return goog.array.map(nearest, function(n) {
		return n.obj;
	});
};

/**
 * 
 * @param metaSpec object (molecule,array of atoms, peaks, bonds ...)
 * @returns
 */
specview.model.NeighborList.metaSpecToNeighbors = function(metaSpec) {
	var laMolecule=[metaSpec[0].molecule];
	var neighbors = goog.array.map(laMolecule, function(mol) {
		return {
			obj : mol,
			getCenter : function() {return mol.getCenter();
			},
			getDistance : function(point) {return goog.math.Coordinate.distance(mol.getCenter(), point)/4;
			}
		};
	});

	goog.array.forEach(laMolecule, function(mol) {
		//atoms
		neighbors = goog.array.concat(neighbors, goog.array.map(mol.atoms,function(a) 
		{
//			alert("coordinates of the atom: "+a+"\n"+a.pixelCoordinates.x+" "+a.pixelCoordinates.y);
			return {
				obj : a,
//				getCenter : function() {return a.pixelCoordinates;},//pixel cooridnates are not known yet
//				getDistance : function(point) {return goog.math.Coordinate.distance(a.pixelCoordinates, point);}//*10;}
				getCenter : function() {return a.coord;},//coord as it appears in the file
				getDistance : function(point) {return goog.math.Coordinate.distance(a.coord, point);}//*10;}
			};
		}));

		//bonds
		neighbors = goog.array.concat(neighbors, goog.array.map(mol.bonds,function(b) 
		{
			return {
				obj : b,
//				getCenter : function() {
//					var midPoint = goog.math.Vec2.fromCoordinate(goog.math.Coordinate.sum(b.source.pixelCoordinates, b.target.pixelCoordinates));
//					return midPoint.scale(0.5);
//				},
//				getDistance : function(point) {
//					var line = new goog.math.Line(b.source.pixelCoordinates.x,b.source.pixelCoordinates.y, b.target.pixelCoordinates.x,b.target.pixelCoordinates.y);
//					return goog.math.Coordinate.distance(line.getClosestSegmentPoint(point.x, point.y),point);
//				}
				getCenter : function() {
					var midPoint = goog.math.Vec2.fromCoordinate(goog.math.Coordinate.sum(b.source.coord, b.target.coord));
					return midPoint.scale(0.5);
				},
				getDistance : function(point) {
					var line = new goog.math.Line(b.source.coord.x,b.source.coord.y, b.target.coord.x,b.target.coord.y);
					return goog.math.Coordinate.distance(line.getClosestSegmentPoint(point.x, point.y),point);
				}
			};
		}));
	});
	var leSpec=[metaSpec[0].spectrum];
	
	goog.array.forEach(leSpec, function(spec){
		//peaks
		neighbors=goog.array.concat(neighbors,goog.array.map(spec.peakList,function(s){
//			alert("One peak will be highlighted at the following coordinates:\n"+s.pixelCoord);//PIXEL COORDINATES
			return {
				obj : s,
				getCenter : function() {return s.coord;},
				getDistance : function(point) {return goog.math.Coordinate.distance(s.coord,point);}
//				getCenter : function() {return s.pixelCoord;},
//				gerDistance : function(point) {return goog.math.Coordinate.distance(s.pixelCoord,point);}
			};
		}));
	});
	return neighbors;
};





/**
 * @param {Array.
 *            <specview.model.Molecule>} molecules
 * @return {Array.<specview.model.Neighbor>}
 */
specview.model.NeighborList.moleculesToNeighbors = function(molecules) {
	var neighbors = goog.array.map(molecules, function(mol) {
		return {
			obj : mol,
			getCenter : function() {return mol.getCenter();
			},
			getDistance : function(point) {return goog.math.Coordinate.distance(mol.getCenter(), point)/4;
			}
		};
	});

	goog.array.forEach(molecules, function(mol) {
		//atoms
		neighbors = goog.array.concat(neighbors, goog.array.map(mol.atoms,function(a) 
		{
			return {
				obj : a,
				getCenter : function() {return a.coord;},
				getDistance : function(point) {return goog.math.Coordinate.distance(a.coord, point);}//*10;}
			};
		}));

		//bonds
		neighbors = goog.array.concat(neighbors, goog.array.map(mol.bonds,function(b) 
		{
			return {
				obj : b,
				getCenter : function() {
					var midPoint = goog.math.Vec2.fromCoordinate(goog.math.Coordinate.sum(b.source.coord, b.target.coord));
					return midPoint.scale(0.5);
				},
				getDistance : function(point) {
					var line = new goog.math.Line(b.source.coord.x,b.source.coord.y, b.target.coord.x,b.target.coord.y);
					return goog.math.Coordinate.distance(line.getClosestSegmentPoint(point.x, point.y),point);
				}
			};
		}));
	});
	return neighbors;
};




specview.model.NeighborList.prototype.logger = goog.debug.Logger.getLogger('specview.model.NeighborList');
//specview.model.NeighborList.prototype.logger2 = goog.debug.Logger.getLogger('specview.model.NeighborList');
