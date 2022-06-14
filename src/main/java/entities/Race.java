package entities;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@NamedQuery(name = "Race.deleteAllRows", query = "DELETE from Race")
@Table(name = "races")
public class Race {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "duration_in_minutes")
    private int duration;

    @ManyToMany
    private Set<Car> cars = new HashSet<>();

    public Race() {
    }

    public Race(String name, String location, String startDate, int duration) {
        this.name = name;
        this.location = location;
        this.startDate = startDate;
        this.duration = duration;
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

    public Set<Car> getCars() {
        return cars;
    }
}
