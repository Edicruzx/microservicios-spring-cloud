# Jenkins: microservicios hacia ACR y AKS

Este flujo deja el proyecto a la par con las practicas de los videos 67 y 68:

1. Jenkins descarga el codigo desde GitHub.
2. El Dockerfile multietapa usa Maven dentro de Docker para compilar
   `discovery-server` o `config-server`.
3. Docker construye una imagen versionada con el numero del build.
4. Jenkins inicia sesion en Azure Container Registry (ACR) y publica la imagen.
5. `kubectl` actualiza el Deployment en Azure Kubernetes Service (AKS).
6. Jenkins espera el rollout y muestra los pods y Services resultantes.

## Credenciales requeridas

En **Administrar Jenkins > Credentials > System > Global** deben existir:

- `acr-mitocode`: tipo **Username with password**, con el usuario administrador de ACR.
- `kubeconfig-mitocode`: tipo **Secret file**, con el kubeconfig de AKS.

Estas credenciales ya se referencian en `Jenkinsfile.azure`; nunca deben
guardarse en GitHub. Para produccion se recomienda reemplazarlas por una
identidad administrada con permisos minimos.

## Configurar el job

En el job de Jenkins selecciona **Configure** y usa:

- Definition: **Pipeline script from SCM**
- SCM: **Git**
- Repository URL: `https://github.com/Edicruzx/microservicios-spring-cloud.git`
- Branch Specifier: `*/main`
- Script Path: `Jenkinsfile.azure`

Guarda el job. La primera ejecucion registra los parametros; las siguientes se
lanzan desde **Build with Parameters**.

## Parametros

- `SERVICE_NAME`: elige `discovery-server` o `config-server`.
- `VIDEO_DEMO_MODE=false`: modo recomendado. Mantiene Services internos,
  una replica de Eureka y dos de Config Server.
- `VIDEO_DEMO_MODE=true`: replica el video. Crea tres replicas de Eureka,
  cinco de Config Server y un Service publico `LoadBalancer` para cada uno.

Ejecuta primero `discovery-server` y despues `config-server`, porque Config
Server se registra en Eureka mediante el nombre Kubernetes
`http://discovery-server:8761/eureka/`.

## Verificacion

```cmd
kubectl get deployment,pods,service --namespace mitocode
kubectl rollout status deployment/discovery-server --namespace mitocode
kubectl rollout status deployment/config-server --namespace mitocode
```

Modo seguro, acceso local:

```cmd
kubectl port-forward service/discovery-server 18761:8761 --namespace mitocode
kubectl port-forward service/config-server 19000:9000 --namespace mitocode
```

Abre `http://localhost:18761`. Eureka debe mostrar `CONFIG-SERVER` después de
que este termine de iniciar. Para comprobar Config Server abre
`http://localhost:19000/product-service/desa`.

En modo demostracion, espera la IP publica:

```cmd
kubectl get service --namespace mitocode --watch
```

Al terminar la exposicion, vuelve a ejecutar ambos servicios con
`VIDEO_DEMO_MODE=false`; el pipeline elimina los LoadBalancer publicos.

## Costos

AKS y los LoadBalancer pueden consumir credito mientras existan. Cuando toda la
practica haya terminado y ya no necesites ningun recurso del grupo:

```cmd
az group delete --name rg-mitocode-dev --yes --no-wait
```
