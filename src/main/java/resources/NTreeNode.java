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

package resources;

import org.jacoco.core.analysis.ICoverageNode;

import java.util.LinkedList;

/**
 * This class is a Graph implementation used in structuring the IBundleCoverage substructure.
 * It is responsible for making the report generating phase better than O(n^4) that the iterative loop would be
 */
public class NTreeNode {
    public LinkedList<NTreeNode> childNodes;

    //Super interface used by all the coverage elements classes
    //Later, the concrete implementation can be got with coverageElement.getElementType()
    private ICoverageNode coverageElement;



}