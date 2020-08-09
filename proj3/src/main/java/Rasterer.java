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
        boolean success = true;

        // reads input
        double lrlon  = params.get("lrlon");
        double ullon  = params.get("ullon");
        double ullat  = params.get("ullat");
        double lrlat  = params.get("lrlat");

        // test validate
        if (ullon >= lrlon || lrlat >= ullat ||
            ullon >= MapServer.ROOT_LRLON    ||
            ullat <= MapServer.ROOT_LRLAT    ||
            lrlon <= MapServer.ROOT_ULLON    ||
            lrlat >= MapServer.ROOT_ULLAT) {
            results.put("render_grid", null);
            results.put("raster_ul_lon", 0.0);
            results.put("raster_ul_lat", 0.0);
            results.put("raster_lr_lon", 0.0);
            results.put("raster_lr_lat", 0.0);
            results.put("depth", 0);
            results.put("query_success", false);
        } else {
            // calculation
            int d = depthLevel(params);

            int[] ulId = getUpperLeftValue(ullon, ullat, d);
            int[] lrId = getLowerRightValue(lrlon, lrlat, d);
            String[][] test = new String[lrId[1] - ulId[1] + 1][lrId[0] - ulId[0] + 1];
            for (int i = ulId[0]; i <= lrId[0]; i += 1) {
                for (int j = ulId[1]; j <= lrId[1]; j += 1) {
                    test[j - ulId[1]][i - ulId[0]] = "d" + d + "_x" + i + "_y" + j + ".png";
                }
            }

            double[] raul = raterUpperLeftCorner(ulId[0], ulId[1], d, false);
            double[] ralr = raterUpperLeftCorner(lrId[0], lrId[1], d, true);
            double ras_ul_lon = raul[0];
            double ras_ul_lat = raul[1];
            double ras_lr_lon = ralr[0];
            double ras_lr_lat = ralr[1];

            results.put("render_grid", test);
            results.put("raster_ul_lon", ras_ul_lon);
            results.put("raster_ul_lat", ras_ul_lat);
            results.put("raster_lr_lon", ras_lr_lon);
            results.put("raster_lr_lat", ras_lr_lat);
            results.put("depth", d);
            results.put("query_success", true);
        }
        return results;
    }

    /**
     * @param lon longitude of the upper left corner of the whole picture
     * @param lat latitude of the upper left corner of the whole picture
     * @return int[x, y] of the upper left corner
     */
    private int[] getUpperLeftValue(double lon, double lat, int dep) {
        int x; int y;
        if (lon <= MapServer.ROOT_ULLON) {
            x = 0;
        } else {
            x = getULXValue(lon, dep);
        }

        if (lat >= MapServer.ROOT_ULLAT) {
            y = 0;
        } else {
            y = getULYValue(lat, dep);
        }
        return new int[]{x, y};
    }

    /**
     * @param lon longitude of the lower right corner of the whole picture
     * @param lat latitude of the lower right corner of the whole picture
     * @return int[x, y] of the lower right corner
     */
    private int[] getLowerRightValue(double lon, double lat, int dep) {
        int x; int y;
        if (lon >= MapServer.ROOT_LRLON) {
            x = dep;
        } else {
            x = getULXValue(lon, dep);
        }

        if (lat <= MapServer.ROOT_LRLAT) {
            y = dep;
        } else {
            y = getULYValue(lat, dep);
        }
        return new int[]{x, y};
    }

    private double[] raterUpperLeftCorner(int x, int y, int d, boolean lr) {
        double gapLon = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) * x / Math.pow(2, d);
        double gapLat = (MapServer.ROOT_LRLAT - MapServer.ROOT_ULLAT) * y/ Math.pow(2, d);
        double resLon = MapServer.ROOT_ULLON + gapLon;
        double resLat = MapServer.ROOT_ULLAT + gapLat;

        if (lr) {
            resLon += gapLon / x;
            resLat += gapLat / y;
        }
        return new double[]{resLon, resLat};
    }

    private int getULXValue(double lon, int dep) {
        double[] boundaries = new double[(int) Math.pow(2, dep) + 1];
        boundaries[0] = MapServer.ROOT_ULLON;
        for (int i = 1; i < boundaries.length; i += 1) {
            double gap = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON)
                    * i / Math.pow(2, dep);
            boundaries[i] = MapServer.ROOT_ULLON + gap;
            if (boundaries[i - 1] < lon && lon <= boundaries[i]) {
                return i - 1;
            }
        }
        return -1;
    }

    private int getULYValue(double lat, int dep) {
        double[] boundaries = new double[(int) Math.pow(2, dep) + 1];
        boundaries[0] = MapServer.ROOT_ULLAT;
        for (int i = 1; i < boundaries.length; i += 1) {
            double gap = (MapServer.ROOT_LRLAT - MapServer.ROOT_ULLAT)
                    * i / Math.pow(2, dep);
            boundaries[i] = MapServer.ROOT_ULLAT + gap;
            if (boundaries[i - 1] >= lat && lat > boundaries[i]) {
                return i - 1;
            }
        }
        return -1;
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
