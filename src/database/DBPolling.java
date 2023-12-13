package database;

public class DBPolling<T> implements Runnable {
    private boolean _isConnected = (DBConnection.getConnection() != null);

    public void run() {
        while (true) {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(0);
            }

            if (_isConnected) {
                // TODO: Implement polling
            }
        }
    }

    public static void polling() {
        Thread pollingThread = new Thread(new DBPolling<>());

        pollingThread.start();
    }
}
