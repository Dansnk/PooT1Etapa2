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
         return a_end.getPosition()+a_end.getRadius();
      } else {
    	  if (b_end != null)
    		  return b_end.getPosition()-b_end.getRadius()-this.restLength;
      }
      return 0;
   }
   
   public double getBendPosition() {
	   if (b_end != null){
		   return b_end.getPosition()-b_end.getRadius();
	   } else {
		   if (a_end != null)
			   return a_end.getPosition()+ a_end.getRadius()+ this.restLength;
	   }
	   return this.restLength;
   }

   public double getForce(Ball ball) {
      double force = 0;
      if ((a_end == null) || (b_end == null))
         return force;
      if ((ball != a_end) && (ball != b_end))
         return force;
      if (ball==a_end)
      {
    	 double length = this.getBendPosition()- this.getAendPosition();  //xb-xa > 0;
    	 force = this.stiffness * (length - this.restLength); 
    	 return force;
      }
      else if (ball==b_end){
    	 double length = this.getBendPosition()- this.getAendPosition();  //xb-xa > 0;
    	 force = -1*this.stiffness * (length - this.restLength);
    	 return force; 
      }
      return force; 
   }
   
   //POSIBLEMENTE MAL HECHA
	   public void computeNextState(double delta_t, MyWorld w){
		   /*if ( a_end != null)
			   a_end.computeNextState(delta_t,w);
		   if (b_end != null)
			   b_end.computeNextState(delta_t, w);
			  */
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
   
   public double getStiffness(){
	   return this.stiffness;
   }
}