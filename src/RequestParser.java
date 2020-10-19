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
                    String[] finished = current.toString().split(",");
                    current = null;
                    return finished;
                default:
                    current.append(character);
                    break;
            }
        }
        return null;
    }
}
