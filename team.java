
import java.util.Comparator;

public class team {
  public long elapsedtime;
  public double points = 0; 
  public String teamname;
  public int index;
  public int racepoints = 0;
  public int bedpoints = 0;
  public int uniformpoints = 0;
  public int donation = 0;
  public int donationpoints = 0; 
  public team(long elapsedtime, int points, String teamnamem,int index){
    this.elapsedtime = elapsedtime;
    this.teamname = teamnamem;
    this.index = index;
  }
  public team(){
    
  }
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
