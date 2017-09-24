package com.FileTransfer.Transfer;

import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;






public class FileSend {
	
	public static String IP; //IP
	public static int port; //port
    public byte[] MsgCont; //消息包
 
    public FileSend(String IP, int port, byte[] MsgCont){
       this.IP = IP;
       this.port = port;
       this.MsgCont = MsgCont;
    }
	public byte[] getMsgCont() {
		return MsgCont;
	}
	public void setMsgCont(byte[] msgCont) {
		MsgCont = msgCont;
	}
	
	/*
	 * 发送消息包的线程
	 */
	private static Runnable sendMsgCont(byte[] MsgCont){
		
	        return new Runnable(){
	        	Socket socket = null;
	        	ByteArrayInputStream bais = null;
	            DataOutputStream dos = null;
				public void run() {
					if(createconn()){
						try {
							System.out.println("开始发送...");
							byte[] buf = new byte[4096];
							bais = new ByteArrayInputStream(MsgCont);
							dos = new DataOutputStream(socket.getOutputStream());
							int readLen = 0;
							while((readLen = bais.read(buf)) != -1){
								dos.write(buf, 0, readLen);
								dos.flush();
							}	
						} catch (IOException e) {
							e.printStackTrace();
						}finally{
							try {
								shutDownConnection();
							} catch (IOException e) {
								System.out.println("关闭异常");
							}
						}
						
				}
					
				}
				  /*
				   * 建立连接
				   */
				public boolean createconn(){
					try {		
						socket = new Socket(IP, port);
						System.out.println("连接服务器成功!");
						return true;
					    }catch (IOException e) {
					    System.out.println("连接服务器失败!");
					    return false;
						}
				 }
				/*
			     * 释放连接
				 */
				public void shutDownConnection() throws IOException {
			            if (dos != null)
						    dos.close();
			            if (bais != null)
							bais.close();
			            if (socket != null)
			             	socket.close();
							
			    }
	
             };
       }
	
	public void service(){
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        executorService.execute(sendMsgCont(MsgCont));
        }
 
	/*
     * 测试类
     */
	
//	public static void main(String[] args){
//        new FileSend("localhost",10010,new byte[]{1,2,3,4,5,6}).service();
//    }
    }



