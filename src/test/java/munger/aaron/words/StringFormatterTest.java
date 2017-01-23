package munger.aaron.words;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StringFormatterTest {

    @Test
    public void testStringWithoutPunctuation_returnsSameString(){
        String original = "the original string";
        String result = StringFormatter.formatString(original);
        assertEquals(original, result);
    }

    @Test
    public void testStringWithPunctuation_removesPunctuation(){
        String original = "the. original, string!";
        String result = StringFormatter.formatString(original);
        assertEquals("the original string", result);
    }

    @Test
    public void testStringWithQuotes_removesQuotes(){
        String original = "\"the original\" string's";
        String result = StringFormatter.formatString(original);
        assertEquals("the original string's", result);
    }

    @Test
    public void testStringWithDecimal_decimalNotRemoved(){
        String original = "the 2.0 string";
        String result = StringFormatter.formatString(original);
        assertEquals("the 2.0 string", result);
    }

    @Test
    public void testStringWithParens_removesParens(){
        String original = "(the original) string";
        String result = StringFormatter.formatString(original);
        assertEquals("the original string", result);
    }

    @Test
    public void testCapitalization_allIsLowerCase(){
        String original = "The ORIGINAL sTrINg";
        String result = StringFormatter.formatString(original);
        assertEquals("the original string", result);
    }

}
