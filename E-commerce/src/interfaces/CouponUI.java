package interfaces;

import services.CouponService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class CouponUI {

    private final CouponService service;
    private final Scanner scanner;

   private static transient final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public CouponUI(CouponService service, Scanner scanner) {
        this.service = service;
        this.scanner = scanner;
    }

    public void menu(){

        while(true){
            System.out.println("\n--- Cupons ---");
            System.out.println("1 - Criar cupom");
            System.out.println("2 - Listar cupons válidos");
            System.out.println("3 - Expirar cupom");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");
            int op = scanner.nextInt(); scanner.nextLine();

            switch (op) {
                case 1 -> create();
                case 2 -> findAll();
                case 3 -> expire();
                case 0 -> { return; }
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private void create(){
        System.out.print("Código: ");
        String code = scanner.nextLine();
        System.out.print("Valor do desconto: ");
        double value = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("É percentual? (s/n): ");
        boolean percentage = scanner.nextLine().equalsIgnoreCase("s");
        System.out.print("Validade (dd/MM/yyyy): ");
        LocalDate validity = LocalDate.parse(scanner.nextLine(), format);

        service.save(code, value, percentage, validity, false);
        System.out.println("Cupom criado com sucesso!");
    }

    private void findAll(){
        service.findAll();
    }

    private void expire(){
        System.out.print("ID do cupom a expirar: ");
        long id = scanner.nextLong();
        scanner.nextLine();
        service.expire(id);
    }
}
