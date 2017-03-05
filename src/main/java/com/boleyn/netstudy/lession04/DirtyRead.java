package com.boleyn.netstudy.lession04;

public class DirtyRead {
	private String username = "renlei";
	private String password = "123";
	
	public synchronized void setValue(String username, String password){
		this.username = username;
		
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.password = password;
		System.out.println("setValue最终结果:username = " + username + " , password = " + password);
	}
	
	public void getValue(){
		System.out.println("getValue最终结果:username = " + username + " , password = " + password);
	}
	
	public static void main(String[] args) throws Exception {
		final DirtyRead dr = new DirtyRead();
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				dr.setValue("boleyn", "456");
			}
		});
		t1.start();
		Thread.sleep(1000);
		
		dr.getValue();
	}
}
