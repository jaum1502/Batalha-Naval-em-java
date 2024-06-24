import java.util.Random;
import java.util.Scanner;

public class BatalhaNaval {
    public BatalhaNaval() {
        Scanner entrada = new Scanner(System.in); // entrada de dados

        Random aleat = new Random(); // gerador de números aleatórios

        int[][] matriz2 = new int[8][8]; // Matriz 8x8 (tabuleiro)

        espacoBranco(); // Imprimir linhas em branco

        System.out.println("O computador está posicionando seus navios...");

        int qtdPC = 0; // Contador de navios do computador
        int qtdPCNova = 0; // Variável para contar navios do computador
        int qtdJogadas = 0; // Contador de jogadas realizadas pelo jogador

        qtdPCNova = barco(aleat, matriz2, qtdPCNova); // Posiciona os navios do computador
        qtdPC = qtdPCNova;

        System.out.println("Mapa:");
        impressaoMatrizes(matriz2, qtdPC, qtdPCNova); // Imprime o estado atual do tabuleiro

        // Loop principal do jogo
        do {
            System.out.print("\nDiga onde você deseja procurar um barco inimigo: \nLinha: ");
            int missilL = entrada.nextInt(); // linha digitada pelo jogador
            System.out.print("Coluna: ");
            int missilC = entrada.nextInt(); // coluna digitada pelo jogador
            if (missilL < 0 || missilC < 0 || missilL > 7 || missilC > 7) {
                espacoBranco();
                System.out.println("Algum dos valores inseridos é inválido, tente novamente. \n");
                System.out.println("Mapa: ");
                impressaoMatrizes(matriz2, qtdPC, qtdPCNova);
            } else if (matriz2[missilL][missilC] == 3 || matriz2[missilL][missilC] == 2) {
                espacoBranco();

                System.out.println("Você já atacou nessa casa antes. Vamos tentar outro lugar. \n");
                System.out.println("Mapa: ");
                impressaoMatrizes(matriz2, qtdPC, qtdPCNova);
            } else {
                espacoBranco(); // Limpa a tela
                qtdPCNova = procurarBarco(matriz2, missilL, missilC, qtdPCNova); // Verifica se acertou um navio
                if (qtdPC == qtdPCNova) {
                    System.out.println("Você errou... \nMapa: ");
                } else {
                    System.out.println("Você acertou! \nMapa:");
                }
                impressaoMatrizes(matriz2, qtdPC, qtdPCNova); // Imprime o tabuleiro atualizado
                qtdPC = qtdPCNova;
                qtdJogadas++;
            }
        } while (qtdPC > 0 && qtdJogadas < 30); // Continua até que o jogador ganhe ou atinja o limite de jogadas (30 é
                                                // o limite)
        if (qtdPC == 0) {
            System.out.println("Parabéns! Você afundou todos os navios inimigos e ganhou!");
        } else {
            System.out.println("Que pena... Você atingiu o limite de jogadas e perdeu.");
        }
        entrada.close();

    }

    // Método para imprimir o tabuleiro com cores diferentes para cada tipo de
    // célula
    public void impressaoMatrizes(int matriz2[][], int qtdPC, int qtdPCNova) {
        // Imprime os números das colunas
        System.out.print("   ");
        for (int coluna = 0; coluna < matriz2[0].length; coluna++) {
            System.out.print(coluna + " ");
        }
        System.out.println();

        // Imprime as linhas com seus respectivos números e o conteúdo das células
        for (int linha = 0; linha < matriz2.length; linha++) {
            // Imprime o número da linha
            System.out.print(linha + "  ");

            for (int coluna = 0; coluna < matriz2[linha].length; coluna++) {
                if (matriz2[linha][coluna] == 2) {
                    System.out.print("\u001b[1;31m" + "X " + "\u001b[0m"); // X vermelho para acertos
                } else if (matriz2[linha][coluna] == 3) {
                    System.out.print("\u001b[1;36m" + "0 " + "\u001b[0m"); // ~ ciano para erro
                } else {
                    System.out.print("\u001b[1;34m" + "~ " + "\u001b[0m"); // ~ azul para água
                }
            }
            System.out.println();
        }
    }

    // Método para posicionar os navios do computador no tabuleiro
    public int barco(Random aleat, int matriz2[][], int qtdPCNova) {
        for (int i = 0; i < 10; i++) {
            int linha = aleat.nextInt(8);
            int coluna = aleat.nextInt(8);
            if (matriz2[linha][coluna] == 1) {
                i--; // Se já houver navio na posição, tenta novamente
            } else {
                matriz2[linha][coluna] = 1; // Coloca um navio na posição aleatória
                qtdPCNova++;
            }
        }
        return qtdPCNova; // Retorna a quantidade total de navios do computador
    }

    // Método para verificar se o jogador acertou um navio inimigo
    public int procurarBarco(int matriz2[][], int missilL, int missilC, int qtdPCNova) {
        if (matriz2[missilL][missilC] == 1) {
            matriz2[missilL][missilC] = 2;
            qtdPCNova--; // Decrementa a quantidade de navios do computador
        } else {
            matriz2[missilL][missilC] = 3; // Marca como erro (3)
        }
        return qtdPCNova; // Retorna a quantidade atualizada de navios do computador
    }

    // Método para imprimir espaços em branco (simula limpeza da tela)
    public void espacoBranco() {

        // Cria várias linhas em branco para limpar do terminal
        for (int i = 0; i < 10; i++) {
            System.out.println("");
        }
    }

    // Método main para iniciar o jogo
    public static void main(String[] args) {
        new BatalhaNaval(); // puxa o construtor
    }
}
