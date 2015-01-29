package cgl.iotcloud.config;

/**
 * Created by shameera on 1/23/15.
 */
public class SpoutConfig {

    private String id;
    private String spoutClass;
    private int parallelism;

    public SpoutConfig(String id, String spoutClass, int parallelism) {
        this.id = id;
        this.spoutClass = spoutClass;
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

    public String getSpoutClass() {
        return spoutClass;
    }

    public void setSpoutClass(String spoutClass) {
        this.spoutClass = spoutClass;
    }

    public int getParallelism() {
        return parallelism;
    }

    public void setParallelism(int parallelism) {
        this.parallelism = parallelism;
    }
}
