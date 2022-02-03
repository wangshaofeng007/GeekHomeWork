package week4;
import java.util.concurrent.CountDownLatch;

public class ThreadNoSync01 {

    public static void main(String[] args) throws InterruptedException{
        CountDownLatch countDownLatch = new CountDownLatch(2);
        for(int i=0;i<2;i++){
            new Worker(countDownLatch).start();
        }
        countDownLatch.await();
        System.out.println("thread invoke");
        System.out.println("main invoke");
    }

    static class Worker extends Thread {
        private CountDownLatch countDownLatch;
        public  Worker(CountDownLatch countDownLatch){
            this.countDownLatch = countDownLatch;
        }
        @Override
        public void run(){
            synchronized (this){
               int sum =  sum(5);
               System.out.println(sum);
                countDownLatch.countDown();
            }
        }
    }


    private static int sum(int src){
       return cal(src);
    }

    private static int cal(int n){
        if(n<2){
            return 1;
        }else{
            return cal(n-2)+cal(n-1);
        }
    }
}
