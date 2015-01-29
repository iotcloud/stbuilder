package cgl.iotcloud.config;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shameera on 1/23/15.
 */
public class TopologyConfig {
    private List<SpoutConfig> spouts;
    private List<BoltConfig> bolts;


    public List<BoltConfig> getBolts() {
        return bolts;
    }

    public void setBolts(List<BoltConfig> bolts) {
        this.bolts = bolts;
    }

    public List<SpoutConfig> getSpouts() {
        return spouts;
    }

    public void setSpouts(List<SpoutConfig> spouts) {
        this.spouts = spouts;
    }
}
