package cgl.iotcloud.config;

import java.util.List;

/**
 * Created by shameera on 1/23/15.
 */
public class BoltConfig {
    private String id;
    private String boltClass;
    private int parallelism;
    private List<DeclarerConfig> declarers;

    public BoltConfig() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBoltClass() {
        return boltClass;
    }

    public void setBoltClass(String boltClass) {
        this.boltClass = boltClass;
    }

    public int getParallelism() {
        return parallelism;
    }

    public void setParallelism(int parallelism) {
        this.parallelism = parallelism;
    }

    public List<DeclarerConfig> getDeclarers() {
        return declarers;
    }

    public void setDeclarers(List<DeclarerConfig> declarers) {
        this.declarers = declarers;
    }
}
