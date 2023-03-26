import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.Scanner;

public class RUPA {
    private static team [] teams;
    private static Scanner inputscanner = new Scanner(System.in);
    private static Scanner filescanner;
    private static MaxPQ max;
    private static StopWatch sw = new StopWatch();

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
        for(int i = teams.length -1; i >= 0; i--){
            System.out.println(i + ": " + teams[i].teamname + " with " + teams[i].points + " points.");
        }
        

    }
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
    private static void race() throws InterruptedException{
        System.out.println("Round 1!");
        max = new MaxPQ(teams.length, team.compareByTime);
        for(int i = 0; i < teams.length; i++){
            System.out.println("contestant " + teams[i].teamname);
            System.out.println("enter P to begin countdown");
            inputscanner.next();
            teams[i].elapsedtime = timerpo();
            max.insert(teams[i]);
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
            
            System.out.println("contestant " + teams[asdf].teamname);
            System.out.println("enter P to begin countdown");
            inputscanner.next();
            a.elapsedtime = timerpo();
            max.insert(a);
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
            System.out.println("contestant " + teams[asdf].teamname);
            System.out.println("enter P to begin countdown");
            inputscanner.next();
            a.elapsedtime = timerpo();
            max.insert(a);
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
            System.out.println("contestant " + teams[asdf].teamname);
            System.out.println("enter P to begin countdown");
            inputscanner.next();
            a.elapsedtime = timerpo();
            max.insert(a);
            asdf++;
        }
        teams[teams.length - 2] = max.delMax();
        teams[teams.length-2].racepoints += 25;
        teams[teams.length - 1] = max.delMax();
        teams[teams.length-1].racepoints += 30;



    }
    public static String formatDuration(long millis) {
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format("%02dmin%02dsec", minutes, seconds);
    } 
    private static Long timerpo() throws InterruptedException{
        System.out.println("3");
        Thread.sleep(1000);
        System.out.println("2");
        Thread.sleep(1000);
        System.out.println("1");
        Thread.sleep(1000);
        System.out.println("start");
        String tim = "s";
        sw.start();
        System.out.println("enter q to stop stopwatch");
        while(!tim.equals("q")){
            tim = inputscanner.nextLine();
        }
        sw.stop();
        System.out.println(formatDuration(sw.getElapsedTime()));
        return sw.getElapsedTime();
    }
    
    
}
