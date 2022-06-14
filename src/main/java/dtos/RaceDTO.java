package dtos;

import entities.Car;
import entities.Race;

import javax.persistence.Column;
import javax.persistence.ManyToMany;
import java.util.*;

public class RaceDTO {
    private int id;
    private String name;
    private String location;
    private String startDate;
    private int duration;
    private Set<CarDTO> cars = new HashSet<>();

    public RaceDTO(Race race) {
        this.id=race.getId();
        this.name=race.getName();
        this.location=race.getLocation();
        this.startDate=race.getStartDate();
        this.duration=race.getDuration();

        for (Car car : race.getCars()) {
            this.cars.add(new CarDTO(car));
        }

    }

    public static List<RaceDTO> getDtos(List<Race> raceList){
        List<RaceDTO> raceDTOS = new ArrayList();
        raceList.forEach(race->raceDTOS.add(new RaceDTO(race)));
        return raceDTOS;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Set<CarDTO> getCars() {
        return cars;
    }

    public void setCars(Set<CarDTO> cars) {
        this.cars = cars;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RaceDTO raceDTO = (RaceDTO) o;
        return id == raceDTO.id && duration == raceDTO.duration && Objects.equals(name, raceDTO.name) && Objects.equals(location, raceDTO.location) && Objects.equals(startDate, raceDTO.startDate) && Objects.equals(cars, raceDTO.cars);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, location, startDate, duration, cars);
    }
}
