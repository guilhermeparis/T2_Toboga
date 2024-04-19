import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class tobogaRecursivo {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java tobogaRecursivo <filename.txt>");
            return;
        }

        //Caminho do arquivo que contem a matriz.
    	String nomeArquivo = args[0];

        //Ler matriz do arquivo
        int[][] toboga = constroiMatriz(nomeArquivo);


        if (toboga != null) {
            long caminhos = contaCaminhos(toboga);
            System.out.println("Maneiras de descer o toboga: " + caminhos);
        }
    }

    //Construir a matriz a partir do arquivo
    private static int[][] constroiMatriz(String nomeArquivo) {
        try {
            File f = new File(nomeArquivo);
            Scanner sc = new Scanner(f);

            int linhas = sc.nextInt();
            int colunas = sc.nextInt();
            int[][] toboga = new int[linhas][colunas];

            for (int i = 0; i < linhas; i++) {
                for (int j = 0; j < colunas; j++) {
                    toboga[i][j] = sc.nextInt();
                }
            }

            System.out.println("Tipo de Toboga: " + linhas + " por " + colunas);
            for (int i = 0; i < linhas; i++) {
                for (int j = 0; j < colunas; j++) {
                    System.out.print(toboga[i][j]);
                }
                System.out.print("\n");
            }
            System.out.println("\n");

            sc.close();
            //Retornar a matriz gerada
            return toboga;

        } catch (FileNotFoundException e) {
            System.err.println("Arquivo nao encontrado: " + e.getMessage());
            return null;
        }
    }
    
    public static long contaCaminhos(int[][] toboga) {
        int linhas = toboga.length;
        int colunas = toboga[0].length;
        return contaCaminhoRecursivo(toboga, linhas, colunas, linhas - 1, colunas - 1);
    }
    
    private static long contaCaminhoRecursivo(int[][] toboga, int linhas, int colunas, int i, int j) {
        // Se detectar uma area fora do toboga ou se a parte do toboga e proibida, retorna 0
        if (i < 0 || j < 0 || toboga[i][j] == 1) {
            return 0;
        }
        
        //Final da recursao: topo do toboga.
        if (i == 0 && j ==  0) {
            return 1;
        }
        
        //Comecando do final do toboga, explorar os caminhos para esquerda e para cima.
        long esquerda = contaCaminhoRecursivo(toboga, linhas, colunas, i, j - 1);
        long cima = contaCaminhoRecursivo(toboga, linhas, colunas, i - 1, j);
        
        //Somar as possibilidades de caminhos
        return esquerda + cima;
    }
}