package monopolyetse_funcionalidades;

import java.util.Random;
import monopolyetse_entidades.Xogador;
import static monopolyetse_funcionalidades.Juego.consola;

public class Dado 
{
    //Resultado e dobles non poden ser modificados mediante getters senón
    //que a súa modificación realizase internamente unicamente por
    //medio da función lanzarDados()
    private int resultado;
    private boolean dobles;
    private final Random rn;
    
    public Dado()
    {
        this.resultado = 1;
        this.dobles = false;
        this.rn = new Random();
    }
    
    public Dado(long semilla)
    {
        this.resultado = 1;
        this.dobles = false;
        this.rn = new Random(semilla);
    }
    
    public boolean getDobles()
    {
        return this.dobles;
    }
    
    public int getResultado()
    {
        return this.resultado;
    }
    
    public void lanzarDados(Xogador xogador)
    {
        int temp = 0;
        
        this.dobles = false;
        
        temp = rn.nextInt(6) + 1;
        consola.imprimir("Dado 1: " + temp);
        this.resultado = temp;
        temp = rn.nextInt(6) + 1;
        consola.imprimir("Dado 2: "  + temp);
        
        if(temp == this.resultado)
        {
            this.dobles = true;
        }
        
        this.resultado += temp;
        
        xogador.getEstatistica().sumarLanzamentosDados();
    }
}