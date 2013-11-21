/*
 * An XML document type.
 * Localname: getAphiaNameByIDResponse
 * Namespace: http://aphia/v1.0/AphiaName
 * Java type: aphia.v1_0.aphianame.GetAphiaNameByIDResponseDocument
 *
 * Automatically generated - do not modify.
 */
package aphia.v1_0.aphianame.impl;
/**
 * A document containing one getAphiaNameByIDResponse(@http://aphia/v1.0/AphiaName) element.
 *
 * This is a complex type.
 */
public class GetAphiaNameByIDResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.aphianame.GetAphiaNameByIDResponseDocument
{
    
    public GetAphiaNameByIDResponseDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName GETAPHIANAMEBYIDRESPONSE$0 = 
        new javax.xml.namespace.QName("http://aphia/v1.0/AphiaName", "getAphiaNameByIDResponse");
    
    
    /**
     * Gets the "getAphiaNameByIDResponse" element
     */
    public aphia.v1_0.aphianame.GetAphiaNameByIDResponseDocument.GetAphiaNameByIDResponse getGetAphiaNameByIDResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphianame.GetAphiaNameByIDResponseDocument.GetAphiaNameByIDResponse target = null;
            target = (aphia.v1_0.aphianame.GetAphiaNameByIDResponseDocument.GetAphiaNameByIDResponse)get_store().find_element_user(GETAPHIANAMEBYIDRESPONSE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "getAphiaNameByIDResponse" element
     */
    public void setGetAphiaNameByIDResponse(aphia.v1_0.aphianame.GetAphiaNameByIDResponseDocument.GetAphiaNameByIDResponse getAphiaNameByIDResponse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphianame.GetAphiaNameByIDResponseDocument.GetAphiaNameByIDResponse target = null;
            target = (aphia.v1_0.aphianame.GetAphiaNameByIDResponseDocument.GetAphiaNameByIDResponse)get_store().find_element_user(GETAPHIANAMEBYIDRESPONSE$0, 0);
            if (target == null)
            {
                target = (aphia.v1_0.aphianame.GetAphiaNameByIDResponseDocument.GetAphiaNameByIDResponse)get_store().add_element_user(GETAPHIANAMEBYIDRESPONSE$0);
            }
            target.set(getAphiaNameByIDResponse);
        }
    }
    
    /**
     * Appends and returns a new empty "getAphiaNameByIDResponse" element
     */
    public aphia.v1_0.aphianame.GetAphiaNameByIDResponseDocument.GetAphiaNameByIDResponse addNewGetAphiaNameByIDResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphianame.GetAphiaNameByIDResponseDocument.GetAphiaNameByIDResponse target = null;
            target = (aphia.v1_0.aphianame.GetAphiaNameByIDResponseDocument.GetAphiaNameByIDResponse)get_store().add_element_user(GETAPHIANAMEBYIDRESPONSE$0);
            return target;
        }
    }
    /**
     * An XML getAphiaNameByIDResponse(@http://aphia/v1.0/AphiaName).
     *
     * This is a complex type.
     */
    public static class GetAphiaNameByIDResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.aphianame.GetAphiaNameByIDResponseDocument.GetAphiaNameByIDResponse
    {
        
        public GetAphiaNameByIDResponseImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName RETURN$0 = 
            new javax.xml.namespace.QName("", "return");
        
        
        /**
         * Gets the "return" element
         */
        public java.lang.String getReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "return" element
         */
        public org.apache.xmlbeans.XmlString xgetReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RETURN$0, 0);
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
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RETURN$0, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * Sets the "return" element
         */
        public void setReturn(java.lang.String xreturn)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(RETURN$0);
                }
                target.setStringValue(xreturn);
            }
        }
        
        /**
         * Sets (as xml) the "return" element
         */
        public void xsetReturn(org.apache.xmlbeans.XmlString xreturn)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(RETURN$0);
                }
                target.set(xreturn);
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
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(RETURN$0);
                }
                target.setNil();
            }
        }
    }
}
