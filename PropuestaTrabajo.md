# Reconocimiento de lengua de signos usando redes neuronales #

El objeto fundamental del trabajo es la implementación de algoritmos de aprendizaje basados en redes neuronales y su aplicación al problema de reconocer (sobre imágenes en formato PGM), señales realizadas con la mano y en última instancia reconocimiento del alfabeto en el lenguaje de signos para personas sordas.
Para realizar el trabajo es necesario conocer los contenidos que se dan en el tema 5 de la asignatura.
## PARTE I: Lectura de las referencias ##
Se recomiendan las siguientes referencias para comprender bien el algoritmo de retropropagación. Además, en la primera de ellas (sección 4.7) se describe una aplicación similar a la que se pide en este trabajo.

  * Tema 4 del libro "Machine Learning" de Tom Mitchell (en la biblioteca del centro hay varios ejemplares para su préstamo y consulta). En la sección 4.5 se describe el algoritmo de retropropagación y en la 4.7 se dan detalles sobre la aplicación de redes neuronales al reconocimiento de imágenes.
  * Sección 20.5 del capítulo 20 del libro Inteligencia Artificial: un enfoque moderno de S. Russell y P. Norvig (segunda edición en español, disponible en la biblioteca). En la tercera edición (sólo en inglés) las redes neuronales se tratan en la sección 18.7.

## PARTE II: Implementación de algoritmos de aprendizaje de redes neuronales ##
Implementar en Lisp (preferentemente) o en Java los siguientes algoritmos de aprendizaje para redes neuronales:

  * Regla delta para entrenamiento del perceptrón simple
  * Algoritmo de retropropagación
  * Algoritmo de retropropagación con momentum

En todos los casos, considerar el sigmoide como función de activación. En el caso del perceptrón multicapa, es suficiente con implementar el algoritmo para una y para dos capas ocultas, aunque es prefible que el algoritmo se haga de manera genérica y pueda ejecutarse con cualquier número de capas ocultas. En cualquier caso, el usuario debe poder introducir como argumento de entrada tanto el número de capas ocultas de la red, como el número de neuronas de cada capa.

NOTA: La implementación debe ser original, no permitiéndose usar ninguna parte de código o librerias (de redes neuronales) que no hayan sido desarrolladas por los miembros del grupo.
## PARTE III: Aprendizaje de redes neuronales para el reconocimiento del alfabeto en el lenguaje de signos ##
En esta parte se trata de construir redes neuronales que permitan reconocer imágenes de determinados signos realizados con las manos:

  * Distinguir una mano abierta (la derecha, por ejemplo) de una mano cerrada.
  * Distinguir una mano izquierda de una mano derecha.
  * Distinguir número de dedos levantados (0,1,2,3,4 ó 5) de una mano derecha.
  * Distinguir imágenes de las distintas vocales en el alfabeto del lenguaje de signos
  * Distinguir imágenes de las distintas letras en el alfabeto del lenguaje de signos

Esta lista de problemas está en orden ascendente de dificultad. No es necesario resolver todos los problemas anteriores para conseguir la máxima calificación, pero se tendrá en cuenta el grado de dificultad del problema o problemas de la lista anterior que se resuelvan.

Una parte importante de este trabajo es la tarea de construir un conjunto de entrenamiento. Para ello se recomienda hacer numerosas fotos de manos realizando los diferentes signos que se pretenden reconocer. No es necesario que las imágenes sean de una resolución alta, ni en color. Para mayor sencillez de la tarea de clasificación, se pueden hacer las fotos con guante blanco sobre fondo oscuro (o viceversa).

Se recomienda usar el formato PGM con una escala de grises no muy amplia. PGM (Portable GreyMap) es un formato de imagen muy simple, en el que cada pixel está codificado por un número (usualmente un byte), que indica su escala de gris. Para esta aplicación no deberíamos necesitar imágenes de más de 30x30 píxeles y con una amplitud de grises no mayor de 200. En wikipedia se puede encontrar más información sobre PGM y la libreria Netpbm que permite pasar de unos formatos de imagen a otros (Netpbm está disponible para numerosas plataformas).
## PARTE IV: Documentación del experimento ##
Se pide elaborar un pequeño documento en el que se describa el trabajo realizado, los problemas que se han resuelto y la experimentación que se ha llevado a cabo para obtener las redes neuronales que sirven para realizar las distintas tareas de clasificación propuestas anteriormente. En este documento se debe describir, al menos:

  * Proceso de obtención del conjunto de entrenamiento.
  * Distintas posibilidades intentadas, en cuanto a estructura de la red (número de capas ocultas, número de unidades en cada capa, etc.)
  * Resultados según los distintos algoritmos de entrenamiento (regla delta, retropropagación, momentum)
  * Generación de los pesos iniciales, factores de aprendizaje,...
  * Distintos criterios de parada

## PARTE V: Defensa ##
Se recomienda tener preparada la presentación de la defensa en un portátil propio. Si no se dispone de portátil para la defensa, contactar previamente con el profesor. En cualquier caso, el código usado para la demostración deberá ser el mismo que se envíe antes de la fecha tope de entrega.

En la presentación se ha de mostrar el conjunto de entrenamiento usado y dar ejemplos de clasificaciones que realizan las redes neuronales finalmente obtenidas para cada problema.

Además, deberá mostrarse la ejecución de los algoritmos de entrenamiento. Obviamente, las redes finalemente obtenidas son el resultado de un proceso de entrenamiento realizado previamente, que no hay que realizar en la defensa. Sin embargo, se debe traer preparado una pequeña demostración en la que se vea la ejecución de los distintos algoritmos implementados (por ejemplo, con una versión modificada en la que se obtenga alguna salida que indique cómo van actualizándose los pesos). Cuando se realice la entrega en el sistema, no es necesario adjuntar el conjunto de entrenamiento. Sólamente es necesario adjuntar en el sistema de entrega, el código y la documentación.