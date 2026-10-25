package by.primech85.service;

import by.primech85.Dao.TripDao;
import by.primech85.Dto.TripDto;

import java.util.List;
import java.util.stream.Collectors;

public class TripService {
    private static final TripService INSTANCE = new TripService();
    private final TripDao tripDao = TripDao.getInstance();
    private  TripService(){

    }
    public static TripService getInstance(){
        return INSTANCE;
    }
    public List<TripDto> tripALL(){
        return tripDao.findAll().stream().map(
                trip -> new TripDto(
                        trip.getId(),
                        "%s - %s - %s".formatted(
                                trip.getArrival_code(),
                                trip.getDeparture_code(),
                                trip.getStatus()
                        ))).collect(Collectors.toList());
    }
}
