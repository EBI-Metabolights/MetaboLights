/*
 * Copyright 2010 Paul Novak paul@wingu.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
goog.provide('specview.ring.RingPartitioner');
goog.require('goog.array');





/**
 * partitions array of rings into connected lists
 * 
 * @param {Array.
 *            <specview.ring.Ring>} rings list of rings to group into connected
 *            arrays
 * @return {Array.<Array.<specview.ring.Ring>>} array of arrays of Rings
 */
specview.ring.RingPartitioner.getPartitionedRings = function(rings){
    var partitions = [];
    done= new Array(rings.length);
    for(x=0, x2=rings.length; x<x2; x++) {
        done[x]=false;
    }
    for (i=0, j=rings.length; i < j; i++) {
        if (!done[i]) {
          partition = new Array();
          partition.push(rings[i]);
          done[i]=true;
          atomCount=rings[i].atoms.length;
          for (k=i+1;k<rings.length;k++){
            if (!done[k]) {
				atomCount2 = rings[k].atoms.length;
				connected: for (p = 0; p < partition.length; p++) {
					atomCount = partition[p].atoms.length;
					for (a = 0; a < atomCount; a++) {
						for (a2 = 0; a2 < atomCount2; a2++) {
							if (partition[p].atoms[a] == rings[k].atoms[a2]) {
								partition.push(rings[k]);
								done[k] = true;
								k=i;
								break connected;
							}
						}
					}
				}
			}
          }
          partitions.push(partition);  
        }
    }
    return partitions;
}

/**
 * finds rings directly connected to the subject ring
 * 
 * @param{specview.ring.Ring} ring, the ring which we want to find direct
 *                         connections to
 * @param{Array.<specview.ring.Ring>} rings, the rings we want to search for
 *               connections
 * @return{Array.<specview.ring.Ring>} array of directly connected rings, which
 *                does *not* include the subject ring
 */
specview.ring.RingPartitioner.directConnectedRings = function(ring, rings){
	result = [];
	atomCount=ring.atoms.length;
	for (k=0,k1=rings.length;k<k1;k++){
		if (ring != rings[k]) {
			atomCount2 = rings[k].atoms.length;
			connected: for (a = 0; a < atomCount; a++) {
				for (a2 = 0; a2 < atomCount2; a2++) {
					if (ring.atoms[a] == rings[k].atoms[a2]) {
						result.push(rings[k]);
						break connected;
					}
				}
			}
		}
	}
	return result;
}


