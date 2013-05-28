package uk.ac.ebi.metabolights.spectrumbrowser.client.common.utils;

import com.google.gwt.core.client.GWT;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("UnusedDeclaration")
public abstract class Console {
    //Set VERBOSE to true to see the model in PRODUCTION (if the console is available)
    public final static boolean VERBOSE = !GWT.isScript();

    private static final boolean IS_SCRIPT = GWT.isScript();

    private static native void _error(String message)/*-{
        if($wnd.console){
            $wnd.console.error(message);
        }
    }-*/;

    public static void error(String msg){
        if(IS_SCRIPT){
            Console._error(msg);
        }else{
            System.err.println(msg);
        }
    }

    private static native void _info(String message)/*-{
        if($wnd.console){
            $wnd.console.info(message);
        }
    }-*/;

    public static void info(String msg){
        if(IS_SCRIPT){
            Console._info(msg);
        }else{
            System.out.println(msg);
        }
    }

    private static native void _log(String message)/*-{
        if($wnd.console){
            $wnd.console.log(message);
        }
    }-*/;

    public static void log(String msg){
        if(IS_SCRIPT){
            Console._log(msg);
        }else{
            System.out.println(msg);
        }
    }

    private static native void _warn(String message)/*-{
        if($wnd.console){
            $wnd.console.warn(message)
        }
    }-*/;

    public static void warn(String msg){
        if(IS_SCRIPT){
            Console._warn(msg);
        }else{
            System.out.println("! "  + msg);
        }
    }
}
