package pl.kielce.tu.util;

import pl.kielce.tu.actions.*;
import pl.kielce.tu.model.Show;
import pl.kielce.tu.model.User;
import pl.kielce.tu.repository.ShowRepository;
import pl.kielce.tu.session.LoginService;
import pl.kielce.tu.session.Session;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author ciepluchs
 */
public abstract class ViewUtil {

    private static final String NEXT_LINE = "\n";
    private static final String SPACE_DELIMITER = " ";
    private static final int COLUMN_WIDTH = 32;
    private static final int NUMBER_OF_COLUMNS = 3;
    private static final String BACK_SYMBOL = "<-";
    private static final String BACK = "back";
    private static final String QUIT = "quit";
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private ViewUtil() {
    }

    public static void cls() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception E) {
            System.out.println(E);
        }
    }

    public static void displayLoginPrompt(LoginService loginService) {
        java.io.Console console = System.console();
        String username = console.readLine("Personal ID: ");
        String password = new String(console.readPassword("Password: "));
        User user = loginService.login(username, password);
        if (user != null) {
            Session.setUser(user);
        }
    }

    public static void displayMainMenu(ShowRepository showRepository) {
        List<Action> mainMenuActions = new ArrayList<>();
        mainMenuActions.add(new AddShow(showRepository));
        mainMenuActions.add(new DisplayShows(showRepository));
        ViewUtil.cls();
        List<Action> availableActions = ActionUtil.getAvailableActions(mainMenuActions);
        while (true) {
            System.out.println("################################################ CINEMA  ################################################");
            int i = 0;
            for (Action action : availableActions) {
                System.out.println(++i + ") " + action.getDisplayName());
            }
            System.out.println("Where you want to go: ");
            Scanner scanner = new Scanner(System.in);
            String operation = scanner.nextLine();
            if (QUIT.equals(operation)) {
                return;
            }
            int nextOperation = Integer.parseInt(operation) - 1;
            availableActions.get(nextOperation).execute();
        }
    }

    public static void displaySubMenu(List<Action> availableActions) {
        System.out.println("What you want to do: ");
        int i = 0;
        for (Action action : availableActions) {
            System.out.println(++i + ") " + action.getDisplayName());
        }
        System.out.println("<- back");
    }

    public static boolean isBackOptionSelected(String selectedOption) {
        return BACK_SYMBOL.equals(selectedOption) || BACK.equals(selectedOption);
    }

    public static String getSelectedOption() {
        Scanner scanner = new Scanner(System.in);
        return scanner.next();
    }

    public static String getTable(List<Show> shows) {
        String tableHeader = getTableHeader();
        String tableContent = getTableContent(shows);
        return tableHeader + "\n" + tableContent;
    }

    private static String getTableContent(List<Show> shows) {
        String tableContent = "";
        for (Show show : shows) {
            StringBuilder sb = new StringBuilder();
            String movie = show.getMovie();
            String date = format.format(show.getDate());
            String duration = String.valueOf(show.getDurationTimeInMinutes());
            sb.append(shows.indexOf(show) + 1);
            sb.append(SPACE_DELIMITER.repeat(9 - sb.length()));
            sb.append(movie).append(SPACE_DELIMITER.repeat((COLUMN_WIDTH + 9) - sb.length()));
            sb.append(date);
            sb.append(SPACE_DELIMITER.repeat((2 * COLUMN_WIDTH + 9) - sb.length()));
            sb.append(duration);
            sb.append(NEXT_LINE);
            tableContent += sb.toString();
        }
        return tableContent;
    }

    private static String getTableHeader() {
        StringBuilder sb = new StringBuilder();
        sb.append("--------|");
        sb.append("-------------------------------|".repeat(NUMBER_OF_COLUMNS) + "\n");
        sb.append("   No.  |");
        sb.append("             MOVIE             |");
        sb.append("             DATE              |");
        sb.append("       DURATION (minutes)      |\n");
        sb.append("--------|");
        sb.append("-------------------------------|".repeat(NUMBER_OF_COLUMNS) + "\n");
        return sb.toString();
    }

    public static String getDetailedView(Show show) {
        StringBuilder sb = new StringBuilder();
        sb.append("Movie              : ").append(show.getMovie()).append(NEXT_LINE);
        sb.append("Date               : ").append(format.format(show.getDate())).append(NEXT_LINE);
        sb.append("Duration (minutes) : ").append(show.getDurationTimeInMinutes()).append(NEXT_LINE);
        sb.append("Available seats    : ").append(show.getAvailableSeats()).append(NEXT_LINE);
        return sb.toString();
    }
}
