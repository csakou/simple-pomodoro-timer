import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PomodoroJFrame extends JFrame{
    private static final long serialVersionUID = 1L;
    private JFrame mainFrame;
    private JLabel headerLabel;
    private JLabel statusLabel;
    private JPanel controlPanel;
    private final int pomodoroTime = 1500;
    private final int longBreakTime = 900;
    private final int shortBreakTime = 300;
    public static boolean timerAlreadyRunning = false;
    

    public PomodoroJFrame()
    {
        prepareGUI();
    }

    private void prepareGUI()
    {
        mainFrame = new JFrame("Pomodoro Test 1.0");
        mainFrame.setSize(600,400);
        mainFrame.setLayout(new GridLayout(3, 2));

        mainFrame.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent windowEvent)
            {
                System.exit(0);
            }
        });

        headerLabel = new JLabel("", JLabel.CENTER);
        statusLabel = new JLabel("No timer running.", JLabel.CENTER);
        statusLabel.setSize(350, 100);

        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        mainFrame.add(headerLabel);
        mainFrame.add(controlPanel);
        mainFrame.add(statusLabel);
        mainFrame.setVisible(true);

    }

    private void showButtonDemo()
    {
        headerLabel.setText("Simple Pomodoro Timer");

        JButton startButton = new JButton("Start");
        JButton shortBreakButton = new JButton("Short Break");
        JButton longBreakButton = new JButton("Long Break");
        longBreakButton.setHorizontalTextPosition(SwingConstants.LEFT);   

        startButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (timerAlreadyRunning == false)
                {
                    new PomodoroTimer(statusLabel, pomodoroTime);
                    timerAlreadyRunning = true;
                }
                else
                {
                    JOptionPane.showMessageDialog(mainFrame, "Timer is already running!");
                }
            }
        });

        shortBreakButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (timerAlreadyRunning == false)
                {
                    new PomodoroTimer(statusLabel, shortBreakTime);
                    timerAlreadyRunning = true;
                }
                else
                {
                    JOptionPane.showMessageDialog(mainFrame, "Timer is already running!");
                }
            }
        });

        longBreakButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (timerAlreadyRunning == false)
                {
                    new PomodoroTimer(statusLabel, longBreakTime);
                    timerAlreadyRunning = true;
                }
                else
                {
                    JOptionPane.showMessageDialog(mainFrame, "Timer is already running!");
                }
            }
        });

        controlPanel.add(startButton);
        controlPanel.add(shortBreakButton);
        controlPanel.add(longBreakButton);       

        mainFrame.setVisible(true);
    }

    public class PomodoroTimer implements ActionListener
    {
        private JLabel label;
        Timer pomodoroTimer;
        int remainingTime;

        public PomodoroTimer(JLabel passedLabel, int passRemainingTime)
        {
            pomodoroTimer = new Timer(1000, this);
            label = passedLabel;
            remainingTime = passRemainingTime;
            pomodoroTimer.start();
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (--this.remainingTime > 0)
            {
                String minutesAsString = String.format("%2s", String.valueOf(remainingTime / 60)).replace(" ", "0");
                String secondsAsString = String.format("%2s", String.valueOf(remainingTime % 60)).replace(" ", "0");
                label.setText(minutesAsString + ":" + secondsAsString);
            }
            else
            {
                label.setText("Time's Up!");
                pomodoroTimer.stop();
                timerAlreadyRunning = false;
            }
        }
    }

    public static void main(String args[])
    {
        PomodoroJFrame pomodoroJFrameDemo = new PomodoroJFrame();
        pomodoroJFrameDemo.showButtonDemo();
    }
}