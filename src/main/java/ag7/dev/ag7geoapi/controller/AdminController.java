package ag7.dev.ag7geoapi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import ag7.dev.ag7geoapi.db.CityRepository;
import ag7.dev.ag7geoapi.db.DepartmentRepository;
import ag7.dev.ag7geoapi.db.RegionRepository;
import ag7.dev.ag7geoapi.model.City;
import ag7.dev.ag7geoapi.model.Department;
import ag7.dev.ag7geoapi.model.Region;

/**
 * Controller for Admin
 * @ag7-alexis
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/private/admin")
public class AdminController {

    private DepartmentRepository departmentRepository;
    private RegionRepository regionRepository;
    private CityRepository cityRepository;

    public AdminController(DepartmentRepository departmentRepository, RegionRepository regionRepository,
            CityRepository cityRepository) {
        this.departmentRepository = departmentRepository;
        this.regionRepository = regionRepository;
        this.cityRepository = cityRepository;
    }

    /**
     * Request to collect all data from gouv api and wiki api
     * @throws JSONException
     * @throws InterruptedException
     */

    @RequestMapping(value = "/initData", method = RequestMethod.GET)
    public void initData() throws JSONException, InterruptedException {

        ArrayList<Region> regions = new ArrayList<>();
        ArrayList<Department> departments = new ArrayList<>();
        ArrayList<City> cities = new ArrayList<>();

        // Get all Region of France
        String urlRegionGouv = "https://geo.api.gouv.fr/regions";

        RestTemplate restTemplateRegionGouv = new RestTemplate();

        String resultRegionGouv = restTemplateRegionGouv.getForObject(urlRegionGouv, String.class);

        JSONArray arrayRegionGouv = new JSONArray(resultRegionGouv);

        for (int i = 0; i < arrayRegionGouv.length(); i++) {

            String nomRegion = arrayRegionGouv.getJSONObject(i).getString("nom");
            String codeRegion = arrayRegionGouv.getJSONObject(i).getString("code");

            // Get info about Region from Wikipedia
            String urlRegionWiki = "https://fr.wikipedia.org/w/api.php?"
                    + "format=json&action=query&prop=extracts&exintro&explaintext&redirects=1&titles=" + nomRegion;
            RestTemplate restTemplateRegionWiki = new RestTemplate();

            String resultRegionWiki = restTemplateRegionWiki.getForObject(urlRegionWiki, String.class);

            JSONObject itemRegionWiki1 = new JSONObject(resultRegionWiki).getJSONObject("query").getJSONObject("pages");

            JSONObject itemRegionWiki2 = itemRegionWiki1.getJSONObject(itemRegionWiki1.names().getString(0));

            String infoRegion = itemRegionWiki2.getString("extract");

            Region region = new Region(nomRegion, infoRegion);

            regions.add(region);

            // Timer to prevent ban
            Random rng = new Random();
            int timeSleep = rng.nextInt((6 - 3) + 1) + 3;
            TimeUnit.SECONDS.sleep(timeSleep);

            String urlDepartmentGouv = "https://geo.api.gouv.fr/regions/" + codeRegion + "/departements";

            RestTemplate restTemplateDepartmentGouv = new RestTemplate();

            String resultDepartmentGouv = restTemplateDepartmentGouv.getForObject(urlDepartmentGouv, String.class);

            JSONArray arrayDepartmentGouv = new JSONArray(resultDepartmentGouv);

            for (int j = 0; j < arrayDepartmentGouv.length(); j++) {
                // Get info about department of a region from wiki
                String nomDepartment = arrayDepartmentGouv.getJSONObject(j).getString("nom");
                String codeDepartment = arrayDepartmentGouv.getJSONObject(j).getString("code");

                // try call wiki api with {{name_department}}+_(departement)
                String urlDepartmentWiki = "https://fr.wikipedia.org/w/api.php?"
                        + "format=json&action=query&prop=extracts&exintro&explaintext&redirects=1&titles="
                        + nomDepartment + "_(département)";

                RestTemplate restTemplateDepartmentWiki = new RestTemplate();

                String resultDepartmentWiki = restTemplateDepartmentWiki.getForObject(urlDepartmentWiki, String.class);

                JSONObject itemDepartmentWiki1 = new JSONObject(resultDepartmentWiki).getJSONObject("query")
                        .getJSONObject("pages");

                String infoDepartment = "";

                if (!itemDepartmentWiki1.names().getString(0).equals("-1")) {

                    JSONObject itemDepartmentWiki2 = itemDepartmentWiki1
                            .getJSONObject(itemDepartmentWiki1.names().getString(0));

                    infoDepartment = itemDepartmentWiki2.getString("extract");
                }

                if (infoDepartment.equals("")) {
                    // if first try do'nt work, try with only {{name_department}}
                    urlDepartmentWiki = "https://fr.wikipedia.org/w/api.php?"
                            + "format=json&action=query&prop=extracts&exintro&explaintext&redirects=1&titles="
                            + nomDepartment;

                    resultDepartmentWiki = restTemplateDepartmentWiki.getForObject(urlDepartmentWiki, String.class);

                    itemDepartmentWiki1 = new JSONObject(resultDepartmentWiki).getJSONObject("query")
                            .getJSONObject("pages");

                    JSONObject itemDepartmentWiki2 = itemDepartmentWiki1
                            .getJSONObject(itemDepartmentWiki1.names().getString(0));

                    infoDepartment = itemDepartmentWiki2.getString("extract");
                }

                Department department = new Department(nomDepartment, infoDepartment, codeDepartment, region);

                departments.add(department);

                // get all city of a department

                String urlCityGouv = "https://geo.api.gouv.fr/departements/" + codeDepartment + "/communes";

                RestTemplate restTemplateCityGouv = new RestTemplate();

                String resultCityGouv = restTemplateCityGouv.getForObject(urlCityGouv, String.class);

                JSONArray arrayCityGouv = new JSONArray(resultCityGouv);

                for (int k = 0; k < arrayCityGouv.length(); k++) {
                    if (arrayCityGouv.getJSONObject(k).has("population")) {
                        if (arrayCityGouv.getJSONObject(k).getLong("population") >= 2000) {
                            long populationCity = arrayCityGouv.getJSONObject(k).getLong("population");
                            String nomCity = arrayCityGouv.getJSONObject(k).getString("nom");
                            JSONArray postalCodesCityJsonArray = arrayCityGouv.getJSONObject(k)
                                    .getJSONArray("codesPostaux");
                            List<String> postalCodesCity = new ArrayList<>();
                            for (int l = 0; l < postalCodesCityJsonArray.length(); l++) {
                                postalCodesCity.add(postalCodesCityJsonArray.getString(l));
                            }

                            // get city info from wiki

                            String urlCityWiki = "https://fr.wikipedia.org/w/api.php?"
                                    + "format=json&action=query&prop=extracts&exintro&explaintext&redirects=1&titles="
                                    + nomCity;

                            RestTemplate restTemplateCityWiki = new RestTemplate();

                            String resultCityWiki = restTemplateCityWiki.getForObject(urlCityWiki, String.class);

                            JSONObject itemCityWiki = new JSONObject(resultCityWiki).getJSONObject("query")
                                    .getJSONObject("pages");

                            String infoCity = "";

                            if (!itemCityWiki.names().getString(0).equals("-1")) {
                                JSONObject itemCityWiki1 = itemCityWiki
                                        .getJSONObject(itemCityWiki.names().getString(0));

                                infoCity = itemCityWiki1.getString("extract");
                            }

                            if (infoCity.equals("")) {
                                urlCityWiki = "https://fr.wikipedia.org/w/api.php?"
                                        + "format=json&action=query&prop=extracts&exintro&explaintext&redirects=1&titles="
                                        + nomCity + "_(" + department.getName() + ")";

                                resultCityWiki = restTemplateCityWiki.getForObject(urlCityWiki, String.class);

                                itemCityWiki = new JSONObject(resultCityWiki).getJSONObject("query")
                                        .getJSONObject("pages");

                                if (!itemCityWiki.names().getString(0).equals("-1")) {
                                    JSONObject itemCityWiki1 = itemCityWiki
                                            .getJSONObject(itemCityWiki.names().getString(0));

                                    infoCity = itemCityWiki1.getString("extract");
                                }
                            }

                            City city = new City(nomCity, infoCity, populationCity, department);
                            city.setPostalCodes(postalCodesCity);
                            cities.add(city);

                            System.out.println(city.toString());
                        }
                    }
                }

            }

        }
        this.regionRepository.saveAll(regions);
        this.departmentRepository.saveAll(departments);
        this.cityRepository.saveAll(cities);
    }

}