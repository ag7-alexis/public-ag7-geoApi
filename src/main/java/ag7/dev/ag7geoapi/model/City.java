package ag7.dev.ag7geoapi.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * City Entity
 * @ag7-alexis
 */

@Entity
@Table(name = "City", indexes = { @Index(name = "index_name_City", columnList = "name_City", unique = false) })
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "name_City", nullable = false)
    private String name;

    @Column(columnDefinition = "text")
    private String info;

    private long population;

    private String postalCodes;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Department department;

    public City() {
    }

    public City(long id, String name, String info, long population, String postalCodes, Department department) {
        this.id = id;
        this.name = name;
        this.info = info;
        this.population = population;
        this.postalCodes = postalCodes;
        this.department = department;
    }

    public City(String name, String info, long population, String postalCodes, Department department) {
        this.name = name;
        this.info = info;
        this.population = population;
        this.postalCodes = postalCodes;
        this.department = department;
    }

    public City(String name, String info, long population, Department department) {
        this.name = name;
        this.info = info;
        this.population = population;
        this.department = department;
    }


    public City(String name, String info, long population, String postalCodes) {
        this.name = name;
        this.info = info;
        this.population = population;
        this.postalCodes = postalCodes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<String> getPostalCodes() {
        List<String> postalCodes = new ArrayList<>();

        for (String code : this.postalCodes.split(",")) {
            postalCodes.add(code);
        }

        return postalCodes;
    }

    public void setPostalCodes(List<String> postalCodes) {
        String result = "";
        for (String code : postalCodes) {
            result+= code + ",";
        }
        this.postalCodes = result;
    }
    

    @Override
    public String toString() {
        return "City [department=" + department + ", id=" + id + ", postalCode=" + postalCodes + ", name=" + name + ", population="
                + population + "]";
    }



}