package com.boleyn.netstudy.lession02;

/**
 * 关键字synchronized取得的锁都是对象锁，而不是把一段代码（方法）当作锁
 * 所以代码中哪个线程先执行synchronized关键字的方法，那个线程就持有该方法所属对象的锁（Lock）
 * 
 * 在静态方法上加synchronized关键字，表示锁定.class类，类级别的锁
 * @author Boleyn
 *
 */
public class MultiThread {
	
	private static int num = 0;
	
	/** 加上static类级别的锁*/
	//public static synchronized void printNum(String tag) {
	public synchronized void printNum(String tag) {
		try {
			if (tag.equals("a")) {
				num = 100;
				System.out.println("tag a , set num over!");
				Thread.sleep(1000);
			}else{
				num = 200;
				System.out.println("tag b , set num over!");
			}
			System.out.println("tag " + tag + ", num = " + num);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//注意观察run方法输出的顺序
	public static void main(String[] args) {
		//两个不同的对象
		final MultiThread m1 = new MultiThread();
		final MultiThread m2 = new MultiThread();
		
		Thread t1 = new Thread(()-> {
			m1.printNum("a");
		});
		
		Thread t2 = new Thread(()-> {
			m2.printNum("a");
		});
		
		t1.start();
		t2.start();
	}

}
