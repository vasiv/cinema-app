package pl.kielce.tu.model;

import com.datastax.driver.core.utils.UUIDs;

import java.util.Date;
import java.util.UUID;

/**
 * @author ciepluchs
 */
public class Show {

    private UUID id;
    private String movie;
    private Date date;
    private int durationTimeInMinutes;
    private int availableSeats;

    public Show(String movie, Date date, int durationTimeInMinutes, int availableSeats) {
        id = UUIDs.timeBased();
        this.movie = movie;
        this.date = date;
        this.durationTimeInMinutes = durationTimeInMinutes;
        this.availableSeats = availableSeats;
    }

    public Show(UUID id, String movie, Date date, int durationTimeInMinutes, int availableSeats) {
        this.id = id;
        this.movie = movie;
        this.date = date;
        this.durationTimeInMinutes = durationTimeInMinutes;
        this.availableSeats = availableSeats;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getDurationTimeInMinutes() {
        return durationTimeInMinutes;
    }

    public void setDurationTimeInMinutes(int durationTimeInMinutes) {
        this.durationTimeInMinutes = durationTimeInMinutes;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }
}
