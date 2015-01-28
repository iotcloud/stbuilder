package cgl.iotcloud.config;

/**
 * Created by shameera on 1/23/15.
 */
public class SpoutConfig {

    private String id;
    private String spout;
    private int parallelism;

    public SpoutConfig(String id, String spout, int parallelism) {
        this.id = id;
        this.spout = spout;
        this.parallelism = parallelism;
    }

    public SpoutConfig () {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSpout() {
        return spout;
    }

    public void setSpout(String spout) {
        this.spout = spout;
    }

    public int getParallelism() {
        return parallelism;
    }

    public void setParallelism(int parallelism) {
        this.parallelism = parallelism;
    }
}
