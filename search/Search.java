package search;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents one search on criagslist.org. A Search contains all necessary parameters to run a web search for
 * vehicles on the website, including model, minimum year, minimum/maximum miles, and minimum/maximum price.
 */
public class Search {
    //the vehicle model
    private String model;
    //the minimum price
    private int priceMinimum;
    //the maximum price
    private int priceMaximum;
    //the number of minimum miles
    private int milesMinimum;
    //the maximum number of miles
    private int milesMaximum;
    //the minimum year of the vehicle
    private int yearMinimum;

    /**
     * Constructs a Search with default search parameters
     */
    public Search() {
        this("bmw", 5000, 9000, 50000, 85000, 2008);
        this.adjustSearchParameters();
    }

    /**
     * Constructs a Search with the given search parameters
     *
     * @param model the desired vehicle model
     * @param priceMinimum the minimum price
     * @param priceMaximum the maximum price
     * @param milesMinimum the minimum number of miles
     * @param milesMaximum the maximum number of miles
     * @param yearMinimum the minimum year
     */
    public Search(String model, int priceMinimum, int priceMaximum, int milesMinimum, int milesMaximum, int yearMinimum) {
        this.model = model.toLowerCase();
        this.priceMinimum = priceMinimum;
        this.priceMaximum = priceMaximum;
        this.milesMinimum = milesMinimum;
        this.milesMaximum = milesMaximum;
        this.yearMinimum = yearMinimum;
    }

    /**
     * Returns a map of search urls that can be used to search for a vehicle with this Search's specified parameters.
     * The urls are constructed for northeastern, central, and southern jersey (nnj, cnj, snj respectively).
     *
     * @return the map of search urls
     */
    public Map<String, String> getSearchLinks() {
        //northern new jersey search link
        String url_nnj = String.format("https://newjersey.craigslist.org/search/cto?auto_transmission=2&hasPic=1&min_price=%s"
                        + "&max_auto_miles=%s&max_price=%s&auto_make_model=%s&min_auto_miles=%s"
                        + "&min_auto_year=%s&auto_title_status=1", String.valueOf(this.priceMinimum),
                String.valueOf(this.milesMaximum), String.valueOf(this.priceMaximum), this.model,
                String.valueOf(this.milesMinimum), String.valueOf(this.yearMinimum));

        //central new jersey search link
        String url_cnj = String.format("https://cnj.craigslist.org/search/cto?auto_transmission=2&hasPic=1&min_price=%s"
                        + "&max_auto_miles=%s&max_price=%s&auto_make_model=%s&min_auto_miles=%s"
                        + "&min_auto_year=%s&auto_title_status=1", String.valueOf(this.priceMinimum),
                String.valueOf(this.milesMaximum), String.valueOf(this.priceMaximum), this.model,
                String.valueOf(this.milesMinimum), String.valueOf(this.yearMinimum));

        //southern new jersey search link
        String url_snj = String.format("https://southjersey.craigslist.org/search/cto?auto_transmission=2&hasPic=1&min_price=%s"
                        + "&max_auto_miles=%s&max_price=%s&auto_make_model=%s&min_auto_miles=%s"
                        + "&min_auto_year=%s&auto_title_status=1", String.valueOf(this.priceMinimum),
                String.valueOf(this.milesMaximum), String.valueOf(this.priceMaximum), this.model,
                String.valueOf(this.milesMinimum), String.valueOf(this.yearMinimum));

        //map of search urls
        Map<String, String> urls = new HashMap<String, String>();
        urls.put("nnj", url_nnj);
        urls.put("cnj", url_cnj);
        urls.put("snj", url_snj);

        return urls;
    }

    /**
     * Mutates this Search's parameters based on the current set model to create consistent search parameters.
     */
    private void adjustSearchParameters() {
        if (this.model.equals("bmw") || this.model.equals("bimmer")) {
            //search criteria for bmw
            this.model = "bmw";
            this.priceMinimum = 5000;
            this.priceMaximum = 9000;
            this.milesMinimum = 50000;
            this.milesMaximum = 80000;
            this.yearMinimum = 2009;
        } else if (this.model.equals("mercedes") || this.model.equals("mb")
                || this.model.equals("mercedes benz") || this.model.equals("benz")) {
            //search criteria for mercedes-benz
            this.model = "mercedes";
            this.priceMinimum = 6000;
            this.priceMaximum = 14000;
            this.milesMinimum = 50000;
            this.milesMaximum = 80000;
            this.yearMinimum = 2008;
        } else if (this.model.equals("jeep") || this.model.equals("jw")
                || this.model.equals("jeep wrangler") || this.model.equals("wrangler")) {
            //search criteria for mercedes-benz
            this.model = "wrangler";
            this.priceMinimum = 3000;
            this.priceMaximum = 8000;
            this.milesMinimum = 50000;
            this.milesMaximum = 110000;
            this.yearMinimum = 1998;
        }
    }

    /**
     * Sets this Search's model to the given model
     *
     * @param model the new model
     */
    public void setModel(String model) {
        this.model = model.toLowerCase();
        this.adjustSearchParameters();
    }

    /**
     * Sets this Search's minimum price to the given price
     *
     * @param priceMinimum the new minimum price
     */
    public void setPriceMinimum(int priceMinimum) {
        this.priceMinimum = priceMinimum;
    }

    /**
     * Sets this Search's maximum price to the given price
     *
     * @param priceMaximum the new maximum price
     */
    public void setPriceMaximum(int priceMaximum) {
        this.priceMaximum = priceMaximum;
    }

    /**
     * Sets this Search's minimum miles to the given value
     * @param milesMinimum the new number of minimum miles
     */
    public void setMilesMinimum(int milesMinimum) {
        this.milesMinimum = milesMinimum;
    }

    /**
     * Sets this Search's maximum miles to the given value
     *
     * @param milesMaximum the new number of maximum miles
     */
    public void setMilesMaximum(int milesMaximum) {
        this.milesMaximum = milesMaximum;
    }

    /**
     * Sets this Search's minimum year to the given value
     *
     * @param yearMinimum the new minimum year
     */
    public void setYearMinimum(int yearMinimum) {
        this.yearMinimum = yearMinimum;
    }

    /**
     * Returns this Search's model
     *
     * @return the model
     */
    public String getModel() {
        return this.model;
    }

    /**
     * Returns this Search's minimum price
     *
     * @return the minimum price
     */
    public int getPriceMinimum() {
        return this.priceMinimum;
    }

    /**
     * Returns this Search's maximum price
     *
     * @return the maximum price
     */
    public int getPriceMaximum() {
        return this.priceMaximum;
    }

    /**
     * Returns this Search's minimum number of miles
     *
     * @return the minimum number of miles
     */
    public int getMilesMinimum() {
        return this.milesMinimum;
    }

    /**
     * Returns this Search's maximum number of miles
     *
     * @return the maximum number of miles
     */
    public int getMilesMaximum() {
        return this.milesMaximum;
    }

    /**
     * Returns this Search's minimum year
     *
     * @return the minimum year
     */
    public int getYearMinimum() {
        return this.yearMinimum;
    }
}
