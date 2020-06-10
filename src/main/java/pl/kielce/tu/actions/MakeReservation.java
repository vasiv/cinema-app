package pl.kielce.tu.actions;

import pl.kielce.tu.model.Show;
import pl.kielce.tu.model.enumeration.Role;
import pl.kielce.tu.repository.ShowRepository;
import pl.kielce.tu.util.ViewUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * @author ciepluchs
 */
public class MakeReservation implements Action {

    private static final String HEADER = "############################################## RESERVATION  #############################################";
    private static final List<Role> ALLOWED_ROLES = Arrays.asList(Role.CUSTOMER);
    private static final String DISPLAY_NAME = "Make a reservation";

    private ShowRepository repository;
    private Show show;

    public MakeReservation(ShowRepository repository, Show show) {
        this.repository = repository;
        this.show = show;
    }

    @Override
    public void execute() {
        System.out.println(HEADER);
        System.out.println(ViewUtil.getDetailedView(show));
        System.out.println("-".repeat(105) + "\n");
        int bookedSeats = getModifiedTravel();
        repository.update(show, bookedSeats);
        ViewUtil.cls();
        System.out.println("Reservation made!");
    }

    @Override
    public String getDisplayName() {
        return DISPLAY_NAME;
    }

    @Override
    public List<Role> getAllowedRoles() {
        return ALLOWED_ROLES;
    }

    private int getModifiedTravel() {
        Scanner input = new Scanner(System.in);
        System.out.println("How many tickets you want to book: ");
        return input.nextInt();
    }
}
