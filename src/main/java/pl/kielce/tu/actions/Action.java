package pl.kielce.tu.actions;


import pl.kielce.tu.model.enumeration.Role;

import java.util.List;

public interface Action {

    void execute();

    String getDisplayName();

    List<Role> getAllowedRoles();
}
