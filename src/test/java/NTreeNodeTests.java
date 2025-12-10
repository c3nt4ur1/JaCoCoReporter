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

import static org.junit.jupiter.api.Assertions.*;

import org.jacoco.core.analysis.IPackageCoverage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.platform.commons.util.ReflectionUtils;
import resources.NTreeComponent;
import resources.NTreeNode;

import java.io.IOException;
import java.util.LinkedList;
import java.io.File;

import org.jacoco.core.analysis.ICoverageNode;
import org.jacoco.core.analysis.IBundleCoverage;

public class NTreeNodeTests {
    IBundleCoverage bundle;
    NTreeNode rootNode;
    NTreeNode bundleRoot;

    /**
     * This method sets up the static member of JacocoReporter with a project copied under the root directory of the reporter project
     */
    //For now, it only deals with one sample project as test
    @BeforeAll
    static void initializeTests(){
        String projectDir = System.getProperty("sample.project.directory", ".");

        JacocoReporter.projectPath = new File(projectDir).getAbsolutePath();

        JacocoReporter.classesDir = JacocoReporter.projectPath + "/target/classes";
        JacocoReporter.execFile = JacocoReporter.projectPath + "/target/jacoco.exec";

        JacocoReporter.bundleName = System.getProperty("bundle.name", "tests");
    }

    @BeforeEach
    void setup(){
        try {
            bundle = JacocoReporter.Analyse(JacocoReporter.deserealize());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        LinkedList<NTreeComponent> bundleChildren = new LinkedList<>();

        //Generates a tree with the bundle only.
        bundleRoot = new NTreeNode(bundle, bundleChildren);

    }

    @AfterEach
    void wrapUp(){
        bundle = null;
        rootNode = null;
    }

    //This is just for testing the setups and wrap ups
    @Test
    void setupTesting(){

        assertTrue(bundleRoot.getChildrenElements().isEmpty());

    }

    @Test
    void leafsClassTesting(){

        LinkedList<NTreeNode> IPackageCoverageNodes = new LinkedList<>();
        LinkedList<IPackageCoverage> bundleChildren = new LinkedList<>();

        for(IPackageCoverage packageCoverage : bundle.getPackages()){
            IPackageCoverageNodes.addLast(new NTreeNode(packageCoverage, null));
            bundleChildren.addLast(packageCoverage);
        }
        bundleRoot.setChildrenElements(IPackageCoverageNodes);

        assertNotNull(bundleRoot);
        assertNotNull(bundleRoot.getChildrenElements());
        for(NTreeComponent node : bundleRoot.getChildrenElements()){
            assertNotNull(node);
        }

    }

}
