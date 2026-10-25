package by.primech85.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Trip {
    private Long id;
    private Station arrival_code;
    private LocalDateTime arrival_date;
    private String departure_code;
    private LocalDateTime departure_date;
    private Status_type status;
    private Long train_id;
    private String trip_no;

    public Trip() {
    }

    public Trip(Long id, Station arrival_code,
                LocalDateTime arrival_date, String departure_code,
                LocalDateTime departure_date, Status_type status,
                Long train_id, String trip_no) {
        this.id = id;
        this.arrival_code = arrival_code;
        this.arrival_date = arrival_date;
        this.departure_code = departure_code;
        this.departure_date = departure_date;
        this.status = status;
        this.train_id = train_id;
        this.trip_no = trip_no;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "id=" + id +
                ", arrival_code='" + arrival_code + '\'' +
                ", arrival_date=" + arrival_date +
                ", departure_code='" + departure_code + '\'' +
                ", departure_date=" + departure_date +
                ", status=" + status +
                ", train_id=" + train_id +
                ", trip_no='" + trip_no + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Trip trip = (Trip) o;
        return Objects.equals(id, trip.id) && Objects.equals(arrival_code, trip.arrival_code) && Objects.equals(arrival_date, trip.arrival_date) && Objects.equals(departure_code, trip.departure_code) && Objects.equals(departure_date, trip.departure_date) && status == trip.status && Objects.equals(train_id, trip.train_id) && Objects.equals(trip_no, trip.trip_no);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, arrival_code, arrival_date, departure_code, departure_date, status, train_id, trip_no);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Station getArrival_code() {
        return arrival_code;
    }

    public void setArrival_code(Station arrival_code) {
        this.arrival_code = arrival_code;
    }

    public LocalDateTime getArrival_date() {
        return arrival_date;
    }

    public void setArrival_date(LocalDateTime arrival_date) {
        this.arrival_date = arrival_date;
    }

    public String getDeparture_code() {
        return departure_code;
    }

    public void setDeparture_code(String departure_code) {
        this.departure_code = departure_code;
    }

    public LocalDateTime getDeparture_date() {
        return departure_date;
    }

    public void setDeparture_date(LocalDateTime departure_date) {
        this.departure_date = departure_date;
    }

    public Status_type getStatus() {
        return status;
    }

    public void setStatus(Status_type status) {
        this.status = status;
    }

    public Long getTrain_id() {
        return train_id;
    }

    public void setTrain_id(Long train_id) {
        this.train_id = train_id;
    }

    public String getTrip_no() {
        return trip_no;
    }

    public void setTrip_no(String trip_no) {
        this.trip_no = trip_no;
    }
}
