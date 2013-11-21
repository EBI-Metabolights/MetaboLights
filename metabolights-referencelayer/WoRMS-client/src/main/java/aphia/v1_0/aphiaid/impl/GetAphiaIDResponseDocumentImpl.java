/*
 * An XML document type.
 * Localname: getAphiaIDResponse
 * Namespace: http://aphia/v1.0/AphiaID
 * Java type: aphia.v1_0.aphiaid.GetAphiaIDResponseDocument
 *
 * Automatically generated - do not modify.
 */
package aphia.v1_0.aphiaid.impl;
/**
 * A document containing one getAphiaIDResponse(@http://aphia/v1.0/AphiaID) element.
 *
 * This is a complex type.
 */
public class GetAphiaIDResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.aphiaid.GetAphiaIDResponseDocument
{
    
    public GetAphiaIDResponseDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName GETAPHIAIDRESPONSE$0 = 
        new javax.xml.namespace.QName("http://aphia/v1.0/AphiaID", "getAphiaIDResponse");
    
    
    /**
     * Gets the "getAphiaIDResponse" element
     */
    public aphia.v1_0.aphiaid.GetAphiaIDResponseDocument.GetAphiaIDResponse getGetAphiaIDResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiaid.GetAphiaIDResponseDocument.GetAphiaIDResponse target = null;
            target = (aphia.v1_0.aphiaid.GetAphiaIDResponseDocument.GetAphiaIDResponse)get_store().find_element_user(GETAPHIAIDRESPONSE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "getAphiaIDResponse" element
     */
    public void setGetAphiaIDResponse(aphia.v1_0.aphiaid.GetAphiaIDResponseDocument.GetAphiaIDResponse getAphiaIDResponse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiaid.GetAphiaIDResponseDocument.GetAphiaIDResponse target = null;
            target = (aphia.v1_0.aphiaid.GetAphiaIDResponseDocument.GetAphiaIDResponse)get_store().find_element_user(GETAPHIAIDRESPONSE$0, 0);
            if (target == null)
            {
                target = (aphia.v1_0.aphiaid.GetAphiaIDResponseDocument.GetAphiaIDResponse)get_store().add_element_user(GETAPHIAIDRESPONSE$0);
            }
            target.set(getAphiaIDResponse);
        }
    }
    
    /**
     * Appends and returns a new empty "getAphiaIDResponse" element
     */
    public aphia.v1_0.aphiaid.GetAphiaIDResponseDocument.GetAphiaIDResponse addNewGetAphiaIDResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiaid.GetAphiaIDResponseDocument.GetAphiaIDResponse target = null;
            target = (aphia.v1_0.aphiaid.GetAphiaIDResponseDocument.GetAphiaIDResponse)get_store().add_element_user(GETAPHIAIDRESPONSE$0);
            return target;
        }
    }
    /**
     * An XML getAphiaIDResponse(@http://aphia/v1.0/AphiaID).
     *
     * This is a complex type.
     */
    public static class GetAphiaIDResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.aphiaid.GetAphiaIDResponseDocument.GetAphiaIDResponse
    {
        
        public GetAphiaIDResponseImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName RETURN$0 = 
            new javax.xml.namespace.QName("", "return");
        
        
        /**
         * Gets the "return" element
         */
        public int getReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    return 0;
                }
                return target.getIntValue();
            }
        }
        
        /**
         * Gets (as xml) the "return" element
         */
        public org.apache.xmlbeans.XmlInt xgetReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlInt target = null;
                target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(RETURN$0, 0);
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
                org.apache.xmlbeans.XmlInt target = null;
                target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(RETURN$0, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * Sets the "return" element
         */
        public void setReturn(int xreturn)
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
                target.setIntValue(xreturn);
            }
        }
        
        /**
         * Sets (as xml) the "return" element
         */
        public void xsetReturn(org.apache.xmlbeans.XmlInt xreturn)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlInt target = null;
                target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(RETURN$0);
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
                org.apache.xmlbeans.XmlInt target = null;
                target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(RETURN$0);
                }
                target.setNil();
            }
        }
    }
}
