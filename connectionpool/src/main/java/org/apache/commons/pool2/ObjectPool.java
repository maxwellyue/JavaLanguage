/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.pool2;

import java.io.Closeable;
import java.util.NoSuchElementException;

/**
 * 池化接口
 * 使用示例：
 *      Object obj = null;
 *      try{
 *          obj = pool.borrowObject();
 *          try {
 *              //...use the object...
 *          } catch(Exception e) {
 *              // invalidate the object
 *              pool.invalidateObject(obj); //do not return the object to the pool twice
 *              obj = null;
 *          } finally {
 *              // make sure the object is returned to the pool
 *              if(null != obj) {
 *                  pool.returnObject(obj);
 *              }
 *          }
 *      }catch(Exception e) {
 *          // failed to borrow an object
 *      }
 *
 * @param <T> 池中对象类型
 * @since 2.0
 */
public interface ObjectPool<T> extends Closeable {

    /**
     * 从池中获取一个实例
     *
     * 该方法返回的实例，
     * 要么是新创建的（PooledObjectFactory#makeObject），
     * 要么是之前idle的对象且已经激活（PooledObjectFactory#activateObject）和验证（PooledObjectFactory#validateObject）
     *
     * 按照契约，客户端必须使用returnObject()方法归还借出去的实例。
     *
     * 当池中资源耗尽时，该方法的行为并没有严格定义，不过可以在具体实现中来定义。
     *
     * @return
     * @throws Exception
     * @throws NoSuchElementException
     * @throws IllegalStateException
     */
    T borrowObject() throws Exception, NoSuchElementException,
            IllegalStateException;

    /**
     * 将实例归还给池
     *
     * 按照契约，该实例必须是通过borrowObject()方法获取的
     *
     * @param obj
     * @throws Exception
     */
    void returnObject(T obj) throws Exception;


    /**
     * 将池中的一个对象变成无效的
     *
     * 按照契约，该对象必须已经从池中通过borrowObject方法获取到了。
     *
     * 该方法应该在这种情况下使用：
     *
     * 对象已经被借出，但由于异常或其他问题，要将它变为无效的
     *
     *
     * @param obj
     * @throws Exception
     */
    void invalidateObject(T obj) throws Exception;

    /**
     *
     * 使用对象工厂创建一个对象，并将其放入idle对象池中
     *
     * @throws Exception
     * @throws IllegalStateException
     * @throws UnsupportedOperationException
     */
    void addObject() throws Exception, IllegalStateException,
            UnsupportedOperationException;


    /**
     * 返回对象池中当前空闲的实例个数
     *
     * 大约是可以被借出的对象的个数
     *
     * @return
     */
    int getNumIdle();


    /**
     * 返回对象池中当前已经被借出的实例个数
     *
     * @return
     */
    int getNumActive();

    /**
     * 清除池中空闲的对象
     *
     * 如果实现不支持该操作，抛出UnsupportedOperationException异常
     *
     * @throws Exception
     * @throws UnsupportedOperationException
     */
    void clear() throws Exception, UnsupportedOperationException;

    /**
     * 关闭池，并释放与之相关的资源
     *
     * 在该方法之后再次调用addObject或者borrowObject将会抛出IllegalStateException异常
     *
     * 在实现中，如果所有的资源并不能释放，应该silently fail
     *
     */
    @Override
    void close();
}
