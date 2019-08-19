package monopolyetse_excepcions;

public final class FNumeroNegativo extends ExcAritmetica
{
    private String numeroIncorrecto;
    public FNumeroNegativo(float numero , boolean estricto)
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
