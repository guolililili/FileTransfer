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
    public byte[] MsgCont; //��Ϣ��
 
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
	 * ������Ϣ�����߳�
	 */
	private static Runnable sendMsgCont(byte[] MsgCont){
		
	        return new Runnable(){
	        	Socket socket = null;
	        	ByteArrayInputStream bais = null;
	            DataOutputStream dos = null;
				public void run() {
					if(createconn()){
						try {
							System.out.println("��ʼ����...");
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
								System.out.println("�ر��쳣");
							}
						}
						
				}
					
				}
				  /*
				   * ��������
				   */
				public boolean createconn(){
					try {		
						socket = new Socket(IP, port);
						System.out.println("���ӷ������ɹ�!");
						return true;
					    }catch (IOException e) {
					    System.out.println("���ӷ�����ʧ��!");
					    return false;
						}
				 }
				/*
			     * �ͷ�����
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
     * ������
     */
	
//	public static void main(String[] args){
//        new FileSend("localhost",10010,new byte[]{1,2,3,4,5,6}).service();
//    }
    }



