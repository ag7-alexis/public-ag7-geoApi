package ag7.dev.ag7geoapi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ag7.dev.ag7geoapi.Mapper;
import ag7.dev.ag7geoapi.controller.viewmodel.city.*;
import ag7.dev.ag7geoapi.db.CityRepository;
import ag7.dev.ag7geoapi.db.DepartmentRepository;
import ag7.dev.ag7geoapi.model.City;
import ag7.dev.ag7geoapi.model.Department;

/**
 * City Controller : make rest request to get city details
 * @ag7-alexis
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/public/api/city")
public class CityController {

    private CityRepository cityRepository;
    private DepartmentRepository departmentRepository;
    private Mapper mapper;

    public CityController(CityRepository cityRepository, DepartmentRepository departmentRepository, Mapper mapper) {
        this.cityRepository = cityRepository;
        this.departmentRepository = departmentRepository;
        this.mapper = mapper;
    }

    /**
     * Request to get details on cities
     * @param param_city param to found the city
     * @param info option to get or not the info field
     * @param department option to get or not the department field
     * @param region option to get or not the region field
     * @return a JSONArray of cities who match with param
     */

    @RequestMapping(value = "/{param_city}", method = RequestMethod.GET)
    public ResponseEntity<?> getCities(@PathVariable String param_city,
            @RequestParam(value = "noInfo") Optional<?> info,
            @RequestParam(value = "noDepartment") Optional<?> department,
            @RequestParam(value = "noRegion") Optional<?> region) {

        List<City> cities = new ArrayList<>();

        if (param_city.matches("[0-9]+") && param_city.length() == 5) {
            cities = this.cityRepository.findAllByPostalCodesContainingIgnoreCase(param_city);
        } else {
            cities = this.cityRepository.findAllByNameContainingIgnoreCase(param_city);
        }

        if (cities.size() < 1)
            return ResponseEntity.ok(cities);

        if (!info.isPresent() && !department.isPresent() && !region.isPresent()) {
            // all details
            return ResponseEntity.ok(this.mapperCityList(cities, CityViewModelDepInfo.class));
        } else if (info.isPresent() && !department.isPresent() && !region.isPresent()) {
            // all details without info
            return ResponseEntity.ok(this.mapperCityList(cities, CityViewModelDep.class));
        } else if (info.isPresent() && department.isPresent()) {
            // no details
            return ResponseEntity.ok(this.mapperCityList(cities, SimpleCityViewModel.class));
        } else if (info.isPresent() && !department.isPresent() && region.isPresent()) {
            // no info and no region
            return ResponseEntity.ok(this.mapperCityList(cities, CityViewModelDepNoReg.class));
        } else if (!info.isPresent() && !department.isPresent() && region.isPresent()) {
            // no region
            return ResponseEntity.ok(this.mapperCityList(cities, CityViewModelDepInfoNoReg.class));
        } else if (!info.isPresent() && department.isPresent()) {
            // no department no region
            return ResponseEntity.ok(this.mapperCityList(cities, CityViewModelInfo.class));
        }

        return ResponseEntity.ok(new ArrayList<>());
    }

    /**
     * Request to get cities of a department
     * @param param_department param to found the department
     * @param info option to get or not the info field
     * @param department option to get or not the department field
     * @param region option to get or not the region field
     * @return a JSONArray of cities who match with param
     */

    @RequestMapping(value = "/{param_department}/department", method = RequestMethod.GET)
    public ResponseEntity<?> getCitiesByDepartment(@PathVariable String param_department,
            @RequestParam(value = "noInfo") Optional<?> info,
            @RequestParam(value = "noDepartment") Optional<?> department,
            @RequestParam(value = "noRegion") Optional<?> region) {

        Optional<Department> testDepartment = Optional.ofNullable(null);
        if (param_department.matches(".*\\d+.*")) {
            testDepartment = this.departmentRepository.findByNumIgnoreCase(param_department);
        } else {
            testDepartment = this.departmentRepository.findByNameIgnoreCase(param_department);
        }

        if (!testDepartment.isPresent())
            return ResponseEntity.ok(new ArrayList<>());

        List<City> cities = testDepartment.get().getCities();

        if (cities.size() < 1)
            return ResponseEntity.ok(cities);

        if (!info.isPresent() && !department.isPresent() && !region.isPresent()) {
            // all details
            return ResponseEntity.ok(this.mapperCityList(cities, CityViewModelDepInfo.class));
        } else if (info.isPresent() && !department.isPresent() && !region.isPresent()) {
            // all details without info
            return ResponseEntity.ok(this.mapperCityList(cities, CityViewModelDep.class));
        } else if (info.isPresent() && department.isPresent()) {
            // no details
            return ResponseEntity.ok(this.mapperCityList(cities, SimpleCityViewModel.class));
        } else if (info.isPresent() && !department.isPresent() && region.isPresent()) {
            // no info and no region
            return ResponseEntity.ok(this.mapperCityList(cities, CityViewModelDepNoReg.class));
        } else if (!info.isPresent() && !department.isPresent() && region.isPresent()) {
            // no region
            return ResponseEntity.ok(this.mapperCityList(cities, CityViewModelDepInfoNoReg.class));
        } else if (!info.isPresent() && department.isPresent()) {
            // no department no region
            return ResponseEntity.ok(this.mapperCityList(cities, CityViewModelInfo.class));
        }

        return ResponseEntity.ok(new ArrayList<>());
    }

    /**
     * Method to get ViewModel of every cities
     * @param cities list of city to convert 
     * @param type class of ViewModel wanted
     * @return list of cities ViewModel
     */

    private List<?> mapperCityList(List<City> cities, Class type) {
        if (type == CityViewModelDepInfo.class) {
            return cities.stream().map(city -> this.mapper.fromCityToCityViewModelDepInfo(city))
                    .collect(Collectors.toList());
        } else if (type == CityViewModelDep.class) {
            return cities.stream().map(city -> this.mapper.fromCityToCityViewModelDep(city))
                    .collect(Collectors.toList());
        } else if (type == SimpleCityViewModel.class) {
            return cities.stream().map(city -> this.mapper.fromCityToSimpleCityViewModel(city))
                    .collect(Collectors.toList());
        } else if (type == CityViewModelDepNoReg.class) {
            return cities.stream().map(city -> this.mapper.fromCityToCityViewModelDepNoReg(city))
                    .collect(Collectors.toList());
        } else if (type == CityViewModelDepInfoNoReg.class) {
            return cities.stream().map(city -> this.mapper.fromCityToCityViewModelDepInfoNoReg(city))
                    .collect(Collectors.toList());
        } else if (type == CityViewModelInfo.class) {
            return cities.stream().map(city -> this.mapper.fromCityToCityViewModelInfo(city))
                    .collect(Collectors.toList());
        }
        return null;
    }

}