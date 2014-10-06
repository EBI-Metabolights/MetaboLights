package uk.ac.ebi.metabolights.service;

import junit.framework.TestCase;

/**
 * Created by kalai on 01/07/2014.
 */
public class RCloudServiceImplTest extends TestCase {

//    private RCloudServiceImpl rCloudService = null;
//    private RServices rServices = null;
//
//    public void setUp() throws Exception {
//        super.setUp();
//        this.rCloudService = new RCloudServiceImpl();
//        this.rServices = this.rCloudService.getRServices("METABOLIGHTS_4G");
//    }
//
//    public void tearDown() throws Exception {
//
//    }
//
//    public void testGetRServices() throws Exception {
//        //assertEquals("IP assigned", rServices.getHostIp(), "10.7.35.59");
//        //assertFalse(rServices.getHostIp().equals(null));
//
//    }
//
//    public void testExecuteScript() throws Exception {
//        Vector<String> script = new Vector<String>();
//        //script.add("result <- sum(1+10)");
//        script = readScript(getClass().getResource("/inputfiles/metaboanalyst/test-scripts.R").getFile());
//        this.rCloudService.executeScript(this.rServices, script);
////        RObject obj = this.rServices.getObject("res");
////        RNumeric charac = (RNumeric) obj;
////        System.out.println("RESULT: " + charac.getValue()[0]);
//
//
////        RObject obj = this.rServices.getObject("result");
////        RNumeric num = (RNumeric) obj;
////        assertEquals("Expected result", num.getValue()[0], 11.0);
//        //System.out.println("Has objects ? "+this.rServices.listDevices()[0].hasGraphicObjects());
////        this.rCloudService.saveImageDataToFile(this.rServices.listDevices()[0].getJpg(),
////                "/Users/kalai/IdeaProjects/MetaboLights/metabolomes-code/metabolights-webapps/src/test/resources/inputfiles/metaboanalyst/testy.png");
//
//
//    }
//
//    public void testSaveImageDataToFile() throws Exception {
//
//    }
//
//    public void testFreeRServices() throws Exception {
//        this.rCloudService.freeRServices(this.rServices);
//        // assertEquals("All listeners released", 0, rServices.listDevices().length);
//    }
//
//
//    private Vector<String> readScript(String scriptfilename) {
//        Vector<String> script = new Vector<String>();
//        try {
//            BufferedReader input = new BufferedReader(new FileReader(new File(scriptfilename)));
//            try {
//                String line = null;
//                while ((line = input.readLine()) != null) {
//                    System.out.println(line);
//                    script.add(line);
//                }
//            } finally {
//                try {
//                    input.close();
//                } catch (IOException ioe) {
//                }
//            }
//        } catch (FileNotFoundException fnfe) {
//            fnfe.printStackTrace();
//        } catch (IOException ioe) {
//            ioe.printStackTrace();
//        }
//        return script;
//    }
}