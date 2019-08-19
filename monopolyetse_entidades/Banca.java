package monopolyetse_entidades;

/*A clase Banca, desempeña, como Xogador, un papel
moi particular dentro do xogo, de modo que non se contempla
en principio distinguir diferentes subclases dentro desta clase
xa que no xogo só debe aparecer unha instancia desta clase
*/
public final class Banca extends Xogador
{
    private Parking parking;
    
    public Banca()
    {
        super();
        
        this.parking = null;
    }
    
    public Parking getParking()
    {
        return this.parking;
    }
    
    public void setParking(Parking parking)
    {
        this.parking = parking;
    }
    
    @Override
    public void sumarCantidade(Xogador emisor, float cantidade, int concepto)
    {
        //super.sumarCantidade(cantidade, concepto);
        
        this.parking.sumarBote(cantidade);
    }
    
    @Override
    public float restarCantidade(Xogador receptor, float cantidade, int concepto)
    {
        if(concepto == Concepto.cobroPagoPremioImposto)
        {
            this.parking.restarBote(cantidade);
        }
        
        return 0.0f;
    }
    
    public void anularBote()
    {
        this.parking.anularBote();
    }
}
