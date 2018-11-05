package com.maxwell.learning.rpc.rmidemo;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public interface UserService extends Remote {

    String getUsername(int userId) throws RemoteException;

    User getById(int userId) throws RemoteException;
}
