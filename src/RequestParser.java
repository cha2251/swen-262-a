import java.util.ArrayList;

public class RequestParser {

    StringBuilder current;
    public String[] parseRequest(String input) {
        if(current == null) {
            current = new StringBuilder();
        }
        char[] characters = input.toCharArray();
        for(char character : characters) {
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
    public String[] finalize(String s) {
        ArrayList<String> args = new ArrayList<>();
        current = new StringBuilder();
        boolean inBrackets = false;
        for(char character : s.toCharArray()) {
            switch (character) {
                case ',':
                    if(inBrackets) {
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
        return (String[]) args.toArray();
    }
}
