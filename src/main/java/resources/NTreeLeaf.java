package resources;

import org.jacoco.core.analysis.ILine;

import java.util.LinkedList;

/**
 * @author Adriano Naime Bonin
 * This is the n-ary tree leaf implementation. It is meant to be called in NTreeNode.findLeafs in order to get the data from its ILine member
 */
public class NTreeLeaf extends NTreeComponent{
    ILine lineCoverage;
    int lineNum;


    public NTreeLeaf(ILine iLine, String parentNodeName, int lineNum){
        this.coverageItemIdString = parentNodeName;
        this.lineNum = lineNum;
        lineCoverage = iLine;
    }

    /**
     * You should never try to find the leafs from a leaf in the context of JaCoCo's hierarchy. Therefore, it throws an exception if attempted
     * @param root Root node of the tree
     * @param target Linked list where leafs' lineCoverage would be stored if not illegal. Comes from the abstract parent
     */
    @Override
    public void findLeafs(NTreeComponent root, LinkedList<ILine> target){

        throw new ClassCastException("findLeafs method should be called from a root, or a node of the n-ary tree, never from a leaf");

    }

}
