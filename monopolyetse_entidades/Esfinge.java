package monopolyetse_entidades;

import java.util.HashMap;
import static monopolyetse_funcionalidades.Juego.consola;

/*Planteado como está o xogo, os avatares dos xogadores teñen
un tipo diferenciado e non se plantea que podan existir diferentes
subtipos para cada un deles, de modo que as clases dos tipos
diferenciados de avatar poden ser finais
*/
public final class Esfinge extends Avatar{
    
    private int turnosSeguidos;
    
    public Esfinge(Xogador xogador, Casilla casilla, HashMap<String, Avatar> avatares)
    {
        super(xogador, casilla, "Esfinge", avatares);
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
        int[] novaPos = new int[2];
        
        int[] pos = this.getCasilla().getPosCasilla();
        
        novaPos[0] = pos[0];
        novaPos[1] = pos[1];
        
        if(posicions > 4)
        {
            if(pos[1] == 1 || pos[1] == 3)
            {
                novaPos[1] += (novaPos[1] + 1)% numLados;
                novaPos[0] = 1;
                posicions--;
            }
            
            int posicionsAtaLado = numCasillas - novaPos[0];

            while(posicionsAtaLado <= posicions)
            {
                if(posicionsAtaLado % 2 != 0)
                {
                    novaPos[1] = (novaPos[1] + 2)%numLados;
                }
                novaPos[0] = 0;
                
                if(novaPos[1] == 0)
                {
                    this.completarVolta();
                }

                posicions -= posicionsAtaLado;
                posicionsAtaLado = numCasillas;
            }

            if(posicions > 0)
            {
                if(posicions % 2 != 0)
                {
                    novaPos[1] = (novaPos[1] + 2)%numLados;
                    novaPos[0] += posicions;
                    novaPos[0] = numCasillas - novaPos[0];
                }
                else
                {
                    novaPos[0] += posicions;
                }
            }

            if(this.turnosSeguidos > 1)
            {
                consola.imprimir("Levas dous turnos seguidos!");

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
            consola.imprimir("Obtiveches menos dun 4! Revirtense os cambios dos lanzamentos anteriores!");
            
            if(this.turnosSeguidos >= 1)
            {
                this.getXogador().getCambios().revertirCambios(this.getXogador());
            }
            
            this.turnosSeguidos = 0;
            this.setTurnosDisponibles(0);
            
            novaPos = moverEnBasico(posicions, false, numCasillas, numLados);
        }
        
        return novaPos;
    }
}
