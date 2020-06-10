package pl.kielce.tu.repository;

import pl.kielce.tu.model.Show;

import java.util.List;

public interface ShowRepository {

    void add(Show show);

    List<Show> getAll();

    void delete(Show show);

    Show update(Show show, int numberOfBookedSeats);
}
