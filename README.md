# **Matchinvest REST API**

## **Visão Geral**

### O **Matchinvest REST API é** um serviço backend desenvolvido em Java 17 com Spring Boot para gerenciar usuários, perfis de investidores e assessores, autenticação via JWT e documentação interativa via Swagger.

## **Funcionalidades Principais**

- **Autenticação & Autorização**: registro e login de usuários com Spring Security e JWT.
- **Perfis de Usuário**:
    - Registro de investidores e assessores.
    - CRUD completo para perfis de investidores (`/api/v1/investors`).
    - CRUD completo para perfis de assessores (`/api/v1/advisors`).
- **População Inicial**: scripts SQL para dados de exemplo via `data.sql`.
- **Banco de Dados Em Memória & Criptografado**: H2 configurado com criptografia em repouso.
- **Validações & Segurança**:
    - Validação de DTOs com Bean Validation (`@Valid`).
    - Sanitização básica de inputs.
    - SAST: SpotBugs integrado.
- **Logging**: SLF4J + Logback para registro de requisições e eventos.
- **Documentação & Testes**:
    - Swagger/OpenAPI para exploração interativa.
    - Testes unitários e de integração (JUnit & MockMvc).

## **Tecnologias e Dependências**
- Java 17
- Spring Boot 3.5
- Spring Security (JWT)
- Spring Data JPA
- H2 Database (arquivo com criptografia AES)
- MapStruct (mapeamento DTO ↔ Entity)
- Lombok
- SLF4J + Logback
- SpotBugs (SAST)
- Swagger/OpenAPI (springdoc-openapi)

## **Pré‑requisitos**
- JDK 17+
- Maven 3.6+
- (Opcional) IDE com suporte a Spring Boot

## **Configuração**
1. **Clone o repositório**:
```bash
git clone https://github.com/Natan-TI/MatchInvestAPI
cd matchinvest-rest
```

2. **Aplicação de Propriedades**: Edite `src/main/resources/application.properties` com:
```yaml
#H2 com criptografia em repouso
spring.datasource.url=jdbc:h2:file:./data/matchinvest;CIPHER=AES
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=filePassword userPassword
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

#JPA
spring.jpa.hibernate.ddl-auto=create
spring.jpa.defer-datasource-initialization=true
spring.sql.init.mode=always

#JWT
jwt.secret=aMinhaChaveSecretaSuperSeguraAqui1234567890
jwt.expiration=86400000
```

3. **Scripts SQL**:
- `data.sql` em `src/main/resources` popula dados iniciais.
- Ajuste conforme necessidade.

## **Executando a Aplicação**
```bash
mvn clean spring-boot:run
```
- Acesse o console H2 em: `http://localhost:8080/h2-console`.
- URL de conexão: `jdbc:h2:file:./data/matchinvest;CIPHER=AES`
- Usuário: `sa`
- Senha: `filePassword userPassword`

## **Documentação Swagger**
Após subir a aplicação, a interface interativa estará disponível em:
```bash
http://localhost:8080/swagger-ui/index.html
```
### Autenticação no Swagger
1. Clique em **Authorize** (cadeado no canto superior).
2. Insira:
```java
Bearer <seu_jwt_aqui>
```
3. Agora você pode testar endpoints protegidos.

## **Endpoints Principais**

- **POST**   - `/api/v1/auth/register`    - Registrar novo usuário                 - Sem role
- **POST**   - `/api/v1/auth/login`       - Login e obtenção de JWT                - Sem role
- **POST**   - `/api/v1/auth/choose-role` - Selecionar uma role (INVESTOR/ADVISOR) - Sem role
- **GET**    - `/api/v1/investors`        - Listar investidores                    - ROLE_INVESTOR/ADVISOR
- **POST**   - `/api/v1/investors`        - Criar perfil de investidor             - ROLE_INVESTOR
- **PUT**    - `/api/v1/investors/{id}`   - Atualizar perfil                       - ROLE_INVESTOR
- **DELETE** - `/api/v1/investors/{id}`   - Remover perfil                         - ROLE_INVESTOR
- **GET**    - `/api/v1/advisors`         - Listar assessores                      - ROLE_INVESTOR/ADVISOR
- **POST**   - `/api/v1/advisors`         - Criar perfil de assessor               - ROLE_ADVISOR

Consulte o Swagger para detalhes de request/response.

## **Segurança e Qualidade de Código**

- **SAST**: SpotBugs configurado no Maven (`mvn spotbugs:check`).
- **Logs**:
    - Requisições principais e erros são logados com SLF4J.
    - Exemplo em `InvestorController`:
    ```java
    private static final Logger log = LoggerFactory.getLogger(InvestorController.class);
    ```

## **Integrantes**

- Enzo Luiz Goulart                 - **RM99666**
- Gustavo Henrique Santos Bonfim    - **RM98864**
- Kayky Paschoal Ribeiro            - **RM99929**
- Lucas Yuji Farias Umada           - **RM99757**
- Natan Eguchi dos Santos           - **RM98720**