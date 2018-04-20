


### JMX专业术语：

* MBean
<br>是Managed Bean的简称。在JMX中MBean代表一个被管理的资源实例，通过MBean暴露一系列方法和属性，外界可以获取被管理的资源的状态和操纵MBean的行为。事实上，MBean就是一个Java Object，同JavaBean模型一样，外界使用反射来获取Object的值和调用Object的方法，只是MBean提供了更加容易操作的反射的使用。Mbean 包括4种类型：标准MBean、动态MBean、开放MBean、模型MBean。

2、MBeanServer： MBeanServer是MBean 的容器。MBeanServer管理这些MBean，并且通过代理外界对它们的访问。MBeanServer提供了一种注册机制，通过注册Adaptor和Connector，以及MBean到MBeanServer，并且通过代理外界对它们的访问。外界可以通过名字来得到相应的MBean实例。

3、JMX Agent： Agent只是一个Java进程，它包括这个MBeanServer和一系列附加的MbeanService。当然这些Service也是通过MBean的形式来发布。

4、Protocol Adapters and Connectors： JMX Agent通过各种各样的Adapter和Connector来与外界(JVM之外)进行通信。同样外界（JVM之外）也必须通过某个Adapter和Connector来向JMX Agent发送管理或控制请求。Jdmk5.1中，sun提供很多Adaptor和Connector的实现

Adapter和Connector的区别在于：Adapter是使用某种协议(HTTP或者SNMP)来与JMX Agent获得联系，Agent端会有一个对象(Adapter)来处理有关协议的细节。比如SNMP Adapter和HTTP Adapter。而Connector在Agent端和client端都必须有这样一个对象来处理相应的请求与应答。比如RMI Connector。

JMX Agent可以带有任意多个Adapter，因此可以使用多种不同的方式访问Agent。

JMX基本构架：JMX分为三层，分别负责处理不同的事务。它们分别是：

1、Instrumentation 层： Instrumentation层主要包括了一系列的接口定义和描述如何开发MBean的规范。通常JMX所管理的资源有一个或多个MBean组成，因此这个资源可以是任何由Java语言开发的组件，或是一个JavaWrapper包装的其他语言开发的资源。

2、Agent 层： Agent用来管理相应的资源，并且为远端用户提供访问的接口。Agent层构建在Intrumentation层之上，并且使用管理Instrumentation层内部的组件。通常Agent由一个MBeanServer组成。另外Agent还提供一个或多个Adapter或Connector以供外界的访问

3、Distributed 层： Distributed层关心Agent如何被远端用户访问的细节。它定义了一系列用来访问Agent的接口和组件，包括Adapter和Connector的描述。

image

应用场景：
中间件软件WebLogic的管理页面就是基于JMX开发的。

hibernate号称实现了JMX规范，将可管理，可调用的MBean注册到MBeanServer中，通过一种类似“web服务”的方式公布出去，并且伴有一个名字，可以通过该名字找到该MBean。并且，这里的MBean是可以被管理的

Tomcat的自带应用manager就是使用了JMX方式来管理Tomcat，以此完成Web应用的动态部署、启动、停止。

JBoss则整个系统都基于JMX构架, 使用JMX治理内部的各个Service。

基于 Java 的开源网管软件 Hyperic HQ ，通过JMX与各被治理资源进行通讯和信息采集．

JMX是一个治理的框架。
当我们想使用JMX的时候，就要问，我们的系统当中有需要监控治理的资源或者对象吗？实事求是一点，我们不能为了想使用一个高端的技术，就歪曲系统的本来面目。

假如第一个问题是肯定的，接下来就是看这些资源是否有生命周期。

经典案例： JBoss 就是将所有可部署的组件作为资源来治理，这些组建都有其生命周期。这个理念甚至延伸到了其系统内部，将其内部的服务作为组件纳入到 JMX中来，成就了 JBoss 基于JMX的微内核系统。

典型应用场景：　
一个大系统中，各内部模块系统之间的,基于接口方式的互相调用和治理，使用JMX是最佳方案．带来的好处是:

1、面向接口，远程调用对于开发人员是透明的，模块在调用JMX接口时，与调用本地方法几乎相同．

2、可视化的治理界面，　通过　Jconsole等JMX客户端，可以实时监控系统，并且可实时调用方法进行某些操作．

某聊天系统，一台服务器作为 在线用户列表服务器 List1，　N 台服务器为用户提供聊天业务处理 N1 ,N2,N3…， 一台服务器作为后台治理系统 J2．　

系统治理员现在进行下面这样一个操作，察看某用户是否在线，找到该用户，发现其在线，则将该用户加入黑名单，并踢下线．

对应的JMX接口可以有以下几个： List1 为 J2提供: 查询在线用户JMX接口，加入黑名单接口，kickout接口； List1 为N1..等服务器提供以下接口：　注册业务服务器，添加在线用户．查找黑名单用户； N1…到N3为 List1 提供 :kickout接口。

因此在上面的踢下线操作，则由用户在J2的 Web 界面发出，交由 list1 执行, list1 记录黑名单之后，再找到用户所在业务服务器调用N1提供的接口让用户下线．

以上情形是在生产环境下的部署，而在开发工作，则可以将A1,A2,N…N3等功能合并在一个应用中调试．　由于使用的是JMX接口，在本地调试合并之后，可以直接调用应用内部接口方法；这样借助JMX实现的应用模块的灵活组装与拆分，使得系统的可以根据负载需要，根据性能情况，灵活的拆分和整合部署分布式的应用．

JMX与Web Service
1、个人认为，我们实现JMX规范，将东西发布出去，和通过web Service的方式是很类似的，也是可以远程调用的，只是相对的web Service的方式更加SOA一些

3、从这里，我觉得JMX可以实现的，我们也都可以通过web Service实现，只是看在它有个“M”上，以后如果有什么系统管理，监控方面的，可以考虑使用它，也许开发，个人觉得还是使用web service好一些。

4、选择webservice,xmlrpc等，但是这些都需要手工编写或用工具生成大量的代码来辅助完成接口间的java对象序列化。

现在的JMX连接方式：
提供了三种Connector：RMI Connector / JMXMP Connector（JMX message protocol Connector）/ Jolokia

RMI Connector 用JAVA的RMI功能来实现，可以在本地调用接口对象，这个不多说，因为只能是JAVA客户端使用，实现不了异构系统。
JMXMP Connector 就是使用协议在通讯了，Java也有实现这种连接的API,其他语言肯定也有的,这个能实现异构系统调用，是RMC模式。
Jolokia is a JMX-HTTP bridge giving an alternative method of accessing JMX beans，can be accessed using /jolokia on your management HTTP server.
The main focus of Jolokia is to allow easy access to JMX MBeans from everywhere. MBeans can be provided by the JVM itself, by an application server or an application itself, where each MBean is registered at a specific MBeanServer. Multiple MBeanServers can co-exist in a single JVM. The so called PlatformMBeanServer is always present and is created by the JVM during startup. Especially application servers often create additional MBeanServers for various purposes. When accessing an MBean remotely via JSR-160, the MBeanServer holding the requested MBean must be known before. Jolokia instead merges all MBeanServers it can find to give a single view on all MBeans. The merging algorithm is described in Section 9.1.1, “MBeanServer merging”.

OSGI与JMX
说到这里，感觉OSGI与JMX也好像，在看到JMX能够对MBean进行管理的时候，我就觉得跟OSGI很像，OSGI管理的是Bundle，找了找资源，原来早就有人考虑过了： http://teamojiao.iteye.com/blog/438334

if your question means, how to manage an OSGi runtime with JMX, you should have a look at MAEXO (http://code.google.com/p/maexo/). With MAEXO bundles up and running you will transparently get MBeans for a fair amount of services of the OSGi runtime as well as MBeans for your own services and bundles. Just have a look at the screencast.

实现原理
从JAVA 5开始，JDK就提供非常有名的java.lang.management 包，包里提供了许多MXBean的接口类，开发者可以很方便的获取到JVM的内存、GC、线程、锁、class、甚至操作系统层面的各种信息。我们可以遍历程序所有的MBean查看。

一个简单的代码示例

### 实例的大体思路：

假如我自己有一个服务MyService，其中定义了启动、关闭的方法

现在我想通过JMX来管理我的服务的启动，并查看它的状态

那么，要写一个名字为XxxxMBean的接口，在其中定义你想实现的功能，

在这里就是启动、关闭我的服务，并可以获取我的服务的实时状态

然后，写一个类实现XxxxMBean这个接口，这个类的名字任意。

最后，将这个类交给MBeanServer，并在程序启动的时候，启动MBeanServer