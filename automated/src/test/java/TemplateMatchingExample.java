import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.io.File;
import java.util.List;
import java.util.ArrayList;
import org.opencv.core.Core;

import org.opencv.core.Scalar;

public class TemplateMatchingExample {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);  // 加载OpenCV库
    }

    // 加载图像库
    public static List<Mat> loadImageLibrary(String folderPath) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        List<Mat> imageLibrary = new ArrayList<>();
        for (File file : files) {
            if (file.getName().endsWith(".png") || file.getName().endsWith(".jpg")) {
                Mat img = Imgcodecs.imread(file.getAbsolutePath(), Imgcodecs.IMREAD_GRAYSCALE);
                imageLibrary.add(img);
            }
        }
        return imageLibrary;
    }

    // 获取屏幕截图的方法（仅模拟，实际应用时需要具体方法获取屏幕图像）
    public static Mat getScreenShot() {
        // 这里只是模拟，实际应用中可以用 Java AWT 的 Robot 类或其他方法获取屏幕截图
        // 用随机生成一张图来测试
        Mat image = Mat.ones(300, 300, CvType.CV_8UC1);
        Imgproc.putText(image, "Test", new Point(50, 50), Imgproc.FONT_HERSHEY_SIMPLEX, 1, new Scalar(255, 255, 255), 2);
        return image;
    }

    // 模板匹配方法
    public static void matchTemplate(Mat screen, List<Mat> imageLibrary, double threshold) {
        for (Mat template : imageLibrary) {
            // 执行模板匹配
            Mat result = new Mat();
            Imgproc.matchTemplate(screen, template, result, Imgproc.TM_CCOEFF_NORMED);

            // 查找匹配位置
            Core.MinMaxLocResult mmr = Core.minMaxLoc(result);
            if (mmr.maxVal >= threshold) {
                System.out.println("Found match with score: " + mmr.maxVal);
            }
        }
    }

    public static void main(String[] args) {
        // 加载图像库
        List<Mat> imageLibrary = loadImageLibrary("path_to_image_folder");  // 替换为实际路径

        // 模拟获取屏幕截图
        Mat screen = getScreenShot();

        // 设置匹配的阈值
        double threshold = 0.8;

        // 进行模板匹配
        matchTemplate(screen, imageLibrary, threshold);
    }
}
