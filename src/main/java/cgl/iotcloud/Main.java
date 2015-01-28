package cgl.iotcloud;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.generated.StormTopology;
import backtype.storm.utils.Utils;
import cgl.iotcloud.config.JsonConfigParser;
import cgl.iotcloud.config.StormConfig;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;

/**
 * Created by shameera on 1/25/15.
 */
public class Main {


    public static void main(String[] args) throws Exception {
        Logger log = LoggerFactory.getLogger(Main.class);
        JsonConfigParser jsonConfigParser;
        if (args.length > 0) {
            jsonConfigParser = new JsonConfigParser(args[0]);
        } else {
            log.info("No command line argument found, hence use default configuration file");
            File jsonFile = new File("src/main/resources/Sample.json");
            jsonConfigParser = new JsonConfigParser(jsonFile.getAbsolutePath());
//            jsonConfigParser = new JsonConfigParser(Main.class.getResourceAsStream("/src/main/resources/Sample.json"));
        }
        StormTopology topology = jsonConfigParser.parse();
        Config config = new Config();
        config.setNumWorkers(2);
        config.setDebug(true);

        LocalCluster localCluster = new LocalCluster();
        localCluster.submitTopology("test", config, topology);
        Utils.sleep(10000);
        localCluster.shutdown();

    }
}


      /*  StormConfig stormConfig = new StormConfig();
        TopologyConfig topologyConfig = new TopologyConfig();
        SpoutConfig spoutConfig = new SpoutConfig();
        spoutConfig.setId("word");
        spoutConfig.setParallelism(6);
        spoutConfig.setSpout("org.sample.spout");

        BoltConfig boltConfig = new BoltConfig();
        boltConfig.setId("split");
        boltConfig.setParallelism(8);
        boltConfig.setBolt("org.sample.bolt");

        List<SpoutConfig> spouts = new ArrayList<SpoutConfig>();
        spouts.add(spoutConfig);
        List<BoltConfig> bolts = new ArrayList<BoltConfig>();
        bolts.add(boltConfig);
        topologyConfig.setBolts(bolts);
        topologyConfig.setSpouts(spouts);
        stormConfig.setTopology(topologyConfig);
        System.out.println(gson.toJson(stormConfig));*/

/*        FileReader reader = new FileReader(new File("/Users/shameera/work/source/stbuilder/src/main/resources/Sample.json"));
        StormConfig stormConfig = gson.fromJson(reader, StormConfig.class);
        System.out.println(stormConfig.getTopology().getBolts().get(1).getId());*/