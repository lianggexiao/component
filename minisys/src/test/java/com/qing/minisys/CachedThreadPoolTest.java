//package com.qing.minisys;
//
//import java.util.ArrayList;
//import java.util.UUID;
//import java.util.concurrent.Callable;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.Future;
//
//public class CachedThreadPoolTest {
//
//    /**
//     * //TODO 添加方法功能描述
//     * @param args
//     */
//    public static void main(String[] args) throws Exception {
//        ExecutorService executorService = Executors.newFixedThreadPool(200);
//        ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2 + 1);
//        for (int i = 0; i < 100000; i++) {
//            executorService.submit(new Runnable() {
//
//                @Override
//                public void run() {
//                    CountDownLatch countDownLatch = new CountDownLatch(2);
//                    ArrayList<Future<String>> result = new ArrayList<Future<String>>();
//                    for (int i = 0; i < 2; i++) {
//                        Future<String> str = es.submit(new Callable<String>() {
//
//                            @Override
//                            public String call() throws Exception {
//                                try {
//                                    Thread.sleep(1000);
//                                    return UUID.randomUUID().toString();
//                                } finally {
//                                    countDownLatch.countDown();
//                                }
//                            }
//
//                        });
//                        result.add(str);
//                    }
//
//                    try {
//                        countDownLatch.await();
//                    } catch (InterruptedException e1) {
//                        e1.printStackTrace();
//                    }
//
//                    String string = "";
//                    for (int i = 0; i < 2; i++) {
//                        try {
//                            string += result.get(i).get();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        } catch (ExecutionException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    System.out.println(Thread.currentThread().getId() + " - " + string);
//                }
//            });
//        }
//
//        System.in.read();
//
//    }
//
//}
