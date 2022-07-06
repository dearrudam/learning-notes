///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 11+

public class UsingAnonymousClasses {

    public static void main(String... args) {

        Thread th;

        th = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread [%s] is doing A...".formatted(Thread.currentThread().getName()));
            }
        });

        th.start();
        th = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread [%s] is doing B...".formatted(Thread.currentThread().getName()));
            }
        });

        th.start();
        th = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread [%s] is doing C...".formatted(Thread.currentThread().getName()));
            }
        });

        th.start();
        
    }
}
