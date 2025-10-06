package interfaces;

import entities.Order;
import services.OrderService;

import java.math.BigDecimal;
import java.util.Scanner;

public class OrderUI {


        private final OrderService orderService;
        private final Scanner sc;

        public OrderUI(OrderService orderService, Scanner sc) {
            this.orderService = orderService;
            this.sc = sc;
        }

        public void menu() throws InterruptedException {
            while (true) {
                System.out.println("\n--- Pedidos ---");
                System.out.println("1 - Criar pedido");
                System.out.println("2 - Listar pedidos");
                System.out.println("3 - Adicionar item");
                System.out.println("4 - Remover item");
                System.out.println("5 - Alterar quantidade de item");
                System.out.println("6 - Aplicar cupom de desconto");
                System.out.println("7 - Finalizar pedido");
                System.out.println("8 - Pagar pedido");
                System.out.println("9 - Entregar pedido");
                System.out.println("0 - Voltar");
                System.out.print("Escolha: ");
                int op = sc.nextInt();
                sc.nextLine();

                switch (op) {
                    case 1 -> ToCreate();
                    case 2 -> list();
                    case 3 -> addItem();
                    case 4 -> removeItem();
                    case 5 -> changeQuantity();
                    case 6 -> applyCoupon();
                    case 7 -> finish();
                    case 8 -> pay();
                    case 9 -> deliver();
                    case 0 -> { return; }
                    default -> System.out.println("Opção inválida!");
                }
            }
        }

        private void ToCreate() {
            System.out.print("ID do cliente: ");
            long clientId = sc.nextLong(); sc.nextLine();
            try {
                Order order = orderService.createOrder(clientId);
                System.out.println("Pedido criado: " + order.getId());
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }

        private void list() {
            orderService.findAll();
        }

        private void addItem() {
            System.out.print("ID do pedido: ");
            long orderId = sc.nextLong();
            System.out.print("ID do produto: ");
            long productId = sc.nextLong();
            System.out.print("Quantidade: (Se adicionar a quantidade 5 produtos ou mais um é grátis!");
            int qtd = sc.nextInt();
            System.out.print("Preço de venda: ");
            BigDecimal salePrice = sc.nextBigDecimal();
            sc.nextLine();
            try {
                orderService.addItemToOrder(orderId, productId, salePrice, qtd);
                System.out.println("Item adicionado!");
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }

        private void removeItem() {
            System.out.print("ID do pedido: ");
            long orderId = sc.nextLong();
            System.out.print("ID do produto: ");
            long productId = sc.nextLong();
            sc.nextLine();
            try {
                orderService.removeItemFromOrder(orderId, productId);
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }

        private void changeQuantity() {
            System.out.print("ID do pedido: ");
            long orderId = sc.nextLong();
            System.out.print("ID do produto: ");
            long productId = sc.nextLong();
            System.out.print("Nova quantidade: ");
            int qtd = sc.nextInt();
            sc.nextLine();
            try {
                orderService.changeQuantity(orderId, productId, qtd);
                System.out.println("Quantidade atualizada!");
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }

        private void finish() {
            System.out.print("ID do pedido: ");
            long orderId = sc.nextLong(); sc.nextLine();
            try {
                orderService.finalizeOrder(orderId);
                System.out.println("Pedido finalizado e aguardando pagamento!");
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }

        private void pay() {
            System.out.print("ID do pedido: ");
            long orderId = sc.nextLong(); sc.nextLine();
            try {
                orderService.pay(orderId);
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }

        private void deliver() {
            System.out.print("ID do pedido: ");
            long orderId = sc.nextLong(); sc.nextLine();
            try {
                orderService.deliver(orderId);
                System.out.println("Pedido entregue!");
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }

        public void applyCoupon() {
            System.out.print("ID do pedido: ");
            long orderId = sc.nextLong();
            System.out.print("Código do cupom: ");
            String couponCode = sc.next();
            sc.nextLine();
            try {
                orderService.applyCoupon(orderId, couponCode);
                System.out.println("Cupom aplicado!");
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }
