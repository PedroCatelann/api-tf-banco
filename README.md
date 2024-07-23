## Bem vindo ao projeto api-tf-banco 🎆

**Configuração inicial git (só precisa ser feita uma vez) :**

```bash
    git config --global init.defaultBranch dev
    git config --global user.name "Fulano de Tal"
    git config --global user.email fulanodetal@exemplo.br
```

**Adicionando repositório git:**

```bash
    git init .
    git remote add origin <url do repositório git>
    git add .
    git commit -m "Primeiro commit"
    git push -u origin dev
```


**Especificando profile**

Para ajustar o profile basta adicionar a variável de ambiente abaixo no seu editor:

```sh
ARCH_ENVIRONMENTS=dev
```

**Documentos úteis:**

- [Setup do ambiente Java](http://docs.dev.autbank.net/arch-java/setup-environment-java/)
- [Documentação de desenvolvimento](http://docs.dev.autbank.net/arch-java/)
- [Documentação de CI/CD](http://docs.dev.autbank.net/pipelines-ci-cd/)
- [Documentação para criação de remessas](http://docs.dev.autbank.net/remessas/)
- [Lista de itens de infra (MSSQL, Postgres, redis, kafka...)](http://docs.dev.autbank.net/ambiente-dev/infraestrutura/)
