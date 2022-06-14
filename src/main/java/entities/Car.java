package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NamedQuery(name = "Car.deleteAllRows", query = "DELETE from Car")
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "brand")
    private String brand;

    @Column(name = "make")
    private String make;

    @Column(name = "year")
    private int year;

    @Column(name = "sponsor")
    private String sponsor;

    @Column(name = "color")
    private String color;

    @Column(name = "img_url")
    private String image;

    @ManyToMany(mappedBy = "cars")
    private List<Race> races = new ArrayList<>();

    @OneToMany(mappedBy = "car")
    private Set<Driver> drivers = new HashSet<>();


    public Car() {
    }

    public Car(String name, String brand, String make, int year, String sponsor, String color, String image) {
        this.name = name;
        this.brand = brand;
        this.make = make;
        this.year = year;
        this.sponsor = sponsor;
        this.color = color;
        this.image =image;

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

    public List<Race> getRaces() {
        return races;
    }

    public Set<Driver> getDrivers() {
        return drivers;
    }


    public void addRace(Race race) {
        this.races.add(race);
        if(!race.getCars().contains(this)){
            race.addCar(this);
        }
    }

    public void removeDriver(Driver driver) {
        this.drivers.remove(driver);
        driver.setCar(null);
    }

    public void addDriver(Driver driver) {
        this.drivers.add(driver);
        if(!driver.getCar().equals(this)){
            driver.setCar(this);
        }
    }
}
