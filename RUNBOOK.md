# Ejecución local del proyecto

## 1. Infraestructura

Desde PowerShell, en la raíz del proyecto:

```powershell
.\start-infrastructure.ps1
```

## 2. Servicios

Inicia desde IntelliJ en este orden:

1. `DiscoveryServerApplication` — puerto 8761
2. `ConfigServerApplication` — puerto 9000
3. `UserServiceApplication`
4. `ProductServiceApplication` — puerto 9001
5. `AuditServiceApplication` — puerto 9004
6. `ProductQueryServiceApplication` — puerto 9005
7. `CloudGatewayApplication` — puerto 9080
8. `OrderSagaApplication` — puerto 9006

User Service utiliza el puerto `9002`. Product Service utiliza `9001` y Audit Service `9004`; cada servicio tiene una variable de puerto independiente para evitar colisiones en IntelliJ.

Config Server usa por defecto el repositorio local `config-server-files`. Si se ejecuta desde otra carpeta, define `CONFIG_REPO_PATH` con la ruta absoluta de ese directorio.

## Mapa de puertos Java

| Aplicación | Puerto | Variable opcional |
|---|---:|---|
| Discovery Server | 8761 | `DISCOVERY_SERVER_PORT` |
| Authentication Server | 8088 | `AUTH_SERVER_PORT` |
| Authentication Client | 8089 | `AUTH_CLIENT_PORT` |
| Config Server | 9000 | `CONFIG_SERVER_PORT` |
| Product Service | 9001 | `PRODUCT_SERVER_PORT` |
| User Service | 9002 | `USER_SERVER_PORT` |
| Audit Service | 9004 | `AUDIT_SERVER_PORT` |
| Product Query Service | 9005 | `QUERY_SERVER_PORT` |
| Order SAGA Service | 9006 | `SAGA_SERVER_PORT` |
| Client Service | 9020 | `CLIENT_SERVER_PORT` |
| Client Service Ribbon | 9021 | `RIBBON_SERVER_PORT` |
| Cloud Gateway | 9080 | `GATEWAY_SERVER_PORT` |

Los servicios nuevos tienen configuración local predeterminada; Config Server es opcional para ellos.

Las rutas de Product, Product Query y Audit requieren el encabezado:

```http
Authorization: Bearer <access_token>
```

El token se obtiene desde Authentication Server mediante `/oauth/token`. El cliente y secreto predeterminados son `mitocode`/`mitocode` y deben cambiarse fuera del entorno local.

### Keycloak opcional

Levanta Keycloak con:

```powershell
docker compose -f .\docker-files\docker-compose.yml up -d keycloak
```

Configura `SECURITY_PROVIDER=keycloak` antes de iniciar Gateway. El realm importado es `mitocode`, el cliente `microservices-client` y el usuario local de demostración es `mitocode`/`mitocode`. La consola queda en http://localhost:8180.

## 3. Prueba CQRS

Crear un producto (lado de escritura):

```http
POST http://localhost:9080/api/product-service/product
Content-Type: application/json

{
  "productId": "P100",
  "productName": "Curso de Microservicios",
  "productType": "Curso",
  "price": 750,
  "stock": 50
}
```

Consultar el modelo de lectura:

```http
GET http://localhost:9080/api/product-query-service/product-query/P100
```

Consultar auditoría:

```http
GET http://localhost:9080/api/audit-service/audit
```

## Prueba SAGA

```http
POST http://localhost:9080/api/order-saga-service/sagas
Authorization: Bearer <access_token>
Content-Type: application/json

{"productId":"P100","quantity":2}
```

Consulta el estado usando el `sagaId`. Para ejecutar la acción compensatoria:

```http
POST http://localhost:9080/api/order-saga-service/sagas/{sagaId}/compensate
Authorization: Bearer <access_token>
```

## Strangler

`GET /api/catalog` consulta el nuevo modelo CQRS. Para mantener temporalmente un consumidor antiguo, envía `X-Use-Legacy: true` y Gateway lo dirigirá al Product Service original.

## Consolas

- Eureka: http://localhost:8761
- Zipkin: http://localhost:9411
- Prometheus: http://localhost:9090
- Grafana: http://localhost:3000 (`admin` / `admin`)
- Mongo Express: http://localhost:8081

## Verificación del código

```powershell
.\build-all.ps1
```
