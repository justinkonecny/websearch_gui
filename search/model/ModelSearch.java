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

public class ModelSearch implements IModelSearch {

    private List<Advertisement> advertisements;
    private int oldestPostAge;

    public ModelSearch() {
        this.advertisements = new ArrayList<Advertisement>();
        this.oldestPostAge = 14;
    }

    @Override
    public List<Advertisement> executeSearch(Search search) throws IOException {
        System.out.println(String.format("[Search Parameters]: [model: %s], [min year: %d]", search.getModel(), search.getYearMinimum()));
        System.out.println(String.format("  [min price: %d], [max price: %d], [min miles: %d], [max miles: %d]",
                search.getPriceMinimum(), search.getPriceMaximum(), search.getMilesMinimum(), search.getMilesMaximum()));
        System.out.println("========================================");

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
                Advertisement advertisement = new Advertisement();
                String adTitle = this.getCapitalized(adHtml.getElementsByTag("p").select("a[href^=\"http\"]").text());

                String[] adDate = adHtml.getElementsByTag("time").attr("datetime").split(" ")[0].split("-");
                int adAge = this.getTimeDelta(Integer.valueOf(adDate[0]),
                        Integer.valueOf(adDate[1]), Integer.valueOf(adDate[2]));
                String adLink = adHtml.getElementsByTag("a").attr("href");
                String adLocation = adLink.split("\\.")[0].split("//")[1];

                if (adAge <= this.oldestPostAge && adLocation != "newyork" && adLocation != "philadelphia") {
                    countValid++;
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

                    advertisement.setTitle(adTitle);
                    advertisement.setLocation(adLocation);
                    advertisement.setAge(adAge);
                    advertisement.setLink(adLink);
                    advertisement.setAttributes(adAttr);
                    advertisement.setImages(adImageList);
                    advertisement.setBody(adBody);

                    this.advertisements.add(advertisement);

                    boolean flag = false;
                    if (flag) {
                        break;
                    }
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

        return this.advertisements;
    }

    private int getTimeDelta(int year, int month, int day) {
        LocalDate then = LocalDate.of(year, month, day);
        LocalDate now = LocalDateTime.now().toLocalDate();
        Period period = Period.between(then, now);
        return period.getDays();
    }

    private void removeDuplicates(List<Advertisement> listAds) {
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

    private String getCapitalized(String str) {
        String cap = str.substring(0, 1).toUpperCase() + str.substring(1);
        for (int i = 1; i < cap.length(); i++) {
            if (cap.charAt(i) == ' ') {
                cap = cap.substring(0, i + 1) + cap.substring(i + 1, i + 2).toUpperCase() + cap.substring(i + 2);
            }
        }
        return cap;
    }
}
