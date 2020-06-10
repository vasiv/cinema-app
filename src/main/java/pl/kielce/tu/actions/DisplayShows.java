package pl.kielce.tu.actions;

import pl.kielce.tu.model.Show;
import pl.kielce.tu.model.enumeration.Role;
import pl.kielce.tu.repository.ShowRepository;
import pl.kielce.tu.util.ViewUtil;

import java.util.Arrays;
import java.util.List;

/**
 * @author ciepluchs
 */
public class DisplayShows implements Action {

    private static final String HEADER = "################################################## SHOWS ################################################";
    private static final List<Role> ALLOWED_ROLES = Arrays.asList(Role.EMPLOYEE, Role.CUSTOMER);
    private static final String DISPLAY_NAME = "Shows";

    private ShowRepository repository;

    public DisplayShows(ShowRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute() {
        while (true) {
            List<Show> allTravelOffers = repository.getAll();
            String tableWithBooks = ViewUtil.getTable(allTravelOffers);
            ViewUtil.cls();
            System.out.println(HEADER);
            System.out.println(tableWithBooks);
            System.out.println("Type number of offer to see details:");
            String selectedOption = ViewUtil.getSelectedOption();
            ViewUtil.cls();
            if (ViewUtil.isBackOptionSelected(selectedOption)) {
                return;
            } else {
                new DisplayShowDetails(repository, allTravelOffers.get(Integer.parseInt(selectedOption) - 1)).execute();
            }
        }
    }

    @Override
    public String getDisplayName() {
        return DISPLAY_NAME;
    }

    @Override
    public List<Role> getAllowedRoles() {
        return ALLOWED_ROLES;
    }

}
