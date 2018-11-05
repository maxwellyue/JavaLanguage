package com.maxwell.learning.rpc.rmidemo;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class UserServiceImpl extends UnicastRemoteObject implements UserService{

    protected UserServiceImpl() throws RemoteException {
    }

    @Override
    public String getUsername(int userId) throws RemoteException {
        return "username:::" + userId;
    }

    @Override
    public User getById(int userId) throws RemoteException {
        User user = new User();
        user.setUserId(userId);
        user.setUsername("username:::" + userId);
        user.setAge(userId*10);
        return user;
    }
}
