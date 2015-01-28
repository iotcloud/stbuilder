package cgl.iotcloud.config;

import backtype.storm.generated.StormTopology;
import backtype.storm.topology.BoltDeclarer;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import org.mortbay.util.ajax.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static cgl.iotcloud.config.Grouping.*;

/**
 * Created by shameera on 1/23/15.
 */
public class JsonConfigParser implements IConfigParser {

    private static final Logger log = LoggerFactory.getLogger(JsonConfigParser.class);

    private File jsonFile;

    public JsonConfigParser(String jsonFilePath) {
        jsonFile = new File(jsonFilePath);
    }

    @Override
    public StormTopology parse() throws Exception {
        Gson gson = new Gson();
        StormConfig stormConfig = gson.fromJson(new FileReader(jsonFile), StormConfig.class);
        if (stormConfig != null) {
            TopologyBuilder builder = new TopologyBuilder();
            IRichSpout spoutObj = null;
            for (SpoutConfig spout : stormConfig.getTopology().getSpouts()) {
                Class spoutClazz = Class.forName(spout.getSpout());
                Object newInstance = spoutClazz.newInstance();
                if (newInstance instanceof IRichSpout) {
                    spoutObj = (IRichSpout) newInstance;
                }
                if (spoutObj == null) {
                    log.error(spout.getSpout() + " is not an IRichSpout implementation");
                }
                builder.setSpout(spout.getId(), spoutObj, spout.getParallelism());
            }

            IRichBolt boltObj = null;
            for (BoltConfig boltConfig : stormConfig.getTopology().getBolts()) {
                Class boltClazz = Class.forName(boltConfig.getBolt());
                Object newInstance = boltClazz.newInstance();
                if (newInstance instanceof IRichBolt) {
                    boltObj = (IRichBolt) newInstance;
                }
                if (boltObj == null) {
                    log.error(boltConfig.getBolt() + " is not an IRichBolt implementation");
                }

                BoltDeclarer declarer = builder.setBolt(boltConfig.getId(), boltObj, boltConfig.getParallelism());
                DeclarerConfig declarerConfig = boltConfig.getDeclarer();
                if (declarerConfig != null) {
                    switch (declarerConfig.getGrouping()) {
                        case SHUFFLE:
                            if (declarerConfig.getStreamId() != null) {
                                declarer.shuffleGrouping(declarerConfig.getComponentId(), declarerConfig.getStreamId());
                            } else {
                                declarer.shuffleGrouping(declarerConfig.getComponentId());
                            }
                            break;
                        case FIELDS:
                            if (declarerConfig.getStreamId() != null) {
                                declarer.fieldsGrouping(declarerConfig.getComponentId(),
                                        declarerConfig.getStreamId(), new Fields(declarerConfig.getFields()));
                            } else {
                                declarer.fieldsGrouping(declarerConfig.getComponentId(),
                                        new Fields(declarerConfig.getFields()));
                            }
                            break;
                        default:
                            log.info("Other grouping is not yet implemented");
                            break;
                    }
                }
            }

            return builder.createTopology();
        }
        return null;
    }
}
