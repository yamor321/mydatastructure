package main.com.example;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MyDataStructure extends Thread {

	private static HashMap<String, Object> myhash = new HashMap<>();

	public static BlockingQueue queue;

	public MyDataStructure(int capacity) {

		queue = new ArrayBlockingQueue(capacity);
	}

	/**
	 * add value to the data structure. complexity is o(1)
	 * @param key
	 * @param value
	 * @param timeToLive
	 */

	public void put(String key, Object value, int timeToLive) throws InterruptedException {

		while (queue.remainingCapacity() == 0) {
			myhash.remove(queue.peek());
			try {
				queue.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {

				myhash.put(key,value);

				try {
					queue.put(key);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				if (timeToLive > 0 ) {
					try {
						sleep(timeToLive);
						remove(key);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
				}
		});

		thread.start();

		}

	/**
	 * remove value from the data structure. complexity is o(1)
	 * @param key
	 */
	public void remove(String key) {

		myhash.remove(key);
		queue.remove(key);
	}

	/**
	 * get value from the data structure. complexity is o(1)
	 * @param key
	 * @return
	 */
	public Object get(String key) {

		return myhash.get(key);
	}

	/**
	 * get number of keys in the data structure. complexity is o(1)
	 * @return number of keys in the data structure
	 */
	public int size() throws InterruptedException {

		Thread.sleep(1);
		return queue.size();
	}
}

