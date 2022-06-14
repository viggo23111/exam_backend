package dtos;

import entities.Car;
import entities.Driver;
import entities.User;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DriverDTO {
    private int id;
    private String name;
    private int birthYear;
    private String experience;
    private String gender;
    private int userID;
    private String userName;
    private String password;

    public DriverDTO(Driver driver) {
        this.id = driver.getId();
        this.name = driver.getName();
        this.birthYear = driver.getBirthYear();
        this.experience = driver.getExperience();
        this.gender = driver.getGender();
        if(driver.getUser()!=null) {
            this.userID = driver.getUser().getId();
        }

    }

    public static List<DriverDTO> getDtos(List<Driver> driverList){
        List<DriverDTO> driverDTOS = new ArrayList();
        driverList.forEach(driver->driverDTOS.add(new DriverDTO(driver)));
        return driverDTOS;
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

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DriverDTO driverDTO = (DriverDTO) o;
        return id == driverDTO.id && birthYear == driverDTO.birthYear && userID == driverDTO.userID && Objects.equals(name, driverDTO.name) && Objects.equals(experience, driverDTO.experience) && Objects.equals(gender, driverDTO.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birthYear, experience, gender, userID);
    }

    @Override
    public String toString() {
        return "DriverDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthYear=" + birthYear +
                ", experience='" + experience + '\'' +
                ", gender='" + gender + '\'' +
                ", userID=" + userID +
                '}';
    }
}
