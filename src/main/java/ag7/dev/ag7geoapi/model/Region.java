package ag7.dev.ag7geoapi.model;

import javax.persistence.*;
import java.util.List;

/**
 * Region Entity
 * @ag7-alexis
 */
@Entity
@Table(name = "Region", indexes = {@Index(name = "index_name_Region", columnList = "name_Region", unique = true)})
public class Region {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "name_Region", nullable = false)
    private String name;

    @Column(columnDefinition="text")
    private String info;

    @OneToMany(mappedBy = "region", fetch = FetchType.LAZY)
    private List<Department> departments;

	public Region() {
	}

	public Region(long id, String name, String info) {
		this.id = id;
		this.name = name;
		this.info = info;
	}

	public Region(String name, String info) {
		this.name = name;
		this.info = info;
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

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}


}