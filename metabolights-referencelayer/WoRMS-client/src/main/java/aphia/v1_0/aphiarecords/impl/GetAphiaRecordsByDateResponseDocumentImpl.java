/*
 * An XML document type.
 * Localname: getAphiaRecordsByDateResponse
 * Namespace: http://aphia/v1.0/AphiaRecords
 * Java type: aphia.v1_0.aphiarecords.GetAphiaRecordsByDateResponseDocument
 *
 * Automatically generated - do not modify.
 */
package aphia.v1_0.aphiarecords.impl;
/**
 * A document containing one getAphiaRecordsByDateResponse(@http://aphia/v1.0/AphiaRecords) element.
 *
 * This is a complex type.
 */
public class GetAphiaRecordsByDateResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.aphiarecords.GetAphiaRecordsByDateResponseDocument
{
    
    public GetAphiaRecordsByDateResponseDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName GETAPHIARECORDSBYDATERESPONSE$0 = 
        new javax.xml.namespace.QName("http://aphia/v1.0/AphiaRecords", "getAphiaRecordsByDateResponse");
    
    
    /**
     * Gets the "getAphiaRecordsByDateResponse" element
     */
    public aphia.v1_0.aphiarecords.GetAphiaRecordsByDateResponseDocument.GetAphiaRecordsByDateResponse getGetAphiaRecordsByDateResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiarecords.GetAphiaRecordsByDateResponseDocument.GetAphiaRecordsByDateResponse target = null;
            target = (aphia.v1_0.aphiarecords.GetAphiaRecordsByDateResponseDocument.GetAphiaRecordsByDateResponse)get_store().find_element_user(GETAPHIARECORDSBYDATERESPONSE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "getAphiaRecordsByDateResponse" element
     */
    public void setGetAphiaRecordsByDateResponse(aphia.v1_0.aphiarecords.GetAphiaRecordsByDateResponseDocument.GetAphiaRecordsByDateResponse getAphiaRecordsByDateResponse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiarecords.GetAphiaRecordsByDateResponseDocument.GetAphiaRecordsByDateResponse target = null;
            target = (aphia.v1_0.aphiarecords.GetAphiaRecordsByDateResponseDocument.GetAphiaRecordsByDateResponse)get_store().find_element_user(GETAPHIARECORDSBYDATERESPONSE$0, 0);
            if (target == null)
            {
                target = (aphia.v1_0.aphiarecords.GetAphiaRecordsByDateResponseDocument.GetAphiaRecordsByDateResponse)get_store().add_element_user(GETAPHIARECORDSBYDATERESPONSE$0);
            }
            target.set(getAphiaRecordsByDateResponse);
        }
    }
    
    /**
     * Appends and returns a new empty "getAphiaRecordsByDateResponse" element
     */
    public aphia.v1_0.aphiarecords.GetAphiaRecordsByDateResponseDocument.GetAphiaRecordsByDateResponse addNewGetAphiaRecordsByDateResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiarecords.GetAphiaRecordsByDateResponseDocument.GetAphiaRecordsByDateResponse target = null;
            target = (aphia.v1_0.aphiarecords.GetAphiaRecordsByDateResponseDocument.GetAphiaRecordsByDateResponse)get_store().add_element_user(GETAPHIARECORDSBYDATERESPONSE$0);
            return target;
        }
    }
    /**
     * An XML getAphiaRecordsByDateResponse(@http://aphia/v1.0/AphiaRecords).
     *
     * This is a complex type.
     */
    public static class GetAphiaRecordsByDateResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.aphiarecords.GetAphiaRecordsByDateResponseDocument.GetAphiaRecordsByDateResponse
    {
        
        public GetAphiaRecordsByDateResponseImpl(org.apache.xmlbeans.SchemaType sType)
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
