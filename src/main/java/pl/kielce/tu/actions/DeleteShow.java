package pl.kielce.tu.actions;

import pl.kielce.tu.model.Show;
import pl.kielce.tu.model.enumeration.Role;
import pl.kielce.tu.repository.ShowRepository;

import java.util.Arrays;
import java.util.List;

/**
 * @author ciepluchs
 */
public class DeleteShow implements Action {

    private static final List<Role> ALLOWED_ROLES = Arrays.asList(Role.EMPLOYEE);
    private static final String DISPLAY_NAME = "Delete";

    private ShowRepository repository;
    private Show travelOffer;

    public DeleteShow(ShowRepository repository, Show show) {
        this.repository = repository;
        this.travelOffer = show;
    }

    @Override
    public void execute() {
        repository.delete(travelOffer);
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
