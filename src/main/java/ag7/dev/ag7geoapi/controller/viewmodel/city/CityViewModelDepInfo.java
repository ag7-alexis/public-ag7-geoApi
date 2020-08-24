package ag7.dev.ag7geoapi.controller.viewmodel.city;

import ag7.dev.ag7geoapi.controller.viewmodel.department.DepartmentViewModelRegInfo;

public class CityViewModelDepInfo extends SimpleCityViewModel {
    private String info;
    private DepartmentViewModelRegInfo department;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public DepartmentViewModelRegInfo getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentViewModelRegInfo department) {
        this.department = department;
    }


}