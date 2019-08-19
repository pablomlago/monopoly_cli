package monopolyetse_excepcions;

public class ExcCompra extends MonopolyExcepcion{
    
    public ExcCompra(String mensaxe)
    {
        super("\n\tCompra: " + mensaxe);
    }
}
