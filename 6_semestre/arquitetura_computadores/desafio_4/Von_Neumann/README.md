# CPU Von Neumann de 8 bits

Este repositório contém uma CPU de **8 bits** no estilo **arquitetura Von Neumann**: programa e dados compartilham a mesma memória (RAM). O material está em **dois formatos**:

- **Logisim** (`.circ`): esquemático para explorar a estrutura visualmente.
- **Verilog** (`Verilog/`): simulação com **Icarus Verilog** (compilador `iverilog` e executor `vvp`), com opção de visualizar sinais no **GTKWave**.

---

## Estrutura do projeto

Coloque-se na pasta que contém este `README.md` (normalmente `...\Von_Neumann\Von_Neumann\`).

| Caminho | Conteúdo |
|--------|----------|
| `Instruc_Set.png` | Tabela do **conjunto de instruções** (codificação em 8 bits). |
| `arc_von_neumann.circ` | Arquitetura principal no Logisim. |
| `ALU.circ` | ULA no Logisim. |
| `log.vcd` | Exemplo de arquivo de ondas (pode ser sobrescrito pela simulação). |
| `RAM-hex-input-Add-Sample/` | Material de referência relacionado a entrada na RAM (exemplo de uso). |
| `Verilog/Testbench.v` | Bancada de teste: clock, tempo de simulação, geração do `.vcd`. |
| `Verilog/Behavioral Model/` | Módulos Verilog do modelo **comportamental** (inclui `RAM.v`, `CPU-Behavioural.v`, etc.). |
| `Verilog/CPU-Dataflow.v` | Modelo alternativo em **um único arquivo** (fluxo de dados). |

---

## Funcionalidades

O processador executa instruções definidas pelo conjunto documentado em `Instruc_Set.png`. Essas instruções são armazenadas na **RAM** (no Verilog, valores iniciais em `RAM.v`).

Um ciclo de instrução é dividido em três fases: **Buscar**, **Decodificar** e **Executar**.

A unidade de controle, ao decodificar, aciona sinais conforme o tipo de instrução, por exemplo:

- carregar o registrador de imediato;
- escrever no banco de registradores;
- alterar o PC (contador de programa);
- selecionar endereço a partir de imediato ou PC;
- selecionar registradores no arquivo de registradores;
- definir a operação da ULA;
- encaminhar a saída da ULA para o destino adequado.

**Entrada de dados na simulação Verilog:** não há teclado nem leitura automática de arquivo por padrão. O “programa” entra pela **inicialização da RAM** em `RAM.v` (bloco `initial`) e, durante a execução, pela **própria CPU** (escritas na RAM), conforme o código carregado.

---

## Requisitos de software

| Ferramenta | Uso |
|------------|-----|
| **Logisim Evolution** | Abrir e simular os arquivos `.circ`. |
| **Icarus Verilog** | Comandos `iverilog` e `vvp` para compilar e rodar o Verilog. |
| **GTKWave** (opcional) | Abrir o arquivo `.vcd` e inspecionar sinais. |

---

## 1. Instalação do Icarus Verilog (Windows)

### 1.1 Pelo Winget (recomendado)

Abra o **PowerShell** ou **Terminal** e execute:

```powershell
winget install --id Icarus.Verilog -e --accept-package-agreements --accept-source-agreements
```

**Importante:** o identificador correto no catálogo atual é `Icarus.Verilog`. Se usar outro ID (por exemplo nomes antigos de pacotes), o Winget pode responder que **nenhum pacote** foi encontrado.

Após a instalação:

1. **Feche e abra de novo** o terminal (para atualizar o `PATH`).
2. Confira se os comandos existem:

```powershell
iverilog -V
vvp -V
```

Se aparecer “não é reconhecido como nome de cmdlet…”, o `PATH` pode não incluir a pasta do Icarus. Em instalações típicas no Windows, os executáveis ficam em:

`C:\Program Files\iverilog\bin`

Adicione essa pasta ao **PATH** do sistema (Configurações → Sistema → Sobre → Configurações avançadas do sistema → Variáveis de ambiente) ou reabra o instalador e verifique opções relacionadas ao PATH.

### 1.2 Instalação manual (se o Winget falhar no download)

Se o Winget exibir erro de rede (`InternetOpenUrl` / falha ao baixar), baixe o instalador no site do mantenedor do build para Windows, por exemplo:

`https://bleyer.org/icarus/`

Procure o instalador **x64** mais recente (ex.: `iverilog-v12-…-x64_setup.exe`), execute-o e depois confira o `PATH` como acima.

### 1.3 GTKWave (visualização de ondas)

O Winget pode não listar o GTKWave na sua região. Opções:

- Baixe em: `https://gtkwave.sourceforge.net/`
- Ou use outro instalador que você já utilize (Chocolatey, MSYS2, etc.), desde que o comando `gtkwave` fique disponível no terminal.

---

## 2. Instalação do Logisim Evolution (Windows)

```powershell
winget install --id logisim-evolution.logisim-evolution -e --accept-package-agreements --accept-source-agreements
```

Depois, abra o Logisim e use **Arquivo → Abrir** nos arquivos:

- `arc_von_neumann.circ` — visão geral da arquitetura;
- `ALU.circ` — ULA.

Ative o modo de **simulação** na interface do Logisim para “ligar” o circuito e usar pinos/chaves conforme o desenho do projeto.

---

## 3. Simulação Verilog (Icarus): passo a passo

Os arquivos `VerilogBM-210-235.v` e `Verilog-210-235.v` mencionados em versões antigas da documentação **não existem** neste pacote. O fluxo correto é compilar **todos** os módulos do modelo comportamental mais o `Testbench.v`.

### 3.1 Abrir o terminal na pasta certa

No PowerShell:

```powershell
cd "C:\caminho\para\a\pasta\Von_Neumann\Verilog"
```

(Ajuste o caminho: deve ser a pasta `Verilog` que fica **ao lado** deste `README.md`.)

### 3.2 Compilar

**PowerShell (Windows)** — uma linha:

```powershell
iverilog -o von_neumann.vvp "Behavioral Model\ALU.v" "Behavioral Model\Control-Unit.v" "Behavioral Model\Mux-Demux.v" "Behavioral Model\RAM.v" "Behavioral Model\Register-File.v" "Behavioral Model\CPU-Behavioural.v" Testbench.v
```

**Linux ou macOS** (barra `/` e ordem equivalente):

```bash
iverilog -o von_neumann.vvp \
  "Behavioral Model/ALU.v" \
  "Behavioral Model/Control-Unit.v" \
  "Behavioral Model/Mux-Demux.v" \
  "Behavioral Model/RAM.v" \
  "Behavioral Model/Register-File.v" \
  "Behavioral Model/CPU-Behavioural.v" \
  Testbench.v
```

Se não houver mensagens de erro, será gerado o executável de simulação `von_neumann.vvp`.

### 3.3 Executar a simulação

```powershell
vvp von_neumann.vvp
```

Você deve ver mensagens de texto (`$display`) no terminal. O testbench também gera o arquivo de ondas:

`VerilogBM-210-235.vcd`

### 3.4 Ver as ondas no GTKWave

Na mesma pasta `Verilog`:

```powershell
gtkwave VerilogBM-210-235.vcd
```

No GTKWave, expanda a hierarquia, selecione sinais de interesse e use **Append** para plotá-los.

### 3.5 Duração da simulação

Em `Testbench.v`, o tempo total está controlado por um atraso antes de `$finish` (por exemplo `#100`). Se a CPU não tiver tempo suficiente para completar instruções, **aumente** esse valor (por exemplo `#5000`), salve, **recompile** e rode `vvp` de novo.

---

## 4. Como abrir e alterar as instruções em `RAM.v`

O programa (instruções e constantes iniciais na memória) é definido no módulo **RAM**, arquivo:

`Verilog/Behavioral Model/RAM.v`

### 4.1 Como abrir o arquivo

- **Cursor / VS Code:** `Ctrl+P`, digite `RAM.v` e escolha `Verilog/Behavioral Model/RAM.v`, ou abra pela árvore de pastas: `Verilog` → `Behavioral Model` → `RAM.v`.
- **Explorador de arquivos do Windows:** navegue até a pasta do projeto e abra o arquivo com duplo clique (pode abrir no Cursor ou no editor de texto que preferir).

### 4.2 O que editar

Dentro de `RAM.v`, localize o bloco `initial` marcado com o comentário:

`//******************Write your program here**********//`

Cada linha tem o formato:

```verilog
ram[endereco] <= 8'bbbbbbbbb;
```

- **`endereco`:** de `0` a `255` (8 bits de endereço).
- **`8'bbbbbbbbb`:** **8 bits** da instrução ou dado, no formato binário. A codificação deve seguir **`Instruc_Set.png`**.

Substitua ou acrescente linhas conforme seu programa. As instruções de exemplo que já vêm no arquivo podem ser **totalmente sobrescritas**.

### 4.3 Depois de mudar a RAM

1. Salve `RAM.v` (`Ctrl+S`).
2. **Recompile** com `iverilog` (seção 3.2).
3. **Execute** com `vvp` (seção 3.3).

Sem recompilar, a simulação continua usando o executável antigo.

### 4.4 Dica: carregar memória a partir de arquivo (avançado)

Se quiser evitar colar centenas de linhas à mão, você pode pesquisar pela tarefa do Verilog **`$readmemh`** ou **`$readmemb`** e carregar um arquivo `.hex` / `.mem` no `initial` da RAM. Isso exige editar `RAM.v` (ou o testbench) com cuidado para manter a sintaxe válida no Icarus.

---

## 5. Modelo em fluxo de dados (`CPU-Dataflow.v`)

Existe um modelo alternativo **monolítico** em `Verilog/CPU-Dataflow.v`, cujo módulo principal se chama **`cpu`** (nome em minúsculas).

Para usá-lo em vez do modelo comportamental:

1. Em `Testbench.v`, troque a instância de `CPU u_cpu(clk)` para `cpu u_cpu(clk);` (ajuste o nome do módulo exatamente como em `CPU-Dataflow.v`).
2. Compile **apenas** `CPU-Dataflow.v` + `Testbench.v` (não inclua ao mesmo tempo os `.v` da pasta `Behavioral Model`, para evitar módulos duplicados).

Exemplo de compilação:

```powershell
iverilog -o von_neumann_df.vvp CPU-Dataflow.v Testbench.v
vvp von_neumann_df.vvp
```

(Ajuste o testbench antes, conforme o item 1.)

---

## 6. Conjunto de instruções

Consulte sempre a figura:

![Instruction Set](Instruc_Set.png)

Cada instrução válida deve ser mapeada para **8 bits** coerentes com essa tabela ao preencher `ram[...]` em `RAM.v`.

---

## 7. Resumo rápido (cheat sheet)

| Objetivo | Ação |
|----------|------|
| Instalar simulador Verilog (Windows) | `winget install --id Icarus.Verilog -e` |
| Ir para a pasta de simulação | `cd ...\Von_Neumann\Verilog` |
| Compilar modelo comportamental | Comando `iverilog -o von_neumann.vvp` da seção 3.2 |
| Rodar | `vvp von_neumann.vvp` |
| Ver ondas | `gtkwave VerilogBM-210-235.vcd` |
| Mudar o programa | Editar `Verilog/Behavioral Model/RAM.v`, salvar, recompilar |
| Abrir esquemático | Logisim Evolution → abrir `arc_von_neumann.circ` ou `ALU.circ` |

---

## 8. Créditos e contexto

Projeto educacional de CPU Von Neumann de 8 bits (README expandido para instalação, comandos corretos do Icarus e uso detalhado da RAM e da simulação). Contribuidores citados no código-fonte Verilog incluem Praveen Kumar Gupta e Durvesh Bhalekar.
