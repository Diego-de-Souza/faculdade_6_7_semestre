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

## 1. Instalação no Windows: MSYS2, Icarus Verilog e GTKWave

Há duas abordagens comuns: **MSYS2** (um único ambiente com `pacman` para Icarus e GTKWave — útil quando o Winget falha no download) ou **instaladores / Winget** separados.

### 1.1 MSYS2 (recomendado para Icarus + GTKWave)

O [MSYS2](https://www.msys2.org/) fornece um terminal tipo Linux no Windows e o gestor de pacotes **`pacman`**, onde estão o **Icarus Verilog** e o **GTKWave** já empacotados para o ambiente **MINGW64**.

#### Passo 1 — Instalar o MSYS2

1. Descarregue o instalador em: `https://www.msys2.org/` (ficheiro do tipo `msys2-x86_64-….exe`).
2. Execute o instalador. O caminho predefinido costuma ser **`C:\msys64`** (pode alterar, mas anote o caminho).
3. No fim, deixe marcada a opção para abrir o MSYS2 se o instalador oferecer.

#### Passo 2 — Atualizar o sistema de pacotes

Abra o atalho **“MSYS2 MSYS”** (ou **MINGW64**, conforme as instruções do primeiro arranque) e execute:

```bash
pacman -Syu
```

Se pedir para **fechar a janela** e voltar a abrir, faça-o. Repita `pacman -Syu` até não haver mais atualizações obrigatórias.

> **Nota:** podem aparecer avisos `Connection reset by peer` ao sincronizar espelhos. Normalmente basta **tentar de novo** mais tarde ou [ajustar espelhos](https://www.msys2.org/docs/mirror/) se o problema persistir.

#### Passo 3 — Instalar Icarus Verilog e GTKWave

Abra o terminal **“MSYS2 MINGW64”** (menu Iniciar — não confundir com “UCRT64” ou “MSYS” puro para este passo).

Instale os dois pacotes de uma vez:

```bash
pacman -S mingw-w64-x86_64-iverilog mingw-w64-x86_64-gtkwave
```

Confirme com **`Y`** quando o `pacman` perguntar.

#### Passo 4 — Verificar se os comandos existem

Ainda no **MINGW64**:

```bash
iverilog -V
vvp -V
gtkwave --version
```

Os executáveis ficam em **`C:\msys64\mingw64\bin`** (ajuste se instalou o MSYS2 doutro sítio).

#### Passo 5 — Terminal UCRT64 vs MINGW64

Os pacotes `mingw-w64-x86_64-*` instalados acima colocam programas em **`mingw64`**. No terminal **UCRT64**, o comando `iverilog` pode aparecer como **não encontrado** porque o `PATH` desse perfil aponta para **`ucrt64`**, não para **`mingw64`**.

**Soluções (escolha uma):**

- Use sempre **“MSYS2 MINGW64”** para compilar, simular e abrir ondas neste projeto; ou  
- Num terminal UCRT64, só para essa sessão:

  ```bash
  export PATH="/mingw64/bin:$PATH"
  iverilog -V
  ```

- Ou instale as variantes **UCRT** do mesmo software, se existirem no repositório:

  ```bash
  pacman -Ss iverilog
  pacman -Ss gtkwave
  ```

  e instale pacotes com prefixo `mingw-w64-ucrt-x86_64-` em vez de `mingw-w64-x86_64-`, se listados.

#### Passo 6 — Usar Icarus e GTKWave no PowerShell do Windows

Adicione ao **PATH** do Windows (variáveis de ambiente do utilizador ou do sistema):

`C:\msys64\mingw64\bin`

Feche e volte a abrir o **PowerShell**, depois:

```powershell
iverilog -V
gtkwave --version
```

Sem isto, o PowerShell responde que `iverilog` ou `gtkwave` “não é reconhecido”.

---

### 1.2 Icarus Verilog pelo Winget (alternativa ao MSYS2)

No **PowerShell**:

```powershell
winget install --id Icarus.Verilog -e --accept-package-agreements --accept-source-agreements
```

**ID correto:** `Icarus.Verilog`.

Após instalar, **reabra o terminal** e teste:

```powershell
iverilog -V
vvp -V
```

Caminho típico dos executáveis: `C:\Program Files\iverilog\bin` — adicione ao **PATH** se o comando não for encontrado.

#### Se o Winget falhar no download (`InternetOpenUrl` / `0x80072eff`)

O pacote está correto; o problema costuma ser **rede, proxy, antivírus (inspeção HTTPS) ou firewall**.

- Tente **descarregar o instalador no browser** a partir de `https://bleyer.org/icarus/` (instalador **x64**) e execute o `.exe` manualmente; ou  
- Use a instalação via **MSYS2** (secção 1.1).

---

### 1.3 Icarus Verilog — instalador manual (Windows)

1. Abra `https://bleyer.org/icarus/` no navegador.  
2. Descarregue o instalador **x64** mais recente (ex.: `iverilog-v12-…-x64_setup.exe`).  
3. Execute o instalador e confirme que `iverilog` e `vvp` estão no **PATH** (ou adicione `C:\Program Files\iverilog\bin` manualmente).

---

### 1.4 GTKWave sem MSYS2 (instalador oficial)

Se **não** quiser o GTKWave pelo `pacman`:

1. Descarregue o instalador em: `https://gtkwave.sourceforge.net/`  
2. Instale e, se quiser usar na linha de comando, adicione a pasta de instalação do GTKWave ao **PATH** do Windows.

No PowerShell, também pode abrir por **caminho completo**, por exemplo:

```powershell
& "C:\msys64\mingw64\bin\gtkwave.exe" "C:\Users\SEU_USER\Downloads\Von_Neumann\Von_Neumann\Verilog\VerilogBM-210-235.vcd"
```

(Ajuste utilizador e caminho do MSYS2.)

#### Ver ondas no GTKWave (passo obrigatório)

Depois de **Ficheiro → Abrir** o `.vcd`:

1. No painel **SST** (árvore), clique num módulo (ex.: `u_cpu`).  
2. Na lista de **sinais** em baixo, selecione um ou mais sinais (ex.: `clk`, `dbus[7:0]`).  
3. Clique em **Append**.

Só assim as formas de onda aparecem à direita; o GTKWave não desenha sinais que não tenham sido adicionados com **Append**.

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

Na mesma pasta `Verilog` (ou com caminho completo para o `.vcd`):

```powershell
gtkwave VerilogBM-210-235.vcd
```

No terminal **MSYS2 MINGW64** (com GTKWave instalado pela secção 1.1):

```bash
cd "/c/caminho/para/Von_Neumann/Von_Neumann/Verilog"
gtkwave VerilogBM-210-235.vcd
```

Depois de abrir o ficheiro: na árvore **SST** escolha um módulo (ex.: `u_cpu`), selecione sinais na lista inferior e clique em **Append** — ver detalhe na **secção 1.4**.

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
| Instalar MSYS2 + Icarus + GTKWave | Secção **1.1** (`pacman -S mingw-w64-x86_64-iverilog mingw-w64-x86_64-gtkwave` no **MINGW64**) |
| Instalar só Icarus (Winget) | `winget install --id Icarus.Verilog -e` (secção **1.2**) |
| PATH no PowerShell para MSYS2 | Adicionar `C:\msys64\mingw64\bin` (ajustar caminho da instalação) |
| Ir para a pasta de simulação | `cd ...\Von_Neumann\Verilog` |
| Compilar modelo comportamental | Comando `iverilog -o von_neumann.vvp` da seção 3.2 |
| Rodar | `vvp von_neumann.vvp` |
| Ver ondas | `gtkwave VerilogBM-210-235.vcd` → módulo na SST → sinais → **Append** (secção **1.4**) |
| Mudar o programa | Editar `Verilog/Behavioral Model/RAM.v`, salvar, recompilar |
| Abrir esquemático | Logisim Evolution → abrir `arc_von_neumann.circ` ou `ALU.circ` |

---

## 8. Créditos e contexto

Projeto educacional de CPU Von Neumann de 8 bits (README com instalação de **MSYS2**, **Icarus Verilog**, **GTKWave**, comandos de simulação e uso da RAM). Contribuidores citados no código-fonte Verilog incluem Praveen Kumar Gupta e Durvesh Bhalekar.
