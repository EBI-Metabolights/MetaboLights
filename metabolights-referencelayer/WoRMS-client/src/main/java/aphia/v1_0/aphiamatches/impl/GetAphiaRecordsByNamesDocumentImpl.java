/*
 * An XML document type.
 * Localname: getAphiaRecordsByNames
 * Namespace: http://aphia/v1.0/AphiaMatches
 * Java type: aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument
 *
 * Automatically generated - do not modify.
 */
package aphia.v1_0.aphiamatches.impl;
/**
 * A document containing one getAphiaRecordsByNames(@http://aphia/v1.0/AphiaMatches) element.
 *
 * This is a complex type.
 */
public class GetAphiaRecordsByNamesDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument
{
    
    public GetAphiaRecordsByNamesDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName GETAPHIARECORDSBYNAMES$0 = 
        new javax.xml.namespace.QName("http://aphia/v1.0/AphiaMatches", "getAphiaRecordsByNames");
    
    
    /**
     * Gets the "getAphiaRecordsByNames" element
     */
    public aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument.GetAphiaRecordsByNames getGetAphiaRecordsByNames()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument.GetAphiaRecordsByNames target = null;
            target = (aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument.GetAphiaRecordsByNames)get_store().find_element_user(GETAPHIARECORDSBYNAMES$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "getAphiaRecordsByNames" element
     */
    public void setGetAphiaRecordsByNames(aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument.GetAphiaRecordsByNames getAphiaRecordsByNames)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument.GetAphiaRecordsByNames target = null;
            target = (aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument.GetAphiaRecordsByNames)get_store().find_element_user(GETAPHIARECORDSBYNAMES$0, 0);
            if (target == null)
            {
                target = (aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument.GetAphiaRecordsByNames)get_store().add_element_user(GETAPHIARECORDSBYNAMES$0);
            }
            target.set(getAphiaRecordsByNames);
        }
    }
    
    /**
     * Appends and returns a new empty "getAphiaRecordsByNames" element
     */
    public aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument.GetAphiaRecordsByNames addNewGetAphiaRecordsByNames()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument.GetAphiaRecordsByNames target = null;
            target = (aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument.GetAphiaRecordsByNames)get_store().add_element_user(GETAPHIARECORDSBYNAMES$0);
            return target;
        }
    }
    /**
     * An XML getAphiaRecordsByNames(@http://aphia/v1.0/AphiaMatches).
     *
     * This is a complex type.
     */
    public static class GetAphiaRecordsByNamesImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument.GetAphiaRecordsByNames
    {
        
        public GetAphiaRecordsByNamesImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName SCIENTIFICNAMES$0 = 
            new javax.xml.namespace.QName("", "scientificnames");
        private static final javax.xml.namespace.QName LIKE$2 = 
            new javax.xml.namespace.QName("", "like");
        private static final javax.xml.namespace.QName FUZZY$4 = 
            new javax.xml.namespace.QName("", "fuzzy");
        private static final javax.xml.namespace.QName MARINEONLY$6 = 
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
         * Gets the "like" element
         */
        public boolean getLike()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LIKE$2, 0);
                if (target == null)
                {
                    return false;
                }
                return target.getBooleanValue();
            }
        }
        
        /**
         * Gets (as xml) the "like" element
         */
        public org.apache.xmlbeans.XmlBoolean xgetLike()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlBoolean target = null;
                target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(LIKE$2, 0);
                return target;
            }
        }
        
        /**
         * Tests for nil "like" element
         */
        public boolean isNilLike()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlBoolean target = null;
                target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(LIKE$2, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * Sets the "like" element
         */
        public void setLike(boolean like)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LIKE$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(LIKE$2);
                }
                target.setBooleanValue(like);
            }
        }
        
        /**
         * Sets (as xml) the "like" element
         */
        public void xsetLike(org.apache.xmlbeans.XmlBoolean like)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlBoolean target = null;
                target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(LIKE$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(LIKE$2);
                }
                target.set(like);
            }
        }
        
        /**
         * Nils the "like" element
         */
        public void setNilLike()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlBoolean target = null;
                target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(LIKE$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(LIKE$2);
                }
                target.setNil();
            }
        }
        
        /**
         * Gets the "fuzzy" element
         */
        public boolean getFuzzy()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FUZZY$4, 0);
                if (target == null)
                {
                    return false;
                }
                return target.getBooleanValue();
            }
        }
        
        /**
         * Gets (as xml) the "fuzzy" element
         */
        public org.apache.xmlbeans.XmlBoolean xgetFuzzy()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlBoolean target = null;
                target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(FUZZY$4, 0);
                return target;
            }
        }
        
        /**
         * Tests for nil "fuzzy" element
         */
        public boolean isNilFuzzy()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlBoolean target = null;
                target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(FUZZY$4, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * Sets the "fuzzy" element
         */
        public void setFuzzy(boolean fuzzy)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FUZZY$4, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FUZZY$4);
                }
                target.setBooleanValue(fuzzy);
            }
        }
        
        /**
         * Sets (as xml) the "fuzzy" element
         */
        public void xsetFuzzy(org.apache.xmlbeans.XmlBoolean fuzzy)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlBoolean target = null;
                target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(FUZZY$4, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(FUZZY$4);
                }
                target.set(fuzzy);
            }
        }
        
        /**
         * Nils the "fuzzy" element
         */
        public void setNilFuzzy()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlBoolean target = null;
                target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(FUZZY$4, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(FUZZY$4);
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
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MARINEONLY$6, 0);
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
                target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(MARINEONLY$6, 0);
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
                target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(MARINEONLY$6, 0);
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
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MARINEONLY$6, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MARINEONLY$6);
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
                target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(MARINEONLY$6, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(MARINEONLY$6);
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
                target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(MARINEONLY$6, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(MARINEONLY$6);
                }
                target.setNil();
            }
        }
    }
}
