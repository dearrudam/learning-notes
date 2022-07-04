---
title: [PT-BR] Utilizando Docker no dia a dia
description: 
tags: [docker, beginners, tutorial, braziliandevs]
language: PT-BR
cover_image:
series: 
published: true
publish_date: 
---
# [PT-BR] Utilizando Docker no dia a dia

Olá, tudo bem? Estou tentando aprender como compartilhar o pouco do meu conhecimento afim de aprender muito mais junto com vocês que até aqui chegaram, então, não hesitem em comentar ok? Seu feedback será de muito importante (nem que seja pra mim parar de compartilhar, ok? 😜).

Nesse primeiro artigo tentarei ser direto no que diz respeito a utilização do docker do ponto de vista de um mero desenvolvedor de sistemas, o meu!!!

Soluções como banco de dados em memória (derby, h2, entre outros), ou "mocks" de um serviços externo (usando bibliotecas como o Mockito ou soluções como o Wiremock, por exemplo) sempre foram uma saída durante o desenvolvimento de alguns trabalhos até que conheci o Docker. Não que eu simplesmente substitui esses itens do meu "cinto de utilidades" pelo Docker, até porque não acredito em "bala de prata" e sempre tem o bendito "depende" que tenta trazer a lucidez para nós que estamos trabalhando nesse mundo vasto de tecnologias que evoluem a cada dia.

Outra coisa interessante é a possibilidade de testar algo sendo executado talvez em um S.O. totalmente diferente do que você está desenvolvendo em questões de segundos ao invés de esperar o time de infra da sua empresa disponibilizar um ambiente de DEV para aquele cara que você precisa "trocar figurinhas". (Quem nunca esperou dias por isso não vai entender o que estou falando...kkkkk). 

Bom, chega de papo! Bora ver alguns comandos Docker!

Para mais detalhes sobre Docker, dê uma olhada na [documentação] (https://docs.docker.com/get-started/overview/)!


## Listar todos comandos disponíveis do Docker:

```bash
$ docker --help
```

## Visualizar mais informações de um dado comando do Docker:

```bash
$ docker <comando> --help
```

## Qual versão estou utilizando?

```bash
$ docker --version
Docker version 19.03.13, build 4484c46d9d
```

## Como saber quais são as imagens que tenho disponível no meu ambiente local?

```bash
$ docker images
```

## Onde posso procurar imagens Docker?

Eu costumo procurar no [Docker Hub](https://hub.docker.com/). 

## Legal, achei a imagem que quero executar, e agora? como faço?

Por exemplo, digamos que eu queira baixar uma imagem com um banco de dados Postgres, então o nome da imagem será "postgres".

As imagens docker detém um controle de versionamento que são ficam relacionadas através de "tags".

Por definição, caso nenhuma "tag" for informada, a versão "latest" será utilizada. Essa é a versão mais atual de uma dada imagem. 

Então, dependendo da imagem que você estiver procurando, será possível ter várias "tags" relacionando várias versões.

Há duas formas de adquirir imagens Docker: baixando pelo comando "docker pull" ou pelo comando "docker run". 

### Baixando imagem Docker:

```bash
$ docker pull <imagem:tag>
```

Olha eu baixando imagem Docker do Postgres:

```bash
$ docker pull postgres

ou

$ docker pull postgres:latest

```

### Como apago imagens do meu ambiente local?

Opa! Espero que tenha certeza que queira realmente realizar esse comando hein? Vai que depois você não consiga a imagem novamente... 

Caso for isso mesmo que queira fazer, não esqueça de parar os containers que estão rodando com a dada imagem, ou então use a "força bruta" usando a flag "-f".

```bash
$ docker rmi postgres
```

### Como apago imagens que não estou utilizando do meu ambiente local?

Cuidado aí camarada! Vai apagar imagem que não deve, hein?

```bash
$ docker image prune
WARNING! This will remove all dangling images.
Are you sure you want to continue? [y/N] y
Deleted Images:
deleted: sha256:817f2d3d51eccb32a160cecca3b6bb95de017810498f1bb0a1d7627f59e3c5f9
deleted: sha256:f129d29273cc0b7aa597502f4dce816fbf219e186cc4d3469084574ec3add5ba
deleted: sha256:2afe8460604b177f834f0be1baacce9bbaf8b76dab822f9707154f317a106fc9
deleted: sha256:48a572b0b067213717f89cc0c69b2ed07f298e39eda2547de03e70b8ee5a7c38
deleted: sha256:8b502c3465d294c115f3362df39177b6c4126478773f6fc67a9cafbb2e2b6981
deleted: sha256:cf507dd0064138ab6667763c156a1faba8f62cc73a0d20d515096e0f9e874826
deleted: sha256:d52eacba2f4b050fb5cf846f120795cce83c70aa312c3928c1a9d1ae193580e1
deleted: sha256:35c1acf0e0075ecea7e1650b9ee37c4a2e119070ce6fcc8f82cd2f4a8a061eb1
deleted: sha256:5fed32dbbbd219cf0d7a3a3b372f1005f0132705c05a15e4a972e35d8bbdeed6
deleted: sha256:c91270ef2ff80bc858249a6a70904419b3da6c88faf68ebcc405e67fe579ddc6
deleted: sha256:93e69cdd16ed03440a0783cd1ff6892d3d7faf795315992726f5ea0977e35d8d
deleted: sha256:8b83b573f5dec4d579291deef5be1eccd0bf79ef41ea2e26961b5472b9f3fc75
deleted: sha256:9d9d3f04875ea561242877a370c072fd1fe1376004b4aa4967d0f1d1abdd1215
deleted: sha256:7c87e41d03431fdde0e13603d09a9276a945e4e7a79e34cd1019078d58b71c1d
deleted: sha256:07cab433985205f29909739f511777a810f4a9aff486355b71308bb654cdc868
```

### Executando uma imagem Docker

O comando de execução é o "docker run". Ele é cheio de parâmetros e vale dar uma olhada no help do comando para melhor detalhamento:

```bash
$ docker run --help
```

Não se desespere com a quantidade de parâmetros! 

Normalmente os mantenedores das imagens constumam documentar como configurar e iniciar o container.

No nosso exemplo, vamos inicializar um container com a imagem Postgres:

```bash
$ $ docker run --name some-postgres -e POSTGRES_PASSWORD=mysecretpassword -d postgres
Unable to find image 'postgres:latest' locally
latest: Pulling from library/postgres
d121f8d1c412: Pull complete 
9f045f1653de: Pull complete 
fa0c0f0a5534: Pull complete 
54e26c2eb3f1: Pull complete 
cede939c738e: Pull complete 
69f99b2ba105: Pull complete 
218ae2bec541: Pull complete 
70a48a74e7cf: Pull complete 
c0159b3d9418: Pull complete 
353f31fdef75: Pull complete 
03d73272c393: Pull complete 
8f89a54571bf: Pull complete 
4885714928b5: Pull complete 
3060b8f258ec: Pull complete 
Digest: sha256:0171a93d62342d2ab2461069609175674d2a1809a1ad7ce9ba141e2c09db1156
Status: Downloaded newer image for postgres:latest
2cb420e36c116f72df282bc22920d04855b6e0ada9fe23b89d05747af3062829
```

### Mas que parada é essa de "Pulling from ..." ?

Isso acontece quando é passado no comando de execução uma imagem que não está disponível no seu cache local do seu ambiente, resumindo, o docker vai tentar realizar o download da imagem necessária. A partir da próxima executação, esse passo não irá acontecer! (Ufa!!!) 

## Quais containers estão rodando?

```bash
$ docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS               NAMES
2cb420e36c11        postgres            "docker-entrypoint.s…"   26 seconds ago      Up 9 seconds        5432/tcp            some-postgres
```

Caso queira visualizar tanto os containers executando como os parados, basta adicionar a flag "-a".

## Quero parar meus containers, como faço?

Pegue os "ID"s ou os "NAMES" dos containers em questão e execute o seguinte comando:

Detalhe: é possível parar vários containers em um mesmo comando, basta passar tanto o id quanto o nome do container como argumentos conforme abaixo:

```bash
$ docker stop <CONTAINER ID> <CONTAINER NAME> <CONTAINER ID>
```

Exemplo:

```bash
$ docker ps -a
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS               NAMES
757c0dd2d3dc        postgres            "docker-entrypoint.s…"   8 seconds ago       Up 5 seconds        5432/tcp            some-postgres-other
b3f3199c2ef4        postgres            "docker-entrypoint.s…"   25 seconds ago      Up 22 seconds       5432/tcp            some-postgres

$ docker stop 757c0dd2d3dc some-postgres
757c0dd2d3dc
some-postgres
```

## Agora quero remover meus containers, e aí?

Detalhe: é possível remover containers em um mesmo comando, basta passar tanto o id quanto o nome do container conforme abaixo:

Outra coisa, caso for isso mesmo que queira fazer, não esqueça de parar os containers que estão rodando com a dada imagem, ou então use a "força bruta" usando a flag "-f".

Pegue os IDs ou os "NAMES" dos containers em questão e execute o seguinte comando:

```bash
$ docker rm <CONTAINER ID> <CONTAINER NAME> <CONTAINER ID>
```

Exemplo:

```bash
$ docker ps -a
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS               NAMES
757c0dd2d3dc        postgres            "docker-entrypoint.s…"   8 seconds ago       Up 5 seconds        5432/tcp            some-postgres-other
b3f3199c2ef4        postgres            "docker-entrypoint.s…"   25 seconds ago      Up 22 seconds       5432/tcp            some-postgres

$ docker rm -f 757c0dd2d3dc some-postgres
757c0dd2d3dc
some-postgres
```

## Legal, após subir um container, como acessá-lo?

Quando o Docker é instalado, por padrão ele cria 3 redes: "bridge", "none", and "host". Por padrão ele utiliza a rede "bridge" para conectar os containers com o host.

Você pode até criar uma rede customizada, mas isso está fora do contexto desse artigo! heheh  

### Visualizar redes configuradas no Docker:

```bash
$ docker network ls
NETWORK ID          NAME                DRIVER              SCOPE
93ec2c8226b5        bridge              bridge              local
a2cd7ddae279        host                host                local
518332f330ee        none                null                local
```

### Inspecionando uma configuração de rede:

```bash
$ docker network <NETWORK ID or NAME>
```

Exemplo: visualizando a rede "bridge":

```bash
$ docker network inspect bridge
[
    {
        "Name": "bridge",
        "Id": "93ec2c8226b57b9eb6080e9580d68247a7a109174b14a78041820aa27772a2d2",
        "Created": "2020-10-02T10:02:24.548224386-03:00",
        "Scope": "local",
        "Driver": "bridge",
        "EnableIPv6": false,
        "IPAM": {
            "Driver": "default",
            "Options": null,
            "Config": [
                {
                    "Subnet": "172.17.0.0/16",
                    "Gateway": "172.17.0.1"
                }
            ]
        },
        "Internal": false,
        "Attachable": false,
        "Ingress": false,
        "ConfigFrom": {
            "Network": ""
        },
        "ConfigOnly": false,
        "Containers": {
            "eec4785fe9d22b9abb1aa78f3dda6f36de3b988ec6b8f6ac08428481f1e64e61": {
                "Name": "some-postgres",
                "EndpointID": "d2d60a278eb15177ed2dbbb393b19b93a80ebb6a16693ba820221d252822d5e9",
                "MacAddress": "02:42:ac:11:00:02",
                "IPv4Address": "172.17.0.2/16",
                "IPv6Address": ""
            }
        },
        "Options": {
            "com.docker.network.bridge.default_bridge": "true",
            "com.docker.network.bridge.enable_icc": "true",
            "com.docker.network.bridge.enable_ip_masquerade": "true",
            "com.docker.network.bridge.host_binding_ipv4": "0.0.0.0",
            "com.docker.network.bridge.name": "docker0",
            "com.docker.network.driver.mtu": "1500"
        },
        "Labels": {}
    }
]
```

## Acessando o container

Inspecione as configurações de rede do container que quer acessar pelo comando:

```bash
$ docker container inspect <CONTAINER ID or CONTAINER NAME>
```

Assim você tera certeza de qual o endereço de IP e porta que o container está expondo:

Exemplo:

```bash
$ docker container inspect some-postgres
[
    {
        "Id": "eec4785fe9d22b9abb1aa78f3dda6f36de3b988ec6b8f6ac08428481f1e64e61",
        "Created": "2020-10-06T06:32:28.041545362Z",
        "Path": "docker-entrypoint.sh",
        "Args": [
            "postgres"
        ],
        "State": {
            "Status": "running",
            "Running": true,
            "Paused": false,
            "Restarting": false,
            "OOMKilled": false,
            "Dead": false,
            "Pid": 161922,
            "ExitCode": 0,
            "Error": "",
            "StartedAt": "2020-10-06T06:32:33.738235112Z",
            "FinishedAt": "0001-01-01T00:00:00Z"
        },
        "Image": "sha256:817f2d3d51eccb32a160cecca3b6bb95de017810498f1bb0a1d7627f59e3c5f9",
        "ResolvConfPath": "/var/lib/docker/containers/eec4785fe9d22b9abb1aa78f3dda6f36de3b988ec6b8f6ac08428481f1e64e61/resolv.conf",
        "HostnamePath": "/var/lib/docker/containers/eec4785fe9d22b9abb1aa78f3dda6f36de3b988ec6b8f6ac08428481f1e64e61/hostname",
        "HostsPath": "/var/lib/docker/containers/eec4785fe9d22b9abb1aa78f3dda6f36de3b988ec6b8f6ac08428481f1e64e61/hosts",
        "LogPath": "/var/lib/docker/containers/eec4785fe9d22b9abb1aa78f3dda6f36de3b988ec6b8f6ac08428481f1e64e61/eec4785fe9d22b9abb1aa78f3dda6f36de3b988ec6b8f6ac08428481f1e64e61-json.log",
        "Name": "/some-postgres",
        "RestartCount": 0,
        "Driver": "overlay2",
        "Platform": "linux",
        "MountLabel": "",
        "ProcessLabel": "",
        "AppArmorProfile": "docker-default",
        "ExecIDs": null,
        "HostConfig": {
            "Binds": null,
            "ContainerIDFile": "",
            "LogConfig": {
                "Type": "json-file",
                "Config": {}
            },
            "NetworkMode": "default",
            "PortBindings": {},
            "RestartPolicy": {
                "Name": "no",
                "MaximumRetryCount": 0
            },
            "AutoRemove": false,
            "VolumeDriver": "",
            "VolumesFrom": null,
            "CapAdd": null,
            "CapDrop": null,
            "Capabilities": null,
            "Dns": [],
            "DnsOptions": [],
            "DnsSearch": [],
            "ExtraHosts": null,
            "GroupAdd": null,
            "IpcMode": "private",
            "Cgroup": "",
            "Links": null,
            "OomScoreAdj": 0,
            "PidMode": "",
            "Privileged": false,
            "PublishAllPorts": false,
            "ReadonlyRootfs": false,
            "SecurityOpt": null,
            "UTSMode": "",
            "UsernsMode": "",
            "ShmSize": 67108864,
            "Runtime": "runc",
            "ConsoleSize": [
                0,
                0
            ],
            "Isolation": "",
            "CpuShares": 0,
            "Memory": 0,
            "NanoCpus": 0,
            "CgroupParent": "",
            "BlkioWeight": 0,
            "BlkioWeightDevice": [],
            "BlkioDeviceReadBps": null,
            "BlkioDeviceWriteBps": null,
            "BlkioDeviceReadIOps": null,
            "BlkioDeviceWriteIOps": null,
            "CpuPeriod": 0,
            "CpuQuota": 0,
            "CpuRealtimePeriod": 0,
            "CpuRealtimeRuntime": 0,
            "CpusetCpus": "",
            "CpusetMems": "",
            "Devices": [],
            "DeviceCgroupRules": null,
            "DeviceRequests": null,
            "KernelMemory": 0,
            "KernelMemoryTCP": 0,
            "MemoryReservation": 0,
            "MemorySwap": 0,
            "MemorySwappiness": null,
            "OomKillDisable": false,
            "PidsLimit": null,
            "Ulimits": null,
            "CpuCount": 0,
            "CpuPercent": 0,
            "IOMaximumIOps": 0,
            "IOMaximumBandwidth": 0,
            "MaskedPaths": [
                "/proc/asound",
                "/proc/acpi",
                "/proc/kcore",
                "/proc/keys",
                "/proc/latency_stats",
                "/proc/timer_list",
                "/proc/timer_stats",
                "/proc/sched_debug",
                "/proc/scsi",
                "/sys/firmware"
            ],
            "ReadonlyPaths": [
                "/proc/bus",
                "/proc/fs",
                "/proc/irq",
                "/proc/sys",
                "/proc/sysrq-trigger"
            ]
        },
        "GraphDriver": {
            "Data": {
                "LowerDir": "/var/lib/docker/overlay2/5f06b2f1ac9aff28d4227a9b0d5472e1add96c5bedd1bf25fd449f687d759da5-init/diff:/var/lib/docker/overlay2/7a7a896bb660e0d85fc80ab24e4d794cbf2af41f519cb607a708973c298e2da5/diff:/var/lib/docker/overlay2/e42f3b8dc605a19bcd411d1cb3f71cd2e4f52de86f5864bb07813a7a4f8cf2d9/diff:/var/lib/docker/overlay2/c1116b15cef241d04b9374b62726a829638298e1300000c4fce8066bd4a16c33/diff:/var/lib/docker/overlay2/c0dc7953db526a9d9588bbb9e01234864a7d7b74569e2bd196bb415ce83a4652/diff:/var/lib/docker/overlay2/34aa317f049518d3cf909ba2713379ed2ea734a0a11c3a8a175457b78b723dad/diff:/var/lib/docker/overlay2/f4da5c06e47acb75946e2e78d9cff87285b10a35b0387097795a35eac873bafd/diff:/var/lib/docker/overlay2/4f6667cd7c9a083f0ce8889af4c9a3d02b81dfa86fc2bb367b4ba37bf6dc8dc3/diff:/var/lib/docker/overlay2/e3072ad80c0aba2e0f7314a2363c5acb838832a5c65fcc2c34c84e0d92705d39/diff:/var/lib/docker/overlay2/b9206a652ec537817c9e8f443cc42df902ed40f88656526b3dfc47c3fbab2ae1/diff:/var/lib/docker/overlay2/257013dddd1be7e7b400158d86b4ef28bbe7fa052e119df7f0ecf26210c872fa/diff:/var/lib/docker/overlay2/0fcdb3f15729db9f0a07f0789f61d6723e26073516ff98e6c27eec81ed4477c1/diff:/var/lib/docker/overlay2/edc83f533c47bd2985fdcd298e56c394f9055494d357ade0f751c3f1b2ac2a7f/diff:/var/lib/docker/overlay2/2ec83a49325b92d51ff07ac5ccf7dd80ef2d5484eb580dcd4e7f6d5c44cf0619/diff:/var/lib/docker/overlay2/3354771de57d6715ddaf7a05dde8f3bd32a49f89f28be06dd2a05a65e43d9960/diff",
                "MergedDir": "/var/lib/docker/overlay2/5f06b2f1ac9aff28d4227a9b0d5472e1add96c5bedd1bf25fd449f687d759da5/merged",
                "UpperDir": "/var/lib/docker/overlay2/5f06b2f1ac9aff28d4227a9b0d5472e1add96c5bedd1bf25fd449f687d759da5/diff",
                "WorkDir": "/var/lib/docker/overlay2/5f06b2f1ac9aff28d4227a9b0d5472e1add96c5bedd1bf25fd449f687d759da5/work"
            },
            "Name": "overlay2"
        },
        "Mounts": [
            {
                "Type": "volume",
                "Name": "d062384bcf57694686350a4e140e784abed87b46d5daa6025ac524a3f2d216e0",
                "Source": "/var/lib/docker/volumes/d062384bcf57694686350a4e140e784abed87b46d5daa6025ac524a3f2d216e0/_data",
                "Destination": "/var/lib/postgresql/data",
                "Driver": "local",
                "Mode": "",
                "RW": true,
                "Propagation": ""
            }
        ],
        "Config": {
            "Hostname": "eec4785fe9d2",
            "Domainname": "",
            "User": "",
            "AttachStdin": false,
            "AttachStdout": false,
            "AttachStderr": false,
            "ExposedPorts": {
                "5432/tcp": {}
            },
            "Tty": false,
            "OpenStdin": false,
            "StdinOnce": false,
            "Env": [
                "POSTGRES_PASSWORD=mysecretpassword",
                "PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/lib/postgresql/13/bin",
                "GOSU_VERSION=1.12",
                "LANG=en_US.utf8",
                "PG_MAJOR=13",
                "PG_VERSION=13.0-1.pgdg100+1",
                "PGDATA=/var/lib/postgresql/data"
            ],
            "Cmd": [
                "postgres"
            ],
            "Image": "postgres",
            "Volumes": {
                "/var/lib/postgresql/data": {}
            },
            "WorkingDir": "",
            "Entrypoint": [
                "docker-entrypoint.sh"
            ],
            "OnBuild": null,
            "Labels": {},
            "StopSignal": "SIGINT"
        },
        "NetworkSettings": {
            "Bridge": "",
            "SandboxID": "a1c7ed40f3afa66570a953d169831da9cc2a3fb3467036883f2b0ea4c4acbdae",
            "HairpinMode": false,
            "LinkLocalIPv6Address": "",
            "LinkLocalIPv6PrefixLen": 0,
            "Ports": {
                "5432/tcp": null
            },
            "SandboxKey": "/var/run/docker/netns/a1c7ed40f3af",
            "SecondaryIPAddresses": null,
            "SecondaryIPv6Addresses": null,
            "EndpointID": "d2d60a278eb15177ed2dbbb393b19b93a80ebb6a16693ba820221d252822d5e9",
            "Gateway": "172.17.0.1",
            "GlobalIPv6Address": "",
            "GlobalIPv6PrefixLen": 0,
            "IPAddress": "172.17.0.2",
            "IPPrefixLen": 16,
            "IPv6Gateway": "",
            "MacAddress": "02:42:ac:11:00:02",
            "Networks": {
                "bridge": {
                    "IPAMConfig": null,
                    "Links": null,
                    "Aliases": null,
                    "NetworkID": "93ec2c8226b57b9eb6080e9580d68247a7a109174b14a78041820aa27772a2d2",
                    "EndpointID": "d2d60a278eb15177ed2dbbb393b19b93a80ebb6a16693ba820221d252822d5e9",
                    "Gateway": "172.17.0.1",
                    "IPAddress": "172.17.0.2",
                    "IPPrefixLen": 16,
                    "IPv6Gateway": "",
                    "GlobalIPv6Address": "",
                    "GlobalIPv6PrefixLen": 0,
                    "MacAddress": "02:42:ac:11:00:02",
                    "DriverOpts": null
                }
            }
        }
    }
]
```

Nesse caso, o IP é 172.17.0.2 e a porta exporta é 5432/tcp.

Com isso podemos testar a conexão:

```bash
$ telnet 172.17.0.2 5432
Trying 172.17.0.2...
Connected to 172.17.0.2.
Escape character is '^]'.
```

Uma outra forma é encontrar a porta que o container está exportando é pelo comando:

```bash
$ docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS               NAMES
eec4785fe9d2        postgres            "docker-entrypoint.s…"   33 minutes ago      Up 33 minutes       5432/tcp            some-postgres
```

E através da inspeção da rede que o container está conectado é possível identificar o seu IP:

```bash
$ docker network inspect bridge
[
    {
        "Name": "bridge",
        "Id": "93ec2c8226b57b9eb6080e9580d68247a7a109174b14a78041820aa27772a2d2",
        "Created": "2020-10-02T10:02:24.548224386-03:00",
        "Scope": "local",
        "Driver": "bridge",
        "EnableIPv6": false,
        "IPAM": {
            "Driver": "default",
            "Options": null,
            "Config": [
                {
                    "Subnet": "172.17.0.0/16",
                    "Gateway": "172.17.0.1"
                }
            ]
        },
        "Internal": false,
        "Attachable": false,
        "Ingress": false,
        "ConfigFrom": {
            "Network": ""
        },
        "ConfigOnly": false,
        "Containers": {
            "eec4785fe9d22b9abb1aa78f3dda6f36de3b988ec6b8f6ac08428481f1e64e61": {
                "Name": "some-postgres",
                "EndpointID": "d2d60a278eb15177ed2dbbb393b19b93a80ebb6a16693ba820221d252822d5e9",
                "MacAddress": "02:42:ac:11:00:02",
                "IPv4Address": "172.17.0.2/16",
                "IPv6Address": ""
            }
        },
        "Options": {
            "com.docker.network.bridge.default_bridge": "true",
            "com.docker.network.bridge.enable_icc": "true",
            "com.docker.network.bridge.enable_ip_masquerade": "true",
            "com.docker.network.bridge.host_binding_ipv4": "0.0.0.0",
            "com.docker.network.bridge.name": "docker0",
            "com.docker.network.driver.mtu": "1500"
        },
        "Labels": {}
    }
]
```

## Queria modificar ou instalar algo no container, tem como?

Sim, tem! O ideal acredito que seria criar sua própria imagem através de um "Dockerfile" chamando o comando "docker build", mas as vezes você só quer acessar o container que está em executação e talvez mexer em algo. 

Eu costumo verificar se o container tem o BASH ou SH instalado, daí eu executo o seguinte comando:

```yaml
$ docker exec -it eec4785fe9d2 bash
root@eec4785fe9d2:/#
```

 Já tentei usar o comando "docker attach", mas sempre digitava "exit" ao invés de sair da maneira correta sem parar o container pressionando CTRL+P seguido de CTRL+Q. Pelo menos assim meu container vai continuar executando! 😜

## Puts! Não tem uma forma mais fácil de utilizar o Docker?

Para nossa alegria, SIMMMMMMMMM!!!!

Temos uma ferramenta oficial chamada [Docker-compose](https://docs.docker.com/compose/).

E o [Docker Swarm](https://docs.docker.com/engine/reference/commandline/stack_deploy/), que é uma ferramenta de cluster nativa para Docker.

Ambas tem o propósito de ajudar a gerenciar múltiplos containers.

E outro detalhe: ambas suportam um arquivo de configuração no padrão [Docker Compose file](https://docs.docker.com/compose/compose-file/).

Podemos então configurar um arquivo YAML que descreve nossos containers e executar com ambas ferramentas, mas há ressalvas: tem algumas features de configuração que funcionam em uma ferramenta e não em outra e vice-versa, então atenção caso queira portar de uma ferramenta para outra.

Meu arquivo de exemplo: docker-componse.yml:

```yaml
version: '3.1'

services:

  db:
    image: postgres
    restart: always
    environment:
      POSTGRES_PASSWORD: example
    ports:
      - "15432:5432"

  adminer:
    image: adminer
    restart: always
    ports:
      - 8080:8080
    depends_on:
      - db
```

Detalhe a propriedade '**services:db:ports**': ela define que a porta 15432 no host local (localhost) será redirecionada para a porta 5432 do container 'db', que é o servidor Postgres.

A propriedade '**services:adminer**' define um outro container que na verdade é um cliente para acessar o bando de dados Postgres via aplicativo web no browser;  

Outro detalhe é a propriedade '**services:adminer:depends_on**':  essa propriedade define que o serviço 'adminer' só ficará disponível caso o serviço 'db' esteja disponível.

Eu particularmente ando utilizando o Docker-compose. Então vou seguir aqui com os comandos para utilizá-lo!

## Iniciar containers com docker-compose

 Observação: a flag "-f" <caminho do arquivo YAML> é necessário caso o arquivo de configuração esteja em outro diretório diferente do diretório de trabalho atual.

```yaml
$ docker-compose up -d
Creating network "dearrudam_default" with the default driver
Creating dearrudam_db_1 ... done
Creating dearrudam_adminer_1 ... done
```

*Detalhe*: os containers e configurações serão criados e nomeados utilizando como prefixo o nome do diretório corrente do comando.

Olhe eu aqui acessando o container 'adminer': 

![Alt Text](https://dev-to-uploads.s3.amazonaws.com/i/hnxtiqc4kujne617v7ni.png)

## Destruindo containers com docker-compose

 O comando abaixo pára e destrói os containers descritos no arquivo de configuração:

```yaml
$ docker-compose down
Stopping dearrudam_adminer_1 ... done
Stopping dearrudam_db_1      ... done
Removing dearrudam_adminer_1 ... done
Removing dearrudam_db_1      ... done
Removing network dearrudam_default
```

## Pontos de atenção!

Quando quiser manter dados dos containers, dê uma olhada em "docker volumes", ok ?

# Finalizando...

Gostaria de agradecer a oportunidade de criar esse artigo e principalmente por você leitor por chegar até aqui! Desculpe as gafes!!!

Reforço que esses comandos são comandos que eu realmente utilizo no meu dia-a-dia, e aceito sugestões de utilização!

E mais, a utilização do Docker vai além dessa simples utilização que descrevi aqui. Com o Docker se consolidou uma forma padronizada de distribuíção de soluções containerizadas resolvendo vários problemas de deployments como o famoso "Na minha máquina funciona!" entre outros no mundo cloud. Vale a pena estudar a origem e também os detalhes que com certeza esse artigo não abordou!

Abraços à todos e espero feedbacks!