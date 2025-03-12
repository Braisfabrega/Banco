
import java.util.ArrayList;
import java.util.Random;

public class CUsuari {
    private String IBAN;
    private String nom;
    private String apellido1;
    private String apellido2;
    private String email;
    private String contra;
    private int saldo;
    private CTargeta targeta;
    private static int nCuentas=0;
    private static int saldoInicial=0;
    
    public CUsuari(String nom, String apellido1, String apellido2, String email,String contra, CTargeta targeta){
        this.IBAN=generarIBAN();
        this.nom=nom;
        this.apellido1=apellido1;
        this.apellido2=apellido2;
        this.email=email;
        this.contra=contra;
        this.saldo=saldoInicial;
        this.targeta=targeta;
        nCuentas++;
    }
    
    public CUsuari(){
        this("","","","@","",new CTargeta());
    }
    
    public CUsuari(CUsuari obj){
        this(obj.nom, obj.apellido1, obj.apellido2, obj.email, obj.contra, obj.targeta);
    }
    
    
    private String generarIBAN(){
        Random r= new Random();
        StringBuilder IBAN = new StringBuilder();
        StringBuilder nCuenta = new StringBuilder();
        String pais="ES";
        for (int i=0;i<16;i++){
            nCuenta.append(r.nextInt(10));
        }
        String CVV=generarCVV(nCuenta.toString(),pais);
        IBAN.append(pais);
        IBAN.append(CVV);
        IBAN.append(nCuenta);
        return IBAN.toString();
    }
    private String generarCVV(String numeroCuenta, String pais){
        String ibanParcial = pais + "00" + numeroCuenta;
        String ibanConvertido = convertirLetrasANumeros(ibanParcial);
        int resultado = calcularModulo97(ibanConvertido);
        int control = 98 - resultado;
        String CVV = String.format("%02d", control);
        return CVV; 
    }
    
    private String convertirLetrasANumeros(String iban){
        StringBuilder ibanNumerico = new StringBuilder();
        for (char c : iban.toCharArray()) {
            if (Character.isLetter(c)) {
                int num = Character.toUpperCase(c) - 'A' + 10;
                ibanNumerico.append(num);
            } else {
                ibanNumerico.append(c);
            }
        }
        return ibanNumerico.toString();
    }
    
    private int calcularModulo97(String ibanConvertido){
        StringBuilder sb = new StringBuilder(ibanConvertido);
        StringBuilder result = new StringBuilder();
        while (sb.length() > 0) {
            String temp = sb.substring(0, Math.min(9, sb.length()));  
            sb.delete(0, temp.length()); 
            int numero = Integer.parseInt(temp);
            result.setLength(0); 
            result.append(numero % 97);

            if (sb.length() > 0) {
                sb.insert(0, result);
            }
        }

        return Integer.parseInt(result.toString());
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public CTargeta getTargeta() {
        return targeta;
    }

    public void setTargeta(CTargeta targeta) {
        this.targeta = targeta;
    }

    public static int getnCuentas() {
        return nCuentas;
    }

    public static void setnCuentas(int nCuentas) {
        CUsuari.nCuentas = nCuentas;
    }

    public static int getSaldoInicial() {
        return saldoInicial;
    }

    public static void setSaldoInicial(int saldoInicial) {
        CUsuari.saldoInicial = saldoInicial;
    }
    
    
    public boolean iniciarsesio(String contra){
        if (this.contra.equals(contra)){
            return true;
        }else{
            System.out.println("Contrasenya incorrecta");
            return false;
        }
    }
    
    public boolean depositar(int pin, int quantitatat, String concepte){
        if(this.targeta.getPin()==pin){
            this.saldo+=quantitatat;
            System.out.println("Diners afegits correctament");
            this.targeta.getMovimientos().put(this.targeta.getnTransferencia(),concepte+ "   +"+Integer.toString(quantitatat));
            this.targeta.setnTransferencia(this.targeta.getnTransferencia()+1);
            return true;
        }else{
            System.out.println("Pin incorrecte");
            return false;
        }
    }
    
    public boolean retirar(int pin, int quantitatat, String concepte){
        if(this.targeta.getPin()==pin){
            if (quantitatat<=this.saldo){
                this.saldo-=quantitatat;
                System.out.println("Diners retirats correctament");
                this.targeta.getMovimientos().put(this.targeta.getnTransferencia(),concepte+ "   -"+Integer.toString(quantitatat));
                this.targeta.setnTransferencia(this.targeta.getnTransferencia()+1);
                return true;
            }else{
                System.out.println("Saldo insuficient");
                return false;
            }
            
        }else{
            System.out.println("Pin incorrecte");
            return false;
        }
    }
    
    public boolean transferencia(int pin, int quantitat, String IBAN, ArrayList<CUsuari> usuaris, String concepte){
        int destinatari=-1;
        for(int i=0;i<usuaris.size();i++){
            if (usuaris.get(i).IBAN.equals(IBAN)){
                destinatari=i;
            }
        }
        if(destinatari==-1){
            System.out.println("Destinatari no trobat");
            return false;
        }else{
            if(this.retirar(pin, quantitat, concepte)){
                usuaris.get(destinatari).depositar(usuaris.get(destinatari).targeta.getPin(), quantitat, concepte);
                
                this.targeta.getMovimientos().put(this.targeta.getnTransferencia(),concepte+ "   +"+Integer.toString(quantitat));
                this.targeta.setnTransferencia(this.targeta.getnTransferencia()+1);
                
                this.targeta.getMovimientos().put(usuaris.get(destinatari).getTargeta().getnTransferencia(),concepte+ "   +"+Integer.toString(quantitat));
                usuaris.get(destinatari).getTargeta().setnTransferencia(usuaris.get(destinatari).getTargeta().getnTransferencia()+1);
                System.out.println("Transferencia ordenada correctament");
                return true;
            }else{
                return false;
            }
        }
    }
    
    public boolean cambiarcontra(String contra, String contra2){
        if (this.contra.equals(contra2)){
            this.contra=contra2;
            System.out.println("S'ha cambiart la contrasenya");
            return true;
        }else{
            System.out.println("Contrasenya incorrecte");
            return false;
        }
    }

    @Override
    public String toString() {
        return "IBAN=" + IBAN + "\nNom=" + nom + " " + apellido1 + " " + apellido2 + "\nemail=" + email + "\ncontra=" + contra + "\nsaldo=" + saldo + "\ntargeta=" + targeta.toString();
    }
    
    
    
    
    
    
}
