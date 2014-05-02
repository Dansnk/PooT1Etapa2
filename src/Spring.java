public class Spring extends PhysicsElement {
   private static int id=0;  // Spring identification
   protected final double restLength;
   private final double stiffness;
   protected Ball a_end, b_end;

   private Spring(){   // nobody can create a block without state
      this(0,0);
   }
   
   public Spring(double restLength, double stiffness){
      super(id++);
      this.restLength = restLength;
      this.stiffness = stiffness;
      a_end = b_end = null;
   }
   
   public void attachEnd (Ball sa) {  // note: we attach a spring to a ball, 
      if(a_end==null){                //       not the other way around.
         a_end = sa;
      	 sa.attachSpring(this);
      } else {
    	  if (b_end==null){
    	  b_end = sa;
      	  sa.attachSpring(this);
    	  }
      }
   }
   
   private double getAendPosition() {
      if (a_end != null){
         return a_end.getPosition();
      } else {
    	  if (b_end != null)
         return b_end.getPosition()-this.restLength;
      }
      return 0;
   }
   
   public double getBendPosition() {
	   if (b_end != null){
		   return b_end.getPosition();
	   } else {
		   if (a_end != null)
		   return a_end.getPosition() + this.restLength;
	   }
	   return this.restLength;
   }

   public double getForce(Ball ball) {
	   double force = 0;
	   force=(Math.abs(getAendPosition()-getBendPosition())-restLength)*stiffness;
	      if((getAendPosition() < getBendPosition())^ (ball==a_end))
	    	  return -force;
	      else return force;
   }

   public void computeNextState(double delta_t, MyWorld w){
	   }
   
   public void updateState(){
   }

   public String getDescription() {
      return "Spring_"+ getId()+":a_end\tb_end";
   }
   
   public String getState() {
	   String line;
	   line = "\t"+ this.getAendPosition() + "\t"+ this.getBendPosition();
	   return line;
   }
}