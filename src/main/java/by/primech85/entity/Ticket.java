package by.primech85.entity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

public class Ticket {
    private Long id;
    private BigDecimal cost;
    private String passenger_name;
    private String passenger_passport;
    private String seat_no;
    private Trip trip;

    public Ticket() {
    }

    public Ticket(Long id, BigDecimal cost, String passenger_name, String passenger_passport, String seat_no, Trip trip) {
        this.id = id;
        this.cost = cost;
        this.passenger_name = passenger_name;
        this.passenger_passport = passenger_passport;
        this.seat_no = seat_no;
        this.trip = trip;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", cost=" + cost +
                ", passenger_name='" + passenger_name + '\'' +
                ", passenger_passport='" + passenger_passport + '\'' +
                ", seat_no='" + seat_no + '\'' +
                ", trip=" + trip +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id) && Objects.equals(cost, ticket.cost) && Objects.equals(passenger_name, ticket.passenger_name) && Objects.equals(passenger_passport, ticket.passenger_passport) && Objects.equals(seat_no, ticket.seat_no) && Objects.equals(trip, ticket.trip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cost, passenger_name, passenger_passport, seat_no, trip);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getPassenger_name() {
        return passenger_name;
    }

    public void setPassenger_name(String passenger_name) {
        this.passenger_name = passenger_name;
    }

    public String getPassenger_passport() {
        return passenger_passport;
    }

    public void setPassenger_passport(String passenger_passport) {
        this.passenger_passport = passenger_passport;
    }

    public String getSeat_no() {
        return seat_no;
    }

    public void setSeat_no(String seat_no) {
        this.seat_no = seat_no;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }
}
