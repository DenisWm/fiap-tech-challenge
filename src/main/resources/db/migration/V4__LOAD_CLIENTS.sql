-- Migration script to insert clients into the clients table

INSERT INTO clients (id, name, email, cpf) VALUES
                                               ('1', 'João Silva', 'joao.silva@example.com', '68574374164'),
                                               ('2', 'Maria Oliveira', 'maria.oliveira@example.com', '83632310882'),
                                               ('3', 'Carlos Souza', 'carlos.souza@example.com', '33612407198'),
                                               ('4', 'Ana Pereira', 'ana.pereira@example.com', '33612407198');