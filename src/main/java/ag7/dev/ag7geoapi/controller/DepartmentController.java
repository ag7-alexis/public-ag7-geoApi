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
import ag7.dev.ag7geoapi.controller.viewmodel.department.*;
import ag7.dev.ag7geoapi.db.DepartmentRepository;
import ag7.dev.ag7geoapi.db.RegionRepository;
import ag7.dev.ag7geoapi.model.Department;
import ag7.dev.ag7geoapi.model.Region;

/**
 * Department Controller : to get department details
 * @ag7-alexis
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/public/api/department")
public class DepartmentController {

    private DepartmentRepository departmentRepository;
    private RegionRepository regionRepository;
    private Mapper mapper;

    public DepartmentController(DepartmentRepository departmentRepository, RegionRepository regionRepository,
            Mapper mapper) {
        this.departmentRepository = departmentRepository;
        this.regionRepository = regionRepository;
        this.mapper = mapper;
    }

    /**
     * Request to get details on department
     * @param param_department param to found the department
     * @param info option to get or not the info field
     * @param region option to get or not the region field
     * @return a JSONObject of the department who match with param
     */

    @RequestMapping(value = "/{param_department}", method = RequestMethod.GET)
    private ResponseEntity<?> getDepartments(@PathVariable String param_department,
            @RequestParam(value = "noInfo") Optional<?> info, @RequestParam(value = "noRegion") Optional<?> region) {

        Optional<Department> testDepartment = Optional.ofNullable(null);

        if (param_department.matches(".*\\d+.*")) {
            testDepartment = this.departmentRepository.findByNumIgnoreCase(param_department);
        } else {
            testDepartment = this.departmentRepository.findByNameIgnoreCase(param_department);
        }

        if (!testDepartment.isPresent())
            return ResponseEntity.ok(null);

        if (!info.isPresent() && !region.isPresent()) {
            // all details
            return ResponseEntity.ok(this.mapper.fromDepartmentToDepartmentViewModelRegInfo(testDepartment.get()));
        } else if (info.isPresent() && !region.isPresent()) {
            // no info
            return ResponseEntity.ok(this.mapper.fromDepartmentToDepartmentViewModelReg(testDepartment.get()));
        } else if (info.isPresent() && region.isPresent()) {
            // no details
            return ResponseEntity.ok(this.mapper.fromDepartmentToSimpleDepartmentViewModel(testDepartment.get()));
        } else if (!info.isPresent() && region.isPresent()) {
            // no region
            return ResponseEntity.ok(this.mapper.fromDepartmentToDepartmentViewModelInfo(testDepartment.get()));
        }

        return ResponseEntity.ok(null);
    }

    /**
     * Request to get departments of a region
     * @param param_region param to found the region
     * @param info option to get or not the info field
     * @param region option to get or not the region field
     * @return a JSONArray of departments in the region who match with param
     */

    @RequestMapping(value = "/{param_region}/region", method = RequestMethod.GET)
    public ResponseEntity<?> getDepartmentsByRegion(@PathVariable String param_region,
            @RequestParam(value = "noInfo") Optional<?> info, 
            @RequestParam(value = "noRegion") Optional<?> region) {

                Optional<Region> testRegion = this.regionRepository.findByNameIgnoreCase(param_region);

                if (!testRegion.isPresent()) return ResponseEntity.ok(new ArrayList<>());

                Region theRegion = testRegion.get();

                if (!info.isPresent() && !region.isPresent()) {
                    // all details
                    List<DepartmentViewModelRegInfo> departmentViewModelRegInfos = theRegion.getDepartments().stream().map(department -> this.mapper.fromDepartmentToDepartmentViewModelRegInfo(department)).collect(Collectors.toList());
                    return ResponseEntity.ok(departmentViewModelRegInfos);
                } else if (info.isPresent() && !region.isPresent()) {
                    // no info
                    List<DepartmentViewModelReg> departmentViewModelRegs = theRegion.getDepartments().stream().map(department -> this.mapper.fromDepartmentToDepartmentViewModelReg(department)).collect(Collectors.toList());
                    return ResponseEntity.ok(departmentViewModelRegs);
                } else if (info.isPresent() && region.isPresent()) {
                    // no details
                    List<SimpleDepartmentViewModel> simpleDepartmentViewModels = theRegion.getDepartments().stream().map(department -> this.mapper.fromDepartmentToSimpleDepartmentViewModel(department)).collect(Collectors.toList());
                    return ResponseEntity.ok(simpleDepartmentViewModels);
                } else if (!info.isPresent() && region.isPresent()) {
                    // no region
                    List<DepartmentViewModelInfo> departmentViewModelInfos = theRegion.getDepartments().stream().map(department -> this.mapper.fromDepartmentToDepartmentViewModelInfo(department)).collect(Collectors.toList());
                    return ResponseEntity.ok(departmentViewModelInfos);
                }
        
                return ResponseEntity.ok(new ArrayList<>());

    }

}