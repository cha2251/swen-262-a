public class AdvanceRequest implements Request {

    /*
    Used for the simulated advancement of time in the library
     */

    private Library library;

    /**
     Instantiates Library in this class
     @param library
     **/
    public AdvanceRequest(Library library) {
        this.library = library;
    }

    /**
     * Advances time by the specified amount of time
     * @param args
     * @return
     */
    @Override
    public String execute(String[] args) {
        String cleintID = args[0];
        String prefix = args[1] + ",";
        //If days are between the range
        long days = Long.parseLong(args[2]);
        if (0 > days || days > 7) {
            return prefix + "invalid-number-of-days," + days + ";";
        }
        //UndoRedo.getInstance().addCommand(new Command("Advance", args));
        //If hours is defined
        if (args.length == 4) {
            //If hours are between the range
            long hours = Long.parseLong(args[3]);
            if (0 > hours || hours > 23) {
                return prefix + "invalid-number-of-hours," + hours + ";";
            }
            library.modifyTime(days, hours, cleintID);
            return prefix + "success";
        }
        library.modifyTime(days, 0, cleintID);
        return prefix + "success";
    }


    /**
     * This was a class to undo an advance request, but turns out to be unneeded
     * @param args
     * @return
     */
    public String undo(String[] args) {
        String prefix = args[0] + ",";
        String cleintID = args[0];
        //If days are between the range
        long days = Long.parseLong(args[1]) * -1;
        //If hours is defined˚
        if (args.length == 3) {
            //If hours are between the range
            long hours = Long.parseLong(args[2]) * -1;
            library.modifyTime(days, hours, cleintID);
            return prefix + "Time Reverted";
        }
        library.modifyTime(days, 0, cleintID);
        return prefix + "Time Reverted";
    }

}
