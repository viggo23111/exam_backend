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
    private String image;
    private Set<DriverDTO> drivers = new HashSet<>();


    public CarDTO(Car car) {
        this.id = car.getId();
        this.name = car.getName();
        this.brand =  car.getBrand();
        this.make =  car.getMake();
        this.year =  car.getYear();
        this.sponsor =  car.getSponsor();
        this.color =  car.getColor();
        this.image =  car.getImage();
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarDTO carDTO = (CarDTO) o;
        return id == carDTO.id && year == carDTO.year && Objects.equals(name, carDTO.name) && Objects.equals(brand, carDTO.brand) && Objects.equals(make, carDTO.make) && Objects.equals(sponsor, carDTO.sponsor) && Objects.equals(color, carDTO.color) && Objects.equals(image, carDTO.image) && Objects.equals(drivers, carDTO.drivers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, brand, make, year, sponsor, color, image, drivers);
    }

    @Override
    public String toString() {
        return "CarDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", make='" + make + '\'' +
                ", year=" + year +
                ", sponsor='" + sponsor + '\'' +
                ", color='" + color + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
