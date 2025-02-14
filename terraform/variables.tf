variable "kubeconfig_path" {
  description = "Caminho para o arquivo de configuração kubeconfig"
  type        = string
  default     = "~/.kube/config"
}