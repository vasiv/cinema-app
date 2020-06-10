package pl.kielce.tu.actions;

import pl.kielce.tu.model.Show;
import pl.kielce.tu.model.enumeration.Role;
import pl.kielce.tu.repository.ShowRepository;
import pl.kielce.tu.util.ViewUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * @author ciepluchs
 */
public class AddShow implements Action {

    private static final String HEADER = "################################################ NEW SHOW ###############################################";
    private static final List<Role> ALLOWED_ROLES = Arrays.asList(Role.EMPLOYEE);
    private static final String DISPLAY_NAME = "Add show";

    private ShowRepository repository;

    public AddShow(ShowRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute() {
        ViewUtil.cls();
        System.out.println(HEADER);
        Show showToBeAdded = getShowToBeAdded();
        repository.add(showToBeAdded);
        ViewUtil.cls();
        System.out.println("Show added!");
    }

    @Override
    public String getDisplayName() {
        return DISPLAY_NAME;
    }

    @Override
    public List<Role> getAllowedRoles() {
        return ALLOWED_ROLES;
    }

    private Show getShowToBeAdded() {
        Scanner input = new Scanner(System.in);
        System.out.println("Movie tittle: ");
        String movieTitle = input.nextLine();
        System.out.println("Date: ");
        String dateString = input.nextLine();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            System.err.println("Cannot parse Date due to: " + e);
        }
        System.out.println("Duration time: ");
        int duration = input.nextInt();
        System.out.println("Number of seats: ");
        int seats = input.nextInt();
        return new Show(movieTitle, date, duration, seats);
    }
}
