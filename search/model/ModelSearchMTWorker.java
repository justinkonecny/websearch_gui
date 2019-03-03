package search.model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import search.Advertisement;
import search.util.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ModelSearchMTWorker extends Thread {
    //the list of advertisements to append to
    private List<Advertisement> listAdvertisement;
    //the search location
    private final String searchLocation;
    //the search URL
    private final String searchLink;
    //the oldest post age
    private final int oldestPostAge;

    /**
     * Constructs one thread as worker dedicated to parsing one search location.
     * @param searchLocation
     * @param searchLink
     * @param oldestPostAge
     */
    public ModelSearchMTWorker(String searchLocation, String searchLink, int oldestPostAge) {
        this.listAdvertisement = new ArrayList<Advertisement>();
        this.searchLocation = searchLocation;
        this.searchLink = searchLink;
        this.oldestPostAge = oldestPostAge;
    }

    /**
     * Runs this thread worker, connecting to one location link and executing the search for that location.
     */
    @Override
    public void run() {
        try {
            System.out.println("[Parsing]: " + this.searchLocation.toUpperCase());
            Document doc = Jsoup.connect(this.searchLink).get();
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
                    this.listAdvertisement.add(advertisement);

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Advertisement> getListAdvertisement() {
        return this.listAdvertisement;
    }
}
