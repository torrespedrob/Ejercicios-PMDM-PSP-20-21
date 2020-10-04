// Pedro Torres Barba | Teatro del Mojo
package teatrodelmojo;

import java.util.Scanner;
import java.util.ArrayList;

public class TeatroDelMojo {

  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);

    // Creamos el ArrayList que corresponde a los visitantes
    ArrayList<Visitante> totalVisitantes = new ArrayList<Visitante>();
    // Butaca almacenara la posicion de los visitantes
    Visitante[][] butaca = new Visitante[10][9];
    System.out.println("Bienvenido al Teatro del Mojo ☕");
    Obra obra = creaObra();

    // Generador de visitantes
    System.out.print("¿Cuantos visitantes quieres crear?: ");
    int numVisitantes = Integer.parseInt(s.nextLine());
    System.out.println("\n");
    for (int i = 0; i < numVisitantes; i++) {
      totalVisitantes.add(Visitante.aleatorizar(i));
    }
    ubicaVisitantes(butaca, totalVisitantes, obra);
    muestraAsientos(butaca);
    System.out.println("\n");

    // Operaciones principales del programa
    String select = "";
    do {
      System.out.println("Selecciona una butaca si quieres saber sus datos.");
      System.out.println("Escribe obra para mostrar la informacion de la obra.");
      System.out.println("Escribe grafica para ver los asientos.");
      System.out.println("Escribe salir para cerrar el programa.");
      System.out.print("\nTu comando: ");

      select = s.nextLine();
      System.out.print("\n");
      if (select.equalsIgnoreCase("obra")) {
        System.out.println(obra + "\n");
      } else if (select.equalsIgnoreCase("grafica")) {
        muestraAsientos(butaca);
      } else if (select.equalsIgnoreCase("salir")); else {
        select = select.toUpperCase();
        int x = select.charAt(0) - 65;
        int y = Character.getNumericValue(select.charAt(1));
        System.out.println("\n" + butaca[y][x]);
      }
    } while (!select.equalsIgnoreCase("salir"));

  }

  // Esta funcion pide los datos de la obra y los recopila
  public static Obra creaObra() {
    Scanner s = new Scanner(System.in);
    // Variables que nos pide el ejercicio
    String titulo;
    int duracion;
    String compania;
    String director;
    float precioEntrada;
    int rating;

    // Formulario de entrada de datos para el usuario
    System.out.println("Vaya introduciendo los datos de la obra a crear");
    System.out.print("Titulo: ");
    titulo = s.nextLine();
    System.out.print("Duracion (en minutos): ");
    duracion = Integer.parseInt(s.nextLine());
    System.out.print("Compania: ");
    compania = s.nextLine();
    System.out.print("Director: ");
    director = s.nextLine();
    System.out.print("Precio de una entrada: ");
    precioEntrada = Float.parseFloat(s.nextLine());
    System.out.print("Rating (edad minima recomendada): ");
    rating = Integer.parseInt(s.nextLine());
    return new Obra(titulo, duracion, compania, director, precioEntrada, rating);
  }

  // Seccion dedicada a la creacion de una obra con los datos anteriores
  public static class Obra {

    private String titulo;
    private int duracion;
    private String compania;
    private String director;
    private float precioEntrada;
    private int rating;

    // Getter
    public float getPrecioEntrada() {
      return this.precioEntrada;
    }

    public int getRating() {
      return this.rating;
    }

    // Setter
    public Obra(String titulo, int duracion,
      String compania, String director,
      float precioEntrada, int rating) {
      this.titulo = titulo;
      this.duracion = duracion;
      this.compania = compania;
      this.director = director;
      this.precioEntrada = precioEntrada;
      this.rating = rating;
    }

    // Salida formateada que ve el usuario
    public String toString() {
      return "Titulo: " + titulo
        + "\nDuracion: " + duracion
        + "\nCompania: " + compania
        + "\nDirector: " + director
        + "\nPrecio de una entrada: " + precioEntrada
        + "\nRating (edad minima necesaria): " + rating;
    }

  }

  public static void ubicaVisitantes(Visitante[][] butaca, ArrayList<Visitante> totalVisitante, Obra obra) {
    int asientoOcupado = 0;
    for (int l = 0; l < totalVisitante.size() && asientoOcupado < 90;) {
      int x = (int) (Math.random() * 10);
      int y = (int) (Math.random() * 9);

      if (butaca[x][y] == null) {
        if (totalVisitante.get(l).butacaVisitante(x, y, obra)) {
          butaca[x][y] = totalVisitante.get(l);
          asientoOcupado++;
        }
        l++;
      }
    }

  }

  public static class Visitante {

    private String nombre;
    private String dni;
    private int edad;
    private float dinero;
    private String butaca = "No ocupado";

    public String getButaca() {
      return butaca;
    }

    public Visitante(String nombre, String dni, int edad, float dinero) {
      this.nombre = nombre;
      this.dni = dni;
      this.edad = edad;
      this.dinero = dinero;

    }

    @Override
    public String toString() {
      return "Nombre: " + this.nombre
        + "\nDNI: " + this.dni
        + "\nEdad: " + this.edad
        + "\nDinero: " + this.dinero
        + "\nAsiento: " + this.butaca;
    }

    public boolean butacaVisitante(int x, int y, Obra obra) {
      String numButaca = "";
      if (this.edad >= obra.getRating() && this.dinero >= obra.getPrecioEntrada()) {
        numButaca += ((char) (65 + y));
        numButaca += "" + x;
        this.butaca = numButaca;
        return true;
      } else {
        return false;
      }
    }

    public static Visitante aleatorizar(int i) {
      String nombre = "Visitante" + i;
      // El ejercicio no nos pide controlar los dni
      String dni = "" + i + ((char) (65 + (i / 10000000)));
      int longDni = dni.length();
      for (int y = 0; y < (9 - longDni); y++) {
        dni = "0" + dni;
      }
      int edad = (int) (Math.random() * 99);
      float dinero = (float) (Math.random() * 500);
      return new Visitante(nombre, dni, edad, dinero);

    }
  }

  public static void muestraAsientos(Visitante[][] butaca) {

    for (int x = 0; x < 9; x++) {
      for (int y = 0; y < 10; y++) {
        if (butaca[y][x] != null) {
          System.out.print("   " + butaca[y][x].getButaca());
        } else {
          System.out.print("   ??");
        }
      }
      System.out.println();
    }
  }

}
