package com.cs.appoint.timer;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {   
    	test();
    }
    
    public static void myTest(){
    	List<Integer> list = new ArrayList<Integer>();
    	for(int i=1; i<=21; i++){
    		list.add(i);
    	}
    	System.out.println(list);
    	
    	int cycelTotal = 5;
    	for(int i=0; i<cycelTotal; i++){
    		List<Integer> tmp = new ArrayList<Integer>();
    		for(int j=0; j<list.size(); j++){
    			tmp.add(list.get(j));
    		}
    	}
    }
	public static void test() {
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < 13; i++) {
			list.add(i);
		}
		System.out.println(list);

		int init = 10;// 每隔1000条循环一次
		int total = list.size();
		int cycelTotal = total / init;
		if (total % init != 0) {
			cycelTotal += 1;
			if (total < init) {
				init = list.size();
			}
		}

		System.out.println("循环保存的次数：" + cycelTotal);// 循环多少次
		List<Integer> list2 = new ArrayList<Integer>();
		for (int i = 0; i < cycelTotal; i++) {
			for (int j = 0; j < init; j++) {
				if (list.size()-1 < j) {
					break;
				}
				list2.add(list.get(j));
			}
			System.out.println("保存1000条数据到数据库....");
			System.out.println(list2);// 每次循环保存的数据输出
			// 接下来写保存数据库方法
			// .............
			list.removeAll(list2);// 移出已经保存过的数据
			list2.clear();// 移出当前保存的数据
		}
	}
}


class MyTask implements Runnable {
   private int taskNum;
    
   public MyTask(int num) {
       this.taskNum = num;
   }
    
   @Override
   public void run() {
       System.out.println("正在执行task "+taskNum);
       try {
           Thread.currentThread().sleep(4000);
       } catch (InterruptedException e) {
           e.printStackTrace();
       }
       System.out.println("task "+taskNum+"执行完毕");
   }
}
