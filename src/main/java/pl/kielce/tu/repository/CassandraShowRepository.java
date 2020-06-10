package pl.kielce.tu.repository;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import pl.kielce.tu.model.Show;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ciepluchs
 */
public class CassandraShowRepository implements ShowRepository {

    private static final String TABLE_NAME = "shows";
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private Session session;

    public CassandraShowRepository(Session session) {
        this.session = session;
        createTable();
    }

    public void createTable() {
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
                .append(TABLE_NAME)
                .append("(")
                .append("id uuid PRIMARY KEY, ")
                .append("movie text,")
                .append("date text,")
                .append("durationTimeInMinutes int,")
                .append("availableSeats int);");
        final String query = sb.toString();
        session.execute(query);
    }

    public void add(Show show) {
        String date = format.format(show.getDate());
        StringBuilder sb = new StringBuilder("INSERT INTO ")
                .append(TABLE_NAME)
                .append("(id, movie, date, durationTimeInMinutes, availableSeats) ")
                .append("VALUES (")
                .append(show.getId())
                .append(", '")
                .append(show.getMovie())
                .append("', '")
                .append(date)
                .append("', ")
                .append(show.getDurationTimeInMinutes())
                .append(", ")
                .append(show.getAvailableSeats())
                .append(");");
        String query = sb.toString();
        session.execute(query);
    }

    @Override
    public List<Show> getAll() {
        StringBuilder sb = new StringBuilder("SELECT * FROM ")
                .append(TABLE_NAME);
        String query = sb.toString();
        ResultSet rs = session.execute(query);
        List<Show> shows = new ArrayList<>();
        for (Row r : rs) {
            Show show = parseToShow(r);
            shows.add(show);
        }
        return shows;
    }

    @Override
    public void delete(Show show) {
        StringBuilder sb = new StringBuilder("DELETE FROM ")
                .append(TABLE_NAME)
                .append(" WHERE id = ")
                .append(show.getId())
                .append(";");
        String query = sb.toString();
        session.execute(query);
    }

    @Override
    public Show update(Show show, int numberOfBookedSeats) {
        int updatedNumberOfSeats = show.getAvailableSeats() - numberOfBookedSeats;
        show.setAvailableSeats(updatedNumberOfSeats);
        StringBuilder sb =
                new StringBuilder("UPDATE shows SET availableseats = " + updatedNumberOfSeats + " WHERE id = " + show.getId());
        String query = sb.toString();
        session.execute(query);
        return show;
    }

    private Show parseToShow(Row r) {
        String dateString = r.getString("date");
        Date date = null;
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            System.err.println("Cannot parse Date due to: " + e);
        }
        return new Show(r.getUUID("id"), r.getString("movie"), date,
               r.getInt("durationtimeinminutes"), r.getInt("availableseats"));
    }
}
