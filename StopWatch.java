import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StopWatch extends JFrame {
  private boolean running;
  private long startTime;
  public long stopTime;
  private long elapsedTime;
  public boolean kill = false;
  private JLabel teamLabel;
  private JLabel timeLabel;
  private JButton startStopButton;
  private Timer closeTimer; // Timer for automatic closing

  public StopWatch(String teamName) {
    super("StopWatch");

    running = false;
    startTime = 0;
    stopTime = 0;
    elapsedTime = 0;

    teamLabel = new JLabel(teamName, SwingConstants.CENTER);
    teamLabel.setFont(new Font("Arial", Font.BOLD, 24));

    JLabel titleLabel = new JLabel("StopWatch", SwingConstants.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

    timeLabel = new JLabel("00:00:00.000");
    timeLabel.setFont(new Font("Arial", Font.BOLD, 36));
    timeLabel.setHorizontalAlignment(SwingConstants.CENTER);

    startStopButton = new JButton("Start");
    startStopButton.setFocusable(false);
    startStopButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        startStop();
      }
    });

    JPanel contentPane = new JPanel(new GridLayout(4, 1, 10, 10)); // Use GridLayout
    contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    contentPane.add(teamLabel);
    contentPane.add(titleLabel);
    contentPane.add(timeLabel);
    contentPane.add(startStopButton);

    addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
          startStop();
        }
      }
    });

    setContentPane(contentPane);
    setResizable(false); // Set resizable to false
    pack();
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Change to DISPOSE_ON_CLOSE

    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosed(WindowEvent e) {
        kill = true; // Set kill to true when the window is closed
      }
    });

    // Initialize the close timer
    closeTimer = new Timer(3000, new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        closeTimer.stop(); // Stop the timer
        dispose(); // Close the StopWatch object
      }
    });
    closeTimer.setRepeats(false); // Set it to fire only once
    setVisible(true);
  }
  /*
   * Actual stopwatch method that calculates time with System.currenTimeMillis(). 
   */
  private void startStop() {
    if (!running) {
      running = true;
      startTime = System.currentTimeMillis();
      startStopButton.setText("Stop");
      startStopButton.setBackground(Color.RED);
      Timer timer = new Timer(1, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          if (running) {
            elapsedTime = System.currentTimeMillis() - startTime;
            updateTimeLabel(elapsedTime);
          }
        }
      });
      timer.start();
    } else {
      this.kill = true;
      running = false;
      stopTime = System.currentTimeMillis();
      startStopButton.setText("Start");
      startStopButton.setBackground(null);

      elapsedTime = stopTime - startTime;
      updateTimeLabel(elapsedTime);

      // Start the close timer
      closeTimer.restart();
    }
  }
  /*
   * Updates the JLabel object, timeLabel, based on the time elapsed. 
   */
  private void updateTimeLabel(long time) {
    long hours = time / 3600000;
    long minutes = (time % 3600000) / 60000;
    long seconds = (time % 60000) / 1000;
    long milliseconds = time % 1000;
    String formattedTime = String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, milliseconds);
    timeLabel.setText(formattedTime);
  }
  /*
   * Returns the elapsed time to the race method. 
   */
  public long getElapsedTime() {
    return elapsedTime;
  }

  public void closeStopWatch() {
    closeTimer.stop();
    dispose();
  }

  public static void main(String[] args) throws InterruptedException {
    //for testing purposes only
  }
}
