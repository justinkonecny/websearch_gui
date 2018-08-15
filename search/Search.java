package search;

import java.util.HashMap;
import java.util.Map;

public class Search {
    private String model;
    private int priceMinimum;
    private int priceMaximum;
    private int milesMinimum;
    private int milesMaximum;
    private int yearMinimum;

    public Search() {
        this("bmw", 5000, 9000, 50000, 85000, 2008);
        this.adjustSearchParameters();
    }

    public Search(String model, int priceMinimum, int priceMaximum, int milesMinimum, int milesMaximum, int yearMinimum) {
        this.model = model.toLowerCase();
        this.priceMinimum = priceMinimum;
        this.priceMaximum = priceMaximum;
        this.milesMinimum = milesMinimum;
        this.milesMaximum = milesMaximum;
        this.yearMinimum = yearMinimum;
    }

    public Map<String, String> getSearchLinks() {
        // northern new jersey search link
        String url_nnj = String.format("https://newjersey.craigslist.org/search/cto?auto_transmission=2&hasPic=1&min_price=%s"
                        + "&max_auto_miles=%s&max_price=%s&auto_make_model=%s&min_auto_miles=%s"
                        + "&min_auto_year=%s&auto_title_status=1", String.valueOf(this.priceMinimum),
                String.valueOf(this.milesMaximum), String.valueOf(this.priceMaximum), this.model,
                String.valueOf(this.milesMinimum), String.valueOf(this.yearMinimum));

        // central new jersey search link
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

        // map of search urls
        Map<String, String> urls = new HashMap<String, String>();
        urls.put("nnj", url_nnj);
        urls.put("cnj", url_cnj);
        urls.put("snj", url_snj);

        return urls;
    }

    private void adjustSearchParameters() {
        if (this.model.equals("bmw") || this.model.equals("bimmer")) {
            // search criteria for bmw
            this.model = "bmw";
            this.priceMinimum = 5000;
            this.priceMaximum = 9000;
            this.milesMinimum = 50000;
            this.milesMaximum = 80000;
            this.yearMinimum = 2009;
        } else if (this.model.equals("mercedes") || this.model.equals("mb") || this.model.equals("mercedes benz") || this.model.equals("benz")) {
            // search criteria for mercedes-benz
            this.model = "mercedes";
            this.priceMinimum = 6000;
            this.priceMaximum = 14000;
            this.milesMinimum = 50000;
            this.milesMaximum = 80000;
            this.yearMinimum = 2008;
        } else if (this.model.equals("jeep") || this.model.equals("jw") || this.model.equals("jeep wrangler") || this.model.equals("wrangler")) {
            // search criteria for mercedes-benz
            this.model = "wrangler";
            this.priceMinimum = 3000;
            this.priceMaximum = 8000;
            this.milesMinimum = 50000;
            this.milesMaximum = 110000;
            this.yearMinimum = 1998;
        }
    }

    public void setModel(String model) {
        this.model = model.toLowerCase();
        this.adjustSearchParameters();
    }

    public void setPriceMinimum(int priceMinimum) {
        this.priceMinimum = priceMinimum;
    }

    public void setPriceMaximum(int priceMaximum) {
        this.priceMaximum = priceMaximum;
    }

    public void setMilesMinimum(int milesMinimum) {
        this.milesMinimum = milesMinimum;
    }

    public void setMilesMaximum(int milesMaximum) {
        this.milesMaximum = milesMaximum;
    }

    public void setYearMinimum(int yearMinimum) {
        this.yearMinimum = yearMinimum;
    }

    public String getModel() {
        return this.model;
    }

    public int getPriceMinimum() {
        return this.priceMinimum;
    }

    public int getPriceMaximum() {
        return this.priceMaximum;
    }

    public int getMilesMinimum() {
        return this.milesMinimum;
    }

    public int getMilesMaximum() {
        return this.milesMaximum;
    }

    public int getYearMinimum() {
        return this.yearMinimum;
    }
}
