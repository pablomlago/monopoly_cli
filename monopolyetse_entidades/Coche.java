package monopolyetse_entidades;

import java.util.HashMap;
import static monopolyetse_funcionalidades.Juego.consola;

/*Planteado como está o xogo, os avatares dos xogadores teñen
un tipo diferenciado e non se plantea que podan existir diferentes
subtipos para cada un deles, de modo que as clases dos tipos
diferenciados de avatar poden ser finais
*/
public final class Coche extends Avatar
{
    private int turnosSeguidos;
    
    public Coche(Xogador xogador, Casilla casilla, HashMap<String, Avatar> avatares)
    {
        super(xogador, casilla, "Coche", avatares);
        
        this.turnosSeguidos = 0;
    }
    
    public int getTurnosSeguidos()
    {
        return this.turnosSeguidos;
    }
    
    public void setTurnosSeguidos(int turnosSeguidos)
    {
        this.turnosSeguidos = turnosSeguidos;
    }
    
    @Override
    public int[] moverEnAvanzado(int posicions, int numCasillas, int numLados)
    {
        int[] novaPos;
        
        if(posicions > 4)
        {
            novaPos = moverEnBasico(posicions, false, numCasillas, numLados);
            this.turnosSeguidos++;
            
            if(this.turnosSeguidos > 4)
            {
                consola.imprimir("Levas catro turnos seguidos!");
                
                this.turnosSeguidos = 0;
                this.setTurnosDisponibles(0);
            }
            else
            {
                this.turnosSeguidos++;
                this.setTurnosDisponibles(1);
                this.getXogador().setAccionsPermitidas(0); //Revisar
            }
        }
        else
        {
            consola.imprimir("Obtiveches menos dun 4!");
            novaPos = moverEnBasico(-posicions, false, numCasillas, numLados);
            
            this.turnosSeguidos = 0;
            this.setTurnosDisponibles(-3);
        }
            
        return novaPos;
    }
}
