## Tech Challenge FIAP: fase 4
**Objetivo:** separar o monolito Lanchonete-app em microsserviços.

**Microsserviços:** Pedido, Pagamento e Produção.

**Linguagem:** Spring Boot , Java 17.

**Banco de dados:** PostgreSQL

# Microsserviço de Pedido (Order)

Este projeto é um microsserviço de pedido desenvolvido em Java, utilizando Spring Boot. Ele inclui diversas funcionalidades, como integração com postgreSQL e análise de qualidade com SonarQube.

## Sumário

- [Instalação](#instalação)
- [Configuração](#configuração)
- [Dependências](#dependências)
- [Pipelines](#pipelines)
- [Testes](#testes)
- [Deploy](#deploy)
- [Colaboradores](#colaboradores)

## Instalação

1. Clone o repositório:
   ```bash
   git clone https://github.com/DenisWm/fiap-tech-challenge

## Configuração

O projeto está configurado para utilizar Java 17 e Spring Boot. As configurações principais estão definidas nos arquivos build.gradle, incluindo a configuração de plugins como o JaCoCo para relatórios de cobertura de teste e o SonarQube para análise de qualidade.

2. Variáveis de ambiente necessárias:
   ```bash
    export SONAR_TOKEN=your-sonar-token
    export DOCKER_USERNAME=your-docker-username
    export DOCKER_PASSWORD=your-docker-password
    export DISCORD_WEBHOOK_URL=your-discord-webhook-url

## Dependências

As dependências do projeto estão gerenciadas no build.gradle e incluem bibliotecas essenciais como spring-boot-starter-web, spring-boot-starter-data-jpa, spring-boot-starter-amqp e springdoc-openapi. Também são incluídas dependências relacionadas ao postgreSQL e testes (JUnit, Mockito).

## Pipelines

O projeto possui três pipelines principais configurados no GitHub Actions:

* CI/CD Pipeline: Executa testes e análises de qualidade sempre que há um push ou pull request na branch master.

* Terraform Deploy: Provisiona a infraestrutura utilizando Terraform e é acionado após a conclusão bem-sucedida do pipeline CI/CD.

* SonarQube Analysis: Realiza a análise de qualidade do código utilizando SonarQube e é acionado em push ou pull requests.

## Testes

Os testes são configurados e executados utilizando JUnit e Mockito. O Gradle está configurado para gerar relatórios de cobertura com JaCoCo e esses relatórios são enviados para o SonarQube para análise de qualidade.

## Deploy

O deploy é realizado utilizando Docker, onde a imagem é construída e enviada para o Docker Hub. Além disso, a infraestrutura é provisionada utilizando Terraform, que é validado e aplicado automaticamente.

## Colaboradores
## Desenvolvedores do Grupo 30

- Denis William Mamoni

- Gabriela Marques Fernandes Poncet

- Jessica Prado Costa

- Vinícius Saraiva

- Ludionei Reis

---

## Tech Challenge FIAP: fase 01.

**Objetivo:** criar um sistema de controle de pedidos para uma lanchonete do bairro que está se expandindo.

**Funcionalidades:** Cadastro e identificação de clientes, CRUD de produtos, buscar produtos por categoria, listar os pedidos, etc

**Linguagem:** Spring Boot , Java 17.

**Banco de dados:** PostgreSQL.

**Swagger URL:**
http://localhost:8080/swagger-ui/index.html#/ 

**Requisitos:**
- Clonar o projeto localmente;
- Ter o docker instalado na máquina;
- Digitar no terminal dentro do projeto: docker compose up.

## Tech Challenge FIAP: fase 02 - Kubernetes e Clean Architecture

**Objetivo:** Escalabilidade com aumento e diminuição de Pods conforme demanda

**Funcionalidades:** Deployments, Services, Configmaps, Secrets, PV, PVC, HPA, EFK (monitoramento de logs) e Kube-state-metrics (métricas monitoradas pelo Prometheus e Grafana).

**Apresentação em vídeo:** 
https://www.youtube.com/watch?v=zwh09buDOxM

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

## Tech Challenge FIAP: fase 03 - Distribuição da aplicação

**CI/CD Pipeline - Application**

Este repositório contém o pipeline de CI/CD para a aplicação lanchonete-app, utilizando GitHub Actions para automatizar os processos de build, teste e deploy.

**Visão Geral**

Este pipeline de CI/CD automatiza o processo de build, teste e deploy da aplicação lanchonete-app. Ele é acionado em commits na branch master e em pull requests para a mesma branch.

**Estrutura do Pipeline**

O pipeline é dividido em três etapas principais:

- Build: Compila a aplicação.

- Test: Executa testes unitários e gera relatórios de cobertura de código.

- Deploy: Constrói e publica a imagem Docker, e faz o deploy da aplicação no Kubernetes.

**Detalhamento das Etapas**

**Build**
  - Ação: actions/checkout@v2
    - Descrição: Faz checkout do código fonte.

  - Permissões para o Gradle Wrapper:
    - Comando: `chmod +x ./gradlew`
  
  - Configuração do JDK:
    - Ação: actions/setup-java@v2
    - Distribuição: adopt
    - Versão: 17

  - Compilação com Gradle:
    - Comando: `./gradlew build --no-daemon`

**Test**
  - Ação: actions/checkout@v2
    - Descrição: Faz checkout do código fonte.

  - Permissões para o Gradle Wrapper:
    - Comando: `chmod +x ./gradlew`
  
  - Configuração do JDK:
    - Ação: actions/setup-java@v2
    - Distribuição: adopt
    - Versão: 17
  
  - Execução de Testes Unitários:
    - Comando: `./gradlew test jacocoTestReport`
  
  - Upload do Relatório JaCoCo:
    - Ação: actions/upload-artifact@v4
    - Nome: jacoco-report
    - Caminho: build/reports/jacoco/test/html/
  
  - Verificação do Quality Gate:
    - Comando: `./gradlew check`
    - Continua em caso de erro: continue-on-error: true

**Deploy**
  - Ação: actions/checkout@v2
    - Descrição: Faz checkout do código fonte.

  - Construção da Imagem Docker:
    - Comando: `docker build -t gabiponcet/lanchonete-app:latest .`

  - Login no Docker Hub:
    - Ação: docker/login-action@v1
    - Username: ${{ secrets.DOCKER_USERNAME }}
    - Password: ${{ secrets.DOCKER_PASSWORD }}

  - Publicação da Imagem Docker:
    - Comando: `docker push gabiponcet/lanchonete-app:latest`

  - Instalação do LocalStack CLI:
    - Comando: `pip install localstack`

  - Início do LocalStack com Docker:
    - Comandos:
      `export LAMBDA_EXECUTOR=docker
      localstack start -d`

  - Variáveis de Ambiente:
    `LAMBDA_EXECUTOR: docker`

  - Instalação do kubectl:
    - Comandos:
      `curl -LO "https://storage.googleapis.com/kubernetes-release/release/$(curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt)/bin/linux/amd64/kubectl"
      chmod +x ./kubectl
      sudo mv ./kubectl /usr/local/bin/kubectl`

  - Instalação do Minikube:
    - Comandos:
      `curl -LO https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64
      sudo install minikube-linux-amd64 /usr/local/bin/minikube`

  - Início do Minikube:
    - Comandos:
      `minikube start --driver=docker --force
      sudo chown -R $USER $HOME/.kube $HOME/.minikube
      sudo chgrp -R $USER $HOME/.kube $HOME/.minikube
      export KUBECONFIG=$HOME/.kube/config`

  - Deploy no Kubernetes:
    - Comando: `kubectl apply -R -f k8s/`

  - Rollback de Componentes Específicos:
    - Condição: `if: failure()`
    - Comandos:
      `kubectl rollout undo deployment lanchonete-app
      kubectl rollout undo deployment db`
  - Variáveis de Ambiente:
    `KUBECONFIG: ${{ secrets.KUBECONFIG }}`

**Notificações no Discord**

Ao final de cada etapa do pipeline, notificações são enviadas ao Server no Discord. 

- Exemplo de mensagens sinalizando o status da etapa de deploy:

  - Notificação de Sucesso no Discord:
    - Condição: `if: success()`
    - Comando:
      `curl -X POST \
        -H 'Content-type: application/json' \
        --data '{"content":"✅ O deploy foi concluído com sucesso!"}' \
        ${{ secrets.DISCORD_WEBHOOK_URL }}`

  - Notificação de Falha no Discord:
    - Condição: `if: failure()`
    - Comando:
      `curl -X POST \
        -H 'Content-type: application/json' \
        --data '{"content":"⚠️ O deploy falhou! Verifique os logs para mais detalhes."}' \
        ${{ secrets.DISCORD_WEBHOOK_URL }}`
      
  - Conclusão
    
Este pipeline de CI/CD garante um processo contínuo de integração e entrega para a aplicação lanchonete-app, desde a construção do código até o deploy em ambiente Kubernetes. As etapas de rollback e notificação ajudam a garantir que qualquer problema seja rapidamente identificado e tratado.

---
