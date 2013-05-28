package uk.ac.ebi.metabolights.spectrumbrowser.client.viewer.data.factory;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;
import uk.ac.ebi.metabolights.spectrumbrowser.client.viewer.data.model.NMRSpectraData;

@SuppressWarnings("UnusedDeclaration")
public abstract class ModelFactory {

    interface BeanFactory extends AutoBeanFactory {
        AutoBean<NMRSpectraData> peakList();
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