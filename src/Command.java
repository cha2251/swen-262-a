import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class Command {

    private String command;
    private String[] args;
    private ArrayList<LocalDateTime> dates;
    private double fine;


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
    public Command(String command, String[] args, ArrayList<LocalDateTime> dates, double fine){
        this.args = args;
        this.command = command;
        this.dates = dates;
        this.fine = fine;
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
     * Getter for fine
     * @return
     */
    public double getFine() {
        return fine;
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
