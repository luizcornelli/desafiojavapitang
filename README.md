# desafiojavapitang

Desafio técnico Java Pitang

# Sobre o desafio

O desafio consiste em criar uma aplicação responsável por gerenciar usuários e carros

# Ferramentas utilizadas

1. Spring boot
2. Banco de dados H2
3. Java versão 11
4. Docker
5. EC2 AWS e ECR AWS
6. Jenkis
7. Swagger
8. Testes unitários dos contratos da API usando MockMvc

# Testes

1. Para fazer os testes em Produção deve-se acessar o Link da documentação [Swagger](http://ec2-35-153-66-185.compute-1.amazonaws.com:8081/swagger-ui/index.html) 
2. Para executar o projeto localmente basta clonar o projeto e importar na sua IDE de preferencia, subir a aplicação e acessar localhost na porta 8081 e o [Swagger](http://localhost:8081/swagger-ui/index.html) 
3. Para executar os testes unitários é preciso fazer o passo do item 2 e clicar com o botão direito **src > main > test**. 

# Histórias de usuário

1. Gestão de Usuários:

  * DJP1 - Eu, enquanto Usuário não autenticado quero catalogar informações sobre usuários para que eu possa acessar posteriormente
   
  * DJP2 - Eu, enquanto Usuário não autenticado quero ser capaz de ver uma lista de usuários para que eu possa ter um maior controle sobre usuários cadastrados
   
  * DJP3 - Eu, enquanto Usuário não autenticado quero ser capaz de atualizar informações dos usuários para corrigir erros ou atualizar detalhes
   
  * DJP4 - Eu, enquanto Usuário não autenticado quero ser capaz de remover informações de usuários que não são mais relevantes
   
  * DJP5 - Eu, enquanto Usuário registrado e não autenticado, eu quero poder fazer login no sistema usando meu login de usuário e senha
   
  * DJP6 - Eu, enquanto Usuário não autenticado quero ser capaz de pesquisar informações de um usuário para que eu possa ter um maior controle sobre este usuário cadastrado
   
  * DJP6 - Eu, enquanto Usuário registrado e autenticado quero catalogar informações sobre meus automoveis para que eu possa acessar posteriormente
   
  * DJP7 - Eu, enquanto Usuário registrado e autenticado quero ser capaz de ver uma lista de detalhes dos meus automoveis para que eu possa ter um maior controle sobre meus carros
   
  * DJP8 - Eu, enquanto Usuário registrado e autenticado quero ser capaz de atualizar informações dos meus automoveis para corrigir erros ou atualizar detalhes
   
  * DJP9 - Eu, enquanto Usuário registrado e autenticado quero ser capaz de remover informações de automoveis que não são mais relevantes
   
  * DJP10 - Eu, enquanto Usuário registrado e autenticado, eu quero poder vizualizar minhas informações de login no sistema
   
  * DJP11 - Eu, enquanto Usuário registrado e autenticado quero ser capaz de pesquisar informações de um automovel para que eu possa ter um maior controle sobre este carro

# Solução

* **Spring Java e H2 Database:**

	Justificativa:
	
	O Spring Framework é uma escolha sólida para desenvolvimento Java, fornecendo uma arquitetura robusta e modular para construir aplicativos escaláveis e eficientes.
	O banco de dados H2 é uma excelente escolha para ambientes de desenvolvimento e teste, proporcionando alta performance e sendo totalmente compatível com padrões SQL.
	
	Defesa Técnica:
	
	O Spring Java é amplamente utilizado na comunidade de desenvolvimento Java devido à sua facilidade de uso, flexibilidade e suporte extensivo à integração de diferentes componentes.
	O H2 Database é fácil de configurar e usar, permitindo aos desenvolvedores testar e validar seus aplicativos de forma eficiente antes de escaloná-los para bancos de dados mais robustos em produção.

* **Pipeline Jenkins:**

  	Justificativa:

	Um pipeline automatizado é essencial para implementação contínua e integração contínua (CI/CD). O Jenkins oferece uma maneira poderosa e flexível de configurar pipelines para automatizar o processo de 		construção, teste e implantação de aplicativos.

  	Defesa Técnica:
	
	O Jenkins oferece uma vasta gama de plugins que permitem integração perfeita com várias ferramentas e serviços, garantindo um pipeline altamente personalizável.
	Pipelines automatizados reduzem erros humanos, aumentam a eficiência e garantem que as atualizações de código sejam rapidamente validadas e implantadas, proporcionando uma experiência de desenvolvimento ágil.

* **Amazon EC2 e Amazon ECR:**

  	Justificativa:
	
	A Amazon Web Services (AWS) é uma das principais provedoras de serviços em nuvem, oferecendo escalabilidade, confiabilidade e segurança líderes do setor.
	O Amazon EC2 (Elastic Compute Cloud) fornece instâncias virtuais escaláveis e o Amazon ECR (Elastic Container Registry) oferece um repositório seguro para imagens de contêiner Docker.

	Defesa Técnica:
	
	Usar o EC2 e o ECR da AWS garante alta disponibilidade e escalabilidade automática, permitindo que a infraestrutura seja dimensionada facilmente para atender às demandas do aplicativo.
	O ECR proporciona uma maneira segura e fácil de gerenciar imagens de contêiner Docker, facilitando a implantação de aplicativos em um ambiente altamente controlado e confiável.

* **Docker:**

  	Justificativa:

	O Docker é uma tecnologia de contêiner amplamente adotada que permite empacotar um aplicativo e todas as suas dependências em um contêiner isolado, garantindo consistência e portabilidade em diferentes 	 
 	ambientes.

	Defesa Técnica:
	
	Os contêineres Docker oferecem isolamento de recursos, garantindo que o aplicativo funcione da mesma maneira, independentemente do ambiente em que é executado.
	A portabilidade dos contêineres Docker facilita a implantação consistente do aplicativo em qualquer ambiente que suporte Docker, seja localmente em um ambiente de desenvolvimento ou em servidores de produção.

* **Swagger:**

  	Justificativa:

	O Swagger é uma ferramenta poderosa para documentação de API, permitindo que os desenvolvedores visualizem, interajam e testem chamadas de API diretamente pela interface Swagger.

	Defesa Técnica:
	
	A documentação clara e interativa da API é fundamental para facilitar a integração de serviços e para que outros desenvolvedores entendam como interagir com o sistema.
	O Swagger também simplifica a validação e os testes das APIs, melhorando a eficiência do processo de desenvolvimento e garantindo a qualidade do código.

* **Testes unittários:**

	Justificativa:
	
	Testes unitários são essenciais para garantir a funcionalidade correta de componentes individuais do código, fornecendo feedback imediato aos desenvolvedores sobre possíveis problemas.

	Defesa Técnica:
	
	Testes unitários garantem que partes específicas do código funcionem conforme esperado, evitando regressões e reduzindo a probabilidade de bugs em estágios posteriores do desenvolvimento.
	Além disso, eles facilitam a refatoração, melhoram a manutenibilidade e proporcionam confiança aos desenvolvedores, sabendo que suas alterações não prejudicam outras partes do sistema.
   
