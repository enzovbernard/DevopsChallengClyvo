# Clyvo - Gestão Veterinária

## Descrição do Projeto

Aplicação web desenvolvida para a disciplina de Java Advanced (FIAP), com o objetivo de gerenciar o cuidado de animais domésticos: cadastro de responsáveis, animais, veterinários, doenças e vacinas, além do agendamento de consultas e do controle de aplicação de vacinas.

O projeto evoluiu a partir da API REST Clyvo desenvolvida no semestre anterior, agora reconstruído como uma aplicação web completa com camadas de visualização (Thymeleaf), controle de versão de banco de dados (Flyway) e autenticação/autorização por perfil (Spring Security).

## Tecnologias Utilizadas

- Java 21
- Spring Boot 4
- Spring MVC + Thymeleaf
- Spring Security (autenticação por sessão)
- Spring Data JPA / Hibernate
- Flyway (controle de versão do banco de dados)
- Oracle Database
- Bootstrap 5
- Maven

## Arquitetura

O projeto segue arquitetura em camadas:

- **Control**: controllers Spring MVC, recebem requisições e retornam `ModelAndView` para as telas Thymeleaf.
- **Model**: entidades JPA que representam as tabelas do banco de dados.
- **Repository**: interfaces Spring Data JPA para acesso ao banco.
- **Security**: configuração do Spring Security, autenticação e liberação de rotas por perfil.
- **Templates (Thymeleaf)**: telas HTML da aplicação.

## Funcionalidades

### Cadastros (CRUD completo)
- Responsáveis
- Animais
- Veterinários (somente ADMIN)
- Doenças (somente ADMIN)
- Vacinas (somente ADMIN)

### Fluxos de negócio
- **Agendamento de consultas**: vincula um animal a um veterinário em uma data futura, impedindo que o mesmo veterinário tenha duas consultas marcadas no mesmo dia.
- **Aplicação de vacinas**: registra a aplicação de uma vacina em um animal, impedindo reaplicação antes do prazo definido pela frequência de doses.

### Segurança
- Login por sessão com Spring Security.
- Dois perfis de usuário: `ADMIN` (acesso completo) e `USER` (acesso a Responsáveis, Animais, Consultas e Vacinação).
- Rotas de catálogo (Veterinários, Doenças, Vacinas) restritas ao perfil ADMIN.
- Senhas armazenadas com hash BCrypt.
- Proteção CSRF ativa em todos os formulários.

## Pré-requisitos

- JDK 21 instalado
- Eclipse IDE (com suporte a Maven)
- Acesso a um banco de dados Oracle (11g+ recomendado, testado em Oracle 19c)

## Configuração

### 1. Clonar o repositório

```
git clone https://github.com/enzovbernard/Java-ChallengeClyvo.git
```

### 2. Importar no Eclipse

File > Import > Maven > Existing Maven Projects, selecionando a pasta clonada.

### 3. Configurar o JDK 21

Caso o projeto não reconheça o JDK 21 automaticamente: botão direito no projeto > Properties > Java Build Path > Libraries, e ajuste a JRE System Library para a versão 21. Se necessário, adicione o JDK em Window > Preferences > Java > Installed JREs antes disso.

### 4. Configurar as variáveis de ambiente do banco de dados

As credenciais do banco **não ficam no código-fonte**, por segurança. Elas são lidas de variáveis de ambiente:

| Variável | Descrição | Exemplo |
|---|---|---|
| `DB_URL` | URL JDBC do Oracle | `jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL` |
| `DB_USERNAME` | Usuário do Oracle | `rm000000` |
| `DB_PASSWORD` | Senha do Oracle | `sua_senha` |

No Eclipse: Run > Run Configurations > (selecione a configuração do projeto, ou crie uma nova do tipo "Java Application" apontando para `ClyvoJavaApplication`) > aba Environment > New, adicionando as três variáveis acima.

### 5. Executar a aplicação

Botão direito em `ClyvoJavaApplication.java` > Run As > Java Application (ou use a Run Configuration já criada no passo anterior).

Na primeira execução, o Flyway cria automaticamente todas as tabelas e popula os usuários iniciais — não é necessário rodar nenhum script SQL manualmente.

### 6. Acessar a aplicação

Abra o navegador em:

```
http://localhost:8080/login
```

## Usuários de Teste

| Usuário | Senha | Perfil |
|---|---|---|
| admin | 1234 | ADMIN |
| usuario | 1234 | USER |

## Perfis de Acesso

| Recurso | ADMIN | USER |
|---|---|---|
| Responsáveis | Sim | Sim |
| Animais | Sim | Sim |
| Consultas | Sim | Sim |
| Vacinação | Sim | Sim |
| Veterinários | Sim | Não |
| Doenças | Sim | Não |
| Vacinas | Sim | Não |

Tentativas de acesso direto (pela URL) a rotas restritas por um usuário sem permissão são redirecionadas para uma página de acesso negado.



## Integrantes

- Caio Kenzo Tayra - RM562979
- Enzo Vieira Bernardini - RM563000
- Caio Kenzo Tayra - RM561857
