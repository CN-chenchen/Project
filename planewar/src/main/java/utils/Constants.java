package utils;

import java.util.ArrayList;

import image.EnemyBullet;
import image.GameImage;
import image.PlayerBullet;

/**
 * Created by Hello on 2016/10/4.
 */

public class Constants {

    public static int display_width;

    public static int display_height;

    public static boolean isCatch = false;

    public static ArrayList<GameImage> gameImages = new ArrayList<GameImage>();

    public static ArrayList<PlayerBullet> bullets_player = new ArrayList<PlayerBullet>();

    public static ArrayList<EnemyBullet> bullets_enemy = new ArrayList<EnemyBullet>();

    public static long score = 0;
    public static int level = 1;
    public static int enemies = 35;//开始出现敌人的数字
    public static int speed = 16;//敌人移动速度
    public static int target = 150;//关卡目标
    public static int[][] update = {{1, 150, 35, 16}, {2, 150, 30, 17}, {3, 150, 30, 18}, {4, 150, 25, 19}, {5, 150, 25, 20}, {6, 150, 20, 21}};

}
