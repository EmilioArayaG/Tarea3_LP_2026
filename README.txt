Tarea 3 Lenguajes de Programacion

Nombre alumno: Emilio Alfonso Araya Guzman
Rol alumno: 202473561-6

Descripcion general:
Esta tarea es un RPG por turnos hecho en Java basado en el universo de Final Fantasy VII. El jugador controla a Cloud Strife y debe ir explorando distintas zonas (Sector 7, Gongaga y el Nucleo del Planeta), subir de nivel, juntar materias y al final pelear contra Sephiroth.

El archivo principal del programa es "Main.java", y el codigo esta organizado en tres paquetes:
 - componentes/  : tiene las clases de Combate, Materia, Mejora, Estadisticas, Elemento, etc.
 - entidades/    : Jugador (Cloud), Enemigo, EnemigoSalvaje, EnemigoSimulador y Sephiroth.
 - mapa/         : la clase abstracta Zona y las tres zonas del juego (Sector7, Gongaga, NucleoPlaneta).

Instrucciones de uso:
-Para que el programa funcione hay que tener todos los archivos .java de los paquetes "componentes", "entidades" y "mapa", junto al "Main.java" y al "makefile" en la misma carpeta. Es importante mantener la estructura de carpetas tal cual esta en la carpeta, sino el compilador no encuentra las clases.

-El programa se desarrollo y se probo con Java 11 (en vscode), pero deberia funcionar con cualquier version de Java 11 o superior. Se recomienda usar esa version para asegurar que todo corra bien.

-Instrucciones para compilar y ejecutar:

   Estando en la carpeta del proyecto, abrir la terminal y usar los comandos en el siguiente orden:
     1. make -> compila todo y deja los .class en una carpeta llamada "out"
     2. make run -> compila y ejecuta el juego
     (despues de probar el juego)
     3. (opcional) make clean   -> borra la carpeta "out" con los archivos compilados

-Una consideracion importante es que el programa se probo en WSL Ubuntu (entorno Linux), por lo que se recomienda ese entorno para evitar problemas.
-Otra consideracion es seguir las instrucciones para poder compilar de forma correcta.
-Asegurarse que se encuentra en la carpeta al momento de usar la terminal

Consideraciones importantes:
1- La clase Combate (componentes/Combate.java) es el motor del combate del juego. No esta en el diagrama de clases base pero se agrego para hacer mas facil el funcionamiento del programa. Contiene todos sus metodos internos para manejar los turnos, las acciones del jugador, los ataques enemigos y las recompensas al terminar el combate.

2- Se agregaron metodos extra en varias clases del diagrama para que el funcionamiento del juego:
   - Jugador: atacarFisico, lanzarMagia, curar, usarAtaqueLimite, recibirDanio, morir, subirNivel, recibirChatarra, cargarLimite. La clase anidada Arma tiene ademas costoMP, tieneElemento y contarMateriasElemento.
   - Sephiroth: resetContadorSuperNova.
   - EnemigoSimulador: checkDanoSeguro.
   - Sector7: accionZona, aplicarMejora.
   - Gongaga: encontrarMateria.
   - NucleoPlaneta: accionZona.
   - Estadisticas: recibirDMG.