# App Estante de Livros, em desenvolvimento utilizando Java e Angular

### Techs usadas:
Backend:
- Java 11
- Spring Boot 2.3.5

Frontend:
- NodeJS 12.18.3
- Typescript 4
- Angular 10
- ngx-rocket para criar o app inicial

### OBS:
- Há uma pasta docs com as requisições (Postman)
- Crie as entidades na seguinte ordem: 
    (1-2) Autor e Categoria 
    (3) Livro (Livro depende de Autor e Categoria)
- A interface (Listagem) só pode ser testada criando as entidades no Postman, o modelo de cadastro foi iniciado mas não concluido.
- docker-compose e Dockerfile do frontend estão com erros

### Requisitos para executar:

| Software                          | Versão              |
| ----------------------------  | ------------------------ |
| Docker          | Docker version 19.03.12, build 48a66213fe |
| docker-compose | docker-compose version 1.25.0 |
| Node          | v12.18.3 |
| Java | 11 |
| Maven | Apache Maven 3.6.3 |

### Execução dos containers utilizando composer (Fix):
   
1) Utilizando o composer (Fix):
```
$ docker-compose -f docker-compose.yml up -d
```
Abra no navegador o endereço http://localhost:8000 para ver a aplicação

### Execução dos containers individualmente

2.1) Backend
### Build
```
$ docker build -t -f backend/Dockerfile bookshelf-backend .
```

### Executar
```
$ docker run -p 8080:8080 bookshelf-backend
```

2.2) Frontend (FIX)
### Build
```
$ docker build -t -f backend/Dockerfile bookshelf-backend .
```

### Executar
```
$ docker run -p 8080:8080 bookshelf-backend
```
