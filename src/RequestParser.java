public class RequestParser implements RequestListener {
    @Override
    public void update(Request request) {

    }
    StringBuilder current;
    public String parseRequest(String input) {
        if(current == null) {
            current = new StringBuilder();
        }
        char[] characters = input.toCharArray();
        for(char character : characters) {
            switch (character) {
                case ';':
                    String finished = current.toString();
                    current = null;
                    return finished;
                default:
                    current.append(character);
                    break;
            }
        }
        return "";
    }
}
