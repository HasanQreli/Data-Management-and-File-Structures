import java.util.ArrayList;

public class CengTreeNodeInternal extends CengTreeNode
{
    private ArrayList<Integer> keys;
    private ArrayList<CengTreeNode> children;

    public CengTreeNodeInternal(CengTreeNode parent)
    {
        super(parent);

        // TODO: Extra initializations, if necessary.

        keys = new ArrayList<Integer>();
        children = new ArrayList<CengTreeNode>();
        this.type = CengNodeType.Internal;
    }

    // GUI Methods - Do not modify
    public ArrayList<CengTreeNode> getAllChildren()
    {
        return this.children;
    }
    public Integer keyCount()
    {
        return this.keys.size();
    }
    public Integer keyAtIndex(Integer index)
    {
        if(index >= this.keyCount() || index < 0)
        {
            return -1;
        }
        else
        {
            return this.keys.get(index);
        }
    }

    // Extra Functions

    public CengTreeNode getChild(Integer key){
        int i=0;
        while(i<this.keyCount() && key>=this.keys.get(i)){
            i++;
        }
        return this.children.get(i);
    }

    public void insert(Integer key, CengTreeNode l, CengTreeNode r){
        if(keyCount() == 0){
            this.keys.add(key);
            this.children.add(l);
            this.children.add(r);
            return;
        }
        int i=0;
        while(i<this.keyCount() && key>=this.keys.get(i)){
            i++;
        }
        this.keys.add(i, key);
        this.children.set(i, l);
        this.children.add(i+1, r);
    }

    public void get_part(CengTreeNodeInternal node, Integer start, Integer end){
        // this.keys equal to node.keys from start to end(included)
        // this.children equal to node.children from start to end(not included)
        this.keys.clear();
        this.children.clear();
        for(int i=start; i<end; i++){
            this.keys.add(node.keys.get(i));
            this.children.add(node.children.get(i));
            node.children.get(i).setParent(this);
        }
        this.children.add(node.children.get(end));
        node.children.get(end).setParent(this);
    }
}
