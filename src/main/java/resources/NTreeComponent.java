package resources;

import org.jacoco.core.analysis.IClassCoverage;
import org.jacoco.core.analysis.ICoverageNode;
import org.jacoco.core.analysis.ILine;

import java.util.LinkedList;
import java.util.List;

/**
 * This interface provides the necessary compatibility between nodes inherited from ICoverageNode and instances of the ILine class
 * The covered element must be implemented in the inherited classes
 */
public abstract class NTreeComponent {

    LinkedList<NTreeComponent> childrenElements = null;

    /**
     * This is the base of the findLeafs method that is implemented in NTreeNode and causes an exception in NTreeLeaf
     * @param root Root of the n-ary tree
     * @param target Linked list to store ILine objects
     */
    public abstract void findLeafs(NTreeComponent root, LinkedList<ILine> target);

}
