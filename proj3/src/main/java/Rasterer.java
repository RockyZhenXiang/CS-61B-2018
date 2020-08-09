import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {

    public Rasterer() {
        // YOUR CODE HERE

    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        System.out.println(params);
        Map<String, Object> results = new HashMap<>();

        // reads input
        double lrlon  = params.get("lrlon");
        double ullon  = params.get("ullon");
        double w  = params.get("w");
        double h  = params.get("h");
        double ullat  = params.get("ullat");
        double lrlat  = params.get("lrlat");

        int d = depthLevel(params);

        int[] upperLeftIndex = getUpperLeftFigure(ullon, ullat, d);
        String ul = "d" + d + "_x" + upperLeftIndex[0] + "_y" + upperLeftIndex[1] + ".png";
        String[][] test = new String[][]{{ul}};

        results.put("render_grid", test);
        results.put("raster_ul_lon", ullon);
        results.put("raster_ul_lat", ullat);
        results.put("raster_lr_lon", lrlon);
        results.put("raster_lr_lat", lrlat);
        results.put("depth", d);
        results.put("query_success", true);

        return results;
    }

    /**
     * @param lon longitude of the upper left corner of the whole picture
     * @param lat latitude of the upper left corner of the whole picture
     * @return int[x, y] of the upper left corner
     */
    private int[] getUpperLeftFigure(double lon, double lat, int dep) {
        int x = getXValue(lon, dep);
        int y = getYValue(lat, dep);
        if (x == -1 || y == -1) {
            throw new RuntimeException("getUpperLeftFigure() has a bug, " +
                    "x = " + x + " y = " + y);
        }
        return new int[]{x, y};
    }

    private int getXValue(double lon, int dep) {
        double[] boundaries = new double[(int) Math.pow(2, dep) + 1];
        boundaries[0] = MapServer.ROOT_ULLON;
        for (int i = 1; i < boundaries.length + 1; i += 1) {
            double gap = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON)
                    * i / Math.pow(2, dep);
            boundaries[i] = MapServer.ROOT_ULLON + gap;
            if (boundaries[i - 1] < lon && lon <= boundaries[i]) {
                return i - 1;
            }
        }

        return -1;
    }

    private int getYValue(double lat, int dep) {

        return 0;
    }

    private int depthLevel(Map<String, Double> params) {

        double lrlon  = params.get("lrlon");
        double ullon  = params.get("ullon");
        double w  = params.get("w");

        double expectedLonDPP = (lrlon - ullon) / w;
        // From d0_x0_y0.png
        double currentLonDpp = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / MapServer.TILE_SIZE;
        int currentDepth = 0;
        while (currentLonDpp > expectedLonDPP && currentDepth < 7) {
            currentLonDpp /= 2;
            currentDepth += 1;
        }
        return currentDepth;
    }

}
