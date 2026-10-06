# API de Agendamento de Comunicações

API REST desenvolvida em **Java** e **Spring Boot** para gerenciamento de agendamentos de envio de comunicações. O projeto permite criar, consultar e cancelar agendamentos, utilizando **PostgreSQL** como banco de dados e **Docker** para padronização do ambiente. A infraestrutura está preparada para execução na **AWS** (EC2, RDS, CloudWatch e SQS).

> **Origem:** trabalho baseado no [desafio técnico do Magalu](https://drive.google.com/file/d/1GkY5ZNsxDoiVcdDNCNDxYVd4nrvK_U0x/view?pli=1).
>
> **Objetivo:** colocar em prática os aprendizados em **AWS** e **PostgreSQL**, aplicando conceitos de computação em nuvem e banco de dados em uma aplicação backend real.

## Sumário

- [Sobre o projeto](#sobre-o-projeto)
- [Funcionalidades](#funcionalidades)
- [Arquitetura](#arquitetura)
- [Tecnologias](#tecnologias)
- [Modelagem](#modelagem)
- [Autenticação e autorização](#autenticação-e-autorização)
- [Endpoints](#endpoints)
- [Documentação com Swagger](#documentação-com-swagger)
- [Fluxo de um agendamento](#fluxo-de-um-agendamento)
- [Como executar com Docker](#como-executar-com-docker)
- [Segurança](#segurança)
- [Aprendizados](#aprendizados)
- [Autor](#autor)

## Sobre o projeto

A aplicação é uma plataforma de agendamento de comunicações. Os canais suportados são **Email**, **SMS**, **Push** e **WhatsApp**.

> **Nota:** o envio efetivo da comunicação não faz parte da primeira versão. O sistema registra e controla o agendamento, deixando a estrutura preparada para uma futura implementação do processamento dos envios.

## Funcionalidades

- **Agendamento:** o cliente informa data e hora de envio, destinatário, mensagem e tipo da comunicação. Após a validação, o agendamento é salvo com o status inicial `AGENDADA`.
- **Consulta:** retorna as informações da comunicação e seu status atual a partir do identificador.
- **Cancelamento:** altera o status do registro para `CANCELADA`, respeitando as regras de negócio da aplicação.

## Arquitetura

A aplicação utiliza uma **arquitetura em camadas**, separando responsabilidades entre *controller*, *service*, *repository* e *entidades*.


![image alt](https://github.com/lucaslleonardo/magaluaws/raw/360c26ad091a697242e4916bd74f1238bb46b5b6/DiagramaAws.jpg)


**Amazon EC2:** executa a aplicação Spring Boot em um container Docker. Um **Elastic IP** mantém o endereço público fixo, evitando atualizar o IP a cada reinicialização.

**Amazon RDS:** hospeda o PostgreSQL, separando a aplicação do gerenciamento direto do servidor de banco de dados.

**Amazon CloudWatch:** recebe os logs gerados pela aplicação, permitindo acompanhar eventos e erros sem depender apenas dos logs do container.

**Amazon SQS:** possibilita o processamento assíncrono das comunicações. Na primeira versão, o sistema apenas registra o agendamento; a fila fica preparada para um futuro componente responsável pelo envio, desacoplando a API do processamento.

```
Spring Boot → SQS → Processador de comunicação → Email / SMS / Push / WhatsApp
```

## Tecnologias

**Backend**

- Java 21
- Spring Boot (Web, Data JPA, Validation)
- Spring Security com JWT
- Swagger / OpenAPI (SpringDoc)
- PostgreSQL
- Maven

**Infraestrutura e DevOps**

- Docker e Docker Compose
- Amazon EC2, RDS, CloudWatch e SQS

## Modelagem

A entidade principal representa uma solicitação de comunicação:

```
Comunicacao
├── id
├── dataHoraEnvio
├── destinatario
├── mensagem
├── tipo
├── status
├── dataCriacao
└── dataAtualizacao
```

**Tipo:** `EMAIL`, `SMS`, `PUSH`, `WHATSAPP`

**Status**

| Valor | Descrição |
|---|---|
| `AGENDADA` | Agendamento registrado, aguardando envio |
| `ENVIADA` | Comunicação enviada (uso futuro) |
| `CANCELADA` | Agendamento cancelado |

## Autenticação e autorização

A API utiliza **Spring Security** para o login de usuários, com tokens **JWT** e controle de acesso por **roles**:

| Role | Descrição |
|---|---|
| `USUARIO` | Perfil de cliente/usuário da plataforma |
| `FUNCIONARIO` | Perfil de funcionário, com acesso conforme as permissões definidas na aplicação |

1. O usuário realiza login e recebe um token JWT.
2. As requisições protegidas enviam o token no cabeçalho `Authorization: Bearer <seu_token>`.
3. O Spring Security valida o token e verifica se a role do usuário permite acessar o recurso.

## Endpoints

### Criar agendamento

`POST /mensagem`

Requisição:

```json
{
  "dataHoraEnvio": "dd/MM/yyyy HH:mm:ss",
  "destinatario": "cliente@email.com",
  "mensagem": "Sua comunicação foi agendada.",
  "tipo": "EMAIL"
}
```

Resposta:

```json
{
  "id": 1,
  "dataHoraEnvio": "dd/MM/yyyy HH:mm:ss",
  "destinatario": "cliente@email.com",
  "mensagem": "Sua comunicação foi agendada.",
  "tipo": "EMAIL",
  "status": "AGENDADA"
}
```

### Consultar agendamento

`GET /mensagem/{id}`

### Cancelar agendamento

`DELETE /mensagem/{id}`

O cancelamento não é permitido para comunicações que já tenham sido processadas.

## Documentação com Swagger

A API é documentada com **Swagger (OpenAPI)**, que permite visualizar e testar os endpoints pelo navegador. Com a aplicação em execução, acesse:

```
http://localhost:8080/swagger-ui.html
```

Para testar rotas protegidas, faça login, copie o token JWT retornado e informe-o no botão **Authorize**.

## Fluxo de um agendamento

```
Cliente
   │  POST /comunicacoes
   ▼
Controller
   │
   ▼
Service
   ├── Validação
   ├── Regras de negócio
   └── Persistência
           │
           ▼
       PostgreSQL
           │
           ▼
     status = AGENDADA
```

## Como executar com Docker

O Docker Compose sobe a API (Spring Boot) e o PostgreSQL em containers isolados.

**1. Clone o repositório**

```bash
git clone https://github.com/lucaslleonardo/magaluaws.git
cd magaluaws
```

**2. Crie o arquivo `.env` na raiz do projeto**

```env
DATABASE_NAME=comunicacoes
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=sua_senha_segura
JWT_SECRET=uma_chave_secreta_longa_e_aleatoria
JWT_EXPIRATION=3600000
```

**3. Suba os containers**

```bash
docker compose up --build       # iniciar
docker compose up --build -d    # iniciar em segundo plano
docker compose down             # parar
```

| Serviço | Porta |
|---|---|
| API | `8080` |
| PostgreSQL (host) | `5433` |

## Segurança

Práticas adotadas:

- Não versionar senhas ou credenciais da AWS; usar variáveis de ambiente para configurações sensíveis
- Não armazenar Access Keys no código; usar IAM com permissões mínimas
- Restringir o acesso ao RDS por Security Groups, permitindo apenas a aplicação (o banco não é exposto à internet)
- Evitar exposição desnecessária de portas
- Utilizar HTTPS em produção

Na execução na AWS, além das variáveis do `.env`, a aplicação utiliza:

```
DATABASE_HOST
DATABASE_PORT
AWS_REGION
AWS_SQS_QUEUE
```

## Aprendizados

A implementação do **Spring Security** foi uma oportunidade de treinar o que já havia sido aprendido e de colocar em prática novos aprendizados. Conceitos praticados no projeto:

- Autenticação e autorização com Spring Security, JWT e roles
- Documentação de API com Swagger e logs para acompanhamento da aplicação
- Deploy em EC2 com Elastic IP
- Banco gerenciado com RDS (PostgreSQL)
- Comunicação assíncrona com SQS
- Monitoramento com CloudWatch
- Permissões com IAM e rede com Security Groups
- Containerização com Docker

A proposta é compreender não apenas como utilizar cada serviço, mas também qual problema cada um resolve dentro da arquitetura.

## Autor

**Lucas Leonardo** — [@lucaslleonardo](https://github.com/lucaslleonardo)
