**Tech Challenge FIAP: fase 02 - Kubernetes**

**Objetivo:** Escalabilidade com aumento e diminuição de Pods conforme demanda

**Funcionalidades:** Deployments, Services, Configmaps, Secrets, PV, PVC, HPA, EFK (monitoramento de logs) e Kube-state-metrics (métricas monitoradas pelo Prometheus e Grafana)

**Requisitos:**
- Clonar o projeto localmente;
- Ter o docker instalado na máquina;
- Ter o minikube instalado e devidamente configurado
- ter o prometheus e o grafana devidamente instalados
- Por utilizar o minikube como host, deve-se executar os seguintes passos:

1 - docker compose build
2 - docker compose up db -d
3 - Build da aplicação deve ser feito pelo FiapTechChallengeApplication.java

**Testes de Carga**

- verificar se o metrics está disponível => kubectl get deployment metrics-server -n kube-system
- aplicar os arquivos da pasta k8s (não aplicar os da pasta EFK e KSM): verificar se o metrics está de pé - kubectl apply -f .\k8s\  
- Aguardar os pods estarem estáveis
- Executar os arquivos da pasta KSM:  kubectl apply -f .\k8s\KSM
- Executar no terminal: kubectl port-forward -n kube-system svc/kube-state-metrics 8081:8080
- Iniciar o Prometheus e o Grafana
- verifique a saúde dos Targets em http://localhost:9090/targets
- acompanhe seus dahboard no grafana: http://localhost:3000
- Aplicar o hpa: kubectl apply -f .\k8s\lanchonete-app-hpa.yaml 
- executar o teste de carga presente em src/test: k6 run load-test.js

**Coleta e Monitoramento de logs com EFK**

- aplicar os arquivos da pasta k8s (não aplicar os da pasta EFK e KSM)
- Aguardar os pods estarem estáveis
- Executar os arquivos da pasta EFK:  kubectl apply -f .\k8s\EFK
- Execute no terminal: kubectl port-forward svc/kibana 5601:5601
- Quando os pods do EFK estiverem estáveis, monitore os logs nos gráficos que você configurou em http://localhost:5601
