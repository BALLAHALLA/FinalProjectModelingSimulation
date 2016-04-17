// OOSimL v 1.1 File: Car.java, Wed Feb 24 11:35:47 2016
 
import java.util.*; 
import psimjava.*; 
/** 
description
 OOSimL model of a Port System with conditional waiting	
 Ships arrive at a port and unload their cargo. Ships request
 2 tugboats and a pier. To leave the ships request 1 tugboat.
 The activities of a Car, docking, unloading, and leaving, 
 have a finite period of duration.
 If the resources are not available, Cars have to wait.

 Condition: Cars are only allowed to dock if 2 tugboats are
 available and the tide is not low. 
 The tide changes every 12 hours. The low tide lasts
  for 4 hours. The tide is modeled as a process.

 This system is similar to an operating system in which
 processes have conditional waiting, using conditional
 variables in monitors.

 Activity of a Car object:
 1. conditional wait for tugboats and low tide
 2. dock
 3. unload
 4. undock
 5. leave (terminate)

 Assumptions: 
 1. dock interval is constant
 2. undock interval takes about 65% of dock interval
 (C) J M Garrido June 2011. Updated Nov. 2014.
 Department of Computer Science, Kennesaw State University
 File: Car.osl
*/
 public  class Car  extends psimjava.Process     {
static Scanner scan = new Scanner (System.in);
 private double  startw; 
  // Car start time of wait 
 private double  cdelay; 
  // unload interval 
 private double  simclock; 
  // current simulation time 
 private String  carname; 
   private  static RushHour crushhour; 
 // ship number 
 public int  carnum; 
  // 
 public Car(  String  crname, double  carDelay) { 
super(shname); 
cdelay =  carDelay;
carnum =  Offramp.numarrivals;
 // ship number 
carname =  shname;
 } // end initializer 
 // 
 public void  Main_body(   ) { 
 double  wait_per; 
  // wait interval for this ship 
 double  doc_per; 
  // dock interval 
 //int  tugsavail; 
  // tug boats available 
 boolean  mcond =  false ; 
  // condition for RushHour 
 simclock = StaticSync.get_clock();
System.out.println(carname+ 
" arrives at: "+ 
simclock);
tracedisp(carname+ 
" arrives ");
 // constant dock period 
doc_per =  1.0;
 // assign simulation clock to simclock 
 // start time to wait 
startw =  simclock;
 // 
//System.out.println(carname+ 
//" requests pier at: "+ 
//simclock);
 // 
//tracedisp(carname+ 
//" requests pier");
 // attempt to acquire pier 
 //Offramp.piers.acquire(1);
 // 
 simclock = StaticSync.get_clock();
//System.out.println(carname+ 
//" acquired pier at time: "+ 
//simclock);
 // wait for high RushHour and 2 tugboats 
 // display carname, " waits until condition true at: ", simclock 
 // tracewrite carname, " waits until condition true" 
//tracedisp(carname+ 
//" acquired pier");
 //          
mcond =   false ;
 while ( mcond !=  true  ) { 
 
 doc_per = 5.0;
mcond =  Offramp.rush_hour();
 // evaluate condition 
System.out.println(carname+ 
" Testing condition: "+ 
mcond+ 
" at: "+ 
simclock);
tracedisp(carname+ 
" Testing condition: "+ 
mcond);
 //    
 //Offramp.dockq.waituntil(mcond);
 // 
 simclock = StaticSync.get_clock();
 // 
 // display carname, " continues at: ", simclock 
 // tracewrite carname, " continues" 
 // 
 // 
 } // endwhile 
 // wait for available tugboats 
 // display "Number of available tugboats: ", tugsavail 
 tugsavail = Offramp.tugs.num_avail(); 
 // 
 // attempt to acquire tugboats 
 Offramp.tugs.acquire(2);
 // 
 simclock = StaticSync.get_clock();
System.out.println(carname+ 
" acquires 2 tugboats at: "+ 
simclock);
tracedisp(carname+ 
" acquires 2 tugboats");
 // 
wait_per =  (simclock) - (startw);
 // wait interval 
 // accumulate wait 
 //  
 Offramp.acc_wait += wait_per;
 // ship now will dock 
 // dock interval 
  delay(doc_per);
 // 
 Offramp.tugs.release(2);
 // 
 simclock = StaticSync.get_clock();
System.out.println(carname+ 
" has docked releases tugs, signaling dockq at: "+ 
simclock);
tracedisp(carname+ 
" has docked, releases tugs");
 //    
 // 
  Offramp.dockq.signal();
 // ship will now cdelay 
System.out.println(carname+ 
" starts to cdelay for: "+ 
cdelay);
tracedisp(carname+ 
" starts to cdelay for: "+ 
cdelay);
 // 
 // take time to cdelay 
  delay(cdelay);
 // 
 simclock = StaticSync.get_clock();
System.out.println(carname+ 
" finished unloading, requesting tugboat at: "+ 
simclock);
tracedisp(carname+ 
" finished unloading, requesting tugboat");
 //    
 // start to wait again 
startw =  simclock;
 // 
 Offramp.tugs.acquire(1);
 // 
simclock =  get_clock();
System.out.println(carname+ 
" acquires 1 tugboat at "+ 
simclock);
tracedisp(carname+ 
" acquires 1 tugboat to undock");
 // 
wait_per =  (simclock) - (startw);
 // 
 Offramp.acc_wait += wait_per;
 // undock 
 // time ship takes to undock 
  delay(( (0.65) * (doc_per)) );
 // 
 // release the tugboat 
 Offramp.tugs.release(1);
 // 
simclock =  get_clock();
System.out.println(carname+ 
" releases tugboat at: "+ 
simclock);
tracedisp(carname+ 
" releases tugboat");
 // 
 // release the pier 
 //Offramp.piers.release(1);
 // 
 // signal condition object 
  Offramp.dockq.signal();
 // 
System.out.println(carname+ 
" departing at: "+ 
simclock);
tracedisp(carname+ 
" departing");
  terminate();
 // terminate this ship process 
 }  // end Main_body 
} // end class/interf Ship 
