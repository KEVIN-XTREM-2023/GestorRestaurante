# Gestor Restaurante



<center>

<img src="https://cdn.icon-icons.com/icons2/2419/PNG/512/service_restaurant_icon_146861.png" alt="VB." width="200">
</center>

Sistema para la gestión de comida en un restaurante usando patrones de software.


Este sistema fue desarrollado como proyecto de fin de semestre para la materia de Patrones de Software, en la Universidad Técnica de Ambato.

Este proyecto se basa en la creación de un restaurante con registro de clientes usando patrones de software. 

### Patrón de diseño?

-   Un patrón de diseño es una descripción de 
    clases y objetos comunicándose entre sí 
    adaptada para resolver un problema de 
    diseño general en un contexto particular.

### ELEMENTOS DE UN PATRON


- Nombre del Patron: 
    - Se utiliza para describir un problema de diseño, su solución, y consecuencias en
    una o dos palabras..

- El problema: 
    - Describe cuando aplicar el patrón. Se explica el problema y su contexto.
- Solución: 
    - Describe los elementos que forma el diseño, sus relaciones, responsabilidades y
    colaboraciones. .
- Las consecuencias:
    - Las consecuencias son los resultados de aplicar el patrón.

Este proyecto esta conformado por tres tipos de patrones los cuales pertenecen a diferentes familias

### Clase de patrones 
#### Creacionales
El propósito del patrón es 
crear nuevos objetos de 
forma transparente al 
sistema.

#### Estructurales
Reflejan la composición 
formada por diferentes 
objetos o clases con un 
objetivo establecido 
formando una estructura 
mayor.

#### De comportamiento
Reflejan la forma de 
interactuar diferentes objetos 
o clases distribuyendo su 
responsabilidad para lograr un 
objetivo establecido.

## Software Utilizado
En este proyecto, se utilizó NetBeans IDE , para los siguientes patrones (Singleton, Decorador y Abstract Factory), también se utilizo el apache XAMPP con MYSQL que ya viene integrado , otras opciones de herramientas serian:

- Eclipse en vez de NetBeans IDE 
- WampServer para el Apache
- IntelliJ IDEA en vez de NetBeans IDE
- Cualquier BD con tal que sean relacionales 

## Lenguaje de Programación
<center>

<img src="https://cdn.icon-icons.com/icons2/2415/PNG/512/java_original_wordmark_logo_icon_146459.png" alt="VB." width="200">
</center>



El sistema fue implementado en Java para la creación de los patrones asi como su interfaz usando Jframes.


## Servidor Apache
Se uso XAMPP porque ya viene incluido MySQL el cual se utiliza para hacer la conexión a  la base de datos y aplicar el patron Singleton que es única instancia.

## Version Utilizada
Java la version 8 porque es la mas estable que existe.

## Instalación de Java

1. Descarga de JDK (Java Development Kit):

- Visita la página de descargas de Oracle JDK: Oracle JDK Downloads.
- Selecciona la versión de JDK que deseas descargar. A menudo, se recomienda la versión más reciente. Haz clic en "JDK Download".
2. Acepta los Términos de Uso:

- En la página de descargas, acepta los términos de uso y elige la versión adecuada para tu sistema operativo (Windows, macOS, o Linux).
3. Descarga e Instala:

- Descarga el instalador y ejecútalo.
- Sigue las instrucciones del instalador para completar la instalación. Asegúrate de marcar la opción para agregar el JDK al PATH del sistema.
4. Verifica la Instalación:

- Abre una nueva ventana de terminal o símbolo del sistema.
- Ejecuta el comando java -version y javac -version para verificar que Java se ha instalado correctamente.

## Instalación de XAMPP
1. Descarga de XAMPP:

- Visita el sitio oficial de XAMPP: XAMPP Download.
    Haz clic en el botón de descarga correspondiente a tu sistema operativo (Windows).
2. Ejecuta el Instalador:

    Una vez descargado, ejecuta el archivo de instalación (por lo general, tiene un nombre como "xampp-installer.exe").
3. Configura las Componentes:

-   Durante la instalación, podrás seleccionar qué componentes instalar. Para la mayoría de los casos, puedes mantener las configuraciones predeterminadas, que incluyen Apache, MySQL, PHP, y phpMyAdmin.
4. Selecciona el Directorio de Instalación:

- Elige la ubicación en la que deseas instalar XAMPP. El directorio predeterminado es C:\xampp.
5. Configuración de Servicios:

-   En la pantalla de configuración de servicios, puedes seleccionar si deseas que XAMPP se inicie automáticamente al arrancar el sistema. Puedes dejar las opciones predeterminadas si no estás seguro.
6. Configuración del Bitnami:

- Durante la instalación, es posible que se te pregunte si deseas instalar aplicaciones adicionales de Bitnami. Puedes optar por instalar o desmarcar esta opción según tus necesidades.
7. Completa la Instalación:

- Continúa con el proceso de instalación siguiendo las instrucciones en pantalla.
8. Inicia XAMPP:

-   Después de la instalación, inicia XAMPP desde el acceso directo en el escritorio o desde el menú de inicio.
9. Inicia los Servicios:

- En la interfaz de control de XAMPP, inicia los servicios Apache y MySQL haciendo clic en los botones correspondientes.
10. Verifica la Instalación:

-   Abre un navegador web y accede a http://localhost/. Si todo está configurado correctamente, deberías ver la página de inicio de XAMPP.



## Anexos
### Menu Principal
![Login.](https://github.com/Kevin-Saquinga/ImagenesGit/blob/patrones/perfil.png?raw=true)



### Registro Patron Singleton
![Login.](https://github.com/Kevin-Saquinga/ImagenesGit/blob/patrones/registro.png?raw=true)


### Menu Restaurante Patron Singleton
![Login.](https://github.com/Kevin-Saquinga/ImagenesGit/blob/patrones/menuresta.png?raw=true)


### Elegir Menu Patron  Abstract Factory 
![Login.](https://github.com/Kevin-Saquinga/ImagenesGit/blob/patrones/comida.png?raw=true)

### Cancelar - Total Patron Decorador
![Login.](https://github.com/Kevin-Saquinga/ImagenesGit/blob/patrones/pagar.png?raw=true)

