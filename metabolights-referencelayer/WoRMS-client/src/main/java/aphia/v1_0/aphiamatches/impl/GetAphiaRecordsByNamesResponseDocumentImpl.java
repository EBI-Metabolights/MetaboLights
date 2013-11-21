/*
 * An XML document type.
 * Localname: getAphiaRecordsByNamesResponse
 * Namespace: http://aphia/v1.0/AphiaMatches
 * Java type: aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesResponseDocument
 *
 * Automatically generated - do not modify.
 */
package aphia.v1_0.aphiamatches.impl;
/**
 * A document containing one getAphiaRecordsByNamesResponse(@http://aphia/v1.0/AphiaMatches) element.
 *
 * This is a complex type.
 */
public class GetAphiaRecordsByNamesResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesResponseDocument
{
    
    public GetAphiaRecordsByNamesResponseDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName GETAPHIARECORDSBYNAMESRESPONSE$0 = 
        new javax.xml.namespace.QName("http://aphia/v1.0/AphiaMatches", "getAphiaRecordsByNamesResponse");
    
    
    /**
     * Gets the "getAphiaRecordsByNamesResponse" element
     */
    public aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesResponseDocument.GetAphiaRecordsByNamesResponse getGetAphiaRecordsByNamesResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesResponseDocument.GetAphiaRecordsByNamesResponse target = null;
            target = (aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesResponseDocument.GetAphiaRecordsByNamesResponse)get_store().find_element_user(GETAPHIARECORDSBYNAMESRESPONSE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "getAphiaRecordsByNamesResponse" element
     */
    public void setGetAphiaRecordsByNamesResponse(aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesResponseDocument.GetAphiaRecordsByNamesResponse getAphiaRecordsByNamesResponse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesResponseDocument.GetAphiaRecordsByNamesResponse target = null;
            target = (aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesResponseDocument.GetAphiaRecordsByNamesResponse)get_store().find_element_user(GETAPHIARECORDSBYNAMESRESPONSE$0, 0);
            if (target == null)
            {
                target = (aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesResponseDocument.GetAphiaRecordsByNamesResponse)get_store().add_element_user(GETAPHIARECORDSBYNAMESRESPONSE$0);
            }
            target.set(getAphiaRecordsByNamesResponse);
        }
    }
    
    /**
     * Appends and returns a new empty "getAphiaRecordsByNamesResponse" element
     */
    public aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesResponseDocument.GetAphiaRecordsByNamesResponse addNewGetAphiaRecordsByNamesResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesResponseDocument.GetAphiaRecordsByNamesResponse target = null;
            target = (aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesResponseDocument.GetAphiaRecordsByNamesResponse)get_store().add_element_user(GETAPHIARECORDSBYNAMESRESPONSE$0);
            return target;
        }
    }
    /**
     * An XML getAphiaRecordsByNamesResponse(@http://aphia/v1.0/AphiaMatches).
     *
     * This is a complex type.
     */
    public static class GetAphiaRecordsByNamesResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesResponseDocument.GetAphiaRecordsByNamesResponse
    {
        
        public GetAphiaRecordsByNamesResponseImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName RETURN$0 = 
            new javax.xml.namespace.QName("", "return");
        
        
        /**
         * Gets the "return" element
         */
        public aphia.v1_0.AphiaMatches getReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                aphia.v1_0.AphiaMatches target = null;
                target = (aphia.v1_0.AphiaMatches)get_store().find_element_user(RETURN$0, 0);
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
                aphia.v1_0.AphiaMatches target = null;
                target = (aphia.v1_0.AphiaMatches)get_store().find_element_user(RETURN$0, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * Sets the "return" element
         */
        public void setReturn(aphia.v1_0.AphiaMatches xreturn)
        {
            synchronized (monitor())
            {
                check_orphaned();
                aphia.v1_0.AphiaMatches target = null;
                target = (aphia.v1_0.AphiaMatches)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    target = (aphia.v1_0.AphiaMatches)get_store().add_element_user(RETURN$0);
                }
                target.set(xreturn);
            }
        }
        
        /**
         * Appends and returns a new empty "return" element
         */
        public aphia.v1_0.AphiaMatches addNewReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                aphia.v1_0.AphiaMatches target = null;
                target = (aphia.v1_0.AphiaMatches)get_store().add_element_user(RETURN$0);
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
                aphia.v1_0.AphiaMatches target = null;
                target = (aphia.v1_0.AphiaMatches)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    target = (aphia.v1_0.AphiaMatches)get_store().add_element_user(RETURN$0);
                }
                target.setNil();
            }
        }
    }
}
