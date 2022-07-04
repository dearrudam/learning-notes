---
title: Using Docker day by day
description: 
tags: [docker, beginners, tutorial]
cover_image:
series: 
published: true
publish_date: 
---
# Using Docker day by day

Hi, everyone? How is it going? I'm here trying to learn how to share a little of my knowledge in order to learn much more with those of you who have come this far. So don't hesitate to comment, okay? Your feedback is so important. And it doesn't matter if it means that I need to stop.😜 lol!

In this first article in English, I will try to be straightforward with regard to the use of Docker from the point of view of a mere software developer, which is my point of view.

Solutions, such as in-memory databases (Derby, H2, among others) or the "mocking" of external services (using libraries like Mockito or solutions like Wiremock), have always been good solutions during the development of some solutions until I learned about Docker. This doesn't mean that I've replaced those items from my "utility belt" with Docker. It is because I don't believe in a "silver bullet." Also, we need to deal with the "it-depends" thinking that clarifies the issues that we are working on in the technology world, which evolves every day.

The other interesting thing is that you may be able to test something that perhaps runs on a different OS that you're working on right now in a couple of seconds. You can do this instead of waiting for an IT Team to provide you with a kind of development environment that you need to communicate with. (Whoever has never faced a scenario like that probably will not understand what I'm talking about. Maybe you do. Am I right?) 

Okay, I think that I've talked enough! Let's see some Docker commands!

For more details about Docker, take a look at the [documentation](https://docs.docker.com/get-started/overview/).

## Listing of the available Docker commands:




```bash
$ docker --help
```

## Getting detail about a given Docker command usage:

```bash
$ docker <comando> --help
```

## What version am I using?

```bash
$ docker --version
Docker version 19.03.13, build 4484c46d9d
```

## How can I see the available Docker images that are in my local environment?

```bash
$ docker images
```

## Where can I find and get more Docker images?

I'm used to getting Docker images from the [Docker Hub](https://hub.docker.com/) site.

## Cool! Okay, I've got the image that I want to execute, but now what? How can I execute it?

Let's suppose that you would like to download a Docker image with a Postgres database; you should perform a search with a "postgres" word in the Docker hub. You'll find the official Docker image with Postgres and others that are not official.

Docker images have a version control that is related through "tags" in order to identify the version.  

By default, in case no tag is informed, the "latest" version will be used. Maybe you'll find a lot of available "tags." This means there will be many versions. 

Right here, it's up to you! Choose one tag, and go ahead! (In this article, I've chosen the latest one!)

There are two ways to download Docker images: using a "docker pull" command or a "docker run" command. 

### Downloading Docker images:

```bash
$ docker pull <imagem:tag>
```

Check it out below. I'm getting the official "postgres" Docker image:

```bash
$ docker pull postgres

ou

$ docker pull postgres:latest

```

### How can I delete Docker images from my local machine?

Watch out! I hope you're sure about this action, okay? Maybe it's not a good idea. Maybe you'll need such Docker images in the future.

Ok, if you're really sure about it, don't forget to stop and delete the containers derived from the target Docker image, or you can use "brutal force" putting the "-f" flag at the command.

```bash
$ docker rmi postgres
```

### How can I delete the Docker images that I'm not using from my local machine?

Be careful, buddy! Don't delete Docker images that shouldn't be deleted. Did you?

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

### Running Docker images

The command is "docker run." Such a command has a lot of parameters, so I recommend that you  take a look at the command help for details.

```bash
$ docker run --help
```

Don't worry about the high number of available parameters. Normally, the maintainers are accustomed to providing usage guides for its Docker images.

In our sample, I'm starting a container based on the "postgres" Docker image:

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

### What does the "Pulling from..." output mean?

It happens during the initial process when the Docker engine recognizes that it's missing the requested Docker image in the local machine. Once the requested Docker image is on the local machine, the next process will happen pretty quickly!

## What containers are running?

```bash
$ docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS               NAMES
2cb420e36c11        postgres            "docker-entrypoint.s…"   26 seconds ago      Up 9 seconds        5432/tcp            some-postgres
```

Do you want to see all containers that you have created on your local machine? Add the "-a" flag at the "docker ps" command.

## I'd like to stop my active containers. How can I do it?

You need to use or the "ID"s or the "NAMES" of the active containers and perform the command below.

**Tip**: It's possible to stop many containers with one command only. You'll need just to provide the "ID"s or "NAMES" of the target containers.

```bash
$ docker stop <CONTAINER ID> <CONTAINER NAME> <CONTAINER ID>
```

Example:

```bash
$ docker ps -a
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS               NAMES
757c0dd2d3dc        postgres            "docker-entrypoint.s…"   8 seconds ago       Up 5 seconds        5432/tcp            some-postgres-other
b3f3199c2ef4        postgres            "docker-entrypoint.s…"   25 seconds ago      Up 22 seconds       5432/tcp            some-postgres

$ docker stop 757c0dd2d3dc some-postgres
757c0dd2d3dc
some-postgres
```

## I want to delete my containers. How can I do it?

**Tip**: It's possible to delete many containers with one command only. You'll just need to provide the "ID"s or "NAMES" of the target containers.

One more thing: if it is what you need to do, don't forget to stop the containers that are running. Or you can use the "brutal force" mode by using the "-f" flag.

Use the "ID"s or the "NAMES" of the containers that you want to delete, and perform the command below:

```bash
$ docker rm <CONTAINER ID> <CONTAINER NAME> <CONTAINER ID>
```

Example:

```bash
$ docker ps -a
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS               NAMES
757c0dd2d3dc        postgres            "docker-entrypoint.s…"   8 seconds ago       Up 5 seconds        5432/tcp            some-postgres-other
b3f3199c2ef4        postgres            "docker-entrypoint.s…"   25 seconds ago      Up 22 seconds       5432/tcp            some-postgres

$ docker rm -f 757c0dd2d3dc some-postgres
757c0dd2d3dc
some-postgres
```

## Great! The container is up and running. How can I access it?

During the Docker installation, by default, it's installed with three networks: "bridge," "none," and "host." Also, by default, it uses the "bridge" network for communication between the container and the host.

Docker lets you create a custom network, but it's beyond the scope of this article! Sorry. Lol.

### Listing the configured networks at Docker:

```bash
$ docker network ls
NETWORK ID          NAME                DRIVER              SCOPE
93ec2c8226b5        bridge              bridge              local
a2cd7ddae279        host                host                local
518332f330ee        none                null                local
```

### Inspecting a network configuration:

```bash
$ docker network <NETWORK ID or NAME>
```

Example: inspecting the "bridge" network:

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

## Accessing an active container:

Inspect the network configurations of the container that you want to access:

```bash
$ docker container inspect <CONTAINER ID or CONTAINER NAME>
```

Right now, you will know the IP address and port of the container that you're trying to connect:

Example:

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

In the case above, the IP is 172.17.0.2, and the exposed port is 5432 under the TCP protocol.

With the required information, we can test to see if the container is accessible:

```bash
$ telnet 172.17.0.2 5432
Trying 172.17.0.2...
Connected to 172.17.0.2.
Escape character is '^]'.
```

Another way to get the same information in order to obtain the exposed container’s port is by using the “docker ps” command...

```bash
$ docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS               NAMES
eec4785fe9d2        postgres            "docker-entrypoint.s…"   33 minutes ago      Up 33 minutes       5432/tcp            some-postgres
```

and with the “docker network inspect bridge” command, we get the container’s IP from the network that it’s connected to. In this case, the bridge network is the default network. Check it out below:

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

## I'd like to modify or install something at the container only. Is it possible?

Yeah, it is! 

In my humble opinion, I think that the recommendation is to create your Docker image based on a "Dockerfile" that will be built with the "docker build" command, but sometimes, you just want to access a given container in order to customize it or be able to perform some commands there.

I'm used to verifying if the container has BASH or SH installed. If the answer is, "Yes," then I'll perform the following command:

```bash
$ docker exec -it eec4785fe9d2 bash
root@eec4785fe9d2:/#
```

I've tried many times to use the "docker attach" command, but I'm used to typing the "exit" command instead of pressing CTRL+P and CTRL+Q. At least, in this way, my container will keep running! 😜

## Oh, no... is there an easier way to use Docker?

It is for our happiness, YES!!!

We've got an official tool called [Docker Compose](https://docs.docker.com/compose/):

Also, we've got [Docker Swarm](https://docs.docker.com/engine/reference/commandline/stack_deploy/) that is a native cluster tool for Docker:

Both of them were created to help us manage multiple containers.

One more point: both support configuration files based on [the "Docker Compose file" specification](https://docs.docker.com/compose/compose-file/).

Let's create a YAML file that follows the Docker Compose specification to describe all containers that we want to be able to execute. As mentioned before, the same file can be used by both tools, but there are caveats: there are configuration features that are only supported by one tool instead of another one and vice-versa. Therefore, pay attention if you want to interoperate such a file.

Here's my sample "docker-componse.yml" file:

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

Pay attention to the **"sevices:db:ports"** property: such property configures the ports that will be exposed. In my sample, it is defined that the 15432 port at the localhost will be redirected to the 5432 port of the **"db"** container (our postgres server).

The "services:adminer" property describes another container. It will be a client that will be used for us to access the Postgres server through the browser. 

One interesting property is the **"services:adminer:depends_on."** It means that the **"adminer"** container will be started if and only if the **"db"** container is running.

I'm accustomed to using the Docker Compose tool. Then we will go ahead and show you basic commands to use it!

## Starting the containers with Docker Compose:

**PS**: the "-f <YAML file>" option is necessary if the YAML file name is different than "docker-compose.yml" or "docker-compose.yaml" and/or if such a file is on a different directory than the directory on which you are trying to perform the commands.  

```bash
$ docker-compose up -d
Creating network "dearrudam_default" with the default driver
Creating dearrudam_db_1 ... done
Creating dearrudam_adminer_1 ... done
```

A curious detail: the name of the current directory will be used as the prefix name of the containers and configurations described in the Docker Compose file.

Take a look below. I'm accessing the "adminer" container through my browser: 

![Alt Text](https://dev-to-uploads.s3.amazonaws.com/i/hnxtiqc4kujne617v7ni.png)

## Destroying containers with Docker Compose:

The command below stops and destroys all containers described in the configuration file: 

```bash
$ docker-compose down
Stopping dearrudam_adminer_1 ... done
Stopping dearrudam_db_1      ... done
Removing dearrudam_adminer_1 ... done
Removing dearrudam_db_1      ... done
Removing network dearrudam_default
```

## One more important point!

If you want to keep the data of the container after the container stops, take a look at the "docker volume" commands, okay?

# Summarizing...

I am very thankful for the opportunity to create this article, and I'd like to thank you, the reader, for coming this far! I am sorry for any misunderstandings!

Also, I'd like to thank my English teacher James Zarrello for helping me with this article translation. Thank you, James!

I'd like to pay attention to these commands because I'm using them every day, and I will be happy if you can provide me with any other good usage suggestions!

Indeed, Docker goes beyond the simple usage that I've described in this article. Docker has consolidated a standardization of the distribution of containerized solutions and solves many deployment issues like the famous one "in my machine works" and others in the cloud world. 

Learning Docker in a thorough way is very worthwhile. There are many interesting points that this article didn't show for sure!

I hope you enjoyed this article! Looking forward to the feedback!

See you later!