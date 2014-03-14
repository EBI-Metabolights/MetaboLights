/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 03/06/13 11:49
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.spectrumbrowser.client.viewer.data.factory;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;
import uk.ac.ebi.metabolights.spectrumbrowser.client.viewer.data.model.NMRSpectraData;
import uk.ac.ebi.metabolights.spectrumbrowser.client.viewer.data.model.PeakListRaw;

@SuppressWarnings("UnusedDeclaration")
public abstract class ModelFactory {

    interface BeanFactory extends AutoBeanFactory {
        AutoBean<NMRSpectraData> peakListNMR();

        AutoBean<PeakListRaw> peakListMS();
    }


    public static <T> T getModelObject(Class<T> cls, String json) throws ModelFactoryException {
        try{
            BeanFactory factory = GWT.create(BeanFactory.class);
            AutoBean<T> bean = AutoBeanCodex.decode(factory, cls, json);
            return bean.as();
        }catch (Throwable e){
            throw new ModelFactoryException("Error mapping json string for [" + cls + "]: " + json, e);
        }
    }
}