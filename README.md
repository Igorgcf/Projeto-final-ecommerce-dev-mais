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
