package cgl.iotcloud.config;

import java.util.List;

/**
 * Created by shameera on 1/25/15.
 */
public class DeclarerConfig {
    private Grouping grouping;
    private String componentId ;
    private String streamId ;
    private List<String> fields;

    public Grouping getGrouping() {
        return grouping;
    }

    public void setGrouping(Grouping grouping) {
        this.grouping = grouping;
    }

    public String getComponentId() {
        return componentId;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    public String getStreamId() {
        return streamId;
    }

    public void setStreamId(String streamId) {
        this.streamId = streamId;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }
}
