import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.*;
import java.awt.*;

public class ActiveClient extends MessageParser implements Runnable {

    public static String MonitorName;
    Thread runner;
    Socket toMonitor = null;
    public static int MONITOR_PORT;
    public static int LOCAL_PORT;
    public int SleepMode;
    int DELAY = 90000;  //Interval after which a new Active Client is started
    long prevTime,present;
    String name;

    public ActiveClient() {
        super("[no-name]", "[no-password]");
        MonitorName="";
        toMonitor = null;
        MONITOR_PORT=0;
        LOCAL_PORT=0;
    }

    public ActiveClient(String mname, int p, int lp, int sm, String name, String password) {
        super(name, password);
        try {
            SleepMode = sm;
            MonitorName = mname;
            MONITOR_PORT = p;
            LOCAL_PORT = lp;
            name = name;
        } catch (NullPointerException n) {
            System.out.println("Active Client [Constructor]: TIMEOUT Error: "+n);
        }
    }

    public void start() {
        if (runner == null) {
            runner = new Thread(this);
            runner.start();
        }
    }

    public void run() {
        while(Thread.currentThread() == runner) {
            try {
                System.out.print("Active Client: trying monitor: "+MonitorName+ " port: "+MONITOR_PORT+"...");
                System.out.println("\n\n\n\n\n\n\n NAME: " + name + "\n\n\n\n\n\n\n\n\n\n");
                toMonitor = new Socket(MonitorName, MONITOR_PORT);
                System.out.println("completed.");
                out = new PrintWriter(toMonitor.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(toMonitor.getInputStream()));

                HOSTNAME = toMonitor.getLocalAddress().getHostName();
                CType = 0;   //Indicates Client
                HOST_PORT = LOCAL_PORT;
                if (!Login()) {
                    if (IsVerified == 0) System.exit(1);
                }

                /*
                System.out.println("\nTrying request.....");
                Execute("TRANSFER_REQUEST");

                String msg = GetMonitorMessage();
                String next = GetNextCommand(msg, "");

                if (next != null) {
                    do {
                        System.out.println("msg: " + msg);
                        System.out.println("next: " + next);
                        Execute(next);
                        msg = GetMonitorMessage();
                        next = GetNextCommand(msg, "");
                    } while (next != null && !next.equals("QUIT"));
                }
                */

                /*
                System.out.println("***************************");
                if (Execute("GET_GAME_IDENTS")) {
                String msg = GetMonitorMessage();
                System.out.println("ActiveClient [GET_GAME_IDENTS]:\n\t"+msg);
                }
                if (Execute("RANDOM_PARTICIPANT_HOST_PORT")) {
                String msg = GetMonitorMessage();
                System.out.println("ActiveClient [RANDOM_PARTICIPANT_HOST_PORT]:\n\t"+msg);
                }
                if (Execute("PARTICIPANT_HOST_PORT", "FRANCO")) {
                String msg = GetMonitorMessage();
                System.out.println("ActiveClient [PARTICIPANT_HOST_PORT]:\n\t"+msg);
                }
                if (Execute("PARTICIPANT_STATUS")) {
                String msg = GetMonitorMessage();
                System.out.println("ActiveClient [PARTICIPANT_STATUS]:\n\t"+msg);
                }
                ChangePassword(PASSWORD);
                System.out.println("Password:"+PASSWORD);
                */

                toMonitor.close();
                out.close();
                in.close();
                try { runner.sleep(DELAY); } catch (Exception e) {}

            } catch (UnknownHostException e) {
            } catch (IOException e) {
                try {
                    toMonitor.close();
                    //toMonitor = new Socket(MonitorName,MONITOR_PORT);
                } catch (IOException ioe) {
                } catch (NullPointerException n) {
                    try {
                    toMonitor.close();
                    //toMonitor = new Socket(MonitorName,MONITOR_PORT);
                    } catch (IOException ioe) {}
                }
            }
        }
    }
}
