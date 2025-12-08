public class Line {

    int coverageStatus;
    final String identifier;

    public final String reportString;

    public Line(int status, String id){
        this.coverageStatus = status;
        this.identifier = id;

        //This -1 changes the coverage scale, meaning:
        /*
         * Empty: -1
         * Not Covered: 0
         * Covered: 1
         * Partially Covered: 2
         */
        this.reportString = this.identifier + " " + (this.coverageStatus - 1);

    }

}
