[captura]: resources/readme/screenshot.png
[sprite_caballero]: resources/knight_blue/idle1.png "Caballero"
[sprite_duende_piromano]: resources/torch_goblin_red/idle1.png "Duende pirómano"
[choza_duendes]: resources/goblin_hut/standing1.png "Choza de duendes"
[choza_destruida]: resources/goblin_hut/destroyed.png "Choza destruída"
[jerarquia_entidad]: resources/readme/jerarquia_entidad.png "Diagrama de clases UML"
[animaciones]: resources/readme/animaciones.png "Animaciones de personaje"
[caballero_en_juego]: resources/readme/caballero.png "Caballero en el juego"
[caballero_atacando]: resources/readme/caballero_atacando.png "Caballero atacando"

# Juego de combate medieval

Sencillo juego de combate que utiliza varias características de j2d para crear un proyecto cohesionado.

El jugador controla un caballero que debe defenderse de los duendes que salen periódicamente de la choza, ya sea matando a todos los duendes o destruyendo el edificio.

![captura][]

## Créditos

- Sprites
  - Autor: [Pixel Frog](https://pixelfrog-assets.itch.io/)
  - Pertenece a la versión antigua del pack [Tiny Swords](https://pixelfrog-assets.itch.io/tiny-swords)
  - Licencia: [CC0](https://creativecommons.org/publicdomain/zero/1.0/)

- Sonidos de paso
    - [Grass Step Left](https://freesound.org/s/535220/) y [Grass Step Right](https://freesound.org/s/535222/)
    - Autor: spycrah
    - Licencias: [Attribution 4.0](https://creativecommons.org/licenses/by/4.0/)

- Sonido de ataque con antorcha
    - [Torch Swing.wav](https://freesound.org/s/472687/)
    - Autor: SilverIllusionist
    - Licencia: [Attribution 4.0](https://creativecommons.org/licenses/by/4.0/)

- Sonido de ataque con espada
    - [Basic Melee Swing / Miss / Whoosh](https://freesound.org/s/420668/)
    - Autor: SypherZent
    - Licencia: [CC0](https://creativecommons.org/publicdomain/zero/1.0/)

- Sonido de destrucción de choza
    - [Wood Break.wav](https://freesound.org/s/443293/)
    - Autor: Deathscyp
    - Licencia: [CC0](https://creativecommons.org/publicdomain/zero/1.0/)

## Jerarquía de clases de `EntidadFaccion`

![jerarquia_entidad][]

`EntidadFaccion` representa un personaje o edificio que pertenece a una facción (`CABALLEROS` o `DUENDES`) y puede ser dañado por facciones enemigas. Al incluirse en una `EscenaCombate`, la `EntidadFaccion` se registra desde su método `objetoIncluido()` para que pueda ser golpeada.

### Controlador de vida

Cada `EntidadFaccion` tiene un `ControladorVida` que debe ser inicializado mediante el método protegido `EntidadFaccion.setControladorVida()`. De este modo, cada subclase puede definir un `ControladorVida` con parámetros que no están disponibles en el constructor.

Por ejemplo, `Personaje` necesita pasar su `PielPersonaje` al constructor de `ControladorVida`, pero el atributo `piel` pertenece a la instancia y no es posible usarlo antes de llamar a `super()`, por lo que no se puede inyectar el `ControladorVida` directamente en el constructor de `EntidadFaccion`.

Por otra parte, la clase abstracta `Edificio` implementa `IVidaControlada`. Si `ControladorVida` fuera un parámetro del constructor, `ChozaDuende` necesitaría construirlo usando una referencia a `this`, lo cual no es posible antes de inicializar `super`.

En nuestra jerarquía, `ChozaDuende` y `Personaje` inicializan el `ControladorVida`, mientras que `Caballero` y `DuendePiromano` se aprovechan de la inicialización realizada por `Personaje`. Opcionalmente, se puede pasar un `IVisualizadorNumerico` al constructor de `Personaje` para que se visualize una barra de vida, que es lo que hace `Caballero` indirectamente.

### `Personaje`

Esta clase se encarga del comportamiento común de todos los personajes: animaciones y ataques.

Con el método `ciclo()`, se comprueba hacia dónde se mueve el personaje e intenta cambiar el `Estado`, que está compuesto por una `Accion` y dos valores de tipo `Direccion`, `direccionPrimaria` y `direccionSecundaria`. De este modo, si un personaje se mueve en una trayectoria hacia arriba pero ligeramente inclinada a la derecha, su `Estado` tendrá `CAMINANDO` como acción, `ARRIBA` como dirección primaria y `DERECHA` como dirección secundaria. En caso de que el movimiento esté perfectamente alineado a una dirección, ambos atributos de dirección tendrán ese mismo valor. Si ha podido cambiar el `Estado`, se notifica a `PielPersonaje` para que muestre la animación correspondiente.

`PielPersonaje` utiliza dos niveles de `EnumMap` para mapear cada `Estado` a una animación usando los enumerados `Accion` y `Direccion`. Como solo las animaciones de ataque tienen variantes hacia arriba y abajo, se procede de la siguiente manera:

- Para la acción `ATACANDO`, se accede al `EnumMap<Direccion, Animacion>` con la dirección primaria.

- Para otras acciones:
    - Si la dirección primaria es horizontal, se accede con ella al segundo nivel de `EnumMap`.
    - Si la dirección primaria es vertical y la secundaria horizontal, se accede con la dirección secundaria.
    - Si ninguna dirección es horizontal (movimiento perfectamente vertical), se accede usando la constante `DERECHA`.


![animaciones]

Si el personaje decide llamar al método `ataca()` desde el `Estado` descrito, la `Acción` pasará a ser `ATACANDO` y se bloqueará tanto el cambio de animaciones como los nuevos ataques hasta que finalice el `temporizadorAtaque`. La animación resultante será el ataque hacia arriba porque ahora sí podemos usar la dirección primaria.

Cuando finalice el `temporizadorAtaque`, el método `ciclo()` podrá devolver al personaje a la animación de la acción `PARADO` o `CAMINANDO` que le corresponda.

### `AtacanteMele`

Para simular un ataque con un arma cuerpo a cuerpo, se genera un `AreaDanho` en la dirección en la que mira el personaje. Este `AreaDanho` aplicará un daño a todos los objetivos de facciones enemigas que estén presentes en ella en el momento preciso para simular el golpe y después se autodestruirá.

Con `super.ataca()` en su método `ataca()`, la clase intenta cambiar el estado a `ATACANDO` y, si ha podido, efectúa el ataque.

### `Caballero`

La clase `Caballero` representa al personaje jugable, un caballero de armadura azul que, utilizando un objeto `GuiaPorRaton`, puede moverse hacia la posición del ratón mientras se mantiene pulsado el botón izquierdo del mismo y que realiza un ataque con su espada .

![sprite_caballero][]

El sprite del caballero es acompañado por dos visualizadores numéricos. El primero representa la salud de `EntidadFaccion.controladorVida` y el segundo representa el tiempo restante de `Personaje.temporizadorAtaque`. Esta segunda barra está llena cuando se puede realizar un ataque y se vacía la realizarlo, llenándose de nuevo cuando el ataque vuelve a estar disponible.

![caballero_en_juego][]

![caballero_atacando][]

### `DuendePiromano`

Es el principal enemigo del juego. Su método `ciclo()` utiliza la `GuiaObjeto` para moverlo hacia el jugador y atacar cuando se acerca lo suficiente.

![sprite_duende_piromano][]

### `Edificio`

`Edificio` es la clase base para un edificio que puede estar representado por un icono o una animación base y que puede ser destruido, lo cual reproduce un sonido y cambia su icono.

Al igual que `Personaje`, es un `JObjetoRectangulo` que tiene un `JObjetoIcono` como adorno, siendo en este caso una instancia de la propia clase `JObjetoIcono` y no una subclase como `PielPersonaje`.

### `ChozaDuende`

La choza es un edificio que genera duendes periódicamente usando un `Temporizador`. Puede generar un total de hasta 4 duendes, aunque la generación puede ser interrumpida si es destruída.

![choza_duendes][]
![choza_destruida][]
