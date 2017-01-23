package munger.aaron.words;

import munger.aaron.command.Command;

import java.util.*;

public class Histogram implements Command {

    HashMap<String, Integer> wordMap = new HashMap<String, Integer>();

    public void run(String string) {
        if (string != null && !string.isEmpty()) {
            String formatted = StringFormatter.formatString(string);
            String[] words = formatted.split(" ");
            addWords(words);
        }
    }

    public void printHistogram(){
        List<Map.Entry<String, Integer>> entryList = getSortedWordMap();
        for(Map.Entry<String, Integer> entry : entryList){
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    private List<Map.Entry<String, Integer>> getSortedWordMap(){
        List<Map.Entry<String, Integer>> entryList = new LinkedList<Map.Entry<String, Integer>>(wordMap.entrySet());
        Collections.sort(entryList, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o1.getValue()).compareTo( o2.getValue()) * -1;
            }
        });
        return entryList;
    }

    private void addWords(String[] words){
        for(String word : words){
            if (!word.isEmpty()) {
                if (wordMap.containsKey(word)) {
                    wordMap.put(word, wordMap.get(word) + 1);
                } else {
                    wordMap.put(word, 1);
                }
            }
        }
    }

    public HashMap<String, Integer> getWordMap(){
        return wordMap;
    }

}
