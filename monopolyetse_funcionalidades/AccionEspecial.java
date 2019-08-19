package monopolyetse_funcionalidades;

import java.util.ArrayList;
import monopolyetse_entidades.Avatar;
import monopolyetse_entidades.Casa;
import monopolyetse_entidades.Casilla;
import monopolyetse_entidades.Concepto;
import monopolyetse_entidades.Edificio;
import monopolyetse_entidades.Hotel;
import monopolyetse_entidades.Piscina;
import monopolyetse_entidades.Pista;
import monopolyetse_entidades.Xogador;
import static monopolyetse_funcionalidades.Juego.consola;

public class AccionEspecial
{
    private final Accion accion;
    
    public AccionEspecial(Accion accion)
    {
        this.accion = accion;
    }
    
    public void realizarAccionSorte(int numCarta)
    {
        float cantidade = 0.0f;
        //Engadir prinln
        switch(numCarta)
        {
            case 1:
                consola.imprimir("Vai a Glorieta de Bilbao. Se pasas por salida cobra " + Valor.dineiroSalida);
                this.moverConCobrar("Glorieta de Bilbao");
                break;
            case 2:
                consola.imprimir("Decides facer un viaxe de placer. Avanza ata Puerta del Sol");
                this.moverSenCobrar("Puerta del Sol");
                break;
            case 3:
                cantidade = 2000;
                consola.imprimir("Vendes o teu billete de avión para Terrassa nunha subasta por Internet. Cobra " + cantidade);
                this.cobrarCantidade(cantidade);
                break;
            case 4:
                consola.imprimir("Vai a Calle Velazquez. Se pasas por Salida, cobra " + Valor.dineiroSalida);
                this.moverConCobrar("Calle Velazquez");
                break;
            case 5:
                consola.imprimir("Os acreedores persígenche por impago. Vai á Caŕcel. Vai directamente"
                        + " sen pasar pola casilla de Salida e sen cobrar os " + Valor.dineiroSalida);
                this.accion.enviarCarcel();
                break;
            case 6:
                cantidade = 1000;
                consola.imprimir("Ganaches o bote da loteria! Recibe " + cantidade);
                this.cobrarCantidade(cantidade);
                break;
            case 7:
                cantidade = 1500;
                consola.imprimir("Paga "+ cantidade + " pola matricula do colexio privado");
                this.pagarCantidade(cantidade);
                break;
            case 8:
                consola.imprimir("O aumento do imposto sobre bens inmobles afecta a todas as tuas propiedades."
                        + " Paga 40 para casa, 115 por hotel, 200 por piscina e 750 por pista de deportes.");
                this.pagoImpostosEdificios();
                break;
            case 9:
                consola.imprimir("Vai a Paseo do Prado. Se pasas por salida cobra " + Valor.dineiroSalida);
                this.moverConCobrar("Paseo del Prado");
                break;
            case 10:
                cantidade = 250;
                consola.imprimir("Fostes elixido presidente da xunta directiva. Para a cada xogador " + cantidade);
                this.pagarXogadores(cantidade);
                break;
            case 11:
                consola.imprimir("Hora punta de trafico! Retrocede tres casillas.");
                this.retrocederCasilla(3);
                break;
            case 12:
                cantidade = 150;
                consola.imprimir("Multanche por usar o móbil mentras conduces. Paga "+ cantidade);
                this.pagarCantidade(cantidade);
                break;
            case 13:
                cantidade = 1500;
                consola.imprimir("Beneficio pola venta das tuas accions. Recibe " + cantidade);
                this.cobrarCantidade(1500);
                break;
            case 14:
                consola.imprimir("Avanza ata a casilla de transporte máis cercana. Se non ten dono,"
                    + "podes comprarlla a banca. Se ten dono, paga o doble da operación indicada.");
                this.moverEstacion();
                break;
        }
    }
    
    public void realizarAccionComunidade(int numCarta)
    {
        float cantidade = 0.0f;
        switch(numCarta)
        {
            case 1:
                cantidade = 500;
                consola.imprimir("Paga " + cantidade + " por un fin de semana nun balneario de 5 estrelas.");
                this.pagarCantidade(cantidade);
                break;
            case 2:
                consola.imprimir("Investíganche por fraude de identidade. Vai a Carcel. Vai directamente sen pasar pola"
                    + " casilla de Salida e sen cobrar os " + Valor.dineiroSalida);
                this.accion.enviarCarcel();
                break;
            case 3:
                consola.imprimir("Colocate na casilla de Salida. Cobra " + Valor.dineiroSalida);
                this.moverConCobrar("Salida");
                break;
            case 4:
                cantidade = 2000;
                consola.imprimir("A tua compañia de Internet obten beneficios. Recibe " + cantidade);
                this.cobrarCantidade(cantidade);
                break;
            case 5:
                cantidade = 1000;
                consola.imprimir("Paga " + cantidade + " por invitar a todos os teus amigos a un viaxe a León");
                this.pagarCantidade(cantidade);
                break;
            case 6:
                cantidade = 5000;
                consola.imprimir("Devolucion de Facenda. Cobra "+ cantidade);
                this.cobrarCantidade(cantidade);
                break;
            case 7:
                consola.imprimir("Retrocede ata Ronda de Valencia para comprar antiguidades exoticas");
                this.moverSenCobrar("Ronda de Valencia");
                break;
            case 8:
                cantidade  = 200;
                consola.imprimir("Alquilas aos teus compañeiros unha vila en Cannes durante unha semana."
                    + " Paga " + cantidade + " a cada xogador");
                this.pagarXogadores(cantidade);
                break;
            case 9:
                cantidade = 1000;
                consola.imprimir("Recibe " + cantidade + " de beneficios por alquilar os servizos do teu jet privado.");
                this.cobrarCantidade(cantidade);
                break;
            case 10:
                consola.imprimir("Vai a Plaza de Espana. Se pasas pola casilla de Salida, cobra " + Valor.dineiroSalida);
                this.moverConCobrar("Plaza de Espana");
                break;
        }
    }
    
    public void cobrarCantidade(float cantidade)
    {
        Xogador xogadorTurno = this.accion.getTurno().getXogadorTurno();
        xogadorTurno.sumarCantidade(xogadorTurno.getBanca(), cantidade, Concepto.cobroPagoPremioImposto);
    }
    
    public void moverConCobrar(String nomeCasilla)
    {
        Avatar avatarXogador = this.accion.getTurno().getXogadorTurno().getAvatar();
        Casilla casilla = this.accion.getTableiro().getCasilla(nomeCasilla);
        
        this.comprobarCompletada(avatarXogador, avatarXogador.getCasilla(), casilla);
        
        this.accion.getTableiro().situarAvatarCasilla(avatarXogador, casilla);
        this.accion.realizarAccion(casilla);
    }
    
    public void moverSenCobrar(String nomeCasilla)
    {
        Avatar avatarXogador = this.accion.getTurno().getXogadorTurno().getAvatar();
        Casilla casilla = this.accion.getTableiro().getCasilla(nomeCasilla);
        
        this.accion.getTableiro().situarAvatarCasilla(avatarXogador, casilla);
        this.accion.realizarAccion(casilla);
    }
    
    public void comprobarCompletada(Avatar avatar, Casilla inicio, Casilla fin)
    {
        int[] posInicial = inicio.getPosCasilla();
        int[] posFinal = fin.getPosCasilla();
        
        int distIniSaida = distanciaSaida(posInicial);
        int distFinSaida = distanciaSaida(posFinal); 
        
        if(distIniSaida <= distFinSaida)
        {
            avatar.completarVolta();
        }
     }
    
    public int distanciaSaida(int[] pos)
    {
        return 40 - pos[1]*this.accion.getTableiro().getNumCasillas() - pos[0];
    }
    
    public void retrocederCasilla(int posicions)
    {
        this.accion.moverAvatar(-posicions, false);
    }
    
    public void pagarXogadores(float cantidade)
    {
        Xogador xogadorTurno = this.accion.getTurno().getXogadorTurno();
        Xogador banca = this.accion.getTableiro().getBanca();
        ArrayList<Xogador> xogadores = this.accion.getTurno().getXogadores();
        
        //Haberá que revisar isto
        for(Xogador xogador : xogadores)
        {
            if(!xogador.equals(xogadorTurno))
            {
                if(!this.accion.pagarXogador(xogador, cantidade, 3))
                {
                    break;
                }
            }
        }
    }
    
    public void pagarCantidade(float cantidade)
    {
        Xogador banca = this.accion.getTableiro().getBanca();
        this.accion.pagarXogador(banca, cantidade, 3);
    }
    
    public void pagoImpostosEdificios()
    {
        float cantidade = 0.0f;
        Xogador xogadorTurno = this.accion.getTurno().getXogadorTurno();
        Xogador banca = this.accion.getTableiro().getBanca();
        
        for(Edificio ed : xogadorTurno.getEdificios())
        {
            if(ed instanceof Casa)
            {
                cantidade += 40f;
            }
            else if(ed instanceof Hotel)
            {
                cantidade += 115f;
            }
            else if(ed instanceof Piscina)
            {
                cantidade += 200f;
            }
            else if(ed instanceof Pista)
            {
                cantidade += 750f;
            }
            cantidade += ed.getPrezo();
        }
        
        this.accion.pagarXogador(banca, cantidade, 3);
    }
    
    public void moverEstacion()
    {
        Avatar avatarXogador = this.accion.getTurno().getXogadorTurno().getAvatar();
        
        int[] posInicial = avatarXogador.getCasilla().getPosCasilla();
        int[] posFinal = new int[2];
        
        if(posInicial[0] >= 5)
        {
            posFinal[1] = (posInicial[1] + 1) % this.accion.getTableiro().getNumCasillas();
        }
        posFinal[0] = 5;
        
        
        //Revisar, faltaría engadir 
        String casilla = this.accion.getTableiro().getCasilla(posFinal).getNome();
        this.moverSenCobrar(casilla);
    }
    
}




