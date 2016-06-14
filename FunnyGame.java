import java.util.Scanner;

public class FunnyGame {
    private static int[] lower_edges, result_nodes, visited;
    private static int[][] matriz;
    private static int n, ini;

    public static void main(String[] args) {
        System.out.println("");
        funnyGame();
    }
    public static void funnyGame() {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        if (ini > n || n < 0 || n > 1000) {
            System.out.println("");
            return;
        }
        lower_edges = new int[n];
        result_nodes = new int[n];
        visited = new int[n];
        matriz = new int[n][n];
        ini = sc.nextInt() - 1;
        int node1, node2, edges = 0;
        while (edges < n - 1) {
            node1 = sc.nextInt() - 1;
            node2 = sc.nextInt() - 1;
            matriz[node1][node2] = 1;
            matriz[node2][node1] = 1;
            ++edges;
        }
        System.out.println(result());
    }

    public static String result() {
        evaluate(ini, 0);
        for (int i = 0; i < n; ++i) {
            if (lower_edges[i] != 0) {
                if (lower_edges[i] % 2 == 1) {
                    result_nodes[i] = 1;
                } else {
                    result_nodes[i] = 2;
                }
            }
        }
        int[] a = new int[n];
        visited = a;
        int res = scanMatriz(1, ini);
        int[] initials = initialArr();
        String output = "";
        if (res == 1) {
            for (int i = 1; i < n; ++i) {
                if (initials[i] == 1 && result_nodes[i] == 1) {
                    output = "First player wins flying to airport " + (i + 1);
                    break;
                }
            }
        } else {
            output = "First player loses";
        }
        return output;
    }

    public static int[] initialArr() {
        int[] initials = new int[n];
        for (int i = 0; i < n; ++i) {
            initials[i] = matriz[i][ini];
        }
        return initials;
    }

    public static void evaluate(int ini, int total_edges) {
        if (play(matriz, ini)) {
            visited[ini] = 1;
            ++total_edges;
            for (int i = 0; i < n; ++i) {
                if (matriz[i][ini] == 1 && visited[i] != 1) {
                    evaluate(i, total_edges);
                }
            }
        } else {
            lower_edges[ini] = total_edges;
        }
    }

    public static int scanMatriz(int state, int ini) {
        int next_state;
        if (state == 1) {
            next_state = 2;
        } else {
            next_state = 1;
        }
        if (play(matriz, ini) && result_nodes[ini] == 0) {
            visited[ini] = 1;
            for (int i = 0; i < n; ++i) {
                if (matriz[i][ini] == 1 && visited[i] != 1) {
                    if (state == 1) {
                        if (scanMatriz(next_state, i) == 1) {
                            result_nodes[ini] = 1;
                        } else if (result_nodes[ini] != 1) {
                            result_nodes[ini] = 2;
                        }
                    } else if (scanMatriz(next_state, i) == 2) {
                        result_nodes[ini] = 2;
                    } else if (result_nodes[ini] != 2) {
                        result_nodes[ini] = 1;
                    }
                }
            }
        }
        return result_nodes[ini];
    }

    public static boolean play(int[][] matriz, int ini) {
        for (int i = 0; i < n; ++i) {
            if (matriz[i][ini] == 1 && visited[i] != 1) {
                return true;
            }
        }
        return false;
    }
}
