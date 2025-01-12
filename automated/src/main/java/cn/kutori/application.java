package cn.kutori;

import cn.kutori.config.OpenCVConfig;
import cn.kutori.config.SelectRunFileConfig;
import cn.kutori.utils.KeyListenerUtils;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.sun.jna.platform.win32.WinDef;
import org.opencv.core.Mat;

import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

import static cn.kutori.enumPojo.coordinate.COORDINATEX;
import static cn.kutori.enumPojo.coordinate.COORDINATEY;
import static cn.kutori.utils.ScreenCaptureUtils.getScreenShot;

public class application  {


    public static void main(String[] args) throws Exception {
        // 禁用 JNativeHook 的日志输出
        Logger jnhLogger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        jnhLogger.setLevel(Level.WARNING);
        jnhLogger.setUseParentHandlers(false);

        KeyListenerUtils keyListenerUtils = new KeyListenerUtils();


        try {
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(keyListenerUtils);
        } catch (Exception e) {
            throw new Exception(e);
        }

        // 加载OpenCV库
        OpenCVConfig openCVConfig = new OpenCVConfig();
        openCVConfig.init();

        // 图片路径
        String templateImagePath = "again.png";
        String templateImagePath1 = "yes2.png";
        String templateImagePath2 = "skip4.png";
        String templateImagePath3 = "have4.png";

        // 获取窗口句柄
        SelectRunFileConfig selectRunFileConfig = new SelectRunFileConfig();
        WinDef.HWND hwnd = selectRunFileConfig.selectRunFile();

        final Lock lock = new ReentrantLock();
        final Condition condition = lock.newCondition();

        while (true) {
            lock.lock();
            if (!keyListenerUtils.isRunning) {
                // 如果进程未启动，则跳过图像匹配逻辑
                long time = condition.awaitNanos(500 * 1_000_000L);
                continue;
            }
            try {
                Mat screen = getScreenShot();
                // 处理图像匹配逻辑
                if (openCVConfig.getXY(screen, templateImagePath) != null) {
                    Map<String, Integer> map = openCVConfig.getXY(screen, templateImagePath);
                    selectRunFileConfig.sendMouseClick(hwnd, map.get(COORDINATEX.value), map.get(COORDINATEY.value));
                    long time = condition.awaitNanos(500 * 1_000_000L);
                    Mat screen1 = getScreenShot();
                    if (openCVConfig.getXY(screen1, templateImagePath1) != null) {
                        Map<String, Integer> map1 = openCVConfig.getXY(screen1, templateImagePath1);
                        selectRunFileConfig.sendMouseClick(hwnd, map1.get(COORDINATEX.value), map1.get(COORDINATEY.value));
                    }
                } else if (openCVConfig.getXY(screen, templateImagePath1) != null) {
                    Map<String, Integer> map = openCVConfig.getXY(screen, templateImagePath1);
                    selectRunFileConfig.sendMouseClick(hwnd, map.get(COORDINATEX.value), map.get(COORDINATEY.value));
                } else if (openCVConfig.getXY(screen, templateImagePath2) != null) {
                    Map<String, Integer> map = openCVConfig.getXY(screen, templateImagePath2);
                    selectRunFileConfig.sendMouseClick(hwnd, map.get(COORDINATEX.value), map.get(COORDINATEY.value));
                }
                long time1 = condition.awaitNanos(500 * 1_000_000L);
                Mat screen3 = getScreenShot();
                System.out.println(openCVConfig.getSum(screen3, templateImagePath3));
                if (openCVConfig.getSum(screen3, templateImagePath3) >= 4) {
                    break;
                }
                long time2 = condition.awaitNanos(500 * 1_000_000L);
            } finally {
                lock.unlock();
            }
        }
    }

}
