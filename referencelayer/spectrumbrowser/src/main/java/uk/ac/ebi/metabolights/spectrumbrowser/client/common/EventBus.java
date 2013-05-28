package uk.ac.ebi.metabolights.spectrumbrowser.client.common;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.SimpleEventBus;
import uk.ac.ebi.metabolights.spectrumbrowser.client.common.utils.Console;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class EventBus extends SimpleEventBus {
    // In "Dev Mode" the model fired to the model bus will be shown in the console
    // with this string builder we provide a way to nest the string and provide a
    // visual indicator of which model are fired after each other
    private StringBuilder nestedSpace = new StringBuilder();

    public EventBus() {
        super();
        //For DEV purposes, shows a line between last and current execution
        if(Console.VERBOSE){
            Console.info("");
        }
    }

    @Override
    public void fireEvent(GwtEvent<?> event) {
        if(Console.VERBOSE){
            this.nestedSpace.append(" ");
            String aux = event.toString();
            if(aux.equals("An event type")){ //By default is what the GWT has in toString
                String[] name = event.getClass().getName().split("\\.");
                Console.info("[EventBus] ->" + this.nestedSpace + name[name.length-1]);
            }else{
                Console.info("[EventBus] ->" + this.nestedSpace + event.toString());
            }
        }

        super.fireEvent(event);

        if(Console.VERBOSE){
            this.nestedSpace.delete(1,2);
        }
    }
}
