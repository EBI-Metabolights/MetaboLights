/*
 * XML Type:  Vernacular
 * Namespace: http://aphia/v1.0
 * Java type: aphia.v1_0.Vernacular
 *
 * Automatically generated - do not modify.
 */
package aphia.v1_0.impl;
/**
 * An XML Vernacular(@http://aphia/v1.0).
 *
 * This is a complex type.
 */
public class VernacularImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.Vernacular
{
    
    public VernacularImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName VERNACULAR$0 = 
        new javax.xml.namespace.QName("", "vernacular");
    private static final javax.xml.namespace.QName LANGUAGECODE$2 = 
        new javax.xml.namespace.QName("", "language_code");
    private static final javax.xml.namespace.QName LANGUAGE$4 = 
        new javax.xml.namespace.QName("", "language");
    
    
    /**
     * Gets the "vernacular" element
     */
    public java.lang.String getVernacular()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(VERNACULAR$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "vernacular" element
     */
    public org.apache.xmlbeans.XmlString xgetVernacular()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(VERNACULAR$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "vernacular" element
     */
    public void setVernacular(java.lang.String vernacular)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(VERNACULAR$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(VERNACULAR$0);
            }
            target.setStringValue(vernacular);
        }
    }
    
    /**
     * Sets (as xml) the "vernacular" element
     */
    public void xsetVernacular(org.apache.xmlbeans.XmlString vernacular)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(VERNACULAR$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(VERNACULAR$0);
            }
            target.set(vernacular);
        }
    }
    
    /**
     * Gets the "language_code" element
     */
    public java.lang.String getLanguageCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LANGUAGECODE$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "language_code" element
     */
    public org.apache.xmlbeans.XmlString xgetLanguageCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LANGUAGECODE$2, 0);
            return target;
        }
    }
    
    /**
     * Sets the "language_code" element
     */
    public void setLanguageCode(java.lang.String languageCode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LANGUAGECODE$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(LANGUAGECODE$2);
            }
            target.setStringValue(languageCode);
        }
    }
    
    /**
     * Sets (as xml) the "language_code" element
     */
    public void xsetLanguageCode(org.apache.xmlbeans.XmlString languageCode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LANGUAGECODE$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(LANGUAGECODE$2);
            }
            target.set(languageCode);
        }
    }
    
    /**
     * Gets the "language" element
     */
    public java.lang.String getLanguage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LANGUAGE$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "language" element
     */
    public org.apache.xmlbeans.XmlString xgetLanguage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LANGUAGE$4, 0);
            return target;
        }
    }
    
    /**
     * Sets the "language" element
     */
    public void setLanguage(java.lang.String language)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LANGUAGE$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(LANGUAGE$4);
            }
            target.setStringValue(language);
        }
    }
    
    /**
     * Sets (as xml) the "language" element
     */
    public void xsetLanguage(org.apache.xmlbeans.XmlString language)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LANGUAGE$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(LANGUAGE$4);
            }
            target.set(language);
        }
    }
}
