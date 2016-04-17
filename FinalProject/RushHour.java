// OOSimL v 1.1 File: RushHour.java, Wed Feb 24 11:35:57 2016
 
import java.util.*; 
import psimjava.*; 
/** 
description
OOSimL model of a Port System with conditional waiting
Ships arrive at a port and unload their cargo. Ships request
2 tugboats and a pier (harbor deck). To leave the ships request 1 tugboat.
The activities of a ship, ddocking, unloading, and leaving, 
have a finite period of duration.
If the resources are not available, ships have to wait.
Condition: Ships are only allowed to dock if 2 tugboats are
available and the RushHour is not low.
 
This class represents the behavior of the RushHour object, which changes
state from low RushHour to high RushHour and from high to low. 
The RushHour changes every 13 hours (time units). The low RushHour lasts
 for 4 time units. The RushHour is modeled as a process.
 
(C) J M Garrido, June 2011. Updated Nov. 2014. 
Dept of Computer Science, Kennesaw State University

Main class: Offramp File: RushHour.java
*/
 public  class RushHour  extends psimjava.Process     {
static Scanner scan = new Scanner (System.in);
 private boolean  highwaybusy; 
  // state of tide object 
 private String  rushHourName; 
  // name of tide object 
 public RushHour(  String  rname) { 
super(rname); 
highwaybusy =   true ;
rushHourName =  rname;
System.out.println(rushHourName+ 
" created ");
 } // end initializer 
 // 
 public boolean  get_highwaybusy(   ) { 
return highwaybusy; 
 }  // end get_highwaybusy 
 // 
 public void  Main_body(   ) { 
 double  simclock; 
  double  highwaybusydur; 
  double  highwayNotBusydur; 
  simclock = StaticSync.get_clock();
highwaybusydur =  (7.0) * (60.0);
 // low tide duration in min 
highwayNotBusydur =  (15.0) * (60.0);
 while ( simclock <= Offramp.simperiod ) { 
highwaybusy =   true ;
System.out.println(rushHourName+ 
" highwaybusy = true at: "+ 
simclock);
tracedisp(rushHourName+ 
" highwaybusy = true");
  delay(highwaybusydur);
 // duration of low tide 
highwaybusy =   false ;
 simclock = StaticSync.get_clock();
System.out.println(rushHourName+ 
" highwaybusy = false at: "+ 
simclock);
tracedisp(rushHourName+ 
" highwaybusy = false");
  Offramp.dockq.signal();
  delay(highwayNotBusydur);
 // duration of high tide 
 simclock = StaticSync.get_clock();
 } // endwhile 
  terminate();
 }  // end Main_body 
} // end class/interf Tide 
