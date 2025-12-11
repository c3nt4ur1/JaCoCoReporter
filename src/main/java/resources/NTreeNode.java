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

import org.jacoco.core.analysis.IBundleCoverage;
import org.jacoco.core.analysis.ICoverageNode;
import org.jacoco.core.analysis.ILine;

import java.util.LinkedList;


/**
 * @author Adriano Naime Bonin (alias c3nt4ur1)
 * This class represents the nodes of an n-ary tree, that contain some implementation of the ICoverageNode interface from JaCoCo's api
 */
public class NTreeNode extends NTreeComponent{

    ICoverageNode coverageElement;

    public NTreeNode(ICoverageNode covered){

        this.coverageElement = covered;
        this.buildChildrenNodes();

    }

    /**
     * This method recursively finds all the leafs from the tree and store them in a linked list
     * @param root The root of the tree to be searched
     * @param target The linked list where the coverage data for each line will be stored upon accessing its correspondent leaf (NTreeLeaf class)
     */
    @Override
    public void findLeafs(NTreeComponent root, LinkedList<ILine> target){

        if(root.childrenElements.getFirst() instanceof NTreeNode){
            for(NTreeComponent node : root.childrenElements){
                findLeafs(node, target);
            }
        }else if(root.childrenElements.getFirst() instanceof NTreeLeaf){
            for(NTreeComponent leafComp : root.childrenElements){
                //Casting is necessary for forcing the downward polymorphism
                NTreeLeaf leaf = (NTreeLeaf) leafComp;

                target.addLast(leaf.lineCoverage);

            }
        }
    }

    /**
     * This method sets the children nodes linked list of a node
     * @param childrenElements
     */
    public void setChildrenElements(LinkedList<? extends NTreeComponent> childrenElements){

        Class<?> referenceClass = childrenElements.getFirst().getClass();

        for(NTreeComponent component : childrenElements){
            if(component.getClass() != referenceClass){
                throw new IllegalArgumentException();
            }
        }

        this.childrenElements = (LinkedList<NTreeComponent>) childrenElements;

    }

    public void buildChildrenNodes(){

        ICoverageNode.ElementType type = this.coverageElement.getElementType();

        //Inside an if/else if/else clause
        //this.childrenElements = (LinkedList<>)((IElementType) this.coverageComponent).get[ElementType]();
        //Create NTreeNode elements based on the list, then call the method to them as well

        if(type == ICoverageNode.ElementType.BUNDLE){

        }else if(type == ICoverageNode.ElementType.PACKAGE){

        }else if(type == ICoverageNode.ElementType.CLASS){

        }else if(type == ICoverageNode.ElementType.METHOD){

        }


    }
}