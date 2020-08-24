package ag7.dev.ag7geoapi.controller.viewmodel.city;

import ag7.dev.ag7geoapi.controller.viewmodel.department.SimpleDepartmentViewModel;

public class CityViewModelDepNoReg extends SimpleCityViewModel {
    private SimpleDepartmentViewModel department;

    public SimpleDepartmentViewModel getDepartment() {
        return department;
    }

    public void setDepartment(SimpleDepartmentViewModel department) {
        this.department = department;
    }

}