package xyz.iamthedefender.cosmetics.util;

import org.bukkit.scheduler.BukkitTask;
import xyz.iamthedefender.cosmetics.api.util.Run;

import java.util.ArrayList;
import java.util.List;

public class SyncQueue {

    private static final List<Runnable> tasks = new ArrayList<>();
    private static BukkitTask startTask = null;

    public static void add(Runnable runnable) {
        tasks.add(runnable);
    }

    public static void start(int delay) {
        if (startTask != null) {
            startTask.cancel();
            startTask = null;
            tasks.clear();
        }

        startTask = Run.delayed(SyncQueue::internalStart, delay);
    }

    private static void internalStart() {
        if (tasks.isEmpty()) return;

        Runnable first = tasks.remove(0);

        first.run();

        Run.delayed(SyncQueue::internalStart, 1L);
    }

}
