import java.time.LocalDate;
import java.util.HashMap;
import java.util.Random;
public class CTargeta {
    Random r = new Random();
    
    private String nTargeta;
    private int CVV;
    private String fechaCaducidad;
    private int pin;
    private HashMap<Integer,String> movimientos;
    private int nTransferencia;
    
    
    public CTargeta(int pin){
        this.nTargeta=generarNumeroTargeta();
        this.CVV=r.nextInt(1000);
        LocalDate fechaFutura = LocalDate.now().plusYears(5);
        int mes = fechaFutura.getMonthValue();
        String año = Integer.toString(fechaFutura.getYear()).substring(2);
        this.fechaCaducidad = String.format("%02d/%s", mes, año);
        this.pin=pin;
        this.movimientos=new HashMap<Integer,String>();
    }
    
    public CTargeta(){
        this(1111);
    }
    
    
    private String generarNumeroTargeta(){
        String nBase=generarAleatroio(15);
        int nControl = calcularnControl(nBase);
        return nBase + nControl;
    }
    
    private String generarAleatroio(int n){
        StringBuilder numero = new StringBuilder();
        for (int i = 0; i < n; i++) {
            numero.append(r.nextInt(10));
        }
        return numero.toString();
    }
    
    private int calcularnControl(String n){
        int suma = 0;
        boolean duplicar = false;
        for (int i = n.length() - 1; i >= 0; i--) {
            int digito = Character.getNumericValue(n.charAt(i));
            if (duplicar) {
                digito *= 2;
                if (digito > 9) {
                    digito -= 9;
                }
            }
            suma += digito;
            duplicar = !duplicar;
        }
        return (10 - (suma % 10)) % 10;

    }
    public String getnTargeta() {
        return nTargeta;
    }


    public int getCVV() {
        return CVV;
    }


    public String getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(String fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public HashMap<Integer,String>  getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(HashMap<Integer,String> movimientos) {
        this.movimientos = movimientos;
    }

    public int getnTransferencia() {
        return nTransferencia;
    }

    public void setnTransferencia(int nTransferencia) {
        this.nTransferencia = nTransferencia;
    }
    
    public boolean cambiarpin(int pin, int pin2){
        if (this.pin==pin){
            this.pin=pin2;
            System.out.println("S'ha cambiart el pin");
            return true;
        }else{
            System.out.println("Pin incorrecte");
            return false;
        }
    }

    @Override
    public String toString() {
        return "Numero de targeta =" + nTargeta + ", CVV=" + CVV + ", fechaCaducidad=" + fechaCaducidad + ", pin=" + pin;
    }
    
    
}
