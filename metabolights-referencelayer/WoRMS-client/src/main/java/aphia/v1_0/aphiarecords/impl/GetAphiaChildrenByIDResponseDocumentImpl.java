/*
 * An XML document type.
 * Localname: getAphiaChildrenByIDResponse
 * Namespace: http://aphia/v1.0/AphiaRecords
 * Java type: aphia.v1_0.aphiarecords.GetAphiaChildrenByIDResponseDocument
 *
 * Automatically generated - do not modify.
 */
package aphia.v1_0.aphiarecords.impl;
/**
 * A document containing one getAphiaChildrenByIDResponse(@http://aphia/v1.0/AphiaRecords) element.
 *
 * This is a complex type.
 */
public class GetAphiaChildrenByIDResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.aphiarecords.GetAphiaChildrenByIDResponseDocument
{
    
    public GetAphiaChildrenByIDResponseDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName GETAPHIACHILDRENBYIDRESPONSE$0 = 
        new javax.xml.namespace.QName("http://aphia/v1.0/AphiaRecords", "getAphiaChildrenByIDResponse");
    
    
    /**
     * Gets the "getAphiaChildrenByIDResponse" element
     */
    public aphia.v1_0.aphiarecords.GetAphiaChildrenByIDResponseDocument.GetAphiaChildrenByIDResponse getGetAphiaChildrenByIDResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiarecords.GetAphiaChildrenByIDResponseDocument.GetAphiaChildrenByIDResponse target = null;
            target = (aphia.v1_0.aphiarecords.GetAphiaChildrenByIDResponseDocument.GetAphiaChildrenByIDResponse)get_store().find_element_user(GETAPHIACHILDRENBYIDRESPONSE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "getAphiaChildrenByIDResponse" element
     */
    public void setGetAphiaChildrenByIDResponse(aphia.v1_0.aphiarecords.GetAphiaChildrenByIDResponseDocument.GetAphiaChildrenByIDResponse getAphiaChildrenByIDResponse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiarecords.GetAphiaChildrenByIDResponseDocument.GetAphiaChildrenByIDResponse target = null;
            target = (aphia.v1_0.aphiarecords.GetAphiaChildrenByIDResponseDocument.GetAphiaChildrenByIDResponse)get_store().find_element_user(GETAPHIACHILDRENBYIDRESPONSE$0, 0);
            if (target == null)
            {
                target = (aphia.v1_0.aphiarecords.GetAphiaChildrenByIDResponseDocument.GetAphiaChildrenByIDResponse)get_store().add_element_user(GETAPHIACHILDRENBYIDRESPONSE$0);
            }
            target.set(getAphiaChildrenByIDResponse);
        }
    }
    
    /**
     * Appends and returns a new empty "getAphiaChildrenByIDResponse" element
     */
    public aphia.v1_0.aphiarecords.GetAphiaChildrenByIDResponseDocument.GetAphiaChildrenByIDResponse addNewGetAphiaChildrenByIDResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiarecords.GetAphiaChildrenByIDResponseDocument.GetAphiaChildrenByIDResponse target = null;
            target = (aphia.v1_0.aphiarecords.GetAphiaChildrenByIDResponseDocument.GetAphiaChildrenByIDResponse)get_store().add_element_user(GETAPHIACHILDRENBYIDRESPONSE$0);
            return target;
        }
    }
    /**
     * An XML getAphiaChildrenByIDResponse(@http://aphia/v1.0/AphiaRecords).
     *
     * This is a complex type.
     */
    public static class GetAphiaChildrenByIDResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.aphiarecords.GetAphiaChildrenByIDResponseDocument.GetAphiaChildrenByIDResponse
    {
        
        public GetAphiaChildrenByIDResponseImpl(org.apache.xmlbeans.SchemaType sType)
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
