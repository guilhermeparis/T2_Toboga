import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.Scanner;

public class tobogaRecursivoMemo {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java tobogaRecursivoMemo <filename.txt>");
            return;
        }

        //Caminho do arquivo que contem a matriz.
    	String nomeArquivo = args[0];

        //Ler matriz do arquivo
        int[][] toboga = constroiMatriz(nomeArquivo);


        if (toboga != null) {
            BigInteger caminhos = contaCaminhos(toboga);
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
    
    public static BigInteger contaCaminhos(int[][] toboga) {
        int linhas = toboga.length;
        int colunas = toboga[0].length;
        BigInteger [][] memo = new BigInteger[linhas][colunas];
        return contaCaminhoRecursivo(toboga, memo, linhas, colunas, linhas - 1, colunas - 1);
    }
    
    private static BigInteger contaCaminhoRecursivo(int[][] toboga, BigInteger[][] memo, int linhas, int colunas, int i, int j) {
          // Se detectar uma area fora do toboga ou se a parte do toboga e proibida, retorna 0
          if (i < 0 || j < 0 || toboga[i][j] == 1) {
            return BigInteger.ZERO;
        }
        
        //Final da recursao: topo do toboga.
        if (i == 0 && j == 0) {
            return BigInteger.ONE;
        }

        //Vetor de memorizacao
        if (memo[i][j] != null) {
            return memo[i][j];
        }

        //Comecando do final do toboga, explorar os caminhos para esquerda e para cima.
        BigInteger esquerda = contaCaminhoRecursivo(toboga, memo, linhas, colunas, i, j - 1);
        BigInteger cima = contaCaminhoRecursivo(toboga, memo, linhas, colunas, i - 1, j);

        // Memorizar o resultado
        memo[i][j] = esquerda.add(cima);
        
        return memo[i][j];
    }
}