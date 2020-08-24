package ag7.dev.ag7geoapi;

import org.springframework.stereotype.Component;

import ag7.dev.ag7geoapi.controller.viewmodel.city.CityViewModelDep;
import ag7.dev.ag7geoapi.controller.viewmodel.city.CityViewModelDepInfo;
import ag7.dev.ag7geoapi.controller.viewmodel.city.CityViewModelDepInfoNoReg;
import ag7.dev.ag7geoapi.controller.viewmodel.city.CityViewModelDepNoReg;
import ag7.dev.ag7geoapi.controller.viewmodel.city.CityViewModelInfo;
import ag7.dev.ag7geoapi.controller.viewmodel.city.SimpleCityViewModel;
import ag7.dev.ag7geoapi.controller.viewmodel.department.DepartmentViewModelInfo;
import ag7.dev.ag7geoapi.controller.viewmodel.department.DepartmentViewModelReg;
import ag7.dev.ag7geoapi.controller.viewmodel.department.DepartmentViewModelRegInfo;
import ag7.dev.ag7geoapi.controller.viewmodel.department.SimpleDepartmentViewModel;
import ag7.dev.ag7geoapi.controller.viewmodel.region.RegionViewModelInfo;
import ag7.dev.ag7geoapi.controller.viewmodel.region.SimpleRegionViewModel;
import ag7.dev.ag7geoapi.model.City;
import ag7.dev.ag7geoapi.model.Department;
import ag7.dev.ag7geoapi.model.Region;

@Component
public class Mapper {

    // CITY

    public SimpleCityViewModel fromCityToSimpleCityViewModel (City city) {
        SimpleCityViewModel simpleCityViewModel = new SimpleCityViewModel();

        simpleCityViewModel.setName(city.getName());
        simpleCityViewModel.setPopulation(city.getPopulation());
        simpleCityViewModel.setPostalCodes(city.getPostalCodes());

        return simpleCityViewModel;
    }
    
    public CityViewModelDepInfo fromCityToCityViewModelDepInfo (City city) {
        CityViewModelDepInfo cityViewModelDepInfo = new CityViewModelDepInfo();

        cityViewModelDepInfo.setName(city.getName());
        cityViewModelDepInfo.setInfo(city.getInfo());
        cityViewModelDepInfo.setPopulation(city.getPopulation());
        cityViewModelDepInfo.setPostalCodes(city.getPostalCodes());
        cityViewModelDepInfo.setDepartment(this.fromDepartmentToDepartmentViewModelRegInfo(city.getDepartment()));

        return cityViewModelDepInfo;
    }

    public CityViewModelDep fromCityToCityViewModelDep (City city) {
        CityViewModelDep cityViewModelDep = new CityViewModelDep();

        cityViewModelDep.setName(city.getName());
        cityViewModelDep.setPopulation(city.getPopulation());
        cityViewModelDep.setPostalCodes(city.getPostalCodes());
        cityViewModelDep.setDepartment(this.fromDepartmentToDepartmentViewModelReg(city.getDepartment()));

        return cityViewModelDep;
    }

    public CityViewModelInfo fromCityToCityViewModelInfo (City city) {
        CityViewModelInfo cityViewModelInfo = new CityViewModelInfo();

        cityViewModelInfo.setName(city.getName());
        cityViewModelInfo.setInfo(city.getInfo());
        cityViewModelInfo.setPopulation(city.getPopulation());
        cityViewModelInfo.setPostalCodes(city.getPostalCodes());

        return cityViewModelInfo;
    }

    public CityViewModelDepNoReg fromCityToCityViewModelDepNoReg (City city) {
        CityViewModelDepNoReg cityViewModelDepNoReg = new CityViewModelDepNoReg();

        cityViewModelDepNoReg.setName(city.getName());
        cityViewModelDepNoReg.setPopulation(city.getPopulation());
        cityViewModelDepNoReg.setPostalCodes(city.getPostalCodes());
        cityViewModelDepNoReg.setDepartment(this.fromDepartmentToSimpleDepartmentViewModel(city.getDepartment()));

        return cityViewModelDepNoReg;
    }

    public CityViewModelDepInfoNoReg fromCityToCityViewModelDepInfoNoReg (City city) {
        CityViewModelDepInfoNoReg cityViewModelDepInfoNoReg = new CityViewModelDepInfoNoReg();

        cityViewModelDepInfoNoReg.setName(city.getName());
        cityViewModelDepInfoNoReg.setPopulation(city.getPopulation());
        cityViewModelDepInfoNoReg.setPostalCodes(city.getPostalCodes());
        cityViewModelDepInfoNoReg.setInfo(city.getInfo());
        cityViewModelDepInfoNoReg.setDepartment(this.fromDepartmentToDepartmentViewModelInfo(city.getDepartment()));

        return cityViewModelDepInfoNoReg;
    }

    // DEPARTMENT

    public SimpleDepartmentViewModel fromDepartmentToSimpleDepartmentViewModel (Department department) {
        SimpleDepartmentViewModel simpleDepartmentViewModel = new SimpleDepartmentViewModel();

        simpleDepartmentViewModel.setName(department.getName());
        simpleDepartmentViewModel.setNumber(department.getNum());
  
        return simpleDepartmentViewModel;
    }

    public DepartmentViewModelInfo fromDepartmentToDepartmentViewModelInfo (Department department) {
        DepartmentViewModelInfo departmentViewModelInfo = new DepartmentViewModelInfo();

        departmentViewModelInfo.setName(department.getName());
        departmentViewModelInfo.setInfo(department.getInfo());
        departmentViewModelInfo.setNumber(department.getNum());

        return departmentViewModelInfo;
    }

    public DepartmentViewModelReg fromDepartmentToDepartmentViewModelReg (Department department) {
        DepartmentViewModelReg departmentViewModelReg = new DepartmentViewModelReg();

        departmentViewModelReg.setName(department.getName());
        departmentViewModelReg.setNumber(department.getNum());
        departmentViewModelReg.setRegion(this.fromRegionToSimpleRegionViewModel(department.getRegion()));

        return departmentViewModelReg;
    }

    public DepartmentViewModelRegInfo fromDepartmentToDepartmentViewModelRegInfo (Department department) {
        DepartmentViewModelRegInfo departmentViewModelRegInfo = new DepartmentViewModelRegInfo();

        departmentViewModelRegInfo.setName(department.getName());
        departmentViewModelRegInfo.setInfo(department.getInfo());
        departmentViewModelRegInfo.setNumber(department.getNum());
        departmentViewModelRegInfo.setRegion(this.fromRegionToRegionViewModelInfo(department.getRegion()));

        return departmentViewModelRegInfo;
    }

    // REGION

    public SimpleRegionViewModel fromRegionToSimpleRegionViewModel (Region region) {
        SimpleRegionViewModel simpleRegionViewModel = new SimpleRegionViewModel();

        simpleRegionViewModel.setName(region.getName());

        return simpleRegionViewModel;
    }

    

    public RegionViewModelInfo fromRegionToRegionViewModelInfo (Region region) {
        RegionViewModelInfo regionViewModelInfo = new RegionViewModelInfo();

        regionViewModelInfo.setName(region.getName());
        regionViewModelInfo.setInfo(region.getInfo());

        return regionViewModelInfo;
    }
}