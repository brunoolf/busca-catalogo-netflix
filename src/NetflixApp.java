// Bruno Lauand Ferrão - 32217994

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class NetflixApp {
    private static BST bstTree = new BST();
    private static AVL avlTree = new AVL();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int opcao;
        do {
            System.out.println("Menu:");
            System.out.println("1. Ler dados de arquivo");
            System.out.println("2. Análise de dados");
            System.out.println("3. Inserir Programa");
            System.out.println("4. Buscar Programa");
            System.out.println("5. Remover Programa");
            System.out.println("6. Exibir Altura das Árvores");
            System.out.println("7. Salvar dados em arquivo");
            System.out.println("8. Encerrar a Aplicação");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    lerDadosDeArquivo(bstTree, avlTree);
                    break;
                case 2:
                    analiseDadosAVL();
                    break;
                case 3:
                    inserirPrograma();
                    break;
                case 4:
                    buscarPrograma();
                    break;
                case 5:
                    removerPrograma();
                    break;
                case 6:
                    exibirAlturaDasArvores();
                    break;
                case 7:
                System.out.print("Informe o nome do arquivo para salvar os dados da AVL: ");
                String fileName = scanner.nextLine();
                salvarDadosEmArquivo(avlTree.getAllDataAsStringList(), fileName);
                System.out.println("Dados da AVL salvos com sucesso!");
                break;
                case 8:
                    encerrarAplicacao();
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 8);
        scanner.close();
    }

    private static String[] splitCSVLine(String line) {
    List<String> dados = new ArrayList<>();
    boolean aspas = false;
    StringBuilder atual = new StringBuilder();

    for (char c : line.toCharArray()) {
        if (c == '"') {
            aspas = !aspas;
        } else if (c == ',' && !aspas) {
            dados.add(atual.toString().trim());
            atual.setLength(0);  // limpa o campo atual
        } else {
            atual.append(c);
        }
    }

    // adiciona o campo final
    dados.add(atual.toString().trim());

    return dados.toArray(new String[0]);
    }


    private static void lerDadosDeArquivo(BST bstTree, AVL avlTree) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o nome do arquivo para ser lido:");
        String fileName = scanner.nextLine();

        try (InputStream inputStream = NetflixApp.class.getClassLoader().getResourceAsStream(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                // Dividir a linha em campos usando a vírgula como delimitador
                String[] dados = splitCSVLine(line);

                boolean taVazio = false;
                for (String field : dados) {
                    if (field.isEmpty()) {
                        taVazio = true;
                        break;
                    }
                }

                // Verificar se há pelo menos 15 campos (os atributos mencionados)
                if (taVazio) {
                    // Lidar com linhas que não têm informações suficientes (por exemplo, registros incompletos)
                } else if(dados.length == 15) {
                    // Criar um objeto ProgramaNetflix com base nos campos
                    ProgramaNetflix programa = new ProgramaNetflix(
                            dados[0],  // id
                            dados[1],  // título
                            dados[2],  // showType
                            dados[3],  // descrição
                            Integer.parseInt(dados[4]),  // releaseYear
                            dados[5],  // ageCertification
                            Integer.parseInt(dados[6]),  // runtime
                            dados[7],  // generos
                            dados[8],  // productionCountries
                            Float.parseFloat(dados[9]),  // temporadas
                            dados[10],  // imdbId
                            Float.parseFloat(dados[11]),  // imdbScore
                            Float.parseFloat(dados[12]),  // imdbVotes
                            Float.parseFloat(dados[13]),  // tmdbPopularity
                            Float.parseFloat(dados[14])  // tmdbScore
                    );

                    // Inserir o objeto nas árvores BST e AVL
                    bstTree.insert(programa);
                    avlTree.insert(programa);
                }
            }
            System.out.println("Dados do arquivo lido com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    private static void analiseDadosAVL() {
    if (avlTree.isEmpty()) {
        System.out.println("A árvore AVL está vazia. Realize a leitura de dados antes de realizar análises.");
        return;
    }

    System.out.println("Escolha um top 10 para ver:");
    System.out.println("1. Top 10 títulos de romance de acordo com os votos do Imdb");
    System.out.println("2. Top 10 títulos com menor avaliação no ranking Imdb");
    System.out.println("3. Top 10 títulos de ação mais populares no ranking do Imdb");
    System.out.println("4. Top 10 títulos mais populares de comédia lançados depois do ano 2000");
    System.out.println("5. Top 10 títulos de terror com maior pontuação no ranking do Tmdb");

    Scanner scanner = new Scanner(System.in);
        int opcao = scanner.nextInt();

        switch (opcao) {
            case 1 -> {
                List<ProgramaNetflix> top10romance = avlTree.top10romance();
                apresentarResultados(top10romance);
            }
            case 2 -> {
                List<ProgramaNetflix> lowestTmdbScore = avlTree.lowestTmdbScore();
                apresentarResultados(lowestTmdbScore);
            }
            case 3 -> {
                List<ProgramaNetflix> top10acao = avlTree.top10acao();
                apresentarResultados(top10acao);
            }
            case 4 -> {
                List<ProgramaNetflix> top10ComediaReleaseYear2000 = avlTree.top10ComediaReleaseYear2000();
                apresentarResultados(top10ComediaReleaseYear2000);
            }
            case 5 -> {
                List<ProgramaNetflix> top10TerrorMaiorPopularidadeTmdb = avlTree.top10TerrorMaiorPopularidadeTmdb();
                apresentarResultados(top10TerrorMaiorPopularidadeTmdb);
            }
            default -> System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
        }
        
    }


    private static void apresentarResultados(List<ProgramaNetflix> resultados) {
        if (resultados.isEmpty()) {
            System.out.println("Nenhum resultado encontrado para a análise.");
        } else {
            System.out.println("Resultados da análise:");
            for (ProgramaNetflix programa : resultados) {
                System.out.println(programa.getTitulo());
            }
        }
    }

    private static void inserirPrograma() {
        Scanner scanner = new Scanner(System.in);
        Locale currentLocale = scanner.locale();

        System.out.print("Digite a categoria ('tm' para filmes ou 'ts' para shows): ");
        String categoria = scanner.nextLine();

        System.out.print("Digite o número para o ID: ");
        int numero = scanner.nextInt();

        scanner.nextLine();

        String id = categoria + numero;

        System.out.print("Digite o título do programa: ");
        String titulo = scanner.nextLine();

        System.out.print("Digite o tipo de show (por exemplo, TV Show, Movie): ");
        String showType = scanner.nextLine();

        System.out.print("Digite a descrição do programa: ");
        String descricao = scanner.nextLine();

        System.out.print("Digite o ano de lançamento: ");
        int releaseYear = scanner.nextInt();

        scanner.nextLine();

        System.out.print("Digite a classificação etária: ");
        String ageCertification = scanner.next();

        System.out.print("Digite a duração em minutos: ");
        int runtime = scanner.nextInt();

        System.out.print("Digite os gêneros separados por vírgula: ");
        String generos = scanner.next();

        System.out.print("Digite os países de produção: ");
        String productionCountries = scanner.next();

        System.out.print("Digite o número de temporadas (apenas para SHOW): ");
        scanner.useLocale(Locale.US);
        double temporadas = categoria.equals("ts") ? scanner.nextDouble() : 0;
        scanner.useLocale(currentLocale);

        System.out.print("Digite o ID do IMDb: ");
        String imdbId = scanner.next();

        System.out.print("Digite a pontuação do IMDb: ");
        scanner.useLocale(Locale.US);
        double imdbScore = scanner.nextDouble();
        scanner.useLocale(currentLocale);

        System.out.print("Digite o número de votos do IMDb: ");
        scanner.useLocale(Locale.US);
        double imdbVotes = scanner.nextDouble();
        scanner.useLocale(currentLocale);

        System.out.print("Digite a popularidade do TMDb: ");
        scanner.useLocale(Locale.US);
        double tmdbPopularity = scanner.nextDouble();
        scanner.useLocale(currentLocale);

        System.out.print("Digite a pontuação do TMDb: ");
        scanner.useLocale(Locale.US);
        double tmdbScore = scanner.nextDouble();
        scanner.useLocale(currentLocale);

        ProgramaNetflix novoPrograma = new ProgramaNetflix(id, titulo, showType, descricao, releaseYear, ageCertification, runtime, generos, productionCountries, temporadas, imdbId, imdbScore, imdbVotes, tmdbPopularity, tmdbScore);

        bstTree.insert(novoPrograma);
        avlTree.insert(novoPrograma);
        
        System.out.println("Programa inserido nas árvores.");
    }

    private static void buscarPrograma() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o ID do programa a ser buscado: ");
        String idBusca = scanner.next();
    
        long startTimeAVL = System.nanoTime();
        ProgramaNetflix resultadoAVL = avlTree.search(idBusca);
        long endTimeAVL = System.nanoTime();
    
        // Apresente os resultados da busca na AVL
        if (resultadoAVL != null) {
            System.out.println("Resultado da busca na AVL:");
            System.out.println("Título: " + resultadoAVL.getTitulo());
    
            System.out.println("Número de comparações na AVL: " + avlTree.getComparisons());
            System.out.println("Tempo de execução na AVL: " + (endTimeAVL - startTimeAVL) + " nanosegundos");
        } else {
            System.out.println("Programa não encontrado na AVL.");
            
        }
    }
    

    private static void removerPrograma() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o ID do programa a ser removido: ");
        String idRemocao = scanner.next();
    
        bstTree.remove(idRemocao);
        avlTree.remove(idRemocao);
    
        System.out.println("Programa removido das árvores.");
        
    }
    

    private static void exibirAlturaDasArvores() {
        System.out.println("Altura da BST: " + bstTree.getHeight());
        System.out.println("Altura da AVL: " + avlTree.getHeight());
    }

    private static void salvarDadosEmArquivo(List<String> data, String fileName) {
        try (PrintWriter writer = new PrintWriter(fileName)) {
            for (String line : data) {
                writer.println(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    

    private static void encerrarAplicacao() {
        System.out.println("Encerrando a aplicação.");
    }
}
