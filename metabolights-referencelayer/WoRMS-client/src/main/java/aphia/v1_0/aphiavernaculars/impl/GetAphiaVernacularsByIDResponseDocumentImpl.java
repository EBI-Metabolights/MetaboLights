/*
 * An XML document type.
 * Localname: getAphiaVernacularsByIDResponse
 * Namespace: http://aphia/v1.0/AphiaVernaculars
 * Java type: aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument
 *
 * Automatically generated - do not modify.
 */
package aphia.v1_0.aphiavernaculars.impl;
/**
 * A document containing one getAphiaVernacularsByIDResponse(@http://aphia/v1.0/AphiaVernaculars) element.
 *
 * This is a complex type.
 */
public class GetAphiaVernacularsByIDResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument
{
    
    public GetAphiaVernacularsByIDResponseDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName GETAPHIAVERNACULARSBYIDRESPONSE$0 = 
        new javax.xml.namespace.QName("http://aphia/v1.0/AphiaVernaculars", "getAphiaVernacularsByIDResponse");
    
    
    /**
     * Gets the "getAphiaVernacularsByIDResponse" element
     */
    public aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument.GetAphiaVernacularsByIDResponse getGetAphiaVernacularsByIDResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument.GetAphiaVernacularsByIDResponse target = null;
            target = (aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument.GetAphiaVernacularsByIDResponse)get_store().find_element_user(GETAPHIAVERNACULARSBYIDRESPONSE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "getAphiaVernacularsByIDResponse" element
     */
    public void setGetAphiaVernacularsByIDResponse(aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument.GetAphiaVernacularsByIDResponse getAphiaVernacularsByIDResponse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument.GetAphiaVernacularsByIDResponse target = null;
            target = (aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument.GetAphiaVernacularsByIDResponse)get_store().find_element_user(GETAPHIAVERNACULARSBYIDRESPONSE$0, 0);
            if (target == null)
            {
                target = (aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument.GetAphiaVernacularsByIDResponse)get_store().add_element_user(GETAPHIAVERNACULARSBYIDRESPONSE$0);
            }
            target.set(getAphiaVernacularsByIDResponse);
        }
    }
    
    /**
     * Appends and returns a new empty "getAphiaVernacularsByIDResponse" element
     */
    public aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument.GetAphiaVernacularsByIDResponse addNewGetAphiaVernacularsByIDResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument.GetAphiaVernacularsByIDResponse target = null;
            target = (aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument.GetAphiaVernacularsByIDResponse)get_store().add_element_user(GETAPHIAVERNACULARSBYIDRESPONSE$0);
            return target;
        }
    }
    /**
     * An XML getAphiaVernacularsByIDResponse(@http://aphia/v1.0/AphiaVernaculars).
     *
     * This is a complex type.
     */
    public static class GetAphiaVernacularsByIDResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument.GetAphiaVernacularsByIDResponse
    {
        
        public GetAphiaVernacularsByIDResponseImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName RETURN$0 = 
            new javax.xml.namespace.QName("", "return");
        
        
        /**
         * Gets the "return" element
         */
        public aphia.v1_0.Vernaculars getReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                aphia.v1_0.Vernaculars target = null;
                target = (aphia.v1_0.Vernaculars)get_store().find_element_user(RETURN$0, 0);
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
                aphia.v1_0.Vernaculars target = null;
                target = (aphia.v1_0.Vernaculars)get_store().find_element_user(RETURN$0, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * Sets the "return" element
         */
        public void setReturn(aphia.v1_0.Vernaculars xreturn)
        {
            synchronized (monitor())
            {
                check_orphaned();
                aphia.v1_0.Vernaculars target = null;
                target = (aphia.v1_0.Vernaculars)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    target = (aphia.v1_0.Vernaculars)get_store().add_element_user(RETURN$0);
                }
                target.set(xreturn);
            }
        }
        
        /**
         * Appends and returns a new empty "return" element
         */
        public aphia.v1_0.Vernaculars addNewReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                aphia.v1_0.Vernaculars target = null;
                target = (aphia.v1_0.Vernaculars)get_store().add_element_user(RETURN$0);
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
                aphia.v1_0.Vernaculars target = null;
                target = (aphia.v1_0.Vernaculars)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    target = (aphia.v1_0.Vernaculars)get_store().add_element_user(RETURN$0);
                }
                target.setNil();
            }
        }
    }
}
