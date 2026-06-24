# Grafana con Zipkin

Este bloque deja Grafana conectado a Zipkin como datasource.

## Levantar infraestructura

Desde esta carpeta:

```powershell
cd D:\Estudio\Microservicios\docker-files
```

Si ya tienes Zipkin corriendo en `http://localhost:9411`, levanta solo Grafana y Prometheus:

```powershell
docker compose up -d prometheus grafana
```

Si no tienes Zipkin corriendo, puedes levantarlo tambien desde compose:

```powershell
docker compose up -d zipkin prometheus grafana
```

URLs:

- Zipkin: http://localhost:9411
- Grafana: http://localhost:3000

Credenciales de Grafana:

- Usuario: admin
- Password: admin

## Ver trazas en Grafana

1. Entra a http://localhost:3000.
2. Ve a Explore.
3. Selecciona el datasource Zipkin.
4. Ejecuta una llamada en los microservicios, por ejemplo:
   - http://localhost:9080/api/product-service/product
   - o http://localhost:9020/product si esta levantado client-service.
5. Busca trazas recientes.

Grafana se conecta a Zipkin desde el contenedor de Grafana hacia el host Windows:

```text
http://host.docker.internal:9411
```

Los microservicios siguen enviando trazas a:

```text
http://localhost:9411/api/v2/spans
```