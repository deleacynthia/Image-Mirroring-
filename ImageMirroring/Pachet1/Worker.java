package Pachet1;
public abstract class Worker extends Thread {
    protected boolean consumer;
// ca la laborator
    public abstract void starter() throws InterruptedException;
//verificam daca este sau nu consumer
    protected Worker(Boolean consumer) {
        super();
        this.consumer = consumer;
    }
//pornim thread-ul si afisam cand s-a realizat
    public void run() {
        try {
            this.starter();
            System.out.println("[Worker] Thread-ul s-a terminat.");
        } catch (InterruptedException e) {}
    }
}