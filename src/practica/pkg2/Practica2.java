package practica.pkg2;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * @author Luciano Xiquín
 * @author Oscar Hernández
 * @author Oscar Hernández
 * @author Fernando Mendoza
 */
public class Practica2 {

    static Scanner sc = new Scanner(System.in);
    // Constantes de color agregar al final de un System.out.println("hola" + \u001B[30m);
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    // Constante de color especial para volver a dejar todo con normalidad
    public static final String ANSI_RESET = "\u001B[0m";
    //Para usar los colores System.out.println(ANSI_RED + "Texto de color rojo" + ANSI_RESET);

//el arreglo de los usuarios
    static Usuario[] users = new Usuario[5];
    static Alumno[] alumni = new Alumno[100];
    static Profesor[] profs = new Profesor[20];
    static Curso[] cursos = new Curso[15];

    /*
*Por alguna razón el scanner llega a fallar, así que recomiendo, usar scaneres locales
*de otra forma podrían encontrarse con un error
*Mejor si usan un nuevo scanner para
*/

    public static void main(String[] args) {

        try {
            password();
        } catch (Exception e) {
            System.out.println("Usuario no valido");
        }
    }

    private static void password(){
        String user;
         Scanner sc1 = new Scanner(System.in);
        System.out.println("Usuario:");
        user = sc1.nextLine();
        System.out.println("Contraseña:");
        Console console = System.console();
        char[] password = console.readPassword();
        String pass="";
        for (int a=0;a<password.length;a++){
            pass += String.valueOf(password[a]);;
        }
        if (user.equals("admin") && pass.equals("admin")){
            Menu();
        }if(user.equals(users[0].getNombre())&&pass.equals(users[0].getCont())){
              menu_reporte();
        }if(user.equals(users[1].getNombre())&&pass.equals(users[1].getCont())){
              menu_reporte();
        }if(user.equals(users[2].getNombre())&&pass.equals(users[2].getCont())){
              menu_reporte();
        }if(user.equals(users[3].getNombre())&&pass.equals(users[3].getCont())){
              menu_reporte();
        }if(user.equals(users[4].getNombre())&&pass.equals(users[4].getCont())){
              menu_reporte();
        }else{
            System.out.println("El usuario o la contraseña estan mal escritos");
            System.out.println("Porfavor vulve a ingresarlos \n");
            password();
        }
    }

    private static void Menu() {
        Scanner sc20 = new Scanner(System.in);
        boolean menu = true;
        do {
            try {
                int opc;
                System.out.println(ANSI_YELLOW + " " +"Menu principal"
                        + "\n1)Cargar Alumnos"
                        + "\n2)Cargar Profesores"
                        + "\n3)Cargar Cursos"
                        + "\n4)Asignar alumnos"
                        + "\n5)Asignar profesores"
                        + "\n6)Cargar Notas"
                        + "\n7)Crear usuario"
                        + "\n8)Cerrar Sesión"+ " " +ANSI_RESET);
                switch (opc = sc.nextInt()) {
                    case 1:
                        try {
                        alumni = reader_alumni();
                    } catch (IOException e) {
                        System.out.println("Proceso de carga de alumnos fallida");
                    }
                    break;
                    case 2:
                        try {
                        profs = reader_profs();
                    } catch (IOException e) {
                        System.out.println("Proceso de carga de profesores fallida");
                    }
                    break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        int pos;
                        System.out.println("Seleccione el espacio de memoria donde el usuario será creado"
                                + "\nSolo existen 5 espacios para usuarios (Exceptuando el admin)");
                        pos = sc20.nextInt();
                        users[pos] = crear_usuario();
                        //System.out.println("Usuario: " + users[pos].getNombre() + "/" + users[pos].getCont());
                        break;
                    case 8:
                        password();
                        break;
                    default:
                        System.out.println("Seleccione una de las opciones");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Entrada invalida");
            }
        } while (menu == true);
    }

    private static Usuario crear_usuario() {
        Scanner sc1 = new Scanner(System.in);
        Scanner sc2 = new Scanner(System.in);
        Usuario aux = new Usuario();
        String nombre;
        System.out.print("Ingresé el nombre: ");
        nombre = sc1.nextLine();

        System.out.print("Ingresé la contraseña: ");
        Console console = System.console();
        char[] password = console.readPassword();
        String pass = "";
        for (int a = 0; a < password.length; a++) {
            pass += String.valueOf(password[a]);;
        }

        aux.setNombre(nombre);
        aux.setCont(pass);

        //System.out.println(aux.getNombre()+aux.getCont());
        return aux;
    }

    //Todabía es muy primitivo... cargar alumnos
    private static Alumno[] reader_alumni() throws IOException {

        //variables auxiliares
        int id, carnet;
        String nombre, cumple, genero;
        //la dirección del archivo
        
        Alumno[] aux = null;

        try {
            System.out.println("Introduzca la ruta del archivo: ");
            File csv = new File(sc.nextLine());
            Scanner reader = new Scanner(System.in); //lector para mi archivo
            String linea = "";
            reader.nextLine();//ignorar la primera linea del csv

            while (reader.hasNextLine()) {
                linea += reader.nextLine() + "\n";
            }

            String filas[] = linea.split("\n"); //metemos las lineas de los archivos en un array
            int filasAlumno = filas.length; //Aquí para verificar cuantos pokemons estamos ingresando
            String columnas[];

            for (int i = 0; i < filasAlumno; i++) { //con este for pasamos los parámetros a al constructor de la clase Alumno
                columnas = filas[i].split(",");

                /*Aqui casteamos todos los valores strings a sus respectivos valores para poder realizar
                las respectivas operaciones*/
                id = Integer.parseInt(columnas[0]);
                carnet = Integer.parseInt(columnas[1]);
                nombre = columnas[2];
                cumple = columnas[3];
                genero = columnas[4];

                //Aquí ya instanciamos todos objetos de mi clase Alumno
                aux[i] = new Alumno(id, carnet, nombre, cumple, genero);

            }

        } catch (Exception e) {
            System.out.println("Error al leer el archivo");
            System.out.println(e.getMessage());
        }
        return aux;
    }

    //menu de reportes
    private static void menu_reporte() {
        boolean menu = true;

        Scanner sc1 = new Scanner(System.in);
        int opc;
        while (menu = true) {
            System.out.println(ANSI_RED + "Menu de reportes"
                    + "\n1)Generar Reportes"
                    + "\n2)Cerrar Sesión" + ANSI_RESET);

            try {
                switch (opc = sc1.nextInt()) {
                    case 1:
                        sub_menureporte();
                break;
            case 2:
                password();
                break;
            default:
                System.out.println("Seleccione una opción");
                break;
        }
        }catch(Exception e){
            System.out.println("Entrada no valida");
        }
        
    }
    }

    private static void sub_menureporte(){
        Scanner sc1 = new Scanner(System.in);
        int opc=0;
        System.out.println(ANSI_BLUE+"Menu de reportes"
                + "\n1) Reporte de alumnos"
                + "\n2) Reporte de asignacion de alumnos"
                + "\n3) Reporte de asignacion de profesores"
                + "\n4) Reporte general de cursos"
                + "\n5) Reporte de un curso en especifico"
                + "\n6) Reporte de Top de 5 mejores alumnos de un curso \n");
        System.out.println("Ingresar un numero de la lista para seleccionar una opcion" +ANSI_RESET);

        opc = sc1.nextInt();
        try {
            switch (opc){
                case 1:
                    Reporte_de_alumnos();
                break;
                case 2:
                    Reporte_de_asignacion_de_alumnos();
                    break;
                case 3:
                    Reporte_de_asignacion_de_profesores();
                    break;
                case 4:
                    Reporte_general_de_cursos();
                    break;
                case 5:
                    Reporte_de_un_curso_en_especifico();
                    // Aqui se debera de realizar un reporte especial con seleccion del curso
                    break;
                case 6:
                    Reporte_de_Top_de_5_mejores_alumnos_de_un_curso();
                    // Aqui se debera de realizar un reporte especial con seleccion del curso para despuess seleccionar el top de 5 mas
                    break;
                default:
                    menu_reporte();
            }
        }catch (Exception e){
            System.out.println("Entrada no valida debes de ingresar un entero");
        }

    }

    public static String fecha_creacion_reporte(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String fecha;
        fecha = dtf.format(LocalDateTime.now());
        return fecha;
    }


    public static void Reporte_de_alumnos(){
//     modificar para los valores de ingreso de la tabla
        //matriz de prueba
        String [][] mat ={{"00001","Jose Daniel","10","Masculino"},{"00002","Mariela Alverez","20","Femenino"}};
        String fecha_reporte= "Fecha/Hora de Creacion del archivo: " + fecha_creacion_reporte();
        String tabla = "<html><HEAD>\n" +
                "<title>" +
                "Reporte de alumnos" +
                "</title>" +
                "</HEAD>"
                +"<body background='fondos_de_reportes\\fondo1.jpg'>"
                +"<center>"
                +"<h1>Reporte de alumnos</h1>"
                +"<table border='1' bgcolor='DeepSkyBlue'>"
                +"<tr>" +
                "<td><strong>Carne</strong></td>" +
                "<td><strong>Nombre</strong></td>" +
                "<td><strong>Edad</strong></td>" +
                "<td><strong>Genero</strong></td>" +
                "</tr>";
        for (int i = 0; i < mat.length; i++) {
            String row = "<tr>";
            for (int j = 0; j < mat[0].length; j++) {
                row += "<td>" + mat[i][j] + "</td>";
            }
            row += "</tr>";
            tabla += row;
        }
        tabla += "</table>" +
                "<br>" +
                "<strong>" +
                fecha_reporte +
                "</strong>" +
                "</br>" +
                "</center></body></html>";
        createFile("Reporte de alumnos.html",tabla);
        System.out.println(ANSI_YELLOW + "Se ha realizado el reporte de los alumnos por favor revisa \n"+ANSI_RESET);
        System.out.println(fecha_reporte);
    }

    public static void Reporte_de_asignacion_de_alumnos(){
//     modificar para los valores de ingreso de la tabla
        //matriz de prueba
        String [][] mat ={{"00001","Jose Daniel","10","Matematicas","01/02/2021"},{"00002","Mariela Alverez","20","Idioma Español","02/06/2021"}};
        String fecha_reporte= "Fecha/Hora de Creacion del archivo: " + fecha_creacion_reporte();
        String tabla = "<html><HEAD>\n" +
                "<title>" +
                "Reporte de alumnos" +
                "</title>" +
                "</HEAD>"
                +"<body background='fondos_de_reportes\\fondo1.jpg'>"
                +"<center>"
                +"<h1>Reporte de asignacion de alumnos</h1>"
                +"<table border='1' bgcolor='DeepSkyBlue'>"
                +"<tr>" +
                "<td><strong>Carne</strong></td>" +
                "<td><strong>Nombre del Alumno</strong></td>" +
                "<td><strong>Cadigo</strong></td>" +
                "<td><strong>Nombre del Curso</strong></td>" +
                "<td><strong>Fecha de asignacion al curso</strong></td>" +
                "</tr>";
        for (int i = 0; i < mat.length; i++) {
            String row = "<tr>";
            for (int j = 0; j < mat[0].length; j++) {
                row += "<td>" + mat[i][j] + "</td>";
            }
            row += "</tr>";
            tabla += row;
        }
        tabla += "</table>" +
                "<br>" +
                "<strong>" +
                fecha_reporte +
                "</strong>" +
                "</br>" +
                "</center></body></html>";
        createFile("Reporte de asignacion de alumnos.html",tabla);
        System.out.println(ANSI_YELLOW + "Se ha realizado el reporte de la Asignacionde los alumnos por favor revisa \n"+ANSI_RESET);
        System.out.println(fecha_reporte);
    }

    public static void Reporte_de_asignacion_de_profesores(){
//     modificar para los valores de ingreso de la tabla
        //matriz de prueba
        String [][] mat ={{"00011","Amilcar Montejo","1","Matematicas","01/02/2021"},{"00002","Mariela Alverez","2","Ciencias Naturales","02/06/2021"}};
        String fecha_reporte= "Fecha/Hora de Creacion del archivo: " + fecha_creacion_reporte();
        String tabla = "<html><HEAD>\n" +
                "<title>" +
                "Reporte de asignacion de profesores" +
                "</title>" +
                "</HEAD>"
                +"<body background='fondos_de_reportes\\fondo1.jpg'>"
                +"<center>"
                +"<h1>Reporte de asignacion de profesores</h1>"
                +"<table border='1' bgcolor='DeepSkyBlue'>"
                +"<tr>" +
                "<td><strong>Registro Personal del Profesor</strong></td>" +
                "<td><strong>Nombre el Profesor</strong></td>" +
                "<td><strong>Codigo curso</strong></td>" +
                "<td><strong>Nombre curso</strong></td>" +
                "<td><strong>Fecha de asignacion al curso</strong></td>" +
                "</tr>";
        for (int i = 0; i < mat.length; i++) {
            String row = "<tr>";
            for (int j = 0; j < mat[0].length; j++) {
                row += "<td>" + mat[i][j] + "</td>";
            }
            row += "</tr>";
            tabla += row;
        }
        tabla += "</table>" +
                "<br>" +
                "<strong>" +
                fecha_reporte +
                "</strong>" +
                "</br>" +
                "</center></body></html>";
        createFile("Reporte de asignacion de profesores.html",tabla);
        System.out.println(ANSI_YELLOW + "Se ha realizado el reporte de la Asignacion de los profesores por favor revisa \n"+ANSI_RESET);
        System.out.println(fecha_reporte);
    }

    public static void Reporte_general_de_cursos(){
//     modificar para los valores de ingreso de la tabla
        //matriz de prueba
        String [][] mat ={{"00001","Matematicas","10"},{"00002","Ciencias naturales","20"}};
        String fecha_reporte="Fecha/Hora de Creacion del archivo: "+ fecha_creacion_reporte();
            String tabla = "<html><HEAD>\n" +
                    "<title>" +
                    "Reporte general de cursos" +
                    "</title>" +
                    "</HEAD>"
                    +"<body background='fondos_de_reportes\\fondo1.jpg'>"
                    +"<center>"
                    +"<h1>Reporte general de cursos</h1>"
                    +"<table border='1' bgcolor='DeepSkyBlue'>"
                    +"<tr>" +
                    "<td><strong>Codigo<Strong></td>" +
                    "<td><strong>Nombre del curso</strong></td>" +
                    "<td><strong>Cantidad de alumnos</strong></td>" +
                    "</tr>";
            for (int i = 0; i < mat.length; i++) {
                String row = "<tr>";
                for (int j = 0; j < mat[0].length; j++) {
                    row += "<td>" + mat[i][j] + "</td>";
                }
                row += "</tr>";
                tabla += row;
            }
            tabla += "</table>" +
                    "<br>" +
                    "<strong>" +
                    fecha_reporte +
                    "</strong>" +
                    "</br>" +
                    "</center></body></html>";
            createFile("Reporte general.html",tabla);
        System.out.println(ANSI_YELLOW + "Se ha realizado el reporte General de los cursos por favor revisa \n"+ANSI_RESET);
        System.out.println(fecha_reporte);
    }

    public static void Reporte_de_un_curso_en_especifico(){
//     modificar para los valores de ingreso de la tabla
        //matriz de prueba
        String [][] mat ={{"00001","Jose Enrique","60","Reprovado"},
                {"00002","David Gabriel","61","Aprovado"},
                {"00003","Daniel Hernandez","60","Reprovado"}};
        String fecha_reporte="Fecha/Hora de Creacion del archivo: "+ fecha_creacion_reporte();
        String tabla = "<html><HEAD>\n" +
                "<title>" +
                "Reporte de un curso en especifico" +
                "</title>" +
                "</HEAD>"
                +"<body background='fondos_de_reportes\\fondo1.jpg'>"
                +"<center>"
                +"<h1>Reporte de un curso en especifico</h1>"
                +"<table border='1' bgcolor='DeepSkyBlue'>"
                +"<tr>" +
                "<td><strong>Carne<Strong></td>" +
                "<td><strong>Nombre</strong></td>" +
                "<td><strong>Nota</strong></td>" +
                "<td><strong>Resultado</strong></td>" +
                "</tr>";
        for (int i = 0; i < mat.length; i++) {
            String row = "<tr>";
            for (int j = 0; j < mat[0].length; j++) {
                row += "<td>" + mat[i][j] + "</td>";
            }
            row += "</tr>";
            tabla += row;
        }
        tabla += "</table>" +
                "<br>" +
                "<strong>" +
                fecha_reporte +
                "</strong>" +
                "</br>" +
                "</center></body></html>";
        createFile("Reporte de un curso en especifico.html",tabla);
        System.out.println(ANSI_YELLOW + "Se ha realizado el reporte especifico del curso seleccionado por favor revisa \n"+ANSI_RESET);
        System.out.println(fecha_reporte);
    }

    public static void Reporte_de_Top_de_5_mejores_alumnos_de_un_curso(){
//     modificar para los valores de ingreso de la tabla
        //matriz de prueba
        String [][] mat ={{"00001","Jose Enrique","60"},
                {"00002","David Gabriel","61"},
                {"00003","Daniel Hernandez","60"}};
        String fecha_reporte="Fecha/Hora de Creacion del archivo: "+ fecha_creacion_reporte();
        String tabla = "<html><HEAD>\n" +
                "<title>" +
                "Reporte de Top de 5 mejores alumnos de un curso" +
                "</title>" +
                "</HEAD>"
                +"<body background='fondos_de_reportes\\fondo1.jpg'>"
                +"<center>"
                +"<h1>Reporte de Top de 5 mejores alumnos de un curso</h1>"
                +"<table border='1' bgcolor='DeepSkyBlue'>"
                +"<tr>" +
                "<td><strong>Carne<Strong></td>" +
                "<td><strong>Nombre</strong></td>" +
                "<td><strong>Nota</strong></td>" +
                "</tr>";
        for (int i = 0; i < mat.length; i++) {
            String row = "<tr>";
            for (int j = 0; j < mat[0].length; j++) {
                row += "<td>" + mat[i][j] + "</td>";
            }
            row += "</tr>";
            tabla += row;
        }
        tabla += "</table>" +
                "<br>" +
                "<strong>" +
                fecha_reporte +
                "</strong>" +
                "</br>" +
                "</center></body></html>";
        createFile("Reporte de Top de 5 mejores alumnos de un curso.html",tabla);
        System.out.println(ANSI_YELLOW + "Se ha realizado el reporte del Top de los mejores 5 del curso seleccionado por favor revisa \n"+ANSI_RESET);
        System.out.println(fecha_reporte);
    }

    //crea el archivo en disco, recibe como parámetro la lista de estudiantes
    public static void createFile(String pathname,String html) {
        FileWriter flwriter = null;
        try {
            //crea el flujo para escribir en el archivo
            flwriter = new FileWriter(pathname);
            //crea un buffer o flujo intermedio antes de escribir directamente en el archivo
            BufferedWriter bfwriter = new BufferedWriter(flwriter);

        //Espacio para agregar codigo html por almacenado en una variable
                bfwriter.write(html);

            bfwriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (flwriter != null) {
                try {//cierra el flujo principal
                    flwriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static Profesor[] reader_profs() throws IOException {
        System.out.println("Introduzca la ruta del archivo");
        Profesor[] aux = null;
        String linea = "";
        //la dirección del archivo
        File nuevo = new File(sc.nextLine());
        try {
            FileReader archivo = new FileReader(nuevo);
            BufferedReader buffer = new BufferedReader(archivo);
            String temporal = "";
            while (linea != null) {
                linea = buffer.readLine();
                    //se almacena todo en un String que contendra los datos crudos
                    temporal += linea;
                
                //se almacena ahora todo eso un vector String que tendra cada fila separada
                String[] filas = temporal.split("\n");
                //el aux tendra tantos objetos como elementos del arreglo de filas
                aux = new Profesor[filas.length];

                for (int i = 0; i < filas.length; i++) {
                    /*
                    La posición "i" del arreglo se llenará con su respectivos
                    valores[], valores en posición "i"
                    */
                    String[] valores = filas[i].split(",");
                    aux[i].setId_est(Integer.valueOf(valores[0]));
                    aux[i].setCarnet(Integer.valueOf(valores[1]));
                    aux[i].setNombre(valores[2]);
                    aux[i].setCumple(valores[3]);
                    aux[i].setContrato(valores[4]);
                    aux[i].setGenero(valores[5]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo");
            System.out.println(e.getMessage());
        }
        return aux;
    }
    
    private static Curso[] reador_curso() throws IOException{
    System.out.println("Introduzca la ruta del archivo");
        Curso[] aux = null;
        String linea = "";
        //la dirección del archivo
        File nuevo = new File(sc.nextLine());
        try {
            FileReader archivo = new FileReader(nuevo);
            BufferedReader buffer = new BufferedReader(archivo);
            String temporal = "";
            while (linea != null) {
                linea = buffer.readLine();
                    //se almacena todo en un String que contendra los datos crudos
                    temporal += linea;
                
                //se almacena ahora todo eso un vector String que tendra cada fila separada
                String[] filas = temporal.split("\n");
                //el aux tendra tantos objetos como elementos del arreglo de filas
                aux = new Curso[filas.length];

                for (int i = 0; i < filas.length; i++) {
                    /*
                    La posición "i" del arreglo se llenará con su respectivos
                    valores[], valores en posición "i"
                    */
                    String[] valores = filas[i].split(",");
                    aux[i].setId_clase(Integer.valueOf(valores[0]));
                    aux[i].setCodigo(Integer.valueOf(valores[1]));
                    aux[i].setNombre(valores[2]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo");
            System.out.println(e.getMessage());
        }
        return aux;
    }
}
