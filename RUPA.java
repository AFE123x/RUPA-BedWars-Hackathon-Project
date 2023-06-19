import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Year;
import java.util.Comparator;
import java.util.Scanner;
/*This file contains the main method for this program. This is the program to run.  */
public class RUPA {
  private static team [] teams;
  private static Scanner inputscanner = new Scanner(System.in);
  private static Scanner filescanner;
  private static MaxPQ max;

  public static void main(String[] args) throws FileNotFoundException, InterruptedException{
    filescanner = new Scanner(new File("input.txt"));
    populate();
    race();
    heapsort(teams, team.sortbyindex);
    //
    System.out.println("place the following files : judge1.txt, judge2.txt, judge3.txt, judge4.txt (must be in same folder as program.)");
    inputscanner.nextLine();
    addpoints("judge1.txt");
    addpoints("judge2.txt");
    addpoints("judge3.txt");
    addpoints("judge4.txt");
    heapsort(teams, team.compareByDonations);
    donations();
    for(int i = 0; i < teams.length; i++){
      teams[i].calculateTotalScore();
    }
    heapsort(teams,team.comparebypoints);
    int place = 1;
    for(int i = teams.length -1; i >= 0; i--){
      writeToTextFile((getOrdinalNumber(place) + " place: " + teams[i].teamname + " with " + teams[i].points + " points."), ("results" +Year.now().getValue() + ".txt"));
      //System.out.println(getOrdinalNumber(place) + " place: " + teams[i].teamname + " with " + teams[i].points + " points.");
      place++;
    }
    System.exit(0);
    

  }
  /*This method writes the results, or anystring to a new notepad file
   * named, "results2023", where 2023 can be replaced with whatever year.
   * Automatically tracked. 
   */
   public static void writeToTextFile(String content, String filePath) {
    try {
      // If filePath is null or empty, set the default file name
      if (filePath == null || filePath.isEmpty()) {
        filePath = "results" + Year.now().getValue() + ".txt";
      }

      BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
      writer.write(content);
      writer.newLine();
      writer.close();

       
    } catch (IOException e) {
      System.out.println("An error occurred while writing to the file: " + filePath);
      e.printStackTrace();
    }
  }
  /*Makes the calculations for donations */
  private static void donations(){
    heapsort(teams,team.compareByDonations);
    teams[teams.length-1].donationpoints += 30;
    teams[teams.length - 2].donationpoints +=25;
    teams[teams.length - 3].donationpoints += 20;
    int min = findmin();
    for(int i = teams.length - 4; i >= 0; i--){
      if(teams[i].donation >= (min * 3)){
        teams[i].donationpoints += 15;
      }
      else if(teams[i].donation >= (min * 2)){
        teams[i].donationpoints += 10;
      }
      else if(teams[i].donation >= min){
        teams[i].donationpoints += 5;
      }
      else{
        teams[i].donationpoints += 0;
      }
    }

  }
  private static int findmin(){
    int i = 0;
    while(teams[i].donation <1){
      i++;
    }
    return teams[i].donation;
  }
  private static void heapsort(team [] fufu,Comparator<team> c){
    max = new MaxPQ(fufu.length, c);
    for(int i = 0; i < fufu.length; i++){
      max.insert(teams[i]);
    }
    for(int i = fufu.length-1; i >= 0; i--){
      fufu[i] = max.delMax();
    }
  }
  /*
   * This method scans the text file, "input.txt", which will contain the name of the teams, and the number of donations. 
   */
  private static void populate(){ 
    int size = filescanner.nextInt();
    teams = new team[size];
    for(int i = 0; i < teams.length; i++){
      teams[i] = new team();
      teams[i].teamname = filescanner.next();
      teams[i].donation = filescanner.nextInt();
      teams[i].index = i;
    }
  }
  /*
   * Reads the data from Judge text files. First, the scanner reads the size of the data.
   *  Afterwards, it reads the bed points, then uniform points. 
   */
  private static void addpoints(String filename) throws FileNotFoundException, InterruptedException{
    Scanner s = new Scanner(new File(filename));
    int size = s.nextInt();
    for(int i = 0; i < size; i++){
      s.next();
      teams[i].bedpoints += s.nextInt();
      if(teams[i].bedpoints > 6){
        teams[i].bedpoints = 6;
      }
      if(teams[i].bedpoints < 1){
        teams[i].bedpoints = 1;
      }
      teams[i].uniformpoints += s.nextInt();
      if(teams[i].uniformpoints > 6){
        teams[i].uniformpoints = 6;
      }
      if(teams[i].uniformpoints < 1){
        teams[i].uniformpoints = 1;
      }
    }

  }
  /*
   * This method opens up the StopWatch GUI, and puts the time into the  
   * Priority Queue. 
   * 
   * To-Do: Try and optimize program, so it kills the program before restarting. 
   * Lag is apparent. 
   */
  private static void Stopwatchmethod(team T) throws InterruptedException{
    StopWatch sw = new StopWatch(T.teamname);
    while (!sw.kill) {
    Thread.sleep(10);
    }
    T.elapsedtime = sw.getElapsedTime();
      sw.closeStopWatch();
      max.insert(T);
  }
  /*
   * Actual race method. 
   */
  private static void race() throws InterruptedException{
    System.out.println("Round 1!");
    max = new MaxPQ(teams.length, team.compareByTime);
    for(int i = 0; i < teams.length; i++){
      Stopwatchmethod(teams[i]);
    }
    for(int i = 0; i < teams.length-8; i++){
      teams[i] = max.delMax();
      teams[i].racepoints += 5;
    }
    team [] eight = new team[8];
    for(int i = 0; i < 8; i++){
      eight[i] = max.delMax();
    }
    System.out.println("Round 2!");
    max = new MaxPQ(8, team.compareByTime);
    int asdf = 1;
    for(team a : eight){
       Stopwatchmethod(a);
      asdf++;
    }
    for(int i = teams.length - 8; i < teams.length - 4; i++){
      teams[i] = max.delMax();
      teams[i].racepoints += 15;
    }
    team [] four = new team [4];
    for(int i = 0; i < 4; i++){
      four[i] = max.delMax();
    }
    System.out.println("Round 3!");
    max = new MaxPQ(four.length, team.compareByTime);
    asdf = 1;
    for(team a : four){
      Stopwatchmethod(a);
      asdf++;
    }
    for(int i = teams.length - 4; i < teams.length - 2; i++){
      teams[i] = max.delMax();
      teams[i].racepoints += 20;
    }
    team [] two = new team[2];
    for(int i = 0; i < 2; i++){
      two[i] = max.delMax();
    }
    System.out.println("Round 4!");
    max = new MaxPQ(2, team.compareByTime);
    asdf = 0;
    for(team a : two){
      Stopwatchmethod(a);
      asdf++;
    }
    teams[teams.length - 2] = max.delMax();
    teams[teams.length-2].racepoints += 25;
    teams[teams.length - 1] = max.delMax();
    teams[teams.length-1].racepoints += 30;



  }
  /*
   * Simply returns the ranking based off of a number paramter. 
   * For example, inputting 1 will return 1st, 2 will return 2nd, etc.
   */
  public static String getOrdinalNumber(int number) {
  String ordinalSuffix;

  if (number >= 11 && number <= 13) {
    ordinalSuffix = "th";
  } else {
    int lastDigit = number % 10;

    switch (lastDigit) {
      case 1:
        ordinalSuffix = "st";
        break;
      case 2:
        ordinalSuffix = "nd";
        break;
      case 3:
        ordinalSuffix = "rd";
        break;
      default:
        ordinalSuffix = "th";
        break;
    }
  }

  return number + ordinalSuffix;
}

}
