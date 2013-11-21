/*
 * An XML document type.
 * Localname: getSourcesByAphiaIDResponse
 * Namespace: http://aphia/v1.0/Sources
 * Java type: aphia.v1_0.sources.GetSourcesByAphiaIDResponseDocument
 *
 * Automatically generated - do not modify.
 */
package aphia.v1_0.sources.impl;
/**
 * A document containing one getSourcesByAphiaIDResponse(@http://aphia/v1.0/Sources) element.
 *
 * This is a complex type.
 */
public class GetSourcesByAphiaIDResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.sources.GetSourcesByAphiaIDResponseDocument
{
    
    public GetSourcesByAphiaIDResponseDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName GETSOURCESBYAPHIAIDRESPONSE$0 = 
        new javax.xml.namespace.QName("http://aphia/v1.0/Sources", "getSourcesByAphiaIDResponse");
    
    
    /**
     * Gets the "getSourcesByAphiaIDResponse" element
     */
    public aphia.v1_0.sources.GetSourcesByAphiaIDResponseDocument.GetSourcesByAphiaIDResponse getGetSourcesByAphiaIDResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.sources.GetSourcesByAphiaIDResponseDocument.GetSourcesByAphiaIDResponse target = null;
            target = (aphia.v1_0.sources.GetSourcesByAphiaIDResponseDocument.GetSourcesByAphiaIDResponse)get_store().find_element_user(GETSOURCESBYAPHIAIDRESPONSE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "getSourcesByAphiaIDResponse" element
     */
    public void setGetSourcesByAphiaIDResponse(aphia.v1_0.sources.GetSourcesByAphiaIDResponseDocument.GetSourcesByAphiaIDResponse getSourcesByAphiaIDResponse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.sources.GetSourcesByAphiaIDResponseDocument.GetSourcesByAphiaIDResponse target = null;
            target = (aphia.v1_0.sources.GetSourcesByAphiaIDResponseDocument.GetSourcesByAphiaIDResponse)get_store().find_element_user(GETSOURCESBYAPHIAIDRESPONSE$0, 0);
            if (target == null)
            {
                target = (aphia.v1_0.sources.GetSourcesByAphiaIDResponseDocument.GetSourcesByAphiaIDResponse)get_store().add_element_user(GETSOURCESBYAPHIAIDRESPONSE$0);
            }
            target.set(getSourcesByAphiaIDResponse);
        }
    }
    
    /**
     * Appends and returns a new empty "getSourcesByAphiaIDResponse" element
     */
    public aphia.v1_0.sources.GetSourcesByAphiaIDResponseDocument.GetSourcesByAphiaIDResponse addNewGetSourcesByAphiaIDResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.sources.GetSourcesByAphiaIDResponseDocument.GetSourcesByAphiaIDResponse target = null;
            target = (aphia.v1_0.sources.GetSourcesByAphiaIDResponseDocument.GetSourcesByAphiaIDResponse)get_store().add_element_user(GETSOURCESBYAPHIAIDRESPONSE$0);
            return target;
        }
    }
    /**
     * An XML getSourcesByAphiaIDResponse(@http://aphia/v1.0/Sources).
     *
     * This is a complex type.
     */
    public static class GetSourcesByAphiaIDResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.sources.GetSourcesByAphiaIDResponseDocument.GetSourcesByAphiaIDResponse
    {
        
        public GetSourcesByAphiaIDResponseImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName RETURN$0 = 
            new javax.xml.namespace.QName("", "return");
        
        
        /**
         * Gets the "return" element
         */
        public aphia.v1_0.Sources getReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                aphia.v1_0.Sources target = null;
                target = (aphia.v1_0.Sources)get_store().find_element_user(RETURN$0, 0);
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
                aphia.v1_0.Sources target = null;
                target = (aphia.v1_0.Sources)get_store().find_element_user(RETURN$0, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * Sets the "return" element
         */
        public void setReturn(aphia.v1_0.Sources xreturn)
        {
            synchronized (monitor())
            {
                check_orphaned();
                aphia.v1_0.Sources target = null;
                target = (aphia.v1_0.Sources)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    target = (aphia.v1_0.Sources)get_store().add_element_user(RETURN$0);
                }
                target.set(xreturn);
            }
        }
        
        /**
         * Appends and returns a new empty "return" element
         */
        public aphia.v1_0.Sources addNewReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                aphia.v1_0.Sources target = null;
                target = (aphia.v1_0.Sources)get_store().add_element_user(RETURN$0);
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
                aphia.v1_0.Sources target = null;
                target = (aphia.v1_0.Sources)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    target = (aphia.v1_0.Sources)get_store().add_element_user(RETURN$0);
                }
                target.setNil();
            }
        }
    }
}
