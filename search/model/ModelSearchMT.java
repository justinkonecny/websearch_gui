package search.model;

import search.Advertisement;
import search.Search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents the model implementation to execute searches and parse the results.
 */
public class ModelSearchMT extends AbstractModelSearch {

    /**
     * Constructs the model with an empty list of Advertisements and sets the oldest
     * acceptable posting age to fourteen days.
     */
    public ModelSearchMT() {
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

        List<Advertisement> listAdvertisement = new ArrayList<Advertisement>();

        try {
            System.out.println(String.format("[Search Parameters]: [model: %s], [min year: %d]", search.getModel(), search.getYearMinimum()));
            System.out.println(String.format("  [min price: %d], [max price: %d], [min miles: %d], [max miles: %d]",
                    search.getPriceMinimum(), search.getPriceMaximum(), search.getMilesMinimum(), search.getMilesMaximum()));
            System.out.println("========================================");

            //one thread for each search location
            int threadCount = search.getSearchLinks().size();
            ModelSearchMTWorker[] threadLocation = new ModelSearchMTWorker[threadCount];
            int counter = 0;

            for (Map.Entry<String, String> entry : search.getSearchLinks().entrySet()) {
                //create each thread
                threadLocation[counter] = new ModelSearchMTWorker(entry.getKey(), entry.getValue(), this.oldestPostAge);
                threadLocation[counter].start();
                System.out.print("START: " + counter);
                counter++;
            }

            for (int i = 0; i < threadCount; i++) {
                threadLocation[i].join();
                List<Advertisement> result = threadLocation[i].getListAdvertisement();
                listAdvertisement.addAll(result);
                System.out.println("FINISHED: " + i);
            }

            System.out.println("[Combined Total Found]: " + listAdvertisement.size());
            System.out.println("========================================");

            this.removeDuplicates(listAdvertisement);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        return listAdvertisement;
    }
}
