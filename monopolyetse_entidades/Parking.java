package monopolyetse_entidades;

/*Este e un tipo moi particular de casilla, cunha funcionalidade
claramente diferenciada polo que non será nunca necesario
distinguir subtipos dentro desta
*/
public final class Parking extends Especial
{
    private float bote;
    
    public Parking(String nome, int[] posCasilla)
    {
        super(nome, posCasilla);
        
        this.bote = 0.0f;
    }
    
    @Override
    public String accionCasilla(Xogador xogador)
    {
        super.accionCasilla(xogador);
        
        return "Parking";
    }
            
    
    //Temos que decidir a dond
    @Override
    public String toString()
    {
        String s = "{\n\t"
                                + "bote:"+ this.bote 
                                + "\n\tjugadores: [" 
                                + this.xogadoresEnCasilla()
                                + "]"
                       +"\n}";
        
        return s;
    }
    
    public void anularBote()
    {
        this.bote = 0.0f;
    }
    
    public float getBote()
    {
        return this.bote;
    }
    
    //public void transferirBote(Xogador xogador);
    
    public void sumarBote(float cantidade)
    {
        if(cantidade >= 0)
        {
            this.bote += cantidade;
        }
    }
    
    public void restarBote(float cantidade)
    {
        if(cantidade >= 0)
        {
            this.bote -= cantidade;
        }
        
        if(this.bote < 0)
        {
            this.bote = 0;
        }
    }
}
