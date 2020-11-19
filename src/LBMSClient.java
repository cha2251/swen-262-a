public class LBMSClient {

    private static ClientType clientType;

    /**
     * The main function of the system, calls for creation and start up of library,
     * as well as checking for input
     * @param args
     */
    public static void main(String[] args) {
        setClientType(args[0]);
        clientType.runLibrary();
        }

    private static void setClientType(String arg){
        clientType = Integer.parseInt(arg)==0 ? new LBMS_TextBased() :  new LBMS_GUI();
    }

    /**
     * Parses and handles requests
     * @param input
     */
}
