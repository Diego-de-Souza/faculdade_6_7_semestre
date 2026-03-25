import numpy as np
import matplotlib.pyplot as plt

# ============================
# Parte 1 – Sinal Original
# ============================
x = np.array([0, -2, -1, 0, 1, 2, 3, 0, 0])
n = np.arange(len(x))

plt.figure(figsize=(12, 8))
plt.subplot(2, 2, 1)
plt.stem(n, x, basefmt=" ")
plt.title('Sinal Original x[n]')
plt.xlabel('n')
plt.ylabel('x[n]')

# ============================
# Parte 2 – Transformações
# ============================

# 1. Deslocamento para a direita em 2 unidades: x[n-2]
n_shift = n + 2
plt.subplot(2, 2, 2)
plt.stem(n_shift, x, basefmt=" ")
plt.title('Deslocamento: x[n-2]')
plt.xlabel('n')
plt.ylabel('x[n-2]')

# 2. Reflexão temporal: x[-n]
n_reflect = -n[::-1]
x_reflect = x[::-1]
plt.subplot(2, 2, 3)
plt.stem(n_reflect, x_reflect, basefmt=" ")
plt.title('Reflexão: x[-n]')
plt.xlabel('n')
plt.ylabel('x[-n]')

# 3. Compressão por 2: x[2n]
n_comp = n[n * 2 < len(x)]
x_comp = [x[i * 2] for i in n_comp]
plt.subplot(2, 2, 4)
plt.stem(n_comp, x_comp, basefmt=" ")
plt.title('Compressão: x[2n]')
plt.xlabel('n')
plt.ylabel('x[2n]')

plt.tight_layout()
plt.show()

# ============================
# Parte 3 – Sistemas
# ============================

def sistema1(x):
    x_shifted = np.insert(x[:-1], 0, 0)  # x[n-1], assume zero para n<0
    return x - x_shifted

def sistema2(x):
    return np.cumsum(x)

def sistema3(x):
    y = np.zeros_like(x)
    for i in range(len(x)):
        idx = 2 * i
        if idx < len(x):
            y[i] = x[idx]
    return y

sistemas = {
    "Sistema 1: y[n] = x[n] - x[n-1]": sistema1,
    "Sistema 2: y[n] = soma de x[k] até n": sistema2,
    "Sistema 3: y[n] = x[2n]": sistema3
}

# ============================
# Análise de Propriedades
# ============================

def is_causal(sistema, x):
    impulso = np.zeros_like(x)
    impulso[0] = 1
    resposta = sistema(impulso)
    return np.all(resposta[np.arange(1, len(resposta))] == 0) == False

def has_memory(sistema, x):
    delta = np.zeros_like(x)
    delta[len(x)//2] = 1
    delta_mod = delta.copy()
    delta_mod[len(x)//2] = 2
    return not np.array_equal(sistema(delta), sistema(delta_mod))

def is_stable(sistema, x):
    resposta = sistema(x)
    return np.all(np.abs(resposta) < np.inf)

def is_time_invariant(sistema, x):
    shifted_x = np.roll(x, 1)
    original = sistema(x)
    shifted_output = sistema(shifted_x)
    return np.array_equal(np.roll(original, 1), shifted_output)

def is_linear(sistema, x):
    x1 = x
    x2 = np.roll(x, 1)
    a, b = 2, -1
    left = sistema(a*x1 + b*x2)
    right = a*sistema(x1) + b*sistema(x2)
    return np.allclose(left, right)

# ============================
# Execução e Verificação
# ============================

for nome, sistema in sistemas.items():
    print(f"\n{nome}")
    y = sistema(x)
    print("Saída:", y)

    print("Causal:", "Sim" if is_causal(sistema, x) else "Não")
    print("Com memória:", "Sim" if has_memory(sistema, x) else "Não")
    print("Estável:", "Sim" if is_stable(sistema, x) else "Não")
    print("Invariante no tempo:", "Sim" if is_time_invariant(sistema, x) else "Não")
    print("Linear:", "Sim" if is_linear(sistema, x) else "Não")

    # Plotar o sistema
    plt.stem(n, y, basefmt=" ")
    plt.title(nome)
    plt.xlabel('n')
    plt.ylabel('y[n]')
    plt.grid(True)
    plt.show()
