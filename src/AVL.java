// Bruno Lauand Ferrão - 32217994
// Raul Vilhora Cardoso Matias - 32267274

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class AVL extends BST {
    private int comparisons;

    @Override
    public void insert(ProgramaNetflix data) {
        this.root = insertRec(this.root, data);
    }

    @Override
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
        } else {
        }

        // Atualiza a altura do nó atual
        root.height = 1 + Math.max(height(root.left), height(root.right));

        int balance = getBalance(root);

        // Rotações
        // Esquerda-Esquerda
        if (balance > 1 && idComparison < 0) {
            return rightRotate(root);
        }

        // Direita-Direita
        if (balance < -1 && idComparison > 0) {
            return leftRotate(root);
        }

        // Esquerda-Direita
        if (balance > 1 && idComparison > 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        // Direita-Esquerda
        if (balance < -1 && idComparison < 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    @Override
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

        // Atualiza a altura do nó atual
        root.height = 1 + Math.max(height(root.left), height(root.right));

        // Obtém o fator de balanceamento do nó atual para verificar se é necessário rebalancear
        int balance = getBalance(root);

        // Casos de rotações após a remoção
        if (balance > 1 && getBalance(root.left) >= 0) {
            return rightRotate(root);
        }

        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        if (balance < -1 && getBalance(root.right) <= 0) {
            return leftRotate(root);
        }

        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    private int height(Node root) {
        if (root == null) {
            return 0;
        }
        return root.height;
    }

    private int getBalance(Node root) {
        if (root == null) {
            return 0;
        }
        return height(root.left) - height(root.right);
    }

    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        // Atualiza as alturas
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        // Atualiza as alturas
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    public ProgramaNetflix search(String id) {
        return searchRec(root, id);
    }
    
    private ProgramaNetflix searchRec(Node root, String id) {
        if (root == null || String.valueOf(root.data.getId()).equals(id)) {
            return (root != null) ? root.data : null;
        }

        comparisons++;
    
        if (id.compareTo(String.valueOf(root.data.getId())) < 0) {
            return searchRec(root.left, id);
        } else {
            return searchRec(root.right, id);
        }
    }

    public int getComparisons() {
        return comparisons;
    }

    // Método de análise 1: Top 10 títulos de acordo com os votos do IMDB de gênero romance.
    public List<ProgramaNetflix> top10romance() {
        List<ProgramaNetflix> result = new ArrayList<>();
        top10romanceRec(root, result);
        result.sort(Comparator.comparingDouble(ProgramaNetflix::getImdbVotes).reversed());
        return result.subList(0, Math.min(result.size(), 10));
    }
    
    private void top10romanceRec(Node root, List<ProgramaNetflix> result) {
        if (root != null && result.size() < 10) {
            top10romanceRec(root.left, result);
    
            if (root.data.getGeneros().contains("romance")) {
                result.add(root.data);
            }
    
            top10romanceRec(root.right, result);
        }
    }

    // Método de análise 2: Top 10 títulos com os menores ranking do IMDB
    public List<ProgramaNetflix> lowestTmdbScore() {
        List<ProgramaNetflix> result = new ArrayList<>();
        lowestTmdbScoreRec(root, result);
        result.sort(Comparator.comparingDouble(ProgramaNetflix::getImdbScore));
        return result.subList(0, Math.min(result.size(), 10));
    }
    
    private void lowestTmdbScoreRec(Node root, List<ProgramaNetflix> result) {
        if (root != null && result.size() < 10) {
            lowestTmdbScoreRec(root.left, result);
            result.add(root.data);
            lowestTmdbScoreRec(root.right, result);
        }
    }

    // Método de análise 3: Top 10 títulos de ação com maior ranking do IMDB(pré-ordem)
    public List<ProgramaNetflix> top10acao() {
        List<ProgramaNetflix> result = new ArrayList<>();
        top10AcaoPreOrdem(root, result);
        result.sort(Comparator.comparingDouble(ProgramaNetflix::getImdbScore).reversed());
        return result.subList(0, Math.min(result.size(), 10));
    }
    
    private void top10AcaoPreOrdem(Node root, List<ProgramaNetflix> result) {
        if (root != null && result.size() < 10) {
            if (root.data.getGeneros().contains("action")) {
                result.add(root.data);
            }
    
            top10AcaoPreOrdem(root.left, result);
            top10AcaoPreOrdem(root.right, result);
        }
    }

    // Método de análise 4: Top 10 filmes com ano de lançamento >= 2000, gênero comédia e maior popularidade ranking TMDB (em ordem)
    public List<ProgramaNetflix> top10ComediaReleaseYear2000() {
        List<ProgramaNetflix> result = new ArrayList<>();
        top10ComediaReleaseYear2000RecInOrder(root, result);
        result.sort(Comparator.comparingDouble(ProgramaNetflix::getTmdbPopularity).reversed());
        return result.subList(0, Math.min(result.size(), 10));
    }
    
    private void top10ComediaReleaseYear2000RecInOrder(Node root, List<ProgramaNetflix> result) {
        if (root != null && result.size() < 10) {
            top10ComediaReleaseYear2000RecInOrder(root.left, result);
    
            if (root.data.getReleaseYear() >= 2000 && root.data.getGeneros().contains("comedy")) {
                result.add(root.data);
            }
    
            top10ComediaReleaseYear2000RecInOrder(root.right, result);
        }
    }

    // Método de análise 5: Top 10 filmes gênero terror e maior pontuação ranking TMDB (por nível)
    public List<ProgramaNetflix> top10TerrorMaiorPopularidadeTmdb() {
        List<ProgramaNetflix> result = new ArrayList<>();
        top10TerrorMaiorPopularidadeTmdbLevelOrder(root, result);
        result.sort(Comparator.comparingDouble(ProgramaNetflix::getTmdbScore).reversed());
        return result.subList(0, Math.min(result.size(), 10));
    }
    
    private void top10TerrorMaiorPopularidadeTmdbLevelOrder(Node root, List<ProgramaNetflix> result) {
        if (root == null) {
            return;
        }
    
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
    
        while (!queue.isEmpty() && result.size() < 10) {
            Node current = queue.poll();
    
            if (current.data.getGeneros().contains("horror")) {
                result.add(current.data);
            }
    
            if (current.left != null) {
                queue.add(current.left);
            }
    
            if (current.right != null) {
                queue.add(current.right);
            }
        }
    }

    public boolean isEmpty() {
        return root == null;
    }

    public List<String> getAllDataAsStringList() {
        List<String> result = new ArrayList<>();
        getAllDataAsStringListRec(root, result);
        return result;
    }
    
    private void getAllDataAsStringListRec(Node root, List<String> result) {
        if (root != null) {
            getAllDataAsStringListRec(root.left, result);
            result.add(root.data.toString()); // Assuming you have overridden the toString() method in ProgramaNetflix class
            getAllDataAsStringListRec(root.right, result);
        }
    }
}
