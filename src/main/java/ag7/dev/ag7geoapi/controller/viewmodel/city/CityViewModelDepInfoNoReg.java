package ag7.dev.ag7geoapi.controller.viewmodel.city;

import ag7.dev.ag7geoapi.controller.viewmodel.department.DepartmentViewModelInfo;

public class CityViewModelDepInfoNoReg extends SimpleCityViewModel {
    private String info;
    private DepartmentViewModelInfo department;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public DepartmentViewModelInfo getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentViewModelInfo department) {
        this.department = department;
    }

    
}