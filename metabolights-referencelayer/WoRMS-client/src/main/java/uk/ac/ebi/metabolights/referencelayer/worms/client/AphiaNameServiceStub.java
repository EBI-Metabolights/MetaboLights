
/**
 * AphiaNameServiceStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
        package uk.ac.ebi.metabolights.referencelayer.worms.client;

        

        /*
        *  AphiaNameServiceStub java implementation
        */

        
        public class AphiaNameServiceStub extends org.apache.axis2.client.Stub
        {
        protected org.apache.axis2.description.AxisOperation[] _operations;

        //hashmaps to keep the fault mapping
        private java.util.HashMap faultExceptionNameMap = new java.util.HashMap();
        private java.util.HashMap faultExceptionClassNameMap = new java.util.HashMap();
        private java.util.HashMap faultMessageMap = new java.util.HashMap();

        private static int counter = 0;

        private static synchronized java.lang.String getUniqueSuffix(){
            // reset the counter if it is greater than 99999
            if (counter > 99999){
                counter = 0;
            }
            counter = counter + 1; 
            return java.lang.Long.toString(java.lang.System.currentTimeMillis()) + "_" + counter;
        }

    
    private void populateAxisService() throws org.apache.axis2.AxisFault {

     //creating the Service with a unique name
     _service = new org.apache.axis2.description.AxisService("AphiaNameService" + getUniqueSuffix());
     addAnonymousOperations();

        //creating the operations
        org.apache.axis2.description.AxisOperation __operation;

        _operations = new org.apache.axis2.description.AxisOperation[15];
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://aphia/v1.0", "getExtIDbyAphiaID"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[0]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://aphia/v1.0", "getAphiaRecordsByVernacular"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[1]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://aphia/v1.0", "getAphiaChildrenByID"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[2]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://aphia/v1.0", "getAphiaClassificationByID"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[3]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://aphia/v1.0", "matchAphiaRecordsByNames"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[4]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://aphia/v1.0", "getAphiaRecordsByDate"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[5]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://aphia/v1.0", "getAphiaSynonymsByID"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[6]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://aphia/v1.0", "getAphiaRecords"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[7]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://aphia/v1.0", "getAphiaRecordByID"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[8]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://aphia/v1.0", "getSourcesByAphiaID"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[9]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://aphia/v1.0", "getAphiaNameByID"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[10]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://aphia/v1.0", "getAphiaRecordsByNames"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[11]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://aphia/v1.0", "getAphiaRecordByExtID"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[12]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://aphia/v1.0", "getAphiaVernacularsByID"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[13]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://aphia/v1.0", "getAphiaID"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[14]=__operation;
            
        
        }

    //populates the faults
    private void populateFaults(){
         


    }

    /**
      *Constructor that takes in a configContext
      */

    public AphiaNameServiceStub(org.apache.axis2.context.ConfigurationContext configurationContext,
       java.lang.String targetEndpoint)
       throws org.apache.axis2.AxisFault {
         this(configurationContext,targetEndpoint,false);
   }


   /**
     * Constructor that takes in a configContext  and useseperate listner
     */
   public AphiaNameServiceStub(org.apache.axis2.context.ConfigurationContext configurationContext,
        java.lang.String targetEndpoint, boolean useSeparateListener)
        throws org.apache.axis2.AxisFault {
         //To populate AxisService
         populateAxisService();
         populateFaults();

        _serviceClient = new org.apache.axis2.client.ServiceClient(configurationContext,_service);
        
	
        _serviceClient.getOptions().setTo(new org.apache.axis2.addressing.EndpointReference(
                targetEndpoint));
        _serviceClient.getOptions().setUseSeparateListener(useSeparateListener);
        
    
    }

    /**
     * Default Constructor
     */
    public AphiaNameServiceStub(org.apache.axis2.context.ConfigurationContext configurationContext) throws org.apache.axis2.AxisFault {
        
                    this(configurationContext,"http://www.marinespecies.org/aphia.php?p=soap" );
                
    }

    /**
     * Default Constructor
     */
    public AphiaNameServiceStub() throws org.apache.axis2.AxisFault {
        
                    this("http://www.marinespecies.org/aphia.php?p=soap" );
                
    }

    /**
     * Constructor taking the target endpoint
     */
    public AphiaNameServiceStub(java.lang.String targetEndpoint) throws org.apache.axis2.AxisFault {
        this(null,targetEndpoint);
    }



        
                    /**
                     * Auto generated method signature
                     * &lt;strong&gt;Get any external identifier(s) for a given AphiaID.
&lt;br/&gt;Parameters:
&lt;ul&gt;
 &lt;li&gt;
  &lt;u&gt;type&lt;/u&gt;: Should have one of the following values:
   &lt;ul&gt;
    &lt;li&gt;&lt;u&gt;bold&lt;/u&gt;: Barcode of Life Database (BOLD) TaxID&lt;/li&gt;
    &lt;li&gt;&lt;u&gt;dyntaxa&lt;/u&gt;: Dyntaxa ID&lt;/li&gt;
	&lt;li&gt;&lt;u&gt;eol&lt;/u&gt;: Encyclopedia of Life (EoL) page identifier&lt;/li&gt;
	&lt;li&gt;&lt;u&gt;fishbase&lt;/u&gt;: FishBase species ID&lt;/li&gt;
	&lt;li&gt;&lt;u&gt;iucn&lt;/u&gt;: IUCN Red List Identifier&lt;/li&gt;
    &lt;li&gt;&lt;u&gt;lsid&lt;/u&gt;: Life Science Identifier&lt;/li&gt;
    &lt;li&gt;&lt;u&gt;ncbi&lt;/u&gt;: NCBI Taxonomy ID (Genbank)&lt;/li&gt;
    &lt;li&gt;&lt;u&gt;tsn&lt;/u&gt;: ITIS Taxonomic Serial Number&lt;/li&gt;
  &lt;/ul&gt;
 &lt;/li&gt;
&lt;/ul&gt;&lt;/strong&gt;
                     * @see uk.ac.ebi.metabolights.referencelayer.worms.client.AphiaNameService#getExtIDbyAphiaID
                     * @param getExtIDbyAphiaID0
                    
                     */

                    

                            public  aphia.v1_0.externalidentifiers.GetExtIDbyAphiaIDResponseDocument getExtIDbyAphiaID(

                            aphia.v1_0.externalidentifiers.GetExtIDbyAphiaIDDocument getExtIDbyAphiaID0)
                        

                    throws java.rmi.RemoteException
                    
                    {
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
              _operationClient.getOptions().setAction("getExtIDbyAphiaID");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getExtIDbyAphiaID0,
                                                    optimizeContent(new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getExtIDbyAphiaID")), new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getExtIDbyAphiaID"));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             aphia.v1_0.externalidentifiers.GetExtIDbyAphiaIDResponseDocument.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (aphia.v1_0.externalidentifiers.GetExtIDbyAphiaIDResponseDocument)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getExtIDbyAphiaID"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getExtIDbyAphiaID"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getExtIDbyAphiaID"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * &lt;strong&gt;Get any external identifier(s) for a given AphiaID.
&lt;br/&gt;Parameters:
&lt;ul&gt;
 &lt;li&gt;
  &lt;u&gt;type&lt;/u&gt;: Should have one of the following values:
   &lt;ul&gt;
    &lt;li&gt;&lt;u&gt;bold&lt;/u&gt;: Barcode of Life Database (BOLD) TaxID&lt;/li&gt;
    &lt;li&gt;&lt;u&gt;dyntaxa&lt;/u&gt;: Dyntaxa ID&lt;/li&gt;
	&lt;li&gt;&lt;u&gt;eol&lt;/u&gt;: Encyclopedia of Life (EoL) page identifier&lt;/li&gt;
	&lt;li&gt;&lt;u&gt;fishbase&lt;/u&gt;: FishBase species ID&lt;/li&gt;
	&lt;li&gt;&lt;u&gt;iucn&lt;/u&gt;: IUCN Red List Identifier&lt;/li&gt;
    &lt;li&gt;&lt;u&gt;lsid&lt;/u&gt;: Life Science Identifier&lt;/li&gt;
    &lt;li&gt;&lt;u&gt;ncbi&lt;/u&gt;: NCBI Taxonomy ID (Genbank)&lt;/li&gt;
    &lt;li&gt;&lt;u&gt;tsn&lt;/u&gt;: ITIS Taxonomic Serial Number&lt;/li&gt;
  &lt;/ul&gt;
 &lt;/li&gt;
&lt;/ul&gt;&lt;/strong&gt;
                * @see uk.ac.ebi.metabolights.referencelayer.worms.client.AphiaNameService#startgetExtIDbyAphiaID
                    * @param getExtIDbyAphiaID0
                
                */
                public  void startgetExtIDbyAphiaID(

                 aphia.v1_0.externalidentifiers.GetExtIDbyAphiaIDDocument getExtIDbyAphiaID0,

                  final uk.ac.ebi.metabolights.referencelayer.worms.client.AphiaNameServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
             _operationClient.getOptions().setAction("getExtIDbyAphiaID");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getExtIDbyAphiaID0,
                                                    optimizeContent(new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getExtIDbyAphiaID")), new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getExtIDbyAphiaID"));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         aphia.v1_0.externalidentifiers.GetExtIDbyAphiaIDResponseDocument.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultgetExtIDbyAphiaID(
                                        (aphia.v1_0.externalidentifiers.GetExtIDbyAphiaIDResponseDocument)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorgetExtIDbyAphiaID(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getExtIDbyAphiaID"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getExtIDbyAphiaID"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                                                    java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getExtIDbyAphiaID"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
					
										            callback.receiveErrorgetExtIDbyAphiaID(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetExtIDbyAphiaID(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetExtIDbyAphiaID(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetExtIDbyAphiaID(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetExtIDbyAphiaID(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetExtIDbyAphiaID(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetExtIDbyAphiaID(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetExtIDbyAphiaID(f);
                                            }
									    } else {
										    callback.receiveErrorgetExtIDbyAphiaID(f);
									    }
									} else {
									    callback.receiveErrorgetExtIDbyAphiaID(f);
									}
								} else {
								    callback.receiveErrorgetExtIDbyAphiaID(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrorgetExtIDbyAphiaID(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[0].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[0].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                
                    /**
                     * Auto generated method signature
                     * &lt;strong&gt;Get one or more Aphia Records (max. 50) for a given vernacular.&lt;/strong&gt;&lt;br/&gt;Parameters:
   &lt;ul&gt;
    &lt;li&gt;&lt;u&gt;like&lt;/u&gt;: add a '%'-sign before and after the input (SQL LIKE '%vernacular%' function). Default=false.&lt;/li&gt;
	&lt;li&gt;&lt;u&gt;offset&lt;/u&gt;: starting record number, when retrieving next chunck of (50) records. Default=1.&lt;/li&gt;
   &lt;/ul&gt;
  &lt;/strong&gt;
  &lt;br /&gt; For the structure of the returned AphiaRecords, please refer to the function getAphiaRecordByID()
                     * @see uk.ac.ebi.metabolights.referencelayer.worms.client.AphiaNameService#getAphiaRecordsByVernacular
                     * @param getAphiaRecordsByVernacular2
                    
                     */

                    

                            public  aphia.v1_0.aphiarecords.GetAphiaRecordsByVernacularResponseDocument getAphiaRecordsByVernacular(

                            aphia.v1_0.aphiarecords.GetAphiaRecordsByVernacularDocument getAphiaRecordsByVernacular2)
                        

                    throws java.rmi.RemoteException
                    
                    {
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[1].getName());
              _operationClient.getOptions().setAction("getAphiaRecordsByVernacular");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getAphiaRecordsByVernacular2,
                                                    optimizeContent(new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getAphiaRecordsByVernacular")), new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getAphiaRecordsByVernacular"));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             aphia.v1_0.aphiarecords.GetAphiaRecordsByVernacularResponseDocument.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (aphia.v1_0.aphiarecords.GetAphiaRecordsByVernacularResponseDocument)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaRecordsByVernacular"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaRecordsByVernacular"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaRecordsByVernacular"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * &lt;strong&gt;Get one or more Aphia Records (max. 50) for a given vernacular.&lt;/strong&gt;&lt;br/&gt;Parameters:
   &lt;ul&gt;
    &lt;li&gt;&lt;u&gt;like&lt;/u&gt;: add a '%'-sign before and after the input (SQL LIKE '%vernacular%' function). Default=false.&lt;/li&gt;
	&lt;li&gt;&lt;u&gt;offset&lt;/u&gt;: starting record number, when retrieving next chunck of (50) records. Default=1.&lt;/li&gt;
   &lt;/ul&gt;
  &lt;/strong&gt;
  &lt;br /&gt; For the structure of the returned AphiaRecords, please refer to the function getAphiaRecordByID()
                * @see uk.ac.ebi.metabolights.referencelayer.worms.client.AphiaNameService#startgetAphiaRecordsByVernacular
                    * @param getAphiaRecordsByVernacular2
                
                */
                public  void startgetAphiaRecordsByVernacular(

                 aphia.v1_0.aphiarecords.GetAphiaRecordsByVernacularDocument getAphiaRecordsByVernacular2,

                  final uk.ac.ebi.metabolights.referencelayer.worms.client.AphiaNameServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[1].getName());
             _operationClient.getOptions().setAction("getAphiaRecordsByVernacular");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getAphiaRecordsByVernacular2,
                                                    optimizeContent(new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getAphiaRecordsByVernacular")), new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getAphiaRecordsByVernacular"));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         aphia.v1_0.aphiarecords.GetAphiaRecordsByVernacularResponseDocument.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultgetAphiaRecordsByVernacular(
                                        (aphia.v1_0.aphiarecords.GetAphiaRecordsByVernacularResponseDocument)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorgetAphiaRecordsByVernacular(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaRecordsByVernacular"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaRecordsByVernacular"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                                                    java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaRecordsByVernacular"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
					
										            callback.receiveErrorgetAphiaRecordsByVernacular(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaRecordsByVernacular(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaRecordsByVernacular(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaRecordsByVernacular(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaRecordsByVernacular(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaRecordsByVernacular(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaRecordsByVernacular(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaRecordsByVernacular(f);
                                            }
									    } else {
										    callback.receiveErrorgetAphiaRecordsByVernacular(f);
									    }
									} else {
									    callback.receiveErrorgetAphiaRecordsByVernacular(f);
									}
								} else {
								    callback.receiveErrorgetAphiaRecordsByVernacular(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrorgetAphiaRecordsByVernacular(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[1].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[1].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                
                    /**
                     * Auto generated method signature
                     * &lt;strong&gt;Get the direct children (max. 50) for a given AphiaID.&lt;/strong&gt;&lt;br /&gt;Parameters:
   &lt;ul&gt;
	&lt;li&gt;&lt;u&gt;offset&lt;/u&gt;: starting record number, when retrieving next chunck of (50) records. Default=1.&lt;/li&gt;
	&lt;li&gt;&lt;u&gt;marine_only&lt;/u&gt;: limit to marine taxa. Default=true.&lt;/li&gt;
   &lt;/ul&gt;
                     * @see uk.ac.ebi.metabolights.referencelayer.worms.client.AphiaNameService#getAphiaChildrenByID
                     * @param getAphiaChildrenByID4
                    
                     */

                    

                            public  aphia.v1_0.aphiarecords.GetAphiaChildrenByIDResponseDocument getAphiaChildrenByID(

                            aphia.v1_0.aphiarecords.GetAphiaChildrenByIDDocument getAphiaChildrenByID4)
                        

                    throws java.rmi.RemoteException
                    
                    {
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[2].getName());
              _operationClient.getOptions().setAction("getAphiaChildrenByID");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getAphiaChildrenByID4,
                                                    optimizeContent(new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getAphiaChildrenByID")), new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getAphiaChildrenByID"));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             aphia.v1_0.aphiarecords.GetAphiaChildrenByIDResponseDocument.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (aphia.v1_0.aphiarecords.GetAphiaChildrenByIDResponseDocument)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaChildrenByID"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaChildrenByID"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaChildrenByID"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * &lt;strong&gt;Get the direct children (max. 50) for a given AphiaID.&lt;/strong&gt;&lt;br /&gt;Parameters:
   &lt;ul&gt;
	&lt;li&gt;&lt;u&gt;offset&lt;/u&gt;: starting record number, when retrieving next chunck of (50) records. Default=1.&lt;/li&gt;
	&lt;li&gt;&lt;u&gt;marine_only&lt;/u&gt;: limit to marine taxa. Default=true.&lt;/li&gt;
   &lt;/ul&gt;
                * @see uk.ac.ebi.metabolights.referencelayer.worms.client.AphiaNameService#startgetAphiaChildrenByID
                    * @param getAphiaChildrenByID4
                
                */
                public  void startgetAphiaChildrenByID(

                 aphia.v1_0.aphiarecords.GetAphiaChildrenByIDDocument getAphiaChildrenByID4,

                  final uk.ac.ebi.metabolights.referencelayer.worms.client.AphiaNameServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[2].getName());
             _operationClient.getOptions().setAction("getAphiaChildrenByID");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getAphiaChildrenByID4,
                                                    optimizeContent(new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getAphiaChildrenByID")), new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getAphiaChildrenByID"));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         aphia.v1_0.aphiarecords.GetAphiaChildrenByIDResponseDocument.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultgetAphiaChildrenByID(
                                        (aphia.v1_0.aphiarecords.GetAphiaChildrenByIDResponseDocument)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorgetAphiaChildrenByID(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaChildrenByID"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaChildrenByID"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                                                    java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaChildrenByID"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
					
										            callback.receiveErrorgetAphiaChildrenByID(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaChildrenByID(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaChildrenByID(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaChildrenByID(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaChildrenByID(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaChildrenByID(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaChildrenByID(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaChildrenByID(f);
                                            }
									    } else {
										    callback.receiveErrorgetAphiaChildrenByID(f);
									    }
									} else {
									    callback.receiveErrorgetAphiaChildrenByID(f);
									}
								} else {
								    callback.receiveErrorgetAphiaChildrenByID(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrorgetAphiaChildrenByID(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[2].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[2].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                
                    /**
                     * Auto generated method signature
                     * &lt;strong&gt;Get the complete classification for one taxon. This also includes any sub or super ranks.&lt;/strong&gt;
                     * @see uk.ac.ebi.metabolights.referencelayer.worms.client.AphiaNameService#getAphiaClassificationByID
                     * @param getAphiaClassificationByID6
                    
                     */

                    

                            public  aphia.v1_0.classification.GetAphiaClassificationByIDResponseDocument getAphiaClassificationByID(

                            aphia.v1_0.classification.GetAphiaClassificationByIDDocument getAphiaClassificationByID6)
                        

                    throws java.rmi.RemoteException
                    
                    {
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[3].getName());
              _operationClient.getOptions().setAction("getAphiaClassificationByID");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getAphiaClassificationByID6,
                                                    optimizeContent(new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getAphiaClassificationByID")), new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getAphiaClassificationByID"));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             aphia.v1_0.classification.GetAphiaClassificationByIDResponseDocument.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (aphia.v1_0.classification.GetAphiaClassificationByIDResponseDocument)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaClassificationByID"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaClassificationByID"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaClassificationByID"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * &lt;strong&gt;Get the complete classification for one taxon. This also includes any sub or super ranks.&lt;/strong&gt;
                * @see uk.ac.ebi.metabolights.referencelayer.worms.client.AphiaNameService#startgetAphiaClassificationByID
                    * @param getAphiaClassificationByID6
                
                */
                public  void startgetAphiaClassificationByID(

                 aphia.v1_0.classification.GetAphiaClassificationByIDDocument getAphiaClassificationByID6,

                  final uk.ac.ebi.metabolights.referencelayer.worms.client.AphiaNameServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[3].getName());
             _operationClient.getOptions().setAction("getAphiaClassificationByID");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getAphiaClassificationByID6,
                                                    optimizeContent(new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getAphiaClassificationByID")), new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getAphiaClassificationByID"));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         aphia.v1_0.classification.GetAphiaClassificationByIDResponseDocument.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultgetAphiaClassificationByID(
                                        (aphia.v1_0.classification.GetAphiaClassificationByIDResponseDocument)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorgetAphiaClassificationByID(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaClassificationByID"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaClassificationByID"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                                                    java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaClassificationByID"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
					
										            callback.receiveErrorgetAphiaClassificationByID(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaClassificationByID(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaClassificationByID(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaClassificationByID(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaClassificationByID(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaClassificationByID(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaClassificationByID(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaClassificationByID(f);
                                            }
									    } else {
										    callback.receiveErrorgetAphiaClassificationByID(f);
									    }
									} else {
									    callback.receiveErrorgetAphiaClassificationByID(f);
									}
								} else {
								    callback.receiveErrorgetAphiaClassificationByID(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrorgetAphiaClassificationByID(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[3].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[3].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                
                    /**
                     * Auto generated method signature
                     * &lt;strong&gt;For each given scientific name (may include authority), try to find one or more AphiaRecords, using the TAXAMATCH fuzzy matching algorithm by Tony Rees.&lt;br/&gt;
 This allows you to (fuzzy) match multiple names in one call. Limited to 50 names at once for performance reasons.
 &lt;br/&gt;Parameters:
  &lt;ul&gt;
	&lt;li&gt;&lt;u&gt;marine_only&lt;/u&gt;: limit to marine taxa. Default=true.&lt;/li&gt;
  &lt;/ul&gt;&lt;/strong&gt;
  &lt;br /&gt; For the structure of the returned AphiaRecords, please refer to the function getAphiaRecordByID()
                     * @see uk.ac.ebi.metabolights.referencelayer.worms.client.AphiaNameService#matchAphiaRecordsByNames
                     * @param matchAphiaRecordsByNames8
                    
                     */

                    

                            public  aphia.v1_0.aphiamatches.MatchAphiaRecordsByNamesResponseDocument matchAphiaRecordsByNames(

                            aphia.v1_0.aphiamatches.MatchAphiaRecordsByNamesDocument matchAphiaRecordsByNames8)
                        

                    throws java.rmi.RemoteException
                    
                    {
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[4].getName());
              _operationClient.getOptions().setAction("matchAphiaRecordsByNames");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    matchAphiaRecordsByNames8,
                                                    optimizeContent(new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "matchAphiaRecordsByNames")), new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "matchAphiaRecordsByNames"));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             aphia.v1_0.aphiamatches.MatchAphiaRecordsByNamesResponseDocument.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (aphia.v1_0.aphiamatches.MatchAphiaRecordsByNamesResponseDocument)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"matchAphiaRecordsByNames"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"matchAphiaRecordsByNames"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"matchAphiaRecordsByNames"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * &lt;strong&gt;For each given scientific name (may include authority), try to find one or more AphiaRecords, using the TAXAMATCH fuzzy matching algorithm by Tony Rees.&lt;br/&gt;
 This allows you to (fuzzy) match multiple names in one call. Limited to 50 names at once for performance reasons.
 &lt;br/&gt;Parameters:
  &lt;ul&gt;
	&lt;li&gt;&lt;u&gt;marine_only&lt;/u&gt;: limit to marine taxa. Default=true.&lt;/li&gt;
  &lt;/ul&gt;&lt;/strong&gt;
  &lt;br /&gt; For the structure of the returned AphiaRecords, please refer to the function getAphiaRecordByID()
                * @see uk.ac.ebi.metabolights.referencelayer.worms.client.AphiaNameService#startmatchAphiaRecordsByNames
                    * @param matchAphiaRecordsByNames8
                
                */
                public  void startmatchAphiaRecordsByNames(

                 aphia.v1_0.aphiamatches.MatchAphiaRecordsByNamesDocument matchAphiaRecordsByNames8,

                  final uk.ac.ebi.metabolights.referencelayer.worms.client.AphiaNameServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[4].getName());
             _operationClient.getOptions().setAction("matchAphiaRecordsByNames");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    matchAphiaRecordsByNames8,
                                                    optimizeContent(new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "matchAphiaRecordsByNames")), new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "matchAphiaRecordsByNames"));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         aphia.v1_0.aphiamatches.MatchAphiaRecordsByNamesResponseDocument.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultmatchAphiaRecordsByNames(
                                        (aphia.v1_0.aphiamatches.MatchAphiaRecordsByNamesResponseDocument)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrormatchAphiaRecordsByNames(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"matchAphiaRecordsByNames"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"matchAphiaRecordsByNames"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                                                    java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"matchAphiaRecordsByNames"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
					
										            callback.receiveErrormatchAphiaRecordsByNames(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrormatchAphiaRecordsByNames(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrormatchAphiaRecordsByNames(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrormatchAphiaRecordsByNames(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrormatchAphiaRecordsByNames(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrormatchAphiaRecordsByNames(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrormatchAphiaRecordsByNames(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrormatchAphiaRecordsByNames(f);
                                            }
									    } else {
										    callback.receiveErrormatchAphiaRecordsByNames(f);
									    }
									} else {
									    callback.receiveErrormatchAphiaRecordsByNames(f);
									}
								} else {
								    callback.receiveErrormatchAphiaRecordsByNames(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrormatchAphiaRecordsByNames(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[4].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[4].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                
                    /**
                     * Auto generated method signature
                     * &lt;strong&gt;Lists all AphiaRecords (taxa) modified or added between a specific time interval.&lt;br/&gt;
 &lt;br/&gt;Parameters:
  &lt;ul&gt;
   &lt;li&gt;&lt;u&gt;startdate&lt;/u&gt;: ISO 8601 formatted start date(time). Default=today(). i.e. 2013-11-21T09:33:50+00:00&lt;/li&gt;
   &lt;li&gt;&lt;u&gt;enddate&lt;/u&gt;: ISO 8601 formatted end date(time). Default=today().i.e. 2013-11-21T09:33:50+00:00&lt;/li&gt;
   &lt;li&gt;&lt;u&gt;marine_only&lt;/u&gt;: limit to marine taxa. Default=true.&lt;/li&gt;
   &lt;li&gt;&lt;u&gt;offset&lt;/u&gt;: starting record number, when retrieving next chunck of (50) records. Default=1.&lt;/li&gt;
  &lt;/ul&gt;&lt;/strong&gt;
  &lt;br /&gt; For the structure of the returned AphiaRecords, please refer to the function getAphiaRecordByID()
                     * @see uk.ac.ebi.metabolights.referencelayer.worms.client.AphiaNameService#getAphiaRecordsByDate
                     * @param getAphiaRecordsByDate10
                    
                     */

                    

                            public  aphia.v1_0.aphiarecords.GetAphiaRecordsByDateResponseDocument getAphiaRecordsByDate(

                            aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument getAphiaRecordsByDate10)
                        

                    throws java.rmi.RemoteException
                    
                    {
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[5].getName());
              _operationClient.getOptions().setAction("getAphiaRecordsByDate");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getAphiaRecordsByDate10,
                                                    optimizeContent(new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getAphiaRecordsByDate")), new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getAphiaRecordsByDate"));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             aphia.v1_0.aphiarecords.GetAphiaRecordsByDateResponseDocument.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (aphia.v1_0.aphiarecords.GetAphiaRecordsByDateResponseDocument)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaRecordsByDate"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaRecordsByDate"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaRecordsByDate"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * &lt;strong&gt;Lists all AphiaRecords (taxa) modified or added between a specific time interval.&lt;br/&gt;
 &lt;br/&gt;Parameters:
  &lt;ul&gt;
   &lt;li&gt;&lt;u&gt;startdate&lt;/u&gt;: ISO 8601 formatted start date(time). Default=today(). i.e. 2013-11-21T09:33:50+00:00&lt;/li&gt;
   &lt;li&gt;&lt;u&gt;enddate&lt;/u&gt;: ISO 8601 formatted end date(time). Default=today().i.e. 2013-11-21T09:33:50+00:00&lt;/li&gt;
   &lt;li&gt;&lt;u&gt;marine_only&lt;/u&gt;: limit to marine taxa. Default=true.&lt;/li&gt;
   &lt;li&gt;&lt;u&gt;offset&lt;/u&gt;: starting record number, when retrieving next chunck of (50) records. Default=1.&lt;/li&gt;
  &lt;/ul&gt;&lt;/strong&gt;
  &lt;br /&gt; For the structure of the returned AphiaRecords, please refer to the function getAphiaRecordByID()
                * @see uk.ac.ebi.metabolights.referencelayer.worms.client.AphiaNameService#startgetAphiaRecordsByDate
                    * @param getAphiaRecordsByDate10
                
                */
                public  void startgetAphiaRecordsByDate(

                 aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument getAphiaRecordsByDate10,

                  final uk.ac.ebi.metabolights.referencelayer.worms.client.AphiaNameServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[5].getName());
             _operationClient.getOptions().setAction("getAphiaRecordsByDate");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getAphiaRecordsByDate10,
                                                    optimizeContent(new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getAphiaRecordsByDate")), new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getAphiaRecordsByDate"));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         aphia.v1_0.aphiarecords.GetAphiaRecordsByDateResponseDocument.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultgetAphiaRecordsByDate(
                                        (aphia.v1_0.aphiarecords.GetAphiaRecordsByDateResponseDocument)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorgetAphiaRecordsByDate(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaRecordsByDate"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaRecordsByDate"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                                                    java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaRecordsByDate"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
					
										            callback.receiveErrorgetAphiaRecordsByDate(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaRecordsByDate(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaRecordsByDate(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaRecordsByDate(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaRecordsByDate(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaRecordsByDate(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaRecordsByDate(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaRecordsByDate(f);
                                            }
									    } else {
										    callback.receiveErrorgetAphiaRecordsByDate(f);
									    }
									} else {
									    callback.receiveErrorgetAphiaRecordsByDate(f);
									}
								} else {
								    callback.receiveErrorgetAphiaRecordsByDate(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrorgetAphiaRecordsByDate(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[5].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[5].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                
                    /**
                     * Auto generated method signature
                     * &lt;strong&gt;Get all synonyms for a given AphiaID.&lt;/strong&gt;
                     * @see uk.ac.ebi.metabolights.referencelayer.worms.client.AphiaNameService#getAphiaSynonymsByID
                     * @param getAphiaSynonymsByID12
                    
                     */

                    

                            public  aphia.v1_0.aphiarecords.GetAphiaSynonymsByIDResponseDocument getAphiaSynonymsByID(

                            aphia.v1_0.aphiarecords.GetAphiaSynonymsByIDDocument getAphiaSynonymsByID12)
                        

                    throws java.rmi.RemoteException
                    
                    {
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[6].getName());
              _operationClient.getOptions().setAction("getAphiaSynonymsByID");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getAphiaSynonymsByID12,
                                                    optimizeContent(new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getAphiaSynonymsByID")), new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getAphiaSynonymsByID"));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             aphia.v1_0.aphiarecords.GetAphiaSynonymsByIDResponseDocument.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (aphia.v1_0.aphiarecords.GetAphiaSynonymsByIDResponseDocument)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaSynonymsByID"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaSynonymsByID"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaSynonymsByID"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * &lt;strong&gt;Get all synonyms for a given AphiaID.&lt;/strong&gt;
                * @see uk.ac.ebi.metabolights.referencelayer.worms.client.AphiaNameService#startgetAphiaSynonymsByID
                    * @param getAphiaSynonymsByID12
                
                */
                public  void startgetAphiaSynonymsByID(

                 aphia.v1_0.aphiarecords.GetAphiaSynonymsByIDDocument getAphiaSynonymsByID12,

                  final uk.ac.ebi.metabolights.referencelayer.worms.client.AphiaNameServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[6].getName());
             _operationClient.getOptions().setAction("getAphiaSynonymsByID");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getAphiaSynonymsByID12,
                                                    optimizeContent(new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getAphiaSynonymsByID")), new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getAphiaSynonymsByID"));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         aphia.v1_0.aphiarecords.GetAphiaSynonymsByIDResponseDocument.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultgetAphiaSynonymsByID(
                                        (aphia.v1_0.aphiarecords.GetAphiaSynonymsByIDResponseDocument)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorgetAphiaSynonymsByID(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaSynonymsByID"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaSynonymsByID"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                                                    java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaSynonymsByID"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
					
										            callback.receiveErrorgetAphiaSynonymsByID(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaSynonymsByID(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaSynonymsByID(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaSynonymsByID(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaSynonymsByID(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaSynonymsByID(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaSynonymsByID(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaSynonymsByID(f);
                                            }
									    } else {
										    callback.receiveErrorgetAphiaSynonymsByID(f);
									    }
									} else {
									    callback.receiveErrorgetAphiaSynonymsByID(f);
									}
								} else {
								    callback.receiveErrorgetAphiaSynonymsByID(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrorgetAphiaSynonymsByID(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[6].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[6].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                
                    /**
                     * Auto generated method signature
                     * &lt;strong&gt;Get one or more matching (max. 50) AphiaRecords for a given name.&lt;br/&gt;Parameters:
   &lt;ul&gt;
    &lt;li&gt;&lt;u&gt;like&lt;/u&gt;: add a '%'-sign added after the ScientificName (SQL LIKE function). Default=true.&lt;/li&gt;
	&lt;li&gt;&lt;u&gt;fuzzy&lt;/u&gt;: this parameter is deprecated (and ignored since 2013-07-17). Please use the function matchAphiaRecordsByNames() for fuzzy/near matching.&lt;/li&gt;
	&lt;li&gt;&lt;u&gt;marine_only&lt;/u&gt;: limit to marine taxa. Default=true.&lt;/li&gt;
	&lt;li&gt;&lt;u&gt;offset&lt;/u&gt;: starting recordnumber, when retrieving next chunck of (50) records. Default=1.&lt;/li&gt;
   &lt;/ul&gt;
  &lt;/strong&gt;&lt;br /&gt; For the structure of the returned AphiaRecords, please refer to the function getAphiaRecordByID()
                     * @see uk.ac.ebi.metabolights.referencelayer.worms.client.AphiaNameService#getAphiaRecords
                     * @param getAphiaRecords14
                    
                     */

                    

                            public  aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument getAphiaRecords(

                            aphia.v1_0.aphiarecords.GetAphiaRecordsDocument getAphiaRecords14)
                        

                    throws java.rmi.RemoteException
                    
                    {
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[7].getName());
              _operationClient.getOptions().setAction("getAphiaRecords");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getAphiaRecords14,
                                                    optimizeContent(new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getAphiaRecords")), new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getAphiaRecords"));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaRecords"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaRecords"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaRecords"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * &lt;strong&gt;Get one or more matching (max. 50) AphiaRecords for a given name.&lt;br/&gt;Parameters:
   &lt;ul&gt;
    &lt;li&gt;&lt;u&gt;like&lt;/u&gt;: add a '%'-sign added after the ScientificName (SQL LIKE function). Default=true.&lt;/li&gt;
	&lt;li&gt;&lt;u&gt;fuzzy&lt;/u&gt;: this parameter is deprecated (and ignored since 2013-07-17). Please use the function matchAphiaRecordsByNames() for fuzzy/near matching.&lt;/li&gt;
	&lt;li&gt;&lt;u&gt;marine_only&lt;/u&gt;: limit to marine taxa. Default=true.&lt;/li&gt;
	&lt;li&gt;&lt;u&gt;offset&lt;/u&gt;: starting recordnumber, when retrieving next chunck of (50) records. Default=1.&lt;/li&gt;
   &lt;/ul&gt;
  &lt;/strong&gt;&lt;br /&gt; For the structure of the returned AphiaRecords, please refer to the function getAphiaRecordByID()
                * @see uk.ac.ebi.metabolights.referencelayer.worms.client.AphiaNameService#startgetAphiaRecords
                    * @param getAphiaRecords14
                
                */
                public  void startgetAphiaRecords(

                 aphia.v1_0.aphiarecords.GetAphiaRecordsDocument getAphiaRecords14,

                  final uk.ac.ebi.metabolights.referencelayer.worms.client.AphiaNameServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[7].getName());
             _operationClient.getOptions().setAction("getAphiaRecords");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getAphiaRecords14,
                                                    optimizeContent(new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getAphiaRecords")), new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getAphiaRecords"));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultgetAphiaRecords(
                                        (aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorgetAphiaRecords(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaRecords"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaRecords"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                                                    java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaRecords"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
					
										            callback.receiveErrorgetAphiaRecords(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaRecords(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaRecords(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaRecords(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaRecords(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaRecords(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaRecords(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaRecords(f);
                                            }
									    } else {
										    callback.receiveErrorgetAphiaRecords(f);
									    }
									} else {
									    callback.receiveErrorgetAphiaRecords(f);
									}
								} else {
								    callback.receiveErrorgetAphiaRecords(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrorgetAphiaRecords(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[7].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[7].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                
                    /**
                     * Auto generated method signature
                     * &lt;strong&gt;Get the complete AphiaRecord for a given AphiaID.&lt;/strong&gt;&lt;br /&gt;
 The returned AphiaRecord has this format:&lt;br /&gt;&lt;ul&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;AphiaID&lt;/b&gt;&lt;/u&gt;: unique and persistent identifier within WoRMS. Primary key in the database.&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;url&lt;/b&gt;&lt;/u&gt;: HTTP URL to the AphiaRecord&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;scientificname&lt;/b&gt;&lt;/u&gt;: the full scientific name without authorship&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;authority&lt;/b&gt;&lt;/u&gt;: the authorship information for the scientificname formatted according to the conventions of the applicable nomenclaturalCode&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;rank&lt;/b&gt;&lt;/u&gt;: the taxonomic rank of the most specific name in the scientificname&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;status&lt;/b&gt;&lt;/u&gt;: the status of the use of the scientificname as a label for a taxon. Requires taxonomic opinion to define the scope of a taxon&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;unacceptreason&lt;/b&gt;&lt;/u&gt;: the reason why a scientificname is unaccepted&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;valid_AphiaID&lt;/b&gt;&lt;/u&gt;: the AphiaID (for the scientificname) of the currently accepted taxon&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;valid_name&lt;/b&gt;&lt;/u&gt;: the scientificname of the currently accepted taxon&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;valid_authority&lt;/b&gt;&lt;/u&gt;: the authorship information for the scientificname of the currently accepted taxon&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;kingdom&lt;/b&gt;&lt;/u&gt;: the full scientific name of the kingdom in which the taxon is classified&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;phylum&lt;/b&gt;&lt;/u&gt;: the full scientific name of the phylum or division in which the taxon is classified&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;class&lt;/b&gt;&lt;/u&gt;: the full scientific name of the class in which the taxon is classified&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;order&lt;/b&gt;&lt;/u&gt;: the full scientific name of the order in which the taxon is classified&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;family&lt;/b&gt;&lt;/u&gt;: the full scientific name of the family in which the taxon is classified&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;genus&lt;/b&gt;&lt;/u&gt;: the full scientific name of the genus in which the taxon is classified&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;citation&lt;/b&gt;&lt;/u&gt;: a bibliographic reference for the resource as a statement indicating how this record should be cited (attributed) when used&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;lsid&lt;/b&gt;&lt;/u&gt;: LifeScience Identifier. Persistent GUID for an AphiaID&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;isMarine&lt;/b&gt;&lt;/u&gt;: a boolean flag indicating whether the taxon is a marine organism, i.e. can be found in/above sea water. Possible values: 0/1/NULL&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;isBrackish&lt;/b&gt;&lt;/u&gt;: a boolean flag indicating whether the taxon occurrs in brackish habitats. Possible values: 0/1/NULL&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;isFreshwater&lt;/b&gt;&lt;/u&gt;: a boolean flag indicating whether the taxon occurrs in freshwater habitats, i.e. can be found in/above rivers or lakes. Possible values: 0/1/NULL&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;isTerrestrial&lt;/b&gt;&lt;/u&gt;: a boolean flag indicating the taxon is a terrestial organism, i.e. occurrs on land as opposed to the sea. Possible values: 0/1/NULL&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;isExtinct&lt;/b&gt;&lt;/u&gt;: a flag indicating an extinct organism. Possible values: 0/1/NUL&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;match_type&lt;/b&gt;&lt;/u&gt;: Type of match. Possible values: exact/like/phonetic/near_1/near_2&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;modified&lt;/b&gt;&lt;/u&gt;: The most recent date-time in GMT on which the resource was changed&lt;/li&gt;&lt;/ul&gt;
                     * @see uk.ac.ebi.metabolights.referencelayer.worms.client.AphiaNameService#getAphiaRecordByID
                     * @param getAphiaRecordByID16
                    
                     */

                    

                            public  aphia.v1_0.aphiarecord.GetAphiaRecordByIDResponseDocument getAphiaRecordByID(

                            aphia.v1_0.aphiarecord.GetAphiaRecordByIDDocument getAphiaRecordByID16)
                        

                    throws java.rmi.RemoteException
                    
                    {
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[8].getName());
              _operationClient.getOptions().setAction("getAphiaRecordByID");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getAphiaRecordByID16,
                                                    optimizeContent(new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getAphiaRecordByID")), new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getAphiaRecordByID"));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             aphia.v1_0.aphiarecord.GetAphiaRecordByIDResponseDocument.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (aphia.v1_0.aphiarecord.GetAphiaRecordByIDResponseDocument)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaRecordByID"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaRecordByID"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaRecordByID"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * &lt;strong&gt;Get the complete AphiaRecord for a given AphiaID.&lt;/strong&gt;&lt;br /&gt;
 The returned AphiaRecord has this format:&lt;br /&gt;&lt;ul&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;AphiaID&lt;/b&gt;&lt;/u&gt;: unique and persistent identifier within WoRMS. Primary key in the database.&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;url&lt;/b&gt;&lt;/u&gt;: HTTP URL to the AphiaRecord&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;scientificname&lt;/b&gt;&lt;/u&gt;: the full scientific name without authorship&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;authority&lt;/b&gt;&lt;/u&gt;: the authorship information for the scientificname formatted according to the conventions of the applicable nomenclaturalCode&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;rank&lt;/b&gt;&lt;/u&gt;: the taxonomic rank of the most specific name in the scientificname&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;status&lt;/b&gt;&lt;/u&gt;: the status of the use of the scientificname as a label for a taxon. Requires taxonomic opinion to define the scope of a taxon&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;unacceptreason&lt;/b&gt;&lt;/u&gt;: the reason why a scientificname is unaccepted&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;valid_AphiaID&lt;/b&gt;&lt;/u&gt;: the AphiaID (for the scientificname) of the currently accepted taxon&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;valid_name&lt;/b&gt;&lt;/u&gt;: the scientificname of the currently accepted taxon&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;valid_authority&lt;/b&gt;&lt;/u&gt;: the authorship information for the scientificname of the currently accepted taxon&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;kingdom&lt;/b&gt;&lt;/u&gt;: the full scientific name of the kingdom in which the taxon is classified&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;phylum&lt;/b&gt;&lt;/u&gt;: the full scientific name of the phylum or division in which the taxon is classified&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;class&lt;/b&gt;&lt;/u&gt;: the full scientific name of the class in which the taxon is classified&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;order&lt;/b&gt;&lt;/u&gt;: the full scientific name of the order in which the taxon is classified&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;family&lt;/b&gt;&lt;/u&gt;: the full scientific name of the family in which the taxon is classified&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;genus&lt;/b&gt;&lt;/u&gt;: the full scientific name of the genus in which the taxon is classified&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;citation&lt;/b&gt;&lt;/u&gt;: a bibliographic reference for the resource as a statement indicating how this record should be cited (attributed) when used&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;lsid&lt;/b&gt;&lt;/u&gt;: LifeScience Identifier. Persistent GUID for an AphiaID&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;isMarine&lt;/b&gt;&lt;/u&gt;: a boolean flag indicating whether the taxon is a marine organism, i.e. can be found in/above sea water. Possible values: 0/1/NULL&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;isBrackish&lt;/b&gt;&lt;/u&gt;: a boolean flag indicating whether the taxon occurrs in brackish habitats. Possible values: 0/1/NULL&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;isFreshwater&lt;/b&gt;&lt;/u&gt;: a boolean flag indicating whether the taxon occurrs in freshwater habitats, i.e. can be found in/above rivers or lakes. Possible values: 0/1/NULL&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;isTerrestrial&lt;/b&gt;&lt;/u&gt;: a boolean flag indicating the taxon is a terrestial organism, i.e. occurrs on land as opposed to the sea. Possible values: 0/1/NULL&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;isExtinct&lt;/b&gt;&lt;/u&gt;: a flag indicating an extinct organism. Possible values: 0/1/NUL&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;match_type&lt;/b&gt;&lt;/u&gt;: Type of match. Possible values: exact/like/phonetic/near_1/near_2&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;modified&lt;/b&gt;&lt;/u&gt;: The most recent date-time in GMT on which the resource was changed&lt;/li&gt;&lt;/ul&gt;
                * @see uk.ac.ebi.metabolights.referencelayer.worms.client.AphiaNameService#startgetAphiaRecordByID
                    * @param getAphiaRecordByID16
                
                */
                public  void startgetAphiaRecordByID(

                 aphia.v1_0.aphiarecord.GetAphiaRecordByIDDocument getAphiaRecordByID16,

                  final uk.ac.ebi.metabolights.referencelayer.worms.client.AphiaNameServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[8].getName());
             _operationClient.getOptions().setAction("getAphiaRecordByID");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getAphiaRecordByID16,
                                                    optimizeContent(new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getAphiaRecordByID")), new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getAphiaRecordByID"));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         aphia.v1_0.aphiarecord.GetAphiaRecordByIDResponseDocument.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultgetAphiaRecordByID(
                                        (aphia.v1_0.aphiarecord.GetAphiaRecordByIDResponseDocument)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorgetAphiaRecordByID(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaRecordByID"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaRecordByID"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                                                    java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaRecordByID"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
					
										            callback.receiveErrorgetAphiaRecordByID(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaRecordByID(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaRecordByID(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaRecordByID(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaRecordByID(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaRecordByID(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaRecordByID(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaRecordByID(f);
                                            }
									    } else {
										    callback.receiveErrorgetAphiaRecordByID(f);
									    }
									} else {
									    callback.receiveErrorgetAphiaRecordByID(f);
									}
								} else {
								    callback.receiveErrorgetAphiaRecordByID(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrorgetAphiaRecordByID(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[8].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[8].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                
                    /**
                     * Auto generated method signature
                     * &lt;strong&gt;Get one or more sources/references including links, for one AphiaID&lt;/strong&gt;
                     * @see uk.ac.ebi.metabolights.referencelayer.worms.client.AphiaNameService#getSourcesByAphiaID
                     * @param getSourcesByAphiaID18
                    
                     */

                    

                            public  aphia.v1_0.sources.GetSourcesByAphiaIDResponseDocument getSourcesByAphiaID(

                            aphia.v1_0.sources.GetSourcesByAphiaIDDocument getSourcesByAphiaID18)
                        

                    throws java.rmi.RemoteException
                    
                    {
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[9].getName());
              _operationClient.getOptions().setAction("getSourcesByAphiaID");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getSourcesByAphiaID18,
                                                    optimizeContent(new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getSourcesByAphiaID")), new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getSourcesByAphiaID"));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             aphia.v1_0.sources.GetSourcesByAphiaIDResponseDocument.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (aphia.v1_0.sources.GetSourcesByAphiaIDResponseDocument)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getSourcesByAphiaID"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getSourcesByAphiaID"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getSourcesByAphiaID"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * &lt;strong&gt;Get one or more sources/references including links, for one AphiaID&lt;/strong&gt;
                * @see uk.ac.ebi.metabolights.referencelayer.worms.client.AphiaNameService#startgetSourcesByAphiaID
                    * @param getSourcesByAphiaID18
                
                */
                public  void startgetSourcesByAphiaID(

                 aphia.v1_0.sources.GetSourcesByAphiaIDDocument getSourcesByAphiaID18,

                  final uk.ac.ebi.metabolights.referencelayer.worms.client.AphiaNameServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[9].getName());
             _operationClient.getOptions().setAction("getSourcesByAphiaID");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getSourcesByAphiaID18,
                                                    optimizeContent(new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getSourcesByAphiaID")), new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getSourcesByAphiaID"));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         aphia.v1_0.sources.GetSourcesByAphiaIDResponseDocument.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultgetSourcesByAphiaID(
                                        (aphia.v1_0.sources.GetSourcesByAphiaIDResponseDocument)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorgetSourcesByAphiaID(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getSourcesByAphiaID"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getSourcesByAphiaID"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                                                    java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getSourcesByAphiaID"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
					
										            callback.receiveErrorgetSourcesByAphiaID(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetSourcesByAphiaID(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetSourcesByAphiaID(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetSourcesByAphiaID(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetSourcesByAphiaID(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetSourcesByAphiaID(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetSourcesByAphiaID(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetSourcesByAphiaID(f);
                                            }
									    } else {
										    callback.receiveErrorgetSourcesByAphiaID(f);
									    }
									} else {
									    callback.receiveErrorgetSourcesByAphiaID(f);
									}
								} else {
								    callback.receiveErrorgetSourcesByAphiaID(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrorgetSourcesByAphiaID(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[9].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[9].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                
                    /**
                     * Auto generated method signature
                     * &lt;strong&gt;Get the correct name for a given AphiaID&lt;/strong&gt;.
                     * @see uk.ac.ebi.metabolights.referencelayer.worms.client.AphiaNameService#getAphiaNameByID
                     * @param getAphiaNameByID20
                    
                     */

                    

                            public  aphia.v1_0.aphianame.GetAphiaNameByIDResponseDocument getAphiaNameByID(

                            aphia.v1_0.aphianame.GetAphiaNameByIDDocument getAphiaNameByID20)
                        

                    throws java.rmi.RemoteException
                    
                    {
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[10].getName());
              _operationClient.getOptions().setAction("getAphiaNameByID");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getAphiaNameByID20,
                                                    optimizeContent(new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getAphiaNameByID")), new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getAphiaNameByID"));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             aphia.v1_0.aphianame.GetAphiaNameByIDResponseDocument.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (aphia.v1_0.aphianame.GetAphiaNameByIDResponseDocument)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaNameByID"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaNameByID"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaNameByID"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * &lt;strong&gt;Get the correct name for a given AphiaID&lt;/strong&gt;.
                * @see uk.ac.ebi.metabolights.referencelayer.worms.client.AphiaNameService#startgetAphiaNameByID
                    * @param getAphiaNameByID20
                
                */
                public  void startgetAphiaNameByID(

                 aphia.v1_0.aphianame.GetAphiaNameByIDDocument getAphiaNameByID20,

                  final uk.ac.ebi.metabolights.referencelayer.worms.client.AphiaNameServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[10].getName());
             _operationClient.getOptions().setAction("getAphiaNameByID");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getAphiaNameByID20,
                                                    optimizeContent(new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getAphiaNameByID")), new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getAphiaNameByID"));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         aphia.v1_0.aphianame.GetAphiaNameByIDResponseDocument.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultgetAphiaNameByID(
                                        (aphia.v1_0.aphianame.GetAphiaNameByIDResponseDocument)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorgetAphiaNameByID(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaNameByID"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaNameByID"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                                                    java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaNameByID"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
					
										            callback.receiveErrorgetAphiaNameByID(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaNameByID(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaNameByID(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaNameByID(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaNameByID(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaNameByID(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaNameByID(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaNameByID(f);
                                            }
									    } else {
										    callback.receiveErrorgetAphiaNameByID(f);
									    }
									} else {
									    callback.receiveErrorgetAphiaNameByID(f);
									}
								} else {
								    callback.receiveErrorgetAphiaNameByID(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrorgetAphiaNameByID(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[10].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[10].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                
                    /**
                     * Auto generated method signature
                     * &lt;strong&gt;For each given scientific name, try to find one or more AphiaRecords.&lt;br/&gt;
  This allows you to match multiple names in one call. Limited to 500 names at once for performance reasons.
  &lt;br/&gt;Parameters:
   &lt;ul&gt;
    &lt;li&gt;&lt;u&gt;like&lt;/u&gt;: add a '%'-sign after the ScientificName (SQL LIKE function). Default=false.&lt;/li&gt;
	&lt;li&gt;&lt;u&gt;fuzzy&lt;/u&gt;: this parameter is deprecated (and ignored since 2013-07-17). Please use the function matchAphiaRecordsByNames() for fuzzy/near matching.&lt;/li&gt;
	&lt;li&gt;&lt;u&gt;marine_only&lt;/u&gt;: limit to marine taxa. Default=true.&lt;/li&gt;
   &lt;/ul&gt;&lt;/strong&gt;
   &lt;br /&gt; For the structure of the returned AphiaRecords, please refer to the function getAphiaRecordByID()
                     * @see uk.ac.ebi.metabolights.referencelayer.worms.client.AphiaNameService#getAphiaRecordsByNames
                     * @param getAphiaRecordsByNames22
                    
                     */

                    

                            public  aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesResponseDocument getAphiaRecordsByNames(

                            aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument getAphiaRecordsByNames22)
                        

                    throws java.rmi.RemoteException
                    
                    {
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[11].getName());
              _operationClient.getOptions().setAction("getAphiaRecordsByNames");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getAphiaRecordsByNames22,
                                                    optimizeContent(new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getAphiaRecordsByNames")), new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getAphiaRecordsByNames"));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesResponseDocument.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesResponseDocument)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaRecordsByNames"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaRecordsByNames"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaRecordsByNames"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * &lt;strong&gt;For each given scientific name, try to find one or more AphiaRecords.&lt;br/&gt;
  This allows you to match multiple names in one call. Limited to 500 names at once for performance reasons.
  &lt;br/&gt;Parameters:
   &lt;ul&gt;
    &lt;li&gt;&lt;u&gt;like&lt;/u&gt;: add a '%'-sign after the ScientificName (SQL LIKE function). Default=false.&lt;/li&gt;
	&lt;li&gt;&lt;u&gt;fuzzy&lt;/u&gt;: this parameter is deprecated (and ignored since 2013-07-17). Please use the function matchAphiaRecordsByNames() for fuzzy/near matching.&lt;/li&gt;
	&lt;li&gt;&lt;u&gt;marine_only&lt;/u&gt;: limit to marine taxa. Default=true.&lt;/li&gt;
   &lt;/ul&gt;&lt;/strong&gt;
   &lt;br /&gt; For the structure of the returned AphiaRecords, please refer to the function getAphiaRecordByID()
                * @see uk.ac.ebi.metabolights.referencelayer.worms.client.AphiaNameService#startgetAphiaRecordsByNames
                    * @param getAphiaRecordsByNames22
                
                */
                public  void startgetAphiaRecordsByNames(

                 aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument getAphiaRecordsByNames22,

                  final uk.ac.ebi.metabolights.referencelayer.worms.client.AphiaNameServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[11].getName());
             _operationClient.getOptions().setAction("getAphiaRecordsByNames");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getAphiaRecordsByNames22,
                                                    optimizeContent(new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getAphiaRecordsByNames")), new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getAphiaRecordsByNames"));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesResponseDocument.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultgetAphiaRecordsByNames(
                                        (aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesResponseDocument)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorgetAphiaRecordsByNames(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaRecordsByNames"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaRecordsByNames"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                                                    java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaRecordsByNames"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
					
										            callback.receiveErrorgetAphiaRecordsByNames(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaRecordsByNames(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaRecordsByNames(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaRecordsByNames(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaRecordsByNames(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaRecordsByNames(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaRecordsByNames(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaRecordsByNames(f);
                                            }
									    } else {
										    callback.receiveErrorgetAphiaRecordsByNames(f);
									    }
									} else {
									    callback.receiveErrorgetAphiaRecordsByNames(f);
									}
								} else {
								    callback.receiveErrorgetAphiaRecordsByNames(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrorgetAphiaRecordsByNames(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[11].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[11].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                
                    /**
                     * Auto generated method signature
                     * &lt;strong&gt;Get the Aphia Record for a given external identifier.
&lt;br/&gt;Parameters:
&lt;ul&gt;
 &lt;li&gt;
  &lt;u&gt;type&lt;/u&gt;: Should have one of the following values:
   &lt;ul&gt;
    &lt;li&gt;&lt;u&gt;bold&lt;/u&gt;: Barcode of Life Database (BOLD) TaxID&lt;/li&gt;
    &lt;li&gt;&lt;u&gt;dyntaxa&lt;/u&gt;: Dyntaxa ID&lt;/li&gt;
	&lt;li&gt;&lt;u&gt;eol&lt;/u&gt;: Encyclopedia of Life (EoL) page identifier&lt;/li&gt;
	&lt;li&gt;&lt;u&gt;fishbase&lt;/u&gt;: FishBase species ID&lt;/li&gt;
	&lt;li&gt;&lt;u&gt;iucn&lt;/u&gt;: IUCN Red List Identifier&lt;/li&gt;
    &lt;li&gt;&lt;u&gt;lsid&lt;/u&gt;: Life Science Identifier&lt;/li&gt;
    &lt;li&gt;&lt;u&gt;ncbi&lt;/u&gt;: NCBI Taxonomy ID (Genbank)&lt;/li&gt;
    &lt;li&gt;&lt;u&gt;tsn&lt;/u&gt;: ITIS Taxonomic Serial Number&lt;/li&gt;
  &lt;/ul&gt;
 &lt;/li&gt;
&lt;/ul&gt;&lt;/strong&gt;
                     * @see uk.ac.ebi.metabolights.referencelayer.worms.client.AphiaNameService#getAphiaRecordByExtID
                     * @param getAphiaRecordByExtID24
                    
                     */

                    

                            public  aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument getAphiaRecordByExtID(

                            aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDDocument getAphiaRecordByExtID24)
                        

                    throws java.rmi.RemoteException
                    
                    {
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[12].getName());
              _operationClient.getOptions().setAction("getAphiaRecordByExtID");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getAphiaRecordByExtID24,
                                                    optimizeContent(new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getAphiaRecordByExtID")), new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getAphiaRecordByExtID"));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaRecordByExtID"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaRecordByExtID"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaRecordByExtID"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * &lt;strong&gt;Get the Aphia Record for a given external identifier.
&lt;br/&gt;Parameters:
&lt;ul&gt;
 &lt;li&gt;
  &lt;u&gt;type&lt;/u&gt;: Should have one of the following values:
   &lt;ul&gt;
    &lt;li&gt;&lt;u&gt;bold&lt;/u&gt;: Barcode of Life Database (BOLD) TaxID&lt;/li&gt;
    &lt;li&gt;&lt;u&gt;dyntaxa&lt;/u&gt;: Dyntaxa ID&lt;/li&gt;
	&lt;li&gt;&lt;u&gt;eol&lt;/u&gt;: Encyclopedia of Life (EoL) page identifier&lt;/li&gt;
	&lt;li&gt;&lt;u&gt;fishbase&lt;/u&gt;: FishBase species ID&lt;/li&gt;
	&lt;li&gt;&lt;u&gt;iucn&lt;/u&gt;: IUCN Red List Identifier&lt;/li&gt;
    &lt;li&gt;&lt;u&gt;lsid&lt;/u&gt;: Life Science Identifier&lt;/li&gt;
    &lt;li&gt;&lt;u&gt;ncbi&lt;/u&gt;: NCBI Taxonomy ID (Genbank)&lt;/li&gt;
    &lt;li&gt;&lt;u&gt;tsn&lt;/u&gt;: ITIS Taxonomic Serial Number&lt;/li&gt;
  &lt;/ul&gt;
 &lt;/li&gt;
&lt;/ul&gt;&lt;/strong&gt;
                * @see uk.ac.ebi.metabolights.referencelayer.worms.client.AphiaNameService#startgetAphiaRecordByExtID
                    * @param getAphiaRecordByExtID24
                
                */
                public  void startgetAphiaRecordByExtID(

                 aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDDocument getAphiaRecordByExtID24,

                  final uk.ac.ebi.metabolights.referencelayer.worms.client.AphiaNameServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[12].getName());
             _operationClient.getOptions().setAction("getAphiaRecordByExtID");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getAphiaRecordByExtID24,
                                                    optimizeContent(new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getAphiaRecordByExtID")), new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getAphiaRecordByExtID"));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultgetAphiaRecordByExtID(
                                        (aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorgetAphiaRecordByExtID(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaRecordByExtID"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaRecordByExtID"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                                                    java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaRecordByExtID"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
					
										            callback.receiveErrorgetAphiaRecordByExtID(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaRecordByExtID(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaRecordByExtID(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaRecordByExtID(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaRecordByExtID(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaRecordByExtID(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaRecordByExtID(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaRecordByExtID(f);
                                            }
									    } else {
										    callback.receiveErrorgetAphiaRecordByExtID(f);
									    }
									} else {
									    callback.receiveErrorgetAphiaRecordByExtID(f);
									}
								} else {
								    callback.receiveErrorgetAphiaRecordByExtID(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrorgetAphiaRecordByExtID(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[12].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[12].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                
                    /**
                     * Auto generated method signature
                     * &lt;strong&gt;Get all vernaculars for a given AphiaID.&lt;/strong&gt;
                     * @see uk.ac.ebi.metabolights.referencelayer.worms.client.AphiaNameService#getAphiaVernacularsByID
                     * @param getAphiaVernacularsByID26
                    
                     */

                    

                            public  aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument getAphiaVernacularsByID(

                            aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument getAphiaVernacularsByID26)
                        

                    throws java.rmi.RemoteException
                    
                    {
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[13].getName());
              _operationClient.getOptions().setAction("getAphiaVernacularsByID");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getAphiaVernacularsByID26,
                                                    optimizeContent(new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getAphiaVernacularsByID")), new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getAphiaVernacularsByID"));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaVernacularsByID"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaVernacularsByID"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaVernacularsByID"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * &lt;strong&gt;Get all vernaculars for a given AphiaID.&lt;/strong&gt;
                * @see uk.ac.ebi.metabolights.referencelayer.worms.client.AphiaNameService#startgetAphiaVernacularsByID
                    * @param getAphiaVernacularsByID26
                
                */
                public  void startgetAphiaVernacularsByID(

                 aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument getAphiaVernacularsByID26,

                  final uk.ac.ebi.metabolights.referencelayer.worms.client.AphiaNameServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[13].getName());
             _operationClient.getOptions().setAction("getAphiaVernacularsByID");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getAphiaVernacularsByID26,
                                                    optimizeContent(new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getAphiaVernacularsByID")), new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getAphiaVernacularsByID"));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultgetAphiaVernacularsByID(
                                        (aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorgetAphiaVernacularsByID(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaVernacularsByID"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaVernacularsByID"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                                                    java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaVernacularsByID"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
					
										            callback.receiveErrorgetAphiaVernacularsByID(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaVernacularsByID(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaVernacularsByID(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaVernacularsByID(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaVernacularsByID(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaVernacularsByID(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaVernacularsByID(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaVernacularsByID(f);
                                            }
									    } else {
										    callback.receiveErrorgetAphiaVernacularsByID(f);
									    }
									} else {
									    callback.receiveErrorgetAphiaVernacularsByID(f);
									}
								} else {
								    callback.receiveErrorgetAphiaVernacularsByID(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrorgetAphiaVernacularsByID(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[13].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[13].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                
                    /**
                     * Auto generated method signature
                     * &lt;strong&gt;Get the (first) exact matching AphiaID for a given name.&lt;br/&gt;Parameters:
   &lt;ul&gt;
    &lt;li&gt;&lt;u&gt;marine_only&lt;/u&gt;: limit to marine taxa. Default=true.&lt;/li&gt;
   &lt;/ul&gt;
  &lt;/strong&gt;
                     * @see uk.ac.ebi.metabolights.referencelayer.worms.client.AphiaNameService#getAphiaID
                     * @param getAphiaID28
                    
                     */

                    

                            public  aphia.v1_0.aphiaid.GetAphiaIDResponseDocument getAphiaID(

                            aphia.v1_0.aphiaid.GetAphiaIDDocument getAphiaID28)
                        

                    throws java.rmi.RemoteException
                    
                    {
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[14].getName());
              _operationClient.getOptions().setAction("getAphiaID");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getAphiaID28,
                                                    optimizeContent(new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getAphiaID")), new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getAphiaID"));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             aphia.v1_0.aphiaid.GetAphiaIDResponseDocument.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (aphia.v1_0.aphiaid.GetAphiaIDResponseDocument)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaID"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaID"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaID"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * &lt;strong&gt;Get the (first) exact matching AphiaID for a given name.&lt;br/&gt;Parameters:
   &lt;ul&gt;
    &lt;li&gt;&lt;u&gt;marine_only&lt;/u&gt;: limit to marine taxa. Default=true.&lt;/li&gt;
   &lt;/ul&gt;
  &lt;/strong&gt;
                * @see uk.ac.ebi.metabolights.referencelayer.worms.client.AphiaNameService#startgetAphiaID
                    * @param getAphiaID28
                
                */
                public  void startgetAphiaID(

                 aphia.v1_0.aphiaid.GetAphiaIDDocument getAphiaID28,

                  final uk.ac.ebi.metabolights.referencelayer.worms.client.AphiaNameServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[14].getName());
             _operationClient.getOptions().setAction("getAphiaID");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getAphiaID28,
                                                    optimizeContent(new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getAphiaID")), new javax.xml.namespace.QName("http://aphia/v1.0",
                                                    "getAphiaID"));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         aphia.v1_0.aphiaid.GetAphiaIDResponseDocument.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultgetAphiaID(
                                        (aphia.v1_0.aphiaid.GetAphiaIDResponseDocument)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorgetAphiaID(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaID"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaID"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                                                    java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAphiaID"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
					
										            callback.receiveErrorgetAphiaID(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaID(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaID(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaID(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaID(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaID(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaID(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAphiaID(f);
                                            }
									    } else {
										    callback.receiveErrorgetAphiaID(f);
									    }
									} else {
									    callback.receiveErrorgetAphiaID(f);
									}
								} else {
								    callback.receiveErrorgetAphiaID(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrorgetAphiaID(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[14].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[14].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                


       /**
        *  A utility method that copies the namepaces from the SOAPEnvelope
        */
       private java.util.Map getEnvelopeNamespaces(org.apache.axiom.soap.SOAPEnvelope env){
        java.util.Map returnMap = new java.util.HashMap();
        java.util.Iterator namespaceIterator = env.getAllDeclaredNamespaces();
        while (namespaceIterator.hasNext()) {
            org.apache.axiom.om.OMNamespace ns = (org.apache.axiom.om.OMNamespace) namespaceIterator.next();
            returnMap.put(ns.getPrefix(),ns.getNamespaceURI());
        }
       return returnMap;
    }

    
    
    private javax.xml.namespace.QName[] opNameArray = null;
    private boolean optimizeContent(javax.xml.namespace.QName opName) {
        

        if (opNameArray == null) {
            return false;
        }
        for (int i = 0; i < opNameArray.length; i++) {
            if (opName.equals(opNameArray[i])) {
                return true;   
            }
        }
        return false;
    }
     //http://www.marinespecies.org/aphia.php?p=soap

            private  org.apache.axiom.om.OMElement  toOM(aphia.v1_0.externalidentifiers.GetExtIDbyAphiaIDDocument param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault{

            
                    return toOM(param);
                

            }

            private org.apache.axiom.om.OMElement toOM(final aphia.v1_0.externalidentifiers.GetExtIDbyAphiaIDDocument param)
                    throws org.apache.axis2.AxisFault {

                org.apache.xmlbeans.XmlOptions xmlOptions = new org.apache.xmlbeans.XmlOptions();
                xmlOptions.setSaveNoXmlDecl();
                xmlOptions.setSaveAggressiveNamespaces();
                xmlOptions.setSaveNamespacesFirst();
                org.apache.axiom.om.OMXMLParserWrapper builder = org.apache.axiom.om.OMXMLBuilderFactory.createOMBuilder(
                        new javax.xml.transform.sax.SAXSource(new org.apache.axis2.xmlbeans.XmlBeansXMLReader(param, xmlOptions), new org.xml.sax.InputSource()));
                try {
                    return builder.getDocumentElement(true);
                } catch (java.lang.Exception e) {
                    throw org.apache.axis2.AxisFault.makeFault(e);
                }
            }
        

            private  org.apache.axiom.om.OMElement  toOM(aphia.v1_0.externalidentifiers.GetExtIDbyAphiaIDResponseDocument param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault{

            
                    return toOM(param);
                

            }

            private org.apache.axiom.om.OMElement toOM(final aphia.v1_0.externalidentifiers.GetExtIDbyAphiaIDResponseDocument param)
                    throws org.apache.axis2.AxisFault {

                org.apache.xmlbeans.XmlOptions xmlOptions = new org.apache.xmlbeans.XmlOptions();
                xmlOptions.setSaveNoXmlDecl();
                xmlOptions.setSaveAggressiveNamespaces();
                xmlOptions.setSaveNamespacesFirst();
                org.apache.axiom.om.OMXMLParserWrapper builder = org.apache.axiom.om.OMXMLBuilderFactory.createOMBuilder(
                        new javax.xml.transform.sax.SAXSource(new org.apache.axis2.xmlbeans.XmlBeansXMLReader(param, xmlOptions), new org.xml.sax.InputSource()));
                try {
                    return builder.getDocumentElement(true);
                } catch (java.lang.Exception e) {
                    throw org.apache.axis2.AxisFault.makeFault(e);
                }
            }
        

            private  org.apache.axiom.om.OMElement  toOM(aphia.v1_0.aphiarecords.GetAphiaRecordsByVernacularDocument param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault{

            
                    return toOM(param);
                

            }

            private org.apache.axiom.om.OMElement toOM(final aphia.v1_0.aphiarecords.GetAphiaRecordsByVernacularDocument param)
                    throws org.apache.axis2.AxisFault {

                org.apache.xmlbeans.XmlOptions xmlOptions = new org.apache.xmlbeans.XmlOptions();
                xmlOptions.setSaveNoXmlDecl();
                xmlOptions.setSaveAggressiveNamespaces();
                xmlOptions.setSaveNamespacesFirst();
                org.apache.axiom.om.OMXMLParserWrapper builder = org.apache.axiom.om.OMXMLBuilderFactory.createOMBuilder(
                        new javax.xml.transform.sax.SAXSource(new org.apache.axis2.xmlbeans.XmlBeansXMLReader(param, xmlOptions), new org.xml.sax.InputSource()));
                try {
                    return builder.getDocumentElement(true);
                } catch (java.lang.Exception e) {
                    throw org.apache.axis2.AxisFault.makeFault(e);
                }
            }
        

            private  org.apache.axiom.om.OMElement  toOM(aphia.v1_0.aphiarecords.GetAphiaRecordsByVernacularResponseDocument param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault{

            
                    return toOM(param);
                

            }

            private org.apache.axiom.om.OMElement toOM(final aphia.v1_0.aphiarecords.GetAphiaRecordsByVernacularResponseDocument param)
                    throws org.apache.axis2.AxisFault {

                org.apache.xmlbeans.XmlOptions xmlOptions = new org.apache.xmlbeans.XmlOptions();
                xmlOptions.setSaveNoXmlDecl();
                xmlOptions.setSaveAggressiveNamespaces();
                xmlOptions.setSaveNamespacesFirst();
                org.apache.axiom.om.OMXMLParserWrapper builder = org.apache.axiom.om.OMXMLBuilderFactory.createOMBuilder(
                        new javax.xml.transform.sax.SAXSource(new org.apache.axis2.xmlbeans.XmlBeansXMLReader(param, xmlOptions), new org.xml.sax.InputSource()));
                try {
                    return builder.getDocumentElement(true);
                } catch (java.lang.Exception e) {
                    throw org.apache.axis2.AxisFault.makeFault(e);
                }
            }
        

            private  org.apache.axiom.om.OMElement  toOM(aphia.v1_0.aphiarecords.GetAphiaChildrenByIDDocument param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault{

            
                    return toOM(param);
                

            }

            private org.apache.axiom.om.OMElement toOM(final aphia.v1_0.aphiarecords.GetAphiaChildrenByIDDocument param)
                    throws org.apache.axis2.AxisFault {

                org.apache.xmlbeans.XmlOptions xmlOptions = new org.apache.xmlbeans.XmlOptions();
                xmlOptions.setSaveNoXmlDecl();
                xmlOptions.setSaveAggressiveNamespaces();
                xmlOptions.setSaveNamespacesFirst();
                org.apache.axiom.om.OMXMLParserWrapper builder = org.apache.axiom.om.OMXMLBuilderFactory.createOMBuilder(
                        new javax.xml.transform.sax.SAXSource(new org.apache.axis2.xmlbeans.XmlBeansXMLReader(param, xmlOptions), new org.xml.sax.InputSource()));
                try {
                    return builder.getDocumentElement(true);
                } catch (java.lang.Exception e) {
                    throw org.apache.axis2.AxisFault.makeFault(e);
                }
            }
        

            private  org.apache.axiom.om.OMElement  toOM(aphia.v1_0.aphiarecords.GetAphiaChildrenByIDResponseDocument param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault{

            
                    return toOM(param);
                

            }

            private org.apache.axiom.om.OMElement toOM(final aphia.v1_0.aphiarecords.GetAphiaChildrenByIDResponseDocument param)
                    throws org.apache.axis2.AxisFault {

                org.apache.xmlbeans.XmlOptions xmlOptions = new org.apache.xmlbeans.XmlOptions();
                xmlOptions.setSaveNoXmlDecl();
                xmlOptions.setSaveAggressiveNamespaces();
                xmlOptions.setSaveNamespacesFirst();
                org.apache.axiom.om.OMXMLParserWrapper builder = org.apache.axiom.om.OMXMLBuilderFactory.createOMBuilder(
                        new javax.xml.transform.sax.SAXSource(new org.apache.axis2.xmlbeans.XmlBeansXMLReader(param, xmlOptions), new org.xml.sax.InputSource()));
                try {
                    return builder.getDocumentElement(true);
                } catch (java.lang.Exception e) {
                    throw org.apache.axis2.AxisFault.makeFault(e);
                }
            }
        

            private  org.apache.axiom.om.OMElement  toOM(aphia.v1_0.classification.GetAphiaClassificationByIDDocument param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault{

            
                    return toOM(param);
                

            }

            private org.apache.axiom.om.OMElement toOM(final aphia.v1_0.classification.GetAphiaClassificationByIDDocument param)
                    throws org.apache.axis2.AxisFault {

                org.apache.xmlbeans.XmlOptions xmlOptions = new org.apache.xmlbeans.XmlOptions();
                xmlOptions.setSaveNoXmlDecl();
                xmlOptions.setSaveAggressiveNamespaces();
                xmlOptions.setSaveNamespacesFirst();
                org.apache.axiom.om.OMXMLParserWrapper builder = org.apache.axiom.om.OMXMLBuilderFactory.createOMBuilder(
                        new javax.xml.transform.sax.SAXSource(new org.apache.axis2.xmlbeans.XmlBeansXMLReader(param, xmlOptions), new org.xml.sax.InputSource()));
                try {
                    return builder.getDocumentElement(true);
                } catch (java.lang.Exception e) {
                    throw org.apache.axis2.AxisFault.makeFault(e);
                }
            }
        

            private  org.apache.axiom.om.OMElement  toOM(aphia.v1_0.classification.GetAphiaClassificationByIDResponseDocument param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault{

            
                    return toOM(param);
                

            }

            private org.apache.axiom.om.OMElement toOM(final aphia.v1_0.classification.GetAphiaClassificationByIDResponseDocument param)
                    throws org.apache.axis2.AxisFault {

                org.apache.xmlbeans.XmlOptions xmlOptions = new org.apache.xmlbeans.XmlOptions();
                xmlOptions.setSaveNoXmlDecl();
                xmlOptions.setSaveAggressiveNamespaces();
                xmlOptions.setSaveNamespacesFirst();
                org.apache.axiom.om.OMXMLParserWrapper builder = org.apache.axiom.om.OMXMLBuilderFactory.createOMBuilder(
                        new javax.xml.transform.sax.SAXSource(new org.apache.axis2.xmlbeans.XmlBeansXMLReader(param, xmlOptions), new org.xml.sax.InputSource()));
                try {
                    return builder.getDocumentElement(true);
                } catch (java.lang.Exception e) {
                    throw org.apache.axis2.AxisFault.makeFault(e);
                }
            }
        

            private  org.apache.axiom.om.OMElement  toOM(aphia.v1_0.aphiamatches.MatchAphiaRecordsByNamesDocument param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault{

            
                    return toOM(param);
                

            }

            private org.apache.axiom.om.OMElement toOM(final aphia.v1_0.aphiamatches.MatchAphiaRecordsByNamesDocument param)
                    throws org.apache.axis2.AxisFault {

                org.apache.xmlbeans.XmlOptions xmlOptions = new org.apache.xmlbeans.XmlOptions();
                xmlOptions.setSaveNoXmlDecl();
                xmlOptions.setSaveAggressiveNamespaces();
                xmlOptions.setSaveNamespacesFirst();
                org.apache.axiom.om.OMXMLParserWrapper builder = org.apache.axiom.om.OMXMLBuilderFactory.createOMBuilder(
                        new javax.xml.transform.sax.SAXSource(new org.apache.axis2.xmlbeans.XmlBeansXMLReader(param, xmlOptions), new org.xml.sax.InputSource()));
                try {
                    return builder.getDocumentElement(true);
                } catch (java.lang.Exception e) {
                    throw org.apache.axis2.AxisFault.makeFault(e);
                }
            }
        

            private  org.apache.axiom.om.OMElement  toOM(aphia.v1_0.aphiamatches.MatchAphiaRecordsByNamesResponseDocument param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault{

            
                    return toOM(param);
                

            }

            private org.apache.axiom.om.OMElement toOM(final aphia.v1_0.aphiamatches.MatchAphiaRecordsByNamesResponseDocument param)
                    throws org.apache.axis2.AxisFault {

                org.apache.xmlbeans.XmlOptions xmlOptions = new org.apache.xmlbeans.XmlOptions();
                xmlOptions.setSaveNoXmlDecl();
                xmlOptions.setSaveAggressiveNamespaces();
                xmlOptions.setSaveNamespacesFirst();
                org.apache.axiom.om.OMXMLParserWrapper builder = org.apache.axiom.om.OMXMLBuilderFactory.createOMBuilder(
                        new javax.xml.transform.sax.SAXSource(new org.apache.axis2.xmlbeans.XmlBeansXMLReader(param, xmlOptions), new org.xml.sax.InputSource()));
                try {
                    return builder.getDocumentElement(true);
                } catch (java.lang.Exception e) {
                    throw org.apache.axis2.AxisFault.makeFault(e);
                }
            }
        

            private  org.apache.axiom.om.OMElement  toOM(aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault{

            
                    return toOM(param);
                

            }

            private org.apache.axiom.om.OMElement toOM(final aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument param)
                    throws org.apache.axis2.AxisFault {

                org.apache.xmlbeans.XmlOptions xmlOptions = new org.apache.xmlbeans.XmlOptions();
                xmlOptions.setSaveNoXmlDecl();
                xmlOptions.setSaveAggressiveNamespaces();
                xmlOptions.setSaveNamespacesFirst();
                org.apache.axiom.om.OMXMLParserWrapper builder = org.apache.axiom.om.OMXMLBuilderFactory.createOMBuilder(
                        new javax.xml.transform.sax.SAXSource(new org.apache.axis2.xmlbeans.XmlBeansXMLReader(param, xmlOptions), new org.xml.sax.InputSource()));
                try {
                    return builder.getDocumentElement(true);
                } catch (java.lang.Exception e) {
                    throw org.apache.axis2.AxisFault.makeFault(e);
                }
            }
        

            private  org.apache.axiom.om.OMElement  toOM(aphia.v1_0.aphiarecords.GetAphiaRecordsByDateResponseDocument param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault{

            
                    return toOM(param);
                

            }

            private org.apache.axiom.om.OMElement toOM(final aphia.v1_0.aphiarecords.GetAphiaRecordsByDateResponseDocument param)
                    throws org.apache.axis2.AxisFault {

                org.apache.xmlbeans.XmlOptions xmlOptions = new org.apache.xmlbeans.XmlOptions();
                xmlOptions.setSaveNoXmlDecl();
                xmlOptions.setSaveAggressiveNamespaces();
                xmlOptions.setSaveNamespacesFirst();
                org.apache.axiom.om.OMXMLParserWrapper builder = org.apache.axiom.om.OMXMLBuilderFactory.createOMBuilder(
                        new javax.xml.transform.sax.SAXSource(new org.apache.axis2.xmlbeans.XmlBeansXMLReader(param, xmlOptions), new org.xml.sax.InputSource()));
                try {
                    return builder.getDocumentElement(true);
                } catch (java.lang.Exception e) {
                    throw org.apache.axis2.AxisFault.makeFault(e);
                }
            }
        

            private  org.apache.axiom.om.OMElement  toOM(aphia.v1_0.aphiarecords.GetAphiaSynonymsByIDDocument param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault{

            
                    return toOM(param);
                

            }

            private org.apache.axiom.om.OMElement toOM(final aphia.v1_0.aphiarecords.GetAphiaSynonymsByIDDocument param)
                    throws org.apache.axis2.AxisFault {

                org.apache.xmlbeans.XmlOptions xmlOptions = new org.apache.xmlbeans.XmlOptions();
                xmlOptions.setSaveNoXmlDecl();
                xmlOptions.setSaveAggressiveNamespaces();
                xmlOptions.setSaveNamespacesFirst();
                org.apache.axiom.om.OMXMLParserWrapper builder = org.apache.axiom.om.OMXMLBuilderFactory.createOMBuilder(
                        new javax.xml.transform.sax.SAXSource(new org.apache.axis2.xmlbeans.XmlBeansXMLReader(param, xmlOptions), new org.xml.sax.InputSource()));
                try {
                    return builder.getDocumentElement(true);
                } catch (java.lang.Exception e) {
                    throw org.apache.axis2.AxisFault.makeFault(e);
                }
            }
        

            private  org.apache.axiom.om.OMElement  toOM(aphia.v1_0.aphiarecords.GetAphiaSynonymsByIDResponseDocument param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault{

            
                    return toOM(param);
                

            }

            private org.apache.axiom.om.OMElement toOM(final aphia.v1_0.aphiarecords.GetAphiaSynonymsByIDResponseDocument param)
                    throws org.apache.axis2.AxisFault {

                org.apache.xmlbeans.XmlOptions xmlOptions = new org.apache.xmlbeans.XmlOptions();
                xmlOptions.setSaveNoXmlDecl();
                xmlOptions.setSaveAggressiveNamespaces();
                xmlOptions.setSaveNamespacesFirst();
                org.apache.axiom.om.OMXMLParserWrapper builder = org.apache.axiom.om.OMXMLBuilderFactory.createOMBuilder(
                        new javax.xml.transform.sax.SAXSource(new org.apache.axis2.xmlbeans.XmlBeansXMLReader(param, xmlOptions), new org.xml.sax.InputSource()));
                try {
                    return builder.getDocumentElement(true);
                } catch (java.lang.Exception e) {
                    throw org.apache.axis2.AxisFault.makeFault(e);
                }
            }
        

            private  org.apache.axiom.om.OMElement  toOM(aphia.v1_0.aphiarecords.GetAphiaRecordsDocument param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault{

            
                    return toOM(param);
                

            }

            private org.apache.axiom.om.OMElement toOM(final aphia.v1_0.aphiarecords.GetAphiaRecordsDocument param)
                    throws org.apache.axis2.AxisFault {

                org.apache.xmlbeans.XmlOptions xmlOptions = new org.apache.xmlbeans.XmlOptions();
                xmlOptions.setSaveNoXmlDecl();
                xmlOptions.setSaveAggressiveNamespaces();
                xmlOptions.setSaveNamespacesFirst();
                org.apache.axiom.om.OMXMLParserWrapper builder = org.apache.axiom.om.OMXMLBuilderFactory.createOMBuilder(
                        new javax.xml.transform.sax.SAXSource(new org.apache.axis2.xmlbeans.XmlBeansXMLReader(param, xmlOptions), new org.xml.sax.InputSource()));
                try {
                    return builder.getDocumentElement(true);
                } catch (java.lang.Exception e) {
                    throw org.apache.axis2.AxisFault.makeFault(e);
                }
            }
        

            private  org.apache.axiom.om.OMElement  toOM(aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault{

            
                    return toOM(param);
                

            }

            private org.apache.axiom.om.OMElement toOM(final aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument param)
                    throws org.apache.axis2.AxisFault {

                org.apache.xmlbeans.XmlOptions xmlOptions = new org.apache.xmlbeans.XmlOptions();
                xmlOptions.setSaveNoXmlDecl();
                xmlOptions.setSaveAggressiveNamespaces();
                xmlOptions.setSaveNamespacesFirst();
                org.apache.axiom.om.OMXMLParserWrapper builder = org.apache.axiom.om.OMXMLBuilderFactory.createOMBuilder(
                        new javax.xml.transform.sax.SAXSource(new org.apache.axis2.xmlbeans.XmlBeansXMLReader(param, xmlOptions), new org.xml.sax.InputSource()));
                try {
                    return builder.getDocumentElement(true);
                } catch (java.lang.Exception e) {
                    throw org.apache.axis2.AxisFault.makeFault(e);
                }
            }
        

            private  org.apache.axiom.om.OMElement  toOM(aphia.v1_0.aphiarecord.GetAphiaRecordByIDDocument param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault{

            
                    return toOM(param);
                

            }

            private org.apache.axiom.om.OMElement toOM(final aphia.v1_0.aphiarecord.GetAphiaRecordByIDDocument param)
                    throws org.apache.axis2.AxisFault {

                org.apache.xmlbeans.XmlOptions xmlOptions = new org.apache.xmlbeans.XmlOptions();
                xmlOptions.setSaveNoXmlDecl();
                xmlOptions.setSaveAggressiveNamespaces();
                xmlOptions.setSaveNamespacesFirst();
                org.apache.axiom.om.OMXMLParserWrapper builder = org.apache.axiom.om.OMXMLBuilderFactory.createOMBuilder(
                        new javax.xml.transform.sax.SAXSource(new org.apache.axis2.xmlbeans.XmlBeansXMLReader(param, xmlOptions), new org.xml.sax.InputSource()));
                try {
                    return builder.getDocumentElement(true);
                } catch (java.lang.Exception e) {
                    throw org.apache.axis2.AxisFault.makeFault(e);
                }
            }
        

            private  org.apache.axiom.om.OMElement  toOM(aphia.v1_0.aphiarecord.GetAphiaRecordByIDResponseDocument param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault{

            
                    return toOM(param);
                

            }

            private org.apache.axiom.om.OMElement toOM(final aphia.v1_0.aphiarecord.GetAphiaRecordByIDResponseDocument param)
                    throws org.apache.axis2.AxisFault {

                org.apache.xmlbeans.XmlOptions xmlOptions = new org.apache.xmlbeans.XmlOptions();
                xmlOptions.setSaveNoXmlDecl();
                xmlOptions.setSaveAggressiveNamespaces();
                xmlOptions.setSaveNamespacesFirst();
                org.apache.axiom.om.OMXMLParserWrapper builder = org.apache.axiom.om.OMXMLBuilderFactory.createOMBuilder(
                        new javax.xml.transform.sax.SAXSource(new org.apache.axis2.xmlbeans.XmlBeansXMLReader(param, xmlOptions), new org.xml.sax.InputSource()));
                try {
                    return builder.getDocumentElement(true);
                } catch (java.lang.Exception e) {
                    throw org.apache.axis2.AxisFault.makeFault(e);
                }
            }
        

            private  org.apache.axiom.om.OMElement  toOM(aphia.v1_0.sources.GetSourcesByAphiaIDDocument param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault{

            
                    return toOM(param);
                

            }

            private org.apache.axiom.om.OMElement toOM(final aphia.v1_0.sources.GetSourcesByAphiaIDDocument param)
                    throws org.apache.axis2.AxisFault {

                org.apache.xmlbeans.XmlOptions xmlOptions = new org.apache.xmlbeans.XmlOptions();
                xmlOptions.setSaveNoXmlDecl();
                xmlOptions.setSaveAggressiveNamespaces();
                xmlOptions.setSaveNamespacesFirst();
                org.apache.axiom.om.OMXMLParserWrapper builder = org.apache.axiom.om.OMXMLBuilderFactory.createOMBuilder(
                        new javax.xml.transform.sax.SAXSource(new org.apache.axis2.xmlbeans.XmlBeansXMLReader(param, xmlOptions), new org.xml.sax.InputSource()));
                try {
                    return builder.getDocumentElement(true);
                } catch (java.lang.Exception e) {
                    throw org.apache.axis2.AxisFault.makeFault(e);
                }
            }
        

            private  org.apache.axiom.om.OMElement  toOM(aphia.v1_0.sources.GetSourcesByAphiaIDResponseDocument param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault{

            
                    return toOM(param);
                

            }

            private org.apache.axiom.om.OMElement toOM(final aphia.v1_0.sources.GetSourcesByAphiaIDResponseDocument param)
                    throws org.apache.axis2.AxisFault {

                org.apache.xmlbeans.XmlOptions xmlOptions = new org.apache.xmlbeans.XmlOptions();
                xmlOptions.setSaveNoXmlDecl();
                xmlOptions.setSaveAggressiveNamespaces();
                xmlOptions.setSaveNamespacesFirst();
                org.apache.axiom.om.OMXMLParserWrapper builder = org.apache.axiom.om.OMXMLBuilderFactory.createOMBuilder(
                        new javax.xml.transform.sax.SAXSource(new org.apache.axis2.xmlbeans.XmlBeansXMLReader(param, xmlOptions), new org.xml.sax.InputSource()));
                try {
                    return builder.getDocumentElement(true);
                } catch (java.lang.Exception e) {
                    throw org.apache.axis2.AxisFault.makeFault(e);
                }
            }
        

            private  org.apache.axiom.om.OMElement  toOM(aphia.v1_0.aphianame.GetAphiaNameByIDDocument param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault{

            
                    return toOM(param);
                

            }

            private org.apache.axiom.om.OMElement toOM(final aphia.v1_0.aphianame.GetAphiaNameByIDDocument param)
                    throws org.apache.axis2.AxisFault {

                org.apache.xmlbeans.XmlOptions xmlOptions = new org.apache.xmlbeans.XmlOptions();
                xmlOptions.setSaveNoXmlDecl();
                xmlOptions.setSaveAggressiveNamespaces();
                xmlOptions.setSaveNamespacesFirst();
                org.apache.axiom.om.OMXMLParserWrapper builder = org.apache.axiom.om.OMXMLBuilderFactory.createOMBuilder(
                        new javax.xml.transform.sax.SAXSource(new org.apache.axis2.xmlbeans.XmlBeansXMLReader(param, xmlOptions), new org.xml.sax.InputSource()));
                try {
                    return builder.getDocumentElement(true);
                } catch (java.lang.Exception e) {
                    throw org.apache.axis2.AxisFault.makeFault(e);
                }
            }
        

            private  org.apache.axiom.om.OMElement  toOM(aphia.v1_0.aphianame.GetAphiaNameByIDResponseDocument param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault{

            
                    return toOM(param);
                

            }

            private org.apache.axiom.om.OMElement toOM(final aphia.v1_0.aphianame.GetAphiaNameByIDResponseDocument param)
                    throws org.apache.axis2.AxisFault {

                org.apache.xmlbeans.XmlOptions xmlOptions = new org.apache.xmlbeans.XmlOptions();
                xmlOptions.setSaveNoXmlDecl();
                xmlOptions.setSaveAggressiveNamespaces();
                xmlOptions.setSaveNamespacesFirst();
                org.apache.axiom.om.OMXMLParserWrapper builder = org.apache.axiom.om.OMXMLBuilderFactory.createOMBuilder(
                        new javax.xml.transform.sax.SAXSource(new org.apache.axis2.xmlbeans.XmlBeansXMLReader(param, xmlOptions), new org.xml.sax.InputSource()));
                try {
                    return builder.getDocumentElement(true);
                } catch (java.lang.Exception e) {
                    throw org.apache.axis2.AxisFault.makeFault(e);
                }
            }
        

            private  org.apache.axiom.om.OMElement  toOM(aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault{

            
                    return toOM(param);
                

            }

            private org.apache.axiom.om.OMElement toOM(final aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument param)
                    throws org.apache.axis2.AxisFault {

                org.apache.xmlbeans.XmlOptions xmlOptions = new org.apache.xmlbeans.XmlOptions();
                xmlOptions.setSaveNoXmlDecl();
                xmlOptions.setSaveAggressiveNamespaces();
                xmlOptions.setSaveNamespacesFirst();
                org.apache.axiom.om.OMXMLParserWrapper builder = org.apache.axiom.om.OMXMLBuilderFactory.createOMBuilder(
                        new javax.xml.transform.sax.SAXSource(new org.apache.axis2.xmlbeans.XmlBeansXMLReader(param, xmlOptions), new org.xml.sax.InputSource()));
                try {
                    return builder.getDocumentElement(true);
                } catch (java.lang.Exception e) {
                    throw org.apache.axis2.AxisFault.makeFault(e);
                }
            }
        

            private  org.apache.axiom.om.OMElement  toOM(aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesResponseDocument param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault{

            
                    return toOM(param);
                

            }

            private org.apache.axiom.om.OMElement toOM(final aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesResponseDocument param)
                    throws org.apache.axis2.AxisFault {

                org.apache.xmlbeans.XmlOptions xmlOptions = new org.apache.xmlbeans.XmlOptions();
                xmlOptions.setSaveNoXmlDecl();
                xmlOptions.setSaveAggressiveNamespaces();
                xmlOptions.setSaveNamespacesFirst();
                org.apache.axiom.om.OMXMLParserWrapper builder = org.apache.axiom.om.OMXMLBuilderFactory.createOMBuilder(
                        new javax.xml.transform.sax.SAXSource(new org.apache.axis2.xmlbeans.XmlBeansXMLReader(param, xmlOptions), new org.xml.sax.InputSource()));
                try {
                    return builder.getDocumentElement(true);
                } catch (java.lang.Exception e) {
                    throw org.apache.axis2.AxisFault.makeFault(e);
                }
            }
        

            private  org.apache.axiom.om.OMElement  toOM(aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDDocument param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault{

            
                    return toOM(param);
                

            }

            private org.apache.axiom.om.OMElement toOM(final aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDDocument param)
                    throws org.apache.axis2.AxisFault {

                org.apache.xmlbeans.XmlOptions xmlOptions = new org.apache.xmlbeans.XmlOptions();
                xmlOptions.setSaveNoXmlDecl();
                xmlOptions.setSaveAggressiveNamespaces();
                xmlOptions.setSaveNamespacesFirst();
                org.apache.axiom.om.OMXMLParserWrapper builder = org.apache.axiom.om.OMXMLBuilderFactory.createOMBuilder(
                        new javax.xml.transform.sax.SAXSource(new org.apache.axis2.xmlbeans.XmlBeansXMLReader(param, xmlOptions), new org.xml.sax.InputSource()));
                try {
                    return builder.getDocumentElement(true);
                } catch (java.lang.Exception e) {
                    throw org.apache.axis2.AxisFault.makeFault(e);
                }
            }
        

            private  org.apache.axiom.om.OMElement  toOM(aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault{

            
                    return toOM(param);
                

            }

            private org.apache.axiom.om.OMElement toOM(final aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument param)
                    throws org.apache.axis2.AxisFault {

                org.apache.xmlbeans.XmlOptions xmlOptions = new org.apache.xmlbeans.XmlOptions();
                xmlOptions.setSaveNoXmlDecl();
                xmlOptions.setSaveAggressiveNamespaces();
                xmlOptions.setSaveNamespacesFirst();
                org.apache.axiom.om.OMXMLParserWrapper builder = org.apache.axiom.om.OMXMLBuilderFactory.createOMBuilder(
                        new javax.xml.transform.sax.SAXSource(new org.apache.axis2.xmlbeans.XmlBeansXMLReader(param, xmlOptions), new org.xml.sax.InputSource()));
                try {
                    return builder.getDocumentElement(true);
                } catch (java.lang.Exception e) {
                    throw org.apache.axis2.AxisFault.makeFault(e);
                }
            }
        

            private  org.apache.axiom.om.OMElement  toOM(aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault{

            
                    return toOM(param);
                

            }

            private org.apache.axiom.om.OMElement toOM(final aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument param)
                    throws org.apache.axis2.AxisFault {

                org.apache.xmlbeans.XmlOptions xmlOptions = new org.apache.xmlbeans.XmlOptions();
                xmlOptions.setSaveNoXmlDecl();
                xmlOptions.setSaveAggressiveNamespaces();
                xmlOptions.setSaveNamespacesFirst();
                org.apache.axiom.om.OMXMLParserWrapper builder = org.apache.axiom.om.OMXMLBuilderFactory.createOMBuilder(
                        new javax.xml.transform.sax.SAXSource(new org.apache.axis2.xmlbeans.XmlBeansXMLReader(param, xmlOptions), new org.xml.sax.InputSource()));
                try {
                    return builder.getDocumentElement(true);
                } catch (java.lang.Exception e) {
                    throw org.apache.axis2.AxisFault.makeFault(e);
                }
            }
        

            private  org.apache.axiom.om.OMElement  toOM(aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault{

            
                    return toOM(param);
                

            }

            private org.apache.axiom.om.OMElement toOM(final aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument param)
                    throws org.apache.axis2.AxisFault {

                org.apache.xmlbeans.XmlOptions xmlOptions = new org.apache.xmlbeans.XmlOptions();
                xmlOptions.setSaveNoXmlDecl();
                xmlOptions.setSaveAggressiveNamespaces();
                xmlOptions.setSaveNamespacesFirst();
                org.apache.axiom.om.OMXMLParserWrapper builder = org.apache.axiom.om.OMXMLBuilderFactory.createOMBuilder(
                        new javax.xml.transform.sax.SAXSource(new org.apache.axis2.xmlbeans.XmlBeansXMLReader(param, xmlOptions), new org.xml.sax.InputSource()));
                try {
                    return builder.getDocumentElement(true);
                } catch (java.lang.Exception e) {
                    throw org.apache.axis2.AxisFault.makeFault(e);
                }
            }
        

            private  org.apache.axiom.om.OMElement  toOM(aphia.v1_0.aphiaid.GetAphiaIDDocument param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault{

            
                    return toOM(param);
                

            }

            private org.apache.axiom.om.OMElement toOM(final aphia.v1_0.aphiaid.GetAphiaIDDocument param)
                    throws org.apache.axis2.AxisFault {

                org.apache.xmlbeans.XmlOptions xmlOptions = new org.apache.xmlbeans.XmlOptions();
                xmlOptions.setSaveNoXmlDecl();
                xmlOptions.setSaveAggressiveNamespaces();
                xmlOptions.setSaveNamespacesFirst();
                org.apache.axiom.om.OMXMLParserWrapper builder = org.apache.axiom.om.OMXMLBuilderFactory.createOMBuilder(
                        new javax.xml.transform.sax.SAXSource(new org.apache.axis2.xmlbeans.XmlBeansXMLReader(param, xmlOptions), new org.xml.sax.InputSource()));
                try {
                    return builder.getDocumentElement(true);
                } catch (java.lang.Exception e) {
                    throw org.apache.axis2.AxisFault.makeFault(e);
                }
            }
        

            private  org.apache.axiom.om.OMElement  toOM(aphia.v1_0.aphiaid.GetAphiaIDResponseDocument param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault{

            
                    return toOM(param);
                

            }

            private org.apache.axiom.om.OMElement toOM(final aphia.v1_0.aphiaid.GetAphiaIDResponseDocument param)
                    throws org.apache.axis2.AxisFault {

                org.apache.xmlbeans.XmlOptions xmlOptions = new org.apache.xmlbeans.XmlOptions();
                xmlOptions.setSaveNoXmlDecl();
                xmlOptions.setSaveAggressiveNamespaces();
                xmlOptions.setSaveNamespacesFirst();
                org.apache.axiom.om.OMXMLParserWrapper builder = org.apache.axiom.om.OMXMLBuilderFactory.createOMBuilder(
                        new javax.xml.transform.sax.SAXSource(new org.apache.axis2.xmlbeans.XmlBeansXMLReader(param, xmlOptions), new org.xml.sax.InputSource()));
                try {
                    return builder.getDocumentElement(true);
                } catch (java.lang.Exception e) {
                    throw org.apache.axis2.AxisFault.makeFault(e);
                }
            }
        
                                
                                private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, aphia.v1_0.externalidentifiers.GetExtIDbyAphiaIDDocument param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                throws org.apache.axis2.AxisFault{
                                org.apache.axiom.soap.SOAPEnvelope envelope = factory.getDefaultEnvelope();
                                if (param != null){
                                envelope.getBody().addChild(toOM(param, optimizeContent));
                                }
                                return envelope;
                                }
                            
                                
                                private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, aphia.v1_0.aphiarecords.GetAphiaRecordsByVernacularDocument param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                throws org.apache.axis2.AxisFault{
                                org.apache.axiom.soap.SOAPEnvelope envelope = factory.getDefaultEnvelope();
                                if (param != null){
                                envelope.getBody().addChild(toOM(param, optimizeContent));
                                }
                                return envelope;
                                }
                            
                                
                                private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, aphia.v1_0.aphiarecords.GetAphiaChildrenByIDDocument param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                throws org.apache.axis2.AxisFault{
                                org.apache.axiom.soap.SOAPEnvelope envelope = factory.getDefaultEnvelope();
                                if (param != null){
                                envelope.getBody().addChild(toOM(param, optimizeContent));
                                }
                                return envelope;
                                }
                            
                                
                                private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, aphia.v1_0.classification.GetAphiaClassificationByIDDocument param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                throws org.apache.axis2.AxisFault{
                                org.apache.axiom.soap.SOAPEnvelope envelope = factory.getDefaultEnvelope();
                                if (param != null){
                                envelope.getBody().addChild(toOM(param, optimizeContent));
                                }
                                return envelope;
                                }
                            
                                
                                private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, aphia.v1_0.aphiamatches.MatchAphiaRecordsByNamesDocument param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                throws org.apache.axis2.AxisFault{
                                org.apache.axiom.soap.SOAPEnvelope envelope = factory.getDefaultEnvelope();
                                if (param != null){
                                envelope.getBody().addChild(toOM(param, optimizeContent));
                                }
                                return envelope;
                                }
                            
                                
                                private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                throws org.apache.axis2.AxisFault{
                                org.apache.axiom.soap.SOAPEnvelope envelope = factory.getDefaultEnvelope();
                                if (param != null){
                                envelope.getBody().addChild(toOM(param, optimizeContent));
                                }
                                return envelope;
                                }
                            
                                
                                private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, aphia.v1_0.aphiarecords.GetAphiaSynonymsByIDDocument param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                throws org.apache.axis2.AxisFault{
                                org.apache.axiom.soap.SOAPEnvelope envelope = factory.getDefaultEnvelope();
                                if (param != null){
                                envelope.getBody().addChild(toOM(param, optimizeContent));
                                }
                                return envelope;
                                }
                            
                                
                                private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, aphia.v1_0.aphiarecords.GetAphiaRecordsDocument param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                throws org.apache.axis2.AxisFault{
                                org.apache.axiom.soap.SOAPEnvelope envelope = factory.getDefaultEnvelope();
                                if (param != null){
                                envelope.getBody().addChild(toOM(param, optimizeContent));
                                }
                                return envelope;
                                }
                            
                                
                                private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, aphia.v1_0.aphiarecord.GetAphiaRecordByIDDocument param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                throws org.apache.axis2.AxisFault{
                                org.apache.axiom.soap.SOAPEnvelope envelope = factory.getDefaultEnvelope();
                                if (param != null){
                                envelope.getBody().addChild(toOM(param, optimizeContent));
                                }
                                return envelope;
                                }
                            
                                
                                private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, aphia.v1_0.sources.GetSourcesByAphiaIDDocument param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                throws org.apache.axis2.AxisFault{
                                org.apache.axiom.soap.SOAPEnvelope envelope = factory.getDefaultEnvelope();
                                if (param != null){
                                envelope.getBody().addChild(toOM(param, optimizeContent));
                                }
                                return envelope;
                                }
                            
                                
                                private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, aphia.v1_0.aphianame.GetAphiaNameByIDDocument param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                throws org.apache.axis2.AxisFault{
                                org.apache.axiom.soap.SOAPEnvelope envelope = factory.getDefaultEnvelope();
                                if (param != null){
                                envelope.getBody().addChild(toOM(param, optimizeContent));
                                }
                                return envelope;
                                }
                            
                                
                                private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                throws org.apache.axis2.AxisFault{
                                org.apache.axiom.soap.SOAPEnvelope envelope = factory.getDefaultEnvelope();
                                if (param != null){
                                envelope.getBody().addChild(toOM(param, optimizeContent));
                                }
                                return envelope;
                                }
                            
                                
                                private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDDocument param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                throws org.apache.axis2.AxisFault{
                                org.apache.axiom.soap.SOAPEnvelope envelope = factory.getDefaultEnvelope();
                                if (param != null){
                                envelope.getBody().addChild(toOM(param, optimizeContent));
                                }
                                return envelope;
                                }
                            
                                
                                private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                throws org.apache.axis2.AxisFault{
                                org.apache.axiom.soap.SOAPEnvelope envelope = factory.getDefaultEnvelope();
                                if (param != null){
                                envelope.getBody().addChild(toOM(param, optimizeContent));
                                }
                                return envelope;
                                }
                            
                                
                                private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, aphia.v1_0.aphiaid.GetAphiaIDDocument param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                throws org.apache.axis2.AxisFault{
                                org.apache.axiom.soap.SOAPEnvelope envelope = factory.getDefaultEnvelope();
                                if (param != null){
                                envelope.getBody().addChild(toOM(param, optimizeContent));
                                }
                                return envelope;
                                }
                            


        /**
        *  get the default envelope
        */
        private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory){
        return factory.getDefaultEnvelope();
        }

        public org.apache.xmlbeans.XmlObject fromOM(
        org.apache.axiom.om.OMElement param,
        java.lang.Class type,
        java.util.Map extraNamespaces) throws org.apache.axis2.AxisFault{
        try{
        

            if (aphia.v1_0.externalidentifiers.GetExtIDbyAphiaIDDocument.class.equals(type)){
            if (extraNamespaces!=null){
            return aphia.v1_0.externalidentifiers.GetExtIDbyAphiaIDDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching(),
            new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(extraNamespaces));
            }else{
            return aphia.v1_0.externalidentifiers.GetExtIDbyAphiaIDDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching());
            }
            }

        

            if (aphia.v1_0.externalidentifiers.GetExtIDbyAphiaIDResponseDocument.class.equals(type)){
            if (extraNamespaces!=null){
            return aphia.v1_0.externalidentifiers.GetExtIDbyAphiaIDResponseDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching(),
            new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(extraNamespaces));
            }else{
            return aphia.v1_0.externalidentifiers.GetExtIDbyAphiaIDResponseDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching());
            }
            }

        

            if (aphia.v1_0.aphiarecords.GetAphiaRecordsByVernacularDocument.class.equals(type)){
            if (extraNamespaces!=null){
            return aphia.v1_0.aphiarecords.GetAphiaRecordsByVernacularDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching(),
            new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(extraNamespaces));
            }else{
            return aphia.v1_0.aphiarecords.GetAphiaRecordsByVernacularDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching());
            }
            }

        

            if (aphia.v1_0.aphiarecords.GetAphiaRecordsByVernacularResponseDocument.class.equals(type)){
            if (extraNamespaces!=null){
            return aphia.v1_0.aphiarecords.GetAphiaRecordsByVernacularResponseDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching(),
            new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(extraNamespaces));
            }else{
            return aphia.v1_0.aphiarecords.GetAphiaRecordsByVernacularResponseDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching());
            }
            }

        

            if (aphia.v1_0.aphiarecords.GetAphiaChildrenByIDDocument.class.equals(type)){
            if (extraNamespaces!=null){
            return aphia.v1_0.aphiarecords.GetAphiaChildrenByIDDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching(),
            new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(extraNamespaces));
            }else{
            return aphia.v1_0.aphiarecords.GetAphiaChildrenByIDDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching());
            }
            }

        

            if (aphia.v1_0.aphiarecords.GetAphiaChildrenByIDResponseDocument.class.equals(type)){
            if (extraNamespaces!=null){
            return aphia.v1_0.aphiarecords.GetAphiaChildrenByIDResponseDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching(),
            new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(extraNamespaces));
            }else{
            return aphia.v1_0.aphiarecords.GetAphiaChildrenByIDResponseDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching());
            }
            }

        

            if (aphia.v1_0.classification.GetAphiaClassificationByIDDocument.class.equals(type)){
            if (extraNamespaces!=null){
            return aphia.v1_0.classification.GetAphiaClassificationByIDDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching(),
            new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(extraNamespaces));
            }else{
            return aphia.v1_0.classification.GetAphiaClassificationByIDDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching());
            }
            }

        

            if (aphia.v1_0.classification.GetAphiaClassificationByIDResponseDocument.class.equals(type)){
            if (extraNamespaces!=null){
            return aphia.v1_0.classification.GetAphiaClassificationByIDResponseDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching(),
            new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(extraNamespaces));
            }else{
            return aphia.v1_0.classification.GetAphiaClassificationByIDResponseDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching());
            }
            }

        

            if (aphia.v1_0.aphiamatches.MatchAphiaRecordsByNamesDocument.class.equals(type)){
            if (extraNamespaces!=null){
            return aphia.v1_0.aphiamatches.MatchAphiaRecordsByNamesDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching(),
            new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(extraNamespaces));
            }else{
            return aphia.v1_0.aphiamatches.MatchAphiaRecordsByNamesDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching());
            }
            }

        

            if (aphia.v1_0.aphiamatches.MatchAphiaRecordsByNamesResponseDocument.class.equals(type)){
            if (extraNamespaces!=null){
            return aphia.v1_0.aphiamatches.MatchAphiaRecordsByNamesResponseDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching(),
            new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(extraNamespaces));
            }else{
            return aphia.v1_0.aphiamatches.MatchAphiaRecordsByNamesResponseDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching());
            }
            }

        

            if (aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument.class.equals(type)){
            if (extraNamespaces!=null){
            return aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching(),
            new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(extraNamespaces));
            }else{
            return aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching());
            }
            }

        

            if (aphia.v1_0.aphiarecords.GetAphiaRecordsByDateResponseDocument.class.equals(type)){
            if (extraNamespaces!=null){
            return aphia.v1_0.aphiarecords.GetAphiaRecordsByDateResponseDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching(),
            new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(extraNamespaces));
            }else{
            return aphia.v1_0.aphiarecords.GetAphiaRecordsByDateResponseDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching());
            }
            }

        

            if (aphia.v1_0.aphiarecords.GetAphiaSynonymsByIDDocument.class.equals(type)){
            if (extraNamespaces!=null){
            return aphia.v1_0.aphiarecords.GetAphiaSynonymsByIDDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching(),
            new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(extraNamespaces));
            }else{
            return aphia.v1_0.aphiarecords.GetAphiaSynonymsByIDDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching());
            }
            }

        

            if (aphia.v1_0.aphiarecords.GetAphiaSynonymsByIDResponseDocument.class.equals(type)){
            if (extraNamespaces!=null){
            return aphia.v1_0.aphiarecords.GetAphiaSynonymsByIDResponseDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching(),
            new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(extraNamespaces));
            }else{
            return aphia.v1_0.aphiarecords.GetAphiaSynonymsByIDResponseDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching());
            }
            }

        

            if (aphia.v1_0.aphiarecords.GetAphiaRecordsDocument.class.equals(type)){
            if (extraNamespaces!=null){
            return aphia.v1_0.aphiarecords.GetAphiaRecordsDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching(),
            new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(extraNamespaces));
            }else{
            return aphia.v1_0.aphiarecords.GetAphiaRecordsDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching());
            }
            }

        

            if (aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument.class.equals(type)){
            if (extraNamespaces!=null){
            return aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching(),
            new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(extraNamespaces));
            }else{
            return aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching());
            }
            }

        

            if (aphia.v1_0.aphiarecord.GetAphiaRecordByIDDocument.class.equals(type)){
            if (extraNamespaces!=null){
            return aphia.v1_0.aphiarecord.GetAphiaRecordByIDDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching(),
            new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(extraNamespaces));
            }else{
            return aphia.v1_0.aphiarecord.GetAphiaRecordByIDDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching());
            }
            }

        

            if (aphia.v1_0.aphiarecord.GetAphiaRecordByIDResponseDocument.class.equals(type)){
            if (extraNamespaces!=null){
            return aphia.v1_0.aphiarecord.GetAphiaRecordByIDResponseDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching(),
            new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(extraNamespaces));
            }else{
            return aphia.v1_0.aphiarecord.GetAphiaRecordByIDResponseDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching());
            }
            }

        

            if (aphia.v1_0.sources.GetSourcesByAphiaIDDocument.class.equals(type)){
            if (extraNamespaces!=null){
            return aphia.v1_0.sources.GetSourcesByAphiaIDDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching(),
            new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(extraNamespaces));
            }else{
            return aphia.v1_0.sources.GetSourcesByAphiaIDDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching());
            }
            }

        

            if (aphia.v1_0.sources.GetSourcesByAphiaIDResponseDocument.class.equals(type)){
            if (extraNamespaces!=null){
            return aphia.v1_0.sources.GetSourcesByAphiaIDResponseDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching(),
            new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(extraNamespaces));
            }else{
            return aphia.v1_0.sources.GetSourcesByAphiaIDResponseDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching());
            }
            }

        

            if (aphia.v1_0.aphianame.GetAphiaNameByIDDocument.class.equals(type)){
            if (extraNamespaces!=null){
            return aphia.v1_0.aphianame.GetAphiaNameByIDDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching(),
            new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(extraNamespaces));
            }else{
            return aphia.v1_0.aphianame.GetAphiaNameByIDDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching());
            }
            }

        

            if (aphia.v1_0.aphianame.GetAphiaNameByIDResponseDocument.class.equals(type)){
            if (extraNamespaces!=null){
            return aphia.v1_0.aphianame.GetAphiaNameByIDResponseDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching(),
            new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(extraNamespaces));
            }else{
            return aphia.v1_0.aphianame.GetAphiaNameByIDResponseDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching());
            }
            }

        

            if (aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument.class.equals(type)){
            if (extraNamespaces!=null){
            return aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching(),
            new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(extraNamespaces));
            }else{
            return aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching());
            }
            }

        

            if (aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesResponseDocument.class.equals(type)){
            if (extraNamespaces!=null){
            return aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesResponseDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching(),
            new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(extraNamespaces));
            }else{
            return aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesResponseDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching());
            }
            }

        

            if (aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDDocument.class.equals(type)){
            if (extraNamespaces!=null){
            return aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching(),
            new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(extraNamespaces));
            }else{
            return aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching());
            }
            }

        

            if (aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument.class.equals(type)){
            if (extraNamespaces!=null){
            return aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching(),
            new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(extraNamespaces));
            }else{
            return aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching());
            }
            }

        

            if (aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument.class.equals(type)){
            if (extraNamespaces!=null){
            return aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching(),
            new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(extraNamespaces));
            }else{
            return aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching());
            }
            }

        

            if (aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument.class.equals(type)){
            if (extraNamespaces!=null){
            return aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching(),
            new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(extraNamespaces));
            }else{
            return aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching());
            }
            }

        

            if (aphia.v1_0.aphiaid.GetAphiaIDDocument.class.equals(type)){
            if (extraNamespaces!=null){
            return aphia.v1_0.aphiaid.GetAphiaIDDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching(),
            new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(extraNamespaces));
            }else{
            return aphia.v1_0.aphiaid.GetAphiaIDDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching());
            }
            }

        

            if (aphia.v1_0.aphiaid.GetAphiaIDResponseDocument.class.equals(type)){
            if (extraNamespaces!=null){
            return aphia.v1_0.aphiaid.GetAphiaIDResponseDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching(),
            new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(extraNamespaces));
            }else{
            return aphia.v1_0.aphiaid.GetAphiaIDResponseDocument.Factory.parse(
            param.getXMLStreamReaderWithoutCaching());
            }
            }

        
        }catch(java.lang.Exception e){
        throw org.apache.axis2.AxisFault.makeFault(e);
        }
        return null;
        }

        
        
   }
   