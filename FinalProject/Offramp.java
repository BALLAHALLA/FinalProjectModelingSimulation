// OOSimL v 1.1 File: Offramp.java, Wed Feb 24 11:35:39 2016
 
import java.util.*; 
import java.io.*; 
import java.text.DecimalFormat; 
import psimjava.*; 
/** 
description
OOSimL model of a ramp System with conditional waiting.
Ships arrive at a ramp and unload their cargo. Ships request
2 tugboats and a pier (harbor deck). To leave the ships 
request 1 tugboat.
The activities of a ship, ddocking, unloading, and leaving, 
have a finite period of duration.
If the resources are not available, ships have to wait.

Condition: Ships are only allowed to dock if 2 tugboats are
available and the tide is not low. 
The tide changes every 13 hours. The low tide lasts
 for 4 hours. The tide is modeled as a process.
This system is similar to an operating system in which
processes have conditional waiting, using conditional
variables in monitors.

(C) J M Garrido, Updated Nov. 2015.
Dept of Computer Science, Kennesaw State University

Main class: Offramp, File: Offramp.osl
*/
 public  class Offramp  extends psimjava.Process     {
static Scanner scan = new Scanner (System.in);
 private  static double  arr_mean; 
  // mean inter-arrival period    
 private  static double  unloadmean; 
  // mean unload interval  
 private  static double  unloadstdev; 
  // unload standard dev 
 // global variables 
 public  static int  numarrivals = 0; 
  // number of arriving ships 
 // accum wait period 
 public  static double  acc_wait = 0.0; 
  // 
 public  static double  simperiod; 
  // simulation time period 
 public  static double  close_arrival; 
  // close time for arrival 
  public  static Condq dockq; 
 // conditional wait 
  public  static Res tugs; 
 // tugboats 
 // jetties 
  //public  static Res piers; 
 // 
  public  static Simulation run; 
 // 
  public  static Arrivals carrivals; 
  public  static RushHour cur_rushhour; 
  public  static Offramp ramp; 
  public  static PrintWriter statf; 
 // file for statistics 
  public  static PrintWriter tracef; 
 // file for trace 
 public Offramp(  String  rampname) { 
super(rampname); 
 } // end initializer 
 // 
 // function to evaluate condition 
 // there must be atleast two tugboats available and 
 // the tide must not be low 
 static  public boolean  rush_hour(   ) { 
 //if ( (( tugs.num_avail() >= 2) ) && (( cur_rushhour.get_highwaybusy() !=  true ) )) { 
//return  true ;
if ( ( cur_rushhour.get_highwaybusy() !=  true ) ) { 
return  true ;  
 }
 else {
return  false ; 
 } // endif 
 }  // end rush_hour 
 // 
 public static void main(String[] args) { 
simperiod =  5760.5;
 // simulation period 
 // time to close ship arrivals 
close_arrival =  4073.5;
 // 
arr_mean =  46.45;
 // mean inter-arrival period for ships 
unloadmean =  24.5;
 // mean unload interval  
 // unload standard dev 
unloadstdev =  3.85;
 // 
 // 
 run = new Simulation( "ramp System with Conditions" );
 // setup files for reporting 
 Simulation.set_tracef("offramptrace.txt");
tracef = Simulation.get_tracefile();
 // 
 Simulation.set_statf("offrampstatf.txt");
statf = Simulation.get_statfile();
 // create standard resource objects 
//tugs = new Res("Tugs", 3);
 // 
//piers = new Res("Piers", 2);
 // Conditional wait object 
 // 
dockq = new Condq("Dock cond queue");
 // main active object of model 
ramp = new Offramp("ramp with Conditions");
 ramp.start();
 }  // end main 
 // 
 public void  Main_body(   ) { 
 double  var1; 
  String  fmtout1; 
  // format object round output to three decimal positions 
  DecimalFormat dfmt; 
dfmt = new DecimalFormat("0.###");
statf =  Simulation.get_statfile();
 // access statistics file 
 // access trace file     
tracef =  Simulation.get_tracefile();
 // 
System.out.println("Initiating Arrivals with mean inter-arrival: "+ 
arr_mean);
carrivals = new Arrivals("Arrivals ", arr_mean, unloadmean, unloadstdev);
 carrivals.start();
 // 
cur_rushhour = new RushHour("RushHour");
 cur_rushhour.start();
 // 
System.out.println("Starting simulation ...");
 run.start_sim( simperiod );
 // 
statf.println("Total number of ships that arrived: "+ 
numarrivals);
statf.flush();
System.out.println("Total number of ships that arrived: "+ 
numarrivals);
var1 =  (acc_wait)/(numarrivals);
fmtout1 =  dfmt.format(var1);
statf.println("Average wait period: "+ 
fmtout1);
statf.flush();
System.out.println("Average wait period: "+ 
fmtout1);
 // 
System.exit(1); 
 }  // end Main_body 
} // end class/interf Offramp 
