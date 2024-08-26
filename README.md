# ms_publicacion
Este servicio permite crear publicaciones y obtener una línea de tiempo (publicaciones de usuarios seguidos).

### Dominio
![Dominio.png](doc%2FImagenes%20readme%2FDominio.png)
### DTO
![DTO.png](doc%2FImagenes%20readme%2FDTO.png)
### Diagrama de servicio
![Diagrama de clases.png](doc%2FImagenes%20readme%2FDiagrama%20de%20clases.png)

## API Reference

Se exponen los siguientes servicios:

#### Crear
```http
POST /api/v1/publicacion/{usuarioId} HTTP/1.1

{
    "contenido": "mensaje de prueba"
}
```
| Parameter   | Type     | Description                                                        |
|:------------|:---------|:-------------------------------------------------------------------|
| `usuarioId` | `Long`   | **Requerido**. ID del usuario creador de la publicacion            |
| `contenido` | `String` | **Requerido**. Contenido de la publicacion. Máximo 280 caracteres. |
#### Respuesta
```json
{
  "id": 12,
  "contenido": "mensaje de prueba",
  "usuarioId": 3,
  "fechaCreacion": "2024-08-25T16:56:53.0495418"
}
```

#### Obtener timeline
```http
GET /api/v1/publicacion/{usuarioId}/timeline?page={page}&size={size}&sort={sort}
```
| Parameter | Type     | Description                                                          |
| :-------- | :------- |:---------------------------------------------------------------------|
| `usuarioId`      | `string` | **Requerido**. id del usuario al que se le va a buscar el timeline   |
| `page`      | `int` | **Opcional**. nro de página (Default: 0)                             |
| `size`      | `int` | **Opcional**. cantidad por página (Default: 5)                       |
| `sort`      | `string` | **Opcional**. Criterio de ordenamiento (Default: fechaCreacion,DESC) |
#### Respuesta
```json
{
  "content": [
    {
      "id": 12,
      "contenido": "#Video En un partido muy entretenido, Lanús y Godoy Cruz igualaron 1-1 por la fecha 12 de la #LigaProfesional.",
      "usuarioId": 3,
      "fechaCreacion": "2024-08-25T16:56:53"
    },
    {
      "id": 11,
      "contenido": "Esta es tu señal, cielo. Activá tu cuenta remunerada a través de la app, que tenés unos pesos más todos los días.",
      "usuarioId": 4,
      "fechaCreacion": "2024-08-25T16:56:21"
    },
    {
      "id": 10,
      "contenido": "¿Mark Wahlberg y Halle Berry en una comedia de acción? Tengo que ver eso. La película ‘El Sindicato’ ya está disponible, solo en Netflix. ",
      "usuarioId": 5,
      "fechaCreacion": "2024-08-25T16:55:44"
    },
    {
      "id": 9,
      "contenido": "¡Están de vuelta! ‘Ranma ½’, una nueva adaptación de un clásico llegará a Netflix este 5 de octubre.",
      "usuarioId": 5,
      "fechaCreacion": "2024-08-25T16:55:04"
    },
    {
      "id": 8,
      "contenido": "¿Se dieron cuenta de esta señal? Son las 14 h del 19/8, si sumamos 14 + 19 + 8 = 41, igual que la 41% TNA de nuestra cuenta remunerada. ¿Coincidencia? No lo creo.",
      "usuarioId": 4,
      "fechaCreacion": "2024-08-25T16:54:38"
    }
  ],
  "page": {
    "size": 5,
    "number": 0,
    "totalElements": 9,
    "totalPages": 2
  }
}
```
## Correr aplicación
```bash
mvn spring-boot:run
```
## Correr los test
```bash
mvn clean test
```
## Tech stack
Java 17, Spring 3.3.3, MySQL

