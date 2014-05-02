import java.util.*;

public class Ball extends PhysicsElement {
   private static int id=0;  // Ball identification number
   private final double mass;
   private final double radius;
   private double pos_t;     // current position at time t
   private double pos_tPlusDelta;  // next position in delta time in future
   private double speed_t;   // speed at time t
   private double speed_tPlusDelta;   // speed in delta time in future
   private double a_t;    // acceleration at time t
   private double a_tMinusDelta;  // acceleration delta time ago;
   private ArrayList<Spring> springs;  // ArrayList can grow, arrays cannot. 

   private Ball(){   // nobody can create a block without state
     this(1.0,0.1,0,0);
   }
   
   public Ball(double mass, double radius, double position, double speed){
      super(id++);
      this.mass = mass;
      pos_t = position;
      speed_t = speed;
      this.radius = radius;	
      springs = new ArrayList<Spring>();
   }
   
   public void attachSpring(Spring sa){
	   springs.add(sa);
   }

   public void computeNextState(double delta_t, MyWorld world) {
     Ball b;  // Assumption: on collision we only change speed.   
     if ((b=world.findCollidingBall(this))!= null){ /* elastic collision */
        speed_tPlusDelta=(speed_t*(mass-b.getMass())+2*b.getMass()*b.getSpeed())/(mass+b.getMass());
        pos_tPlusDelta = pos_t;
        a_t= getNetForce()/mass;
        pos_tPlusDelta = pos_t + speed_t*delta_t + a_t*delta_t*delta_t;
     } else {
    	 
    	 /*
         a_t= getNetForce()/mass;
         speed_tPlusDelta= speed_t + a_t*delta_t;
         pos_tPlusDelta = pos_t + speed_t*delta_t + (0.5)*a_t*delta_t*delta_t;
         */ 
    	 
    	 a_t= getNetForce()/mass;
         speed_tPlusDelta=speed_t + 0.5*(3*a_t-a_tMinusDelta)*delta_t;
         pos_tPlusDelta = pos_t + speed_t*delta_t + (1/6)*(4*a_t-a_tMinusDelta)*delta_t*delta_t;
    	 
     }
   }
   
   public void updateState(){
	     pos_t = pos_tPlusDelta;
	     speed_t = speed_tPlusDelta;
	     a_tMinusDelta = a_t;
	   }
   
   public boolean collide(Ball b) {
	  double distance = this.pos_t - b.pos_t;
	  double collision = Math.sqrt(distance*distance);
	  double radiusT = this.radius + b.radius;
	  if(collision <= radiusT){	
		  return true;
	  }
	  else return false;
    }
   	
   private double getNetForce() { //TERMINAR
	   double NetForce = 0;
	   for (Spring spring: springs)
		   NetForce = NetForce + spring.getForce(this);
	   return NetForce;
   }
   
   public String getDescription() {
	   String line = null;
	   line = "Position_Ball"+ id + "\t"+ "Speed_Ball"+ id;
	   return line;
   
   }
   
   public String getState(){
	   String line = null;
	   line = pos_t + "\t" + speed_t;
	   return line;
   }
   
   public double getPosition(){
	   return pos_t;
   }
   
   public double getSpeed() {
	   return speed_t;
    }
   
   public double getMass(){
	   return mass;
   }

   public double getRadius() {
	   return radius;
   }
   

}