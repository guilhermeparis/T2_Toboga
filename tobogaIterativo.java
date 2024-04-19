import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.Scanner;

public class tobogaIterativo {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java tobogaIterativo <filename.txt>");
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
        }

        catch (FileNotFoundException e) {
            System.err.println("Arquivo nao encontrado: " + e.getMessage());
           return null;
        }
    }
    
    public static BigInteger contaCaminhos(int[][] toboga) {
        int linhas = toboga.length;
        int colunas = toboga[0].length;
        BigInteger [][] memo = new BigInteger[linhas][colunas];
        
        //Inicializando o vetor de memorizacao com zeros.
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                memo[i][j] = BigInteger.ZERO;
            }
        }

        //Caso base: topo do toboga.
        memo[0][0] = BigInteger.ONE;

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                // Pular o calculo do inicio do toboga pois ja esta na memoria.
                if (i == 0 && j == 0) {
                    continue;
                }
                
                // Pular as areas proibidas.
                if (toboga[i][j] == 1) {
                    continue;
                }

                // Calcular o numero de caminhos pelas areas adjacentes.
                BigInteger baixo = (i > 0) ? memo[i - 1][j] : BigInteger.ZERO;
                BigInteger direita = (j > 0) ? memo[i][j - 1] : BigInteger.ZERO;
                memo[i][j] = baixo.add(direita);
            }
        }

        // Retorna o valor no final do vetor
        return memo[linhas - 1][colunas - 1];
    }
}