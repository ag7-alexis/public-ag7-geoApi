package ag7.dev.ag7geoapi.controller.viewmodel.city;

import ag7.dev.ag7geoapi.controller.viewmodel.department.DepartmentViewModelReg;

public class CityViewModelDep extends SimpleCityViewModel {
    private DepartmentViewModelReg department;

    public DepartmentViewModelReg getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentViewModelReg department) {
        this.department = department;
    }
    
}