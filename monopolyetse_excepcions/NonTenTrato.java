package monopolyetse_excepcions;
        
import monopolyetse_entidades.Xogador;

public final class NonTenTrato extends ExcTrato
{
    public NonTenTrato(Xogador xogador, int trato)
    {
        super("O xogador non ten o trato!" 
            + "\n\t\t" + xogador.getNomeXogador() + " non ten o trato "
            + trato);
    }
}
