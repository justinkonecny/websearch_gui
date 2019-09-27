package search.model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import search.Advertisement;
import search.Search;
import search.util.Util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractModelSearch implements IModelSearch {
    //the list of Advertisements processes from the search
    protected List<Advertisement> advertisements;
    //the oldest acceptable post to include in the results
    protected int oldestPostAge;
    //list of 'known' models to search through for a search of '$all'
    protected List<String> knownModels;
    //list of 'suv' models to search through for a search of '$suv'
    protected List<String> suvModels;

    public AbstractModelSearch() {
        this.advertisements = new ArrayList<Advertisement>();
        this.knownModels = new ArrayList<String>(Arrays.asList("bmw", "mercedes", "wrangler"));
        this.suvModels = new ArrayList<String>(Arrays.asList("mercedes", "infiniti", "lexus"));
        this.oldestPostAge = 14;
    }

    /**
     * Executes the given Search and returns the resulting listings, checking for the selected model.
     *
     * @param search the Search containing all parameters desired in the results
     * @return the list of Advertisements found matching the search parameters
     * @throws IOException              if the search URLS cannot be opened or the web page cannot be accessed
     * @throws IllegalArgumentException if the given Search is null
     */
    @Override
    public List<Advertisement> executeSearch(Search search) throws IOException, IllegalArgumentException {
        this.advertisements = new ArrayList<Advertisement>();
        if (search.getModel().toLowerCase().equals("$all")) {
            //test for a shortcut to test for a set of 'standard' models
            this.searchList(this.knownModels, search);
        } else if (search.getModel().toLowerCase().equals("$suv")) {
            //test for a shortcut to search for a set of SUVs
            this.searchList(this.suvModels, search);
        } else {
            this.advertisements = this.conductSearch(search);
        }

        return this.advertisements;
    }

    /**
     * Searches all models in the given list with the criteria of the given Search.
     *
     * @param listModels the list of String representing all models to search for
     * @param search     the search with search options
     * @throws IOException if the given Search is null
     */
    protected void searchList(List<String> listModels, Search search) throws IOException {
        for (String model : listModels) {
            search.setModel(model);
            List<Advertisement> adList = this.conductSearch(search);
            this.advertisements.addAll(adList);
        }
        System.out.println("[All Found]: " + this.advertisements.size());
        System.out.println("========================================");
        search.setModel("bmw");
    }

    /**
     * Connects to the URL of each Advertisement to save the listing images and attributes.
     *
     * @param getAllImages whether or not to processes all advertisement images or just the first
     * @throws IOException if the advertisement link cannot be connect to
     */
    protected void populateAdvertisements(boolean getAllImages) throws IOException {
        for (Advertisement advertisement : this.advertisements) {
            populateAdvertisement(advertisement, getAllImages);
        }
        System.out.println("========================================");
    }

    /**
     * Connects to the URL of one Advertisement to save the listing images and attributes.
     *
     * @param getAllImages whether or not to processes all advertisement images or just the first
     * @throws IOException if the advertisement link cannot be connect to
     */
    protected void populateAdvertisement(Advertisement advertisement, boolean getAllImages) throws IOException {
        Document adSoup = getAdvertisementSoup(advertisement);
        Elements adThumbs = adSoup.getElementsByTag("a").select(".thumb");
        List<Image> adImageList = new ArrayList<Image>();

        for (Element thumb : adThumbs) {
            Image img = this.getImage(thumb);
            if (img != null) {
                adImageList.add(img);
            }
            if (!getAllImages) {
                break;
            }
        }

        Element adBodyHtml = adSoup.getElementsByTag("section").select("[id=postingbody]").first();
        String adBody = adBodyHtml.ownText();
        Elements attrs = adSoup.getElementsByTag("p").select(".attrgroup");
        String adAttr = "";

        for (Element attr : attrs) {
            Elements attrChildrenItr = attr.children().select("span");

            for (Element child : attrChildrenItr) {
                adAttr += (Util.getCapitalized(child.text()) + System.lineSeparator());
            }
        }

        advertisement.setAttributes(adAttr);
        advertisement.setImages(adImageList);
        advertisement.setBody(adBody);
    }

    /**
     * Given an element, returns the embedded image.
     *
     * @param thumb the element to search
     * @return the image, or null of none exsits
     */
    protected Image getImage(Element thumb) {
        try {
            URL thumbUrl = new URL(thumb.attr("href"));
            return ImageIO.read(thumbUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns a list of all images for this advertisement.
     *
     * @param advertisement the advertisement to get images from
     * @return the list of images
     * @throws IOException if the connection to the ad URL cannot be made
     */
    @Override
    public List<Image> getAdImages(Advertisement advertisement) throws IOException {
        Elements adThumbs = getAdvertisementSoup(advertisement).getElementsByTag("a").select(".thumb");
        List<Image> adImageList = new ArrayList<Image>();

        for (Element thumb : adThumbs) {
            Image img = this.getImage(thumb);
            if (img != null) {
                adImageList.add(img);
            }
        }
        return adImageList;
    }

    /**
     * Removes duplicate Advertisements (based on matching title) from the given list.
     *
     * @param listAds the list of Advertisements to remove duplicates from
     */
    protected void removeDuplicates(List<Advertisement> listAds) {
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
            System.out.println("[Total Listings Remaining]: " + listAds.size());
            System.out.println("========================================");
        }
    }

    protected Document getAdvertisementSoup(Advertisement advertisement) throws IOException {
        System.out.println("[Processing]: " + advertisement.getTitle());
        String adLink = advertisement.getLink();
        return Jsoup.connect(adLink).get();
    }

    protected abstract List<Advertisement> conductSearch(Search search) throws IOException, IllegalArgumentException;
}
