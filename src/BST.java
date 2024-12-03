// Bruno Lauand Ferrão - 32217994

import java.io.PrintWriter;

public class BST {
    protected Node root;

    public BST() {
        this.root = null;
    }

    public void insert(ProgramaNetflix data) {
        this.root = insertRec(this.root, data);
    }

    protected Node insertRec(Node root, ProgramaNetflix data) {
        if (root == null) {
            return new Node(data);
        }
    
        // Comparação de IDs como strings
        int idComparison = data.getId().compareTo(root.data.getId());
    
        if (idComparison < 0) {
            // O novo ID é menor, insira à esquerda
            root.left = insertRec(root.left, data);
        } else if (idComparison > 0) {
            // O novo ID é maior, insira à direita
            root.right = insertRec(root.right, data);
        }
    
        return root;
    }

    public void remove(String id) {
        this.root = removeRec(this.root, id);
    }

    protected Node removeRec(Node root, String id) {
        if (root == null) {
            return root;
        }

        if (id.compareTo(String.valueOf(root.data.getId())) < 0) {
            root.left = removeRec(root.left, id);
        } else if (id.compareTo(String.valueOf(root.data.getId())) > 0) {
            root.right = removeRec(root.right, id);
        } else {
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }

            root.data = minValue(root.right);

            root.right = removeRec(root.right, String.valueOf(root.data.getId()));
        }

        return root;
    }

    protected ProgramaNetflix minValue(Node root) {
        ProgramaNetflix minValue = root.data;
        while (root.left != null) {
            minValue = root.left.data;
            root = root.left;
        }
        return minValue;
    }

    public void inOrderTraversalSave(PrintWriter writer) {
        inOrderTraversalSaveRec(this.root, writer);
    }

    private void inOrderTraversalSaveRec(Node root, PrintWriter writer) {
        if (root != null) {
            inOrderTraversalSaveRec(root.left, writer);
            writer.println(root.data.toString());
            inOrderTraversalSaveRec(root.right, writer);
        }
    }

    public int getHeight() {
        return getHeightRec(this.root);
    }

    private int getHeightRec(Node root) {
        if (root == null) {
            return 0;
        }

        int leftHeight = getHeightRec(root.left);
        int rightHeight = getHeightRec(root.right);

        return 1 + Math.max(leftHeight, rightHeight);
    }
}
