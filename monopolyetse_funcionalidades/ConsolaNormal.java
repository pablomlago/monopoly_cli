package monopolyetse_funcionalidades;

import java.util.Scanner;
import static monopolyetse_funcionalidades.Juego.consola;

public class ConsolaNormal implements IConsola
{
    @Override
    public void imprimir(String mensaxe)
    {
        System.out.println(mensaxe);
    }
    
    @Override
    public String leer(String descripcion)
    {
        consola.imprimir(descripcion);
        System.out.print("$> ");
        Scanner scanner= new Scanner(System.in);
        String orden= scanner.nextLine();
        
        return orden;
    }
}
