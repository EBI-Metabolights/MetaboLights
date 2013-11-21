/*
 * An XML document type.
 * Localname: getAphiaRecordByExtIDResponse
 * Namespace: http://aphia/v1.0/AphiaRecord
 * Java type: aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument
 *
 * Automatically generated - do not modify.
 */
package aphia.v1_0.aphiarecord.impl;
/**
 * A document containing one getAphiaRecordByExtIDResponse(@http://aphia/v1.0/AphiaRecord) element.
 *
 * This is a complex type.
 */
public class GetAphiaRecordByExtIDResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument
{
    
    public GetAphiaRecordByExtIDResponseDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName GETAPHIARECORDBYEXTIDRESPONSE$0 = 
        new javax.xml.namespace.QName("http://aphia/v1.0/AphiaRecord", "getAphiaRecordByExtIDResponse");
    
    
    /**
     * Gets the "getAphiaRecordByExtIDResponse" element
     */
    public aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument.GetAphiaRecordByExtIDResponse getGetAphiaRecordByExtIDResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument.GetAphiaRecordByExtIDResponse target = null;
            target = (aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument.GetAphiaRecordByExtIDResponse)get_store().find_element_user(GETAPHIARECORDBYEXTIDRESPONSE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "getAphiaRecordByExtIDResponse" element
     */
    public void setGetAphiaRecordByExtIDResponse(aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument.GetAphiaRecordByExtIDResponse getAphiaRecordByExtIDResponse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument.GetAphiaRecordByExtIDResponse target = null;
            target = (aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument.GetAphiaRecordByExtIDResponse)get_store().find_element_user(GETAPHIARECORDBYEXTIDRESPONSE$0, 0);
            if (target == null)
            {
                target = (aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument.GetAphiaRecordByExtIDResponse)get_store().add_element_user(GETAPHIARECORDBYEXTIDRESPONSE$0);
            }
            target.set(getAphiaRecordByExtIDResponse);
        }
    }
    
    /**
     * Appends and returns a new empty "getAphiaRecordByExtIDResponse" element
     */
    public aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument.GetAphiaRecordByExtIDResponse addNewGetAphiaRecordByExtIDResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument.GetAphiaRecordByExtIDResponse target = null;
            target = (aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument.GetAphiaRecordByExtIDResponse)get_store().add_element_user(GETAPHIARECORDBYEXTIDRESPONSE$0);
            return target;
        }
    }
    /**
     * An XML getAphiaRecordByExtIDResponse(@http://aphia/v1.0/AphiaRecord).
     *
     * This is a complex type.
     */
    public static class GetAphiaRecordByExtIDResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument.GetAphiaRecordByExtIDResponse
    {
        
        public GetAphiaRecordByExtIDResponseImpl(org.apache.xmlbeans.SchemaType sType)
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
