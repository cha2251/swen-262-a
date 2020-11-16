import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class Command {

    private String command;
    private String[] args;


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
     * Formatting of toString of the command object
     * @return
     */
    @Override
    public String toString() {
        return command + "(" + args.toString() + ")\n";
    }

}
