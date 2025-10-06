package services;

import entities.*;
import enums.Status;
import repositories.ClientRepository;
import repositories.OrderRepository;
import repositories.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class OrderService {

    private final OrderRepository repository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final NotificationService notificationService;
    private final CouponService couponService;

    public OrderService(OrderRepository repository, ClientRepository clientRepository, NotificationService notificationService, ProductRepository productRepository, CouponService couponService) {
        this.repository = repository;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
        this.notificationService = notificationService;
        this.couponService = couponService;
    }

    public Order createOrder(Long clientId) {

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new NoSuchElementException("Cliente não encontrado com ID: " + clientId));
        if (client.getDocument() == null || client.getDocument().isBlank()) {
            throw new IllegalStateException("Cliente sem documento, não pode criar pedido.");
        }
        Order order = new Order(null, clientId);
        repository.save(order);
        return order;
    }

    public Order addItemToOrder(Long orderId, Long productId, BigDecimal salePrice, int quantity) {

        Order order = getOrder(orderId);
        validateStatus(order, Status.OPEN, "Somente pedidos em aberto podem ser modificados.");

        Product product = productRepository.findById(productId).orElseThrow(() -> new NoSuchElementException("Produto não encontrado com ID: " + productId));

        Optional<OrderedItem> existingItem = order.getItems().stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst();
        if (existingItem.isPresent()) {
            int newQuantity = existingItem.get().getQuantity() + quantity;
            if (salePrice == null) salePrice = existingItem.get().getSalePrice();
            order.changeQuantity(productId, newQuantity);
        } else {
            if (salePrice == null || salePrice.compareTo(BigDecimal.ZERO) <= 0)
                throw new IllegalArgumentException("Preço do item deve ser informado e maior que zero.");
            order.addItem(new OrderedItem(product.getId(), product.getName(), salePrice, quantity));
        }
        if(quantity >= 5){
          discountFixedAmount(orderId);
          }

        repository.save(order);

        return order;
    }


    public Order changeQuantity(Long orderId, Long productId, int newQuantity) {

        Order order = getOrder(orderId);
        validateStatus(order, Status.OPEN, "Somente pedidos em aberto podem alterar quantidade de itens.");
        order.changeQuantity(productId, newQuantity);
        repository.save(order);
        return order;
    }

    public Order removeItemFromOrder(Long orderId, Long productId) {

        Order order = getOrder(orderId);
        validateStatus(order, Status.OPEN, "Somente pedidos em aberto podem remover itens.");
        Optional<Product> obj = productRepository.findById(productId);
        if(obj.isEmpty()){
            System.out.println("Produto não encontrado Id: " + productId);
        }else {
            order.RemoveItemByProduct(productId);
            System.out.println("Item removido!");
        }
        repository.save(order);
        return order;
    }

    public Order finalizeOrder(Long orderId) {

        Order order = getOrder(orderId);
        if (order.getItems().isEmpty()) {
            throw new IllegalStateException("Não é possível finalizar um pedido sem itens.");
        }
        if (order.getTotalAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalStateException("Total do pedido deve ser maior que zero para finalizar.");
        }
        order.setStatus(Status.AWAITING_PAYMENT);
        repository.save(order);
        System.out.println("*****-------------------------------------------******");
        notifyClient(order, "Pedido aguardando pagamento", "Seu pedido %d está aguardando pagamento. Total: R$ %s".formatted(order.getId(), order.getTotalAmount()));
        System.out.println("*****-------------------------------------------******");
        return order;
    }

    public Order pay(Long orderId){

        Order order = getOrder(orderId);

        if (order.getStatus() != Status.AWAITING_PAYMENT)
            throw new IllegalStateException("Somente pedidos aguardando pagamento podem ser pagos.");

        System.out.println("Seu pagamento está sendo processado! order Id: " + orderId);

        Thread paymentThread = new Thread(() -> {
        try{
            Thread.sleep(7000);
            order.setStatus(Status.PAID);

            System.out.println("\nPagamento aprovado com sucesso!");
            System.out.println("Pedido #" + order.getId() + " agora está com status: PAGO!");
            System.out.println("*****-------------------------------------------******");
            notifyClient(order, "Pagamento aprovado", "Pagamento do pedido %d aprovado. Obrigado!".formatted(order.getId()));
            System.out.println("*****-------------------------------------------******");

        }catch(InterruptedException e){
            System.err.println("[ERRO] Falha no processamento do pagamento: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
        repository.save(order);
    });
        paymentThread.start();
        return order;
}

    public Order deliver(Long orderId) {

        Order order = getOrder(orderId);
        if (order.getStatus() != Status.PAID)
            throw new IllegalStateException("Somente pedidos pagos podem ser entregues.");
        order.setStatus(Status.FINISHED);
        repository.save(order);
        System.out.println("*****--------------------------------------------------------------------------------------------------******");
        notifyClient(order, "Pedido entregue", "Seu pedido %d foi entregue. Obrigado por comprar conosco!".formatted(order.getId()));
        System.out.println("*****--------------------------------------------------------------------------------------------------******");
        return order;
    }

    public Order getOrder(Long id) {

        Optional<Order> order = repository.findById(id);
        if (order == null) {
            throw new NoSuchElementException("Pedido não encontrado com ID: " + id);
        }
        return order.get();
    }

    public void findAll() {

        List<Order> orders = repository.findAll();
        if (orders.isEmpty()) {
            System.out.println("Nenhum pedido cadastrado.");
        }
        for (Order order : orders) {
            order.displayDetails();
        }

    }

    public void validateStatus(Order order, Status allowed, String message) {
        if (order.getStatus() != allowed) {
            throw new IllegalStateException(message);
        }
    }

    private void notifyClient(Order order, String subject, String message) {

        Optional<Client> client = clientRepository.findById(order.getClientId());
        if (client.isPresent())
            notificationService.notifyEmail(client.get().getEmail(), subject, message);
    }

    public void discountFixedAmount(Long orderId){

        Order order = getOrder(orderId);
        validateStatus(order, Status.OPEN, "Somente pedidos em aberto podem ser modificados.");

        List<OrderedItem> items = order.getItems();
        int totalItems = order.getItems().stream()
                .mapToInt(OrderedItem::getQuantity)
                .sum();

        double totalBefore = order.getTotalAmount().doubleValue();
        double totalAfter = totalBefore;
        double totalDiscount = 0.0;

        if (totalItems >= 5) {

            double cheapestItemValue = items.stream()
                    .mapToDouble(item -> item.getSalePrice().doubleValue())
                    .min()
                    .orElse(0.0);

            System.out.printf("Promoção aplicada! Pedido com %d itens — o item mais barato (R$ %.2f) foi grátis.%n", totalItems, cheapestItemValue);

            totalAfter -= cheapestItemValue;
            totalDiscount += cheapestItemValue;
            order.setDiscount(BigDecimal.valueOf(totalDiscount));
            System.out.printf("Cupom aplicado! Total antes: %.2f | depois: %.2f%n", totalBefore, totalAfter);
            System.out.printf("Desconto total aplicado: %.2f | Total antes: %.2f | Total final (após descontos): %.2f%n",
                    totalDiscount, totalBefore, totalAfter);
            repository.save(order);
        }
    }

    public void applyCoupon(Long orderId, String couponCode) {
        Order order = getOrder(orderId);
        validateStatus(order, Status.OPEN, "Somente pedidos em aberto podem aplicar cupom.");

        double totalBefore = order.getTotalAmount().doubleValue();
        double totalAfter = totalBefore;
        double totalDiscount = 0.0;

        if(order.getDiscount() != null && order.getDiscount().compareTo(BigDecimal.ZERO) > 0){
            System.out.println("Cupom já aplicado anteriormente. Desconto atual: " + order.getDiscount());

        }else if (couponCode != null && !couponCode.isBlank()) {
                Coupon coupon = couponService.findByCode(couponCode)
                        .filter(Coupon::isValid)
                        .orElseThrow(() -> new NoSuchElementException("Cupom inválido ou expirado! Código do cupom: " + couponCode));

                double discountedTotal = coupon.isPercentage() ?
                        totalAfter * (1 - coupon.getValue() / 100) :
                        totalAfter - coupon.getValue();

                totalDiscount += (totalAfter - discountedTotal);
                order.setDiscount(BigDecimal.valueOf(totalDiscount));
                totalAfter = discountedTotal;
                coupon.markAsUsed();
                repository.save(order);
                System.out.printf("Cupom aplicado! Total antes: %.2f | depois: %.2f%n", totalBefore, totalAfter);
                System.out.printf("Desconto total aplicado: %.2f | Total antes: %.2f | Total final (após descontos): %.2f%n",
                    totalDiscount, totalBefore, totalAfter);
        }
    }

}



