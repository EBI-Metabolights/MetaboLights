HelloWorld Applet

public class HelloWorld extends java.applet.Applet
{
   public void paint(java.awt.Graphics g)
   {
        g.drawString("Hello World!",50,25);
        System.out.println("Hello World!");
    }
} 