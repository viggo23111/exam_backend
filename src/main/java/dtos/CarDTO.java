package dtos;

import entities.Car;
import entities.Driver;
import entities.Race;

import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.*;

public class CarDTO {
    private int id;
    private String name;
    private String brand;
    private String make;
    private int year;
    private String sponsor;
    private String color;
    private Set<RaceDTO> races = new HashSet<>();
    private Set<DriverDTO> drivers = new HashSet<>();

    public CarDTO(Car car) {
        this.id = car.getId();
        this.name = car.getName();
        this.brand =  car.getBrand();
        this.make =  car.getMake();
        this.year =  car.getYear();
        this.sponsor =  car.getSponsor();
        this.color =  car.getColor();

        for (Race race : car.getRaces()) {
            this.races.add(new RaceDTO(race));
        }

        for (Driver driver : car.getDrivers()) {
            this.drivers.add(new DriverDTO(driver));
        }

    }
    public static List<CarDTO> getDtos(List<Car> carList){
        List<CarDTO> carDTOS = new ArrayList();
        carList.forEach(car->carDTOS.add(new CarDTO(car)));
        return carDTOS;
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Set<RaceDTO> getRaces() {
        return races;
    }

    public void setRaces(Set<RaceDTO> races) {
        this.races = races;
    }

    public Set<DriverDTO> getDrivers() {
        return drivers;
    }

    public void setDrivers(Set<DriverDTO> drivers) {
        this.drivers = drivers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarDTO carDTO = (CarDTO) o;
        return id == carDTO.id && year == carDTO.year && Objects.equals(name, carDTO.name) && Objects.equals(brand, carDTO.brand) && Objects.equals(make, carDTO.make) && Objects.equals(sponsor, carDTO.sponsor) && Objects.equals(color, carDTO.color) && Objects.equals(races, carDTO.races) && Objects.equals(drivers, carDTO.drivers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, brand, make, year, sponsor, color, races, drivers);
    }
}
