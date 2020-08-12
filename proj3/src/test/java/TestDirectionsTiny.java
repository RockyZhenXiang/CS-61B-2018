import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestDirectionsTiny {


    private static final int NUM_TESTS = 8;
    private static final String OSM_DB_PATH = "../library-sp18/data/tiny-clean.osm.xml";
    private static GraphDB graph;

    @Before
    public void setUp() throws Exception {
        graph = new GraphDB(OSM_DB_PATH);
    }

    @Test
    public void testRouteDirections() throws Exception {
        List<Long> shortestPath = Router.shortestPath(graph, 0.4, 38.1, 0.4, 38.6); //41-46
        List<Router.NavigationDirection> actual = Router.routeDirections(graph, shortestPath);
        System.out.println(graph.distance(41,63));
        System.out.println(graph.distance(66,63));
        System.out.println(graph.distance(66,46));
    }
}
