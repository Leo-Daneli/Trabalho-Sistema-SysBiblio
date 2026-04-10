static LivroService livroService = new LivroService();

static void main() {
    int opcao;

    do {
        IO.println("=================================");
        IO.println("            SYSBIBLIO            ");
        IO.println("=================================");
        IO.println("1 - Cadastrar livro");
        IO.println("2 - Listar livros");
        IO.println("3 - Pesquisar livros");
        IO.println("4 - Remover livro");
        IO.println("5 - Editar livro");
        IO.println("6 - Listar livros ordenados");
        IO.println("0 - Sair");
        IO.println("=================================");

        opcao = lerInteiro("Escolha uma opção: ");
        IO.println();

        switch (opcao) {
            case 1:
                cadastrarLivro();
                break;
            case 2:
                listarLivros(livroService.listarLivros(), "ACERVO CADASTRADO");
                break;
            case 3:
                pesquisarLivros();
                break;
            case 4:
                removerLivro();
                break;
            case 5:
                editarLivro();
                break;
            case 6:
                listarLivrosOrdenados();
                break;
            case 0:
                IO.println("Encerrando o SysBiblio...");
                break;
            default:
                IO.println("Opção inválida.");
        }

        if (opcao != 0) {
            IO.println();
            IO.readln("Pressione ENTER para continuar...");
            IO.println();
        }
    } while (opcao != 0);
}

static void cadastrarLivro() {
    String titulo = lerTextoObrigatorio("Título: ");
    String autor = lerTextoObrigatorio("Autor: ");
    int ano = lerAnoValido("Ano de publicação: ");
    int paginas = lerInteiroPositivo("Número de páginas: ");

    boolean cadastrou = livroService.adicionarLivro(titulo, autor, ano, paginas);

    if (cadastrou) {
        IO.println("Livro cadastrado com sucesso.");
    } else {
        IO.println("Não foi possível cadastrar o livro.");
    }
}

static void pesquisarLivros() {
    if (!livroService.temLivros()) {
        IO.println("Não há livros cadastrados para pesquisar.");
        return;
    }

    IO.println("1 - Pesquisar por título");
    IO.println("2 - Pesquisar por autor");
    IO.println("3 - Pesquisar por ano");

    int tipoPesquisa = lerInteiro("Escolha o tipo de pesquisa: ");
    List<Livro> resultados = new ArrayList<>();

    switch (tipoPesquisa) {
        case 1:
            String titulo = lerTextoObrigatorio("Digite o título ou parte dele: ");
            resultados = livroService.pesquisarPorTitulo(titulo);
            listarLivros(resultados, "RESULTADO DA PESQUISA POR TÍTULO");
            break;
        case 2:
            String autor = lerTextoObrigatorio("Digite o autor ou parte dele: ");
            resultados = livroService.pesquisarPorAutor(autor);
            listarLivros(resultados, "RESULTADO DA PESQUISA POR AUTOR");
            break;
        case 3:
            int ano = lerAnoValido("Digite o ano de publicação: ");
            resultados = livroService.pesquisarPorAno(ano);
            listarLivros(resultados, "RESULTADO DA PESQUISA POR ANO");
            break;
        default:
            IO.println("Tipo de pesquisa inválido.");
    }
}

static void removerLivro() {
    if (!livroService.temLivros()) {
        IO.println("Não há livros cadastrados para remover.");
        return;
    }

    listarLivros(livroService.listarLivros(), "LIVROS CADASTRADOS");
    int indice = lerIndiceLivro("Digite o número do livro que deseja remover: ");

    boolean removeu = livroService.removerLivro(indice);

    if (removeu) {
        IO.println("Livro removido com sucesso.");
    } else {
        IO.println("Não foi possível remover o livro.");
    }
}

static void editarLivro() {
    if (!livroService.temLivros()) {
        IO.println("Não há livros cadastrados para editar.");
        return;
    }

    listarLivros(livroService.listarLivros(), "LIVROS CADASTRADOS");
    int indice = lerIndiceLivro("Digite o número do livro que deseja editar: ");

    String novoTitulo = lerTextoObrigatorio("Novo título: ");
    String novoAutor = lerTextoObrigatorio("Novo autor: ");
    int novoAno = lerAnoValido("Novo ano de publicação: ");
    int novasPaginas = lerInteiroPositivo("Novo número de páginas: ");

    boolean editou = livroService.editarLivro(indice, novoTitulo, novoAutor, novoAno, novasPaginas);

    if (editou) {
        IO.println("Livro editado com sucesso.");
    } else {
        IO.println("Não foi possível editar o livro.");
    }
}

static void listarLivrosOrdenados() {
    if (!livroService.temLivros()) {
        IO.println("Não há livros cadastrados para ordenar.");
        return;
    }

    IO.println("1 - Ordenar por título");
    IO.println("2 - Ordenar por ano");

    int tipoOrdenacao = lerInteiro("Escolha o tipo de ordenação: ");

    switch (tipoOrdenacao) {
        case 1:
            listarLivros(livroService.listarOrdenadoPorTitulo(), "ACERVO ORDENADO POR TÍTULO");
            break;
        case 2:
            listarLivros(livroService.listarOrdenadoPorAno(), "ACERVO ORDENADO POR ANO");
            break;
        default:
            IO.println("Tipo de ordenação inválido.");
    }
}

static void listarLivros(List<Livro> livros, String tituloSecao) {
    IO.println(tituloSecao);

    if (livros.isEmpty()) {
        IO.println("Nenhum livro encontrado.");
        return;
    }

    for (int i = 0; i < livros.size(); i++) {
        IO.println((i + 1) + " - " + livros.get(i));
    }

    IO.println("Total de livros: " + livros.size());
}

static int lerIndiceLivro(String mensagem) {
    while (true) {
        int numero = lerInteiro(mensagem);
        int indice = numero - 1;

        if (livroService.indiceValido(indice)) {
            return indice;
        }

        IO.println("Número de livro inválido.");
    }
}

static String lerTexto(String mensagem) {
    String texto = IO.readln(mensagem);

    if (texto == null) {
        return "";
    }

    return texto.trim();
}

static String lerTextoObrigatorio(String mensagem) {
    while (true) {
        String texto = lerTexto(mensagem);

        if (!texto.isEmpty()) {
            return texto;
        }

        IO.println("Campo obrigatório. Digite um valor válido.");
    }
}

static int lerInteiro(String mensagem) {
    while (true) {
        String texto = lerTexto(mensagem);

        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            IO.println("Digite um número inteiro válido.");
        }
    }
}

static int lerInteiroPositivo(String mensagem) {
    while (true) {
        int valor = lerInteiro(mensagem);

        if (valor > 0) {
            return valor;
        }

        IO.println("Digite um número maior que zero.");
    }
}

static int lerAnoValido(String mensagem) {
    while (true) {
        int ano = lerInteiro(mensagem);

        if (ano > 0 && ano <= Year.now().getValue()) {
            return ano;
        }

        IO.println("Digite um ano válido.");
    }
}

static class Livro {
    private String titulo;
    private String autor;
    private int anoPublicacao;
    private int numeroPaginas;

    Livro(String titulo, String autor, int anoPublicacao, int numeroPaginas) {
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
        this.numeroPaginas = numeroPaginas;
    }

    String getTitulo() {
        return titulo;
    }

    void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    String getAutor() {
        return autor;
    }

    void setAutor(String autor) {
        this.autor = autor;
    }

    int getAnoPublicacao() {
        return anoPublicacao;
    }

    void setAnoPublicacao(int anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    int getNumeroPaginas() {
        return numeroPaginas;
    }

    void setNumeroPaginas(int numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    @Override
    public String toString() {
        return "Título: " + titulo
                + " | Autor: " + autor
                + " | Ano: " + anoPublicacao
                + " | Páginas: " + numeroPaginas;
    }
}

static class LivroService {
    private final List<Livro> livros = new ArrayList<>();

    boolean adicionarLivro(String titulo, String autor, int anoPublicacao, int numeroPaginas) {
        if (!textoValido(titulo)) {
            return false;
        }

        if (!textoValido(autor)) {
            return false;
        }

        if (!anoValido(anoPublicacao)) {
            return false;
        }

        if (!numeroPaginasValido(numeroPaginas)) {
            return false;
        }

        Livro livro = new Livro(
                normalizarTexto(titulo),
                normalizarTexto(autor),
                anoPublicacao,
                numeroPaginas
        );

        livros.add(livro);
        return true;
    }

    List<Livro> listarLivros() {
        return new ArrayList<>(livros);
    }

    List<Livro> pesquisarPorTitulo(String titulo) {
        List<Livro> resultados = new ArrayList<>();

        if (!textoValido(titulo)) {
            return resultados;
        }

        String tituloPesquisa = normalizarTexto(titulo).toLowerCase();

        for (Livro livro : livros) {
            if (livro.getTitulo().toLowerCase().contains(tituloPesquisa)) {
                resultados.add(livro);
            }
        }

        return resultados;
    }

    List<Livro> pesquisarPorAutor(String autor) {
        List<Livro> resultados = new ArrayList<>();

        if (!textoValido(autor)) {
            return resultados;
        }

        String autorPesquisa = normalizarTexto(autor).toLowerCase();

        for (Livro livro : livros) {
            if (livro.getAutor().toLowerCase().contains(autorPesquisa)) {
                resultados.add(livro);
            }
        }

        return resultados;
    }

    List<Livro> pesquisarPorAno(int anoPublicacao) {
        List<Livro> resultados = new ArrayList<>();

        if (!anoValido(anoPublicacao)) {
            return resultados;
        }

        for (Livro livro : livros) {
            if (livro.getAnoPublicacao() == anoPublicacao) {
                resultados.add(livro);
            }
        }

        return resultados;
    }

    boolean removerLivro(int indice) {
        if (!indiceValido(indice)) {
            return false;
        }

        livros.remove(indice);
        return true;
    }

    boolean editarLivro(int indice, String novoTitulo, String novoAutor, int novoAnoPublicacao, int novoNumeroPaginas) {
        if (!indiceValido(indice)) {
            return false;
        }

        if (!textoValido(novoTitulo)) {
            return false;
        }

        if (!textoValido(novoAutor)) {
            return false;
        }

        if (!anoValido(novoAnoPublicacao)) {
            return false;
        }

        if (!numeroPaginasValido(novoNumeroPaginas)) {
            return false;
        }

        Livro livro = livros.get(indice);
        livro.setTitulo(normalizarTexto(novoTitulo));
        livro.setAutor(normalizarTexto(novoAutor));
        livro.setAnoPublicacao(novoAnoPublicacao);
        livro.setNumeroPaginas(novoNumeroPaginas);

        return true;
    }

    List<Livro> listarOrdenadoPorTitulo() {
        List<Livro> copia = listarLivros();

        copia.sort(
                Comparator.comparing(Livro::getTitulo, String.CASE_INSENSITIVE_ORDER)
                          .thenComparing(Livro::getAutor, String.CASE_INSENSITIVE_ORDER)
        );

        return copia;
    }

    List<Livro> listarOrdenadoPorAno() {
        List<Livro> copia = listarLivros();

        copia.sort(
                Comparator.comparingInt(Livro::getAnoPublicacao)
                          .thenComparing(Livro::getTitulo, String.CASE_INSENSITIVE_ORDER)
        );

        return copia;
    }

    boolean indiceValido(int indice) {
        return indice >= 0 && indice < livros.size();
    }

    boolean temLivros() {
        return !livros.isEmpty();
    }

    private boolean textoValido(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }

    private boolean anoValido(int ano) {
        return ano > 0 && ano <= Year.now().getValue();
    }

    private boolean numeroPaginasValido(int numeroPaginas) {
        return numeroPaginas > 0;
    }

    private String normalizarTexto(String texto) {
        return texto.trim();
    }
}