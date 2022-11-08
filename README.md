## Version
1. Java 8
2. Spring 2.1.8

### Caso de uso -> EFETUAR LOGIN
1. Caso o usuário esteja cadastrado no sistema, poderá efetuar o login, dessa forma o sistema irá exigir que o mesmo informe obrigatoriamente o email e senha cadastrados no sistema. Caso não esteja cadatrado, o sistema deverá disponibilizar um formulário para cadastro do usuário.

### Caso de uso -> CADASTRO DE USUÁRIOS
1. Sistema deverá permitir que qualquer usuário possa efetuar o seu cadastro para acesso ao sistema. Os dados do usuário que deverão ser exigidos pelo sistema são: nome, email. O email deverá ser único, a fim de evitar cadastros duplos.

### Caso de uso -> CADASTRO DE LANÇAMENTOS
1. Deseja-se cadastar os lançamentos de Receita e Despesa de cada mês, com seu valor e descrição. Ao cadastrar, ele ficará pendente. Haverá opção de cancelá-lo ou efetivá-lo. Ao efetivá-lo, ele incorpora o saldo. Os usuários poderão consultar seus lançamentos, filtrando-os por ano, mês, descrição ou tipo de lançamento.

<br />
Controller <- EndPoints<br />
           <- DTO's(Data TRansfer Object)<br />
           <- Service<br />
<br />
Service <- Regras de Negócio<br />

<br />
Model <- Service<br />
      <- Entities<br />
      <- Repositories(DAO's Persistência)<br />

<br />
Controller <- Service -> Model <br />

### Passos para iniciar projeto.
1. Criar base de dados.
2. Criar Entidades no postgres
3. Modelo-> Entity-> Mapear Entidades(Entity) com JPA (Estudar sobre as funções ManyToOne...)<br />
Modelo-> Enums-> Mapear tipos de status.
4. Configurar Repositories JPA