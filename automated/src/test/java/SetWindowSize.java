import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;

    public class SetWindowSize {
        public static void main(String[] args) {
            // 查找目标窗口
            String windowTitle = "Clash for Windows"; // 替换成你窗口的标题
            WinDef.HWND hwnd = User32.INSTANCE.FindWindow(null, windowTitle);

            if (hwnd == null || !User32.INSTANCE.IsWindow(hwnd)) {
                System.err.println("窗口句柄无效！");
                return;
            }

            // 调整窗口大小和位置 (x, y, width, height)
            int x = 100;        // 窗口左上角 X 坐标
            int y = 100;        // 窗口左上角 Y 坐标
            int width = 100;    // 窗口宽度
            int height = 100;   // 窗口高度

            boolean resized = User32.INSTANCE.SetWindowPos(
                    hwnd,
                    null,
                    x, y, width, height,
                    WinUser.SWP_NOZORDER | WinUser.SWP_NOACTIVATE | WinUser.SWP_SHOWWINDOW
            );

            if (resized) {
                System.out.println("窗口大小已更改！");
            } else {
                System.err.println("无法更改窗口大小！");
                return;
            }

            // 强制重绘整个窗口区域
            User32.INSTANCE.InvalidateRect(hwnd, null, true);
            User32.INSTANCE.UpdateWindow(hwnd);

            System.out.println("窗口大小更改并强制重绘完成！");
            WinDef.RECT rect = new WinDef.RECT();
            User32.INSTANCE.GetWindowRect(hwnd, rect);
            System.out.println("窗口大小：宽度 = " + (rect.right - rect.left) + ", 高度 = " + (rect.bottom - rect.top));

        }
    }