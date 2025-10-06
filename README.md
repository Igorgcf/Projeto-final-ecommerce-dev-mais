# Projeto-final-ecommerce-dev-mais
## Projeto referente ao módulo 03 do programa Desenvolva+.
🛒 Ada Commerce – E-Commerce (Java)

Ada Commerce é um projeto de simulação de um sistema de E-Commerce desenvolvido em Java puro, com base em princípios de orientação a objetos, SOLID e programação funcional.
Este sistema foi projetado para funcionar sem banco de dados, utilizando persistência em memória e serialização de arquivos (.dat) para armazenamento local.

🚀 Funcionalidades Principais
👤 Clientes

Cadastrar novos clientes com documento obrigatório

Listar e atualizar dados de clientes

Os clientes não podem ser excluídos (mantidos como histórico)


📦 Produtos

Cadastrar, listar e atualizar produtos

Os produtos não são excluídos (mantidos como histórico)


🧾 Pedidos

Criar pedidos para clientes cadastrados

Adicionar, alterar e remover itens do pedido

Controlar status do pedido: ABERTO → AGUARDANDO PAGAMENTO → PAGO → FINALIZADO

Validar regras de negócio:

Um pedido só pode ser finalizado se possuir ao menos um item e valor > 0

Após o pagamento, o pedido pode ser entregue


💰 Pagamentos (com Threads)

Processamento assíncrono de pagamento com Thread ou ExecutorService

Exibe mensagem "Seu pagamento está sendo processado..."

Permite que o menu continue ativo durante o processamento

Simula tempo de aprovação e notificação automática do cliente


🎟️ Cupons de Desconto

Criar, listar, e expirar cupons de desconto

Aplicar cupons a pedidos com validação automática:

Cupom válido por data (LocalDate)

Cupom não utilizado anteriormente

Aplicação de desconto fixo ou percentual


🧮 Regras de Desconto

Descontos simples: desconto direto no valor total (fixo ou percentual)

Regras compostas: combinação de múltiplos descontos

Promoção automática: se o pedido possuir 5 ou mais produtos, o item mais barato é gratuito (100% de desconto apenas nesse produto)

⚙️ Execução do Projeto
Pré-requisitos

Java 17+ instalado
IDE como IntelliJ IDEA, Eclipse ou VS Code (com extensão Java)
Nenhuma dependência externa é necessária

Passos:
1. Clone este repositório:
```bash
git clone https://github.com/Igorgcf/Projeto-final-ecommerce-dev-mais.git
```
2. Compile o projeto (Manualmente opcional sem IDE):
```bash
javac src/**/*.java
```
3. Execute o programa:
```bash
java -cp src Main
```

💾 Persistência em Arquivos

Os dados são salvos em arquivos .dat dentro da pasta ada-commerce-data/, por exemplo:
ada-commerce-data/

 ├── clients.dat
 
 ├── products.dat
 
 ├── orders.dat
 
 └── coupons.dat

⚠️ Esses arquivos armazenam objetos Java serializados.
Eles não são legíveis manualmente, mas mantêm o histórico das operações realizadas.

🧵 Threads de Pagamento

A simulação de pagamento é feita de forma não bloqueante, utilizando Thread ou ExecutorService.
Durante o processamento, o menu permanece acessível e o sistema exibe:
```nginx
Seu pagamento está sendo processado...

Pagamento aprovado com sucesso!
Pedido #1 agora está com status: PAGO
```

📘 Conceitos Acadêmicos Aplicados

Encapsulamento – classes bem definidas e atributos privados

Polimorfismo – comportamento diferenciado para descontos simples e compostos

Herança e Composição – entre entidades e serviços

Programação Funcional – uso de Lambdas e Streams

Concorrência – processamento de pagamento com Threads

Boas práticas SOLID – responsabilidade única e baixo acoplamento

🏆 Créditos

Projeto desenvolvido por Igor Gonçalves de Freitas 💡
Como parte de estudo avançado em Java, POO, Programação Funcional e Princípios SOLID, aplicados a um cenário real de E-Commerce.

🧠 Tecnologias e Conceitos Utilizados

| Categoria                     | Descrição                                                                  |
| ----------------------------- | -------------------------------------------------------------------------- |
| **Linguagem**                 | Java 17+                                                                   |
| **Paradigma**                 | Orientação a Objetos e Programação Funcional                               |
| **Datas e horários**          | `java.time.LocalDate` e `LocalDateTime`                                    |
| **Expressões Lambda**         | Utilizadas em filtros, mapeamentos e validações                            |
| **Streams API**               | Operações de filtro, mapeamento e redução sobre coleções                   |
| **Interfaces Funcionais**     | Aplicadas em regras de desconto e validação                                |
| **Threads / ExecutorService** | Processamento assíncrono de pagamento                                      |
| **Persistência**              | Serialização com `ObjectOutputStream` e `ObjectInputStream`                |
| **Tratamento de Exceções**    | Controle de falhas em I/O e valores nulos                                  |
| **Generics**                  | Implementados em classes de serviço e repositório para maior flexibilidade |

src/

 ├── model/
 
 │    ├── Client.java
 
 │    ├── Product.java
 
 │    ├── Order.java
 
 │    ├── OrderedItem.java
 
 │    ├── Coupon.java
 
 │    └── enums/Status.java
 
 │
 
 ├── service/
 
 │    ├── ClientService.java
 
 │    ├── ProductService.java
 
 │    ├── OrderService.java
 
 │    ├── CouponService.java
 
 │    ├── NotificationService.java
 
 │
 
 ├── repository/
 
 │    ├── ClientRepository.java
 
 │    ├── ProductRepository.java
 
 │     ├── OrderRepository.java
      
 │    ├── CouponReposiory.java
      
 │     ├── Repository.java
 
 │
 
 ├── interfaces/
 
 │    ├── ClientUI.java
 
 │    ├── ProductUI.java
 
 │    ├── OrderUI.java
 
 │    └── CouponUI.java
 
 └── Main.java

Nenhuma dependência externa é necessária
 
![image](https://img.freepik.com/premium-photo/wildlife-tracks-document-animal-tracks-snow-inviting-viewers-guess-what-wildlife-might-be-nearby_997534-75869.jpg?semt=ais_hybrid&w=740&q=80)
