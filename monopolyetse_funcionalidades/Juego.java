package monopolyetse_funcionalidades;

import monopolyetse_excepcions.ExcCompra;
import monopolyetse_excepcions.AccionNonPermitida;
import monopolyetse_excepcions.INumeroNegativo;
import monopolyetse_excepcions.ExcHipoteca;
import monopolyetse_excepcions.RequisitosEdificios;
import monopolyetse_excepcions.FNumeroNegativo;
import monopolyetse_excepcions.ExcTrato;
import monopolyetse_excepcions.ExcXogador;
import monopolyetse_excepcions.RestriccionAcciones;
import monopolyetse_excepcions.ExcAritmetica;
import monopolyetse_excepcions.ExcDeshipotecar;
import monopolyetse_excepcions.TipoInadecuado;
import monopolyetse_excepcions.ExcEdificar;
import java.util.ArrayList;
import monopolyetse_entidades.Tableiro;
import monopolyetse_entidades.Xogador;
import monopolyetse_excepcions.ExcVender;
import monopolyetse_excepcions.NonExisteCasilla;

public class Juego implements IComandos {

    //Non precisaremos getters para estes atributos, xa que esta clase
    //constitúe unha interfaz para o usuario para interactuar co xogo
    public static ConsolaNormal consola = new ConsolaNormal();

    private final ArrayList<Xogador> xogadores;
    private final Tableiro tableiro;
    private final Accion accion;

    private boolean salir;

    public Juego() {
        this.tableiro = new Tableiro();
        this.accion = this.tableiro.getAccion();
        this.xogadores = this.accion.getTurno().getXogadores();

        this.salir = false;
    }
    //IMPLEMENTACION

    @Override
    public void crearXogadores() {
        boolean finalizar = false;

        while (!finalizar) {
            String orden = consola.leer("Crear xogadores: ");
            String[] partes = orden.split(" ");
            String comando = partes[0];

            switch (comando) {
                case "crear":
                    if (partes.length == 4) {
                        if (this.xogadores.size() < 6) {
                            this.crearXogador(partes[2], partes[3]);
                        } else {
                            consola.imprimir("Non se poden crear máis de 6 xogadores!");
                        }
                    } else {
                        consola.imprimir("Comando incorrecto!");
                        consola.imprimir("Sintaxis: crear jugador <nombreJugador> <tipoAvatar>");
                    }
                    break;
                case "finalizar":
                    if (partes.length == 1) {
                        if (this.xogadores.size() < 2) {
                            consola.imprimir("Débense introducir polo menos dous xogadores!");
                        } else {
                            consola.imprimir("Xogadores creados! iniciase o xogo!");
                            finalizar = true;
                        }
                    } else {
                        consola.imprimir("Comando incorrecto!");
                        consola.imprimir("Sintaxis: finalizar");
                    }
                    break;
                case "salir":
                    if (partes.length == 1) {
                        consola.imprimir("Gracias por xogar!");

                        this.salir();
                        finalizar = true;
                    } else {
                        consola.imprimir("Comando incorrecto!");
                        consola.imprimir("Sintaxis: salir");
                    }
                    break;

                default:
                    consola.imprimir("Non se introduciu un comando válido!");
                    break;
            }
        }
    }

    @Override
    public void crearXogador(String nomeXogador, String tipoAvatar) {
        this.accion.crearXogador(nomeXogador, tipoAvatar);
    }

    @Override
    public void mostrarXogadorTurno() {
        this.accion.mostrarXogadorTurno();
    }

    @Override
    public void listarXogadores() {
        this.accion.listarXogadores();
    }

    @Override
    public void listarAvatares() {
        this.accion.listarAvatares();
    }

    @Override
    public void listarEnVenta() {
        this.accion.listarEnVenta();
    }

    @Override
    public void listarEdificios() {
        this.accion.listarEdificios();
    }

    @Override
    public void listarEdificiosGrupo(String color) {
        this.accion.listarEdificiosGrupo(color);
    }

    @Override
    public void lanzarDado() {
        this.accion.lanzarDado();
    }

    @Override
    public void acabarTurno() {
        this.accion.acabarTurno();
    }

    @Override
    public void describirCasilla(String nomeCasilla) {
        try
        {
            this.accion.describirCasilla(nomeCasilla);
        }
        catch(NonExisteCasilla excepcion)
        {
            consola.imprimir(excepcion.getMessage());
        }
    }

    @Override
    public void describirXogador(String nomeXogador) {
        this.accion.describirXogador(nomeXogador);
    }

    @Override
    public void describirAvatar(String nomeAvatar) {
        this.accion.describirAvatar(nomeAvatar);
    }

    @Override
    public void mostrarTableiro() {
        this.accion.mostrarTableiro();
    }

    @Override
    public void comprarCasilla(String nomeCasilla) {
        try {
            this.accion.comprarCasilla(nomeCasilla);
        } catch (RestriccionAcciones excepcion) {
            consola.imprimir(excepcion.getMessage());
            if (excepcion instanceof AccionNonPermitida) {
                AccionNonPermitida exc = (AccionNonPermitida) excepcion;
                consola.imprimir("\t\tRequiriase " + exc.getAccionRequerida()
                        + " pero o xogador so pode " + exc.getAccionsPermitidas());
            }
        } catch (TipoInadecuado excepcion) {
            consola.imprimir(excepcion.getMessage());
            consola.imprimir("\tRequerido: " + excepcion.getTipoPrecisado()
                    + ", Tipo Casilla: " + excepcion.getTipoCasilla());
        } catch (ExcCompra | ExcXogador | NonExisteCasilla excepcion) {
            consola.imprimir(excepcion.getMessage());
        }
    }

    @Override
    public void cambiarModoMovemento() {
        this.accion.cambiarModoMovemento();
    }

    @Override
    public void hipotecarPropiedade(String nomeCasilla) {
        try {
            this.accion.hipotecarPropiedade(nomeCasilla);
        } catch (ExcHipoteca | ExcXogador excepcion) {
            consola.imprimir(excepcion.getMessage());
        }
    }

    @Override
    public void deshipotecarPropiedade(String nomeCasilla) {
        try {
            this.accion.deshipotecarPropiedade(nomeCasilla);
        } catch (ExcXogador | ExcDeshipotecar excepcion) {
            consola.imprimir(excepcion.getMessage());
        }
    }

    @Override
    public void mostrarEstatisticasGlobais() {
        this.accion.mostrarEstatisticasGlobais();
    }

    @Override
    public void mostrarEstatisticasXogador(String nomeXogador) {
        this.accion.mostrarEstatisticasXogador(nomeXogador);
    }

    @Override
    public void venderEdificio(String tipoEdificio, String numEdificio, String nomeCasilla) {
        try
        {
            this.accion.venderEdificio(tipoEdificio, Integer.valueOf(numEdificio), nomeCasilla);
        }
        catch(ExcXogador | ExcVender | NonExisteCasilla excepcion)
        {
            consola.imprimir(excepcion.getMessage());
        }
        catch(ExcAritmetica excepcion)
        {
            consola.imprimir(excepcion.getMessage());
            if (excepcion instanceof INumeroNegativo) {
                INumeroNegativo exc = (INumeroNegativo) excepcion;
                consola.imprimir("\t\t" + exc.getNumeroIncorrecto());
            }
        }
        catch(TipoInadecuado excepcion)
        {
            consola.imprimir(excepcion.getMessage());
            consola.imprimir("\tRequerido: " + excepcion.getTipoPrecisado()
                    + ", Tipo Casilla: " + excepcion.getTipoCasilla());
        }
    }

    @Override
    public void edificar(String tipoEdificio) {
        try {
            this.accion.edificar(tipoEdificio);
        } catch (TipoInadecuado excepcion) {
            consola.imprimir(excepcion.getMessage());
            consola.imprimir("\tRequerido: " + excepcion.getTipoPrecisado()
                    + ", Tipo Casilla: " + excepcion.getTipoCasilla());
        } catch (RestriccionAcciones excepcion) {
            consola.imprimir(excepcion.getMessage());
            if (excepcion instanceof AccionNonPermitida) {
                AccionNonPermitida exc = (AccionNonPermitida) excepcion;
                consola.imprimir("\t\tRequiriase " + exc.getAccionRequerida()
                        + " pero o xogador so pode " + exc.getAccionsPermitidas());
            }
        } catch (ExcHipoteca | ExcXogador excepcion) {
            consola.imprimir(excepcion.getMessage());
        } catch (ExcEdificar excepcion) {
            consola.imprimir(excepcion.getMessage());
            if (excepcion instanceof RequisitosEdificios) {
                RequisitosEdificios exc = (RequisitosEdificios) excepcion;
                consola.imprimir("\t\tNon se pode construir  " + exc.getTipoRequerido()
                        + " en " + exc.getSolar().getNome() + ". " + exc.getRequerimento());
            }
        }
    }

    @Override
    public void salirCarcel() {
        this.accion.salirCarcel();
    }

    @Override
    public void proseguirMovemento() {
        this.accion.proseguirMovemento();
    }

    @Override
    public void aceptarTrato(String tratoid) {
        try {
            this.accion.aceptarTrato(tratoid);
        } catch (ExcTrato | ExcXogador excepcion) {
            consola.imprimir(excepcion.getMessage());
        }
    }

    @Override
    public void eliminarTrato(String tratoid) {
        try {
            this.accion.eliminarTrato(tratoid);
        } catch (ExcTrato excepcion) {
            consola.imprimir(excepcion.getMessage());
        }
    }

    @Override
    public void listarTratosRecibidos() {
        try {
            this.accion.listarTratosRecibidos();
        } catch (ExcTrato excepcion) {
            consola.imprimir(excepcion.getMessage());
        }
    }

    @Override
    public void procesarTrato(String trato) {
        try {
            this.accion.procesarTrato(trato);
        } 
        catch (ExcXogador | ExcTrato | NonExisteCasilla excepcion) {
            consola.imprimir(excepcion.getMessage());
        }
        catch (TipoInadecuado excepcion) {
            consola.imprimir(excepcion.getMessage());
            consola.imprimir("\tRequerido: " + excepcion.getTipoPrecisado()
                    + ", Tipo Casilla: " + excepcion.getTipoCasilla());
        }
        catch (ExcAritmetica excepcion) {
            consola.imprimir(excepcion.getMessage());
            if (excepcion instanceof INumeroNegativo) {
                INumeroNegativo exc = (INumeroNegativo) excepcion;
                consola.imprimir("\t\t" + exc.getNumeroIncorrecto());
            }
            if (excepcion instanceof FNumeroNegativo) {
                FNumeroNegativo exc = (FNumeroNegativo) excepcion;
                consola.imprimir("\t\t" + exc.getNumeroIncorrecto());
            }
        }
    }

    @Override
    public void salir() {
        consola.imprimir("\nGracias por xogar.");
        this.salir = true;
    }

    public void iniciarXogo() {
        while (!this.salir) {
            
            if(this.xogadores.size() > 1)
            {
                String orden = consola.leer("Que accion desexas realizar?");
                String[] partes = orden.split(" ");
                String comando = partes[0];

                switch (comando) {
                    case "jugador":
                        if (partes.length == 1) {
                            this.mostrarXogadorTurno();
                        } else {
                            consola.imprimir("Comando incorrecto");
                            consola.imprimir("Sintaxis: jugador");
                        }
                        break;
                    case "listar":
                        if (partes.length == 2) {
                            if (partes[1].equalsIgnoreCase("jugadores")) {
                                this.listarXogadores();
                            } else if (partes[1].equalsIgnoreCase("avatares")) {
                                this.listarAvatares();
                            } else if (partes[1].equalsIgnoreCase("enventa")) {
                                this.listarEnVenta();
                            } else if (partes[1].equalsIgnoreCase("edificios")) {
                                this.listarEdificios();
                            } else {
                                consola.imprimir("Comando incorrecto");
                                consola.imprimir("listar {jugadores, avatares, enventa}");
                            }
                        } else if (partes.length == 3) {
                            if (partes[1].equalsIgnoreCase("edificios")) { //meter comprobacion para partes[2]
                                this.listarEdificiosGrupo(partes[2]);
                            }
                        } else {
                            consola.imprimir("Comando incorrecto");
                        }
                        break;
                    case "lanzar":
                        if (partes.length == 2 && partes[1].equalsIgnoreCase("dados")) {
                            this.lanzarDado();
                        } else {
                            consola.imprimir("Comando incorrecto");
                            consola.imprimir("Sintaxis: lanzar dados");
                        }
                        break;
                    case "acabar":
                        if (partes.length == 2 && partes[1].equalsIgnoreCase("turno")) {
                            this.acabarTurno();
                        } else {
                            consola.imprimir("Comando incorrecto");
                            consola.imprimir("Sintaxis: acabar turno");
                        }
                        break;
                    case "describir":
                        String nomeCasilla = "";
                        if (partes.length > 1) {
                            if (partes.length == 2 && partes[1].equalsIgnoreCase("DeLujo")) {
                                this.describirCasilla("Impuesto de lujo");
                            } else if (partes.length == 2 && partes[1].equalsIgnoreCase("SobreCapital")) {
                                this.describirCasilla("Impuesto sobre el capital");
                            } else if (partes.length == 2 && partes[1].equalsIgnoreCase("Parking")) {
                                this.describirCasilla("Parking gratuito");
                            } else if (partes.length == 3 && partes[1].equalsIgnoreCase("jugador")) {
                                this.describirXogador(partes[2]);
                            } else if (partes.length == 3 && partes[1].equalsIgnoreCase("avatar")) {
                                this.describirAvatar(partes[2]);
                            } else {
                                for (int i = 1; i < partes.length; i++) {
                                    if (i < partes.length - 1) {
                                        nomeCasilla += partes[i] + " ";
                                    } else {
                                        nomeCasilla += partes[i];
                                    }
                                }
                                this.describirCasilla(nomeCasilla);
                            }
                        } else {
                            consola.imprimir("Comando incorrecto");
                            consola.imprimir("Sintaxis (casillas): describir {DeLujo, SobreCapital, Parking, <nomeCasilla>}");
                            consola.imprimir("Sintaxis (jugadores): describir jugador <nombreJugador>");
                            consola.imprimir("Sintaxis (avatares): describir avatar <nombreAvatar>");
                        }
                        break;
                    case "ver":
                        if (partes.length == 2 && partes[1].equalsIgnoreCase("tablero")) {
                            this.mostrarTableiro();
                        } else {
                            consola.imprimir("Comando incorrecto");
                            consola.imprimir("Sintaxis: ver tablero");
                        }
                        break;
                    case "comprar":
                        String nomCasilla = "";
                        if (partes.length > 1) {
                            for (int i = 1; i < partes.length; i++) {
                                if (i < partes.length - 1) {
                                    nomCasilla += partes[i] + " ";
                                } else {
                                    nomCasilla += partes[i];
                                }
                            }
                            this.comprarCasilla(nomCasilla);
                        } else {
                            consola.imprimir("Comando incorrecto");
                            consola.imprimir("Sintaxis: comprar <nombreCasilla>");
                        }
                        break;
                    //Casos entrega 2
                    case "cambiar":
                        if (partes.length == 2 && partes[1].equalsIgnoreCase("modo")) {
                            this.accion.cambiarModoMovemento();
                        } else {
                            consola.imprimir("\nComando incorrecto.");
                            consola.imprimir("Sintaxis: cambiar modo");
                        }
                        break;
                    case "hipotecar":
                        String casilla = "";
                        for (int i = 1; i < partes.length; i++) {
                            if (i < partes.length - 1) {
                                casilla += partes[i] + " ";
                            } else {
                                casilla += partes[i];
                            }
                        }
                        this.hipotecarPropiedade(casilla);
                        break;
                    case "deshipotecar":
                        String scasilla = "";
                        for (int i = 1; i < partes.length; i++) {
                            if (i < partes.length - 1) {
                                scasilla += partes[i] + " ";
                            } else {
                                scasilla += partes[i];
                            }
                        }
                        this.deshipotecarPropiedade(scasilla);
                        break;
                    case "estadisticas":
                        switch (partes.length) {
                            case 1:
                                this.mostrarEstatisticasGlobais();
                                break;
                            case 2:
                                this.mostrarEstatisticasXogador(partes[1]);
                                break;
                            default:
                                consola.imprimir("Comando incorrecto");
                                consola.imprimir("Sintaxis: estadisticas | estadisticas <nomeXogador>");
                                break;
                        }
                        break;
                    case "vender":
                        if (partes.length > 3) {
                            String nome = "";
                            int i;
                            for (i = 2; i < partes.length - 1; i++) {
                                if (i < partes.length - 2) {
                                    nome += partes[i] + " ";
                                } else {
                                    nome += partes[i];
                                }
                            }
                            boolean error;
                            try {
                                Integer.parseInt(partes[i]);
                                error = false;
                            } catch (NumberFormatException excepcion) {
                                error = true;
                            }
                            if (error == true) {
                                consola.imprimir("Comando incorrecto\nSintaxe: vender <tipo> <solar> <num>");
                                break;
                            }

                            this.venderEdificio(partes[1], partes[i], nome);
                        }
                        else
                        {
                            consola.imprimir("Comando incorrecto\nSintaxe: vender <tipo> <solar> <num>");
                        }
                        break;
                    case "edificar":
                        if (partes.length == 2) {
                            this.edificar(partes[1]);
                        } else {
                            consola.imprimir("Comando incorrecto\nSintaxe: edificar <casa | hotel | piscina | pista>");
                        }
                        break;
                    case "salir":
                        if (partes.length == 2 && partes[1].equalsIgnoreCase("carcel")) {
                            this.salirCarcel();
                        } else if (partes.length == 1) {
                            this.salir(); 
                        } else {
                            consola.imprimir("\nComando incorrecto.");
                            consola.imprimir("Sintaxis: salir | salir carcel");
                        }
                        break;
                    case "continuar":
                        if (partes.length == 2 && partes[1].equalsIgnoreCase("movemento")) {
                            this.proseguirMovemento();
                        } else {
                            consola.imprimir("\nComando incorrecto.");
                            consola.imprimir("continuar movemento");
                        }
                        break;
                    //Temporal
                    case "mover":
                        this.accion.lanzarDadoHackeado(Integer.parseInt(partes[1]), Boolean.parseBoolean(partes[2]));
                        break;
                    //
                    case "trato":
                        if (partes.length > 1) {
                            this.procesarTrato(orden);
                        } else {
                            consola.imprimir("\nComando incorrecto.");
                            consola.imprimir("Sintaxis: trato ...");
                        }
                        break;

                    case "tratos": //hay que cambialo para que imprima todos os tratos
                        if (partes.length == 1) {
                            this.listarTratosRecibidos();
                        }
																								else {
																												consola.imprimir("\nComando incorrecto.");
																												consola.imprimir("\nSintaxis: tratos");
																								}
                        break;
                    case "emitidos": //funcion para comprobar a eliminaicon de tratos
                        consola.imprimir(this.accion.getTurno().getXogadorTurno().getTratosEmitidos().toString());
                        break;
                    case "aceptar":
                        if (partes.length == 2) {
                            this.aceptarTrato(partes[1]);
                        }
                        break;
                    case "eliminar":
                        if (partes.length == 2) {
                            this.eliminarTrato(partes[1]);
                        } else {
                            consola.imprimir("\nComando incorrecto.");
                            consola.imprimir("Sintaxis: eliminar trato<numTrato>");
                        }
                        break;

                    default:
                        consola.imprimir("\nComando incorrecto.");
                        break;
                }
            }
            else
            {
                this.mostrarGanador();
                this.salir();
            }
        }
    }
    
    public void mostrarGanador()
    {
        this.accion.mostrarGanador();
    }
}
