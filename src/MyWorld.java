import java.util.*;
import java.io.*;

public class MyWorld {
   private PrintStream out;
   
   private ArrayList<PhysicsElement> elements;  // array to hold everything in my world.

   public MyWorld(){
      this(System.out);  // delta_t= 0.1[ms] and refreshPeriod=200 [ms]
   }
   
   public MyWorld(PrintStream output){
      out = output;
      elements = new ArrayList<PhysicsElement>();     
   }

   public void addElement(PhysicsElement e) {
      elements.add(e);
   }

   public void printStateDescription(){
     String s ="Time\t";
     for (PhysicsElement e:elements)
       s+=e.getDescription() + "\t";
     out.println(s);
   }

   public void printState(double t){
	   String line = t + "\t";
	   for (PhysicsElement e:elements)
		   line+=e.getState() + "\t";
    out.println(line);
   }

   public void simulate (double delta_t, double endTime, double samplingTime) {  // simulate passing time
      double t=0;
      printStateDescription();
      printState(t);
      while (t<endTime) {
         for(double nextStop=t+samplingTime; t<nextStop; t+=delta_t) {
           for (PhysicsElement e: elements)   // compute each element next state based on current global state  
              e.computeNextState(delta_t,this); // compute each element next state based on current global state
           for (PhysicsElement e: elements)  // for each element update its state. 
              e.updateState();     // update its state
         }
         printState(t);
      }
   }

public Ball findCollidingBall(Ball me) {
	   for (PhysicsElement e:elements) {
		   if(me.getClass() == e.getClass() && me.getId() != e.getId()){
			   Ball b = (Ball) e;
			   if(me.collide(b)){
				   return b;
			   }
			   else return null; 
		   }
	   }
	   return null;
   } 
} 