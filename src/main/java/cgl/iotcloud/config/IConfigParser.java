package cgl.iotcloud.config;

import backtype.storm.generated.StormTopology;

import java.io.IOException;

/**
 * Created by shameera on 1/23/15.
 */
public interface IConfigParser {

    public StormTopology parse() throws Exception;
}
