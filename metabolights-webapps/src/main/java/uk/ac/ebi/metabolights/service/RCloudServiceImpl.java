package uk.ac.ebi.metabolights.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.rcloud.rpf.RemoteLogListener;
import uk.ac.ebi.rcloud.rpf.ServantProviderFactory;
import uk.ac.ebi.rcloud.server.RServices;
import uk.ac.ebi.rcloud.server.callback.RAction;
import uk.ac.ebi.rcloud.server.callback.RActionListener;
import uk.ac.ebi.rcloud.server.graphics.GDDevice;
import uk.ac.ebi.rcloud.server.graphics.GDObjectListener;
import uk.ac.ebi.rcloud.server.graphics.primitive.GDImage;
import uk.ac.ebi.rcloud.server.graphics.primitive.GDObject;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

/**
 * Created by kalai on 01/07/2014.
 */
public class RCloudServiceImpl implements RCloudService {
    private final Logger log = LoggerFactory.getLogger(RCloudServiceImpl.class);

    private ServantProviderFactory spFactory = null;
    private ServerRActionListener actionListener = null;
    private ServerLogListener logListener = null;
    private ServerGDObjectListener graphicListener = null;


    public RCloudServiceImpl() {

// settings for accessing the R Cloud
//
        setSystemProperty("naming.mode", "db");
        setSystemProperty("db.type", "oracle");
        setSystemProperty("db.name", "RCLDPRO");
        setSystemProperty("db.host", "ora-vm-030.ebi.ac.uk");
        setSystemProperty("db.port", "1531");
        //setSystemProperty("servername", "METABOLIGHTS_4G_289564");

// action listener, actions are originated by R
//
        try {
            actionListener = new ServerRActionListener();
        } catch (RemoteException re) {
            log.error("ServerRActionListener could not be created!", re);
        }


// log listener
//
        try {
            logListener = new ServerLogListener();
        } catch (RemoteException re) {
            log.error("ServerLogListener could not be created!", re);
        }

// image listener
//

        try {
            graphicListener = new ServerGDObjectListener();
        } catch (RemoteException re) {
            log.error("ServerGDObjectListener could not be created!", re);
        }
    }


    @Override
    public RServices getRServices(String poolname) {
        RServices r = null;

// getting factory
        spFactory = ServantProviderFactory.getFactory();

        String servername = System.getProperty("servername");

// and the server, that’s it
        try {
            if (servername == null) {
                r = (RServices) spFactory.getServantProvider().borrowServantProxyNoWait(poolname);
            } else {
                r = (RServices) spFactory.getServantProvider().getRegistry().lookup(servername);
            }

        } catch (Exception ex) {
            log.error("Error!", ex);
        }

        if (r != null) {
            try {
// attaching listeners
                if (actionListener != null) {
                    log.info("adding action listener...");
                    r.addRConsoleActionListener(actionListener);
                }
                if (logListener != null) {
                    log.info("adding log listener...");
                    r.addLogListener(logListener);
                }

                if (graphicListener != null) {
//
//                        log.info("listing devices...");
//                        GDDevice[] devices = r.listDevices();
//                        System.out.println("devices length: " + devices.length);
//
//
//                        if (devices != null && devices.length > 0) {
//                            log.info("adding listener to existing device...");
//
//                            devices[0].addGraphicListener(graphicListener);
//
//                        } else {

                    log.info("creating device...");

// creating device, if we need to produce images
                    GDDevice device = r.newDevice(800, 800);
                    log.info("adding Graphic listener to new device...");

// adding listener
                    device.addGraphicListener(graphicListener);
//                        }
                }

            } catch (Exception ex) {
                log.error("Error!", ex);

                freeRServices(r);
            }
        } else {
            log.error("Error, R is null");
        }
        return r;
    }

    @Override
    public void executeScript(RServices rs, Vector<String> script) {
        if (rs != null) {
            try {
                log.info("executing script..");

                for (String cmd : script) {
                    log.info(cmd);
                    rs.asynchronousConsoleSubmit(cmd);

                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException ie) {
                    }
                }


            } catch (RemoteException re) {
                log.error("Error!", re);
            }
        } else {
            log.info("No R Servers Available");
        }
    }

    @Override
    public void saveImageDataToFile(byte[] data, String filename) {
        try {
            System.out.println(filename);
            FileOutputStream fos = new FileOutputStream(filename);
            fos.write(data);
            fos.close();
        } catch (IOException ioe) {
            log.error("Error!", ioe);
        }
    }

    @Override
    public void freeRServices(RServices r) {
        // free R services
        if (r != null) {

            try {

// detaching listeners
                // unbind listeners
                if (logListener != null) {
                    log.info("disposing log listener");
                    r.removeLogListener(logListener);
                }
                if (actionListener != null) {
                    log.info("disposing action listener");
                    r.removeRConsoleActionListener(actionListener);
                }
                if (graphicListener != null) {
                    GDDevice[] devices = r.listDevices();

                    for (GDDevice d : devices) {
                        log.info("disposing graphics listener");

                        d.removeGraphicListener(graphicListener);
                        d.dispose();
                    }
                }

// returning the server
                // return server to pool
                log.info("releasing server");

                spFactory.getServantProvider().returnServantProxy(r);
            } catch (Exception e) {
                log.error("Error!", e);
            }
        }

    }

    private void setSystemProperty(String name, String value) {
        String val = System.getProperty(name);

        if (val == null || val.equals("")) {
            System.setProperty(name, value);
        }
    }

    class ServerRActionListener extends UnicastRemoteObject implements RActionListener {
        final private Logger log = LoggerFactory.getLogger(getClass());

        public ServerRActionListener() throws RemoteException {
            super();
        }

        public void notify(RAction action) throws RemoteException {
            log.info(action.toString());

            //if (action.getActionName().equals(RActionType.CONSOLE)) {
            //    log.info(action.getAttributes().get("log").toString());
            //}
        }
    }

    // log listener implementation
//
    class ServerLogListener extends UnicastRemoteObject implements RemoteLogListener {
        final private Logger log = LoggerFactory.getLogger(getClass());

        public ServerLogListener() throws RemoteException {
            super();
        }

        public void write(String text) throws RemoteException {
            log.info("log-" + text);
            //log.info(action.toString());
        }
    }

// image listener implementation
//

    class ServerGDObjectListener extends UnicastRemoteObject implements GDObjectListener {
        final private Logger log = LoggerFactory.getLogger(getClass());


        public ServerGDObjectListener() throws RemoteException {
            super();
        }

        public void pushObjects(Vector<GDObject> objects) throws RemoteException {
            log.info("pushObjects-objects.size()=" + objects.size());
            for (GDObject o : objects) {
                log.info("pushObjects-o=" + o.toString());

                if (o instanceof GDImage) {

                    // .getImage() converts .jpg byte array into
                    // java BufferedImage
                    //
                    // to get pure .jpg, use getImageData()
                    //

                    BufferedImage img = ((GDImage) o).getImage();
                    System.out.println("Displaying image frame");

                }
            }
        }
    }
}
