package com.movie.script.analysis;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.HashSet;
import java.util.StringTokenizer;

public class UniqueWordsMapper extends Mapper<Object, Text, Text, Text> {

    private Text character = new Text();
    private Text word = new Text();

    @Override
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        if (line.contains(":")) {
            String[] part = line.split(":");
            character.set(part[0].trim());
            String dialogue = part[1].trim();
            HashSet<String> wordSet = new HashSet<>();
            StringTokenizer token = new StringTokenizer(dialogue);
            while (token.hasMoreTokens()) {
                wordSet.add(token.nextToken().toLowerCase());
            }

            word.set(wordSet.toString());
            context.write(character, word);
        }
    }
}
