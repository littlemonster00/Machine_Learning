// Phan chia doi bong (Do Thi Phan Doi)
#include <stdio.h>

#define white -1
#define  red 0
#define  green 1

int color[100];
int fail;

// List
typedef struct {
    int data[100];
    int size;
} List;

void make_null_list(List* L) {
    L->size = 0;
}

void push_back(List* L, int x) {
    L->data[L->size] = x;
    ++L->size;
}

int element_at(List* L, int i) {
    return L->data[i - 1];
}

// Graph
typedef struct {
    int A[100][100];
    int n;
} Graph;

void init_graph(Graph* G, int n) {
    G->n = n;

    int i, j;

    for (i = 1; i <= n; ++i) {
        for (j = 1; j <= n; ++j) {
            G->A[i][j] = 0;
        }
    }
}

void add_egde(Graph* G, int x, int y) {
    G->A[x][y] = 1;
    G->A[y][x] = 1;
}

int adjacent(Graph* G, int x, int y) {
    return G->A[x][y];
}

List neighbors(Graph* G, int x) {
    int y;
    List list;

    make_null_list(&list);

    for (y = 1; y <= G->n; ++y) {
        if (adjacent(G, x, y)) {
            push_back(&list, y);
        }
    }

    return list;
}

void colorize(Graph* G, int x, int c) {
    if (color[x] == white) {
        color[x] = c;

        List list = neighbors(G, x);

        int i;
        for (i = 1; i <=  list.size; ++i) {
            int y = element_at(&list, i);
            colorize(G, y, -c);
        }
    } else {
        if (color[x] != c) {
            fail = 1;
        }
    }
}

int is_bigraph(Graph* G) {
    int i;
    for (i = 1; i <= G->n; ++i) {
        color[i] = white;
    }

    fail = 0;

    colorize(G, 1, green);

    return !fail;
}

int main() {
    //freopen("dt.txt", "r", stdin);

    Graph G;
    int n, m, i, x, y;

    scanf("%d%d", &n, &m);

    init_graph(&G, n);

    for (i = 1; i <= m; ++i) {
        scanf("%d%d", &x, &y);

        add_egde(&G, x, y);
    }

    if (is_bigraph(&G)) {
        for (i = 1; i <= n; ++i) {
            if (color[i] == color[1]) {
                printf("%d ", i);
            }
        }
        printf("\n");

        for (i = 1; i <= n; ++i) {
            if (color[i] != color[1]) {
                printf("%d ", i);
            }
        }
        printf("\n");
    } else {
        printf("IMPOSSIBLE");
    }

    return 0;
}
