package GUI.Frames;

/*
* Date: June 5, 2020
* Authors: Shahrukh Qureshi
* Description: This class creates the Options Frame (Second Page)
*
* Method List: 
* 1. void actionPerformed(final ActionEvent e) = Handles all events on JComponents
*/

// Import Statements 
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.net.URI;
import java.awt.event.ActionEvent;
import java.awt.Desktop;
import GUI.MenuFrames.SettingsFrame;
import GUI.Methods.GUIMethods;
import GetStats.CanadianStats;

public class OptionsFrame implements ActionListener {

    private JFrame frameOptions;
    private JButton btnStats, btnSelfAssessment;
    private JLabel lblTitle;
    private JMenuBar menuBar;
    private final JMenu menu;
    private final JMenuItem[] items;
    static String dailyCases, dailyDeaths, deaths, recovered, totalActive;

    // Void Constructor
    public OptionsFrame() {

        // JFrame
        frameOptions = new JFrame();
        frameOptions = GUIMethods.frameSetter(frameOptions, 300, 300);

        // JButtons
        btnStats = new JButton("View Canadian Stats");
        btnStats = GUIMethods.btnSetter(btnStats);
        btnSelfAssessment = new JButton("Self Assessment");
        btnSelfAssessment = GUIMethods.btnSetter(btnSelfAssessment);

        // JLabels
        lblTitle = new JLabel("<html><u>Please Select One of the Options");

        // JMenuBar/JMenu/JMenuItem
        menuBar = new JMenuBar();
        menu = new JMenu("File");
        items = new JMenuItem[2];
        items[0] = new JMenuItem("Settings");
        items[0].addActionListener(this);
        items[1] = new JMenuItem("Exit");
        items[1].addActionListener(this);
        menuBar = GUIMethods.barSetter(menuBar, menu, items);
        frameOptions.setJMenuBar(menuBar);

        // Strings (Data)
        dailyCases = "";
        dailyDeaths = "";
        deaths = "";
        recovered = "";
        totalActive = "";

        // Location set with (x, y, width, height)

        // JLabels
        lblTitle.setBounds(50, 20, 190, 30);
        frameOptions.add(lblTitle);

        // JButtons
        btnStats.setBounds(23, 60, 240, 50);
        btnStats.addActionListener(this);
        frameOptions.add(btnStats);

        btnSelfAssessment.setBounds(23, 120, 240, 50);
        btnSelfAssessment.addActionListener(this);
        frameOptions.add(btnSelfAssessment);

        frameOptions.setVisible(true);

    } // Constructor

    @Override
    public void actionPerformed(ActionEvent e) {

        // If the user goes to File -> Settings the following will occur
        if (e.getSource() == items[0]) {
            new SettingsFrame();
        }
        // If the user goes to File -> Exit the following will occur
        else if (e.getSource() == items[1]) {
            frameOptions.dispose();
            GUIMethods.showMsg("Thank you for using the Covid-19 Tracker");
        }
        // If the user clicks the "View Canadian Stats" button the following will occur
        else if (e.getSource() == btnStats) {
            frameOptions.dispose();
            CanadianStats.getStats(dailyCases, dailyDeaths, totalActive, recovered, deaths);
        }
        // If the user clicks the "Self Assessment" button the following will occur
        else {
            try 
            {
                final Desktop openDesktop = Desktop.getDesktop();
                openDesktop.browse(new URI("https://covid-19.ontario.ca/self-assessment/"));
            }
            catch (Exception error) 
            {
                GUIMethods.showMsg("An unexpected error has occured!\nProgram will now end");
                System.exit(0);
            }
        }

    } // actionPerformed Method

} // OptionsFrame Class