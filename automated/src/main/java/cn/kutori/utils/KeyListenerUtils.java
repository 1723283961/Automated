package cn.kutori.utils;

import cn.kutori.application;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import javax.swing.*;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class KeyListenerUtils implements NativeKeyListener {
    public   boolean isRunning = false;  // 进程运行状态
    private final Set<Integer> pressedKeys = new HashSet<>();  // 存储按下的键
    private static final Logger logger = Logger.getLogger(application.class.getName());
    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        pressedKeys.add(e.getKeyCode());

        // 检查是否按下组合键 'O' 和 'P'
        if (pressedKeys.contains(NativeKeyEvent.VC_O) && pressedKeys.contains(NativeKeyEvent.VC_P)) {
            isRunning = !isRunning;  // 切换进程状态

            if (isRunning) {
                JOptionPane.showMessageDialog(null, "进程已启动", "提示", JOptionPane.INFORMATION_MESSAGE);
                logger.info("启动操作...");
                // 在这里添加启动操作的代码
            } else {
                JOptionPane.showMessageDialog(null, "进程已停止", "提示", JOptionPane.INFORMATION_MESSAGE);
                logger.info("停止操作...");
                // 在这里添加停止操作的代码
            }
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        // 释放按键时的逻辑
        pressedKeys.remove(e.getKeyCode());
        logger.info("按键释放: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
        // 按下并释放某个键时的逻辑
        logger.info("输入字符: " + e.getKeyChar());
    }

}
