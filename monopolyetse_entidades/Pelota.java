package monopolyetse_entidades;

import java.util.HashMap;

/*Planteado como está o xogo, os avatares dos xogadores teñen
un tipo diferenciado e non se plantea que podan existir diferentes
subtipos para cada un deles, de modo que as clases dos tipos
diferenciados de avatar poden ser finais
*/
public final class Pelota extends Avatar
{
    private int posicionsRestantes;
    private boolean movementoFinalizado;
    
    public Pelota(Xogador xogador, Casilla casilla, HashMap<String, Avatar> avatares)
    {
        super(xogador, casilla, "Pelota", avatares);
        
        this.posicionsRestantes = 0;
        this.movementoFinalizado = true;
    }
    
    public int getPosicionsRestantes()
    {
        return this.posicionsRestantes;
    }
    
    public boolean getMovementoFinalizado()
    {
        return this.movementoFinalizado;
    }
    
    public void setPosicionsRestantes(int posicionsRestantes)
    {
        this.posicionsRestantes = posicionsRestantes;
    }
    
    public void setMovementoFinalizado(boolean movementoFinalizado)
    {
        this.movementoFinalizado = movementoFinalizado;
    }
    
    @Override
    public int[] moverEnAvanzado(int posicions, int numCasillas, int numLados)
    {
        int[] novaPos;
        
        if(posicions > 4)
        {
            novaPos = this.moverEnBasico(5, false, numCasillas, numLados);

            this.posicionsRestantes  = posicions - 5;
        }
        else
        {
            novaPos = this.moverEnBasico(-1, false, numCasillas, numLados);

            this.posicionsRestantes = -posicions + 1;
        }

        if(this.posicionsRestantes == 0)
        {
            this.getXogador().setAccionsPermitidas(0); //Revisar
        }
        else
        {
            this.movementoFinalizado = false;
            this.getXogador().setAccionsPermitidas(3); //Revisar
        }

        this.setTurnosDisponibles(0); //Revisar
        
        return novaPos;
    }
    
    public int[] moverEnAvanzado(int numCasillas, int numLados) //Completar movemento
    {
        int[] novaPos;
        int signo = Integer.signum(this.posicionsRestantes);
            
        if(Math.abs(this.posicionsRestantes) > 1)
        {
            this.posicionsRestantes -= 2*signo;
            novaPos = moverEnBasico(2*signo, false, numCasillas, numLados);

            if(this.posicionsRestantes == 0)
            {
                this.movementoFinalizado = true;
                this.getXogador().setAccionsPermitidas(0);
            }
        }
        else
        {
            novaPos = moverEnBasico(signo, false, numCasillas, numLados);

            this.posicionsRestantes = 0;
            this.movementoFinalizado = true;
        }
        
        return novaPos;
    }
}