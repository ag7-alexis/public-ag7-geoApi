package ag7.dev.ag7geoapi.controller.viewmodel.department;

import ag7.dev.ag7geoapi.controller.viewmodel.region.SimpleRegionViewModel;

public class DepartmentViewModelReg extends SimpleDepartmentViewModel {
    private SimpleRegionViewModel region;

    public SimpleRegionViewModel getRegion() {
        return region;
    }

    public void setRegion(SimpleRegionViewModel region) {
        this.region = region;
    }

}