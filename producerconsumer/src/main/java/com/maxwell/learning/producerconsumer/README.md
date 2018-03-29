生产者消费者模式

---
### 注意点
###### 生产或消费的过程无需同步，仅同步放入和取出这两个动作，否则会导致多线程效率降低
这一点非常重要，即遵循"同步代码尽量少"的原则，该同步的同步，不该同步的不要同步；
在生产者消费者模型中，生产者的生产过程和消费者的消费过程就不需要同步，这可能是非常耗时的过程；
生产者将生产出的商品放入仓库（代码中的queue），和消费者从仓库中取出商品的这两个动作，则需要同步，否则可能会出现取空或溢出的问题。

错误示例：
```
@Override
        public void run() {
            while (true) {
                synchronized (queue) {
                    while (queue.size() == maxSize) {
                        try {
                            System.out.println("队列已满，【" + threadName + "】进入等待状态");
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    //模拟生产过程中的耗时操作
                    Goods goods = new Goods();
                    try {
                        Thread.sleep(new Random().nextInt(1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    queue.add(goods);
                    System.out.println("【" + threadName + "】生产了一个商品：【" + goods.toString() + "】");
                    queue.notifyAll();
                }
            }
        }
```
正确示例：
```
@Override
        public void run() {
            while (true) {
                //模拟生产过程中的耗时操作
                Goods goods = new Goods();
                try {
                    Thread.sleep(new Random().nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (queue) {
                    while (queue.size() == maxSize) {
                        try {
                            System.out.println("队列已满，【" + threadName + "】进入等待状态");
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    queue.add(goods);
                    System.out.println("【" + threadName + "】生产了一个商品：【" + goods.toString() + "】");
                    queue.notifyAll();
                }
            }
        }
```