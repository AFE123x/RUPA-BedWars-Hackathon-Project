
import java.util.Comparator;

public class team {
    public long elapsedtime;
    public double points = 0; //THIS ONE
    public String teamname;
    public int index;
    public int racepoints = 0;
    public int bedpoints = 0;
    public int uniformpoints = 0;
    public int donation = 0;
    public int donationpoints = 0; 
    // Add fields and methods here
    public team(long elapsedtime, int points, String teamnamem,int index){
        this.elapsedtime = elapsedtime;
        this.teamname = teamnamem;
        this.index = index;
    }
    public team(){
        
    }

   
    //I think it can be a void method by we just adjust the points variable above. We only need to sort the data at the end
    // im using this method to make an array with the total points for each team 
    // and then using it to return in rupa.java
    // lmk what u wanna do and i can do that
    //we already have an array in the main method. I think the point of this method should be to populate the points variable here.
    //We can then sort the data in the main using the comparator comparebypoints. 
    // ok ill put a newe variable up there for totalpoints?..
    //We already have a variable for that. The one where I commented "THIS ONE".
   // got it
    public void calculateTotalScore(){
        this.points = (Double) (0.3*racepoints) + (0.2*bedpoints) + (0.2*uniformpoints) + (0.3*donationpoints);
    }

    public static Comparator<team> comparebypoints = new Comparator<team>() {
        @Override
        public int compare(team o1,team o2) {
            return Double.compare(o1.points, o2.points);
        }
    };
    public static Comparator<team> sortbyindex = new Comparator<team>() {
        @Override
        public int compare(team o1,team o2) {
            return Integer.compare(o1.index, o2.index);
        }
    };
    
    public static Comparator<team> compareByTime = new Comparator<team>() {
        @Override
        public int compare(team o1, team o2) {
            return Long.compare(o1.elapsedtime, o2.elapsedtime);
        }
    };
    public static Comparator<team> compareByDonations = new Comparator<team>() {
        @Override
        public int compare(team o1, team o2) {
            return Long.compare(o1.donation, o2.donation);
        }
    };


   
}
