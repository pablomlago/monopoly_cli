/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopolyetse_entidades;

import java.util.HashMap;
import static monopolyetse_funcionalidades.Juego.consola;
import monopolyetse_funcionalidades.Valor;

/*A clase Avatar será abstracta xa que todos os tipos de avatar
están perfectamente definidos, de maneira que non é necesario
instanciar esta clase, xa que só se crean instancias das súas
subclases. Así, esta clase aúna as propiedades e funcionalidades
comúns a todos os tipos de avatares.*/
public abstract class Avatar {
    
    private final Xogador xogador;
    private final String codigo;
    private final String tipo;
    private Casilla casilla;
    
    private int voltasParciais;
    
    private boolean estarCarcel;
    //Non empregaremos un setter para este atributo xa que 
    //externamente só deberíamos poder incrementalo,
    //de aí a función sumarTurnosCarcel()
    private int turnosCarcel;
    
    private boolean modoMovemento;
    
    private int turnosDisponibles;
    
    //ESTADISTICA
    private int voltasTotais;
    
    public Avatar(Xogador xogador, Casilla casilla, String tipo, HashMap<String, Avatar> avatares)
    {
        this.xogador=xogador;
        
        //Variables auxiliares para asegurar que obtemos un código non duplicado
        int numero;
        String tempCodigo;
        
        do
        {
            numero = (int) (Math.random() * 26) + 65;
            tempCodigo = Character.toString((char)numero);
        } while(avatares.containsKey(tempCodigo));
        
        codigo= tempCodigo;
        this.tipo=tipo;
        this.casilla=casilla;
        //Conseguir que os avatares sexan distintos
        
        this.estarCarcel = false;
        this.modoMovemento = false;
        
        this.turnosCarcel = 0;
        this.voltasParciais = 0;
        
        this.turnosDisponibles = 1;
        
        //ESTADISTICAS
        this.voltasTotais = 0;
    }
 
    public Xogador getXogador(){
        return xogador;
    }
    public String getCodigo(){
        return codigo;
    }
    public String getTipo(){
        return tipo;
    }
    public Casilla getCasilla(){
        return casilla;
    }
    
    public boolean getEstarCarcel()
    {
        return this.estarCarcel;
    }
    
    public int getTurnosCarcel()
    {
        return this.turnosCarcel;
    }
    
    public int getVoltasParciais()
    {
        return this.voltasParciais;
    }
    
    public int getVoltasTotais()
    {
        return this.voltasTotais;
    }
    
    public int getTurnosDisponibles()
    {
        return this.turnosDisponibles;
    }
    
    public void setEstarCarcel(boolean estarCarcel)
    {
        if(!estarCarcel)
        {
            this.turnosCarcel = 0;
        }
        else
        {
            this.xogador.getEstatistica().sumarVecesNaCarcel();
        }
        this.estarCarcel = estarCarcel;
    }
    
    public void setCasilla(Casilla casilla) {
        this.casilla=casilla;
    }
    
    public void setTurnosDisponibles(int turnosDisponibles)
    {
        this.turnosDisponibles =  turnosDisponibles;
    }
    
    public void sumarTurnosCarcel()
    {
        this.turnosCarcel++;
    }
    
    public void sumarVolta()
    {
        this.voltasTotais++;
        this.voltasParciais++;
    }
    
    public void anularVoltas()
    {
        this.voltasParciais = 0;
    }
    
    public int[] moverEnBasico(int posicions, boolean dobles, int numCasillas, int numLados) //Revisar modificador acceso
    {
        int[] pos = this.casilla.getPosCasilla();
        int[] novaPos = new int[2];
        int aux;
        int sign  = 0;
        
        novaPos[0] = (pos[0] + posicions) % numCasillas;
        
        //Java non sabe facer módulos
        if(novaPos[0] < 0)
        {
            novaPos[0] += numCasillas;
            sign = -1;
        }
        
        aux = (pos[1] + ((pos[0] + posicions) / numCasillas));
        
        if(sign == -1)
        {
            aux -= 1;
        }
        
        //Teremos que borralo posteriormente
        
        novaPos[1] = aux % numLados;
        
        aux = aux / numLados;
        
        if(aux > 0)
        {
            this.completarVolta();
        }
        
        if(novaPos[1] < 0)
        {
            novaPos[1] += numLados;
        }
        
        if(dobles)
        {
            consola.imprimir("Obtiveches dobles! Podes lanzar outra vez.");
            this.setTurnosDisponibles(1); //Poderiamos cambiar o valor directamente
        }
        else
        {
            this.setTurnosDisponibles(0);
        }
        
        return novaPos;
    }
    
    public abstract int[] moverEnAvanzado(int posicions, int numCasillas, int numLados);
    
    public void completarVolta()
    {
        consola.imprimir("Pasaches por Salida. Cobras " + Valor.dineiroSalida);
        
        this.xogador.sumarCantidade(this.xogador.getBanca(), Valor.dineiroSalida, Concepto.cobroSaida);
        
        this.sumarVolta();
    }
    
    public void cambiarModoMovemento()
    {
        this.modoMovemento = !this.modoMovemento;
    }
    public boolean getModoMovemento()
    {
        return this.modoMovemento;
    }
    
    @Override
    public String toString(){
        return "{"
                    +"\n\tid:" + codigo
                    +",\n\ttipo:"+tipo
                    +",\n\tcasilla:"+casilla.getNome()
                    +",\n\txogador:"+this.xogador.getNomeXogador()
                +"\n}";
    }
    
}