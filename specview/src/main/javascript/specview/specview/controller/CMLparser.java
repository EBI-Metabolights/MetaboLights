import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;

import java.applet.Applet;

public class CMLparser extends Applet{

	  public void init() { }
	  
	  // This method is mandatory, but can be empty.(i.e.,have no actual code).
	  public void stop() { }
	
	/*
	   public String convertXMLFileToString() 
	   { 

	     try{ 
	       DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance(); 
	       InputStream inputStream = new FileInputStream(new File(this.fileName));
	       org.w3c.dom.Document doc = documentBuilderFactory.newDocumentBuilder().parse(inputStream); 
	       StringWriter stw = new StringWriter(); 
	       Transformer serializer = TransformerFactory.newInstance().newTransformer(); 
	       serializer.transform(new DOMSource(doc), new StreamResult(stw)); 
	       return stw.toString(); 
	     } 
	     catch (Exception e) { 
	       e.printStackTrace(); 
	     } 
	       return null; 
	   }
	   */
	   public void paint(Graphics g)
	   {
		    g.drawString("YOur file", 20,10);
	   }
	   
	   public String parse(String s){
//		   String res = "cool";
//		   DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
//		     SecurityManager security = System.getSecurityManager();
		   File f = new File(s);
//		   security.checkRead(s);
//		   try{
//			   InputStream inputStream = new FileInputStream(f);   
//		   }catch(java.io.FileNotFoundException e){
//			   res = "not cool";
//			   return e.toString();
//		   }
//		   return res;

		   try{ 
		       DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance(); 
		       InputStream inputStream = new FileInputStream(f);
		       org.w3c.dom.Document doc = documentBuilderFactory.newDocumentBuilder().parse(inputStream); 
		       StringWriter stw = new StringWriter(); 
		       Transformer serializer = TransformerFactory.newInstance().newTransformer(); 
		       serializer.transform(new DOMSource(doc), new StreamResult(stw)); 
		       return stw.toString(); 
		     } 
		     catch (Exception e) { 
		       e.printStackTrace(); 
		     } 
		       return null; 
		       
		   
	   }
		/*
		   String fileName = "/home/epu/test1_frag.cml.1.xml";
		     try{ 
			       DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance(); 
			       InputStream inputStream = new FileInputStream(new File(fileName));
			       org.w3c.dom.Document doc = documentBuilderFactory.newDocumentBuilder().parse(inputStream); 
			       StringWriter stw = new StringWriter(); 
			       Transformer serializer = TransformerFactory.newInstance().newTransformer(); 
			       serializer.transform(new DOMSource(doc), new StreamResult(stw)); 
			       return stw.toString(); 
			     } 
			     catch (Exception e) { 
			       e.printStackTrace(); 
			     } 
			       return null; 
			   }
		*/
}