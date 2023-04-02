# HPE Challenge. Fase 2: El Conflicto

Para el desarrollo de esta prueba se ha realizado una herramienta de Control de Consumo Hidroeléctrico, apodada _hidrococo_. Este repositorio contiene dos contenedores de docker que pueden ser desplegados utilizando la siguiente sucesión de comandos en la carpeta raíz:

```bash
docker-compose build  # Construye todo lo necesario para docker
docker-compose up -d  # Despliega los servicios en segundo plano
docker-compose exec hidrococo bash  # Inicia una sesión bash en el contenedor de la aplicación

hidrococo ayuda  # Imprime la ayuda de la aplicación
```

Desafortunadamente, a fecha de la entrega no se han conseguido conectar los contenedores de docker de la base de datos y la aplicación, lo que ha resultado en que no se hayan podido realizar tests de funcionamiento y la aplicación final resulte inutilizable.
Pese a esto, el repositorio presenta la arquitectura general con dockers desplegables así como el código fuente casi al completo de la  aplicación java.
