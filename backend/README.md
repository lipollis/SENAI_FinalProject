
## Criação dos enums: Perfil, Prioridade e Status
#### São um conjunto de valores constantes e pré-definidos.
```java
public enum Perfil {
    ADMIN(0, "ROLE_ADMIN"), CLIENTE(1, "ROLE_CLIENTE"), TECNICO(2, "ROLE_TECNICO");

    // ATRIBUTOS
    private Integer codigo;
    private String descricao;

    // CONSTRUTOR
    Perfil(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    // GETTERS
    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    // MÉTODO STATIC PARA NÃO PRECISAR CRIAR UMA INSTÂNCIA
    // DE PERFIL PARA CHAMAR ESTE MÉTODO EM OUTRAS PARTES DO CÓDIGO
    public static Perfil toEnum(Integer cod){
        if(cod == null){
            return null;
        }
        for(Perfil x : Perfil.values()){
            if(cod.equals(x.getCodigo())){
                return x;
            }
        }
        throw new IllegalArgumentException("Perfil inválido");
    }
}
```

```java
public enum Prioridade {
    BAIXA(0, "BAIXA"), MEDIA(1, "MEDIA"), ALTA(2, "ALTA");

    // ATRIBUTOS
    private Integer codigo;
    private String descricao;

    // CONSTRUTOR
    Prioridade(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    // GETTERS
    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    // MÉTODO STATIC PARA NÃO PRECISAR CRIAR UMA INSTÂNCIA
    // DE PERFIL PARA CHAMAR ESTE MÉTODO EM OUTRAS PARTES DO CÓDIGO
    public static Prioridade toEnum(Integer cod){
        if(cod == null){
            return null;
        }
        for(Prioridade x : Prioridade.values()){
            if(cod.equals(x.getCodigo())){
                return x;
            }
        }
        throw new IllegalArgumentException("Prioridade inválida");
    }
}
```

```java
public enum Status {
    ABERTO(0, "ABERTO"), ANDAMENTO(1, "ANDAMENTO"), ENCERRADO(2, "ENCERRADO");

    // ATRIBUTOS
    private Integer codigo;
    private String descricao;

    // CONSTRUTOR
    Status(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    // GETTERS
    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    // MÉTODO STATIC PARA NÃO PRECISAR CRIAR UMA INSTÂNCIA
    // DE PERFIL PARA CHAMAR ESTE MÉTODO EM OUTRAS PARTES DO CÓDIGO
    public static Status toEnum(Integer cod){
        if(cod == null){
            return null;
        }
        for(Status x : Status.values()){
            if(cod.equals(x.getCodigo())){
                return x;
            }
        }
        throw new IllegalArgumentException("Status inválido");
    }
}
```

## Construção das classes de domínio
#### São a representação de uma entidade de negócio na estrutura MVC
#### Anotações para construção de entidades para o banco de dados
```java
@Entity()
public abstract class Pessoa implements Serializable {
    // SERIALIZABLE CRIA UMA SEQUÊNCIA DE BYTES DAS INSTÂNCIAS DA CLASSE PARA QUE POSSAM
    // SEREM TRAFEGADAS EM REDE E ARMAZENADAS EM ARQUIVOS DE MEMÓRIA
    private static final long serialVersionUID = 1L;

    // ATRIBUTOS, NÃO PODE SER PRIVATE PQ OUTRAS CLASSES PRECISA ACESSAR ESSES ATRIBUTOS.
    // O PROTECTE É USADO PARA PROTEGER OS DADOS E PERMITIR QUE AS CLASSES FILHAS ACESSEM
    // CONCEITO DE HERANÇA
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;
    protected String nome;

    @Column(unique = true)
    protected String cpf;

    @Column(unique = true)
    protected String email;
    protected String senha;

    //@ElementCollection(fetch = FetchType.EAGER) -> É UMA COLEÇÃO DE ELEMENTOS DO TIPO INTEGER,
    // QUE QDO DER GET, BUSCAR O USUÁRIO, A LISTA DE PERFIS VEM IMEDIATAMENTE
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "PERFIS")
    protected Set<Integer> perfis= new HashSet<>(); // O SET PERMITE QUE NÃO HAJA VALORES IGUAIS NA LISTA

    @JsonFormat(pattern = "dd/MM/yyyy")
    protected LocalDate dataCriacao = LocalDate.now(); // MOMENTO ATUAL DA INSTÂNCIA CRIADA

    // CONSTRUTOR VAZIO
    public Pessoa() {
        super();
        addPerfil(Perfil.CLIENTE);
    }

    // CONSTRUTOR
    public Pessoa(Integer id, String nome, String cpf, String email, String senha) {
        super();
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        addPerfil(Perfil.CLIENTE);
    }

    // MÉTODOS ACESSORES E MODIFICADORES
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Set<Perfil> getPerfis() {
        return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
    }

    public void addPerfil(Perfil perfil) {
        this.perfis.add(perfil.getCodigo());
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    // HASH CODE EQUALS FAZ A COMPARAÇÃO DE UM DETERMINADO OBJETO
    // PELO SEU VALOR COM O VALOR DOS ATRIBUTOS, NÃO O VALOR EM MEMÓRIA
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Pessoa other = (Pessoa) obj;
        if (cpf == null) {
            if (other.cpf != null)
                return false;
        } else if (!cpf.equals(other.cpf))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
}
```

```java
@Entity
public class Projeto implements Serializable {
    // SERIALIZABLE CRIA UMA SEQUÊNCIA DE BYTES DAS INSTÂNCIAS DA CLASSE PARA QUE POSSAM
    // SEREM TRAFEGADAS EM REDE E ARMAZENADAS EM ARQUIVOS DE MEMÓRIA
    private static final long serialVersionUID = 1L;

    // ATRIBUTOS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataAbertura = LocalDate.now();
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataFechamento;

    private Prioridade prioridade;
    private Status status;
    private String titulo;
    private String observacoes;

    @ManyToOne // MUITOS PROJETOS PARA UM TÉCNICO, UM TÉCNICO COM UMA LISTA DE PROJETOS
    @JoinColumn(name = "tecnico_id")
    private Tecnico tecnico;

    @ManyToOne // MUITOS PROJETOS PARA UM CLIENTE, UM CLIENTE COM UMA LISTA DE PROJETOS
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    // CONSTRUTOR
    public Chamado() {
        super();
    }

    public Projeto(Integer id, Prioridade prioridade, Status status, String titulo, String observacoes, Tecnico tecnico, Cliente cliente) {
        super();
        this.id = id;
        this.prioridade = prioridade;
        this.status = status;
        this.titulo = titulo;
        this.observacoes = observacoes;
        this.tecnico = tecnico;
        this.cliente = cliente;
    }

    // GETTERS E SETTERS
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDate dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public LocalDate getDataFechamento() {
        return dataFechamento;
    }

    public void setDataFechamento(LocalDate dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Tecnico getTecnico() {
        return tecnico;
    }

    public void setTecnico(Tecnico tecnico) {
        this.tecnico = tecnico;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    // HASH CODE AND EQUALS - PARA A COMPARAÇÃO DO ID
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Projeto other = (Projeto) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
```

```java
@Entity
public class Cliente extends Pessoa {
    // SERIALIZABLE CRIA UMA SEQUÊNCIA DE BYTES DAS INSTÂNCIAS DA CLASSE PARA QUE POSSAM
    // SEREM TRAFEGADAS EM REDE E ARMAZENADAS EM ARQUIVOS DE MEMÓRIA
    // POR CAUSA DO EXTENDS PESSOA QUE POSSUI SERIALIZAÇÃO
    private static final long serialVersionUID = 1L;

    // ATRIBUTOS
    @JsonIgnore // IMPEDE A SERIALIZAÇÃO DAS INFORMAÇÕES QUANDO FIZER REQUISIÇÃO GET
    @OneToMany(mappedBy = "cliente") // UM CLIENTE PARA MUITOS PROJETOS - mapeado com o nome cliente em Projeto
    private List<Projeto> projetos = new ArrayList<>();
    // CONSTRUTOR DA SUPER CLASSE
    public Cliente() {
        super();
        addPerfil(Perfil.CLIENTE);
    }

    public Cliente(Integer id, String nome, String cpf, String email, String senha) {
        super(id, nome, cpf, email, senha);
        addPerfil(Perfil.CLIENTE);
    }

    // GETTER E SETTER
    public List<Projeto> getProjetos() {
        return projetos;
    }

    public void setProjetos(List<Projeto> projetos) {
        this.projetos = projetos;
    }
}
```

```java
@Entity
public class Tecnico extends Pessoa{
    // SERIALIZABLE CRIA UMA SEQUÊNCIA DE BYTES DAS INSTÂNCIAS DA CLASSE PARA QUE POSSAM
    // SEREM TRAFEGADAS EM REDE E ARMAZENADAS EM ARQUIVOS DE MEMÓRIA
    // POR CAUSA DO EXTENDS PESSOA QUE POSSUI SERIALIZAÇÃO
    private static final long serialVersionUID = 1L;

    // ATRIBUTOS
    @JsonIgnore // IMPEDE A SERIALIZAÇÃO DAS INFORMAÇÕES QUANDO FIZER REQUISIÇÃO GET
    @OneToMany(mappedBy = "tecnico") // UM TÉCNICO PARA MUITOS PROJETOS - mapeado com o nome tecnico em Projeto
    private List<Projeto> projetos = new ArrayList<>();

    // CONSTRUTOR DA SUPER CLASSE
    public Tecnico() {
        super();
        addPerfil(Perfil.TECNICO);
    }

    public Tecnico(Integer id, String nome, String cpf, String email, String senha) {
        super(id, nome, cpf, email, senha);
        addPerfil(Perfil.TECNICO);
    }

    // GETTER E SETTER
    public List<Projeto> getProjetos() {
        return projetos;
    }

    public void setProjetos(List<Projeto> projetos) {
        this.projetos = projetos;
    }
}
```

## Configurações no application.propertires
####
```
#application.properties
        spring.profiles.active=test

        jwt.secret=StringUsadaParaGerarToken
        jwt.expiration=604800000
```
```
#application-dev.properties
spring.datasource.url=jdbc:mysql://localhost:3306/helpdesk
spring.datasource.username=root
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=none
# spring.jpa.hibernate.ddl-auto=create

spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```
```
#application-test.properties
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=
spring.datasource.driver-class-name=org.h2.Driver
```

## Criação dos Repositories
#### Um Repository (Repositório) é um objeto que isola os objetos ou entidades do domínio do código que acessa o banco de dados.

#### Um repositório fica entre as regras de negócio e a camada de persistência:
- Ele provê uma interface para as regras de negócio onde os objetos são acessados como em uma coleção.
- Ele usa a camada de persistência para gravar e recuperar os dados necessários para persistir e recuperar os objetos de negócio.

```java
public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {
    Optional<Pessoa> findByCpf(String cpf);
    Optional<Pessoa> findByEmail(String email);
}
```
```java
public interface ClienteRepository extends JpaRepository<Cliente, Integer> { }
```
```java
public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> { }
```
```java
public interface ProjetoRepository extends JpaRepository<Projeto, Integer> { }
```

## TestConfig
```java
@Configuration
@Profile("test")
public class TestConfig {

    @Autowired
    private DBService dbService;

    @Bean
    public void instanciaDB() {
        this.dbService.instanciaDB();
    }
}
```
## DBService
#### Serviço que é possível injetar em outras partes do projeto
```java
@Service
public class DBService {
    @Autowired
    private ProjetoRepository projetoRepository;
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private TecnicoRepository tecnicoRepository;

    // MÉTODO
    public void instanciaDB() {
      Tecnico tec1 = new Tecnico(null, "admin", "550.482.150-95", "admin@mail.com",  "admin");
      tec1.addPerfil(Perfil.ADMIN);
      Tecnico tec2 = new Tecnico(null, "Tarsila do Amaral", "903.347.070-56", "caipirinha.amaral@mail.com.br", "123");
      Tecnico tec3 = new Tecnico(null, "Oswald de Andrade", "271.068.470-54", "oswald.andrade@mail.com.br", "123");
      Tecnico tec4 = new Tecnico(null, "Anita Malfatti", "162.720.120-39", "anita.malfatti@mail.com.br", "123");
      Tecnico tec5 = new Tecnico(null, "Mário de Andrade", "778.556.170-27", "mario.andrade@mail.com.br", "123");

      Cliente cli1 = new Cliente(null, "Graça Aranha", "111.661.890-74", "g.aranha@mail.com.br", "123");
      Cliente cli2 = new Cliente(null, "Victor Brecheret", "322.429.140-06", "victor.brecheret@mail.com.br", "123");
      Cliente cli3 = new Cliente(null, "Manuel Bandeira", "792.043.830-62", "manu.bandeira@mail.com.br", "123");
      Cliente cli4 = new Cliente(null, "Guiomar Novaes", "177.409.680-30", "gugu.novaes@mail.com.br", "123");
      Cliente cli5 = new Cliente(null, "Heitor Villa-Lobos", "081.399.300-83", "villa.lobos@mail.com.br", "123");

        Projeto p1 = new Projeto(null, Prioridade.MEDIA, Status.ANDAMENTO, "Projeto 1", "Teste Projeto 1", tec1, cli1);
        Projeto p2 = new Projeto(null, Prioridade.ALTA, Status.ABERTO, "Projeto 2", "Teste Projeto 2", tec1, cli2);
        Projeto p3 = new Projeto(null, Prioridade.BAIXA, Status.ENCERRADO, "Projeto 3", "Teste Projeto 3", tec2, cli3);
        Projeto p4 = new Projeto(null, Prioridade.ALTA, Status.ABERTO, "Projeto 4", "Teste Projeto 4", tec3, cli3);
        Projeto p5 = new Projeto(null, Prioridade.MEDIA, Status.ANDAMENTO, "Projeto 5", "Teste Projeto 5", tec2, cli1);
        Projeto p6 = new Projeto(null, Prioridade.BAIXA, Status.ENCERRADO, "Projeto 7", "Teste Projeto 6", tec1, cli5);

        pessoaRepository.saveAll(Arrays.asList(tec1, tec2, tec3, tec4, tec5, cli1, cli2, cli3, cli4, cli5));
        chamadoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6));
    }
}
```

## Perfil de desenvolvimento

```java
@Configuration
@Profile("dev")
public class DevConfig {

    @Autowired
    private DBService dbService;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String value;

    @Bean
    public boolean instanciaDB() {
        if(value.equals("create")) {
            this.dbService.instanciaDB();
        }
        return false;
    }
}
```
## Método GET id tecnico
#### Criação do método para buscar por id para o técnico, usando a entidade
```java
@RestController
@RequestMapping(value = "/tecnicos") //localhost:8080/tecnicos
public class TecnicoResource {

    @Autowired
    private TecnicoService service;

    // MÉTODO - SERVE PARA REPRESENTAR TODA A RESPOSTA HTTP
    @GetMapping(value = "/{id}")
    public ResponseEntity<Tecnico> findById(@PathVariable Integer id){
        // COMUNICAÇÃO COM O BANCO DE DADOS
        Tecnico obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }
}
```
```java
@Service
public class TecnicoService {

    @Autowired
    private TecnicoRepository repository;

    // MÉTODO IRÁ BUSCAR NO DATABASE O ID E RETORNAR O OBJETO
    public Tecnico findById(Integer id){
        Optional<Tecnico> obj = repository.findById(id);
        return obj.orElse(null);
    }
}
```

## TecnicoDTO
#### Criação do DTO para tecnico e endpoint de busca usando o DTO
#### DTO serve para transferir dados de um sistema para outro
```java
// O DTO É USADO COMO BOA PRÁTICA PARA A TRANSFERÊNCIA DE DADOS.
// NÃO É ACONSELHAVEL TER OS ENDPOINTS, COMO DE BUSCA GET, USANDO
// O QUE ESTÁ GUARDADO NA ENTITY
public class TecnicoDTO implements Serializable {

    // SERIALIZABLE CRIA UMA SEQUÊNCIA DE BYTES DAS INSTÂNCIAS DA CLASSE PARA QUE POSSAM
    // SEREM TRAFEGADAS EM REDE E ARMAZENADAS EM ARQUIVOS DE MEMÓRIA
    private static final long serialVersionUID = 1L;

    // ATRIBUTOS
    protected Integer id;
    protected String nome;
    protected String cpf;
    protected String email;
    protected String senha;
    protected Set<Integer> perfis= new HashSet<>(); // O SET PERMITE QUE NÃO HAJA VALORES IGUAIS NA LISTA

    @JsonFormat(pattern = "dd/MM/yyyy")
    protected LocalDate dataCriacao = LocalDate.now();

    // CONSTRUTOR DA SUPER CLASSE
    public TecnicoDTO() {
        super();
        addPerfil(Perfil.CLIENTE);
    }

    public TecnicoDTO(Tecnico obj) {
        super();
        this.id = obj.getId();
        this.nome = obj.getNome();
        this.cpf = obj.getCpf();
        this.email = obj.getEmail();
        this.senha = obj.getSenha();
        this.perfis = obj.getPerfis().stream().map(x -> x.getCodigo()).collect(Collectors.toSet());
        this.dataCriacao = obj.getDataCriacao();
        addPerfil(Perfil.CLIENTE);
    }

    // GETTERS E SETTERS
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    // RETORNA A DESCRIÇÃO DO PERFIL. DE OUTRA FORMA O QUE É EXIBIDO É APENAS O VALUE DO PERFIL
    public Set<Perfil> getPerfis() {
        return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
    }

    public void addPerfil(Perfil perfil) {
        this.perfis.add(perfil.getCodigo());
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
```
#### Alterações em TecnicoResource para adequar a transferência usando o DTO
```java
    // MÉTODO - SERVE PARA REPRESENTAR TODA A RESPOSTA HTTP
    @GetMapping(value = "/{id}")
    public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id){
        // COMUNICAÇÃO COM O BANCO DE DADOS
        Tecnico obj = service.findById(id);
        return ResponseEntity.ok().body(new TecnicoDTO(obj));
    }
```
## Criação da exceção para objeto não encontrado em formato personalizado: 
```java
/ CRIA A MENSAGEM DE EXCEÇÃO QUANDO UM VALOR DE ID, POR EXEMPLO, NÃO É ENCONTRADO
public class ObjectNotFoundException extends RuntimeException{
    // SERIALIZABLE CRIA UMA SEQUÊNCIA DE BYTES DAS INSTÂNCIAS DA CLASSE PARA QUE POSSAM
    // SEREM TRAFEGADAS EM REDE E ARMAZENADAS EM ARQUIVOS DE MEMÓRIA
    private static final long serialVersionUID = 1L;

    // CONSTRUTOR DA SUPER CLASSE
    public ObjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectNotFoundException(String message) {
        super(message);
    }
}
```
#### Alteração no TecnicoService já que agora tem uma classe para as exceções
```java
    // MÉTODO IRÁ BUSCAR NO DATABASE O ID E RETORNAR O OBJETO
    public Tecnico findById(Integer id){
        Optional<Tecnico> obj = repository.findById(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id: " + id));
    }
```
#### Criação do Resource Exception Handler
```java
// MANIPULADOR DE EXCEÇÕES DO RECURSO
@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class) //MANIPULADOR DA CLASSE ObjectNotFoundException
    public ResponseEntity<StandardError> objectNotFoundException(ObjectNotFoundException ex,
                                                                 HttpServletRequest request) {

        StandardError error = new StandardError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(),
                "Object Not Found", ex.getMessage(), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
```
#### Criação do Standard Error
```java
public class StandardError {
    private static final long serialVersionUID = 1L;

    // ATRIBUTOS EXIBIDOS QUANDO UMA EXCEÇÃO É DETECTADA NO SISTEMA
    private Long timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

    // CONSTRUTOR DE SUPER CLASSE
    public StandardError() {
        super();
    }

    public StandardError(Long timestamp, Integer status, String error, String message, String path) {
        super();
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    // GETTERS E SETTERS
    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
```
## Criação dos métodos para listar técnico sem parâmetros definidos
#### TecnicoResource
```java
    // MÉTODO LISTARÁ TODOS OS TÉCNICOS DTO - QUANDO NÃO HÁ PARÂMETROS DEFINIDOS
    @GetMapping
    public ResponseEntity<List<TecnicoDTO>> findAll() {
        List<Tecnico> list = service.findAll();
        List<TecnicoDTO> listDTO = list.stream().map(obj -> new TecnicoDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }
```
#### TecnicoService
```java
    // MÉTODO LISTARÁ TODOS OS TÉCNICOS
    public List<Tecnico> findAll() {
        return repository.findAll();
        }
```
## Implementação do método create para novos técnicos
#### TecnicoResource
```java
    // MÉTODO PARA CRIAR UM NOVO REGISTRO DE TÉCNICO
    @PostMapping
    public ResponseEntity<TecnicoDTO> create(@Valid @RequestBody TecnicoDTO objDTO) {
        Tecnico newObj = service.create(objDTO);
        // SETA O ENDEREÇO DO NOVO OBJETO
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
```
#### TecnicoService
```java
    // MÉTODO PARA CRIAR UM NOVO REGISTRO DE TÉCNICO
    public Tecnico create(TecnicoDTO objDTO) {
        objDTO.setId(null); // GARANTE RECEBER O ID NULLO
        Tecnico newObj = new Tecnico(objDTO); // CONVERTE AS INFORMAÇÕES DO BANCO PARA UMA NOVA REQUISIÇÃO
        return repository.save(newObj);
    }
```
## Validações para CPF e E-mail de Tecnico
#### Nova exception criada
```java
// CRIA A MENSAGEM DE EXCEÇÃO QUANDO UM VALOR DE CPF E/OU EMAIL JÁ EXISTIREM
public class DataIntegrityViolationException extends RuntimeException{
    // SERIALIZABLE CRIA UMA SEQUÊNCIA DE BYTES DAS INSTÂNCIAS DA CLASSE PARA QUE POSSAM
    // SEREM TRAFEGADAS EM REDE E ARMAZENADAS EM ARQUIVOS DE MEMÓRIA
    private static final long serialVersionUID = 1L;

    // CONSTRUTOR DA SUPER CLASSE
    public DataIntegrityViolationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataIntegrityViolationException(String message) {
        super(message);
    }
}
```
#### Implementação do método de validação em ResourceExceptionHandler
```java
    //MANIPULADOR DA CLASSE DataIntegrityViolationException - PARA CASO DE EMAIL E CPF JÁ CONSTAR REGISTRADO
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardError> dataIntegrityViolationException(DataIntegrityViolationException ex,
                                                                 HttpServletRequest request) {

        StandardError error = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
                "Violação de dados", ex.getMessage(), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
```
#### Para o campos obigatórios não serem criados como nulo, foi adicionado notações para os atributos em TecnicoDTO
```java
    // ATRIBUTOS
    protected Integer id;
    @NotNull(message = "O campo nome é obrigatório.")
    protected String nome;
    @NotNull(message = "O campo CPF é obrigatório.")
    protected String cpf;
    @NotNull(message = "O campo e-mail é obrigatório.")
    protected String email;
    @NotNull(message = "O campo senha é obrigatório.")
    protected String senha;
```
#### Implementação de exceção para o tratamento de erro para campos não preenchidos com as notações @NotNull
```java
public class FieldMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    // CAMPOS QUE PRECISAM DE TRATAMENTO
    private String fieldName;
    private String message;

    // CONSTRUTOR DA SUPER CLASSE
    public FieldMessage() {
        super();
    }

    // CONSTRUTOR COM OS PARÂMETROS
    public FieldMessage(String fieldName, String message) {
        super();
        this.fieldName = fieldName;
        this.message = message;
    }

    // GETTERS E SETTERS
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
```
#### Validação dos erros
```java
public class ValidationError extends StandardError {
    private static final long serialVersionUID = 1L;

    // JÁ POSSUI TODOS OS ATRIBUTOS DE StandardError (extends = herança)
    // E TEM UM ATRIBUTO A MAIS COM A LISTA DE MENSAGEM
    private List<FieldMessage> errors = new ArrayList<>();

    // CONSTRUTOR DA SUPER CLASSE
    public ValidationError() {
        super();
    }

    // CONSTRUTOR COM OS PARÂMETROS
    public ValidationError(Long timestamp, Integer status, String error, String message, String path) {
        super(timestamp, status, error, message, path);
    }

    // GETTERS E SETTERS
    public List<FieldMessage> getErrors() {
        return errors;
    }

    public void addError(String fieldName, String message) {
        this.errors.add(new FieldMessage(fieldName, message));
    }
}
```
#### Implementação do método de validação em ResourceExceptionHandler
```java
// MÉTODO PARA A VALIDAÇÃO DOS ERROS PARA OS CAMPOS
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validationErrors(MethodArgumentNotValidException ex,
                                                          HttpServletRequest request) {

        ValidationError errors = new ValidationError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
                "Validation error", "Erro na validação dos campos", request.getRequestURI());

        // ACESSA TODOS OS ERROS DE CADA FIELD COM ERRO
        for(FieldError x : ex.getBindingResult().getFieldErrors()) {
            errors.addError(x.getField(), x.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
```
#### Para o CRUD implementado, o TecnicoResource é apresentado como:
```java
@RestController
@RequestMapping(value = "/tecnicos") //localhost:8080/tecnicos
public class TecnicoResource {

    @Autowired
    private TecnicoService service;

    // MÉTODO - SERVE PARA REPRESENTAR TODA A RESPOSTA HTTP
    @GetMapping(value = "/{id}")
    public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id){
        // COMUNICAÇÃO COM O BANCO DE DADOS
        Tecnico obj = service.findById(id);
        return ResponseEntity.ok().body(new TecnicoDTO(obj));
    }

    // MÉTODO LISTARÁ TODOS OS TÉCNICOS DTO - QUANDO NÃO HÁ PARÂMETROS DEFINIDOS
    @GetMapping
    public ResponseEntity<List<TecnicoDTO>> findAll() {
        List<Tecnico> list = service.findAll();
        List<TecnicoDTO> listDTO = list.stream().map(obj -> new TecnicoDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    // MÉTODO PARA CRIAR UM NOVO REGISTRO DE TÉCNICO
    @PostMapping
    public ResponseEntity<TecnicoDTO> create(@Valid @RequestBody TecnicoDTO objDTO) {
        Tecnico newObj = service.create(objDTO);
        // SETA O ENDEREÇO DO NOVO OBJETO
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    // MÉTODO PARA ATUALIZAR UM NOVO REGISTRO DE TÉCNICO
    @PutMapping(value = "/{id}")
    public ResponseEntity<TecnicoDTO> update(@PathVariable Integer id, @Valid @RequestBody TecnicoDTO objDTO) {
        Tecnico obj = service.update(id, objDTO);
        return ResponseEntity.ok().body(new TecnicoDTO(obj));
    }

    // MÉTODO PARA DELETAR UM NOVO REGISTRO DE TÉCNICO
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<TecnicoDTO> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
```
#### Para o CRUD implementado, o TecnicoService é apresentado como:
```java
@Service
public class TecnicoService {

    @Autowired
    private TecnicoRepository repository;
    @Autowired
    private PessoaRepository pessoaRepository;

    // MÉTODO IRÁ BUSCAR NO DATABASE O ID E RETORNAR O OBJETO
    public Tecnico findById(Integer id){
        Optional<Tecnico> obj = repository.findById(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id: " + id));
    }

    // MÉTODO LISTARÁ TODOS OS TÉCNICOS
    public List<Tecnico> findAll() {
        return repository.findAll();
    }

    // MÉTODO PARA CRIAR UM NOVO REGISTRO DE TÉCNICO
    public Tecnico create(TecnicoDTO objDTO) {
        objDTO.setId(null); // GARANTE RECEBER O ID NULLO
        validaPorCpfEEmail(objDTO);
        Tecnico newObj = new Tecnico(objDTO); // CONVERTE AS INFORMAÇÕES DO BANCO PARA UMA NOVA REQUISIÇÃO
        return repository.save(newObj);
    }

    // MÉTODO PARA VALIDAR SE O CPF E O EMAIL JÁ ESTÃO CADASTRADOS
    private void validaPorCpfEEmail(TecnicoDTO objDTO) {
        // verifica em Pessoa se CPF já existe
        Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());
        if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
            throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
        }
        // verifica em Pessoa se email já existe
        obj = pessoaRepository.findByEmail(objDTO.getEmail());
        if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
            throw new DataIntegrityViolationException("E-mail já cadastrado no sistema!");
        }
    }

    // ENDPOINT PARA ATUALIZAR AS INFORMAÇÕES
    public Tecnico update(Integer id, @Valid TecnicoDTO objDTO) {
        // busca o registro antes da alteração
        objDTO.setId(id);
        Tecnico oldObj = findById(id);

      if(!objDTO.getSenha().equals(oldObj.getSenha())){
        objDTO.setSenha(encoder.encode(objDTO.getSenha()));
      }

        validaPorCpfEEmail(objDTO);
        oldObj = new Tecnico(objDTO);
        return repository.save(oldObj);
    }

    // ENDPOINT PARA DELETAR UM REGISTRO
    public void delete(Integer id) {
        Tecnico obj = findById(id);

        if (obj.getChamados().size() > 0) {
            throw new DataIntegrityViolationException("Técnico possui ordens de serviço e não pode ser deletado!");
        }

        repository.deleteById(id);
    }
}
```
## CRUD Cliente
#### Em Clientes, é implementado um novo Construtor
```java
    public Cliente(ClienteDTO obj) {
        super();
        this.id = obj.getId();
        this.nome = obj.getNome();
        this.cpf = obj.getCpf();
        this.email = obj.getEmail();
        this.senha = obj.getSenha();
        this.perfis = obj.getPerfis().stream().map(x -> x.getCodigo()).collect(Collectors.toSet());
        this.dataCriacao = obj.getDataCriacao();
    }
```
#### Criação da classe ClienteDTO 
```java
// O DTO É USADO COMO BOA PRÁTICA PARA A TRANSFERÊNCIA DE DADOS.
// NÃO É ACONSELHAVEL TER OS ENDPOINTS, COMO DE BUSCA GET, USANDO
// O QUE ESTÁ GUARDADO NA ENTITY
public class ClienteDTO implements Serializable{
        // SERIALIZABLE CRIA UMA SEQUÊNCIA DE BYTES DAS INSTÂNCIAS DA CLASSE PARA QUE POSSAM
        // SEREM TRAFEGADAS EM REDE E ARMAZENADAS EM ARQUIVOS DE MEMÓRIA
        private static final long serialVersionUID = 1L;

        // ATRIBUTOS
        protected Integer id;
        @NotNull(message = "O campo nome é obrigatório.")
        protected String nome;
        @NotNull(message = "O campo CPF é obrigatório.")
        @CPF
        protected String cpf;
        @NotNull(message = "O campo e-mail é obrigatório.")
        protected String email;
        @NotNull(message = "O campo senha é obrigatório.")
        protected String senha;
        protected Set<Integer> perfis= new HashSet<>(); // O SET PERMITE QUE NÃO HAJA VALORES IGUAIS NA LISTA

        @JsonFormat(pattern = "dd/MM/yyyy")
        protected LocalDate dataCriacao = LocalDate.now();

        // CONSTRUTOR DA SUPER CLASSE
        public ClienteDTO() {
            super();
            addPerfil(Perfil.CLIENTE);
        }

        public ClienteDTO(Cliente obj) {
            super();
            this.id = obj.getId();
            this.nome = obj.getNome();
            this.cpf = obj.getCpf();
            this.email = obj.getEmail();
            this.senha = obj.getSenha();
            this.perfis = obj.getPerfis().stream().map(x -> x.getCodigo()).collect(Collectors.toSet());
            this.dataCriacao = obj.getDataCriacao();
            addPerfil(Perfil.CLIENTE);
        }

        // GETTERS E SETTERS
        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getCpf() {
            return cpf;
        }

        public void setCpf(String cpf) {
            this.cpf = cpf;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getSenha() {
            return senha;
        }

        public void setSenha(String senha) {
            this.senha = senha;
        }

        // RETORNA A DESCRIÇÃO DO PERFIL. DE OUTRA FORMA O QUE É EXIBIDO É APENAS O VALUE DO PERFIL
        public Set<Perfil> getPerfis() {
            return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
        }

        public void addPerfil(Perfil perfil) {
            this.perfis.add(perfil.getCodigo());
        }

        public LocalDate getDataCriacao() {
            return dataCriacao;
        }

        public void setDataCriacao(LocalDate dataCriacao) {
            this.dataCriacao = dataCriacao;
        }
    }
```
#### Criação da classe ClienteService
```java
@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;
    @Autowired
    private PessoaRepository pessoaRepository;

    // MÉTODO IRÁ BUSCAR NO DATABASE O ID E RETORNAR O OBJETO
    public Cliente findById(Integer id){
        Optional<Cliente> obj = repository.findById(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id: " + id));
    }

    // MÉTODO LISTARÁ TODOS OS CLIENTES
    public List<Cliente> findAll() {
        return repository.findAll();
    }

    // MÉTODO PARA CRIAR UM NOVO REGISTRO DE CLIENTES
    public Cliente create(ClienteDTO objDTO) {
        objDTO.setId(null); // GARANTE RECEBER O ID NULLO
        validaPorCpfEEmail(objDTO);
        Cliente newObj = new Cliente(objDTO); // CONVERTE AS INFORMAÇÕES DO BANCO PARA UMA NOVA REQUISIÇÃO
        return repository.save(newObj);
    }

    // MÉTODO PARA VALIDAR SE O CPF E O EMAIL JÁ ESTÃO CADASTRADOS
    private void validaPorCpfEEmail(ClienteDTO objDTO) {
        // verifica em Pessoa se CPF já existe
        Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());
        if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
            throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
        }
        // verifica em Pessoa se email já existe
        obj = pessoaRepository.findByEmail(objDTO.getEmail());
        if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
            throw new DataIntegrityViolationException("E-mail já cadastrado no sistema!");
        }
    }

    // ENDPOINT PARA ATUALIZAR AS INFORMAÇÕES
    public Cliente update(Integer id, @Valid ClienteDTO objDTO) {
        // busca o registro antes da alteração
        objDTO.setId(id);
        Cliente oldObj = findById(id);

      if(!objDTO.getSenha().equals(oldObj.getSenha())){
        objDTO.setSenha(encoder.encode(objDTO.getSenha()));
      }

        validaPorCpfEEmail(objDTO);
        oldObj = new Cliente(objDTO);
        return repository.save(oldObj);
    }

    // ENDPOINT PARA DELETAR UM REGISTRO
    public void delete(Integer id) {
        Cliente obj = findById(id);

        if (obj.getProjetos().size() > 0) {
            throw new DataIntegrityViolationException("Cliente possui projetos e não pode ser deletado!");
        }

        repository.deleteById(id);
    }
}
```
#### Criação da classe ClienteResource
```java
@RestController
@RequestMapping(value = "/clientes") //localhost:8080/clientes
public class ClienteResource {

    @Autowired
    private ClienteService service;

    // MÉTODO - SERVE PARA REPRESENTAR TODA A RESPOSTA HTTP
    @GetMapping(value = "/{id}")
    public ResponseEntity<ClienteDTO> findById(@PathVariable Integer id){
        // COMUNICAÇÃO COM O BANCO DE DADOS
        Cliente obj = service.findById(id);
        return ResponseEntity.ok().body(new ClienteDTO(obj));
    }

    // MÉTODO LISTARÁ TODOS OS CLIENTE DTO - QUANDO NÃO HÁ PARÂMETROS DEFINIDOS
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> findAll() {
        List<Cliente> list = service.findAll();
        List<ClienteDTO> listDTO = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    // MÉTODO PARA CRIAR UM NOVO REGISTRO DE CLIENTE
    @PostMapping
    public ResponseEntity<ClienteDTO> create(@Valid @RequestBody ClienteDTO objDTO) {
        Cliente newObj = service.create(objDTO);
        // SETA O ENDEREÇO DO NOVO OBJETO
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    // MÉTODO PARA ATUALIZAR UM NOVO REGISTRO DE CLIENTE
    @PutMapping(value = "/{id}")
    public ResponseEntity<ClienteDTO> update(@PathVariable Integer id, @Valid @RequestBody ClienteDTO objDTO) {
        Cliente obj = service.update(id, objDTO);
        return ResponseEntity.ok().body(new ClienteDTO(obj));
    }

    // MÉTODO PARA DELETAR UM NOVO REGISTRO DE CLIENTE
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ClienteDTO> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
```
## Implementação do ProjetoDTO
```java
// O DTO É USADO COMO BOA PRÁTICA PARA A TRANSFERÊNCIA DE DADOS.
// NÃO É ACONSELHAVEL TER OS ENDPOINTS, COMO DE BUSCA GET, USANDO
// O QUE ESTÁ GUARDADO NA ENTITY
public class ProjetoDTO implements Serializable {
    // SERIALIZABLE CRIA UMA SEQUÊNCIA DE BYTES DAS INSTÂNCIAS DA CLASSE PARA QUE POSSAM
    // SEREM TRAFEGADAS EM REDE E ARMAZENADAS EM ARQUIVOS DE MEMÓRIA
    private static final long serialVersionUID = 1L;

    // ATRIBUTOS
    private Integer id;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataAbertura = LocalDate.now();
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataFechamento;
    @NotNull(message = "O campo PRIORIDADE é requerido")
    private Integer prioridade;
    @NotNull(message = "O campo STATUS é requerido")
    private Integer status;
    @NotNull(message = "O campo TITULO é requerido")
    private String titulo;
    @NotNull(message = "O campo OBSERVAÇÕES é requerido")
    private String observacoes;
    @NotNull(message = "O campo TECNICO é requerido")
    private Integer tecnico;
    @NotNull(message = "O campo CLIENTE é requerido")
    private Integer cliente;
    private String nomeTecnico;
    private String nomeCliente;

    // CONSTRUTOR DA SUPER CLASSE
    public ProjetoDTO() {
        super();
    }

    // CONSTRUTOR COM OS PARAMETROS
    public ProjetoDTO(Projeto obj) {
        this.id = obj.getId();
        this.dataAbertura = obj.getDataAbertura();
        this.dataFechamento = obj.getDataFechamento();
        this.prioridade = obj.getPrioridade().getCodigo();
        this.status = obj.getStatus().getCodigo();
        this.titulo = obj.getTitulo();
        this.observacoes = obj.getObservacoes();
        this.tecnico = obj.getTecnico().getId();
        this.cliente = obj.getCliente().getId();
        this.nomeCliente = obj.getCliente().getNome();
        this.nomeTecnico = obj.getTecnico().getNome();
    }

    // GETTERS E SETTERS
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDate dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public LocalDate getDataFechamento() {
        return dataFechamento;
    }

    public void setDataFechamento(LocalDate dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    public Integer getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Integer prioridade) {
        this.prioridade = prioridade;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Integer getTecnico() {
        return tecnico;
    }

    public void setTecnico(Integer tecnico) {
        this.tecnico = tecnico;
    }

    public Integer getCliente() {
        return cliente;
    }

    public void setCliente(Integer cliente) {
        this.cliente = cliente;
    }

    public String getNomeTecnico() {
        return nomeTecnico;
    }

    public void setNomeTecnico(String nomeTecnico) {
        this.nomeTecnico = nomeTecnico;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }
}
```
## Implementação do ProjetoResource
```java
@RestController
@RequestMapping(value = "/chamados") //localhost:8080/projetos
public class ProjetoResource {

    @Autowired
    private ProjetoService service;

    // MÉTODO - SERVE PARA REPRESENTAR TODA A RESPOSTA HTTP
    @GetMapping(value = "/{id}")
    public ResponseEntity<ProjetoDTO> findById(@PathVariable Integer id) {
        Projeto obj = service.findById(id);
        return ResponseEntity.ok().body(new ProjetoDTO(obj));
    }

    // MÉTODO LISTARÁ TODOS OS PROJETOS DTO - QUANDO NÃO HÁ PARÂMETROS DEFINIDOS
    @GetMapping
    public ResponseEntity<List<ProjetoDTO>> findAll() {
        List<Projeto> list = service.findAll();
        List<ProjetoDTO> listDTO = list.stream().map(obj -> new ProjetoDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    // MÉTODO PARA CRIAR UM NOVO REGISTRO DE PROJETOS
    @PostMapping
    public ResponseEntity<ProjetoDTO> create(@Valid @RequestBody ProjetoDTO obj) {
        Projeto newObj = service.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    // MÉTODO PARA ATUALIZAR UM NOVO REGISTRO DE PROJETOS
    @PutMapping(value = "/{id}")
    public ResponseEntity<ProjetoDTO> update(@PathVariable Integer id, @Valid @RequestBody ProjetoDTO objDTO) {
        Projeto newObj = service.update(id, objDTO);
        return ResponseEntity.ok().body(new ProjetoDTO(newObj));
    }
}
```
## Implementação do ProjetoService
```java
@Service
public class ProjetoService {
    @Autowired
    private ProjetoRepository repository; // COMUNICAÇÃO COM O BANCO
    @Autowired
    private TecnicoService tecnicoService;
    @Autowired
    private ClienteService clienteService;

    // MÉTODO IRÁ BUSCAR NO DATABASE O ID E RETORNAR O OBJETO
    public Projeto findById(Integer id) {
        Optional<Projeto> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID: " + id));
    }

    // MÉTODO LISTARÁ TODOS OS PROJETOS
    public List<Projeto> findAll() {
        return repository.findAll();
    }

    // MÉTODO PARA CRIAR UM NOVO REGISTRO DE PROJETO
    public Projeto create(ProjetoDTO obj) {
        return repository.save(newProjeto(obj));
    }

    // VERIFICA SE O ID DO TÉCNICO E CLIENTE EXISTEM QUANDO O CHAMADO É CRIADO
    private Projeto newProjeto(ProjetoDTO obj) {
        Tecnico tecnico = tecnicoService.findById(obj.getTecnico());
        Cliente cliente = clienteService.findById(obj.getCliente());

        Projeto projeto = new Projeto();
        // PARA ATUALIZAR O PROJETO, POIS != NULL
        if(obj.getId() != null) {
            projeto.setId(obj.getId());
        }
        
        // PARA APRESENTAR A DATA ATUAL QUANDO ENCERRADO O PROJETO
        if(obj.getStatus().equals(2)) {
            projeto.setDataFechamento(LocalDate.now());
        }

        projeto.setTecnico(tecnico);
        projeto.setCliente(cliente);
        projeto.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
        projeto.setStatus(Status.toEnum(obj.getStatus()));
        projeto.setTitulo(obj.getTitulo());
        projeto.setObservacoes(obj.getObservacoes());
        return projeto;
    }

    // ENDPOINT PARA ATUALIZAR AS INFORMAÇÕES
    public Projeto update(Integer id, @Valid ProjetoDTO objDTO) {
        objDTO.setId(id);
        Projeto oldObj = findById(id);
        oldObj = newProjeto(objDTO);
        return repository.save(oldObj);
    }
}
```
## Emprego da Autenticação
#### Novas dependências no pom.xml
```
<!-- Spring Security -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-security</artifactId>
		</dependency>
		<dependency>
			<groupId>io.jsonwebtoken</groupId>
			<artifactId>jjwt</artifactId>
			<version>0.7.0</version>
		</dependency>
```
## Implementação das configurações de segurança
#### Adicionado exceções de ambiente e criptografia de senha em SecutiryConfig
```java
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // PARA LIBERAR O ACESSO AO SQL PELO h2-console
    private static final String[] PUBLIC_MATCHERS = { "/h2-console/**" };

    @Autowired
    //INTERFACE QUE REPRESENTA O AMBIENTE NO QUAL O APP ATUAL ESTÁ SENDO EXECUTADO. HABILITA A INTERFACE
    // DO H2 QUANDO SE ESTÁ EM AMBIENTE DE "test"
    private Environment env;

    // MÉTODO PARA QUE QUALQUER ENDPOINT QUE NECESSITE DE DEFESA CONTRA VULNERABILIDADE É ESPECIFICADO AQUI
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // VALIDA SE O AMBIENTE DE DESENVOLVIMENTO É O DE test, QUE ENTÃO CHAMA O HATTP HEADERS E DESABILITA O frameOptions
        if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
            http.headers().frameOptions().disable();
        }

        // DESABILITA O ATAQUE DO TIPO CSRF QUE CONSISTE EM ATAQUES DE SESSÃO ARMAZENADA DO USUÁRIO, MAS COMO ESTA
        // APLICAÇÃO NÃO ARMAZENA SESSÃO, NÃO PRECISA SER MANTIDA, EM CONTRAPARTIDA É PRECISO GARANTIR QUE A
        // SESSÃO NÃO SERÁ CRIADA, ENTÃO É EMPREGADO sessionManagement
        http.cors().and().csrf().disable();
        // para a autorização do h2-console
        http.authorizeRequests().antMatchers(PUBLIC_MATCHERS).permitAll().anyRequest().authenticated();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    // A SENHA ESTAVA SALVA NO BANCO O QUE PERMITE QUE QUALQUER UM QUE TENHA ACESSO A ESTE BANCO TAMBÉM TEM PODER
    // DESTAS SENHAS, O QUE É UMA FALHA DE SEGURANÇA,ESTE BEAN ENTÃO FORNECE A SENHA COMO CRIPTOGRAFADA NO BANCO
    // É INJETADO EM QUALQUER PARTE DO PROJETO
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

```
#### Inserção em DBService das instâncias que criptografam as senhas
```java
    @Autowired
    private BCryptPasswordEncoder encoder;

    // MÉTODO
    public void instanciaDB() {
        Tecnico tec1 = new Tecnico(null, "admin", "550.482.150-95", "admin@mail.com",  encoder.encode("admin"));
        tec1.addPerfil(Perfil.ADMIN);
        Tecnico tec2 = new Tecnico(null, "Tarsila do Amaral", "903.347.070-56", "caipirinha.amaral@mail.com.br",  encoder.encode("123"));
        Tecnico tec3 = new Tecnico(null, "Oswald de Andrade", "271.068.470-54", "oswald.andrade@mail.com.br",  encoder.encode("123"));
        Tecnico tec4 = new Tecnico(null, "Anita Malfatti", "162.720.120-39", "anita.malfatti@mail.com.br",  encoder.encode("123"));
        Tecnico tec5 = new Tecnico(null, "Mário de Andrade", "778.556.170-27", "mario.andrade@mail.com.br",  encoder.encode("123"));

        Cliente cli1 = new Cliente(null, "Graça Aranha", "111.661.890-74", "g.aranha@mail.com.br",  encoder.encode("123"));
        Cliente cli2 = new Cliente(null, "Victor Brecheret", "322.429.140-06", "victor.brecheret@mail.com.br",  encoder.encode("123"));
        Cliente cli3 = new Cliente(null, "Manuel Bandeira", "792.043.830-62", "manu.bandeira@mail.com.br",  encoder.encode("123"));
        Cliente cli4 = new Cliente(null, "Guiomar Novaes", "177.409.680-30", "gugu.novaes@mail.com.br",  encoder.encode("123"));
        Cliente cli5 = new Cliente(null, "Heitor Villa-Lobos", "081.399.300-83", "villa.lobos@mail.com.br",  encoder.encode("123"));
```
#### Para ClienteService também é chamada a instância de criptografia e quando criado um registro, a senha já vai criptografada para o banco
```java
    @Autowired
    private BCryptPasswordEncoder encoder;

    // MÉTODO PARA CRIAR UM NOVO REGISTRO DE CLIENTES
    public Cliente create(ClienteDTO objDTO) {
        objDTO.setId(null); // GARANTE RECEBER O ID NULLO
        objDTO.setSenha(encoder.encode(objDTO.getSenha())); //A SENHA É SALVA NO BANCO JÁ CRIPTOGRAFADA
        validaPorCpfEEmail(objDTO);
        Cliente newObj = new Cliente(objDTO); // CONVERTE AS INFORMAÇÕES DO BANCO PARA UMA NOVA REQUISIÇÃO
        return repository.save(newObj);
        }
```
#### O mesmo processo para TecnicoService
```java
    @Autowired
    private BCryptPasswordEncoder encoder;

    // MÉTODO PARA CRIAR UM NOVO REGISTRO DE TÉCNICO
    public Tecnico create(TecnicoDTO objDTO) {
        objDTO.setId(null); // GARANTE RECEBER O ID NULO
        objDTO.setSenha(encoder.encode(objDTO.getSenha())); //A SENHA É SALVA NO BANCO JÁ CRIPTOGRAFADA
        validaPorCpfEEmail(objDTO);
        Tecnico newObj = new Tecnico(objDTO); // CONVERTE AS INFORMAÇÕES DO BANCO PARA UMA NOVA REQUISIÇÃO
        return repository.save(newObj);
        }
```
#### Implementação do contrato de usuário usando o User Spring Security
```java
// USER SPRING SECURITY
public class UserSS implements UserDetails {
    // SERIAL É IMPLEMENTADO NO UserDetails, ENTÃO PRECISA SER INSTÂNCIA AQUI
    private static final long serialVersionUID = 1L;

    // ATRIBUTOS
    private Integer id;
    private String email;
    private String senha;
    private Collection<? extends GrantedAuthority> authorities;

    // CONSTRUTOR COM OS PARÂMETROS
    public UserSS(Integer id, String email, String senha, Set<Perfil> perfis) {
        super();
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.authorities = perfis.stream().map(x -> new SimpleGrantedAuthority(x.getDescricao())).collect(Collectors.toSet());
    }

    // GETTER
    public Integer getId() {
        return id;
    }

    // MÉTODOS NÃO IMPLEMENTADOS DE USER DETAILS, REGRAS DE NEGÓCIO PADRÃO COMO CONTA EXPIRADA, CONTA BLOQUEADA, ETC
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
```
#### Criação da implementação do serviço de detalhes do usuário
```java
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PessoaRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Pessoa> user = repository.findByEmail(email);
        if(user.isPresent()) {
            return new UserSS(user.get().getId(), user.get().getEmail(), user.get().getSenha(), user.get().getPerfis());
        }
        throw new UsernameNotFoundException(email);
    }
}
```
#### Credenciais DTO para a conversão do usuário e login
```java
public class CredenciaisDTO {
    // CLASSE PARA A CONVERSÃO DO USUÁRIO COM A REQUISIÇÃO DE LOGIN

    // ATRIBUTOS
    private String email;
    private String senha;

    // GETTERS E SETTERS
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
```
## Classe JWTUtil
#### A mais importante para o JWT
```java
@Component
public class JWTUtil {

    // VARIÁVEL PARA TRAZER O VALOR QUE FOI DADO NO APPLICATION.PROPERTIES
    // PARA O TEMPO DE EXPIRAÇÃO DO TOKEN
    @Value("${jwt.expiration}")
    private Long expiration;

    // VARIÁVEL PARA TRAZER O VALOR QUE FOI DADO NO APPLICATION.PROPERTIES
    // PARA A PALAVRA SECRETA DO TOKEN
    @Value("${jwt.secret}")
    private String secret;

    // GERA O TOKEN CONFORME O JWT SECRET EMPREGADO EM APPLICATION.PROPERTIES
    // O COMPACT DEIXA A APLICAÇÃO MAIS PERFORMÁTICA COMPACTANDO A CHAVE
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();
    }

    public boolean tokenValido(String token) {
        Claims claims = getClaims(token);
        if(claims != null) {
            String username = claims.getSubject();
            Date expirationDate = claims.getExpiration();
            Date now = new Date(System.currentTimeMillis());

            if(username != null && expirationDate != null && now.before(expirationDate)) {
                return true;
            }
        }
        return false;
    }
    
    private Claims getClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            return null;
        }
    }

    public String getUsername(String token) {
        Claims claims = getClaims(token);
        if(claims != null) {
            return claims.getSubject();
        }
        return null;
    }
}
```
#### Criação dos filtros de validação do token. Com as classes implementadas no package security e depois chamadas no SecurityConfig
```java
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    // INTERCEPTA O MÉTODO POST

    // É A PRINCIPAL INTERFACE DE ESTRATÉGIA PARA AUTENTICAÇÃO. SE A AUTENTIFICAÇÃO DE ENTRADA
    // FOR VÁLIDA, É VERIFICADO O MÉTODO QUE ELE POSSUI COMO AUTHENTICATION RETORNA UMA INSTÂNCIA
    // DE AUTENTICAÇÃO COM UM SINALIZADOR DEFINIDO COMO VERDADEIRO.
    // SE NÃO FOR VÁLIDO, ESSE SINALIZADOR VAI RETORNAR UM VALOR NULO
    private AuthenticationManager authenticationManager;
    private JWTUtil jwtUtil;

    // CONSTRUTOR
    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        super();
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    // MÉTODO PARA AUTENTIFICAÇÃO
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            // VALORES CONVERTIDOS PARA O DTO
            CredenciaisDTO creds = new ObjectMapper().readValue(request.getInputStream(), CredenciaisDTO.class);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getSenha(), new ArrayList<>());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            return authentication;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    // MÉTODO PARA AUTENTIFICAÇÃO COM SUCESSO
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        String username = ((UserSS) authResult.getPrincipal()).getUsername();
        String token = jwtUtil.generateToken(username);
        // NECESSÁRIO PARA COLETAR O TOKEN DEPOIS
        response.setHeader("access-control-expose-headers", "Authorization");
        response.setHeader("Authorization", "Bearer " + token);
    }

    @Override
    // MÉTODO PARA AUTENTIFICAÇÃO NÃO SUCEDIDA
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {

        response.setStatus(401);
        response.setContentType("application/json");
        response.getWriter().append(json());
    }

    // CONTEÚDO DA MENSAGEM DE ERRO PARA LOGIN INCORRETO
    private CharSequence json() {
        long date = new Date().getTime();
        return "{"
                + "\"timestamp\": " + date + ", "
                + "\"status\": 401, "
                + "\"error\": \"Não autorizado\", "
                + "\"message\": \"Email ou senha inválidos\", "
                + "\"path\": \"/login\"}";
    }
}
```

```java
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // PARA LIBERAR O ACESSO AO SQL PELO h2-console
    private static final String[] PUBLIC_MATCHERS = { "/h2-console/**" };

    @Autowired
    //INTERFACE QUE REPRESENTA O AMBIENTE NO QUAL O APP ATUAL ESTÁ SENDO EXECUTADO. HABILITA A INTERFACE
    // DO H2 QUANDO SE ESTÁ EM AMBIENTE DE "test"
    private Environment env;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private UserDetailsService userDetailsService;

    // MÉTODO PARA QUE QUALQUER ENDPOINT QUE NECESSITE DE DEFESA CONTRA VULNERABILIDADE É ESPECIFICADO AQUI
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // VALIDA SE O AMBIENTE DE DESENVOLVIMENTO É O DE test, QUE ENTÃO CHAMA O HATTP HEADERS E DESABILITA O frameOptions
        if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
            http.headers().frameOptions().disable();
        }

        // DESABILITA O ATAQUE DO TIPO CSRF QUE CONSISTE EM ATAQUES DE SESSÃO ARMAZENADA DO USUÁRIO, MAS COMO ESTA
        // APLICAÇÃO NÃO ARMAZENA SESSÃO, NÃO PRECISA SER MANTIDA, EM CONTRAPARTIDA É PRECISO GARANTIR QUE A
        // SESSÃO NÃO SERÁ CRIADA, ENTÃO É EMPREGADO sessionManagement
        http.cors().and().csrf().disable();
        // FILTROS DE AUTENTICAÇÃO E AUTORIZAÇÃO
        http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
        http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));
        // para a autorização do h2-console
        http.authorizeRequests().antMatchers(PUBLIC_MATCHERS).permitAll().anyRequest().authenticated();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    // A SENHA ESTAVA SALVA NO BANCO O QUE PERMITE QUE QUALQUER UM QUE TENHA ACESSO A ESTE BANCO TAMBÉM TEM PODER
    // DESTAS SENHAS, O QUE É UMA FALHA DE SEGURANÇA,ESTE BEAN ENTÃO FORNECE A SENHA COMO CRIPTOGRAFADA NO BANCO
    // É INJETADO EM QUALQUER PARTE DO PROJETO
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
```

```java
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    // ATRIBUTOS
    private JWTUtil jwtUtil;
    private UserDetailsService userDetailsService;

    // CONSTRUTOR
    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil,
                                  UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    // MÉTODO
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // CAMPO QUE AUTORIZA A REQUISIÇÃO USANDO NO HEADER O TOKEN VALIDADO
        String header = request.getHeader("Authorization");
        if(header != null && header.startsWith("Bearer ")) {
            // O TOKEN GERADO INICIA COM A PALAVRA 'BEARER ' QUE SÃO 7 CARACTERES, OU SEJA, AQUI ESTÁ AVISANDO QUE O TOKEN
            // SÓ INICIA DE FATO APÓS 7 CARACTERES
            UsernamePasswordAuthenticationToken authToken = getAuthentication(header.substring(7));
            if(authToken != null) {
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        chain.doFilter(request, response);
    }

    // VERIFICA SE O TOKEN É VÁLIDO
    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        if(jwtUtil.tokenValido(token)) {
            String username = jwtUtil.getUsername(token);
            UserDetails details = userDetailsService.loadUserByUsername(username);
            return new UsernamePasswordAuthenticationToken(details.getUsername(), null, details.getAuthorities());
        }
        return null;
    }
}
```
#### É usado a anotação '@PreAuthorize("hasAnyRole('ADMIN')")' nos endpoints em TecnicoResource

#### Para teste no Postman, criar requisição de login, usar um json com email e senha, onde após a autenticação é gerado um token que pode ser acessado na guia Headers do resultado no Postman.
#### Acessar qualquer outra requisição, ir em Headers do Postman (na guia de inserção de parâmetros) e no campo key: Authorization, value: (token gerado). Assim será habilitado o acesso as informações.

### HEROKU
- Criar o app

- No terminal:

```
heroku -v
```
```
heroku login
```
```
heroku git:remote -a helpdesk-lipollis
```
```
git remote -v
```
```
git subtree push --prefix backend heroku main
```