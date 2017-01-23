package munger.aaron.words;

import static org.junit.Assert.*;

import munger.aaron.words.Histogram;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class HistogramTest {

    Histogram histogram;

    @Before
    public void Initialize(){
        histogram = new Histogram();
    }

    @Test
    public void GivenEmptyString_HistogramWillHaveNoKeys(){
        histogram.run("");
        checkHistogramContents(new String []{});
    }

    @Test
    public void GivenStringWithOnlySpaces_HistogramWillHaveNoKeys(){
        histogram.run("     ");
        checkHistogramContents(new String []{});
    }

    @Test
    public void GivenStringWithWords_HistogramHasCorrectKeys(){
        histogram.run("this that other");
        checkHistogramContents(new String []{"this", "that", "other"});
    }

    @Test
    public void GivenStringDuplicateWords_HistogramHasCorrectCounts(){
        histogram.run("this this that other that this");
        checkHistogramContents(new String []{"this", "that", "other"});
        HashMap<String, Integer> map = histogram.getWordMap();
        assertEquals((Integer) 3, map.get("this"));
        assertEquals((Integer) 2, map.get("that"));
        assertEquals((Integer) 1, map.get("other"));
    }

    @Test
    public void GivenStringWithPunctuation_KeysDoNotIncludePunctuation(){
        histogram.run("one sentence. second sentence, third!");
        checkHistogramContents(new String []{"one", "second", "sentence", "third"});
    }

    private void checkHistogramContents(String[] expected){
        HashMap<String, Integer> map = histogram.getWordMap();
        assertEquals(expected.length, map.keySet().size());
        for (String key : expected){
            assertTrue(map.containsKey(key));
        }
    }
}
