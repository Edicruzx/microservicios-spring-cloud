# Manifiestos Kubernetes para AKS

Los manifiestos usados por `Jenkinsfile.azure` son plantillas:

- `discovery-server.yaml`: Deployment y Service interno de Eureka.
- `config-server.yaml`: Deployment y Service interno de Config Server.
- `public-service.yaml`: LoadBalancer opcional para la demostracion.
- `platform.yaml`: plataforma completa experimental; no la usa este pipeline.

Jenkins reemplaza `IMAGE_REGISTRY`, `IMAGE_TAG` y `REPLICA_COUNT` antes de
aplicar cada plantilla. Los archivos `*.generated.yaml` se generan durante el
build y estan excluidos de Git.

Para Config Server, el pipeline convierte la carpeta `config-server-files` en
un ConfigMap y la monta en `/app/config-server-files`. El servicio se conecta a
Eureka por DNS interno de Kubernetes:

```text
http://discovery-server:8761/eureka/
```

Comandos de diagnostico:

```cmd
kubectl get deployment,pods,service,configmap --namespace mitocode
kubectl describe deployment config-server --namespace mitocode
kubectl logs deployment/config-server --namespace mitocode --tail=100
```

MongoDB, Kafka, Keycloak y los microservicios de negocio siguen siendo una
etapa posterior; estos manifiestos cubren los dos servicios desplegados en los
videos 67 y 68.
