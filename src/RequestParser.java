import java.util.ArrayList;

/**
 * A class that parses strings into requests
 */
public class RequestParser {

    StringBuilder current;

    /**
     * Converts a list of strings into a string array
     * @param arr
     * @return
     */
    public static String[] GetStringArray(ArrayList<String> arr) {

        // declaration and initialise String Array
        String[] str = new String[arr.size()];

        // ArrayList to Array Conversion
        for (int j = 0; j < arr.size(); j++) {

            // Assign each value to String array
            str[j] = arr.get(j);
        }

        return str;
    }

    /**
     * Converts a string into an array of commands
     * @param input
     * @return
     */
    public String[] parseRequest(String input) {
        if (current == null) {
            current = new StringBuilder();
        }
        char[] characters = input.toCharArray();
        for (char character : characters) {
            switch (character) {
                case ';':
                    String finished = current.toString();
                    current = null;
                    //Separate fields for commands
                    return finalize(finished);
                default:
                    current.append(character);
                    break;
            }
        }
        return null;
    }

    /**
     * Converts a string into a StringBuilder for parsing
     * @param s
     * @return
     */
    public String[] finalize(String s) {
        ArrayList<String> args = new ArrayList<>();
        current = new StringBuilder();
        boolean inBrackets = false;
        for (char character : s.toCharArray()) {
            switch (character) {
                case ',':
                    if (inBrackets) {
                        current.append(character);
                        break;
                    }
                    args.add(current.toString());
                    current = new StringBuilder();
                    break;
                case '{':
                    inBrackets = true;
                    current.append(character);
                    break;
                case '}':
                    inBrackets = false;
                    current.append(character);
                    break;
                default:
                    current.append(character);
                    break;
            }
        }
        args.add(current.toString());
        current = new StringBuilder();
        return GetStringArray(args);
    }
}
