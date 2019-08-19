package monopolyetse_funcionalidades;

public class Principal 
{
    public static void main(String[] args) 
    {
        //Menu menu = new Menu();
        Juego juego = new Juego();
        juego.crearXogadores();
        juego.iniciarXogo();
    }
}
