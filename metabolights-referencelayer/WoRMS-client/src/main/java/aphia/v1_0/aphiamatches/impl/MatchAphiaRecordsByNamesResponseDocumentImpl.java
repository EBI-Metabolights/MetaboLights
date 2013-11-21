/*
 * An XML document type.
 * Localname: matchAphiaRecordsByNamesResponse
 * Namespace: http://aphia/v1.0/AphiaMatches
 * Java type: aphia.v1_0.aphiamatches.MatchAphiaRecordsByNamesResponseDocument
 *
 * Automatically generated - do not modify.
 */
package aphia.v1_0.aphiamatches.impl;
/**
 * A document containing one matchAphiaRecordsByNamesResponse(@http://aphia/v1.0/AphiaMatches) element.
 *
 * This is a complex type.
 */
public class MatchAphiaRecordsByNamesResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.aphiamatches.MatchAphiaRecordsByNamesResponseDocument
{
    
    public MatchAphiaRecordsByNamesResponseDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName MATCHAPHIARECORDSBYNAMESRESPONSE$0 = 
        new javax.xml.namespace.QName("http://aphia/v1.0/AphiaMatches", "matchAphiaRecordsByNamesResponse");
    
    
    /**
     * Gets the "matchAphiaRecordsByNamesResponse" element
     */
    public aphia.v1_0.aphiamatches.MatchAphiaRecordsByNamesResponseDocument.MatchAphiaRecordsByNamesResponse getMatchAphiaRecordsByNamesResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiamatches.MatchAphiaRecordsByNamesResponseDocument.MatchAphiaRecordsByNamesResponse target = null;
            target = (aphia.v1_0.aphiamatches.MatchAphiaRecordsByNamesResponseDocument.MatchAphiaRecordsByNamesResponse)get_store().find_element_user(MATCHAPHIARECORDSBYNAMESRESPONSE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "matchAphiaRecordsByNamesResponse" element
     */
    public void setMatchAphiaRecordsByNamesResponse(aphia.v1_0.aphiamatches.MatchAphiaRecordsByNamesResponseDocument.MatchAphiaRecordsByNamesResponse matchAphiaRecordsByNamesResponse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiamatches.MatchAphiaRecordsByNamesResponseDocument.MatchAphiaRecordsByNamesResponse target = null;
            target = (aphia.v1_0.aphiamatches.MatchAphiaRecordsByNamesResponseDocument.MatchAphiaRecordsByNamesResponse)get_store().find_element_user(MATCHAPHIARECORDSBYNAMESRESPONSE$0, 0);
            if (target == null)
            {
                target = (aphia.v1_0.aphiamatches.MatchAphiaRecordsByNamesResponseDocument.MatchAphiaRecordsByNamesResponse)get_store().add_element_user(MATCHAPHIARECORDSBYNAMESRESPONSE$0);
            }
            target.set(matchAphiaRecordsByNamesResponse);
        }
    }
    
    /**
     * Appends and returns a new empty "matchAphiaRecordsByNamesResponse" element
     */
    public aphia.v1_0.aphiamatches.MatchAphiaRecordsByNamesResponseDocument.MatchAphiaRecordsByNamesResponse addNewMatchAphiaRecordsByNamesResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiamatches.MatchAphiaRecordsByNamesResponseDocument.MatchAphiaRecordsByNamesResponse target = null;
            target = (aphia.v1_0.aphiamatches.MatchAphiaRecordsByNamesResponseDocument.MatchAphiaRecordsByNamesResponse)get_store().add_element_user(MATCHAPHIARECORDSBYNAMESRESPONSE$0);
            return target;
        }
    }
    /**
     * An XML matchAphiaRecordsByNamesResponse(@http://aphia/v1.0/AphiaMatches).
     *
     * This is a complex type.
     */
    public static class MatchAphiaRecordsByNamesResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.aphiamatches.MatchAphiaRecordsByNamesResponseDocument.MatchAphiaRecordsByNamesResponse
    {
        
        public MatchAphiaRecordsByNamesResponseImpl(org.apache.xmlbeans.SchemaType sType)
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
