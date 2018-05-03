使用示例：
```
Object obj = null;
try{
   obj = pool.borrowObject();
   try {
       //...use the object...
   } catch(Exception e) {
       // invalidate the object
       pool.invalidateObject(obj); //do not return the object to the pool twice
       obj = null;
   } finally {
       // make sure the object is returned to the pool
       if(null != obj) {
           pool.returnObject(obj);
       }
   }
}catch(Exception e) {
   // failed to borrow an object
}
```