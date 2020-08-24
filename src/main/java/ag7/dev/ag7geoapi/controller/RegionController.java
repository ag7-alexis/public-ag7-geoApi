package ag7.dev.ag7geoapi.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ag7.dev.ag7geoapi.Mapper;
import ag7.dev.ag7geoapi.controller.viewmodel.region.RegionViewModelInfo;
import ag7.dev.ag7geoapi.controller.viewmodel.region.SimpleRegionViewModel;
import ag7.dev.ag7geoapi.db.RegionRepository;
import ag7.dev.ag7geoapi.model.Region;

/**
 * Region Controller : to get region details
 * @ag7-alexis
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/public/api/region")
public class RegionController {

    private RegionRepository regionRepository;
    private Mapper mapper;

    public RegionController(RegionRepository regionRepository, Mapper mapper) {
        this.regionRepository = regionRepository;
        this.mapper = mapper;
    }

    /**
     * Request to get region details
     * @param param_region param to found the region
     * @param info option to get or not the info field
     * @return a JSONObject of the region who match with param
     */

    @RequestMapping(value = "/{param_region}", method = RequestMethod.GET)
    public ResponseEntity<?> getRegion(@PathVariable String param_region,
            @RequestParam(value = "noInfo") Optional<?> info) {

        Optional<Region> testRegion = this.regionRepository.findByNameIgnoreCase(param_region);

        if (!testRegion.isPresent())
            return ResponseEntity.ok(new JSONObject());

        Region theRegion = testRegion.get();

        if (info.isPresent()) {
            return ResponseEntity.ok(this.mapper.fromRegionToSimpleRegionViewModel(theRegion));
        } else {
            return ResponseEntity.ok(this.mapper.fromRegionToRegionViewModelInfo(theRegion));
        }
    }

    /**
     * Request to get all regions
     * @param info option to get or not the info field
     * @return a JSONArray of all regions
     */

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getAllRegions(@RequestParam(value = "noInfo") Optional<?> info) {
        List<Region> regions = this.regionRepository.findAll();
        if (info.isPresent()) {
            List<SimpleRegionViewModel> simpleRegionViewModels = regions.stream().map(region -> this.mapper.fromRegionToSimpleRegionViewModel(region)).collect(Collectors.toList());
            return ResponseEntity.ok(simpleRegionViewModels);
        } else {
            List<RegionViewModelInfo> regionViewModelInfos = regions.stream().map(region -> this.mapper.fromRegionToRegionViewModelInfo(region)).collect(Collectors.toList());
            return ResponseEntity.ok(regionViewModelInfos);
        }
    }
}