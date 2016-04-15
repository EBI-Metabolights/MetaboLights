var metExploreD3 = {

    // Naming
    GraphUtils:"",
    GraphCaption:"",
    GraphNetwork:"",
    GraphLink:"",
    GraphFunction:"",
    GraphMapping:"",
    GraphPanel:"",
    GraphNode:"",

    // Interface with stores
    // Getters & Setters
    getGlobals : function(){
        return _metExploreViz;
    },

    // CompartmentInBioSource
    newNetworkData : function(panel){
        return new NetworkData(panel);
    },

    // CompartmentInBioSource
    getCompartmentInBiosourceSet : function(){
        return _metExploreViz.getSessionById("viz").getD3Data().getCompartments();
    },
    sortCompartmentInBiosource : function(){
        _metExploreViz.getSessionById("viz").getD3Data().sortCompartments();
    },
    getCompartmentInBiosourceLength : function(){
        return _metExploreViz.getSessionById("viz").getD3Data().getCompartmentsLength();
    },

    // Scale
    setScale : function(store, panel){
        _metExploreViz.getSessionById(panel).setScale(store);
    },
    getScaleById : function(panel){
        return _metExploreViz.getSessionById(panel).getScale();
    },
    

    // Metabolite
    setMetabolitesSet : function(store){
        Ext.getStore("S_Metabolite") = store;
    },
    getMetabolitesSet : function(){
        return Ext.getStore("S_Metabolite");
    },
    getMetaboliteById : function(store, id){
        return store.getById(id);
    },
    

    // Reaction
    setReactionsSet : function(store){
        Ext.getStore("S_Reaction") = store;
    },
    getReactionsSet : function(){
        return Ext.getStore("S_Reaction");
    },
    getReactionById : function(store, id){
        return store.getById(id);
    },


    // ReactionStyle
    setReactionStyle : function(store){
        _metExploreViz.reactionStyle = store;
    },
    getReactionStyle : function(){
        return _metExploreViz.reactionStyle;
    },


    // MetaboliteStyle
    setMetaboliteStyle : function(store){
        _metExploreViz.metaboliteStyle = store;
    },
    getMetaboliteStyle : function(){
        return _metExploreViz.metaboliteStyle;
    },


    // LinkStyle
    setLinkStyle : function(store){
        _metExploreViz.linkStyle = store;
    },
    getLinkStyle : function(){
        return _metExploreViz.linkStyle;
    },


    // Condition
    setConditionsSet : function(store){
        Ext.getStore("S_Condition") = store;
    },
    getConditionsSet : function(){
        return Ext.getStore("S_Condition");
    },
    getConditionById : function(store, id){
        return store.getById(id);
    },
    getConditionsSetLength : function(store){
        return store.getCount();
    },


    // MappingInfo
    setMappingInfosSet : function(store){
        Ext.getStore("S_MappingInfo") = store;
    },
    getMappingInfosSet : function(){
        return Ext.getStore("S_MappingInfo");
    },
    getMappingInfoById : function(store, id){
        return store.getById(id);
    },
    findMappingInfo : function(mappingInfoStore, id, idMapping){
        return mappingInfoStore.findRecord(id, idMapping);
    },


    // ColorMapping
    setColorMappingsSet : function(store){
        Ext.getStore("S_ColorMapping") = store;
    },
    getColorMappingsSet : function(){
        return Ext.getStore("S_ColorMapping");
    },
    getColorMappingById : function(store, id){
        return store.getColorByName(id);
    }, 
    getColorMappingsSetLength : function(store){
        return store.getCount();
    },
    resetColorMapping : function(store){
        store.loadData([],false);
    },
    addColorMapping : function(colorStore, n, c){
        colorStore.add({
            'name' : n,
            "value" : c
        }); 
    },


    // ReactionMap
    newReactionMapStore : function(store){
        return Ext.create('Ext.data.Store',{
            fields : ['id','ec','name','reversible']
        });
    },
    addReactionMap : function(storeReactionMap, myid, myec, myname, myreversible){
        storeReactionMap.add({
            id: myid,
            ec: myec,
            name: myname,
            reversible: myreversible
        });
    },
    getReactionMapsSetLength : function(store){
        return store.getCount();
    },


    // Mapping selectionned by user
    getMappingSelection : function(){
        var combBoxSelectMappingVisu = Ext.getCmp('selectMappingVisu');
        return combBoxSelectMappingVisu.getValue();
    },

    // Other function 
   
    /******************************************
    * Display the mask with the loading GIF 
    * @param {} mask : The mask to show
    */
    showMask : function(mask){
        mask.show();
    },
    /******************************************
    * Hide the mask
    * @param {} mask : The mask to hide
    */
    hideMask : function(mask){
        mask.hide();
    },
    /******************************************
    * Create a mask with the loading GIF 
    * @param {} label : The mask label
    * @param {} component : The panel where is displayed the mask
    */
    createLoadMask : function(label, component){
        if(component!='viz')
            var panelComponent = component.substring(0, component.length-5);
        else
            var panelComponent = component;
        if(panelComponent!= undefined){

            return new Ext.LoadMask({
                    target: Ext.getCmp(panelComponent), 
                    msg: label, 
                    msgCls:'msgClsCustomLoadMask'
                });
        }
        else{
            return undefined;
        }
    },


    /******************************************
    * Create a task 
    * @param {} func : The task function
    */
    createDelayedTask : function(func){
        return new Ext.util.DelayedTask(func);
    },
    /******************************************
    * Fix a delay to task 
    * @param {} task : The task to delay
    * @param {} time : The delay
    */
    fixDelay : function(task, time){
       task.delay(time);
    },
    /******************************************
    * Stop task 
    * @param {} task : The task to stop
    */
    stopTask : function(task){
       task.cancel();
    },

    /******************************************
    * Fire event with argument
    * @param {} cmp : View which received the event
    * @param {} task : Name of the event
    * @param {} arg : Argument for the event
    */
    fireEvent2Arg : function(cmp, name, arg1, arg2){
        var component = Ext.getCmp(cmp);
        if(component!= undefined){
            component.fireEvent(name, arg1, arg2);
        }
    },

    /******************************************
    * Fire event with argument
    * @param {} cmp : View which received the event
    * @param {} task : Name of the event
    * @param {} arg : Argument for the event
    */
    fireEventArg : function(cmp, name, arg){
        var component = Ext.getCmp(cmp);
        if(component!= undefined){
            component.fireEvent(name, arg);
        }
    },

    /******************************************
    * Fire Event
    * @param {} cmp : View which received the event
    * @param {} task : Name of the event
    */
    fireEvent : function(cmp, name){
        var component = Ext.getCmp(cmp);
        if(component!= undefined){
            component.fireEvent(name);
        }
    },

    /******************************************
    * Fire Event
    * @param {} cmp : View which received the event
    * @param {} task : Name of the event
    */
    fireEventParentWebSite : function(myEvent, arg){
        var theEvent = new Event(myEvent, arg);
        theEvent.value = arg;
        _metExploreViz.getParentWebSite().document.dispatchEvent(theEvent);
    },

    /******************************************
    * Display message 
    * @param {} type : Message type
    * @param {} msg : Message to display
    */
    displayMessage : function(type, msg){
       Ext.Msg.alert(type, msg);
    },

    /******************************************
    * Display message question Ok
    * @param {} type : Message type
    * @param {} msg : Message to display
    */
    displayMessageOK : function(msgTitle, msg, fct){
       
        Ext.Msg.show({
           title:msgTitle,
           msg: msg,
           buttons: Ext.Msg.OKCANCEL,
           fn: fct,
           icon: Ext.Msg.QUESTION
       });
    },
    /******************************************
    * Defer function 
    * @param {} func : The function to defer
    * @param {} time : The delay
    */
    deferFunction : function(func, time){
        return new Ext.defer(func, time);
    }
};

var _metExploreViz;
var metExploreViz = function(panel, webSite){
    _metExploreViz = this;
    this.path = Ext.getDoc().dom.location.origin + "/metexploreviz/";

    // Global variables 
    this.sessions = {};

    this.launched = false;
    this.panel = panel;
    this.linkStyle = new LinkStyle(25, 2, 5, 5, 'red', 'green', 'black', '0.7', '#154B7D');
    this.reactionStyle = new ReactionStyle(10, 20, 3, 3, 'ec', 8, '#154B7D');
    this.metaboliteStyle = new MetaboliteStyle(10, 10, 5, 5, 6, 1,'name', '#b2ae92');
    this.initialData = undefined;
    this.activeMapping = "";
    
    this.maxDisplayedLabels = 500;
    this.comparedPanels = [];
    this.mappings = [];
    this.colorMappings = [];
    this.linkedByTypeOfMetabolite = false;
    this.parentWebSite = webSite;

    // Dispatch the event.
    metExploreD3.fireEventArg('networkPanel', "afterlunch", panel);
};

metExploreViz.prototype = {
    getParentWebSite : function(){
        return this.parentWebSite;
    },
    cloneNetworkData : function(networkData){
        var newData = new NetworkData();
       
        var n, name, comp, dbId, ec, id, rev, sc, bt, sel, lv, svg, svgW, svgH;

        for (var j=0; j<networkData.nodes.length; j++) {
            n = networkData.nodes[j];
            if(n.getName()!=undefined)
                name = n.getName().valueOf();
            else
                name = undefined;
            
            if(n.getCompartment()!=undefined)
                comp = n.getCompartment().valueOf();
            else
                comp = undefined;
            
            if(n.getDbIdentifier()!=undefined)
                dbId = n.getDbIdentifier().valueOf();
            else
                dbId = undefined;
            
            if(n.getEC()!=undefined)
                ec = n.getEC().valueOf();
            else
                ec = undefined;
            
            if(n.getId()!=undefined)
                id = n.getId().valueOf();
            else
                id = undefined;

            if(n.getReactionReversibility()!=undefined && n.getBiologicalType()=="reaction")
                rev = n.getReactionReversibility().valueOf();
            else
                rev = undefined;
            
            if(n.getIsSideCompound()!=undefined)
                sc = n.getIsSideCompound().valueOf();
            else
                sc = undefined;
            
            if(n.getBiologicalType()!=undefined)
                bt = n.getBiologicalType().valueOf();
            else
                bt = undefined;

            if(n.isSelected()!=undefined)
                sel = n.isSelected().valueOf();
            else
                sel = undefined;

            if(n.getLabelVisible()!=undefined)
                lv = n.getLabelVisible().valueOf();
            else
                lv = undefined;

            if(n.getSvg()!=undefined)
                svg = n.getSvg().valueOf();
            else
                svg = undefined;

            if(n.getSvgWidth()!=undefined)
                svgW = n.getSvgWidth().valueOf();
            else
                svgW = undefined;

            if(n.getSvgHeight()!=undefined)
                svgH = n.getSvgHeight().valueOf();
            else
                svgH = undefined;

            newData.addNode(name,comp,dbId,id,rev,bt,sel,lv,svg,svgW,svgH,sc,ec);
        }

        for (var j=0; j<networkData.links.length; j++) {

            var id = networkData.links[j].getId().valueOf(); 
            var src = networkData.links[j].getSource();
            var source = newData.nodes[src];
            
            var tgt = networkData.links[j].getTarget();
            var target = newData.nodes[tgt];
       
            var interaction = networkData.links[j].getInteraction().valueOf();    
            var reversible = networkData.links[j].isReversible().valueOf();  
            
            newData.links[j] = new LinkData(id, source, target, interaction, reversible);
        }

        return newData;
    },
    
    cloneSession : function(){
        var newSession = new NetworkVizSession();
        newSession.reset();
        
        var n, name, comp, dbId, ec, id, rev, sc, bt, sel, lv, svg, svgW, svgH;

        for (var j=0; j<this.initialData.nodes.length; j++) {
            n = this.initialData.nodes[j];
            if(n.getName()!=undefined)
                name = n.getName().valueOf();
            else
                name = undefined;
            
            if(n.getCompartment()!=undefined)
                comp = n.getCompartment().valueOf();
            else
                comp = undefined;
            
            if(n.getDbIdentifier()!=undefined)
                dbId = n.getDbIdentifier().valueOf();
            else
                dbId = undefined;
            
            if(n.getEC()!=undefined)
                ec = n.getEC().valueOf();
            else
                ec = undefined;
            
            if(n.getId()!=undefined)
                id = n.getId().valueOf();
            else
                id = undefined;

            if(n.getReactionReversibility()!=undefined && n.getBiologicalType()=="reaction")
                rev = n.getReactionReversibility().valueOf();
            else
                rev = undefined;
            
            if(n.getIsSideCompound()!=undefined)
                sc = n.getIsSideCompound().valueOf();
            else
                sc = undefined;
            
            if(n.getBiologicalType()!=undefined)
                bt = n.getBiologicalType().valueOf();
            else
                bt = undefined;

            if(n.isSelected()!=undefined)
                sel = n.isSelected().valueOf();
            else
                sel = undefined;

            if(n.getLabelVisible()!=undefined)
                lv = n.getLabelVisible().valueOf();
            else
                lv = undefined;

            if(n.getSvg()!=undefined)
                svg = n.getSvg().valueOf();
            else
                svg = undefined;

            if(n.getSvgWidth()!=undefined)
                svgW = n.getSvgWidth().valueOf();
            else
                svgW = undefined;

            if(n.getSvgHeight()!=undefined)
                svgH = n.getSvgHeight().valueOf();
            else
                svgH = undefined;

            newSession.d3Data.addNode(name,comp,dbId,id,rev,bt,sel,lv,svg,svgW,svgH,sc,ec);
        }

        for (var j=0; j<this.initialData.links.length; j++) {

            var id = this.initialData.links[j].getId().valueOf(); 

            var src = this.initialData.links[j].getSource();
            var source = newSession.d3Data.nodes[this.initialData.nodes.indexOf(src)];
            
            var tgt = this.initialData.links[j].getTarget();
            var target = newSession.d3Data.nodes[this.initialData.nodes.indexOf(tgt)];
       
            var interaction = this.initialData.links[j].getInteraction().valueOf();    
            var reversible = this.initialData.links[j].isReversible().valueOf();  
            
            newSession.d3Data.links[j] = new LinkData(id, source, target, interaction, reversible);
        }

        return newSession;
    },
    //If there are less than this number of reactions in the store, then the labelled are displayed.
    getMaxDisplayedLabels:function(){return this.maxDisplayedLabels;},
    setMaxDisplayedLabels:function(maxLabels){this.maxDisplayedLabels = maxLabels;},

    getMetaboliteStyle : function(){
        return this.metaboliteStyle;
    },

    //NetworkVizSession
    addSession : function(store){
        this.sessions[store.getId()] = store;
    },
    getSessionsSet : function(){
        return this.sessions;
    },
    getSessionById : function(panel){
        return this.sessions[panel];
    },
    getSessionsLength : function(){
        return this.sessions.length;
    },
    removeSession : function(panel){
        if(this.sessions[panel]!=undefined)
        {
            delete this.sessions[panel];    
        }
    },

    getActiveMapping:function()
    {
       return this.activeMapping;
    },    
    setActiveMapping:function(activeMapping)
    {
       this.activeMapping = activeMapping;
    },

    getInitialData:function()
    {
       return this.initialData;
    },    
    setInitialData:function(initialData)
    {
       this.initialData = initialData;
    },
    
    isLaunched :function(){
        return this.launched;
    },
    setLaunched : function(bool){
        this.launched = bool;
    },

    addComparedPanel : function(aComparedPanel){
        this.comparedPanels.push(aComparedPanel);
    },
    removeComparedPanel : function(aComparedPanel){
        var found=false;
        var i=0;
        while(!found)
        {
            if(this.comparedPanels[i].getPanel()==aComparedPanel.getPanel())
            {
                this.comparedPanels.splice(i,1);
                found=true;        
            }
            i++;
        }
    },
    getComparedPanelsLength : function(){
        return this.comparedPanels.length;
    },
    getComparedPanelById : function(id){
        var theComparedPanel = null;
        this.comparedPanels.forEach(function(aComparedPanel){            
            if(aComparedPanel.getPanel()==id)
                theComparedPanel = aComparedPanel;
        });
        return theComparedPanel;
    },

    // Mapping
    getMappingsSet : function(){
        return this.mappings;
    },
    addMapping : function(aMapping){
        this.mappings.push(aMapping);
    },
    resetMappings : function(aMapping){
        this.mappings = [];
    },
    removeMapping : function(aMapping){
        this.mappings.remove(aMapping);
    },
    getMappingsLength : function(){
        return this.mappings.length;
    },
    getMappingByName : function(name){
        var themapping = null;
        this.mappings.forEach(function(aMapping){            
            if(aMapping.getName()==name)
                themapping = aMapping;
        });
        return themapping;
    },

    // ColorMapping
    getColorMappingsSet : function(){
        return this.colorMappings;
    },
    getColorMappingById : function(id){
        var theColorMapping = null;
        this.colorMappings.forEach(function(aColorMapping){            
            if(aColorMapping.getName()==id)
                theColorMapping = aColorMapping;
        });
        return theColorMapping;
    }, 
    getColorMappingsSetLength : function(){
        return this.colorMappings.length;
    },
    resetColorMapping : function(){
        this.colorMappings = [];
    },
    addColorMapping : function(n, c){
        this.colorMappings.push(new ColorMapping(n,c)); 
    },

    isLinkedByTypeOfMetabolite : function(){
        return this.linkedByTypeOfMetabolite;
    },
    setLinkedByTypeOfMetabolite : function(bool){
        this.linkedByTypeOfMetabolite = bool;
    }

};
