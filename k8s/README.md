# Despliegue en AKS

Antes de aplicar el manifiesto, sustituye `IMAGE_REGISTRY` y `IMAGE_TAG` por el Azure Container Registry y la versión creada por Jenkins.

```powershell
az aks get-credentials --resource-group <grupo> --name <cluster>
kubectl apply -f platform.yaml
kubectl rollout status deployment/cloud-gateway -n mitocode
kubectl get services -n mitocode
```

MongoDB y Kafka deben proporcionarse como servicios administrados o desplegarse en el clúster. Sus direcciones se configuran en `microservices-config`.
