package munger.aaron.words;

public class StringFormatter {

    public static String formatString(String original){
        return removePunctuation(original)
                .toLowerCase()
                .trim();
    }

    private static String removePunctuation(String original){
        return original
                .replaceAll("[\\.,!\"\\?\\):;](?:$| )", " ")
                .replaceAll("(?:^| )[\\(\"]", " ");
    }
}
