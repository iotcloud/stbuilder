package cgl.iotcloud.config;

import backtype.storm.generated.StormTopology;
import backtype.storm.topology.BoltDeclarer;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

/**
 * Created by shameera on 1/23/15.
 */
public class JsonConfigParser implements IConfigParser {

    private static final Logger log = LoggerFactory.getLogger(JsonConfigParser.class);

    private Reader jsonConfigReader;

    public JsonConfigParser(String jsonFilePath) throws IOException, ProcessingException {
        jsonConfigReader = new FileReader(new File(jsonFilePath));
        validateJsonConf();
    }

    public JsonConfigParser(InputStream jsonInputStream) throws IOException, ProcessingException {
        if (jsonInputStream == null) {
            throw new IllegalArgumentException("Input stream should not be NULL");
        }
        jsonConfigReader = new InputStreamReader(jsonInputStream);
        validateJsonConf();
    }

    @Override
    public StormTopology parse() throws Exception {
        Gson gson = new Gson();
        StormConfig stormConfig = gson.fromJson(jsonConfigReader, StormConfig.class);
        if (stormConfig != null) {
            TopologyBuilder builder = new TopologyBuilder();
            // load all spout configuration and set it to the builder
            IRichSpout spoutObj = null;
            for (SpoutConfig spout : stormConfig.getTopology().getSpouts()) {
                Class spoutClazz = Class.forName(spout.getSpoutClass());
                Object newInstance = spoutClazz.newInstance();
                if (newInstance instanceof IRichSpout) {
                    spoutObj = (IRichSpout) newInstance;
                }
                if (spoutObj == null) {
                    log.error(spout.getSpoutClass() + " is not an IRichSpout implementation");
                    continue;
                }
                builder.setSpout(spout.getId(), spoutObj, spout.getParallelism());
            }
            // load all bolt configurations and set it to the builder.
            IRichBolt boltObj = null;
            for (BoltConfig boltConfig : stormConfig.getTopology().getBolts()) {
                Class boltClazz = Class.forName(boltConfig.getBoltClass());
                Object newInstance = boltClazz.newInstance();
                if (newInstance instanceof IRichBolt) {
                    boltObj = (IRichBolt) newInstance;
                }
                if (boltObj == null) {
                    log.error(boltConfig.getBoltClass() + " is not an IRichBolt implementation");
                    continue;
                }
                BoltDeclarer declarer = builder.setBolt(boltConfig.getId(), boltObj, boltConfig.getParallelism());
                List<DeclarerConfig> declarerConfigList = boltConfig.getDeclarers();
                if (declarerConfigList != null) {
                    for (DeclarerConfig declarerConfig : declarerConfigList) {
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
                                log.info("Only SHUFFLE and FIELDS groupings are supported, others are not yet implemented");
                                break;
                        }

                        if (declarerConfig.getConfigurations() != null) {
                            declarer.addConfigurations(declarerConfig.getConfigurations());
                        }
                    }
                }
            }
            return builder.createTopology();
        }
        log.warn("Couldn't crate storm topology as Storm config is NULL");
        return null;
    }

    private boolean validateJsonConf() throws IOException, ProcessingException {
        JsonNode schemaNode = JsonLoader.fromFile(new File("src/main/resources/ConfigurationSchema.json"));
        JsonSchemaFactory schemaFactory = JsonSchemaFactory.byDefault();
        /*
        Here we consume the original jsonConfigReader in the process of validation and recreate a Reader from JsonNode
        then assign it back to the jsonConfigReader, if the validation succeed.
        Can we improve this logic?
         */
        if (schemaFactory.getSyntaxValidator().schemaIsValid(schemaNode)) {
            JsonSchema jsonSchema = schemaFactory.getJsonSchema(schemaNode);
            JsonNode topologyConfig = JsonLoader.fromReader(jsonConfigReader);
            ProcessingReport report = jsonSchema.validate(topologyConfig);
            if (report.isSuccess()) {
                log.info("Validation succeeded... ");
                log.info(report.toString());
                jsonConfigReader = new InputStreamReader(new ByteArrayInputStream(topologyConfig.toString().getBytes()));
                return true;
            } else {
                log.error("Validation failed...");
                log.error(report.toString());
                throw new RuntimeException("Configuration file validation failed, Please recheck configuration file and try again");
            }
        } else {
            log.error("Json configuration schema is not valid ");
            log.error("Terminate the process .....");
            throw new RuntimeException("Json Configuration schema is not valid");
        }
    }
}
