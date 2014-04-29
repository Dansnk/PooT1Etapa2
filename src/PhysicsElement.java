public abstract class PhysicsElement {
   private final int myId; /* to identify each element within its category */
   
   protected PhysicsElement( int id){
      myId = id;
   }
   
   protected int getId() {
      return myId;
   }
   
   public abstract String getDescription();
   public abstract String getState();
   public abstract void updateState();
   public abstract void computeNextState(double delta_t, MyWorld myWorld);
}