/*
 * An XML document type.
 * Localname: matchAphiaRecordsByNames
 * Namespace: http://aphia/v1.0/AphiaMatches
 * Java type: aphia.v1_0.aphiamatches.MatchAphiaRecordsByNamesDocument
 *
 * Automatically generated - do not modify.
 */
package aphia.v1_0.aphiamatches.impl;
/**
 * A document containing one matchAphiaRecordsByNames(@http://aphia/v1.0/AphiaMatches) element.
 *
 * This is a complex type.
 */
public class MatchAphiaRecordsByNamesDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.aphiamatches.MatchAphiaRecordsByNamesDocument
{
    
    public MatchAphiaRecordsByNamesDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName MATCHAPHIARECORDSBYNAMES$0 = 
        new javax.xml.namespace.QName("http://aphia/v1.0/AphiaMatches", "matchAphiaRecordsByNames");
    
    
    /**
     * Gets the "matchAphiaRecordsByNames" element
     */
    public aphia.v1_0.aphiamatches.MatchAphiaRecordsByNamesDocument.MatchAphiaRecordsByNames getMatchAphiaRecordsByNames()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiamatches.MatchAphiaRecordsByNamesDocument.MatchAphiaRecordsByNames target = null;
            target = (aphia.v1_0.aphiamatches.MatchAphiaRecordsByNamesDocument.MatchAphiaRecordsByNames)get_store().find_element_user(MATCHAPHIARECORDSBYNAMES$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "matchAphiaRecordsByNames" element
     */
    public void setMatchAphiaRecordsByNames(aphia.v1_0.aphiamatches.MatchAphiaRecordsByNamesDocument.MatchAphiaRecordsByNames matchAphiaRecordsByNames)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiamatches.MatchAphiaRecordsByNamesDocument.MatchAphiaRecordsByNames target = null;
            target = (aphia.v1_0.aphiamatches.MatchAphiaRecordsByNamesDocument.MatchAphiaRecordsByNames)get_store().find_element_user(MATCHAPHIARECORDSBYNAMES$0, 0);
            if (target == null)
            {
                target = (aphia.v1_0.aphiamatches.MatchAphiaRecordsByNamesDocument.MatchAphiaRecordsByNames)get_store().add_element_user(MATCHAPHIARECORDSBYNAMES$0);
            }
            target.set(matchAphiaRecordsByNames);
        }
    }
    
    /**
     * Appends and returns a new empty "matchAphiaRecordsByNames" element
     */
    public aphia.v1_0.aphiamatches.MatchAphiaRecordsByNamesDocument.MatchAphiaRecordsByNames addNewMatchAphiaRecordsByNames()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiamatches.MatchAphiaRecordsByNamesDocument.MatchAphiaRecordsByNames target = null;
            target = (aphia.v1_0.aphiamatches.MatchAphiaRecordsByNamesDocument.MatchAphiaRecordsByNames)get_store().add_element_user(MATCHAPHIARECORDSBYNAMES$0);
            return target;
        }
    }
    /**
     * An XML matchAphiaRecordsByNames(@http://aphia/v1.0/AphiaMatches).
     *
     * This is a complex type.
     */
    public static class MatchAphiaRecordsByNamesImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.aphiamatches.MatchAphiaRecordsByNamesDocument.MatchAphiaRecordsByNames
    {
        
        public MatchAphiaRecordsByNamesImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName SCIENTIFICNAMES$0 = 
            new javax.xml.namespace.QName("", "scientificnames");
        private static final javax.xml.namespace.QName MARINEONLY$2 = 
            new javax.xml.namespace.QName("", "marine_only");
        
        
        /**
         * Gets the "scientificnames" element
         */
        public aphia.v1_0.Scientificnames getScientificnames()
        {
            synchronized (monitor())
            {
                check_orphaned();
                aphia.v1_0.Scientificnames target = null;
                target = (aphia.v1_0.Scientificnames)get_store().find_element_user(SCIENTIFICNAMES$0, 0);
                if (target == null)
                {
                    return null;
                }
                return target;
            }
        }
        
        /**
         * Tests for nil "scientificnames" element
         */
        public boolean isNilScientificnames()
        {
            synchronized (monitor())
            {
                check_orphaned();
                aphia.v1_0.Scientificnames target = null;
                target = (aphia.v1_0.Scientificnames)get_store().find_element_user(SCIENTIFICNAMES$0, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * Sets the "scientificnames" element
         */
        public void setScientificnames(aphia.v1_0.Scientificnames scientificnames)
        {
            synchronized (monitor())
            {
                check_orphaned();
                aphia.v1_0.Scientificnames target = null;
                target = (aphia.v1_0.Scientificnames)get_store().find_element_user(SCIENTIFICNAMES$0, 0);
                if (target == null)
                {
                    target = (aphia.v1_0.Scientificnames)get_store().add_element_user(SCIENTIFICNAMES$0);
                }
                target.set(scientificnames);
            }
        }
        
        /**
         * Appends and returns a new empty "scientificnames" element
         */
        public aphia.v1_0.Scientificnames addNewScientificnames()
        {
            synchronized (monitor())
            {
                check_orphaned();
                aphia.v1_0.Scientificnames target = null;
                target = (aphia.v1_0.Scientificnames)get_store().add_element_user(SCIENTIFICNAMES$0);
                return target;
            }
        }
        
        /**
         * Nils the "scientificnames" element
         */
        public void setNilScientificnames()
        {
            synchronized (monitor())
            {
                check_orphaned();
                aphia.v1_0.Scientificnames target = null;
                target = (aphia.v1_0.Scientificnames)get_store().find_element_user(SCIENTIFICNAMES$0, 0);
                if (target == null)
                {
                    target = (aphia.v1_0.Scientificnames)get_store().add_element_user(SCIENTIFICNAMES$0);
                }
                target.setNil();
            }
        }
        
        /**
         * Gets the "marine_only" element
         */
        public boolean getMarineOnly()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MARINEONLY$2, 0);
                if (target == null)
                {
                    return false;
                }
                return target.getBooleanValue();
            }
        }
        
        /**
         * Gets (as xml) the "marine_only" element
         */
        public org.apache.xmlbeans.XmlBoolean xgetMarineOnly()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlBoolean target = null;
                target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(MARINEONLY$2, 0);
                return target;
            }
        }
        
        /**
         * Tests for nil "marine_only" element
         */
        public boolean isNilMarineOnly()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlBoolean target = null;
                target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(MARINEONLY$2, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * Sets the "marine_only" element
         */
        public void setMarineOnly(boolean marineOnly)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MARINEONLY$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MARINEONLY$2);
                }
                target.setBooleanValue(marineOnly);
            }
        }
        
        /**
         * Sets (as xml) the "marine_only" element
         */
        public void xsetMarineOnly(org.apache.xmlbeans.XmlBoolean marineOnly)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlBoolean target = null;
                target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(MARINEONLY$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(MARINEONLY$2);
                }
                target.set(marineOnly);
            }
        }
        
        /**
         * Nils the "marine_only" element
         */
        public void setNilMarineOnly()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlBoolean target = null;
                target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(MARINEONLY$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(MARINEONLY$2);
                }
                target.setNil();
            }
        }
    }
}
