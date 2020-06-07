package GUI.Frames;

/*
* Date: June 7, 2020
* Authors: Shahrukh Qureshi
* Description: This class creates the Stats Frame (Third Page)
*
* Method List: 
* 1. void actionPerformed(final ActionEvent e) = Handles all events on JComponents
*/

// Import Statements 

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import GUI.MenuFrames.SettingsFrame;
import GUI.Methods.GUIMethods;
import GetStats.CanadianStats;

public class StatsFrame implements ActionListener {

    private JFrame frameStats;
    private JLabel lblCountry, lblDailyCases, lblDailyDeaths, lblDeaths, lblRecovered, lblTotalActive;
    private JMenuBar menuBar;
    private final JMenu menu;
    private final JMenuItem[] items;

    // Constructor
    public StatsFrame(String dailyCases, String dailyDeaths, String deaths, String recovered, String total) {

        // JFrame
        frameStats = new JFrame();
        frameStats = GUIMethods.frameSetter(frameStats, 300, 210);

        // JLabels
        lblCountry = new JLabel("<html><u>Canada's Covid-19 Statistics");
        lblDailyCases = new JLabel("Daily Cases: " + dailyCases);
        lblDailyDeaths = new JLabel("Daily Deaths: " + dailyDeaths);
        lblDeaths = new JLabel("Deaths: " + deaths.replaceAll("}", ""));
        lblRecovered = new JLabel("Recoveries: " + recovered);
        lblTotalActive = new JLabel("Active Cases: " + total);

        // JMenuBar/JMenu/JMenuItem
        menuBar = new JMenuBar();
        menu = new JMenu("File");
        items = new JMenuItem[4];
        items[0] = new JMenuItem("Settings");
        items[0].addActionListener(this);
        items[1] = new JMenuItem("Back");
        items[1].addActionListener(this);
        items[2] = new JMenuItem("Refresh");
        items[2].addActionListener(this);
        items[3] = new JMenuItem("Exit");
        items[3].addActionListener(this);
        menuBar = GUIMethods.barSetter(menuBar, menu, items);
        frameStats.setJMenuBar(menuBar);

        // Location set with (x, y, width, height)

        // JLabels
        lblCountry.setBounds(50, 10, 300, 30);
        frameStats.add(lblCountry);

        lblDailyCases.setBounds(50, 40, 300, 30);
        frameStats.add(lblDailyCases);

        lblDailyDeaths.setBounds(50, 60, 300, 30);
        frameStats.add(lblDailyDeaths);

        lblDeaths.setBounds(50, 80, 300, 30);
        frameStats.add(lblDeaths);

        lblRecovered.setBounds(50, 100, 300, 30);
        frameStats.add(lblRecovered);

        lblTotalActive.setBounds(50, 120, 300, 30);
        frameStats.add(lblTotalActive);

        frameStats.setVisible(true);

    } // Constructor

    @Override
    public void actionPerformed(ActionEvent e) {

        // If the user goes to File -> Settings the following will occur
        if (e.getSource() == items[0]) {
            new SettingsFrame();
        }
        // If the user goes to File -> Back the folling will occur
        else if (e.getSource() == items[1]) {
            frameStats.dispose();
            new OptionsFrame();
        }
        // If the user goes to File -> Refresh the following will occur
        else if (e.getSource() == items[2]) {
            frameStats.dispose();
            CanadianStats.getStats(OptionsFrame.dailyCases, OptionsFrame.dailyDeaths, OptionsFrame.totalActive, OptionsFrame.recovered, OptionsFrame.deaths);
        }
        // If the user goes to File -> Exit the following will occur
        else {
            GUIMethods.showMsg("Thank you for using Covid-19 Tracker!\nStay Safe!");
            System.exit(0);
        }

    } // actionPerformed Method

} // class StatsFrame