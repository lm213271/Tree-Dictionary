
//
// Complete this class: 60 points + 20 bonus points
//
// Note:
// Do NOT copy from textbook, or any other sources
// Do NOT copy from code that we did in class (as it contains errors)
//
// Do READ CODE from textbook and in-class code and write it out as your own code
//

//
//TreeDictionary implements the (self-balance) binary search tree as a Dictionary
//
public class TreeDictionary<T extends Comparable<T>>
{
    public static void main(String [] args)
    {
        //Optional: test TreeDictionary here
    }

    /**
     * insert the data into the treeDictionary by reading the keywords in the record
     * if the keyword is present then just add the record to the node record array
     * @param record: Object of Record class having json data
     */
    public void insert(Record<T> record) //20 points + 10 bonus (AVL insertion)
    {
        //insert this records into the tree based on its keywords

        //1. for each keyword in this record, find the node that contains this keyword

        //2. if no such node exists, create a new node and assign the keyword

        //3. insert the record into the node

        //4. repeat until all keywords in the record are processed

        //(bonus: implement AVL insertion that balances the tree)

        for (T keyword: record.Keywords) 
        {
            if (keyword != null)
            {
                Node<T> findResult = find(keyword);
                if (findResult == null) 
                {
                    this.root = insert(record, keyword, this.root);
                } 
                else 
                {
                    findResult.records.insert(record);
                }
            }
        }
    }

    /**
     * actually inserts into the tree
     * @param record: Object of Record class having json data
     * @param keyword : keyword word in the record
     * @param root: root of the
     * @return: current root node of the tree
     */
    private Node<T> insert(Record<T> record,T keyword, Node<T> root) 
    {
        if (root == null) 
        {
            Node<T> node = new Node<>(keyword);
            node.records.insert(record);
            return node;
        }
        if (root.keyword.compareTo(keyword)>0) 
        {
            root.left = insert(record, keyword, root.left);
        }
        else 
        {
            root.right = insert(record, keyword, root.right);
        }

        // balancing starts
        int difference = getBalance(root);

        // difference is greater than 1 and current value is greater than 
        // input value then right rotation
        if (difference > 1 && root.left.keyword.compareTo(keyword)>0) 
        {
            return rightRotate(root);
        }

        if (difference < -1 && root.right.keyword.compareTo(keyword)<0) 
        {
            return leftRotate(root);
        }

        if (difference > 1 && root.left.keyword.compareTo(keyword)<0) 
        {
            return lrRotate(root);
        }

        if (difference < -1 && root.right.keyword.compareTo(keyword)>0) 
        {
            return rlRotate(root);
        }

        return root;
    }

    /**
     * left right rotate the tree
     * @param p: AVLNode
     * @return : return node of the tree
     */
    private Node<T> lrRotate(Node<T> p) 
    {
        p.left = leftRotate(p.left);
        return rightRotate(p);
    }

    /**
     * right left rotate the tree
     * @param p: AVLNode
     * @return : node of the tree
     */
    private Node<T> rlRotate(Node<T> p) 
    {
        p.right = rightRotate(p.right);
        return leftRotate(p);
    }
    /**
     * right rotate the tree
     * @param p: AVLNode
     * @return : node of the tree
     */
    private Node<T> rightRotate(Node<T> p) {
        Node<T> x = p.left;
        Node<T> T2 = x.right;

        // Rotation
        x.right = p;
        p.left = T2;
        return x;
    }

    /**
     * left rotate the tree
     * @param p: AVLNode
     * @return : node of the tree
     */
    private Node<T> leftRotate(Node<T> p) {
        Node<T> y = p.right;
        Node<T> T2 = y.left;

        // Rotation
        y.left = p;
        p.right = T2;
        return y;
    }

    /**
     * get information of the difference between the height of the both child node
     * @param node
     * @return: difference in the height
     */
    private int getBalance(Node<T> node) {
        if (node != null) {
            return getHeight(node.left) - getHeight(node.right);
        }
        return 0;
    }

    /**
     * provide the height of the node
     * @param node
     * @return: int value of the height
     */
    private int getHeight(Node<T> node) {
        if (node == null) {
            return 0;
        }
        else {
            int lDepth = getHeight(node.left);
            int rDepth = getHeight(node.right);

            if (lDepth > rDepth)
                return lDepth + 1;
            return rDepth + 1;
        }
    }

    /**
     * Responsible for inorder traversal of the tree
     * @return: linkedList of the result of the inorder traversal
     */
    private LinkedList<Node<T>> InOrderTraversal() //10 points
    {
        //TODO : store in-order traversal of tree nodes in a linked list
        LinkedList<Node<T>> linkedList = new LinkedList<>();
        inorderTraversal(root, linkedList);
        return linkedList;
    }

    /**
     * actual inroder traversal function made to support recursive call
     * @param root
     * @param linkedList
     */
    private void inorderTraversal(Node<T> root, LinkedList<Node<T>> linkedList) {
        if (root == null) {
            return;
        }
        inorderTraversal(root.left, linkedList);
        if ( root != null)
            linkedList.insert(root);
        inorderTraversal(root.right, linkedList);
    }

    /**
     * remove the node from the tree if the keyword matched any node
     * @param keyword
     */
    private void remove(T keyword) //10 points + 10 bonux (AVL remove)
    {
        //
        // TODO: use a keyword to remove a node that contains this word
        //

        //(bonus: implement AVL remove that balances the tree)
        this.root = removeNode(this.root, keyword);
    }

    /**
     * Actual private that does the removal of node and balance the binary tree
     * @param root
     * @param keyword
     * @return
     */
    @SuppressWarnings("unchecked")
    private Node<T> removeNode(Node<T> root, T keyword) {
        if (root == null) {
            return root;
        }
        root = find(root, keyword);
        if (root!=null) {
            if (root.left == null || root.right == null) {
                Node temp = null;
                if (temp == root.left)
                    temp = root.right;
                else
                    temp = root.left;

                // No child case
                if (temp == null)
                {
                    temp = root;
                    root = null;
                }
                else // One child case
                    root = temp;
            }

            if (root == null) {
                return root;
            }

            // balancing starts
            int difference = getBalance(root);

            // difference is greater than 1 and current value is greater than input v
            // alue then right rotation
            if (difference > 1 && root.left.keyword.compareTo(keyword)>0) {
                return rightRotate(root);
            }

            if (difference < -1 && root.right.keyword.compareTo(keyword)<0) {
                return leftRotate(root);
            }

            if (difference > 1 && root.left.keyword.compareTo(keyword)<0) {
                return lrRotate(root);
            }

            if (difference < -1 && root.right.keyword.compareTo(keyword)>0) {
                return rlRotate(root);
            }

            return root;

        }
        return null;
    }

    /**
     *
     * @param keyword: data to be used for searching in the node
     * @return : return the node if keyword matches
     */
    private Node<T> find(T keyword) //10 points
    {
        //
        // TODO: find a node that contains this keyword
        //
        return find(root, keyword);

    }

    /**
     * private recursive find method that actually does the search job
     * @param root
     * @param keyword
     * @return: resulting node
     */
    private Node<T> find(Node<T> root, T keyword) {
        if (root == null || root.keyword.compareTo(keyword)==0) {
            return root;
        }
        if (root.keyword.compareTo(keyword)> 0) {
            return find(root.left, keyword);
        }
        else
          return find(root.right, keyword);
    }

    /**
     * find all the node and append the records in the result for each keyword in the
     * linkedlist
     * @param keywords
     * @return: DynamicArray object
     */
    public DynamicArray<Record<T>> find( LinkedList<T> keywords  ) //10 points
    {
      //
      //TODO: find an array of records that contain the given keywords
      //

      //hint: use find_then_build
        DynamicArray<Record<T>> result = new DynamicArray<>();
        for(T data: keywords) {
            Node<T> node = find(data);
            if (node != null) {
                for (Record<T> record: node.records) {
                    boolean flag = true;
                    for (Record<T> val: result) {
                        if (val.Name == record.Name) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag)
                        result.insert(record);
                }
            }
        }
        if (result.size()!=0)
            return result;
      return null; //return null when no records are found
    }

    // ----------------------------------------------------------------------
    //
    // !!! READ but Do NOT Change anything after this line
    //
    // ----------------------------------------------------------------------

    private class Node<T>
    {
        Node(){ records=new DynamicArray<Record<T>>();}
        Node(T k){ keyword=k; records=new DynamicArray<Record<T>>();}

        T keyword; //nodes are ordered by Keywords
        Node<T> parent;
        Node<T> left, right; //children
        DynamicArray<Record<T>> records;
        public String toString(){ return "["+keyword+" ("+records.size()+")]"; }
    }

    private Node<T> root; //root of the tree, can be null

    //build this tree by inserting the records
    public void build( DynamicArray<Record<T>> records )
    {
        for(Record<T> r : records)
        {
          insert(r);
        }
    }

    //find a node that contains the given keyword and then
    //build a tree using the records stored in the found node
    //finally return the tree
    private TreeDictionary<T> find_then_build(T keyword)
    {
        //
        //use keyword to find the node
        Node<T> node = find(keyword);
        if(node==null) return null;

        //
        //build the tree from this node's record
        TreeDictionary<T> newT=new TreeDictionary<T>();
        newT.build(node.records);

        //
        //remove the keyword from the Tree
        newT.remove(keyword);

        //done
        return newT;
    }

    public String toString()
    {
      //list all the keyworkds and number of records for each keyword
      //visit all nodes in In-Order traversal.
      LinkedList<Node<T>> nodes = InOrderTraversal();
      String S="Tree Dictionary: {";
      for(Node<T> node : nodes) S+=node.toString()+", ";
      if(!nodes.is_empty()) S=S.substring(0,S.length()-2);
      S+="}";
      return S;
    }
}
