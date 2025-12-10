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

    public abstract void findLeafs(NTreeComponent root, LinkedList<ILine> target);

}
