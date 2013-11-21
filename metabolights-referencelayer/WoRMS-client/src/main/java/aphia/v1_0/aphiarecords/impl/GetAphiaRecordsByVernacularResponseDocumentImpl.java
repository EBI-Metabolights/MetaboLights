/*
 * An XML document type.
 * Localname: getAphiaRecordsByVernacularResponse
 * Namespace: http://aphia/v1.0/AphiaRecords
 * Java type: aphia.v1_0.aphiarecords.GetAphiaRecordsByVernacularResponseDocument
 *
 * Automatically generated - do not modify.
 */
package aphia.v1_0.aphiarecords.impl;
/**
 * A document containing one getAphiaRecordsByVernacularResponse(@http://aphia/v1.0/AphiaRecords) element.
 *
 * This is a complex type.
 */
public class GetAphiaRecordsByVernacularResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.aphiarecords.GetAphiaRecordsByVernacularResponseDocument
{
    
    public GetAphiaRecordsByVernacularResponseDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName GETAPHIARECORDSBYVERNACULARRESPONSE$0 = 
        new javax.xml.namespace.QName("http://aphia/v1.0/AphiaRecords", "getAphiaRecordsByVernacularResponse");
    
    
    /**
     * Gets the "getAphiaRecordsByVernacularResponse" element
     */
    public aphia.v1_0.aphiarecords.GetAphiaRecordsByVernacularResponseDocument.GetAphiaRecordsByVernacularResponse getGetAphiaRecordsByVernacularResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiarecords.GetAphiaRecordsByVernacularResponseDocument.GetAphiaRecordsByVernacularResponse target = null;
            target = (aphia.v1_0.aphiarecords.GetAphiaRecordsByVernacularResponseDocument.GetAphiaRecordsByVernacularResponse)get_store().find_element_user(GETAPHIARECORDSBYVERNACULARRESPONSE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "getAphiaRecordsByVernacularResponse" element
     */
    public void setGetAphiaRecordsByVernacularResponse(aphia.v1_0.aphiarecords.GetAphiaRecordsByVernacularResponseDocument.GetAphiaRecordsByVernacularResponse getAphiaRecordsByVernacularResponse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiarecords.GetAphiaRecordsByVernacularResponseDocument.GetAphiaRecordsByVernacularResponse target = null;
            target = (aphia.v1_0.aphiarecords.GetAphiaRecordsByVernacularResponseDocument.GetAphiaRecordsByVernacularResponse)get_store().find_element_user(GETAPHIARECORDSBYVERNACULARRESPONSE$0, 0);
            if (target == null)
            {
                target = (aphia.v1_0.aphiarecords.GetAphiaRecordsByVernacularResponseDocument.GetAphiaRecordsByVernacularResponse)get_store().add_element_user(GETAPHIARECORDSBYVERNACULARRESPONSE$0);
            }
            target.set(getAphiaRecordsByVernacularResponse);
        }
    }
    
    /**
     * Appends and returns a new empty "getAphiaRecordsByVernacularResponse" element
     */
    public aphia.v1_0.aphiarecords.GetAphiaRecordsByVernacularResponseDocument.GetAphiaRecordsByVernacularResponse addNewGetAphiaRecordsByVernacularResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiarecords.GetAphiaRecordsByVernacularResponseDocument.GetAphiaRecordsByVernacularResponse target = null;
            target = (aphia.v1_0.aphiarecords.GetAphiaRecordsByVernacularResponseDocument.GetAphiaRecordsByVernacularResponse)get_store().add_element_user(GETAPHIARECORDSBYVERNACULARRESPONSE$0);
            return target;
        }
    }
    /**
     * An XML getAphiaRecordsByVernacularResponse(@http://aphia/v1.0/AphiaRecords).
     *
     * This is a complex type.
     */
    public static class GetAphiaRecordsByVernacularResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.aphiarecords.GetAphiaRecordsByVernacularResponseDocument.GetAphiaRecordsByVernacularResponse
    {
        
        public GetAphiaRecordsByVernacularResponseImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName RETURN$0 = 
            new javax.xml.namespace.QName("", "return");
        
        
        /**
         * Gets the "return" element
         */
        public aphia.v1_0.AphiaRecords getReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                aphia.v1_0.AphiaRecords target = null;
                target = (aphia.v1_0.AphiaRecords)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    return null;
                }
                return target;
            }
        }
        
        /**
         * Tests for nil "return" element
         */
        public boolean isNilReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                aphia.v1_0.AphiaRecords target = null;
                target = (aphia.v1_0.AphiaRecords)get_store().find_element_user(RETURN$0, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * Sets the "return" element
         */
        public void setReturn(aphia.v1_0.AphiaRecords xreturn)
        {
            synchronized (monitor())
            {
                check_orphaned();
                aphia.v1_0.AphiaRecords target = null;
                target = (aphia.v1_0.AphiaRecords)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    target = (aphia.v1_0.AphiaRecords)get_store().add_element_user(RETURN$0);
                }
                target.set(xreturn);
            }
        }
        
        /**
         * Appends and returns a new empty "return" element
         */
        public aphia.v1_0.AphiaRecords addNewReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                aphia.v1_0.AphiaRecords target = null;
                target = (aphia.v1_0.AphiaRecords)get_store().add_element_user(RETURN$0);
                return target;
            }
        }
        
        /**
         * Nils the "return" element
         */
        public void setNilReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                aphia.v1_0.AphiaRecords target = null;
                target = (aphia.v1_0.AphiaRecords)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    target = (aphia.v1_0.AphiaRecords)get_store().add_element_user(RETURN$0);
                }
                target.setNil();
            }
        }
    }
}
