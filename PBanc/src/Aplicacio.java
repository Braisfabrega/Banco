
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Aplicacio {
    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);
        ArrayList<CUsuari> usuaris= new ArrayList<CUsuari>();
        usuaris.add(new CUsuari("Brais","Fabrega", "Fiallega", "b.f@gmial.com", "1234",new CTargeta(1234)));
        int op=0;
        while(op!=3){
            System.out.println("1. Crear compte\n2. Iniciar sessio\n3. Sortir\nQue vols fer?");
            op=sc.nextInt();
            sc.nextLine();
            switch(op){
                case 1:
                    System.out.print("Nom: ");
                    String nom= sc.nextLine();
                    System.out.print("Cognom 1: ");
                    String cognom1= sc.nextLine();
                    System.out.print("Cognom 2: ");
                    String cognom2= sc.nextLine();
                    System.out.print("Email: ");
                    String email= sc.nextLine();
                    System.out.print("Contrsenya: ");
                    String contra= sc.nextLine();
                    System.out.print("Pin: ");
                    int pin=sc.nextInt();
                    sc.nextLine();
                    usuaris.add(new CUsuari(nom,cognom1, cognom2, email, contra,new CTargeta(pin)));
                    System.out.println("Usuari creat corectament");
                    break;
                case 2:
                    System.out.print("Email: ");
                    email=sc.nextLine();
                    System.out.print("Contra: ");
                    contra=sc.nextLine();
                    int c=-1;
                    for (int i=0;i<usuaris.size();i++){
                        if(usuaris.get(i).getEmail().equals(email)){
                            c=i;
                        }
                    }
                    if (c==-1){
                        System.out.println("No s'ha trobat ninguna compte amb aquest correu");
                    }else{
                        if(usuaris.get(c).iniciarsesio(contra)){
                            while(op!=8){
                                System.out.println("Compte actual: "+usuaris.get(c).getNom()+" "+usuaris.get(c).getApellido1()+" "+usuaris.get(c).getApellido2());
                                System.out.println("Saldo: "+usuaris.get(c).getSaldo());
                                System.out.println("1. Depositar");
                                System.out.println("2. Retirar efectiu");
                                System.out.println("3. Transferencia bancaria"); // en aquest progrma només s'acceptaran casos on es facin transferencies entre usuaris dins d'aquest programa
                                System.out.println("4. Veure moviments del compte");
                                System.out.println("5. Veure informacio de la compte");
                                System.out.println("6. Cambiar contrasenya");
                                System.out.println("7. Cambiar pin");
                                System.out.println("8. Sortir");
                                System.out.println("Que vols fer: ");
                                op=sc.nextInt();
                                sc.nextLine();
                                switch (op) {
                                    case 1:
                                        System.out.print("Introdueix el pin de la targeta: ");
                                        pin=sc.nextInt();
                                        sc.nextLine();
                                        System.out.print("Introdueix la quantitat a depositar: ");
                                        int quantitat=sc.nextInt();
                                        sc.nextLine();
                                        System.out.print("Introdueix el concepte: ");
                                        String concepte=sc.nextLine();
                                        usuaris.get(c).depositar(pin, quantitat, concepte);
                                        break;
                                    case 2:
                                        System.out.print("Introdueix el pin de la targeta: ");
                                        pin=sc.nextInt();
                                        sc.nextLine();
                                        System.out.print("Introdueix la quantitat a retirar: ");
                                        quantitat=sc.nextInt();
                                        sc.nextLine();
                                        System.out.print("Introdueix el concepte: ");
                                        concepte=sc.nextLine();
                                        usuaris.get(c).depositar(pin, quantitat, concepte);
                                        break;
                                    case 3:
                                        System.out.print("Introdueix el pin de la targeta: ");
                                        pin=sc.nextInt();
                                        sc.nextLine();
                                        System.out.print("Introdueix la quantitat de diners: ");
                                        quantitat=sc.nextInt();
                                        sc.nextLine();
                                        System.out.print("Introdueix el IBAN del destinatari: ");
                                        String iban=sc.nextLine();
                                        System.out.print("Introdueix el concepte: ");
                                        concepte=sc.nextLine();
                                        usuaris.get(c).transferencia(pin, quantitat, iban, usuaris, concepte);
                                        break;
                                    case 4:
                                        HashMap<Integer,String> moviments= usuaris.get(c).getTargeta().getMovimientos();
                                        for (Map.Entry<Integer, String> entry : moviments.entrySet()) {
                                            System.out.println("Numero de transferencia: " + entry.getKey() + " -> Especificacions: " + entry.getValue());
                                        }
                                        break;
                                    case 5:
                                        System.out.println(usuaris.get(c).toString());
                                        break;
                                    case 6:
                                        System.out.print("Instrodueix la contrasenya actual: ");
                                        contra= sc.nextLine();
                                        System.out.print("Instrodueix la contrasenya nova: ");
                                        String contra2= sc.nextLine();
                                        usuaris.get(c).cambiarcontra(contra, contra2);
                                        break;
                                    case 7:
                                        System.out.print("Instrodueix el pin actual: ");
                                        pin= sc.nextInt();
                                        System.out.print("Instrodueix el pin nou: ");
                                        int pin2= sc.nextInt();
                                        usuaris.get(c).getTargeta().cambiarpin(pin, pin2);
                                        break;
                                    case 8:
                                        break;
                                    default:
                                        System.out.println("Opcio no valida");
                                        break;
                                }
                            }
                        }
                    }
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Opcio no valida");
                    break;
            }
        }
    }
    
}
