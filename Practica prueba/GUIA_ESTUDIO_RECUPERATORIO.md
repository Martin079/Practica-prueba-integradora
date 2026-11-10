# Guía de Estudio - Recuperatorio Programación Orientada a Objetos
## Juego Tower Defense: Torres vs Jefe Final

---

## 1. CONCEPTOS FUNDAMENTALES DE POO APLICADOS

### 1.1 Herencia (`extends`)
```java
public abstract class Torre implements Atacante { ... }
public class Arquera extends Torre { ... }
public class Catapulta extends Torre { ... }
public class Glacial extends Torre { ... }
```
- **Clase abstracta base** (`Torre`): define atributos comunes (`costo`, `danioBase`) y constructor compartido
- **Subclases concretas**: cada una redefine el comportamiento de `atacar()`
- **Principio**: "Es un" — Arquera *es una* Torre

### 1.2 Polimorfismo
```java
ArrayList<Torre> torres = jugador.getTorres();
for (Torre torre : torres) {
    torre.atacar(jefe); // Cada una ejecuta SU versión de atacar()
}
```
- **Una referencia de tipo base** (`Torre`) apunta a objetos de tipos derivados
- **Dispatch dinámico**: Java decide en tiempo de ejecución qué `atacar()` llamar
- **Sin `instanceof` ni `if/else` por tipo** — requisito explícito del enunciado

### 1.3 Interfaces (`implements`)
```java
public interface Atacante {
    int atacar(Enemigo enemigo); // Contrato: retorna monedas ganadas
}

public abstract class Torre implements Atacante { ... }
```
- **Contrato**: define QUÉ debe hacer, no CÓMO
- **Múltiple implementación**: cualquier clase puede `implements Atacante`
- **Polimorfismo de interfaz**: `Atacante a = new Arquera();`

### 1.4 Sobrescritura (`@Override`)
```java
@Override
public int atacar(Enemigo enemigo) { ... } // En Arquera, Catapulta, Glacial
```
- **Obligatorio** en subclases de clase abstracta con métodos abstractos
- **Anotación `@Override`**: compilar avisa si la firma no coincide con la superclase
- **Covarianza de retorno**: el tipo de retorno puede ser subclase (no aplica aquí, todos `int`)

### 1.5 Clases Abstractas vs Interfaces
| Característica | Clase Abstracta | Interfaz |
|----------------|-----------------|----------|
| Estado (atributos) | ✅ Sí (`costo`, `danioBase`) | ❌ Solo `static final` |
| Constructores | ✅ Sí | ❌ No |
| Métodos con implementación | ✅ Sí | ✅ `default` (Java 8+) |
| Herencia múltiple | ❌ Una sola | ✅ Múltiples `implements` |
| Uso aquí | Base común con estado | Contrato de comportamiento |

---

## 2. ARRAYLIST Y COLECCIONES

### 2.1 Declaración y Uso Básico
```java
// En Jugador.java
private ArrayList<Torre> torres = new ArrayList<>();

public ArrayList<Torre> getTorres() { return torres; }
public void agregarTorre(Torre torre) { torres.add(torre); }
```

### 2.2 Operaciones Clave
| Método | Descripción | Ejemplo |
|--------|-------------|---------|
| `add(E)` | Agrega al final | `torres.add(nuevaTorre)` |
| `get(int)` | Obtiene por índice | `torres.get(0)` |
| `size()` | Cantidad de elementos | `torres.size()` |
| `isEmpty()` | Verifica si vacío | `torres.isEmpty()` |
| `remove(int)` | Elimina por índice | `torres.remove(0)` |

### 2.3 Iteración Polimórfica (for-each)
```java
// FORMA CORRECTA - usa polimorfismo
for (Torre torre : torres) {
    int monedas = torre.atacar(jefe); // Dispatch dinámico
}

// FORMA INCORRECTA - rompe polimorfismo
for (Torre torre : torres) {
    if (torre instanceof Arquera) { ... } // ❌ NO HACER ESTO
}
```

### 2.4 Tipado Genérico
```java
ArrayList<Torre> torres; // Solo acepta Torre y subclases
// torres.add("string"); // Error de compilación
// torres.add(new Enemigo()); // Error de compilación
```

---

## 3. ENUMERADORES (ENUM)

### 3.1 Definición Básica con Estado
```java
public enum TipoTorre {
    ARQUERA(3),
    CATAPULTA(5),
    GLACIAL(4);

    private final int precio; // Estado inmutable

    TipoTorre(int precio) { this.precio = precio; }

    public int getPrecio() { return precio; }
}
```

### 3.2 Métodos en Enum (Requisito Clave)
```java
public boolean puedeComprar(int monedas) {
    return monedas >= precio; // Lógica encapsulada
}

public Torre crearTorre() { // Factory Method
    return switch (this) {
        case ARQUERA -> new Arquera();
        case CATAPULTA -> new Catapulta();
        case GLACIAL -> new Glacial();
    };
}
```

### 3.3 Uso en el Menú de Compra
```java
TipoTorre[] tipos = TipoTorre.values(); // Array con todos los valores

for (int i = 0; i < tipos.length; i++) {
    System.out.println((i + 1) + ". " + tipos[i]); // toString() personalizado
}

int opcion = entrada.ingresarEntero(1, tipos.length + 1);
TipoTorre seleccionado = tipos[opcion - 1];

if (seleccionado.puedeComprar(jugador.getMonedas())) {
    jugador.restarMonedas(seleccionado.getPrecio());
    Torre torre = seleccionado.crearTorre(); // Factory
    jugador.agregarTorre(torre);
}
```

### 3.4 Enum vs Clase Constante
| Enum | Clase con `static final` |
|------|--------------------------|
| Type-safe (solo valores definidos) | Cualquier `int` válido |
| Métodos y estado propios | Solo datos |
| `values()`, `valueOf()`, `ordinal()` | Manual |
| `switch` exhaustivo (compilador avisa) | No verificado |

---

## 4. ENCAPSULAMIENTO Y ACCESO

### 4.1 Niveles de Visibilidad
```java
public class Torre {
    private int costo;        // Solo esta clase
    protected int danioBase;  // Esta clase + subclases + mismo paquete
    public int getCosto() { ... } // Todos
}
```

### 4.2 Getters Protegidos para Subclases
```java
// En Torre (abstracta)
protected int getDanioBase() { return danioBase; }

// En Arquera (subclase)
int danio = getDanioBase(); // ✅ Acceso permitido
// int danio = this.danioBase; // ❌ Si fuera private en padre
```

### 4.3 Principio de Responsabilidad Única (SRP)
| Clase | Responsabilidad |
|-------|-----------------|
| `Jugador` | Gestiona monedas y colección de torres |
| `Torre` (abstracta) | Estado común de torres |
| `Arquera/Catapulta/Glacial` | Lógica de ataque específica |
| `Enemigo` | Vida y recibir daño |
| `Utilidades` | Funciones helper (`limitarVida`) |
| `TipoTorre` | Datos de compra + factory |
| `Main` | Flujo del juego (UI + coordinación) |

---

## 5. FLUJO DEL JUEGO - PUNTOS CLAVE

### 5.1 Bucle Principal
```java
while (opcion != 3 && jefe.getVida() > 0) {
    // Mostrar menú
    // Leer opción
    // Ejecutar acción
    // Verificar victoria
}
```
- **Condición compuesta**: sale por "Salir" O por "Jefe muerto"
- **Verificación post-ataque**: después de `atacarJefe()`, chequear `jefe.getVida() <= 0`

### 5.2 Construir Torre
```
1. Mostrar opciones (usando TipoTorre.values())
2. Leer selección
3. Validar: tipo.puedeComprar(monedas)
4. Si OK: restarMonedas(), crearTorre(), agregarTorre()
5. Si NO: mensaje error
```

### 5.3 Atacar Jefe
```
1. Validar: torres no vacío
2. Para cada torre en orden:
   a. Si jefe muerto -> break
   b. torre.atacar(jefe) -> retorna monedas
   c. Si monedas > 0 -> jugador.agregarMonedas()
3. Mostrar resumen final
```

---

## 6. ERRORES COMUNES A EVITAR

| Error | Por qué está mal | Correcto |
|-------|------------------|----------|
| `if (torre instanceof Arquera)` | Rompe polimorfismo, viola requisito | `torre.atacar(enemigo)` |
| Verificar vida **antes** del daño en Catapulta | Consigna dice "después del impacto" | Aplicar daño, LUEGO verificar |
| `danioBase` como `public`/`protected` campo | Rompe encapsulamiento | `private` + `getDanioBase()` |
| Hardcodear precios en `Main` | Duplicación, mantenimiento difícil | Enum `TipoTorre` con `getPrecio()` |
| No usar `Utilidades.limitarVida()` | Requisito técnico #4 y #6 | `Enemigo.recibirDanio()` lo usa |
| `ArrayList` sin genérico (`new ArrayList()`) | Unsafe, warnings | `new ArrayList<Torre>()` |
| Olvidar `@Override` | No avisa si firma incorrecta | Siempre en métodos sobrescritos |

---

## 7. PATRONES DE DISEÑO IMPLÍCITOS

### 7.1 Template Method (implícito en `Torre`)
- Clase abstracta define estructura común
- Subclases implementan paso variable (`atacar()`)

### 7.2 Factory Method (en `TipoTorre.crearTorre()`)
```java
public Torre crearTorre() {
    return switch (this) { ... } // Crea instancia concreta
}
```
- Encapsula instanciación
- Cliente (`Main`) no conoce clases concretas

### 7.3 Strategy (interface `Atacante`)
- Comportamiento de ataque intercambiable
- Cada torre = estrategia distinta

---

## 8. CHECKLIST PRE-EVALUACIÓN

### Estructura del Proyecto
- [ ] Paquetes: `torres`, `enemigo`, `utilidades`, (default para `Main`)
- [ ] Imports correctos: `import torres.*;`, `import enemigo.Enemigo;`, `import utilidades.*;`

### Clases Obligatorias
- [ ] `Torre` abstracta con `costo`, `danioBase`, constructor, getters
- [ ] `Arquera`, `Catapulta`, `Glacial` extendiendo `Torre` + `@Override atacar()`
- [ ] `Atacante` interface con `int atacar(Enemigo)`
- [ ] `TipoTorre` enum con precios, `puedeComprar()`, `crearTorre()`
- [ ] `Jugador` con monedas, `ArrayList<Torre>`, métodos CRUD
- [ ] `Enemigo` con vida=100, `recibirDanio()` usando `Utilidades.limitarVida()`
- [ ] `Utilidades.limitarVida(int)` → clamp 0-100
- [ ] `Main` con menú, bucle, `construirTorre()`, `atacarJefe()`

### Lógica de Ataques
- [ ] Arquera: 1 flecha (10 dmg) + 50% 2da flecha (+10 dmg, +2 monedas)
- [ ] Catapulta: 20 dmg → **luego** si vida<30: +10 dmg extra
- [ ] Glacial: 15 dmg → si vida>40 **antes**: dmg/2; 25% +3 monedas

### Requisitos Técnicos
- [ ] Polimorfismo: `for (Torre t : torres) t.atacar(jefe)` sin `instanceof`
- [ ] ArrayList<Torre> en Jugador
- [ ] Enum usado **solo para compra** (factory crea objetos reales)
- [ ] Encapsulamiento: private + getters protected
- [ ] `Utilidades.limitarVida()` usada en `Enemigo`
- [ ] `Atacante.atacar()` retorna monedas ganadas

---

## 9. CONSEJOS PARA EL EXAMEN

### 9.1 Antes de Empezar
1. **Leer TODO el enunciado** 2 veces
2. **Identificar clases** del enunciado (sustantivos): Jugador, Torre, Arquera, Catapulta, Glacial, Jefe, Enemigo, Menú
3. **Identificar comportamientos** (verbos): construir, atacar, comprar, recibir daño, limitar vida
4. **Dibujar diagrama mental** de herencia y relaciones

### 9.2 Orden de Implementación Recomendado
```
1. Utilidades.limitarVida()        ← Fácil, base para Enemigo
2. Enemigo                         ← Usa Utilidades
3. Atacante (interface)            ← Contrato
4. Torre (abstracta)               ← Base común
5. Arquera, Catapulta, Glacial     ← Lógica específica
6. TipoTorre (enum)                ← Datos + factory
7. Jugador                         ← Estado + colección
8. Main                            ← Orquesta todo
```

### 9.3 Testing Mental (Casos de Borde)
- Construir sin monedas → mensaje error, no agrega torre
- Atacar sin torres → mensaje error, no crashea
- Catapulta: jefe 35 vida → 20 dmg → queda 15 → **extra 10** → queda 5
- Catapulta: jefe 25 vida → 20 dmg → queda 5 → **extra 10** → queda 0 (muerto)
- Glacial: jefe 50 vida → dmg = 7 (mitad)
- Glacial: jefe 30 vida → dmg = 15 (normal)
- Jefe muere en torre 2 de 4 → torres 3 y 4 no atacan

### 9.4 Trucos de Eclipse/IDE
- `Ctrl+Shift+O` → Organiza imports
- `Alt+Shift+S` → Generate getters/setters/constructors
- `Ctrl+1` sobre error → Quick fix
- `Ctrl+F11` → Re-ejecutar último run
- `Syso` + `Ctrl+Espacio` → `System.out.println()`

### 9.5 Manejo del Tiempo
| Tiempo | Actividad |
|--------|-----------|
| 10 min | Lectura + diagrama mental |
| 15 min | Clases base (Utilidades, Enemigo, Atacante, Torre) |
| 20 min | 3 Torres concretas (lógica de ataque) |
| 10 min | Enum TipoTorre + Jugador |
| 15 min | Main (menú, bucles, integración) |
| 10 min | Testing + corrección bugs |
| **80 min** | **Total estimado** |

---

## 10. RESUMEN DE SINTAXIS CLAVE

```java
// Enum con constructor y métodos
public enum Nombre { VALOR1(1), VALOR2(2); 
    private int x; 
    Nombre(int x) { this.x = x; }
    public int getX() { return x; }
}

// ArrayList tipado
List<Torre> lista = new ArrayList<>();
lista.add(new Arquera());
for (Torre t : lista) { t.atacar(e); }

// Polimorfismo
Torre t = new Catapulta(); // Upcasting
t.atacar(enemigo);         // Llama Catapulta.atacar()

// Interface
Atacante a = new Glacial(); // Polimorfismo de interfaz
a.atacar(enemigo);

// Clase abstracta
public abstract class Base {
    protected int valor; // Visible en subclases
    public abstract void metodoAbstracto(); // Obliga a implementar
}

// Override
@Override
public void metodoAbstracto() { ... } // Compilador verifica firma
```

---

## 11. DIFERENCIAS CON EJERCICIOS SIMILARES

| Variante Común | Qué Cambia |
|----------------|------------|
| Torres con alcance/velocidad | Más atributos en `Torre` abstracta |
| Enemigos múltiples (ArrayList<Enemigo>) | Doble loop: torres × enemigos |
| Torres mejorables (upgrade) | Método `mejorar()` en Torre + costo extra |
| Mapa 2D con posiciones | Clase `Posicion` + `tablero[x][y]` |
| Ondas de enemigos | Bucle externo por oleada |
| Factory externo (no enum) | Clase `TorreFactory` separada |

**La base siempre es la misma**: herencia + polimorfismo + encapsulamiento + colecciones.

---

¡Éxitos mañana! Repasá este documento, practicá escribir las clases de memoria y entendé **por qué** cada decisión de diseño (no solo *qué* escribir). La lógica de Catapulta (daño extra **después**) y el uso correcto del Enum son los puntos donde más se equivoca la gente.

---

*Guía generada basada en el enunciado "Tema 1 – Práctico: Evaluación integradora del paradigma orientado a objetos"*