package concurrency.other;

public class Livelock {

    public static void main(String[] args) {

        final Diner husband = new Diner("Bob");
        final Diner wife = new Diner("Alice");

        final Spoon s = new Spoon(husband);

        new Thread(new Runnable() {
            public void run() {
                husband.eatWith(s, wife);
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                wife.eatWith(s, husband);
            }
        }).start();
    }

    static class Spoon {
        private Diner owner;

        public Spoon(Diner d) {
            owner = d;
        }

        public synchronized void setOwner(Diner d) {
            owner = d;
        }

        public synchronized void use() {
            System.out.printf(owner.name + " has eaten!");
        }
    }

    static class Diner {
        private String name;
        private boolean isHungry;

        Diner(String n) {
            name = n;
            isHungry = true;
        }

        String getName() {
            return name;
        }

        boolean isHungry() {
            return isHungry;
        }

        public void eatWith(Spoon spoon, Diner spouse) {
            while (isHungry) {
                if (spoon.owner != this) {  // Don't have the spoon, so wait patiently for spouse.
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        continue;
                    }
                    continue;
                }
                if (spouse.isHungry()) { // If spouse is hungry, insist upon passing the spoon.
                    System.out.println(name + ": You eat first my darling " + spouse.getName());
                    spoon.setOwner(spouse);
                    continue;
                }
                // Spouse wasn't hungry, so finally eat
                spoon.use();
                isHungry = false;
                System.out.println(name + ": I am stuffed, eat my darling " + spouse.getName());
                spoon.setOwner(spouse);
            }
        }
    }

}
