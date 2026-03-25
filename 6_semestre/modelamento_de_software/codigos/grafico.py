import matplotlib.pyplot as plt
import networkx as nx

# Criação do grafo direcionado
G = nx.DiGraph()

# Componentes do sistema
components = [
    "Dispositivos de Coleta de Dados",
    "Aplicativo Móvel/Web",
    "Servidor/Backend",
    "Banco de Dados",
    "Módulo de Análise de Dados",
    "Sistema de Notificações"
]

# Conexões entre os componentes
connections = [
    ("Dispositivos de Coleta de Dados", "Aplicativo Móvel/Web"),
    ("Aplicativo Móvel/Web", "Servidor/Backend"),
    ("Servidor/Backend", "Banco de Dados"),
    ("Servidor/Backend", "Módulo de Análise de Dados"),
    ("Módulo de Análise de Dados", "Sistema de Notificações"),
    ("Sistema de Notificações", "Aplicativo Móvel/Web")
]

# Adicionando nós e arestas ao grafo
G.add_nodes_from(components)
G.add_edges_from(connections)

# Layout hierárquico manual para melhor visualização
pos = {
    "Dispositivos de Coleta de Dados": (0, 3),
    "Aplicativo Móvel/Web": (0, 2),
    "Servidor/Backend": (0, 1),
    "Banco de Dados": (-1, 0),
    "Módulo de Análise de Dados": (1, 0),
    "Sistema de Notificações": (0, -1)
}

# Desenhar o grafo
plt.figure(figsize=(10, 8))
nx.draw(G, pos, with_labels=True, node_color='skyblue', node_size=3000, 
        font_size=10, font_weight='bold', arrowsize=20, edge_color='gray')

plt.title("Diagrama de Componentes do Sistema de Monitoramento de Saúde", fontsize=14)
plt.show()
