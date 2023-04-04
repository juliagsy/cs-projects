package cw3.pckg.main;

import java.io.File;

import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

public class Main
{
  public static void main(String[] args) throws Exception
  {
    String webappLocation = "src/main/webapp";
    Tomcat tomcat = new Tomcat();
    tomcat.setPort(8080);

    tomcat.getConnector();

    StandardContext stdCtx = (StandardContext) tomcat.addWebapp("/", new File(webappLocation).getAbsolutePath());
    File webClasses = new File("target/classes");
    WebResourceRoot webRsrcRoot = new StandardRoot(stdCtx);
    webRsrcRoot.addPreResources(new DirResourceSet(webRsrcRoot, "/WEB-INF/classes", webClasses.getAbsolutePath(), "/"));
    stdCtx.setResources(webRsrcRoot);

    tomcat.start();
    tomcat.getServer().await();
  }
}
