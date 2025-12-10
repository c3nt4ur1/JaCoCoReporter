package resources;

import org.jacoco.core.analysis.ILine;

import java.util.LinkedList;

public class NTreeLeaf extends NTreeComponent{
    ILine lineCoverage;

    public NTreeLeaf(ILine iLine){
        lineCoverage = iLine;
    }

    @Override
    public void findLeafs(NTreeComponent root, LinkedList<ILine> target){

        throw new ClassCastException("findLeafs method should be called from a root, or a node of the n-ary tree, never from a leaf");

    }

}
