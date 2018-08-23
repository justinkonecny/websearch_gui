package search.model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import search.Advertisement;
import search.Search;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Represents the model implementation to execute searches and parse the results.
 */
public class ModelSearch implements IModelSearch {
    //the list of Advertisements processes from the search
    private List<Advertisement> advertisements;
    //the oldest acceptable post to include in the results
    private int oldestPostAge;

    /**
     * Constructs the model with an empty list of Advertisements and sets the oldest
     * acceptable posting age to fourteen days.
     */
    public ModelSearch() {
        this.advertisements = new ArrayList<Advertisement>();
        this.oldestPostAge = 14;
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
    public List<Advertisement> executeSearch(Search search) throws IOException, IllegalArgumentException {
        if (search == null) {
            throw new IllegalArgumentException("Search cannot be null");
        }

        System.out.println(String.format("[Search Parameters]: [model: %s], [min year: %d]", search.getModel(), search.getYearMinimum()));
        System.out.println(String.format("  [min price: %d], [max price: %d], [min miles: %d], [max miles: %d]",
                search.getPriceMinimum(), search.getPriceMaximum(), search.getMilesMinimum(), search.getMilesMaximum()));
        System.out.println("========================================");

        this.advertisements = new ArrayList<Advertisement>();

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
                String adTitle = this.getCapitalized(adHtml.getElementsByTag("p").select("a[href^=\"http\"]").text());
                String[] adDate = adHtml.getElementsByTag("time").attr("datetime").split(" ")[0].split("-");
                int adAge = this.getTimeDelta(Integer.valueOf(adDate[0]),
                        Integer.valueOf(adDate[1]), Integer.valueOf(adDate[2]));
                String adLink = adHtml.getElementsByTag("a").attr("href");
                String adLocation = adLink.split("\\.")[0].split("//")[1];

                if (adAge <= this.oldestPostAge && adLocation != "newyork" && adLocation != "philadelphia") {
                    countValid++;
                    Advertisement advertisement = new Advertisement();
                    advertisement.setTitle(adTitle);
                    advertisement.setLocation(adLocation);
                    advertisement.setAge(adAge);
                    advertisement.setLink(adLink);
                    this.advertisements.add(advertisement);
                }
                countTotal++;
            }

            System.out.println("[Total Listings Found]: " + countTotal);
            System.out.println("[Valid Listings Found]: " + countValid);
            System.out.println("========================================");
        }

        System.out.println("[Combined Total Found]: " + this.advertisements.size());
        System.out.println("========================================");

        this.removeDuplicates(this.advertisements);
        this.populateAdvertisements();

        return this.advertisements;
    }

    /**
     * Connects to the URL of each Advertisement to save the listing images and attributes.
     *
     * @throws IOException if the advertisement link cannot be connect to
     */
    private void populateAdvertisements() throws IOException {
        for (Advertisement advertisement : this.advertisements) {
            String adLink = advertisement.getLink();
            Document adSoup = Jsoup.connect(adLink).get();
            List<Image> adImageList = new ArrayList<Image>();
            Elements adThumbs = adSoup.getElementsByTag("a").select(".thumb");
            for (Element thumb : adThumbs) {
                URL thumbUrl = new URL(thumb.attr("href"));
                try {
                    Image image = ImageIO.read(thumbUrl);
                    adImageList.add(image);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Element adBodyHtml = adSoup.getElementsByTag("section").select("[id=postingbody]").first();
            String adBody = adBodyHtml.ownText();
            Elements attrs = adSoup.getElementsByTag("p").select(".attrgroup");
            String adAttr = "";
            Iterator<Element> attrItr = attrs.iterator();
            while (attrItr.hasNext()) {
                Element attr = attrItr.next();
                Iterator<Element> attrChildrenItr = attr.children().select("span").iterator();
                while (attrChildrenItr.hasNext()) {
                    Element child = attrChildrenItr.next();
                    adAttr += (this.getCapitalized(child.text()) + System.lineSeparator());
                }
            }
            advertisement.setAttributes(adAttr);
            advertisement.setImages(adImageList);
            advertisement.setBody(adBody);
        }
    }

    /**
     * Calculates the time difference (in days) between now and the given date.
     *
     * @param year the date's year
     * @param month the date's month
     * @param day the date's day
     * @return the difference in time (in days)
     */
    private int getTimeDelta(int year, int month, int day) {
        LocalDate then = LocalDate.of(year, month, day);
        LocalDate now = LocalDateTime.now().toLocalDate();
        Period period = Period.between(then, now);
        return period.getDays();
    }

    /**
     * Removes duplicate Advertisements (based on matching title) from the given list.
     *
     * @param listAds the list of Advertisements to remove duplicates from
     */
    private void removeDuplicates(List<Advertisement> listAds) {
        if (listAds != null) {
            System.out.println("[Removing Duplicate Listings]");
            List<Advertisement> listOrig = new ArrayList<Advertisement>(listAds);
            List<String> listUnique = new ArrayList<String>();

            for (int i = 0; i < listOrig.size(); i++) {
                if (listUnique.contains(listOrig.get(i).getTitle().toLowerCase())) {
                    listAds.remove(listOrig.get(i));
                } else {
                    listUnique.add(listOrig.get(i).getTitle().toLowerCase());
                }
            }
            System.out.println("[Total Listings Remaining]: " + this.advertisements.size());
            System.out.println("========================================");
        }
    }

    /**
     * Returns the given String with all words capitalized.
     *
     * @param str the String to capitalize
     * @return the capitalized String
     */
    private String getCapitalized(String str) {
        if (str != null && !str.equals("")) {
            str = str.substring(0, 1).toUpperCase() + str.substring(1);
            for (int i = 1; i < str.length(); i++) {
                if (str.charAt(i) == ' ') {
                    str = str.substring(0, i + 1) + str.substring(i + 1, i + 2).toUpperCase() + str.substring(i + 2);
                }
            }
        }
        return str;
    }
}
