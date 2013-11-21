
/**
 * AphiaNameServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package uk.ac.ebi.metabolights.referencelayer.worms.client;

    /**
     *  AphiaNameServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class AphiaNameServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public AphiaNameServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public AphiaNameServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for getExtIDbyAphiaID method
            * override this method for handling normal response from getExtIDbyAphiaID operation
            */
           public void receiveResultgetExtIDbyAphiaID(
                    aphia.v1_0.externalidentifiers.GetExtIDbyAphiaIDResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getExtIDbyAphiaID operation
           */
            public void receiveErrorgetExtIDbyAphiaID(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getAphiaRecordsByVernacular method
            * override this method for handling normal response from getAphiaRecordsByVernacular operation
            */
           public void receiveResultgetAphiaRecordsByVernacular(
                    aphia.v1_0.aphiarecords.GetAphiaRecordsByVernacularResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getAphiaRecordsByVernacular operation
           */
            public void receiveErrorgetAphiaRecordsByVernacular(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getAphiaChildrenByID method
            * override this method for handling normal response from getAphiaChildrenByID operation
            */
           public void receiveResultgetAphiaChildrenByID(
                    aphia.v1_0.aphiarecords.GetAphiaChildrenByIDResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getAphiaChildrenByID operation
           */
            public void receiveErrorgetAphiaChildrenByID(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getAphiaClassificationByID method
            * override this method for handling normal response from getAphiaClassificationByID operation
            */
           public void receiveResultgetAphiaClassificationByID(
                    aphia.v1_0.classification.GetAphiaClassificationByIDResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getAphiaClassificationByID operation
           */
            public void receiveErrorgetAphiaClassificationByID(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for matchAphiaRecordsByNames method
            * override this method for handling normal response from matchAphiaRecordsByNames operation
            */
           public void receiveResultmatchAphiaRecordsByNames(
                    aphia.v1_0.aphiamatches.MatchAphiaRecordsByNamesResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from matchAphiaRecordsByNames operation
           */
            public void receiveErrormatchAphiaRecordsByNames(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getAphiaRecordsByDate method
            * override this method for handling normal response from getAphiaRecordsByDate operation
            */
           public void receiveResultgetAphiaRecordsByDate(
                    aphia.v1_0.aphiarecords.GetAphiaRecordsByDateResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getAphiaRecordsByDate operation
           */
            public void receiveErrorgetAphiaRecordsByDate(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getAphiaSynonymsByID method
            * override this method for handling normal response from getAphiaSynonymsByID operation
            */
           public void receiveResultgetAphiaSynonymsByID(
                    aphia.v1_0.aphiarecords.GetAphiaSynonymsByIDResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getAphiaSynonymsByID operation
           */
            public void receiveErrorgetAphiaSynonymsByID(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getAphiaRecords method
            * override this method for handling normal response from getAphiaRecords operation
            */
           public void receiveResultgetAphiaRecords(
                    aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getAphiaRecords operation
           */
            public void receiveErrorgetAphiaRecords(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getAphiaRecordByID method
            * override this method for handling normal response from getAphiaRecordByID operation
            */
           public void receiveResultgetAphiaRecordByID(
                    aphia.v1_0.aphiarecord.GetAphiaRecordByIDResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getAphiaRecordByID operation
           */
            public void receiveErrorgetAphiaRecordByID(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getSourcesByAphiaID method
            * override this method for handling normal response from getSourcesByAphiaID operation
            */
           public void receiveResultgetSourcesByAphiaID(
                    aphia.v1_0.sources.GetSourcesByAphiaIDResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getSourcesByAphiaID operation
           */
            public void receiveErrorgetSourcesByAphiaID(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getAphiaNameByID method
            * override this method for handling normal response from getAphiaNameByID operation
            */
           public void receiveResultgetAphiaNameByID(
                    aphia.v1_0.aphianame.GetAphiaNameByIDResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getAphiaNameByID operation
           */
            public void receiveErrorgetAphiaNameByID(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getAphiaRecordsByNames method
            * override this method for handling normal response from getAphiaRecordsByNames operation
            */
           public void receiveResultgetAphiaRecordsByNames(
                    aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getAphiaRecordsByNames operation
           */
            public void receiveErrorgetAphiaRecordsByNames(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getAphiaRecordByExtID method
            * override this method for handling normal response from getAphiaRecordByExtID operation
            */
           public void receiveResultgetAphiaRecordByExtID(
                    aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getAphiaRecordByExtID operation
           */
            public void receiveErrorgetAphiaRecordByExtID(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getAphiaVernacularsByID method
            * override this method for handling normal response from getAphiaVernacularsByID operation
            */
           public void receiveResultgetAphiaVernacularsByID(
                    aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getAphiaVernacularsByID operation
           */
            public void receiveErrorgetAphiaVernacularsByID(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getAphiaID method
            * override this method for handling normal response from getAphiaID operation
            */
           public void receiveResultgetAphiaID(
                    aphia.v1_0.aphiaid.GetAphiaIDResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getAphiaID operation
           */
            public void receiveErrorgetAphiaID(java.lang.Exception e) {
            }
                


    }
    