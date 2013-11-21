/*
 * An XML document type.
 * Localname: getAphiaRecordsResponse
 * Namespace: http://aphia/v1.0/AphiaRecords
 * Java type: aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument
 *
 * Automatically generated - do not modify.
 */
package aphia.v1_0.aphiarecords.impl;
/**
 * A document containing one getAphiaRecordsResponse(@http://aphia/v1.0/AphiaRecords) element.
 *
 * This is a complex type.
 */
public class GetAphiaRecordsResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument
{
    
    public GetAphiaRecordsResponseDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName GETAPHIARECORDSRESPONSE$0 = 
        new javax.xml.namespace.QName("http://aphia/v1.0/AphiaRecords", "getAphiaRecordsResponse");
    
    
    /**
     * Gets the "getAphiaRecordsResponse" element
     */
    public aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument.GetAphiaRecordsResponse getGetAphiaRecordsResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument.GetAphiaRecordsResponse target = null;
            target = (aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument.GetAphiaRecordsResponse)get_store().find_element_user(GETAPHIARECORDSRESPONSE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "getAphiaRecordsResponse" element
     */
    public void setGetAphiaRecordsResponse(aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument.GetAphiaRecordsResponse getAphiaRecordsResponse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument.GetAphiaRecordsResponse target = null;
            target = (aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument.GetAphiaRecordsResponse)get_store().find_element_user(GETAPHIARECORDSRESPONSE$0, 0);
            if (target == null)
            {
                target = (aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument.GetAphiaRecordsResponse)get_store().add_element_user(GETAPHIARECORDSRESPONSE$0);
            }
            target.set(getAphiaRecordsResponse);
        }
    }
    
    /**
     * Appends and returns a new empty "getAphiaRecordsResponse" element
     */
    public aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument.GetAphiaRecordsResponse addNewGetAphiaRecordsResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument.GetAphiaRecordsResponse target = null;
            target = (aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument.GetAphiaRecordsResponse)get_store().add_element_user(GETAPHIARECORDSRESPONSE$0);
            return target;
        }
    }
    /**
     * An XML getAphiaRecordsResponse(@http://aphia/v1.0/AphiaRecords).
     *
     * This is a complex type.
     */
    public static class GetAphiaRecordsResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument.GetAphiaRecordsResponse
    {
        
        public GetAphiaRecordsResponseImpl(org.apache.xmlbeans.SchemaType sType)
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
