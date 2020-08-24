package ag7.dev.ag7geoapi.controller.viewmodel.city;

import java.util.List;

public class SimpleCityViewModel {
    private String name;
    private long population;
    private List<String> postalCodes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    public List<String> getPostalCodes() {
        return postalCodes;
    }

    public void setPostalCodes(List<String> postalCodes) {
        this.postalCodes = postalCodes;
    }

    

}