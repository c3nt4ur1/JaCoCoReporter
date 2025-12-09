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
import org.jacoco.core.analysis.ILine;

import java.util.LinkedList;

/**
 * This class is a Graph implementation used in structuring the IBundleCoverage substructure.
 * It is responsible for making the report generating phase better than O(n^4) that the iterative loop would be
 */
public class NTreeNode<T> {
    public LinkedList<NTreeNode<T>> childNodes;

    final private T coverageElement;


    /**
     * Standard constructor for each node of the n-ary tree
     *
     * @param coverageElement Corresponds to the data to be stored in the node. It's default type is implemented
     * @param children The linked list where the found leaves (ILine elements)
     */
    public NTreeNode(T coverageElement, LinkedList<NTreeNode<T>> children){

        this.coverageElement = coverageElement;

        if(coverageElement instanceof ICoverageNode){
            this.childNodes = children;
        }else if(coverageElement instanceof ILine){
            if(children != null){
                throw new IllegalArgumentException();
            }
            this.childNodes = null;
        }

    }

    void getLeaves(NTreeNode<T> tree, LinkedList<ILine> target){

        if(!(this.coverageElement instanceof ICoverageNode || !(this.coverageElement instanceof  ILine))){
            throw new IllegalArgumentException();
        }


        if(this.coverageElement instanceof ICoverageNode){
            for(NTreeNode<T> node : tree.childNodes){
                getLeaves(node, target);
            }
        }else{
            target.addLast((ILine)tree.coverageElement);
        }
    }
}