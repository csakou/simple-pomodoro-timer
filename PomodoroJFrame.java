/**
 * This is a simple Pomodoro application to help with productivity and focus in studying or
 * whatever task somebody would want to focus on. There are other applications out there,
 * most probably way better than this one in the sense of features and code quality,
 * but this is the first try of a computer science undergraduate to create a solid,
 * working application, as bug free as possible.
 * @author Sakoumpentas Christos
 * @version 12/12/2019
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/**
 * Class that extends JFrame class and creates a Window Frame that runs
 * a simple pomodoro application. You can set at the beginning how many sessions you'd like
 * to do, pause, restart or reset the current running session. Each session consists
 * of 6 rounds split in 3 25 minutes of work, two 5 minute short breaks in between and 
 * 1 long break at the end if there is another session coming up.
 */

public class PomodoroJFrame extends JFrame{
    // The items in our frame
    private static final long serialVersionUID = 1L;
    private JFrame mainFrame;
    private JLabel headerLabel;
    private JLabel subHeaderLabel;
    private JLabel sessionLabel;
    private JLabel statusLabel;
    private JButton sessionButton;
    private JButton minusButton;
    private JButton plusButton;
    private JButton pauseButton;
    private JButton restartButton;
    private JButton resetButton;
    private JPanel timerButtonPanel;
    private JPanel sessionPanel;
    private JPanel controlButtonPanel;

    // Constantsand variables for the flow control of the program and timers.
    private static int numberOfSessions = 0;
    private final int pomodoroTime = 1500;
    private final int longBreakTime = 900;
    private final int shortBreakTime = 300;
    private static boolean sessionAlreadyRunning = false;
    private int rounds = 6;
    
    // Timer and definition of it's ActionListener method and a value to be used in the timer.
    private final Timer pomodoroTimer = new Timer(1000, new ActionListener()
                                                  {
                                                      @Override
                                                      public void actionPerformed(ActionEvent event)
                                                      {
                                                          if (--remainingTime >= 0)
                                                          {
                                                              timerShow(remainingTime);
                                                          }
                                                          else
                                                          {
                                                              pomodoroTimer.stop();
                                                              statusLabel.setText("Time's Up!");
                                                              rounds--;
                                                              threadSleep();
                                                              PomodoroTimer();
                                                          }
                                                      }
                                                  });
    private static int remainingTime;

    // Constructor creating the GUI as defined in the function below.
    public PomodoroJFrame()
    {
        prepareGUI();
    }

    // Function that prepares the whole gui layout.
    private void prepareGUI()
    {
        // Creating the frame (window) that will be our app.
        mainFrame = new JFrame("Pomodoro");
        mainFrame.setSize(600,400);
        /* We are setting the layour as a grid to avoid using absolute values.*/
        mainFrame.setLayout(new GridLayout(6, 1));
        
        mainFrame.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent windowEvent)
            {
                System.exit(0);
            }
        });

        // setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Defining the labels and panels of the frame
        headerLabel = new JLabel("Each session consists of 6 rounds:", JLabel.CENTER);
        subHeaderLabel = new JLabel("3 rounds of 25 minutes, 2 of 5 minutes and 1 of 15 minutes.", JLabel.CENTER);
        sessionLabel = new JLabel(String.valueOf(numberOfSessions), JLabel.CENTER);
        statusLabel = new JLabel("00:00", JLabel.CENTER);
        // Setting the size, fonts and font size of the timer.
        statusLabel.setSize(350,100);
        statusLabel.setFont(new Font("Serif", Font.BOLD, 28));
        
        // Creating the panels that will contain the buttons.
        timerButtonPanel = new JPanel();
        sessionPanel = new JPanel();
        controlButtonPanel = new JPanel();

        // Create the layout of each panel.
        timerButtonPanel.setLayout(new FlowLayout());
        sessionPanel.setLayout(new FlowLayout());
        controlButtonPanel.setLayout(new FlowLayout());


        // Creating the buttons and setting their parameters
        sessionButton = new JButton("Start");
        minusButton = new JButton("-");
        plusButton = new JButton("+");
        pauseButton = new JButton("Pause");
        restartButton = new JButton("Restart");
        resetButton = new JButton("Reset");

        // Set the size of each JButton.
        sessionButton.setPreferredSize(new Dimension(125, 25));
        minusButton.setPreferredSize(new Dimension(45, 45));
        plusButton.setPreferredSize(new Dimension(45, 45));
        pauseButton.setPreferredSize(new Dimension(125, 25));
        restartButton.setPreferredSize(new Dimension(125, 25));
        resetButton.setPreferredSize(new Dimension(125, 25));

        // Calling the actions every time a button is clicked.
        sessionButton.addActionListener(startButtonActions);
        minusButton.addActionListener(sessionButtonsActions);
        plusButton.addActionListener(sessionButtonsActions);
        pauseButton.addActionListener(controlButtonActions);
        restartButton.addActionListener(controlButtonActions);
        resetButton.addActionListener(controlButtonActions);
        
        // Adding the labels and panels into the actual Window that will appear.
        mainFrame.add(headerLabel);
        mainFrame.add(subHeaderLabel);
        mainFrame.add(timerButtonPanel);
        mainFrame.add(sessionPanel);
        mainFrame.add(controlButtonPanel);
        mainFrame.add(restartButton);
        mainFrame.add(statusLabel);

        // Adding the buttons and labels into the panels of the window.
        timerButtonPanel.add(sessionButton);
        sessionPanel.add(minusButton);
        sessionPanel.add(sessionLabel);
        sessionPanel.add(plusButton);
        controlButtonPanel.add(pauseButton);
        controlButtonPanel.add(restartButton);
        controlButtonPanel.add(resetButton);

        //Get the required spaces for all to fit.
        mainFrame.pack();
    }

    // ActionListener function for the start button. If no running session exists already then start a new one.
    // If a running session exists then show a pop up informing the user about it.
    private ActionListener startButtonActions = new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent event)
        {
            if (sessionAlreadyRunning == false)
            {
                PomodoroTimer();
            }
            else
            {
                JOptionPane.showMessageDialog(mainFrame, "Timer is already running!");
            }
        }
    };

    // ActionListener function for the session buttons. They increment and decrement
    // the number of sessions that the user wants to have. Each session consists of 6 rounds.
    // (5 if the user has selected only 1 session so there is no need for a long break)
    private ActionListener sessionButtonsActions = new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent event)
        {
            if (sessionAlreadyRunning == false)
            {
                if (event.getSource() == minusButton)
                {
                    if (numberOfSessions != 0)
                    {
                        numberOfSessions--;
                        sessionLabel.setText(String.valueOf(numberOfSessions));
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(mainFrame, "Sessions already at 0");
                    }
                }
                else if (event.getSource() == plusButton)
                {
                    numberOfSessions++;
                    sessionLabel.setText(String.valueOf(numberOfSessions));
                }
            }
            else
            {
                JOptionPane.showMessageDialog(mainFrame, "Timer is already running!");
            }
        }
    };

    // ActionLIstener for the control Buttons of the application
    //meaning pause, restart and reset.
    private ActionListener controlButtonActions = new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent event)
        {
            if (event.getSource() == pauseButton && pomodoroTimer.isRunning() == true)
            {
                pomodoroTimer.stop();
            }
            if(event.getSource() == restartButton && pomodoroTimer.isRunning() == false)
            {
                pomodoroTimer.restart();
            }
            if(event.getSource() == resetButton && sessionAlreadyRunning == true){
                pomodoroTimer.stop();
                resetApp();
            }
        }
    };

    /* The main method for the running timer. Essentially depending on which round
    you are at the timer changes the amount of time it will start a countdown upon,
    and if it's the last round and there is no session coming up it will skip the
    long break*/
    private void PomodoroTimer()
    {
        sessionAlreadyRunning = true;
        if (rounds % 2 == 0 && rounds != 0)
        {
            remainingTime = pomodoroTime;
        }
        else if(rounds % 2 == 1 && rounds != 0)
        {
            remainingTime = shortBreakTime;
        }
        else if (rounds == 0)
        {
            remainingTime = longBreakTime;
        }

        if(rounds != 0 && numberOfSessions >= 1)
        {
            pomodoroTimer.start();
        }
        else if (rounds == 0 && numberOfSessions == 1)
        {
            JOptionPane.showMessageDialog(mainFrame, "You are done with your session(s). Good job! Keep working hard!");
            resetApp();
        }
    }

    // Simmple method to reset state of the program.
    private void resetApp()
    {
        remainingTime = 0;
        numberOfSessions = 0;
        rounds = 6;
        sessionAlreadyRunning = false;
        sessionLabel.setText(String.valueOf(numberOfSessions));
        timerShow(remainingTime);
    }

    // Function to show the time in the format of "mm:ss" in the JFrame.
    public void timerShow(int remainingTime)
    {
        // Show the timer in mm:ss format and display it on label.
        String minutesAsString = String.format("%2s", String.valueOf(remainingTime / 60)).replace(" ", "0");
        String secondsAsString = String.format("%2s", String.valueOf(remainingTime % 60)).replace(" ", "0");
        statusLabel.setText(minutesAsString + ":" + secondsAsString);
    }

    // Function to pause the program for 5 seconds.
    public void threadSleep()
    {
        try
        {
            Thread.sleep(5000);
        }
        catch (InterruptedException exception)
        {
            System.out.println(exception);
        }
    }

    public static void main(String args[])
    {
        // Create a new JFrame object and make it visible.
        new PomodoroJFrame().mainFrame.setVisible(true);
    }
}
