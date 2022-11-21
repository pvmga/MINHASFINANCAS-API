## APP
1. GitToolBox
2. Key Promoter X
3. One Dark Theme
4. Rainbow Brackets
5. Translation
6. Code With me
7. Nyan Progress bar

## Version
1. Java 8 (Eu estou usando 11)
2. Spring 2.1.8 (Eu estou usando 2.7.5)

--------- Choose dependencies<br>

3. Spring Boot DevTools (O módulo DevTools inclui ferramentas utilitárias no projeto, dentre elas a Automatic Restart, que reinicia o servidor automaticamente ao detectar alterações no código fonte da aplicação.)<br>
4. Spring Web<br>
5. PostgreSQL (Conectar com o banco de dados PostgreSQL)<br>
6. Spring Data JPA (Módulo incluiso no Spring Boot para trabalhar com banco de dados, deixando as coisas mais fáceis. Por podrão o Spring Boot utiliza o Hibernate como implementação do JPA, mas se quiser consegue configurar outro.)<br>
7. Lombok developer (Não necessitar criar os setter e getter na mão)<br>
<br>

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
1. Configurar conexão com banco de dados.
2. Criar base de dados.
3. Modelo-> Entity-> Mapear Entidades(Entity) <br />
      3.1-> Estudar sobre as funções ManyToOne.<br />
      3.2-> Caso o banco de dados esteja dentro de um schema, não esquecer de definir.<br />
      3.3-> Gerar hashCode and equals methods. <br />
      3.4-> Gerar toString() - Facilitará o debug <br />
      3.5-> @ManyToOne (Many entidade atual e One é --- X lançamentos para 1 usuário)<br />
      3.6-> @ManyToMany (Muitos para Muitos)<br />
      3.7-> @OneToMany (1 para muitos)<br />
      3.8-> @OneToOne (1 para 1)<br />
   Modelo-> Enums-> Serão os tipos dos campos status e tipo. Estou forçando a sempre receber estes valores.<br />
      3.9-> EnumType.STRING (Irá salvar o nome do banco de dados) EnumType.ORDINAL (Irá salvar 0 ou 1, mas também depende da quantidade de tipos que tem) <br />
   Modelo-> Repository-> Configurar interface Jpa<br />


6. Service-> UsuarioService.java-> Interface que será definido para trabalhar com a entidade Usuários.
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

### Iniciando com controller
18. Salvar e Autenticar com DTO.

### Iniciando com Lançamento service. (CRUD basicamente)
19. Service-> LancamentoService.java -> Criado todos métodos da interface
20. Service->Impl-> LancamentoServiceImpl -> Implementado todos métodos aplicado na interface

### Para validar lançamento
21. Service-> LancamentoService.java -> criar método para validar Lancamento
22. Service->Impl-> LancamentoServiceImpl.java -> Implementar método criado na interface

### Controller
23. Controller-> LancamentoController.java
