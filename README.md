# Tarea 3 — Lenguajes de Programación 2026

**Nombre:** Emilio Araya  
**Rol:** 202473561-1

---

## Descripción del juego

RPG por turnos basado en el universo de Final Fantasy VII.  
El jugador controla a **Cloud Strife** y debe explorar tres zonas, ganar experiencia, equipar materias y derrotar al jefe final **Sephiroth** en el Núcleo del Planeta.

### Zonas
| Zona | Nivel requerido | Descripción |
|------|----------------|-------------|
| **Sector 7** | 1 | Simulador de combate y tienda de mejoras |
| **Gongaga** | 5 | Jungla: 30% Materia, 70% emboscada de enemigos salvajes |
| **Núcleo del Planeta** | 20 | Combate final contra Sephiroth (no se puede huir) |

### Mecánicas principales
- **Combate por turnos**: ataque físico, magia, ataque límite o huir
- **Barra de límite**: se carga con daño recibido y daño infligido; al llegar a 100 permite un ataque devastador
- **Materias**: se recogen en Gongaga y se equipan en la Buster Sword para habilitar magia
- **Muerte de Cloud**: pierde mochila y chatarra, conserva materias equipadas, regresa al Sector 7

---

## Compilación y ejecución

### Requisitos
- Java 11 o superior
- `make` instalado

### Comandos

```bash
# Compilar
make

# Compilar y ejecutar
make run

# Limpiar archivos compilados
make clean
```

### Sin make

```bash
mkdir -p out
javac -d out $(find . -name "*.java" ! -name "Gongarga.java" ! -name "main.java")
java -cp out Main
```
