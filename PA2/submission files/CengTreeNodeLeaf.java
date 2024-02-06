import java.util.ArrayList;

public class CengTreeNodeLeaf extends CengTreeNode
{
    private ArrayList<CengBook> books;
    // TODO: Any extra attributes

    public CengTreeNodeLeaf(CengTreeNode parent)
    {
        super(parent);

        // TODO: Extra initializations

        books = new ArrayList<CengBook>();
        this.type = CengNodeType.Leaf;
    }

    // GUI Methods - Do not modify
    public int bookCount()
    {
        return books.size();
    }
    public Integer bookKeyAtIndex(Integer index)
    {
        if(index >= this.bookCount()) {
            return -1;
        } else {
            CengBook book = this.books.get(index);

            return book.getBookID();
        }
    }

    // Extra Functions

    public void addBook(CengBook book){
        int i=0;
        int id = book.getBookID();
        while(i<this.bookCount() && id>=this.books.get(i).getBookID()){
            i++;
        }
        this.books.add(i, book);
    }

    public CengBook getBook(Integer i){
        return this.books.get(i);
    }

    public Integer findBookIndex(Integer key){
        for(int i=0; i<this.bookCount(); i++){
            if(this.bookKeyAtIndex(i).equals(key))
                return i;

        }
        return -1;
    }
}
