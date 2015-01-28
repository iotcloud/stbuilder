package cgl.iotcloud.config;

/**
 * Created by shameera on 1/23/15.
 */
public class BoltConfig {
    private String id;
    private String bolt;
    private int parallelism;
    private DeclarerConfig declarer;

    public BoltConfig() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBolt() {
        return bolt;
    }

    public void setBolt(String bolt) {
        this.bolt = bolt;
    }

    public int getParallelism() {
        return parallelism;
    }

    public void setParallelism(int parallelism) {
        this.parallelism = parallelism;
    }

    public DeclarerConfig getDeclarer() {
        return declarer;
    }

    public void setDeclarer(DeclarerConfig declarer) {
        this.declarer = declarer;
    }
}
