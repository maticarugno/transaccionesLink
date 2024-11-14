# Transacciones Link

## Arquitectura

Se opto por una arquitectura de microservicios, se eligio este tipo de arquitectura debido 
a su escalabilidad, resiliencia y flexibilidad.

La misma cuenta con los siguientes microservicios:

- Gestor de Transacciones: que contiene los endpoint de ingreso al sistema, la logica de procesamiento de transacciones y las validaciones para asegurar idempotencia y consistencia en los datos
- Conversor de monedas: convierte montos en cualquier moneda a la moneda base (USD)
- Validador de transacciones: realiza validaciones sobre las transacciones para determinar si seran aprobadas o no. Funciona de forma asincronica
- Compensador de transacciones: en el caso de transacciones fallidas, contiene la logica de compensacion para las mismas

Conexion entre microservicios:
La comunicacion entre el gestor de transacciones y el validador se realiza de forma asincrona
mediante colas en RabbitMQ, al igual que la comunicacion entre el gestor y el sistema de compensacion.
Para estos casos se opto por colas para poder manejarlos de forma asincrona y desacoplarlos uno de otro. 
De esta manera el usuario no tiene que esperar que se realicen todas las validaciones necesarias y puede seguir operando.

La comunicacion entre el gestor de transacciones y el de conversion de moneda se realiza mediante un ednpoint.
En este caso se usa una api REST ya que ofrece una comunicacion sencilla y efectiva, especialmente donde la latencia no es un problema

Persistencia:
Se implemento una base de datos en MongoDB. La misma cuenta con dos colecciones, 
transaccion para guardar las transacciones, y cotizaciones, para las cotizaciones de las monedas.

Libraries: cree una library con las entidades de la transaccion para que la puedan utilizar todos los microservicios

## Flujo de una Transaccion

El flujo que seguiria la transaccion en la aplicacion seria el siguiente:
Se recibe una transaccion mediante un endpoint en el gestor de transacciones.

Se valida que la misma no exista ya, para mantener la idempotencia.

Se cambia el estado de la transaccion a PENDING.

Se llama al servicio de conversion de moendas y se obtiene el monto en la moneda base.

Se guarda la transaccion en la base de datos.

Se envia la transaccion al servicio de validacion de manera asincrona mediante una cola. Mientras tanto el endpoint devuelve un 200

El servicio de validacion lee la transaccion de la cola, la valida seteando su estado a COMPLETED o FAILED segun corresponda, 
y la envia a otra cola de mensajeria de Rabbit.

El gestor de transacciones lee la transaccion de la cola y actualiza su estado en la base de datos.

En caso de que el estado sea FAILED, la envia mediante otra cola de Rabbit al servicio de compensacion.

## Endpoints

La aplicacion cuenta con tres endpoints:

- El primero para iniciar una transaccion:

curl --location 'http://localhost:8080/transacciones' \
--header 'Content-Type: application/json' \
--data '{
"transaccionId": 1,
"tipo": "tarjeta",
"cardId": "43211234",
"usuarioId": "113411",
"monto": 200000,
"moneda": "ARG",
"fecha": "2024-10-15T10:20:00Z",
"mccCode": 5411
}'

El body puede ser el del ejemplo anterior o:
{
"transaccionId": 3,
"tipo": "transferencia",
"usuarioId": "113411",
"monto": 200000,
"moneda": "ARG",
"fecha": "2024-10-15T10:20:00Z",
"bankCode": "123"
}

{
"transaccionId": 4,
"tipo": "p2p",
"usuarioId": "113411",
"monto": 200000,
"moneda": "ARG",
"fecha": "2024-10-15T10:20:00Z",
"emisorId": "123",
"receptorId": "321",
"nota": "asdqwe"
}
 Dependiendo que tipo de transaccion sea.

- El segundo endpoint es para buscar el estado de una transaccion:
  
curl --location 'http://localhost:8080/transacciones/3/estado'

- El tercero es para filtrar transacciones de un usuario:

curl --location 'http://localhost:8080/transacciones/usuario/123?currency=ARG&montoMin=1000&montoMax=100000&orden=desc'

# Otras consideraciones

Me gustaria aclarar algunas cosas que no se llegaron a implementar:

- El servicio de conversion de moendas actualmente toma las cotizaciones de la base de datos. Pero en un
ejemplo dela "vida real" las cotizaciones se deberian obtener mediante un endpoint a una aplicacion
externa. Mi sugerencia seria que en vez de consultar a la app externa cada vez que entra una transaccion,
se consulte cada x cantidad de tiempo y se guarde la cotizacion, entonces cada vez que ingrese una 
transaccion en nuestro sistema ya se tiene "a mano" la cotizacion, sin necesidad de depender de una app externa.
- En el servicio de validacion no se hicieron validaciones reales, pero se deberian validar cosas como si
una transaccion supera cierto monto, solicitar aprobacion del banco o del usuario.
- Lo mismo pasa con el sistema de compensacion, se deberia tal vez cancelar la compra o avisarle al usuario mediante alguna notificacion.
- Hay cierto codigo repetido, como la configuracion de Rabbit, que tal vez se podria manejar desde una library separada
