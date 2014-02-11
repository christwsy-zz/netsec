import java.util.*;
import java.io.*;
import java.net.*;
import java.lang.*;

class Homework {
   public static String MONITOR_NAME = "helios.ececs.uc.edu";
   public static int MONITOR_PORT = 8180;
   public static int HOST_PORT = 20000 +(int)(Math.random()*1000);
   public static int MAX = 5;
   ActiveClient ac;
   Server s;

    public Homework(String name, String password) {
        System.out.println("Project Begin:\n\tMonitor: "+MONITOR_NAME+" random port: "+HOST_PORT+" monitor port: "+MONITOR_PORT);
        ac = new ActiveClient(MONITOR_NAME, MONITOR_PORT, HOST_PORT, 0, name, password);
        s = new Server(HOST_PORT, HOST_PORT, name, password);
    }

    public static void main(String[] args) {
        Homework hw = new Homework("johne", "eversole");
        hw.ac.start(); //Start the Active Client
        hw.s.start();  //Start the Server
   }       
}
