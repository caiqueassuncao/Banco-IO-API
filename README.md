# Banco API

API REST de banco desenvolvida em Spring Boot com interface web em HTML puro.

## Tecnologias

- Java 17
- Spring Boot 3.2
- Maven
- HTML + CSS + JavaScript (frontend)

## Como rodar

### Pré-requisitos

- Java 17 instalado
- Maven instalado e configurado no PATH

### 1. Iniciar a API

Abra o CMD, navegue até a pasta do projeto e rode:

```cmd
cd C:\caminho\para\banco
mvn spring-boot:run
```

A API sobe em `http://localhost:8082`

### 2. Abrir o frontend

Com a API rodando, abra o arquivo `banco-frontend.html` direto no navegador.

---

## Endpoints

### Contas

| Método | Rota | Descrição |
|---|---|---|
| POST | `/api/contas` | Criar conta |
| GET | `/api/contas` | Listar todas |
| GET | `/api/contas/{id}` | Buscar por ID |
| DELETE | `/api/contas/{id}` | Deletar conta |

### Operações

| Método | Rota | Descrição |
|---|---|---|
| POST | `/api/contas/{id}/depositar` | Depositar |
| POST | `/api/contas/{id}/sacar` | Sacar |
| POST | `/api/contas/{id}/transferir` | Transferir para outra conta |
| GET | `/api/contas/{id}/historico` | Histórico de transações |

---

## Exemplos de requisição

### Criar conta
```json
POST /api/contas
{
  "titular": "João Silva",
  "cpf": "123.456.789-00",
  "saldoInicial": 1000.00
}
```

### Depositar
```json
POST /api/contas/{id}/depositar
{
  "valor": 500.00
}
```

### Sacar
```json
POST /api/contas/{id}/sacar
{
  "valor": 200.00
}
```

### Transferir
```json
POST /api/contas/{id}/transferir
{
  "destinoId": "id-da-conta-destino",
  "valor": 300.00
}
```

---

## Estrutura do projeto

```
banco/
├── pom.xml
├── banco-frontend.html
└── src/
    └── main/
        ├── java/com/banco/
        │   ├── BancoApplication.java
        │   ├── controller/
        │   │   └── ContaController.java
        │   ├── service/
        │   │   └── ContaService.java
        │   ├── repository/
        │   │   └── ContaRepository.java
        │   ├── model/
        │   │   ├── Conta.java
        │   │   └── Transacao.java
        │   └── exception/
        │       └── GlobalExceptionHandler.java
        └── resources/
            └── application.properties
```

---

## Observações

- Os dados ficam em memória — ao reiniciar o servidor tudo é zerado
- O frontend se conecta automaticamente à API em `localhost:8082`
- Para testar os endpoints manualmente, use o Postman

---

## Licença

MIT
