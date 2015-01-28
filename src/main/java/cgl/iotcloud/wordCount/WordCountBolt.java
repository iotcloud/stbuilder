package cgl.iotcloud.wordCount;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shameera on 1/23/15.
 */
public class WordCountBolt extends BaseRichBolt {
    private OutputCollector _collector;
    private Map<String,Integer> wordCount;

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        _collector = outputCollector;
        wordCount = new HashMap<String, Integer>();
    }

    @Override
    public void execute(Tuple tuple) {
        final String word = tuple.getString(0);
        Integer count = wordCount.get(word);
        if (count == null) {
            count = 0;
        }
        count++;
        wordCount.put(word, count);

        System.out.println("++++++++++++++++++++++++++++++++++++ " + word + " ====== " + count);
        _collector.emit(tuple, new Values(word, count));
        _collector.ack(tuple);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("word", "count"));
    }
}
