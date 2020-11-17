import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class Command {

    private String command;
    private String[] args;
    private ArrayList<LocalDateTime> dates;


    /**
     * Creation of a command object
     * @param command
     * @param args
     */
    public Command(String command, String[] args){
        this.args = args;
        this.command = command;
    }

    /**
     * Creation of a command object for return requsts
     * @param command
     * @param args
     */
    public Command(String command, String[] args, ArrayList<LocalDateTime> dates){
        this.args = args;
        this.command = command;
        this.dates = dates;
    }

    /**
     * Getter for command
     * @return
     */
    public String getCommand() {
        return command;
    }

    /**
     * Getter for args
     * @return
     */
    public String[] getArgs() {
        return args;
    }

    /**
     * Getter for Dates
     * @return
     */
    public ArrayList<LocalDateTime> getDates() {
        return dates;
    }

    /**
     * Formatting of toString of the command object
     * @return
     */
    @Override
    public String toString() {
        return command + "(" + args.toString() + ")\n";
    }

}
