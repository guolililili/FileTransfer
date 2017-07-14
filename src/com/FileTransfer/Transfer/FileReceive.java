package com.FileTransfer.Transfer;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileReceive {
	public int port;
	private ServerSocket serversocket = null;
	private Socket socket = null;
	private DataInputStream dis = null;
	private ByteArrayOutputStream baos = null;
	private byte[] MsgCont;
	private ExecutorService executorService;  
	
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public byte[] getMsgCont() {
		return MsgCont;
	}
	public void setMsgCont(byte[] msgCont) {
		MsgCont = msgCont;
	}
	 public FileReceive(int port) throws Exception{
	        try {
	        	
	        	serversocket = new ServerSocket(port);
	            System.out.println("服务器启动!");
	            executorService = Executors.newFixedThreadPool(5);
	        } catch (Exception e) {
	            throw new Exception("绑定端口不成功!");
	        }  
	    }  
	/*
	 * 服务器主线程，为每一个客户分配一个工作线程
	 */
	   public void service(){
		  
		  while(true){
			  Socket socket = null;	 
			  try {
	            	socket = serversocket.accept();  //主线程循环执行serversocket.accept()，获取与客户端的连接
	            	executorService.execute(new receiveThread(socket));//当拿到客户端的连接请求，就将socket对象传递给线程池，让线程池中多线程去执行具体任务
			  } catch (Exception e) {
	                e.printStackTrace();
	            }
		       
		    }          
		  }
	   /*
	    * 接收消息包线程
	    */
		class receiveThread implements Runnable{
			private Socket socket;
			public receiveThread(Socket socket) {
				this.socket = socket;
			}
			public void run() {
				try {
					    dis = new DataInputStream(socket.getInputStream()); //输入流，接收客户端消息
					    baos = new ByteArrayOutputStream();
					    int buffsize = 4096;
						byte[] buff = new byte[buffsize];
						int len = 0;
					    System.out.println("开始接收...");
						while((len = dis.read(buff, 0, buff.length)) != -1){
							baos.write(buff, 0, len);
							MsgCont = baos.toByteArray();
							System.out.println("MsgCont:"+Arrays.toString(MsgCont)+" 它的大小:"+MsgCont.length);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}finally{
						try {
							shutDownConnection();
						} catch (IOException e) {
							System.out.println("服务器端资源关闭异常!");
						}
					}
		
			}
			
		}
		/*
	     * 释放连接
		 */
		public void shutDownConnection() throws IOException {
	        
	            if (baos != null)	
				    baos.close();
	            if (dis != null)
				    dis.close();
	            if (socket != null)
	            	socket.close();
	            if (serversocket != null)
	                serversocket.close();
	
	    }
		/*
		 * 测试类
		 */
//		public static void main(String[] args) throws Exception{
//            new FileReceive(10010).service();
//        }

	
}
