package monopolyetse_excepcions;

public final class INumeroNegativo extends ExcAritmetica
{
    private String numeroIncorrecto;
    
    public INumeroNegativo(int numero , boolean estricto)
    {
        super("O numero non e adecuado");
        
        if(numero < 0 && estricto)
        {
            numeroIncorrecto = numero + " e menor que 0";
        }
        else if(numero <= 0 && !estricto)
        {
            numeroIncorrecto = numero + " e menor ou igual que 0";
        }
    }
    
    public String getNumeroIncorrecto()
    {
        return numeroIncorrecto;
    }
}
