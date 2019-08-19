package monopolyetse_entidades;

import java.util.ArrayList;
import java.util.HashMap;
import monopolyetse_funcionalidades.Accion;
import monopolyetse_funcionalidades.AccionEspecial;
import monopolyetse_funcionalidades.Dado;
import static monopolyetse_funcionalidades.Juego.consola;
import monopolyetse_funcionalidades.Valor;

/*O tableiro é unico dentro do xogo, de modo que non sera
necesario distinguir diferentes subtupos deste, tal como esta
planteado o xogo.
*/
public final class Tableiro 
{
    //Por ser final non precisaremos setter xa que unicamente
    //se asignarán no constructor da clase
    private final ArrayList<ArrayList<Casilla>> casillas;
    private final HashMap<String, Xogador> xogadores;
    private final HashMap<String, Avatar> avatares;
    
    private final Dado dado;
    
    private final Banca banca;
    
    private final Accion accion;
    
    public Tableiro()
    {
        this.xogadores = new HashMap<>();
        this.avatares = new HashMap<>();
        this.banca = new Banca();
        this.casillas = new ArrayList();
        
        ArrayList<Carta> barallaSorte = new ArrayList();
        ArrayList<Carta> barallaComunidade = new ArrayList();
        
        this.dado = new Dado();
        
        //Inicialización baralla
        this.accion = new Accion(this);
        AccionEspecial accionCartas = new AccionEspecial(accion);
        for(int i = 0; i < Valor.numeroCartasSorte; i++)
        {
            barallaSorte.add(new CartaSorte(i, accionCartas));
        }
        for(int i = 0; i < Valor.numeroCartasComunidade; i++)
        {
            barallaComunidade.add(new CartaComunidade(i, accionCartas));
        }
        
        ArrayList<Casilla> lado = new ArrayList<>();
        
        Grupo grupoTransporte = new Grupo("Transporte");
        Grupo grupoServicio=new Grupo("Servicio");
        GrupoSolar grupoMarron = new GrupoSolar("Marron");
        GrupoSolar grupoCian = new GrupoSolar("Cian");
        GrupoSolar grupoRosa = new GrupoSolar("Rosa");
        GrupoSolar grupoLaranxa = new GrupoSolar("Laranxa");
        GrupoSolar grupoVermello = new GrupoSolar("Vermello");
        GrupoSolar grupoAmarelo = new GrupoSolar("Amarelo");
        GrupoSolar grupoVerde = new GrupoSolar("Verde");
        GrupoSolar grupoAzul = new GrupoSolar("Azul");
        
        int[] posicionsCasilla = new int[2];
        posicionsCasilla[0] = 0;
        posicionsCasilla[1] = 0;
        
        Especial casilla = new Especial("Salida", posicionsCasilla);
        
        lado.add(casilla);
        
        posicionsCasilla[0]++;
        
        Solar solar = new Solar(grupoMarron, "Ronda de Valencia", posicionsCasilla);
        this.banca.engadirPropiedade(solar);
        grupoMarron.engadirPropiedade(solar);
        
        lado.add(solar);
        
        posicionsCasilla[0]++;
        
        Comunidade comunidade = new Comunidade("Caja de comunidad", posicionsCasilla, barallaComunidade);
        
        lado.add(comunidade);
        
        posicionsCasilla[0]++;
        
        solar = new Solar(grupoMarron, "Plaza lavapies", posicionsCasilla);
        this.banca.engadirPropiedade(solar);
        grupoMarron.engadirPropiedade(solar);
        
        lado.add(solar);
        
        posicionsCasilla[0]++;
        
        Imposto imposto = new Imposto("Impuesto sobre el capital", Valor.prezoImposto1, posicionsCasilla);
        
        lado.add(imposto);
        
        posicionsCasilla[0]++;
        
        Transporte transporte = new Transporte(grupoTransporte, "Estacion de Goya", posicionsCasilla);
        this.banca.engadirPropiedade(transporte);
        grupoTransporte.engadirPropiedade(transporte);
        
        lado.add(transporte);
        
        posicionsCasilla[0]++;
        
        solar = new Solar(grupoCian, "Glorieta cuatro caminos", posicionsCasilla);
        this.banca.engadirPropiedade(solar);
        grupoCian.engadirPropiedade(solar);
        
        lado.add(solar);
        
        posicionsCasilla[0]++;
        
        Sorte sorte = new Sorte("Suerte", posicionsCasilla, barallaSorte);
        
        lado.add(sorte);
        
        posicionsCasilla[0]++;
        
        solar = new Solar(grupoCian, "Avenida Reina Victoria", posicionsCasilla);
        this.banca.engadirPropiedade(solar);
        grupoCian.engadirPropiedade(solar);
        
        lado.add(solar);
        
        posicionsCasilla[0]++;
        
        solar = new Solar(grupoCian, "Calle Bravo Murillo", posicionsCasilla);
        this.banca.engadirPropiedade(solar);
        grupoCian.engadirPropiedade(solar);
        
        lado.add(solar);
        
        this.casillas.add(lado);
        
        //Lado oeste
        lado = new ArrayList<>();
        
        posicionsCasilla[0] = 0;
        posicionsCasilla[1]++;
        
        Carcel carcel = new Carcel("Carcel", posicionsCasilla);
        
        lado.add(carcel);
        
        posicionsCasilla[0]++;
        
        solar = new Solar(grupoRosa, "Glorieta de Bilbao", posicionsCasilla);
        this.banca.engadirPropiedade(solar);
        grupoRosa.engadirPropiedade(solar);
        
        lado.add(solar);
        
        posicionsCasilla[0]++;
        
        Servicio servicio = new Servicio(grupoServicio, "Compania de electricidad", dado, posicionsCasilla);
        this.banca.engadirPropiedade(servicio);
        grupoServicio.engadirPropiedade(servicio);
        
        lado.add(servicio);
        
        posicionsCasilla[0]++;
        
        solar = new Solar(grupoRosa, "Calle Alberto Aguilera", posicionsCasilla);
        this.banca.engadirPropiedade(solar);
        grupoRosa.engadirPropiedade(solar);
        
        lado.add(solar);
        
        posicionsCasilla[0]++;
        
        solar= new Solar(grupoRosa, "Calle Fuencarral", posicionsCasilla);
        this.banca.engadirPropiedade(solar);
        grupoRosa.engadirPropiedade(solar);
        
        lado.add(solar);
        
        posicionsCasilla[0]++;
        
        transporte = new Transporte(grupoTransporte, "Estacion de las delicias", posicionsCasilla);
        this.banca.engadirPropiedade(transporte);
        grupoTransporte.engadirPropiedade(transporte);
        
        lado.add(transporte);
        
        posicionsCasilla[0]++;
        
        solar = new Solar(grupoLaranxa, "Avenida Felipe II", posicionsCasilla);
        this.banca.engadirPropiedade(solar);
        grupoLaranxa.engadirPropiedade(solar);
        
        lado.add(solar);
        
        posicionsCasilla[0]++;
        
        comunidade = new Comunidade("Caja de comunidad", posicionsCasilla, barallaComunidade);
        
        lado.add(comunidade);
        
        posicionsCasilla[0]++;
        
        solar = new Solar(grupoLaranxa, "Calle Velazquez", posicionsCasilla);
        this.banca.engadirPropiedade(solar);
        grupoLaranxa.engadirPropiedade(solar);
        
        lado.add(solar);
        
        posicionsCasilla[0]++;
        
        solar = new Solar(grupoLaranxa, "Calle Serrano", posicionsCasilla);
        this.banca.engadirPropiedade(solar);
        grupoLaranxa.engadirPropiedade(solar);
        
        lado.add(solar);
        
        this.casillas.add(lado);
        
        lado = new ArrayList<>();
        
        posicionsCasilla[0] = 0;
        posicionsCasilla[1]++;
        
        Parking parking = new Parking("Parking gratuito", posicionsCasilla);
        this.banca.setParking(parking);
        
        lado.add(parking);
        
        posicionsCasilla[0]++;
        
        solar = new Solar(grupoVermello, "Avenida de America", posicionsCasilla);
        this.banca.engadirPropiedade(solar);
        grupoVermello.engadirPropiedade(solar);
        
        lado.add(solar);
        
        posicionsCasilla[0]++;
        
        sorte = new Sorte("Suerte", posicionsCasilla, barallaSorte);
        
        lado.add(sorte);
        
        posicionsCasilla[0]++;
        
        solar = new Solar(grupoVermello, "Calle Maria de Molina", posicionsCasilla);
        this.banca.engadirPropiedade(solar);
        grupoVermello.engadirPropiedade(solar);
        
        lado.add(solar);
        
        posicionsCasilla[0]++;
        
        solar = new Solar(grupoVermello, "Calle Cea Bermudez", posicionsCasilla);
        this.banca.engadirPropiedade(solar);
        grupoVermello.engadirPropiedade(solar);
        
        lado.add(solar);
        
        posicionsCasilla[0]++;
        
        transporte = new Transporte(grupoTransporte, "Estacion del mediodia", posicionsCasilla);
        this.banca.engadirPropiedade(transporte);
        grupoTransporte.engadirPropiedade(transporte);
        
        lado.add(transporte);
        
        posicionsCasilla[0]++;
        
        solar = new Solar(grupoAmarelo, "Avenida de los Reyes", posicionsCasilla);
        this.banca.engadirPropiedade(solar);
        grupoAmarelo.engadirPropiedade(solar);
        
        lado.add(solar);
        
        posicionsCasilla[0]++;
        
        solar = new Solar(grupoAmarelo, "Calle Bailen", posicionsCasilla);
        this.banca.engadirPropiedade(solar);
        grupoAmarelo.engadirPropiedade(solar);
        
        lado.add(solar);
        
        posicionsCasilla[0]++;
        
        servicio = new Servicio(grupoServicio, "Compania de aguas", dado, posicionsCasilla);
        this.banca.engadirPropiedade(servicio);
        grupoServicio.engadirPropiedade(servicio);
        
        lado.add(servicio);
        
        posicionsCasilla[0]++;
        
        solar = new Solar(grupoAmarelo, "Plaza de Espana", posicionsCasilla);
        this.banca.engadirPropiedade(solar);
        grupoAmarelo.engadirPropiedade(solar);
        
        lado.add(solar);
        
        this.casillas.add(lado);
        
        lado = new ArrayList<>();
        
        posicionsCasilla[0] = 0;
        posicionsCasilla[1]++;
        
        IrCarcel ircarcel = new IrCarcel("Ir a la carcel", posicionsCasilla);
        
        lado.add(ircarcel);
        
        posicionsCasilla[0]++;
        
        solar = new Solar(grupoVerde, "Puerta del sol", posicionsCasilla);
        this.banca.engadirPropiedade(solar);
        grupoVerde.engadirPropiedade(solar);
        
        lado.add(solar);
        
        posicionsCasilla[0]++;
        
        solar = new Solar(grupoVerde, "Calle Alcala", posicionsCasilla);
        this.banca.engadirPropiedade(solar);
        grupoVerde.engadirPropiedade(solar);
        
        lado.add(solar);
        
        posicionsCasilla[0]++;
        
        comunidade = new Comunidade("Caja de comunidad", posicionsCasilla, barallaComunidade);
        
        lado.add(comunidade);
        
        posicionsCasilla[0]++;
        
        solar = new Solar(grupoVerde, "Gran via", posicionsCasilla);
        this.banca.engadirPropiedade(solar);
        grupoVerde.engadirPropiedade(solar);
        
        lado.add(solar);
        
        posicionsCasilla[0]++;
        
        transporte = new Transporte(grupoTransporte, "Estacion del norte", posicionsCasilla);
        this.banca.engadirPropiedade(transporte);
        grupoTransporte.engadirPropiedade(transporte);
        
        lado.add(transporte);
        
        posicionsCasilla[0]++;
        
        sorte = new Sorte("Suerte", posicionsCasilla, barallaSorte);
        
        lado.add(sorte);
        
        posicionsCasilla[0]++;
        
        solar = new Solar(grupoAzul, "Paseo de la Castellana", posicionsCasilla);
        this.banca.engadirPropiedade(solar);
        grupoAzul.engadirPropiedade(solar);
        
        lado.add(solar);
        
        posicionsCasilla[0]++;
        
        imposto = new Imposto("Impuesto de lujo", Valor.prezoImposto2, posicionsCasilla);
        
        lado.add(imposto);
        
        posicionsCasilla[0]++;
        
        solar = new Solar(grupoAzul, "Paseo del Prado", posicionsCasilla);
        this.banca.engadirPropiedade(solar);
        grupoAzul.engadirPropiedade(solar);
        
        lado.add(solar);
        
        this.casillas.add(lado);
        
    }
    
    //Getters para os atributos ca clase, aínda que non 
    //usaremos ningún deles, xa que en xeral accederemos
    //a elementos concretos a través de funcións auxiliares
    //pertencentes a esta mesma clase, poden resultar de utilidade
    //en futuras versións
    public ArrayList<ArrayList<Casilla>> getCasillas()
    {
        return this.casillas;
    }
    
    public HashMap<String, Xogador> getXogadores()
    {
        return this.xogadores;
    }
    
    public HashMap<String, Avatar> getAvatares()
    {
        return this.avatares;
    }
    
    public Banca getBanca()
    {
        return this.banca;
    }
    
    public Dado getDado()
    {
        return this.dado;
    }
    
    public Accion getAccion()
    {
        return this.accion;
    }
    
    //Funcións para simplificar as operacións sobre o tableiro
    
    //Accessors a unha casilla particular
    public Casilla getCasilla(int[] pos)
    {
        Casilla casillaBuscada = null;
        
        if(pos[1] >= 0 && pos[1] < this.casillas.size() && pos[0] >= 0 
                && pos[0] < this.casillas.get(pos[1]).size())
        {
            casillaBuscada = casillas.get(pos[1]).get(pos[0]);
        }
        
        return casillaBuscada;
    }
    
    public Casilla getCasilla(String nomeCasilla)
    {
        Casilla casillaBuscada = null;
        
        for(ArrayList<Casilla> lado : casillas)
        {
            for(Casilla casilla : lado)
            {
                if(casilla.getNome().equalsIgnoreCase(nomeCasilla))
                {
                    casillaBuscada = casilla;
                }
            }
        } 
        
        return casillaBuscada;
    }
    
    //Accessors as dimensións do tableiro
    public int getNumLados()
    {
        return this.casillas.size();
    }
    
    //Só ten sentido se os lados teñen a mesma lonxitude
    //o que é valido para o tableiro do Monopoly
    public int getNumCasillas()
    {
        return this.casillas.get(0).size();
    }
    
    //Funcións asociadas aos xogadores
    public Xogador getXogador(String nomeXogador)
    {
        return this.xogadores.get(nomeXogador);
    }
    
    public void engadirXogador(Xogador xogador)
    {
        xogadores.put(xogador.getNomeXogador(), xogador);
        //Os avatares deberían ter distinto código
        avatares.put(xogador.getAvatar().getCodigo(), xogador.getAvatar());
    }
    
    public boolean existeXogador(String nomeXogador)
    {
        return this.xogadores.containsKey(nomeXogador);
    }
    
    public void eliminarXogador(Xogador xogador)
    {
        Casilla casilla = xogador.getAvatar().getCasilla();
        
        //Eliminación avatar
        casilla.eliminarAvatar(xogador.getAvatar());
        this.avatares.remove(xogador.getAvatar().getCodigo());
        
        this.xogadores.remove(xogador.getNomeXogador());
    }
    
    //Funcións asociadas aos avatares
    public void situarAvatarCasilla(Avatar avatar, Casilla casilla)
    {
        Casilla pos = avatar.getCasilla();
        //Eliminámolo da casilla na que se atopa
        pos.getAvatares().remove(avatar.getCodigo());
        
        casilla.introducirAvatar(avatar);
        avatar.setCasilla(casilla);
    }
    
    public Avatar getAvatar(String nomeAvatar)
    {
        Avatar avatarBuscado = this.avatares.get(nomeAvatar);
        
        return avatarBuscado;
    }
    
    //Funcións bote (accesibles a través de banca) 
    /*
   public void sumarBote(float cantidade)
    {
        float bote = 0;
        Casilla parking = this.getCasilla("Parking gratuito");
        
        bote = parking.getPrezoAlquiler();
        bote += cantidade;
        
        parking.setPrezoAlquiler(bote);
    }
    
    public float getBote()
    {
        Casilla parking = this.getCasilla("Parking gratuito");
        
        return parking.getPrezoAlquiler();
    }
    
    public void setBote(float cantidade)
    {
        Casilla parking = this.getCasilla("Parking gratuito");  
        parking.setPrezoAlquiler(cantidade);
    }*/
    
    //Funcións para a visualización de elementos do tableiro
    public void listarEnVenta()
    {
        for(ArrayList<Casilla> lado : casillas)
        {
            for(Casilla casilla : lado)
            {
                if(casilla instanceof Propiedade)
                {
                    Propiedade propiedade = (Propiedade)casilla;
                    if(propiedade.getPropietario().equals(banca))
                    {
                        consola.imprimir(propiedade.resumenPropiedade());
                    }
                    
                }
            }
        }
    }
    
     @Override
    public String toString()
    {
        String s = "";
        
        for(int i=0; i<10; i++){
            s+="|"+String.format("%-35s",  this.casillas.get(2).get(i).getCodigoColor() + this.casillas.get(2).get(i).getNome()+this.casillas.get(2).get(i).imprimirAvatares());
            
        }
        int j=0;
        for(int i=9; i>=0; i--){
            
            s+="|"+String.format("%-35s",  this.casillas.get(3).get(j).getCodigoColor() + this.casillas.get(3).get(j).getNome())+this.casillas.get(3).get(j).imprimirAvatares()+"|\n";
            s+="|"+String.format("%-35s",  this.casillas.get(1).get(i).getCodigoColor() + this.casillas.get(1).get(i).getNome()+this.casillas.get(1).get(i).imprimirAvatares());
            
             j++;
             if(i>0){
                s+="|"+String.format("%-278s", "");
        
             }
        }
        for(int i=9; i>=0; i--){
            
            s+="|"+String.format("%-35s",  this.casillas.get(0).get(i).getCodigoColor() + this.casillas.get(0).get(i).getNome()+this.casillas.get(0).get(i).imprimirAvatares());
        }
        s+="|";
        
        return s;
    }
}