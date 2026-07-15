# Jenkins: Discovery Server hacia ACR y AKS

Este flujo automatiza el servicio que ya fue validado manualmente:

1. Jenkins construye la imagen con el `Dockerfile` de la raiz.
2. Publica `discovery-server:<BUILD_NUMBER>` en ACR.
3. Aplica el manifiesto Kubernetes.
4. Actualiza el Deployment y espera que el rollout termine.

## Requisitos del agente Jenkins

El agente que ejecuta el pipeline debe ser Windows y tener disponibles en su
`PATH` los comandos `docker` y `kubectl`. Docker Desktop debe estar iniciado.

## Credencial de ACR

Para esta demostracion se puede habilitar temporalmente el usuario administrador:

```cmd
az acr update --name mitocodeacr9986 --admin-enabled true
az acr credential show --name mitocodeacr9986
```

En Jenkins, crea una credencial global de tipo **Username with password**:

- ID: `acr-mitocode`
- Username: el valor `username` retornado por Azure.
- Password: uno de los valores de `passwords` retornados por Azure.

No copies estas claves al repositorio ni a capturas de pantalla.

## Credencial de AKS

Genera un kubeconfig administrativo temporal fuera del repositorio:

```cmd
az aks get-credentials --resource-group rg-mitocode-dev --name aks-mitocode-dev --admin --file "%TEMP%\aks-mitocode-dev-admin.yaml" --overwrite-existing
```

En Jenkins, crea una credencial global de tipo **Secret file**:

- ID: `kubeconfig-mitocode`
- File: `%TEMP%\aks-mitocode-dev-admin.yaml`

El kubeconfig administrativo es apropiado solo para esta demostracion. En un
entorno productivo debe reemplazarse por una identidad con permisos minimos.

## Crear el pipeline

1. Crea un nuevo elemento de tipo **Pipeline**.
2. Usa **Pipeline script from SCM** si el repositorio esta en Git.
3. Configura `Jenkinsfile.azure` como **Script Path**.
4. Ejecuta **Build Now**.

La imagen publicada quedara versionada con el numero de build de Jenkins.

## Verificacion

```cmd
kubectl get deployment,pods,service --namespace mitocode
kubectl describe deployment discovery-server --namespace mitocode
```

Para abrir Eureka localmente:

```cmd
kubectl port-forward service/discovery-server 18761:8761 --namespace mitocode
```

## Cierre de la demostracion

AKS consume credito mientras el nodo existe. Cuando termine toda la practica:

```cmd
az group delete --name rg-mitocode-dev --yes --no-wait
```
