package GetStats;

/*
* Date: June 7, 2020
* Authors: Shahrukh Qureshi
* Description: This class contains all of the code for the API processing / ASYNC code
*
* Method List:
* 1. void getStats(String dailyCase, String dailyDeath, String total, String recovered, String deaths) = This method opens a new thread for an async task to begin
* 2. static String stats(final String data) = This method gets the data from the API
* 3. static String dailyCases(final String cases) = This method will return the number of daily cases
* 4. static String dailyDeaths(final String cases) = This method will return the number of daily deaths
* 5. static String total(final String total) = This method will return the number of total cases
* 6. static String recovered(final String recovered) = This method will return the number of recovered cases
* 7. static String deaths(final String deaths) = This method will return the number of deaths
* 8. void registerEvent(final EventListener eventListener) = This method sets the EventListener
* 9. void loading(String dailyCase, String dailyDeath, String total, String recovered, String deaths) = This method displays a loading screen while the data from the API processes
*
* Class List:
* public class = CanadianStats
* local class = PerformTask
*
* Interface List:
* EventListener
*/

// Import Statements
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import GUI.Frames.LoadingFrame;
import GUI.Frames.StatsFrame;
import GUI.Methods.GUIMethods;

public class CanadianStats {

    private static String api = "https://thevirustracker.com/free-api?countryTimeline=CA";
    private static StringBuilder sBuilder = new StringBuilder();
    private EventListener eventListener;

    // This method opens a new thread for an async task to begin
    public static void getStats(String dailyCase, String dailyDeath, String total, String recovered, String deaths) {

        final CanadianStats sCanadianStats = new CanadianStats();
        final EventListener eventListener = new PerformTask();

        sCanadianStats.registerEvent(eventListener);
        sCanadianStats.loading(dailyCase, dailyDeath, total, recovered, deaths);

    } // getStats Method

    // This method gets the data from the API
    public static String stats(final String data) {

        try {
            String information;

            final URL url = new URL(api);
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "chrome");

            final BufferedReader inputData = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            while ((information = inputData.readLine()) != null) {
                sBuilder.append(information);
            }

            inputData.close();
            return sBuilder.toString();

        } catch (final Exception error) {
            GUIMethods.showMsg("An unexpected error has occured\nProgram will now close");
            System.exit(0);
        }
        return null;

    } // stats Method

    // This method will return the number of daily cases
    static String dailyCases(final String cases) {

        String information = "";
        String[] split;
        String[] data;

        information = stats(information);

        split = information.split("\"new_daily_cases\"");

        data = split[split.length - 1].split(",");

        return data[0].replaceAll(":", ""); // Index of daily cases

    } // dailyCases Method

    // This method will return the number of daily deaths
    static String dailyDeaths(final String cases) {

        String information = "";
        String[] split;
        String[] data;

        information = stats(information);

        split = information.split("\"new_daily_cases\"");

        data = split[split.length - 1].split(",");

        return data[1].replaceAll("\"new_daily_deaths\":", ""); // Index of daily cases

    } // dailyDeaths Method

    // This method will return the number of total cases
    static String total(final String total) {

        String information = "";
        String[] split;
        String[] data;

        information = stats(information);

        split = information.split("\"new_daily_cases\"");

        data = split[split.length - 1].split(",");

        return data[2].replaceAll("\"total_cases\":", ""); // Index of total

    } // total Method

    // This method will return the number of recovered cases
    static String recovered(final String recovered) {

        String information = "";
        String[] split;
        String[] data;

        information = stats(information);

        split = information.split("\"new_daily_cases\"");

        data = split[split.length - 1].split(",");

        return data[3].replaceAll("\"total_recoveries\":", ""); // Index of recovered

    } // recovered Method

    // This method will return the number of deaths
    static String deaths(final String deaths) {

        String information = "";
        String[] split;
        String[] data;

        information = stats(information);

        split = information.split("\"new_daily_cases\"");

        data = split[split.length - 1].split(",");

        return data[4].replaceAll("\"total_deaths\":", ""); // Index of deaths

    } // deaths Method

    // This method sets the EventListener
    public void registerEvent(final EventListener eventListener) {

        this.eventListener = eventListener;

    } // registerEvent Method

    // This method displays a loading screen while the data from the API processes
    public void loading(String dailyCase, String dailyDeath, String total, String recovered, String deaths) {
        // Creating a new thread
        new Thread(new Runnable() {

            @Override
            public void run() {
                new LoadingFrame();
                if (eventListener != null) {
                    eventListener.onEvent(dailyCase, dailyDeath, total, recovered, deaths);
                }
            } // run Method
        }).start(); // Starting the thread
    }

} // CanadianStats Class

// This interface allows for classes to impliment methods required for an async
// task
interface EventListener {

    void onEvent(String dailyCase, String dailyDeath, String total, String recovered, String deaths);

} // EventListener Interface

// This class performs a task in the background (Async)
class PerformTask implements EventListener {

    @Override
    public void onEvent(String dailyCase, String dailyDeath, String total, String recovered, String deaths) {

         // Getting the data and adding it to the rightful variable
        dailyCase = CanadianStats.dailyCases(dailyCase);
        dailyDeath = CanadianStats.dailyDeaths(dailyDeath);
        deaths = CanadianStats.deaths(deaths);
        recovered = CanadianStats.recovered(recovered);
        total = CanadianStats.total(total);
        LoadingFrame.frameLoading.dispose(); // Disposing the loading screen

        new StatsFrame(dailyCase, dailyDeath, deaths, recovered, total); // Passing the data to the stats frame and calling it to run

    } // onEvent Method

} // PerformTask Class