/*
 * An XML document type.
 * Localname: getExtIDbyAphiaIDResponse
 * Namespace: http://aphia/v1.0/externalidentifiers
 * Java type: aphia.v1_0.externalidentifiers.GetExtIDbyAphiaIDResponseDocument
 *
 * Automatically generated - do not modify.
 */
package aphia.v1_0.externalidentifiers.impl;
/**
 * A document containing one getExtIDbyAphiaIDResponse(@http://aphia/v1.0/externalidentifiers) element.
 *
 * This is a complex type.
 */
public class GetExtIDbyAphiaIDResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.externalidentifiers.GetExtIDbyAphiaIDResponseDocument
{
    
    public GetExtIDbyAphiaIDResponseDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName GETEXTIDBYAPHIAIDRESPONSE$0 = 
        new javax.xml.namespace.QName("http://aphia/v1.0/externalidentifiers", "getExtIDbyAphiaIDResponse");
    
    
    /**
     * Gets the "getExtIDbyAphiaIDResponse" element
     */
    public aphia.v1_0.externalidentifiers.GetExtIDbyAphiaIDResponseDocument.GetExtIDbyAphiaIDResponse getGetExtIDbyAphiaIDResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.externalidentifiers.GetExtIDbyAphiaIDResponseDocument.GetExtIDbyAphiaIDResponse target = null;
            target = (aphia.v1_0.externalidentifiers.GetExtIDbyAphiaIDResponseDocument.GetExtIDbyAphiaIDResponse)get_store().find_element_user(GETEXTIDBYAPHIAIDRESPONSE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "getExtIDbyAphiaIDResponse" element
     */
    public void setGetExtIDbyAphiaIDResponse(aphia.v1_0.externalidentifiers.GetExtIDbyAphiaIDResponseDocument.GetExtIDbyAphiaIDResponse getExtIDbyAphiaIDResponse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.externalidentifiers.GetExtIDbyAphiaIDResponseDocument.GetExtIDbyAphiaIDResponse target = null;
            target = (aphia.v1_0.externalidentifiers.GetExtIDbyAphiaIDResponseDocument.GetExtIDbyAphiaIDResponse)get_store().find_element_user(GETEXTIDBYAPHIAIDRESPONSE$0, 0);
            if (target == null)
            {
                target = (aphia.v1_0.externalidentifiers.GetExtIDbyAphiaIDResponseDocument.GetExtIDbyAphiaIDResponse)get_store().add_element_user(GETEXTIDBYAPHIAIDRESPONSE$0);
            }
            target.set(getExtIDbyAphiaIDResponse);
        }
    }
    
    /**
     * Appends and returns a new empty "getExtIDbyAphiaIDResponse" element
     */
    public aphia.v1_0.externalidentifiers.GetExtIDbyAphiaIDResponseDocument.GetExtIDbyAphiaIDResponse addNewGetExtIDbyAphiaIDResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.externalidentifiers.GetExtIDbyAphiaIDResponseDocument.GetExtIDbyAphiaIDResponse target = null;
            target = (aphia.v1_0.externalidentifiers.GetExtIDbyAphiaIDResponseDocument.GetExtIDbyAphiaIDResponse)get_store().add_element_user(GETEXTIDBYAPHIAIDRESPONSE$0);
            return target;
        }
    }
    /**
     * An XML getExtIDbyAphiaIDResponse(@http://aphia/v1.0/externalidentifiers).
     *
     * This is a complex type.
     */
    public static class GetExtIDbyAphiaIDResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.externalidentifiers.GetExtIDbyAphiaIDResponseDocument.GetExtIDbyAphiaIDResponse
    {
        
        public GetExtIDbyAphiaIDResponseImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName RETURN$0 = 
            new javax.xml.namespace.QName("", "return");
        
        
        /**
         * Gets the "return" element
         */
        public aphia.v1_0.Externalidentifiers getReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                aphia.v1_0.Externalidentifiers target = null;
                target = (aphia.v1_0.Externalidentifiers)get_store().find_element_user(RETURN$0, 0);
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
                aphia.v1_0.Externalidentifiers target = null;
                target = (aphia.v1_0.Externalidentifiers)get_store().find_element_user(RETURN$0, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * Sets the "return" element
         */
        public void setReturn(aphia.v1_0.Externalidentifiers xreturn)
        {
            synchronized (monitor())
            {
                check_orphaned();
                aphia.v1_0.Externalidentifiers target = null;
                target = (aphia.v1_0.Externalidentifiers)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    target = (aphia.v1_0.Externalidentifiers)get_store().add_element_user(RETURN$0);
                }
                target.set(xreturn);
            }
        }
        
        /**
         * Appends and returns a new empty "return" element
         */
        public aphia.v1_0.Externalidentifiers addNewReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                aphia.v1_0.Externalidentifiers target = null;
                target = (aphia.v1_0.Externalidentifiers)get_store().add_element_user(RETURN$0);
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
                aphia.v1_0.Externalidentifiers target = null;
                target = (aphia.v1_0.Externalidentifiers)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    target = (aphia.v1_0.Externalidentifiers)get_store().add_element_user(RETURN$0);
                }
                target.setNil();
            }
        }
    }
}
