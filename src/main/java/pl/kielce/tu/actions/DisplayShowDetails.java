package pl.kielce.tu.actions;

import pl.kielce.tu.model.Show;
import pl.kielce.tu.model.enumeration.Role;
import pl.kielce.tu.repository.ShowRepository;
import pl.kielce.tu.util.ActionUtil;
import pl.kielce.tu.util.ViewUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author ciepluchs
 */
public class DisplayShowDetails implements Action {

    private static final String HEADER = "################################################# SHOW  #################################################";
    private static final List<Role> ALLOWED_ROLES = Arrays.asList(Role.EMPLOYEE, Role.CUSTOMER);
    private static final String DISPLAY_NAME = "Details";

    private List<Action> subActions = new ArrayList<>();
    private Show show;

    public DisplayShowDetails(ShowRepository repository, Show show) {
        this.show = show;
        subActions.add(new DeleteShow(repository, show));
        subActions.add(new MakeReservation(repository, show));
    }

    @Override
    public void execute() {
        System.out.println(HEADER);
        System.out.println(ViewUtil.getDetailedView(show));
        List<Action> availableActions = ActionUtil.getAvailableActions(subActions);
        while (true) {
            ViewUtil.displaySubMenu(availableActions);
            String selectedOption = ViewUtil.getSelectedOption();
            ViewUtil.cls();
            if (ActionUtil.isBackOptionSelected(selectedOption)) {
                return;
            } else {
                Action selectedAction = ActionUtil.getSelectedAction(availableActions, selectedOption);
                selectedAction.execute();
                return;
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
