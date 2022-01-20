package com.example.mnet;

import java.util.concurrent.Executor;

class DirectExecutor implements Executor {
    public void execute(Runnable r) {
        new Thread(r).start();
    }
}