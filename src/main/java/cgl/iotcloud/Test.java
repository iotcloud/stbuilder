/*
package cgl.iotcloud;

import backtype.storm.generated.StormTopology;
import backtype.storm.topology.BoltDeclarer;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.TopologyBuilder;
import cgl.iotcloud.config.IConfigParser;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

*/
/**
 * Created by shameera on 1/22/15.
 *//*

public class Test implements IConfigParser{
    JsonReader jsonReader;
    TopologyBuilder builder;
    Logger log = LoggerFactory.getLogger(Test.class);

    public Test(String jsonConfigFilePath) throws FileNotFoundException {
        this(new File(jsonConfigFilePath));

    }

    public Test(File jsonConfigFile) throws FileNotFoundException {
        jsonReader = new JsonReader(new FileReader(jsonConfigFile));
    }

    @Override
    public StormTopology parse() throws Exception {
        builder = new TopologyBuilder();
        jsonReader.beginObject();
        jsonReader.nextString(); // "storm"
        jsonReader.beginObject();
        jsonReader.nextString(); // "topology"
        jsonReader.beginObject();
        JsonToken nextToken = jsonReader.peek();
        String sportOrBolt = jsonReader.nextName();
        while (nextToken == JsonToken.NAME) {
            if (sportOrBolt.equals("spouts")) {
                jsonReader.beginArray();
                JsonToken nextSpout = jsonReader.peek();
                while (nextSpout == JsonToken.BEGIN_OBJECT) {
                    parseSpout(jsonReader);
                    nextSpout = jsonReader.peek();
                }
                jsonReader.endArray();
            }else if (sportOrBolt.equals("bolts")) {
                jsonReader.beginArray();
                JsonToken nextBolt = jsonReader.peek();
                while (nextBolt == JsonToken.BEGIN_OBJECT) {
                    parseBolt(jsonReader);
                    nextBolt = jsonReader.peek();
                }
                jsonReader.endArray();
            }

        }
        return builder.createTopology();
    }

    private void parseBolt(JsonReader jsonReader) throws IOException {
        String id = "";
        IRichBolt bolt = null;
        int parallelism = 0;


        jsonReader.beginObject();
        while (jsonReader.peek() != JsonToken.END_OBJECT) {
            String name = jsonReader.nextName();
            if (name.equals("id")) {
                id = jsonReader.nextString();
            }else if (name.equals("bolt")) {
                try {
                    String boltClassName = jsonReader.nextString();
                    Object obj = Class.forName(boltClassName).newInstance();
                    if (obj instanceof IRichBolt) {
                        bolt = (IRichBolt) obj;
                    } else {
                        log.error(boltClassName + " is not an implementation of IRichBolt interface");
                    }
                } catch (InstantiationException e) {
                    log.error("Error while creating new bolt instance", e);
                } catch (IllegalAccessException e) {
                    log.error("Error while creating new bolt instance", e);
                } catch (ClassNotFoundException e) {
                    log.error("Error while creating new bolt instance", e);
                }
            }else if (name.equals("parallelism")) {
                parallelism = jsonReader.nextInt();
            }else if (name.equals("declarer")) {
                break;
            }
        }
        BoltDeclarer declarer = builder.setBolt(id, bolt, parallelism);

    }

    private void parseSpout(JsonReader jsonReader) throws IOException {
        String id = "";
        IRichSpout spout = null;
        int parallelism = 0;

        jsonReader.beginObject();
        while (jsonReader.peek() != JsonToken.END_OBJECT) {
            String name = jsonReader.nextName();
            if (name.equals("id")) {
                id = jsonReader.nextString();
            }else if (name.equals("spout")) {
                try {
                    String spoutClassName = jsonReader.nextString();
                    Object obj = Class.forName(spoutClassName).newInstance();
                    if (obj instanceof IRichSpout) {
                        spout = (IRichSpout) obj;
                    } else {
                        log.error(spoutClassName + " is not an implementation of IRichSpout interface");
                    }
                } catch (InstantiationException e) {
                    log.error("Error while creating new spout instance", e);
                } catch (IllegalAccessException e) {
                    log.error("Error while creating new spout instance", e);
                } catch (ClassNotFoundException e) {
                    log.error("Error while creating new spout instance", e);
                }
            }else if (name.equals("parallelism")) {
                parallelism = jsonReader.nextInt();
            }
        }
        builder.setSpout(id, spout, parallelism);
        jsonReader.endObject();
    }

}
*/
