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


import org.jacoco.core.analysis.*;
import org.jacoco.core.data.*;
import org.jacoco.core.tools.ExecFileLoader;

import java.io.*;
import java.security.InvalidParameterException;

/**
 * This is the primary class that deals with the deserialization of JaCoCo's binaries and the reporting
 * @author Adriano Naime Bonin (alias c3nt4ur1)
 */
public class JacocoReporter {

    static String execFile = "target/jacoco.exec";
    static ExecutionDataStore dataStore;
    static SessionInfoStore sessionStore;
    static String classesDir;
    static String BundleName;
    static String targetFile = "JacocoReport.txt";
    static String projectPath;


    /**
     * Deserealizes the .exec binary and loads it to primary memory
     *
     * @throws IOException If the .exec file is invalid or something went wrong in creating the File object
     * @return ExecFileLoader with the sessions and tests data
     */
    public static ExecFileLoader deserealize() throws IOException{

        System.out.println("Entered the deserialization phase");

        ExecFileLoader fileLoader = new ExecFileLoader();

        File execFileObj = new File(execFile);

        if(!execFileObj.exists()){
            throw new IOException("Binary file does not exist");
        }

            fileLoader.load(execFileObj);

        dataStore = fileLoader.getExecutionDataStore();
        sessionStore = fileLoader.getSessionInfoStore();

        System.out.println("Finished the deserialization phase");
        return fileLoader;

    }


    /**
     * Matches the bytecode files with the ExecutionDataStore data collected in deserealize()
     * @param loader ExecFileLoader with the deserializaion of the .exec file
     * @return IBundleCoverage object from the analysis of the deserialized data
     * @throws IOException If the classes directory (that should contain the .class files) does not exist or if it is a file instead, it throws an IOException
     */
    public static IBundleCoverage Analyse(ExecFileLoader loader) throws IOException{

        System.out.println("Entered the Analyse method");

        CoverageBuilder coverageBuilder = new CoverageBuilder();

        Analyzer analyzer = new Analyzer(loader.getExecutionDataStore(), coverageBuilder);

        System.out.println("Creating the classes directory object");
        File classesDirectoryFile = new File(classesDir);

        if(!classesDirectoryFile.exists() || !classesDirectoryFile.isDirectory()){
            throw new IOException("ERROR: unable to find the .class files directory");
        }

        System.out.println("Ready to run the analysis");
        analyzer.analyzeAll(classesDirectoryFile);

        System.out.println("Finished the analysis");
        return coverageBuilder.getBundle(BundleName);
    }


    /**
     * Creates and writes up the report in a .txt file
     *
     * @param bundle This expects the IBundleCoverage object containing the data bundle from the analysis of the binary deserialization
     * @throws FileNotFoundException if there is some problem in creating the PrintStream object
     */
    public static void report(IBundleCoverage bundle) throws FileNotFoundException{

        System.out.println("Entered the reporting phase");

        File output = new File(targetFile);
        PrintStream pStream;
        pStream = new PrintStream(output);

        pStream.println(bundle.getName());

        System.out.println("Ready to iterate over the bundle");
        //Iterates over all packages, that contain all the classes that contain all the lines
        //Bundle->Package->Class->Line structure
        for(IPackageCoverage packageCoverage : bundle.getPackages()){

            String packageName = packageCoverage.getName().replace("/", ".");

            for(IClassCoverage classCoverage : packageCoverage.getClasses()){

                String className = classCoverage.getName().replace("/", ".");

                for(IMethodCoverage methodCoverage : classCoverage.getMethods()){

                    String methodName = methodCoverage.getName().replace("/", ".");

                    String baseSignature = packageName + "." +
                            className.substring(className.lastIndexOf('.') + 1) + "." +
                            methodName;

                    if(methodCoverage.getFirstLine() > 0) {

                        for (int i = methodCoverage.getFirstLine(); i <= methodCoverage.getLastLine(); i++) {

                            ILine line = classCoverage.getLine(i);

                            if(line.getStatus() != ICounter.EMPTY){

                                /*
                                 * line.getStatus() results
                                 *
                                 * Empty: 0
                                 * Not Covered: 1
                                 * Fully Covered: 2
                                 * Partially Covered: 3
                                 */

                                Line reportableLine = new Line(line.getStatus(), baseSignature + ":" + i);
                                pStream.println(reportableLine.reportString);
                            }


                        }
                    }
                }
            }
        }
    }

    public static void reportGz(IBundleCoverage bundle) throws FileNotFoundException{

        System.out.println("Entered the reporting phase");

        File output = new File(targetFile);
        PrintStream pStream;
        pStream = new PrintStream(output);

        pStream.println(bundle.getName());

        System.out.println("Ready to iterate over the bundle");
        //Iterates over all packages, that contain all the classes that contain all the lines
        //Bundle->Package->Class->Line structure
        for(IPackageCoverage packageCoverage : bundle.getPackages()){

            String packageName = packageCoverage.getName().replace("/", ".");

            for(IClassCoverage classCoverage : packageCoverage.getClasses()){

                String className = classCoverage.getName().replace("/", ".");

                for(IMethodCoverage methodCoverage : classCoverage.getMethods()){

                    String methodName = methodCoverage.getName().replace("/", ".");

                    String baseSignature = packageName + "." +
                            className.substring(className.lastIndexOf('.') + 1) + "." +
                            methodName;

                    if(methodCoverage.getFirstLine() > 0) {

                        for (int i = methodCoverage.getFirstLine(); i <= methodCoverage.getLastLine(); i++) {

                            ILine line = classCoverage.getLine(i);

                            if(line.getStatus() != ICounter.EMPTY && line.getStatus() != 1){

                                /*
                                 * line.getStatus() results
                                 *
                                 * Empty: 0
                                 * Not Covered: 1
                                 * Fully Covered: 2
                                 * Partially Covered: 3
                                 */

                                /*

                                Line reportableLine = new Line(line.getStatus(), baseSignature + ":" + i);
                                pStream.println(reportableLine.reportString);
                                */
                                pStream.println(baseSignature + "#" + i);

                            }


                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args){

        //Later implement the possibility of variating the input and output files. It is already setup in the class
        //Only dealing with String[] args is necessary; The rest is already calling the modifiable members

        BundleName = args[0];
        projectPath = args[1];
        classesDir = projectPath + "/target/classes";

        if(!(projectPath.equals("."))){
            execFile = projectPath + "/target/jacoco.exec";
            System.out.println(execFile);
        }

        String reportFormat = args[2];

        if(reportFormat.equalsIgnoreCase("default")) {

            try{
                ExecFileLoader deserialized = deserealize();
                try{
                    IBundleCoverage bundleCoverage = Analyse(deserialized);
                    try{
                        report(bundleCoverage);
                    } catch (IOException e) {
                        System.out.println("Failed to write up the report");
                        throw new RuntimeException(e);
                    }
                }catch (IOException e){
                    System.out.println("Something went wrong with the analysis step");
                    throw new RuntimeException(e);
                }
            } catch (IOException e) {
                System.out.println("Failed to deserialize the binary .exec file");
                throw new RuntimeException(e);
            }
        }else if(reportFormat.equalsIgnoreCase("gz")){

            try{
                ExecFileLoader deserialized = deserealize();
                try{
                    IBundleCoverage bundleCoverage = Analyse(deserialized);
                    try{
                        reportGz(bundleCoverage);
                    } catch (IOException e) {
                        System.out.println("Failed to write up the report");
                        throw new RuntimeException(e);
                    }
                }catch (IOException e){
                    System.out.println("Something went wrong with the analysis step");
                    throw new RuntimeException(e);
                }
            } catch (IOException e) {
                System.out.println("Failed to deserialize the binary .exec file");
                throw new RuntimeException(e);
            }
        }else{
            System.out.println("Unknown report format");
            throw new InvalidParameterException("Invalid report format");
        }
    }
}
