// OOSimL v 1.1 File: Arrivals.java, Wed Feb 24 11:35:28 2016
 
import java.util.*; 
import psimjava.*; 
/** 
description
 OOSimL model of a Port System with conditional waiting.
Cars arrive at a port and unload their cargo. Cars request
2 tugboats and a pier (harbor deck). To leave the Cars request 1 tugboat.
The activities of a Car, ddocking, unloading, and leaving, 
have a finite period of duration.
If the resources are not available, Cars have to wait.
Condition: Cars are only allowed to dock if 2 tugboats are
available and the tide is not low. 
The tide changes every 13 time units. The low tide lasts
 for 4 time units. The tide is modeled as a process.
This system is similar to an operating system in which
processes have conditional waiting, using conditional
variables in monitors.
An object of this class creates Car objects randomly using a negative 
exponential distribution (NegExp), given the mean inter-arrival period.

 (C) J M Garrido June 2011. Updated Nov. 2014.
 Department of Computer Science, Kennesaw State University
 
 Main class: Offramp  File: Arrivals.osl 
*/
 public  class Arrivals  extends psimjava.Process     {
static Scanner scan = new Scanner (System.in);
 private String  arrname; 
  private double  unloadmean; 
  private double  unloadstd; 
   private NegExp next; 
 // Random number generator 
  private Normal unload; 
 // Random number generator 
 public Arrivals(  String  larrname, double  arr_mean, double  umean, double  unlstd) { 
super(larrname); 
 // generator of random numbers for inter-arrival times  
arrname =  larrname;
 // Negative exponential probability distribution 
System.out.println(arrname+ 
" arr_mean: "+ 
arr_mean+ 
" umean: "+ 
umean+ 
" unlstd: "+ 
unlstd);
next = new NegExp("Car inter-arrival", arr_mean);
 //  
unloadmean =  umean;
 // unload mean interval (Normal dist)  
 // unload standard deviation  
unloadstd =  unlstd;
 // random unload interval 
unload = new Normal("Unload interval", unloadmean, unloadstd);
 } // end initializer 
 // 
 public void  Main_body(   ) { 
 double  simclock; 
  double  inter_arr; 
  // inter-arrival interval 
 double  unloadper; 
  // unload period 
 int  carnum; 
   Car lcar; 
 simclock = StaticSync.get_clock();
 while ( simclock <= Offramp.close_arrival ) { 
 // generate random interarrival interval 
 inter_arr = next.fdraw(); 
System.out.println(arrname+ 
" Inter-arrival interval: "+ 
inter_arr);
 // 
  delay(inter_arr);
 // wait for Car arrival 
 Offramp.numarrivals++;
 // increment Car counter 
 // Car number 
 // 
carnum =  Offramp.numarrivals;
 // random unload interval 
 unloadper = unload.fdraw(); 
lcar = new Car("Car" + carnum, unloadper);
 lcar.start();
 simclock = StaticSync.get_clock();
 } // endwhile 
System.out.println(arrname+ 
" terminates at: "+ 
simclock);
  terminate();
 }  // end Main_body 
} // end class/interf Arrivals 
