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
	            System.out.println("����������!");
	            executorService = Executors.newFixedThreadPool(5);
	        } catch (Exception e) {
	            throw new Exception("�󶨶˿ڲ��ɹ�!");
	        }  
	    }  
	/*
	 * ���������̣߳�Ϊÿһ���ͻ�����һ�������߳�
	 */
	   public void service(){
		  
		  while(true){
			  Socket socket = null;	 
			  try {
	            	socket = serversocket.accept();  //���߳�ѭ��ִ��serversocket.accept()����ȡ��ͻ��˵�����
	            	executorService.execute(new receiveThread(socket));//���õ��ͻ��˵��������󣬾ͽ�socket���󴫵ݸ��̳߳أ����̳߳��ж��߳�ȥִ�о�������
			  } catch (Exception e) {
	                e.printStackTrace();
	            }
		       
		    }          
		  }
	   /*
	    * ������Ϣ���߳�
	    */
		class receiveThread implements Runnable{
			private Socket socket;
			public receiveThread(Socket socket) {
				this.socket = socket;
			}
			public void run() {
				try {
					    dis = new DataInputStream(socket.getInputStream()); //�����������տͻ�����Ϣ
					    baos = new ByteArrayOutputStream();
					    int buffsize = 4096;
						byte[] buff = new byte[buffsize];
						int len = 0;
					    System.out.println("��ʼ����...");
						while((len = dis.read(buff, 0, buff.length)) != -1){
							baos.write(buff, 0, len);
							MsgCont = baos.toByteArray();
							System.out.println("MsgCont:"+Arrays.toString(MsgCont)+" ���Ĵ�С:"+MsgCont.length);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}finally{
						try {
							shutDownConnection();
						} catch (IOException e) {
							System.out.println("����������Դ�ر��쳣!");
						}
					}
		
			}
			
		}
		/*
	     * �ͷ�����
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
		 * ������
		 */
//		public static void main(String[] args) throws Exception{
//            new FileReceive(10010).service();
//        }

	
}
