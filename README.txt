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

-El programa se desarrollo y se probo con Java 11 (en vscode), pero deberia funcionar con cualquier version de Java 11 o superior. Se recomienda usar minimo esa version para asegurar que todo corra bien.

-Instrucciones para compilar y ejecutar:

   Estando en la carpeta del proyecto, abrir la terminal y usar los comandos en el siguiente orden:
     1. make         -> compila todo y deja los .class en una carpeta llamada "out"
     2. make run     -> compila y ejecuta el juego
     (despues de probar el juego)
     3. (opcional) make clean   -> borra la carpeta "out" con los archivos compilados

-Una consideracion importante es que el programa se probo en WSL Ubuntu (entorno Linux), por lo que se recomienda ese entorno para evitar problemas. Igual deberia funcionar en cualquier sistema operativo que tenga Java y make instalados.
-Otra consideracion es seguir las instrucciones para poder compilar de forma correcta.
-Asegurarse que se encuentra en la carpeta al momento de usar la terminal
Resultados y terminal:
-El juego corre completamente en la terminal de forma interactiva. Al ejecutarlo aparece el menu principal donde se puede elegir a que zona viajar, gestionar la mochila o equipar materias. Cada combate muestra el turno por turno con el daño, el estado de la barra de limite, las opciones de ataque (fisico, magia, limite o huir) y los resultados de cada accion.

Consideraciones importantes:
1- Estructura del proyecto: el codigo esta dividido en paquetes (componentes, entidades, mapa) por lo que es necesario respetar las carpetas al compilar, sino van a fallar los imports.

2- Ataque limite: la barra se llena con daño, no con turnos. Si el jugador no recibe ni hace daño, la barra no avanza.

3- Probabilidades en Gongaga: al entrar a Gongaga hay un 30% de chance de encontrar una materia y un 70% de que aparezca un enemigo salvaje, esto se hace de forma aleatoria cada vez que se viaja.

4- Tienda en Sector 7: las mejoras se compran con la chatarra que se va juntando en los combates, asi que conviene farmear un poco antes de gastar.

5- Combate final: contra Sephiroth no se puede huir, asi que hay que entrar bien preparado con materias equipadas y un buen nivel.