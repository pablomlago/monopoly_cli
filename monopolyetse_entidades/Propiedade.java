package monopolyetse_entidades;

import monopolyetse_excepcions.CaerCasilla;
import monopolyetse_excepcions.ConPropietario;
import monopolyetse_excepcions.DineiroInsuficienteCompra;
import monopolyetse_excepcions.ExcCompra;
import monopolyetse_excepcions.ExcXogador;
import static monopolyetse_funcionalidades.Juego.consola;
import monopolyetse_funcionalidades.NoAlquiler;
import monopolyetse_excepcions.RestriccionAcciones;

/*Os xogadores non posuiran propiedades dun xeito abstracto
senón que as propiedades das que dispoñan pertencerán a un
tipo particular dentro da clase propiedade, proporcionando
esta unicamente os atributos e funcionalidades común a todas
estas, de modo que 
*/
public abstract class Propiedade extends Casilla
{
    private final Grupo grupo;
    
    private Xogador propietario; //Revisar protected
    
    private float prezo;
    private float prezoAlquiler;
    
    //ESTADISTICAS
    private float alquilerAcumulado;
    
    public Propiedade(Grupo grupo, String nome, int[] posCasilla)
    {
        super(nome, posCasilla);
        
        this.grupo = grupo;
        
        this.prezo = this.grupo.getPrezo();
        this.prezoAlquiler = this.grupo.getPrezoAlquiler();
        this.propietario = null;
        
        this.alquilerAcumulado = 0.0f;
    }
    
    public boolean pertenceAXogador(Xogador xogador)
    {
        return this.propietario.equals(xogador);
    }
    
    public Xogador getPropietario()
    {
        return this.propietario;
    }
    
    public float getAlquilerAcumulado()
    {
        return this.alquilerAcumulado;
    }
    
    public Grupo getGrupo()
    {
        return this.grupo;
    }
    public void setPropietario(Xogador xogador)
    {
        if(xogador != null)
        {
            this.propietario = xogador;
        }
    }
    
    public float valor()
    {
        return this.prezo;
    }
    
    public void setPrezo(float prezo)
    {
        if(prezo >= 0)
        {
            this.prezo = prezo;
        }
    }
    
    public float getPrezoAlquiler()
    {
        return this.prezoAlquiler;
    }
    
    @Override
    public String getCodigoColor()
    {
        return this.grupo.getCodigoColor();
    }
    
    public void setPrezoAlquiler(float prezoAlquiler)
    {
        if(prezoAlquiler >= 0)
        {
            this.prezoAlquiler = prezoAlquiler;
        }
    }
    
    public void sumarAlquilerAcumulado(float cantidade)
    {
        this.alquilerAcumulado += cantidade;
    }
    
    public float alquiler() {
        
        float acumulado = this.getPrezoAlquiler();
        
        return acumulado;
    }
    
    @Override
    public void cobroRealizado(float cantidade)
    {
        this.sumarAlquilerAcumulado(cantidade);
        this.getGrupo().sumarAlquilerAcumulado(cantidade);
    }
    
    @Override
    public String accionCasilla(Xogador xogador)
    {
        String accionCasilla = super.accionCasilla(xogador);
        
        if(this.propietario.getNomeXogador().equalsIgnoreCase("Banca"))
        {
            accionCasilla = "Comprar";
        }
        else
        {
            //Comprobamos que a casilla non esté hipotecada
            if(this.propietario.getPropiedades().containsKey(this.getNome()))
            {
                if(!xogador.equals(this.propietario))
                {
                    boolean cobrar = true;
                    for(NoAlquiler trato : xogador.getNoAlquileres())
                    {
                        if(trato.getCasillaNoAlquiler().equals(this))
                        {
                            cobrar = false;
                        }
                    }
                    if(cobrar)
                    {
                        accionCasilla = "Cobrar";
                    }
                }
            }
        }
        
        return accionCasilla;
    }
    
    public String resumenPropiedade()
    {
        String s = "{\n\ttipo:"+ this.getGrupo().getTipo() +                                                    
                                                         ",\n\tvalor:"+ this.valor()+"\n}";
        
        return s;
    }
    
    public void comprar(Xogador comprador) throws ExcCompra, ExcXogador, RestriccionAcciones
    {
        if(this.propietario instanceof Banca)
        {
            if(comprador.getAvatar().getCasilla().equals(this))
            {
                if(this.prezo-comprador.getFortuna()<=0)
                {
                    comprador.engadirPropiedade(this);
                    comprador.restarCantidade(this.propietario, this.prezo, Concepto.pagoInversion);
                    this.propietario = comprador;

                    if(this instanceof Solar)
                    {
                        ((Solar)this).foiComprada();
                    }

                    consola.imprimir("O xogador " + comprador.getNomeXogador() + " compra a casilla " + this.getNome() +" por " + this.prezo +".\n"
                            + "A súa fortuna actual é " + comprador.getFortuna());
                    
                    comprador.getCambios().engadirPropiedade(this);

                    //O xogador só pode comprar unha vez para o coche
                    /*if(xogador.getAccionsPermitidas() == Accions.TODAS)
                    {
                        //Se podia realizar todas as accións impedimos que compre
                        xogador.setAccionsPermitidas(Accions.SO_EDF);
                    }
                    else
                    {
                        //Se só podía comprar evitamos que o siga facendo
                        xogador.setAccionsPermitidas(Accions.NINGUNA);
                    }*/

                    comprador.setAccionsPermitidas(Accions.NINGUNA);
                    /*
                    if(xogador.getAvatar() instanceof Coche)
                    {
                        Coche coche = (Coche)xogador.getAvatar();
                        //Se está no movemento especial so poderemos facer unha compra
                        if(coche.getModoMovemento())
                        {
                            if(xogador.getAccionsPermitidas() == Accions.TODAS)
                            {
                                xogador.setAccionsPermitidas(Accions.SO_EDF);
                            }
                            else
                            {
                                //Se só podía comprar evitamos que o siga facendo
                                xogador.setAccionsPermitidas(Accions.NINGUNA);
                            }
                        }
                    }
                    */
                }
                else
                {
                    throw new DineiroInsuficienteCompra(comprador, this, this.prezo - comprador.getFortuna());
                }
            }
            else
            {
                throw new CaerCasilla(comprador, this);
            }
        }
        else
        {
            throw new ConPropietario(this.propietario, this);
        }
    }
}
