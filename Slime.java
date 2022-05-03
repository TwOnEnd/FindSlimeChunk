package Structure;

import java.util.Random;

public class Slime {
    private int isSlimechunk(int xPosition, int zPosition, long seed) {
        Random rnd = new Random(
                seed +
                        (int) (xPosition * xPosition * 0x4c1906) +
                        (int) (xPosition * 0x5ac0db) +
                        (int) (zPosition * zPosition) * 0x4307a7L +
                        (int) (zPosition * 0x5f24f) ^ 0x3ad8025f);
        if ((rnd.nextInt(10) % 10) == 0) {
            return 1;
        } else {
            return 0;
        }
    }

    public void findSlimechunk(int r, int l, int num, long seed) {
        // https://www.chunkbase.com/apps/slime-finder#1896902807270617436
        // r 区块半径
        // l 有效直径
        // num 有效直径内数量
        final int[][] finder = new int[l][2 * r];
        final int[] count = new int[2 * r];
        int total = 0;
        long startTime = System.currentTimeMillis(); // 获取开始时间
        for (int i = 0; i < 2 * r; i++) {
            int x = i - r;
            int n = i % l;

            for (int j = 0; j < 2 * r; j++) {
                int z = j - r;

                count[j] -= finder[n][j];
                finder[n][j] = isSlimechunk(x, z, seed);
                count[j] += finder[n][j];
            }

            int sum = count[0] + count[1] + count[2] + count[3] + count[4] + count[5] + count[6] + count[7] + count[8]
                    + count[9] + count[10] /*+ count[11] + count[12] + count[13] + count[14] + count[15] + count[16]*/;// 随变量l加减

            for (int k = 0; k < 2 * r - l; k++) {
                if (sum > num) {
                    System.out.printf("x:%d|z:%d\t-> n=%d\n", (x + 1) * 16, (k - r) * 16, sum);// 往左下角x,z各取变量l格
                    total += 1;
                }
                sum += count[k + l] - count[k];
            }
        }
        long endTime = System.currentTimeMillis(); // 获取结束时间
        System.out.printf("finishTime:%dms | total:%d\n", (endTime - startTime), total);
    }
}
