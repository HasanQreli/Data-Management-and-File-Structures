import java.util.ArrayList;

public class CengTree
{
    public CengTreeNode root;
    // Any extra attributes...

    public CengTree(Integer order)
    {
        CengTreeNode.order = order;
        // TODO: Initialize the class
        root = new CengTreeNodeLeaf(null);
    }

    public void addBook(CengBook book)
    {
        // TODO: Insert Book to Tree
        // find leaf to add book
        // overflow copy and loop push up
        // no overflow done
        CengTreeNodeLeaf leaf = findLeafNode(book.getBookID());
        leaf.addBook(book);
        if(leaf.bookCount()<=2*CengTreeNode.order){
            return;
        }
        else{
            CengTreeNodeInternal parent = copy_up(leaf);
            while(parent.keyCount() > 2*CengTreeNode.order){
                parent = push_up(parent);
            }
            if(parent.getParent() == null){
                root = parent;
            }
        }

    }

    public ArrayList<CengTreeNode> searchBook(Integer bookID)
    {
        // TODO: Search within whole Tree, return visited nodes.
        // Return null if not found.
        ArrayList<CengTreeNode> result = new ArrayList<CengTreeNode>();

        if(root == null){
            System.out.println("Could not find " + bookID + ".");
            return null;
        }
        else{
            CengTreeNode curr = root;
            while(curr.getType() != CengNodeType.Leaf){
                result.add(curr);
                curr = ((CengTreeNodeInternal) curr).getChild(bookID);
            }
            result.add(curr);
            int index = ((CengTreeNodeLeaf)curr).findBookIndex(bookID);
            if(index == -1){
                System.out.println("Could not find " + bookID + ".");
                return null;
            }
            else{
                int tabcount = 0;
                String tabs;
                for(int i=0; i<result.size()-1; i++){
                    tabs = "\t".repeat(tabcount);
                    System.out.println(tabs + "<index>");
                    CengTreeNodeInternal internal_node = ((CengTreeNodeInternal) result.get(i));
                    for(int j=0; j<internal_node.keyCount(); j++){
                        System.out.println(tabs + internal_node.keyAtIndex(j));
                    }
                    System.out.println(tabs + "</index>");
                    tabcount++;
                }
                tabs = "\t".repeat(tabcount);
                CengTreeNodeLeaf leaf_node = ((CengTreeNodeLeaf) result.get(result.size()-1));
                System.out.println(tabs + "<record>" + leaf_node.getBook(index).fullName() + "</record>");
            }
            return result;
        }
    }

    public void printTree()
    {
        // TODO: Print the whole tree to console
        if(root != null){
            int tabcount = 0;
            printTreeHelper(tabcount, root);
        }
    }


    public void printTreeHelper(int tabcount, CengTreeNode node){
        String tabs = "\t".repeat(tabcount);
        if(node.getType() == CengNodeType.Internal){
            System.out.println(tabs + "<index>");
            CengTreeNodeInternal internal_node = (CengTreeNodeInternal) node;
            for(int j=0; j<internal_node.keyCount(); j++){
                System.out.println(tabs + internal_node.keyAtIndex(j));
            }
            System.out.println(tabs + "</index>");
            ArrayList<CengTreeNode> children = internal_node.getAllChildren();
            for(int i=0; i<children.size(); i++){
                printTreeHelper(tabcount+1, children.get(i));
            }
        }
        else{
            CengTreeNodeLeaf leaf_node = (CengTreeNodeLeaf) node;
            System.out.println(tabs + "<data>");
            for(int i=0; i<leaf_node.bookCount(); i++){
                System.out.println(tabs + "<record>" + leaf_node.getBook(i).fullName() + "</record>");
            }
            System.out.println(tabs + "</data>");

        }
    }

    // Any extra functions...
    public CengTreeNodeLeaf findLeafNode(Integer key){
        CengTreeNode curr = root;
        while(curr.getType() == CengNodeType.Internal){
            curr = ((CengTreeNodeInternal) curr).getChild(key);
        }
        return ((CengTreeNodeLeaf) curr);
    }

    public CengTreeNodeInternal copy_up(CengTreeNodeLeaf overflow_leaf){
        CengTreeNodeInternal parent = (CengTreeNodeInternal) overflow_leaf.getParent();
        Integer pos = CengTreeNode.order;

        // left and right nodes
        CengTreeNodeLeaf l =  new CengTreeNodeLeaf(parent);
        CengTreeNodeLeaf r =  new CengTreeNodeLeaf(parent);
        for(int i=0; i<pos; i++){
            l.addBook(overflow_leaf.getBook(i));
        }
        for(int i=pos; i<overflow_leaf.bookCount(); i++){
            r.addBook(overflow_leaf.getBook(i));
        }

        // parent node

        Integer copied_key = overflow_leaf.bookKeyAtIndex(pos);
        if(parent != null){
            parent.insert(copied_key, l, r);
        }
        else{
            parent = new CengTreeNodeInternal(null);
            l.setParent(parent);
            r.setParent(parent);
            parent.insert(copied_key, l, r);
        }
        return parent;

    }

    public CengTreeNodeInternal push_up(CengTreeNodeInternal overflow_node){
        CengTreeNodeInternal parent = (CengTreeNodeInternal) overflow_node.getParent();
        Integer pos = CengTreeNode.order;

        // left and right nodes
        CengTreeNodeInternal l =  new CengTreeNodeInternal(parent);
        CengTreeNodeInternal r =  new CengTreeNodeInternal(parent);

        l.get_part(overflow_node, 0, pos);
        r.get_part(overflow_node, pos+1, overflow_node.keyCount());

        // parent node

        Integer pushed_key = overflow_node.keyAtIndex(pos);
        if(parent != null){
            parent.insert(pushed_key, l, r);
        }
        else{
            parent = new CengTreeNodeInternal(null);
            l.setParent(parent);
            r.setParent(parent);
            parent.insert(pushed_key, l, r);
        }
        return parent;

    }


}



