import javax.security.auth.login.CredentialException;
import java.util.*;
import java.util.function.LongConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class provides a shortestPath method for finding routes between two points
 * on the map. Start by using Dijkstra's, and if your code isn't fast enough for your
 * satisfaction (or the autograder), upgrade your implementation by switching it to A*.
 * Your code will probably not be fast enough to pass the autograder unless you use A*.
 * The difference between A* and Dijkstra's is only a couple of lines of code, and boils
 * down to the priority you use to order your vertices.
 */
public class Router {

    private static class Node implements Comparable{
        private long id;
        private double cost;

        public Node(long id, double cost) {
            this.id = id;
            this.cost = cost;
        }

        public long getId() {
            return id;
        }

        public double getCost() {
            return cost;
        }

        /**
         * returns 1 if this.cost is bigger then o.cost
         * returns -1 if this.cost is smaller then o.cost
         * returns 0 if this.cost == o.cost
         * if o is null, return 1
         * if o is from a different class, throw a exception
         */
        @Override
        public int compareTo(Object o) {
            if (o.getClass() != this.getClass()) {
                throw new RuntimeException("Cannot compare to different classes");
            }

            try{
                return Double.compare(cost, ((Node) o).cost);

            }catch (Exception e) {
                System.out.println("Class of o is" + o.getClass() + ", Class of this is Node");
                throw e;
            }
        }
    }


    /**
     * Return a List of longs representing the shortest path from the node
     * closest to a start location and the node closest to the destination
     * location.
     * @param g The graph to use.
     * @param stlon The longitude of the start location.
     * @param stlat The latitude of the start location.
     * @param destlon The longitude of the destination location.
     * @param destlat The latitude of the destination location.
     * @return A list of node id's in the order visited on the shortest path.
     */
    public static List<Long> shortestPath(GraphDB g, double stlon, double stlat,
                                          double destlon, double destlat) {
        long start = g.closest(stlon, stlat);
        long end = g.closest(destlon, destlat);

        return astar(g, start, end);
    }

    private static List<Long> astar(GraphDB g, long startId, long endId) {
        // initialing required data structures
        PriorityQueue<Node> fringe = new PriorityQueue<>(); // used to choose right node
        Map<Long, Double> bestDis = new HashMap<>(); // value is the distance from startId to key
        Map<Long, Long> parent = new HashMap<>(); // value is the parent of the key
        Set<Long> marked = new HashSet<>(); // when a node is checked, it is marked

        // storing initial values
        marked.add(startId);
        bestDis.put(startId, 0.0);
        parent.put(startId, startId);

        for (long nei: g.adjacent(startId)) {
            double dis = g.distance(startId, nei);
            double h = g.distance(nei, endId); 
            Node neiNode = new Node(nei, dis + h);
            fringe.add(neiNode);
            bestDis.put(nei, dis);
            parent.put(nei, startId);
        }

        // start route finding
        while (fringe.size() != 0) {
            Node currentNode = fringe.poll();
            if (marked.contains(currentNode.id)) {
                continue;
            }
            if (currentNode.id == endId) {
                return getParent(endId, parent);
            } else {
                long currentId = currentNode.id;
                marked.add(currentNode.id);
                for (long nei: g.adjacent(currentId)) {
                    double dis = g.distance(nei, currentId);
                    //relax
                    if (bestDis.containsKey(nei)) { // if nei is seen before
                        if (bestDis.get(nei) > bestDis.get(currentId) + dis) {
                            bestDis.put(nei, bestDis.get(currentId) + dis);
                            parent.put(nei, currentId);
                            fringe.add(new Node(nei, bestDis.get(currentId) + dis + g.distance(nei, endId)) );
                        }
                    } else {
                        bestDis.put(nei, bestDis.get(currentId) + dis);
                        parent.put(nei, currentId);
                        fringe.add(new Node(nei, bestDis.get(currentId) + dis + g.distance(nei, endId)));
                    }
                }
            }
        }

        System.out.println("Cannot find a path between startId and endId");
        return null;
    }

    private static List<Long> getParent(long endId, Map<Long, Long> parent) {
        List<Long> res = new ArrayList<>();
        long id = endId;
        while (true) {
            res.add(0, id);
            if (id == parent.get(id)) {
                break;
            } else {
                id = parent.get(id);
            }
        }

        return res;
    }


    /**
     * Create the list of directions corresponding to a route on the graph.
     * @param g The graph to use.
     * @param route The route to translate into directions. Each element
     *              corresponds to a node from the graph in the route.
     * @return A list of NavigatiionDirection objects corresponding to the input
     * route.
     */
    public static List<NavigationDirection> routeDirections(GraphDB g, List<Long> route) {
        List<NavigationDirection> res = new ArrayList<>();
        String wayName = g.getEdge().get(route.get(0)).get(route.get(1));
        double distance = 0;
        int direction = 0;

        // I am now at the second node of route
        for (int i = 1; i < route.size(); i += 1) {
            long current = route.get(i);
            long prev = route.get(i - 1);
            String passedRoadName = g.getEdge().get(current).get(prev);
            if (!passedRoadName.equals(wayName)) {
                NavigationDirection nd = new NavigationDirection();
                if (res.size() == 0) {
                    nd.direction = 0;
                } else {
                    nd.direction = direction;
                }
                nd.way = wayName;
                nd.distance = distance;
                res.add(nd);
                // finished adding one nd in the list

                // change variables
                distance = g.distance(prev, current);
                wayName = passedRoadName;
                direction =  relativeBearing(g, route, i);
            } else {
                distance += g.distance(prev, current);
            }

            if (i == route.size() - 1) { // if I am at the last node, add the last path
                NavigationDirection nd = new NavigationDirection();
                if (res.size() == 0) {
                    nd.direction = 0;
                } else {
                    nd.direction = direction;
                }
                nd.way = wayName;
                nd.distance = distance;
                res.add(nd);
            }
        }

        return res;
    }

    private static int relativeBearing(GraphDB g, List<Long> route, int i) {
        long prevprev = route.get(i - 2);
        long prev = route.get(i - 1);
        long current = route.get(i);
        double lastBearing = g.bearing(prevprev, prev);
        double thisBearing = g.bearing(prev, current);
        double reB = thisBearing - lastBearing;
        while (reB < -180) {
            reB += 360;
        }
        while (reB > 180) {
            reB -= 360;
        }
        return angle2Direction(reB);
    }
    private static int angle2Direction(double angle) {
        if (-15 <= angle && angle <= 15) {
            return 1; // straight
        } else if (-30 <= angle && angle < -15) {
            return 2; // slight left
        } else if (15 < angle && angle <= 30) {
            return 3; // slight right
        } else if (-100 <= angle && angle < -30) {
            return 5; // left
        } else if (30 < angle && angle <= 100) {
            return 4; // right
        } else if (angle < -100) {
            return 6; // sharp left
        } else if (100 < angle) {
            return 7; // sharp right
        }

        return -1;
    }


    /**
     * Class to represent a navigation direction, which consists of 3 attributes:
     * a direction to go, a way, and the distance to travel for.
     */
    public static class NavigationDirection {

        /** Integer constants representing directions. */
        public static final int START = 0;
        public static final int STRAIGHT = 1;
        public static final int SLIGHT_LEFT = 2;
        public static final int SLIGHT_RIGHT = 3;
        public static final int RIGHT = 4;
        public static final int LEFT = 5;
        public static final int SHARP_LEFT = 6;
        public static final int SHARP_RIGHT = 7;

        /** Number of directions supported. */
        public static final int NUM_DIRECTIONS = 8;

        /** A mapping of integer values to directions.*/
        public static final String[] DIRECTIONS = new String[NUM_DIRECTIONS];

        /** Default name for an unknown way. */
        public static final String UNKNOWN_ROAD = "unknown road";
        
        /** Static initializer. */
        static {
            DIRECTIONS[START] = "Start";
            DIRECTIONS[STRAIGHT] = "Go straight";
            DIRECTIONS[SLIGHT_LEFT] = "Slight left";
            DIRECTIONS[SLIGHT_RIGHT] = "Slight right";
            DIRECTIONS[LEFT] = "Turn left";
            DIRECTIONS[RIGHT] = "Turn right";
            DIRECTIONS[SHARP_LEFT] = "Sharp left";
            DIRECTIONS[SHARP_RIGHT] = "Sharp right";
        }

        /** The direction a given NavigationDirection represents.*/
        int direction;
        /** The name of the way I represent. */
        String way;
        /** The distance along this way I represent. */
        double distance;

        /**
         * Create a default, anonymous NavigationDirection.
         */
        public NavigationDirection() {
            this.direction = STRAIGHT;
            this.way = UNKNOWN_ROAD;
            this.distance = 0.0;
        }

        public String toString() {
            return String.format("%s on %s and continue for %.3f miles.",
                    DIRECTIONS[direction], way, distance);
        }

        /**
         * Takes the string representation of a navigation direction and converts it into
         * a Navigation Direction object.
         * @param dirAsString The string representation of the NavigationDirection.
         * @return A NavigationDirection object representing the input string.
         */
        public static NavigationDirection fromString(String dirAsString) {
            String regex = "([a-zA-Z\\s]+) on ([\\w\\s]*) and continue for ([0-9\\.]+) miles\\.";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(dirAsString);
            NavigationDirection nd = new NavigationDirection();
            if (m.matches()) {
                String direction = m.group(1);
                if (direction.equals("Start")) {
                    nd.direction = NavigationDirection.START;
                } else if (direction.equals("Go straight")) {
                    nd.direction = NavigationDirection.STRAIGHT;
                } else if (direction.equals("Slight left")) {
                    nd.direction = NavigationDirection.SLIGHT_LEFT;
                } else if (direction.equals("Slight right")) {
                    nd.direction = NavigationDirection.SLIGHT_RIGHT;
                } else if (direction.equals("Turn right")) {
                    nd.direction = NavigationDirection.RIGHT;
                } else if (direction.equals("Turn left")) {
                    nd.direction = NavigationDirection.LEFT;
                } else if (direction.equals("Sharp left")) {
                    nd.direction = NavigationDirection.SHARP_LEFT;
                } else if (direction.equals("Sharp right")) {
                    nd.direction = NavigationDirection.SHARP_RIGHT;
                } else {
                    return null;
                }

                nd.way = m.group(2);
                try {
                    nd.distance = Double.parseDouble(m.group(3));
                } catch (NumberFormatException e) {
                    return null;
                }
                return nd;
            } else {
                // not a valid nd
                return null;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof NavigationDirection) {
                return direction == ((NavigationDirection) o).direction
                    && way.equals(((NavigationDirection) o).way)
                    && distance == ((NavigationDirection) o).distance;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(direction, way, distance);
        }
    }
}
