## 巡回セールスマン問題（Travelling Salesman Problem）全探索解法

Java 8u192

### 何
引数で指定した数の都市を10000 * 10000 平面状にランダムに配置，
総当たり的に最短ルートを探索します．爆発的に増大するので注意  
具体的には14都市超えたあたりから実行時間がとんでもないことになってくるはず  
13都市なら多分3分くらい

### HOW TO USE

ex. 6つの都市を回るぞ(60通り)  

```
$ java TSP 6
java.awt.Point[x=734,y=6270]
java.awt.Point[x=8571,y=6761]
java.awt.Point[x=7742,y=2227]
java.awt.Point[x=8046,y=7723]
java.awt.Point[x=8957,y=4244]
java.awt.Point[x=8079,y=6689]
all paths: 60
minimal : 21978.4467543496
```
