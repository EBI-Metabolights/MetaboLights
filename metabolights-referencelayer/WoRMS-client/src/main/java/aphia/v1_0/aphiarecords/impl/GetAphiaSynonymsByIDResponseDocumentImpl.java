/*
 * An XML document type.
 * Localname: getAphiaSynonymsByIDResponse
 * Namespace: http://aphia/v1.0/AphiaRecords
 * Java type: aphia.v1_0.aphiarecords.GetAphiaSynonymsByIDResponseDocument
 *
 * Automatically generated - do not modify.
 */
package aphia.v1_0.aphiarecords.impl;
/**
 * A document containing one getAphiaSynonymsByIDResponse(@http://aphia/v1.0/AphiaRecords) element.
 *
 * This is a complex type.
 */
public class GetAphiaSynonymsByIDResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.aphiarecords.GetAphiaSynonymsByIDResponseDocument
{
    
    public GetAphiaSynonymsByIDResponseDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName GETAPHIASYNONYMSBYIDRESPONSE$0 = 
        new javax.xml.namespace.QName("http://aphia/v1.0/AphiaRecords", "getAphiaSynonymsByIDResponse");
    
    
    /**
     * Gets the "getAphiaSynonymsByIDResponse" element
     */
    public aphia.v1_0.aphiarecords.GetAphiaSynonymsByIDResponseDocument.GetAphiaSynonymsByIDResponse getGetAphiaSynonymsByIDResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiarecords.GetAphiaSynonymsByIDResponseDocument.GetAphiaSynonymsByIDResponse target = null;
            target = (aphia.v1_0.aphiarecords.GetAphiaSynonymsByIDResponseDocument.GetAphiaSynonymsByIDResponse)get_store().find_element_user(GETAPHIASYNONYMSBYIDRESPONSE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "getAphiaSynonymsByIDResponse" element
     */
    public void setGetAphiaSynonymsByIDResponse(aphia.v1_0.aphiarecords.GetAphiaSynonymsByIDResponseDocument.GetAphiaSynonymsByIDResponse getAphiaSynonymsByIDResponse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiarecords.GetAphiaSynonymsByIDResponseDocument.GetAphiaSynonymsByIDResponse target = null;
            target = (aphia.v1_0.aphiarecords.GetAphiaSynonymsByIDResponseDocument.GetAphiaSynonymsByIDResponse)get_store().find_element_user(GETAPHIASYNONYMSBYIDRESPONSE$0, 0);
            if (target == null)
            {
                target = (aphia.v1_0.aphiarecords.GetAphiaSynonymsByIDResponseDocument.GetAphiaSynonymsByIDResponse)get_store().add_element_user(GETAPHIASYNONYMSBYIDRESPONSE$0);
            }
            target.set(getAphiaSynonymsByIDResponse);
        }
    }
    
    /**
     * Appends and returns a new empty "getAphiaSynonymsByIDResponse" element
     */
    public aphia.v1_0.aphiarecords.GetAphiaSynonymsByIDResponseDocument.GetAphiaSynonymsByIDResponse addNewGetAphiaSynonymsByIDResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiarecords.GetAphiaSynonymsByIDResponseDocument.GetAphiaSynonymsByIDResponse target = null;
            target = (aphia.v1_0.aphiarecords.GetAphiaSynonymsByIDResponseDocument.GetAphiaSynonymsByIDResponse)get_store().add_element_user(GETAPHIASYNONYMSBYIDRESPONSE$0);
            return target;
        }
    }
    /**
     * An XML getAphiaSynonymsByIDResponse(@http://aphia/v1.0/AphiaRecords).
     *
     * This is a complex type.
     */
    public static class GetAphiaSynonymsByIDResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.aphiarecords.GetAphiaSynonymsByIDResponseDocument.GetAphiaSynonymsByIDResponse
    {
        
        public GetAphiaSynonymsByIDResponseImpl(org.apache.xmlbeans.SchemaType sType)
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
