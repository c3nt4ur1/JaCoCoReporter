/*
 Copyright 2025 Adriano Naime Bonin

 Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

/**
 * This class deals with formatting the output of each ILine object data to the report with the default report style
 * @author Adriano Naime Bonin (alias c3nt4ur1)
 */
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
