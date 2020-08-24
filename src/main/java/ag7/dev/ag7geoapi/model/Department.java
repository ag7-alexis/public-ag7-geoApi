package ag7.dev.ag7geoapi.model;

import javax.persistence.*;

import java.util.List;

/**
 * Department Entity
 * @ag7-alexis
 */
@Entity
@Table(name = "Department", indexes = {@Index(name = "index_name_Department",columnList = "name_Department", unique = true)})
public class Department {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "name_Department", nullable = false)
    private String name;

    @Column(columnDefinition="text")
	private String info;
	
	private String num;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Region region;
	
	@ManyToMany(mappedBy = "department", fetch = FetchType.LAZY)
	private List<City> Cities;

	public Department() {
	}

	public Department(long id, String name, String info, Region region) {
		this.id = id;
		this.name = name;
		this.info = info;
		this.region = region;
	}

	public Department(String name, String info, String num, Region region) {
		this.name = name;
		this.info = info;
		this.num = num;
		this.region = region;
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

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public List<City> getCities() {
		return Cities;
	}

	public void setCities(List<City> cities) {
		Cities = cities;
	}



}