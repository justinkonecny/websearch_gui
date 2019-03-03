package search.model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import search.Advertisement;
import search.Search;
import search.util.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Represents the model implementation to execute searches and parse the results.
 */
public class ModelSearchST extends AbstractModelSearch {

    /**
     * Constructs the model with an empty list of Advertisements and sets the oldest
     * acceptable posting age to fourteen days.
     */
    public ModelSearchST() {
        super();
    }

    /**
     * Executes the given Search and returns the resulting listings.
     *
     * @param search the Search containing all parameters desired in the results
     * @return the list of Advertisements found matching the search parameters
     * @throws IOException if the search URLS cannot be opened or the web page cannot be accessed
     * @throws IllegalArgumentException if the given Search is null
     */
    @Override
    protected List<Advertisement> conductSearch(Search search) throws IOException, IllegalArgumentException {
        if (search == null) {
            throw new IllegalArgumentException("Search cannot be null");
        }

        System.out.println(String.format("[Search Parameters]: [model: %s], [min year: %d]", search.getModel(), search.getYearMinimum()));
        System.out.println(String.format("  [min price: %d], [max price: %d], [min miles: %d], [max miles: %d]",
                search.getPriceMinimum(), search.getPriceMaximum(), search.getMilesMinimum(), search.getMilesMaximum()));
        System.out.println("========================================");

        List<Advertisement> listAdvertisement = new ArrayList<Advertisement>();

        for (Map.Entry<String, String> entry : search.getSearchLinks().entrySet()) {
            String searchLocation = entry.getKey();
            String searchLink = entry.getValue();

            System.out.println("[Parsing]: " + search.getModel().toUpperCase() + " " + searchLocation.toUpperCase());
            Document doc = Jsoup.connect(searchLink).get();
            Elements listHtml = doc.getElementsByTag("ul").select(".rows");
            listHtml = listHtml.select("li");
            int countTotal = 0;
            int countValid = 0;

            Iterator<Element> listIter = listHtml.iterator();
            while (listIter.hasNext()) {
                Element adHtml = listIter.next();
                String adTitle = Util.getCapitalized(adHtml.getElementsByTag("p").select("a[href^=\"http\"]").text());
                String[] adDate = adHtml.getElementsByTag("time").attr("datetime").split(" ")[0].split("-");
                int adAge = Util.getTimeDelta(Integer.valueOf(adDate[0]),
                        Integer.valueOf(adDate[1]), Integer.valueOf(adDate[2]));
                String adLink = adHtml.getElementsByTag("a").attr("href");
                String adLocation = adLink.split("\\.")[0].split("//")[1];
                String adPrice = adHtml.select("span.result-price").first().text().substring(1);

                if (adAge <= this.oldestPostAge && !adLocation.equals("newyork") && !adLocation.equals("philadelphia")) {
                    countValid++;
                    Advertisement advertisement = new Advertisement();
                    advertisement.setTitle(adTitle);
                    advertisement.setLocation(adLocation);
                    advertisement.setAge(adAge);
                    advertisement.setLink(adLink);
                    listAdvertisement.add(advertisement);

                    try {
                        advertisement.setPrice(Integer.valueOf(adPrice));
                    } catch (NumberFormatException e) {
                        System.out.println("[Could not get price for]: " + adTitle);
                    }
                }
                countTotal++;
            }

            System.out.println("[Total Listings Found]: " + countTotal);
            System.out.println("[Valid Listings Found]: " + countValid);
            System.out.println("========================================");
        }

        System.out.println("[Combined Total Found]: " + listAdvertisement.size());
        System.out.println("========================================");

        this.removeDuplicates(listAdvertisement);

        return listAdvertisement;
    }
}
