# patientapplication-policy.hcl
path "secret/patientapplication/dev" {
  capabilities = ["create", "read", "update", "delete", "list"]
}
path "secret/data/patientapplication/dev" {
  capabilities = ["read"]
}

path "secret/data/patientapplication" {
  capabilities = ["read"]
}

path "secret/metadata/patientapplication/dev" {
  capabilities = ["read", "list"]
}
