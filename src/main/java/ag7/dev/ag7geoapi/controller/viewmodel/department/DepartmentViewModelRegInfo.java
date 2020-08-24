package ag7.dev.ag7geoapi.controller.viewmodel.department;

import ag7.dev.ag7geoapi.controller.viewmodel.region.RegionViewModelInfo;

public class DepartmentViewModelRegInfo extends SimpleDepartmentViewModel {
    private String info;
    private RegionViewModelInfo region;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public RegionViewModelInfo getRegion() {
        return region;
    }

    public void setRegion(RegionViewModelInfo region) {
        this.region = region;
    }

}