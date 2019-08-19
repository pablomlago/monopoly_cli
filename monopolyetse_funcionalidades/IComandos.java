package monopolyetse_funcionalidades;

public interface IComandos {
    public abstract void crearXogadores(); //Feito
    public abstract void crearXogador(String nomeXogador, String tipoAvatar); //Feito
    public abstract void mostrarXogadorTurno();
    public abstract void listarXogadores();
    public abstract void listarAvatares();
    public abstract void listarEdificios();
    public abstract void listarEnVenta();
    public abstract void listarEdificiosGrupo(String color);
    public abstract void lanzarDado();
    public abstract void acabarTurno();
    public abstract void describirCasilla(String nomeCasilla);
    public abstract void describirXogador(String nomeXogador);
    public abstract void describirAvatar(String nomeAvatar);
    public abstract void mostrarTableiro();
    public abstract void comprarCasilla(String nomeCasilla); //Cambialo de Xogador xogador, Casilla casilla
    public abstract void cambiarModoMovemento();
    public abstract void hipotecarPropiedade(String nomeCasilla);
    public abstract void deshipotecarPropiedade(String nomeCasilla);
    public abstract void mostrarEstatisticasGlobais();
    public abstract void mostrarEstatisticasXogador(String nomeXogador);
    public abstract void venderEdificio(String tipoEdificio, String numEdificios, String nomeCasilla); //Cambiar argumentos
    public abstract void edificar(String tipoEdificio); //Cambiar
    public abstract void salirCarcel();
    public abstract void proseguirMovemento();
    
    //TRATO
    public abstract void aceptarTrato(String tratoid);
    public abstract void eliminarTrato(String tratoid);
    public abstract void listarTratosRecibidos();
    public abstract void procesarTrato(String trato);
    
    public abstract void salir(); //Feito
}
