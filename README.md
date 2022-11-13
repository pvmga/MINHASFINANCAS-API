## Version
1. Java 8
2. Spring 2.1.8

### Controller 
      Controller <- EndPoints
      Controller <- DTO's(Data TRansfer Object)
      Controller <- Service

### Service 
     Service <- Regras de Negócio

### Model 
      Model <- Service
      Model  <- Entities<br />
      Model  <- Repositories(DAO's Persistência)<br />

### Estrutura Genérica 
      Controller <- Service -> Model

### Caso de uso -> EFETUAR LOGIN
1. Caso o usuário esteja cadastrado no sistema, poderá efetuar o login, dessa forma o sistema irá exigir que o mesmo informe obrigatoriamente o email e senha cadastrados no sistema. Caso não esteja cadatrado, o sistema deverá disponibilizar um formulário para cadastro do usuário.

### Caso de uso -> CADASTRO DE USUÁRIOS
1. Sistema deverá permitir que qualquer usuário possa efetuar o seu cadastro para acesso ao sistema. Os dados do usuário que deverão ser exigidos pelo sistema são: nome, email. O email deverá ser único, a fim de evitar cadastros duplos.

### Caso de uso -> CADASTRO DE LANÇAMENTOS
1. Deseja-se cadastar os lançamentos de Receita e Despesa de cada mês, com seu valor e descrição. Ao cadastrar, ele ficará pendente. Haverá opção de cancelá-lo ou efetivá-lo. Ao efetivá-lo, ele incorpora o saldo. Os usuários poderão consultar seus lançamentos, filtrando-os por ano, mês, descrição ou tipo de lançamento.

### Passos para iniciar projeto.
1. Criar base de dados.
2. Criar Entidades(Tabelas) no postgres
3. Modelo-> Entity-> Mapear Entidades(Entity) com JPA (Estudar sobre as funções ManyToOne...)<br />
   Modelo-> Enums-> Serão os tipos dos campos status e tipo. Estou forçando a sempre receber estes valores.<br />
   Modelo-> Repository-> Configurar interface Jpa<br />
6. Service-> UsuarioService.java-> Implementar Interface métodos iniciais que serão utilizados na camada Implemetation.
7. Service-> Impl-> UsuarioServiceImpl.java-> Classe que irá implementar a interface criada no passo 6 contendo mesmos métodos
8. VALIDAÇÃO E-MAIL. <br />
   Modelo-> Repository-> UsuarioRepository-> Camada repository, criar existsByEmail.<br />
   Service-> Impl-> UsuarioServiceImpl -> Implementar o exists criado.<br />
   Service-> Impl-> Criar exception customizada.<br />
   Exception-> RegraNegocioException.java<br />

### (Test Integração) Primeiro Test "Valida Existência Email Repositório" (Validará nosso método criado acessando diretamente o repositorio)
9. Configurar application-test.properties com banco de dados alternativo h2.
10. Packing de Test, criar a mesma estrutura do projeto padrão. 
11. Modelo-> Repository-> UsuarioRepositoryTest.java (junit será os pacote importados)

### (Test Unitário) Segundo Test "Valida Existência Email Service" (Validará nosso método acessando diretamente a class de implementação)
12. Packing de Test, criar a mesma estrutura do projeto padrão.
13. Service-> UsuarioServiceTest.java

### Método autenticar
14. Modelo-> Repository-> UsuarioRepository.java
15. Exception-> ErroAutenticacaoException.java
16. Método autenticar.

### Salvar usuário
17. Service-> Impl-> UsuarioServiceImpl.java-> Implementar salvarUsuario(Usuario usuario)
