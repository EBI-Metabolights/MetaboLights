/*
 * An XML document type.
 * Localname: getAphiaRecordByIDResponse
 * Namespace: http://aphia/v1.0/AphiaRecord
 * Java type: aphia.v1_0.aphiarecord.GetAphiaRecordByIDResponseDocument
 *
 * Automatically generated - do not modify.
 */
package aphia.v1_0.aphiarecord.impl;
/**
 * A document containing one getAphiaRecordByIDResponse(@http://aphia/v1.0/AphiaRecord) element.
 *
 * This is a complex type.
 */
public class GetAphiaRecordByIDResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.aphiarecord.GetAphiaRecordByIDResponseDocument
{
    
    public GetAphiaRecordByIDResponseDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName GETAPHIARECORDBYIDRESPONSE$0 = 
        new javax.xml.namespace.QName("http://aphia/v1.0/AphiaRecord", "getAphiaRecordByIDResponse");
    
    
    /**
     * Gets the "getAphiaRecordByIDResponse" element
     */
    public aphia.v1_0.aphiarecord.GetAphiaRecordByIDResponseDocument.GetAphiaRecordByIDResponse getGetAphiaRecordByIDResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiarecord.GetAphiaRecordByIDResponseDocument.GetAphiaRecordByIDResponse target = null;
            target = (aphia.v1_0.aphiarecord.GetAphiaRecordByIDResponseDocument.GetAphiaRecordByIDResponse)get_store().find_element_user(GETAPHIARECORDBYIDRESPONSE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "getAphiaRecordByIDResponse" element
     */
    public void setGetAphiaRecordByIDResponse(aphia.v1_0.aphiarecord.GetAphiaRecordByIDResponseDocument.GetAphiaRecordByIDResponse getAphiaRecordByIDResponse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiarecord.GetAphiaRecordByIDResponseDocument.GetAphiaRecordByIDResponse target = null;
            target = (aphia.v1_0.aphiarecord.GetAphiaRecordByIDResponseDocument.GetAphiaRecordByIDResponse)get_store().find_element_user(GETAPHIARECORDBYIDRESPONSE$0, 0);
            if (target == null)
            {
                target = (aphia.v1_0.aphiarecord.GetAphiaRecordByIDResponseDocument.GetAphiaRecordByIDResponse)get_store().add_element_user(GETAPHIARECORDBYIDRESPONSE$0);
            }
            target.set(getAphiaRecordByIDResponse);
        }
    }
    
    /**
     * Appends and returns a new empty "getAphiaRecordByIDResponse" element
     */
    public aphia.v1_0.aphiarecord.GetAphiaRecordByIDResponseDocument.GetAphiaRecordByIDResponse addNewGetAphiaRecordByIDResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiarecord.GetAphiaRecordByIDResponseDocument.GetAphiaRecordByIDResponse target = null;
            target = (aphia.v1_0.aphiarecord.GetAphiaRecordByIDResponseDocument.GetAphiaRecordByIDResponse)get_store().add_element_user(GETAPHIARECORDBYIDRESPONSE$0);
            return target;
        }
    }
    /**
     * An XML getAphiaRecordByIDResponse(@http://aphia/v1.0/AphiaRecord).
     *
     * This is a complex type.
     */
    public static class GetAphiaRecordByIDResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.aphiarecord.GetAphiaRecordByIDResponseDocument.GetAphiaRecordByIDResponse
    {
        
        public GetAphiaRecordByIDResponseImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName RETURN$0 = 
            new javax.xml.namespace.QName("", "return");
        
        
        /**
         * Gets the "return" element
         */
        public aphia.v1_0.AphiaRecord getReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                aphia.v1_0.AphiaRecord target = null;
                target = (aphia.v1_0.AphiaRecord)get_store().find_element_user(RETURN$0, 0);
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
                aphia.v1_0.AphiaRecord target = null;
                target = (aphia.v1_0.AphiaRecord)get_store().find_element_user(RETURN$0, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * Sets the "return" element
         */
        public void setReturn(aphia.v1_0.AphiaRecord xreturn)
        {
            synchronized (monitor())
            {
                check_orphaned();
                aphia.v1_0.AphiaRecord target = null;
                target = (aphia.v1_0.AphiaRecord)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    target = (aphia.v1_0.AphiaRecord)get_store().add_element_user(RETURN$0);
                }
                target.set(xreturn);
            }
        }
        
        /**
         * Appends and returns a new empty "return" element
         */
        public aphia.v1_0.AphiaRecord addNewReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                aphia.v1_0.AphiaRecord target = null;
                target = (aphia.v1_0.AphiaRecord)get_store().add_element_user(RETURN$0);
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
                aphia.v1_0.AphiaRecord target = null;
                target = (aphia.v1_0.AphiaRecord)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    target = (aphia.v1_0.AphiaRecord)get_store().add_element_user(RETURN$0);
                }
                target.setNil();
            }
        }
    }
}
