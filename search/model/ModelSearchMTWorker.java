package search.model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import search.Advertisement;
import search.util.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ModelSearchMTWorker extends Thread {
    //the model
    private final ModelSearchMT model;
    //the list of advertisements to append to
    private final List<Advertisement> LIST_ADVERTISEMENT;
    //the search location
    private final String SEARCH_LOCATION;
    //the search URL
    private final String SEARCH_LINK;
    //the oldest post age
    private final int OLDEST_POST_AGE;

    /**
     * Constructs one thread as worker dedicated to parsing one search location.
     *
     * @param searchLocation the name of the location to search
     * @param searchLink     the link to search
     * @param oldestPostAge  the oldest a post can be in order to be accepted
     */
    public ModelSearchMTWorker(ModelSearchMT model, String searchLocation, String searchLink, int oldestPostAge) {
        this.model = model;
        this.LIST_ADVERTISEMENT = new ArrayList<Advertisement>();
        this.SEARCH_LOCATION = searchLocation;
        this.SEARCH_LINK = searchLink;
        this.OLDEST_POST_AGE = oldestPostAge;
    }

    /**
     * Runs this thread worker, connecting to one location link and executing the search for that location.
     */
    @Override
    public void run() {
        try {
            System.out.println("[Parsing]: " + ModelSearchMTWorker.this.SEARCH_LOCATION.toUpperCase());
            Document doc = Jsoup.connect(ModelSearchMTWorker.this.SEARCH_LINK).get();
            Elements listHtml = doc.getElementsByTag("ul").select(".rows").select("li");

            int threadCount = listHtml.size();
            AdvertisementLoaderThread[] loaderThreads = new AdvertisementLoaderThread[threadCount];

            int countValid = 0;
            for (int i = 0; i < threadCount; i++) {
                loaderThreads[i] = new AdvertisementLoaderThread(listHtml.get(i));
                loaderThreads[i].start();
            }

            for (int i = 0; i < threadCount; i++) {
                loaderThreads[i].join();
                countValid += loaderThreads[i].getCountValid();
            }

            System.out.println("[Count Valid]: " + countValid);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Advertisement> getListAdvertisement() {
        return this.LIST_ADVERTISEMENT;
    }

    private class AdvertisementLoaderThread extends Thread {
        private Element adHtml;
        private int countValid;

        AdvertisementLoaderThread(Element adHtml) {
            this.adHtml = adHtml;
            this.countValid = 0;
        }

        @Override
        public void run() {
            String adTitle = Util.getCapitalized(adHtml.getElementsByTag("p").select("a[href^=\"http\"]").text());

            String[] adDate = adHtml.getElementsByTag("time").attr("datetime").split(" ")[0].split("-");
            int adAge = Util.getTimeDelta(Integer.valueOf(adDate[0]), Integer.valueOf(adDate[1]), Integer.valueOf(adDate[2]));

            String adLink = adHtml.getElementsByTag("a").attr("href");
            String adLocation = adLink.split("\\.")[0].split("//")[1];
            String adPrice = adHtml.select("span.result-price").first().text().substring(1);

            if (adAge <= ModelSearchMTWorker.this.OLDEST_POST_AGE && !adLocation.equals("newyork") && !adLocation.equals("philadelphia")) {
                this.countValid++;
                Advertisement advertisement = new Advertisement();
                advertisement.setTitle(adTitle);
                advertisement.setLocation(adLocation);
                advertisement.setAge(adAge);
                advertisement.setLink(adLink);

                try {
                    ModelSearchMTWorker.this.model.populateAdvertisement(advertisement, true);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                synchronized (ModelSearchMTWorker.this.LIST_ADVERTISEMENT) {
                    ModelSearchMTWorker.this.LIST_ADVERTISEMENT.add(advertisement);
                }

                System.out.println(advertisement.getTitle());

                try {
                    advertisement.setPrice(Integer.valueOf(adPrice));
                } catch (NumberFormatException e) {
                    System.out.println("[Could not get price for]: " + adTitle);
                }
            }
        }

        public int getCountValid() {
            return this.countValid;
        }
    }
}
