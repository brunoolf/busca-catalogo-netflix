// Bruno Lauand Ferrão - 32217994

public class Node {
    ProgramaNetflix data;
    Node left, right;
    int height;

    public Node(ProgramaNetflix data) {
        this.data = data;
        this.left = this.right = null;
        this.height = 1;
    }
    

    @Override
    public String toString() {
        return "ID: " + data.getId() + ", Título: " + data.getTitulo(); // Exemplo, ajuste conforme necessário
    }
}
