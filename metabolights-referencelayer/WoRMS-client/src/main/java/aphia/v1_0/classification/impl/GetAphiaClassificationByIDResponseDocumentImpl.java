/*
 * An XML document type.
 * Localname: getAphiaClassificationByIDResponse
 * Namespace: http://aphia/v1.0/Classification
 * Java type: aphia.v1_0.classification.GetAphiaClassificationByIDResponseDocument
 *
 * Automatically generated - do not modify.
 */
package aphia.v1_0.classification.impl;
/**
 * A document containing one getAphiaClassificationByIDResponse(@http://aphia/v1.0/Classification) element.
 *
 * This is a complex type.
 */
public class GetAphiaClassificationByIDResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.classification.GetAphiaClassificationByIDResponseDocument
{
    
    public GetAphiaClassificationByIDResponseDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName GETAPHIACLASSIFICATIONBYIDRESPONSE$0 = 
        new javax.xml.namespace.QName("http://aphia/v1.0/Classification", "getAphiaClassificationByIDResponse");
    
    
    /**
     * Gets the "getAphiaClassificationByIDResponse" element
     */
    public aphia.v1_0.classification.GetAphiaClassificationByIDResponseDocument.GetAphiaClassificationByIDResponse getGetAphiaClassificationByIDResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.classification.GetAphiaClassificationByIDResponseDocument.GetAphiaClassificationByIDResponse target = null;
            target = (aphia.v1_0.classification.GetAphiaClassificationByIDResponseDocument.GetAphiaClassificationByIDResponse)get_store().find_element_user(GETAPHIACLASSIFICATIONBYIDRESPONSE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "getAphiaClassificationByIDResponse" element
     */
    public void setGetAphiaClassificationByIDResponse(aphia.v1_0.classification.GetAphiaClassificationByIDResponseDocument.GetAphiaClassificationByIDResponse getAphiaClassificationByIDResponse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.classification.GetAphiaClassificationByIDResponseDocument.GetAphiaClassificationByIDResponse target = null;
            target = (aphia.v1_0.classification.GetAphiaClassificationByIDResponseDocument.GetAphiaClassificationByIDResponse)get_store().find_element_user(GETAPHIACLASSIFICATIONBYIDRESPONSE$0, 0);
            if (target == null)
            {
                target = (aphia.v1_0.classification.GetAphiaClassificationByIDResponseDocument.GetAphiaClassificationByIDResponse)get_store().add_element_user(GETAPHIACLASSIFICATIONBYIDRESPONSE$0);
            }
            target.set(getAphiaClassificationByIDResponse);
        }
    }
    
    /**
     * Appends and returns a new empty "getAphiaClassificationByIDResponse" element
     */
    public aphia.v1_0.classification.GetAphiaClassificationByIDResponseDocument.GetAphiaClassificationByIDResponse addNewGetAphiaClassificationByIDResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.classification.GetAphiaClassificationByIDResponseDocument.GetAphiaClassificationByIDResponse target = null;
            target = (aphia.v1_0.classification.GetAphiaClassificationByIDResponseDocument.GetAphiaClassificationByIDResponse)get_store().add_element_user(GETAPHIACLASSIFICATIONBYIDRESPONSE$0);
            return target;
        }
    }
    /**
     * An XML getAphiaClassificationByIDResponse(@http://aphia/v1.0/Classification).
     *
     * This is a complex type.
     */
    public static class GetAphiaClassificationByIDResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.classification.GetAphiaClassificationByIDResponseDocument.GetAphiaClassificationByIDResponse
    {
        
        public GetAphiaClassificationByIDResponseImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName RETURN$0 = 
            new javax.xml.namespace.QName("", "return");
        
        
        /**
         * Gets the "return" element
         */
        public aphia.v1_0.Classification getReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                aphia.v1_0.Classification target = null;
                target = (aphia.v1_0.Classification)get_store().find_element_user(RETURN$0, 0);
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
                aphia.v1_0.Classification target = null;
                target = (aphia.v1_0.Classification)get_store().find_element_user(RETURN$0, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * Sets the "return" element
         */
        public void setReturn(aphia.v1_0.Classification xreturn)
        {
            synchronized (monitor())
            {
                check_orphaned();
                aphia.v1_0.Classification target = null;
                target = (aphia.v1_0.Classification)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    target = (aphia.v1_0.Classification)get_store().add_element_user(RETURN$0);
                }
                target.set(xreturn);
            }
        }
        
        /**
         * Appends and returns a new empty "return" element
         */
        public aphia.v1_0.Classification addNewReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                aphia.v1_0.Classification target = null;
                target = (aphia.v1_0.Classification)get_store().add_element_user(RETURN$0);
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
                aphia.v1_0.Classification target = null;
                target = (aphia.v1_0.Classification)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    target = (aphia.v1_0.Classification)get_store().add_element_user(RETURN$0);
                }
                target.setNil();
            }
        }
    }
}
